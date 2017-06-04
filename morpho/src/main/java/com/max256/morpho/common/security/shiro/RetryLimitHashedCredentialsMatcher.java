package com.max256.morpho.common.security.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import com.max256.morpho.common.cache.redis.RedisManager;
import com.max256.morpho.common.exception.SystemException;
import com.max256.morpho.common.security.shiro.cache.redis.RedisShiroCache;
import com.max256.morpho.common.util.ObjectStreamSerializeUtils;


/**
 * 自定义shiro凭证管理器 用来进行加密密码的校验匹配 和密码输错次数
 * ps：Shiro提供了CredentialsMatcher的散列实现HashedCredentialsMatcher，
 * 和之前的PasswordMatcher不同的是，它只用于密码验证，且可以提供自己的盐，而不是随机生成盐，
 * 且生成密码散列值的算法需要自己写，因为能提供自己的盐。 
 * 密码输入错误锁定账号依靠Ehcache自带的timeToIdleSeconds来保证锁定时间
 * 
 * @author fbf
 * 
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	
	
	/*设置这两个的目的是redis需要手动设置api确保过期时间 而ehcache在它的配置文件里设置过期时间来保证*/

	// 密码重试缓存，会根据用户名把密码，重复尝试密码时不在每次去读数据库获得密码
	private Cache<String, AtomicInteger> passwordRetryCache;
	//redis缓存管理器
	private RedisManager redisManager;
	
	//是否开启登录次数重试限制
	private Boolean enableRetryLimit;
	//允许的最大失败次数
	private Integer allowAttemptNum;
	//锁定时间 单位秒
	private Integer lockTime;


	public Boolean getEnableRetryLimit() {
		return enableRetryLimit;
	}
	public void setEnableRetryLimit(Boolean enableRetryLimit) {
		//如果没有参数 默认 关闭
		if(enableRetryLimit==null){
			this.enableRetryLimit = false;
		}else{
			this.enableRetryLimit = enableRetryLimit;
		}
	}
	public Integer getAllowAttemptNum() {
		return allowAttemptNum;
	}
	public void setAllowAttemptNum(Integer allowAttemptNum) {
		//如果没有参数 或者参数不合法使用默认值 5次
		if(allowAttemptNum==null||allowAttemptNum.intValue()<2){
			this.allowAttemptNum = 5;
		}else{
			this.allowAttemptNum = allowAttemptNum;
		}
	}
	public Integer getLockTime() {
		return lockTime;
	}
	public void setLockTime(Integer lockTime) {
		//如果没有参数 或者参数不合法使用默认值 60秒
		if(lockTime==null||lockTime.intValue()<5){
			this.lockTime = 60;
		}else{
			this.lockTime = lockTime;
		}
	}
	// 构造器注入，把缓存管理器注入 (同时使用了ehcache和redis)
	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager, RedisManager redisManager) {
		this.passwordRetryCache = cacheManager.getCache("passwordRetryCache");
		this.redisManager = redisManager;
	}
	// 单构造器注入，把缓存管理器注入 只支持单ehcache不支持redis
	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		this.passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	/**
	 * 
	 * token参数 the AuthenticationToken submitted during the authentication
	 * attempt.是用户输入的令牌 info 参数the AuthenticationInfo stored in the system
	 * matching the token principal是数据库里查到的用户账号信息
	 * 
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
			//获得用户名
		 	String username = token.getPrincipal().toString();
		 	AtomicInteger retryCount=null;
		 	if(this.enableRetryLimit){
		 		 // 获得尝试登录次数
		 		retryCount= passwordRetryCache.get(username);
		        //如果为空说明之前没有尝试失败过 
		        if (retryCount == null) {
		        	//在登录限制系统里初始化限制它
		            retryCount = new AtomicInteger(0);
		            //如果是redis实现
		            //需要设置redis的过期时间
		            if(passwordRetryCache instanceof RedisShiroCache)
		            {
		            	try {
		            		//新建并存储
							redisManager.saveValueByKey(RedisShiroCache.DB_INDEX, ((RedisShiroCache<String, AtomicInteger>)passwordRetryCache).generateCacheKey(username).getBytes(), ObjectStreamSerializeUtils.serialize(retryCount), this.lockTime);
						} catch (Exception e) {
							throw new SystemException("持久化输入错误次数到redis时,发生异常",0);
						}
		            }else
		            {	//如果是其它缓存基础设施的话例如ehcache他要利用timeToIdleSeconds来保证 这里也是10分钟
		            	passwordRetryCache.put(username, retryCount);
		            }
		        }
		        
		        if(retryCount.intValue() >= this.allowAttemptNum)
		        {
		        	//错误次数太多
		        	throw new  ExcessiveAttemptsException();
		        }
		 	}
	       
		 	//匹配
	        boolean matches = super.doCredentialsMatch(token, info);
	        //如果开启了重试匹配
	        if(this.enableRetryLimit){
	        	 if (matches) {
	 	            //从缓存中移除该用户的登录记录
	 	            passwordRetryCache.remove(username);
	 	        }else{
	 	        	//密码错误失败
	 	        	//如果是redis缓存
	 	        	if(passwordRetryCache instanceof RedisShiroCache)
	 	 	        {
	 	 	        	try {
	 	 	        		//更新失败次数同时更新到期时间
	 	 					redisManager.saveValueByKey(RedisShiroCache.DB_INDEX, ((RedisShiroCache<String, AtomicInteger>)passwordRetryCache).generateCacheKey(username).getBytes(), ObjectStreamSerializeUtils.serialize(new AtomicInteger(retryCount.incrementAndGet())), this.lockTime);
	 	 				} catch (Exception e) {
	 	 					throw new SystemException("持久化输入错误次数到redis时,发生异常",0);
	 	 				}
	 	 	        }else{
	 	 	        	//其他缓存
	 	 	        	passwordRetryCache.put(username, new AtomicInteger(retryCount.incrementAndGet()));
	 	 	        }
	 	        }
	        }

	        return matches;
	}

	public Cache<String, AtomicInteger> getPasswordRetryCache() {
		return passwordRetryCache;
	}

	public void setPasswordRetryCache(Cache<String, AtomicInteger> passwordRetryCache) {
		this.passwordRetryCache = passwordRetryCache;
	}
	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}
}

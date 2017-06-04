package com.max256.morpho.common.security.shiro;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.sys.service.SysRoleService;
import com.max256.morpho.sys.service.SysUserService;

/**
 * 数据库存储的shiro Realm 这是一个spring bean
 * 
 * @author fbf
 * 
 */
public class ShiroDbRealm extends AuthorizingRealm {
	// 日志工具
	protected Logger logger = LogManager.getLogger(ShiroDbRealm.class);

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysRoleService sysRoleService;
	
	
	/**
	 * 清空所有缓存
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	/**
	 * 清除所有用户认证缓存
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	/**
	 * 清除所有用户授权缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * 清空指定PrincipalCollection的所有缓存
	 */
	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	/**
	 * 清空指定PrincipalCollection的认证信息缓存.
	 */
	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	/**
	 * 清空指定PrincipalCollection的权限信息缓存.
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
	
	/**
     * 清除当前用户权限信息
     */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	
	/**
     * 清除当前用户认证信息
     */
	public void clearCachedAuthenticationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthenticationInfo(principals);
	}

	/**
	 * 认证回调函数,登录时调用
	 * 首先根据传入的用户名获取User信息；然后如果user为空，那么抛出没找到帐号异常UnknownAccountException；
	 * 如果user找到但锁定了抛出锁定异常LockedAccountException；最后生成AuthenticationInfo信息，
	 * 交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
	 * 如果不匹配将抛出密码错误异常IncorrectCredentialsException；
	 * 另外如果密码重试此处太多将抛出超出重试次数异常ExcessiveAttemptsException；
	 * 在组装SimpleAuthenticationInfo信息时， 需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt），
	 * CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// 做日志info级别
		// logger.info("Shiro开始登录认证----------------------");

		// 当用户登录了系统以后，把username和password放入到了UsernamePasswordToken中
		// 在这个地方把登录的username能够提取出来
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = (String) usernamePasswordToken.getPrincipal();
		// 按照用户名去数据库查询（注意这里不需要密码，只需查询有没有此用户即可）
		SysUser param=new SysUser();
		param.setUserName(username);
		SysUser sysUser = this.sysUserService.selectOne(param);
		// 如果查询出来结果为空，直接shiro异常，不再进行下一步
		if (sysUser == null) {
			throw new UnknownAccountException();// 没找到帐号
		} else {
			// 如果得到的sysyUser的IsValid为1，抛出帐号锁定异常
			if (SysUser.STOP.equals(sysUser.getIsValid())) {
				throw new LockedAccountException(); // 帐号锁定
			}
			

			// 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
			// 当用户执行登录时,在方法处理上要实现user.login(token);
			// 然后会自动进入这个类进行认证
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
			// 能查到此用户的话
			// SecurityUtils.getSubject().getSession().setAttribute("user",
			// sysUser);
			// 把用户的信息封装到该对象中
			// SimpleAuthenticationInfo(sysUser,sysUser.getUserPassword().toCharArray(),getName())
			// 第一个参数是数据库查询出来的sysUser（这里肯定不为空，为空时早已经返回null）
			// 第二个参数是数据库查询出来的sysUser的密码，toCharArray()为了安全，String在内存中，有快照
			// 第三个参数是本realm的名字

			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
					sysUser, // 用户entity
					sysUser.getUserPassword().toCharArray(), // 密码
					ByteSource.Util.bytes(sysUser.getSalt()),// salt
					getName() // realm name
			);


			// 该对象封装了用户名和密码的信息
			return authenticationInfo;
		}

	}

	/**
	 * 完成授权
	 * 只有需要验证权限时才会调用, 授权查询回调函数
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 准备返回数据
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 得到用户
		SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
		// 查找角色 设置角色支持多角色
		authorizationInfo.setRoles(sysRoleService.findSysRoles(sysUser.getSysRoleIds().split(",")));
		// 根据角色查找权限 设置权限字符串
		authorizationInfo.setStringPermissions(sysRoleService.findPermissions(sysUser.getSysRoleIds()));
		// 放到授权信息实体中返回
		return authorizationInfo;
	}

}

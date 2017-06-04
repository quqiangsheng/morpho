package com.max256.morpho.common.security.shiro.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import com.max256.morpho.common.cache.redis.RedisManager;

/**
 * 
 *  RedisShiroCacheManager
 *  Redis管理
 *  直接实现shiro提供的CacheManager接口不包装其他cache框架
 *  内部调用RedisManager实现redis数据操作
 */
public class RedisShiroCacheManager implements CacheManager {

    private RedisManager cacheManager;
    
    public RedisManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(RedisManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new RedisShiroCache<K, V>(name, getCacheManager());
    }

}

package com.max256.morpho.common.security.shiro.cache.ehcache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 
 *  EhCacheShiroCacheManager
 *  实现shiro的CacheManager接口
 *  ehCache管理
 */
public class EhCacheShiroCacheManager implements CacheManager {

	//采用spring的cacheManager实现
    private org.springframework.cache.CacheManager cacheManager;



	/**
     * 设置spring cacheManager
     *
     * @param cacheManager
     */
    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    public org.springframework.cache.CacheManager getCacheManager() {
		return cacheManager;
	}

    @Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
    	org.apache.shiro.cache.Cache<K, V> cache=new EhcacheShiroCache<K, V>(name, getCacheManager());
        return cache;
    }



}

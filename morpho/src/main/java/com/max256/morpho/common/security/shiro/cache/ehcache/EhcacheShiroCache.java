package com.max256.morpho.common.security.shiro.cache.ehcache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import net.sf.ehcache.Ehcache;

/**
 *  EhcacheShiroCache
 *  继承shiro 的cache接口符合shiro的扩展
 *  采用spring提供的接口完成操作
 */
public class EhcacheShiroCache<K, V> implements Cache<K, V>{
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EhcacheShiroCache.class);
	
	//采用EhCacheCacheManager实现cache访问 通过构造注入
	private CacheManager cacheManager;
	private String name;


	/**构造器
	 * @param name ehcache哪个区
	 * @param cacheManager 注入springCahceManager
	 */
	public EhcacheShiroCache(String name, CacheManager cacheManager) {
		this.name=name;
        this.cacheManager = cacheManager;
    }

	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		//得到ehcache某个区域的cache
		org.springframework.cache.Cache cache=cacheManager.getCache(name);
    	ValueWrapper value = cache.get(key);
        if (value instanceof SimpleValueWrapper) {
            return (V)((SimpleValueWrapper) value).get();
        }
        return (V)value;
    }

    
    public V put(K key, V value) throws CacheException {
    	//得到ehcache某个区域的cache
    	org.springframework.cache.Cache cache=cacheManager.getCache(name);
    	cache.put(key, value);
        return value;
    }

    
    public V remove(K key) throws CacheException {
    	//得到ehcache某个区域的cache
    	org.springframework.cache.Cache cache=cacheManager.getCache(name);
    	V previos = get(key);
    	cache.evict(key);
        return previos;
    }

    
    public void clear() throws CacheException {
    	//得到ehcache某个区域的cache
    	org.springframework.cache.Cache cache=cacheManager.getCache(name);
    	cache.clear();
    }

    
    public int size() {
    	//得到ehcache某个区域的cache
    	org.springframework.cache.Cache cache=cacheManager.getCache(name);
        if(cache.getNativeCache() instanceof Ehcache) {
            Ehcache ehcache = (Ehcache) cache.getNativeCache();
            return ehcache.getSize();
        }
        throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
    }

    
    @SuppressWarnings("unchecked")
	public Set<K> keys() {
    	//得到ehcache某个区域的cache
    	org.springframework.cache.Cache cache=cacheManager.getCache(name);
        if(cache.getNativeCache() instanceof Ehcache) {
            Ehcache ehcache = (Ehcache) cache.getNativeCache();
            return new HashSet<K>(ehcache.getKeys());
        }
        throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
    }

    
    @SuppressWarnings("unchecked")
	public Collection<V> values() {
    	//得到ehcache某个区域的cache
    	org.springframework.cache.Cache cache=cacheManager.getCache(name);	
        if(cache.getNativeCache() instanceof Ehcache) {
            Ehcache ehcache = (Ehcache) cache.getNativeCache();
            List<K> keys = ehcache.getKeys();
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (K key : keys) {
                    V value = get(key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        }
        throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
    }
}

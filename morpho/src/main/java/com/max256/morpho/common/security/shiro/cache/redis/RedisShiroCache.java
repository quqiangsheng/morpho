package com.max256.morpho.common.security.shiro.cache.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.max256.morpho.common.cache.redis.RedisManager;
import com.max256.morpho.common.util.ObjectStreamSerializeUtils;

import redis.clients.jedis.Jedis;

/**
 * 
 * RedisShiroCache
 * 使用redis来实现shiro cache接口
 */

public class RedisShiroCache<K, V> implements Cache<K, V> {

	private static final Logger logger = LoggerFactory.getLogger(RedisShiroCache.class);
	
	/**
	 * 为了不和其他的缓存混淆，采用追加前缀方式以作区分
	 */
    public static final String REDIS_SHIRO_CACHE = "redis_shrio_cache";
    /**
     * Redis 分片(分区)，也可以在配置文件中配置,默认dbIndex=0
     */
    public static final int DB_INDEX = 0;

    private RedisManager redisManager;
    
    private String name;

    
    public RedisShiroCache(String name, RedisManager redisManager) {
        this.name = name;
        this.redisManager = redisManager;
    }


    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
	@Override
    public V get(K key) throws CacheException {
        byte[] byteValue = new byte[0];
        try {
            byteValue = redisManager.getValueByKey(DB_INDEX, generateCacheKey(key).getBytes());
        } catch (Exception e) {
        	logger.error("get value by cache throw exception",e);
        }
        return (V) ObjectStreamSerializeUtils.deserialize(byteValue);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
        	redisManager.saveValueByKey(DB_INDEX, generateCacheKey(key).getBytes(), ObjectStreamSerializeUtils.serialize(value), -1);
        } catch (Exception e) {
        	logger.error("put cache throw exception",e);
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
        	redisManager.deleteByKey(DB_INDEX, generateCacheKey(key).getBytes());
        } catch (Exception e) {
        	logger.error("remove cache  throw exception",e);
        }
        return previos;
    }

    @Override
    public void clear() throws CacheException {
    	Jedis jedis = null;
    	try
    	{
    		jedis = redisManager.getJedis();
        	jedis.select(DB_INDEX);
        	jedis.flushDB();
    	}finally
    	{
    		if(null != jedis)
    		{
    			jedis.close();
    		}
    	}
    	
    	
    	
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @SuppressWarnings("unchecked")
	@Override
    public Set<K> keys() {
    	Jedis jedis = null;
    	try
    	{
    		jedis = redisManager.getJedis();
        	jedis.select(DB_INDEX);
            Set<String> keys = jedis.keys("*" + getName() + "*");
            return (Set<K>)keys;
    	}finally
    	{
    		if(null != jedis)
    		{
    			jedis.close();
    		}
    	}
    	
    }

    @Override
    public Collection<V> values() {
    	Set<K> keys = keys();
    	if (!CollectionUtils.isEmpty(keys)) {
            final List<V> values = new ArrayList<V>(keys.size());
            keys.forEach(new Consumer<K>() {
				@Override
				public void accept(K key) {
					V value = get(key);
				    if (value != null) {
				        values.add(value);
				    }
				}
			});
            return Collections.unmodifiableList(values);
        } else {
            return Collections.emptyList();
        }
    }

    public String generateCacheKey(Object key) {
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }

}

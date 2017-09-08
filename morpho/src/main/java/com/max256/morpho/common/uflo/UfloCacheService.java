package com.max256.morpho.common.uflo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.bstek.uflo.expr.impl.ProcessMapContext;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.service.CacheService;
import com.max256.morpho.common.util.ObjectStreamSerializeUtils;
import com.max256.morpho.common.util.SpringUtils;

import net.sf.ehcache.Ehcache;

/**
 * uflo2缓存实现 
 * 自动检测redis是否可用 可用的话自动使用redis 
 * 再检测ehcache是否可用 可用的话用ehcache
 * 都不可用则使用ConcurrentHashMap
 * @author admin
 *
 */
@Component
public class UfloCacheService implements CacheService {
	
	public static String EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME="ufloProcessDefinitionCache";//ehcache配置文件中UfloCacheService使用的cache  name 请保持一致
	public static final String REDIS_UFLO_PROCESS_DEFINITION_CACHE_NAME = "uflo_process_definition_cache:";//redis里存储时前缀
    public static final String REDIS_UFLO_PROCESS_DEFINITION_CACHE_NAME_ALL = "*uflo_process_definition_cache:*";
    
    public static String EHCAHCE_UFLO_CONTEXT_CACHE_NAME="ufloContextCache";//ehcache配置文件中UfloCacheService使用的cache  name 请保持一致
	public static final String REDIS_UFLO_CONTEXT_CACHE_NAME = "uflo_context_cache:";//redis里存储时前缀
    public static final String REDIS_UFLO_CONTEXT_CACHE_NAME_ALL = "*uflo_context_cache:*";
    
    
	private Map<Long,ProcessDefinition> processDefinitionMap=new ConcurrentHashMap<Long,ProcessDefinition>();
	private Map<Long,ProcessMapContext> contextMap=new ConcurrentHashMap<Long,ProcessMapContext>();
	
	//尝试注入
	/*@Resource(name="redisTemplate")*/
	private StringRedisTemplate redisTemplate;
	/*@Resource(name="ehCacheManager")*/
	private EhCacheCacheManager ehCacheCacheManager;
	
	
	
	/**
	 * 构造方法里手动注入 如果bean不存在则注入null
	 */
	public UfloCacheService() {
		super();
		if(SpringUtils.containsBean("redisTemplate")){
			Object find=SpringUtils.getBean("redisTemplate");
			if(find instanceof StringRedisTemplate){
				StringRedisTemplate findStringRedisTemplate=(StringRedisTemplate) find;	
				setRedisTemplate(findStringRedisTemplate);
			}else{
				setRedisTemplate(null);
			}
			
			
		}else{
			setRedisTemplate(null);
		}
		if(SpringUtils.containsBean("ehCacheManager")){
			EhCacheCacheManager findEhCacheCacheManager=(EhCacheCacheManager) SpringUtils.getBean("ehCacheManager");
			setEhCacheCacheManager(findEhCacheCacheManager);
		}else{
			setEhCacheCacheManager(null);
		}

	}
	public Map<Long, ProcessDefinition> getProcessDefinitionMap() {
		return processDefinitionMap;
	}
	public void setProcessDefinitionMap(Map<Long, ProcessDefinition> processDefinitionMap) {
		this.processDefinitionMap = processDefinitionMap;
	}
	public Map<Long, ProcessMapContext> getContextMap() {
		return contextMap;
	}
	public void setContextMap(Map<Long, ProcessMapContext> contextMap) {
		this.contextMap = contextMap;
	}
	public StringRedisTemplate getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	public EhCacheCacheManager getEhCacheCacheManager() {
		return ehCacheCacheManager;
	}
	public void setEhCacheCacheManager(EhCacheCacheManager ehCacheCacheManager) {
		this.ehCacheCacheManager = ehCacheCacheManager;
	}
	@Override
	public ProcessDefinition getProcessDefinition(long processId) {
		if(redisTemplate!=null){
			//使用redis
			String findBase64Pd=redisTemplate.opsForValue().get(REDIS_UFLO_PROCESS_DEFINITION_CACHE_NAME+processId);
			if(StringUtils.isBlank(findBase64Pd)){
				return null;
			}else{
				byte[] decodeFromString = Base64Utils.decodeFromString(findBase64Pd);
				if(decodeFromString==null){
					return null;
				}else{
					Object deserialize = ObjectStreamSerializeUtils.deserialize(decodeFromString);
					if(deserialize==null){
						return null;
					}else{
						return (ProcessDefinition)deserialize;
					}
				}
				
			}
		}else if(ehCacheCacheManager!=null&&ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME)!=null){
			//使用ehCache
			ProcessDefinition findProcessDefinition=ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME).get(processId, ProcessDefinition.class);
			return findProcessDefinition;
		}else{
			//使用ConcurrentHashMap
			return processDefinitionMap.get(processId);
		}
		
	}
	@Override
	public void putProcessDefinition(long processId, ProcessDefinition process) {
		if(redisTemplate!=null){
			//使用redis
			byte[] serialize = ObjectStreamSerializeUtils.serialize(process);
			String encodeToString = Base64Utils.encodeToString(serialize);
			redisTemplate.opsForValue().set(REDIS_UFLO_PROCESS_DEFINITION_CACHE_NAME+processId, encodeToString);
		}else if(ehCacheCacheManager!=null&&ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME)!=null){
			//使用ehCache
			ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME).put(processId, process);
			
		}else{
			//使用ConcurrentHashMap
			processDefinitionMap.put(processId, process);
		}
		
	}
	@Override
	public boolean containsProcessDefinition(long processId) {
		ProcessDefinition processDefinition = getProcessDefinition(processId);
		if(processDefinition==null){
			return false;	
		}else{
			return true;
		}
		
	}
	@Override
	public Collection<ProcessDefinition> loadAllProcessDefinitions() {
		if(redisTemplate!=null){
			Collection<ProcessDefinition> listProcessDefinition=new ArrayList<>();
			//使用redis
			Set<String> findAllKey=redisTemplate.keys(REDIS_UFLO_PROCESS_DEFINITION_CACHE_NAME_ALL);
			for (String string : findAllKey) {
				String findBase64Pd=redisTemplate.opsForValue().get(string);
				byte[] decodeFromString = Base64Utils.decodeFromString(findBase64Pd);
				if(decodeFromString==null){
				}else{
					Object deserialize = ObjectStreamSerializeUtils.deserialize(decodeFromString);
					if(deserialize==null){	
					}else{
						listProcessDefinition.add((ProcessDefinition)deserialize) ;
					}
				}
			}
			return listProcessDefinition;	
		}else if(ehCacheCacheManager!=null&&ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME)!=null){
			//使用ehCache
			EhCacheCache ehCacheCache=(EhCacheCache)ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME);
			Ehcache nativeCache = ehCacheCache.getNativeCache();
			Collection<ProcessDefinition> listProcessDefinition=new ArrayList<>();
			@SuppressWarnings("rawtypes")
			List keys = nativeCache.getKeys();
			for (Object object : keys) {
				listProcessDefinition.add((ProcessDefinition) nativeCache.get(object).getObjectValue());
			}
			return listProcessDefinition;
		}else{
			//使用ConcurrentHashMap
			return processDefinitionMap.values();
		}
	}
	@Override
	public void removeProcessDefinition(long processId) {
		if(redisTemplate!=null){
			//使用redis
			redisTemplate.delete(REDIS_UFLO_PROCESS_DEFINITION_CACHE_NAME+processId);
			
		}else if(ehCacheCacheManager!=null&&ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME)!=null){
			//使用ehCache
			ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_PROCESS_DEFINITION_CACHE_NAME).evict(processId);
		}else{
			//使用ConcurrentHashMap
			processDefinitionMap.remove(processId);
		}
	}
	
	@Override
	public ProcessMapContext getContext(long processInstanceId) {
		
		if(redisTemplate!=null){
			//使用redis
			String findBase64Piid=redisTemplate.opsForValue().get(REDIS_UFLO_CONTEXT_CACHE_NAME+processInstanceId);
			if(StringUtils.isBlank(findBase64Piid)){
				return null;
			}else{
				byte[] decodeFromString = Base64Utils.decodeFromString(findBase64Piid);
				if(decodeFromString==null){
					return null;
				}else{
					Object deserialize = ObjectStreamSerializeUtils.deserialize(decodeFromString);
					if(deserialize==null){
						return null;
					}else{
						return (ProcessMapContext)deserialize;
					}
				}
				
			}
		}else if(ehCacheCacheManager!=null&&ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_CONTEXT_CACHE_NAME)!=null){
			//使用ehCache
			ProcessMapContext findPmp=ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_CONTEXT_CACHE_NAME).get(processInstanceId, ProcessMapContext.class);
			return findPmp;
		}else{
			//使用ConcurrentHashMap
			return contextMap.get(processInstanceId);
		}
	}
	@Override
	public void putContext(long processInstanceId, ProcessMapContext context) {
		
		if(redisTemplate!=null){
			//使用redis
			byte[] serialize = ObjectStreamSerializeUtils.serialize(context);
			String encodeToString = Base64Utils.encodeToString(serialize);
			redisTemplate.opsForValue().set(REDIS_UFLO_CONTEXT_CACHE_NAME+processInstanceId, encodeToString);
		}else if(ehCacheCacheManager!=null&&ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_CONTEXT_CACHE_NAME)!=null){
			//使用ehCache
			ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_CONTEXT_CACHE_NAME).put(processInstanceId, context);
			
		}else{
			//使用ConcurrentHashMap
			contextMap.put(processInstanceId, context);
		}
		
	}
	@Override
	public void removeContext(long processInstanceId) {
		
		if(redisTemplate!=null){
			//使用redis
			redisTemplate.delete(REDIS_UFLO_CONTEXT_CACHE_NAME+processInstanceId);
			
		}else if(ehCacheCacheManager!=null&&ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_CONTEXT_CACHE_NAME)!=null){
			//使用ehCache
			ehCacheCacheManager.getCache(UfloCacheService.EHCAHCE_UFLO_CONTEXT_CACHE_NAME).evict(processInstanceId);
		}else{
			//使用ConcurrentHashMap
			contextMap.remove(processInstanceId);
		}
		
	}
	@Override
	public boolean containsContext(long processInstanceId) {
		ProcessMapContext context = getContext(processInstanceId);
		if(context==null){
			return false;
		}else{
			return true;
		}
		
	}

}

package com.max256.morpho.common.cache;


import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;

/**
 * spring cache的默认key生成策略仅仅是同个cache name里寻找key 
 * 此时的key仅仅是方法的入参 在实际中是远远不够的 
 * 这里把key的生成策略改为类名+方法名+入参 更实用
 * @author fbf
 * @since 2016年10月31日 上午10:28:11
 * @version V1.0
 * 
 */
public class MethodSignatureCacheKeyGenerator implements KeyGenerator {

    public static final String NO_PARAM_KEY = "NO_PARAM_KEY";
    public static final String NULL_PARAM_KEY = "NULL_PARAM_KEY";
	protected Logger log = LogManager.getLogger(this.getClass());
    
    @Override
    public Object generate(Object target, Method method, Object... params) {

        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getSimpleName()).append(".").append(method.getName()).append(":");
        //没有参数的key
        if (params.length == 0) {
            return key.append(NO_PARAM_KEY).toString();
        }
        for (Object param : params) {
            if (param == null) {
            	//参数为null
                log.warn("input null param for Spring cache, use default key={}", NULL_PARAM_KEY);
                key.append(NULL_PARAM_KEY);
            } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                int length = Array.getLength(param);
                for (int i = 0; i < length; i++) {
                    key.append(Array.get(param, i));
                    key.append(',');
                }
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                key.append(param);
            } 
            /*else if (param instanceof Collection) {
            	 Collection temp=(Collection)param;
            	 for (Iterator iterator = temp.iterator(); iterator.hasNext();) {					
					 key.append((Object) iterator.next());
                     key.append(',');
				}
            }*/
            else if (param instanceof String) {
            	 key.append((String)param);
           }else {
                log.debug("Using an object as a cache key may lead to unexpected results. " +
                        "Either use @Cacheable(key=..) or implement CacheKey. Method is " + target.getClass() + "#" + method.getName());
                key.append(param.hashCode());
            }
            key.append('-');
        }

        String finalKey = key.toString();
        log.debug("using cache key={}", finalKey);
        return finalKey;
    }

}

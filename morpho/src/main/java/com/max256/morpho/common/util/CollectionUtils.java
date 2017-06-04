package com.max256.morpho.common.util;

import java.util.Iterator;
import java.util.Map;

/**
 * 集合工具类
 * @author fbf
 *
 */
public class CollectionUtils {
	/**map toString 
	 * map必须是Map<String,String> 或者重写了toString方法的实体类
	 * @param map
	 * @return
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	public static String mapToString( Map map){
		StringBuilder sb=new StringBuilder();
		
		Iterator<String> keys = map.keySet().iterator(); 
	    while(keys.hasNext()) { 
			String key = (String) keys.next(); 
		    String value=map.get(key).toString(); 
		    sb.append("[{key:");
		    sb.append(key);
		    sb.append("},{value:");
		    sb.append(value);
		    sb.append("}]");
	    }
		return sb.toString();
		
	}
}

package com.max256.morpho.common.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 基于Jackson的JSON相关相关工具类
 * @author fbf
 * @since 2016年8月4日 下午4:13:05
 * @version V1.0
 * 
 */
public class JsonUtils {
	//日志
	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	//JsonMapper对象
	private final static ObjectMapper objectMapper = new ObjectMapper();

	/**方法从给定的JSON内容字符串反序列化JSON内容 。
	 * @param content
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public static List readListValue(String content) {
		try {
			return objectMapper.readValue(content, List.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Lists.newArrayList();
	}

	/**方法从给定的JSON内容字符串反序列化JSON内容 。
	 * @param content
	 * @return Map<String,object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> readValue(String content) {
		try {
			return objectMapper.readValue(content, Map.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Maps.newHashMap();
	}
	
	/**
	 * 把java对象序列化为JSON字符串
	 * @param value 是准备序列化为JSON 字符串的对象
	 * @return 序列化后的JSON字符串
	 */
	public static String writeValueAsString(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}

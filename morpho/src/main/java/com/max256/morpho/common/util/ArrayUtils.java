package com.max256.morpho.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Array工具类
 * 
 * fbf
 */
public class ArrayUtils {
	/**
	 * 字符串数组转换为Integer类型的list <功能详细描述>
	 * 
	 * @param ids
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static List<Integer> convertStringArrayToIntegerList(String[] ids) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			for (int i = 0; i < ids.length; i++) {
				if (StringUtils.isBlank(ids[i])) {
					continue;
				}
				list.add(Integer.valueOf(ids[i]));
			}
		} catch (Exception e) {
			throw new RuntimeException("字符串数组转换为Integer类型的list时发生异常!", e);
		}
		return list;
	}

	/**
	 * 字符串数组转换为Long类型的list <功能详细描述>
	 * 
	 * @param ids
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static List<Long> convertStringArrayToLongList(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		try {
			for (int i = 0; i < ids.length; i++) {
				if (StringUtils.isBlank(ids[i])) {
					continue;
				}
				list.add(Long.valueOf(ids[i]));
			}
		} catch (Exception e) {
			throw new RuntimeException("字符串数组转换为Long类型的list时发生异常!", e);
		}
		return list;
	}

	/**
	 * 字符串数组转换为String类型的list <功能详细描述>
	 * 
	 * @param ids
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static List<String> convertStringArrayToStringList(String[] ids) {
		List<String> list = new ArrayList<String>();
		try {
			for (int i = 0; i < ids.length; i++) {
				if (StringUtils.isBlank(ids[i])) {
					continue;
				}
				list.add(ids[i]);
			}
		} catch (Exception e) {
			throw new RuntimeException("字符串数组转换为String类型的list时发生异常!", e);
		}
		return list;
	}

	/**
	 * 数组转list(去除空字符串元素) <功能详细描述>
	 * 
	 * @param ids
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<?> toList(T[] ids, Class<?> type) {
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		try {
			for (int i = 0; i < ids.length; i++) {
				if ("".equals(ids[i])) {
					continue;
				}
				if (type.equals(String.class)) {
					list.add(String.valueOf(ids[i]));
				}
				if (type.equals(Integer.class)) {
					list.add(Integer.valueOf(String.valueOf(ids[i])));
				}
				if (type.equals(Long.class)) {
					list.add(Long.valueOf(String.valueOf(ids[i])));
				}
				if (type.equals(Float.class)) {
					list.add(Float.valueOf(String.valueOf(ids[i])));
				}
				if (type.equals(Double.class)) {
					list.add(Double.valueOf(String.valueOf(ids[i])));
				}
				if (type.equals(Boolean.class)) {
					list.add(Boolean.valueOf(String.valueOf(ids[i])));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("数组类型转换成list时发生异常!", e);
		}
		return list;
	}
}

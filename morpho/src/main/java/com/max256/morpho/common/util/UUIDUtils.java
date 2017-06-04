package com.max256.morpho.common.util;

import java.util.UUID;

/**
 * UUIDUtils uuid工具类
 * 
 * @author fbf
 * 
 */
public class UUIDUtils {

	/**
	 * @return 返回32位uuid,把系统的36位带‘-’的‘-’去掉
	 */
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/**
	 * @return 返回36位系统产生的uuid
	 */
	public static String get36UUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

}

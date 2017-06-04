package com.max256.morpho.common.security.encrypt;

import java.util.Random;

/**
 * 加密模块专用 用于生成随机aes密钥
 * 
 * @author fbf
 *
 */
public class RandomUtils {

	public static String getRandom(int length) {
		//每次实例化用于减少random相似的几率
		Random random = new Random();
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}

	public static String getRandomNum(int length) {
		//每次实例化用于减少random相似的几率
		Random random = new Random();
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			ret.append(Integer.toString(random.nextInt(10)));
		}
		return ret.toString();
	}

}

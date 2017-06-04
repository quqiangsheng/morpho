package com.max256.morpho.common.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * PasswordUtils密码加密工具类 这里的配置要和shiro配置文件中凭证匹配器里的配置一致！！ 属性和方法均为静态的直接使用 不要实例化！！
 * 
 * @author fbf
 * 
 */
public class PasswordUtils {
	// 安全随机数生成器，apache shiro提供的 SecureRandomNumberGenerator用于生成一个随机数
	private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	// 加密方式，命名遵循java安全api里标准命名，这里使用md5
	public static String algorithmName = "md5";
	// hash次数 默认为2次
	public static int hashIterations = 2;

	/**
	 * 加密密码
	 * 
	 * @param String
	 *            password传入密码
	 * @return 加密后的密码
	 */
	public static PasswordData encryptPassword(String password) {

		// 随机生成salt
		String salt = randomNumberGenerator.nextBytes().toHex();
		// 加密密码
		// Shiro还提供了通用的散列支持，通过调用SimpleHash时指定散列算法，其内部使用了Java的MessageDigest实现。
		String newPassword = new SimpleHash(algorithmName, password,
				ByteSource.Util.bytes(salt), hashIterations).toHex();
		PasswordData pd = new PasswordData(newPassword, salt);
		return pd;
	}
	/**
	 * 加密密码 
	 * 
	 * @param String
	 *            password传入密码
	 *            String salt指定的salt
	 * @return 加密后的密码
	 */
	public static PasswordData encryptPassword(String password,String salt) {
		// 加密密码
		// Shiro还提供了通用的散列支持，通过调用SimpleHash时指定散列算法，其内部使用了Java的MessageDigest实现。
		String newPassword = new SimpleHash(algorithmName, password,
				ByteSource.Util.bytes(salt), hashIterations).toHex();
		PasswordData pd = new PasswordData(newPassword, salt);
		return pd;
	}

	// 测试
	public static void main(String[] args) {
		/*
		 * PasswordUtils pu=new PasswordUtils();
		 * System.out.println(pu.encryptPassword("admin"));
		 */
		System.out.println(PasswordUtils.encryptPassword("5589761"));
	}
}

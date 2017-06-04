package com.max256.morpho.common.security.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


/**
 * aes算法用于加密文本
 * 使用AES/ECB/PKCS5Padding算法
 * @author fbf
 *
 */
public class AES {
	/**
	 * 加密
	 * 
	 * @param data
	 *            需要加密的内容
	 * @param key
	 *            加密密码
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		// 参数校验
		CheckUtils.notEmpty(data, "data");
		CheckUtils.notEmpty(key, "key");
		if(key.length!=16){
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES"); 
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat,"AES");
			//创建密码器
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);
			//初始化 加密模式 并给定秘钥
			cipher.init(Cipher.ENCRYPT_MODE, seckey);
			//执行加密
			byte[] result = cipher.doFinal(data);
			//返回加密后的字节数组
			return result; 
		} catch (Exception e){
			throw new RuntimeException("encrypt fail!", e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @return 解密后的字节数组
	 */
	public static byte[] decrypt(byte[] data, byte[] key) {
		//参数校验 都不能为空
		CheckUtils.notEmpty(data, "data");
		CheckUtils.notEmpty(key, "key");
		//校验key的长度 正确的key是16字节
		if(key.length!=16){
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		}
		try {
			/*从给定的字节数组中构造一个密钥。
			这个构造函数不检查给定的字节是否确实指定了指定算法的一个密匙。
			例如，如果算法是DES，这个构造函数不检查键是否长8字节，也不检查弱或半弱键。
			为了完成这些检查，应该使用一个特定于算法的关键规范类*/
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES"); 
			//创建解码器
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 创建密码器
			//初始化 选择解密模式 并给定秘钥
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			//解密
			byte[] result = cipher.doFinal(data);
			//返回解密后的字节数组
			return result; 
		} catch (Exception e){
			throw new RuntimeException("decrypt fail!", e);
		}
	}
	
	/**
	 * 加密使用
	 * @param data 要加密的内容
	 * @param key 加密秘钥  key直接使用不经过base64编码
	 * @return 加密后的字节数组经Base64编码后的String字符串
	 */
	public static String encryptToBase64(String data, String key){
		try {
			//加密后的字节数组
			byte[] valueByte = encrypt(
					//使用指定编码解码String data 使用UTF-8
					data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), 
					//使用指定编码解码String key 使用UTF-8
					key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
			//用base64编码加密后的字节数组
			return new String(Base64.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encryptToBase64 fail!", e);
		}
		
	}
	
	/**
	 * 加密使用(推荐)
	 * @param data 要加密的内容
	 * @param key 加密秘钥  传入的key必须是经过base64编码后的 
	 * @return 加密后的字节数组经Base64编码后的String字符串
	 */
	public static String encryptWithKeyBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(
					//使用指定编码解码String data 使用UTF-8
					data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), 
					//使用指定编码解码String key 使用UTF-8得到key的字节数组 再用base64解码  因为传入的参数key是经过base64编码的
					Base64.decode(key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING)));
			//用base64编码加密后的字节数组
			return new String(Base64.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encryptWithKeyBase64 fail!", e);
		}
	}
	
	/**
	 * 解密使用
	 * @param data 要解密的内容 data需要是经过base64编码的
	 * @param key 加密秘钥  key直接使用不经过base64编码
	 * @return 解密后的字节数组经Base64编码后的String字符串
	 */
	public static String decryptFromBase64(String data, String key){
		try {
			//加密状态数据的字节数组-->用base64解码-->originalData原始的字节数组(Base64解码后的)
			byte[] originalData = Base64.decode(data.getBytes());
			//解密
			byte[] valueByte = decrypt(
					//originalData原始的字节数组Base64解码后的
					originalData, 
					//使用指定编码解码String key 使用UTF-8
					key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
			//解密后的字节数组以默认UTF-8编码转换为String对象返回
			return new String(valueByte, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decryptFromBase64 fail!", e);
		}
	}
	
	
	
	/**
	 * 解密使用 (推荐)
	 * @param data 要解密的内容 data需要是经过base64编码的
	 * @param key 加密秘钥  传入的key必须是经过base64编码后的 
	 * @return 解密后的String字符串
	 */
	public static String decryptWithKeyBase64(String data, String key){
		try {
			//先把data转换成字节数组 用base64算法解码得到原始的data字节数组
			byte[] originalData = Base64.decode(data.getBytes());
			//解密
			byte[] valueByte = decrypt(
					//originalData原始的字节数组Base64解码后的
					originalData, 
					//使用指定编码解码String key 使用UTF-8得到key的字节数组 再用base64解码  因为传入的参数key是经过base64编码的
					Base64.decode(key.getBytes()));
			return new String(valueByte, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decryptWithKeyBase64 fail!", e);
		}
	}
	
	/**
	 * 生成随机key
	 * @return 随机key字节数组
	 */
	public static byte[] genarateRandomKey(){
		KeyGenerator keygen = null;
		try {
			//算法类型AES/ECB/PKCS5Padding  aes算法 ecb(密码本)模式 PKCS5Padding填充方式
			keygen = KeyGenerator.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(" genarateRandomKey fail!", e);
		}
		//安全随机数
		SecureRandom random = new SecureRandom();
		//用安全随机数初始化key生成器
		keygen.init(random);
		// 生成一个key
		Key key = keygen.generateKey();
		//返回key的字节数组
		return key.getEncoded();
	}
	
	/**
	 * 生成经Base64编码后的随机key
	 * @return  经Base64编码后的随机key
	 */
	public static String genarateRandomKeyWithBase64(){
		//生成随机key得到它的字节数组 在经过base64编码生成经编码后的字节数组 再转换成String
		return new String(Base64.encode(genarateRandomKey()));
	}
	
}

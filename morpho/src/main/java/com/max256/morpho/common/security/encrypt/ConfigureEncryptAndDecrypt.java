package com.max256.morpho.common.security.encrypt;

/**
 * 加解密使用的算法不要修改
 * @author fbf
 *
 */
public class ConfigureEncryptAndDecrypt {
	//加解密过程中的编码
	public static final String CHAR_ENCODING = "UTF-8";
	//AES算法类型
	public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
	//RSA算法类型
	public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
	//签名算法类型
	public static final String SIGN_ALGORITHM = "SHA1WithRSA";
	//签名在Message报文中的data中的sign属性中
	public static final String SIGN = "sign";
	//签名在Message报文中的data中的plainData属性中
	public static final String PLAIN_DATA = "plainData";
}


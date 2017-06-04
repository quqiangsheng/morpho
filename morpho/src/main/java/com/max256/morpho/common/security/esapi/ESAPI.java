package com.max256.morpho.common.security.esapi;

/**
 * ESAPI工具类的工厂
 * @author fbf
 * @since 2016年10月11日 上午10:39:48
 * @version V1.0
 * 
 */
public class ESAPI {
	private static final ESAPIEncryptor encryptUtils = new ESAPIEncryptor();

	  private static final ESAPIEncoder encoderUtils = new ESAPIEncoder();

	  public static ESAPIEncryptor encryptor()
	  {
	    return encryptUtils;
	  }

	  public static ESAPIEncoder encoder()
	  {
	    return encoderUtils;
	  }
}

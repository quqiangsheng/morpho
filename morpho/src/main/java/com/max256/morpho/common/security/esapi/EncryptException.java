package com.max256.morpho.common.security.esapi;

/**
 * ESAPI加解密工具类
 * @author fbf
 * @since 2016年10月11日 上午10:38:14
 * @version V1.0
 * 
 */
public class EncryptException extends Throwable {
	
	private static final long serialVersionUID = 1628618029177373172L;

	public EncryptException() {
	}

	public EncryptException(String message) {
		super(message);
	}

	public EncryptException(String message, Throwable cause) {
		super(message, cause);
	}

	public EncryptException(Throwable cause) {
		super(cause);
	}

	public EncryptException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
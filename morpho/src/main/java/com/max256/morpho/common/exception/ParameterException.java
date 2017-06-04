package com.max256.morpho.common.exception;

/**
 * 参数异常 
 * 继承自业务异常
 * 使用时throw new ParameterException("XXXX")
 * 
 * @author fbf
 */
public class ParameterException extends BusinessException {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public ParameterException() {
		super();
	}

	public ParameterException(String errMsg, int errCode) {
		super(errMsg, errCode);
	}
	public ParameterException(String errMsg) {
		super(errMsg, 0);
	}
	
}

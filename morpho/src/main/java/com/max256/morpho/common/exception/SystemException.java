package com.max256.morpho.common.exception;

/**
 * 
 * 自定义系统异常 
 * throw new SystemException("XXXX")
 * 系统本身发生问题时抛出 业务错误不要使用本异常
 * 
 * @author fbf
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errMsg;// 错误消息
	private Integer errCode;// 错误码 1永远代表成功 0代表失败或其他非1的正整数代表失败 更详细的错误码请自定义并遵守


	public SystemException() {
	}

	public SystemException(String errMsg) {
		this.errMsg = errMsg;
	}

	public SystemException(Integer errCode) {
		this.errCode = errCode;
	}

	public SystemException(String errMsg, Integer errCode) {
		this.errMsg = errMsg;
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public SystemException setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		this.errCode =0;
		return this;
	}

	public int getErrCode() {
		return this.errCode;
	}

	public SystemException setErrCode(Integer errCode) {
		this.errCode = errCode;
		return this;
	}

	public String getMessage() {
		return this.errMsg;
	}

}

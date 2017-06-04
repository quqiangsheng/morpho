package com.max256.morpho.common.exception;

/**
 * 业务错误
 * 
 * @author fbf
 * @since 2016年10月25日 下午3:29:20
 * @version V1.0
 * 
 */

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errMsg;// 错误消息
	private Integer errCode;// 错误码 1永远代表成功 0代表失败或其他非1的正整数代表失败 更详细的错误码请自定义并遵守


	public BusinessException() {
	}

	public BusinessException(String errMsg) {
		this.errMsg = errMsg;
	}

	public BusinessException(int errCode) {
		this.errCode = errCode;
	}

	public BusinessException(String errMsg, int errCode) {
		this.errMsg = errMsg;
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public BusinessException setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

	public int getErrCode() {
		return this.errCode;
	}

	public BusinessException setErrCode(int errCode) {
		this.errCode = errCode;
		return this;
	}

	public String getMessage() {
		return this.errMsg;
	}
}

package com.max256.morpho.common.util;

import java.util.Map;

/**
 * hibernate Validation utils校验工具类配合使用 校验结果
 * 
 * @author qqs
 * 
 */
public class ValidationResult {

	// 校验结果是否有错
	private boolean hasErrors;

	// 校验错误信息
	private Map<String, String> errorMsg;

	public Map<String, String> getErrorMsg() {
		return errorMsg;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setErrorMsg(Map<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	@Override
	public String toString() {
		return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg="
				+ errorMsg + "]";
	}

}
package com.max256.morpho.common.util;

import java.util.Collection;

import com.max256.morpho.common.exception.BusinessException;

public class AssertUtils {
	public static boolean isNull(Object obj) {
		return !isNotNull(obj);
	}

	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	public static boolean isEmpty(Object obj) {
		return !isNotEmpty(obj);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Object obj) {
		if (!isNotNull(obj)) {
			return false;
		}

		if (String.class.isAssignableFrom(obj.getClass())) {
			if (((String) obj).length() == 0)
				return false;
		} else if (Collection.class.isAssignableFrom(obj.getClass())) {
			if (((Collection) obj).isEmpty())
				return false;
		} else if ((obj.getClass().isArray()) && (((Object[]) obj).length == 0)) {
			return false;
		}

		return true;
	}

	public static boolean notNull(Object obj, String errMsg)
			throws BusinessException {
		if (!isNotNull(obj)) {
			throw new BusinessException(errMsg,0);
		}
		return true;
	}

	public static boolean notNull(Object obj, String errMsg, int errCode)
			throws BusinessException {
		if (!isNotNull(obj)) {
			throw new BusinessException(errMsg, errCode);
		}
		return true;
	}

	public static boolean notNull(Object obj) throws BusinessException {
		return notNull(obj, "对象为空!");
	}

	public static boolean isTrue(boolean value, String errMsg)
			throws BusinessException {
		if (!value) {
			throw new BusinessException(errMsg,0);
		}
		return true;
	}

	public static boolean notEmpty(Object obj, String errMsg)
			throws BusinessException {
		if (!isNotEmpty(obj)) {
			throw new BusinessException(errMsg,0);
		}
		return true;
	}

	public static boolean notEmpty(Object obj, String errMsg, int errCode)
			throws BusinessException {
		if (!isNotEmpty(obj)) {
			throw new BusinessException(errMsg, errCode);
		}
		return true;
	}

	public static boolean notEmpty(Object obj) throws BusinessException {
		return notEmpty(obj, "对象为空!");
	}

	public static void isNumber(String str, String errMsg)
			throws BusinessException {
		notEmpty(str, errMsg);
		if (!str.matches("^[0-9]*$"))
			throw new BusinessException(errMsg,0);
	}

	public static void isChinese(String str, String errMsg)
			throws BusinessException {
		notEmpty(str, errMsg);
		if (!str.matches("^[\\u4e00-\\u9fa5]{0,}$"))
			throw new BusinessException(errMsg,0);
	}

	public static void isEnglish(String str, String errMsg)
			throws BusinessException {
		notEmpty(str, errMsg);
		if (!str.matches("^[A-Za-z]+$"))
			throw new BusinessException(errMsg,0);
	}

	public static void isEnglishOrNum(String str, String errMsg)
			throws BusinessException {
		notEmpty(str, errMsg);
		if (!str.matches("^[A-Za-z0-9]+$"))
			throw new BusinessException(errMsg,0);
	}

	public static void isEmail(String str, String errMsg)
			throws BusinessException {
		notEmpty(str, errMsg);
		if (!str.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"))
			throw new BusinessException(errMsg,0);
	}

	public static void isURL(String str, String errMsg)
			throws BusinessException {
		notEmpty(str, errMsg);
		if (!str.matches("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?"))
			throw new BusinessException(errMsg,0);
	}

	public static void isPhone(String str, String errMsg)
			throws BusinessException {
		notEmpty(str, errMsg);
		if (!str.matches("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)"))
			throw new BusinessException(errMsg,0);
	}

	public static void isPostcode(String str, String errMsg)
			throws BusinessException {
		notEmpty(str, errMsg);
		if (!str.matches("[1-9]\\d{5}(?!\\d)"))
			throw new BusinessException(errMsg,0);
	}
}

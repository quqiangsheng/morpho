package com.max256.morpho.common.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.max256.morpho.common.entity.SysUser;

/**
 * Shiro工具类
 * 
 * @author fbf
 */
public class ShiroUtils {

	/**
	 * 得到当前的session
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	/**
	 * 得到当前的session
	 * @return
	 */
	public static Session getSession(boolean flag) {
		return SecurityUtils.getSubject().getSession(flag);
	}

	/**
	 * 得到当前subject
	 * @return
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**得到当前的SysUser
	 * @return
	 */
	public static SysUser getSysUser() {
		return (SysUser)SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 得到当前的用户id
	 * @return
	 */
	public static String getUserId() {
		return getSysUser().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	/**
	 * 获得当前session验证码 并且删除
	 * @param key是session中验证码的key值
	 * @return 返回value 并且删除key
	 */
	public static String getSessionCaptcha(String key) {
		String captcha = getSessionAttribute(key).toString();
		getSession().removeAttribute(key);
		return captcha;
	}

}

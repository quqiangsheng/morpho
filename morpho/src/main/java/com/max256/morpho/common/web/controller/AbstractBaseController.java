package com.max256.morpho.common.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.security.shiro.ShiroUtils;


/**
 * 抽象基础controller
 * @author fbf 
 * @since 2017年1月5日 下午4:10:26
 * @version V1.0
 * 
 */
public abstract class AbstractBaseController {
	
	/**
	 * slf4j 日志
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 得到一个新的ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到http request对象
	 * 
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	
	/**
	 * 取得当前的登陆用户
	 * @return
	 */
	protected SysUser getSysUser() {
		return ShiroUtils.getSysUser();
	}

	/**
	 * 取得当前的用户id
	 * @return
	 */
	protected String getUserId() {
		return getSysUser().getUserId();
	}
	/**
	 * 取得当前的session id
	 * @return session id如果为空则返回null
	 */
	protected String getSessionId() {
		Session session=ShiroUtils.getSubject().getSession(false);
		if(session==null){
			return null;
		}else{
			return (String) session.getId();
		}
	}
}

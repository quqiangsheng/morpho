package com.max256.morpho.common.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.max256.morpho.common.config.Constants;

/**
 * 自定义增强servlet listener监听器 可
 * 以在这里根据需求做扩展 由于本系统的session已经由shiro控制，
 * 所以session的监听部分已经失效
 * 如果需要请配置shiro的session监听器
 * (默认以开启com.max256.morpho.common.security.shiro.ShiroSessionListener)
 * 
 * @author fbf
 * 
 */
public class MyContextListener implements ServletContextListener,
		InitializingBean, ServletContextAware, HttpSessionListener,
		HttpSessionBindingListener {
	// 日志
	private static final Logger logger = LoggerFactory
			.getLogger(MyContextListener.class);

	/* 通过实现ServletContextAware可获得servletContext */
	private ServletContext servletContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		if(logger.isDebugEnabled()){
		logger.debug("afterPropertiesSet");
		}
	}



	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("系统已经关闭");
	}

	/*
	 * do sth
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		//验证授权信息
		/*ValidateLicence vl= new ValidateLicence();
		try {
			if(!vl.validateLicence(servletContext)){
				logger.info("系统没有授权,即将关闭~~~");
				System.exit(0);
			};
		} catch (Exception e) {
		}
		logger.info("系统授权有效");*/
		logger.info("系统已经启动");
		//系统常量加入到servletContext
		servletContext.setAttribute("sysname", Constants.SYS_NAME);//系统名称 
		servletContext.setAttribute("copyright", Constants.SYS_COPYRIGHT);//版权信息
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	/*----------以下部分在shiro的环境中已经不起作用了------------*/
	/* 在shiro集成下HttpsessionAttributeListener会失效 */
	/*
	 * session创建时做日志
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		if(logger.isDebugEnabled()){
			logger.debug("SessionCreated  id:" + se.getSession().getId());	
		}
	}

	/*
	 * session销毁时做日志
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		if(logger.isDebugEnabled()){
		logger.debug("SessionDestroyed. id:" + se.getSession().getId());
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		if(logger.isDebugEnabled()){			
			logger.debug("valueBound:" + event.getName() + " : " + event.getValue());
		}

	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		if(logger.isDebugEnabled()){
			logger.debug("valueUnbound:" + event.getName() + " : "
				+ event.getValue());
		}
	}



	

}

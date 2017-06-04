package com.max256.morpho.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 
 * spring上下文获取工具类
 * 
 * 
 * @author fbf
 */
public class ApplicationContextUtils implements ApplicationContextAware, DisposableBean {
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationContextUtils.class);
	private static ApplicationContext context = null;

	/**得到Spring上下文
	 * @return
	 */
	public static ApplicationContext getContext() {
		return context;
	}
	/**得到Spring上下文中的bean
	 * @return
	 */
	public static  Object getBean(String name) {
		if(context==null){
			return null;
		}else{
			return  context.getBean(name);
		}
	}
	
	/**
	 * 从静态变量context中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		
		return context.getBean(requiredType);
	}


	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		if (null == ApplicationContextUtils.context) {
			ApplicationContextUtils.context = context;
		}
	}
	
	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		if (logger.isDebugEnabled()){
			logger.debug("清除SpringContextHolder中的ApplicationContext:" + context);
		}
		context = null;
	}
	
	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	@Override
	public void destroy() throws Exception {
		ApplicationContextUtils.clearHolder();
	}

}

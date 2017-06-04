package com.max256.morpho.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.SecurityUtils;

import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.entity.SysUser;

/**
 * 日志线程绑定器 可以绑定线程的相关数据存入 在日志中取出时可以显示出来 类似线程的线程局部变量 依赖log4j2的ThreadContext
 * 依赖servlet规范 不支持logback和log4j 他们有相应的MDC NDC实现 和此实现类似 API不同
 * 在log4j配置中，使用mdc：（log4j 1.x 与 2.x中都可以使用此配置） %X{REQUEST_UUID} %X{HOST_IP}
 * %X{HOST_NAME} %X{APPKEY}
 * 
 * @author fbf
 * @since 2016年12月30日 上午11:47:03
 * @version V1.0
 * 
 */
public class LogThreadContextFilter implements Filter {
	// 远程访问的用户名
	public static final String USER_NAME = "USER_NAME";
	// 远程访问的用户主键
	public static final String USER_UUID = "USER_UUID";
	// session id
	public static final String SESSION_ID = "SESSION_ID";
	// app模块名字 为分布式服务时预留拓展
	public static final String APP_KEY = "APP_KEY";
	// appKey读取web.xml里的初始参数
	private String appKey = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 尝试读取
		appKey = filterConfig.getInitParameter("appKey");
		// appkey可以在filter init-param中配置
		if (appKey == null) {
			//再尝试读取常量里的
			appKey = Constants.SYS_NAME;
		}
	}

	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			//写入绑定到线程数据到日志
			insertIntoMDC(request);
			chain.doFilter(request, response);
		} finally {
			//清空不用的线程局部变量
			clearMDC();
		}
	}

	@Override
	public void destroy() {

	}

	protected void insertIntoMDC(ServletRequest request) {
		if(SecurityUtils.getSubject().getSession(false)==null){
			ThreadContext.put(LogThreadContextFilter.SESSION_ID,"" );
		}else{			
			ThreadContext.put(LogThreadContextFilter.SESSION_ID,SecurityUtils.getSubject().getSession(false).getId().toString() );
		}
		SysUser sysUser=(SysUser)SecurityUtils.getSubject().getPrincipal();
		
		if(sysUser!=null){
			ThreadContext.put(LogThreadContextFilter.USER_NAME, sysUser.getUserName());
			ThreadContext.put(LogThreadContextFilter.USER_UUID, sysUser.getUuid());
		}else{
			ThreadContext.put(LogThreadContextFilter.USER_NAME, "");
			ThreadContext.put(LogThreadContextFilter.USER_UUID, "");
		}
		ThreadContext.put(LogThreadContextFilter.APP_KEY, appKey);
	}

	protected void clearMDC() {

		ThreadContext.remove(LogThreadContextFilter.SESSION_ID);

		ThreadContext.remove(LogThreadContextFilter.USER_NAME);

		ThreadContext.remove(LogThreadContextFilter.USER_UUID);

		ThreadContext.remove(LogThreadContextFilter.APP_KEY);

	}

}

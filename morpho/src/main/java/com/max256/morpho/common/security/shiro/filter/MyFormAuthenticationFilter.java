package com.max256.morpho.common.security.shiro.filter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.entity.SysLog;
import com.max256.morpho.common.util.IPUtils;
import com.max256.morpho.common.util.UUIDUtils;
import com.max256.morpho.sys.service.SysLogService;


/**
 * 
 * 表单登录拦截器
 * 基于shiro拦截器扩展
 * 增加登陆成功时的日志
 * 这也是一个spring bean
 * 
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	
	@Resource
	private SysLogService sysLogService;

	Logger log=LoggerFactory.getLogger(this.getClass());
	/*
	 * 登录成功事件
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		UsernamePasswordToken token1 = (UsernamePasswordToken) token;
		SysLog sysLog = new SysLog();
		sysLog.setUuid(UUIDUtils.get32UUID());
		sysLog.setLogUser(token1.getUsername());
		sysLog.setLogInfo("用户:" + token1.getUsername() + " 成功登录系统!");
		sysLog.setLogIp(IPUtils.getIpAddr((HttpServletRequest) request));
		sysLog.setLogAgent(((HttpServletRequest) request)
				.getHeader("User-Agent"));
		sysLog.setLogId(sysLog.getUuid());
		sysLog.setLogTime(new Timestamp(System.currentTimeMillis()));
		// 我们在这里加入日志记录
		if(log.isInfoEnabled()){
			log.info("登录日志:"+sysLog);
		}
		sysLogService.insert(sysLog);
		//we handled the success redirect directly, prevent the chain from continuing:
		return super.onLoginSuccess(token, subject, request, response);
	}
	
	
	/* 
	 * 当登录失败时 委托shiro 的realm进行登录失败后执行此方法	
	 *  */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String error="";
		if (e instanceof UnknownAccountException) {
			error = Constants.UNKNOWN_ACCOUNT_EXCEPTION;
		} else if (e instanceof IncorrectCredentialsException) {
			error = Constants.INCORRECT_CREDENTIALS_EXCEPTION;
		}else if (e instanceof LockedAccountException ) {
			error = Constants.ACCOUNT_LOCKED;
		}else if (e instanceof ExcessiveAttemptsException) {
			error = Constants.EXCESSIVE_ATTEMPTS_EXCEPTION;
		}else{
			error=Constants.UNKNOWN_ERROR;
		}
		// 带参数转发
		Map<String,String> param=new HashMap<String,String>();
		param.put(Constants.LOGIN_FAILURE_KEY, error);
        try {
			WebUtils.issueRedirect(request, response, Constants.ERROR_PAGE_URL,param);
		} catch (IOException e1) {

		}
		//停止链式处理 本步骤已经重定向
        return false;
		

	}


}

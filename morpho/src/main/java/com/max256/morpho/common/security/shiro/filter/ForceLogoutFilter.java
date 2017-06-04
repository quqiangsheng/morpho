package com.max256.morpho.common.security.shiro.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.max256.morpho.common.config.Constants;

/**
 * 管理员强制踢人过滤器 
 * 继承自AccessControlFilter
 * 
 * @author fbf
 * 
 */
public class ForceLogoutFilter extends AccessControlFilter {
	
	/*
	 * 是否允许访问 根据得到的getSubject(request, response).getSession(false)
	 * session值是否为空判断 true if the request should proceed through the filter
	 * normally, false if the request should be processed by this filter's
	 * onAccessDenied(ServletRequest, ServletResponse, Object) method instead.
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Session session = getSubject(request, response).getSession(false);
		if (session == null) {
			//session为空说明第一次登陆 放行
			return true;
		}
		// 检测session中的key为Constants.SESSION_FORCE_LOGOUT_KEY的值是否为空 为空返回true
		// 说明该session没有被强制退出的标记 允许访问页面 否则返回false不允许继续访问
		return session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) == null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		//得到Subject
		Subject subject = getSubject(request, response);
        if (subject != null) {
        	//强制退出登陆
            subject.logout();
        }
        Map<String,String> param=new HashMap<String,String>();
		param.put(Constants.LOGIN_FAILURE_KEY, Constants.KICK_OUT);
		WebUtils.issueRedirect(request, response, Constants.ERROR_PAGE_URL,param);

        //停止链式处理 本步骤已经重定向
        return false;
		
	}
	
}

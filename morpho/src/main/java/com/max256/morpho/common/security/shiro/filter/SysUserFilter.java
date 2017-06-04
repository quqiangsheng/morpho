package com.max256.morpho.common.security.shiro.filter;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.sys.service.SysUserService;

/**
 *  验证用户有效性过滤器
 *  1、用户是否删除 
 *  2、用户是否锁定 
 *  3 SysUserFilter用于绑定当前SysUser和sysUserName到session中
 *  4 关联druid的session监控与用户名相绑定
 * 扩展自AccessControlFilter基类的过滤器将只处理指定路径,允许其他的所有人通过
 * 
 * @author fbf
 */
public class SysUserFilter extends AccessControlFilter {

	@Resource
	private SysUserService sysUserService;

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		{
			Subject subject = getSubject(request, response);
			if (subject == null) {
				return true;
			}
			// 此处注意缓存 防止大量的查询db
			SysUser user = (SysUser) subject.getPrincipal();// 这个返回的是SysUser
			if (user == null) {
				// user不存在时
				return true;
			}
			// 得到当前session
			Session session = subject.getSession();
			// 把当前用户放到session中
			session.setAttribute(Constants.CURRENT_USER, user);
			if (StringUtils.isNotBlank(user.getUserName())) {
				session.setAttribute(Constants.CURRENT_USER_NAME, user.getUserName());
			} else {
				session.setAttribute(Constants.CURRENT_USER_NAME, "");
			}
			// druid监控需要 关联druid
			((HttpServletRequest) request).getSession().setAttribute(Constants.CURRENT_USER_NAME, 
					user.getUserName());
			return true;
		}
	}

	/*
	 * 是否允许被访问
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		Session session=subject.getSession(false);
		if(session==null){
			// 没有用户登陆时 允许访问
			return true;
		}
		// 得到请求中的SysUser
		SysUser user = (SysUser) session.getAttribute(Constants.CURRENT_USER);
		if (user == null) {
			// 没有用户登陆时 允许访问
			return true;
		}
		// 等于0意味着用户停用
		if (user.getIsValid() != null && user.getIsValid().equals("0")) {
			// 拒绝访问
			return false;
		} else {
			// 用户没有停用允许访问
			return true;
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		//得到Subject
		Subject subject = getSubject(request, response);
        if (subject != null) {
        	//强制退出登陆
            subject.logout();
        }
		//账号已被锁定
		// 带参数转发
        Map<String,String> param=new HashMap<String,String>();
		param.put(Constants.LOGIN_FAILURE_KEY, Constants.ACCOUNT_LOCKED);
		WebUtils.issueRedirect(request, response, Constants.ERROR_PAGE_URL,param);
        //停止链式处理 本步骤已经重定向
        return false;
	}

}
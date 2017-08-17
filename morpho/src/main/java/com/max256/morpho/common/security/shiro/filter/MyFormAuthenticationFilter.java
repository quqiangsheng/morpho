package com.max256.morpho.common.security.shiro.filter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import com.max256.morpho.common.dto.GeneralReturnData;
import com.max256.morpho.common.dto.R;
import com.max256.morpho.common.entity.SysLog;
import com.max256.morpho.common.entity.SysRole;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.util.AjaxUtils;
import com.max256.morpho.common.util.IPUtils;
import com.max256.morpho.common.util.JsonUtils;
import com.max256.morpho.common.util.UUIDUtils;
import com.max256.morpho.sys.service.SysLogService;
import com.max256.morpho.sys.service.SysRoleService;


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
	@Resource
	private SysRoleService sysRoleService;

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
		//相关信息
		SysUser sessionUser = (SysUser) SecurityUtils.getSubject()
				.getPrincipal();
		// 根据当前登陆的用户名查找他所拥有的权限
		/*Set<String> permissions = sysUserService
				.findPermissionsByUserName(sessionUser.getUserName());*/
		// 根据权限查找拥有的菜单资源
		/*List<SysResource> menus = sysResourceService.findMenus(permissions);*/
		// 把菜单放入session中
		/*session.setAttribute(Constants.SESSION_MENUS, menus);*/
		//当前用户角色名字放入到session中
		if(StringUtils.isNotBlank(sessionUser.getSysRoleIds())||!sessionUser.getSysRoleIds().equals("null")){
			//查询出的中文名称拼串
			String names="";
			//拆分ids 
			String[] idsplit =sessionUser.getSysRoleIds().split(",");
			//依次查询
			for (int i = 0; i < idsplit.length; i++) {
				if(StringUtils.isNotBlank(idsplit[i])){
					SysRole find=sysRoleService.findSysRoleById(idsplit[i]);
					if(null!=find){
						names=names+find.getRoleName()+",";
					}
				}
			}
			//查询完毕 删除最后一个符号
			if(names.length()>0){
				names=names.substring(0, names.length()-1);
			}
			SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_ROLES_NAME, names);
		}
		
		//当前用户所属组织机构名字放入到session中
		/*if(StringUtils.isNotBlank(sessionUser.getSysOrganizationId())){
			SysOrganization findSysOrganization=sysOrganizationService.getByHql("from SysOrganization where id='"+sessionUser.getSysOrganizationId()+"'");
			session.setAttribute(Constants.CURRENT_ORGANIZATION_NAME, findSysOrganization.getName());
		}*/
		
		//判断请求方式 如果是ajax或者http接口形式的登陆 返回json 不重定向
		Boolean isAjaxFlag=AjaxUtils.isAjax((HttpServletRequest)request, (HttpServletResponse)response);
		if(isAjaxFlag){
			//返回json信息
			GeneralReturnData<String> returnData= R.data();
			returnData.setStatus("1");
			returnData.setInfo("登陆成功");
			String returnJsonData=JsonUtils.writeValueAsString(returnData);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			//允许跨域
			((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().println(returnJsonData);
			return false;
		}else{
			return super.onLoginSuccess(token, subject, request, response);
		}
		//we handled the success redirect directly, prevent the chain from continuing:
		
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
 
        //判断请求方式 如果是ajax或者http接口形式的登陆 返回json 不重定向
  		Boolean isAjaxFlag=AjaxUtils.isAjax((HttpServletRequest)request, (HttpServletResponse)response);
  		if(isAjaxFlag){
  			//返回json信息
  			GeneralReturnData<String> returnData= R.data();
  			returnData.setStatus("0");
  			returnData.setInfo("登陆失败,错误代码:"+error);
  			String returnJsonData=JsonUtils.writeValueAsString(returnData);
  			try {
  				response.setCharacterEncoding("UTF-8");
  				response.setContentType("application/json;charset=UTF-8");
  				//允许跨域
  				((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
				response.getWriter().println(returnJsonData);
				
			} catch (IOException e1) {
			}
  			return false;
  		}else{
  			try {
  				WebUtils.issueRedirect(request, response, Constants.ERROR_PAGE_URL,param);
  			} catch (IOException e1) {

  			}
  			//停止链式处理 本步骤已经重定向
  	        return false;
  		}
		
		

	}
	
	
	


}

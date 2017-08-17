package com.max256.morpho.sys.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.entity.SysRole;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.SysRoleService;
import com.max256.morpho.sys.service.SysUserService;

/**
 * 
 * @author fbf
 * 
 */
@Controller
public class LoginController extends AbstractBaseController {
	
	@Resource
	SysUserService sysUserService;
	@Resource
	SysRoleService sysRoleService;

	/**
	 * 尝试登陆系统后台主页  get方法
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String goDefault() {
		return "redirect:index";
	}
	/**
	 * 到后台主页
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String goIndex(HttpServletRequest request, HttpSession session) {

		//SysUser sessionUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
		// 根据当前登陆的用户名查找他所拥有的权限
		/*Set<String> permissions = sysUserService
				.findPermissionsByUserName(sessionUser.getUserName());*/
		// 根据权限查找拥有的菜单资源
		/*List<SysResource> menus = sysResourceService.findMenus(permissions);*/
		// 把菜单放入session中
		/*session.setAttribute(Constants.SESSION_MENUS, menus);*/
		//当前用户角色名字放入到session中
		/*if(StringUtils.isNotBlank(sessionUser.getSysRoleIds())||!sessionUser.getSysRoleIds().equals("null")){
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
				
				
			session.setAttribute(Constants.CURRENT_ROLES_NAME, names);
		}
		*/
		//当前用户所属组织机构名字放入到session中
		/*if(StringUtils.isNotBlank(sessionUser.getSysOrganizationId())){
			SysOrganization findSysOrganization=sysOrganizationService.getByHql("from SysOrganization where id='"+sessionUser.getSysOrganizationId()+"'");
			session.setAttribute(Constants.CURRENT_ORGANIZATION_NAME, findSysOrganization.getName());
		}*/
		
		// 登录到后台主页
		return "sys/index";
	}
	/**
	 * 到login登陆界面 
	 * 专用登录 防止用户自己输入到登录页面 因为登录页面需要记忆回退地址
	 */
	@RequestMapping(value="/login")
	public String goLogin() {
		return "login";
	}
	/**
	 * 显示错误代码和错误详情页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = Constants.ERROR_PAGE_URL)
	public String goErrorPage(HttpServletRequest request) {
		// 错误信息 可能是异常对象 也可能是错误代码
		String error = "";
		Object exceptionObject =  request.getParameter(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(exceptionObject==null){
			error="没有错误~";
			request.setAttribute("error", error);
			return "error/errorpage";
		}
		if(exceptionObject instanceof Exception){
			request.setAttribute("error", ((Exception)exceptionObject).getMessage());
		}else{
			//错误原因
			error=Constants.ERROR_CODE_MAP.get(exceptionObject);
			if(StringUtils.isBlank(error)){
				error="没有错误~";
			}
			// 登陆失败 重定向到系统主页页面重新登录 
			request.setAttribute("error", error);
		}
		return "error/errorpage";
		
	}
	/**
	 * 到后台首屏页面
	 */
	@RequestMapping("/sys/firstscreen")
	public String goFirstscreen(){
		return "sys/firstscreen";
	}
	
	
}

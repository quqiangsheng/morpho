package com.max256.morpho.common.security.shiro.taglib;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.util.CollectionUtils;

import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.entity.SysResource;
import com.max256.morpho.common.entity.SysRole;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.util.SpringUtils;
import com.max256.morpho.sys.service.SysResourceService;
import com.max256.morpho.sys.service.SysRoleService;


/**
 * 系统自定义标签库
 * 
 * @author fbf
 * 
 */
public class Functions {
	
	private static SysRoleService roleService;
	private static SysResourceService resourceService;

	//注入bean
	public static SysResourceService getResourceService() {
		if (resourceService == null) {
			resourceService = SpringUtils.getBean(SysResourceService.class);
		}
		return resourceService;
	}

	public static SysRoleService getRoleService() {
		if (roleService == null) {
			roleService = SpringUtils.getBean(SysRoleService.class);
		}
		return roleService;
	}

	/**
	 * 判断资源对象是否在集合中
	 * 
	 * @param iterable
	 * @param element
	 * @return
	 */
	public static boolean in(String str, Object element) {
		if (str == null) {
			return false;
		}
		String[] strs = str.split(",");

		// 修正
		return CollectionUtils.contains(Arrays.asList(strs).iterator(), element);
	}

	/**
	 * 是否强制退出 得到是否强制退出标记
	 * 
	 * @param session
	 * @return
	 */
	public static boolean isForceLogout(Session session) {
		return session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) != null;
	}





	/**
	 * 用户身份 根据sessionid查找用户名 显示到页面上 如果session已经被踢了 得到session中的主principal
	 * （也就是用户名）
	 * 
	 * 注意：如果只有session但是通过session无法关联出它的用户名 显示在页面是会报500错误
	 * 
	 * @param session
	 * @return
	 */
	public static String principal(Session session) {
		PrincipalCollection principalCollection = (PrincipalCollection) session
				.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if (null == principalCollection) {
			return "";
		}

		SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
		if (null == sysUser) {
			return "";
		}

		return sysUser.getUserName();
	}

	// 根据id显示资源名称
	public static String resourceName(String resourceId) {
		SysResource resource = getResourceService().findSysResourceById(
				resourceId);
		if (resource == null) {
			return "";
		}
		return resource.getResourceName();
	}

	// 根据id列表显示多个资源名称ids---id---name
	// 根据id字符串 用“,”分割的String形势的字符串
	public static String resourceNames(String resourceIds) {
		if (StringUtils.isEmpty(resourceIds)) {
			return "";
		}
		String[] ids = resourceIds.split(",");
		// 准备拼接返回的字符串
		StringBuilder s = new StringBuilder();
		for (String resourceId : ids) {
			SysResource resource = getResourceService().findSysResourceById(
					resourceId);
			if (resource == null) {
				return "";
			}
			s.append(resource.getResourceName());
			s.append(",");
		}

		if (s.length() > 0) {
			s.deleteCharAt(s.length() - 1);
		}

		return s.toString();
	}

	// 根据id显示角色名称
	public static String roleName(String roleId) {
		SysRole role = getRoleService().findSysRoleById(roleId);
		if (role == null) {
			return "";
		}
		return role.getRoleDesc();
	}

	// 根据id列表显示多个角色名称
	public static String roleNames(String roleIds) {
		
		return getRoleService().getRolesNamesByIds(roleIds);
	

	}

}

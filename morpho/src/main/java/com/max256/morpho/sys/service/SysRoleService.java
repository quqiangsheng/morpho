package com.max256.morpho.sys.service;

import java.util.Set;

import com.max256.morpho.common.entity.SysRole;
import com.max256.morpho.common.service.BaseService;



/**
 * @author fbf
 * @since 2017年1月10日 上午10:38:01
 * @version V1.0
 * 
 */
public interface SysRoleService extends BaseService<SysRole> {
	

	/**
	 * 根据角色编号得到权限字符串列表
	 * 
	 * @param roleIds
	 * @return
	 */
	public abstract Set<String> findPermissions(String sysRoleIds) ;
	
	/**
	 * 根据角色编号得到权限字符串列表
	 * 
	 * @param roleIds
	 * @return
	 */
	public abstract Set<String> findPermissions(Set<String> sysRoleIds);

	
	/**根据角色id查找SysRole
	 * @param sysRoleId
	 * @return
	 */
	public abstract SysRole findSysRoleById(String sysRoleId);

	/**
	 * 根据角色编号得到角色标识符列表
	 * 
	 * @param sysRoleIds
	 *            可变参数
	 * @return
	 */
	public abstract Set<String> findSysRoles(String[] sysRoleIds);
	
	/**
	 * 得到所有的角色名字根据角色ids
	 * @param roleIds 多个角色的ids
	 * @return
	 */
	public abstract String getRolesNamesByIds(String roleIds);
}

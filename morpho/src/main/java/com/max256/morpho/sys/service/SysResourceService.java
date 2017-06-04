package com.max256.morpho.sys.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.max256.morpho.common.entity.SysResource;
import com.max256.morpho.common.service.BaseService;

public interface SysResourceService extends BaseService<SysResource> {
	
	public Serializable createSysResource(SysResource sysResource); 

	// 找到所有的SysResource 不分页
	public abstract List<SysResource> findAll();

	// 根据用户权限得到菜单
	public abstract List<SysResource> findMenus(Set<String> permissions);

	// 得到资源对应的权限字符串
	public abstract Set<String> findPermissions(Set<String> sysResourceIds);

	// 根据SysResource id查找SysResource
	public abstract SysResource findSysResourceById(String sysResourceId);
	
	/**
	 * 删除自己和子资源
	 * @param sysResource
	 */
	void deleteSelfAndSubSysResource(SysResource sysResource);

}

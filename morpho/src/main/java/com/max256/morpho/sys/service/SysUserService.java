package com.max256.morpho.sys.service;

import java.util.Set;

import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.service.BaseService;



public interface SysUserService extends BaseService<SysUser> {
	// 根据用户名查找权限
	public Set<String> findPermissionsByUserName(String userName);
	// 根据用户名查找角色
	public Set<String> findRolesByUserName(String userName);
}

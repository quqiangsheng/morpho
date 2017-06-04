package com.max256.morpho.sys.service.impl;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.service.impl.BaseServiceImpl;
import com.max256.morpho.sys.service.SysRoleService;
import com.max256.morpho.sys.service.SysUserService;
@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService{
	@Resource
	SysRoleService sysRoleService;
	// 根据用户名查找权限
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> findPermissionsByUserName(String userName) {
		// 1 根据用户名查找SysUser
		SysUser param=new SysUser();
		param.setUserName(userName);
		SysUser user = selectOne(param);
		if (user == null) {
			// 2 如果SysUser为空则返回空
			return Collections.EMPTY_SET;
		}
		// 3 如果找到了 根据SysUser的角色ids查找角色
		return sysRoleService.findPermissions(user.getSysRoleIds());
	}

	// 根据用户名查找角色
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> findRolesByUserName(String userName) {
		// 1 根据用户名查找SysUser
		SysUser param=new SysUser();
		param.setUserName(userName);
		SysUser user = selectOne(param);
		if (user == null) {
			// 2 如果SysUser为空则返回空
			return Collections.EMPTY_SET;
		}
		// 3 如果找到了 根据SysUser的角色ids查找角色
		String[] ids = user.getSysRoleIds().split(",");
		return sysRoleService.findSysRoles(ids);
	}
	

}

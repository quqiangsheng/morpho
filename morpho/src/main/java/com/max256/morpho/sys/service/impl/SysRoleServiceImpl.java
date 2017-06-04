package com.max256.morpho.sys.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.max256.morpho.common.entity.SysRole;
import com.max256.morpho.common.service.impl.BaseServiceImpl;
import com.max256.morpho.sys.service.SysResourceService;
import com.max256.morpho.sys.service.SysRoleService;
@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService{
	
	@Resource
	SysResourceService sysResourceService;
	

	@Override
	public Set<String> findPermissions(String sysRoleIds) {
		 Set<String> param = new HashSet<String>(Arrays.asList(sysRoleIds.split(",")));   
		 return findPermissions(param);
	}

	@Override
	public Set<String> findPermissions(Set<String> sysRoleIds) {
		// 准备查询条件 资源ids集合
		Set<String> resourceIds = new HashSet<String>();
		// 遍历sysRoleIds角色编号
		for (String sysRoleId : sysRoleIds) {
			// 根据角色编号id找到角色实体对象
			SysRole role = findSysRoleById(sysRoleId);
			if (role != null) {
				// 把角色字符串按“,”号分割成 list<String>
				String ids = role.getResourceIds();
				// 按逗号分隔成字符数组
				String[] resourceIdStrs = ids.split(",");
				List<String> idsList = Arrays.asList(resourceIdStrs);
				// 查到了把该角色对象的ResourceIds提取出来 这是一个List 然后把这个List全部添加到resourceIds中
				// resourceIds是Set会自动去除重复的内容
				resourceIds.addAll(idsList);
			}
		}
		// 根据resourceIds（既所有的资源）查找权限 返回
		return sysResourceService.findPermissions(resourceIds);
	}

	@Override
	public SysRole findSysRoleById(String sysRoleId) {
		SysRole param=new SysRole();
		param.setRoleId(sysRoleId);
		return selectOne(param);
	}

	@Override
	public Set<String> findSysRoles(String[] sysRoleIds) {
		// 准备角色Set存放返回数据
		Set<String> sysRoles = new HashSet<String>();
		for (String sysRoleId : sysRoleIds) {
			// 根据角色id号查找SysRole
			SysRole sysRole = findSysRoleById(sysRoleId);
			if (sysRole != null) {
				// 查到了 取出角色标示名称如"admin" 添加到角色标识符列表中
				sysRoles.add(sysRole.getRoleName());
			}
		}
		return sysRoles;
	}

	@Override
	public String getRolesNamesByIds(String roleIds) {
		if (StringUtils.isEmpty(roleIds)) {
			//空字符串返回空字符串
			return "";
		}
		String[] ids = roleIds.split(",");		
		// 准备角色Set存放返回数据
		String sysRoles = "";
		for (String sysRoleId : ids) {
			// 根据角色id号查找SysRole
			SysRole sysRole = findSysRoleById(sysRoleId);
			
			if (sysRole != null) {
				// 查到了 取出角色标示名称如"admin" 添加到角色标识符列表中
				sysRoles=sysRoles+sysRole.getRoleName()+",";
			}
		}
		if(sysRoles.length()>0){
			sysRoles=sysRoles.substring(0, sysRoles.length()-1);
		}
		return sysRoles;
	}

}

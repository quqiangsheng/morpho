package com.max256.morpho.sys.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.max256.morpho.common.entity.SysResource;
import com.max256.morpho.common.service.impl.BaseServiceImpl;
import com.max256.morpho.sys.service.SysResourceService;

import tk.mybatis.mapper.entity.Example;
@Service("sysResourceService")
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService{

	

	@Override
	public List<SysResource> findAll() {
		SysResource param=new SysResource();
		List<SysResource>  findSysResource=select(param);
		return findSysResource;
	}

	@Override
	public List<SysResource> findMenus(Set<String> permissions) {
		// 先找到allSysResources
		List<SysResource> allSysResources = findAll();
		// 准备menus List 用作存储返回结果
		List<SysResource> menus = new ArrayList<SysResource>();
		// 遍历所有的allSysResources
		for (SysResource sysResource : allSysResources) {
			if (sysResource.isRootNode()) {
				// 如果SysResource是根节点 跳过
				continue;
			}
			if (!sysResource.getResourceType() .equals("1")) {
				// 如果SysResource的类型不是菜单类型 跳过
				continue;
			}
			if (!hasPermission(permissions, sysResource)) {
				// 如果这个sysResource不在permissions权限字符串中 说明当前用户没有这个权限 跳过
				// 不把这个sysResource添加到菜单中
				continue;
			}
			// 添加到菜单列表中
			menus.add(sysResource);
		}
		return menus;
	}

	@Override
	public Set<String> findPermissions(Set<String> sysResourceIds) {
		// 权限字符串集合，用来返回
		Set<String> permissions = new HashSet<String>();
		// 遍历传进来的sysResourceIds
		for (String sysResourceId : sysResourceIds) {
			// 根据每个资源id的查找资源实体SysResource
			SysResource sysResource = findSysResourceById(sysResourceId);
			// 如果找了sysResource不为空且sysResource的Permission不为空
			if (sysResource != null
					&& !StringUtils.isEmpty(sysResource.getPermission())) {
				// 取出sysResource中的权限字符串 加入到permissions中 用于返回
				permissions.add(sysResource.getPermission());
			}
		}
		// 返回权限字符串
		return permissions;
		
	}

	@Override
	public SysResource findSysResourceById(String sysResourceId) {
		SysResource param=new SysResource();
		param.setResourceId(Integer.parseInt(sysResourceId));
		SysResource findSysResource=selectOne(param);
		return findSysResource;
	}

	@Override
	public Serializable createSysResource(SysResource sysResource) {
		//创建新的资源时先对比是否有和目前资源重复的 没有的话新建资源 有的话更新老的资源
		SysResource param=new SysResource();
		param.setResourceName(sysResource.getResourceName());
		SysResource findSysResource=selectOne(param);
		if(findSysResource!=null){
			//属于老资源更新 并不新建
			findSysResource.setPermission(sysResource.getPermission());//更新权限字符串
			findSysResource.setResourceUrl(sysResource.getResourceUrl());//更新资源URL
			findSysResource.setResourceType(sysResource.getResourceType());//更新资源类型
			return insert(findSysResource);
		}
		//新资源创建它
		return insert(sysResource);
	}
	// 判断permissions权限字符串中是否有给定SysResource资源的权限
	private boolean hasPermission(Set<String> permissions, SysResource resource) {
		if (StringUtils.isEmpty(resource.getPermission())) {
			return true;
		}
		// 遍历权限字符串
		for (String permission : permissions) {
			WildcardPermission p1 = new WildcardPermission(permission);
			WildcardPermission p2 = new WildcardPermission(
					resource.getPermission());
			if (p1.implies(p2) || p2.implies(p1)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void deleteSelfAndSubSysResource(SysResource sysResource) {
		//先删除自己
		this.delete(sysResource);
		//判断有没有子
		Example example =new Example(SysResource.class);
		example.createCriteria().andEqualTo("parentId", sysResource.getResourceId());
		//如果有子 则selectByExample.size()大于0
		List<SysResource> selectByExample = this.selectByExample(example);
		if(selectByExample!=null&&selectByExample.size()>0){
			//递归删除
			for (SysResource sysResource2 : selectByExample) {
				deleteSelfAndSubSysResource(sysResource2);
			}
		}
	}


}

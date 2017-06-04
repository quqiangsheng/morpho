/*package com.max256.morpho.common.util.excel;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.util.ApplicationContextUtils;

*//**
 * 字段类型转换
 * @author ThinkGem
 *//*
public class RoleListType {
	
	private static SysUserService sysUserService = ApplicationContextUtils.getBean(SysUserService.class);
	
	*//**
	 * 获取对象值（导入）
	 *//*
	public static Object getValue(String val) {
		List<SysUser> roleList = Lists.newArrayList();
		List<SysUser> allRoleList = sysUserService.findAll();
		for (String s : StringUtils.split(val, ",")){
			for (SysUser e : allRoleList){
				if (e.getUserName().equals(s)){
					roleList.add(e);
				}
			}
		}
		return roleList.size()>0?roleList:null;
	}
	
	*//**
	 * 设置对象值（导出）
	 *//*
	public static String setValue(Object val) {
		if (val != null){
			String sb="";
			@SuppressWarnings("unchecked")
			List<SysUser> roleList = (List<SysUser>)val;
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = roleList.iterator(); iterator.hasNext();) {
				SysUser sysUser = (SysUser) iterator.next();
				sb+=sysUser.getUserName();
				sb+=",";
			}
			return sb;
		}
		return "";
	}
	
}
*/
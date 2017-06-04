package com.max256.morpho.sys.service.impl;

import org.springframework.stereotype.Service;

import com.max256.morpho.common.entity.SysOrganization;
import com.max256.morpho.common.service.impl.BaseServiceImpl;
import com.max256.morpho.sys.service.SysOrganizationService;

import tk.mybatis.mapper.entity.Example;
@Service("sysOrganizationService")
public class SysOrganizationServiceImpl extends BaseServiceImpl<SysOrganization> implements SysOrganizationService{
	@Override
	public void deleteSelfAndSubSysOrganization(SysOrganization sysOrganization){
		//先删除自己
		this.delete(sysOrganization);
		//删除子
		Example example =new Example(SysOrganization.class);
		example.createCriteria().andEqualTo("parentId", sysOrganization.getOrgId());
		//删除
		this.deleteByExample(example);
	}
}

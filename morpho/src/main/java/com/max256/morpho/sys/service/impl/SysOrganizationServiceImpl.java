package com.max256.morpho.sys.service.impl;

import java.util.List;

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
		//判断有没有子
		Example example =new Example(SysOrganization.class);
		example.createCriteria().andEqualTo("parentId", sysOrganization.getOrgId());
		//如果有子 则selectByExample.size()大于0
		List<SysOrganization> selectByExample = this.selectByExample(example);
		if(selectByExample!=null&&selectByExample.size()>0){
			//递归删除
			for (SysOrganization sysOrganization2 : selectByExample) {
				deleteSelfAndSubSysOrganization(sysOrganization2);
			}
		}
		
		
	}
}

package com.max256.morpho.sys.service;

import com.max256.morpho.common.entity.SysOrganization;
import com.max256.morpho.common.service.BaseService;



public interface SysOrganizationService extends BaseService<SysOrganization> {

	/**
	 * 删除自己和子组织机构
	 * @param orgId
	 */
	void deleteSelfAndSubSysOrganization(SysOrganization sysOrganization);
	
}

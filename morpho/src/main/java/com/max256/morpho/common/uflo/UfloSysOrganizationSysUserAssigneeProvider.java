package com.max256.morpho.common.uflo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.assign.Entity;
import com.bstek.uflo.process.assign.PageQuery;
import com.max256.morpho.common.entity.SysOrganization;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.sys.service.SysOrganizationService;
import com.max256.morpho.sys.service.SysUserService;

import tk.mybatis.mapper.entity.Example;

/**
 * 部门->用户分配
 * @author fbf
 * @since 2017年8月23日
 */
@Component
public class UfloSysOrganizationSysUserAssigneeProvider implements AssigneeProvider {
	private boolean disabledSysOrganizationSysUserProvider=false;//默认启用
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrganizationService sysOrganizationService;
	//树形结构
	public boolean isTree() {
		return true;
	}

	public String getName() {
		return "组织机构->用户";
	}

	public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {
		if(parentId!=null && parentId.equals("null")){
			parentId=null;
		}
		if(parentId==null){
			parentId="0";
		}
		List<Entity> list=new ArrayList<Entity>();		
		Example example=new Example(SysOrganization.class);
		example.createCriteria().andEqualTo("parentId", parentId);
		example.createCriteria().andEqualTo("isValid", "1");
		//找出所有下一级
    	List<SysOrganization> sysOrganizations= sysOrganizationService.selectByExample(example);
    	if(sysOrganizations!=null&&sysOrganizations.size()!=0){
    		
    		for(SysOrganization sysOrganization:sysOrganizations){
    			Entity entity=new Entity(sysOrganization.getOrgId().toString(),sysOrganization.getOrgName());
    			//如果是叶子结点是可以选择的 非叶子结点不能选择
    			Example example1=new Example(SysOrganization.class);
    			example1.createCriteria().andEqualTo("parentId", sysOrganization.getOrgId());
    			example1.createCriteria().andEqualTo("isValid", "1");
    			Integer selectCountByExample = sysOrganizationService.selectCountByExample(example1);
    			if(selectCountByExample==0){
    				//是叶子节点
    				entity.setChosen(false);
    			}else{
    				//非叶子节点只能展开
    				entity.setChosen(false);
    			}
    			list.add(entity);
    		}
    		
    	}
    	
        if(parentId != null) {
        	SysUser param=new SysUser();
        	param.setIsValid("1");
        	param.setSysOrganizationId(parentId);
            List<SysUser> select = sysUserService.select(param);
            if (select != null) {
              for (SysUser user : select) {
                list.add(new Entity(user.getUserId(), user.getUserName()));
              }
            }
         }
        pageQuery.setResult(list);
		pageQuery.setTree(true);

	}

	/* 
	 * 
	 * 根据	entityId招对应的用户ids
	 *  */
	public Collection<String> getUsers(String entityId,Context context,ProcessInstance processInstance) {
		List<String> users=new ArrayList<String>();
		users.add(entityId);
		return users;
	}

	public boolean disable() {
		return disabledSysOrganizationSysUserProvider;
	}



	public boolean isDisabledSysOrganizationSysUserProvider() {
		return disabledSysOrganizationSysUserProvider;
	}

	public void setDisabledSysOrganizationSysUserProvider(boolean disabledSysOrganizationSysUserProvider) {
		this.disabledSysOrganizationSysUserProvider = disabledSysOrganizationSysUserProvider;
	}

	
}

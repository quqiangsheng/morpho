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
import com.max256.morpho.common.entity.SysRole;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.sys.service.SysRoleService;
import com.max256.morpho.sys.service.SysUserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 角色->用户分配
 * @author fbf
 * @since 2017年8月23日
 */
@Component
public class UfloSysRoleSysUserAssigneeProvider implements AssigneeProvider {
	private boolean disabledSysRoleSysUserProvider=false;//默认启用
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysRoleService sysRoleService;
	/**
	 * 设计器层面是否要用树形结构进行展示
	 * @return 返回true，表示设计器会用树形加载当前任务处理人列表
	 */
	public boolean isTree() {
		return true;
	}
	/**
	 * @return 返回当前任务处理人提供者名称，比如员工列表，部门列表等
	 */
	public String getName() {
		return "角色->用户";
	}
	/**
	 * @return the disabledSysRoleSysUserProvider
	 */
	public boolean isDisabledSysRoleSysUserProvider() {
		return disabledSysRoleSysUserProvider;
	}
	/**
	 * @param disabledSysRoleSysUserProvider the disabledSysRoleSysUserProvider to set
	 */
	public void setDisabledSysRoleSysUserProvider(boolean disabledSysRoleSysUserProvider) {
		this.disabledSysRoleSysUserProvider = disabledSysRoleSysUserProvider;
	}
	/**
	 * 分页方式查询返回具体的任务处理人，可以是具体的人，也可以是部门等之类容器型对象
	 * @param pageQuery 用于包装分页信息的查询对象
	 * @param parentId 上级实体对象的ID，可能为空
	 */
	public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {	
		
		List<Entity> entitys=new ArrayList<Entity>();
		if(parentId==null){
			Example example =new Example(SysRole.class);
			example.createCriteria().andEqualTo("isValid", "1");
			List<SysRole> findList=sysRoleService.selectByExample(example);
			for (SysRole sysRole : findList) {
				Entity entity = new Entity(sysRole.getRoleId(),sysRole.getRoleName());
				entity.setChosen(false);
				entitys.add(entity);
			}
		}else{
			//根据角色id查找用户
			Example example =new Example(SysUser.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("isValid","1");
			Criteria createCriteria2 = example.createCriteria();
			createCriteria2.orLike("sysRoleIds", parentId);
			createCriteria2.orLike("sysRoleIds", parentId+",%");
			createCriteria2.orLike("sysRoleIds", "%,"+parentId+",%");
			createCriteria2.orLike("sysRoleIds", "%,"+parentId);
			example.and(createCriteria2);
			List<SysUser> selectByExample = sysUserService.selectByExample(example);
			for (SysUser sysUser : selectByExample) {				
				entitys.add(new Entity(sysUser.getUserId(), sysUser.getUserName()));
			}	
		}
		pageQuery.setResult(entitys);
	}
	/**
	 * 根据指定的处理人ID，返回具体的任务处理人用户名
	 * @param entityId 处理人ID，可能是一个用户的用户名，这样就是直接返回这个用户名，也可能是一个部门的ID，那么就是返回这个部门下的所有用户的用户名等 
	 * @param context context 流程上下文对象
	 * @param processInstance 流程实例对象
	 * @return 返回一个或多个任务处理人的ID
	 */
	public Collection<String> getUsers(String entityId,Context context,ProcessInstance processInstance) {
		Collection<String> usernames = new ArrayList<String>();
	    usernames.add(entityId);
	    return usernames;
	}
	/**
	 * @return 是否禁用当前任务处理人提供器
	 */
	public boolean disable() {
		return disabledSysRoleSysUserProvider;
	}
	
	
	public boolean isDisabledSysRoleSysUserAssigneeProvider() {
		return disabledSysRoleSysUserProvider;
	}

	
}

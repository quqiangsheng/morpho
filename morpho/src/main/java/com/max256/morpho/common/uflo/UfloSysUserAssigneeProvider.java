package com.max256.morpho.common.uflo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.Entity;
import com.bstek.uflo.process.assign.PageQuery;
import com.bstek.uflo.process.assign.impl.AbstractAssigneeProvider;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.sys.service.SysUserService;

import tk.mybatis.mapper.entity.Example;


/**
 * uflo2流程任务指定者与系统用户相关联
 * @author fbf
 *
 */
@Component
public class UfloSysUserAssigneeProvider extends AbstractAssigneeProvider{
	@Resource(name="sysUserService")
	private SysUserService sysUserService;
	private boolean disabledSysUserAssigneeProvider=false;
	/**
	 * 设计器层面是否要用树形结构进行展示
	 * @return 返回true，表示设计器会用树形加载当前任务处理人列表
	 */
	public boolean isTree() {
		return false;
	}
	/**
	 * @return 返回当前任务处理人提供者名称，比如员工列表，部门列表等
	 */
	public String getName() {
		return "用户";
	}
	/**
	 * 分页方式查询返回具体的任务处理人，可以是具体的人，也可以是部门等之类容器型对象
	 * @param pageQuery 用于包装分页信息的查询对象
	 * @param parentId 上级实体对象的ID，可能为空
	 */
	public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {	
		int index=pageQuery.getPageIndex();
		int size=pageQuery.getPageSize();
		Example example =new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", "1");
		List<SysUser> findList=sysUserService.selectByExampleAndRowBounds
				(example, new RowBounds((index-1)*size, size));
		pageQuery.setRecordCount(sysUserService.selectCountByExample(example));
		
		List<Entity> entitys=new ArrayList<Entity>();
		for (SysUser sysUser : findList) {
			entitys.add(new Entity(sysUser.getUserId(),sysUser.getUserName()));
		}
		pageQuery.setResult(entitys);
	}
	/**
	 * @param disabledSysUserAssigneeProvider the disabledSysUserAssigneeProvider to set
	 */
	public void setDisabledSysUserAssigneeProvider(boolean disabledSysUserAssigneeProvider) {
		this.disabledSysUserAssigneeProvider = disabledSysUserAssigneeProvider;
	}
	/**
	 * 根据指定的处理人ID，返回具体的任务处理人用户名
	 * @param entityId 处理人ID，可能是一个用户的用户名，这样就是直接返回这个用户名，也可能是一个部门的ID，那么就是返回这个部门下的所有用户的用户名等 
	 * @param context context 流程上下文对象
	 * @param processInstance 流程实例对象
	 * @return 返回一个或多个任务处理人的ID
	 */
	public Collection<String> getUsers(String entityId,Context context,ProcessInstance processInstance) {
		List<String> users=new ArrayList<String>();
		users.add(entityId);
		return users;
	}
	/**
	 * @return 是否禁用当前任务处理人提供器
	 */
	public boolean disable() {
		return disabledSysUserAssigneeProvider;
	}
	
	
	public boolean isDisabledSysUserAssigneeProvider() {
		return disabledSysUserAssigneeProvider;
	}
	

	
}

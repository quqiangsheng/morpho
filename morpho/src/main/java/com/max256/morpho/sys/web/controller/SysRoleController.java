package com.max256.morpho.sys.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.max256.morpho.common.annotation.SysRes;
import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.dto.DataGridReturnData;
import com.max256.morpho.common.dto.GeneralReturnData;
import com.max256.morpho.common.dto.R;
import com.max256.morpho.common.entity.SysRole;
import com.max256.morpho.common.util.UUIDUtils;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.SysRoleService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 角色
 * 控制器
 * @author fbf
 */
@RequestMapping("/sys/sysrole")
@Controller
@SysRes(name="角色管理",type=Constants.MENU)
@RequiresPermissions("sys:role:*")
public class SysRoleController extends AbstractBaseController {
	@Resource
	private SysRoleService sysRoleService;
	/**
	 * 到角色管理页面
	 */
	@SysRes(name="页面访问")
	@RequiresPermissions("sys:role:page")
	@RequestMapping(value="/sysrole", method=RequestMethod.GET)
	public String goSysRole(){
		return "sys/sysrole/sysrole";
	}
	/**
	 * 到新增角色页面
	 */
	@RequiresPermissions("sys:role:page")
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String goSysRoleAdd(){
		return "sys/sysrole/sysrole_add";
	}
	
	/**
	 * 到修改角色页面
	 */
	@RequiresPermissions("sys:role:page")
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String goSysRoleEdit(){
		return "sys/sysrole/sysrole_edit";
	}
	
	/**
	 * 删除角色
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="删除")
	@RequiresPermissions("sys:role:delete")
	public  GeneralReturnData doDelete(String uuid){
		GeneralReturnData result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//删除
		sysRoleService.deleteByPrimaryKey(uuid);
		result.setInfo("删除成功");
		result.setStatus("1");
		return result;
	}
	
	/**
	 * 所有角色列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	@SysRes(name="查询")
	@RequiresPermissions("sys:role:query")
	public DataGridReturnData<SysRole> list(
		@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
		SysRole sysRole){
		DataGridReturnData<SysRole> r=R.dataGrid();
		r.setPageNumber(pageNumber);
		r.setPageSize(pageSize);
		Example example =new Example(SysRole.class);
		Criteria criteria=example.createCriteria();
		if(StringUtils.isNotBlank(sysRole.getRoleName())){
			criteria.andEqualTo("roleName", sysRole.getRoleName());
		}
		if(StringUtils.isNotBlank(sysRole.getRoleId())){
			criteria.andEqualTo("roleId", sysRole.getRoleId());
		}
		if(StringUtils.isNotBlank(sysRole.getIsValid())){
			criteria.andEqualTo("isValid", sysRole.getIsValid());
		}
		List<SysRole> findList=sysRoleService.selectByExampleAndRowBounds(example, new RowBounds((pageNumber-1)*pageSize, pageSize));
		r.setRows(findList);
		r.setTotal(sysRoleService.selectCountByExample(example));
		r.setStatus("1");
		r.setInfo("所有角色列表");
		return r;
	}
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/uuid/{uuid}")
	@ResponseBody
	@RequiresPermissions("sys:role:query")
	public GeneralReturnData uuid(@PathVariable("uuid") String uuid){
		GeneralReturnData<SysRole> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//查找SysRole
		SysRole findSysRole=sysRoleService.selectByPrimaryKey(uuid);
		if(null==findSysRole){
			result.setInfo("查询结果为空");
			result.setStatus("0");
			return result;
		}
		result.setData(findSysRole);
		result.setStatus("1");
		return result;
	}
	/**
	 * 创建新角色
	 * @param 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/createsysrole")
	@ResponseBody
	@SysRes(name="新增")
	@RequiresPermissions("sys:role:create")
	public GeneralReturnData doCreateSysRole(SysRole sysRole){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(null==sysRole){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		sysRole.setUuid(UUIDUtils.get32UUID());
		//校验id
		if(StringUtils.isNotBlank(sysRole.getRoleId())){
			//用户有输入 检验是否被使用
			SysRole paramSysRole=new SysRole();
			paramSysRole.setRoleId(sysRole.getRoleId());
			int findCount=sysRoleService.selectCount(paramSysRole);
			if(findCount!=0){
				result.setInfo("该角色ID已经被使用,请重新输入");
				result.setStatus("0");
				return result;
			}
		}else{
			result.setInfo("角色ID不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysRole.getRoleName())){
			result.setInfo("角色名不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysRole.getRoleDesc())){
			result.setInfo("角色描述不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysRole.getResourceIds())){
			result.setInfo("角色拥有的资源权限不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysRole.getIsValid())){
			result.setInfo("请选择是否启用角色,您没有选择");
			result.setStatus("0");
			return result;
		}
		if(!sysRole.getIsValid().equals("0")&&!sysRole.getIsValid().equals("1")){
			result.setInfo("是否启用角色参数错误,只能为0或1,分别代表不启用和启用");
			result.setStatus("0");
			return result;
		}
		
		//校验角色名字是否重复
		SysRole paramSysRole=new SysRole();
		paramSysRole.setRoleName(sysRole.getRoleName());
		int findCount=sysRoleService.selectCount(paramSysRole);
		if(findCount!=0){
			result.setInfo("该角色名已经被使用,请重新输入");
			result.setStatus("0");
			return result;
		}
		
		// 新增
		sysRoleService.insert(sysRole);
		result.setInfo("新增角色成功");
		result.setStatus("1");
		return result;
	}
	/**
	 * 修改角色信息
	 * @param 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/updatesysrole")
	@ResponseBody
	@SysRes(name="更新")
	@RequiresPermissions("sys:role:update")
	public GeneralReturnData doUpdateSysRole(SysRole sysRole){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(null==sysRole){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysRole.getUuid())){
			result.setInfo("参数中主键错误");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysRole.getResourceIds())){
			result.setInfo("资源权限id不能为空");
			result.setStatus("0");
			return result;
		}
		//查找SysRole
		SysRole findSysRole=sysRoleService.selectByPrimaryKey(sysRole.getUuid());
		if(null==findSysRole){
			result.setInfo("找不到对应的角色信息");
			result.setStatus("0");
			return result;
		}
		//查到结果 
		//修改角色描述
		if(StringUtils.isNotBlank(sysRole.getRoleDesc())){
			findSysRole.setRoleDesc(sysRole.getRoleDesc());
		}
		
		//修改是否可用
		if(StringUtils.isBlank(sysRole.getIsValid())){
			result.setInfo("请选择是否启用角色,您没有选择");
			result.setStatus("0");
			return result;
		}
		if(!sysRole.getIsValid().equals("0")&&!sysRole.getIsValid().equals("1")){
			result.setInfo("是否启用角色参数错误,只能为0或1,分别代表不启用和启用");
			result.setStatus("0");
			return result;
		}else{
			findSysRole.setIsValid(sysRole.getIsValid());
		}
		findSysRole.setResourceIds(sysRole.getResourceIds());
		// 保存修改
		sysRoleService.updateByPrimaryKey(findSysRole);
		result.setInfo("修改角色成功");
		result.setStatus("1");
		return result;
	}
}

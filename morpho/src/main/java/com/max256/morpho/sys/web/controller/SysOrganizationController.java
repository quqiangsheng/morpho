package com.max256.morpho.sys.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import com.max256.morpho.common.dto.TreeReturnData;
import com.max256.morpho.common.entity.SysOrganization;
import com.max256.morpho.common.util.UUIDUtils;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.SysOrganizationService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 组织机构
 * 控制器
 * @author fbf
 */
@RequestMapping("/sys/sysorganization")
@Controller
@SysRes(name="组织机构管理",type=Constants.MENU)
@RequiresPermissions("sys:organization:*")
public class SysOrganizationController extends AbstractBaseController {
	@Resource
	private SysOrganizationService sysOrganizationService;
	/**
	 * 到组织机构页面
	 */
	@RequestMapping(value="/sysorganization", method=RequestMethod.GET)
	@SysRes(name="页面访问")
	@RequiresPermissions("sys:organization:page")
	public String goSysOrganization(){
		return "sys/sysorganization/sysorganization";
	}
	
	/**
	 * 到新增组织机构页面
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	@RequiresPermissions("sys:organization:page")
	public String goSysOrganizationAdd(){
		return "sys/sysorganization/sysorganization_add";
	}
	
	/**
	 * 到修改组织机构页面
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	@RequiresPermissions("sys:organization:page")
	public String goSysOrganizationEdit(){
		return "sys/sysorganization/sysorganization_edit";
	}
	
	/**
     * 获取组织机构的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
	@SysRes(name="查询")
	@RequiresPermissions("sys:organization:query")
    public DataGridReturnData<TreeReturnData> tree() {
    	//准备返回数据
    	DataGridReturnData<TreeReturnData>  r=R.dataGrid();
    	r.setRows(new ArrayList<TreeReturnData>());
    	//找出所有的树
    	List<SysOrganization> findSysOrganizationList= sysOrganizationService.selectAll();
    	if(findSysOrganizationList.size()==0){
    		r.setStatus("0");
        	r.setInfo("没有数据");
        	r.setTotal(0);
            return r;
    	}
    	
    	for (Iterator<SysOrganization> iterator = findSysOrganizationList.iterator(); iterator.hasNext();) {
			SysOrganization sysOrganization =  iterator.next();
			TreeReturnData treeReturnData=new TreeReturnData();
			treeReturnData.setName(sysOrganization.getOrgName());
			treeReturnData.setId(sysOrganization.getOrgId());
			treeReturnData.setpId(sysOrganization.getParentId());
			treeReturnData.setComment(sysOrganization.getOrgDesc());
			if(sysOrganization.getOrgId()==1){
				//顶级机构默认选中默认展开
				treeReturnData.setChecked(true);
				treeReturnData.setOpen(true);
			}
			r.getRows().add(treeReturnData);
		}
    	r.setStatus("1");
    	r.setInfo("全部的组织机构树");
    	r.setTotal(findSysOrganizationList.size());
        return r;
    }
	
	/**
     * 获取所有组织机构列表 按照pid排序+id排序
     */
    @RequestMapping(value = "/list")
    @ResponseBody
	@RequiresPermissions("sys:organization:query")
    public Object list(@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
    		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
    		SysOrganization sysOrganization) {
    	//准备返回数据
    	DataGridReturnData<SysOrganization> r=R.dataGrid();
    	//分页
    	r.setPageNumber(pageNumber);
    	r.setPageSize(pageSize);
    	Example example = new Example(SysOrganization.class);
    	Criteria criteria=example.createCriteria();
    	if(StringUtils.isNotBlank(sysOrganization.getOrgName())){
    		criteria.andEqualTo("orgName", sysOrganization.getOrgName());
    	}
    	if(sysOrganization.getOrgId()!=null){
    		criteria.andEqualTo("orgId", sysOrganization.getOrgId());
    	}
    	if(sysOrganization.getParentId()!=null){
    		criteria.andEqualTo("parentId", sysOrganization.getParentId());
    	}
    	example.orderBy("parentId").asc().orderBy("orgId").asc();
    	List<SysOrganization>  findList=sysOrganizationService.selectByExampleAndRowBounds(example, new RowBounds((pageNumber-1)*pageSize, pageSize));
    	r.setRows(findList);
		r.setTotal(sysOrganizationService.selectCountByExample(example));
		r.setStatus("1");
		r.setInfo("所有组织机构列表");
		return r;
    }
    /**
	 * 删除
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="删除")
	@RequiresPermissions("sys:organization:delete")
	public  GeneralReturnData doDelete(String uuid){
		GeneralReturnData result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//
		SysOrganization findSysOrganization=sysOrganizationService.selectByPrimaryKey(uuid);
		if(findSysOrganization==null){
			result.setInfo("找不到该机构");
			result.setStatus("0");
			return result;
		}
		//删除自己以及子组织机构
		sysOrganizationService.deleteSelfAndSubSysOrganization(findSysOrganization);
		result.setInfo("删除成功");
		result.setStatus("1");
		return result;
	}
	
	
	/**
	 * 添加新的组织机构
	 * @param orgId
	 * @param orgName
	 * @param orgDesc
	 * @param isValid
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="新增")
	@RequiresPermissions("sys:organization:create")
	public  GeneralReturnData<String> doAdd(
			@RequestParam(defaultValue="" ,name="orgId") String orgId,
			@RequestParam(defaultValue="" ,name="orgName") String orgName,
			@RequestParam(defaultValue="" ,name="orgDesc") String orgDesc,
			@RequestParam(defaultValue="" ,name="isValid") String isValid,
			@RequestParam(defaultValue="" ,name="pid") String pid){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(orgId)
				||StringUtils.isBlank(orgName)
				||StringUtils.isBlank(orgDesc)
				||!NumberUtils.isNumber(orgId)
				||!NumberUtils.isNumber(pid)
				||!NumberUtils.isNumber(isValid)){
			result.setInfo("参数错误组织机构ID,上级组织ID和组织名称不能为空,组织机构ID和上级ID必须是数字");
			result.setStatus("0");
			return result;
		}
		//唯一性校验
		SysOrganization param1=new SysOrganization();
		param1.setOrgId(Integer.parseInt(orgId));
		Integer result1=sysOrganizationService.selectCount(param1);
		SysOrganization param2=new SysOrganization();
		param2.setOrgName(orgName);
		Integer result2=sysOrganizationService.selectCount(param2);
		SysOrganization param3=new SysOrganization();
		if(result1>0||result2>0){
			result.setInfo("参数错误,组织机构ID和组织名称ID不能重复");
			result.setStatus("0");
			return result;
		}
		param3.setOrgId(Integer.parseInt(pid));
		Integer result3=sysOrganizationService.selectCount(param3);
		if(result3==0){
			result.setInfo("上级组织机构ID不存在");
			result.setStatus("0");
			return result;
		}
		//新增
		SysOrganization newSysOrganization= new SysOrganization();
		newSysOrganization.setUuid(UUIDUtils.get32UUID());
		newSysOrganization.setIsValid(isValid);
		newSysOrganization.setOrgDesc(orgDesc);
		newSysOrganization.setOrgId(Integer.parseInt(orgId));
		newSysOrganization.setOrgName(orgName);
		newSysOrganization.setParentId(Integer.parseInt(pid));
		newSysOrganization.setRegisterTime(new Date());
		if(isValid!=null&&isValid.equals("0")){
			newSysOrganization.setIsValid("0");
		}else{
			newSysOrganization.setIsValid("1");
		}
		//添加
		sysOrganizationService.insert(newSysOrganization);
		result.setInfo("添加成功");
		result.setStatus("1");
		return result;
	}
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/uuid/{uuid}")
	@ResponseBody
	@RequiresPermissions("sys:organization:query")
	public GeneralReturnData uuid(@PathVariable("uuid") String uuid){
		GeneralReturnData<SysOrganization> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//查找SysOrganization
		SysOrganization findSysOrganization=sysOrganizationService.selectByPrimaryKey(uuid);
		if(null==findSysOrganization){
			result.setInfo("查询结果为空");
			result.setStatus("0");
			return result;
		}
		result.setData(findSysOrganization);
		result.setStatus("1");
		return result;
	}
	
	/**
	 * 更新组织机构
	 * @param orgId
	 * @param orgName
	 * @param orgDesc
	 * @param isValid
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="更新")
	@RequiresPermissions("sys:organization:update")
	public  GeneralReturnData<String> doUpdate(
			@RequestParam(defaultValue="" ,name="uuid") String uuid,
			@RequestParam(defaultValue="" ,name="orgName") String orgName,
			@RequestParam(defaultValue="" ,name="orgDesc") String orgDesc,
			@RequestParam(defaultValue="1" ,name="isValid") String isValid,
			@RequestParam(defaultValue="" ,name="pid") String pid){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)
				||StringUtils.isBlank(orgName)
				||StringUtils.isBlank(orgDesc)
				||!NumberUtils.isNumber(pid)
				||!NumberUtils.isNumber(isValid)){
			result.setInfo("参数错误组织机构主键,上级组织ID和组织名称不能为空,上级ID必须是数字");
			result.setStatus("0");
			return result;
		}
		//唯一性校验
		SysOrganization param1=new SysOrganization();
		param1.setUuid(uuid);
		Integer result1=sysOrganizationService.selectCount(param1);
		if(result1!=1){
			result.setInfo("通过主键找不到该组织机构");
			result.setStatus("0");
			return result;
		}
		SysOrganization findSysOrganization=sysOrganizationService.selectOne(param1);
		if(orgName.equals(findSysOrganization.getOrgName())){
			//名字相同不用更改
		}else{
			SysOrganization param2=new SysOrganization();
			param2.setOrgName(orgName);
			Integer result2=sysOrganizationService.selectCount(param2);
			if(result2>0){
				result.setInfo("该组织机构名字已被使用");
				result.setStatus("0");
				return result;
			}
		}
		//检查上级机构是否有变化
		if(!pid.equals(findSysOrganization.getParentId()+"")){
			//检查上级组织机构是否存在
			SysOrganization param3=new SysOrganization();
			param3.setOrgId(Integer.parseInt(pid));
			Integer result3=sysOrganizationService.selectCount(param3);
			if(result3==0){
				result.setInfo("上级组织机构ID不存在");
				result.setStatus("0");
				return result;
			}
		}
		if(pid.equals(findSysOrganization.getOrgId())){
			result.setInfo("该组织机构的上级组织不能是自己");
			result.setStatus("0");
			return result;
		}
		
		//修改
		findSysOrganization.setOrgName(orgName);
		findSysOrganization.setOrgDesc(orgDesc);
		findSysOrganization.setIsValid(isValid);
		findSysOrganization.setParentId(Integer.parseInt(pid));
		sysOrganizationService.updateByPrimaryKey(findSysOrganization);
		result.setInfo("修改成功");
		result.setStatus("1");
		return result;
	}
	
	
	
}

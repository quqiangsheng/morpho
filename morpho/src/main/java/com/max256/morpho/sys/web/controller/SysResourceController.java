package com.max256.morpho.sys.web.controller;

import java.util.ArrayList;
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
import com.max256.morpho.common.entity.SysResource;
import com.max256.morpho.common.util.UUIDUtils;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.SysResourceService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 资源权限管理
 * 控制器
 * @author fbf
 */
@RequestMapping("/sys/sysresource")
@Controller
@SysRes(name="资源权限管理",type=Constants.MENU)
@RequiresPermissions("sys:resource:*")
public class SysResourceController extends AbstractBaseController {
	@Resource
	private SysResourceService sysResourceService;
	/**
	 * 到资源权限管理页面
	 */
	@RequestMapping(value="/sysresource", method=RequestMethod.GET)
	@SysRes(name="页面访问")
	@RequiresPermissions("sys:resource:page")
	public String goSysResource(){
		return "sys/sysresource/sysresource";
	}
	
	/**
	 * 到新增资源权限页面
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	@RequiresPermissions("sys:resource:page")
	public String goSysResourceAdd(){
		return "sys/sysresource/sysresource_add";
	}
	
	/**
	 * 到修改资源权限页面
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	@RequiresPermissions("sys:resource:page")
	public String goSysResourceEdit(){
		return "sys/sysresource/sysresource_edit";
	}
	
	/**
     * 获取资源权限的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
	@SysRes(name="查询")
	@RequiresPermissions("sys:resource:query")
    public DataGridReturnData<TreeReturnData> tree() {
    	//准备返回数据
    	DataGridReturnData<TreeReturnData>  r=R.dataGrid();
    	r.setRows(new ArrayList<TreeReturnData>());
    	//找出所有的树
    	List<SysResource> findSysResourceList= sysResourceService.selectAll();
    	if(findSysResourceList.size()==0){
    		r.setStatus("0");
        	r.setInfo("没有数据");
        	r.setTotal(0);
            return r;
    	}
    	
    	for (Iterator<SysResource> iterator = findSysResourceList.iterator(); iterator.hasNext();) {
			SysResource sysResource =  iterator.next();
			TreeReturnData treeReturnData=new TreeReturnData();
			treeReturnData.setName(sysResource.getResourceName());
			treeReturnData.setId(sysResource.getResourceId());
			treeReturnData.setpId(sysResource.getParentId());
			treeReturnData.setComment(sysResource.getPermission());//权限字符串放到备注字段
			if(sysResource.getResourceId().intValue()==1){
				//顶级资源权限默认选中默认展开
				treeReturnData.setChecked(true);
				treeReturnData.setOpen(true);
			}
			r.getRows().add(treeReturnData);
		}
    	r.setStatus("1");
    	r.setInfo("全部的资源权限树");
    	r.setTotal(findSysResourceList.size());
        return r;
    }
	
	/**
     * 获取所有资源权限列表 按照pid排序+id排序
     */
    @RequestMapping(value = "/list")
    @ResponseBody
	@RequiresPermissions("sys:resource:query")
    public Object list(@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
    		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
    		SysResource sysResource) {
    	//准备返回数据
    	DataGridReturnData<SysResource> r=R.dataGrid();
    	//分页
    	r.setPageNumber(pageNumber);
    	r.setPageSize(pageSize);
    	Example example = new Example(SysResource.class);
    	Criteria criteria=example.createCriteria();
    	if(StringUtils.isNotBlank(sysResource.getResourceName())){
    		criteria.andEqualTo("resourceName", sysResource.getResourceName());
    	}
    	if(sysResource.getResourceId()!=null){
    		criteria.andEqualTo("resourceId", sysResource.getResourceId());
    	}
    	if(sysResource.getParentId()!=null){
    		criteria.andEqualTo("parentId", sysResource.getParentId());
    	}
    	example.orderBy("parentId").asc().orderBy("resourceId").asc();
    	List<SysResource>  findList=sysResourceService.selectByExampleAndRowBounds(example, new RowBounds((pageNumber-1)*pageSize, pageSize));
    	r.setRows(findList);
		r.setTotal(sysResourceService.selectCountByExample(example));
		r.setStatus("1");
		r.setInfo("所有资源权限列表");
		return r;
    }
    /**
	 * 删除
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="删除")
	@RequiresPermissions("sys:resource:delete")
	public  GeneralReturnData doDelete(String uuid){
		GeneralReturnData result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//
		SysResource findSysResource=sysResourceService.selectByPrimaryKey(uuid);
		if(findSysResource==null){
			result.setInfo("找不到该资源权限");
			result.setStatus("0");
			return result;
		}
		//删除自己以及子资源权限
		sysResourceService.deleteSelfAndSubSysResource(findSysResource);
		result.setInfo("删除成功");
		result.setStatus("1");
		return result;
	}
	
	
	/**
	 * 添加新的资源权限
	 * @param resourceId
	 * @param resourceName
	 * @param permission
	 * @param isValid
	 * @param pid
	 * @param resourceType
	 * @param resourceUrl
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="新增")
	@RequiresPermissions("sys:resource:create")
	public  GeneralReturnData<String> doAdd(
			@RequestParam(defaultValue="" ,name="resourceId") String resourceId,
			@RequestParam(defaultValue="" ,name="resourceName") String resourceName,
			@RequestParam(defaultValue="" ,name="permission") String permission,
			@RequestParam(defaultValue="" ,name="isValid") String isValid,
			@RequestParam(defaultValue="" ,name="resourceType") String resourceType,
			@RequestParam(defaultValue="" ,name="resourceUrl") String resourceUrl,
			@RequestParam(defaultValue="" ,name="pid") String pid){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(resourceId)
				||StringUtils.isBlank(resourceName)
				||StringUtils.isBlank(permission)
				||StringUtils.isBlank(resourceType)
				||StringUtils.isBlank(resourceUrl)
				||!NumberUtils.isNumber(resourceId)
				||!NumberUtils.isNumber(pid)
				||!NumberUtils.isNumber(isValid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		
		if(!resourceType.equals("1")&&!resourceType.equals("2")){
			result.setInfo("参数错误resourceType只能为1菜单或者2按钮");
			result.setStatus("0");
			return result;
		}
		//唯一性校验
		SysResource param1=new SysResource();
		param1.setResourceId(Integer.parseInt(resourceId));
		Integer result1=sysResourceService.selectCount(param1);
		SysResource param2=new SysResource();
		param2.setResourceName(resourceName);
		Integer result2=sysResourceService.selectCount(param2);
		SysResource param4=new SysResource();
		param4.setPermission(permission);
		Integer result4=sysResourceService.selectCount(param4);
		SysResource param5=new SysResource();
		param5.setResourceUrl(resourceUrl);
		Integer result5=sysResourceService.selectCount(param5);
		
		if(result1>0||result2>0||result4>0||result5>0){
			result.setInfo("参数错误,资源权限ID和资源权限名字和URL和权限字符串不能重复");
			result.setStatus("0");
			return result;
		}
		SysResource param3=new SysResource();
		param3.setResourceId(Integer.parseInt(pid));
		Integer result3=sysResourceService.selectCount(param3);
		if(result3==0){
			result.setInfo("上级资源权限ID不存在");
			result.setStatus("0");
			return result;
		}
		//新增
		SysResource newSysResource= new SysResource();
		newSysResource.setUuid(UUIDUtils.get32UUID());
		newSysResource.setIsValid(isValid);
		newSysResource.setParentId(Integer.parseInt(pid));
		newSysResource.setResourceId(Integer.parseInt(resourceId));
		newSysResource.setResourceName(resourceName);
		newSysResource.setPermission(permission);
		newSysResource.setResourceUrl(resourceUrl);
		newSysResource.setResourceType(resourceType);//1菜单2按钮
		if(isValid!=null&&isValid.equals("0")){
			newSysResource.setIsValid("0");
		}else{
			newSysResource.setIsValid("1");
		}
		//添加
		sysResourceService.insert(newSysResource);
		result.setInfo("添加成功");
		result.setStatus("1");
		return result;
	}
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/uuid/{uuid}")
	@ResponseBody
	@RequiresPermissions("sys:resource:query")
	public GeneralReturnData uuid(@PathVariable("uuid") String uuid){
		GeneralReturnData<SysResource> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//查找SysResource
		SysResource findSysResource=sysResourceService.selectByPrimaryKey(uuid);
		if(null==findSysResource){
			result.setInfo("查询结果为空");
			result.setStatus("0");
			return result;
		}
		result.setData(findSysResource);
		result.setStatus("1");
		return result;
	}
	
	/**
	 * 更新资源权限
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="更新")
	@RequiresPermissions("sys:resource:update")
	public  GeneralReturnData<String> doUpdate(
			@RequestParam(defaultValue="" ,name="uuid") String uuid,
			@RequestParam(defaultValue="" ,name="permission") String permission,
			@RequestParam(defaultValue="" ,name="isValid") String isValid,
			@RequestParam(defaultValue="" ,name="resourceUrl") String resourceUrl,
			@RequestParam(defaultValue="" ,name="pid") String pid){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)
				||StringUtils.isBlank(permission)
				||StringUtils.isBlank(resourceUrl)
				||!NumberUtils.isNumber(isValid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//唯一性校验
		SysResource param1=new SysResource();
		param1.setUuid(uuid);
		Integer result1=sysResourceService.selectCount(param1);
		if(result1!=1){
			result.setInfo("通过主键找不到该资源权限");
			result.setStatus("0");
			return result;
		}
		SysResource findSysResource=sysResourceService.selectOne(param1);
		//修改
		findSysResource.setIsValid(isValid);
		findSysResource.setPermission(permission);
		findSysResource.setResourceUrl(resourceUrl);
		if(StringUtils.isNotBlank(pid)&&!findSysResource.getParentId().equals(Integer.parseInt(pid))&&!findSysResource.getResourceId().equals(Integer.parseInt(pid))){
			findSysResource.setParentId(Integer.parseInt(pid));
		}
		sysResourceService.updateByPrimaryKey(findSysResource);
		result.setInfo("修改成功");
		result.setStatus("1");
		return result;
	}
	/**
	 * 根据角色ids获得获得ResourceName
	 */
	@RequestMapping(value="/getresourcenamebyresourceid")
	@ResponseBody
	@RequiresPermissions("sys:resource:query")
	public  GeneralReturnData<String> doGetResourceNameByResourceIds(
			@RequestParam(defaultValue="" ,name="resourceIds") String resourceIds){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		ArrayList<String>  resultArray = new ArrayList<String> ();
		if(StringUtils.isBlank(resourceIds)){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		String[] strArray=resourceIds.split(",");
		List<SysResource> selectAll = sysResourceService.selectAll();
		if(selectAll.size()==0){
			result.setInfo("系统中没有找到任何资源权限,请联系管理员");
			result.setStatus("0");
			return result;
		}
		for (int i = 0; i < strArray.length; i++) {
			String param=strArray[i];
			for (Iterator<SysResource> iterator = selectAll.iterator(); iterator.hasNext();) {
				SysResource sysResource =iterator.next();
				if(param.equals(sysResource.getResourceId().intValue()+"")){
					resultArray.add(sysResource.getResourceName());
				}
			}
		}
		result.setStatus("1");
		result.setData(StringUtils.join(resultArray, ","));
		return result;
	}
	
}

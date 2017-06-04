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
import com.max256.morpho.common.entity.SysCode;
import com.max256.morpho.common.util.UUIDUtils;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.SysCodeService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 数据字典
 * 控制器
 * @author fbf
 */
@RequestMapping("/sys/syscode")
@Controller
@SysRes(name="数据字典管理",type=Constants.MENU)
@RequiresPermissions("sys:code:*")
public class SysCodeController extends AbstractBaseController {
	@Resource
	private SysCodeService sysCodeService;
	/**
	 * 到数据字典页面
	 */
	@RequestMapping(value="/syscode", method=RequestMethod.GET)
	@SysRes(name="数据字典页面访问")
	@RequiresPermissions("sys:code:page")
	public String goSysCode(){
		return "sys/syscode/syscode";
	}
	
	/**
	 * 到详细信息页面
	 */
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	@RequiresPermissions("sys:code:page")
	public String goDetail(){
		return "sys/syscode/syscode_detail";
	}
	
	/**
	 * 删除
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="删除")
	@RequiresPermissions("sys:code:delete")
	public  GeneralReturnData doDelete(String uuid){
		GeneralReturnData result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//删除
		sysCodeService.deleteByPrimaryKey(uuid);
		result.setInfo("删除成功");
		result.setStatus("1");
		return result;
	}
	
	/**
	 * 所有字典列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	@SysRes(name="查询")
	@RequiresPermissions("sys:code:query")
	public DataGridReturnData<SysCode> list(
		@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
		SysCode sysCode){
		DataGridReturnData<SysCode> r=R.dataGrid();
		r.setPageNumber(pageNumber);
		r.setPageSize(pageSize);
		Example example =new Example(SysCode.class);
		Criteria criteria=example.createCriteria();
		if(StringUtils.isNotBlank(sysCode.getCodeId())){
			criteria.andEqualTo("codeId", sysCode.getCodeId());
		}
		if(StringUtils.isNotBlank(sysCode.getCodeName())){
			criteria.andEqualTo("codeName", sysCode.getCodeName());
		}
		if(StringUtils.isNotBlank(sysCode.getCodeText())){
			criteria.andEqualTo("codeText", sysCode.getCodeText());
		}
		if(StringUtils.isNotBlank(sysCode.getCodeValue())){
			criteria.andEqualTo("codeValue", sysCode.getCodeValue());
		}
		List<SysCode> findList=sysCodeService.selectByExampleAndRowBounds(example, new RowBounds((pageNumber-1)*pageSize, pageSize));
		r.setRows(findList);
		r.setTotal(sysCodeService.selectCountByExample(example));
		r.setStatus("1");
		r.setInfo("所有字典列表");
		return r;
	}
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/uuid/{uuid}")
	@ResponseBody
	@RequiresPermissions("sys:code:query")
	public GeneralReturnData uuid(@PathVariable("uuid") String uuid){
		GeneralReturnData<SysCode> result=new GeneralReturnData<>();
		//参数校验
		if(StringUtils.isBlank(uuid)){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//查找SysUser
		SysCode findSysCode=sysCodeService.selectByPrimaryKey(uuid);
		if(null==findSysCode){
			result.setInfo("查询结果为空");
			result.setStatus("0");
			return result;
		}
		result.setData(findSysCode);
		result.setStatus("1");
		return result;
	}
	/**
	 * 创建新SysCode
	 * @param 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/createsyscode")
	@ResponseBody
	@SysRes(name="新增")
	@RequiresPermissions("sys:code:create")
	public GeneralReturnData doCreateSysCode(SysCode sysCode){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(null==sysCode){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		sysCode.setUuid(UUIDUtils.get32UUID());
		//校验id
		if(StringUtils.isNotBlank(sysCode.getCodeId())){
			//用户有输入 检验是否被使用
			SysCode paramSysCode=new SysCode();
			paramSysCode.setCodeId(sysCode.getCodeId());
			int findCount=sysCodeService.selectCount(paramSysCode);
			if(findCount!=0){
				result.setInfo("该字典项ID已经被使用,请重新输入");
				result.setStatus("0");
				return result;
			}
		}else{
			//用户没有输入自动生成和uuid一样的
			sysCode.setCodeId(sysCode.getUuid());
		}
		if(StringUtils.isBlank(sysCode.getCodeName())){
			result.setInfo("字典名不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysCode.getCodeText())){
			result.setInfo("字典文本不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysCode.getCodeValue())){
			result.setInfo("字典值不能为空,您输入的为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysCode.getIsValid())){
			sysCode.setIsValid("1");//为空的话默认为启用
		}
		// 保存
		sysCodeService.insert(sysCode);
		result.setInfo("新增字典项成功");
		result.setStatus("1");
		return result;
	}
	/**
	 * 修改字典项信息
	 * @param 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/updatesyscode")
	@ResponseBody
	@SysRes(name="修改")
	@RequiresPermissions("sys:code:update")
	public GeneralReturnData doUpdateSysCode(SysCode sysCode){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(null==sysCode){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		if(StringUtils.isBlank(sysCode.getUuid())){
			result.setInfo("参数中主键错误");
			result.setStatus("0");
			return result;
		}
		//查找findSysCode
		SysCode findSysCode=sysCodeService.selectByPrimaryKey(sysCode.getUuid());
		if(null==findSysCode){
			result.setInfo("找不到对应的字典项");
			result.setStatus("0");
			return result;
		}
		//查到结果 
		//修改CodeName
		if(StringUtils.isNotBlank(sysCode.getCodeName())){
			findSysCode.setCodeName(sysCode.getCodeName());
		}
		//修改CodeText
		if(StringUtils.isNotBlank(sysCode.getCodeText())){
			findSysCode.setCodeText(sysCode.getCodeText());
		}
		//修改CodeDesc
		if(StringUtils.isNotBlank(sysCode.getCodeDesc())){
			findSysCode.setCodeDesc(sysCode.getCodeDesc());
		}
		//修改CodeValue
		if(StringUtils.isNotBlank(sysCode.getCodeValue())){
			findSysCode.setCodeValue(sysCode.getCodeValue());
		}

		//修改是否可用
		if(StringUtils.isBlank(sysCode.getIsValid())){
			findSysCode.setIsValid("1");
		}
		if(!sysCode.getIsValid().equals("0")&&!sysCode.getIsValid().equals("1")){
			result.setInfo("是否启用参数错误,只能为0或1,分别代表不启用和启用");
			result.setStatus("0");
			return result;
		}else{
			findSysCode.setIsValid(sysCode.getIsValid());
		}
		
		// 保存修改
		sysCodeService.updateByPrimaryKey(findSysCode);
		result.setInfo("修改字典项成功");
		result.setStatus("1");
		return result;
	}
	
}

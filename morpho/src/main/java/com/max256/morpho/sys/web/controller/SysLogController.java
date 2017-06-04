package com.max256.morpho.sys.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.max256.morpho.common.annotation.SysRes;
import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.dto.DataGridReturnData;
import com.max256.morpho.common.dto.R;
import com.max256.morpho.common.entity.SysLog;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.SysLogService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 系统日志
 * @author fbf
 */
@RequestMapping("/sys/syslog")
@Controller
@SysRes(name="系统日志管理",type=Constants.MENU)
@RequiresPermissions("sys:log:*")
public class SysLogController extends AbstractBaseController {

	@Resource
	private SysLogService sysLogService;
	/**
	 * 到系统日志管理页面
	 */
	@RequestMapping(value="/syslog", method=RequestMethod.GET)
	@SysRes(name="页面访问")
	@RequiresPermissions("sys:log:page")
	public String goSyslog(){
		return "sys/syslog/syslog";
	}

	/**
	 * 所有日志列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	@SysRes(name="查询")
	@RequiresPermissions("sys:log:query")
	public DataGridReturnData<SysLog> list(
		@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
		@RequestParam(name="logUser", defaultValue="")	String logUser,
		@RequestParam(name="beginDate", defaultValue="") String beginDate, 
		@RequestParam(name="endDate", defaultValue="") String endDate){
		//示例hibernate方式 当然也可以用mybatis方式
		DataGridReturnData<SysLog> r=R.dataGrid();
		r.setPageNumber(pageNumber);
		r.setPageSize(pageSize);
		Example example =new Example(SysLog.class);
		Criteria criteria=example.createCriteria();
		if(!beginDate.equals("")&&!endDate.equals("")){
			criteria.andBetween("logTime", beginDate, endDate);
		}
		if(StringUtils.isNotBlank(logUser)){
			criteria.andEqualTo("logUser", logUser);
		}
		List<SysLog> findList=sysLogService.selectByExampleAndRowBounds(example, new RowBounds((pageNumber-1)*pageSize, pageSize));
		r.setRows(findList);
		r.setTotal(sysLogService.selectCountByExample(example));
		r.setStatus("1");
		r.setInfo("所有日志列表");
		return r;
	}
	

}

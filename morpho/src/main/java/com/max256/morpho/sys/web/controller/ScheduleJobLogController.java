package com.max256.morpho.sys.web.controller;

import java.util.List;
import java.util.Map;

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
import com.max256.morpho.common.dto.R.WrapCommand;
import com.max256.morpho.common.entity.ScheduleJobLog;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.ScheduleJobLogService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * ScheduleJobLogController
 * 定时任务日志 控制器
 * 
 * @author fbf
 */
@RequestMapping("/sys/schedulejoblog")
@Controller
@SysRes(name="定时任务日志管理",type=Constants.MENU)
@RequiresPermissions("sys:schedulejoblog:*")
public class ScheduleJobLogController extends  AbstractBaseController{
	
	@Resource
	private ScheduleJobLogService scheduleJobLogService;
	
	
	/**
	 * 到ScheduleJobLog定时任务日志页面
	 */
	@RequestMapping(value="/schedulejoblogpage", method=RequestMethod.GET)
	@SysRes(name="定时任务日志页面访问")
	@RequiresPermissions("sys:schedulejoblog:page")
	public String goScheduleJobLogPage(){
		return "sys/schedulejob/schedulejoblog_page";
	}
	
	
	/**
	 * 查询列表
	 */
	@SuppressWarnings({ "rawtypes",  "unchecked" })
	@RequestMapping("/list")
	@ResponseBody
	@SysRes(name="查询")
	@RequiresPermissions("sys:schedulejoblog:query")
	public DataGridReturnData list(
		@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
		ScheduleJobLog scheduleJobLog){
		DataGridReturnData r=R.dataGrid();
		r.setPageNumber(pageNumber);
		r.setPageSize(pageSize);
		
		Example example = new Example(ScheduleJobLog.class);
    	Criteria criteria=example.createCriteria();
    	if(StringUtils.isNotBlank(scheduleJobLog.getBeanName())){
    		criteria.andEqualTo("beanName", scheduleJobLog.getBeanName());
    	}
    	if(StringUtils.isNotBlank(scheduleJobLog.getLogId())){
    		criteria.andEqualTo("logId", scheduleJobLog.getLogId());
    	}
    	if(scheduleJobLog.getJobId()!=null){
    		criteria.andEqualTo("jobId", scheduleJobLog.getJobId());
    	}
    	if(StringUtils.isNotBlank(scheduleJobLog.getMethodName())){
    		criteria.andEqualTo("methodName", scheduleJobLog.getMethodName());
    	}
    	if(StringUtils.isNotBlank(scheduleJobLog.getParams())){
    		criteria.andEqualTo("params", scheduleJobLog.getParams());
    	}
    	if(scheduleJobLog.getStatus()!=null){
    		criteria.andEqualTo("status", scheduleJobLog.getStatus());
    	}
		List<ScheduleJobLog> findList=scheduleJobLogService.selectByExampleAndRowBounds(example, new RowBounds((pageNumber-1)*pageSize, pageSize));
		r.setRows(findList);
		r.setTotal(scheduleJobLogService.selectCountByExample(example));
		r.setStatus("1");
		r.setInfo("查询结果列表");
		R.wrapList(r,new WrapCommand() {
			@Override
			public void wrap(String fieldName, Object fildValue, Map<String, Object> wrapHashMap) {
				if(fieldName.equals("status")&&fildValue.equals(ScheduleJobLog.STATUS_SUCCESS)){
					wrapHashMap.put("statusText", "正常");
				}
				if(fieldName.equals("status")&&fildValue.equals(ScheduleJobLog.STATUS_FAIL)){
					wrapHashMap.put("statusText", "失败");
				}
			}
		});
		return r;
	}

}

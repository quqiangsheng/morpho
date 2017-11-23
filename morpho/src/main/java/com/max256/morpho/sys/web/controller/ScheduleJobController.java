package com.max256.morpho.sys.web.controller;




import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.JobKey;
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
import com.max256.morpho.common.dto.R.WrapCommand;
import com.max256.morpho.common.entity.ScheduleJob;
import com.max256.morpho.common.scheduler.quartz.ScheduleUtils;
import com.max256.morpho.common.web.controller.AbstractBaseController;
import com.max256.morpho.sys.service.ScheduleJobService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * ScheduleJobController
 * 定时任务 控制器
 * 
 * @author fbf
 */
@RequestMapping("/sys/schedulejob")
@Controller
@SysRes(name="定时任务管理",type=Constants.MENU)
@RequiresPermissions("sys:schedulejob:*")
public class ScheduleJobController extends  AbstractBaseController{
	
	@Resource
	private ScheduleJobService scheduleJobService;

	
	/**
	 * 到ScheduleJob定时任务页面
	 */
	@RequestMapping(value="/schedulejobpage", method=RequestMethod.GET)
	@SysRes(name="定时任务页面访问")
	@RequiresPermissions("sys:schedulejob:page")
	public String goScheduleJobPage(){
		return "sys/schedulejob/schedulejob_page";
	}
	
	/**
	 * 到ScheduleJob新增定时任务页面
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	@SysRes(name="定时任务页面访问")
	@RequiresPermissions("sys:schedulejob:page")
	public String goScheduleJobAddPage(){
		return "sys/schedulejob/schedulejob_add";
	}
	/**
	 * 查询指定主键的详细信息
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@SysRes(name="查询详细信息")
	@RequestMapping("/uuid/{jobId}")
	@ResponseBody
	@RequiresPermissions("sys:schedulejob:query")
	public GeneralReturnData uuid(@PathVariable("jobId") Long uuid){
		GeneralReturnData<ScheduleJob> result=new GeneralReturnData<>();
		//参数校验
		if(uuid==null){
			result.setInfo("参数错误");
			result.setStatus("0");
			return result;
		}
		//查找ScheduleJob
		ScheduleJob scheduleJob=scheduleJobService.selectByPrimaryKey(uuid);
		if(null==scheduleJob){
			result.setInfo("查询结果为空");
			result.setStatus("0");
			return result;
		}
		result.setData(scheduleJob);
		result.setStatus("1");
		return result;
	}
		
	/**
	 * 到ScheduleJob修改定时任务页面
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	@SysRes(name="定时任务页面访问")
	@RequiresPermissions("sys:schedulejob:page")
	public String goScheduleJobEditPage(){
		return "sys/schedulejob/schedulejob_edit";
	}
	
	
	/**
	 * 根据主键删除定时任务
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="删除")
	@RequiresPermissions("sys:schedulejob:delete")
	public  GeneralReturnData<String> doDelete(Long[] jobIds){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		if(jobIds==null||jobIds.length==0){
			result.setData("0");
			result.setInfo("参数为空,请重新输入");
			return result;
		}
		for (int i = 0; i < jobIds.length; i++) {
			JobKey jobKey = ScheduleUtils.getJobKey(jobIds[i]);
			if(jobKey==null){
				result.setData("0");
				result.setInfo("参数错误,jobId="+jobIds[i]+" 的任务无法获取");
				return result;
			}
		}
		//删除
		scheduleJobService.deleteBatch(jobIds);
		result.setInfo("删除成功");
		result.setStatus("1");
		return result;
	}
	
	/**
	 * 查询列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list")
	@ResponseBody
	@SysRes(name="查询")
	@RequiresPermissions("sys:schedulejob:query")
	public DataGridReturnData list(
		@RequestParam(name="pageNumber", defaultValue="1")	Integer pageNumber, 
		@RequestParam(name="pageSize", defaultValue="50")	Integer pageSize,
		ScheduleJob scheduleJob){
		DataGridReturnData r=R.dataGrid();
		r.setPageNumber(pageNumber);
		r.setPageSize(pageSize);
		Example example = new Example(ScheduleJob.class);
    	Criteria criteria=example.createCriteria();
    	if(StringUtils.isNotBlank(scheduleJob.getBeanName())){
    		criteria.andEqualTo("beanName", scheduleJob.getBeanName());
    	}
    	if(StringUtils.isNotBlank(scheduleJob.getCronExpression())){
    		criteria.andEqualTo("cronExpression", scheduleJob.getCronExpression());
    	}
    	if(scheduleJob.getJobId()!=null){
    		criteria.andEqualTo("jobId", scheduleJob.getJobId());
    	}
    	if(StringUtils.isNotBlank(scheduleJob.getMethodName())){
    		criteria.andEqualTo("methodName", scheduleJob.getMethodName());
    	}
    	if(StringUtils.isNotBlank(scheduleJob.getParams())){
    		criteria.andEqualTo("params", scheduleJob.getParams());
    	}
    	if(scheduleJob.getStatus()!=null){
    		criteria.andEqualTo("status", scheduleJob.getStatus());
    	}
		List<ScheduleJob> findList=scheduleJobService.selectByExampleAndRowBounds(example,  new RowBounds((pageNumber-1)*pageSize, pageSize));
		
		r.setRows(findList);
		r.setTotal(scheduleJobService.selectCountByExample(example));
		r.setStatus("1");
		r.setInfo("查询结果列表");
		R.wrapList(r,new WrapCommand() {
			@Override
			public void wrap(String fieldName, Object fildValue, Map<String, Object> wrapHashMap) {
				if(fieldName.equals("status")&&fildValue.equals(ScheduleJob.STATUS_ACTIVATE)){
					wrapHashMap.put("statusText", "激活");
				}
				if(fieldName.equals("status")&&fildValue.equals(ScheduleJob.STATUS_PAUSE)){
					wrapHashMap.put("statusText", "暂停");
				}
			}
		});
		return r;
	}
	
	/**
	 * 创建新ScheduleJob
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="新增")
	@RequiresPermissions("sys:schedulejob:add")
	public GeneralReturnData<String>  doCreateScheduleJob(ScheduleJob scheduleJob){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(null==scheduleJob){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		if(null==scheduleJob.getJobId()){
			result.setInfo("参数JobId为空");
			result.setStatus("0");
			return result;
		}
		ScheduleJob selectByPrimaryKey = scheduleJobService.selectByPrimaryKey(scheduleJob.getJobId());
		if(selectByPrimaryKey!=null){
			result.setInfo("参数JobId与已存在的job重复,请重新输入");
			result.setStatus("0");
			return result;
		}
		if(scheduleJob.getStatus()==null){
			//如果没有指定则默认为是暂停状态 启动状态需要手动指定
			scheduleJob.setStatus(ScheduleJob.STATUS_PAUSE);
		}
		// 保存
		scheduleJobService.save(scheduleJob);
		result.setInfo("新增成功");
		result.setStatus("1");
		return result;
	}
	/**
	 * 修改任务信息
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/updateschedulejob", method=RequestMethod.POST)
	@ResponseBody
	@SysRes(name="修改")
	@RequiresPermissions("sys:schedulejob:update")
	public GeneralReturnData<String> doUpdateScheduleJob(ScheduleJob scheduleJob){
		GeneralReturnData<String> result=new GeneralReturnData<>();
		//参数校验
		if(null==scheduleJob){
			result.setInfo("参数为空");
			result.setStatus("0");
			return result;
		}
		if(scheduleJob.getJobId()==null){
			result.setInfo("参数中主键错误");
			result.setStatus("0");
			return result;
		}
		//查找findScheduleJob
		ScheduleJob findScheduleJob=scheduleJobService.selectByPrimaryKey(scheduleJob.getJobId());
		if(null==findScheduleJob){
			result.setInfo("找不到对应的数据");
			result.setStatus("0");
			return result;
		}
		//修改
		findScheduleJob.setBeanName(scheduleJob.getBeanName());
		findScheduleJob.setParams(scheduleJob.getParams());
		findScheduleJob.setCronExpression(scheduleJob.getCronExpression());
		findScheduleJob.setMethodName(scheduleJob.getMethodName());
		findScheduleJob.setRemark(scheduleJob.getRemark());
		if(scheduleJob.getStatus()==null){
			//如果没有指定则默认为是暂停状态 启动状态需要手动指定
			findScheduleJob.setStatus(ScheduleJob.STATUS_PAUSE);
		}else{
			findScheduleJob.setStatus(scheduleJob.getStatus());
		}
		
		//查到结果 修改	
		// 保存修改
		scheduleJobService.update(findScheduleJob);
		result.setInfo("修改成功");
		result.setStatus("1");
		return result;
	}
	
	/**
	 * 立即执行任务
	 */
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedulejob:run")
	@ResponseBody
	public GeneralReturnData<String> run( Long[] jobIds){
		GeneralReturnData<String> data = R.data();
		if(jobIds==null||jobIds.length==0){
			data.setData("0");
			data.setInfo("参数为空,请重新输入");
			return data;
		}
		for (int i = 0; i < jobIds.length; i++) {
			JobKey jobKey = ScheduleUtils.getJobKey(jobIds[i]);
			if(jobKey==null){
				data.setData("0");
				data.setInfo("参数错误,jobId="+jobIds[i]+" 的任务无法获取");
				return data;
			}
		}
		//立即执行
		scheduleJobService.run(jobIds);
		
		data.setData("1");
		data.setInfo("立即执行成功");
		return data;
	}
	
	/**
	 * 暂停定时任务
	 */
	
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedulejob:pause")
	@ResponseBody
	public GeneralReturnData<String> pause( Long[] jobIds){
		
		GeneralReturnData<String> data = R.data();
		if(jobIds==null||jobIds.length==0){
			data.setData("0");
			data.setInfo("参数为空,请重新输入");
			return data;
		}
		for (int i = 0; i < jobIds.length; i++) {
			JobKey jobKey = ScheduleUtils.getJobKey(jobIds[i]);
			if(jobKey==null){
				data.setData("0");
				data.setInfo("参数错误,jobId="+jobIds[i]+" 的任务无法获取");
				return data;
			}
		}
		//暂停任务
		scheduleJobService.pause(jobIds);
		
		data.setData("1");
		data.setInfo("暂停定时任务成功");
		return data;
	}
	
	/**
	 * 恢复定时任务
	 */
	
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedulejob:resume")
	@ResponseBody
	public GeneralReturnData<String> resume( Long[] jobIds){
		GeneralReturnData<String> data = R.data();
		if(jobIds==null||jobIds.length==0){
			data.setData("0");
			data.setInfo("参数为空,请重新输入");
			return data;
		}
		for (int i = 0; i < jobIds.length; i++) {
			JobKey jobKey = ScheduleUtils.getJobKey(jobIds[i]);
			if(jobKey==null){
				data.setData("0");
				data.setInfo("参数错误,jobId="+jobIds[i]+" 的任务无法获取");
				return data;
			}
		}
		//恢复任务
		scheduleJobService.resume(jobIds);
		data.setData("1");
		data.setInfo("恢复定时任务成功");
		return data;
	}


}

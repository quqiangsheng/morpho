package com.max256.morpho.common.scheduler.quartz;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.max256.morpho.common.entity.ScheduleJob;
import com.max256.morpho.common.entity.ScheduleJobLog;
import com.max256.morpho.common.util.SpringUtils;
import com.max256.morpho.common.util.UUIDUtils;
import com.max256.morpho.sys.service.ScheduleJobLogService;

/**
 * 定时任务
 * 任务执行和保存日志
 */
public class ScheduleJobBean extends QuartzJobBean {
	//日志
	private Logger logger = LoggerFactory.getLogger(getClass());
	//线程执行器框架
	private ExecutorService service = Executors.newSingleThreadExecutor(); 
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	//ScheduleJob
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);
        
        //获取spring bean 记录日志
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringUtils.getBean("scheduleJobLogService");
        
        //数据库保存执行记录
        ScheduleJobLog log = new ScheduleJobLog();
        log.setJobId(scheduleJob.getJobId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setCreateTime(new Date());
        
        //任务开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            //执行任务
        	if(logger.isInfoEnabled()){
        		logger.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
        	}
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
            		scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            
			future.get();
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes(times);
			//任务状态      1：成功0： 失败 
			log.setStatus(1);
			if(logger.isInfoEnabled()){
				logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
			}
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);	
			}
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes(times);
			//任务状态      1：成功0： 失败 
			log.setStatus(0);
			log.setErrorInfo(StringUtils.substring(e.toString(), 0, 500));
		}finally {
			log.setLogId(UUIDUtils.get32UUID());
			scheduleJobLogService.insert(log);
		}
    }
}

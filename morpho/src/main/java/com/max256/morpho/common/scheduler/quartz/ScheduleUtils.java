package com.max256.morpho.common.scheduler.quartz;


import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.max256.morpho.common.entity.ScheduleJob;
import com.max256.morpho.common.exception.BusinessException;

/**
 * 定时任务工具类
 * 对quartz API的封装 请千万不要直接访问quartz的表数据!
 */
public class ScheduleUtils {
    private final static String JOB_NAME = "TASK_";
    
    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }
    
    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
        	throw new BusinessException("获取定时任务CronTrigger出现异常 "+e.getMessage());
        }
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
        try {
        	//构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJobBean.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		//withMisfireHandlingInstructionDoNothing该策略应该根据您的实际情况去判断
            		.withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(scheduleBuilder).build();

            //往JobDataMap中放入参数，运行时的方法可以获取key为ScheduleJob.JOB_PARAM_KEY
            jobDetail.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY, scheduleJob);
            //调度器调度任务  调度器=任务+触发器
            scheduler.scheduleJob(jobDetail, trigger);
            //暂停任务
            if(scheduleJob.getStatus().equals(ScheduleJob.STATUS_PAUSE)){
            	pauseJob(scheduler, scheduleJob.getJobId());
            }else if(scheduleJob.getStatus().equals(ScheduleJob.STATUS_ACTIVATE)){
            	//正常执行任务
            	
            }
        } catch (SchedulerException e) {
        	throw new BusinessException("创建定时任务失败 "+e.getMessage());
        }
    }
    
    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            //用新的cron表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		//withMisfireHandlingInstructionDoNothing该策略应该根据您的实际情况去判断
            		.withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());
            
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            
            //参数
            trigger.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY, scheduleJob);
            //重新调度
            scheduler.rescheduleJob(triggerKey, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus().equals(ScheduleJob.STATUS_PAUSE)){
            	pauseJob(scheduler, scheduleJob.getJobId());
            }else if(scheduleJob.getStatus().equals(ScheduleJob.STATUS_ACTIVATE)){
            	//正常执行任务
            	
            }
            
        } catch (SchedulerException e) {
        	throw new BusinessException("更新定时任务失败 "+e.getMessage());
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, ScheduleJob scheduleJob) {
        try {
        	//参数
        	JobDataMap dataMap = new JobDataMap();
        	dataMap.put(ScheduleJob.JOB_PARAM_KEY, scheduleJob);
        	//立即执行Job  忽略cron表达式只执行一次
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
        } catch (SchedulerException e) {
        	throw new BusinessException("立即执行定时任务失败 "+e.getMessage());
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
        	throw new BusinessException("暂停定时任务失败"+e.getMessage());
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
        	throw new BusinessException("暂停定时任务失败 "+e.getMessage());
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
        	throw new BusinessException("删除定时任务失败 "+e.getMessage());
        }
    }
}

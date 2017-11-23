package com.max256.morpho.sys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.max256.morpho.common.entity.ScheduleJob;
import com.max256.morpho.common.scheduler.quartz.ScheduleUtils;
import com.max256.morpho.common.service.impl.BaseServiceImpl;
import com.max256.morpho.sys.service.ScheduleJobService;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJob>  implements ScheduleJobService {
	@Autowired
    private Scheduler scheduler;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJob> scheduleJobList = selectAll();
		for(ScheduleJob scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}
	



	@Override
	@Transactional
	public void save(ScheduleJob scheduleJob) {
		scheduleJob.setCreateTime(new Date());
        super.insert(scheduleJob);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	@Override
	@Transactional
	public void update(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);     
        super.updateByPrimaryKey(scheduleJob);
    }

	@Override
	@Transactional
    public void deleteBatch(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    		//删除数据
    		super.deleteByPrimaryKey(jobId);
    	}
	}

	@Override
	@Transactional
    public int updateBatch(Long[] jobIds, int status){
		for (int i = 0; i < jobIds.length; i++) {
			ScheduleJob selectByPrimaryKey = selectByPrimaryKey(jobIds[i]);
			selectByPrimaryKey.setStatus(status);
			update(selectByPrimaryKey);
		}
    	return jobIds.length;
    }
    
	@Override
	@Transactional
    public void run(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.run(scheduler, queryObject(jobId));
    	}
    }

	@Override
	@Transactional
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
    	updateBatch(jobIds, ScheduleJob.STATUS_PAUSE);
    }

	@Override
	@Transactional
    public void resume(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds,  ScheduleJob.STATUS_ACTIVATE);
    }




	@Override
	public ScheduleJob queryObject(Long jobId) {
		return selectByPrimaryKey(jobId);
	}





    
}

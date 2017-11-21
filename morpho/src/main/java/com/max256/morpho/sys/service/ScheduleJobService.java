package com.max256.morpho.sys.service;

import com.max256.morpho.common.entity.ScheduleJob;
import com.max256.morpho.common.service.BaseService;

/**
 * 定时任务
 */
public interface ScheduleJobService extends BaseService<ScheduleJob> {

	/**
	 * 根据ID，查询定时任务
	 */
	ScheduleJob queryObject(Long jobId);
	
	/**
	 * 保存定时任务
	 */
	void save(ScheduleJob scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJob scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] jobIds);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);
}

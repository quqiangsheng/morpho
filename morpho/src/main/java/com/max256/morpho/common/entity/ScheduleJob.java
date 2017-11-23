package com.max256.morpho.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 定时器
 * 
 */
@Entity
@Table(name="schedule_job")
public class ScheduleJob implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Transient
	public static final Integer STATUS_ACTIVATE=1;//激活
	@Transient
	public static final Integer STATUS_PAUSE=0;//暂停
	
	/**
	 * 任务调度参数key
	 */
	@Transient
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
	
	/**
	 * 任务id
	 */
	@Id
	@Column(name="job_id")
	private Long jobId;

	/**
	 * spring bean名称
	 */
	@Column(name="bean_name")
	private String beanName;
	
	/**
	 * 方法名
	 */
	@Column(name="method_name")
	private String methodName;
	
	/**
	 * 参数
	 */
	@Column(name="params")
	private String params;
	
	/**
	 * cron表达式
	 */
	@Column(name="cron_expression")
	private String cronExpression;

	/**
	 * 任务状态
	 */
	@Column(name="status")
	private Integer status;

	/**
	 * 备注
	 */
	@Column(name="remark")
	private String remark;

	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Date createTime;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}

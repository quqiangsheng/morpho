package com.max256.morpho.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 定时执行日志
 */
@Entity
@Table(name="schedule_job_log")
public class ScheduleJobLog implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Transient
	public static final Integer STATUS_SUCCESS=1;//成功
	@Transient
	public static final Integer STATUS_FAIL=0;//失败
	
	/**
	 * 日志id
	 */
	@Id
	@Column(name="log_id")
	private String logId;
	
	/**
	 * 任务id
	 */
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
	 * 任务状态     1：成功  0： 失败
	 */
	@Column(name="status")
	private Integer status;
	
	/**
	 * 失败信息
	 */
	@Column(name="error_info")
	private String errorInfo;
	
	/**
	 * 耗时(单位：毫秒)
	 */
	@Column(name="times")
	private Long times;
	
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Date createTime;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}

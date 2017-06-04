package com.max256.morpho.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 系统日志表
 * 
 * @author fbf
 * 
 */
@Entity
@Table(name="sys_log")
public class SysLog implements java.io.Serializable {
	@Transient
	private static final long serialVersionUID = -7961551963263558739L;
	//主键
	@Id
	@Column(name="uuid")
	private String uuid;
	//日志编号
	@Column(name="log_id")
	private String logId;
	// 日志信息
	@Column(name="log_info")
	private String logInfo;
	// 日志当事人
	@Column(name="log_user")
	private String logUser;
	// 日志发生时间
	@Column(name="log_time")
	private Timestamp logTime;
	@Column(name="log_ip")
	private String logIp;
	// 访问浏览器
	@Column(name="log_agent")
	private String logAgent;

	public SysLog() {
		super();
	}

	public SysLog(String uuid, String logId, String logInfo, String logUser,
			Timestamp logTime, String logIp, String logAgent) {
		super();
		this.uuid = uuid;
		this.logId = logId;
		this.logInfo = logInfo;
		this.logUser = logUser;
		this.logTime = logTime;
		this.logIp = logIp;
		this.logAgent = logAgent;
	}


	public String getLogAgent() {
		return logAgent;
	}


	public String getLogId() {
		return logId;
	}


	public String getLogInfo() {
		return logInfo;
	}


	public String getLogIp() {
		return logIp;
	}


	public Timestamp getLogTime() {
		return logTime;
	}


	public String getLogUser() {
		return logUser;
	}

	public String getUuid() {
		return uuid;
	}

	public void setLogAgent(String logAgent) {
		this.logAgent = logAgent;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "SysLog [logId=" + logId + ", logInfo=" + logInfo + ", logUser="
				+ logUser + ", logTime=" + logTime + ", logIp=" + logIp
				+ ", logAgent=" + logAgent + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logAgent == null) ? 0 : logAgent.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result + ((logInfo == null) ? 0 : logInfo.hashCode());
		result = prime * result + ((logIp == null) ? 0 : logIp.hashCode());
		result = prime * result + ((logTime == null) ? 0 : logTime.hashCode());
		result = prime * result + ((logUser == null) ? 0 : logUser.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysLog other = (SysLog) obj;
		if (logAgent == null) {
			if (other.logAgent != null)
				return false;
		} else if (!logAgent.equals(other.logAgent))
			return false;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		if (logInfo == null) {
			if (other.logInfo != null)
				return false;
		} else if (!logInfo.equals(other.logInfo))
			return false;
		if (logIp == null) {
			if (other.logIp != null)
				return false;
		} else if (!logIp.equals(other.logIp))
			return false;
		if (logTime == null) {
			if (other.logTime != null)
				return false;
		} else if (!logTime.equals(other.logTime))
			return false;
		if (logUser == null) {
			if (other.logUser != null)
				return false;
		} else if (!logUser.equals(other.logUser))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	

}
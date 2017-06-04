package com.max256.morpho.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * HttpSession和SysUser的视图
 * 
 * @author fbf
 *
 */
public class HttpSession extends SysUser implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * Session Id
	 */
	private String sessionId;
	/*
	 * Session Host
	 */
	private String host;
	/*
	 * Session创建时间
	 */
	private Date startTime;
	/*
	 * Session最后交互时间
	 */
	private Date lastAccess;
	/*
	 * Session timeout
	 */
	private long timeout;
	/*
	 * session 是否踢出
	 * true:在线；false:已被剔出退出
	 */
	private Boolean sessionStatus = Boolean.TRUE;

	public HttpSession() {
	}


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public Boolean isSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(Boolean sessionStatus) {
		this.sessionStatus = sessionStatus;
	}


	@Override
	public String toString() {
		return "HttpSession [sessionId=" + sessionId + ", host=" + host + ", startTime=" + startTime + ", lastAccess="
				+ lastAccess + ", timeout=" + timeout + ", sessionStatus=" + sessionStatus + ", getUuid()=" + getUuid()
				+ ", getUserId()=" + getUserId() + ", getUserName()=" + getUserName() + ", getUserNickname()="
				+ getUserNickname() + ", getSysRoleIds()=" + getSysRoleIds() + ", getSysOrganizationId()="
				+ getSysOrganizationId() + ", getIsValid()=" + getIsValid() + ", getRegisterTime()=" + getRegisterTime()
				+ "]";
	}
	
}

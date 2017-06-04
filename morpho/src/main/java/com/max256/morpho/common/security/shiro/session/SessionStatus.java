package com.max256.morpho.common.security.shiro.session;

import java.io.Serializable;

/**
 * 
 *  SessionStatus
 *  http session 状态
 */
public class SessionStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//是否在线(true:在线；false:已被剔出退出)
	private Boolean status = true;

	
	public Boolean isOnline(){
		return status;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}

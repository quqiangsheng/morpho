package com.max256.morpho.sys.service;

import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.max256.morpho.common.entity.HttpSession;


/**
 * HttpSessionService
 * 管理http session的接口
 * @author fbf
 *
 */
public interface HttpSessionService {

	/**
	 * 获取系统所有session的绑定用户信息的视图
	 * 
	 */
	public  List<HttpSession> getAllUser();
	
	
	public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(String...userIds);
	
	/**
	 * 
	 * 根据sessionId获得绑定用户信息的session的视图
	 * @param session
	 */
	public HttpSession getSession(String sessionId);
	
	/**
	 * 
	 * 根据session获得绑定用户信息的session的视图
	 * @param session
	 */
	public HttpSession getSessionUser(Session session);
	
	/**
	 * 踢出用户
	 * @param sessionId
	 */
	public boolean kickoutUser(String sessionId);
	
	
}

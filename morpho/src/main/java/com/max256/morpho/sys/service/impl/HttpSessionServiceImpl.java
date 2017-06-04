package com.max256.morpho.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.max256.morpho.common.entity.HttpSession;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.exception.BusinessException;
import com.max256.morpho.common.security.shiro.session.SessionStatus;
import com.max256.morpho.common.security.shiro.session.ShiroSessionDAO;
import com.max256.morpho.sys.service.HttpSessionService;


/**
 * 管理Session
 */
@Service("httpSessionService")
public class HttpSessionServiceImpl implements HttpSessionService {

	private static final Logger logger = LoggerFactory.getLogger(HttpSessionServiceImpl.class);

	/**
	 * session status
	 */
	public static final String SESSION_STATUS = "session_status";

	@Autowired
	ShiroSessionDAO shiroSessionDao;

	/**
	 * 获取所有的有效Session用户
	 * 
	 * @return
	 */
	public List<HttpSession> getAllUser() {
		// 获取所有session
		Collection<Session> sessions = shiroSessionDao.getActiveSessions();
		//准备返回数据
		List<HttpSession> list = new ArrayList<HttpSession>();
		//遍历shiro session
		for (Session session : sessions) {
			HttpSession hs = getSessionUser(session);
			if (null != hs) {
				list.add(hs);
			}
		}
		return list;
	}

	/**
	 * 根据ID查询 SimplePrincipalCollection
	 * 
	 * @param userIds
	 *            用户ID
	 * @return
	 */
	public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(
			String... userIds) {
		// 把userIds 转成Set，好判断
		Set<String> idset = new TreeSet<String>(Arrays.asList(userIds));
		// 获取所有session
		Collection<Session> sessions = shiroSessionDao.getActiveSessions();
		// 定义返回
		List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
		for (Session session : sessions) {
			// 获取SimplePrincipalCollection
			Object obj = session
					.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if (null != obj && obj instanceof SimplePrincipalCollection) {
				// 强转
				SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
				// 判断用户，匹配用户ID。
				obj = spc.getPrimaryPrincipal();
				if (null != obj && obj instanceof SysUser) {
					SysUser user = (SysUser) obj;
					// 比较用户ID，符合即加入集合
					if (idset.contains(user.getUserId())) {
						list.add(spc);
					}
				}
			}
		}
		return list;
	}

	
	public HttpSession getSession(String sessionId) {
		Session session = shiroSessionDao.readSession(sessionId);
		HttpSession hs = getSessionUser(session);
		return hs;
	}

	
	public HttpSession getSessionUser(Session session) {
		// 获取session登录信息
		Object obj = session
				.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if (null == obj) {
			return null;
		}
		// 确保是 SimplePrincipalCollection对象。
		if (obj instanceof SimplePrincipalCollection) {
			SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
			/**
			 * 获取用户登录的，@link SampleRealm.doGetAuthenticationInfo(...)方法中 return
			 * new SimpleAuthenticationInfo(user,user.getPswd(),
			 * getName());的user 对象。
			 */
			obj = spc.getPrimaryPrincipal();
			if (null != obj && obj instanceof SysUser) {
				SysUser findUser=(SysUser) obj;
				// 存储session + user 综合信息
				HttpSession userBo = new HttpSession();
				userBo.setEmail(findUser.getEmail());
				userBo.setIsValid(findUser.getIsValid());
				userBo.setRegisterTime(findUser.getRegisterTime());
				userBo.setSysOrganizationId(findUser.getSysOrganizationId());
				userBo.setSysRoleIds(findUser.getSysRoleIds());
				userBo.setUserId(findUser.getUserId());
				userBo.setUserName(findUser.getUserId());
				userBo.setUserNickname(findUser.getUserNickname());
				userBo.setUuid(findUser.getUuid());//此uuid是用户的uuid不是session的id
				// 最后一次和系统交互的时间
				userBo.setLastAccess(session.getLastAccessTime());
				// 主机的ip地址
				userBo.setHost(session.getHost());
				// session ID
				userBo.setSessionId(session.getId().toString());
				// 回话到期 ttl(ms)
				userBo.setTimeout(session.getTimeout());
				// session创建时间
				userBo.setStartTime(session.getStartTimestamp());
				// 是否踢出
				SessionStatus sessionStatus = (SessionStatus) session
						.getAttribute(SESSION_STATUS);
				boolean status = Boolean.TRUE;
				if (null != sessionStatus) {
					status = sessionStatus.getStatus();
				}
				userBo.setSessionStatus(status);
				return userBo;
			}
		}
		return null;
	}

	/**
	 * 根据sessionid踢人
	 * @param sessionId支持多哥sessionId 用,分割 如果是单个直接输入session
	 */
	public boolean kickoutUser(String sessionId) {
		boolean flag = true;
		try {
			String[] sessionIds = null;
			if (sessionId.indexOf(",") == -1) {
				//单个session
				sessionIds = new String[] { sessionId };
			} else {
				//多session
				sessionIds = sessionId.split(",");
			}
			for (String id : sessionIds) {
				// 获取用户Session
				Session session = shiroSessionDao.readSession(id);
				SessionStatus sessionStatus = new SessionStatus();
				// 是否踢出 true:有效，false：踢出。
				sessionStatus.setStatus(Boolean.FALSE);
				// 更新Session
				session.setAttribute(SESSION_STATUS, sessionStatus);
				shiroSessionDao.update(session);
			}
		} catch (Exception e) {
			flag = false;
			logger.error("踢出用户sessionId:{} 时发生异常:{}", sessionId, e);
			throw new BusinessException("踢出用户sessionId:{"+sessionId+"} 时发生异常");
		}
		return flag;
	}

}

package com.max256.morpho.common.security.shiro.session.redis;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.max256.morpho.common.cache.redis.RedisManager;
import com.max256.morpho.common.security.shiro.session.SessionStatus;
import com.max256.morpho.common.security.shiro.session.ShiroSessionRepository;
import com.max256.morpho.common.util.ObjectStreamSerializeUtils;
import com.max256.morpho.sys.service.impl.HttpSessionServiceImpl;



/**
 * 
 *  RedisShiroSessionRepository
 *  redis 实现 Session 管理
 */
public class RedisShiroSessionRepository implements ShiroSessionRepository {
   
	private static final Logger logger = LoggerFactory.getLogger(RedisShiroSessionRepository.class);
	
	public static final String REDIS_SHIRO_SESSION = "shiro_session:";
    //这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
    public static final String REDIS_SHIRO_ALL = "*shiro_session:*";
    /*
     * session存储过期时间
     */
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    /*
     * 使用的redis分区
     */
    private static final int DB_INDEX = 0;

    private RedisManager redisManager;
    

    public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	@Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            byte[] key = generateRedisSessionKey(session.getId()).getBytes();
            
            //不存在才添加。
            if(null == session.getAttribute(HttpSessionServiceImpl.SESSION_STATUS)){
            	//session状态
            	SessionStatus sessionStatus = new SessionStatus();
            	session.setAttribute(HttpSessionServiceImpl.SESSION_STATUS, sessionStatus);
            }
            
            byte[] value = ObjectStreamSerializeUtils.serialize(session);
            long sessionTimeOut = session.getTimeout() / 1000;
            Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
            getRedisManager().saveValueByKey(DB_INDEX, key, value, expireTime.intValue());
        } catch (Exception e) {
        	logger.warn("save session error,id:"+session.getId(), e);
        }
    }

    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
        	getRedisManager().deleteByKey(DB_INDEX,generateRedisSessionKey(id).getBytes());
        } catch (Exception e) {
        	logger.warn("删除session出现异常，id:"+id ,e);
        }
    }

   
	@Override
    public Session getSession(Serializable id) {
        if (id == null)
        	 throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getRedisManager().getValueByKey(DB_INDEX, generateRedisSessionKey(id).getBytes());
            session = (Session)ObjectStreamSerializeUtils.deserialize(value);
        } catch (Exception e) {
        	logger.debug("获取session异常，id:"+id, e);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
    	Collection<Session> sessions = null;
		try {
			sessions = getRedisManager().AllSession(DB_INDEX,REDIS_SHIRO_ALL);
		} catch (Exception e) {
			logger.warn("获取全部session异常", e);
		}
        return sessions;
    }

    private String generateRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }
}

package com.max256.morpho.common.web.webservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * webservice加密拦截器
 *
 */
public class WssServerAuthHandler implements CallbackHandler {
	//日志
	private static final Logger logger = LoggerFactory.getLogger(WssServerAuthHandler.class);
	//密码
	private Map<String, String> passwords = new HashMap<String, String>();


	public WssServerAuthHandler() {
		//初始化密码
		passwords.put("admin", "admin");
	}


	/**
	 * Here, we attempt to get the password from the private
	 * alias/passwords map.
	 */
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			if (callback instanceof WSPasswordCallback) {
				WSPasswordCallback wSPasswordCallback = (WSPasswordCallback) callback;
				String id = wSPasswordCallback.getIdentifier();
				if(logger.isInfoEnabled()){
					logger.info("identifier: " + id);
				}
				if (passwords.containsKey(id)) {
					String pas = passwords.get(id);
					//					if (key == null) {
					//						throw new SecurityException("null password.");
					//					} else if (!pas.equals(key)) {
					//						throw new SecurityException("Incorrect password.");
					//					}
					/**
					 * 此处应该这样做：
					 * 1. 查询数据库，得到数据库中该用户名对应密码
					 * 2. 设置密码，wss4j会自动将你设置的密码与客户端传递的密码进行匹配
					 * 3. 如果相同，则放行，否则返回权限不足信息
					 */
					wSPasswordCallback.setPassword(pas);
				} else {
					throw new SecurityException("Invalid user.");
				}
			}
		}

	}


	/**
	 * Add an alias/password pair to the callback mechanism.
	 */
	public void setAliasPassword(String alias, String password) {
		passwords.put(alias, password);
	}
}

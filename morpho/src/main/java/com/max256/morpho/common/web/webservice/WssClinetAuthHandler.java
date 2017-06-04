package com.max256.morpho.common.web.webservice;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.log4j.Logger;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class WssClinetAuthHandler implements CallbackHandler {
	private static final Logger logger = Logger.getLogger(WssServerAuthHandler.class);


	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			if (callback instanceof WSPasswordCallback) {
				WSPasswordCallback wSPasswordCallback = (WSPasswordCallback) callback;
				String id = wSPasswordCallback.getIdentifier();
				logger.info("identifier: " + id);
				wSPasswordCallback.setPassword("admin");
			}
		}
	}
}
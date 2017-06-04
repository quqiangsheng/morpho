/*package com.max256.morpho.common.util;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

*//**
 * apache cxf webservice client工具类
 * 
 * @author fbf
 * @since 2015年12月25日 下午5:01:52
 * @version V1.0
 * 
 *//*
public class WebServiceClientUtils {

	*//**
	 * 设置客户端超时时间
	 * 
	 * @param proxy
	 * @param connectionTimeout
	 *            ConnectionTimeout ================== Specifies the amount of
	 *            time, in milliseconds, that the client will attempt to
	 *            establish a connection before it times out. The default is
	 *            30000 (30 seconds). 0 specifies that the client will continue
	 *            to attempt to open a connection indefinitely.
	 * @param receiveTimeout
	 * 
	 *            ==================Specifies the amount of time, in
	 *            milliseconds, that the client will wait for a response before
	 *            it times out. The default is 60000. 0 specifies that the
	 *            client will wait indefinitely.
	 *//*
	public static void setClientTimeout(Object proxy, long connectionTimeout,
			long receiveTimeout) {

		Client client = ClientProxy.getClient(proxy);
		if (client != null) {
			HTTPConduit conduit = (HTTPConduit) client.getConduit();
			HTTPClientPolicy policy = new HTTPClientPolicy();

			
			 * ConnectionTimeout ================== Specifies the amount of
			 * time, in milliseconds, that the client will attempt to establish
			 * a connection before it times out. The default is 30000 (30
			 * seconds). 0 specifies that the client will continue to attempt to
			 * open a connection indefinitely.
			 
			
			 * ReceiveTimeout ==================Specifies the amount of time, in
			 * milliseconds, that the client will wait for a response before it
			 * times out. The default is 60000. 0 specifies that the client will
			 * wait indefinitely.
			 
			
			 * Chunking is by default enabled in CXF webservices so we are
			 * disabling it.
			 

			policy.setConnectionTimeout(connectionTimeout);
			policy.setReceiveTimeout(receiveTimeout);
			policy.setAllowChunking(false);
			conduit.setClient(policy);
		}
	}

	public WebServiceClientUtils() {
	}

}
*/
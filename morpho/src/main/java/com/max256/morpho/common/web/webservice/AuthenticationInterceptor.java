package com.max256.morpho.common.web.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 认证拦截器
 *
 */
public class AuthenticationInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	//日志
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	private Map<String, String> skeys = new HashMap<String, String>();
	/*SAAJ(SOAP with Attachments API for JAVA) SAAJ是在松散耦合软件系统中利用SOAP协议实现的基于XML消息传递的API规范*/
	private SAAJInInterceptor saajIn = new SAAJInInterceptor();


	public AuthenticationInterceptor() {
		// 指定该拦截器在哪个阶段被激发
		super(Phase.PRE_INVOKE);
		skeys.put("86527d8c4-ww90e-481b-a15e-4da667d66584", "86527d8c4-ww90e-481b-a15e-4da667d66584");
		skeys.put("v1ee9987-109e-46vd-8966-0qc703721c70", "v1ee9987-109e-46vd-8966-0qc703721c70");
	}


	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		
		List<Header> headers = message.getHeaders();
		//debug记录日志
		if (headers != null&&logger.isDebugEnabled()) {
			logger.debug(headers.toString());
		}
		//得到内容
		SOAPMessage msg = message.getContent(SOAPMessage.class);
		if (msg == null) {
			saajIn.handleMessage(message);
			msg = message.getContent(SOAPMessage.class);
		}
		// 获取参数  
		List<?> content = message.getContent(List.class);
		if (content != null && content.size() > 0) {
			//得到秘钥
			Object skey = content.get(0);
			//秘钥匹配
			String key = skeys.get(skey);
			if (StringUtils.isBlank(key)) {
				//匹配失败
				if (logger.isDebugEnabled()) {
					logger.debug("Invalid skey.");
				}
				throw new Fault(new SecurityException("Invalid skey."));
			} else {
				//匹配成功
				if (logger.isDebugEnabled()) {
					logger.debug(content.toString());
				}
			}
		}
	}
}

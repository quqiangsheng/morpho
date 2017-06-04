package com.max256.morpho.common.web.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

public class IpServiceClientDemo {
	public static void main(String[] args) {
		// 以下和服务端配置类似，不对，应该说服务端和这里的安全验证配置一致
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, "admin");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);

		// 指定在调用远程ws之前触发的回调函数WsClinetAuthHandler，其实类似于一个拦截器
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, WssClinetAuthHandler.class.getName());
		ArrayList<SoapInterceptor> list = new ArrayList<SoapInterceptor>();

		// 添加cxf安全验证拦截器，必须
		// 认证
		list.add(new SAAJOutInterceptor());
		//加密
		list.add(new WSS4JOutInterceptor(outProps));
		//客户端代理工厂bean
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		// WebServiceSample服务端接口实现类，这里并不是直接把服务端的类copy过来，具体请参考http://learning.iteye.com/blog/1333223
		factory.setServiceClass(IpService.class);
		// 设置ws访问地址
		factory.setAddress("http://127.0.0.1:8080/morpho/webservice/ipService");

		//注入拦截器，用于加密安全验证信息
		factory.getOutInterceptors().addAll(list);
		//传剑客户端代理
		IpService service = (IpService) factory.create();
		//远程调用
		String response = service.getIpInfo("111.111.111.111");
		//输出结果
		System.out.println(response);
	}
}
package com.max256.morpho.common.web.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Ip服务
 * 
 */
@WebService(name = "IpService", targetNamespace = "http://www.max256.com/", portName = "IpServicePort", serviceName = "IpService")
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public interface IpService {

	@WebMethod
	public String getIpInfo(@WebParam(name = "ip") String ip);

}

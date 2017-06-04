package com.max256.morpho.common.security.shiro.bind.method;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.security.encrypt.Message;
import com.max256.morpho.common.security.encrypt.ServerUtils;
import com.max256.morpho.common.security.shiro.bind.annotation.RequestMessage;
import com.max256.morpho.common.security.shiro.session.ShiroSessionDAO;

/**
 * 用于绑定@RequestMessage的方法参数解析器
 * 
 * @author fbf
 */
public class RequestMessageMethodArgumentResolver implements
		HandlerMethodArgumentResolver {
	
	@Autowired
	ShiroSessionDAO shiroSessionDao;

	public RequestMessageMethodArgumentResolver() {
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		//方法参数 需要强转的类型
		Class<?> clazz = parameter.getParameterType();
		HttpServletRequest request=(HttpServletRequest) webRequest.getNativeRequest();
		//得到请求数据
		Message requestMessage=(Message) request.getAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_KEY);
		//得到session
		Session session = shiroSessionDao.readSession(requestMessage.getToken());
		// 服务端私钥pkcs#8格式
		String serverPrivateKeyString = (String)session.getAttribute(Constants.SERVER_PRIVATE_KEY);
		// 公钥编码x.509格式
		String clientPublicKeyString = (String)session.getAttribute(Constants.CLIENT_PUBLIC_KEY);
		// 服务器处理 解密
		Object decryptObject = null ;
		try {
			decryptObject= ServerUtils.decryptObject(
					requestMessage,
					clazz, 
					clientPublicKeyString,
					serverPrivateKeyString);
		} catch (Exception e) {
			
		}

		return decryptObject;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(RequestMessage.class)) {
			return true;
		}
		return false;
	}
}

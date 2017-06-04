package com.max256.morpho.common.security.shiro.filter;

import java.io.BufferedReader;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.security.encrypt.Message;
import com.max256.morpho.common.security.encrypt.ServerUtils;
import com.max256.morpho.common.security.shiro.session.ShiroSessionDAO;
import com.max256.morpho.common.util.NumberUtils;

/**
 * 加解密拦截器
 * 同时提供报文转换与验签
 * 与com.max256.morpho.common.security.encrypt包下的功能配合使用
 * @author fbf
 *
 */
public class EncryptFilter extends AccessControlFilter {
	
	// 日志
	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ShiroSessionDAO shiroSessionDao;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(!((HttpServletRequest)request).getMethod().equals("POST")){
			//非post请求
			//拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求方式错误,请使用HTTP POST方式");
			return false;
		}
		//判断该session是否存在 存在的话 提取sessionid为token号提取服务器私钥 服务器公钥 客户端公钥 进行验签和解密 和反序列化 并放入request域中
		//对request进行包装 缓存
		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
		BufferedReader br=null;
		try {
			 br=wrappedRequest.getReader();
		} catch (Exception e) {
			
		}
		if(br==null){
			//没有内容 拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求内容错误,消息体为空");
			return false;
		}
		String str = ""; 
		StringBuilder requestBody=new StringBuilder();
		while((str = br.readLine()) != null){
			requestBody.append(str);
		}
		if(requestBody.length()==0){
			//没有内容 拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求内容错误,消息体为空");
			return false;
		}
		//有内容 进行解析
		Message requestMessage=null;
		try {
			//注意stringBuilder toString
			requestMessage=JSONObject.parseObject(requestBody.toString(), Message.class);
		} catch (Exception e) {
			//没有内容 拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求内容格式不正确,无法解析");
			return false;
		}
		//无法解析
		if(StringUtils.isBlank(requestMessage.getData())
				||StringUtils.isBlank(requestMessage.getEncryptkey())
				||StringUtils.isBlank(requestMessage.getToken())
				||StringUtils.isBlank(requestMessage.getStatus())
				||StringUtils.isBlank(requestMessage.getTimestamp())
				){
			//报文不完整 拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求内容格式不正确,有缺项,或者部分必须有的字段为空");
			return false;
		}
		if(!requestMessage.getStatus().equals("1")){
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求内容中状态不正确,必须为1");
			//状态不正确 拒绝访问
			return false;
		}
		//解析成功Token前面判断过了肯定不为null
		String requestToken=requestMessage.getToken();
		//读取session
		Session session = null;
		try {
			session= shiroSessionDao.readSession(requestToken);
		} catch (Exception e) {
			
		}
		if(session==null){
			//根据token取不到session token无效
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求内容错误,无效的token");
			return false;
		}
		//session有效
		//服务端私钥
		String serverPrivateKeyString = (String)session.getAttribute(Constants.SERVER_PRIVATE_KEY);
		//客户端公钥
		String clientPublicKeyString = (String)session.getAttribute(Constants.CLIENT_PUBLIC_KEY);
		// 解密客户端传过来的数据 先验签
		if (!ServerUtils.checkDecryptAndSign(requestMessage, clientPublicKeyString, serverPrivateKeyString)) {
			//验签不通过 拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求内容无法通过签名验证,可能是被篡改,或者请求加密时秘钥不正确");
			return false;
		}
		//根据时间戳和当前时间对比 超过5s默认为是同一条消息的重放攻击 拒绝 如果比上次访问时间还要早 说明是非法消息
		String startTime=requestMessage.getTimestamp();//该单位是毫微妙
		if(!NumberUtils.isNumber(startTime)){
			//时间戳格式错误 拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "时间戳格式错误,单位微秒");
			return false;
		}
		Long startTimeLong=Long.parseLong(startTime);
		//更严格的时间对比策略
		Long nowTime=new Date().getTime();//当前时间  服务器端时间
		Long sessionStartTime=session.getStartTimestamp().getTime();//因为session存在 所以肯定不会为null
		//请求时间靠后(考虑到客户端可能与服务器有时间不同步 最多容纳5分钟)
		if(startTimeLong>(nowTime+300000L)){
			//时间戳格式错误 拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求无效时间戳错误");
			return false;
		}
		if(startTimeLong<sessionStartTime){
			//时间戳格式错误 拒绝访问
			request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY, "请求无效时间戳错误");
			return false;
		}
		
		//验签通过 可以访问 吧requestMessage放入request域中
		request.setAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_KEY, requestMessage);
		request.setAttribute(Constants.GET_SESSION_BY_TOKEN, session);
		//放行 controller中可以取到
		return true;
		
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Message responseMessage=new Message();
		responseMessage.setStatus("0");//失败
		String info=(String) request.getAttribute(Constants.REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY);
		if(info!=null){
			responseMessage.setInfo(info);//失败原因
		}else{
			responseMessage.setInfo("");//失败原因
		}
		response.getWriter().write(JSONObject.toJSONString(responseMessage,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue));
		// 不再进行后续 直接返回错误信息
		return false;
	}

	
}

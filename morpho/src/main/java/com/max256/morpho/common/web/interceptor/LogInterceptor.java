package com.max256.morpho.common.web.interceptor;

import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.max256.morpho.common.util.CheckMobile;


/**
 * 调试日志拦截器 调试时使用
 * @author fbf
 *
 */
public class LogInterceptor implements HandlerInterceptor {
	
	// 日志
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获得HTTP头信息
	 * 
	 * @param request
	 * @return
	 */
	private static String getRequestHeaderNames(HttpServletRequest request) {
		StringBuilder params = new StringBuilder();
		Enumeration<String> names = request.getHeaderNames();
		params.append("?");
		while (names.hasMoreElements()) {
			String paramName = names.nextElement();
			String values = request.getHeader(paramName);
			params.append(paramName + "=" + values + "&");
		}
		return params.toString().substring(0, params.length() - 1);
	}

	/**
	 * 获得请求参数信息
	 * 
	 * @param request
	 * @return
	 */
	private static String getRequestParamers(HttpServletRequest request) {
		StringBuilder params = new StringBuilder();
		Enumeration<String> names = request.getParameterNames();
		params.append("?");
		while (names.hasMoreElements()) {
			String paramName = names.nextElement();
			String[] values = request.getParameterValues(paramName);
			if (values.length == 1) {
				params.append(paramName + "=" + values[0] + "&");
			} else {
				for (int i = 0; i < values.length; i++) {
					params.append(paramName + "[" + i + "]=" + values[i] + "&");
				}
			}
		}
		return params.toString().substring(0, params.length() - 1);
	}

	/**
	 * 获得响应头信息
	 * 
	 * @param response
	 * @return
	 */
	private static String getResponseHeaderNames(HttpServletResponse response) {
		StringBuilder params = new StringBuilder();
		Collection<String> names = response.getHeaderNames();
		params.append("?");
		if (!CollectionUtils.isEmpty(names)) {
			for (String name : names) {
				String values = response.getHeader(name);
				params.append(name + "=" + values + "&");
			}
		}
		return params.toString().substring(0, params.length() - 1);
	}



	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 当开启info级别时日志记录如下信息
		if (logger.isInfoEnabled()) {
			logger.info("请求相关日志----------------------------------------------------------------");
			// request中得到请求开始时间
			@SuppressWarnings("unused")
			long requestStartTime = (Long) request
					.getAttribute("requestStartTime");
			// 得到请求结束时间

			// 如果出现异常requestEndTime和executeTime可能会出现空指针异常
			/*
			 * long requestEndTime =
			 * (Long)request.getAttribute("requestEndTime");
			 */
			// 请求经历的时间
			/* long executeTime = requestEndTime - requestStartTime; */
			/*
			 * logger.info("[" + handler + "] 本次请求执行时间 : " + executeTime +
			 * "ms");
			 */
			if (SecurityUtils.getSubject().getSession() != null) {
				logger.info("session id:"
						+ SecurityUtils.getSubject().getSession().getId()
								.toString()); // 会话ID
			}
			logger.info("协议类型：" + request.getProtocol());// 协议名称 for example,
															// http, https, or
															// ftp.
															// 获取协议信息和版本号，即HTTP/1.1
			logger.info("服务器名：" + request.getServerName());// 获得服务器名a String
															// containing the
															// name of the
															// server
			logger.info("服务器监听的端口号：" + request.getServerPort());// 获取服务器端口号
			logger.info("请求路径：" + request.getRequestURI());// 请求路径a String
															// containing the
															// part of the URL
															// from the protocol
															// name up to the
															// query string
			logger.info("请求HTTP方法：" + request.getMethod());// 请求方法 POST,GET,等
			logger.info("客户端类型："
					+ (CheckMobile.check(request.getHeader("user-agent")) ? (String) "移动设备"
							: (String) "PC"));// 是否移动端访问
			/*
			 * one of the static members BASIC_AUTH, FORM_AUTH,
			 * CLIENT_CERT_AUTH, DIGEST_AUTH (suitable for == comparison) or the
			 * container-specific string indicating the authentication scheme,
			 * or null if the request was not authenticated.
			 */
			logger.info("HTTP认证类型：" + request.getAuthType());// 方法认证的名称
			logger.info("HTTP body编码方式：" + request.getCharacterEncoding());// 返回网页使用的编码，在网页的charset中的值
			logger.info("POST请求数据的字节数：" + request.getContentLength());// 只用于POST请求，表示所发送数据的字节数
			logger.info("content-type：" + request.getContentType());// 获取content-type
																	// a String
																	// containing
																	// the name
																	// of the
																	// MIME type
																	// of the
																	// request
			logger.info("请求服务器的IP：" + request.getLocalAddr());// 获取本地地址，根据访问方法不同而不同，为127.0.0.1或者公网ip
																// a String
																// containing
																// the IP
																// address on
																// which the
																// request was
																// received.
			logger.info("请求服务器的主机名：" + request.getLocalName());// 获取本地IP的名称，如127.0.0.1就是localhost
			logger.info("最终处理请求的端口号：" + request.getLocalPort());// 最终处理请求的端口号
			logger.info("本次请求映射到服务器实际路径：" + request.getPathTranslated());// 映射到服务器实际路径之后的路径信息
			logger.info("客户端的主机名：" + request.getRemoteHost());// 客户端的主机名
			logger.info("客户端的端口号：" + request.getRemotePort());// 客户端的端口号
			logger.info("是否启用安全通道HTTPS："
					+ (request.isSecure() ? (String) "true" : (String) "false"));// 检查是否是安全通道
			logger.info("所有的HTTP请求头部：" + getRequestHeaderNames(request));// 获得所有的头信息
			logger.info("所有的请求参数：" + getRequestParamers(request));// 获得所有请求参数
			// 响应信息
			logger.info("响应头信息：" + getResponseHeaderNames(response));// 响应头信息
			logger.info("响应编码类型：" + response.getCharacterEncoding());// 获取编码类型
			logger.info("响应ContentType：" + response.getContentType());// 获取Content的MIME类型和编码
			logger.info("响应的状态码：" + response.getStatus());// 获取response的状态码
			// JVM信息
			logger.info("JVM可用CPU核数："
					+ Runtime.getRuntime().availableProcessors());// JVM可用CPU核数
			logger.info("JVM最大内存：" + Runtime.getRuntime().maxMemory() / 1024
					/ 1024 + "MB");// 最大内存
			logger.info("JVM已分配内存：" + Runtime.getRuntime().totalMemory() / 1024
					/ 1024 + "MB");// 已分配内存
			logger.info("JVM已分配内存中的剩余空间：" + Runtime.getRuntime().freeMemory()
					/ 1024 / 1024 + "MB");// 已分配内存中的剩余空间
			logger.info("JVM最大可用内存："
					+ ((Runtime.getRuntime().maxMemory() / 1024 / 1024)
							- (Runtime.getRuntime().totalMemory() / 1024 / 1024) + (Runtime
							.getRuntime().freeMemory() / 1024 / 1024)) + "MB");// 最大可用内存

			double tempmem = (Runtime.getRuntime().totalMemory() * 1.0 - Runtime
					.getRuntime().freeMemory() * 1.0)
					/ (Runtime.getRuntime().maxMemory() * 1.0);
			logger.info("JVM已用实际内存百分比：" + tempmem * 100 + "%");// 最大内存
		}

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 访问结束的时间
		long requestEndTime = System.currentTimeMillis();
		// 放到request scope中
		request.setAttribute("requestEndTime", requestEndTime);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 访问当前时间
		long requestStartTime = System.currentTimeMillis();
		// 放到request scope中
		request.setAttribute("requestStartTime", requestStartTime);
		return true;
	}
}

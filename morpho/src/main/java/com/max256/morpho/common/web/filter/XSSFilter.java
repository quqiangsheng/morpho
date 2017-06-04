package com.max256.morpho.common.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XSSFilter跨站脚本攻击过滤器
 * 
 * @author fbf
 */

public class XSSFilter implements Filter {
	private String encoding = "UTF-8";
	private boolean forceEncoding = false;
	// 过滤路径
	List<String> filterPaths = new ArrayList<String>();
	// 日志
	private static final Logger logger = LoggerFactory.getLogger(XSSFilter.class);


	public XSSFilter() {
	}

	@Override
	public void destroy() {
	}

	/*
	 * 过滤方法
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 如果不是HttpServlet相关请求放行，不作处理
		if (!(request instanceof HttpServletRequest)
				|| !(response instanceof HttpServletResponse)) {
			chain.doFilter(request, response);
			return;
		}
		// 强转为HttpServletRequest HttpServletResponse
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 编码判断
		if (this.encoding != null
				&& (this.forceEncoding || request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding) {
				response.setCharacterEncoding(this.encoding);
			}
		}
		// 判断HTTP头Accept的类型如果为空则直接放行
		String accept = httpRequest.getHeader("Accept");
		if (StringUtils.isBlank(accept)) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		// Returns the part of this request's URL that calls the servlet.
		// This path starts with a "/" character and includes either the servlet
		// name or a path to the servlet
		String servletPath = httpRequest.getServletPath();
		boolean willFilter = false;
		for (String filterPath : filterPaths) {

			Pattern p = Pattern.compile(filterPath);
			// 如果过滤列表中和当前路径匹配则过滤
			if (p.matcher(servletPath).matches()) {
				logger.warn("XsswillFilterPath :　" + servletPath);
				willFilter = true;
				break;
			}
		}
		// 如果过滤成功
		if (willFilter) {
			// 判断HTTP头的是否为application/json text/javascript
			if (!accept.contains("application/json")
					&& !accept.contains("text/javascript")) {
				// 如果不是要求返回json，日志记录请求的查询参数
				logger.info("getQueryString = " + httpRequest.getQueryString());
				// 对request进行包装
				XSSRequestWrapper xssRequest = new XSSRequestWrapper(
						httpRequest);
				// 放行
				chain.doFilter(xssRequest, httpResponse);
			} else {
				// 如果是要求返回json，日志记录请求的查询参数
				logger.info("getQueryString[json] = "
						+ httpRequest.getQueryString());
				XSSRequestJSONWrapper xssRequest = new XSSRequestJSONWrapper(
						httpRequest);
				chain.doFilter(xssRequest, httpResponse);
			}
			return;
		}
		chain.doFilter(httpRequest, httpResponse);
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 如果没有设置编码，就设置，如果设置过忽略设置，保持原设置
		String encoding = StringUtils.trimToEmpty(filterConfig
				.getInitParameter("encoding"));
		if (!StringUtils.isBlank(encoding)) {
			this.encoding = encoding;
		}
		String forceEncoding = StringUtils.trimToEmpty(filterConfig
				.getInitParameter("forceEncoding"));
		if (forceEncoding.toLowerCase().equals("true")) {
			this.forceEncoding = true;
		}
		// 如果有的话，读取filterPaths配置到list存储
		String filterPaths = StringUtils.trimToEmpty(filterConfig
				.getInitParameter("filterPaths"));
		if (!StringUtils.isBlank(filterPaths)) {
			String[] filterPath = filterPaths.split(",");
			for (String path : filterPath) {
				this.filterPaths.add(path);
			}
		}

	}

	/**
	 * @return the forceEncoding
	 */
	public boolean isForceEncoding() {
		return forceEncoding;
	}

	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @param forceEncoding
	 *            the forceEncoding to set
	 */
	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}

}

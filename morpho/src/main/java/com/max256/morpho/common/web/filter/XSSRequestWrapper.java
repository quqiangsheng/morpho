package com.max256.morpho.common.web.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;

/**
 * XSSRequestWrapper防止跨站脚本攻击请求包装器
 * 
 * @author fbf
 * 
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

	private static Pattern[] patterns = new Pattern[] {
			// Script fragments
			Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			// src='...'
			Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL),
			Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL),
			// lonely script tags
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			// eval(...)
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			// expression(...)
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			// javascript:...
			Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
			// vbscript:...
			Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
			// onload(...)=...
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			// sql1
			Pattern.compile(
					"('|dba|user|into|select|group|order|by|join|where|insert|into|delete|update|set|alter|system|parameter|truncate|drop|declare|dbms|commit|create|replace|modify|comment)+(\\p{javaWhitespace}|\\/\\*|_|\\.|$|E')+",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL),
			// sql2
			Pattern.compile(
					"\\p{javaWhitespace}*%|%\\p{javaWhitespace}*|!|=|!\\p{javaWhitespace}*=|<|>|in\\p{javaWhitespace}*\\(|\\(|\\)|like|or|and|where|\\%|\\'|\\\"|\\;|\\/\\*",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL),
			// js
			Pattern.compile(
					"base64|confirm|ajax|function|iframe|alert|<|>|'|\"|&|#|%|\\\\|\\\\u|\\\\x",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL) };

	/**
	 * 
	 * @param value
	 * @return
	 */
	static String xssKeyWordFilter(String value) {
		if (value == null || "".equals(value)) {
			return value;
		}
		// XSS过滤
		Pattern pattern = Pattern.compile("^.*((?i)script)+.*$",
				Pattern.CASE_INSENSITIVE);// 用指定的正则表达式进行预编译
		// SQL过滤
		// Pattern pattern =
		// Pattern.compile("^.*(dba|user|zc01|xc01|yh01|insert|into|delete|update|set|alter|system|parameter|truncate|drop|declare|dbms|commit|create|replace|modify|comment)+(\\p{javaWhitespace}|\\/\\*|_|\\.)+.*$");//
		// 数字，大写\D表示非数字

		Matcher matcher = pattern.matcher(value.toString());// 创建匹配给定输入与此模式的匹配器。
		StringBuffer sbf = new StringBuffer();
		while (matcher.find()) {// 描输入序列以查找与该模式匹配的下一个子序列。
			matcher.appendReplacement(sbf, "");//
		}
		matcher.appendTail(sbf);
		System.out.println(sbf.toString());
		return sbf.toString();
	}

	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		value = stripXSS(name, value);
		return stripXSS(value);
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		String encodedValue = stripXSS(value);
		encodedValue = stripXSS(parameter, encodedValue);
		LogManager.getLogger(this.getClass()).info(
				parameter + "=[" + value + "] encodedString=[" + encodedValue
						+ "]");
		return encodedValue;
	}

	/*
	 * 
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		Enumeration<String> parameterNames = this.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			parameterMap.put(name, this.getParameterValues(name));
		}
		return parameterMap;
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = stripXSS(values[i]);
			encodedValues[i] = stripXSS(parameter, encodedValues[i]);
			LogManager.getLogger(this.getClass()).info(
					parameter + "=[" + values[i] + "]]");
			LogManager.getLogger(this.getClass()).info(
					"encodedValue=[" + encodedValues[i] + "]");
		}

		return encodedValues;
	}

	@Override
	public String getQueryString() {
		String queryString = super.getQueryString();

		if (queryString != null) {
			try {
				queryString = URLDecoder.decode(queryString,
						this.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Enumeration<String> parameterNames = this.getParameterNames();
			StringBuffer sb = new StringBuffer();
			while (parameterNames.hasMoreElements()) {
				String parameterName = parameterNames.nextElement();
				String[] parameterValues = this
						.getParameterValues(parameterName);
				sb.append(parameterName).append("=");
				for (int i = 0; i < parameterValues.length; i++) {
					sb.append(parameterValues[i]);
					if (i < parameterValues.length - 1) {
						sb.append(",");
					}
				}
				if (parameterNames.hasMoreElements()) {
					sb.append(parameterName).append("&");
				}
			}
			LogManager.getLogger(this.getClass()).info(
					"queryString=[" + queryString + "]");
			LogManager.getLogger(this.getClass()).info(
					"encodedString=[" + sb.toString() + "]");
			return sb.toString();
		}
		return queryString;
	}

	private String stripXSS(String value) {
		if (value != null) {
			// NOTE: It's highly recommended to use the ESAPI library and
			// uncomment the following line to avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);

			// Avoid null characters
			value = value.replaceAll("\0", "");

			// Remove all sections that match a pattern
			for (Pattern scriptPattern : patterns) {
				value = StringUtils.trimToEmpty(scriptPattern.matcher(value)
						.replaceAll(""));
				// value = scriptPattern.matcher(value).replaceAll("");
			}
		}
		return value;
	}

	private String stripXSS(String parameter, String value) {
		if (parameter != null && value != null) {
			if (parameter.equals("ex")) {
				value = String.valueOf(NumberUtils.toInt(value));
			}
		}
		return value;
	}
}
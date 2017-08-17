package com.max256.morpho.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ajax工具类
 * @author fbf
 *
 */
public class AjaxUtils {
	/**
	 * 判断当前请求是否为异步请求.
	 * 综合两种判断方法
	 * 1.根据客户端的http请求头判断
	 * 2.根据访问controller是否有ResponseBody注解判断
	 */
	public static boolean isAjax(HttpServletRequest request, HttpServletResponse response){
		//判断请求头
		String flagHeader=request.getHeader("X-Requested-With");
		if (flagHeader != null && !flagHeader.equals("") && !flagHeader.equals("null")) {
			return (true);
		}
		String acceptHeader=request.getHeader("Accept");
		if (acceptHeader != null 
				&& !acceptHeader.equals("")
				&& !acceptHeader.equals("null")
				//表明带json字符
				&&acceptHeader.toLowerCase().indexOf("json")!=-1) {
			return (true);
		}
		return (false);
	}
	


}

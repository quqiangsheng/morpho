package com.max256.morpho.common.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.max256.morpho.common.util.ip2region.DbSearcher;

/**
 * IP工具类 getAddr方法
 * 
 * 
 * @author fbf
 * 
 */

public class IPUtils {

	private static Logger logger = LoggerFactory.getLogger(IPUtils.class);

	/**
	 * 获取登录用户的IP 所对应的真实物理地址 依赖网络和淘宝的IP地址库
	 * 
	 * @param request
	 * @return
	 */
	public static String getAddr(HttpServletRequest request) {
		String ip ;
		//如果有X-Real-IP的话有先使用
		if(request.getHeader("X-Real-IP")!=null&&
				request.getHeader("X-Real-IP").trim().length()>=7&&
				!"unknown".equalsIgnoreCase(request.getHeader("X-Real-IP").trim())&&
				!request.getHeader("X-Real-IP").trim().equals("0:0:0:0:0:0:0:1")&&
				!request.getHeader("X-Real-IP").trim().startsWith("127.")
				){
			ip =request.getHeader("X-Real-IP").trim();
		}else{
			ip = request.getHeader("x-forwarded-for");			
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//apache兼容性请求头
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//weblogic兼容性请求头
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//如果还是获取不到就获得原始的RemoteAddr
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			//如果原始ip为ip-v6的0:0:0:0:0:0:0:1 代表本地
			ip = "127.0.0.1";
		}
		if (ip.split(",").length > 1) {
			//如果数组长度大于1  第一部分为真实客户端ip 之后为各级代理服务器ip
			//注意x-forwarded-for字段可能被伪造 需要注意sql注入和可能跟ip敏感的业务安全判断
			ip = ip.split(",")[0];
		}
		return getIpInfo(ip);
	}

	/**
	 * 得到所有的本地IP 本地可能有多块网卡或者虚拟网卡 得到所有的网卡IP
	 * 
	 * @return
	 */
	public static List<String> getAllLocalIP() {
		List<String> ipList = new ArrayList<String>();
		try {
			// 根据java.net包的工具类
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			NetworkInterface networkInterface;
			Enumeration<InetAddress> inetAddresses;
			InetAddress inetAddress;
			String ip;
			while (networkInterfaces.hasMoreElements()) {
				networkInterface = networkInterfaces.nextElement();
				inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					inetAddress = inetAddresses.nextElement();
					if (inetAddress != null
							&& inetAddress instanceof Inet4Address) { // IPV4
						ip = inetAddress.getHostAddress();
						ipList.add(ip);
					}
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return ipList;
	}

	public static String getIp(HttpServletRequest request) {
		String ip ;
		//如果有X-Real-IP的话有先使用
		if(request.getHeader("X-Real-IP")!=null&&
				request.getHeader("X-Real-IP").trim().length()>=7&&
				!"unknown".equalsIgnoreCase(request.getHeader("X-Real-IP").trim())&&
				!request.getHeader("X-Real-IP").trim().equals("0:0:0:0:0:0:0:1")&&
				!request.getHeader("X-Real-IP").trim().startsWith("127.")
				){
			ip =request.getHeader("X-Real-IP").trim();
		}else{
			ip = request.getHeader("x-forwarded-for");			
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//apache兼容性请求头
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//weblogic兼容性请求头
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//如果还是获取不到就获得原始的RemoteAddr
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			//如果原始ip为ip-v6的0:0:0:0:0:0:0:1 代表本地
			ip = "127.0.0.1";
		}
		if (ip.split(",").length > 1) {
			//如果数组长度大于1  第一部分为真实客户端ip 之后为各级代理服务器ip
			//注意x-forwarded-for字段可能被伪造 需要注意sql注入和可能跟ip敏感的业务安全判断
			ip = ip.split(",")[0];
		}
		return ip;
	}

	/**
	 * 得到ip + 地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip ;
		//如果有X-Real-IP的话有先使用
		if(request.getHeader("X-Real-IP")!=null&&
				request.getHeader("X-Real-IP").trim().length()>=7&&
				!"unknown".equalsIgnoreCase(request.getHeader("X-Real-IP").trim())&&
				!request.getHeader("X-Real-IP").trim().equals("0:0:0:0:0:0:0:1")&&
				!request.getHeader("X-Real-IP").trim().startsWith("127.")
				){
			ip =request.getHeader("X-Real-IP").trim();
		}else{
			ip = request.getHeader("x-forwarded-for");			
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//apache兼容性请求头
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//weblogic兼容性请求头
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//如果还是获取不到就获得原始的RemoteAddr
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			//如果原始ip为ip-v6的0:0:0:0:0:0:0:1 代表本地
			ip = "127.0.0.1";
		}
		if (ip.split(",").length > 1) {
			//如果数组长度大于1  第一部分为真实客户端ip 之后为各级代理服务器ip
			//注意x-forwarded-for字段可能被伪造 需要注意sql注入和可能跟ip敏感的业务安全判断
			ip = ip.split(",")[0];
		}
		return ip + " " + getIpInfo(ip);
	}

	/**
	 * 通过IP获取中文地址描述
	 * 需要ip库或者网络支持
	 * 这里采用ip2region库实现
	 * @param ip
	 * @return
	 */
	public static String getIpInfo(String ip) {
		String info = "";
		DbSearcher ipSearcher = (DbSearcher)ApplicationContextUtils.getBean("ipSearcher");
		String region="";
		try {
			region = ipSearcher.memorySearch(ip).getRegion();
		} catch (IOException e) {
			//不报错 容忍
			region="||||";
		}
		String[] regions = StringUtils.split(region, '|');
		/*System.out.println("ip       "+ip);
		System.out.println("Couuntry "+regions[0]);
		System.out.println("Area     "+regions[1]);
		System.out.println("Province "+regions[2]);
		System.out.println("City     "+regions[3]);
		System.out.println("operator "+regions[4]);*/
		StringBuilder sb=new StringBuilder();
		sb.append("国家：");
		sb.append(regions[0]);
		sb.append(",");
		sb.append("地区：");
		sb.append(regions[1]);
		sb.append(",");
		sb.append("省：");
		sb.append(regions[2]);
		sb.append(",");
		sb.append("市：");
		sb.append(regions[3]);
		sb.append(",");
		sb.append("运营商：");
		sb.append(regions[4]);
		sb.append(",");
		info=sb.toString();
		return info;
	
	}

}

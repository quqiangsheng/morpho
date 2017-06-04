package com.max256.morpho.common.util.system;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 代表当前主机的信息。
 */
public class HostInfo{

	private final String HOST_NAME;
	private final String HOST_ADDRESS;

	public HostInfo() {
		String hostName;
		String hostAddress;

		try {
			InetAddress localhost = InetAddress.getLocalHost();

			hostName = localhost.getHostName();
			hostAddress = localhost.getHostAddress();
		} catch (UnknownHostException e) {
			hostName = "localhost";
			hostAddress = "127.0.0.1";
		}

		HOST_NAME = hostName;
		HOST_ADDRESS = hostAddress;
	}

	/**
	 * 取得当前主机的名称。
	 * 
	 * <p>
	 * 例如：<code>"webserver1"</code>
	 * </p>
	 * 
	 * @return 主机名
	 */
	public final String getName() {
		return HOST_NAME;
	}

	/**
	 * 取得当前主机的地址。
	 * 
	 * <p>
	 * 例如：<code>"192.168.0.1"</code>
	 * </p>
	 * 
	 * @return 主机地址
	 */
	public final String getAddress() {
		return HOST_ADDRESS;
	}

	/**
	 * 将当前主机的信息转换成字符串。
	 * 
	 * @return 主机信息的字符串表示
	 */
	public final String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtils.append(builder, "Host Name:    ", getName());
		SystemUtils.append(builder, "Host Address: ", getAddress());

		return builder.toString();
	}

}

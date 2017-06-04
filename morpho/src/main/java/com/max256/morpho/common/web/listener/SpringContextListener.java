package com.max256.morpho.common.web.listener;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Who;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.max256.morpho.common.config.AppConfig;
import com.max256.morpho.common.util.PasswordUtils;

/**
 * 自定义spring容器启动监听器 功能:当spring 核心容器加载完成时打印系统相关环境信息 ps:本监听器调用的有sigar的动态链接库
 * win系统请把这些库加到%JAVA_HOME%\bin目录下 linux请参考相关文档配置 主要是用来方便开发阶段 知道系统的环境信息
 * 生产环境可以注释掉本监听器 sigar相关依赖库已经预置在项目中支持windows linux
 * 
 * @author fbf
 * 
 */
@Component("springContextListener")
public class SpringContextListener implements ApplicationListener<ContextRefreshedEvent> {

	/*
	 * 当容器启动是，调用本方法，日志输出系统的启动信息
	 */
	@Resource
	private AppConfig appConfig;
	// Sigar 获得系统环境信息
	Sigar sigar = new Sigar();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// 读取配置文件 设置设否开启登录验证码校验
			String enableCaptcha = appConfig.getValue("login.enableCaptch");
			if (StringUtils.isNotBlank(enableCaptcha)) {
				enableCaptcha = enableCaptcha.trim().toLowerCase();
				// 判断是否符合要求 1 yes true都代表开启 0 no false代表不开启
				if (enableCaptcha.equals("1") || enableCaptcha.equals("yes") || enableCaptcha.equals("true")) {
					// 开启
					com.max256.morpho.common.config.Constants.CAPTCHA_ENABLED = true;
					System.out.println("打开登陆验证码校验");
				} else if (enableCaptcha.equals("0") || enableCaptcha.equals("no") || enableCaptcha.equals("false")) {
					// 关闭
					com.max256.morpho.common.config.Constants.CAPTCHA_ENABLED = false;
					System.out.println("关闭登陆验证码校验");
				} else {
					// 保持类里定义的默认值
					System.out.println("使用默认的登陆验证码校验策略:关闭验证码校验");
				}

			}
			// 读取配置文件 设置登录成功后的转跳url
			String successUrl = appConfig.getValue("shiro.success.url");
			if (StringUtils.isNotBlank(successUrl)) {
				successUrl = successUrl.trim();
				com.max256.morpho.common.config.Constants.SUCCESS_URL = successUrl;
			} else {
				// 报错停止启动
				throw new RuntimeException("the properties shiro.success.url  is not properly ");
			}
			// 读取配置文件 设置系统名字
			String sysName = appConfig.getValue("system.sysname");
			if (StringUtils.isNotBlank(sysName)) {
				sysName = sysName.trim();
				com.max256.morpho.common.config.Constants.SYS_NAME = sysName;
			}
			System.out.println("系统名称:" + com.max256.morpho.common.config.Constants.SYS_NAME);
			// 读取配置文件 设置系统版权信息
			String sysCopyright = appConfig.getValue("system.copyright");
			if (StringUtils.isNotBlank(sysCopyright)) {
				sysCopyright = sysCopyright.trim();
				com.max256.morpho.common.config.Constants.SYS_COPYRIGHT = sysCopyright;
			}
			System.out.println("系统版权:" + com.max256.morpho.common.config.Constants.SYS_COPYRIGHT);
			// 读取配置文件 设置密码解密工具类的配置和配置文件中的一致
			String algorithmName = appConfig.getValue("shiro.credentialsMatcher.hashAlgorithmName");
			if (StringUtils.isNotBlank(algorithmName)) {
				algorithmName = algorithmName.trim();
				PasswordUtils.algorithmName = algorithmName;
			} else {
				// 报错停止启动
				throw new RuntimeException(
						"the properties shiro.credentialsMatcher.hashAlgorithmName  is not properly ");
			}
			String hashIterations = appConfig.getValue("shiro.credentialsMatcher.hashIterations");
			if (StringUtils.isNotBlank(hashIterations)) {
				hashIterations = hashIterations.trim();
				try {
					PasswordUtils.hashIterations = Integer.parseInt(hashIterations);
				} catch (NumberFormatException e) {
					// 报错停止启动
					throw new RuntimeException(
							"the properties shiro.credentialsMatcher.hashIterations  is not properly ");
				}
			} else {
				// 报错停止启动
				throw new RuntimeException("the properties shiro.credentialsMatcher.hashIterations  is not properly ");
			}

			// root application context 没有parent，他就是老大.
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			System.out.println(
					"-----------------系统启动成功，以下是应用环境信息：-------------------------------------------------------------------------------------");
			System.out.println("数据库驱动名字：" + appConfig.getValue("druid.jdbc.driverClassName"));
			System.out.println("数据库链接URL：" + appConfig.getValue("druid.jdbc.url"));
			System.out.println("数据库用户名：" + appConfig.getValue("druid.jdbc.username"));
			System.out.println("数据库密码：" + appConfig.getValue("druid.jdbc.password"));
			System.out.println("链接测试语句：" + appConfig.getValue("druid.jdbc.validationQuery"));
			System.out.println("testOnBorrow：" + appConfig.getValue("druid.jdbc.testOnBorrow"));
			System.out.println("testOnReturn：" + appConfig.getValue("druid.jdbc.testOnReturn"));
			System.out.println("testWhileIdle：" + appConfig.getValue("druid.jdbc.testWhileIdle"));
			System.out.println("连接池初始化大小：" + appConfig.getValue("druid.jdbc.initialSize"));
			System.out.println("连接池最大链接数量：" + appConfig.getValue("druid.jdbc.maxActive"));
			System.out.println("连接池最小连接数量：" + appConfig.getValue("druid.jdbc.minIdle"));
			System.out.println("连接池最大等待时间：" + appConfig.getValue("druid.jdbc.maxWait"));
			System.out.println(
					"-------------------以下是系统环境信息：-----------------------------------------------------------------------------------------------");
			Map<String, String> map = System.getenv();
			for (Iterator<String> itr = map.keySet().iterator(); itr.hasNext();) {
				String key = itr.next();
				System.out.println(key + "=" + map.get(key));
			}
			System.out.println(
					"-------------------以下是系统属性信息：-----------------------------------------------------------------------------------------------");
			Properties props = System.getProperties();
			props.list(System.out);
			System.out.println(
					"-------------------以下是操作系统环境信息：-----------------------------------------------------------------------------------------------");
			// 获取cpu信息代码
			// CPU数量（单位：个）
			int cpuLength;
			try {
				cpuLength = sigar.getCpuInfoList().length;
				System.out.println("CPU数量（单位：个）" + cpuLength);
			} catch (SigarException e1) {

				e1.printStackTrace();
			}

			// CPU的总量（单位：HZ）及CPU的相关信息
			CpuInfo infos[];
			try {
				infos = sigar.getCpuInfoList();

				for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
					CpuInfo info = infos[i];
					System.out.println("mhz=" + info.getMhz());// CPU的总量MHz
					System.out.println("vendor=" + info.getVendor());// 获得CPU的卖主，如：Intel
					System.out.println("model=" + info.getModel());// 获得CPU的类别，如：Celeron
					System.out.println("cache size=" + info.getCacheSize());// 缓冲存储器数量
				}

			} catch (SigarException e) {
				e.printStackTrace();
			}

			// 获取内存相关代码

			// 物理内存信息
			Mem mem;
			try {
				mem = sigar.getMem();
				// 内存总量
				System.out.println("内存总量Total = " + mem.getTotal() / 1024L / 1024 + "M av");
				// 当前内存使用量
				System.out.println("当前内存使用量Used = " + mem.getUsed() / 1024L / 1024 + "M used");
				// 当前内存剩余量
				System.out.println("当前内存剩余量Free = " + mem.getFree() / 1024L / 1024 + "M free");
			} catch (SigarException e) {

				e.printStackTrace();
			}

			// 系统页面文件交换区信息
			Swap swap;
			try {
				swap = sigar.getSwap();
				// 交换区总量
				System.out.println("交换区总量Total = " + swap.getTotal() / 1024L + "K av");
				// 当前交换区使用量
				System.out.println("当前交换区使用量Used = " + swap.getUsed() / 1024L + "K used");
				// 当前交换区剩余量
				System.out.println("当前交换区剩余量Free = " + swap.getFree() / 1024L + "K free");
			} catch (SigarException e) {

				e.printStackTrace();
			}

			// 获得操作系统相关信息
			// 取到当前操作系统的名称
			String hostname = "";
			try {
				hostname = InetAddress.getLocalHost().getHostName();
			} catch (Exception exc) {
				try {
					hostname = sigar.getNetInfo().getHostName();
				} catch (SigarException e) {
					hostname = "localhost.unknown";
				} finally {
					sigar.close();
				}
			}
			System.out.println("操作系统名称：" + hostname);

			// 取当前操作系统的信息
			OperatingSystem OS = OperatingSystem.getInstance();
			// 操作系统内核类型如： 386、486、586等x86
			System.out.println("OS.getArch() = " + OS.getArch());
			System.out.println("OS.getCpuEndian() = " + OS.getCpuEndian());//
			System.out.println("OS.getDataModel() = " + OS.getDataModel());//
			// 系统描述
			System.out.println("OS.getDescription() = " + OS.getDescription());
			System.out.println("OS.getMachine() = " + OS.getMachine());//
			// 操作系统类型
			System.out.println("OS.getName() = " + OS.getName());
			System.out.println("OS.getPatchLevel() = " + OS.getPatchLevel());//
			// 操作系统的卖主
			System.out.println("OS.getVendor() = " + OS.getVendor());
			// 卖主名称
			System.out.println("OS.getVendorCodeName() = " + OS.getVendorCodeName());
			// 操作系统名称
			System.out.println("OS.getVendorName() = " + OS.getVendorName());
			// 操作系统卖主类型
			System.out.println("OS.getVendorVersion() = " + OS.getVendorVersion());
			// 操作系统的版本号
			System.out.println("OS.getVersion() = " + OS.getVersion());

			// 取当前系统进程表中的用户信息
			Who who[];
			try {
				who = sigar.getWhoList();
				if (who != null && who.length > 0) {
					for (int i = 0; i < who.length; i++) {
						System.out.println("\n~~~~~~~~~" + String.valueOf(i) + "~~~~~~~~~");
						Who _who = who[i];
						// 当前系统进程表中的用户名
						System.out.println("当前系统进程表中的用户名getUser() = " + _who.getUser());
						System.out.println("getDevice() = " + _who.getDevice());
						System.out.println("getHost() = " + _who.getHost());
						System.out.println("getTime() = " + _who.getTime());

					}
				}
			} catch (SigarException e) {
				e.printStackTrace();
			}

			// 取得磁盘相关信息
			// 取硬盘已有的分区及其详细信息（通过sigar.getFileSystemList()来获得FileSystem列表对象，然后对其进行编历
			FileSystem fslist[];
			String dir = System.getProperty("user.home");// 当前用户文件夹路径
			try {
				fslist = sigar.getFileSystemList();

				System.out.println(dir + "   " + fslist.length);
				for (int i = 0; i < fslist.length; i++) {
					System.out.println("\n~~~~~~~~~~" + i + "~~~~~~~~~~");
					FileSystem fs = fslist[i];
					// 分区的盘符名称
					System.out.println("fs.getDevName() = " + fs.getDevName());
					// 分区的盘符名称
					System.out.println("fs.getDirName() = " + fs.getDirName());
					System.out.println("fs.getFlags() = " + fs.getFlags());//
					// 文件系统类型，比如 FAT32、NTFS
					System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());
					// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
					System.out.println("fs.getTypeName() = " + fs.getTypeName());
					// 文件系统类型
					System.out.println("fs.getType() = " + fs.getType());
					FileSystemUsage usage = null;
					try {
						usage = sigar.getFileSystemUsage(fs.getDirName());
					} catch (SigarException e) {
						if (fs.getType() == 2)
							throw e;
						continue;
					}
					switch (fs.getType()) {
					case 0: // TYPE_UNKNOWN ：未知
						break;
					case 1: // TYPE_NONE
						break;
					case 2: // TYPE_LOCAL_DISK : 本地硬盘
						// 文件系统总大小
						System.out.println(" Total = " + usage.getTotal() + "KB");
						// 文件系统剩余大小
						System.out.println(" Free = " + usage.getFree() + "KB");
						// 文件系统可用大小
						System.out.println(" Avail = " + usage.getAvail() + "KB");
						// 文件系统已经使用量
						System.out.println(" Used = " + usage.getUsed() + "KB");
						double usePercent = usage.getUsePercent() * 100D;
						// 文件系统资源的利用率
						System.out.println(" Usage = " + usePercent + "%");
						break;
					case 3:// TYPE_NETWORK ：网络
						break;
					case 4:// TYPE_RAM_DISK ：闪存
						break;
					case 5:// TYPE_CDROM ：光驱
						break;
					case 6:// TYPE_SWAP ：页面交换
						break;
					}
					System.out.println(" DiskReads = " + usage.getDiskReads());
					System.out.println(" DiskWrites = " + usage.getDiskWrites());
				}
			} catch (SigarException e1) {
				e1.printStackTrace();
			}
			// 获取网络信息
			// 当前机器的正式域名
			try {
				System.out.println(InetAddress.getLocalHost().getCanonicalHostName());
			} catch (UnknownHostException e) {
				try {
					System.out.println(sigar.getFQDN());
				} catch (SigarException ex) {
				} finally {
					sigar.close();
				}
			}

			// 取到当前机器的IP地址
			String address = null;
			try {
				address = InetAddress.getLocalHost().getHostAddress();
				// 没有出现异常而正常当取到的IP时，如果取到的不是网卡循回地址时就返回
				// 否则再通过Sigar工具包中的方法来获取
				System.out.println(address);
				if (!NetFlags.LOOPBACK_ADDRESS.equals(address)) {
				}
			} catch (UnknownHostException e) {
				// hostname not in DNS or /etc/hosts
			}
			try {
				address = sigar.getNetInterfaceConfig().getAddress();
			} catch (SigarException e) {
				address = NetFlags.LOOPBACK_ADDRESS;
			} finally {
			}
			System.out.println(address);

			// 取到当前机器的MAC地址
			String[] ifaces;
			String hwaddr = null;
			try {
				ifaces = sigar.getNetInterfaceList();
				for (int i = 0; i < ifaces.length; i++) {
					NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
					if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
							|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
							|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
						continue;
					}
					hwaddr = cfg.getHwaddr();
					System.out.println(hwaddr);
					// break;
				}
				System.out.println(hwaddr != null ? hwaddr : null);
				// 一些其他的信息
				for (int i = 0; i < ifaces.length; i++) {
					NetInterfaceConfig cfg;
					try {
						cfg = sigar.getNetInterfaceConfig(ifaces[i]);
						if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
								|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
								|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
							continue;
						}
						System.out.println("cfg.getAddress() = " + cfg.getAddress());// IP地址
						System.out.println("cfg.getBroadcast() = " + cfg.getBroadcast());// 网关广播地址
						System.out.println("cfg.getHwaddr() = " + cfg.getHwaddr());// 网卡MAC地址
						System.out.println("cfg.getNetmask() = " + cfg.getNetmask());// 子网掩码
						System.out.println("cfg.getDescription() = " + cfg.getDescription());// 网卡描述信息
						System.out.println("cfg.getType() = " + cfg.getType());//
						System.out.println("cfg.getDestination() = " + cfg.getDestination());
						System.out.println("cfg.getFlags() = " + cfg.getFlags());//
						System.out.println("cfg.getMetric() = " + cfg.getMetric());
						System.out.println("cfg.getMtu() = " + cfg.getMtu());
						System.out.println("cfg.getName() = " + cfg.getName());
					} catch (SigarException e) {
						e.printStackTrace();
					}

				}
			} catch (SigarException e1) {
				e1.printStackTrace();
			}

			// 获取网络流量等信息
			String ifNames[];
			try {
				ifNames = sigar.getNetInterfaceList();
				for (int i = 0; i < ifNames.length; i++) {
					String name = ifNames[i];
					NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
					System.out.println("\nname = " + name);// 网络设备名
					System.out.println("Address = " + ifconfig.getAddress());// IP地址
					System.out.println("Netmask = " + ifconfig.getNetmask());// 子网掩码
					if ((ifconfig.getFlags() & 1L) <= 0L) {
						System.out.println("!IFF_UP...skipping getNetInterfaceStat");
						continue;
					}
					try {
						NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
						System.out.println("RxPackets = " + ifstat.getRxPackets());// 接收的总包裹数
						System.out.println("TxPackets = " + ifstat.getTxPackets());// 发送的总包裹数
						System.out.println("RxBytes = " + ifstat.getRxBytes());// 接收到的总字节数
						System.out.println("TxBytes = " + ifstat.getTxBytes());// 发送的总字节数
						System.out.println("RxErrors = " + ifstat.getRxErrors());// 接收到的错误包数
						System.out.println("TxErrors = " + ifstat.getTxErrors());// 发送数据包时的错误数
						System.out.println("RxDropped = " + ifstat.getRxDropped());// 接收时丢弃的包数
						System.out.println("TxDropped = " + ifstat.getTxDropped());// 发送时丢弃的包数
					} catch (SigarNotImplementedException e) {
					} catch (SigarException e) {
						System.out.println(e.getMessage());
					}
				}

			} catch (SigarException e1) {
				e1.printStackTrace();
			}
			System.out.println("---------------系统线程信息-------------------");
			ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
			// 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
			ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
			// 遍历线程信息，仅打印线程ID和线程名称信息
			for (ThreadInfo threadInfo : threadInfos) {
				System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
			}

			System.out.println(
					"-------------------系统信息显示结束--------------------------------------------------------------------------------------------------");
			// sigar用完要释放流
			sigar.close();// 释放流
		}

	}

}

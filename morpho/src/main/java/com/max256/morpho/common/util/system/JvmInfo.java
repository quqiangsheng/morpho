package com.max256.morpho.common.util.system;
/**
 * 代表Java Virtual Machine Implementation的信息。
 */
public class JvmInfo {

	private final String JAVA_VM_NAME = SystemUtils.get("java.vm.name", false);
	private final String JAVA_VM_VERSION = SystemUtils.get("java.vm.version", false);
	private final String JAVA_VM_VENDOR = SystemUtils.get("java.vm.vendor", false);
	private final String JAVA_VM_INFO = SystemUtils.get("java.vm.info", false);

	/**
	 * 取得当前JVM impl.的名称（取自系统属性：<code>java.vm.name</code>）。
	 * 
	 * <p>
	 * 例如Sun JDK 1.4.2：<code>"Java HotSpot(TM) Client VM"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 */
	public final String getName() {
		return JAVA_VM_NAME;
	}

	/**
	 * 取得当前JVM impl.的版本（取自系统属性：<code>java.vm.version</code>）。
	 * 
	 * <p>
	 * 例如Sun JDK 1.4.2：<code>"1.4.2-b28"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 */
	public final String getVersion() {
		return JAVA_VM_VERSION;
	}

	/**
	 * 取得当前JVM impl.的厂商（取自系统属性：<code>java.vm.vendor</code>）。
	 * 
	 * <p>
	 * 例如Sun JDK 1.4.2：<code>"Sun Microsystems Inc."</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 */
	public final String getVendor() {
		return JAVA_VM_VENDOR;
	}

	/**
	 * 取得当前JVM impl.的信息（取自系统属性：<code>java.vm.info</code>）。
	 * 
	 * <p>
	 * 例如Sun JDK 1.4.2：<code>"mixed mode"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 */
	public final String getInfo() {
		return JAVA_VM_INFO;
	}

	/**
	 * 将Java Virutal Machine Implementation的信息转换成字符串。
	 * 
	 * @return JVM impl.的字符串表示
	 */
	public final String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtils.append(builder, "JavaVM Name:    ", getName());
		SystemUtils.append(builder, "JavaVM Version: ", getVersion());
		SystemUtils.append(builder, "JavaVM Vendor:  ", getVendor());
		SystemUtils.append(builder, "JavaVM Info:    ", getInfo());

		return builder.toString();
	}

}

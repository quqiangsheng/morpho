package com.max256.morpho.common.util.system;

/**
 * 代表当前OS的信息。
 */
public class OsInfo{

	private final String OS_VERSION = SystemUtils.get("os.version", false);
	private final String OS_ARCH = SystemUtils.get("os.arch", false);
	private final String OS_NAME = SystemUtils.get("os.name", false);
	private final boolean IS_OS_AIX = getOSMatches("AIX");
	private final boolean IS_OS_HP_UX = getOSMatches("HP-UX");
	private final boolean IS_OS_IRIX = getOSMatches("Irix");
	private final boolean IS_OS_LINUX = getOSMatches("Linux") || getOSMatches("LINUX");
	private final boolean IS_OS_MAC = getOSMatches("Mac");
	private final boolean IS_OS_MAC_OSX = getOSMatches("Mac OS X");
	private final boolean IS_OS_OS2 = getOSMatches("OS/2");
	private final boolean IS_OS_SOLARIS = getOSMatches("Solaris");
	private final boolean IS_OS_SUN_OS = getOSMatches("SunOS");
	private final boolean IS_OS_WINDOWS = getOSMatches("Windows");
	private final boolean IS_OS_WINDOWS_2000 = getOSMatches("Windows", "5.0");
	private final boolean IS_OS_WINDOWS_95 = getOSMatches("Windows 9", "4.0");
	private final boolean IS_OS_WINDOWS_98 = getOSMatches("Windows 9", "4.1");
	private final boolean IS_OS_WINDOWS_ME = getOSMatches("Windows", "4.9");
	private final boolean IS_OS_WINDOWS_NT = getOSMatches("Windows NT");
	private final boolean IS_OS_WINDOWS_XP = getOSMatches("Windows", "5.1");

	// 由于改变file.encoding属性并不会改变系统字符编码，为了保持一致，通过LocaleUtil取系统默认编码。
	private final String FILE_SEPARATOR = SystemUtils.get("file.separator", false);
	private final String LINE_SEPARATOR = SystemUtils.get("line.separator", false);
	private final String PATH_SEPARATOR = SystemUtils.get("path.separator", false);

	/**
	 * 取得当前OS的架构（取自系统属性：<code>os.arch</code>）。
	 * 
	 * <p>
	 * 例如：<code>"x86"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getArch() {
		return OS_ARCH;
	}

	/**
	 * 取得当前OS的名称（取自系统属性：<code>os.name</code>）。
	 * 
	 * <p>
	 * 例如：<code>"Windows XP"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getName() {
		return OS_NAME;
	}

	/**
	 * 取得当前OS的版本（取自系统属性：<code>os.version</code>）。
	 * 
	 * <p>
	 * 例如：<code>"5.1"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getVersion() {
		return OS_VERSION;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为AIX，则返回<code>true</code>
	 */
	public final boolean isAix() {
		return IS_OS_AIX;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为HP-UX，则返回<code>true</code>
	 */
	public final boolean isHpUx() {
		return IS_OS_HP_UX;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为IRIX，则返回<code>true</code>
	 */
	public final boolean isIrix() {
		return IS_OS_IRIX;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Linux，则返回<code>true</code>
	 */
	public final boolean isLinux() {
		return IS_OS_LINUX;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Mac，则返回<code>true</code>
	 */
	public final boolean isMac() {
		return IS_OS_MAC;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为MacOS X，则返回<code>true</code>
	 */
	public final boolean isMacOsX() {
		return IS_OS_MAC_OSX;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为OS2，则返回<code>true</code>
	 */
	public final boolean isOs2() {
		return IS_OS_OS2;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Solaris，则返回<code>true</code>
	 */
	public final boolean isSolaris() {
		return IS_OS_SOLARIS;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Sun OS，则返回<code>true</code>
	 */
	public final boolean isSunOS() {
		return IS_OS_SUN_OS;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Windows，则返回<code>true</code>
	 */
	public final boolean isWindows() {
		return IS_OS_WINDOWS;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Windows 2000，则返回<code>true</code>
	 */
	public final boolean isWindows2000() {
		return IS_OS_WINDOWS_2000;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Windows 95，则返回<code>true</code>
	 */
	public final boolean isWindows95() {
		return IS_OS_WINDOWS_95;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Windows 98，则返回<code>true</code>
	 */
	public final boolean isWindows98() {
		return IS_OS_WINDOWS_98;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Windows ME，则返回<code>true</code>
	 */
	public final boolean isWindowsME() {
		return IS_OS_WINDOWS_ME;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Windows NT，则返回<code>true</code>
	 */
	public final boolean isWindowsNT() {
		return IS_OS_WINDOWS_NT;
	}

	/**
	 * 判断当前OS的类型。
	 * 
	 * <p>
	 * 如果不能取得系统属性<code>os.name</code>（因为Java安全限制），则总是返回<code>false</code>
	 * </p>
	 * 
	 * @return 如果当前OS类型为Windows XP，则返回<code>true</code>
	 */
	public final boolean isWindowsXP() {
		return IS_OS_WINDOWS_XP;
	}

	/**
	 * 匹配OS名称。
	 * 
	 * @param osNamePrefix OS名称前缀
	 * 
	 * @return 如果匹配，则返回<code>true</code>
	 */
	private final boolean getOSMatches(String osNamePrefix) {
		if (OS_NAME == null) {
			return false;
		}

		return OS_NAME.startsWith(osNamePrefix);
	}

	/**
	 * 匹配OS名称。
	 * 
	 * @param osNamePrefix OS名称前缀
	 * @param osVersionPrefix OS版本前缀
	 * 
	 * @return 如果匹配，则返回<code>true</code>
	 */
	private final boolean getOSMatches(String osNamePrefix, String osVersionPrefix) {
		if ((OS_NAME == null) || (OS_VERSION == null)) {
			return false;
		}

		return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);
	}

	/**
	 * 取得OS的文件路径的分隔符（取自系统属性：<code>file.separator</code>）。
	 * 
	 * <p>
	 * 例如：Unix为<code>"/"</code>，Windows为<code>"\\"</code>。
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getFileSeparator() {
		return FILE_SEPARATOR;
	}

	/**
	 * 取得OS的文本文件换行符（取自系统属性：<code>line.separator</code>）。
	 * 
	 * <p>
	 * 例如：Unix为<code>"\n"</code>，Windows为<code>"\r\n"</code>。
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getLineSeparator() {
		return LINE_SEPARATOR;
	}

	/**
	 * 取得OS的搜索路径分隔符（取自系统属性：<code>path.separator</code>）。
	 * 
	 * <p>
	 * 例如：Unix为<code>":"</code>，Windows为<code>";"</code>。
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getPathSeparator() {
		return PATH_SEPARATOR;
	}

	/**
	 * 将OS的信息转换成字符串。
	 * 
	 * @return OS的字符串表示
	 */
	public final String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtils.append(builder, "OS Arch:        ", getArch());
		SystemUtils.append(builder, "OS Name:        ", getName());
		SystemUtils.append(builder, "OS Version:     ", getVersion());
		SystemUtils.append(builder, "File Separator: ", getFileSeparator());
		SystemUtils.append(builder, "Line Separator: ", getLineSeparator());
		SystemUtils.append(builder, "Path Separator: ", getPathSeparator());

		return builder.toString();
	}

}

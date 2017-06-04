package com.max256.morpho.common.util.system;

/**
 * 代表当前用户的信息。
 */
public class UserInfo{

	private final String USER_NAME = SystemUtils.get("user.name", false);
	private final String USER_HOME = SystemUtils.get("user.home", false);
	private final String USER_DIR = SystemUtils.get("user.dir", false);
	private final String USER_LANGUAGE = SystemUtils.get("user.language", false);
	private final String USER_COUNTRY = ((SystemUtils.get("user.country", false) == null) ? SystemUtils.get("user.region", false) : SystemUtils.get("user.country", false));
	private final String JAVA_IO_TMPDIR = SystemUtils.get("java.io.tmpdir", false);

	/**
	 * 取得当前登录用户的名字（取自系统属性：<code>user.name</code>）。
	 * 
	 * <p>
	 * 例如：<code>"admin"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getName() {
		return USER_NAME;
	}

	/**
	 * 取得当前登录用户的home目录（取自系统属性：<code>user.home</code>）。
	 * 
	 * <p>
	 * 例如：<code>"/home/admin"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getHomeDir() {
		return USER_HOME;
	}

	/**
	 * 取得当前目录（取自系统属性：<code>user.dir</code>）。
	 * 
	 * <p>
	 * 例如：<code>"/home/admin/working"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * @since Java 1.1
	 */
	public final String getCurrentDir() {
		return USER_DIR;
	}

	/**
	 * 取得临时目录（取自系统属性：<code>java.io.tmpdir</code>）。
	 * 
	 * <p>
	 * 例如：<code>"/tmp"</code>
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 * 
	 */
	public final String getTempDir() {
		return JAVA_IO_TMPDIR;
	}

	/**
	 * 取得当前登录用户的语言设置（取自系统属性：<code>user.language</code>）。
	 * 
	 * <p>
	 * 例如：<code>"zh"</code>、<code>"en"</code>等
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 */
	public final String getLanguage() {
		return USER_LANGUAGE;
	}

	/**
	 * 取得当前登录用户的国家或区域设置（取自系统属性：JDK1.4 <code>user.country</code>或JDK1.2 <code>user.region</code>）。
	 * 
	 * <p>
	 * 例如：<code>"CN"</code>、<code>"US"</code>等
	 * </p>
	 * 
	 * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
	 * 
	 */
	public final String getCountry() {
		return USER_COUNTRY;
	}

	/**
	 * 将当前用户的信息转换成字符串。
	 * 
	 * @return 用户信息的字符串表示
	 */
	public final String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtils.append(builder, "User Name:        ", getName());
		SystemUtils.append(builder, "User Home Dir:    ", getHomeDir());
		SystemUtils.append(builder, "User Current Dir: ", getCurrentDir());
		SystemUtils.append(builder, "User Temp Dir:    ", getTempDir());
		SystemUtils.append(builder, "User Language:    ", getLanguage());
		SystemUtils.append(builder, "User Country:     ", getCountry());

		return builder.toString();
	}

}

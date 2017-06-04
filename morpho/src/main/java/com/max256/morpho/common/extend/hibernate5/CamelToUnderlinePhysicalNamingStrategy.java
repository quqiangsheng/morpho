package com.max256.morpho.common.extend.hibernate5;

import java.io.Serializable;
import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * 在hibernate5.1版本之后PhysicalNamingStrategy的SpringPhysicalNamingStrategy
 * 实现用来实现驼峰命名的类名到 小写_小写 类似的表名对应
 * 以及属性到字段
 * 类似 类名SysUser 表名sys_user
 * 如果有其他需要可以自行扩展 
 * hibernate5配置命名策略有bug暂不使用
 */
public class CamelToUnderlinePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl implements Serializable {

	/**
	 * Singleton access
	 */
	public static final CamelToUnderlinePhysicalNamingStrategy INSTANCE = new CamelToUnderlinePhysicalNamingStrategy();
	private static final long serialVersionUID = 1L;



	@Override
	public Identifier toPhysicalTableName(Identifier name,
			JdbcEnvironment jdbcEnvironment) {
		return apply(name, jdbcEnvironment);
	}


	@Override
	public Identifier toPhysicalColumnName(Identifier name,
			JdbcEnvironment jdbcEnvironment) {
		return apply(name, jdbcEnvironment);
	}

	protected static Identifier apply(Identifier name, JdbcEnvironment jdbcEnvironment) {
		if (name == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder(name.getText().replace('.', '_'));
		for (int i = 1; i < builder.length() - 1; i++) {
			if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i),
					builder.charAt(i + 1))) {
				builder.insert(i++, '_');
			}
		}
		return getIdentifier(builder.toString(), name.isQuoted(), jdbcEnvironment);
	}

	/**
	 * Get an the identifier for the specified details. By default this method will return
	 * an identifier with the name adapted based on the result of
	 * {@link #isCaseInsensitive(JdbcEnvironment)}
	 * @param name the name of the identifier
	 * @param quoted if the identifier is quoted
	 * @param jdbcEnvironment the JDBC environment
	 * @return an identifier instance
	 */
	protected static Identifier getIdentifier(String name, boolean quoted,
			JdbcEnvironment jdbcEnvironment) {
		if (isCaseInsensitive(jdbcEnvironment)) {
			name = name.toLowerCase(Locale.ROOT);
		}
		return new Identifier(name, quoted);
	}

	/**
	 * Specify whether the database is case sensitive.
	 * @param jdbcEnvironment the JDBC environment which can be used to determine case
	 * @return true if the database is case insensitive sensitivity
	 */
	protected static boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
		return true;
	}

	protected static boolean isUnderscoreRequired(char before, char current, char after) {
		return Character.isLowerCase(before) && Character.isUpperCase(current)
				&& Character.isLowerCase(after);
	}

}

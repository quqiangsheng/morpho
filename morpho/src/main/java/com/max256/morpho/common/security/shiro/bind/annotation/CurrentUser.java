package com.max256.morpho.common.security.shiro.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.max256.morpho.common.config.Constants;

/**
 * 绑定当前登录的用户 不同于@ModelAttribute
 * 
 * @author fbf
 */
@Target({ ElementType.PARAMETER })
// 用于参数上
@Retention(RetentionPolicy.RUNTIME)
// 范围 运行时有效
@Documented
public @interface CurrentUser {

	/**
	 * 当前用户在request中的名字
	 * 
	 * @return
	 */
	String value() default Constants.CURRENT_USER;

}

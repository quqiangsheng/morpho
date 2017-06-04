package com.max256.morpho.common.security.shiro.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 绑定当前请求经服务器端解析后的请求报文
 * RequestMessage
 * @author fbf
 */
@Target({ ElementType.PARAMETER })
// 用于参数上
@Retention(RetentionPolicy.RUNTIME)
// 范围 运行时有效
@Documented
public @interface RequestMessage {


}

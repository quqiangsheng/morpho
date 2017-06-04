package com.max256.morpho.common.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * 列的中文名字
 * 用于生成表单等功能时使用
 * @author fbf
 * @since 2016年10月25日 下午2:44:35
 * @version V1.0
 * 
 */
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Th
{
  public  String value();
}
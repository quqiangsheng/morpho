package com.max256.morpho.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;

/**
 * 校验工具类 hibernate validate 5配合使用 ps：集成hibernate时需要注意：Hibernate Annotations (即
 * Hibernate 3.5.x) 会自动的把你定已在实体模型上的约束信息添加到其映射信息中. 例如,
 * 假设你的一个实体类的属性上有@NotNull的约束的话, 那么Hibernate在生成创建此实体对应的表的DDL的时候,
 * 会自动的给那个属性对应的字段添加上not null. 如果因为某种原因, 你不想使用这个特性,
 * 那么可以将hibernate.validator.apply_to_ddl属性设置为false.
 * 
 * @author qqs
 * 
 */
public class ValidationUtils {

	/**
	 * ConstraintViolation类 方法名 作用 示例 (请参考例 2.9 “Validator.validate() 使用方法”)
	 * getMessage() 获取(经过翻译的)校验错误信息 may not be null getMessageTemplate()
	 * 获取错误信息模版 {javax.validation.constraints.NotNull.message} getRootBean()
	 * 获取被校验的根实体对象 car getRootBeanClass() 获取被校验的根实体类. Car.class getLeafBean()
	 * 如果约束是添加在一个bean(实体对象)上的,那么则返回这个bean的实例, 如果是约束是定义在一个属性上的,
	 * 则返回这个属性所属的bean的实例对象. car getPropertyPath() 从被验证的根对象到被验证的属性的路径.
	 * getInvalidValue() 倒是校验失败的值. passengers getConstraintDescriptor()
	 * 导致校验失败的约束定义.
	 */

	// validator是校验器，它是线程安全的 Validation-->ValidatorFactory-->Validator
	private static Validator validator = Validation
			.buildDefaultValidatorFactory().getValidator();

	/**
	 * 实体校验器
	 */
	public static <T> ValidationResult validateEntity(T obj) {
		ValidationResult result = new ValidationResult();
		// 所有的校验方法都接收零个或多个用来定义此次校验是基于哪个校验组的参数. 如果没有给出这个参数的话,
		// 那么此次校验将会基于默认的校验组 (javax.validation.groups.Default).
		// validate()方法对一个给定的实体对象中定义的所有约束条件进行校验 .所有包括类的 字段的 属性的
		Set<ConstraintViolation<T>> set = validator
				.validate(obj, Default.class);
		// ConstraintViolation中包含了很多方法能够帮你快速定位究竟是什么导致了校验失败
		if (CollectionUtils.isNotEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}

	/**
	 * 属性校验器 必须是符合java bean规范的属性
	 */
	public static <T> ValidationResult validateProperty(T obj,
			String propertyName) {
		ValidationResult result = new ValidationResult();
		// validateProperty()可以对一个给定实体对象的单个属性进行校验. 其中属性名称需要符合JavaBean规范中定义的属性名称.
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj,
				propertyName, Default.class);
		if (CollectionUtils.isNotEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(propertyName, cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}

	/**
	 * 内置约束条件 Annotation Supported data types 作用 Hibernate metadata impact
	 * 
	 * @AssertFalse Boolean, boolean Checks that the annotated element is false.
	 *              没有
	 * @AssertTrue Boolean, boolean Checks that the annotated element is true.
	 *             没有
	 * @DecimalMax BigDecimal, BigInteger, String, byte, short, int, long and
	 *             the respective wrappers of the primitive types. Additionally
	 *             supported by HV: any sub-type of Number. 被标注的值必须不大于约束中指定的最大值.
	 *             这个约束的参数是一个通过BigDecimal定义的最大值的字符串表示. 没有
	 * @DecimalMin BigDecimal, BigInteger, String, byte, short, int, long and
	 *             the respective wrappers of the primitive types. Additionally
	 *             supported by HV: any sub-type of Number. 被标注的值必须不小于约束中指定的最小值.
	 *             这个约束的参数是一个通过BigDecimal定义的最小值的字符串表示. 没有
	 * @Digits(integer=, fraction=) BigDecimal, BigInteger, String, byte, short,
	 *                   int, long and the respective wrappers of the primitive
	 *                   types. Additionally supported by HV: any sub-type of
	 *                   Number. Checks whether the annoted value is a number
	 *                   having up to integer digits and fraction fractional
	 *                   digits. 对应的数据库表字段会被设置精度(precision)和准度(scale).
	 * @Future java.util.Date, java.util.Calendar; Additionally supported by HV,
	 *         if the Joda Time date/time API is on the class path: any
	 *         implementations of ReadablePartial and ReadableInstant.
	 *         检查给定的日期是否比现在晚. 没有
	 * @Max BigDecimal, BigInteger, byte, short, int, long and the respective
	 *      wrappers of the primitive types. Additionally supported by HV:
	 *      String (the numeric value represented by a String is evaluated), any
	 *      sub-type of Number. 检查该值是否小于或等于约束条件中指定的最大值.
	 *      会给对应的数据库表字段添加一个check的约束条件.
	 * @Min BigDecimal, BigInteger, byte, short, int, long and the respective
	 *      wrappers of the primitive types. Additionally supported by HV:
	 *      String (the numeric value represented by a String is evaluated), any
	 *      sub-type of Number. 检查该值是否大于或等于约束条件中规定的最小值.
	 *      会给对应的数据库表字段添加一个check的约束条件.
	 * @NotNull Any type Checks that the annotated value is not null.
	 *          对应的表字段不允许为null.
	 * @Null Any type Checks that the annotated value is null. 没有
	 * @Past java.util.Date, java.util.Calendar; Additionally supported by HV,
	 *       if the Joda Time date/time API is on the class path: any
	 *       implementations of ReadablePartial and ReadableInstant.
	 *       检查标注对象中的值表示的日期比当前早. 没有
	 * @Pattern(regex=, flag=) String 检查该字符串是否能够在match指定的情况下被regex定义的正则表达式匹配. 没有
	 * @Size(min=, max=) String, Collection, Map and arrays Checks if the
	 *             annotated element's size is between min and max (inclusive).
	 *             对应的数据库表字段的长度会被设置成约束中定义的最大值.
	 * @Valid Any non-primitive type 递归的对关联对象进行校验, 如果关联对象是个集合或者数组,
	 *        那么对其中的元素进行递归校验,如果是一个map,则对其中的值部分进行校验. 没有
	 */

}
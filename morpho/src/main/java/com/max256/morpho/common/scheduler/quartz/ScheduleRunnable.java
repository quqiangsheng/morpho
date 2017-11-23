package com.max256.morpho.common.scheduler.quartz;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.util.ReflectionUtils;

import com.max256.morpho.common.exception.BusinessException;
import com.max256.morpho.common.util.SpringUtils;

/**
 * 执行定时任务封装Runnable放入线程池执行
 */
public class ScheduleRunnable implements Runnable {
	private Object target;
	private Method method;
	private String params;
	
	/**
	 * 构造方法
	 * @param beanName spring bean id
	 * @param methodName 方法名称
	 * @param params 方法参数
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchBeanDefinitionException,NoSuchMethodException, SecurityException {
		this.target = SpringUtils.getBean(beanName);
		this.params = params;
		if(this.target==null){
			throw new NoSuchBeanDefinitionException(beanName);
		}
		if(StringUtils.isNotBlank(params)){
			//有参数
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			//没有参数
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public void run() {
		try {
			/*让给定的方法可以访问，如果需要，可以显式地设置它。只有在必要时才调用setAccessible(true)方法，以避免与JVM SecurityManager(如果活动)发生不必要的冲突。*/
			ReflectionUtils.makeAccessible(method);
			//根据参数选择调用的方法的形式
			if(StringUtils.isNotBlank(params)){
				method.invoke(target, params);
			}else{
				method.invoke(target);
			}
		}catch (Throwable e) {
			throw new BusinessException("执行定时任务失败  "+e.getMessage());
		}
	}

}

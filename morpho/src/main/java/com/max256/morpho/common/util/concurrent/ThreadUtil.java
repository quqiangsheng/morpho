/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.max256.morpho.common.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 线程相关工具类.
 * 
 * 1. 处理了InterruptedException的sleep
 * 
 * 2. 优雅关闭线程池的(via guava)
 * 
 * 3. 创建可自定义线程名的ThreadFactory(via guava)
 * 
 * 4. 防止第三方Runnalbe未捕捉异常导致线程跑飞
 */
public abstract class ThreadUtil {

	/////////// 线程相关功能//////////

	/**
	 * sleep等待, 单位为毫秒, 已捕捉并处理InterruptedException.
	 */
	public static void sleep(long durationMillis) {
		try {
			Thread.sleep(durationMillis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * sleep等待，已捕捉并处理InterruptedException.
	 */
	public static void sleep(long duration, TimeUnit unit) {
		try {
			Thread.sleep(unit.toMillis(duration));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/////////// 线程池相关功能//////////

	/**
	 * 按照ExecutorService JavaDoc示例代码编写的Graceful Shutdown方法.
	 * 
	 * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
	 * 
	 * 如果1/2超时时间后, 则调用shutdownNow,取消在workQueue中Pending的任务,并中断所有阻塞函数.
	 * 
	 * 如果1/2超时仍然超時，則強制退出.
	 * 
	 * 另对在shutdown时线程本身被调用中断做了处理.
	 * 
	 * 返回线程最后是否被中断.
	 * 
	 * 使用了Guava的工具类
	 * @see MoreExecutors#shutdownAndAwaitTermination(ExecutorService, long, TimeUnit)
	 */
	public static boolean gracefulShutdown( ExecutorService threadPool, int shutdownTimeoutMills) {
		return threadPool != null
				? MoreExecutors.shutdownAndAwaitTermination(threadPool, shutdownTimeoutMills, TimeUnit.MILLISECONDS)
				: true;
	}

	/**
	 * @see #gracefulShutdown(ExecutorService, int)
	 */
	public static boolean gracefulShutdown( ExecutorService threadPool, int shutdownTimeout,
			TimeUnit timeUnit) {
		return threadPool != null ? MoreExecutors.shutdownAndAwaitTermination(threadPool, shutdownTimeout, timeUnit)
				: true;
	}

	/**
	 * 创建ThreadFactory，使得创建的线程有自己的名字而不是默认的"pool-x-thread-y"
	 * 
	 * 使用了Guava的工具类
	 * 
	 * @see ThreadFactoryBuilder#build()
	 */
	public static ThreadFactory buildThreadFactory(String threadNamePrefix) {
		if(null==threadNamePrefix){
			throw new RuntimeException("参数不能为空");
		}
		return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
	}

	/**
	 * 可设定是否daemon, daemon线程在主线程已执行完毕时, 不会阻塞应用不退出, 而非daemon线程则会阻塞.
	 * 
	 * @see #buildThreadFactory(String)
	 */
	public static ThreadFactory buildThreadFactory( String threadNamePrefix,  boolean daemon) {
		if(null==threadNamePrefix){
			throw new RuntimeException("参数不能为空");
		}
		return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(daemon).build();
	}

	/**
	 * 防止用户没有捕捉异常导致中断了线程池中的线程, 使得SchedulerService无法执行.
	 * 
	 * 在无法控制第三方包的Runnalbe实现时，调用本函数进行包裹.
	 */
	public static Runnable wrapException( Runnable runnable) {
		if(null==runnable){
			throw new RuntimeException("参数不能为空");
		}
		return new WrapExceptionRunnable(runnable);
	}

	/**
	 * 保证不会有Exception抛出到线程池的Runnable包裹类，防止用户没有捕捉异常导致中断了线程池中的线程, 使得SchedulerService无法执行. 在无法控制第三方包的Runnalbe实现时，使用本类进行包裹.
	 */
	public static class WrapExceptionRunnable implements Runnable {

		private static Logger logger = LoggerFactory.getLogger(WrapExceptionRunnable.class);

		private Runnable runnable;

		public WrapExceptionRunnable(Runnable runnable) {
			Validate.notNull(runnable);
			this.runnable = runnable;
		}

		@Override
		public void run() {
			try {
				runnable.run();
			} catch (Throwable e) {
				// catch any exception, because the scheduled thread will break if the exception thrown to outside.
				logger.error("Unexpected error occurred in task", e);
			}
		}
	}
}

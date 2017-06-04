package com.max256.morpho.common.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import com.google.common.util.concurrent.RateLimiter;
import com.max256.morpho.common.util.concurrent.jsr166e.LongAdder;

public class ConcurrentTools {

	/**
	 * 返回没有激烈CAS冲突的LongAdder, 并发的＋1将在不同的Counter里进行，只在取值时将多个Counter求和.
	 * 
	 * 为了保持JDK版本兼容性，统一采用移植版
	 */
	public static LongAdder newLongAdder() {
		return new LongAdder();
	}

	/**
	 * 返回CountDownLatch
	 */
	public static CountDownLatch countDownLatch(int count) {
		return new CountDownLatch(count);
	}

	/**
	 * 返回CyclicBarrier
	 */
	public static CyclicBarrier cyclicBarrier(int count) {
		return new CyclicBarrier(count);
	}

	/**
	 * 返回漏桶算法的RateLimiter
	 * 
	 * @permitsPerSecond 期望的QPS, RateLimiter将QPS平滑到毫秒级别上，但有蓄水及桶外预借的能力.
	 */
	public static RateLimiter rateLimiter(int permitsPerSecond) {
		return RateLimiter.create(permitsPerSecond);
	}
	
	
}

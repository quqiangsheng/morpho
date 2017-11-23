package com.max256.morpho.common.test;

import org.springframework.stereotype.Component;

/**
 * 用于测试quartz jdbc jobstore存储形式的管理的测试用例
 * @author fbf
 *
 */
@Component("quartzHandle")
public class QuartzHandle {
	public void test() {
		System.out.println("test~~~~~~~~~~");
	}
}

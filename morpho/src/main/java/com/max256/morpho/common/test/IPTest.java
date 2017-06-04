package com.max256.morpho.common.test;



import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.max256.morpho.common.util.ip2region.DbSearcher;


/**
 * @author fbf
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-ip2region.xml")
public class IPTest {
	@Autowired
    private DbSearcher dbSearcher;
	@Test
	public void test() throws Exception {
		//开始时间
		Long data1 =System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			String ip = "1.197.132.201";
			String region = dbSearcher.memorySearch(ip).getRegion();
			String[] regions = StringUtils.split(region, '|');
			System.out.println("ip       "+ip);
			System.out.println("Couuntry "+regions[0]);
			System.out.println("Area     "+regions[1]);
			System.out.println("Province "+regions[2]);
			System.out.println("City     "+regions[3]);
			System.out.println("operator "+regions[4]);
			System.out.println("第"+i+"次搜索");
		}
		
		Long data2 =System.currentTimeMillis();
		System.out.println("相差毫秒"+(data2-data1));

	}
	
}

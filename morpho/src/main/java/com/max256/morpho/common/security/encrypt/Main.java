package com.max256.morpho.common.security.encrypt;

import java.sql.Timestamp;

import com.max256.morpho.common.entity.SysUser;

/**
 * aes+rsa+签名实例
 *
 */
public class Main {

	/**
	 * 使用示例
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
			//握手
			Message tokenResponse=ClientUtils.getTokenAndkeyExchange();
			//要传输的数据
			SysUser user = new SysUser();
			user.setUserName("张三");
			user.setEmail("13@qq.com");
			user.setSalt("afrgefvgdfsrg");
			user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
			//发送
			Message bussResponse=ClientUtils.sendMessage("http://localhost:8080/morpho/encrypt/encrypt", 
						user, tokenResponse);
			//解析返回值
			String txt=ClientUtils.decryptObject(bussResponse, String.class, tokenResponse);		
			System.out.println("服务器返回:"+txt);

	}

}

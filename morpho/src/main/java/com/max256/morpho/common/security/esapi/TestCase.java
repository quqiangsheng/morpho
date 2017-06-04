package com.max256.morpho.common.security.esapi;

import com.max256.morpho.common.security.esapi.ESAPIEncoder.DatabaseCodec;

//测试
public class TestCase {
	

	public static void main(String[] args) throws Throwable {
		
		@SuppressWarnings("unused")
		String result=ESAPI.encryptor().encrypt("hello");
		
		/*System.out.println(ESAPI.encryptor().decrypt(result));
		
		System.out.println(result);*/
		ESAPI.encoder().urlEncode("http://localhost:8080/b/1=1");
		System.out.println(ESAPI.encoder().sqlEncode("http://www.example.com/index.php?username=1’%20or%20’1’%20=%20’1′))", DatabaseCodec.ORACLE));
	}
}

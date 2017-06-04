package com.max256.morpho.common.security.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.shiro.codec.Base64;

public class RSATest {

    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey+" \n\r");
            System.err.println("私钥： \n\r" + privateKey+" \n\r");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception {
       /* test();
        testSign();*/
    	//找许可证文件
		    	 File f = new File("licence.txt");
    			InputStream resourceAsStream = new FileInputStream(f);
    			byte b[]=new byte[4096];     //创建合适文件大小的数组   
    			resourceAsStream.read(b);    //读取文件中的内容到b[]数组   
    			resourceAsStream.close();   
    			String licence= new String(b);   
    			//找许可证的公钥文件
    			 File f1 = new File("licence-key.txt");
     			InputStream resourceAsStream1 = new FileInputStream(f1);
    			
    			byte b1[]=new byte[4096];     //创建合适文件大小的数组   
    			resourceAsStream1.read(b1);    //读取文件中的内容到b[]数组   
    			resourceAsStream1.close();   
    			String licenceKey= new String(b1);   
    			
    			

    			
    			byte[] decodedData = RSAUtils.decryptByPublicKey(Base64.decode(licence), licenceKey);
    	        String target = new String(decodedData);
    	        System.out.println("解密后: \r\n" + target);
    }

    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }
    
}
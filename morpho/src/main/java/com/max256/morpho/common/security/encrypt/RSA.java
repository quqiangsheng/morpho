package com.max256.morpho.common.security.encrypt;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 --------------------------------------------**********--------------------------------------------

 该算法于1977年由美国麻省理工学院MIT(Massachusetts Institute of Technology)的Ronal Rivest，Adi Shamir和Len Adleman三位年轻教授提出，并以三人的姓氏Rivest，Shamir和Adlernan命名为RSA算法，是一个支持变长密钥的公共密钥算法，需要加密的文件快的长度也是可变的!

 所谓RSA加密算法，是世界上第一个非对称加密算法，也是数论的第一个实际应用。它的算法如下：

 1.找两个非常大的质数p和q（通常p和q都有155十进制位或都有512十进制位）并计算n=pq，k=(p-1)(q-1)。

 2.将明文编码成整数M，保证M不小于0但是小于n。

 3.任取一个整数e，保证e和k互质，而且e不小于0但是小于k。加密钥匙（称作公钥）是(e, n)。

 4.找到一个整数d，使得ed除以k的余数是1（只要e和n满足上面条件，d肯定存在）。解密钥匙（称作密钥）是(d, n)。

 加密过程： 加密后的编码C等于M的e次方除以n所得的余数。

 解密过程： 解密后的编码N等于C的d次方除以n所得的余数。

 只要e、d和n满足上面给定的条件。M等于N。

 --------------------------------------------**********--------------------------------------------
 */
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * RSA算法
 * @author fbf
 *
 */
public class RSA {
	//日志
	private static final Logger log = LoggerFactory.getLogger(RSA.class);
	/** 指定key的大小 */
	private static int KEYSIZE = 1024;
	/**
	 * 生成密钥对
	 */
	public static Map<String, String> generateKeyPair() throws Exception {
		/** RSA算法要求有一个可信任的随机数源 */
		SecureRandom sr = new SecureRandom();
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		kpg.initialize(KEYSIZE, sr);
		/** 生成密匙对 */
		KeyPair kp = kpg.generateKeyPair();
		/** 得到公钥 */
		Key publicKey = kp.getPublic();
		byte[] publicKeyBytes = publicKey.getEncoded();
		String pub = new String(Base64.encodeBase64(publicKeyBytes),
				ConfigureEncryptAndDecrypt.CHAR_ENCODING);
		/** 得到私钥 */
		Key privateKey = kp.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		String pri = new String(Base64.encodeBase64(privateKeyBytes),
				ConfigureEncryptAndDecrypt.CHAR_ENCODING);

		Map<String, String> map = new HashMap<String, String>();
		map.put("publicKey", pub);
		map.put("privateKey", pri);
		RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
		BigInteger bint = rsp.getModulus();
		byte[] b = bint.toByteArray();
		byte[] deBase64Value = Base64.encodeBase64(b);
		String retValue = new String(deBase64Value);
		map.put("modulus", retValue);
		return map;
	}

	
	/**
	 * RSA加密
	 * @param source 待加密的信息
	 * @param publicKey 加密使用的公钥
	 * @return 加密后的信息
	 */
	public static String encrypt(String source, String publicKey){
		try {
			Key key = null;
			key = getPublicKey(publicKey);
			/** 得到Cipher对象来实现对源数据的RSA加密 */
			Cipher cipher = null;
			cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.RSA_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] b = source.getBytes();
			/** 执行加密操作 */
			byte[] b1 = null;
			b1 = cipher.doFinal(b);
			return new String(Base64.encodeBase64(b1),ConfigureEncryptAndDecrypt.CHAR_ENCODING);
		}catch (Exception e) {
			throw new RuntimeException("encrypt fail! "+e.getMessage());
		}
	}

	
	/**
	 * RSA解密
	 * @param cryptograph 密文
	 * @param privateKey 解密用的私钥
	 * @return
	 */
	public static String decrypt(String cryptograph, String privateKey){
		try{
			Key key = getPrivateKey(privateKey);
			/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
			Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.RSA_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] b1 = Base64.decodeBase64(cryptograph.getBytes());
			/** 执行解密操作 */
			byte[] b = cipher.doFinal(b1);
			return new String(b);
		}catch (Exception e) {
			throw new RuntimeException("decrypt fail! "+e.getMessage());
		}
	}

	/**
	 * 得到公钥
	 * 
	 * @param key密钥字符串（经过base64编码）
	 */
	public static PublicKey getPublicKey(String key)  {
		//x509编码
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
				Base64.decodeBase64(key.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("getPublicKey fail! "+e.getMessage());
		}
		PublicKey publicKey = null;
		try {
			publicKey = keyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException("InvalidKeySpecException fail! "+e.getMessage());
		}
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key密钥字符串（经过base64编码）
	 */
	public static PrivateKey getPrivateKey(String key) {
		try {
			//私钥是pkcs8编码
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(key.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			return privateKey;
		} catch (Exception e) {
			throw new RuntimeException("getPrivateKey fail! "+e.getMessage());
		}
	}

	/**私钥签名加密内容
	 * @param content 加密内容
	 * @param privateKey 私钥签名
	 * @return 签名   经过base64编码签名内容 形成String
	 */
	public static String sign(String content, String privateKey) {
		String charset = ConfigureEncryptAndDecrypt.CHAR_ENCODING;
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(privateKey.getBytes()));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			//签名算法
			Signature signature = Signature.getInstance(ConfigureEncryptAndDecrypt.SIGN_ALGORITHM);
			//初始化签名密钥
			signature.initSign(priKey);
			//更新签名内容
			signature.update(content.getBytes(charset));
			//	签名
			byte[] signed = signature.sign();
			//base64编码签名内容 形成String
			return new String(Base64.encodeBase64(signed));
		} catch (Exception e) {
			throw new RuntimeException("sign fail! "+e.getMessage());
		}
	}
	
	/**
	 * 签名验证
	 * @param content 内容
	 * @param sign 签名
	 * @param publicKey 公钥
	 * @return
	 */
	public static boolean checkSign(String content, String sign, String publicKey)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode2(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(ConfigureEncryptAndDecrypt.SIGN_ALGORITHM);
			signature.initVerify(pubKey);
			signature.update( content.getBytes("utf-8") );
			boolean bverify = signature.verify( Base64.decode2(sign) );
			return bverify;
		} 
		catch (Exception e) 
		{	
			if(log.isErrorEnabled()){
			 log.error(e.getMessage(), e);
			}
		}
		
		return false;
	}	
	
	/**
	 * 从pem文件中得到公钥
	 * 
	 * PEM编码也是密钥对较常用的编码方式，openssl则是以PEM编码为主，相对DER对人可读性更强，以BASE64编码呈现，外围包上类似-----BEGIN RSA PRIVATE KEY-----。
	 * JCE没有对PEM直接支持的方式，但是可以通过第三方包例如bouncycastle解析，当然如果想要自己理解pem编码结构，也可以自己写代码解析。
	 * @param pemFile
	 * @return
	 */
	public static PublicKey getPublicKeyFromPemFile(File pemFile){
		//准备读取的pem文件 
		byte[] key;
		try {
			key = FileUtils.readFileToByteArray(pemFile);
		} catch (IOException e) {
			throw new RuntimeException("读取pem文件时出错:"+e);
		}  
		//使用jce提供者 BouncyCastleProvider
		Security.addProvider(new BouncyCastleProvider());  
		//字节输入流
		ByteArrayInputStream bais = new ByteArrayInputStream(key);  
		//读取pem
		PEMReader reader = new PEMReader(new InputStreamReader(bais),
			new PasswordFinder() {  
			    @Override  
			    public char[] getPassword() {  
			        return "".toCharArray();  
			    }  
		});  
		KeyPair keyPair = null;
		PublicKey pubk=null;
		try {
			
			Object readObj=reader.readObject();
			if(readObj instanceof KeyPair)
			{	//密钥对
				keyPair = (KeyPair) readObj;
			}
			else if(readObj instanceof PublicKey)
			{	//公钥
				pubk=(PublicKey)readObj;
			}
			else if(readObj instanceof PrivateKey)
			{	//私钥
				
			}
		} catch (IOException e) {
			throw new RuntimeException("读取pem文件时出错:"+e);
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException("读取pem文件时出错:"+e);
			}  
		}
		if(keyPair!=null){
			pubk= keyPair.getPublic();  
			return pubk;
		}

		if(pubk!=null){
			return pubk;
		}else{
			throw new RuntimeException("读取pem文件时出错:无法解析到公钥");
		}

		
		
	}
	
	/**
	 * 从pem文件中得到私钥
	 * 
	 * PEM编码也是密钥对较常用的编码方式，openssl则是以PEM编码为主，相对DER对人可读性更强，以BASE64编码呈现，外围包上类似-----BEGIN RSA PRIVATE KEY-----。
	 * JCE没有对PEM直接支持的方式，但是可以通过第三方包例如bouncycastle解析，当然如果想要自己理解pem编码结构，也可以自己写代码解析。
	 * @param pemFile
	 * @return
	 */
	public static PrivateKey getPrivateKeyFromPemFile(File pemFile){
		//准备读取的pem文件 
		byte[] key;
		try {
			key = FileUtils.readFileToByteArray(pemFile);
		} catch (IOException e) {
			throw new RuntimeException("读取pem文件时出错:"+e);
		}  
		//使用jce提供者 BouncyCastleProvider
		Security.addProvider(new BouncyCastleProvider());  
		//字节输入流
		ByteArrayInputStream bais = new ByteArrayInputStream(key);  
		//读取pem
		PEMReader reader = new PEMReader(new InputStreamReader(bais),
			new PasswordFinder() {  
			    @Override  
			    public char[] getPassword() {  
			        return "".toCharArray();  
			    }  
		});  
		KeyPair keyPair = null;
		PrivateKey privateKey =null;
		try {
			
			Object readObj=reader.readObject();
			if(readObj instanceof KeyPair)
			{	//密钥对
				keyPair = (KeyPair) readObj;
			}
			else if(readObj instanceof PublicKey)
			{	//公钥
				
			}
			else if(readObj instanceof PrivateKey)
			{	//私钥
				privateKey=(PrivateKey)readObj;
			}
		} catch (IOException e) {
			throw new RuntimeException("读取pem文件时出错:"+e);
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException("读取pem文件时出错:"+e);
			}  
		}
		if(keyPair!=null){
			privateKey= keyPair.getPrivate();  
			return privateKey;
		}

		if(privateKey!=null){
			return privateKey;
		}else{
			throw new RuntimeException("读取pem文件时出错:无法解析到私钥");
		}
		
	}
	
	/**
	 * 从pem文件中得到公钥
	 * 
	 * PEM编码也是密钥对较常用的编码方式，openssl则是以PEM编码为主，相对DER对人可读性更强，以BASE64编码呈现，外围包上类似-----BEGIN RSA PRIVATE KEY-----。
	 * JCE没有对PEM直接支持的方式，但是可以通过第三方包例如bouncycastle解析，当然如果想要自己理解pem编码结构，也可以自己写代码解析。
	 * @param pemFile
	 * @return
	 */
	public static PublicKey getPublicKeyFromPemString(String pemFile){
		//准备读取的pem文件 
		byte[] key=pemFile.getBytes();
		//使用jce提供者 BouncyCastleProvider
		Security.addProvider(new BouncyCastleProvider());  
		//字节输入流
		ByteArrayInputStream bais = new ByteArrayInputStream(key);  
		//读取pem
		PEMReader reader = new PEMReader(new InputStreamReader(bais),
			new PasswordFinder() {  
			    @Override  
			    public char[] getPassword() {  
			        return "".toCharArray();  
			    }  
		});  
		KeyPair keyPair = null;
		PublicKey pubk=null;
		try {
			
			Object readObj=reader.readObject();
			if(readObj instanceof KeyPair)
			{	//密钥对
				keyPair = (KeyPair) readObj;
			}
			else if(readObj instanceof PublicKey)
			{	//公钥
				pubk=(PublicKey)readObj;
			}
			else if(readObj instanceof PrivateKey)
			{	//私钥
				
			}
		} catch (IOException e) {
			throw new RuntimeException("读取pem文件时出错:"+e);
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException("读取pem文件时出错:"+e);
			}  
		}
		if(keyPair!=null){
			pubk= keyPair.getPublic();  
			return pubk;
		}

		if(pubk!=null){
			return pubk;
		}else{
			throw new RuntimeException("读取pem文件时出错:无法解析到公钥");
		}

		
		
	}
	
	/**
	 * 从pem文件中得到私钥
	 * 
	 * PEM编码也是密钥对较常用的编码方式，openssl则是以PEM编码为主，相对DER对人可读性更强，以BASE64编码呈现，外围包上类似-----BEGIN RSA PRIVATE KEY-----。
	 * JCE没有对PEM直接支持的方式，但是可以通过第三方包例如bouncycastle解析，当然如果想要自己理解pem编码结构，也可以自己写代码解析。
	 * @param pemFile
	 * @return
	 */
	public static PrivateKey getPrivateKeyFromPemString(String pemFile){
		//准备读取的pem文件 
		byte[] key=pemFile.getBytes();
		//使用jce提供者 BouncyCastleProvider
		Security.addProvider(new BouncyCastleProvider());  
		//字节输入流
		ByteArrayInputStream bais = new ByteArrayInputStream(key);  
		//读取pem
		PEMReader reader = new PEMReader(new InputStreamReader(bais),
			new PasswordFinder() {  
			    @Override  
			    public char[] getPassword() {  
			        return "".toCharArray();  
			    }  
		});  
		KeyPair keyPair = null;
		PrivateKey privateKey =null;
		try {
			
			Object readObj=reader.readObject();
			if(readObj instanceof KeyPair)
			{	//密钥对
				keyPair = (KeyPair) readObj;
			}
			else if(readObj instanceof PublicKey)
			{	//公钥
				
			}
			else if(readObj instanceof PrivateKey)
			{	//私钥
				privateKey=(PrivateKey)readObj;
			}
		} catch (IOException e) {
			throw new RuntimeException("读取pem文件时出错:"+e);
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException("读取pem文件时出错:"+e);
			}  
		}
		if(keyPair!=null){
			privateKey= keyPair.getPrivate();  
			return privateKey;
		}

		if(privateKey!=null){
			return privateKey;
		}else{
			throw new RuntimeException("读取pem文件时出错:无法解析到私钥");
		}
		
	}
	
	
	

}
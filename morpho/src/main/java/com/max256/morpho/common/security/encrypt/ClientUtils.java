package com.max256.morpho.common.security.encrypt;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.util.FileUtils;
import com.max256.morpho.common.util.HttpClientUtils;

/**
 * 
 * 加解密组建客户端使用工具类
 * 谁发送谁就是客户端
 * @author fbf
 *
 */
public class ClientUtils {
	//日志
	private static final Logger log = LoggerFactory.getLogger(EncryptUtils.class);
	
	// 客户端自己的公钥
	private static 	String clientPublicKeyPath = Constants.class.getClassLoader().getResource("").getPath()
					+ "client_public_key.pem";
	//客户端自己的私钥
	private static 	String clientPrivateKeyPath = Constants.class.getClassLoader().getResource("").getPath()
			+ "client_private_key.pem";
	//服务器地址
	private static String serverUrl="http://localhost:8080/morpho/encrypt/getserverpublickey";
	private static	boolean  debugFlag=true;
	

	/**加密和签名  (不推荐直接使用,除非你知道你在做什么推荐使用encryptAndSignObject方法)
	 * 原始内容必须是TreeMap<String, Object> 格式 并且TreeMap中的key不允许有为sign的 该字段被签名使用
	 * @param params 待加密的信息 TreeMap<String, Object> 格式 
	 * @param clientPrivateKey 客户端RSA私钥用于签名
	 * @param serverPublicKey 服务端RSA公钥用于加密客户端生成的AES秘钥
	 * @throws Exception
	 */
	private static Message encryptAndSign(TreeMap<String, Object> params,String clientPrivateKey,String serverPublicKey) throws Exception{
		// 客户端证书私钥生成RSA签名 防止抵赖
		String sign = EncryptUtils.handleRSA(params, clientPrivateKey);
		//把签名信息追加到请求参数中
		params.put("sign", sign);
		if(log.isDebugEnabled()){
			log.debug("原始数据RSA私钥签名:"+sign);
		}
		//序列化为json
		String info = JSON.toJSONString(params,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue);
		//加密前的数据+签名+json
		if(log.isDebugEnabled()){
			log.debug("原始数据+原始数据的RSA签名的JSON:"+info);
		}
		//客户端随机生成对称加密的AES密钥
		String aesKey = RandomUtils.getRandom(16);
		//AES密钥
		if(log.isDebugEnabled()){
			log.debug("加密数据的AES秘钥:"+aesKey);
		}
		//用AES秘钥加密数据
		String data = AES.encryptToBase64(info, aesKey);
		if(log.isDebugEnabled()){
			log.debug("经AES加密后的数据:"+data);
		}
		//使用RSA算法将客户端自己随机生成的AESkey加密 对称秘钥用非对称秘钥加密 用服务端公钥
		String encryptkey = RSA.encrypt(aesKey, serverPublicKey);
		if(log.isDebugEnabled()){
			log.debug("客户端使用服务器RSA公钥加密本次通信的AES 加密后的AES为:"+encryptkey);
		}
		//data为加密后的数据  内容为 aes加密后的（数据+签名）encryptkey内容为服务端rsa公钥加密后的aes秘钥
		Message message=new Message();
		message.setData(data);
		message.setEncryptkey(encryptkey);
		message.setStatus("1");
		message.setInfo("消息以加密");
		return message;
	}
	/**加密和签名 原始内容必须是实现序列化接口的任何对象 (推荐使用)
	 * @param object 待加密的信息 java.io.Serializable格式 
	 * @param clientPrivateKey 客户端RSA私钥用于签名
	 * @param serverPublicKey 服务端RSA公钥用于加密客户端生成的AES秘钥
	 */
	public static Message encryptAndSignObject(Serializable object,String clientPrivateKey,String serverPublicKey) {
		//对象用json序列化
		String objectJson=JSON.toJSONString(object,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue);
		TreeMap<String, Object> params=new TreeMap<>();
		params.put(ConfigureEncryptAndDecrypt.PLAIN_DATA, objectJson);
		try {
			return encryptAndSign(params,clientPrivateKey,serverPublicKey);
		} catch (Exception e) {
			throw new RuntimeException("失败,加密和签名过程中发生异常:"+e.getMessage());
		}
	}
	
	
	/**
	 * 与服务器第一次握手获取token并进行公钥交换
	 * @return
	 */
	public static Message getTokenAndkeyExchange(){
		
		File clientPublicKeyFile = new File(clientPublicKeyPath.substring(1));
		String clientPublicKeyPemString;
		try {
			clientPublicKeyPemString = FileUtils.readFileToString(clientPublicKeyFile,Charset.defaultCharset());
		} catch (IOException e1) {
			return null;
		}
		//发起请求交换公钥
		HttpPost httpPost = new HttpPost(serverUrl);
		//封装请求体
		Message sendMessage=new Message();
		sendMessage.setData(clientPublicKeyPemString);
		StringEntity reqEntity = new StringEntity(JSONObject.toJSONString(sendMessage,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue), "UTF-8");
		reqEntity.setContentType("application/json;charset=utf-8");
		httpPost.setEntity(reqEntity);
		if(debugFlag){
			//设置代理 调试抓包使用
			HttpHost proxy = new HttpHost("localhost"+ "", 8888, "http");  
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			httpPost.setConfig(config); 
		}
		
		//执行
		CloseableHttpResponse response;
		try {
			response = HttpClientUtils.getHttpClientInstance().execute(httpPost);
		} catch (ClientProtocolException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			httpPost.abort();
			return null;
		}
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			try {
				result = EntityUtils.toString(entity, "utf-8");
			} catch (ParseException | IOException e) {
				return null;
			}
		}
		try {
			EntityUtils.consume(entity);
		} catch (IOException e1) {
			return null;
		}
		//result是服务器返回的json字符串解析
		Message responseMessage=null;
		try {
			responseMessage=JSONObject.parseObject(result, Message.class);
		} catch (Exception e) {
			return null;
		}
		//解析成功但状态失败
		if(StringUtils.isBlank(responseMessage.getStatus())||!responseMessage.getStatus().equals("1")){
			return null;
		}
		//读取报文
		String token=responseMessage.getToken();
		try {
			response.close();
		} catch (IOException e) {
			return null;
		}
		//参数校验
		if(StringUtils.isBlank(token)){
			return null;
		}
		if(StringUtils.isBlank(responseMessage.getData())){
			return null;
		}
		return responseMessage;
		
	}

	/**
	 * 发送消息
	 * @param serverUrl 服务地址
	 * @param sendMessage 发送的内容
	 * @param tokenAndKeyMessage 包含token和服务端公钥的Message
	 * @return
	 */
	public static Message sendMessage(String serverUrl,Serializable sendObject,Message tokenAndKeyMessage){
		PublicKey serverPublicKey;
		String serverPublicKeyString;
		File clientPrivateKeyFile;
		PrivateKey clientPrivateKey;
		String clientPrivateKeyString;
		try {
			//获取服务器公钥
			serverPublicKey = RSA.getPublicKeyFromPemString(tokenAndKeyMessage.getData());
			//公钥编码x.509格式
			 serverPublicKeyString = new String(Base64.encodeBase64(serverPublicKey.getEncoded()));
			//客户端私钥文件
			 clientPrivateKeyFile = new File(clientPrivateKeyPath.substring(1));
			// 客户端私钥PrivateKey
			 clientPrivateKey = RSA.getPrivateKeyFromPemFile(clientPrivateKeyFile);
			// 客户端私钥PrivateKey pkcs#8格式
			 clientPrivateKeyString = new String(Base64.encodeBase64(clientPrivateKey.getEncoded()));
		} catch (Exception e) {
			return null;
		}
		Message sendMessage=null;
		//对象用json序列化
		String objectJson=JSON.toJSONString(sendObject,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue);
		TreeMap<String, Object> params=new TreeMap<>();
		params.put(ConfigureEncryptAndDecrypt.PLAIN_DATA, objectJson);
		try {
			sendMessage= encryptAndSign(params,clientPrivateKeyString,serverPublicKeyString);
		} catch (Exception e) {
			throw new RuntimeException("失败,加密和签名过程中发生异常:"+e.getMessage());
		}

		Long start=null;
		if(log.isDebugEnabled()){
			start =System.nanoTime();
		}
		// 准备加密
		// 设置token
		sendMessage.setToken(tokenAndKeyMessage.getToken());
		// 发送消息
		String response11=null;
		try {
			 response11 = HttpClientUtils.doPost(
					serverUrl,//"http://localhost:8081/morpho/encrypt/encrypt", 
					JSONObject.toJSONString(sendMessage), null);
		} catch (Exception e) {
			return null;
		}
		
		//解析响应
		Message responseMessage11=null;
		try {
			 responseMessage11=JSONObject.parseObject(response11, Message.class);
		} catch (Exception e) {
			return null;
		}
		
		Long stop=null;
		if(log.isDebugEnabled()){
			stop =System.nanoTime();
			log.debug("发送消息历时:"+(stop-start));
		}
		return responseMessage11;
	}
	
	/**
	 * 解密 (推荐使用)
	 * 包含验签 针对Object封装
	 * @param response
	 * @param clientPublicKey
	 * @param tokenAndKeyMessage 包装了服务器反悔的token和服务器公钥
	 * @return T类型
	 */
	public static <T> T  decryptObject(Message response,Class<T> clazz,Message tokenAndKeyMessage)  {
		PublicKey serverPublicKey;
		String serverPublicKeyString;
		File clientPrivateKeyFile;
		PrivateKey clientPrivateKey;
		String clientPrivateKeyString;
		try {
			//获取服务器公钥
			serverPublicKey = RSA.getPublicKeyFromPemString(tokenAndKeyMessage.getData());
			//公钥编码x.509格式
			 serverPublicKeyString = new String(Base64.encodeBase64(serverPublicKey.getEncoded()));
			//客户端私钥文件
			 clientPrivateKeyFile = new File(clientPrivateKeyPath.substring(1));
			// 客户端私钥PrivateKey
			 clientPrivateKey = RSA.getPrivateKeyFromPemFile(clientPrivateKeyFile);
			// 客户端私钥PrivateKey pkcs#8格式
			 clientPrivateKeyString = new String(Base64.encodeBase64(clientPrivateKey.getEncoded()));
		} catch (Exception e) {
			return null;
		}

		//验签 服务端私钥签名 用服务端
		boolean checkSign=false;
		try {
			checkSign=EncryptUtils.checkDecryptAndSign(
					response.getData(), 
					response.getEncryptkey(), 
					serverPublicKeyString,
					clientPrivateKeyString
					);
		} catch (Exception e) {
			checkSign=false;
		}
		if(!checkSign){
			return null;
		}
		
		JSONObject jsonObj=null;
		// 通过用客户端私钥解密传送过来的aes密钥
		String aeskey = RSA.decrypt(response.getEncryptkey(), clientPrivateKeyString);
		if(log.isDebugEnabled()){
			log.debug("解密后的aes密钥:"+aeskey);
		}
		// 用aes密钥解密data
		String data = AES.decryptFromBase64(response.getData(), aeskey);
		//转换为json对象
		 jsonObj = JSONObject.parseObject(data);
		if(log.isDebugEnabled()){
			log.debug("解密后的数据JSON内容:"+jsonObj);
		}
				
		//取得复杂对象数据封装
		String objectJsonString=(String) jsonObj.get(ConfigureEncryptAndDecrypt.PLAIN_DATA);
		T result=JSONObject.parseObject(objectJsonString,clazz);
		if(log.isDebugEnabled()){
			log.debug("数据解密后的对象的toString()"+result);
		}
		return result;
	}
}

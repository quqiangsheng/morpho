package com.max256.morpho.common.security.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 加解密组建服务器端工具类
 * 谁接收谁就是服务器端
 * @author fbf
 *
 */
public class ServerUtils {
	//日志
	private static final Logger log = LoggerFactory.getLogger(EncryptUtils.class);

	/**验签
	 * @param request
	 * @param clientPublicKey
	 * @param serverPrivateKey
	 * @return 成功返回true 失败返回false
	 */
	public static boolean checkDecryptAndSign(Message request,String clientPublicKey,String serverPrivateKey)  {
		
		try {
			return EncryptUtils.checkDecryptAndSign(
					request.getData(), 
					request.getEncryptkey(), 
					clientPublicKey,
					serverPrivateKey);
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 解密 (不推荐直接使用, 除非你知道你在做什么推荐使用decryptObject)
	 * 之前需要手动验签
	 * @param request
	 * @param clientPublicKey
	 * @param serverPrivateKey
	 * @return
	 */
	private static JSONObject decrypt(Message request,String clientPublicKey,String serverPrivateKey)  {
		// 通过用服务端私钥解密传送过来的aes密钥
		String aeskey = RSA.decrypt(request.getEncryptkey(), serverPrivateKey);
		if(log.isDebugEnabled()){
			log.debug("解密后的aes密钥:"+aeskey);
		}
		// 用aes密钥解密data
		String data = AES.decryptFromBase64(request.getData(), aeskey);
		//转换为json对象
		JSONObject jsonObj = JSONObject.parseObject(data);
		if(log.isDebugEnabled()){
			log.debug("解密后的数据JSON内容:"+jsonObj);
		}
		return jsonObj;
	}
	
	

	/**
	 * 解密 (推荐使用)
	 * 之前需要手动验签 针对Object封装
	 * @param request
	 * @param clientPublicKey
	 * @param serverPrivateKey
	 * @return
	 */
	public static <T> T  decryptObject(Message request,Class<T> clazz,String clientPublicKey,String serverPrivateKey)  {
		JSONObject jsonObj=decrypt(request,clientPublicKey,serverPrivateKey);
		//取得复杂对象数据封装
		String objectJsonString=(String) jsonObj.get(ConfigureEncryptAndDecrypt.PLAIN_DATA);
		T result=JSONObject.parseObject(objectJsonString,clazz);
		if(log.isDebugEnabled()){
			log.debug("数据解密后的对象的toString()"+result);
		}
		return result;
	}
}

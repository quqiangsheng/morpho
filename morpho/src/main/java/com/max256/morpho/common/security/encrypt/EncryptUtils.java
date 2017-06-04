package com.max256.morpho.common.security.encrypt;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class EncryptUtils {
	//日志
	private static final Logger log = LoggerFactory.getLogger(EncryptUtils.class);
	/**
	 * 生成RSA签名
	 */
	public static String handleRSA(TreeMap<String, Object> map,String privateKey) {
		//拼接结果
		StringBuilder sbuffer = new StringBuilder();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			//只拼接value
			sbuffer.append(entry.getValue());
		}
		String signTemp = sbuffer.toString();
		//签名
		String sign = "";
		//私钥签名
		if (StringUtils.isNotEmpty(privateKey)) {
			sign = RSA.sign(signTemp, privateKey);
		}
		return sign;
	}

	/**
	 * 验证签名(在客户端的角度)
	 * @param data
	 *            业务数据密文
	 * @param encryptKey
	 *            对AesKey加密后的密文
	 * @param serverPublickKey
	 *           服务器端提供的公钥
	 * @param clientPrivateKey
	 *            自己的私钥
	 * @return 验签是否通过
	 * @throws Exception
	 */
	public static boolean checkDecryptAndSign(
			String data, 
			String encryptKey,
			String serverPublickKey, 
			String clientPrivateKey) throws Exception {

		/** 1.解密aes*/
		String AESKey = "";
		try {
			//用客户端RSA私钥解密aes秘钥得到aes密钥的明文
			AESKey = RSA.decrypt(encryptKey, clientPrivateKey);
		} catch (Exception e) {
			e.printStackTrace();
			/** AES密钥解密失败 */
			log.error(e.getMessage(), e);
			//验签失败
			return false;
		}

		/** 2.用aeskey解开data。取得data明文 */
		String realData = AES.decryptFromBase64(data, AESKey);
		//realData实际是json字符串 是map格式 用TreeMap接收
		TreeMap<String, String> map = JSON.parseObject(realData,
				new TypeReference<TreeMap<String, String>>() {
				});

		/** 3.取得data明文sign。 */
		String sign = StringUtils.trimToEmpty(map.get("sign"));

		/** 4.对map中的值进行验证 */
		StringBuilder signData = new StringBuilder();
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();

			/** 把sign参数隔过去 */
			if (StringUtils.equals((String) entry.getKey(), "sign")) {
				continue;
			}
			signData.append(entry.getValue() == null ? "" : entry.getValue());
		}
		
		/** 5. result为true时表明验签通过 */
		boolean result = RSA.checkSign(
				signData.toString(), 
				sign,
				serverPublickKey);

		return result;
	}

	
}

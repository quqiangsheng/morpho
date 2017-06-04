package com.max256.morpho.sys.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.max256.morpho.common.annotation.SysRes;
import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.entity.SysUser;
import com.max256.morpho.common.security.encrypt.Base64;
import com.max256.morpho.common.security.encrypt.ClientUtils;
import com.max256.morpho.common.security.encrypt.Message;
import com.max256.morpho.common.security.encrypt.RSA;
import com.max256.morpho.common.security.shiro.ShiroUtils;
import com.max256.morpho.common.security.shiro.bind.annotation.RequestMessage;
import com.max256.morpho.common.security.shiro.session.ShiroSessionDAO;
import com.max256.morpho.common.util.FileUtils;
import com.max256.morpho.common.web.controller.AbstractBaseController;

/**
 * 加密通信控制器 
 * 用于公钥交换 等
 * 
 * @author fbf
 * @since 2017年5月24日 上午9:08:29
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/encrypt")
@SysRes(name="通信加密",type=Constants.MENU)
@RequiresPermissions("transfer:encrypt:*")
public class EncryptController extends AbstractBaseController {
	
	Logger log=LoggerFactory.getLogger(this.getClass());

	@Autowired
	ShiroSessionDAO shiroSessionDao;

	// 公钥交换和session id分配
	@RequestMapping("/getserverpublickey")
	@SysRes(name="通信加密密钥交换",type=Constants.BUTTON)
	@RequiresPermissions("transfer:encrypt:start")
	public void getServerPublicKey(
			HttpServletResponse resp,
			HttpServletRequest req,
			@RequestBody String requestBody) {
		//获得session
		Session session= ShiroUtils.getSession();
		//准备返回包含token和serverPublicKey的数据
		Message responseMessage=new Message();
		if(StringUtils.isBlank(requestBody)){
			//如果是空
			try {
				responseMessage.setStatus("0");
				responseMessage.setInfo("请求参数为空");
				//返回错误信息
				resp.getWriter().write(JSONObject.toJSONString(responseMessage,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue));
				return ;
			} catch (IOException e) {
				
			}
		}
		//请求参数不为空 则读取其中的客户端公钥
		Message requestMessage=null;
		try {
			requestMessage=JSONObject.parseObject(requestBody, Message.class);
		} catch (Exception e) {
			responseMessage.setStatus("0");
			responseMessage.setInfo("请求参数格式不正确");
			//返回错误信息
			try {
				resp.getWriter().write(JSONObject.toJSONString(responseMessage));
				return ;
			} catch (IOException e1) {
			}
		}
		//无法解析
		if(StringUtils.isBlank(requestMessage.getData())){
			responseMessage.setStatus("0");
			responseMessage.setInfo("请求参数中传递的客户端公钥为空");
			//返回错误信息
			try {
				resp.getWriter().write(JSONObject.toJSONString(responseMessage));
				return ;
			} catch (IOException e1) {
			}
		}
		//解析成功
		String clientPublicKey=requestMessage.getData();
		//设置TOKEN
		responseMessage.setToken((String)session.getId());
		//读取客户端公钥信息放入该session
		PublicKey findClientPublicKey=RSA.getPublicKeyFromPemString(clientPublicKey);
		//公钥编码x.509格式
		String clientPublicKeyString = new String(Base64.encodeBase64(findClientPublicKey.getEncoded()));
		session.setAttribute(Constants.CLIENT_PUBLIC_KEY, clientPublicKeyString);
		//session中也放入服务端私钥和服务端公钥
		String serverPublicKeyPath = Constants.class.getClassLoader().getResource("").getPath()
				+ "server_public_key.pem";
		String serverPrivateKeyPath = Constants.class.getClassLoader().getResource("").getPath()
				+ "server_private_key.pem";
		File serverPublicKeyFile = new File(serverPublicKeyPath.substring(1));
		File serverPrivateKeyFile = new File(serverPrivateKeyPath.substring(1));
		if (!serverPublicKeyFile.exists()||!serverPrivateKeyFile.exists()) {
			responseMessage.setStatus("0");
			responseMessage.setInfo("服务端密钥对不存在,请联系管理员");
			//返回错误信息
			try {
				resp.getWriter().write(JSONObject.toJSONString(responseMessage));
				return ;
			} catch (IOException e1) {
			}
		}
		//读取服务器公钥私钥
		PublicKey findServerPublicKey=RSA.getPublicKeyFromPemFile(serverPublicKeyFile);
		PrivateKey findServerPrivateKey=RSA.getPrivateKeyFromPemFile(serverPrivateKeyFile);
		//格式转换
		String serverPublicKeyString = new String(Base64.encodeBase64(findServerPublicKey.getEncoded()));
		String serverPrivateKeyString = new String(Base64.encodeBase64(findServerPrivateKey.getEncoded()));
		//放入session
		session.setAttribute(Constants.SERVER_PUBLIC_KEY, serverPublicKeyString);
		session.setAttribute(Constants.SERVER_PRIVATE_KEY, serverPrivateKeyString);
		//服务端公钥分发给客户端
		String serverPublicKeyPemString="";
		try {
			serverPublicKeyPemString=FileUtils.readFileToString(serverPublicKeyFile,Charset.defaultCharset());
		} catch (IOException e) {
			
		}
		if(StringUtils.isBlank(serverPublicKeyPemString)){
			responseMessage.setStatus("0");
			responseMessage.setInfo("服务端密钥对不存在,请联系管理员");
			//返回错误信息
			try {
				resp.getWriter().write(JSONObject.toJSONString(responseMessage));
				return ;
			} catch (IOException e1) {
			}
		}
		//组装响应分发
		responseMessage.setData(serverPublicKeyPemString);
		responseMessage.setStatus("1");
		responseMessage.setInfo("公钥交换成功,请客户端保存服务端公钥和token");
		try {
			resp.getWriter().write(JSONObject.toJSONString(responseMessage));
			return ;
		} catch (IOException e) {
			
		}
		
	}

	// 解密
	@RequestMapping("/encrypt")
	@ResponseBody
	@SysRes(name="通信加密解密示例",type=Constants.BUTTON)
	@RequiresPermissions("transfer:encrypt:demo")
	public Message doEncrypt(
			HttpServletRequest req,
			HttpServletResponse resp,
			@RequestMessage SysUser requestSysUser
			) {
		//session只能这么获取 不能通过springMVC自动注入的HttpSession和ShiroUtils获得的session 这两种方式是通过cookie的sid取得session的 在加密通信的方式下唯一依靠token获得session
		Session session=(Session) req.getAttribute(Constants.GET_SESSION_BY_TOKEN);

		//服务端私钥
		String serverPrivateKeyString = (String)session.getAttribute(Constants.SERVER_PRIVATE_KEY);
		//客户端公钥
		String clientPublicKeyString = (String)session.getAttribute(Constants.CLIENT_PUBLIC_KEY);
		//准备返回数据
		if(requestSysUser==null){
			Message response=new Message();
			response.setToken(session.getId().toString());
			response.setStatus("0");
			response.setInfo("数据格式无法解析");
			return response;
			
		}
		//业务处理...............................................................................................................................
		log.info("已经接收到客户端发送了的数据" 
				+ requestSysUser.getEmail() + 
				"\n" + requestSysUser.getSalt() + 
				"\n" + requestSysUser.getUserName());
		//处理结果响应
		File srcFile = new File("C:/Users/admin/Desktop/百度文库下载.txt");
		InputStreamReader read = null;
		try {
			read = new InputStreamReader(new FileInputStream(srcFile));
		} catch (FileNotFoundException e) {
			
		} 
		// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String result = "";
		String lineTxt = null;
		try {
			while ((lineTxt = bufferedReader.readLine()) != null) {
				result = result + lineTxt;
			}
		} catch (IOException e) {
			
		}
		try {
			read.close();
		} catch (IOException e) {
			
		}
		//响应...............................................................................................................................
		Message response = ClientUtils.encryptAndSignObject(result, serverPrivateKeyString, clientPublicKeyString);
		response.setToken(session.getId().toString());
		return response;

	}

}

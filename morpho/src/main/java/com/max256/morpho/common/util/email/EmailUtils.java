package com.max256.morpho.common.util.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.max256.morpho.common.exception.SystemException;

import jodd.mail.Email;
import jodd.mail.EmailAttachment;
import jodd.mail.MailException;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 * 
 * @ClassName: EmailUtils
 * @Description: 邮件发送工具类,封装了jodd的mail工具类
 * @author fbf
 * 
 * 	JCE 即 Java 加密扩展（JCE, Java Cryptography Extension），是一组提供加密、密钥生成、密码协议和消息认证码（MAC, Message Authentication Code）算法的框架和接口包，
 *  支持包括对称密码、不对称密码、分组密码、流密码。该软件还支持安全流和密封对象。
 *  Java8影响邮件发送解决办法：
 *	对应我的 Java 8 的版本，下载 JCE ，解压并将其中的两个 jar 包： local_policy.jar ， US_export_policy.jar 复制到 %JAVA_HOME%\jre\lib\security 即可。
 *
 */
@Component
public class EmailUtils {
	

	@Value("${mail.username}")
	private String username;

	@Value("${mail.password}")
	private String password;
	
	@Value("${mail.host}")
	private String host;
	
	@Value("${mail.port}")
	private String port;
	
	@Value("${mail.timeout}")
	private String timeout;
	
	@Value("${mail.starttls.enable}")
	private String startTLS;


	/**
	 * 发送邮件
	 * 
	 * @param toMailAddress 发送到的目的地
	 * @param subject 邮件主题
	 * @param text 邮件正文
	 * @param attachments 附件数组 如果没有附件请输入null 附件
	 * 			可以有多种表示   FileAttachment
	 * 						DataSourceAttachment
	 * 						InputStreamAttachment
	 * 						ByteArrayAttachment
	 * 			这些都是EmailAttachment的具体子类
	 * @return 是否发送成功
	 */
	public boolean sendMail(String toMailAddress, String subject, String text,EmailAttachment[] attachments) throws AuthenticationFailedException,MailException{
		//注入参数的合法性校验
		if(StringUtils.isBlank(this.username)){
			throw new SystemException( "系统邮件配置错误,请联系管理员",0);
		}
		if(StringUtils.isBlank(this.password)){
			throw new SystemException( "系统邮件配置错误,请联系管理员",0);
		}
		if(StringUtils.isBlank(this.host)){
			throw new SystemException( "系统邮件配置错误,请联系管理员",0);
		}
		if(StringUtils.isBlank(this.port)){
			throw new SystemException( "系统邮件配置错误,请联系管理员",0);
		}
		if(StringUtils.isBlank(this.timeout)){
			this.timeout="0";//超时如果没有配置使用默认值
		}
		//传入的参数合法性校验
		if(StringUtils.isBlank(toMailAddress)){
			throw new SystemException( "收件邮箱地址不能为空",0);
		}
		if(StringUtils.isNotBlank(toMailAddress)){
			/*正则表达式如下：     
			-----------------------------------------------------------------------     
			^(\w+((-\w+)|(\.\w+))*)\+\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$     
			-----------------------------------------------------------------------     
			    
			字符描述：     
			^ ：匹配输入的开始位置。     
			\：将下一个字符标记为特殊字符或字面值。     
			* ：匹配前一个字符零次或几次。     
			+ ：匹配前一个字符一次或多次。     
			(pattern) 与模式匹配并记住匹配。     
			x|y：匹配 x 或 y。     
			[a-z] ：表示某个范围内的字符。与指定区间内的任何字符匹配。     
			\w ：与任何单词字符匹配，包括下划线。     
			$ ：匹配输入的结尾。*/
			 String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";    
			 Pattern regex = Pattern.compile(check);    
			 Matcher matcher = regex.matcher(toMailAddress.trim());    
			 boolean isMatched = matcher.matches();    
			 if(isMatched){
				 //匹配
				
			 }else{				 
				 throw new SystemException("发送失败,收件邮箱地址格式错误",0);
			 }
		}
		
		//准备邮件
		Email email = Email
				.create()
				.from(username)
				.to( toMailAddress.trim())
				.subject(subject)
				.addText(text);
		//判断有没有附件
		if(attachments==null){
			//没有附件
		}else{
			//有附件  遍历附件并且添加到邮件
			for (int i = 0; i < attachments.length; i++) {
				email.attach(attachments[i]);
			}
		}
		
		//session
		SendMailSession session;
		//配置服务器 服务器有两种 ssl加密和不加密 根据配置文件判断
		if(null!=this.startTLS&&this.startTLS.toLowerCase().equals("true")){
			//开启加密
			SmtpSslServer smtpSslServer = SmtpSslServer
					.create(host,Integer.parseInt(port))
					.authenticateWith(username, password)
					.startTlsRequired(true)
				    .timeout(Integer.parseInt(timeout));//超时时间配置
			session = smtpSslServer.createSession();
		}else{
			//不加密
			@SuppressWarnings("rawtypes")
			SmtpServer smtpServer = SmtpServer
					.create(host,Integer.parseInt(port))
					.authenticateWith(username, password)
				    .timeout(Integer.parseInt(timeout));//超时时间配置
			session = smtpServer.createSession();
		}
		session.open();
		session.sendMail(email);
		session.close();
		return true;
	}
	public static void main(String[] args) {
	/*	//测试
		EmailUtils mm=new EmailUtils();
		mm.host="smtp.exmail.qq.com";
		mm.password="";
		mm.port="465";
		mm.timeout="25000";
		mm.username="eamil@max256.com";
		mm.startTLS="true";
		//发送
		try {
			//准备附件
			FileAttachment attach1=new FileAttachment(new File("C:\\Users\\admin\\Desktop\\centos7config.txt"), "centos7config.txt", null);
			FileAttachment attach2=new FileAttachment(new File("C:\\Users\\admin\\Desktop\\rsa.txt"), "rsa.txt", null);
			FileAttachment attach3=new FileAttachment(new File("C:\\Users\\admin\\Desktop\\本地dns.jpg"), "本地dns.jpg", null);
			FileAttachment attach4=new FileAttachment(new File("C:\\Users\\admin\\Desktop\\jd-gui.exe"), "jd-gui.exe", null);
			FileAttachment[] attachs=new FileAttachment[4];
			attachs[0]=attach1;
			attachs[1]=attach2;
			attachs[2]=attach3;
			attachs[3]=attach4;
			mm.sendMail("111@qq.com", "测试"+System.nanoTime(),
					"匹配输入的开始位置" ,attachs);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}

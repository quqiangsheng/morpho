package com.max256.morpho.common.util.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mailer {

	/**
	 * <p>
	 * Field log: 日志
	 * </p>
	 */
	private static final Logger LOG = LogManager.getLogger();

	public static void main(String[] args) {
		try {
			// SMTP地址
			// 邮箱地址和密码
			// 域
			new Mailer("smtp.163.com", "true", null, "fringeframework@163.com",
					"wangkang123456").send(
					new String[] { "Omdsupport@CSVW.COM" }, null, null,
					"demo_title", "<h3>你好,陆骞,测试邮件,无需理会</h3>");
			LOG.debug("send sucess!!!");
		} catch (Exception e) {
			LOG.error("send error", e);
		}
	}
	private String host;
	private String auth;
	private String username;
	private String domainUser;

	private String password;

	public Mailer(String host, String auth, String domainUser, String username,
			String password) {
		super();
		this.host = host;
		this.auth = auth;
		this.domainUser = domainUser;
		this.username = username;
		this.password = password;
	}

	public boolean send(String[] to, String[] cc, String[] bcc, String subject,
			String content) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", auth);
		Session s = Session.getInstance(props);

		MimeMessage message = new MimeMessage(s);

		InternetAddress from = new InternetAddress(username);
		message.setFrom(from);
		InternetAddress[] toaddress = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++)
			toaddress[i] = new InternetAddress(to[i]);
		message.setRecipients(Message.RecipientType.TO, toaddress);

		if (cc != null) {
			InternetAddress[] ccaddress = new InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++)
				ccaddress[i] = new InternetAddress(cc[i]);
			message.setRecipients(Message.RecipientType.CC, ccaddress);
		}

		if (bcc != null) {
			InternetAddress[] bccaddress = new InternetAddress[bcc.length];
			for (int i = 0; i < bcc.length; i++)
				bccaddress[i] = new InternetAddress(bcc[i]);
			message.setRecipients(Message.RecipientType.BCC, bccaddress);
		}
		message.setSubject(subject);
		message.setSentDate(new Date());

		BodyPart mdp = new MimeBodyPart();
		mdp.setContent(content, "text/html;charset=utf-8");
		Multipart mm = new MimeMultipart();
		mm.addBodyPart(mdp);
		message.setContent(mm);

		message.saveChanges();
		Transport transport = s.getTransport("smtp");
		transport.connect(host, (null == domainUser) ? username : domainUser,
				password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		return true;
	}
}

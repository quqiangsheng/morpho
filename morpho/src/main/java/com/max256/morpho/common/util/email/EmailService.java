package com.max256.morpho.common.util.email;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.max256.morpho.common.exception.SystemException;

public class EmailService {
	private JavaMailSenderImpl sender;
	private String defaultToAddress = "";

	// 发送HTML文件
	public void sendHTMLMail(MailSenderInfo info) throws MessagingException {
		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "UTF-8");// false表示非marltipart,UTF-8为字符编码

		helper.setFrom(sender.getUsername());
		helper.setTo(StringUtils.isEmpty(info.getToAddress()) ? this.defaultToAddress
				: info.getToAddress());
		helper.setSubject(info.getSubject());
		helper.setText(info.getContent(), true);// 设置为true表示发送的是HTML
		helper.setSentDate(new Date());

		sender.send(msg);
	}

	// 发送Marltipart文件,包含附件(未测试)
	public void sendMarltipartMail(MailSenderInfo info) throws SystemException {
		try {
			MimeMessage msg = sender.createMimeMessage();
			// true表示非arltipart,UTF-8为字符编码
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
			helper.setSubject(info.getSubject());
			helper.setFrom(sender.getUsername());
			helper.setTo(info.getToAddress());
			String[] files = info.getAttachFileNames();// 附件
			if (files != null && files.length > 0) {
				for (String fileName : files) {
					// 获取资源文件
					ClassPathResource file = new ClassPathResource(fileName);
					helper.addAttachment(file.getFilename(), file.getFile());// 设置附件的名称及其文件流
				}
			}
			sender.send(msg);
		} catch (Exception e) {
			throw new SystemException("发送带附件的邮件时发生异常,异常信息:"+e.getMessage(),0);
		}
	}

	// 发送文本文件
	public void sendTextMail(MailSenderInfo info) {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom(sender.getUsername());
		msg.setTo(StringUtils.isEmpty(info.getToAddress()) ? this.defaultToAddress
				: info.getToAddress());
		msg.setSubject(info.getSubject());
		msg.setText(info.getContent());
		msg.setSentDate(new Date());

		sender.send(msg);
	}

	public void setDefaultToAddress(String defaultToAddress) {
		this.defaultToAddress = defaultToAddress;
	}

	public void setSender(JavaMailSenderImpl sender) {
		this.sender = sender;
	}
}

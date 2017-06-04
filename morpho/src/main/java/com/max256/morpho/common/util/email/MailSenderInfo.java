package com.max256.morpho.common.util.email;

public class MailSenderInfo {
	// 邮件接收者的地址
	private String toAddress;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private String[] attachFileNames;

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public String getContent() {
		return content;
	}

	public String getSubject() {
		return subject;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

}

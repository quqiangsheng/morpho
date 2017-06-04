package com.max256.morpho.common.security.encrypt;

import java.io.Serializable;

/**
 * 加密传输的Http Body体 请求时使用
 * @author fbf
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private  String data;//传输的数据
	private  String encryptkey;//加密的秘钥  AES秘钥已经过RSA公钥加密
	private  String token;//回话token
	private  String timestamp=Long.toString(System.currentTimeMillis());//时间戳
	private  String status;//消息状态 1成功 0失败
	private  String info;//消息 可以放入错误原因
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((encryptkey == null) ? 0 : encryptkey.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (encryptkey == null) {
			if (other.encryptkey != null)
				return false;
		} else if (!encryptkey.equals(other.encryptkey))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Message [data=" + data + ", encryptkey=" + encryptkey + ", token=" + token + ", timestamp=" + timestamp
				+ ", status=" + status + ", info=" + info + "]";
	}
	public Message(String data, String encryptkey, String token, String timestamp, String status, String info) {
		super();
		this.data = data;
		this.encryptkey = encryptkey;
		this.token = token;
		this.timestamp = timestamp;
		this.status = status;
		this.info = info;
	}
	public Message() {
		super();
		
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getEncryptkey() {
		return encryptkey;
	}
	public void setEncryptkey(String encryptkey) {
		this.encryptkey = encryptkey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	

	

}

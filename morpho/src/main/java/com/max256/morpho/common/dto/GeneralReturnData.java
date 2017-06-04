package com.max256.morpho.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 统一返回数据
 * 用于ajax统一返回的模板
 * 所有其他json dto的父类
 * 
 * @author fbf
 */
public class GeneralReturnData<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	//存储的消息
	protected ArrayList<String> messages = new ArrayList<String>();
	//状态1成功 0失败 更具体的错误码自定义 默认1成功
	protected String status="1";
	protected T data;//封装要传递的业务数据
	protected String info;//封装要传递状态说明 比如 执行成功,网络IO错误 等这些信息
	protected Long t = System.nanoTime();//JSON对象产生时间
	protected LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
	public ArrayList<String> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Long getT() {
		return t;
	}
	public void setT(Long t) {
		this.t = t;
	}
	public LinkedHashMap<Object, Object> getMap() {
		return map;
	}
	public void setMap(LinkedHashMap<Object, Object> map) {
		this.map = map;
	}
	@Override
	public String toString() {
		return "GeneralReturnData [messages=" + messages + ", status=" + status + ", data=" + data + ", info=" + info
				+ ", t=" + t + ", map=" + map + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		return result;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneralReturnData other = (GeneralReturnData) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (t == null) {
			if (other.t != null)
				return false;
		} else if (!t.equals(other.t))
			return false;
		return true;
	}
	public GeneralReturnData(ArrayList<String> messages, String status, T data, String info, Long t,
			LinkedHashMap<Object, Object> map) {
		super();
		this.messages = messages;
		this.status = status;
		this.data = data;
		this.info = info;
		this.t = t;
		this.map = map;
	}
	public GeneralReturnData() {
		super();
		
	}
	
	
	

}

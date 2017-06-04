package com.max256.morpho.common.util;

import java.io.Serializable;

/**
 * 加密后的密码和salt包装类 用于配合PasswordUtils包装它生成的password和salt
 * 
 * @author fbf
 * 
 */
public class PasswordData implements Serializable {

	private static final long serialVersionUID = -8406826170871714856L;
	private String password;
	private String salt;

	public PasswordData() {
		super();

	}

	/**
	 * @param password加密后的密码
	 * @param salt用于加密的salt
	 */
	public PasswordData(String password, String salt) {
		super();
		this.password = password;
		this.salt = salt;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordData other = (PasswordData) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		return true;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
		return result;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "PasswordData [password=" + password + ", salt=" + salt + "]";
	}

}

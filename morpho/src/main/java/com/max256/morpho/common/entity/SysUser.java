package com.max256.morpho.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户表
 * 
 * @author fbf
 */
@Entity
@Table(name="sys_user")
public class SysUser implements java.io.Serializable {
	@Transient
	private static final long serialVersionUID = -5328380264817910868L;
	@Transient
	public static String OK = "1";// 账号状态字段 1表示账号正常没有被锁定
	@Transient
	public static String STOP = "0";// 账号停用 状态码1
	
	@Id
	@Column(name="uuid")
	private String uuid;// 主键 uuid
	@Column(name="user_id")
	private String userId;// 用户编号
	@Column(name="user_name")
	private String userName;// 用户名
	@Column(name="user_nickname")
	private String userNickname;// 昵称
	@Column(name="user_password")
	private String userPassword;// 密码
	@Column(name="salt")
	private String salt;// 盐 密码加密用
	@Column(name="sys_role_ids")
	private String sysRoleIds; // 拥有的角色列表 角色id
	@Column(name="sys_organization_id")
	private String sysOrganizationId;// 所属机构id
	@Column(name="email")
	private String email;// 电子邮件地址
	@Column(name="is_valid")
	private String isValid;// 是否可用
	@Column(name="register_time")
	private Timestamp registerTime;// 注册时间
	public String getUuid() {
		return uuid;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public String getSalt() {
		return salt;
	}
	public String getSysRoleIds() {
		return sysRoleIds;
	}
	public String getSysOrganizationId() {
		return sysOrganizationId;
	}
	public String getEmail() {
		return email;
	}
	public String getIsValid() {
		return isValid;
	}
	public Timestamp getRegisterTime() {
		return registerTime;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public void setSysRoleIds(String sysRoleIds) {
		this.sysRoleIds = sysRoleIds;
	}
	public void setSysOrganizationId(String sysOrganizationId) {
		this.sysOrganizationId = sysOrganizationId;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
		result = prime * result
				+ ((registerTime == null) ? 0 : registerTime.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
		result = prime
				* result
				+ ((sysOrganizationId == null) ? 0 : sysOrganizationId
						.hashCode());
		result = prime * result
				+ ((sysRoleIds == null) ? 0 : sysRoleIds.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userNickname == null) ? 0 : userNickname.hashCode());
		result = prime * result
				+ ((userPassword == null) ? 0 : userPassword.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		SysUser other = (SysUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (isValid == null) {
			if (other.isValid != null)
				return false;
		} else if (!isValid.equals(other.isValid))
			return false;
		if (registerTime == null) {
			if (other.registerTime != null)
				return false;
		} else if (!registerTime.equals(other.registerTime))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		if (sysOrganizationId == null) {
			if (other.sysOrganizationId != null)
				return false;
		} else if (!sysOrganizationId.equals(other.sysOrganizationId))
			return false;
		if (sysRoleIds == null) {
			if (other.sysRoleIds != null)
				return false;
		} else if (!sysRoleIds.equals(other.sysRoleIds))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userNickname == null) {
			if (other.userNickname != null)
				return false;
		} else if (!userNickname.equals(other.userNickname))
			return false;
		if (userPassword == null) {
			if (other.userPassword != null)
				return false;
		} else if (!userPassword.equals(other.userPassword))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SysUser [uuid=" + uuid + ", userId=" + userId + ", userName="
				+ userName + ", userNickname=" + userNickname
				+ ", userPassword=" + userPassword + ", salt=" + salt
				+ ", sysRoleIds=" + sysRoleIds + ", sysOrganizationId="
				+ sysOrganizationId + ", email=" + email + ", isValid="
				+ isValid + ", registerTime=" + registerTime + "]";
	}
	
	

}
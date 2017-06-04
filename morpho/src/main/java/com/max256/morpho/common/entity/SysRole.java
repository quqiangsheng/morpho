package com.max256.morpho.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 系统角色表
 * 
 * @author fbf
 * 
 */
@Entity
@Table(name="sys_role")
public class SysRole implements Serializable {
	@Transient
	private static final long serialVersionUID = -6038348467230784741L;
	@Id
	@Column(name="uuid")
	private String uuid; // 主键
	@Column(name="role_id")
	private String roleId; // 角色编号
	@Column(name="role_name")
	private String roleName; // 角色标识名称 程序中判断使用,如"admin"
	@Column(name="role_desc")
	private String roleDesc; // 角色描述,UI界面显示使用
	@Column(name="resource_ids")
	private String resourceIds; // 拥有的资源 资源字符串
	@Column(name="is_valid")
	private String isValid; // 是否可用,如果不可用将不会添加给用户
	public String getUuid() {
		return uuid;
	}
	public String getRoleId() {
		return roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public String getResourceIds() {
		return resourceIds;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
		result = prime * result
				+ ((resourceIds == null) ? 0 : resourceIds.hashCode());
		result = prime * result
				+ ((roleDesc == null) ? 0 : roleDesc.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result
				+ ((roleName == null) ? 0 : roleName.hashCode());
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
		SysRole other = (SysRole) obj;
		if (isValid == null) {
			if (other.isValid != null)
				return false;
		} else if (!isValid.equals(other.isValid))
			return false;
		if (resourceIds == null) {
			if (other.resourceIds != null)
				return false;
		} else if (!resourceIds.equals(other.resourceIds))
			return false;
		if (roleDesc == null) {
			if (other.roleDesc != null)
				return false;
		} else if (!roleDesc.equals(other.roleDesc))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
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
		return "SysRole [uuid=" + uuid + ", roleId=" + roleId + ", roleName="
				+ roleName + ", roleDesc=" + roleDesc + ", resourceIds="
				+ resourceIds + ", isValid=" + isValid + "]";
	}

	
}

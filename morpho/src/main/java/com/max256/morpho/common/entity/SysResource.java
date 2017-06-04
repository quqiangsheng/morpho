package com.max256.morpho.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * SysResource 系统资源
 * 
 * fbf
 * 
 */
@Entity
@Table(name="sys_resource")
public class SysResource implements Serializable {
	@Transient
	private static final long serialVersionUID = 5367150089836913993L;// 序列化id

	@Id
	@Column(name="uuid")
	private String uuid;// 主键
	@Column(name="resource_id")
	private Integer resourceId; // 编号
	@Column(name="resource_name")
	private String resourceName; // 资源节点名称
	@Column(name="resource_type")
	private String resourceType ;// 资源类型 1菜单或者2按钮 默认是1菜单
	@Column(name="resource_url")
	private String resourceUrl; // 访问资源路径
	@Column(name="permission")
	private String permission; // 权限字符串
	@Column(name="parent_id")
	private Integer parentId; // 父编号

	@Column(name="is_valid")
	private String isValid;// 是否停用

	/**
	 * 判断是否为根节点 因为parentId为String类型 用equals来判断 只能有一个根节点所有资源权限由这一个根节点发展而来 根节点id=0
	 * uuid=0 根节点的父节点parentId="-1" 这样的节点为根节点
	 * 
	 * @return boolean
	 */
	@Transient
	public boolean isRootNode() {
		return parentId.equals("-1");
	}




	public String getUuid() {
		return uuid;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getResourceType() {
		return resourceType;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public String getPermission() {
		return permission;
	}

	public Integer getParentId() {
		return parentId;
	}



	public String getIsValid() {
		return isValid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}



	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result + ((resourceType == null) ? 0 : resourceType.hashCode());
		result = prime * result + ((resourceUrl == null) ? 0 : resourceUrl.hashCode());
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
		SysResource other = (SysResource) obj;
		if (isValid == null) {
			if (other.isValid != null)
				return false;
		} else if (!isValid.equals(other.isValid))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		if (resourceName == null) {
			if (other.resourceName != null)
				return false;
		} else if (!resourceName.equals(other.resourceName))
			return false;
		if (resourceType == null) {
			if (other.resourceType != null)
				return false;
		} else if (!resourceType.equals(other.resourceType))
			return false;
		if (resourceUrl == null) {
			if (other.resourceUrl != null)
				return false;
		} else if (!resourceUrl.equals(other.resourceUrl))
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
		return "SysResource [uuid=" + uuid + ", resourceId=" + resourceId + ", resourceName=" + resourceName
				+ ", resourceType=" + resourceType + ", resourceUrl=" + resourceUrl + ", permission=" + permission
				+ ", parentId=" + parentId + ", isValid=" + isValid + "]";
	}


	
	

}

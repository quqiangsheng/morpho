package com.max256.morpho.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * SysOrg 组织机构表
 */
@Entity
@Table(name="sys_organization")
public class SysOrganization implements java.io.Serializable {
	@Transient
	private static final long serialVersionUID = 3293573248676278812L;
	@Id
	@Column(name="uuid")
	private String uuid;// 主键
	@Column(name="org_desc")
	private String orgDesc;// 组织机构介绍
	@Column(name="is_valid")
	private String isValid;// 是否停用
	@Column(name="org_id")
	private Integer orgId;// 当前机构编号
	@Column(name="parent_id")
	private Integer parentId; // 上级机构编号
	@Column(name="org_name")
	private String orgName;// 当前机构名字
	@Column(name="register_time")
	private Date registerTime; // 此机构的加入的时间


	@Transient
	// 判断该机构是否为顶级机构 因为parentId为String类型 用equals来判断
	public boolean isRootNode() {
		return parentId.equals("-1");
	}



	public String getUuid() {
		return uuid;
	}

	public String getOrgDesc() {
		return orgDesc;
	}

	public String getIsValid() {
		return isValid;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public Integer getParentId() {
		return parentId;
	}



	public String getOrgName() {
		return orgName;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}



	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
		result = prime * result + ((orgDesc == null) ? 0 : orgDesc.hashCode());
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
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
		SysOrganization other = (SysOrganization) obj;
		if (isValid == null) {
			if (other.isValid != null)
				return false;
		} else if (!isValid.equals(other.isValid))
			return false;
		if (orgDesc == null) {
			if (other.orgDesc != null)
				return false;
		} else if (!orgDesc.equals(other.orgDesc))
			return false;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (registerTime == null) {
			if (other.registerTime != null)
				return false;
		} else if (!registerTime.equals(other.registerTime))
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
		return "SysOrganization [uuid=" + uuid + ", orgDesc=" + orgDesc + ", isValid=" + isValid + ", orgId=" + orgId
				+ ", parentId=" + parentId + ", orgName=" + orgName + ", registerTime=" + registerTime + "]";
	}




	

}
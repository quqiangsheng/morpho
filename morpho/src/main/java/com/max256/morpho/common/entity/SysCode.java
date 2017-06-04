package com.max256.morpho.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 数据字典表
 */
@Entity
@Table(name="sys_code")
public class SysCode implements java.io.Serializable {
	@Transient
	private static final long serialVersionUID = -7961551963263558737L;
	@Id
	@Column(name="uuid")
	private String uuid;//主键
	@Column(name="code_id")
	private String codeId;//字典id
	@Column(name="code_name")
	private String codeName;//字典名字
	@Column(name="code_text")
	private String codeText;//字典文本内容
	@Column(name="code_value")
	private String codeValue;//字典的值
	@Column(name="code_desc")
	private String codeDesc;//字典描述
	@Column(name="is_valid")
	private String isValid;//是否有效
	
	public String getUuid() {
		return uuid;
	}
	public String getCodeId() {
		return codeId;
	}
	public String getCodeName() {
		return codeName;
	}
	public String getCodeText() {
		return codeText;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public String getCodeDesc() {
		return codeDesc;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeDesc == null) ? 0 : codeDesc.hashCode());
		result = prime * result + ((codeId == null) ? 0 : codeId.hashCode());
		result = prime * result
				+ ((codeName == null) ? 0 : codeName.hashCode());
		result = prime * result
				+ ((codeText == null) ? 0 : codeText.hashCode());
		result = prime * result
				+ ((codeValue == null) ? 0 : codeValue.hashCode());
		result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
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
		SysCode other = (SysCode) obj;
		if (codeDesc == null) {
			if (other.codeDesc != null)
				return false;
		} else if (!codeDesc.equals(other.codeDesc))
			return false;
		if (codeId == null) {
			if (other.codeId != null)
				return false;
		} else if (!codeId.equals(other.codeId))
			return false;
		if (codeName == null) {
			if (other.codeName != null)
				return false;
		} else if (!codeName.equals(other.codeName))
			return false;
		if (codeText == null) {
			if (other.codeText != null)
				return false;
		} else if (!codeText.equals(other.codeText))
			return false;
		if (codeValue == null) {
			if (other.codeValue != null)
				return false;
		} else if (!codeValue.equals(other.codeValue))
			return false;
		if (isValid == null) {
			if (other.isValid != null)
				return false;
		} else if (!isValid.equals(other.isValid))
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
		return "SysCode [uuid=" + uuid + ", codeId=" + codeId + ", codeName="
				+ codeName + ", codeText=" + codeText + ", codeValue="
				+ codeValue + ", codeDesc=" + codeDesc + ", isValid=" + isValid
				+ "]";
	}
	


}
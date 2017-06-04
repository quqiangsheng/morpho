package com.max256.morpho.common.dto;

import java.util.List;

/**
 * zTree专用格式json对象 
 * 用于返回时生成json格式字符串
 * 
 * @author：fbf
 */
public class TreeReturnData extends GeneralReturnData<String> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;// 节点编号
	private Integer pId;// 父节点编号
	private String name;// 节点名称
	private Boolean open = false;// true 表示节点为 展开 状态 false 表示节点为 折叠 状态 默认false
	private Boolean checked = false;// check 默认选中
	private List<TreeReturnData> children;
	private String icon;// 默认值：无 节点自定义图标的 URL 路径。
	private String url; // 节点链接的目标 URL

	private String comment;// 其他信息，扩展用

	public List<TreeReturnData> getChildren() {
		return children;
	}

	public String getComment() {
		return comment;
	}

	public String getIcon() {
		return icon;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getUrl() {
		return url;
	}

	public void setChildren(List<TreeReturnData> children) {
		this.children = children;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	




	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}




	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TreeReturnData() {
		super();
	}

	@Override
	public String toString() {
		return "TreeReturnData [id=" + id + ", pId=" + pId + ", name=" + name + ", open=" + open + ", check=" + checked
				+ ", children=" + children + ", icon=" + icon + ", url=" + url + ", comment=" + comment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((checked == null) ? 0 : checked.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((open == null) ? 0 : open.hashCode());
		result = prime * result + ((pId == null) ? 0 : pId.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeReturnData other = (TreeReturnData) obj;
		if (checked == null) {
			if (other.checked != null)
				return false;
		} else if (!checked.equals(other.checked))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (open == null) {
			if (other.open != null)
				return false;
		} else if (!open.equals(other.open))
			return false;
		if (pId == null) {
			if (other.pId != null)
				return false;
		} else if (!pId.equals(other.pId))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	
}

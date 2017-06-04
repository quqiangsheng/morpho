package com.max256.morpho.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 返回easyui datagrid的json数据
 * 
 * @author fbf
 * 
 * 
 */
public class DataGridReturnData<T> extends GeneralReturnData<T> implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private List<T> rows;//每条记录
	private long total;//总记录数
	private List<Map<Object, Object>> footer = new ArrayList<>();//页脚
	private int pageNumber;// 当前页码
	private int pageSize;//每页多少条
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<Map<Object, Object>> getFooter() {
		return footer;
	}
	/**
	 * @param footer the footer to set
	 */
	public void addFooter(Map<Object, Object> footer) {
		this.footer.add(footer);
	}

	public void setFooter(List<Map<Object, Object>> footer) {
		this.footer = footer;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@Override
	public String toString() {
		return "DataGridReturnData [rows=" + rows + ", total=" + total + ", footer=" + footer + ", pageNumber="
				+ pageNumber + ", pageSize=" + pageSize + ", messages=" + messages + ", status=" + status + ", data="
				+ data + ", info=" + info + ", t=" + t + ", map=" + map + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((footer == null) ? 0 : footer.hashCode());
		result = prime * result + pageNumber;
		result = prime * result + pageSize;
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		result = prime * result + (int) (total ^ (total >>> 32));
		return result;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataGridReturnData other = (DataGridReturnData) obj;
		if (footer == null) {
			if (other.footer != null)
				return false;
		} else if (!footer.equals(other.footer))
			return false;
		if (pageNumber != other.pageNumber)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		if (total != other.total)
			return false;
		return true;
	}

	

}

package com.max256.morpho.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 返回jqgrid的json数据
 * 
 * @author fbf
 */
public class ListPageReturnData<T> extends GeneralReturnData<T> implements Serializable {

	private static final long serialVersionUID =1L;
	// 总记录数
	private int totalCount;
	// 每页记录数
	private int pageSize;
	// 总页数
	private int totalPage;
	// 当前页数
	private int currPage;
	// 列表数据
	private List<T> list;
	/**
	 * 分页
	 * 
	 * @param list
	 *            列表数据
	 * @param totalCount
	 *            总记录数
	 * @param pageSize
	 *            每页记录数
	 * @param currPage
	 *            当前页数
	 */
	public ListPageReturnData(List<T> list, int totalCount, int pageSize,
			int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}
	
	public ListPageReturnData() {
		super();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	
	public String getStatus() {
		return status;
	}
	public T getData() {
		return data;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setData(T data) {
		this.data = data;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + currPage;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + pageSize;
		result = prime * result + totalCount;
		result = prime * result + totalPage;
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
		ListPageReturnData other = (ListPageReturnData) obj;
		if (currPage != other.currPage)
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (totalCount != other.totalCount)
			return false;
		if (totalPage != other.totalPage)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListPageReturnData [totalCount=" + totalCount + ", pageSize=" + pageSize + ", totalPage=" + totalPage
				+ ", currPage=" + currPage + ", list=" + list + ", messages=" + messages + ", status=" + status
				+ ", data=" + data + ", info=" + info + ", t=" + t + ", map=" + map + "]";
	}


	

	
	
}

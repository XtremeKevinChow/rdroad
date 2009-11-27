package com.magic.crm.common;

import java.io.Serializable;

/**
 * @author Water
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PageAttribute implements Serializable {
	private int nPageNo = 1;
	private int nRecordCount = 0;
	/*需要执行翻页的sql*/
	private String sql = "";
	/* 每一页的记录条数 */
	private int pageSize = 0;	
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setMaxResults(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return Returns the nPageCount.
	 */
	public int getPageCount() {
		int nPageCount = getRecordCount() / getMaxResults();
		if (getRecordCount() % getMaxResults() > 0)
			nPageCount++;
		
		if(nPageCount == 0) nPageCount = 1;
		return nPageCount;
	}
	
	/**
	 * @return 得到起始的记录位置
	 */
	public int getStartPosition() {
		return (getPageNo() - 1) * getMaxResults();
	}
	
	/**
	 * @return 一页可以显示的最多记录条数
	 */
	public int getMaxResults() {
		return (pageSize > 0)?pageSize:Constants.PAGESIZE;
	}
	
	/**
	 * @return Returns the nPageNo.
	 */
	public int getPageNo() {
		if(nPageNo < 1) return nPageNo = 1;
		if(nPageNo > getPageCount()) nPageNo = getPageCount();
		return nPageNo;
	}
	
	/**
	 * @param pageNo The nPageNo to set.
	 */
	public void setPageNo(int pageNo) {
		nPageNo = pageNo;
	}
	
	/**
	 * @return Returns the nRecordCount.
	 */
	public int getRecordCount() {
		return nRecordCount;
	}
	
	/**
	 * @param recordCount The nRecordCount to set.
	 */
	public void setRecordCount(int recordCount) {
		nRecordCount = recordCount;
	}
	/**
	 * @return Returns the sql.
	 */
	public String getSql() {
		return sql;
	}
	/**
	 * @param sql The sql to set.
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public PageAttribute(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getFrom() {
		return (nPageNo -1) * pageSize;
	}
	public int getTo() {
		return nPageNo * pageSize;
	}
}

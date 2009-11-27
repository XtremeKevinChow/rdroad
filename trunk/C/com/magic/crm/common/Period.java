/**
 * Period.java
 * 2008-4-23
 * ÏÂÎç04:58:37
 * user
 * Period
 */
package com.magic.crm.common;

import java.sql.Date;
/**
 * @author user
 *
 */
public class Period {
	
	public Period(Date beginDate, Date endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	
	/** the date begin */
	protected Date beginDate;
	
	/** the date end */
	protected Date endDate;
	
	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * validate if the period is legal
	 * @return
	 */
	public boolean validate () {
		if (getBeginDate().after(getEndDate())) {
			return false;
		}
		return true;
	}
}

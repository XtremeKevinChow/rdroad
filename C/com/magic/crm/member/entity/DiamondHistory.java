/**
 * DiamondHistory.java
 * 2008-4-22
 * ÏÂÎç04:00:34
 * user
 * DiamondHistory
 */
package com.magic.crm.member.entity;

import java.sql.Date;

/**
 * @author user
 *
 */
public class DiamondHistory {
	private int hisId;
	private int mbrId;
	private int diaCount;
	private int ioType;
	private int orderId;
	private int reqId;
	private String packageNo;
	private Date opTime;
	private Date beginDate;
	private Date endDate;
	private int status;
	private Date excTime;
	private int excOperator;
	private String remark;
	private int snId;
	private int excSeqn;
	
	/**
	 * @return the excSeqn
	 */
	public int getExcSeqn() {
		return excSeqn;
	}
	/**
	 * @param excSeqn the excSeqn to set
	 */
	public void setExcSeqn(int excSeqn) {
		this.excSeqn = excSeqn;
	}
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
	 * @return the diaCount
	 */
	public int getDiaCount() {
		return diaCount;
	}
	/**
	 * @param diaCount the diaCount to set
	 */
	public void setDiaCount(int diaCount) {
		this.diaCount = diaCount;
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
	 * @return the excOperator
	 */
	public int getExcOperator() {
		return excOperator;
	}
	/**
	 * @param excOperator the excOperator to set
	 */
	public void setExcOperator(int excOperator) {
		this.excOperator = excOperator;
	}
	/**
	 * @return the excTime
	 */
	public Date getExcTime() {
		return excTime;
	}
	/**
	 * @param excTime the excTime to set
	 */
	public void setExcTime(Date excTime) {
		this.excTime = excTime;
	}
	/**
	 * @return the hisId
	 */
	public int getHisId() {
		return hisId;
	}
	/**
	 * @param hisId the hisId to set
	 */
	public void setHisId(int hisId) {
		this.hisId = hisId;
	}
	/**
	 * @return the ioType
	 */
	public int getIoType() {
		return ioType;
	}
	/**
	 * @param ioType the ioType to set
	 */
	public void setIoType(int ioType) {
		this.ioType = ioType;
	}
	/**
	 * @return the mbrId
	 */
	public int getMbrId() {
		return mbrId;
	}
	/**
	 * @param mbrId the mbrId to set
	 */
	public void setMbrId(int mbrId) {
		this.mbrId = mbrId;
	}
	/**
	 * @return the opTime
	 */
	public Date getOpTime() {
		return opTime;
	}
	/**
	 * @param opTime the opTime to set
	 */
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the packageNo
	 */
	public String getPackageNo() {
		return packageNo;
	}
	/**
	 * @param packageNo the packageNo to set
	 */
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the reqId
	 */
	public int getReqId() {
		return reqId;
	}
	/**
	 * @param reqId the reqId to set
	 */
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	/**
	 * @return the snId
	 */
	public int getSnId() {
		return snId;
	}
	/**
	 * @param snId the snId to set
	 */
	public void setSnId(int snId) {
		this.snId = snId;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
}

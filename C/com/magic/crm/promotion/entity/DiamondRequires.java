/**
 * DiamondRequires.java
 * 2008-4-7
 * 下午12:49:24
 * user
 * DiamondRequires
 */
package com.magic.crm.promotion.entity;

import java.sql.Date;
/**
 * @author user
 *
 */
public class DiamondRequires {
	//pk
	private int reqId;
	//fk
	private int actionId;
	//1-一次性消费；2-累计消费
	private String moneyType;
	//需要金额
	private double moneyRequire;
	//会员级别
	private int mbrLevel;
	//颗数
	private int requireCount;
	//状态
	private int status;
	//操作人
	private int operator;
	//操作日期
	private Date opTime;
	
	//是否只读
	private boolean readOnly;
	
	//删除控制
	public boolean isDeleteAble() { //新建、审核可删除
		return (status == 3 ? false : true);
	}
	//审核控制
	public boolean isCheckAble() { // 新建可审核
		return (status == 1 && getReqId() > 0) ? true : false;
	}
	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return (status == 1 ? false : true);
	}
	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	/**
	 * @return the actionId
	 */
	public int getActionId() {
		return actionId;
	}
	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	/**
	 * @return the requireCount
	 */
	public int getRequireCount() {
		return requireCount;
	}
	/**
	 * @param requireCount the requireCount to set
	 */
	public void setRequireCount(int requireCount) {
		this.requireCount = requireCount;
	}
	/**
	 * @return the mbrLevel
	 */
	public int getMbrLevel() {
		return mbrLevel;
	}
	/**
	 * @param mbrLevel the mbrLevel to set
	 */
	public void setMbrLevel(int mbrLevel) {
		this.mbrLevel = mbrLevel;
	}
	/**
	 * @return the moneyRequire
	 */
	public double getMoneyRequire() {
		return moneyRequire;
	}
	/**
	 * @param moneyRequire the moneyRequire to set
	 */
	public void setMoneyRequire(double moneyRequire) {
		this.moneyRequire = moneyRequire;
	}
	/**
	 * @return the moneyType
	 */
	public String getMoneyType() {
		return moneyType;
	}
	/**
	 * @param moneyType the moneyType to set
	 */
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	/**
	 * @return the operator
	 */
	public int getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(int operator) {
		this.operator = operator;
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

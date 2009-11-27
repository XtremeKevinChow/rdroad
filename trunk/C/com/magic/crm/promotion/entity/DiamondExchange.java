/**
 * DiamondExchange.java
 * 2008-4-7
 * 下午12:53:58
 * user
 * DiamondExchange
 */
package com.magic.crm.promotion.entity;

import java.sql.Date;

import com.magic.crm.product.entity.Product;

/**
 * @author user
 *
 */
public class DiamondExchange {
	//pk
	private int excId;
	//fk
	private int actionId;
	//得钻颗数
	private int exchangeCount;
	//礼品类型
	private int giftType;
	//礼品包id
	private String packageNo;
	//设置者
	private int operator;
	//设置时间
	private Date opTime;
	//状态(1-新建；2-审核；3-删除)
	private int status;
	//是否只读
	private boolean readOnly;
	//是否有效
	private boolean enabled = false;
	//礼包
	private ExpExchangePackageMst packMst = null;
	//礼品
	Product gift = null;
	//礼券
	//TODO
	
	/**
	 * @return the gift
	 */
	public Product getGift() {
		return gift == null ? new Product() : gift;
	}
	/**
	 * @param gift the gift to set
	 */
	public void setGift(Product gift) {
		this.gift = gift;
	}
	/**
	 * @return the packMst
	 */
	public ExpExchangePackageMst getPackMst() {
		return packMst == null ? new ExpExchangePackageMst() : packMst;
	}
	/**
	 * @param packMst the packMst to set
	 */
	public void setPackMst(ExpExchangePackageMst packMst) {
		this.packMst = packMst;
	}
	//删除控制
	public boolean isDeleteAble() { //新建、审核可删除
		return (status == 3 ? false : true);
	}
	//审核控制
	public boolean isCheckAble() { // 新建可审核
		return (status == 1 && getExcId() > 0) ? true : false;
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
	 * @return the exchangeCount
	 */
	public int getExchangeCount() {
		return exchangeCount;
	}
	/**
	 * @param exchangeCount the exchangeCount to set
	 */
	public void setExchangeCount(int exchangeCount) {
		this.exchangeCount = exchangeCount;
	}
	/**
	 * @return the excId
	 */
	public int getExcId() {
		return excId;
	}
	/**
	 * @param excId the excId to set
	 */
	public void setExcId(int excId) {
		this.excId = excId;
	}
	/**
	 * @return the giftType
	 */
	public int getGiftType() {
		return giftType;
	}
	/**
	 * @param giftType the giftType to set
	 */
	public void setGiftType(int giftType) {
		this.giftType = giftType;
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
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}

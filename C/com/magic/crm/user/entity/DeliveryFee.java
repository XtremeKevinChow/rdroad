/**
 * DeliveryFee.java
 * 2008-4-9
 * ÏÂÎç05:16:52
 * user
 * DeliveryFee
 */
package com.magic.crm.user.entity;

import java.sql.Date;
/**
 * @author user
 *
 */
public class DeliveryFee {
	
	private int id;
	private int deliveryType;
	private String deliveryTypeName;
	private int levelId;
	private String levelName;
	private String postcode;
	private double fees;
	private Date beginDate;
	private Date endDate;
	private double requireAmt;
	private String remark;
	private String cityCode;
	private String regionCode;
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
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return the deliveryType
	 */
	public int getDeliveryType() {
		return deliveryType;
	}
	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
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
	 * @return the fees
	 */
	public double getFees() {
		return fees;
	}
	/**
	 * @param fees the fees to set
	 */
	public void setFees(double fees) {
		this.fees = fees;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}
	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}
	/**
	 * @param postcode the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
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
	 * @return the requireAmt
	 */
	public double getRequireAmt() {
		return requireAmt;
	}
	/**
	 * @param requireAmt the requireAmt to set
	 */
	public void setRequireAmt(double requireAmt) {
		this.requireAmt = requireAmt;
	}
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
}

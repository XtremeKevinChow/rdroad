/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

import java.io.Serializable;


/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeliveryInfo implements Serializable {
	/* 送货地址编号 */
	private int addressId = 0;
	/* 收件人编号 */
	private String receiptor = null;
	/* 联系电话 */
	private String phone = null;
	private String phone2 = null;
	/* 送货地址 */
	private String address = null;
	/* 邮政编码 */
	private String postCode = null;
	/* 送货类型 */
	private String deliveryType = null;
	private int deliveryTypeId = -1;
	/* 付款方式 */
	private String paymentType = null;
	private int paymentTypeId = -1;
	/* 当前送货信息为默认送货信息 */
	private boolean isDefault = false;
	
	/**发送费 add by user 2007-03-12**/
	private double deliveryFee = 0d;
	
	private String section = null;
	private String sectionName = null;
	
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * @return Returns the isDefault.
	 */
	public boolean isDefault() {
		return isDefault;
	}
	/**
	 * @param isDefault The isDefault to set.
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return Returns the addressId.
	 */
	public int getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId The addressId to set.
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return Returns the deliveryType.
	 */
	public String getDeliveryType() {
		return deliveryType;
	}
	/**
	 * @param deliveryType The deliveryType to set.
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	/**
	 * @return Returns the paymentType.
	 */
	public String getPaymentType() {
		return paymentType;
	}
	/**
	 * @param paymentType The paymentType to set.
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return Returns the postCode.
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode The postCode to set.
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return Returns the receiptor.
	 */
	public String getReceiptor() {
		return receiptor;
	}
	/**
	 * @param receiptor The receiptor to set.
	 */
	public void setReceiptor(String receiptor) {
		this.receiptor = receiptor;
	}
	public double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public int getDeliveryTypeId() {
		return deliveryTypeId;
	}
	public void setDeliveryTypeId(int deliveryTypeId) {
		this.deliveryTypeId = deliveryTypeId;
	}
	public int getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}
	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
}
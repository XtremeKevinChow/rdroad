/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.form;

import java.util.ArrayList;
import java.util.List;

import com.magic.crm.common.WebForm;

/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeliveryTypeForm extends WebForm {
	/* 会员编号 */
	private int mbId = 0;
	/* 会员卡号 */
	private String cardId = null;
	/* 会员姓名 */
	private String mbName = null;
	/* 送货地址ID */
	private int addressId = 0;
	private String address = null;
	/* 送货邮编 */
	private String postCode = null;
	/* 送货方式集合 */
	private List typeList = new ArrayList();
	/* 当前送货方式编号 */
	private int currDeliveryType = 0;
	
	private String section = "";
	
	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * @return Returns the cardId.
	 */
	public String getCardId() {
		return cardId;
	}
	/**
	 * @param cardId The cardId to set.
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	/**
	 * @return Returns the currDeliveryType.
	 */
	public int getCurrDeliveryType() {
		return currDeliveryType;
	}
	/**
	 * @param currDeliveryType The currDeliveryType to set.
	 */
	public void setCurrDeliveryType(int currDeliveryType) {
		this.currDeliveryType = currDeliveryType;
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
	 * @return Returns the mbId.
	 */
	public int getMbId() {
		return mbId;
	}
	/**
	 * @param mbId The mbId to set.
	 */
	public void setMbId(int mbId) {
		this.mbId = mbId;
	}
	/**
	 * @return Returns the mbName.
	 */
	public String getMbName() {
		return mbName;
	}
	/**
	 * @param mbName The mbName to set.
	 */
	public void setMbName(String mbName) {
		this.mbName = mbName;
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
	 * @return Returns the typeList.
	 */
	public List getTypeList() {
		return typeList;
	}
}
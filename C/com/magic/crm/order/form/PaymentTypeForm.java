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
public class PaymentTypeForm extends WebForm {
	/* ��Ա��� */
	private int mbId = 0;
	/* ��Ա���� */
	private String cardId = null;
	/* ��Ա���� */
	private String mbName = null;
	/* �ͻ���ַID */
	private int addressId = 0;
	private String address = null;
	/* �ͻ��ʱ� */
	private String postCode = null;
	/* �ͻ���ʽ */
	private int deliveryTypeId = 0;
	private String deliveryTypeName = null;
	/* ���ʽ���� */
	private List typeList = new ArrayList();
	/* ��ǰ���ʽ��� */
	private int currPaymentType = 0;

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
	 * @return Returns the currPaymentType.
	 */
	public int getCurrPaymentType() {
		return currPaymentType;
	}
	/**
	 * @param currPaymentType The currPaymentType to set.
	 */
	public void setCurrPaymentType(int currPaymentType) {
		this.currPaymentType = currPaymentType;
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
	 * @return Returns the deliveryTypeId.
	 */
	public int getDeliveryTypeId() {
		return deliveryTypeId;
	}
	/**
	 * @param deliveryTypeId The deliveryTypeId to set.
	 */
	public void setDeliveryTypeId(int deliveryTypeId) {
		this.deliveryTypeId = deliveryTypeId;
	}
	/**
	 * @return Returns the deliveryTypeName.
	 */
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	/**
	 * @param deliveryTypeName The deliveryTypeName to set.
	 */
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
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
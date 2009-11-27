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
public class DeliveryInfoForm extends WebForm {
	
	public int getMb2Id() {
		return mb2Id;
	}
	public void setMb2Id(int mb2Id) {
		this.mb2Id = mb2Id;
	}
	/* 复制会员 id */
	private int mb2Id = 0;
	
	/* 会员编号 */
	private int mbId = 0;
	/* 会员卡号 */
	private String cardId = null;
	/* 会员姓名 */
	private String mbName = null;
	/* 送货地址ID */
	private int addressId = 0;
	/* 送货信息列表 */
	private List deliveryInfoList = new ArrayList();
	/* 收件人编号 */
	private String receiptor = null;
	/* 联系电话 */
	private String phone = null;
	/* 联系电话2 add by user 2008-03-27 */
	private String phone2 = null;
	/* 送货地址 */
	private String address = null;
	/* 邮政编码 */
	private String postCode = null;
	/* 送货类型 */
	private int deliveryTypeId = 0;
	private String deliveryType = null;
	/* 付款方式 */
	private int paymentTypeId = 0;
	private String paymentType = null;
	/* 送货方式列表List<KeyValue> */
	private List deliveryTypeList = new ArrayList();
	/* 付款方式列表List<KeyValue> */
	private List paymentTypeList = new ArrayList();
	/* 是否更新到主地址 */
	private int isUpdate2MainAddress = 0;
	/* 是否更成默认地址 */
	private int isUpdate2DefaultAddress = 0;
	
	private String section = "";
	private String city = "";
	private String province = "";
	
	private String page = "";
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the provs
	 */
	public ArrayList getProvs() {
		return provs;
	}
	/**
	 * @param provs the provs to set
	 */
	public void setProvs(ArrayList provs) {
		this.provs = provs;
	}
	/**
	 * @return the citys
	 */
	public ArrayList getCitys() {
		return citys;
	}
	/**
	 * @param citys the citys to set
	 */
	public void setCitys(ArrayList citys) {
		this.citys = citys;
	}
	/**
	 * @return the sects
	 */
	public ArrayList getSects() {
		return sects;
	}
	/**
	 * @param sects the sects to set
	 */
	public void setSects(ArrayList sects) {
		this.sects = sects;
	}
	private ArrayList provs = new ArrayList();
	private ArrayList citys = new ArrayList();
	private ArrayList sects = new ArrayList();
	
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
	/**
	 * @return Returns the deliveryTypeList.
	 */
	public List getDeliveryTypeList() {
		return deliveryTypeList;
	}
	/**
	 * @return Returns the paymentTypeList.
	 */
	public List getPaymentTypeList() {
		return paymentTypeList;
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
	 * @return Returns the paymentTypeId.
	 */
	public int getPaymentTypeId() {
		return paymentTypeId;
	}
	/**
	 * @param paymentTypeId The paymentTypeId to set.
	 */
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
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
	 * @return Returns the deliveryInfoList.
	 */
	public List getDeliveryInfoList() {
		return deliveryInfoList;
	}
	
    
    /**
     * @return Returns the isUpdate2DefaultAddress.
     */
    public int getIsUpdate2DefaultAddress() {
        return isUpdate2DefaultAddress;
    }
    /**
     * @param isUpdate2DefaultAddress The isUpdate2DefaultAddress to set.
     */
    public void setIsUpdate2DefaultAddress(int isUpdate2DefaultAddress) {
        this.isUpdate2DefaultAddress = isUpdate2DefaultAddress;
    }
    /**
     * @return Returns the isUpdate2MainAddress.
     */
    public int getIsUpdate2MainAddress() {
        return isUpdate2MainAddress;
    }
    /**
     * @param isUpdate2MainAddress The isUpdate2MainAddress to set.
     */
    public void setIsUpdate2MainAddress(int isUpdate2MainAddress) {
        this.isUpdate2MainAddress = isUpdate2MainAddress;
    }
}
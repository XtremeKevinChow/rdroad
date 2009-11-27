/*
 * Created on 2006-2-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.common.pager.PagerForm;

import com.magic.crm.util.CodeName;
import com.magic.crm.util.DateUtil;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MbrGetAwardForm extends PagerForm implements Serializable {
	
	private int stepDtlId;
	
	
	/******************* 常量信息 ********************/
	/** 积分兑换 **/
	public static final int EXP_CHANGE = 1;
	
	/** 积分礼品取消 **/
	public static final int EXP_CANCEL = 2;
	
	/** 核货 **/
	public static final int CHECK_GOODS = 3;
	
	/** 退货 **/
	public static final int RETURN_GOODS = 4;
	
	/** 操作人ID **/
	private int operatorID = 0;
	
	/** 操作人姓名 **/
	private String operatorName = null;
	
	/** 操作类型 **/
	private int operatorType = 0;
	
	/** 有效天数 **/
	private int availDay = 0;
	
	/** 最后日期(兑换礼品) **/
	private java.sql.Date lastDate = null;
	
	private String create_date = "";
	
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/** 礼品状态 **/
	private int itemStatus = -1000;
	
	/** 礼品状态列表 **/
	private Collection itemStatusList = new java.util.ArrayList();
	
	/** 销售类型 **/
	private int sellType = -1;
	
	/** 销售类型列表 **/
	private Collection sellTypeList = new java.util.ArrayList();
	
	/** 是否转移礼品 **/
	private int isTransfer = -1;
	
	/** 是否转移礼品列表 **/
	private Collection isTransferList = new java.util.ArrayList();
	
	/** 说明 **/
	private String description = null;
	
	/** 总使用次数 */
	private int total_num = 1;
	
	/** 已使用次数 */
	private int num =0;
	
	
	
	public int getTotal_num() {
		return total_num;
	}
	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return the size_code
	 */
	public String getSize_code() {
		return size_code;
	}
	/**
	 * @param size_code the size_code to set
	 */
	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}

	private ArrayList colors = new ArrayList();
	private String size_code;
	private ArrayList sizes = new ArrayList();
	
	/**
	 * @return the colors
	 */
	public ArrayList getColors() {
		return colors;
	}
	/**
	 * @param colors the colors to set
	 */
	public void setColors(ArrayList colors) {
		this.colors = colors;
	}
	/**
	 * @return the sizes
	 */
	public ArrayList getSizes() {
		return sizes;
	}
	/**
	 * @param sizes the sizes to set
	 */
	public void setSizes(ArrayList sizes) {
		this.sizes = sizes;
	}


	
	
	
    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
	/**
	 * @return Returns the isTransferList.
	 */
	public Collection getIsTransferList() {
		isTransferList.add(new CodeName("-1", "--请选择--"));
		isTransferList.add(new CodeName("0", "否"));
		isTransferList.add(new CodeName("1", "是"));
		return isTransferList;
	}
	/**
	 * @return Returns the sellTypeList.
	 */
	public Collection getSellTypeList() {
		sellTypeList.add(new CodeName("-1", "--请选择--"));
		sellTypeList.add(new CodeName("3", "其他销售"));
		sellTypeList.add(new CodeName("4", "礼品赠品"));
		sellTypeList.add(new CodeName("5", "介绍人赠品"));
		sellTypeList.add(new CodeName("6", "积分换礼"));
		sellTypeList.add(new CodeName("7", "注册送礼"));
		//sellTypeList.add(new CodeName("8", "会员卡"));
		//sellTypeList.add(new CodeName("9", "网上活动礼品"));
		//sellTypeList.add(new CodeName("10", "刮刮卡"));
		//sellTypeList.add(new CodeName("11", "乐透宝物"));
		sellTypeList.add(new CodeName("12", "转移礼品"));
		sellTypeList.add(new CodeName("13", "人工加礼品"));
		sellTypeList.add(new CodeName("14", "其他"));
		sellTypeList.add(new CodeName("15", "预付款赠礼"));

		return sellTypeList;
	}
	/**
	 * @return Returns the isTransfer.
	 */
	public int getIsTransfer() {
		return isTransfer;
	}
	/**
	 * @param isTransfer The isTransfer to set.
	 */
	public void setIsTransfer(int isTransfer) {
		this.isTransfer = isTransfer;
	}
	/**
	 * @return Returns the sellType.
	 */
	public int getSellType() {
		return sellType;
	}
	/**
	 * @param sellType The sellType to set.
	 */
	public void setSellType(int sellType) {
		this.sellType = sellType;
	}
	/**
	 * @return Returns the itemStatusList.
	 */
	public Collection getItemStatusList() {
		itemStatusList.add(new CodeName("-1000", "--请选择--"));
		itemStatusList.add(new CodeName("0", "待发"));
		itemStatusList.add(new CodeName("1", "已发"));
		itemStatusList.add(new CodeName("-1", "取消"));
		return itemStatusList;
	}
	/**
	 * @return Returns the itemStatus.
	 */
	public int getItemStatus() {
		return itemStatus;
	}
	/**
	 * @param itemStatus The itemStatus to set.
	 */
	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}
	/**
	 * @return Returns the availDay.
	 */
	public int getAvailDay() {
		return availDay;
	}
	/**
	 * @param availDay The availDay to set.
	 */
	public void setAvailDay(int availDay) {
		this.availDay = availDay;
	}
	/**
	 * @return Returns the lastDate.
	 */
	public java.sql.Date getLastDate() {
		java.util.Date date = DateUtil.addDay(DateUtil.getSqlDate(), this.availDay);
		return DateUtil.getSqlDate(date);
	}
	/*************** 会员积分帐户信息 ******************/
	
	/** 会员ID **/
	private long memberID = 0;
	
	/** 会员卡号 **/
	private String cardID = null;
	
	/** 会员姓名 **/
	private String memberName = null;
	
	/**电话号码 **/
	private String telephone = "";
	
	/** 本年度积分 **/
	private int amountExp = 0;
	
	/******************兑换礼品信息 *******************/
	
	//sku id
	int sku_id;
	// 产品颜色
	String color_code="";
	//礼券
	String gift_number="";
	
	/** 货号 **/
	private String itemCode = null;
	
	/** 产品名称 **/
	private String itemName = null;
	
	/** 兑换积分 **/
	private int expExchange = 0;
	
	/** 兑换价 **/
	private double exchangePrice = 0D;

	
	/** 暂存表ID **/
	private int awardID = 0;
	
	/** 选中的纪录ID **/
	private int[] selectedID = null;
	
	
	/**
	 * @return Returns the selectedID.
	 */
	public int[] getSelectedID() {
		return selectedID;
	}
	/**
	 * @param selectedID The selectedID to set.
	 */
	public void setSelectedID(int[] selectedID) {
		this.selectedID = selectedID;
	}
	/**
	 * @return Returns the amountExp.
	 */
	public int getAmountExp() {
		return amountExp;
	}
	/**
	 * @param amountExp The amountExp to set.
	 */
	public void setAmountExp(int amountExp) {
		this.amountExp = amountExp;
	}
	/**
	 * @return Returns the cardID.
	 */
	public String getCardID() {
		return cardID;
	}
	/**
	 * @param cardID The cardID to set.
	 */
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	
	/**
	 * @return Returns the memberID.
	 */
	public long getMemberID() {
		return memberID;
	}
	/**
	 * @param memberID The memberID to set.
	 */
	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}
	/**
	 * @return Returns the memberName.
	 */
	public String getMemberName() {
		return memberName;
	}
	/**
	 * @param memberName The memberName to set.
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	/**
	 * @return Returns the operatorName.
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName The operatorName to set.
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	/**
	 * @return Returns the operatorID.
	 */
	public int getOperatorID() {
		return operatorID;
	}
	/**
	 * @param operatorID The operatorID to set.
	 */
	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
	
	/**
	 * @return Returns the operatorType.
	 */
	public int getOperatorType() {
		return operatorType;
	}
	/**
	 * @param operatorType The operatorType to set.
	 */
	public void setOperatorType(int operatorType) {
		this.operatorType = operatorType;
	}
	
	
	/**
	 * @return Returns the awardID.
	 */
	public int getAwardID() {
		return awardID;
	}
	/**
	 * @param awardID The awardID to set.
	 */
	public void setAwardID(int awardID) {
		this.awardID = awardID;
	}
	/**
	 * @return Returns the exchangePrice.
	 */
	public double getExchangePrice() {
		return exchangePrice;
	}
	/**
	 * @param exchangePrice The exchangePrice to set.
	 */
	public void setExchangePrice(double exchangePrice) {
		this.exchangePrice = exchangePrice;
	}
	/**
	 * @return Returns the expExchange.
	 */
	public int getExpExchange() {
		return expExchange;
	}
	/**
	 * @param expExchange The expExchange to set.
	 */
	public void setExpExchange(int expExchange) {
		this.expExchange = expExchange;
	}
	/**
	 * @return Returns the item_code.
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param item_code The item_code to set.
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public String getColor_code() {
		return color_code;
	}
	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}
	public String getGift_number() {
		return gift_number;
	}
	public void setGift_number(String gift_number) {
		this.gift_number = gift_number;
	}
	public static int getEXP_CHANGE() {
		return EXP_CHANGE;
	}
	public static int getEXP_CANCEL() {
		return EXP_CANCEL;
	}
	public static int getCHECK_GOODS() {
		return CHECK_GOODS;
	}
	public static int getRETURN_GOODS() {
		return RETURN_GOODS;
	}
	public void setLastDate(java.sql.Date lastDate) {
		this.lastDate = lastDate;
	}
	public void setItemStatusList(Collection itemStatusList) {
		this.itemStatusList = itemStatusList;
	}
	public void setSellTypeList(Collection sellTypeList) {
		this.sellTypeList = sellTypeList;
	}
	public void setIsTransferList(Collection isTransferList) {
		this.isTransferList = isTransferList;
	}
	/**
	 * @return Returns the itemName.
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName The itemName to set.
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getStepDtlId() {
		return stepDtlId;
	}
	public void setStepDtlId(int stepDtlId) {
		this.stepDtlId = stepDtlId;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}

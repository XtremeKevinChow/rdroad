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
	
	
	/******************* ������Ϣ ********************/
	/** ���ֶһ� **/
	public static final int EXP_CHANGE = 1;
	
	/** ������Ʒȡ�� **/
	public static final int EXP_CANCEL = 2;
	
	/** �˻� **/
	public static final int CHECK_GOODS = 3;
	
	/** �˻� **/
	public static final int RETURN_GOODS = 4;
	
	/** ������ID **/
	private int operatorID = 0;
	
	/** ���������� **/
	private String operatorName = null;
	
	/** �������� **/
	private int operatorType = 0;
	
	/** ��Ч���� **/
	private int availDay = 0;
	
	/** �������(�һ���Ʒ) **/
	private java.sql.Date lastDate = null;
	
	private String create_date = "";
	
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/** ��Ʒ״̬ **/
	private int itemStatus = -1000;
	
	/** ��Ʒ״̬�б� **/
	private Collection itemStatusList = new java.util.ArrayList();
	
	/** �������� **/
	private int sellType = -1;
	
	/** ���������б� **/
	private Collection sellTypeList = new java.util.ArrayList();
	
	/** �Ƿ�ת����Ʒ **/
	private int isTransfer = -1;
	
	/** �Ƿ�ת����Ʒ�б� **/
	private Collection isTransferList = new java.util.ArrayList();
	
	/** ˵�� **/
	private String description = null;
	
	/** ��ʹ�ô��� */
	private int total_num = 1;
	
	/** ��ʹ�ô��� */
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
		isTransferList.add(new CodeName("-1", "--��ѡ��--"));
		isTransferList.add(new CodeName("0", "��"));
		isTransferList.add(new CodeName("1", "��"));
		return isTransferList;
	}
	/**
	 * @return Returns the sellTypeList.
	 */
	public Collection getSellTypeList() {
		sellTypeList.add(new CodeName("-1", "--��ѡ��--"));
		sellTypeList.add(new CodeName("3", "��������"));
		sellTypeList.add(new CodeName("4", "��Ʒ��Ʒ"));
		sellTypeList.add(new CodeName("5", "��������Ʒ"));
		sellTypeList.add(new CodeName("6", "���ֻ���"));
		sellTypeList.add(new CodeName("7", "ע������"));
		//sellTypeList.add(new CodeName("8", "��Ա��"));
		//sellTypeList.add(new CodeName("9", "���ϻ��Ʒ"));
		//sellTypeList.add(new CodeName("10", "�ιο�"));
		//sellTypeList.add(new CodeName("11", "��͸����"));
		sellTypeList.add(new CodeName("12", "ת����Ʒ"));
		sellTypeList.add(new CodeName("13", "�˹�����Ʒ"));
		sellTypeList.add(new CodeName("14", "����"));
		sellTypeList.add(new CodeName("15", "Ԥ��������"));

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
		itemStatusList.add(new CodeName("-1000", "--��ѡ��--"));
		itemStatusList.add(new CodeName("0", "����"));
		itemStatusList.add(new CodeName("1", "�ѷ�"));
		itemStatusList.add(new CodeName("-1", "ȡ��"));
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
	/*************** ��Ա�����ʻ���Ϣ ******************/
	
	/** ��ԱID **/
	private long memberID = 0;
	
	/** ��Ա���� **/
	private String cardID = null;
	
	/** ��Ա���� **/
	private String memberName = null;
	
	/**�绰���� **/
	private String telephone = "";
	
	/** ����Ȼ��� **/
	private int amountExp = 0;
	
	/******************�һ���Ʒ��Ϣ *******************/
	
	//sku id
	int sku_id;
	// ��Ʒ��ɫ
	String color_code="";
	//��ȯ
	String gift_number="";
	
	/** ���� **/
	private String itemCode = null;
	
	/** ��Ʒ���� **/
	private String itemName = null;
	
	/** �һ����� **/
	private int expExchange = 0;
	
	/** �һ��� **/
	private double exchangePrice = 0D;

	
	/** �ݴ��ID **/
	private int awardID = 0;
	
	/** ѡ�еļ�¼ID **/
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

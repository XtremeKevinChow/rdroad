/*
 * Created on 2006-2-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import com.magic.crm.common.pager.PagerForm;
import java.io.Serializable;

/**
 * @author ��
 * ���ֻ�������Form
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberExpExchangeForm extends PagerForm implements Serializable {
	
	/** ���� **/
	private long ID = 0;
	
	/** ����ID **/
	private long parentID = 0;
	
	/** ����� **/
	private String parentName = null;
	
	/** ��ƷID **/
	private int itemID = 0;
	
	/** ��Ʒ���� **/
	private String itemCode = null;
	
	/** ��Ʒ���� **/
	private String itemName = null;
	
	/** �һ�ʼ���� **/
	private int expStart = 0;

	/** �һ��۸� **/
	private int exchangePrice = 0;
	
	/** ��Ʒ���� **/
	private String content = null;
	
	/** ��ʼ���� **/
	private String startDate = null;
	
	/** �������� **/
	private String endDate = null;
	
	/** ��ѯ���� **/
	private String queryDate = null;
	
	/** ������Ч���� **/
	private int validDay = 0;
	
	/** �Ƿ���Ч **/
	private String validFlag = null;
	
	/** ���״̬ **/
	private String stockStatus = null;
	
	
    /**
     * @return Returns the stockStatus.
     */
    public String getStockStatus() {
        return stockStatus;
    }
    /**
     * @param stockStatus The stockStatus to set.
     */
    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }
	/**
	 * @return Returns the parentName.
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * @param parentName The parentName to set.
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/**
	 * @return Returns the exchangePrice.
	 */
	public int getExchangePrice() {
		return exchangePrice;
	}
	/**
	 * @param exchangePrice The exchangePrice to set.
	 */
	public void setExchangePrice(int exchangePrice) {
		this.exchangePrice = exchangePrice;
	}
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return Returns the expStart.
	 */
	public int getExpStart() {
		return expStart;
	}
	/**
	 * @param expStart The expStart to set.
	 */
	public void setExpStart(int expStart) {
		this.expStart = expStart;
	}
	/**
	 * @return Returns the iD.
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id) {
		ID = id;
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
	/**
	 * @return Returns the itemCode.
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode The itemCode to set.
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return Returns the itemID.
	 */
	public int getItemID() {
		return itemID;
	}
	/**
	 * @param itemID The itemID to set.
	 */
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	
	/**
	 * @return Returns the parentID.
	 */
	public long getParentID() {
		return parentID;
	}
	/**
	 * @param parentID The parentID to set.
	 */
	public void setParentID(long parentID) {
		this.parentID = parentID;
	}
	
	
	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		if (endDate != null && endDate.length() >= 10) {
			return endDate.substring(0, 10);
		}
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		
		this.endDate = endDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		if (startDate != null && startDate.length() >= 10) {
			return startDate.substring(0, 10);
		}
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		
		this.startDate = startDate;
	}
	
	/**
	 * @return Returns the validDay.
	 */
	public int getValidDay() {
		return validDay;
	}
	/**
	 * @param validDay The validDay to set.
	 */
	public void setValidDay(int validDay) {
		this.validDay = validDay;
	}
	
	
	/**
	 * @return Returns the validFlag.
	 */
	public String getValidFlag() {
		return validFlag;
	}
	/**
	 * @param validFlag The validFlag to set.
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	
	/**
	 * @return Returns the queryDate.
	 */
	public String getQueryDate() {
		return queryDate;
	}
	/**
	 * @param queryDate The queryDate to set.
	 */
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
}

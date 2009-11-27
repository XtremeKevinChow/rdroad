/*
 * Created on 2006-6-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.entity;

import java.io.Serializable;
import java.sql.Date;
/**
 * @author user
 * table: MBR_GET_MBR_GIFT
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMemberGift implements Serializable {
    int id;
    int itemId;
    Date beginDate;
    Date endDate;
    int isvalid;
    double price;
    int keepDays;
    int operatorId;
    Date createDate;
    String gift_number;
    
    
    
    public String getGift_number() {
		return gift_number;
	}
	public void setGift_number(String gift_number) {
		this.gift_number = gift_number;
	}
	/**
     * @return Returns the beginDate.
     */
    public Date getBeginDate() {
        return beginDate;
    }
    /**
     * @param beginDate The beginDate to set.
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    /**
     * @return Returns the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate The createDate to set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * @return Returns the endDate.
     */
    public Date getEndDate() {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Returns the isvalid.
     */
    public int getIsvalid() {
        return isvalid;
    }
    /**
     * @param isvalid The isvalid to set.
     */
    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }
    /**
     * @return Returns the itemId.
     */
    public int getItemId() {
        return itemId;
    }
    /**
     * @param itemId The itemId to set.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    /**
     * @return Returns the keepDays.
     */
    public int getKeepDays() {
        return keepDays;
    }
    /**
     * @param keepDays The keepDays to set.
     */
    public void setKeepDays(int keepDays) {
        this.keepDays = keepDays;
    }
    /**
     * @return Returns the operatorId.
     */
    public int getOperatorId() {
        return operatorId;
    }
    /**
     * @param operatorId The operatorId to set.
     */
    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }
    /**
     * @return Returns the price.
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}

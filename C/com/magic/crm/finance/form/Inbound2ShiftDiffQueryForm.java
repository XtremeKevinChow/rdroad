/*
 * Created on 2006-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.form;

import java.io.Serializable;
import java.sql.Date;
import com.magic.crm.common.pager.PagerForm;


/**
 * @author user
 * ���˻����۵�������ϼܲ�ѯform
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Inbound2ShiftDiffQueryForm extends PagerForm implements Serializable {
    
    /** ��ѯ��ʼ���� **/
    private Date startDate = null;
    
    /** ��ѯ�������� **/
    private Date endDate = null;
    
    /** ����ϼܵ��� **/
    private String rsNO = null;
    
    /** ����ϼ����� **/
    private String rsType = null;
    
    /** �������� **/
    private Date createDate = null;
    
    /** ��ƷID **/
    private int itemID = 0;
    
    /** ������ **/
    private String itemCode = null;
    
    /** ��Ʒ���� **/
    private String itemName = null;
    
    /** �ϼ����� **/
    private double sumQty = 0d;
    
    /** ʵ������ **/
    private double factQty = 0d;
    
    /** �������� **/
    private double diffQty = 0d;
    
    /** ���ܺ� **/
    private String shelfNO = null;
    
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
     * @return Returns the startDate.
     */
    public Date getStartDate() {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
     * @return Returns the diffQty.
     */
    public double getDiffQty() {
        return diffQty;
    }
    /**
     * @param diffQty The diffQty to set.
     */
    public void setDiffQty(double diffQty) {
        this.diffQty = diffQty;
    }
    /**
     * @return Returns the factQty.
     */
    public double getFactQty() {
        return factQty;
    }
    /**
     * @param factQty The factQty to set.
     */
    public void setFactQty(double factQty) {
        this.factQty = factQty;
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
     * @return Returns the rsNO.
     */
    public String getRsNO() {
        return rsNO;
    }
    /**
     * @param rsNO The rsNO to set.
     */
    public void setRsNO(String rsNO) {
        this.rsNO = rsNO;
    }
    /**
     * @return Returns the rsType.
     */
    public String getRsType() {
        return rsType;
    }
    /**
     * @param rsType The rsType to set.
     */
    public void setRsType(String rsType) {
        this.rsType = rsType;
    }
    /**
     * @return Returns the shelfNO.
     */
    public String getShelfNO() {
        return shelfNO;
    }
    /**
     * @param shelfNO The shelfNO to set.
     */
    public void setShelfNO(String shelfNO) {
        this.shelfNO = shelfNO;
    }
    /**
     * @return Returns the sumQty.
     */
    public double getSumQty() {
        return sumQty;
    }
    /**
     * @param sumQty The sumQty to set.
     */
    public void setSumQty(double sumQty) {
        this.sumQty = sumQty;
    }
}

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
 * （退货、扣单）入库上架查询form
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Inbound2ShiftDiffQueryForm extends PagerForm implements Serializable {
    
    /** 查询开始日期 **/
    private Date startDate = null;
    
    /** 查询结束日期 **/
    private Date endDate = null;
    
    /** 入库上架单号 **/
    private String rsNO = null;
    
    /** 入库上架类型 **/
    private String rsType = null;
    
    /** 创建日期 **/
    private Date createDate = null;
    
    /** 产品ID **/
    private int itemID = 0;
    
    /** 货号码 **/
    private String itemCode = null;
    
    /** 产品名称 **/
    private String itemName = null;
    
    /** 合计数量 **/
    private double sumQty = 0d;
    
    /** 实际数量 **/
    private double factQty = 0d;
    
    /** 差异数量 **/
    private double diffQty = 0d;
    
    /** 货架号 **/
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

/*
 * Created on 2006-7-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.entity;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinSalesInvoiceItems {
    
    /** 子表标识 **/
    private int arDtlID = 0;
    
    /** 主表标识 **/
    private String arID = null;
    
    /** 仓库编码 **/
    private String stoNO = null;
    
    /** 产品ID **/
    private int itemID = 0;
    
    /** 数量 **/
    private double soQty = 0d;
    
    /** 销售方式 **/
    private String sellType = null;
    
    /** 原币无税单价 **/
    private double soPrice = 0d;
    
    /** 税率 **/
    private double tax = 0d;
    
    /** 不含税金额 **/
    private double amt = 0d;
    
    /** 原币税额 **/
    private double taxAmt = 0d;
    
    /** 原币价税合计 **/
    private double totalAmt = 0d;
    
    /** 备注 **/
    private String remark = null;
    
    /**
     * @return Returns the amt.
     */
    public double getAmt() {
        return amt;
    }
    /**
     * @param amt The amt to set.
     */
    public void setAmt(double amt) {
        this.amt = amt;
    }
    /**
     * @return Returns the arDtlID.
     */
    public int getArDtlID() {
        return arDtlID;
    }
    /**
     * @param arDtlID The arDtlID to set.
     */
    public void setArDtlID(int arDtlID) {
        this.arDtlID = arDtlID;
    }
    /**
     * @return Returns the arID.
     */
    public String getArID() {
        return arID;
    }
    /**
     * @param arID The arID to set.
     */
    public void setArID(String arID) {
        this.arID = arID;
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
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the sellType.
     */
    public String getSellType() {
        return sellType;
    }
    /**
     * @param sellType The sellType to set.
     */
    public void setSellType(String sellType) {
        this.sellType = sellType;
    }
    /**
     * @return Returns the soPrice.
     */
    public double getSoPrice() {
        return soPrice;
    }
    /**
     * @param soPrice The soPrice to set.
     */
    public void setSoPrice(double soPrice) {
        this.soPrice = soPrice;
    }
    /**
     * @return Returns the soQty.
     */
    public double getSoQty() {
        return soQty;
    }
    /**
     * @param soQty The soQty to set.
     */
    public void setSoQty(double soQty) {
        this.soQty = soQty;
    }
    /**
     * @return Returns the stoNO.
     */
    public String getStoNO() {
        return stoNO;
    }
    /**
     * @param stoNO The stoNO to set.
     */
    public void setStoNO(String stoNO) {
        this.stoNO = stoNO;
    }
    /**
     * @return Returns the tax.
     */
    public double getTax() {
        return tax;
    }
    /**
     * @param tax The tax to set.
     */
    public void setTax(double tax) {
        this.tax = tax;
    }
    /**
     * @return Returns the taxAmt.
     */
    public double getTaxAmt() {
        return taxAmt;
    }
    /**
     * @param taxAmt The taxAmt to set.
     */
    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }
    /**
     * @return Returns the totalAmt.
     */
    public double getTotalAmt() {
        return totalAmt;
    }
    /**
     * @param totalAmt The totalAmt to set.
     */
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }
}

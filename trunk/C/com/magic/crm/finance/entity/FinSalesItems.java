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
public class FinSalesItems {
    
    /** �ӱ��ʶ **/
    private int soDtlID = 0;
    
    /** �����ʶ **/
    private int soID = 0;
    
    /** �ֿ��� **/
    private String stoNO = null;
    
    /** ��ƷID **/
    private int itemID = 0;
    
    /** ���� **/
    private double soQty = 0d;
    
    /** ���۷�ʽ **/
    private String sellType = null;
    
    /** ԭ����˰���� **/
    private double soPrice = 0d;
    
    /** ˰�� **/
    private double tax = 0d;
    
    /** ����˰��� **/
    private double amt = 0d;
    
    /** ԭ��˰�� **/
    private double taxAmt = 0d;
    
    /** ԭ�Ҽ�˰�ϼ� **/
    private double totalAmt = 0d;
    
    /** �ۼƿ�Ʊ���� **/
    private double finishQty = 0d;
    
    /** �ۼƿ�Ʊ��� **/
    private double finishAmt = 0d;
    
    /** ��ע **/
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
     * @return Returns the finishAmt.
     */
    public double getFinishAmt() {
        return finishAmt;
    }
    /**
     * @param finishAmt The finishAmt to set.
     */
    public void setFinishAmt(double finishAmt) {
        this.finishAmt = finishAmt;
    }
    /**
     * @return Returns the finishQty.
     */
    public double getFinishQty() {
        return finishQty;
    }
    /**
     * @param finishQty The finishQty to set.
     */
    public void setFinishQty(double finishQty) {
        this.finishQty = finishQty;
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
     * @return Returns the soDtlID.
     */
    public int getSoDtlID() {
        return soDtlID;
    }
    /**
     * @param soDtlID The soDtlID to set.
     */
    public void setSoDtlID(int soDtlID) {
        this.soDtlID = soDtlID;
    }
    /**
     * @return Returns the soID.
     */
    public int getSoID() {
        return soID;
    }
    /**
     * @param soID The soID to set.
     */
    public void setSoID(int soID) {
        this.soID = soID;
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

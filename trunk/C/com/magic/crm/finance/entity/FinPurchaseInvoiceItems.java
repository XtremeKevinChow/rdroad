/*
 * Created on 2006-7-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.entity;

import com.magic.utils.Arith;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseInvoiceItems {
    
    /** �ӱ��ʶ **/
    private int apDtlID = 0;
    
    /** �����ʶ **/
    private int apID = 0;
    
    /** ��ƷID **/
    private int itemID = 0;
    
    /** ���� **/
    private double qty = 0d;
    
    /** Ԥ�㵥�� **/
    private double purPrice = 0d;
    
    /** ���㵥�� **/
    private double apPrice = 0d;
    
    /** ��Ʊ��� **/
    private double amt = 0d;
    
    /** ��˰�ϼ� **/
    private double totalAmt = 0d;
    
    /** ˰�� **/
    private double tax = 0d;
    
    /** �ɹ�������ID **/
    private int psDtlID = 0;
    
    /** �ɹ����� **/
    private double disAmt = 0d;
    
    /** Ӧ�� **/
    private double shouldPay = 0d;
    
    
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
     * @return Returns the apDtlID.
     */
    public int getApDtlID() {
        return apDtlID;
    }
    /**
     * @param apDtlID The apDtlID to set.
     */
    public void setApDtlID(int apDtlID) {
        this.apDtlID = apDtlID;
    }
    /**
     * @return Returns the apID.
     */
    public int getApID() {
        return apID;
    }
    /**
     * @param apID The apID to set.
     */
    public void setApID(int apID) {
        this.apID = apID;
    }
    /**
     * @return Returns the apPrice.
     */
    public double getApPrice() {
        return apPrice;
    }
    /**
     * @param apPrice The apPrice to set.
     */
    public void setApPrice(double apPrice) {
        this.apPrice = apPrice;
    }
    /**
     * @return Returns the disAmt.
     */
    public double getDisAmt() {
        //return Arith.round(Arith.mul(Arith.sub(purPrice, apPrice), qty), 2);
        return Arith.round(Arith.sub(Arith.mul(purPrice, qty), amt), 2);
    }
    /**
     * @param disAmt The disAmt to set.
     */
    public void setDisAmt(double disAmt) {
        this.disAmt = disAmt;
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
     * @return Returns the psDtlID.
     */
    public int getPsDtlID() {
        return psDtlID;
    }
    /**
     * @param psDtlID The psDtlID to set.
     */
    public void setPsDtlID(int psDtlID) {
        this.psDtlID = psDtlID;
    }
    /**
     * @return Returns the purPrice.
     */
    public double getPurPrice() {
        return purPrice;
    }
    /**
     * @param purPrice The purPrice to set.
     */
    public void setPurPrice(double purPrice) {
        this.purPrice = purPrice;
    }
    /**
     * @return Returns the qty.
     */
    public double getQty() {
        return qty;
    }
    /**
     * @param qty The qty to set.
     */
    public void setQty(double qty) {
        this.qty = qty;
    }
    /**
     * @return Returns the rax.
     */
    public double getTax() {
        return tax;
    }
    /**
     * @param rax The rax to set.
     */
    public void setTax(double tax) {
        this.tax = tax;
    }
    /**
     * @return Returns the shouldPay.
     */
    public double getShouldPay() {
        return shouldPay;
    }
    /**
     * @param shouldPay The shouldPay to set.
     */
    public void setShouldPay(double shouldPay) {
        this.shouldPay = shouldPay;
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

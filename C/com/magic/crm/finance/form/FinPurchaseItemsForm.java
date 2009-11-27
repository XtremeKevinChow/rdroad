/*
 * Created on 2006-7-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.form;

import java.sql.Date;

import org.apache.struts.action.ActionForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseItemsForm extends ActionForm {
    
    /** 子表标识 **/
    private int psDtlID = 0;
    
    /** 主表标识 **/
    private int psID = 0;
    
    /** 产品ID **/
    private int itemID = 0;
    
    /** 产品代码 **/
    private String itemCode = null;
    
    /** 产品名称 **/
    private String itemName = null;
    
    /** 供应商代码 **/
    private String proNO = null;
    
    /** 供应商名称 **/
    private String ProName = null;
    
    /** 数量 **/
    private double purQty = 0d;
    
    /** 预算单价 **/
    private double purPrice = 0d;
    
    /** 原币含税单价 **/
    private double taxPrice = 0d;
    
    /** 金额 **/
    private double purAmt = 0d;
    
    /** 原币税额 **/
    private double taxAmt = 0d;
    
    /** 原币价税合计 **/
    private double amt = 0d;
    
    /** 税率 **/
    private double tax = 0d;
    
    /** 未结数量 **/
    private double useQty = 0d;
    
    /** 累计开票数量 **/
    private double finishQty = 0d;
    
    /** 累计开票金额 **/
    private double finishAmt = 0d;
    
    /** 退货数量 **/
    private double returnQty = 0d;
    
    /** 行状态 **/
    private String status = null;
    
    /** 到货单号 **/
    private String psCode = null;
    
    /** 相应单号 **/
    private String resNO = null;
    
    /** 入库日期 **/
    private Date purchaseDate = null;
    
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
     * @return Returns the psID.
     */
    public int getPsID() {
        return psID;
    }
    /**
     * @param psID The psID to set.
     */
    public void setPsID(int psID) {
        this.psID = psID;
    }
    /**
     * @return Returns the purAmt.
     */
    public double getPurAmt() {
        return purAmt;
    }
    /**
     * @param purAmt The purAmt to set.
     */
    public void setPurAmt(double purAmt) {
        this.purAmt = purAmt;
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
     * @return Returns the purQty.
     */
    public double getPurQty() {
        return purQty;
    }
    /**
     * @param purQty The purQty to set.
     */
    public void setPurQty(double purQty) {
        this.purQty = purQty;
    }
    /**
     * @return Returns the returnQty.
     */
    public double getReturnQty() {
        return returnQty;
    }
    /**
     * @param returnQty The returnQty to set.
     */
    public void setReturnQty(double returnQty) {
        this.returnQty = returnQty;
    }
    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return Returns the taxPrice.
     */
    public double getTaxPrice() {
        return taxPrice;
    }
    /**
     * @param taxPrice The taxPrice to set.
     */
    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }
    /**
     * @return Returns the useQty.
     */
    public double getUseQty() {
        return useQty;
    }
    /**
     * @param useQty The useQty to set.
     */
    public void setUseQty(double useQty) {
        this.useQty = useQty;
    }
    
    /**
     * @return Returns the proName.
     */
    public String getProName() {
        return ProName;
    }
    /**
     * @param proName The proName to set.
     */
    public void setProName(String proName) {
        ProName = proName;
    }
    /**
     * @return Returns the proNO.
     */
    public String getProNO() {
        return proNO;
    }
    /**
     * @param proNO The proNO to set.
     */
    public void setProNO(String proNO) {
        this.proNO = proNO;
    }
    /**
     * @return Returns the psCode.
     */
    public String getPsCode() {
        return psCode;
    }
    /**
     * @param psCode The psCode to set.
     */
    public void setPsCode(String psCode) {
        this.psCode = psCode;
    }
    /**
     * @return Returns the purchaseDate.
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    /**
     * @param purchaseDate The purchaseDate to set.
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    /**
     * @return Returns the resNO.
     */
    public String getResNO() {
        return resNO;
    }
    /**
     * @param resNO The resNO to set.
     */
    public void setResNO(String resNO) {
        this.resNO = resNO;
    }
}

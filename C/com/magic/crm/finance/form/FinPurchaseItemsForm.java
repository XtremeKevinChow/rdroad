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
    
    /** �ӱ��ʶ **/
    private int psDtlID = 0;
    
    /** �����ʶ **/
    private int psID = 0;
    
    /** ��ƷID **/
    private int itemID = 0;
    
    /** ��Ʒ���� **/
    private String itemCode = null;
    
    /** ��Ʒ���� **/
    private String itemName = null;
    
    /** ��Ӧ�̴��� **/
    private String proNO = null;
    
    /** ��Ӧ������ **/
    private String ProName = null;
    
    /** ���� **/
    private double purQty = 0d;
    
    /** Ԥ�㵥�� **/
    private double purPrice = 0d;
    
    /** ԭ�Һ�˰���� **/
    private double taxPrice = 0d;
    
    /** ��� **/
    private double purAmt = 0d;
    
    /** ԭ��˰�� **/
    private double taxAmt = 0d;
    
    /** ԭ�Ҽ�˰�ϼ� **/
    private double amt = 0d;
    
    /** ˰�� **/
    private double tax = 0d;
    
    /** δ������ **/
    private double useQty = 0d;
    
    /** �ۼƿ�Ʊ���� **/
    private double finishQty = 0d;
    
    /** �ۼƿ�Ʊ��� **/
    private double finishAmt = 0d;
    
    /** �˻����� **/
    private double returnQty = 0d;
    
    /** ��״̬ **/
    private String status = null;
    
    /** �������� **/
    private String psCode = null;
    
    /** ��Ӧ���� **/
    private String resNO = null;
    
    /** ������� **/
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

/*
 * Created on 2006-7-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.entity;

import java.sql.Date;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseInvoice {
    
    /** �����ʶ **/
    private int apID = 0;
    
    /** ϵͳ��Ʊ�� **/
    private String sysAPCode = null;
    
    /** ʵ�ʷ�Ʊ�� **/
    private String factAPCode = null;
    
    /** ��Ʊ���� **/
    private Date invoiceDate = null;
    
    /** ��Ʊ���� **/
    private String apType = null;
    
    /** ҵ������ **/
    private String operationClass = null;
    
    /** ��Ʊ���� **/
    private Date createDate = null;
    
    /** �������� **/
    private Date checkDate = null;
    
    /** ��Ӧ�� **/
    private String proNO = null;
    
    /** ��ͷ˰�� **/
    private double tax = 0d;
    
    /** �Ƶ��� **/
    private String creator = null;
    
    /** ����� **/
    private String checkPerson = null;
    
    /** ������ **/
    private String tallier = null;
    
    /** ����ʱ�� **/
    private Date tallyDate = null;
    
    /** ��Ʊ�ܽ�� **/
    private double amt = 0d;
    
    /** ״̬ **/
    private String status = null;
    
    /** �ڳ���־ **/
    private String isFirst = null;
    
    /** ��������־ **/
    private String stockIOSign = null;
    
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
     * @return Returns the apType.
     */
    public String getApType() {
        if (amt >= 0) {
            return "1";
        } else {
            return "2";
        }
    }
    /**
     * @param apType The apType to set.
     */
    public void setApType(String apType) {
        this.apType = apType;
    }
    /**
     * @return Returns the checkDate.
     */
    public Date getCheckDate() {
        return checkDate;
    }
    /**
     * @param checkDate The checkDate to set.
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
    /**
     * @return Returns the checkPerson.
     */
    public String getCheckPerson() {
        return checkPerson;
    }
    /**
     * @param checkPerson The checkPerson to set.
     */
    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
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
     * @return Returns the creator.
     */
    public String getCreator() {
        return creator;
    }
    /**
     * @param creator The creator to set.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }
    /**
     * @return Returns the factAPCode.
     */
    public String getFactAPCode() {
        return factAPCode;
    }
    /**
     * @param factAPCode The factAPCode to set.
     */
    public void setFactAPCode(String factAPCode) {
        this.factAPCode = factAPCode;
    }
    /**
     * @return Returns the isFirst.
     */
    public String getIsFirst() {
        return isFirst;
    }
    /**
     * @param isFirst The isFirst to set.
     */
    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }
    /**
     * @return Returns the operationClass.
     */
    public String getOperationClass() {
        return operationClass;
    }
    /**
     * @param operationClass The operationClass to set.
     */
    public void setOperationClass(String operationClass) {
        this.operationClass = operationClass;
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
     * @return Returns the stockIOSign.
     */
    public String getStockIOSign() {
        return stockIOSign;
    }
    /**
     * @param stockIOSign The stockIOSign to set.
     */
    public void setStockIOSign(String stockIOSign) {
        this.stockIOSign = stockIOSign;
    }
    /**
     * @return Returns the sysAPCode.
     */
    public String getSysAPCode() {
        return sysAPCode;
    }
    /**
     * @param sysAPCode The sysAPCode to set.
     */
    public void setSysAPCode(String sysAPCode) {
        this.sysAPCode = sysAPCode;
    }
    /**
     * @return Returns the tallier.
     */
    public String getTallier() {
        return tallier;
    }
    /**
     * @param tallier The tallier to set.
     */
    public void setTallier(String tallier) {
        this.tallier = tallier;
    }
    /**
     * @return Returns the tallyDate.
     */
    public Date getTallyDate() {
        return tallyDate;
    }
    /**
     * @param tallyDate The tallyDate to set.
     */
    public void setTallyDate(Date tallyDate) {
        this.tallyDate = tallyDate;
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
     * @return Returns the invoiceDate.
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }
    /**
     * @param invoiceDate The invoiceDate to set.
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}

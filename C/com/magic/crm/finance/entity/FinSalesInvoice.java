/*
 * Created on 2006-7-18
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
public class FinSalesInvoice {
    
    /** 主表标识 **/
    private int arID = 0;
    
    /** 销售发票号 **/
    private String arNO = null;
    
    /** 销售订单ID **/
    private int soID = 0;
    
    /** 单据日期 **/
    private Date soDate = null;
    
    /** 业务类型 **/
    private String operationClass = null;
    
    /** 业务员 **/
    private String operator = null;
    
    /** 客户编码 **/
    private String customerID = null;
    
    /** 销售类型 **/
    private String soType = null;
    
    /** 税率 **/
    private double tax = 0d;
    
    /** 状态 **/
    private String status = null;
    
    /** 库存进出标志 **/
    private String stoIOSign = null;
    
    /** 销售金额 **/
    private double soAmt = 0d;
    
    /** 应收金额 **/
    private double arAmt = 0d;
    
    /** 礼券抵用金额 **/
    private double giftAmt = 0d;
    
    /** 卡费 **/
    private double cardAmt = 0d;
    
    /** 发送费 **/
    private double deliverAmt = 0d;
    
    /** 已付金额 **/
    private double payedAmt = 0d;
    
    /** 制单人 **/
    private String creator = null;
    
    /** 制单日期 **/
    private Date createDate = null;
    
    /** 审核人 **/
    private String checkPerson = null;
    
    /** 审核日期 **/
    private Date checkDate = null;
    
    /** 记帐人 **/
    private String tallier = null;
    
    /** 记帐时间 **/
    private Date tallyDate = null;
    
    /** 期初标识 **/
    private String isFirst = null;
    
    /** 备注 **/
    private String remark = null;
    
    /**
     * @return Returns the arAmt.
     */
    public double getArAmt() {
        return arAmt;
    }
    /**
     * @param arAmt The arAmt to set.
     */
    public void setArAmt(double arAmt) {
        this.arAmt = arAmt;
    }
    /**
     * @return Returns the arID.
     */
    public int getArID() {
        return arID;
    }
    /**
     * @param arID The arID to set.
     */
    public void setArID(int arID) {
        this.arID = arID;
    }
    /**
     * @return Returns the arNO.
     */
    public String getArNO() {
        return arNO;
    }
    /**
     * @param arNO The arNO to set.
     */
    public void setArNO(String arNO) {
        this.arNO = arNO;
    }
    /**
     * @return Returns the cardAmt.
     */
    public double getCardAmt() {
        return cardAmt;
    }
    /**
     * @param cardAmt The cardAmt to set.
     */
    public void setCardAmt(double cardAmt) {
        this.cardAmt = cardAmt;
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
     * @return Returns the customerID.
     */
    public String getCustomerID() {
        return customerID;
    }
    /**
     * @param customerID The customerID to set.
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    /**
     * @return Returns the deliverAmt.
     */
    public double getDeliverAmt() {
        return deliverAmt;
    }
    /**
     * @param deliverAmt The deliverAmt to set.
     */
    public void setDeliverAmt(double deliverAmt) {
        this.deliverAmt = deliverAmt;
    }
    /**
     * @return Returns the giftAmt.
     */
    public double getGiftAmt() {
        return giftAmt;
    }
    /**
     * @param giftAmt The giftAmt to set.
     */
    public void setGiftAmt(double giftAmt) {
        this.giftAmt = giftAmt;
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
     * @return Returns the operator.
     */
    public String getOperator() {
        return operator;
    }
    /**
     * @param operator The operator to set.
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    /**
     * @return Returns the payedAmt.
     */
    public double getPayedAmt() {
        return payedAmt;
    }
    /**
     * @param payedAmt The payedAmt to set.
     */
    public void setPayedAmt(double payedAmt) {
        this.payedAmt = payedAmt;
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
     * @return Returns the soAmt.
     */
    public double getSoAmt() {
        return soAmt;
    }
    /**
     * @param soAmt The soAmt to set.
     */
    public void setSoAmt(double soAmt) {
        this.soAmt = soAmt;
    }
    /**
     * @return Returns the soDate.
     */
    public Date getSoDate() {
        return soDate;
    }
    /**
     * @param soDate The soDate to set.
     */
    public void setSoDate(Date soDate) {
        this.soDate = soDate;
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
     * @return Returns the soType.
     */
    public String getSoType() {
        return soType;
    }
    /**
     * @param soType The soType to set.
     */
    public void setSoType(String soType) {
        this.soType = soType;
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
     * @return Returns the stoIOSign.
     */
    public String getStoIOSign() {
        return stoIOSign;
    }
    /**
     * @param stoIOSign The stoIOSign to set.
     */
    public void setStoIOSign(String stoIOSign) {
        this.stoIOSign = stoIOSign;
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
}

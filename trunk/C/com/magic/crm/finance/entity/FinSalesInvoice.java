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
    
    /** �����ʶ **/
    private int arID = 0;
    
    /** ���۷�Ʊ�� **/
    private String arNO = null;
    
    /** ���۶���ID **/
    private int soID = 0;
    
    /** �������� **/
    private Date soDate = null;
    
    /** ҵ������ **/
    private String operationClass = null;
    
    /** ҵ��Ա **/
    private String operator = null;
    
    /** �ͻ����� **/
    private String customerID = null;
    
    /** �������� **/
    private String soType = null;
    
    /** ˰�� **/
    private double tax = 0d;
    
    /** ״̬ **/
    private String status = null;
    
    /** ��������־ **/
    private String stoIOSign = null;
    
    /** ���۽�� **/
    private double soAmt = 0d;
    
    /** Ӧ�ս�� **/
    private double arAmt = 0d;
    
    /** ��ȯ���ý�� **/
    private double giftAmt = 0d;
    
    /** ���� **/
    private double cardAmt = 0d;
    
    /** ���ͷ� **/
    private double deliverAmt = 0d;
    
    /** �Ѹ���� **/
    private double payedAmt = 0d;
    
    /** �Ƶ��� **/
    private String creator = null;
    
    /** �Ƶ����� **/
    private Date createDate = null;
    
    /** ����� **/
    private String checkPerson = null;
    
    /** ������� **/
    private Date checkDate = null;
    
    /** ������ **/
    private String tallier = null;
    
    /** ����ʱ�� **/
    private Date tallyDate = null;
    
    /** �ڳ���ʶ **/
    private String isFirst = null;
    
    /** ��ע **/
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

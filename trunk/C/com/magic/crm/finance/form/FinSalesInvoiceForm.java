/*
 * Created on 2006-7-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.form;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.struts.action.ActionForm;

import com.magic.utils.Arith;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinSalesInvoiceForm extends ActionForm {

    /** ��ʼ���� **/
    private Date startDate = null;
    
    /** �������� **/
    private Date endDate = null;
    
    /** �����ʶ **/
    private int arID = 0;
    
    /** ���۷�Ʊ�� **/
    private String arNO = null;
    
    /** ���۶���ID **/
    private int soID = 0;
    
    /** ���۶����� **/
    private String soNO = null;
    
    /** ��Ӧ���� **/
    private String resNO = null;
    
    /** �������� **/
    private Date soDate = null;
    
    /** ҵ������ **/
    private String operationClass = null;
    
    /** ҵ�����ͣ����ƣ� **/
    private String operationClassName = null;
    
    /** ҵ��Ա **/
    private String operator = null;
    
    /** �ͻ����� **/
    private String customerID = null;
    
    /** �ͻ����� **/
    private String customerName = null;
    
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
    
    /** ��ϸ���ϼƣ� **/
    private double detailAmtTotal = 0d;
    
    /** �Ƶ��� **/
    private String creator = null;
    
    /** �Ƶ��� **/
    private int operatorID = 0;
    
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
    
    /** ��װ�� **/
    private double packageAmt = 0d;
    
    /** ��ϸ�ۼ� **/
    private Collection invoiceDetail = null;
    
    public FinSalesInvoiceForm() {
        invoiceDetail = new ArrayList();
    }
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
     * @return Returns the soNO.
     */
    public String getSoNO() {
        return soNO;
    }
    /**
     * @param soNO The soNO to set.
     */
    public void setSoNO(String soNO) {
        this.soNO = soNO;
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
    
    /**
     * @return Returns the customerName.
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * @param customerName The customerName to set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
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
     * @return Returns the operationClassName.
     */
    public String getOperationClassName() {
        return operationClassName;
    }
    /**
     * @param operationClassName The operationClassName to set.
     */
    public void setOperationClassName(String operationClassName) {
        this.operationClassName = operationClassName;
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
     * @return Returns the operatorID.
     */
    public int getOperatorID() {
        return operatorID;
    }
    /**
     * @param operatorID The operatorID to set.
     */
    public void setOperatorID(int operatorID) {
        this.operatorID = operatorID;
    }
    
    /**
     * @return Returns the invoiceDetail.
     */
    public Collection getInvoiceDetail() {
        return invoiceDetail;
    }
    /**
     * @param invoiceDetail The invoiceDetail to set.
     */
    public void setInvoiceDetail(Collection invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }
    
    /**
     * @return Returns the detailAmtTotal.
     */
    public double getDetailAmtTotal() {
        double total = 0d;
        Iterator it = this.getInvoiceDetail().iterator();
        while (it.hasNext()) {
            FinSalesInvoiceItemsForm form = (FinSalesInvoiceItemsForm)it.next();
            total += form.getTotalAmt();
        }
        return Arith.round(total, 2);
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
	/**
	 * @return the packageAmt
	 */
	public double getPackageAmt() {
		return packageAmt;
	}
	/**
	 * @param packageAmt the packageAmt to set
	 */
	public void setPackageAmt(double packageAmt) {
		this.packageAmt = packageAmt;
	}
    
}

/*
 * Created on 2006-7-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.form;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.action.ActionForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseForm extends ActionForm {
    
    /** ��ѯ��ʼ���� **/
    private Date startDate = null;
    
    /** ��ѯ�������� **/
    private Date endDate = null;
    
    /** ��Ӧ�̻��Ʒ��ǣ�1-����Ӧ�̲飬2-����Ʒ�飩 **/
    private int proOrItem = 0;
    
    /** ���ID **/
    private String ids = null;
    
    /** ��Ӧ�̻��Ʒ��ѯ���� **/
    private String proOrItemCondition = null;
    
    /** �ɹ���������ϸ **/
    private Collection purchaseDetail = null;
    
    /** ���� **/
    private int orderByCondition1 = 0;
    private int ascOrDesc1 = 0;
    private int orderByCondition2 = 0;
    private int ascOrDesc2 = 0;
    private int orderByCondition3 = 0;
    private int ascOrDesc3 = 0;
    
    
	public int getAscOrDesc1() {
		return ascOrDesc1;
	}

	public void setAscOrDesc1(int ascOrDesc1) {
		this.ascOrDesc1 = ascOrDesc1;
	}

	public int getAscOrDesc2() {
		return ascOrDesc2;
	}

	public void setAscOrDesc2(int ascOrDesc2) {
		this.ascOrDesc2 = ascOrDesc2;
	}

	public int getAscOrDesc3() {
		return ascOrDesc3;
	}

	public void setAscOrDesc3(int ascOrDesc3) {
		this.ascOrDesc3 = ascOrDesc3;
	}

	public int getOrderByCondition1() {
		return orderByCondition1;
	}

	public void setOrderByCondition1(int orderByCondition1) {
		this.orderByCondition1 = orderByCondition1;
	}

	public int getOrderByCondition2() {
		return orderByCondition2;
	}

	public void setOrderByCondition2(int orderByCondition2) {
		this.orderByCondition2 = orderByCondition2;
	}

	public int getOrderByCondition3() {
		return orderByCondition3;
	}

	public void setOrderByCondition3(int orderByCondition3) {
		this.orderByCondition3 = orderByCondition3;
	}

	public FinPurchaseForm() {
        purchaseDetail = new ArrayList();
    }
   
    /**
     * @return Returns the purchaseDetail.
     */
    public Collection getPurchaseDetail() {
        return purchaseDetail;
    }
    /**
     * @param purchaseDetail The purchaseDetail to set.
     */
    public void setPurchaseDetail(Collection purchaseDetail) {
        this.purchaseDetail = purchaseDetail;
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
    
    /** �����ʶ **/
    private int psID = 0;
    
    /** �ɹ��������� **/
    private String psCode = null;
    
    /** �������� **/
    private Date purchaseDate = null;
    
    /** ��Ӧ���� **/
    private String resNO = null;
    
    /** ҵ������ **/
    private String operationClass = null;
    
    /** ҵ������ **/
    private String operationClassName = null;
    
    /** ��������־ **/
    private String stockIOSign = null;
    
    /** ҵ��Ա **/
    private String operator = null;
    
    /** ��Ӧ�̱��� **/
    private String proNO = null;
    
    /** ��Ӧ������ **/
    private String proName = null;
    
    /** �ɹ����ͱ��� **/
    private String purType = null;
    
    /** ��ͷ˰�� **/
    private double tax = 0d;
    
    /** �Ƶ��� **/
    private int operatorID = 0;
    
    /** �Ƶ��� **/
    private String creator = null;
    
    /** �Ƶ����� **/
    private Date createDate =  null;
    
    /** ����� **/
    private String checkPerson = null;
    
    /** ������� **/
    private Date checkDate = null;
    
    /** ״̬ **/
    private String status = null;
    
    /** ����Ʊ��־ **/
    private String isRed = null;
    
    /** �Ƿ��ݹ� **/
    private String isTemp = null;
    
    /** �ֿ���� **/
    private String stoNO = null;
    
    /** ��ע **/
    private String remark = null;
    
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
     * @return Returns the proName.
     */
    public String getProName() {
        return proName;
    }
    /**
     * @param proName The proName to set.
     */
    public void setProName(String proName) {
        this.proName = proName;
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
     * @return Returns the isRed.
     */
    public String getIsRed() {
        return isRed;
    }
    /**
     * @param isRed The isRed to set.
     */
    public void setIsRed(String isRed) {
        this.isRed = isRed;
    }
    /**
     * @return Returns the isTemp.
     */
    public String getIsTemp() {
        return isTemp;
    }
    /**
     * @param isTemp The isTemp to set.
     */
    public void setIsTemp(String isTemp) {
        this.isTemp = isTemp;
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
     * @return Returns the purType.
     */
    public String getPurType() {
        return purType;
    }
    /**
     * @param purType The purType to set.
     */
    public void setPurType(String purType) {
        this.purType = purType;
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
     * @return Returns the proOrItem.
     */
    public int getProOrItem() {
        return proOrItem;
    }
    /**
     * @param proOrItem The proOrItem to set.
     */
    public void setProOrItem(int proOrItem) {
        this.proOrItem = proOrItem;
    }
    
    /**
     * @return Returns the ids.
     */
    public String getIds() {
        return ids;
    }
    /**
     * @param ids The ids to set.
     */
    public void setIds(String ids) {
        this.ids = ids;
    }
    /**
     * @return Returns the proOrItemCondition.
     */
    public String getProOrItemCondition() {
        return proOrItemCondition;
    }
    /**
     * @param proOrItemCondition The proOrItemCondition to set.
     */
    public void setProOrItemCondition(String proOrItemCondition) {
        this.proOrItemCondition = proOrItemCondition;
    }
}

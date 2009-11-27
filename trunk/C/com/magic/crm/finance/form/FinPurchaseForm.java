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
    
    /** 查询开始日期 **/
    private Date startDate = null;
    
    /** 查询结束日期 **/
    private Date endDate = null;
    
    /** 供应商或产品标记（1-按供应商查，2-按产品查） **/
    private int proOrItem = 0;
    
    /** 组合ID **/
    private String ids = null;
    
    /** 供应商或产品查询条件 **/
    private String proOrItemCondition = null;
    
    /** 采购单货单明细 **/
    private Collection purchaseDetail = null;
    
    /** 排序 **/
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
    
    /** 主表标识 **/
    private int psID = 0;
    
    /** 采购到货单号 **/
    private String psCode = null;
    
    /** 单据日期 **/
    private Date purchaseDate = null;
    
    /** 相应单号 **/
    private String resNO = null;
    
    /** 业务类型 **/
    private String operationClass = null;
    
    /** 业务名称 **/
    private String operationClassName = null;
    
    /** 库存进出标志 **/
    private String stockIOSign = null;
    
    /** 业务员 **/
    private String operator = null;
    
    /** 供应商编码 **/
    private String proNO = null;
    
    /** 供应商名称 **/
    private String proName = null;
    
    /** 采购类型编码 **/
    private String purType = null;
    
    /** 表头税率 **/
    private double tax = 0d;
    
    /** 制单人 **/
    private int operatorID = 0;
    
    /** 制单人 **/
    private String creator = null;
    
    /** 制单日期 **/
    private Date createDate =  null;
    
    /** 审核人 **/
    private String checkPerson = null;
    
    /** 审核日期 **/
    private Date checkDate = null;
    
    /** 状态 **/
    private String status = null;
    
    /** 负发票标志 **/
    private String isRed = null;
    
    /** 是否暂估 **/
    private String isTemp = null;
    
    /** 仓库代码 **/
    private String stoNO = null;
    
    /** 备注 **/
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

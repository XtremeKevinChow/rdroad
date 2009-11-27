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
public class FinPurchase {

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
    
    /** 库存进出标志 **/
    private String stockIOSign = null;
    
    /** 业务员 **/
    private String operator = null;
    
    /** 供应商编码 **/
    private String proNO = null;
    
    /** 采购类型编码 **/
    private String purType = null;
    
    /** 表头税率 **/
    private double tax = 0d;
    
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
}

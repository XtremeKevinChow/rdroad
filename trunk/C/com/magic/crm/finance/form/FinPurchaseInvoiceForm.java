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
import java.util.Iterator;
import com.magic.utils.Arith;

import org.apache.struts.action.ActionForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseInvoiceForm extends ActionForm {
    
    /** 操作人 **/
    private int operatorID = 0;
    
    /** 查询开始日期 **/
    private Date startDate = null;
    
    /** 查询结束日期 **/
    private Date endDate = null;
    
    /** 采购发票明细聚集 **/
    private Collection invoiceDetail = null;

    /** 主表标识 **/
    private int apID = 0;
    
    /** 采购到货单ID **/
    private int psID = 0;
    
    /** 系统发票号 **/
    private String sysAPCode = null;
    
    /** 发票日期 **/
    private Date invoiceDate = null;
    
    /** 实际发票号 **/
    private String factAPCode = null;
    
    /** 发票类型 **/
    private String apType = null;
    
    /** 业务类型 **/
    private String operationClass = null;
    
    /** 业务类型（名称） **/
    private String operationClassName = null;
    
    /** 开票日期 **/
    private Date createDate = null;
    
    /** 结算日期 **/
    private Date checkDate = null;
    
    /** 供应商 **/
    private String proNO = null;
    
    /** 供应商（名称） **/
    private String proName = null;
    
    /** 表头税率 **/
    private double tax = 0d;
    
    /** 制单人 **/
    private String creator = null;
    
    /** 审核人 **/
    private String checkPerson = null;
    
    /** 记帐人 **/
    private String tallier = null;
    
    /** 记帐时间 **/
    private Date tallyDate = null;
    
    /** 发票总金额 **/
    private double amt = 0d;
    
    /** 发票总金额（amt的同义字） **/
    private double amt0 = 0d;
    
    /** 状态 **/
    private String status = null;
    
    /** 期初标志 **/
    private String isFirst = null;
    
    /** 库存进出标志 **/
    private String stockIOSign = null;
    
    /** 备注 **/
    private String remark = null;
    
    /** 数量合计 **/
    private double qtyAll = 0d;
    
    /** 税额合计 **/
    private double taxAmtAll = 0d;
    
    /** 开票金额合计 **/
    private double amtAll = 0d;
    /** 成本差异合计 **/
    private double disAmtAll = 0d;
        
    public FinPurchaseInvoiceForm() {
        invoiceDetail = new ArrayList();
    }
    
    /**
     * 计算
     * @return
     */
    public void calc() {
        double value0 = 0, value1 = 0, value2 = 0, value3=0;
        if (invoiceDetail != null) {
            Iterator it = invoiceDetail.iterator();
            while (it.hasNext()) {
                FinPurchaseInvoiceItemsForm item = (FinPurchaseInvoiceItemsForm)it.next();
                value0 += item.getQty();
                value1 += item.getAmt();
                value2 += item.getTaxAmt();
                value3 += item.getDisAmt();
                
            }
        }
        this.qtyAll = Arith.round(value0, 0);
        this.amtAll = Arith.round(value1, 2);
        this.taxAmtAll = Arith.round(value2, 2);
        this.disAmtAll = Arith.round(value3, 2);
    }
    
    /**
     * @return Returns the qtyAll.
     */
    public double getQtyAll() {
        return qtyAll;
    }
    /**
     * @return Returns the disAmtAll.
     */
    public double getDisAmtAll() {
        return disAmtAll;
    }
    
    /**
     * @return Returns the amtAll.
     */
    public double getAmtAll() {
        return amtAll;
    }
    
    
    /**
     * @return Returns the taxAmtAll.
     */
    public double getTaxAmtAll() {
        return taxAmtAll;
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
     * @return Returns the amt.
     */
    public double getAmt0() {
        return amt;
    }
    
    /**
     * @param amt0 The amt0 to set.
     */
    public void setAmt0(double amt0) {
        this.amt = amt0;
        this.amt0 = amt0;
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

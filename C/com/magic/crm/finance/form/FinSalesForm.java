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
public class FinSalesForm extends ActionForm {
    
    /** 开始日期 **/
    private Date startDate = null;
    
    /** 结束日期 **/
    private Date endDate = null;
    
    /** 导入类型 **/
    private int dataType = 0;
    
    /** 主表标识 **/
    private int soID = 0;
    
    /** 销售订单号 **/
    private String soNO = null;
    
    /** 单据日期 **/
    private Date soDate = null;
    
    /** 业务类型 **/
    private String operationClass = null;
    
    /** 业务类型（名称） **/
    private String operationClassName = null;
    
    /** 客户编码 **/
    private String customerID = null;
    
    /** 客户名称 **/
    private String customerName = null;
    
    /** 销售类型 **/
    private String soType = null;
    
    /** 税率 **/
    private double tax = 0d;
    
    /** 相应单号 **/
    private String resNO = null;
    
    /** 库存进出标志 **/
    private String stockIOSign = null;
    
    /** 状态 **/
    private String status = null;
    
    /** 是否退货 **/
    private String isReturn = null;
    
    /** 销售金额 **/
    private double soAmt = 0d;
    
    /** 应收金额 **/
    private double arAmt = 0d;
    
    /** 明细金额（合计） **/
    private double detailAmtTotal = 0d;
    
    /** 礼券抵用金额 **/
    private double giftAmt = 0d;
    
    /** 卡费 **/
    private double cardAmt = 0d;
    
    /** 发送费 **/
    private double deliverAmt = 0d;
    
    /** 已付金额 **/
    private double payedAmt = 0d;
    
    /** 制单人 **/
    private int operatorID = 0;
    
    /** 制单人 **/
    private String creator = null;
    
    /** 制单日期 **/
    private Date createDate = null;
    
    /** 审核人 **/
    private String checkPerson = null;
    
    /** 审核日期 **/
    private Date checkDate = null;
    
    /** 备注 **/
    private String remark = null;
    
    /** 包装费 **/
    private double packageAmt = 0d;
    
    /** 明细聚集 **/
    private Collection salesDetail = null;

    public FinSalesForm() {
        salesDetail = new ArrayList();
    }
    /** 产品类型 **/
    private String item_type = null;
    /** 数量 **/
    private int op_qty = 0;
    /** 金额 **/
    private double pre_amt = 0;
    /** ** **/
    private int op_class = 0;


    /**
     * @return Returns the item_type.
     */
    public String getItem_type() {
        return item_type;
    }
    /**
     * @param item_type The item_type to set.
     */
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }
    /**
     * @return Returns the op_qty.
     */
    public int getOp_qty() {
        return op_qty;
    }
    /**
     * @param op_qty The op_qty to set.
     */
    public void setOp_qty(int op_qty) {
        this.op_qty = op_qty;
    }
    /**
     * @return Returns the op_class.
     */
    public int getOp_class() {
        return op_class;
    }
    /**
     * @param op_class The op_class to set.
     */
    public void setOp_class(int op_class) {
        this.op_class = op_class;
    }
    
    /**
     * @return Returns the pre_amt.
     */
    public double getPre_amt() {
        return pre_amt;
    }
    /**
     * @param pre_amt The pre_amt to set.
     */
    public void setPre_amt(double pre_amt) {
        this.pre_amt = pre_amt;
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
     * @return Returns the isReturn.
     */
    public String getIsReturn() {
        return isReturn;
    }
    /**
     * @param isReturn The isReturn to set.
     */
    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
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
     * @return Returns the salesDetail.
     */
    public Collection getSalesDetail() {
        return salesDetail;
    }
    /**
     * @param salesDetail The salesDetail to set.
     */
    public void setSalesDetail(Collection salesDetail) {
        this.salesDetail = salesDetail;
    }
    
    /**
     * @return Returns the dataType.
     */
    public int getDataType() {
        return dataType;
    }
    /**
     * @param dataType The dataType to set.
     */
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
    
    /**
	 * @return the packageFee
	 */
	public double getPackageAmt() {
		return packageAmt;
	}
	/**
	 * @param packageFee the packageFee to set
	 */
	public void setPackageAmt(double packageAmt) {
		this.packageAmt = packageAmt;
	}
    /**
     * @return Returns the detailAmtTotal.
     */
    public double getDetailAmtTotal() {
        double total = 0d;
        Iterator it = this.getSalesDetail().iterator();
        while (it.hasNext()) {
            FinSalesItemsForm form = (FinSalesItemsForm)it.next();
            total += form.getTotalAmt();
        }
        return Arith.round(total, 2);
    }
    
    public void reset() {
        /** 开始日期 **/
        startDate = null;
        
        /** 结束日期 **/
        endDate = null;
        
        /** 导入类型 **/
        dataType = 0;
        
        /** 主表标识 **/
        soID = 0;
        
        /** 销售订单号 **/
        soNO = null;
        
        /** 单据日期 **/
        soDate = null;
        
        /** 业务类型 **/
        operationClass = null;
        
        /** 业务类型（名称） **/
        operationClassName = null;
        
        /** 客户编码 **/
        customerID = null;
        
        /** 客户名称 **/
        customerName = null;
        
        /** 销售类型 **/
        soType = null;
        
        /** 税率 **/
        tax = 0d;
        
        /** 相应单号 **/
        resNO = null;
        
        /** 库存进出标志 **/
        stockIOSign = null;
        
        /** 状态 **/
        status = null;
        
        /** 是否退货 **/
        isReturn = null;
        
        /** 销售金额 **/
        soAmt = 0d;
        
        /** 应收金额 **/
        arAmt = 0d;
        
        /** 明细金额（合计） **/
        detailAmtTotal = 0d;
        
        /** 礼券抵用金额 **/
        giftAmt = 0d;
        
        /** 卡费 **/
        cardAmt = 0d;
        
        /** 发送费 **/
        deliverAmt = 0d;
        
        /** 已付金额 **/
        payedAmt = 0d;
        
        /** 制单人 **/
        operatorID = 0;
        
        /** 制单人 **/
        creator = null;
        
        /** 制单日期 **/
        createDate = null;
        
        /** 审核人 **/
        checkPerson = null;
        
        /** 审核日期 **/
        checkDate = null;
        
        /** 备注 **/
        remark = null;
        
        /** 包装费 **/
        packageAmt = 0d;
        
        /** 明细聚集 **/
        salesDetail = null;
    }
	
    
}

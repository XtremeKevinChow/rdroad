/*
 * Created on 2006-7-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.form;

import com.magic.crm.common.pager.PagerForm;
import java.util.Collection;
import java.util.ArrayList;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomerForm extends PagerForm {
    
    /** 客户代码（PK） **/
    private String customerNO = null;
    
    /** 客户名称 **/
    private String customerName = null;
    
    /** 类型 **/
    private int typeID = 0;
    
    /** 类型列表 **/
    private Collection customerTypeList = null;
    
    /** 类型（实名） **/
    private String typeName = null;
    
    /** 备注 **/
    private String remark = null;
    
    public CustomerForm() {
        customerTypeList = new ArrayList();
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
     * @return Returns the customerNO.
     */
    public String getCustomerNO() {
        return customerNO;
    }
    /**
     * @param customerNO The customerNO to set.
     */
    public void setCustomerNO(String customerNO) {
        this.customerNO = customerNO;
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
     * @return Returns the typeID.
     */
    public int getTypeID() {
        return typeID;
    }
    /**
     * @param typeID The typeID to set.
     */
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
    
    /**
     * @return Returns the typeName.
     */
    public String getTypeName() {
        return typeName;
    }
    /**
     * @param typeName The typeName to set.
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    /**
     * @return Returns the customerTypeList.
     */
    public Collection getCustomerTypeList() {
        return customerTypeList;
    }
    /**
     * @param customerTypeList The customerTypeList to set.
     */
    public void setCustomerTypeList(Collection customerTypeList) {
        this.customerTypeList = customerTypeList;
    }
    
}

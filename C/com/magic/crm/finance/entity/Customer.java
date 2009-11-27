/*
 * Created on 2006-7-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.entity;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Customer {
    
    /** 客户代码（PK） **/
    private String customerNO = null;
    
    /** 客户名称 **/
    private String customerName = null;
    
    /** 类型 **/
    private int typeID = 0;
   
    /** 备注 **/
    private String remark = null;
    
    /** 是否超链接（0-否，1-是） **/
    private int isHref = 0;
    
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
     * @return Returns the isHref.
     */
    public int getIsHref() {
        if (typeID == 5 || typeID == 4) {
            return 0;
        } else {
            return 1;
        }
        
    }
}

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
public class CustomerType {
    
    private int typeID = 0;
    
    private String typeDesc = null;
    
    /**
     * @return Returns the typeDesc.
     */
    public String getTypeDesc() {
        return typeDesc;
    }
    /**
     * @param typeDesc The typeDesc to set.
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
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
}

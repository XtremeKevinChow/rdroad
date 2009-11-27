/*
 * Created on 2006-7-20
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
public class FinDataImportForm extends ActionForm {
    
    private Date startDate = null;
    
    private Date endDate = null;
    
    private int operatorID = 0;
    
    private int periodID = 0;
    
    private String clazzName = null;
    
    private Collection periodList = new ArrayList();
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
     * @return Returns the clazzName.
     */
    public String getClazzName() {
        return clazzName;
    }
    /**
     * @param clazzName The clazzName to set.
     */
    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }
    
    /**
     * @return Returns the periodID.
     */
    public int getPeriodID() {
        return periodID;
    }
    /**
     * @param periodID The periodID to set.
     */
    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }
    
    /**
     * @return Returns the periodList.
     */
    public Collection getPeriodList() {
        return periodList;
    }
    /**
     * @param periodList The periodList to set.
     */
    public void setPeriodList(Collection periodList) {
        this.periodList = periodList;
    }
}

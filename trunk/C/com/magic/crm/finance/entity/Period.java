/*
 * Created on 2006-7-6
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
public class Period {

    /** �����ID **/
    private int ID = 0;
    
    /** ����� **/
    private int year = 0;
    
    /** ����� **/
    private int month = 0;
    
    /** ��ʼ���� **/
    private Date beginDate = null;
    
    /** �������� **/
    private Date endDate = null;
    
    /** �Ƿ�ɾ�� **/
    private String isDeleted = null;
    
    /** �Ƿ����� **/
    private String isUsed = null;
    
    /** �Ƿ�ر� **/
    private String isClosed = null;
    
    /** ����ڣ���ʾ�� **/
    private String periodDisplay = null;
    
    /**
     * @return Returns the periodDisplay.
     */
    public String getPeriodDisplay() {
        return year + "��" + month + "��";
    }
    
    /**
     * @return Returns the beginDate.
     */
    public Date getBeginDate() {
        return beginDate;
    }
    /**
     * @param beginDate The beginDate to set.
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
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
     * @return Returns the iD.
     */
    public int getID() {
        return ID;
    }
    /**
     * @param id The iD to set.
     */
    public void setID(int id) {
        ID = id;
    }
    /**
     * @return Returns the isClosed.
     */
    public String getIsClosed() {
        return isClosed;
    }
    /**
     * @param isClosed The isClosed to set.
     */
    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }
    /**
     * @return Returns the isDeleted.
     */
    public String getIsDeleted() {
        return isDeleted;
    }
    /**
     * @param isDeleted The isDeleted to set.
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
    /**
     * @return Returns the isUsed.
     */
    public String getIsUsed() {
        return isUsed;
    }
    /**
     * @param isUsed The isUsed to set.
     */
    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }
    /**
     * @return Returns the month.
     */
    public int getMonth() {
        return month;
    }
    /**
     * @param month The month to set.
     */
    public void setMonth(int month) {
        this.month = month;
    }
    /**
     * @return Returns the year.
     */
    public int getYear() {
        return year;
    }
    /**
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }
}

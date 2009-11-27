/*
 * Created on 2006-6-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import java.sql.Date;
import org.apache.struts.action.ActionForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberBlackListForm extends ActionForm {
    
    /** 主键 **/
    private long ID = -1;
    
    /** 会员ID **/
    private long memberID = -1;
    
    /** 会员号 **/
    private String cardID = null;
    
    /** 备注 **/
    private String description = null;
    
    /** 创建日期 **/
    private Date createDate = null;
    
    /** 创建人 **/
    private long operatorID = -1;
    
    
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
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Returns the iD.
     */
    public long getID() {
        return ID;
    }
    /**
     * @param id The iD to set.
     */
    public void setID(long id) {
        ID = id;
    }
    /**
     * @return Returns the memberID.
     */
    public long getMemberID() {
        return memberID;
    }
    /**
     * @param memberID The memberID to set.
     */
    public void setMemberID(long memberID) {
        this.memberID = memberID;
    }
    /**
     * @return Returns the operatorID.
     */
    public long getOperatorID() {
        return operatorID;
    }
    /**
     * @param operatorID The operatorID to set.
     */
    public void setOperatorID(long operatorID) {
        this.operatorID = operatorID;
    }
    
    /**
     * @return Returns the cardID.
     */
    public String getCardID() {
        return cardID;
    }
    /**
     * @param cardID The cardID to set.
     */
    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}

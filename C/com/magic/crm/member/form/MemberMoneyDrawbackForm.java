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
public class MemberMoneyDrawbackForm extends ActionForm {
    
    /** 主键 **/
    private long ID = -1;
    
    /** 会员ID **/
    private long memberID = -1;
    
    /** 会员号 **/
    private String cardID = null;
    
    /** 会员名字 **/
    private String name = null;
    
    /** 创建人 **/
    private String operator_name = null;
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	/** 备注 **/
    private String description = null;
    
    /** 创建日期 **/
    private String createDate = null;
    
    /** 创建人 **/
    private long operatorID = -1;
    
    /** 审核人 **/
    private long auditor = -1;
    
    /** 审核日期 **/
    private Date audit_date = null;
    
    /** 状态 **/
    private int status =0;
    
    /** 金额 **/
    private double amount =0;
    
    
    
    public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getAuditor() {
		return auditor;
	}
	public void setAuditor(long auditor) {
		this.auditor = auditor;
	}
	public Date getAudit_date() {
		return audit_date;
	}
	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	/**
     * @return Returns the createDate.
     */
    public String getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate The createDate to set.
     */
    public void setCreateDate(String createDate) {
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


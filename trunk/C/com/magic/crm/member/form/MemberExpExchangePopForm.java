/*
 * Created on 2006-2-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

/**
 * @author ��
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberExpExchangePopForm extends ActionForm implements Serializable {
	
	/** ���� **/
	private long ID = 0;
	
	/** ���ֻ������� **/
	private String name = null;
	
	/** ���� **/
	private String description = null;
	
	/** ��ʼ���� **/
	private String startDate = null;
	
	/** �������� **/
	private String endDate = null;
	
	/** �Ƿ���Ч **/
	private String validFlag = "Y";
	
	/** �һ����� **/
	private int expType = 0;
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
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		if (endDate != null && endDate.length() >= 10) {
			return endDate.substring(0, 10);
		}
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		if (startDate != null && startDate.length() >= 10) {
			return startDate.substring(0, 10);
		}
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the validFlag.
	 */
	public String getValidFlag() {
		return ( validFlag == null || validFlag.equals("") ) ? "Y" : validFlag;
	}
	/**
	 * @param validFlag The validFlag to set.
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	
	
	/**
	 * @return Returns the expType.
	 */
	public int getExpType() {
		return expType;
	}
	/**
	 * @param expType The expType to set.
	 */
	public void setExpType(int expType) {
		this.expType = expType;
	}
}

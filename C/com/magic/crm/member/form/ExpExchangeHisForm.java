package com.magic.crm.member.form;

import java.io.Serializable;
import com.magic.crm.util.CodeName;

import java.util.Collection;
import com.magic.crm.common.pager.PagerForm;
/*
 * Created on 2006-2-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExpExchangeHisForm extends PagerForm implements Serializable {
	
	/** 主键 **/
	private int ID = 0;
	
	/** 发货单号 **/
	private String docNumber = null;
	
	/** 操作类型 **/
	private int opType = 0;
	
	/** 是否有效 **/
	private int isvalid = -1;
	
	/** 创建日期 **/
	private String createDate = null;
	
	/** 开始日期 **/
	private String startDate = null;
	
	/** 结束日期 **/
	private String endDate = null;
	
	/** 创建人 **/
	private String operatorName = null;
	
	/** 本年度积分 **/
	private int exp = 0;
	
	/** 累计积分 **/
	private int totalExp = 0;
	
	/** 会员ID **/
	private long memberID = -1L;
	
	/** 卡号 **/
	private String cardID = null;
	
	/** 操作类型列表 **/
	private Collection opTypeList = new java.util.ArrayList();
	
	private Collection isvalidList = new java.util.ArrayList();
	
	/**
	 * @return Returns the isvalidList.
	 */
	public Collection getIsvalidList() {
		isvalidList.add(new CodeName("-1", "--请选择--"));
		isvalidList.add(new CodeName("0", "未生效"));
		isvalidList.add(new CodeName("1", "有效"));
		isvalidList.add(new CodeName("2", "取消"));
		return isvalidList;
	}
	/**
	 * @return Returns the opTypeList.
	 */
	public Collection getOpTypeList() {
		opTypeList.add(new CodeName("", "--请选择--"));
		opTypeList.add(new CodeName("1", "积分兑换"));
		opTypeList.add(new CodeName("2", "礼品取消"));
		opTypeList.add(new CodeName("3", "核货"));
		opTypeList.add(new CodeName("4", "退货"));
		return opTypeList;
	}
	
	/**
	 * @param opTypeList The opTypeList to set.
	 */
	public void setOpTypeList(Collection opTypeList) {
		this.opTypeList = opTypeList;
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
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	
	/**
	 * @return Returns the docNumber.
	 */
	public String getDocNumber() {
		return docNumber;
	}
	/**
	 * @param docNumber The docNumber to set.
	 */
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	/**
	 * @return Returns the exp.
	 */
	public int getExp() {
		return exp;
	}
	/**
	 * @param exp The exp to set.
	 */
	public void setExp(int exp) {
		this.exp = exp;
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
	 * @return Returns the isvalid.
	 */
	public int getIsvalid() {
		return isvalid;
	}
	/**
	 * @param isvalid The isvalid to set.
	 */
	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
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
	 * @return Returns the operatorName.
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName The operatorName to set.
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return Returns the opType.
	 */
	public int getOpType() {
		return opType;
	}
	/**
	 * @param opType The opType to set.
	 */
	public void setOpType(int opType) {
		this.opType = opType;
	}
	/**
	 * @return Returns the totalExp.
	 */
	public int getTotalExp() {
		return totalExp;
	}
	/**
	 * @param totalExp The totalExp to set.
	 */
	public void setTotalExp(int totalExp) {
		this.totalExp = totalExp;
	}
}

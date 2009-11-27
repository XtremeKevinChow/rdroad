/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.action.ActionForm;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberaddMoneyForm extends ActionForm{
	public int 	ID=0;
	public int 	MB_ID=0;
	public String	MB_CODE="";
	public String MB_NAME="";
	public String 	ORDER_CODE="";
	public int	ORDER_ID=0;
	public String CREATE_DATE="";
	public String 	REMARK="";
	public double 	MONEY=0;
	public String REF_ID="";
	public String TYPE="";
	public int OPERATOR_ID=0;
	public int STATUS=0;
	public int payMethod = -1;
	private String searchRefId = null;
	private String searchMbName = null;
	private String searchUseType = "";
	private String beginDate = null;
	private String endDate = null;
	private int searchPayMethod = -1;
	private int filter1 = -1;
	private String BILL_DATE; // add by user 2008-03-26
	public String USE_TYPE = "0";
	public String ADDRESS="";
	public String payMethodName = "";
	
	private Collection payments = new ArrayList(); 
	
    public Collection getPayments() {
		return payments;
	}
	public void setPayments(Collection payments) {
		this.payments = payments;
	}
	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the searchPayMethod
	 */
	public int getSearchPayMethod() {
		return searchPayMethod;
	}
	/**
	 * @param searchPayMethod the searchPayMethod to set
	 */
	public void setSearchPayMethod(int searchPayMethod) {
		this.searchPayMethod = searchPayMethod;
	}
	/**
	 * @return the searchUseType
	 */
	public String getSearchUseType() {
		return searchUseType;
	}
	/**
	 * @param searchUseType the searchUseType to set
	 */
	public void setSearchUseType(String searchUseType) {
		this.searchUseType = searchUseType;
	}
	/**
	 * @return the filter1
	 */
	public int getFilter1() {
		return filter1;
	}
	/**
	 * @param filter1 the filter1 to set
	 */
	public void setFilter1(int filter1) {
		this.filter1 = filter1;
	}
	/**
	 * @return the searchMbName
	 */
	public String getSearchMbName() {
		return searchMbName;
	}
	/**
	 * @param searchMbName the searchMbName to set
	 */
	public void setSearchMbName(String searchMbName) {
		this.searchMbName = searchMbName;
	}
	/**
	 * @return the aDDRESS
	 */
	public String getADDRESS() {
		return ADDRESS;
	}
	/**
	 * @param address the aDDRESS to set
	 */
	public void setADDRESS(String address) {
		ADDRESS = address;
	}
	/**
	 * @return the mB_NAME
	 */
	public String getMB_NAME() {
		return MB_NAME;
	}
	/**
	 * @param mb_name the mB_NAME to set
	 */
	public void setMB_NAME(String mb_name) {
		MB_NAME = mb_name;
	}
	/**
	 * @param ref_id the rEF_ID to set
	 */
	public void setREF_ID(String ref_id) {
		REF_ID = ref_id;
	}
	/**
	 * @return the uSE_TYPE
	 */
	public String getUSE_TYPE() {
		return USE_TYPE;
	}
	/**
	 * @param use_type the uSE_TYPE to set
	 */
	public void setUSE_TYPE(String use_type) {
		USE_TYPE = use_type;
	}
	/**
	 * @return the bILL_DATE
	 */
	public String getBILL_DATE() {
		return BILL_DATE;
	}
	/**
	 * @param bill_date the bILL_DATE to set
	 */
	public void setBILL_DATE(String bill_date) {
		BILL_DATE = bill_date;
	}
	/**
     * @return Returns the searchRefId.
     */
    public String getSearchRefId() {
        return searchRefId;
    }
    /**
     * @param searchRefId The searchRefId to set.
     */
    public void setSearchRefId(String searchRefId) {
        this.searchRefId = searchRefId;
    }
	/*
	 * get --- set 
	 */
	public int getID(){
		return this.ID;
	}
	public void setID(int iID){
		this.ID=iID;
	}
	
	public int getMB_ID(){
		return this.MB_ID;
	}
	public void setMB_ID(int iMB_ID){
		this.MB_ID=iMB_ID;
	}
	public String getMB_CODE(){
		return this.MB_CODE;
	}
	public void setMB_CODE(String iMB_CODE){
		this.MB_CODE=iMB_CODE;
	}
	public String getORDER_CODE(){
		return this.ORDER_CODE;
	}
	public void setORDER_CODE(String iORDER_CODE){
		this.ORDER_CODE=iORDER_CODE;
	}
	public int getORDER_ID(){
		return this.ORDER_ID;
	}
	public void setORDER_ID(int iORDER_ID){
		this.ORDER_ID=iORDER_ID;
	}
	public String getCREATE_DATE(){
		return this.CREATE_DATE;
	}
	public void setCREATE_DATE(String iCREATE_DATE){
		this.CREATE_DATE=iCREATE_DATE;
	}
	public String getREMARK(){
		return this.REMARK;
	}
	public void setREMARK(String iREMARK){
		this.REMARK=iREMARK;
	}
	public double getMONEY(){
		return this.MONEY;
	}
	public void setMONEY(double iMONEY){
		this.MONEY=iMONEY;
	}

	public String getREF_ID(){
		return this.REF_ID;
	}
	public void setMEMBERID(String iREF_ID){
		this.REF_ID=iREF_ID;
	}
	public String getTYPE(){
		return this.TYPE;
	}
	public void setTYPE(String iTYPE){
		this.TYPE=iTYPE;
	}	
	public int getStatus(){
		return this.STATUS;
	}
	public void setStatus(int iStatus){
		this.STATUS=iStatus;
	}	
	public int getOPERATOR_ID(){
		return this.OPERATOR_ID;
	}
	public void setOPERATOR_ID(int iOPERATOR_ID){
		this.OPERATOR_ID=iOPERATOR_ID;
	}
	public int getPayMethod(){
		return this.payMethod;
	}
	public void setPayMethod(int payMethod){
		this.payMethod=payMethod;
	}	
	
	public String getPayMethodName(){
		return this.payMethodName;
	}
	public void setPayMethodName(String payMethodName){
		this.payMethodName=payMethodName;
	}	
	/*
	 * 	 
	 */

	public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
		ID	=0;
		MB_ID	=0;
		MB_CODE	="";
		ORDER_CODE	="";
		ORDER_ID	=0;
		CREATE_DATE	="";
		REMARK	="";
		MONEY=0;
		REF_ID="";
		TYPE="";
		STATUS=0;
		OPERATOR_ID=0;
	   }	

}

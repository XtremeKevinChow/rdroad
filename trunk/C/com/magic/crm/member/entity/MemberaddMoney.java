/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.entity;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberaddMoney {
	public MemberaddMoney(){
		
	}
	//主键
	public int 	ID=0;
	//会员id
	public int 	MB_ID=0;
	//会员号
	public String MB_CODE="";
	//会员姓名
	public String MB_NAME = "";
	//订单号
	public String 	ORDER_CODE="";
	//订单id
	public int	ORDER_ID=0;
	//地址
	public String ADDRESS="";
	//创建日期
	public String CREATE_DATE="";
	//会员备注
	public String 	REMARK="";
	//汇款金额
	public double 	MONEY=0;
	//汇号（唯一编号）
	public String REF_ID="";
	//导入文件类型（0-邮局大综单；2-邮局加密单；3-线下现金）
	public String TYPE="";
	//操作人id
	public int OPERATOR_ID=0;
	//状态
	public int STATUS=0;
	//单据日期（废弃）
	public String PostDate="";
	//支付方式
	public int payMethod = 0;
	//public String MB_CODE1="";
	//public String ORDER_CODE1="";
	//邮编
	public String postCode = "";
	
	//用途 0-预存款；1-购买书香卡
	public String USE_TYPE = "0";
	//单据日期
	public String bill_date = null;
	
	
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
	 * @return Returns the postCode.
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode The postCode to set.
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/*
	 * get --- set 
	 */
	public int getPayMethod(){
		return this.payMethod;
	}
	public void setPayMethod(int ipayMethod){
		this.payMethod=ipayMethod;
	}
	
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
	/*public String getMB_CODE1(){
		return this.MB_CODE1;
	}
	public void setMB_CODE1(String iMB_CODE1){
		this.MB_CODE1=iMB_CODE1;
	}	
	public String getORDER_CODE1(){
		return this.ORDER_CODE1;
	}
	public void setORDER_CODE1(String iORDER_CODE1){
		this.ORDER_CODE1=iORDER_CODE1;
	}	*/
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
	public void setREF_ID(String iREF_ID){
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
	public String getPostDate(){
		return this.PostDate;
	}
	public void setPostDate(String iPostDate){
		this.PostDate=iPostDate;
	}
	/**
	 * @return the bill_date
	 */
	public String getBill_date() {
		return bill_date;
	}
	/**
	 * @param bill_date the bill_date to set
	 */
	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
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
	
	
}

/*
 * Created on 2005-2-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import org.apache.struts.action.ActionForm;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InquiryForm extends ActionForm{
	public int 	EVENT_ID=0;
	public String	SOLVE_METHOD="";
	public String 	SOLVE_PERSON="";
	public String	SOLVE_DATE="";
	public int 	IS_SOLVE=0;
	public int 	INQUIRY_TYPE=0;
	public int 	INQUIRY_LEVEL=0;
	public int CREATEID=0;
	public String MEMBERID="";
	public int ROOTID=0;
	public int ref_department=0;
	public int STATUS=0;
	public int IS_ANSWER=0;
	public String	SOLVE_DATE2="";
	
	/*
	 * get --- set 
	 */
	public int getEVENT_ID(){
		return this.EVENT_ID;
	}
	public void setEVENT_ID(int iEVENT_ID){
		this.EVENT_ID=iEVENT_ID;
	}
	
	public String getSOLVE_METHOD(){
		return this.SOLVE_METHOD;
	}
	public void setSOLVE_METHOD(String iSOLVE_METHOD){
		this.SOLVE_METHOD=iSOLVE_METHOD;
	}
	public String getSOLVE_PERSON(){
		return this.SOLVE_PERSON;
	}
	public void setSOLVE_PERSON(String iSOLVE_PERSON){
		this.SOLVE_PERSON=iSOLVE_PERSON;
	}
	public String getSOLVE_DATE(){
		return this.SOLVE_DATE;
	}
	public void setSOLVE_DATE(String iSOLVE_DATE){
		this.SOLVE_DATE=iSOLVE_DATE;
	}
	public String getSOLVE_DATE2(){
		return this.SOLVE_DATE2;
	}
	public void setSOLVE_DATE2(String iSOLVE_DATE2){
		this.SOLVE_DATE2=iSOLVE_DATE2;
	}	
	public int getIS_SOLVE(){
		return this.IS_SOLVE;
	}
	public void setIS_SOLVE(int iIS_SOLVE){
		this.IS_SOLVE=iIS_SOLVE;
	}
	public int getINQUIRY_TYPE(){
		return this.INQUIRY_TYPE;
	}
	public void setINQUIRY_TYPE(int iINQUIRY_TYPE){
		this.INQUIRY_TYPE=iINQUIRY_TYPE;
	}
	public int getINQUIRY_LEVEL(){
		return this.INQUIRY_LEVEL;
	}
	public void setINQUIRY_LEVEL(int iINQUIRY_LEVEL){
		this.INQUIRY_LEVEL=iINQUIRY_LEVEL;
	}
	public int getCREATEID(){
		return this.CREATEID;
	}
	public void setCREATEID(int iCREATEID){
		this.CREATEID=iCREATEID;
	}
	public int getROOTID(){
		return this.ROOTID;
	}
	public void setROOTID(int iROOTID){
		this.ROOTID=iROOTID;
	}	
	public String getMEMBERID(){
		return this.MEMBERID;
	}
	public void setMEMBERID(String iMEMBERID){
		this.MEMBERID=iMEMBERID;
	}
	public int getref_department(){
		return this.ref_department;
	}
	public void setref_department(int iref_department){
		this.ref_department=iref_department;
	}	
	public int getStatus(){
		return this.STATUS;
	}
	public void setStatus(int iStatus){
		this.STATUS=iStatus;
	}	
	public int getIS_ANSWER(){
		return this.IS_ANSWER;
	}
	public void setIS_ANSWER(int iIS_ANSWER){
		this.IS_ANSWER=iIS_ANSWER;
	}	
	/*
	 * 	 
	 */

	public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
		EVENT_ID	=0;
		SOLVE_METHOD	="";
		SOLVE_PERSON	="";
		SOLVE_DATE	="";
		IS_SOLVE	=0;
		INQUIRY_TYPE	=0;
		INQUIRY_LEVEL	=0;
		CREATEID=0;
		MEMBERID="";
		ROOTID=0;
		STATUS=0;
	   }	
}

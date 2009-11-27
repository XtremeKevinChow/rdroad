/*
 * Created on 2005-1-31
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
public class MemberEvents {


	protected int EVENT_TYPE=0;
	protected int REF_DOC_ID=0;
	protected String CREATE_DATE="";
	protected String COMMENTS="";
	protected String EVENT_DATE="";
	protected int MEMBER_ID=0;
	protected int ID=0;
	protected int OPERATOR_ID=0;
	protected int MemgetMemID=0;
	protected int Gift_ID=0;
	

	public void setGift_ID(int iGift_ID){
		this.Gift_ID=iGift_ID;
	}
	public int getGift_ID(){
		 return this.Gift_ID;
	}	
	public void setMemgetMemID(int iMemgetMemID){
		this.MemgetMemID=iMemgetMemID;
	}
	public int getMemgetMemID(){
		 return this.MemgetMemID;
	}	
	public void setOPERATOR_ID(int iOPERATOR_ID){
		this.OPERATOR_ID=iOPERATOR_ID;
	}
	public int getOPERATOR_ID(){
		 return this.OPERATOR_ID;
	}	
	public void setEVENT_TYPE(int iEVENT_TYPE){
		this.EVENT_TYPE=iEVENT_TYPE;
	}
	public int getEVENT_TYPE(){
		 return this.EVENT_TYPE;
	}
	public void setREF_DOC_ID(int iREF_DOC_ID){
		this.REF_DOC_ID=iREF_DOC_ID;
	}
	public int getREF_DOC_ID(){
		 return this.REF_DOC_ID;
	}
	public void setCREATE_DATE(String iCREATE_DATE){
		this.CREATE_DATE=iCREATE_DATE;
	}
	public String getCREATE_DATE(){
		 return this.CREATE_DATE;
	}
	public void setCOMMENTS(String iCOMMENTS){
		this.COMMENTS=iCOMMENTS;
	}
	public String getCOMMENTS(){
		 return this.COMMENTS;
	}
	public void setEVENT_DATE(String iEVENT_DATE){
		this.EVENT_DATE=iEVENT_DATE;
	}
	public String getEVENT_DATE(){
		 return this.EVENT_DATE;
	}	
	public void setMEMBER_ID(int iMEMBER_ID){
		this.MEMBER_ID=iMEMBER_ID;
	}
	public int getMEMBER_ID(){
		 return this.MEMBER_ID;
	}
	public void setID(int id){
		this.ID=id;
	}
	public int getID(){
		 return this.ID;
	}
}

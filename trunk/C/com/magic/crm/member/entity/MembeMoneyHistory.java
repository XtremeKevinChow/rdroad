/*
 * Created on 2005-3-3
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
public class MembeMoneyHistory {
	public MembeMoneyHistory(){
		
	}
	public int 	ID=0;
	public int 	MEMBER_ID=0;
	public String MODIFY_DATE="";
	public String 	COMMENTS="";
	public double 	DEPOSIT=0;
	public double 	MONEY_UPDATE=0;
	public int OPERATOR_ID=0;
	public int	EVENT_TYPE=0;
	public int payMethod = 0;
	public String payMethodName = "";
	private String credence = null;
	
    /**
     * @return Returns the credence.
     */
    public String getCredence() {
        return credence;
    }
    /**
     * @param credence The credence to set.
     */
    public void setCredence(String credence) {
        this.credence = credence;
    }
	/*
	 * 其他信息
	 */
	public String OPERATOR_NAME="";
	public String EVENT_TYPE_NAME="";
	public String CARD_ID="";
	public String CARD_NAME="";
	
	public String getOPERATOR_NAME(){
		return this.OPERATOR_NAME;
	}
	public void setOPERATOR_NAME(String iOPERATOR_NAME){
		this.OPERATOR_NAME=iOPERATOR_NAME;
	}
	public String getEVENT_TYPE_NAME(){
		return this.EVENT_TYPE_NAME;
	}
	public void setEVENT_TYPE_NAME(String iEVENT_TYPE_NAME){
		this.EVENT_TYPE_NAME=iEVENT_TYPE_NAME;
	}
	public String getCARD_ID(){
		return this.CARD_ID;
	}
	public void setCARD_ID(String iCARD_ID){
		this.CARD_ID=iCARD_ID;
	}
	public String getCARD_NAME(){
		return this.CARD_NAME;
	}
	public void setCARD_NAME(String iCARD_NAME){
		this.CARD_NAME=iCARD_NAME;
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
	
	public int getOPERATOR_ID(){
		return this.OPERATOR_ID;
	}
	public void setOPERATOR_ID(int iOPERATOR_ID){
		this.OPERATOR_ID=iOPERATOR_ID;
	}	
	
	public int getMEMBER_ID(){
		return this.MEMBER_ID;
	}
	public void setMEMBER_ID(int iMB_ID){
		this.MEMBER_ID=iMB_ID;
	}
	
	public double getDEPOSIT(){
		return this.DEPOSIT;
	}
	public void setDEPOSIT(double iMONEY){
		this.DEPOSIT=iMONEY;
	}
	
	public double getMONEY_UPDATE(){
		return this.MONEY_UPDATE;
	}
	public void setMONEY_UPDATE(double iMONEY_UPDATE){
		this.MONEY_UPDATE=iMONEY_UPDATE;
	}
	
	public String getMODIFY_DATE(){
		return this.MODIFY_DATE;
	}
	public void setMODIFY_DATE(String iCREATE_DATE){
		this.MODIFY_DATE=iCREATE_DATE;
	}	

	public String getCOMMENTS(){
		return this.COMMENTS;
	}
	public void setCOMMENTS(String iREMARK){
		this.COMMENTS=iREMARK;
	}
	
	public int getEVENT_TYPE(){
		return this.EVENT_TYPE;
	}
	public void setEVENT_TYPE(int iTYPE){
		this.EVENT_TYPE=iTYPE;
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
	
	
}

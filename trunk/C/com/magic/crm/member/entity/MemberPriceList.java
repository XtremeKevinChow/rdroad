/*
 * Created on 2005-2-17
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
public class MemberPriceList {
  public String MSC="";
  public String Name="";
	public String getMSC(){
		return this.MSC;
	}
	public void setMSC(String iMSC){
		this.MSC=iMSC;
	}
	public String getName(){
		return this.Name;
	}
	public void setName(String iName){
		this.Name=iName;
	}
	public MemberPriceList(){
		
	}
}

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
public class MemberAddresses {
	public  int ID=0;
	public  int Member_ID=0;
	protected String Postcode="";
	protected String Telephone="";
	protected String Relation_person="";
	protected String Delivery_address="";
	protected String section = "";
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * add by user 2008-03-27
	 * 发货单上可显示多个电话
	 */
	protected String telephone2="";
	
	/**
	 * @return the telephone2
	 */
	public String getTelephone2() {
		return telephone2;
	}
	/**
	 * @param telephone2 the telephone2 to set
	 */
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}
	public void setID(int iID){
		this.ID=iID;
	}
	public int getID(){
		 return this.ID;
	}
	
	public void setMember_ID(int member_ID){
		this.Member_ID=member_ID;
	}
	public int getMember_ID(){
		 return this.Member_ID;
	}
	
	public void setPostcode(String postcode){
		this.Postcode=postcode;
	}
	public String getPostcode(){
		 return this.Postcode;
	}
	
	public void setTelephone(String telephone){
		this.Telephone=telephone;
	}
	public String getTelephone(){
		 return this.Telephone;
	}
	
	public void setRelation_person(String relation_person){
		this.Relation_person=relation_person;
	}
	public String getRelation_person(){
		 return this.Relation_person;
	}
	public void setDelivery_address(String delivery_address){
		this.Delivery_address=delivery_address;
	}
	public String getDelivery_address(){
		 return this.Delivery_address;
	}

}

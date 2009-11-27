/*
 * Created on 2006-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.form;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Prd_pricelistForm {
	protected int ID=0;
	protected int price_type_id=0;
	protected int status=0;
	protected int recruitment_type=0;
	protected int company_id=0;
	protected int is_valid=0;
	protected double entry_fee=0;
	protected String name="";
	protected String effect_date="";
	protected String expirped_date="";
	protected String description="";
	protected String msc="";
	protected String periodical_id="";
	protected int member_category_id=0;
	/*******************************/
    public int getID(){
        return ID;
    }
    public void setID(int iID){
        this.ID=iID;
    }	
	/*******************************/
    public int getPrice_type_id(){
        return price_type_id;
    }
    public void setPrice_type_id(int price_type_id){
        this.price_type_id=price_type_id;
    }
	/*******************************/
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
	/*******************************/
    public int getRecruitment_type(){
        return recruitment_type;
    }
    public void setRecruitment_type(int recruitment_type){
        this.recruitment_type=recruitment_type;
    }
	/*******************************/
    public int getCompany_id(){
        return company_id;
    }
    public void setCompany_id(int company_id){
        this.company_id=company_id;
    }
	/*******************************/
    public int getIs_valid(){
        return is_valid;
    }
    public void setIs_valid(int is_valid){
        this.is_valid=is_valid;
    }

	/*******************************/
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }	
	/*******************************/
    public String getEffect_date(){
        return effect_date;
    }
    public void setEffect_date(String effect_date){
        this.effect_date=effect_date;
    }
	/*******************************/
    public String getExpirped_date(){
        return expirped_date;
    }
    public void setExpirped_date(String expirped_date){
        this.expirped_date=expirped_date;
    }
	/*******************************/
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
	/*******************************/
    public String getMsc(){
        return msc;
    }
    public void setMsc(String msc){
        this.msc=msc;
    }
	/*******************************/
    public double getEntry_fee(){
        return entry_fee;
    }
    public void setEntry_fee(double entry_fee){
        this.entry_fee=entry_fee;
    }	 
	/*******************************/
    public String getPeriodical_id(){
        return periodical_id;
    }
    public void setPeriodical_id(String iperiodical_id){
        this.periodical_id=iperiodical_id;
    }	
	/*******************************/
    public int getMember_category_id(){
        return member_category_id;
    }
    public void setMember_category_id(int imember_category_id){
        this.member_category_id=imember_category_id;
    }    
}

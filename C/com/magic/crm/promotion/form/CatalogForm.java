/*
 * Created on 2006-10-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.form;

import org.apache.struts.action.ActionForm;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CatalogForm extends ActionForm{
	protected int ID=0;
	protected int company_id=0;
	protected int pricelist_id=0;
	protected int member_category_id=0;
	protected int operator_id=0;
	protected int msc_id=0;
	protected String periodical_id="";
	protected String operate_time="";
	protected String catalogs_name="";
	
	
	protected int price_type_id=0;
	protected int status=0;
	protected int recruitment_type=0;
	protected int is_valid=0;
	protected double entry_fee=0;
	protected String name="";
	protected String effect_date="";
	protected String expirped_date="";
	protected String description="";
	protected String msc="";
	protected String recruitment_name="";
	protected String gift_number="";


	/**
	 * @return the msc_id
	 */
	public int getMsc_id() {
		return msc_id;
	}
	/**
	 * @param msc_id the msc_id to set
	 */
	public void setMsc_id(int msc_id) {
		this.msc_id = msc_id;
	}
	/**
	 * @return the recruitment_name
	 */
	public String getRecruitment_name() {
		return recruitment_name;
	}
	/**
	 * @param recruitment_name the recruitment_name to set
	 */
	public void setRecruitment_name(String recruitment_name) {
		this.recruitment_name = recruitment_name;
	}
	/**
	 * @return the gift_number
	 */
	public String getGift_number() {
		return gift_number;
	}
	/**
	 * @param gift_number the gift_number to set
	 */
	public void setGift_number(String gift_number) {
		this.gift_number = gift_number;
	}
	/*******************************/
    public int getID(){
        return ID;
    }
    public void setID(int iID){
        this.ID=iID;
    }	
	/*******************************/
    public int getCompany_id(){
        return company_id;
    }
    public void setCompany_id(int icompany_id){
        this.company_id=icompany_id;
    }	
	/*******************************/
    public int getPricelist_id(){
        return pricelist_id;
    }
    public void setPricelist_id(int ipricelist_id){
        this.pricelist_id=ipricelist_id;
    }	
	/*******************************/
    public int getMember_category_id(){
        return member_category_id;
    }
    public void setMember_category_id(int imember_category_id){
        this.member_category_id=imember_category_id;
    }	
	/*******************************/
    public int getOperator_id(){
        return operator_id;
    }
    public void setOperator_id(int ioperator_id){
        this.operator_id=ioperator_id;
    }	
	/*******************************/
    public int getMscID(){
        return msc_id;
    }
    public void setMscID(int msc_id){
        this.msc_id=msc_id;
    }	
	/*******************************/
    public String getPeriodical_id(){
        return periodical_id;
    }
    public void setPeriodical_id(String iperiodical_id){
        this.periodical_id=iperiodical_id;
    }	   
	/*******************************/
    public String getOperate_time(){
        return operate_time;
    }
    public void setOperate_time(String ioperate_time){
        this.operate_time=ioperate_time;
    }	  
	/*******************************/
    public String getCatalogs_name(){
        return catalogs_name;
    }
    public void setCatalogs_name(String icatalogs_name){
        this.catalogs_name=icatalogs_name;
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
    public String getRecruitmentName(){
        return recruitment_name;
    }
    public void setRecruitmentName(String recruitment_name){
        this.recruitment_name=recruitment_name;
    }    
	/*******************************/
    public double getEntry_fee(){
        return entry_fee;
    }
    public void setEntry_fee(double entry_fee){
        this.entry_fee=entry_fee;
    }	 

}

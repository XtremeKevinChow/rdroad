/*
 * Created on 2006-12-1
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
public class Mbr_use_gift_groupForm extends ActionForm{
	protected int ID=0;

	protected String gift_number="";
	protected String group_no="";
	protected int gift_type=0;
	protected int is_used=0;
	protected int status=0;
	/*******************************/
    public int getID(){
        return ID;
    }
    public void setID(int iID){
        this.ID=iID;
    }
	/*******************************/
    public int getStatust(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
	/*******************************/
    public int getIs_used(){
        return is_used;
    }
    public void setIs_used(int is_used){
        this.is_used=is_used;
    }    
	/*******************************/
    public int getGift_type(){
        return gift_type;
    }
    public void setGift_type(int gift_type){
        this.gift_type=gift_type;
    }  
	/*******************************/
    public String getGift_number(){
        return gift_number;
    }
    public void setGift_number(String gift_number){
        this.gift_number=gift_number;
    }
	/*******************************/
    public String getGroup_no(){
        return group_no;
    }
    public void setGroup_no(String group_no){
        this.group_no=group_no;
    }    
}

/*
 * Created on 2005-2-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import com.magic.crm.common.WebForm;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGIFTForm extends WebForm{
	    public int item_ID=0;
	    public int ID=0;
	    public int operator_id=0;
	    public int pricelist_line_ID=0;
	    public String EFFECT_DATE="";
	    public String EXPIRED_DATE="";
	    public int IS_VALID=0;
	    public String name="";
	    public int keep_days=0;
	    public double price=0;
	    public String end_date="";
	    public String create_date="";	    
	    
	   
	    /*
	     * get set 
	     */

	    public int getID(){
	    	return this.ID ;
	    }
	    public void setID(int iID){
	    	this.ID =iID;
	    }
	    public int getItem_ID(){
	    	return this.item_ID ;
	    }
	    public void setItem_ID(int iitem_ID){
	    	this.item_ID =iitem_ID;
	    }

	    public int getPricelist_line_ID(){
	    	return this.pricelist_line_ID ;
	    }
	    public void setPricelist_line_ID(int ipricelist_line_ID){
	    	this.pricelist_line_ID =ipricelist_line_ID;
	    }
	    public String getEFFECT_DATE(){
	    	return this.EFFECT_DATE ;
	    }
	    public void setEFFECT_DATE(String iEFFECT_DATE){
	    	this.EFFECT_DATE =iEFFECT_DATE;
	    }
	    public String getEXPIRED_DATE(){
	    	return this.EXPIRED_DATE ;
	    }
	    public void setEXPIRED_DATE(String iEXPIRED_DATE){
	    	this.EXPIRED_DATE =iEXPIRED_DATE;
	    }
	    public int getIS_VALID(){
	    	return this.IS_VALID ;
	    }
	    public void setIS_VALID(int iIS_VALID){
	    	this.IS_VALID =iIS_VALID;
	    }
	     public int getOperator_id(){
	     	return this.operator_id ;
	     }
	     public void setOperator_id(int ioperator_id ){
	     	this.operator_id =ioperator_id;
	     }	   
	     public String getNAME(){
	    	return this.name ;
	    }
	    public void setNAME(String iName){
	    	this.name =iName;
	    } 	  
	    public String getEnd_date(){
	    	return this.end_date ;
	    }
	    public void setEnd_date(String iend_date){
	    	this.end_date =iend_date;
	    }    
	    public String getCreate_date(){
	    	return this.create_date ;
	    }
	    public void setCreate_date(String icreate_date){
	    	this.create_date =icreate_date;
	    } 
	    public int getKeep_days(){
	     	return this.keep_days ;
	     }
	     public void setKeep_days(int ikeep_days ){
	     	this.keep_days =ikeep_days;
	     }	 
	     public double getPrice(){
	      	return this.price ;
	      }
	      public void setPrice(double iprice){
	      	this.price =iprice;
	      }		    
	    public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
	        item_ID=0;
	        ID=0;
	        operator_id=0;
	        pricelist_line_ID=0;
		    EFFECT_DATE="";
		    EXPIRED_DATE="";
		    IS_VALID=0;
		    name="";

	    }
}

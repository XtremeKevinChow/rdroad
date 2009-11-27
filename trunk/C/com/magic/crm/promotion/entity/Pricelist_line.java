/*
 * Created on 2006-11-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.entity;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Pricelist_line {
	  protected int id=0;
	  protected int pricelist_id=0;
	  protected int sku_id=0;
	  protected int page=0;
	  
	  protected int sell_type=0;
	  protected int catalog_editon=0;
	  protected int operator_id=0;
	  protected int status=0;
	  protected String item_code = "";
	  protected String item_name = "";
	  protected String modify_date="";
	  protected String catalog_editon_name="";
	  protected String color_code="";
	  protected String color_name="";
	  protected String size_code = "";
	  protected String size_name = "";
	  
	  protected double sale_price=0;
	  protected double vip_price=0;
	  
	  
		public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getColor_code() {
		return color_code;
	}
	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}
	public String getColor_name() {
		return color_name;
	}
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}
	public String getSize_code() {
		return size_code;
	}
	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}
	public String getSize_name() {
		return size_name;
	}
	public void setSize_name(String size_name) {
		this.size_name = size_name;
	}
	public double getSale_price() {
		return sale_price;
	}
	public void setSale_price(double sale_price) {
		this.sale_price = sale_price;
	}
	public double getVip_price() {
		return vip_price;
	}
	public void setVip_price(double vip_price) {
		this.vip_price = vip_price;
	}
		/*******************************/
	    public int getID(){
	        return id;
	    }
	    public void setID(int iID){
	        this.id=iID;
	    }
		/*******************************/
	    public int getPricelist_id(){
	        return pricelist_id;
	    }
	    public void setPricelist_id(int pricelist_id){
	        this.pricelist_id=pricelist_id;
	    }
		/*******************************/
	    
		/*******************************/
	    public int getPage(){
	        return page;
	    }
	    public void setPage(int page){
	        this.page=page;
	    }
		/*******************************/
	    
		/*******************************/
	    public int getSell_type(){
	        return sell_type;
	    }
	    public void setSell_type(int sell_type){
	        this.sell_type=sell_type;
	    }
		/*******************************/
	    public int getCatalog_editon(){
	        return catalog_editon;
	    }
	    public void setCatalog_editon(int catalog_editon){
	        this.catalog_editon=catalog_editon;
	    }
		/*******************************/
	    public int getOperator_id(){
	        return operator_id;
	    }
	    public void setOperator_id(int operator_id){
	        this.operator_id=operator_id;
	    }
		/*******************************/
	    public int getStatus(){
	        return status;
	    }
	    public void setStatus(int status){
	        this.status=status;
	    }
		
		/*******************************/
	    
	  
		
	  
		/*******************************/
	    public String getModify_date(){
	        return modify_date;
	    }
	    public void setModify_date(String modify_date){
	        this.modify_date=modify_date;
	    }	
		  
		/*******************************/
	    public String getCatalog_editon_name(){
	        return catalog_editon_name;
	    }
	    public void setCatalog_editon_name(String catalog_editon_name){
	        this.catalog_editon_name=catalog_editon_name;
	    }		    
}

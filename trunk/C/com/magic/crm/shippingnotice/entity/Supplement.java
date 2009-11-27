/*
 * Created on 2007-3-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.shippingnotice.entity;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Supplement {
	public int id=0;
	public int sd_id=0;

	public int item_id=0;
	public int qty=0;
	public double price=0;
	public String remark="";
	public int dtl_type=0;
	public int sell_type=0;
	public long ship_id=0;
	public int orgin_dtl_id=0;
	public int member_id=0;
	
	public String require_date="";
	public int redelivery_type=0;
	public int is_return_orgin=0;
	public int is_postage=0;
	public int writer=0;
	public String write_date="";
	public int operator=0;
	public String op_time="";
	public int status=0;
	
	
	


	/*******************************/
    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id=id;
    }
    public int getSd_id(){
        return sd_id;
    }
    public void setSd_id(int sd_id){
        this.sd_id=sd_id;
    }    
    public int getItem_id(){
        return item_id;
    }
    public void setItem_id(int item_id){
        this.item_id=item_id;
    }      
    public int getQty(){
        return qty;
    }
    public void setQty(int qty){
        this.qty=qty;
    }      
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price=price;
    }     
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    } 
    public int getSell_type(){
        return sell_type;
    }
    public void setSell_type(int sell_type){
        this.sell_type=sell_type;
    }       
    public int getDtl_type(){
        return dtl_type;
    }
    public void setDtl_type(int dtl_type){
        this.dtl_type=dtl_type;
    }     
    public int getOrgin_dtl_id(){
        return orgin_dtl_id;
    }
    public void setOrgin_dtl_id(int orgin_dtl_id){
        this.orgin_dtl_id=orgin_dtl_id;
    } 
    public int getMember_id(){
        return member_id;
    }
    public void setMember_id(int member_id){
        this.member_id=member_id;
    } 
    public long getShip_id(){
        return ship_id;
    }
    public void setShip_id(long ship_id){
        this.ship_id=ship_id;
    } 
    public String getRequire_date(){
        return require_date;
    }
    public void setRequire_date(String require_date){
        this.require_date=require_date;
    } 
    public int getRedelivery_type(){
        return redelivery_type;
    }
    public void setRedelivery_type(int redelivery_type){
        this.redelivery_type=redelivery_type;
    } 
    public int getIs_return_orgin(){
        return is_return_orgin;
    }
    public void setIs_return_orgin(int is_return_orgin){
        this.is_return_orgin=is_return_orgin;
    } 
    public int getWriter(){
        return writer;
    }
    public void setWriter(int writer){
        this.writer=writer;
    } 
    public String getWrite_date(){
        return write_date;
    }
    public void setWrite_date(String write_date){
        this.write_date=write_date;
    }
    public int getOperator(){
        return operator;
    }
    public void setOperator(int operator){
        this.operator=operator;
    }
    public String getOp_time(){
        return op_time;
    }
    public void setOp_time(String op_time){
        this.op_time=op_time;
    }    
    public int getIs_postage(){
        return is_postage;
    }
    public void setIs_postage(int is_postage){
        this.is_postage=is_postage;
    }
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
}

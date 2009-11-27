/*
 * Created on 2006-12-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.entity;

import java.sql.Date;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mbr_gift_money_by_order {
/*
 * 
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
	protected int ID=0;

	protected String gift_number="";
	protected int level_id=0;
	protected int status=0;
	protected double order_require=0;
	protected double dis_amt=0;
	protected String is_discount = "N";
	protected int dis_type = 1;
	protected Date begin_date=null;
	protected Date end_date=null;
	protected int item_group_id=0;
	
	/*******************************/
    public int getID(){
        return ID;
    }
    public void setID(int iID){
        this.ID=iID;
    }
	/*******************************/
    public int getLevel_id(){
        return level_id;
    }
    public void setLevel_id(int level_id){
        this.level_id=level_id;
    }
	/*******************************/
    public String getGift_number(){
        return gift_number;
    }
    public void setGift_number(String gift_number){
        this.gift_number=gift_number;
    }
	/*******************************/
    public int getStatust(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
	/*******************************/
    public double getOrder_require(){
        return order_require;
    }
    public void setOrder_require(double order_require){
        this.order_require=order_require;
    }
	/*******************************/
    public double getDis_amt(){
        return dis_amt;
    }
    public void setDis_amt(double dis_amt){
        this.dis_amt=dis_amt;
    }
	public int getDis_type() {
		return dis_type;
	}
	public void setDis_type(int dis_type) {
		this.dis_type = dis_type;
	}
	public String getIs_discount() {
		return is_discount;
	}
	public void setIs_discount(String is_discount) {
		this.is_discount = is_discount;
	}
	public Date getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	public int getItem_group_id() {
		return item_group_id;
	}
	public void setItem_group_id(int item_group_id) {
		this.item_group_id = item_group_id;
	}
}

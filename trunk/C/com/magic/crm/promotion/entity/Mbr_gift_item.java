/*
 * Created on 2006-12-1
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
public class Mbr_gift_item {
	protected int ID=0;
	protected int item_group_id=0;
	protected int item_id=0;
	protected int is_must=0;
	/*******************************/
    public int getID(){
        return ID;
    }
    public void setID(int iID){
        this.ID=iID;
    }
	/*******************************/
    public int getItem_group_id(){
        return item_group_id;
    }
    public void setItem_group_id(int item_group_id){
        this.item_group_id=item_group_id;
    }
	/*******************************/
    public int getItem_id(){
        return item_id;
    }
    public void setItem_id(int item_id){
        this.item_id=item_id;
    }
	/*******************************/
    public int getIs_must(){
        return is_must;
    }
    public void setIs_must(int is_must){
        this.is_must=is_must;
    }
}

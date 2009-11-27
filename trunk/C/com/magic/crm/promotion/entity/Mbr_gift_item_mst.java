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
public class Mbr_gift_item_mst {
	protected int min_item_count=0;

	protected int item_group_id=0;
	protected int status=0;
	protected String group_desc;
	protected int itemgroup_type=1;//默认为单品
	/*******************************/
    public int getMin_item_count(){
        return min_item_count;
    }
    public void setMin_item_count(int min_item_count){
        this.min_item_count=min_item_count;
    }
	/*******************************/
    public int getItem_group_id(){
        return item_group_id;
    }
    public void setItem_group_id(int item_group_id){
        this.item_group_id=item_group_id;
    }
	/*******************************/
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
	/*******************************/
	public String getGroup_desc() {
		return group_desc;
	}
	public void setGroup_desc(String group_desc) {
		this.group_desc = group_desc;
	}
	public int getItemgroup_type() {
		return itemgroup_type;
	}
	public void setItemgroup_type(int itemgroup_type) {
		this.itemgroup_type = itemgroup_type;
	}
    
}

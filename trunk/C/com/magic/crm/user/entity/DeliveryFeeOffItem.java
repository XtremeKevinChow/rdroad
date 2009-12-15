package com.magic.crm.user.entity;

public class DeliveryFeeOffItem {
	private int off_id;
	private int item_type;
	private String item_code;
	private int status;
	
	public int getOff_id(){
		return this.off_id;
	}
	public void setOff_id(int off_id){
		this.off_id=off_id;
	}
	/*
	 * 1: 单品   2: 套装
	 */
	public int getItem_type(){
		return this.item_type;
	}
	public void setItem_type(int item_type){
		this.item_type=item_type;
	}
	public String getItemtype_name(){
		if(this.item_type==1) return "单品";
		if(this.item_type==2) return "套装";
		return "";
	}
	public String getItem_code(){
		return this.item_code;
	}
	public void setItem_code(String item_code){
		this.item_code=item_code;
	}
	/*
	 * 1: 有效 -1: 失效
	 */
	public int getStatus(){
		return this.status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	public String getStatus_name(){
		if(this.status==1) return "有效";
		if(this.status==-1) return "无效";
		return "";
	}
}

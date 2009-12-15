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
	 * 1: ��Ʒ   2: ��װ
	 */
	public int getItem_type(){
		return this.item_type;
	}
	public void setItem_type(int item_type){
		this.item_type=item_type;
	}
	public String getItemtype_name(){
		if(this.item_type==1) return "��Ʒ";
		if(this.item_type==2) return "��װ";
		return "";
	}
	public String getItem_code(){
		return this.item_code;
	}
	public void setItem_code(String item_code){
		this.item_code=item_code;
	}
	/*
	 * 1: ��Ч -1: ʧЧ
	 */
	public int getStatus(){
		return this.status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	public String getStatus_name(){
		if(this.status==1) return "��Ч";
		if(this.status==-1) return "��Ч";
		return "";
	}
}

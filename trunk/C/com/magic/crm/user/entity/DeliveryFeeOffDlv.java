package com.magic.crm.user.entity;

public class DeliveryFeeOffDlv {
	private int off_id;
	private int delivery_id;
	private String delivery_name;
	private int off_type;
	private double off_fee;
	private int status;
	
	public DeliveryFeeOffDlv(){
		this.off_id=0;
		this.delivery_id=0;
		this.delivery_name="";
		this.off_type=1;
		this.off_fee=0;
		this.status=1;
	}
	
	public int getOff_id(){
		return this.off_id;
	}
	public void setOff_id(int off_id){
		this.off_id=off_id;
	}
	public int getDelivery_id(){
		return this.delivery_id;
	}
	public void setDelivery_id(int delivery_id){
		this.delivery_id=delivery_id;
	}
	public String getDelivery_name(){
		return this.delivery_name;
	}
	public void setDelivery_name(String delivery_name){
		this.delivery_name=delivery_name;
	}
	/*
	 * 1: 全免
     * 2: 免指定金额
	 */
	public int getOff_type(){
		return this.off_type;
	}
	public void setOff_type(int off_type){
		this.off_type=off_type;
	}
	public String getOfftype_name(){
		if(this.off_type==1) return "全免";
		if(this.off_type==2) return "部分免";
		return "";
	}
	/*
	 * Off_Type为2时有效
	 */
	public double getOff_fee(){
		return this.off_fee;
	}
	public void setOff_fee(double off_fee){
		this.off_fee=off_fee;
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

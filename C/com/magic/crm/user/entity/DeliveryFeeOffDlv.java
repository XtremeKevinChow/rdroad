package com.magic.crm.user.entity;

public class DeliveryFeeOffDlv {
	private int off_id;
	private int delivery_id;
	private int off_type;
	private double off_fee;
	private int status;
	
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
	/*
	 * 1: ȫ��
     * 2: ��ָ�����
	 */
	public int getOff_type(){
		return this.off_type;
	}
	public void setOff_type(int off_type){
		this.off_type=off_type;
	}
	public String getOfftype_name(){
		if(this.off_type==1) return "ȫ��";
		if(this.off_type==2) return "������";
		return "";
	}
	/*
	 * Off_TypeΪ2ʱ��Ч
	 */
	public double getOff_fee(){
		return this.off_fee;
	}
	public void setOff_fee(double off_fee){
		this.off_fee=off_fee;
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

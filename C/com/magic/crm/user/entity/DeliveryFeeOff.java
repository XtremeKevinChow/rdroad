package com.magic.crm.user.entity;

import java.util.Date;

public class DeliveryFeeOff {
	private int id;
	private String name;
	private Date begin_date;
	private Date end_date;
	private int status;
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id=id;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	public Date getBegin_date(){
		return this.begin_date;
	}
	public void setBegin_date(Date begin_date){
		this.begin_date=begin_date;
	}
	public Date getEnd_date(){
		return this.end_date;
	}
	public void setEnd_date(Date end_date){
		this.end_date=end_date;
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

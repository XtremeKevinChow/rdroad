package com.magic.crm.order.entity;

import java.io.Serializable;

public class Money4Qty implements Serializable {

	public double getMoney() {
		return money;
	}
	public int getQty() {
		return qty;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
	private double money;
	private int qty ;
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}

package com.magic.crm.promotion.entity;

import com.magic.crm.product.entity.Product;

import java.sql.Date;


public class ExpExchangeStepDtl {
	private long id;
	ExpExchangeStepMst stepMst = new ExpExchangeStepMst();
	// T-��ȯ��G-��Ʒ��P-���
	private String stepType;
	private String no;
	private Date beginDate;
	private Date endDate;
	//�������
	private double orderRequire;
	//�Ӷ���Ԫ
	private double addMoney;
	private String status = "Y";
	//�������typeΪP-���ʱ�������˶���
	ExpExchangePackageMst packMst = new ExpExchangePackageMst();
	
	//��Ʒ����typeΪG-��Ʒʱ�����ζ���
	Product gift = new Product();
	
	//�Ƿ���Ч
	private boolean enabled = false;
		
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public ExpExchangePackageMst getPackMst() {
		return packMst;
	}
	public void setPackMst(ExpExchangePackageMst packMst) {
		this.packMst = packMst;
	}
	
	public Product getGift() {
		return gift;
	}
	public void setGift(Product gift) {
		this.gift = gift;
	}
	public ExpExchangeStepMst getStepMst() {
		return stepMst;
	}
	public void setStepMst(ExpExchangeStepMst stepMst) {
		this.stepMst = stepMst;
	}
	public double getAddMoney() {
		return addMoney;
	}
	public void setAddMoney(double addMoney) {
		this.addMoney = addMoney;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public double getOrderRequire() {
		return orderRequire;
	}
	public void setOrderRequire(double orderRequire) {
		this.orderRequire = orderRequire;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStepType() {
		return stepType;
	}
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}
	
}

package com.magic.crm.promotion.entity;

import com.magic.crm.product.entity.Product;

public class ExpExchangePackageDtl {
	private long id;
	private ExpExchangePackageMst mst = new ExpExchangePackageMst();;
	//T-��ȯ;G-��Ʒ
	private String packageType;
	private String no;
	private int quantity = 1;
	//Y-��Ч��N-��Ч
	private String status;
	
	//��Ʒ����typeΪG-��Ʒʱ�����ζ���
	Product gift = new Product();
	public Product getGift() {
		return gift;
	}
	public void setGift(Product gift) {
		this.gift = gift;
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
	
	public ExpExchangePackageMst getMst() {
		return mst;
	}
	public void setMst(ExpExchangePackageMst mst) {
		this.mst = mst;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	
}

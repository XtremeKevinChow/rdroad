package com.magic.crm.product.entity;

public class ProductSKU extends Product2 {

	int sku_id;
	String color_code;
	String color_name;
	String color_depth;
	
	String size_code;
	String size_name;
	
	int enable_os;
	int os_qty;
	String ovs_date;
	String status;
	String pub_date;
	
	
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public String getColor_code() {
		return color_code;
	}
	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}
	public String getSize_code() {
		return size_code;
	}
	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}
	
	public String getOvs_date() {
		return ovs_date;
	}
	public void setOvs_date(String ovs_date) {
		this.ovs_date = ovs_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPub_date() {
		return pub_date;
	}
	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}
	public String getColor_depth() {
		return color_depth;
	}
	public void setColor_depth(String color_depth) {
		this.color_depth = color_depth;
	}
	public String getColor_name() {
		return color_name;
	}
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}
	public String getSize_name() {
		return size_name;
	}
	public void setSize_name(String size_name) {
		this.size_name = size_name;
	}
	
	
	
}

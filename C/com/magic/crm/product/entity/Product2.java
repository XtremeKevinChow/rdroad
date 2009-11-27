package com.magic.crm.product.entity;

import java.io.Serializable;

public class Product2 implements Serializable {

	int item_id;
	String item_code;
	String item_name;
	String item_name_en;
	String item_unit;
	
	int item_type;
	int item_category;
	int max_count;
	
	String  item_fabric;  
	String  item_lining;  
	String  item_title;
	String item_desc;
	String barcode;
	
	
	double item_cost;
	double standard_price;
	double sale_price;
	double vip_price;
	
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_name_en() {
		return item_name_en;
	}
	public void setItem_name_en(String item_name_en) {
		this.item_name_en = item_name_en;
	}
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public int getItem_type() {
		return item_type;
	}
	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}
	public int getItem_category() {
		return item_category;
	}
	public void setItem_category(int item_category) {
		this.item_category = item_category;
	}
	public String getItem_fabric() {
		return item_fabric;
	}
	public void setItem_fabric(String item_fabric) {
		this.item_fabric = item_fabric;
	}
	public String getItem_lining() {
		return item_lining;
	}
	public void setItem_lining(String item_lining) {
		this.item_lining = item_lining;
	}
	public String getItem_title() {
		return item_title;
	}
	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}
	public String getItem_desc() {
		return item_desc;
	}
	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public double getItem_cost() {
		return item_cost;
	}
	public void setItem_cost(double item_cost) {
		this.item_cost = item_cost;
	}
	public double getStandard_price() {
		return standard_price;
	}
	public void setStandard_price(double standard_price) {
		this.standard_price = standard_price;
	}
	public double getSale_price() {
		return sale_price;
	}
	public void setSale_price(double sale_price) {
		this.sale_price = sale_price;
	}
	public double getVip_price() {
		return vip_price;
	}
	public void setVip_price(double vip_price) {
		this.vip_price = vip_price;
	}
	public int getMax_count() {
		return max_count;
	}
	public void setMax_count(int max_count) {
		this.max_count = max_count;
	}
}

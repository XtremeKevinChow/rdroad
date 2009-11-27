package com.magic.crm.product.form;

import java.util.ArrayList;

public class Product2Form extends org.apache.struts.action.ActionForm implements
		java.io.Serializable {

	String op_type;
	
	ArrayList items = new ArrayList();
	ArrayList cates = new ArrayList();
	//ArrayList items = new ArrayList();
	int item_id;
	String item_code;
	String item_name;
	String item_name_en;
	
	String saleflag = "Y";
	
	public String getSaleflag() {
		return saleflag;
	}
	public void setSaleflag(String saleflag) {
		this.saleflag = saleflag;
	}
	int item_unit;
	String unit_name;
	
	int item_type;
	String item_type_name;
	int item_category;
	String item_category_name;
	int max_count;
	
	String  item_fabric;  
	String  item_lining;  
	String  item_title;
	String  item_bottom;
	
	String  item_side;  
	String  item_other;
	String item_origin;
	String item_desc;
	String barcode;
	
	
	double item_cost;
	double standard_price;
	double sale_price;
	double vip_price;
	double web_price;
	
	int enable_os;
	int os_qty;
	
	String qry_item_code;
	String qry_item_name;
	String qry_item_type;
	String qry_item_category;
	
	/**
	 * @return the qry_item_code
	 */
	public String getQry_item_code() {
		return qry_item_code;
	}
	/**
	 * @param qry_item_code the qry_item_code to set
	 */
	public void setQry_item_code(String qry_item_code) {
		this.qry_item_code = qry_item_code;
	}
	/**
	 * @return the qry_item_name
	 */
	public String getQry_item_name() {
		return qry_item_name;
	}
	/**
	 * @param qry_item_name the qry_item_name to set
	 */
	public void setQry_item_name(String qry_item_name) {
		this.qry_item_name = qry_item_name;
	}
	/**
	 * @return the qry_item_type
	 */
	public String getQry_item_type() {
		return qry_item_type;
	}
	/**
	 * @param qry_item_type the qry_item_type to set
	 */
	public void setQry_item_type(String qry_item_type) {
		this.qry_item_type = qry_item_type;
	}
	/**
	 * @return the qry_item_category
	 */
	public String getQry_item_category() {
		return qry_item_category;
	}
	/**
	 * @param qry_item_category the qry_item_category to set
	 */
	public void setQry_item_category(String qry_item_category) {
		this.qry_item_category = qry_item_category;
	}
	public String getOp_type() {
		return op_type;
	}
	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}
	
	public String getItem_bottom() {
		return item_bottom;
	}
	public void setItem_bottom(String item_bottom) {
		this.item_bottom = item_bottom;
	}
	public String getItem_side() {
		return item_side;
	}
	public void setItem_side(String item_side) {
		this.item_side = item_side;
	}
	public String getItem_other() {
		return item_other;
	}
	public void setItem_other(String item_other) {
		this.item_other = item_other;
	}
	public String getItem_type_name() {
		return item_type_name;
	}
	public void setItem_type_name(String item_type_name) {
		this.item_type_name = item_type_name;
	}
	public String getItem_category_name() {
		return item_category_name;
	}
	public void setItem_category_name(String item_category_name) {
		this.item_category_name = item_category_name;
	}
	
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
	
	public int getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(int item_unit) {
		this.item_unit = item_unit;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
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
	public ArrayList getItems() {
		return items;
	}
	public void setItems(ArrayList items) {
		this.items = items;
	}
	public String getItem_origin() {
		return item_origin;
	}
	public void setItem_origin(String item_origin) {
		this.item_origin = item_origin;
	}
	public int getEnable_os() {
		return enable_os;
	}
	public void setEnable_os(int enable_os) {
		this.enable_os = enable_os;
	}
	public int getOs_qty() {
		return os_qty;
	}
	public void setOs_qty(int os_qty) {
		this.os_qty = os_qty;
	}
	public ArrayList getCates() {
		return cates;
	}
	public void setCates(ArrayList cates) {
		this.cates = cates;
	}
	public double getWeb_price() {
		return web_price;
	}
	public void setWeb_price(double web_price) {
		this.web_price = web_price;
	}
}

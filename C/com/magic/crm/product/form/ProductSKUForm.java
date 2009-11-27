package com.magic.crm.product.form;

import java.util.ArrayList;

/**
 * @author Administrator
 *
 */
public class ProductSKUForm extends Product2Form {
	
	/**
	 * @return the color_name
	 */
	public String getColor_name() {
		return color_name;
	}
	/**
	 * @param color_name the color_name to set
	 */
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}
	/**
	 * @return the size_name
	 */
	public String getSize_name() {
		return size_name;
	}
	/**
	 * @param size_name the size_name to set
	 */
	public void setSize_name(String size_name) {
		this.size_name = size_name;
	}
	
	int sku_id;
	String color_code;
	String color_name;
	String size_code;
	String size_name;
	
	int availQty = 0;
	
	
	public int getAvailQty() {
		return availQty;
	}
	public void setAvailQty(int availQty) {
		this.availQty = availQty;
	}

	ArrayList colors = new ArrayList();
	ArrayList sizes = new ArrayList();
	
	/*int enable_os;
	int os_qty;*/
	
	public ArrayList getColors() {
		return colors;
	}
	public void setColors(ArrayList colors) {
		this.colors = colors;
	}
	public ArrayList getSizes() {
		return sizes;
	}
	public void setSizes(ArrayList sizes) {
		this.sizes = sizes;
	}
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
	/*public int getEnable_os() {
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
	}*/
	

}

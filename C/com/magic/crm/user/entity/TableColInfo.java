/*
 * Created on 2007-1-30 by zhuxiang
 * TableColInfo.java
 * TODO 
 */
package com.magic.crm.user.entity;

import java.io.Serializable;

/**
 * @author zhuxiang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableColInfo implements Serializable {
	
	// 列页面显示名称
	private String col_name = "";
	// 列名
	private String col_code = "";
	// 列显示顺序
	private int col_order = 0;
	// 列类型
	private String data_type = "";
	// 列总长
	private int data_length = 0;
	// 列整数部分长
	private int data_precision = 0;
	// 列小数部分长
	private int data_scale = 0;
	// 列缺省值
	private Object data_default = null;
	// 列是否可为空
	private boolean is_nullable = true;
	// 列是否主键
	private boolean is_pk = false;
	
	/**
	 * @return Returns the is_pk.
	 */
	public boolean isIs_pk() {
		return is_pk;
	}
	/**
	 * @param is_pk The is_pk to set.
	 */
	public void setIs_pk(boolean is_pk) {
		this.is_pk = is_pk;
	}
	/**
	 * @return Returns the col_code.
	 */
	public String getCol_code() {
		return col_code;
	}
	/**
	 * @param col_code The col_code to set.
	 */
	public void setCol_code(String col_code) {
		this.col_code = col_code;
	}
	/**
	 * @return Returns the col_name.
	 */
	public String getCol_name() {
		return col_name;
	}
	/**
	 * @param col_name The col_name to set.
	 */
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
	/**
	 * @return Returns the col_order.
	 */
	public int getCol_order() {
		return col_order;
	}
	/**
	 * @param col_order The col_order to set.
	 */
	public void setCol_order(int col_order) {
		this.col_order = col_order;
	}
	/**
	 * @return Returns the data_default.
	 */
	public Object getData_default() {
		return data_default;
	}
	/**
	 * @param data_default The data_default to set.
	 */
	public void setData_default(Object data_default) {
		this.data_default = data_default;
	}
	/**
	 * @return Returns the data_length.
	 */
	public int getData_length() {
		return data_length;
	}
	/**
	 * @param data_length The data_length to set.
	 */
	public void setData_length(int data_length) {
		this.data_length = data_length;
	}
	/**
	 * @return Returns the data_precision.
	 */
	public int getData_precision() {
		return data_precision;
	}
	/**
	 * @param data_precision The data_precision to set.
	 */
	public void setData_precision(int data_precision) {
		this.data_precision = data_precision;
	}
	/**
	 * @return Returns the data_scale.
	 */
	public int getData_scale() {
		return data_scale;
	}
	/**
	 * @param data_scale The data_scale to set.
	 */
	public void setData_scale(int data_scale) {
		this.data_scale = data_scale;
	}
	/**
	 * @return Returns the data_type.
	 */
	public String getData_type() {
		return data_type;
	}
	/**
	 * @param data_type The data_type to set.
	 */
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	/**
	 * @return Returns the is_nullable.
	 */
	public boolean getIs_nullable() {
		return is_nullable;
	}
	/**
	 * @param is_nullable The is_nullable to set.
	 */
	public void setIs_nullable(boolean is_nullable) {
		this.is_nullable = is_nullable;
	}
}

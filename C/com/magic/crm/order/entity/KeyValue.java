/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

import java.io.Serializable;


/**
 * @author Water
 * 
 * 记录送货方式、付款方式
 */
public class KeyValue implements Serializable {
	/* 编号 */
	private int id = 0;
	/* 名称 */
	private String name = null;
	/* 是否选择 */
	private boolean isDefault = false;
	
	/**
	 * @return Returns the isDefault.
	 */
	public boolean isDefault() {
		return isDefault;
	}
	/**
	 * @param isDefault The isDefault to set.
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
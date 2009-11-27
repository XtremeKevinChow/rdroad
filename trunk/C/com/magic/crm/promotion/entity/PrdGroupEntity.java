/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.entity;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrdGroupEntity implements Serializable {
	private String group_id;
	private String group_name;
	
	
	/**
	 * @return Returns the group_id.
	 */
	public String getGroup_id() {
		return group_id;
	}
	/**
	 * @param group_id The group_id to set.
	 */
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	/**
	 * @return Returns the group_name.
	 */
	public String getGroup_name() {
		return group_name;
	}
	/**
	 * @param group_name The group_name to set.
	 */
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
}

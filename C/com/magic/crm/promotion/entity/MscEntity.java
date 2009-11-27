/*
 * Created on 2005-7-25
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
public class MscEntity implements Serializable {
	private String msc_code;
	private String msc_name;
	/**
	 * @return Returns the msc_code.
	 */
	public String getMsc_code() {
		return msc_code;
	}
	/**
	 * @param msc_code The msc_code to set.
	 */
	public void setMsc_code(String msc_code) {
		this.msc_code = msc_code;
	}
	/**
	 * @return Returns the msc_name.
	 */
	public String getMsc_name() {
		return msc_name;
	}
	/**
	 * @param msc_name The msc_name to set.
	 */
	public void setMsc_name(String msc_name) {
		this.msc_name = msc_name;
	}
}

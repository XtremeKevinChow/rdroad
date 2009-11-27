/*
 * Created on 2006-2-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

/**
 * @author magic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CodeName {
	
	public CodeName(String inputCode, String inputName) {
		this.code = inputCode;
		this.name = inputName;
	}
	public CodeName(String inputCode, String inputName, boolean checked) {
		this(inputCode, inputName);
		this.checked = checked;
	}
	/** 代码 **/
	private String code = null;
	
	/** 名称 **/
	private String name = null;
	
	/** 是否选中 **/
	private boolean checked = false;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
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

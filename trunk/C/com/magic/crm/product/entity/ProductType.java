/*
 * Created on 2005-11-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.product.entity;

import java.io.Serializable;
/**
 * @author user
 * entity
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * table: VW_PRD_ITEM_CATEGORY
 */
public class ProductType implements Serializable {
	
	/** ����ID **/
	private int ID = 0;
	
	/** �ϼ�����ID **/
	private int parentType = 0;
	
	/** ���Ͳ�� **/
	private int categoryLevel = 0;
	
	/** ��˾ID **/
	private int companyID = 0;
	
	/** �Ƿ�Ϊ��С���� **/
	private int isLeaf = 0;
	
	/** �Ƿ�Ϊ��С�������� **/
	private String isLeafName = null;
	
	/** ���ͱ�� **/
	private String catalogCode = null;
	
	/** ��������  **/
	private String description = null;
	
	/** �������� **/
	private String name = null;
	
	/** �ϼ��������� **/
	private String parentTypeName = null;
	
	
	/**
	 * @return Returns the catalogCode.
	 */
	public String getCatalogCode() {
		return catalogCode;
	}
	/**
	 * @param catalogCode The catalogCode to set.
	 */
	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}
	/**
	 * @return Returns the categoryLevel.
	 */
	public int getCategoryLevel() {
		return categoryLevel;
	}
	/**
	 * @param categoryLevel The categoryLevel to set.
	 */
	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	/**
	 * @return Returns the companyID.
	 */
	public int getCompanyID() {
		return companyID;
	}
	/**
	 * @param companyID The companyID to set.
	 */
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the iD.
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(int id) {
		ID = id;
	}
	/**
	 * @return Returns the isLeaf.
	 */
	public int getIsLeaf() {
		return isLeaf;
	}
	/**
	 * @param isLeaf The isLeaf to set.
	 */
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	/**
	 * @return Returns the isLeafName.
	 */
	public String getIsLeafName() {
		return isLeafName;
	}
	/**
	 * @param isLeafName The isLeafName to set.
	 */
	public void setIsLeafName(String isLeafName) {
		this.isLeafName = isLeafName;
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
	/**
	 * @return Returns the parentType.
	 */
	public int getParentType() {
		return parentType;
	}
	/**
	 * @param parentType The parentType to set.
	 */
	public void setParentType(int parentType) {
		this.parentType = parentType;
	}
	/**
	 * @return Returns the parentTypeName.
	 */
	public String getParentTypeName() {
		return parentTypeName;
	}
	/**
	 * @param parentTypeName The parentTypeName to set.
	 */
	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}
	
}

/*
 * Created on 2007-7-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.form;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Recruit_Activity_PriceListForm extends  org.apache.struts.validator.ValidatorForm implements java.io.Serializable{
	protected int id = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	protected int sectionId = 0;
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	protected int itemId = 0;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}	
	protected String itemCode = null;
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	protected int sellType = 0;
	public int getSellType() {
		return sellType;
	}
	public void setSellType(int sellType) {
		this.sellType = sellType;
	}	
	protected double  price = 0;
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}	
	protected String start_date = null;
	public String getStartDate() {
		return start_date;
	}
	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}	
	protected String end_date = null;
	public String getEndDate() {
		return end_date;
	}
	public void setEndDate(String end_date) {
		this.end_date = end_date;
	}	
	protected String createDate = null;
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}		
	protected String lastModiDate = null;
	public String getLastModiDate() {
		return lastModiDate;
	}
	public void setLastModiDate(String lastModiDate) {
		this.lastModiDate = lastModiDate;
	}	
	protected int creatorId = 0;
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}	
	protected int lastModifierId = 0;
	public int getLastModifierId() {
		return lastModifierId;
	}
	public void setLastModifierId(int lastModifierId) {
		this.lastModifierId = lastModifierId;
	}	
	protected int status = 0;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
	protected double  overx = 0;
	public double getOverx() {
		return overx;
	}
	public void setOverx(double overx) {
		this.overx = overx;
	}		
	
}

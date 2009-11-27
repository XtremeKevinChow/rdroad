/*
 * Created on 2007-7-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.entity;

import com.magic.crm.order.entity.Unit;
/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Recruit_Activity_PriceList {
	//库存状态
	protected int stockStatusId;
	protected String stockStatusName;
	protected int isLastSell = 0;
	
	protected int checked = 0;
	protected int disabled = 0;
	//是否临时对象（0-零时；1-正常）
	protected int temp = 1;
	protected String itemName;
	//单位
	protected Unit unit = new Unit();
	
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
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public int getDisabled() {
		return disabled;
	}
	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	protected double  overx = 0;
	public double getOverx() {
		return overx;
	}
	public void setOverx(double overx) {
		this.overx = overx;
	}
	protected String sectionType;
	/**
	 * @return the sectionType
	 */
	public String getSectionType() {
		return sectionType;
	}
	/**
	 * @param sectionType the sectionType to set
	 */
	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}
	//标准价
	protected double standardPrice;
	/**
	 * @return the standardPrice
	 */
	public double getStandardPrice() {
		return standardPrice;
	}
	/**
	 * @param standardPrice the standardPrice to set
	 */
	public void setStandardPrice(double standardPrice) {
		this.standardPrice = standardPrice;
	}
	/**
	 * @return the stockStatusId
	 */
	public int getStockStatusId() {
		return stockStatusId;
	}
	/**
	 * @param stockStatusId the stockStatusId to set
	 */
	public void setStockStatusId(int stockStatusId) {
		this.stockStatusId = stockStatusId;
	}
	/**
	 * @return the stockStatusName
	 */
	public String getStockStatusName() {
		return stockStatusName;
	}
	/**
	 * @param stockStatusName the stockStatusName to set
	 */
	public void setStockStatusName(String stockStatusName) {
		this.stockStatusName = stockStatusName;
	}
	/**
	 * @return the isLastSell
	 */
	public int getIsLastSell() {
		return isLastSell;
	}
	/**
	 * @param isLastSell the isLastSell to set
	 */
	public void setIsLastSell(int isLastSell) {
		this.isLastSell = isLastSell;
	}
	
}

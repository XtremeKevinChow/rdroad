/*
 * Created on 2005-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.exchange;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class OrderLine implements Serializable {
	private long itemId;
	private String itemCode;
	private String itemName;
	private long webId;
	/**
	 * @return Returns the webId.
	 */
	public long getWebId() {
		return webId;
	}
	/**
	 * @param webId The webId to set.
	 */
	public void setWebId(long webId) {
		this.webId = webId;
	}
	/**
	 * @return Returns the itemName.
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName The itemName to set.
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return Returns the itemId.
	 */
	public long getItemId() {
		return itemId;
	}
	
	private double price;
	private double stand_price;
	private int qty;

	private int status;

	private int sellType;

	
	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return Returns the sellType.
	 */
	public int getSellType() {
		return sellType;
	}
	/**
	 * @param sellType The sellType to set.
	 */
	public void setSellType(int sellType) {
		this.sellType = sellType;
	}
	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return Returns the itemCode.
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode The itemCode to set.
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @param itemId The itemId to set.
	 */
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return Returns the qty.
	 */
	public int getQty() {
		return qty;
	}
	/**
	 * @param qty The qty to set.
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
	/**
	 * @return Returns the stand_price.
	 */
	public double getStand_price() {
		return stand_price;
	}
	/**
	 * @param stand_price The stand_price to set.
	 */
	public void setStand_price(double stand_price) {
		this.stand_price = stand_price;
	}
}

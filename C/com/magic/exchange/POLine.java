package com.magic.exchange;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;

/**
 * POLine Bean
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class POLine {
	private final static int ITEM_NUMBER = 0;

	private final static int ITEM_UOM = 1;

	private final static int ITEM_PRICE = 2;

	private String deliveryDate = null;

	private String price = null;

	private String quantity = null;

	private String itemDesc = null;

	private String itemNumber = null;

	private String deliveryAddress = null;

	private String uom = null;

	public POLine() {
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getItemDesc() {
		return this.itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemNumber() {
		return this.itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUom() {
		return this.uom;
	}
}


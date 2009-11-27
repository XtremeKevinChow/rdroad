/*
 * Created on 2005-4-11
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
public class GiftInfo implements Serializable {
	//crm中的礼品表id
	private int crm_id;
	//网站的礼品表id
	private int web_id;
	//会员id
	private int member_id;
	//会员网站id
	private int member_web_id;
	//产品id
	private int item_id;
	//产品code
	private String item_code;
	//产品价格
	private double price;
	//产品使用的积分
	private int exp;
	//产品的数量
	private int quantity;
	//礼品类型
	private int type;
	//礼品状态
	private int status;
	/**
	 * @return Returns the crm_id.
	 */
	public int getCrm_id() {
		return crm_id;
	}
	/**
	 * @param crm_id The crm_id to set.
	 */
	public void setCrm_id(int crm_id) {
		this.crm_id = crm_id;
	}
	/**
	 * @return Returns the exp.
	 */
	public int getExp() {
		return exp;
	}
	/**
	 * @param exp The exp to set.
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}
	/**
	 * @return Returns the item_id.
	 */
	public int getItem_id() {
		return item_id;
	}
	/**
	 * @param item_id The item_id to set.
	 */
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
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
	 * @return Returns the quantity.
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return Returns the web_id.
	 */
	public int getWeb_id() {
		return web_id;
	}
	/**
	 * @param web_id The web_id to set.
	 */
	public void setWeb_id(int web_id) {
		this.web_id = web_id;
	}
	/**
	 * @return Returns the member_id.
	 */
	public int getMember_id() {
		return member_id;
	}
	/**
	 * @param member_id The member_id to set.
	 */
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	/**
	 * @return Returns the member_web_id.
	 */
	public int getMember_web_id() {
		return member_web_id;
	}
	/**
	 * @param member_web_id The member_web_id to set.
	 */
	public void setMember_web_id(int member_web_id) {
		this.member_web_id = member_web_id;
	}
	/**
	 * @return Returns the item_code.
	 */
	public String getItem_code() {
		return item_code;
	}
	/**
	 * @param item_code The item_code to set.
	 */
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
}

/*
 * Created on 2005-7-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.form;

import java.util.ArrayList;
import java.sql.Date;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpGiftForm extends org.apache.struts.action.ActionForm 
implements java.io.Serializable{
	// pk id
	long id;
	// item id
	long item_id;
	// item code
	String item_code;
	// item name
	String item_name;
	// item silver price
	String silver_price;
	// item gold price
	String gold_price;
	// item web price
	String web_price;
	// order require
	String order_require;
	// group id
	int group_id;
	// group name
	String group_name;
	// start date
	Date start_date;
	// end date
	Date end_date;
	// valid flag
	String valid_flag;
	// flag name
	String flag_name;
	// scope
	int scope =3;
	
	// msc select box
	ArrayList msc_codes = new ArrayList();
	String sel_msc;
	
	// group select box
	ArrayList groups = new ArrayList();
	String sel_group;
	
	// 免发送费的购物金额要求
	String free_delivery_require;
	
	/**
	 * @return Returns the free_delivery_require.
	 */
	public String getFree_delivery_require() {
		return free_delivery_require;
	}
	/**
	 * @param free_delivery_require The free_delivery_require to set.
	 */
	public void setFree_delivery_require(String free_delivery_require) {
		this.free_delivery_require = free_delivery_require;
	}
	/**
	 * @return Returns the groups.
	 */
	public ArrayList getGroups() {
		return groups;
	}
	/**
	 * @param groups The groups to set.
	 */
	public void setGroups(ArrayList groups) {
		this.groups = groups;
	}
	/**
	 * @return Returns the sel_group.
	 */
	public String getSel_group() {
		return sel_group;
	}
	/**
	 * @param sel_group The sel_group to set.
	 */
	public void setSel_group(String sel_group) {
		this.sel_group = sel_group;
	}
	/**
	 * @return Returns the sel_msc.
	 */
	public String getSel_msc() {
		return sel_msc;
	}
	/**
	 * @param sel_msc The sel_msc to set.
	 */
	public void setSel_msc(String sel_msc) {
		this.sel_msc = sel_msc;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	java.util.ArrayList sps = new ArrayList();
	/**
	 * @return Returns the end_date.
	 */
	public Date getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date The end_date to set.
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return Returns the flag_name.
	 */
	public String getFlag_name() {
		return flag_name;
	}
	/**
	 * @param flag_name The flag_name to set.
	 */
	public void setFlag_name(String flag_name) {
		this.flag_name = flag_name;
	}
	/**
	 * @return Returns the gold_price.
	 */
	public String getGold_price() {
		return gold_price;
	}
	/**
	 * @param gold_price The gold_price to set.
	 */
	public void setGold_price(String gold_price) {
		this.gold_price = gold_price;
	}
	/**
	 * @return Returns the group_id.
	 */
	public int getGroup_id() {
		return group_id;
	}
	/**
	 * @param group_id The group_id to set.
	 */
	public void setGroup_id(int group_id) {
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
	/**
	 * @return Returns the item_id.
	 */
	public long getItem_id() {
		return item_id;
	}
	/**
	 * @param item_id The item_id to set.
	 */
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	/**
	 * @return Returns the item_name.
	 */
	public String getItem_name() {
		return item_name;
	}
	/**
	 * @param item_name The item_name to set.
	 */
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	/**
	 * @return Returns the order_require.
	 */
	public String getOrder_require() {
		return order_require;
	}
	/**
	 * @param order_require The order_require to set.
	 */
	public void setOrder_require(String order_require) {
		this.order_require = order_require;
	}
	/**
	 * @return Returns the silver_price.
	 */
	public String getSilver_price() {
		return silver_price;
	}
	/**
	 * @param silver_price The silver_price to set.
	 */
	public void setSilver_price(String silver_price) {
		this.silver_price = silver_price;
	}
	/**
	 * @return Returns the sps.
	 */
	public java.util.ArrayList getSps() {
		return sps;
	}
	/**
	 * @param sps The sps to set.
	 */
	public void setSps(java.util.ArrayList sps) {
		this.sps = sps;
	}
	/**
	 * @return Returns the start_date.
	 */
	public Date getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date The start_date to set.
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return Returns the valid_flag.
	 */
	public String getValid_flag() {
		return valid_flag;
	}
	/**
	 * @param valid_flag The valid_flag to set.
	 */
	public void setValid_flag(String valid_flag) {
		this.valid_flag = valid_flag;
	}
	/**
	 * @return Returns the web_price.
	 */
	public String getWeb_price() {
		return web_price;
	}
	/**
	 * @param web_price The web_price to set.
	 */
	public void setWeb_price(String web_price) {
		this.web_price = web_price;
	}
	/**
	 * @return Returns the scope.
	 */
	public int getScope() {
		return scope;
	}
	/**
	 * @param scope The scope to set.
	 */
	public void setScope(int scope) {
		this.scope = scope;
	}
	
	/**
	 * @return Returns the msc_codes.
	 */
	public ArrayList getMsc_codes() {
		return msc_codes;
	}
	
	/**
	 * @param msc_codes The msc_codes to set.
	 */
	public void setMsc_codes(ArrayList msc_codes) {
		this.msc_codes = msc_codes;
	}
	
	public void copy(SpGiftForm src ) {
		id = src.id;
		item_id = src.item_id;
		item_code = src.item_code;
		item_name = src.item_name;
		silver_price = src.silver_price;
		gold_price = src.gold_price;
		web_price = src.web_price;
		order_require = src.order_require;
		group_id = src.group_id;
		group_name = src.group_name;
		start_date = src.start_date;
		end_date = src.end_date;
		valid_flag = src.valid_flag;
		flag_name = src.flag_name;
		scope = src.scope;
	}
}

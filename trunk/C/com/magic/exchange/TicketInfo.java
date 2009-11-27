/*
 * Created on 2005-4-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.exchange;

import java.io.Serializable;

/**
 * 乐透卡
 * @author Administrator
 *
 * TODO 99read 
 */
public class TicketInfo implements Serializable {
	/**
	 * @return Returns the card_lot.
	 */
	public String getCard_lot() {
		return card_lot;
	}
	/**
	 * @param card_lot The card_lot to set.
	 */
	public void setCard_lot(String card_lot) {
		this.card_lot = card_lot;
	}
	/**
	 * @return Returns the card_num.
	 */
	public String getCard_num() {
		return card_num;
	}
	/**
	 * @param card_num The card_num to set.
	 */
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	/**
	 * @return Returns the card_type.
	 */
	public String getCard_type() {
		return card_type;
	}
	/**
	 * @param card_type The card_type to set.
	 */
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	/**
	 * @return Returns the is_used.
	 */
	public String getIs_used() {
		return is_used;
	}
	/**
	 * @param is_used The is_used to set.
	 */
	public void setIs_used(String is_used) {
		this.is_used = is_used;
	}
	/**
	 * @return Returns the month_hit.
	 */
	public String getMonth_hit() {
		return month_hit;
	}
	/**
	 * @param month_hit The month_hit to set.
	 */
	public void setMonth_hit(String month_hit) {
		this.month_hit = month_hit;
	}
	/**
	 * @return Returns the pass.
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * @param pass The pass to set.
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * @return Returns the used_date.
	 */
	public String getUsed_date() {
		return used_date;
	}
	/**
	 * @param used_date The used_date to set.
	 */
	public void setUsed_date(String used_date) {
		this.used_date = used_date;
	}
	/**
	 * @return Returns the year_hit.
	 */
	public String getYear_hit() {
		return year_hit;
	}
	/**
	 * @param year_hit The year_hit to set.
	 */
	public void setYear_hit(String year_hit) {
		this.year_hit = year_hit;
	}
	
	/* 卡号 */
	String card_num;
	/* 密码 */
	String pass;
	/* 卡类型 */
	String card_type;
	/* 是否使用 */
	String is_used;
	/* 月奖 */
	String month_hit;
	/* 年奖 */
	String year_hit;
	/* 批号 */
	String card_lot;
	/* 使用日期 */
	String used_date;

}

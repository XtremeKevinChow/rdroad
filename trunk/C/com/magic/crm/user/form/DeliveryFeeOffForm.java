package com.magic.crm.user.form;

import java.util.ArrayList;
import java.util.Date;

public class DeliveryFeeOffForm extends org.apache.struts.action.ActionForm implements
		java.io.Serializable {
	
	int off_id;
	String off_name;
	Date begin_date;
	Date end_date;
	int status;
	
	/**
	 * @return the off_id
	 */
	public int getOff_id() {
		return off_id;
	}
	/**
	 * @param off_id the off_id to set
	 */
	public void setOff_id(int off_id) {
		this.off_id = off_id;
	}
	/**
	 * @return the off_name
	 */
	public String getOff_name() {
		return off_name;
	}
	/**
	 * @param off_name the off_name to set
	 */
	public void setOff_name(String off_name) {
		this.off_name = off_name;
	}
	/**
	 * @return the begin_date
	 */
	public Date getBegin_date() {
		return begin_date;
	}
	/**
	 * @param begin_date the begin_date to set
	 */
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	/**
	 * @return the end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}

/*
 * Created on 2005-9-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.entity;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Crush_Card_MST implements java.io.Serializable {
	
	protected String card_num = null;
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String icard_num) {
		this.card_num = icard_num;
	}
	
	protected String pass = null;
	public String getPass() {
		return pass;
	}
	public void setPass(String ipass) {
		this.pass = ipass;
	}
	protected String pass_num = null;
	public String getPass_num() {
		return pass_num;
	}
	public void setPass_num(String ipass_num) {
		this.pass_num = ipass_num;
	}
	
	protected String card_type = null;
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String icard_type) {
		this.card_type = icard_type;
	}	
	
	protected String begin_date = null;
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String ibegin_date) {
		this.begin_date = ibegin_date;
	}
	
	protected String status = null;
	public String getStatus() {
		return status;
	}
	public void setStatus(String istatus) {
		this.status = istatus;
	}	
	
	protected String end_date = null;
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String iend_date) {
		this.end_date = iend_date;
	}	
	
	protected String create_date = null;
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String icreate_date) {
		this.create_date = icreate_date;
	}	
	protected int create_person = 0;
	public int getCreate_person() {
		return create_person;
	}
	public void setCreate_person(int icreate_person) {
		this.create_person = icreate_person;
	}
	
	protected int sale_person = 0;
	public int getSale_person() {
		return sale_person;
	}
	public void setSale_person(int isale_person) {
		this.sale_person = isale_person;
	}	
	
	protected String sale_person_name = "";
	public String getSale_person_name() {
		return sale_person_name;
	}
	public void setSale_person_name(String isale_person_name) {
		this.sale_person_name = isale_person_name;
	} 
	
	protected String sale_date = null;
	public String getSale_date() {
		return sale_date;
	}
	public void setSale_date(String isale_date) {
		this.sale_date = isale_date;
	}		
	
	protected int crush_person = 0;
	public int getCrush_person() {
		return crush_person;
	}
	public void setCrush_person(int icrush_person) {
		this.crush_person = icrush_person;
	}	
	
	protected String crush_person_name = "";
	public String getCrush_person_name() {
		return crush_person_name;
	}
	public void setCrush_person_name(String icrush_person_name) {
		this.crush_person_name = icrush_person_name;
	}	
	
	protected String crush_date = null;
	public String getCrush_date() {
		return crush_date;
	}
	public void setCrush_date(String icrush_date) {
		this.crush_date = icrush_date;
	}	
	
	/**  Èœ„ø®√Ê÷µ(add by user 2006-05-11) **/
	private double money = 0d;
	
	
    /**
     * @return Returns the money.
     */
    public double getMoney() {
        return money;
    }
    /**
     * @param money The money to set.
     */
    public void setMoney(double money) {
        this.money = money;
    }
}

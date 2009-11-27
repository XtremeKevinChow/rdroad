/*
 * Created on 2005-8-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

/**
 * @author zhux
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SupplyMstEntity implements Serializable {
	private String supply_no;                    
    private String supply_name;             
    private Date supply_date;               
    private String writer;                  
    private Date write_date;               
    private String remark;                   
    private String status;
    private String correspond_no;
	/**
	 * @return Returns the correspond_no.
	 */
	public String getCorrespond_no() {
		return correspond_no;
	}
	/**
	 * @param correspond_no The correspond_no to set.
	 */
	public void setCorrespond_no(String correspond_no) {
		this.correspond_no = correspond_no;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the supply_date.
	 */
	public Date getSupply_date() {
		return supply_date;
	}
	/**
	 * @param supply_date The supply_date to set.
	 */
	public void setSupply_date(Date supply_date) {
		this.supply_date = supply_date;
	}
	/**
	 * @return Returns the supply_name.
	 */
	public String getSupply_name() {
		return supply_name;
	}
	/**
	 * @param supply_name The supply_name to set.
	 */
	public void setSupply_name(String supply_name) {
		this.supply_name = supply_name;
	}
	/**
	 * @return Returns the supply_no.
	 */
	public String getSupply_no() {
		return supply_no;
	}
	/**
	 * @param supply_no The supply_no to set.
	 */
	public void setSupply_no(String supply_no) {
		this.supply_no = supply_no;
	}
	/**
	 * @return Returns the write_date.
	 */
	public Date getWrite_date() {
		return write_date;
	}
	/**
	 * @param write_date The write_date to set.
	 */
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	/**
	 * @return Returns the writer.
	 */
	public String getWriter() {
		return writer;
	}
	/**
	 * @param writer The writer to set.
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	private ArrayList dtl = new ArrayList();
	/**
	 * @return Returns the dtl.
	 */
	public ArrayList getDtl() {
		return dtl;
	}
	/**
	 * @param dtl The dtl to set.
	 */
	public void setDtl(ArrayList dtl) {
		this.dtl = dtl;
	}
	
}

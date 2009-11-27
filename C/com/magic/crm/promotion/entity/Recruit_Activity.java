/*
 * Created on 2007-7-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.entity;

import java.util.Collection;
import java.util.ArrayList;
/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Recruit_Activity implements java.io.Serializable {
	protected Collection sectionsList = new ArrayList();
	protected String itemName = null;
	
	protected String msc_code = null;
	public String getMsc_Code() {
		return msc_code;
	}
	public void setMsc_Code(String msc_code) {
		this.msc_code = msc_code;
	}
	protected String name = null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	protected int status = 0;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
	protected int scope = 0;
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
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
	protected int promulgatorId = 0;
	public int getPromulgatorId() {
		return promulgatorId;
	}
	public void setPromulgatorId(int promulgatorId) {
		this.promulgatorId = promulgatorId;
	}	
	protected String remarks = null;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	protected String headhtml = null;
	public String getHeadhtml() {
		return headhtml;
	}
	public void setHeadhtml(String headhtml) {
		this.headhtml = headhtml;
	}
	
	public Collection getSectionsList() {
		return sectionsList;
	}
	public void setSectionsList(Collection sectionsList) {
		this.sectionsList = sectionsList;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}	
}

/*
 * Created on 2007-7-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.form;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Recruit_Activity_SectionForm  extends org.apache.struts.validator.ValidatorForm implements java.io.Serializable{
	protected Collection productsList = new ArrayList();
	
	protected int id = 0;
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}	
	protected String msc_code = null;
	public String getMsc_Code() {
		return msc_code;
	}
	public void setMsc_Code(String msc_code) {
		this.msc_code = msc_code;
	}
	protected String type = "";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	protected String name = null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	protected int maxGoods = 0;
	public int getMaxGoods() {
		return maxGoods;
	}
	public void setMaxGoods(int maxGoods) {
		this.maxGoods = maxGoods;
	}	
	protected int minGoods = 0;
	public int getMinGoods() {
		return minGoods;
	}
	public void setMinGoods(int minGoods) {
		this.minGoods = minGoods;
	}
	protected String createDate = null;
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	protected int creatorId = 0;
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public Collection getProductsList() {
		return productsList;
	}

	public void setProductsList(Collection productsList) {
		this.productsList = productsList;
	}
	protected String sectionImg = "";
	public String getSectionImg() {
		return sectionImg;
	}
	public void setSectionImg(String sectionImg) {
		this.sectionImg = sectionImg;
	}	
	
}

/*
 * Created on 2007-7-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.entity;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Recruit_Activity_Section implements java.io.Serializable{
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
	protected String sectionImg = "";
	public String getSectionImg() {
		return sectionImg;
	}
	public void setSectionImg(String sectionImg) {
		this.sectionImg = sectionImg;
	}	
	public Collection getProductsList() {
		return productsList;
	}

	public void setProductsList(Collection productsList) {
		this.productsList = productsList;
	}
	
	/**
	 * 如果本区中有该礼品，选中它
	 * @param selectedId
	 */
	public Recruit_Activity_PriceList getProduct(int selectedId) {
		Iterator it = this.productsList.iterator();
		
		while (it.hasNext()) {
			Recruit_Activity_PriceList product = (Recruit_Activity_PriceList)it.next();
			if (product.getId() == selectedId) {
				return product;
			}
		}
		return null;
	}
	
	
	/**
	 * 将本区设置无效
	 *
	 */
	public void disabledAll() {
		Iterator it = this.productsList.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList product = (Recruit_Activity_PriceList)it.next();
			product.setDisabled(1);
			
		}
	}
	/**
	 * 将本区所有产品设置有效uncheck
	 *
	 */
	public void releaseAll() {
		Iterator it = this.productsList.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList product = (Recruit_Activity_PriceList)it.next();
			product.setDisabled(0);
			product.setChecked(0);
		}
	}
	/**
	 * 将本没有check的设置有效
	 *
	 */
	public void releasedAllUnchecked() {
		Iterator it = this.productsList.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList product = (Recruit_Activity_PriceList)it.next();
			if (product.getChecked() == 0) {//释放没有打勾的
				product.setDisabled(0);
			}
			
			
		}
	}
	//设置零时对象
	public void resetTemp(Recruit_Activity_PriceList produc) {
		produc.setTemp(0);
	}
	
	public void checked(Recruit_Activity_PriceList product) {
		product.setChecked(1);
	}
	
	public void disabled(Recruit_Activity_PriceList product) {
		product.setDisabled(1);
	}
}

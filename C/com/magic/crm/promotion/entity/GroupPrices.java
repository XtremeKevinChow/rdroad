/**
 * Recruit_Activity_Group_Prices.java
 * 2008-5-7
 * 下午05:12:13
 * user
 * Recruit_Activity_Group_Prices
 */
package com.magic.crm.promotion.entity;

import java.sql.Date;
import java.util.Collection;
import java.util.ArrayList;
/**
 * @author user
 * 打套组
 */
public class GroupPrices {
	private int gpId;
	private int sectionId;
	private String sectionName;
	private Recruit_Activity_Section section = new Recruit_Activity_Section();
	private double saleQty;
	private double saleAmt;
	private int isGift;
	private Date beginDate;
	private Date endDate;
	private Date createDate;
	private int creatorId;
	private Date lastModiDate;
	private int lastModifierId;
	//赠品(E区产品)
	private Collection giftSection = new ArrayList(); 
	//产品(D区产品)
	private Collection product = new ArrayList();
	//是否选中
	private boolean selected = false;
	
	/**
	 * 0;1;-1
	 */
	private int status;
	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the creatorId
	 */
	public int getCreatorId() {
		return creatorId;
	}
	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the gpId
	 */
	public int getGpId() {
		return gpId;
	}
	/**
	 * @param gpId the gpId to set
	 */
	public void setGpId(int gpId) {
		this.gpId = gpId;
	}
	/**
	 * @return the isGift
	 */
	public int getIsGift() {
		return isGift;
	}
	/**
	 * @param isGift the isGift to set
	 */
	public void setIsGift(int isGift) {
		this.isGift = isGift;
	}
	/**
	 * @return the lastModiDate
	 */
	public Date getLastModiDate() {
		return lastModiDate;
	}
	/**
	 * @param lastModiDate the lastModiDate to set
	 */
	public void setLastModiDate(Date lastModiDate) {
		this.lastModiDate = lastModiDate;
	}
	/**
	 * @return the lastModifierId
	 */
	public int getLastModifierId() {
		return lastModifierId;
	}
	/**
	 * @param lastModifierId the lastModifierId to set
	 */
	public void setLastModifierId(int lastModifierId) {
		this.lastModifierId = lastModifierId;
	}
	/**
	 * @return the saleAmt
	 */
	public double getSaleAmt() {
		return saleAmt;
	}
	/**
	 * @param saleAmt the saleAmt to set
	 */
	public void setSaleAmt(double saleAmt) {
		this.saleAmt = saleAmt;
	}
	/**
	 * @return the saleQty
	 */
	public double getSaleQty() {
		return saleQty;
	}
	/**
	 * @param saleQty the saleQty to set
	 */
	public void setSaleQty(double saleQty) {
		this.saleQty = saleQty;
	}
	/**
	 * @return the sectionId
	 */
	public int getSectionId() {
		return sectionId;
	}
	/**
	 * @param sectionId the sectionId to set
	 */
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}
	/**
	 * @param sectionName the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	/**
	 * @return the gift
	 */
	
	/**
	 * @return the product
	 */
	public Collection getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Collection product) {
		this.product = product;
	}
	/**
	 * @return the section
	 */
	public Recruit_Activity_Section getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(Recruit_Activity_Section section) {
		this.section = section;
	}
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * @return the giftSection
	 */
	public Collection getGiftSection() {
		return giftSection;
	}
	/**
	 * @param giftSection the giftSection to set
	 */
	public void setGiftSection(Collection giftSection) {
		this.giftSection = giftSection;
	}
	
	
}

/**
 * GroupPricesForm.java
 * 2008-5-7
 * ÏÂÎç07:44:06
 * user
 * GroupPricesForm
 */
package com.magic.crm.promotion.form;

import java.sql.Date;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

/**
 * @author user
 *
 */
public class GroupPricesForm extends ActionForm {
	private int gpId;
	private int sectionId;
	private double saleQty;
	private double saleAmt;
	private int isGift;
	private Date beginDate;
	private Date endDate;
	private Date createDate;
	private int creatorId;
	private Date lastModiDate;
	private int lastModifierId;
	private int status;
	
	private String msc;
	private Collection sectionList = new ArrayList();
	private Collection activityList = new ArrayList();
	
	/**
	 * @return the activityList
	 */
	public Collection getActivityList() {
		return activityList;
	}
	/**
	 * @param activityList the activityList to set
	 */
	public void setActivityList(Collection activityList) {
		this.activityList = activityList;
	}
	/**
	 * @return the sectionList
	 */
	public Collection getSectionList() {
		return sectionList;
	}
	/**
	 * @param sectionList the sectionList to set
	 */
	public void setSectionList(Collection sectionList) {
		this.sectionList = sectionList;
	}
	/**
	 * @return the msc
	 */
	public String getMsc() {
		return msc;
	}
	/**
	 * @param msc the msc to set
	 */
	public void setMsc(String msc) {
		this.msc = msc;
	}
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
	
}

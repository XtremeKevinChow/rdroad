/**
 * DeliveryFeeForm.java
 * 2008-4-9
 * ÏÂÎç05:22:35
 * user
 * DeliveryFeeForm
 */
package com.magic.crm.user.form;

import java.sql.Date;
import java.util.Collection;
import java.util.ArrayList;
import com.magic.crm.user.entity.DeliveryFee;
import com.magic.crm.common.pager.PagerForm;

/**
 * @author user
 *
 */
public class DeliveryFeeForm extends PagerForm {
	
	public static final long serialVersionUID = 100000002;
	
	private int searchId;
	private String searchProvince;
	private String searchCity;
	
	
	
	private String province;
	private String city;
	private int id;
	private int deliveryTypeM;
	private String deliveryTypeName;
	private int levelId;
	

	private String levelName;
	private String postcodeM;
	private double feeM;
	private Date beginDateM;
	private Date endDateM;
	private double requireAmtM;
	private String remarkM;
	
	
	
	private String[] postcode;
	private int[] deliveryType;
	private double[] fees;
	private Date[] beginDate;
	private Date[] endDate;
	private double[] requireAmt;
	private String[] remark;
	
	private Collection detailList = new ArrayList(); 
	private Collection provinceList = new ArrayList();
	private Collection cityList = new ArrayList();
	private Collection deliveryTypes = new ArrayList();
	private Collection mbrLevels = new ArrayList();
	
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	/**
	 * @return the cityList
	 */
	public Collection getCityList() {
		return cityList;
	}
	/**
	 * @param cityList the cityList to set
	 */
	public void setCityList(Collection cityList) {
		this.cityList = cityList;
	}
	/**
	 * @return the provinceList
	 */
	public Collection getProvinceList() {
		return provinceList;
	}
	/**
	 * @param provinceList the provinceList to set
	 */
	public void setProvinceList(Collection provinceList) {
		this.provinceList = provinceList;
	}
	/**
	 * @return the detailList
	 */
	public Collection getDetailList() {
		return detailList;
	}
	/**
	 * @param detailList the detailList to set
	 */
	public void setDetailList(Collection detailList) {
		this.detailList = detailList;
	}
	/**
	 * @return the searchCity
	 */
	public String getSearchCity() {
		return searchCity;
	}
	/**
	 * @param searchCity the searchCity to set
	 */
	public void setSearchCity(String searchCity) {
		this.searchCity = searchCity;
	}
	/**
	 * @return the beginDate
	 */
	public Date[] getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date[] beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the deliveryType
	 */
	public int[] getDeliveryType() {
		return deliveryType;
	}
	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(int[] deliveryType) {
		this.deliveryType = deliveryType;
	}
	/**
	 * @return the endDate
	 */
	public Date[] getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date[] endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the fees
	 */
	public double[] getFees() {
		return fees;
	}
	/**
	 * @param fees the fees to set
	 */
	public void setFees(double[] fees) {
		this.fees = fees;
	}
	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}
	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	/**
	 * @return the postcode
	 */
	public String[] getPostcode() {
		return postcode;
	}
	/**
	 * @param postcode the postcode to set
	 */
	public void setPostcode(String[] postcode) {
		this.postcode = postcode;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the remark
	 */
	public String[] getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String[] remark) {
		this.remark = remark;
	}
	/**
	 * @return the requireAmt
	 */
	public double[] getRequireAmt() {
		return requireAmt;
	}
	/**
	 * @param requireAmt the requireAmt to set
	 */
	public void setRequireAmt(double[] requireAmt) {
		this.requireAmt = requireAmt;
	}
	/**
	 * @return the searchId
	 */
	public int getSearchId() {
		return searchId;
	}
	/**
	 * @param searchId the searchId to set
	 */
	public void setSearchId(int searchId) {
		this.searchId = searchId;
	}
	/**
	 * @return the searchProvince
	 */
	public String getSearchProvince() {
		return searchProvince;
	}
	/**
	 * @param searchProvince the searchProvince to set
	 */
	public void setSearchProvince(String searchProvince) {
		this.searchProvince = searchProvince;
	}
	/**
	 * @return the beginDateM
	 */
	public Date getBeginDateM() {
		return beginDateM;
	}
	/**
	 * @param beginDateM the beginDateM to set
	 */
	public void setBeginDateM(Date beginDateM) {
		this.beginDateM = beginDateM;
	}
	/**
	 * @return the deliveryTypeM
	 */
	public int getDeliveryTypeM() {
		return deliveryTypeM;
	}
	/**
	 * @param deliveryTypeM the deliveryTypeM to set
	 */
	public void setDeliveryTypeM(int deliveryTypeM) {
		this.deliveryTypeM = deliveryTypeM;
	}
	/**
	 * @return the endDateM
	 */
	public Date getEndDateM() {
		return endDateM;
	}
	/**
	 * @param endDateM the endDateM to set
	 */
	public void setEndDateM(Date endDateM) {
		this.endDateM = endDateM;
	}
	/**
	 * @return the feeM
	 */
	public double getFeeM() {
		return feeM;
	}
	/**
	 * @param feeM the feeM to set
	 */
	public void setFeeM(double feeM) {
		this.feeM = feeM;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the postcodeM
	 */
	public String getPostcodeM() {
		return postcodeM;
	}
	/**
	 * @param postcodeM the postcodeM to set
	 */
	public void setPostcodeM(String postcodeM) {
		this.postcodeM = postcodeM;
	}
	/**
	 * @return the remarkM
	 */
	public String getRemarkM() {
		return remarkM;
	}
	/**
	 * @param remarkM the remarkM to set
	 */
	public void setRemarkM(String remarkM) {
		this.remarkM = remarkM;
	}
	/**
	 * @return the requireAmtM
	 */
	public double getRequireAmtM() {
		return requireAmtM;
	}
	/**
	 * @param requireAmtM the requireAmtM to set
	 */
	public void setRequireAmtM(double requireAmtM) {
		this.requireAmtM = requireAmtM;
	}
	
	public void copy(DeliveryFee dest, int type) {
		if (type == 0) {
			dest.setId(this.getId());
			dest.setFees(this.getFeeM());
			dest.setBeginDate(this.getBeginDateM());
			dest.setEndDate(this.getEndDateM());
			dest.setRequireAmt(this.getRequireAmtM());
			dest.setRemark(this.getRemarkM());
		}
		if (type == 1) {
			this.setId(dest.getId());
			this.setFeeM(dest.getFees());
			this.setBeginDateM(dest.getBeginDate());
			this.setEndDateM(dest.getEndDate());
			this.setRequireAmtM(dest.getRequireAmt());
			this.setRemarkM(dest.getRemark());
		}
	}
	public Collection getDeliveryTypes() {
		return deliveryTypes;
	}
	public Collection getMbrLevels() {
		return mbrLevels;
	}
	public void setDeliveryTypes(Collection deliveryTypes) {
		this.deliveryTypes = deliveryTypes;
	}
	public void setMbrLevels(Collection mbrLevels) {
		this.mbrLevels = mbrLevels;
	}
	
}

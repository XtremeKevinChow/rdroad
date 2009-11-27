/**
 * DefaultDeliveryFeeForm.java
 * 2008-5-19
 * ÉÏÎç09:45:35
 * user
 * DefaultDeliveryFeeForm
 */
package com.magic.crm.user.form;

import org.apache.struts.action.ActionForm;

/**
 * @author user
 *
 */
public class DefaultDeliveryFeeForm extends ActionForm {
	int[] id;
	int[] levelId;
	int[] deliveryId;
	double[] deliveryFee;
	double[] packageFee;
	/**
	 * @return the deliveryFee
	 */
	public double[] getDeliveryFee() {
		return deliveryFee;
	}
	/**
	 * @param deliveryFee the deliveryFee to set
	 */
	public void setDeliveryFee(double[] deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	/**
	 * @return the deliveryId
	 */
	public int[] getDeliveryId() {
		return deliveryId;
	}
	/**
	 * @param deliveryId the deliveryId to set
	 */
	public void setDeliveryId(int[] deliveryId) {
		this.deliveryId = deliveryId;
	}
	/**
	 * @return the id
	 */
	public int[] getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int[] id) {
		this.id = id;
	}
	/**
	 * @return the levelId
	 */
	public int[] getLevelId() {
		return levelId;
	}
	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(int[] levelId) {
		this.levelId = levelId;
	}
	/**
	 * @return the packageFee
	 */
	public double[] getPackageFee() {
		return packageFee;
	}
	/**
	 * @param packageFee the packageFee to set
	 */
	public void setPackageFee(double[] packageFee) {
		this.packageFee = packageFee;
	}
	
}

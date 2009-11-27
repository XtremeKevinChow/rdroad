/**
 * DefaultDeliveryFee.java
 * 2008-5-19
 * ÉÏÎç09:05:35
 * user
 * DefaultDeliveryFee
 */
package com.magic.crm.user.entity;

import com.magic.crm.system.entity.MemberLevel;
import com.magic.crm.system.entity.DeliveryType;
/**
 * @author user
 *
 */
public class DefaultDeliveryFee {
	private int id;
	private DeliveryType deliveryType = new DeliveryType();
	private MemberLevel memberLevel = new MemberLevel();
	private double deliveryFee = 0.0;
	private double packageFee = 0.0;
	/**
	 * @return the deliveryFee
	 */
	public double getDeliveryFee() {
		return deliveryFee;
	}
	/**
	 * @param deliveryFee the deliveryFee to set
	 */
	public void setDeliveryFee(double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	/**
	 * @return the deliveryType
	 */
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}
	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
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
	 * @return the memberLevel
	 */
	public MemberLevel getMemberLevel() {
		return memberLevel;
	}
	/**
	 * @param memberLevel the memberLevel to set
	 */
	public void setMemberLevel(MemberLevel memberLevel) {
		this.memberLevel = memberLevel;
	}
	/**
	 * @return the packageFee
	 */
	public double getPackageFee() {
		return packageFee;
	}
	/**
	 * @param packageFee the packageFee to set
	 */
	public void setPackageFee(double packageFee) {
		this.packageFee = packageFee;
	}
	
}

/*
 * Created on 2007-3-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

import com.magic.utils.Arith;
/**
 * @author user
 * 用于团购购物车明细
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupItemInfo extends ItemInfo {
	
	private static final long serialVersionUID = -2007032700000000002L;

	
	
	/**
	 * @return Returns the standardPrice.
	 */
	public double getStandardPrice() {
		return standardPrice;
	}
	/**
	 * @param standardPrice The standardPrice to set.
	 */
	public void setStandardPrice(double standardPrice) {
		this.standardPrice = standardPrice;
	}
	/**
	 * @return Returns the unPurchaseingCost.
	 */
	public double getUnPurchaseingCost() {
		return unPurchaseingCost;
	}
	/**
	 * @param unPurchaseingCost The unPurchaseingCost to set.
	 */
	public void setUnPurchaseingCost(double unPurchaseingCost) {
		this.unPurchaseingCost = unPurchaseingCost;
	}
	/**
	 * @return Returns the availQty.
	 */
	public int getAvailQty() {
		return availQty;
	}
	/**
	 * @param availQty The availQty to set.
	 */
	public void setAvailQty(int availQty) {
		this.availQty = availQty;
	}
	
	/**
	 * @return Returns the discountPrice.
	 */
	public double getDiscountPrice() {
		return ( discountPrice == 0.0 ? this.getStandardPrice() : discountPrice );
	}
	/**
	 * @param discountPrice The discountPrice to set.
	 */
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
		this.itemPrice = discountPrice;
	}
	
	/**
	 * @return Returns the groupItemMomey.
	 */
	public double getGroupItemMomey() {
		//return groupItemMomey;
		return Arith.round(discountPrice * itemQty, 2);
	}
	/**
	 * @param groupItemMomey The groupItemMomey to set.
	 */
	public void setGroupItemMomey(double groupItemMomey) {
		this.groupItemMomey = groupItemMomey;
	}
	
}

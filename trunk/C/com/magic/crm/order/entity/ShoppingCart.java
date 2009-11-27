/*
 * Created on 2005-1-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

import java.io.Serializable;
import java.util.*;

import com.magic.utils.Arith;
import com.magic.crm.member.entity.OrgMember;
import com.magic.crm.order.entity.OrgOrder;
/**
 * @author Administrator
 *
 * 购物车放在session中
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShoppingCart implements Serializable {
	
	private HashMap mbCart = new HashMap();
	public List getCart(Long mbid) throws Exception {
		Object items = mbCart.get(mbid);
		if (items == null) {
			return null;
		}
		if (items instanceof List) {
			return (List) items;
		} else {
			throw new Exception("shopping cart is not List");
		}
	}
	
	public void setCart(Long mbid,Collection vItems) throws Exception {
		if( mbid == null || vItems == null) throw new Exception("null parameter");
		mbCart.put(mbid,vItems);
	}
	
	public void deleteCart(Long mbid) throws Exception {
		if( mbid == null) throw new Exception("null parameter");
		mbCart.remove(mbid);
		
	}
	//团购订单
	private OrgOrder orgOrder = new OrgOrder();
	public OrgOrder getOrgOrder() {
		return orgOrder;
	}

	public void setOrgOrder(OrgOrder orgOrder) {
		this.orgOrder = orgOrder;
	}
	
	// 团购会员
	private OrgMember orgMember = new OrgMember();
	public OrgMember getOrgMember() {
		return orgMember;
	}
	
	public void setOrgMember(OrgMember orgMember) {
		this.orgMember = orgMember;
	}
	
	// 发送信息
	private DeliveryInfo deliveryInfo = new DeliveryInfo();
	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	// 其他信息 
	private OtherInfo otherInfo = new OtherInfo();
	public OtherInfo getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(OtherInfo otherInfo) {
		this.otherInfo = otherInfo;
	}
	//礼品明细
	private List gifts = new java.util.ArrayList();

	public List getGifts() {
		return gifts;
	}

	public void setGifts(List gifts) {
		this.gifts = gifts;
	}

	// 产品明细
	private List items = new java.util.ArrayList();
	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
	// 折扣
	private double discount = 0.0;
	
	/**
	 * @return Returns the discount.
	 */
	public double getDiscount() {
		return (discount == 0.0 ? 1 : discount);
	}

	/**
	 * @param discount
	 *            The discount to set.
	 */

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * 判断是否预售订单
	 * 一种是产品上标记为预售产品，另外一种是特殊货号
	 * @return
	 */
	public boolean isPreSellOrder() {
		//非礼品
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				//if (ii.is_pre_sell == 1)
				if(ii.getStockStatusName().equals("虚拟库存"))
					return true;
			}
		}
		return false;
	}

	
	/**
	 * @return Returns 团购总价(参考值)
	 */
	public double getGroupTotalMoney() {
		double total = 0.0;
		if (items != null && items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				total += Arith.mul(ii.getSilverPrice(), ii.getItemQty());
			}
		}
		return Arith.round(total, 2);

	}


	/**
	 * @return Returns 团购总价差异
	 */
	public double getGroupDifference() {
		return Arith.sub(getGroupTotalMoney(), getTotalMoney()) ;
	}
	/**
	 * @return Returns 实际总额
	 */
	public double getTotalMoney() {
		double totalMoney = 0.0;
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				//totalMoney += ii.getItemMoney();
				totalMoney += ii.getGroupItemMomey();
			}
		}

		return Arith.round(totalMoney,2);
		
	}

}

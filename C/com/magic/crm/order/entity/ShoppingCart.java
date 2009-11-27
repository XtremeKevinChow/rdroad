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
 * ���ﳵ����session��
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
	//�Ź�����
	private OrgOrder orgOrder = new OrgOrder();
	public OrgOrder getOrgOrder() {
		return orgOrder;
	}

	public void setOrgOrder(OrgOrder orgOrder) {
		this.orgOrder = orgOrder;
	}
	
	// �Ź���Ա
	private OrgMember orgMember = new OrgMember();
	public OrgMember getOrgMember() {
		return orgMember;
	}
	
	public void setOrgMember(OrgMember orgMember) {
		this.orgMember = orgMember;
	}
	
	// ������Ϣ
	private DeliveryInfo deliveryInfo = new DeliveryInfo();
	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	// ������Ϣ 
	private OtherInfo otherInfo = new OtherInfo();
	public OtherInfo getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(OtherInfo otherInfo) {
		this.otherInfo = otherInfo;
	}
	//��Ʒ��ϸ
	private List gifts = new java.util.ArrayList();

	public List getGifts() {
		return gifts;
	}

	public void setGifts(List gifts) {
		this.gifts = gifts;
	}

	// ��Ʒ��ϸ
	private List items = new java.util.ArrayList();
	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
	// �ۿ�
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
	 * �ж��Ƿ�Ԥ�۶���
	 * һ���ǲ�Ʒ�ϱ��ΪԤ�۲�Ʒ������һ�����������
	 * @return
	 */
	public boolean isPreSellOrder() {
		//����Ʒ
		if (items != null || items.size() > 0) {
			Iterator it = items.iterator();
			while (it.hasNext()) {
				ItemInfo ii = (ItemInfo) it.next();
				//if (ii.is_pre_sell == 1)
				if(ii.getStockStatusName().equals("������"))
					return true;
			}
		}
		return false;
	}

	
	/**
	 * @return Returns �Ź��ܼ�(�ο�ֵ)
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
	 * @return Returns �Ź��ܼ۲���
	 */
	public double getGroupDifference() {
		return Arith.sub(getGroupTotalMoney(), getTotalMoney()) ;
	}
	/**
	 * @return Returns ʵ���ܶ�
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

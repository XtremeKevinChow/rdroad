/*
 * Created on 2005-1-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

import java.io.Serializable;

import com.magic.utils.Arith;
import com.magic.crm.member.entity.Member;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Order implements Serializable {

	protected int orderId;
	
	protected long ref_order_id;
	
	public long getRef_order_id() {
		return ref_order_id;
	}

	public void setRef_order_id(long ref_order_id) {
		this.ref_order_id = ref_order_id;
	}

	protected String orderNumber;
	
	protected int buyerId;
	
	protected double totalMoney;
	
	protected double goodsFee;
	
	protected double normalFee;
	
	public double getNormalFee() {
		return normalFee;
	}

	public void setNormalFee(double normalFee) {
		this.normalFee = normalFee;
	}
	
	private Boolean manualFreefreight = false;
	
	/**
	 * @return the manualFreefreight
	 */
	public Boolean getManualFreefreight() {
		return manualFreefreight;
	}

	/**
	 * @param manualFreefreight the manualFreefreight to set
	 */
	public void setManualFreefreight(Boolean manualFreefreight) {
		this.manualFreefreight = manualFreefreight;
	}

	/**
	 * @return the freeFreightReason
	 */
	public String getFreeFreightReason() {
		return freeFreightReason;
	}

	/**
	 * @param freeFreightReason the freeFreightReason to set
	 */
	public void setFreeFreightReason(String freeFreightReason) {
		this.freeFreightReason = freeFreightReason;
	}

	private String freeFreightReason = "";

	protected double payable;
	
	protected double mbPayable;
	
	protected double discount_fee;
	
	
	public double getDiscount_fee() {
		return discount_fee;
	}

	public void setDiscount_fee(double discount_fee) {
		this.discount_fee = discount_fee;
	}

	public double getMbPayable() {
		return mbPayable;
	}

	public void setMbPayable(double mbPayable) {
		this.mbPayable = mbPayable;
	}

	protected int statusId = -1000;
	
	protected String statusName;
	
	protected int prTypeId;
	
	protected int orderType;
	
	protected String orderTypeName;
	
	protected String prTypeName;
	
	protected int categoryId = -1000;
	
	protected String categoryName;
	
	protected String createDate;
	
	protected int creatorId;
	
	protected String creatorName;
	
	protected String modifyDate;
	
	protected int modifierId;
	
	protected String modifierName;
	
	protected double orderUse;
	
	public double getOrderEmoney() {
		return orderEmoney;
	}

	public void setOrderEmoney(double orderEmoney) {
		this.orderEmoney = orderEmoney;
	}

	protected double orderEmoney;
	
	protected double appendFee;
	
	protected boolean isReturned = false;
	
	protected boolean isChanged = false;
	
	protected boolean isSupply = false;
	
	//会员信息
	protected Member member = new Member();
	
	//送货信息
	protected DeliveryInfo deliveryInfo = new DeliveryInfo();
	
	/** add by user 2008-05-19*/
	protected double packageFee;

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

	public double getAppendFee() {
		return appendFee;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public void setAppendFee(double appendFee) {
		this.appendFee = appendFee;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public double getGoodsFee() {
		return goodsFee;
	}

	public void setGoodsFee(double goodsFee) {
		this.goodsFee = goodsFee;
	}

	public int getModifierId() {
		return modifierId;
	}

	public void setModifierId(int modifierId) {
		this.modifierId = modifierId;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getOrderUse() {
		return orderUse;
	}

	public void setOrderUse(double orderUse) {
		this.orderUse = orderUse;
	}

	public double getPayable() {
		return payable;
	}

	public void setPayable(double payable) {
		this.payable = payable;
	}

	public int getPrTypeId() {
		return prTypeId;
	}

	public void setPrTypeId(int prTypeId) {
		this.prTypeId = prTypeId;
	}

	public String getPrTypeName() {
		return prTypeName;
	}

	public void setPrTypeName(String prTypeName) {
		this.prTypeName = prTypeName;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	/**
	 * 订单欠款（订单应付 - 帐户抵扣）
	 * @return
	 */
	public double getOrderOwe() {
		return Arith.round(getPayable() - getOrderUse(), 2);
	}

	/**
	 * 该订单是否可以进行修改
	 * 
	 * @return
	 */
	public boolean isModifiable() {
		
		int status = this.getStatusId();
		//网上订单不能修改，如果一定需要修改可以修改此处，但是也只能允许修改送货信息等
		//if (this.getPrTypeId() == 3)
		//	return false;
		if (this.getOrderType() == 20) {
			return false;
		}

		// 订单状态进行到就绪(25)以后 或被修改被取消后或退货(-8)后 则不能修改
		if (status ==0 ||status == 15 || status ==20 || status== 21 || status ==25) {
			// 不能修改
			return true;
		}

		return false;
	}
	
	/**
	 * 该订单是否可以被取消
	 * 
	 * @return
	 */
	public boolean isCancelable() {
		int status = this.getStatusId();

		//订单状态进行到就绪(25)以后 或被修改被取消后或退货(-8)后 则不能修改
		if (status > 25 || status < 0|| orderType == 20) {
			// 不能取消
			return false;
		}

		return true;
	}
	/**
	 * 该订单是否可以被运行
	 * 
	 * @return
	 */
	public boolean isRunnable() {
		int status = this.getStatusId();
		if (status >= 20 || status < 0|| orderType==20) {
			// 不能取消
			return false;
		}

		return true;
	}
	
	/**
	 * 定单是否可换货
	 * 只有完成的定单才可以换货
	 * @return
	 */
	public boolean isChangeable() {
		int status = this.getStatusId();
		if (status < 98 || status >100) {
			return false;
		}

		return true;
	}
	
	
	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
	public boolean isReturned() {
		
		return this.isReturned;
	}
	
	
	
	public boolean isChanged() {
		return isChanged;
	}
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public boolean isSupply() {
		return isSupply;
	}

	public void setSupply(boolean isSupply) {
		this.isSupply = isSupply;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	
}

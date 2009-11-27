package com.magic.crm.order.entity;

import com.magic.crm.promotion.entity.Mbr_gift_money_by_order;

/**
 * ∂©µ•¿Ò»Ø µÃÂ table:ord_gifts
 * @author user
 *
 */
public class OrderGifts {
	
	private long id;
	
	private int orderId;
	
	private String giftNumber;
	
	private double disAmt;
	
	private long award_id;
	
	/**
	 * @return the award_id
	 */
	public long getAward_id() {
		return award_id;
	}

	/**
	 * @param award_id the award_id to set
	 */
	public void setAward_id(long award_id) {
		this.award_id = award_id;
	}

	private Ticket ticket = new Ticket();
	
	
	private Mbr_gift_money_by_order moneyByOrder= new Mbr_gift_money_by_order();
	
	
	public Mbr_gift_money_by_order getMoneyByOrder() {
		return moneyByOrder;
	}

	public void setMoneyByOrder(Mbr_gift_money_by_order moneyByOrder) {
		this.moneyByOrder = moneyByOrder;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public double getDisAmt() {
		return disAmt;
	}

	public void setDisAmt(double disAmt) {
		this.disAmt = disAmt;
	}

	public String getGiftNumber() {
		return giftNumber;
	}

	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	
}

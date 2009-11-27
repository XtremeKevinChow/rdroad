package com.magic.crm.shippingnotice.entity;

/**
 * 发货单礼券对应entity: shippingnotice_gifts
 * @author user
 *
 */
public class SnGifts {
	
	private long id;
	private int snId;
	private String giftNumber;
	private double disAmt;
	
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
	public int getSnId() {
		return snId;
	}
	public void setSnId(int snId) {
		this.snId = snId;
	}
	
}

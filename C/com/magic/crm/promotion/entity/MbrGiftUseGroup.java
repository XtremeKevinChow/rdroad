package com.magic.crm.promotion.entity;

/**
 * ÀñÈ¯Ê¹ÓÃ×é
 * @author user
 *
 */
public class MbrGiftUseGroup {
	private long id;
	private String groupNO;
	private int giftType;
	private String giftNumber;
	private int isUsed;
	private int status;
	
	public String getGroupNO() {
		return groupNO;
	}
	public void setGroupNO(String groupNO) {
		this.groupNO = groupNO;
	}
	public String getGiftNumber() {
		return giftNumber;
	}
	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber;
	}
	public int getGiftType() {
		return giftType;
	}
	public void setGiftType(int giftType) {
		this.giftType = giftType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}

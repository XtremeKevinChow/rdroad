package com.magic.crm.order.entity;

/**
 * ¿Ò»ØEntity∂‘”¶±Ìmbr_gift_certificates
 * @author user
 *
 */
public class Ticket {
	
	protected long id;
	protected String giftNumber;
	protected int giftType;
	protected String startDate;
	protected String endDate;
	protected String memberStartDate;
	protected String memberEndDate;
	protected int personNum;
	protected int amount;
	protected double giftMoney;
	protected double orderMoney;
	protected String description;
	protected int isWeb;
	protected int isNewMember;
	protected int isOldMember;
	protected int isMemberLevel;
	protected int orderGroupId;
	protected int isMoneyForOrder;
	protected int productGroupId;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public double getGiftMoney() {
		return giftMoney;
	}
	public void setGiftMoney(double giftMoney) {
		this.giftMoney = giftMoney;
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
	
	public int getProductGroupId() {
		return productGroupId;
	}
	public void setProductGroupId(int productGroupId) {
		this.productGroupId = productGroupId;
	}
	public int getIsMemberLevel() {
		return isMemberLevel;
	}
	public void setIsMemberLevel(int isMemberLevel) {
		this.isMemberLevel = isMemberLevel;
	}
	public int getIsMoneyForOrder() {
		return isMoneyForOrder;
	}
	public void setIsMoneyForOrder(int isMoneyForOrder) {
		this.isMoneyForOrder = isMoneyForOrder;
	}
	public int getIsNewMember() {
		return isNewMember;
	}
	public void setIsNewMember(int isNewMember) {
		this.isNewMember = isNewMember;
	}
	public int getIsOldMember() {
		return isOldMember;
	}
	public void setIsOldMember(int isOldMember) {
		this.isOldMember = isOldMember;
	}
	public int getIsWeb() {
		return isWeb;
	}
	public void setIsWeb(int isWeb) {
		this.isWeb = isWeb;
	}
	
	
	public String getMemberEndDate() {
		return memberEndDate;
	}
	public void setMemberEndDate(String memberEndDate) {
		this.memberEndDate = memberEndDate;
	}
	public String getMemberStartDate() {
		return memberStartDate;
	}
	public void setMemberStartDate(String memberStartDate) {
		this.memberStartDate = memberStartDate;
	}
	public int getOrderGroupId() {
		return orderGroupId;
	}
	public void setOrderGroupId(int orderGroupId) {
		this.orderGroupId = orderGroupId;
	}
	public double getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(double orderMoney) {
		this.orderMoney = orderMoney;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
}

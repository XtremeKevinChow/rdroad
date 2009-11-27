package com.magic.crm.member.entity;

public class PostCodeFile {
	
	private String ordNumber;
	
	private String ordPostcode;
	
	private String cardId;
	
	private String mbrPostcode;
	
	private String newPostcode;
	
	private String ordAddress;

	public String getCardId() {
		return cardId;
	}

	public String getMbrPostcode() {
		return mbrPostcode;
	}

	public String getNewPostcode() {
		return newPostcode;
	}

	public String getOrdNumber() {
		return ordNumber;
	}

	public String getOrdPostcode() {
		return ordPostcode;
	}

	public String getOrdAddress() {
		return ordAddress;
	}

	public void setOrdAddress(String ordAddress) {
		this.ordAddress = ordAddress;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setMbrPostcode(String mbrPostcode) {
		this.mbrPostcode = mbrPostcode;
	}

	public void setNewPostcode(String newPostcode) {
		this.newPostcode = newPostcode;
	}

	public void setOrdNumber(String ordNumber) {
		this.ordNumber = ordNumber;
	}

	public void setOrdPostcode(String ordPostcode) {
		this.ordPostcode = ordPostcode;
	}
	
	
}


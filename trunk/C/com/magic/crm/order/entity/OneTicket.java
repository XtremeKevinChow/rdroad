package com.magic.crm.order.entity;

import java.sql.Date;

public class OneTicket {
	/* pk */
	protected long id;
	protected int isNeedPass;
	protected String giftNo;
	protected String pass;
	/* fk */
	protected Ticket ticket = new Ticket();
	protected Date createDate;
	protected int createPerson;
	protected int agentId;
	protected String agentName;
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(int createPerson) {
		this.createPerson = createPerson;
	}
	public String getGiftNo() {
		return giftNo;
	}
	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getIsNeedPass() {
		return isNeedPass;
	}
	public void setIsNeedPass(int isNeedPass) {
		this.isNeedPass = isNeedPass;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
}

/*
 * Created on 2005-4-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.entity;

import java.io.Serializable;

import com.magic.crm.member.entity.MemberAWARD;

/**
 * @author Administrator
 *
 * ��ȯ���ý��
 */
public class TicketMoney implements Serializable {
	
	private OneTicket ticket = null;
	
	/**
	 * @return the ticket
	 */
	public OneTicket getTicket() {
		return ticket;
	}
	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(OneTicket ticket) {
		this.ticket = ticket;
	}
	/**
	 * @return the mbAward
	 */
	public MemberAWARD getMbAward() {
		return mbAward;
	}
	/**
	 * @param mbAward the mbAward to set
	 */
	public void setMbAward(MemberAWARD mbAward) {
		this.mbAward = mbAward;
	}
	private MemberAWARD mbAward = null; 
	
	/* ʹ��״̬ 0-������-1-�쳣 */
	private int useStatus = 0;
	
	/* ��ȯ���� */
	private String ticketCode = "";
	
	/* �ɵ��ý�� */
	private double money = 0;
	
	/* ����������� */
	private double order_floor = 0;
	
	/* ��ȯ���� */
	private String ticketType = "";
	
	/* ��� */
	private String ticketHeader;
	
	/* ���� */
	
	/* �Ƿ��ۿۣ�N-ʵ�ʽ��;Y-�ۿۣ� */
	private String isDiscount = null;
	
	/* �ֿ����ͣ�1-��һ����;2-�ۼ���ɶ����� */
	private int disType = 0;
	
	/* �ҹ��Ĳ�Ʒ�飨-1:Ĭ�ϲ��ҹ�;0:��Ʒ;1-��;2:Ӱ��;3:����;4:��Ϸ���;5:��Ʒ;6:������ */
	private int itemType = -1;
	/* �ҹ��Ĳ�Ʒ���飩��� */
	private double itemTypeMoney;
	
	public int getDisType() {
		return disType;
	}
	public void setDisType(int disType) {
		this.disType = disType;
	}
	public String getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}
	public String getTicketHeader() {
		return ticketHeader;
	}
	public void setTicketHeader(String ticketHeader) {
		this.ticketHeader = ticketHeader;
	}
	/**
	 * @return Returns the ticketType.
	 */
	public String getTicketType() {
		return ticketType;
	}
	/**
	 * @param ticketType The ticketType to set.
	 */
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	/**
	 * @return Returns the money.
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * @param money The money to set.
	 */
	public void setMoney(double money) {
		this.money = money;
	}
	/**
	 * @return Returns the order_floor.
	 */
	public double getOrder_floor() {
		return order_floor;
	}
	/**
	 * @param order_floor The order_floor to set.
	 */
	public void setOrder_floor(double order_floor) {
		this.order_floor = order_floor;
	}
	/**
	 * @return Returns the ticketCode.
	 */
	public String getTicketCode() {
		return ticketCode;
	}
	/**
	 * @param ticketCode The ticketCode to set.
	 */
	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}
	public int getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(int useStatus) {
		this.useStatus = useStatus;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public double getItemTypeMoney() {
		return itemTypeMoney;
	}
	public void setItemTypeMoney(double itemTypeMoney) {
		this.itemTypeMoney = itemTypeMoney;
	}
	
}

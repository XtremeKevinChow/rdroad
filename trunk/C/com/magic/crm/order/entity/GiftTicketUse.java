package com.magic.crm.order.entity;

import java.sql.Date;

/**
 * 
 * @author user
 *
 */
public class GiftTicketUse {
	
	//��Աid
	private int memberId;
	
	//�ܴ���������
	private int totalNum;
	
	//����ʹ�ü�����
	private int num;
	
	//��ȯ����
	private String ticketNum;
	
	//�޸�����
	private Date modDate;
	
	//�Ƿ�����޸�
	private String isUpdateable;

	public String getIsUpdateable() {
		return isUpdateable;
	}

	public void setIsUpdateable(String isUpdateable) {
		this.isUpdateable = isUpdateable;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	
}

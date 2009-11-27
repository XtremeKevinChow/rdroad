package com.magic.crm.order.entity;

import java.sql.Date;

/**
 * 
 * @author user
 *
 */
public class GiftTicketUse {
	
	//会员id
	private int memberId;
	
	//总次数计数器
	private int totalNum;
	
	//个人使用计数器
	private int num;
	
	//礼券号码
	private String ticketNum;
	
	//修改日期
	private Date modDate;
	
	//是否可以修改
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

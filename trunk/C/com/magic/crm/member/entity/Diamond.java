/**
 * Diamond.java
 * 2008-4-22
 * 下午03:59:12
 * user
 * Diamond
 */
package com.magic.crm.member.entity;

/**
 * @author user
 *
 */
public class Diamond {
	/** 会员 */
	protected Member member = null;
	/** 正常 */
	protected int nomalCount;
	/** 未生效 */
	protected int waitNomalCount;
	/** 过期失效 */
	protected int overdueCount;
	/** 退货失效 */
	protected int returnInvalidCount;
	/** 兑换 */
	protected int exchangedCount;
	/**
	 * @return the member
	 */
	public Member getMember() {
		if (member == null) {
			member = new Member();
		}
		return member;
	}
	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}
	/**
	 * @return the nomalCount
	 */
	public int getNomalCount() {
		return nomalCount;
	}
	/**
	 * @param nomalCount the nomalCount to set
	 */
	public void setNomalCount(int nomalCount) {
		this.nomalCount = nomalCount;
	}
	/**
	 * @return the overdueCount
	 */
	public int getOverdueCount() {
		return overdueCount;
	}
	/**
	 * @param overdueCount the overdueCount to set
	 */
	public void setOverdueCount(int overdueCount) {
		this.overdueCount = overdueCount;
	}
	/**
	 * @return the returnInvalidCount
	 */
	public int getReturnInvalidCount() {
		return returnInvalidCount;
	}
	/**
	 * @param returnInvalidCount the returnInvalidCount to set
	 */
	public void setReturnInvalidCount(int returnInvalidCount) {
		this.returnInvalidCount = returnInvalidCount;
	}
	/**
	 * @return the waitNomalCount
	 */
	public int getWaitNomalCount() {
		return waitNomalCount;
	}
	/**
	 * @param waitNomalCount the waitNomalCount to set
	 */
	public void setWaitNomalCount(int waitNomalCount) {
		this.waitNomalCount = waitNomalCount;
	}
	/**
	 * @return the exchangedCount
	 */
	public int getExchangedCount() {
		return exchangedCount;
	}
	/**
	 * @param exchangedCount the exchangedCount to set
	 */
	public void setExchangedCount(int exchangedCount) {
		this.exchangedCount = exchangedCount;
	}
	
}

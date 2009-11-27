package com.magic.crm.member.entity;

/**
 * »ý·Ö Value Object
 * @author user
 *
 */
public class Exp {
	private Member member = new Member();
	
	private int exp;
	private int oldAmountExp;
	private int amountExp;
	public int getAmountExp() {
		return amountExp;
	}
	public void setAmountExp(int amountExp) {
		this.amountExp = amountExp;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getOldAmountExp() {
		return oldAmountExp;
	}
	public void setOldAmountExp(int oldAmountExp) {
		this.oldAmountExp = oldAmountExp;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	
}

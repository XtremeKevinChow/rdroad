package com.magic.crm.user.entity;

import com.magic.utils.Arith;
public class Grade {
	private String userId;
	private String userName;
	private String empNo;
	private int levelId;
	private int total ;
	private int amount1;
	private int amount2;
	private int amount3;
	private int amount4;
	
	public double getRate1() {
		return (total != 0) ? (Arith.div(100*amount1, total, 0)) : 0.00;
	}
	public double getRate2() {
		return (total != 0) ? (Arith.div(100*amount2, total, 0)) : 0.00;
	}
	public double getRate3() {
		return (total != 0) ? (Arith.div(100*amount3, total, 0)) : 0.00;
	}
	public double getRate4() {
		return (total != 0) ? (Arith.div(100*amount4, total, 0)) : 0.00;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getAmount1() {
		return amount1;
	}
	public void setAmount1(int amount1) {
		this.amount1 = amount1;
	}
	public int getAmount2() {
		return amount2;
	}
	public void setAmount2(int amount2) {
		this.amount2 = amount2;
	}
	public int getAmount3() {
		return amount3;
	}
	public void setAmount3(int amount3) {
		this.amount3 = amount3;
	}
	public int getAmount4() {
		return amount4;
	}
	public void setAmount4(int amount4) {
		this.amount4 = amount4;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		String levelName = null;
		switch (levelId) {
		case 4:
			levelName = "不满意";
			break;
		case 3:
			levelName = "一般";
			break;
		case 2:
			levelName = "满意";
			break;
		case 1:
			levelName = "很满意";
			break;
		default:
			break;
		}
		return levelName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}

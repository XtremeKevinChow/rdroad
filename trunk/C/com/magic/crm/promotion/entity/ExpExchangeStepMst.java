package com.magic.crm.promotion.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
public class ExpExchangeStepMst {
	private long id;
	private ExpExchangeActivity activity = new ExpExchangeActivity();
	private int beginExp;
	private int createPerson;
	private Date createDate;
	//Y-有效; N-无效
	private String status = "Y";
	
	//有效性
	private boolean enabled = true;
	
	
	//档次明细<ExpExchangeStepDtl>
	private Collection dtlList = new ArrayList();
	

	public Collection getDtlList() {
		return dtlList;
	}
	public void setDtlList(Collection dtlList) {
		this.dtlList = dtlList;
	}
	public ExpExchangeActivity getActivity() {
		return activity;
	}
	public void setActivity(ExpExchangeActivity activity) {
		this.activity = activity;
	}
	public int getBeginExp() {
		return beginExp;
	}
	public void setBeginExp(int beginExp) {
		this.beginExp = beginExp;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	//明细设置为有效
	public void enabledStepDtl() {
		Iterator it = dtlList.iterator();
		while (it.hasNext()) {
			ExpExchangeStepDtl dtl = (ExpExchangeStepDtl)it.next();
			dtl.setEnabled(true);
		}
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

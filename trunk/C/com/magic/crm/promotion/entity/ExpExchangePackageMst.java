package com.magic.crm.promotion.entity;

import java.util.Collection;
import java.util.ArrayList;

public class ExpExchangePackageMst {
	private String packageNo;
	private String desc;
	//Y-有效；N-无效
	private String status;
	private String url;
	
	private Collection dtlList = new ArrayList();
	
	public Collection getDtlList() {
		return dtlList;
	}
	public void setDtlList(Collection dtlList) {
		this.dtlList = dtlList;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

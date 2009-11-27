package com.magic.crm.promotion.form;

import org.apache.struts.action.ActionForm;
import com.magic.crm.common.pager.PagerForm;
public class ExpExchangePackageForm extends PagerForm{
	private String packageNo;
	private String desc;
	//Y-有效；N-无效
	private String status = "Y";
	private String url;
	
	//数组
	private String[] packageType;
	private String[] no;
	private int[] quantity;
	
	               
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String[] getNo() {
		return no;
	}
	public void setNo(String[] no) {
		this.no = no;
	}
	public int[] getQuantity() {
		return quantity;
	}
	public void setQuantity(int[] quantity) {
		this.quantity = quantity;
	}
	public String[] getPackageType() {
		return packageType;
	}
	public void setPackageType(String[] type) {
		this.packageType = type;
	}
	
}

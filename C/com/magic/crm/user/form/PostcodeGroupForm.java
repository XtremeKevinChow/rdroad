package com.magic.crm.user.form;

import org.apache.struts.action.ActionForm;
public class PostcodeGroupForm extends ActionForm {
	private int id;
	private String groupName;
	private String description;
	
	private String[] postcode;
	private double[] postFee;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String[] getPostcode() {
		return postcode;
	}
	public void setPostcode(String[] postcode) {
		this.postcode = postcode;
	}
	public double[] getPostFee() {
		return postFee;
	}
	public void setPostFee(double[] postFee) {
		this.postFee = postFee;
	}
	
	
}

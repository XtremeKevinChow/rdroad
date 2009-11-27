package com.magic.crm.user.form;
import org.apache.struts.action.ActionForm;
public class PostcodeSetForm extends ActionForm {
	private int id;
	private String postcode;
	private double postFee;
	private int groupId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public double getPostFee() {
		return postFee;
	}
	public void setPostFee(double postFee) {
		this.postFee = postFee;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
}

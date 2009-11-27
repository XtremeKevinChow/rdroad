package com.magic.crm.order.entity;

import com.magic.crm.member.entity.OrgMember;
public class OrgOrder extends Order {
	protected OrgMember orgMember = new OrgMember();

	public OrgMember getOrgMember() {
		return orgMember;
	}

	public void setOrgMember(OrgMember orgMember) {
		this.orgMember = orgMember;
	}
	
}

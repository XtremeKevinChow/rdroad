/*
 * Created on 2005-12-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import org.apache.struts.action.ActionForm;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author user
 * create by user 2005-12-26 16:56
 * ˵������FORM���ں�ҳ�潻����ÿ��Field��ҳ�����Element���Ӧ
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGroupForm extends ActionForm implements Serializable {
	
	/** ��¼ID **/
	private long ID = -1;
	
	/** �Ź����� **/
	private String groupCode = null;
	
	/** ��˾���� **/
	private String compayName = null;
	
	/** ��ϵ�绰 **/
	private String telephone = null;
	
	/** ��ַID **/
	private long addressID = -1;
	
	/** ��ϵ�绰�� **/
	private String telephone2 = null;
	
	/** �ʱ� **/
	private String postcode = null;
	
	/** ��ϵ�� **/
	private String relationPerson = null;
	
	/** ��½�� **/
	private int loginUserID = -1;
	
	/** ��Ա��ַ **/
	private String address = null;
	
	
	protected ArrayList provs = new ArrayList();
	protected ArrayList citys = new ArrayList();
	protected ArrayList sects = new ArrayList();
	
	protected String province = "";
	protected String city = "";
	protected String section = "";
	
	
	
	public ArrayList getProvs() {
		return provs;
	}

	public void setProvs(ArrayList provs) {
		this.provs = provs;
	}

	public ArrayList getCitys() {
		return citys;
	}

	public void setCitys(ArrayList citys) {
		this.citys = citys;
	}

	public ArrayList getSects() {
		return sects;
	}

	public void setSects(ArrayList sects) {
		this.sects = sects;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	/** �չ��캯�� **/
	public MemberGroupForm() {}
	
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return Returns the addressID.
	 */
	public long getAddressID() {
		return addressID;
	}
	/**
	 * @param addressID The addressID to set.
	 */
	public void setAddressID(long addressID) {
		this.addressID = addressID;
	}
	/**
	 * @return Returns the compayName.
	 */
	public String getCompayName() {
		return compayName;
	}
	/**
	 * @param compayName The compayName to set.
	 */
	public void setCompayName(String compayName) {
		this.compayName = compayName;
	}
	/**
	 * @return Returns the groupCode.
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * @param groupCode The groupCode to set.
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	/**
	 * @return Returns the iD.
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id) {
		ID = id;
	}
	/**
	 * @return Returns the postcode.
	 */
	public String getPostcode() {
		return postcode;
	}
	/**
	 * @param postcode The postcode to set.
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/**
	 * @return Returns the relationPerson.
	 */
	public String getRelationPerson() {
		return relationPerson;
	}
	/**
	 * @param relationPerson The relationPerson to set.
	 */
	public void setRelationPerson(String relationPerson) {
		this.relationPerson = relationPerson;
	}
	/**
	 * @return Returns the telephone.
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone The telephone to set.
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return Returns the telephone2.
	 */
	public String getTelephone2() {
		return telephone2;
	}
	/**
	 * @param telephone2 The telephone2 to set.
	 */
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}
	
	/**
	 * @return Returns the loginUserID.
	 */
	public int getLoginUserID() {
		return loginUserID;
	}
	/**
	 * @param loginUserID The loginUserID to set.
	 */
	public void setLoginUserID(int loginUserID) {
		this.loginUserID = loginUserID;
	}
}

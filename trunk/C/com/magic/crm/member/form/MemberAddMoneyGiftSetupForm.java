/*
 * Created on 2006-1-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import com.magic.crm.member.entity.MemberAddMoneyGiftSetup;
/**
 * @author ��
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAddMoneyGiftSetupForm extends ActionForm {
	
	/** ��¼ID **/
	private int id = 0;
	
	/** ��ƷID **/
	private int itemID = 0; 
	
	/** ���� **/
	private String itemCode = null;
	
	/** ��Ʒ���� **/
	private String itemName = null;
	
	/** ��ֵ **/
	private double money = 500.0;
	
	/** �������� **/
	private int keepDays = 0;
	
	/** ��Ʒ�۸� **/
	private double price = 0D;
	
	/** ��¼״̬ **/
	private int status = 0;
	
	/** ������ID **/
	private int operatorID = 0;
	
	/** ���������� **/
	private String operatorName = null;
	
	/** ����ʱ�� **/
	private String createDate = null;
	
	private String gift_number = "";
	private String gift_enddate = "";
	private double gift_money = 0;
	private String gift_start_date = "";
	
	ArrayList gifts = new ArrayList();
	
	/**
	 * @return the gifts
	 */
	public ArrayList getGifts() {
		return gifts;
	}
	/**
	 * @param gifts the gifts to set
	 */
	public void setGifts(ArrayList gifts) {
		this.gifts = gifts;
	}
	/**
	 * @return the gift_number
	 */
	public String getGift_number() {
		return gift_number;
	}
	/**
	 * @param gift_number the gift_number to set
	 */
	public void setGift_number(String gift_number) {
		this.gift_number = gift_number;
	}
	/**
	 * @return the gift_enddate
	 */
	public String getGift_enddate() {
		return gift_enddate;
	}
	/**
	 * @param gift_enddate the gift_enddate to set
	 */
	public void setGift_enddate(String gift_enddate) {
		this.gift_enddate = gift_enddate;
	}
	/**
	 * @return the gift_money
	 */
	public double getGift_money() {
		return gift_money;
	}
	/**
	 * @param gift_money the gift_money to set
	 */
	public void setGift_money(double gift_money) {
		this.gift_money = gift_money;
	}
	/**
	 * @return the gift_start_date
	 */
	public String getGift_start_date() {
		return gift_start_date;
	}
	/**
	 * @param gift_start_date the gift_start_date to set
	 */
	public void setGift_start_date(String gift_start_date) {
		this.gift_start_date = gift_start_date;
	}
	/**
	 * @return Returns the operatorName.
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName The operatorName to set.
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return Returns the createDate.
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate The createDate to set.
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return Returns the operatorID.
	 */
	public int getOperatorID() {
		return operatorID;
	}
	/**
	 * @param operatorID The operatorID to set.
	 */
	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return Returns the keepDays.
	 */
	public int getKeepDays() {
		return keepDays;
	}
	/**
	 * @param keepDays The keepDays to set.
	 */
	public void setKeepDays(int keepDays) {
		this.keepDays = keepDays;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the itemID.
	 */
	public int getItemID() {
		return itemID;
	}
	/**
	 * @param itemID The itemID to set.
	 */
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	/**
	 * @return Returns the money.
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * @param money The money to set.
	 */
	public void setMoney(double money) {
		this.money = money;
	}
	/**
	 * @return Returns the itemCode.
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode The itemCode to set.
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	/**
	 * @return Returns the itemName.
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName The itemName to set.
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void copy(MemberAddMoneyGiftSetup target) {
		target.setId(this.id);
		target.setItemID(this.itemID);
		target.setItemCode(this.itemCode);
		target.setMoney(this.money);
		target.setPrice(this.price);
		target.setOperatorID(this.operatorID);
		target.setGift_number(this.gift_number);
		target.setKeepDays(this.keepDays);
	}
}

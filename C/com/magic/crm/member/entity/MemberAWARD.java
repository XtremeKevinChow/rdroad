/*
 * Created on 2005-2-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.entity;

import org.apache.struts.action.ActionForm;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAWARD extends ActionForm{
     public int member_ID=0;
     //public int item_ID=0;
     public double price=0;
     public String create_date="";
     public int used_amount_exp=0;
     public int status=0;
     public int ID=0;
     public int operator_id=0;
     public int quantity=0;
     public int pricelist_line_ID=0;
     public String description ="";
     public int REF_ORDER_LINE_ID=0;
     public int type=0;
     public int clubID=0;
     public double order_require=0;
     
     int sku_id = 0;
     String gift_number = "";
     String color_code = "";
     String color_name = "";
     
     int total_num =0;
     int num = 0;
     
     
     
	public int getTotal_num() {
		return total_num;
	}
	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return the color_name
	 */
	public String getColor_name() {
		return color_name;
	}
	/**
	 * @param color_name the color_name to set
	 */
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}
	/**
	 * @return the sku_id
	 */
	public int getSku_id() {
		return sku_id;
	}
	/**
	 * @param sku_id the sku_id to set
	 */
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
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
	 * @return the color_code
	 */
	public String getColor_code() {
		return color_code;
	}
	/**
	 * @param color_code the color_code to set
	 */
	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}
	/************ add by user 2006-02-21 ***************/
     /** 货号 **/
     private String itemCode = null;
     
     /** 产品名称 **/
     private String itemName = null;
     
     /** 使用积分 **/
     private String operatorName = null;
     
     /** 是否转移礼品 **/
     private int isTransfer = -1;
     
     /** 最终日期 **/
     private String lastDate = null;
     
     /** 订单金额 **/
     private double orderRequire = 0D;
     
     
	/**
	 * @return Returns the orderRequire.
	 */
	public double getOrderRequire() {
		return orderRequire;
	}
	/**
	 * @param orderRequire The orderRequire to set.
	 */
	public void setOrderRequire(double orderRequire) {
		this.orderRequire = orderRequire;
	}
	/**
	 * @return Returns the lastDate.
	 */
	public String getLastDate() {
		if (this.lastDate != null && this.lastDate.length() >= 10) {
			return this.lastDate.substring(0, 10);
		}
		return lastDate;
	}
	/**
	 * @param lastDate The lastDate to set.
	 */
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	/**
	 * @return Returns the isTransfer.
	 */
	public int getIsTransfer() {
		return isTransfer;
	}
	/**
	 * @param isTransfer The isTransfer to set.
	 */
	public void setIsTransfer(int isTransfer) {
		this.isTransfer = isTransfer;
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
     /*
      * get set 
      */
     //俱乐部ID
     public int getClubID(){
      	return this.clubID ;
      }
      public void setClubID(int iclubID){
      	this.clubID =iclubID;
      }      
     public double getOrder_require(){
     	return this.order_require ;
     }
     public void setOrder_require(double iorder_require){
     	this.order_require =iorder_require;
     } 
     
     public int getType(){
     	return this.type ;
     }
     public void setType(int itype){
     	this.type =itype;
     }     
     public int getMember_ID(){
     	return this.member_ID ;
     }
     public void setMember_ID(int imember_ID){
     	this.member_ID =imember_ID;
     }
     public int getID(){
     	return this.ID ;
     }
     public void setID(int iID){
     	this.ID =iID;
     }
    
     public double getPrice(){
     	return this.price;
     }
     public void setPrice(double iprice){
     	this.price =iprice;
     }
     public String getCreate_date(){
     	if (this.create_date != null && this.create_date.length() >= 10) {
     		return this.create_date.substring(0, 10);
     	}
     	return this.create_date ;
     }
     public void setCreate_date(String icreate_date ){
     	this.create_date =icreate_date;
     }
     public int getUsed_amount_exp(){
     	return this.used_amount_exp ;
     }
     public void setUsed_amount_exp(int iused_amount_exp){
     	this.used_amount_exp =iused_amount_exp;
     }
     public int getStatus(){
     	return this.status ;
     }
     public void setStatus(int istatus ){
     	this.status =istatus;
     }
     public int getOperator_id(){
     	return this.operator_id ;
     }
     public void setOperator_id(int ioperator_id ){
     	this.operator_id =ioperator_id;
     }
     public int getQuantity(){
     	return this.quantity ;
     }
     public void setQuantity(int iquantity){
     	this.quantity =iquantity;
     }
     public int getPricelist_line_ID(){
     	return this.pricelist_line_ID ;
     }
     public void setPricelist_line_ID(int ipricelist_line_ID){
     	this.pricelist_line_ID =ipricelist_line_ID;
     }
     public String getDescription(){
     	return this.description;
     }
     public void setDescription(String idescription ){
     	this.description =idescription;
     }
     public int getREF_ORDER_LINE_ID(){
     	return this.REF_ORDER_LINE_ID ;
     }
     public void setREF_ORDER_LINE_ID(int iREF_ORDER_LINE_ID){
     	this.REF_ORDER_LINE_ID =iREF_ORDER_LINE_ID;
     }

}

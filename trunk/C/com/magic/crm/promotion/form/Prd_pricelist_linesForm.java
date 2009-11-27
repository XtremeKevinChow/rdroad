/*
 * Created on 2006-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.form;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Prd_pricelist_linesForm {
	protected int ID=0;
	protected int pricelist_id=0;
	protected int item_id=0;
	protected int page=0;
	protected int commitment=0;
	protected int sell_type=0;
	protected int catalog_edition=0;
	protected int group_id=0;
	protected int status=0;
	protected double common_price=0;
	protected double card_price=0;
	protected double order_require=0;
	protected double web_price=0;
	protected String modify_date="";
	/*******************************/
    public int getID(){
        return ID;
    }
    public void setID(int iID){
        this.ID=iID;
    }	
	/*******************************/
    public int getPricelist_id(){
        return pricelist_id;
    }
    public void setPricelist_id(int pricelist_id){
        this.pricelist_id=pricelist_id;
    }	
	/*******************************/
    public int getItem_id(){
        return item_id;
    }
    public void setItem_id(int item_id){
        this.item_id=item_id;
    }	
	/*******************************/
    public int getPage(){
        return page;
    }
    public void setPage(int page){
        this.page=page;
    }	
	/*******************************/
    public int getCommitment(){
        return commitment;
    }
    public void setCommitment(int commitment){
        this.commitment=commitment;
    }	
	/*******************************/
    public int getSell_type(){
        return sell_type;
    }
    public void setSell_type(int sell_type){
        this.sell_type=sell_type;
    }	
	/*******************************/
    public int getCatalog_edition(){
        return catalog_edition;
    }
    public void setCatalog_edition(int catalog_edition){
        this.catalog_edition=catalog_edition;
    }	
	/*******************************/
    public int getGroup_id(){
        return group_id;
    }
    public void setGroup_id(int group_id){
        this.group_id=group_id;
    }	
	/*******************************/
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }	
	/*******************************/
    public double getCommon_price(){
        return common_price;
    }
    public void setCommon_price(double common_price){
        this.common_price=common_price;
    }
	/*******************************/
    public double getCard_price(){
        return card_price;
    }
    public void setCard_price(double card_price){
        this.card_price=card_price;
    }
	/*******************************/
    public double getOrder_require(){
        return order_require;
    }
    public void setOrder_require(double order_require){
        this.order_require=order_require;
    }
	/*******************************/
    public double getWeb_price(){
        return web_price;
    }
    public void setWeb_price(double web_price){
        this.web_price=web_price;
    }
	/*******************************/
    public String getModify_date(){
        return modify_date;
    }
    public void setModify_date(String modify_date){
        this.modify_date=modify_date;
    }
}

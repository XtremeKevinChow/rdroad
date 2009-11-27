
package com.magic.crm.promotion.entity;

/**
 * @author Administrator(zhux)
 * Created on 2009-2-12
 */
public class Prom_money4qty {
    protected int id=0;
    public int getID(){
        return id;
    }
    public void setID(int iID){
        this.id=iID;
    }
    protected int promotionid=0;
    public int getPromotionID(){
        return promotionid;
    }
    public void setPromotionID(int ipromotionid){
        this.promotionid=ipromotionid;
    }  
    
    protected double money ;
    protected int qty ;
    
    public double getMoney() {
		return money;
	}
	public int getQty() {
		return qty;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
    protected String creatorID="";
    public String getCreatorID(){
        return creatorID;
    }
    public void setCreatorID(String icreatorID){
        this.creatorID=icreatorID;
    } 
    protected String modifierID="";
    public String getModifierID(){
        return modifierID;
    }
    public void setModifierID(String imodifierID){
        this.modifierID=imodifierID;
    }
    protected String createDate="";
    public String getCreateDate(){
        return createDate;
    }
    public void setCreateDate(String icreateDate){
        this.createDate=icreateDate;
    } 
    protected String modifyDate="";
    public String getModifyDate(){
        return modifyDate;
    }
    public void setModifyDate(String imodifyDate){
        this.modifyDate=imodifyDate;
    } 
    protected int flag=0;
    public int getFlag(){
        return flag;
    }
    public void setFlag(int iflag){
        this.flag=iflag;
    } 
    protected String promotionName="";
    public String getPromotionName(){
        return promotionName;
    }
    public void setPromotionName(String ipromotionName){
        this.promotionName=ipromotionName;
    }  
}

/*
 * @author Administrator(ysm)
 * Created on 2005-10-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.form;

/**
 * @author Administrator(ysm)
 * Created on 2005-10-8
 */
public class Prom_ItemForm {
    protected int id=0;
    public int getID(){
        return id;
    }
    public void setID(int iID){
        this.id=iID;
    }
    protected int promotionID=0;
    public int getPromotionID(){
        return promotionID;
    }
    public void setPromotionID(int ipromotionid){
        this.promotionID=ipromotionid;
    }  
    protected String itemid="";
    public String getItemID(){
        return itemid;
    }
    public void setItemID(String iitemid){
        this.itemid=iitemid;
    }  
    protected String itemcode="";
    public String getItemcode(){
        return itemcode;
    }
    public void setItemcode(String iitemcode){
        this.itemcode=iitemcode;
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

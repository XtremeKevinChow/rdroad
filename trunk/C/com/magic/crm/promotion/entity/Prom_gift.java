/*
 * @author Administrator(ysm)
 * Created on 2005-10-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.entity;

/**
 * @author Administrator(ysm)
 * Created on 2005-10-8
 */
public class Prom_gift {
	
	protected String item_name;
	
    public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
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
    protected double overx=0;
    public double getOverx(){
        return overx;
    }
    public void setOverx(double ioverx){
        this.overx=ioverx;
    } 
    protected double addy=0;
    public double getAddy(){
        return addy;
    }
    public void setAddy(double iaddy){
        this.addy=iaddy;
    } 
    protected String beginDate="";
    public String getBeginDate(){
        return beginDate;
    }
    public void setBeginDate(String ibegindate){
        this.beginDate=ibegindate;
    }
    protected String endDate="";
    public String getEndDate(){
        return endDate;
    }
    public void setEndDate(String ienddate){
        this.endDate=ienddate;
    }   
    protected int validflag=0;
    public int getValidflag(){
        return validflag;
    }
    public void setValidflag(int ivalidflag){
        this.validflag=ivalidflag;
    }
    protected int scope=0;
    public int getScope(){
        return scope;
    }
    public void setScope(int iscope){
        this.scope=iscope;
    }
    protected String msc="";
    public String getMSC(){
        return msc;
    }
    public void setMSC(String imsc){
        this.msc=imsc;
    }
    protected String description="";
    public String getDescription(){
        return description;
    }
    public void setDescription(String idescription){
        this.description=idescription;
    }  
    protected String prom_url="";
    public String getProm_url(){
        return prom_url;
    }
    public void setProm_url(String iprom_url){
        this.prom_url=iprom_url;
    }  
    protected String promotionName="";
    public String getPromotionName(){
        return promotionName;
    }
    public void setPromotionName(String ipromotionName){
        this.promotionName=ipromotionName;
    }  
}

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
public class Promotion {
  protected int id=0;
  public int getId(){
      return id;
  }
  public void setId(int iID){
      this.id=iID;
  }
  protected String name="";
  public String getName(){
      return name;
  }
  public void setName(String iname){
      this.name=iname;
  }  
  protected int putbasket=0;
  public int getPutbasket(){
      return putbasket;
  }
  public void setPutbasket(int iputbasket){
      this.putbasket=iputbasket;
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
  protected String description="";
  public String getDescription(){
      return description;
  }
  public void setDescription(String idescription){
      this.description=idescription;
  }   
  protected int flag=0;
  public int getFlag(){
      return flag;
  }
  public void setFlag(int iflag){
      this.flag=iflag;
  }  
  protected int synch=0;
  public int getSynch(){
      return synch;
  }
  public void setSynch(int isynch){
      this.synch=isynch;
  }
  protected String beginDate="";
  public String getBeginDate(){
      return beginDate;
  }
  public void setBeginDate(String ibeginDate){
      this.beginDate=ibeginDate;
  }  
  protected String endDate="";
  public String getEndDate(){
      return endDate;
  }
  public void setEndDate(String iendDate){
      this.endDate=iendDate;
  }      
  protected int validFlag=0;
  public int getValidFlag(){
      return validFlag;
  }
  public void setValidFlag(int ivalidFlag){
      this.validFlag=ivalidFlag;
  } 
}

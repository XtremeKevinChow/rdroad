package com.magic.crm.config.form;

public class SConfigKeys extends org.apache.struts.validator.ValidatorForm
implements java.io.Serializable {


protected String key = null;
protected String value = null;
protected String description = null;
protected int operatorID;
protected String operateDate;

public String getKey() {
 return key;
}
public void setKey(String key) {
 this.key = key;
}
public String getValue(){
return value;
}
public void setValue(String value) {
this.value = value;
}
public String getDescription(){
return description;
}
public void setDescription(String description) {
this.description = description;
}
public String getOperateDate(){
return operateDate;
}
public void setOperateDate(String operateDate) {
this.operateDate = operateDate;
}   
public int getOperatorID(){
return operatorID;
}
public void setOperatorID(int operatorID) {
this.operatorID = operatorID;
}  

public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
key = null;
value =null;
description = null;
operatorID=0;
operateDate=null;
}
}
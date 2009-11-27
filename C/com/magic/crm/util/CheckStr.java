/*
 * Created on 2007-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckStr {
   public static String checkStr(String str){
   	String new_str=str.replaceAll("\r\n","").replaceAll("\'","").replaceAll("\"","");
   	System.out.println(new_str);
   	return new_str;
   }
   /*
    * 
    * @authormagic
    *
    * TODO To change the template for this generated type comment go to
    * Window - Preferences - Java - Code Style - Code Templates
    * 1、通过邮编判断固定电话是否是上海以外地区
    * 2、通过电话前2位数判断是否是手机或固定电话
    */
   public static String checkPhone(String telno,String post_code){
	if (post_code == null || post_code.length() < 6) {
		return "";
	}
   	String result="";
   	telno=telno.replaceAll("-","").replaceAll(",","").replaceAll("\r\n","").replaceAll("\'","").replaceAll("\"","");//去掉电话号码中"-"符号。
   	if(!post_code.substring(0,2).equals("20")){//不是上海地区的电话
   		if(telno.substring(0,2).equals("13")||telno.substring(0,2).equals("15")){//不是上海地区的手机前面要加"0"
   			//检查手机合法性,长度不到13位的手机号码视为无效。
   			if(telno.length()==11){
   				telno="0"+telno;
   			}else{   				
   				telno="";
   			}
   		}else{
   			if(telno.length()>=10){//带区号的外地固定电话
   				if(!telno.substring(0,10).equals("0")){//区号前未加"0",要补"0"
   					telno="0"+telno; 					
   				}
   			}else{
   				telno="";//外地电话不加区号，默认为无效""
   			}
   		}
	
   	}else{
   		if(telno.substring(0,2).equals("13")||telno.substring(0,2).equals("15")){//手机
   			//检查手机合法性,长度不到13位的手机号码视为无效。
   			if(telno.length()!=11){
   				telno="";
   			}
	    }else{
	    	if(telno.length()!=8){//固定电话
	    		//上海的固定电话小于8位的默认为无效""
	    		telno="";
	    	}
	    }
   	}
   	result=telno;
   	
   	return result;
   }   
  
}

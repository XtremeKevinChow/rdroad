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
    * 1��ͨ���ʱ��жϹ̶��绰�Ƿ����Ϻ��������
    * 2��ͨ���绰ǰ2λ���ж��Ƿ����ֻ���̶��绰
    */
   public static String checkPhone(String telno,String post_code){
	if (post_code == null || post_code.length() < 6) {
		return "";
	}
   	String result="";
   	telno=telno.replaceAll("-","").replaceAll(",","").replaceAll("\r\n","").replaceAll("\'","").replaceAll("\"","");//ȥ���绰������"-"���š�
   	if(!post_code.substring(0,2).equals("20")){//�����Ϻ������ĵ绰
   		if(telno.substring(0,2).equals("13")||telno.substring(0,2).equals("15")){//�����Ϻ��������ֻ�ǰ��Ҫ��"0"
   			//����ֻ��Ϸ���,���Ȳ���13λ���ֻ�������Ϊ��Ч��
   			if(telno.length()==11){
   				telno="0"+telno;
   			}else{   				
   				telno="";
   			}
   		}else{
   			if(telno.length()>=10){//�����ŵ���ع̶��绰
   				if(!telno.substring(0,10).equals("0")){//����ǰδ��"0",Ҫ��"0"
   					telno="0"+telno; 					
   				}
   			}else{
   				telno="";//��ص绰�������ţ�Ĭ��Ϊ��Ч""
   			}
   		}
	
   	}else{
   		if(telno.substring(0,2).equals("13")||telno.substring(0,2).equals("15")){//�ֻ�
   			//����ֻ��Ϸ���,���Ȳ���13λ���ֻ�������Ϊ��Ч��
   			if(telno.length()!=11){
   				telno="";
   			}
	    }else{
	    	if(telno.length()!=8){//�̶��绰
	    		//�Ϻ��Ĺ̶��绰С��8λ��Ĭ��Ϊ��Ч""
	    		telno="";
	    	}
	    }
   	}
   	result=telno;
   	
   	return result;
   }   
  
}

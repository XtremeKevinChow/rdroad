/*
 * Created on 2005-3-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

import javax.servlet.http.*;

import com.magic.crm.common.Constants;
/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class Message {
	
	public static void setMessage(HttpServletRequest request,String strMessage) {
		setMessage(request,strMessage,false,null,null);
	}
	
	public static void setErrorMsg(HttpServletRequest request,String strMessage) {
		setMessage(request,strMessage,true,null,null);
	}
	public static void setMessage(HttpServletRequest request,String strMessage,String strBtnName,String strUrl) {
		setMessage(request,strMessage,true,strBtnName,strUrl);
	}
	
	public static void setMessage(HttpServletRequest request,String strMessage,boolean hasBtn,String strBtnName,String strUrl) {
		request.setAttribute(Constants.LOGIC_MESSAGE, strMessage);
		request.setAttribute("hasBtn",String.valueOf(hasBtn));
		request.setAttribute("btnName", strBtnName);
		request.setAttribute("url", strUrl);
	}
}

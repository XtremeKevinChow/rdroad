/*
 * Created on 2005-1-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.magic.crm.util.Constants;
import com.magic.crm.util.DBManager;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.MemberGIFTForm;
import com.magic.crm.member.dao.*;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebAction;
import com.magic.crm.common.WebForm;


/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public final class MemberQueryGIFTAction extends Action{
	 public MemberQueryGIFTAction() {
	  }
		public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
throws Exception{
	  			 Connection conn = null;			 
	  			 String condition="";
	  			MemberGIFTForm mf = (MemberGIFTForm) form;
	  			String isquery=request.getParameter("isquery");
	  			isquery=(isquery==null)?"":isquery;
	  			 try {	 

	  			 	 conn = DBManager.getConnection();

	  			 	MemberGIFTDAO memberGIFTDAO = new MemberGIFTDAO();
	  			 	MemberGIFT memberGIFT=new MemberGIFT();
	  			 	 /*
	  			 	  * ²éÑ¯Ìõ¼þ
	  			 	  * 
	  			 	  */
	  			 	
	  			 	String itemID=request.getParameter("item_ID");
	  			 	if(itemID!=null&&itemID.length()>0){	  			 
	  			 	condition=" and b.item_code ='"+itemID+"'";
	  			 	}
	  			 	Collection allmemberGIFT=new ArrayList();

	  			 	//if(isquery!=null&&isquery.equals("1")){

	  			 		allmemberGIFT = memberGIFTDAO.QueryMemberGIFT(conn,condition);
	  			 	//}
	  			 	  request.setAttribute("allmemberGIFT", allmemberGIFT);	  			 	
	  			 	return mapping.findForward("success");
	  			 } catch(SQLException se) {
	  			  	throw new ServletException(se);
	  			 } finally {
	  				 try {
	  					 conn.close();

	  				  } catch(SQLException sqe) {


	  				  }

	  			 }

	  }

	}

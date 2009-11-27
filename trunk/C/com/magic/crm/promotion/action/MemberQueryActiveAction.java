/*
 * Created on 2007-7-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.util.DBManager;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberQueryActiveAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
			 Connection conn = null;
			 String condition="";
			 String msc=request.getParameter("MSC");
			 String name=request.getParameter("name");
			 String method = request.getParameter("method");
			 
	  			 try { 			 	
	  			 	 conn = DBManager.getConnection();
	  			 	 MemberDAO memberDAO = new MemberDAO();
	  			 	 String tag=request.getParameter("tag");
	  			 	tag=(tag==null)?"":tag;
	  				if(msc!=null&&msc.length()>0){
	  					condition+=" and msc like '%"+msc+"%'";
	  				}
	  				if(name!=null&&name.length()>0){
	  					condition+=" and name like '%"+name+"%'";
	  				}
	  					  				
	  			 	 Collection memberMSC= null;
	  			 	 if (method != null && method.equals("2")) {
	  			 		memberMSC = memberDAO.QueryRecruit_Activity2(conn);
	  			 	 } else {
	  			 		memberMSC = memberDAO.QueryRecruit_Activity(conn,condition);
	  			 	 }
	  			 	 request.setAttribute("memberMSC",memberMSC);	
	  			 	 return mapping.findForward("success");
	  			 } catch(SQLException se) {

	  			  	throw new ServletException(se);

	  			 } finally {

	  				 try {

	  					 conn.close();

	  				  } catch(SQLException sqe) {

	  					  throw new ServletException(sqe);

	  				  }

	  			 }		
	}
}

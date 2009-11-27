/*
 * Created on 2005-2-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberInquiryDAO;
import com.magic.crm.member.entity.*;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InitInquiryCreateAction3 extends Action{
	public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
throws Exception{
   Connection conn = null;
   try{
   		conn = DBManager.getConnection();	
   		Member member=new Member();
   		MemberDAO memberDAO=new MemberDAO();
   		String memgetmemID=request.getParameter("MemgetmemID");
   		member=memberDAO.getMemberInfo(conn,memgetmemID);
   		
   		request.setAttribute("member",member);
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

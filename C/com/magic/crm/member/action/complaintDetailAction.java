/*
 * Created on 2007-3-1
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

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.magic.crm.member.dao.*;
import com.magic.crm.member.entity.Member;
import com.magic.crm.util.DBManager;
/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class complaintDetailAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		saveToken(request);
		Connection conn = null;
		try{
			conn = DBManager.getConnection();
			Mbr_complaintDAO mcDAO=new Mbr_complaintDAO();
			Member member=new Member();
			MemberDAO memberDAO=new MemberDAO();
			String card_id=request.getParameter("card_id");
			member = memberDAO.getMemberInfo(conn,card_id);
			request.setAttribute("member",member);
			String cmpt_id=request.getParameter("cmpt_id");
			String event_id=request.getParameter("event_id");
			
			String type=request.getParameter("type");
			/*
			 * 列出投诉记录
			 */
			Collection ColListComplaint=mcDAO.ListComplaint(conn," and a.cmpt_id="+cmpt_id,type);
			
			request.setAttribute("ColListComplaint",ColListComplaint);
			/*
			 * 列出投诉记录解决方法
			 */
			Collection ColListComplaintDeal=mcDAO.ListComplaintDeal(conn,Integer.parseInt(event_id));
			
			request.setAttribute("ColListComplaintDeal",ColListComplaintDeal);
			
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

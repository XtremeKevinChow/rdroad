/*
 * Created on 2005-4-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberInquiryDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberInquiry;
import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InquiryRefAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		MemberInquiry memberInquiry=new MemberInquiry();
			try{  				
					PropertyUtils.copyProperties(memberInquiry,form);
			   } catch(InvocationTargetException ite) {
					throw new ServletException(ite);
			   } 	
       User user=new User();
       HttpSession session = request.getSession();
       user = (User)session.getAttribute("user");   
       Collection colUser = null;
       Connection conn = null;
		try{
			conn = DBManager.getConnection();
			Member member=new Member();
			MemberDAO memberDAO = new MemberDAO();					
			MemberInquiryDAO memberInquiryDAO=new MemberInquiryDAO();
			Collection InquiryType = new ArrayList();
			Collection listInquiry = new ArrayList();
			/*
			 * 会员ID
			 */
			String id=request.getParameter("id");				
			/*
			 * 显示会员详细信息
			 */
			member =memberDAO.DetailMembers(conn,id);
			request.setAttribute("member",member);
			String deptid=request.getParameter("deptid");
			String status=request.getParameter("IS_SOLVE");

			InquiryType=memberInquiryDAO.getInquiryType(conn,deptid,status);
		 	request.setAttribute("InquiryType", InquiryType);
		 	request.setAttribute("listInquiry",listInquiry);
		 	String tag=request.getParameter("tag");
	 		/*
	 		 * tag=5 投诉增加页面
	 		 * tag=6 投诉查询页面
	 		 */		 	
		 	if(tag.equals("5")){
			return mapping.findForward("success");
		 	}else{
				UserDAO ud = new UserDAO();
				colUser=ud.findAllUsers(conn,2);
				request.setAttribute("colUser",colUser);

		 		return mapping.findForward("list");
		 	}
		 	
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

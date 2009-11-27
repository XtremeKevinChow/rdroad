/*
 * Created on 2005-2-6
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

import com.magic.crm.member.dao.MemberAddressDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberInquiryDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InquiryDetailAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		saveToken(request);
		Connection conn = null;
		try{
			conn = DBManager.getConnection();
			MemberInquiryDAO memberInquiryDAO=new MemberInquiryDAO();
			Member member=new Member();
			MemberDAO memberDAO=new MemberDAO();
			String card_id=request.getParameter("card_id");
			member = memberDAO.getMemberInfo(conn,card_id);
			request.setAttribute("member",member);
			/*
			 * 会员投诉第createid次投诉number
			 */
			String createid=request.getParameter("createid");
			/*
			 * 列出根节点投诉记录
			 */
			Collection ColListInquiry=memberInquiryDAO.ListInquiry(conn," and rootid=0 and a.createid="+createid);
			request.setAttribute("ColListInquiry",ColListInquiry);
			/*
			 * 列出根节点下投诉记录解决方法
			 */
			Collection ColListInquirySolve=memberInquiryDAO.ListInquiry(conn," and rootid>0 and a.createid="+createid);
			request.setAttribute("ColListInquirySolve",ColListInquirySolve);
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

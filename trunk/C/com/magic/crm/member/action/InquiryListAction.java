/*
 * Created on 2005-2-5
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
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberInquiryDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.common.CommonPageUtil;
import java.util.HashMap;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InquiryListAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
        User user= new User();
        HttpSession session = request.getSession();
        user = (User)session.getAttribute("user"); 
		   Connection conn = null;
		   try{
		   		conn = DBManager.getConnection();		   		
				MemberInquiryDAO memberInquiryDAO=new MemberInquiryDAO();
	            MemberDAO memberDAO = new MemberDAO();
	            Member member = new Member();				
				int id=0;
				String card_id="";
				
	            if (request.getMethod().equalsIgnoreCase("POST")) {
	                /** �����callcenterϵͳ����sessin��ȡ����Ա��Ϣ�����û����ʾ��¼������� * */
	                String iscallcenter = request.getParameter("iscallcenter");
	                if (iscallcenter != null && iscallcenter.equals("1")) {
	                    CallCenterHander hander = new CallCenterHander(request
	                            .getSession());
	                    if (hander.isOnService()) {
	                        Member mb = hander.getServicedMember();
	                        card_id = String.valueOf(mb.getCARD_ID());
	                        id=mb.getID();
	                        if (card_id == null) {
	                            card_id = "";
	                        } else {
	                            if (card_id.length() > 0) {
	                                member = memberDAO.getMemberInfo(conn, card_id.trim());
	                                card_id = String.valueOf(member.getID());
	                                id=member.getID();
	                            }
	                        }
	                        request.setAttribute("iscallcenter", iscallcenter);
	                    } else {
	                        ControlledError ctlErr = new ControlledError();
	                        ctlErr.setErrorTitle("��������");
	                        ctlErr.setErrorBody("û�з��������������");
	                        request
	                                .setAttribute(
	                                        com.magic.crm.util.Constants.ERROR_KEY,
	                                        ctlErr);
	                        return mapping.findForward("controlledError");

	                    }
	                }
	            }				
               
				/*
				 * ��ʾ�ڵ�ROODID=0,ת����REF_DEPARTMENT=user.getDEPARTMENT_ID(),Ͷ���˺���memberid=card_id
				 */
				String condition="";
				if(user.getDEPARTMENT_ID()==2){
					condition=" and a.rootid=0 and a.memberid='"+id+"'   order by a.event_id desc";
				}else{
					condition=" and a.rootid=0 and a.memberid='"+id+"' and a.REF_DEPARTMENT="+user.getDEPARTMENT_ID()+"  order by a.event_id desc";
				}
				
				Collection listInquiry=memberInquiryDAO.ListInquiry(conn,condition);
				request.setAttribute("listInquiry",listInquiry);
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

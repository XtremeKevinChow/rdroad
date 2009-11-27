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
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.member.dao.*;
import com.magic.crm.member.entity.Member;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class complaintListAction extends Action{
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
		   		Mbr_complaintDAO mcDAO=new Mbr_complaintDAO();
	            MemberDAO memberDAO = new MemberDAO();
	            Member member = new Member();				
				int id=0;
				String card_id="";
				
	            if (request.getMethod().equalsIgnoreCase("POST")) {
	                /** 如果是callcenter系统，从sessin中取出会员信息，如果没有提示登录服务对象 * */
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
	                        ctlErr.setErrorTitle("操作错误");
	                        ctlErr.setErrorBody("没有服务对象，请接入服务");
	                        request
	                                .setAttribute(
	                                        com.magic.crm.util.Constants.ERROR_KEY,
	                                        ctlErr);
	                        return mapping.findForward("controlledError");

	                    }
	                }
	            }else{
	            	card_id=request.getParameter("card_id");
                    if (card_id == null) {
                        card_id = "";
                    } else {
                        if (card_id.length() > 0) {
                            member = memberDAO.getMemberInfo(conn, card_id.trim());
                            card_id = String.valueOf(member.getID());
                            id=member.getID();
                        }
                     }	            	
	            }
              
				/*
				 * 某个会员的部门投诉记录
				 */
				String condition="";
				String type=request.getParameter("type");
			
					condition="  and a.mbr_id='"+id+"' and c.type=0";
					//if(user.getDEPARTMENT_ID()!=2){
					//condition+=" and b.dept_id="+user.getDEPARTMENT_ID();
					//}
					condition+="  order by a.cmpt_id desc";
				
				String condition_zixun="  and a.mbr_id='"+id+"' and c.type=1  order by a.cmpt_id desc";	
				/*
				Collection listComplaint=mcDAO.ListComplaint(conn,condition,type);
				request.setAttribute("listComplaint",listComplaint);
				Collection listComplaintZixun=mcDAO.ListComplaint(conn,condition_zixun,type);//咨询
				request.setAttribute("listComplaintZixun",listComplaintZixun);
				*/				
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

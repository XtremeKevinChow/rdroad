/*
 * Created on 2005-2-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
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

import com.magic.crm.member.dao.MemberInquiryDAO;
import com.magic.crm.member.entity.MemberInquiry;
import com.magic.crm.member.form.MemberForm;
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
public class InquirySolveAction extends Action{
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
		   
		
		/*
		 * 令牌机制判断数据重复提交
		 */
	    String token_request = request.getParameter("org.apache.struts.taglib.html.TOKEN");	        
	    if(!isTokenValid(request)){
		    ControlledError ctlErr = new ControlledError();	    		
			    ctlErr.setErrorTitle("操作错误");	    		
			    ctlErr.setErrorBody("重复提交数据");	    		
			    request.setAttribute(Constants.ERROR_KEY, ctlErr);
			    saveToken(request);
			    return mapping.findForward("controlledError");	           	           	          
	    }else{
	        	resetToken(request);
	    }  	
        User user=new User();
        HttpSession session = request.getSession();
        user = (User)session.getAttribute("user"); 
        String is_query=request.getParameter("is_query");//1 新增 0 查询
		Connection conn = null;
		String condition="";
		String inquiry_type=request.getParameter("INQUIRY_TYPE2");
		try{
			conn = DBManager.getConnection();
			
			MemberInquiryDAO memberInquiryDAO=new MemberInquiryDAO();
			/*
			 * 增加会员投诉记录解决方法,false:表示会员新增投诉记录,true表示新增解决方法
			 */
			memberInquiry.setSOLVE_PERSON(user.getId());
			memberInquiry.setStatus(1);
			memberInquiry.setINQUIRY_TYPE(Integer.parseInt(inquiry_type));
			memberInquiry.setROOTID(1);
			memberInquiryDAO.insertInquiry(conn,memberInquiry,true);
			/*
			 * 会员投诉第createid次投诉number
			 */
			String createid=request.getParameter("CREATEID");
			/*
			 * 修改投诉记录状态为1(有关人员已经完成解决)
			 */
			memberInquiryDAO.updateStatus(conn,1,createid); 

			String card_id=request.getParameter("card_id");
			String id=request.getParameter("id");
			/*
			 * 显示节点ROODID=0,转向部门REF_DEPARTMENT=user.getDEPARTMENT_ID(),投诉人号码memberid=card_id
			 */
			if(user.getDEPARTMENT_ID()==2){
				condition=" and a.rootid=0 and a.memberid='"+id+"'   order by a.event_id desc";
				
			}else{
				condition=" and a.rootid=0 and a.memberid='"+id+"' and a.REF_DEPARTMENT="+user.getDEPARTMENT_ID()+" order by a.event_id desc";
				
			}						
			Collection listInquiry=memberInquiryDAO.ListInquiry(conn,condition);
			request.setAttribute("listInquiry",listInquiry);
			saveToken(request);
			if(is_query.equals("0")){
				return mapping.findForward("is_query");
			}
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

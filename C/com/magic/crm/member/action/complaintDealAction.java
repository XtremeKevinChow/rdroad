/*
 * Created on 2007-3-1
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

import com.magic.crm.member.dao.*;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.*;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.ChangeCoding;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class complaintDealAction  extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		
		Mbr_ComplaintForm mcf=new Mbr_ComplaintForm();
		try{  				
				PropertyUtils.copyProperties(mcf,form);
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
       
		Connection conn = null;
		String condition="";
		String event_id=request.getParameter("event_id");
		String cmt_status=request.getParameter("cmt_status");
		String is_fenye=request.getParameter("is_fenye");
		is_fenye=(is_fenye==null)?"":is_fenye;
		
		String go_path="";
		
		if(is_fenye.equals("1")){
			go_path="is_fenye";
		}else{
			go_path="success";
		}
	
		try{
			conn = DBManager.getConnection();
			
			Mbr_complaintDAO mcDAO=new Mbr_complaintDAO();
			/*
			 * 增加会员投诉记录解决方法,
			 */
			mcf.setCreator(Integer.parseInt(user.getId()));
			mcf.setEvent_id(Integer.parseInt(event_id));	
			mcf.setCmpt_status(Integer.parseInt(cmt_status));
			mcf.setCmpt_content(ChangeCoding.unescape(ChangeCoding.toUtf8String(mcf.getCmpt_content())));
			
			Mbr_complaintDAO.insertDeal(conn,mcf);

			String card_id=request.getParameter("card_id");
			String id=request.getParameter("id");
			String type=request.getParameter("type");

			if(type.equals("0")){//投诉
				condition="  and a.mbr_id='"+id+"' and b.dept_id="+user.getDEPARTMENT_ID()+"  order by a.cmpt_id desc";
			}else{
				condition="  and a.mbr_id='"+id+"' order by a.cmpt_id desc";	
			}
			if(is_fenye.equals("1")){
				go_path="is_fenye";
				Collection listComplaint=mcDAO.ListComplaint(conn,condition,type);
				request.setAttribute("listComplaint",listComplaint);				
			}else{
				go_path="success";
			}
		
			saveToken(request);
			return mapping.findForward(go_path);
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

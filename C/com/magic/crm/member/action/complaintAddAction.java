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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.member.dao.*;
import com.magic.crm.member.form.*;
import com.magic.crm.member.entity.*;
import com.magic.crm.user.entity.User;
/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class complaintAddAction extends Action{
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
		 * ���ƻ����ж������ظ��ύ
		 */
       String token_request = request.getParameter("org.apache.struts.taglib.html.TOKEN");	        
       if(!isTokenValid(request)){
   	    ControlledError ctlErr = new ControlledError();	    		
   		    ctlErr.setErrorTitle("��������");	    		
   		    ctlErr.setErrorBody("�ظ��ύ����");	    		
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
		try{
			conn = DBManager.getConnection();
			Member member=new Member();
			MemberDAO memberDAO = new MemberDAO();					
			Mbr_complaintDAO mcDAO=new Mbr_complaintDAO();
			
			/*
			 * ��ԱID
			 */
			String id=request.getParameter("id");				
			/*
			 * ��ʾ��Ա��ϸ��Ϣ
			 */
			member =memberDAO.DetailMembers(conn,id);
			request.setAttribute("member",member);
			/*
			 * ��ʾ�����¼
			 */
			/**
			 * commented by user 2008-03-20
			 * ȥ������Ĵ���
			 */
			 /*String condition="'"+member.getCARD_ID()+"'";
		 	 Collection memberBuy=memberDAO.getMemberBuy(conn,condition);
		 	 request.setAttribute("memberBuy",memberBuy);*/
		 	 
			/*
			 * ���ӻ�ԱͶ�߼�¼
			 */		 
		 	mcf.setMbr_id(Integer.parseInt(id));
		 	mcf.setCreator(Integer.parseInt(user.getId()));
		 	
		 	Mbr_complaintDAO.insert(conn,mcf);
		 	
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

package com.magic.crm.user.action;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.sql.*;

import javax.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.util.Constants;
import com.magic.crm.util.DBManager;
import com.magic.crm.user.form.*;
import com.magic.crm.user.entity.*;
import com.magic.crm.user.dao.*;
import com.magic.crm.util.*;

/**
 * <p>Title: FE China Credit System</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FE China</p>
 * @author Liao Yu Hong
 * @version 1.0
 */

public final class FindUserAction extends Action {

  public FindUserAction() {

  }

  public ActionForward execute(ActionMapping mapping,
							 ActionForm form,
							 HttpServletRequest request,
							 HttpServletResponse response)
		 throws Exception{

  			 Connection conn = null;

  			 try {
  			 	



  			 	 conn = DBManager.getConnection();

  			 	 UserDAO userDao = new UserDAO();

  			 	 Collection allUsers = userDao.findAllUsers(conn);

  			 	 request.setAttribute(Constants.ALL_USERS, allUsers);

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
  			 	 
//***********************************************************************  			 	
  				/*
  			 	UserPage   certificateDAO  = new UserPage() ; 
  				System.out.println("11111111111");
  				int offset = 0;   //翻页时的起始记录所在游标 
  				int length = Constants.PAGE_LENGTH; 
  				String pageOffset = request.getParameter("pager.offset"); 
  				if (pageOffset == null || pageOffset.equals("")) { 
  					System.out.println("22222222222");
  					offset = 0; 
  				} else { 
  					System.out.println("333333333333");
  					offset = Integer.parseInt(pageOffset); 
  				} 
  				System.out.println("4444444444");
  				List certificateList = certificateDAO .findCertificateList (offset,length) ; 
  				System.out.println("5555555555");
  				int size = certificateDAO.getRows(); //  取得总记录数 
  				System.out.println("size is *********"+size);
  				String url = request.getContextPath()+"/"+mapping.getPath()+".do"; 
  				System.out.println("url is *********"+url);
  				String pagerHeader = Pager.generate(offset, size, length, url); //分页处理 
  				System.out.println("pagerHeader is *********"+pagerHeader);
  					request.setAttribute ("pager", pagerHeader) ;
  					Pager s= new Pager();
  					
  					System.out.println("666666666");
  					request.setAttribute ("list", certificateList) ; 
  					return mapping.findForward("success");
  			 } catch (Exception e) { 
  					e.printStackTrace(); 
  				return mapping.findForward ("controlledError") ; 
  			} 
 */
  			
  			 	/*
                ActionForward myforward = null ; 
                String myaction = mapping.getParameter () ; 

	                if (isCancelled (request)) 
	                { 
	                	System.out.println("11111111111");
	                        return mapping.findForward ("controlledError") ; 
	                } 
	                if ("".equalsIgnoreCase (myaction)) 
	                { 
	                	System.out.println("222222222222");
	                        myforward = mapping.findForward ("controlledError") ; 
	                } 
	                else if("LIST".equalsIgnoreCase (myaction)) 
	                { 
	                	System.out.println("333333333333");
	                        myforward = performList (mapping, form, request, response) ; 
	                } 
	                else 
	                { 
	                	System.out.println("4444444444444");
	                        myforward = mapping.findForward ("controlledError") ; 
	                } 
                
  			 	
  			 	return myforward ; 
  			 	*/
  			 	


 
  			}
//*****************************************************************
	/*
  private ActionForward performList(ActionMapping mapping,
            ActionForm actionForm,
            HttpServletRequest request,
            HttpServletResponse response)
		throws Exception{
		try { 
			 
			
			UserPage   certificateDAO  = new UserPage() ; 
			
			int offset = 0;   //翻页时的起始记录所在游标 
			int length = Constants.PAGE_LENGTH; 
			String pageOffset = request.getParameter("pager.offset"); 
			if (pageOffset == null || pageOffset.equals("")) { 
			offset = 0; 
			} else { 
			offset = Integer.parseInt(pageOffset); 
			} 
			List certificateList = certificateDAO .findCertificateList (offset,length) ; 
			int size = certificateDAO.getRows(); //  取得总记录数 
			String url = request.getContextPath()+"/"+mapping.getPath()+".do"; 
			String pagerHeader = Pager.generate(offset, size, length, url); //分页处理 

			request.setAttribute ("pager", pagerHeader) ; 
			request.setAttribute ("list", certificateList) ; 
		} catch (Exception e) { 
			e.printStackTrace(); 
			return mapping.findForward ("error") ; 
		} 
			return mapping.findForward ("success") ; 
		} 
			
			  */
  
}
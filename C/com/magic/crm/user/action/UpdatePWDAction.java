/*
 * Created on 2005-4-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.user.action;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UpdatePWDAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
       User user= new User();
       HttpSession session = request.getSession();
       user = (User)session.getAttribute("user"); 
       UserDAO userdao=new UserDAO();
       String tag=request.getParameter("tag");
       String password=request.getParameter("password"); 
       user.setPWD(password);
       if(tag==null){
       	tag="";
       }
		   Connection conn = null;   
		   try{
		   	
		   		conn = DBManager.getConnection();		   		
		   		if(tag.length()>0){

	  		    	    ControlledError ctlErr = new ControlledError();	    		
		    		    ctlErr.setErrorTitle("操作成功提示");	    		
		    		    ctlErr.setErrorBody("密码修改已经成功!");	    		
		    		    request.setAttribute(Constants.ERROR_KEY, ctlErr);
		    		    userdao.modifyPWD(conn,user);
		    		    return mapping.findForward("controlledError");		   			
		   		}
					return mapping.findForward("success");

			} catch(SQLException se) {		
			  	throw new ServletException(se);		
			} finally {		
					 try {		
						  conn.close();		
					     } catch(SQLException sqe) {		
					     	sqe.printStackTrace();
				         }
			
		   }
	}
}

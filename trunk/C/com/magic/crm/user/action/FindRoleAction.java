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

public final class FindRoleAction extends Action {

  public FindRoleAction() {
  }
  public ActionForward execute(ActionMapping mapping,
  														 ActionForm form,
  														 HttpServletRequest request,
  														 HttpServletResponse response)
  			 throws Exception{

  			 Connection conn = null;

  			 try {
 
  			 	 //DataSource dts = getDataSource(request);

  			 	 conn = DBManager.getConnection();

  			 	 RoleDAO roleDao = new RoleDAO();

  			 	 Role role=new Role();

  			 	 Collection allRoles = roleDao.findAllRoles(conn);
  			 	 request.setAttribute(Constants.ALL_ROLES, allRoles);
  			 	
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
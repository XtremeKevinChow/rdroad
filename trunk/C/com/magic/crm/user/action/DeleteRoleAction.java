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

public final class DeleteRoleAction extends Action{

  public DeleteRoleAction() {
  }

  public ActionForward execute(ActionMapping mapping,
  														 ActionForm form,
  														 HttpServletRequest request,
  														 HttpServletResponse response)
  				throws Exception{

  				RoleForm rf = (RoleForm) form;

  				Role role = new Role();

  				PropertyUtils.copyProperties(role,rf);

  				Connection conn=null;

                                System.out.println("role delete 000000");

                                
  				try {

	  					//DataSource dts = getDataSource(request);

	  				  conn = DBManager.getConnection();

	  				  //check if the role is in use

                                          conn.setAutoCommit(false);

	  				  UserRoleDAO userRoleDao = new UserRoleDAO();

	  				  Collection userRoleCol = userRoleDao.find(conn, role);

                                             System.out.println("role delete 111111111");

	  				  if (userRoleCol != null && userRoleCol.size() > 0){

                                               System.out.println("role delete 222222222");

	  				  		ControlledError ctlErr = new ControlledError();

	  				  		ctlErr.setErrorTitle("删除角色错误");

	  				  		ctlErr.setErrorBody("角色正在使用中，不能删除");

                                                        request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,ctlErr);

	  				  		return mapping.findForward("controlledError");

	  				  }

	  					RoleDAO dao = new RoleDAO();

	  					RoleRightDAO roleRightDao = new RoleRightDAO();

	  					roleRightDao.delete(conn, role);

                                                   System.out.println("role delete 3333333");

	  					dao.delete(conn, role);

                                                   System.out.println("role delete 4444444");

                                                conn.commit();

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
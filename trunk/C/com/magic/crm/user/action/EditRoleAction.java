package com.magic.crm.user.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.sql.*;
import java.math.*;
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

public final class EditRoleAction extends Action{

  public EditRoleAction() {
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

  			 try {

  					//DataSource dts = getDataSource(request);

  				  conn = DBManager.getConnection();

  				  conn.setAutoCommit(false);

  					RoleDAO dao = new RoleDAO();

  					dao.update(conn, role);

  					RoleRightDAO roleRightDao = new RoleRightDAO();

	  				roleRightDao.delete(conn, role);

	  				String RoleID = role.getRoleID();
	  				
	  				
	  				String[] rights = request.getParameterValues("rights");

	  				RoleRight roleRight = new RoleRight();

	  				roleRight.setRoleID(RoleID);

                                        if (rights !=null) {

                                          for (int i = 0, len = rights.length; i < len; i++){

                                           
                                             // System.out.println("rights[i] is "+rights[i]);
                                            roleRightDao.insert(conn,Integer.parseInt(rights[i]), roleRight);

                                          }
                                        }

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
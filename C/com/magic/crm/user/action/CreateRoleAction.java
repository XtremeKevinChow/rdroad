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

public final class CreateRoleAction extends Action{
  public CreateRoleAction() {
  }

	public ActionForward execute(ActionMapping mapping,
															 ActionForm form,
															 HttpServletRequest request,
															 HttpServletResponse response)
					throws Exception{

					RoleForm rf = (RoleForm)form;

					Role role = new Role();
					
				  try{
                    
				  	PropertyUtils.copyProperties(role,rf);

				  }catch(InvocationTargetException ite){

				  	throw new ServletException(ite);

				  }
				  

                                       Connection conn = null;

					try{
						
						//DataSource dts = getDataSource(request);

						conn = DBManager.getConnection();

                                                if (isRoleExists(conn,role)) {
                                                  ControlledError ctlErr = new ControlledError();

                                                  ctlErr.setErrorTitle("新增角色错误");

                                                  ctlErr.setErrorBody("已有同名角色存在");

                                                  request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,ctlErr);

                                                  return mapping.findForward("controlledError");
                                                }

						conn.setAutoCommit(false);

						RoleDAO dao = new RoleDAO();


						String roleId = dao.insert(conn,role).toString();

	  				BigDecimal[] rights = rf.getRights();

	  				RoleRight roleRight = new RoleRight();

	  				roleRight.setRoleID(roleId);

	  				RoleRightDAO roleRightDao = new RoleRightDAO();

                                        if (rights !=null) {

                                          for(int i = 0, len = rights.length; i < len; i++){
                                            roleRight.setRightId(rights[i]);                                            
                                            roleRightDao.insert(conn,Integer.parseInt(rights[i].toString()),roleRight);
                                            System.out.println("roleRightDao.insert");
                                          }
                                        }

  					conn.commit();

						System.out.println("source is ok?");
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


         public boolean isRoleExists(Connection conn, Role role) throws SQLException {

             RoleDAO roleDao = new RoleDAO();

             if (roleDao.findRoleByName(conn,role) != null) {

                 return true;

             }

             return false;

         }

}
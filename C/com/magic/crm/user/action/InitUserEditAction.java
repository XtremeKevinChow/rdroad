package com.magic.crm.user.action;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
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

public final class InitUserEditAction extends Action {

  public InitUserEditAction() {
  }

  public ActionForward execute(ActionMapping mapping,
															 ActionForm form,
															 HttpServletRequest request,
															 HttpServletResponse response)
					throws Exception{

				 Connection conn = null;

				 try{
                                    
				 		UserForm uf = (UserForm) form;
                    

				 		User user = new User();

				 		String[] roles = null;

				 		user.setId(uf.getId());
				 		
				 		UserDAO userDao = new UserDAO();

				 		UserRoleDAO userRoleDao = new UserRoleDAO();

				 		//DataSource dts = getDataSource(request);

				 		conn = DBManager.getConnection();

				 		user = userDao.find(conn, user);
				 		request.setAttribute("user",user);
				 		Collection roleCol = userRoleDao.find(conn, user);

				 		//roles = (Role[])roleCol.toArray(new Role[0]);

                                                 Collection roleIdCol = new ArrayList();

                                                if(roleCol != null) {

                                                  Iterator roleIt = roleCol.iterator();

                                                  UserRole role = null;

                                                  while (roleIt.hasNext()) {

                                                    role = (UserRole) roleIt.next();

                                                    roleIdCol.add(role.getRoleId());

                                                  }

                                                }
                                                
                                                roles = (String[]) roleIdCol.toArray(new String[0]);
                                                System.out.println("initUserEdit22222");

				 		PropertyUtils.copyProperties(uf, user);

				 		uf.setRoles(roles);


				 		//find all roles

				 		RoleDAO roleDao = new RoleDAO();

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
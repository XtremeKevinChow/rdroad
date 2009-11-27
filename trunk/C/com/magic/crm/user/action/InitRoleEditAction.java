package com.magic.crm.user.action;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.math.*;
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

public final class InitRoleEditAction extends Action {

  public InitRoleEditAction() {
  }

	public ActionForward execute(ActionMapping mapping,
															 ActionForm form,
															 HttpServletRequest request,
															 HttpServletResponse response)
					throws Exception{

				 Connection conn = null;

				 try{

				 		RoleForm rf = (RoleForm) form;

				 		Role role = new Role();

				 		BigDecimal[] rightss = null;
				 		
				 		role.setRoleID(rf.getRoleID());
				 		
				 		RoleDAO roleDao = new RoleDAO();

				 		RoleRightDAO roleRightDao = new RoleRightDAO();


				 		conn = DBManager.getConnection();

				 		role = roleDao.findRole(conn, role);

				 		Collection rightCol = roleRightDao.find(conn, role);

                                Collection rightIdCol = new ArrayList();
                                
                                
                                Vector v= new Vector();
                                if (rightCol != null) {

                                  Iterator rightIt = rightCol.iterator();
                                  
                                  RoleRight right = new RoleRight();

                                  while (rightIt.hasNext()) {

                                    right = (RoleRight) rightIt.next();
                                    //System.out.println("right.getRightId() is "+right.getRightId());
                                    rightIdCol.add(right.getRightId());
                                   v.addElement(right.getRightId());
                                  }
                                }
                                                
                        rightss = (BigDecimal[]) rightIdCol.toArray(new BigDecimal[0]);


				 		PropertyUtils.copyProperties(rf, role);

				 		rf.setRights(rightss);

				 		RightDAO rightDao = new RightDAO();
	  			 	Collection allRights = rightDao.findAllRights(conn);	
	  			 	//request.setAttribute("rightId",rightss);
	  			 	request.setAttribute("rightId",v);
	  			 	request.setAttribute(Constants.ALL_RIGHTS,allRights);
	  			   
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
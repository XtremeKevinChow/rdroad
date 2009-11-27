/*
 * Created on 2005-3-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.user.action;

import java.sql.SQLException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.user.dao.PathRightDAO;

import com.magic.crm.util.DBManager;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PathAddOkAction extends Action {


	  public ActionForward execute(ActionMapping mapping,
	  														 ActionForm form,
	  														 HttpServletRequest request,
	  														 HttpServletResponse response)
	  			 throws Exception{

	  	Connection conn=null;
	  	PathRightDAO prd=new PathRightDAO();
	  	try{

				//DataSource dts = getDataSource(request);

				conn = DBManager.getConnection();
				String path=request.getParameter("path");
				String pathName=request.getParameter("pathName");
				String r=request.getParameter("radio");
				int radio=Integer.parseInt(r);
				String rid=request.getParameter("rightid");
				int parentid=Integer.parseInt(rid);

				
				prd.pathInsert(conn,path,pathName,parentid,radio);
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

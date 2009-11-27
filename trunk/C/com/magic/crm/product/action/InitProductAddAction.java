//Source file: C:\\j2sdk1.4\\lib\\com\\fechina\\ccms\\message\\action\\SendMsgAction.java

package com.magic.crm.product.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.product.dao.Product2DAO;
import com.magic.crm.product.dao.TaxDAO;
import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.product.entity.Product;
import com.magic.crm.product.form.Product2Form;
import com.magic.crm.util.DBManager;



/**
 * Implementation of <strong>Action</strong> that populates an instance of
 * <code>SendMsgActionAction</code>
 * @author Kevin zhou
 * @version 1.0
 */
public final class InitProductAddAction
    extends Action {

  /**
   *     Process the specified HTTP request, and create the corresponding HTTP
   *       response (or forward to another web component that will create it).
       *       Return an <code>ActionForward</code> instance describing where and how
       *       control should be forwarded, or <code>null</code> if the response has
   *       already been completed.
   *
   *       @param mapping The ActionMapping used to select this instance
   *       @param form The optional ActionForm bean for this request (if any)
   *       @param request The HTTP request we are processing
   *       @param response The HTTP response we are creating
   *
   *       @return Action to forward to
   *       @throws java.lang.Exception
   * @exception Exception if an input/output error or servlet exception occurs
   * @roseuid 3ED6F16602B4
   */
  private static Logger log = Logger.getLogger("InitProductAddAction.class");

  private static ServletContext context;

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
      Exception {
  	Product product = new Product();
    
    Connection conn = null;
    Collection col = null;
    Collection colUser = null;
    try {
		 	
    	Product2Form pf = (Product2Form) form;
    
	    conn = DBManager.getConnection();
	    //TaxDAO td = new TaxDAO();
	    //取所有产品税率
	    //col = td.findAll(conn);
	    UserDAO ud = new UserDAO();
	    colUser = ud.findAllUsers2(conn);
	    
	    //String item_code = Product2DAO.generateCode(conn);
	    //pf.setItem_code(item_code);
	    //log.info(item_code);
    } catch(SQLException e) {

		  	throw new ServletException(e);

		 } finally {

			 try { conn.close();} catch(Exception e) {}

		 }
    
    //request.setAttribute("alltax", col);
    //request.setAttribute("allUser",colUser);
    
    return mapping.findForward("success");
  }

  
}
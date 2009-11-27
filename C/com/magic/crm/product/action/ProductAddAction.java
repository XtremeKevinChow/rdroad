//Source file: C:\\j2sdk1.4\\lib\\com\\fechina\\ccms\\message\\action\\SendMsgAction.java

package com.magic.crm.product.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;

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
import com.magic.crm.product.dao.ProductBaseDAO;

import com.magic.crm.product.dao.ProductSKUDAO;
import com.magic.crm.product.entity.Product;
import com.magic.crm.product.form.Product2Form;
import com.magic.crm.product.form.ProductSKUForm;
import com.magic.crm.exception.JException;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;


/**
 * Implementation of <strong>Action</strong> that populates an instance of
 * <code>SendMsgActionAction</code>
 * @author Kevin zhou
 * @version 1.0
 */
public final class ProductAddAction
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
  private static Logger log = Logger.getLogger("ProductAddAction.class");

  //private static ServletContext context;

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
      Exception {
	  	ProductSKUForm pf = (ProductSKUForm)form;
	  	Product product = new Product();
	    Connection conn = null;
	
	    User user = (User) request.getSession().getAttribute("user");
	    
	    String req_colors = request.getParameter("selcolors");
	    String[] colors = req_colors.trim().split(",");
	    if(colors== null) {
	    	colors = new String[1];
	    	colors[0] = "0";
	    } else {
	    	HashSet tmp = new HashSet();
	    	for (int i=0;i<colors.length;i++) {
	    		tmp.add(colors[i]);
	    	}
	    	colors = (String[])tmp.toArray(new String[0]);
	    }
	    
	    String req_sizes = request.getParameter("selsizes");
	    String[] sizes = req_sizes.trim().split(",");
	    if(sizes == null) {
	    	sizes = new String[1];
	    	sizes[0] = "0";
	    } else {
	    	HashSet tmp = new HashSet();
	    	for (int i=0;i<sizes.length;i++) {
	    		tmp.add(sizes[i]);
	    	}
	    	sizes = (String[])tmp.toArray(new String[0]);
	    }
	    
	    try {
	      conn = DBManager.getConnection();
	      String error_msg = "";
	      for(int i=0;i<colors.length;i++) {
	    	  if(!ProductBaseDAO.existsColor(conn,colors[i])) {
	    		  if(error_msg.equals("")) {
	    			  error_msg = colors[i];
	    		  } else {
	    			  error_msg = error_msg + "," + colors[i] ;
	    		  }
	    	  }
	     }
	      if(!"".equals(error_msg)) {
	    	  error_msg = "不存在的颜色代码:" + error_msg;
	      }
	      
	      String error_msg2 = "";
	      for(int i=0;i<sizes.length;i++) {
	    	  if(!ProductBaseDAO.existsSize(conn,sizes[i])) {
	    		  if(error_msg2.equals("")) {
	    			  error_msg2 = sizes[i];
	    		  } else {
	    			  error_msg2 = error_msg2 + "," + sizes[i] ;
	    		  }
	    	  }
	     }
	      if(!"".equals(error_msg2)) {
	    	  error_msg2 = "不支持的尺寸代码" + error_msg2;
	      }
	      
	      // 颜色或尺寸有问题则返回输入页面
	      if(!"".equals(error_msg)||!"".equals(error_msg2)) {
	    	  request.setAttribute("LOGIC MESSAGE", error_msg + error_msg2);
	    	  return mapping.findForward("input");
	      }
	      
	      // 否则产生产品和对应的sku
	      conn.setAutoCommit(false);
	      if(pf.getItem_code()==null||"".equals(pf.getItem_code().trim())) {
	    	  pf.setItem_code(Product2DAO.generateCode(conn));
	      }
	      
	      int ret = Product2DAO.insert(conn, pf);
	      
	      ret = ProductSKUDAO.insertByItem(conn, pf, colors, sizes);
	      conn.commit();
	      
	      ArrayList skus = ProductSKUDAO.list(conn,pf.getItem_code());
	      pf.setItems(skus);
	      return mapping.findForward("success");	  			           					  				
		  		      
	    }
	    catch (Exception se) {
	      log.error(se);
	      conn.rollback();
	      Message.setErrorMsg(request, se.getMessage());
	      return mapping.findForward("error");
	    }
	    finally {
	    	try{
	      	conn.close();
	      }
	      catch (SQLException sqe) {}
	
	    }
	    
  }              

  
}
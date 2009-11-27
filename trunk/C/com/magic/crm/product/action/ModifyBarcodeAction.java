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

import com.magic.crm.product.Constants;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.dao.TaxDAO;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.exception.JException;
import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.util.DBManager;

/**
 * Implementation of <strong>Action</strong> that populates an instance of
 * <code>SendMsgActionAction</code>
 * @author Kevin zhou
 * @version 1.0
 */
public final class ModifyBarcodeAction extends Action {

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
	private static Logger log = Logger.getLogger("ModifyBarcodeAction.class");

	private static ServletContext context;

public ActionForward execute(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
      Exception {
  	ProductDAO pd = new ProductDAO();
    Connection conn = null;
    ProductForm pf = (ProductForm)form;
    Collection col = null;
    Collection colUser = null;
    //�޸Ĳ�Ʒ֮ǰ��ȡ����Ʒ�Ļ�����Ϣ���û����ݸ���Ϣ���б�Ҫ�޸�
    if (request.getMethod().equals("GET")) {
    	 try {
		conn = DBManager.getConnection();
		pf = pd.findByPrimaryKey(conn,pf.getItemID());
		
		request.setAttribute(Constants.PRODUCT_FORM,pf);
		
		return mapping.findForward("init");
    	    }
    	    catch (SQLException se) {
    	      throw new JException("�޸Ĳ�Ʒ��Ϣ����");
    	    }
    	    finally {
    	      try {
    	        conn.close();
    	      }
    	      catch (SQLException sqe) {
    	        throw new ServletException(sqe);
    	      }

    	    }		
	}
    
    //�û��ύ��Ʒ��Ϣ��ϵͳ����Ʒ��Ϣ���µ����ݿ�    
    try {
      conn = DBManager.getConnection();
      
      pd.updateBarcode(conn,pf);
      
      request.setAttribute("successMessage", "�޸Ĳ�Ʒ������Ϣ�ɹ���");
      
      return mapping.findForward("success");
    
      
    }
    catch (SQLException se) {
      throw new JException("�޸Ĳ�Ʒ��Ϣ����");
    }
    finally {
      try {
        conn.close();
      }
      catch (SQLException sqe) {
        throw new ServletException(sqe);
      }

    }
    
  }}
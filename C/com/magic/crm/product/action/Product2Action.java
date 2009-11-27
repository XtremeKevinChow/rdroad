package com.magic.crm.product.action;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.ConfigDAO;
import com.magic.crm.util.DBManager;
import com.magic.crm.product.dao.*;
import com.magic.crm.product.form.*;

public class Product2Action extends DispatchAction {
    
	private Logger log = Logger.getLogger(Product2Action.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		Product2Form fm = (Product2Form) form;
		try {
			conn = DBManager.getConnection();
			
			String item_code = request.getParameter("item_code");
			
			//ArrayList skus = ProductSKUDAO.list(conn,item_code);
			//fm.setItems(skus);
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("list");
	}
	
	
	public ActionForward delete(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		Product2Form fm = (Product2Form) form;
		try {
			conn = DBManager.getConnection();
			String item_code = request.getParameter("itemcode");
			Product2DAO.delete(conn, item_code);
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("delete");
	}
	
}

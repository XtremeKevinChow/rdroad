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

public class Product2SetDtlAction extends DispatchAction {
    
	private Logger log = Logger.getLogger(Product2SetDtlAction.class);
	
	/**
	 * list sku
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
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			
			String item_code = request.getParameter("item_code");
			
			ArrayList skus = ProductSKUDAO.list(conn,item_code);
			fm.setItems(skus);
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("list");
	}
	
	/**
	 * list sku
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			
			String set_id= request.getParameter("set_id");
			Product2SetDtlDAO.delete(conn, Integer.parseInt(set_id));
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
	
	public ActionForward save(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String op_type = request.getParameter("op_type");
		if("insert".equals(op_type)) {
			return add(mapping,form,request,response);
		} else {
			return modify(mapping,form,request,response);
		}
		
	}

	
	public ActionForward add(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			Product2SetDtlDAO.insert(conn, fm);
			
			fm.setItem_code(fm.getSet_item_code());
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	public ActionForward modify(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			Product2SetDtlDAO.update(conn, fm);
			
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	
	public ActionForward init(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		
		String set_item_code = request.getParameter("set_item_code");
		fm.setSet_item_code(set_item_code);
		
		fm.setOp_type("insert");
		try {
			conn = DBManager.getConnection();
			
			ArrayList colors = ProductBaseDAO.listColor(conn);
			fm.setColors(colors);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
}


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

import com.magic.crm.product.dao.Product2DAO;
import com.magic.crm.product.dao.ProductBaseDAO;
import com.magic.crm.product.dao.ProductCategoryDAO;
import com.magic.crm.product.form.Product2Form;
import com.magic.crm.product.form.Product2SetForm;
import com.magic.crm.util.DBManager;

public class Product2QryAction extends DispatchAction {
	Logger log = Logger.getLogger(Product2QryAction.class);
	
	
	public ActionForward init(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2Form fm = (Product2Form) form;
		try {
			conn = DBManager.getConnection();
			
			ArrayList cates = ProductCategoryDAO.listMainCates(conn);
			fm.setCates(cates);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	public ActionForward init4order(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2Form fm = (Product2Form) form;
		try {
			conn = DBManager.getConnection();
			
			ArrayList cates = ProductCategoryDAO.listMainCates(conn);
			fm.setCates(cates);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("product4order");
	}
	
	
	public ActionForward query(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2Form fm = (Product2Form) form;
		try {
			conn = DBManager.getConnection();
			
			ArrayList items = Product2DAO.query(conn, fm);
			fm.setItems(items);
			
			ArrayList cates = ProductCategoryDAO.listMainCates(conn);
			fm.setCates(cates);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	public ActionForward query4order(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2Form fm = (Product2Form) form;
		try {
			conn = DBManager.getConnection();
			
			ArrayList items = Product2DAO.query(conn, fm);
			fm.setItems(items);
			
			ArrayList cates = ProductCategoryDAO.listMainCates(conn);
			fm.setCates(cates);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("product4order");
	}
	
	public ActionForward query4stock(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2Form fm = (Product2Form) form;
		try {
			conn = DBManager.getConnection();
			
			ArrayList items = Product2DAO.queryStock(conn, fm.getQry_item_code());
			fm.setItems(items);
			
			ArrayList cates = ProductCategoryDAO.listMainCates(conn);
			fm.setCates(cates);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("product4stock");
	}
}

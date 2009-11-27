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
import com.magic.crm.product.dao.ProductSKUDAO;
import com.magic.crm.product.form.ProductCategoryForm;
import com.magic.crm.product.form.ProductSKUForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;

public class ProductCategoryAction extends DispatchAction {
	private Logger log = Logger.getLogger(ProductCategoryAction.class);
	
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
		ProductCategoryForm fm = (ProductCategoryForm) form;
		try {
			conn = DBManager.getConnection();
			
			ArrayList items = new ArrayList();
			
			if( request.getMethod().equals("POST")) {
				ProductCategoryDAO.listItemCategory(conn, fm);
			}
			request.setAttribute("list", items);
			
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
		ProductCategoryForm fm = (ProductCategoryForm) form;
		try {
			conn = DBManager.getConnection();
			
			//String sku_id = request.getParameter("sku_id");
			ProductCategoryDAO.deleteItemCategory(conn, fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	public ActionForward view(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductCategoryForm fm = (ProductCategoryForm) form;
		try {
			conn = DBManager.getConnection();
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("view");
	}
	
	public ActionForward add(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductCategoryForm fm = (ProductCategoryForm) form;
		try {
			String item_code = request.getParameter("item_code");
			fm.setItem_code(item_code);
			String catalogID = request.getParameter("catalogID");
			fm.setCatalogID(catalogID);
			
			conn = DBManager.getConnection();
			ProductCategoryDAO.addItemCategory(conn, fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	/*public ActionForward modify(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			ProductSKUDAO.modify(conn, fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	
	public ActionForward modinit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			fm.setColors(ProductBaseDAO.listColor(conn));
			
			String item_code = fm.getItem_code();
			int sku_id = fm.getSku_id();
			fm.setSizes(ProductBaseDAO.listSize(conn, item_code));
			
			ProductSKUDAO.findByPK(conn,fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("modinit");
	}*/
}

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
import com.magic.crm.util.Message;
import com.magic.crm.product.dao.*;
import com.magic.crm.product.form.*;

public class ProductSKUAction extends DispatchAction {
    
	private Logger log = Logger.getLogger(ProductSKUAction.class);
	
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
			
			ArrayList skus = new ArrayList();
			if(fm.getBarcode()!=null&&!fm.getBarcode().equals("")) {
				skus = ProductSKUDAO.listByBarcode(conn,fm.getBarcode());
			} else if(fm.getItem_code()!=null&&!fm.getItem_code().equals("")) {
				skus = ProductSKUDAO.list(conn,fm.getItem_code());
			}
			
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
			
			//String sku_id = request.getParameter("sku_id");
			ProductSKUDAO.delete(conn, fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	public ActionForward addinit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			fm.setColors(ProductBaseDAO.listColor(conn));
			
			String item_code = fm.getItem_code();
			
			fm.setSizes(ProductBaseDAO.listSize(conn, item_code));
			Product2DAO.findByItemCode(conn,fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("addinit");
	}
	
	public ActionForward add(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			ProductSKUDAO.insert(conn, fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("addover");
	}
	
	public ActionForward modify(ActionMapping mapping,
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
	
	public ActionForward updateAll(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			ProductSKUDAO.updateAll(conn, fm);
			
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
	}
	
	public ActionForward addgiftinit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			//fm.setColors(ProductBaseDAO.listColor(conn));
			
			//String item_code = fm.getItem_code();
			
			//fm.setSizes(ProductBaseDAO.listSize(conn, item_code));
			//Product2DAO.findByItemCode(conn,fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("addgift");
	}
	
	public ActionForward addGift(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			//fm.setColors(ProductBaseDAO.listColor(conn));
			
			ProductSKUDAO.insertGift(conn, fm);
			conn.commit();
			conn.setAutoCommit(true);
		} catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		Message.setMessage(request, "赠品新增成功");
		return mapping.findForward("message");
	}
	
	public ActionForward editgiftinit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		ProductSKUForm fm = (ProductSKUForm) form;
		try {
			conn = DBManager.getConnection();
			//fm.setColors(ProductBaseDAO.listColor(conn));
			
			//String item_code = fm.getItem_code();
			
			//fm.setSizes(ProductBaseDAO.listSize(conn, item_code));
			//Product2DAO.findByItemCode(conn,fm);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("editgift");
	}
}

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

public class Product2SetAction extends DispatchAction {
    
	private Logger log = Logger.getLogger(Product2SetAction.class);
	
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
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			
			String item_code = request.getParameter("item_code");
			
			ArrayList skus = ProductSKUDAO.list(conn,item_code);
			fm.setItems(skus);
			
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("list");
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
			conn.setAutoCommit(false);
			Product2DAO.insert(conn, fm);
			
			//////////////////////////////////////////////
			Product2DAO.findByItemCode(conn, fm);
			ArrayList parts = Product2DAO.listItemPart(conn, fm);
			fm.setItems(parts);
			fm.setOp_type("update");
			
			ArrayList colors = ProductBaseDAO.listColor(conn);
			fm.setColors(colors);
			conn.commit();
			conn.setAutoCommit(true);
		} catch(Exception e) {
			conn.rollback();
			log.error(e);
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
	
	public ActionForward modify(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			Product2DAO.update(conn, fm);
			
			/////////////////////////////////////////////////////
			Product2DAO.findByItemCode(conn, fm);
			ArrayList parts = Product2DAO.listItemPart(conn, fm);
			fm.setItems(parts);
			fm.setOp_type("update");
			
			ArrayList colors = ProductBaseDAO.listColor(conn);
			fm.setColors(colors);
			
		} catch(Exception e) {
			log.error(e);
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
	
	public ActionForward delete(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			Product2DAO.delete(conn, fm.getSet_item_code());
			
			
			
		} catch(Exception e) {
			log.error(e);
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("list");
	}
	
	
	public ActionForward init(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			
			//String item_code = Product2DAO.generateSetCode(conn);
			//fm.setItem_code(item_code);
			
			fm.setMax_count(8);
			fm.setOp_type("insert");
			
			ArrayList colors = ProductBaseDAO.listColor(conn);
			fm.setColors(colors);
			
			
		} catch(Exception e) {
			log.error(e);
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
	
	public ActionForward updateinit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			
			////////////////////////////////////////
			Product2DAO.findByItemCode(conn, fm);
			ArrayList parts = Product2DAO.listItemPart(conn, fm);
			fm.setItems(parts);
			
			fm.setOp_type("update");
			
		} catch(Exception e) {
			log.error(e);
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
	
	public ActionForward addItem(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			
			Product2SetDtlDAO.insert(conn,fm);
			
			//////////////////////////////////////////////
			Product2DAO.findByItemCode(conn, fm);
			ArrayList parts = Product2DAO.listItemPart(conn, fm);
			fm.setItems(parts);
			fm.setOp_type("update");
			
			ArrayList colors = ProductBaseDAO.listColor(conn);
			fm.setColors(colors);
			
		} catch(Exception e) {
			log.error(e);
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
	
	
	public ActionForward modifyItem(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			
			String set_id = request.getParameter("set_id");
			fm.setSet_id(Integer.parseInt(set_id));
			String index = request.getParameter("index");
			
			String std_price = request.getParameterValues("part_item_std_price")[Integer.parseInt(index)];
			String sale_price = request.getParameterValues("part_item_sale_price")[Integer.parseInt(index)];
			String vip_price = request.getParameterValues("part_item_vip_price")[Integer.parseInt(index)];
			String web_price = request.getParameterValues("part_item_web_price")[Integer.parseInt(index)];
			
			fm.setStandard_price(Double.parseDouble(std_price));
			fm.setSale_price(Double.parseDouble(sale_price));
			fm.setVip_price(Double.parseDouble(vip_price));
			fm.setWeb_price(Double.parseDouble(web_price));
			Product2SetDtlDAO.updatePartPrice(conn,fm);
			
			//////////////////////////////////////////////
			Product2DAO.findByItemCode(conn, fm);
			ArrayList parts = Product2DAO.listItemPart(conn, fm);
			fm.setItems(parts);
			fm.setOp_type("update");
			
			ArrayList colors = ProductBaseDAO.listColor(conn);
			fm.setColors(colors);
			
		} catch(Exception e) {
			log.error(e);
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
	
	public ActionForward deleteItem(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
			
		Product2SetForm fm = (Product2SetForm) form;
		try {
			conn = DBManager.getConnection();
			
			String set_id= request.getParameter("set_id");
			Product2SetDtlDAO.delete(conn, Integer.parseInt(set_id));
			
			
//////////////////////////////////////////////
			Product2DAO.findByItemCode(conn, fm);
			ArrayList parts = Product2DAO.listItemPart(conn, fm);
			fm.setItems(parts);
			fm.setOp_type("update");
			
			ArrayList colors = ProductBaseDAO.listColor(conn);
			fm.setColors(colors);
			
		} catch(Exception e) {
			log.error(e);
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("init");
	}
	
}

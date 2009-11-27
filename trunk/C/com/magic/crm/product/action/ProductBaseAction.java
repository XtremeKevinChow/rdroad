/*
 * Created on 2008-7-1 by zhux
 * ProductBaseAction.java
 * TODO 
 */
package com.magic.crm.product.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.product.dao.ProductBaseDAO;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;


public class ProductBaseAction extends DispatchAction {
private Logger log = Logger.getLogger(ProductBaseAction.class);
	
	/**
	 * add color
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addColor(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			String color_code=request.getParameter("color_code");
			String color_name=request.getParameter("color_name");
			
			ProductBaseDAO.insertColor(conn, color_code, color_name);
		
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	/**
	 * add color
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateColor(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			
			String color_code=request.getParameter("color_code");
			String color_name=request.getParameter("color_name");
			String old_color_code = request.getParameter("old_color_code");
			
			ProductBaseDAO.updateColor(conn, color_code, color_name,old_color_code);
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	/**
	 * add color
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteColor(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			
			String color_code=request.getParameter("color_code");
			
			ProductBaseDAO.deleteColor(conn, color_code);
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	public ActionForward addSize(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			
			String size_code=request.getParameter("size_code");
			String type_id_str=request.getParameter("type_id");
			int type_id = Integer.parseInt(type_id_str);
			ProductBaseDAO.insertSize(conn, size_code, type_id);
		
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	/**
	 * add color
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSize(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			
			String size_code=request.getParameter("size_code");
			String type_id_str=request.getParameter("type_id");
			int type_id = Integer.parseInt(type_id_str);
			
			String old_size_code=request.getParameter("old_size_code");
			String old_type_id_str=request.getParameter("old_type_id");
			int old_type_id = Integer.parseInt(old_type_id_str);
			ProductBaseDAO.updateSize(conn, size_code, type_id,old_size_code,old_type_id);
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	/**
	 * add color
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSize(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			
			String size_code=request.getParameter("size_code");
			String type_id_str=request.getParameter("type_id");
			int type_id = Integer.parseInt(type_id_str);
			ProductBaseDAO.deleteSize(conn, size_code, type_id);
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	public ActionForward addSize2(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			
			String size_code=request.getParameter("size_code");
			String type_id_str=request.getParameter("type_id");
			int type_id = Integer.parseInt(type_id_str);
			ProductBaseDAO.insertSize2(conn, size_code, type_id);
		
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	/**
	 * add color
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSize2(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			
			String size_code=request.getParameter("size_code");
			String type_id_str=request.getParameter("type_id");
			int type_id = Integer.parseInt(type_id_str);
			
			String old_size_code=request.getParameter("old_size_code");
			String old_type_id_str=request.getParameter("old_type_id");
			int old_type_id = Integer.parseInt(type_id_str);
			ProductBaseDAO.updateSize2(conn, size_code, type_id,old_size_code,old_type_id);
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	/**
	 * add color
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSize2(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		User user = (User)request.getSession().getAttribute("user");	
		
		try {
			conn = DBManager.getConnection();
			
			String size_code=request.getParameter("size_code");
			String type_id_str=request.getParameter("type_id");
			int type_id = Integer.parseInt(type_id_str);
			ProductBaseDAO.deleteSize2(conn, size_code, type_id);
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
	
	
	
}

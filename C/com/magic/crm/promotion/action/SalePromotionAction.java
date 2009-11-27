/*
 * Created on 2005-7-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.actions.*;
import org.apache.struts.action.*;

import org.apache.log4j.*;
import com.magic.crm.promotion.form.*;
import com.magic.crm.util.*;
import java.sql.*;
import com.magic.crm.promotion.dao.*;
import java.util.*;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SalePromotionAction extends DispatchAction {
	
	Logger log = Logger.getLogger(SalePromotionAction.class);
	
	/**
	 * 促销活动查询界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		SpGiftForm data = (SpGiftForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			//1 查询信息
			ArrayList ret = SpGiftDAO.query(conn,data.getSel_msc());
			data.setSps(ret);
			
			//2 select box显示
			ret = SpGiftDAO.listMsc(conn);
			data.setMsc_codes(ret);
			
			//3 免发送费信息
			data.setFree_delivery_require(SpFreeDeliveryDAO.getFreeDelivery(conn,data.getSel_msc()));
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("query"); 
	}
	
	/**
	 * 促销活动新增界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		SpGiftForm data = (SpGiftForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			//ArrayList ret = SpGiftDAO.query(conn,data.getSel_msc());
			//data.setSps(ret);
			
			ArrayList ret = SpGiftDAO.listMsc(conn);
			data.setMsc_codes(ret);
			ret = SpGiftDAO.listPrdGroup(conn);
			data.setGroups(ret);
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("add"); 
	}	
	/**
	 * 促销活动新增提交
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		SpGiftForm data = (SpGiftForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			SpGiftDAO.insert(conn,data);
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("reQry"); 
	}	
	
	
	/**
	 * 促销活动修改界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modInit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		SpGiftForm data = (SpGiftForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			ArrayList ret = SpGiftDAO.listMsc(conn);
			data.setMsc_codes(ret);
			ret = SpGiftDAO.listPrdGroup(conn);
			data.setGroups(ret);
			
			String id = request.getParameter("id");
			SpGiftForm src = SpGiftDAO.findByPK(conn,Long.parseLong(id));
			data.copy(src);
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("modify"); 
	}	
	
	/**
	 * 促销活动修改提交
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		SpGiftForm data = (SpGiftForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			SpGiftDAO.update(conn,data);
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("reQry"); 
	}	
	
	/**
	 * 促销活动产品取消
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancel(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		SpGiftForm data = (SpGiftForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			String id = request.getParameter("id");
			data.setId(Long.parseLong(id));
			SpGiftDAO.cancel(conn,data);
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("reQry"); 
	}	
	
	/**
	 * 促销活动设置免发送费信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setFreeDelivery(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		SpGiftForm data = (SpGiftForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			String p1 = data.getFree_delivery_require();
			SpFreeDeliveryDAO.setFreeDelivery(conn,data.getSel_msc(),Double.parseDouble(p1));
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("reQry"); 
	}	
	
}

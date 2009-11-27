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
public class FreeDeliveryFeeAction extends DispatchAction {
	
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
	public ActionForward list(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		FreeDeliveryFeeForm data = (FreeDeliveryFeeForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			//1 查询信息
			ArrayList ret = FreeDeliveryFeeDAO.list(conn);
			request.setAttribute("list", ret);
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("list"); 
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
	public ActionForward view(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		FreeDeliveryFeeForm data = (FreeDeliveryFeeForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			
			//FreeDeliveryFeeDAO.insert(conn,data);
			//String id = request.getParameter("free_id");
			//if(id!=null&&!"".equals(id)) {
				if(data.getID()>0) {
					FreeDeliveryFeeDAO.findByPk(conn, data);
				}
			//}
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("view"); 
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
		FreeDeliveryFeeForm data = (FreeDeliveryFeeForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			
			FreeDeliveryFeeDAO.insert(conn,data);
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("success"); 
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
		FreeDeliveryFeeForm data = (FreeDeliveryFeeForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			
			FreeDeliveryFeeDAO.update(conn,data);
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("success"); 
	}	
	
	/**
	 * 更改状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStatus(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		FreeDeliveryFeeForm data = (FreeDeliveryFeeForm)form;
		
		Connection conn = DBManager.getConnection();
		try {
			
			FreeDeliveryFeeDAO.updateStatus(conn,data);
			
		} catch(Exception e) {
			log.error("exception:",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {};
		}
		
		return mapping.findForward("success"); 
	}	
	
}

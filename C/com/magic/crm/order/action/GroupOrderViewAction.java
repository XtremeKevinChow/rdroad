/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import org.apache.struts.action.*;
import org.apache.log4j.*;
import javax.servlet.http.*;

import com.magic.crm.common.DBOperation;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.util.*;
import java.sql.*;

import com.magic.crm.order.form.*;
/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class GroupOrderViewAction extends Action {
	private Logger log = Logger.getLogger(GroupOrderAddAction.class);
	
	public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		OrderForm order = (OrderForm)form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DBOperation db = new DBOperation(conn);
			//order.setOrderId(Integer.parseInt(request.getParameter("orderId")));
			OrderDAO.getGroupOrderHeadersInfo(db,order);
			OrderDAO.getGroupOrderLinesInfo(db, order);
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("success");
	}
}

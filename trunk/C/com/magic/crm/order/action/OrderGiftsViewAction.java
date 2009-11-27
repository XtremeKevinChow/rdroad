/*
 * Created on 2006-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.order.dao.OrderGiftsDAO;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.util.DBManager;

/**
 * @author user
 *
 * TODO 99read 
 */
public class OrderGiftsViewAction extends Action {
	public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		OrderForm orderForm = (OrderForm) form;
		
		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			OrderGiftsDAO ogdao = new OrderGiftsDAO();
			Collection coll = ogdao.getRecordsByOrderId(conn, orderForm.getOrderId());
			request.setAttribute("list", coll);
			
		} catch(Exception e) {
			
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("success");
	}	
}

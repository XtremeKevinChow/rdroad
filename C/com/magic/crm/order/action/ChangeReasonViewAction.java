package com.magic.crm.order.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.util.DBManager;

public class ChangeReasonViewAction extends Action  {
	private Logger log = Logger.getLogger(GroupOrderAddAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			order.setOrderId(Integer.parseInt(request.getParameter("orderId")));
			OrderDAO.getChangeReason(conn,order);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("success");
	}
}

package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.shippingnotice.dao.ShippingNoticeDAO;
import com.magic.crm.shippingnotice.form.ShippingNoticeForm;
import com.magic.crm.util.DBManager;

public class ConsoleSnQueryAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nextUrl = "success";
		Collection coll = new ArrayList();
		ShippingNoticeForm snForm = (ShippingNoticeForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			coll = ShippingNoticeDAO.list(conn, snForm);
			request.setAttribute("list", coll);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(nextUrl);
	}
}

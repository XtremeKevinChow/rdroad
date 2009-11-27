package com.magic.crm.shippingnotice.action;

import java.sql.Connection;
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

/**
 * 合并发货单列表查询
 * @author user
 *
 */
public class PackageShippingNoticesQueryAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ShippingNoticeForm snForm = (ShippingNoticeForm) form;
		Connection conn = null;
			try {
				conn = DBManager.getConnection();
				Collection list = ShippingNoticeDAO.getPackageSnByLot(conn, snForm);
				request.setAttribute("list", list);
			} catch (Exception e) {
				
				throw e;
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		return mapping.findForward("success");
	}

}

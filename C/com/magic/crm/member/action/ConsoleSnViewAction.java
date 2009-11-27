package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.shippingnotice.dao.ShippingNoticeDAO;
import com.magic.crm.shippingnotice.entity.ShippingNoticeMst;
import com.magic.crm.shippingnotice.form.ShippingNoticeForm;
import com.magic.crm.util.DBManager;

public class ConsoleSnViewAction extends Action {
	public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		ShippingNoticeForm snForm = (ShippingNoticeForm) form;
		ShippingNoticeMst mst = new ShippingNoticeMst();
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			long sn_id = Long.parseLong(request.getParameter("sn_id"));
			
			mst = ShippingNoticeDAO.getShippingNoticeByPK(conn,sn_id);
			mst.setItems(ShippingNoticeDAO.getDetailByMst(conn,snForm.getMst()));
			
			request.setAttribute("snMst", mst);
		
		} catch(Exception e) {
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("success");
	}	
}

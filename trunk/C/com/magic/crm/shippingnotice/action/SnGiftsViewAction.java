/*
 * Created on 2006-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
 * @author user
 *
 * TODO 99read 
 */
public class SnGiftsViewAction extends Action {
	public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		ShippingNoticeForm snForm = (ShippingNoticeForm) form;
		
		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			Collection coll = ShippingNoticeDAO.getGiftsBySnId(conn, snForm.getSn_id());
			request.setAttribute("list", coll);
			
		} catch(Exception e) {
			
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("success");
	}	

}

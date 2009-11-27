/*
 * Created on 2005-5-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.shippingnotice.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.shippingnotice.entity.ShippingNoticeMst;
import com.magic.crm.shippingnotice.dao.ShippingNoticeDAO;
import com.magic.crm.shippingnotice.form.ShippingNoticeForm;
import com.magic.crm.util.DBManager;

/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class ShippingNoticeViewAction extends Action {
	private static Logger log = Logger.getLogger(ShippingNoticeViewAction.class);
	public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		ShippingNoticeForm snForm = (ShippingNoticeForm) form;
		ShippingNoticeMst mst = new ShippingNoticeMst();
		Connection conn = null;
		String queryKey = request.getParameter("queryKey");
		try {
			conn = DBManager.getConnection();
	
			if(queryKey != null && queryKey.equals("findBySNNum")){
				String sn_id = request.getParameter("sn_id");
				//snForm.setMst(ShippingNoticeDAO.findShippingNoticeByNO(conn,sn_id));
				//snForm.setDtls(ShippingNoticeDAO.getDetailByMst(conn,snForm.getMst()));
				mst = ShippingNoticeDAO.findShippingNoticeByNO(conn,sn_id);
				mst.setItems(ShippingNoticeDAO.getDetailByMst(conn,snForm.getMst()));
			}
			else{
				long sn_id = Long.parseLong(request.getParameter("sn_id"));
				//snForm.setMst(ShippingNoticeDAO.getShippingNoticeByPK(conn,sn_id));
				//snForm.setDtls(ShippingNoticeDAO.getDetailByMst(conn,snForm.getMst()));
				mst = ShippingNoticeDAO.getShippingNoticeByPK(conn,sn_id);
				mst.setItems(ShippingNoticeDAO.getDetailByMst(conn,snForm.getMst()));
			}
			request.setAttribute("snMst", mst);
		
		} catch(Exception e) {
			
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("success");
	}	

}

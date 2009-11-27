package com.magic.crm.shippingnotice.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.entity.Member;
import com.magic.crm.shippingnotice.dao.ShippingNoticeDAO;
import com.magic.crm.shippingnotice.form.ShippingNoticeForm;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.DBManager;
import java.util.Collection;
import java.util.ArrayList;

/**
 * @author Administrator
 * 
 *  
 */
public class ShippingNoticeQryAction extends Action {
	private static Logger log = Logger.getLogger(ShippingNoticeQryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ShippingNoticeForm snForm = (ShippingNoticeForm) form;
		
		String iscallcenter = request.getParameter("iscallcenter");
		Collection coll = new ArrayList();
		String strQrySnCode = snForm.getStrQrySnCode();
		String strQryOrdCode = snForm.getStrQryOrdCode();
		String strQryMbrCode = snForm.getStrQryMbrCode();
		String strQryMbrName = snForm.getStrQryMbrName();
		String strQryTelephone = snForm.getStrQryTelephone();
		String strQryShippingNumber = snForm.getStrQryShippingNumber();
		Connection conn = null;
		/** 如果客服已经接入服务，则从session中取出会员信息，当客服在ToolBar中点击“查询”则显示当前会员的发货单信息 * */
		if (iscallcenter != null && iscallcenter.equals("1")) {
			CallCenterHander hander = new CallCenterHander(request.getSession());
			if (hander.isOnService()) {
				Member mb = hander.getServicedMember();
				snForm.setStrQryMbrCode(mb.getCARD_ID());
				snForm.setStrQryMbrName(mb.getNAME());
				
				try {
					conn = DBManager.getConnection();
					//snForm.setMsts(ShippingNoticeDAO.list(conn, snForm));
					coll = ShippingNoticeDAO.list(conn, snForm);
					
				} catch (Exception e) {
					log.error(e);
					throw e;
				} finally {
					try {
						conn.close();
					} catch (Exception e) {
					}
				}
			}
		}else if (!strQrySnCode.equals("") || !strQryOrdCode.equals("")
		
				|| !strQryMbrCode.equals("") || !strQryMbrName.equals("")
				|| !strQryTelephone.equals("")|| !strQryShippingNumber.equals("")) {

			try {
				conn = DBManager.getConnection();
				//snForm.setMsts(ShippingNoticeDAO.list(conn, snForm));
				coll = ShippingNoticeDAO.list(conn, snForm);
				
			} catch (Exception e) {
				log.error(e);
				throw e;
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}

		}
		request.setAttribute("list", coll);
		return mapping.findForward("success");
	}

}

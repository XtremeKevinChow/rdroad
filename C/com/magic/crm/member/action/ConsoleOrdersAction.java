package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.pager.Pager;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.order.action.GroupOrderAddAction;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.common.pager.PagerForm;

public class ConsoleOrdersAction extends Action {
	private Logger log = Logger.getLogger(GroupOrderAddAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		PagerForm pageData = (PagerForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			CallCenterHander hander = new CallCenterHander(request.getSession());
			Member mb = hander.getServicedMember();
			if (hander.isOnService()) {
				int size = MemberDAO.countRecordsByCondition(conn, mb.getID());//计算记录总数
                Pager page = new Pager(pageData.getOffset(), size);//生成page对象
                page.setLength(10);
                pageData.setPager(page);
				Collection memberBuy = MemberDAO.getOrdersByCurrentMember(conn, mb.getID(), pageData);
	            request.setAttribute("memberBuy", memberBuy);
			} else {
				 ControlledError ctlErr = new ControlledError();
	             ctlErr.setErrorTitle("操作错误");
	             ctlErr.setErrorBody("没有服务对象，请接入服务");
	             request.setAttribute(Constants.ERROR_KEY, ctlErr);
	             return mapping.findForward("controlledError");
			}
			
            return mapping.findForward("success");
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

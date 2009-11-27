package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.pager.Pager;
import com.magic.crm.common.pager.PagerForm;
import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.entity.Member;

import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;

public class ConsoleEmoneyAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PagerForm pageData = (PagerForm) form;
		Connection conn = null;

		Collection coll = new ArrayList();

		/** 如果是callcenter系统，从sessin中取出会员信息，如果session中没有会员信息，则提示登录服务对象 * */
		String iscallcenter = request.getParameter("iscallcenter");

		try {
			conn = DBManager.getConnection();

			if (iscallcenter != null && iscallcenter.equals("1")) {
				CallCenterHander hander = new CallCenterHander(request
						.getSession());
				Member mb = hander.getServicedMember();
				if (hander.isOnService()) {
					int size = MemberaddMoneyDAO.countEmoneyRec(conn,
							mb.getID());// 计算记录总数
					Pager page = new Pager(pageData.getOffset(), size);// 生成page对象
					page.setLength(10);
					pageData.setPager(page);
					coll = MemberaddMoneyDAO.getEmoneyHistoryByMember(conn, mb
							.getID(), pageData);
					request.setAttribute("pageModel", coll);
				} else {
					ControlledError ctlErr = new ControlledError();
					ctlErr.setErrorTitle("操作错误");
					ctlErr.setErrorBody("没有服务对象，请接入服务");
					request.setAttribute(Constants.ERROR_KEY, ctlErr);
					return mapping.findForward("controlledError");
				}
			}
			return mapping.findForward("success");

		} catch (SQLException se) {

			throw new ServletException(se);

		} finally {

			try {

				conn.close();

			} catch (SQLException sqe) {

				throw new ServletException(sqe);

			}

		}

	}
}

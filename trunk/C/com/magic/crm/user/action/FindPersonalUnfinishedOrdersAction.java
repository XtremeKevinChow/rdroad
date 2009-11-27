package com.magic.crm.user.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.Collection;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.order.dao.OrderDAO;

public class FindPersonalUnfinishedOrdersAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//从session中得到登陆用户
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
		Connection conn = null;
		try {
			
			
			conn = DBManager.getConnection();
			Collection coll = OrderDAO.findUnfinishedOrderByPerson(conn, Integer.parseInt(user.getId()));
			
			request.setAttribute("orders", coll);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward("success");
	}
}

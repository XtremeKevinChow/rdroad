package com.magic.crm.user.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.user.form.GradeForm;
import com.magic.crm.util.DBManager;

public class FindPersonalGradeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GradeForm pageData = (GradeForm)form;
		Connection conn = null;
		try {
			Collection coll = new java.util.ArrayList();
			if (request.getMethod().equals("POST")) {
				conn = DBManager.getConnection();
				coll = UserDAO.findPersonalValue(conn, pageData);
			} 
			request.setAttribute("list", coll);
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

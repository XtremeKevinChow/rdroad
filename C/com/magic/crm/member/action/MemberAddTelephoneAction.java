package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Constants;

public class MemberAddTelephoneAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		Member member = new Member();
		PropertyUtils.copyProperties(member, form);
		String telType = request.getParameter("telType");
		try {
			conn = DBManager.getConnection();
			//session中的电话不为空
			String currTel = (String)request.getSession().getAttribute(Constants.CURRENT_COME_PHONE);
			if (currTel != null && !currTel.equals("")) {
			
				if (telType.equals("F")) {
					MemberDAO.updateFamilyPhone(conn, member);
				}
				if (telType.equals("C")) {
					MemberDAO.updateCompanyPhone(conn, member);
				}
				if (telType.equals("T")) {
					MemberDAO.updateTelephone(conn, member);
				}
			
			}
		} catch (Exception e) {
			throw new Exception("添加电话错误");
		} finally {
			if (conn != null)
				conn.close();
		}
		return mapping.findForward("success");
	}
}

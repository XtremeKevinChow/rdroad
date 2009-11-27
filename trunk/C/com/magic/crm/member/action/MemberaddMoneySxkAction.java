/**
 * MemberaddMoneySxkAction.java
 * 2008-3-31
 * 下午12:15:03
 * user
 * MemberaddMoneySxkAction
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.form.MemberaddMoneyForm;
import com.magic.crm.util.DBManager;

/**
 * @author user
 *
 */
public class MemberaddMoneySxkAction extends DispatchAction {
	/**
	 * 查询录入的书香卡
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			Collection list = moneyDao.findSxkRecordsByCondition(conn, myForm);
			request.setAttribute("list", list);
			request.setAttribute("memberaddMoneyForm", myForm);
		}catch(Exception e) {
			throw new ServletException();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward("query");
	}
	
}

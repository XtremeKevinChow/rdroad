/*
 * Created on 2005-3-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.entity.MembeMoneyHistory;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.member.form.MemberaddMoneyForm;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.common.CommonPageUtil;
import java.util.HashMap;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberQueryFinanceMoneyAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm fm = (MemberaddMoneyForm)form;
		
		HttpSession session = request.getSession();
		User user = new User();
		user = (User) session.getAttribute("user");
		Connection conn = null;
		MemberaddMoneyDAO memberaddMoneyDAO = new MemberaddMoneyDAO();
		MemberaddMoney memberaddMoney = new MemberaddMoney();
		MembeMoneyHistory info = new MembeMoneyHistory();
		CommonPageUtil pageModel = new CommonPageUtil();
		Collection paymethod = new ArrayList();
		Collection memberMoneyExecl = new ArrayList();

		try {
			conn = DBManager.getConnection();
			
			fm.setPayments(new MemberaddMoneyDAO().QueryPayMethod(conn));
		/*
		 * 取到所有值
		 */
		String tag = request.getParameter("tag");
		String mb_code = request.getParameter("MB_CODE");
		
		if (tag == null) {
			tag = "0";
		}
		if (request.getMethod().equals("GET") && tag.equals("0") && (mb_code ==null || mb_code.equals("")) ) {
		   
		    request.setAttribute("memberPageModel", pageModel);
			return mapping.findForward("success");
		}
		
		String modify_date = request.getParameter("CREATE_DATE");
		String end_date = request.getParameter("endDate");
		String payid = request.getParameter("payMethod");
		String isquery = request.getParameter("isquery");
		
		/*
		 * 如果客户端提交方式为"POST"，则按提交条件，从数据库中选择满足条件的会员充值
		 */

		String s_pageNum = request.getParameter("pageNo");
		int pageNum = 1;
		if (s_pageNum != null && !"".equals(s_pageNum)) {
			pageNum = Integer.parseInt(s_pageNum);
		}
		pageModel.setPageNo(pageNum);
		HashMap hashmap = new HashMap();
		hashmap.put("payid", payid);
		hashmap.put("modify_date", modify_date);
		hashmap.put("end_date", end_date);
		
		hashmap.put("mb_code", mb_code);

		pageModel.setCondition(hashmap);
		Collection historyMoney = new ArrayList();

		String condition = "";
		
			 
			if ( (isquery != null && isquery.equals("1")) || (mb_code !=null && !mb_code.equals("")) ) {
			    
				memberaddMoneyDAO.QueryMemberHistoryMoney(conn, pageModel);

				if (mb_code != null && mb_code.length() > 0) {
					condition += " and a.card_id='" + mb_code + "'";
				}
				if (modify_date != null && modify_date.length() > 0) {
					condition += " and b.modify_date >= date '" + modify_date + "' ";
				}
				if (end_date != null && end_date.length() > 0) {
					condition += " and b.modify_date < date '"+end_date + "' + 1";
				}
				if (payid != null && Integer.parseInt(payid) > -1) {
					condition += " and e.id=" + payid;
				}
				/*
				 * 显示总金额
				 */
				if (s_pageNum == null || s_pageNum.equals("1")) {
					double summoney = memberaddMoneyDAO.getSumMoney(conn,
							condition);
					request.setAttribute("summoney", String.valueOf(summoney));
				}
				/*
				 * 生成报表
				 */
			}

			request.setAttribute("memberPageModel", pageModel);
			String isExcel = request.getParameter("isExcel");
			if (isquery != null && isquery.equals("2")) {
				if (isExcel == null)
					isExcel = "";
				if (isExcel.equals("1")) {
					if (mb_code != null && mb_code.length() > 0) {
						condition += " and a.card_id='" + mb_code + "'";
					}
					if (modify_date != null && modify_date.length() > 0) {
						condition += " and b.modify_date >= date '" + modify_date + "' ";
					}
					if (end_date != null && end_date.length() > 0) {
						condition += " and b.modify_date < date '"+end_date + "' + 1";
					}
					if (payid != null && Integer.parseInt(payid) > -1) {
						condition += " and e.id=" + payid;
					}
					memberMoneyExecl = memberaddMoneyDAO.getMemberMoneyExcel(
							conn, condition);
					request.setAttribute("memberMoneyExecl", memberMoneyExecl);
					return mapping.findForward("excel");
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

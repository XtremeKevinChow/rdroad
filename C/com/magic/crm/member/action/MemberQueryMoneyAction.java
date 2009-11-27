/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.member.dao.MemberaddMoneyDAO;

import com.magic.crm.member.form.MemberaddMoneyForm;
import com.magic.crm.util.DBManager;

/**
 * @author user1 TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberQueryMoneyAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		CommonPageUtil pageModel = new CommonPageUtil();
		try {
			conn = DBManager.getConnection();
			Collection typeList = MemberaddMoneyDAO.getImportTypeList(conn, 0);
			request.setAttribute("typeList", typeList);
			/*
			 * 如果提交方式为“GET”（即进入查询页面准备查询），此时直接转向查询页面，不需要对数据库有任何操作
			 */
			if (request.getMethod().equals("GET")) {
				request.setAttribute("memberMoneyModel", pageModel);
				request.setAttribute("memberaddMoneyForm", myForm);
				return mapping.findForward("success");
			}

			/*
			 * 如果客户端提交方式为"POST"，则按提交条件，从数据库中选择满足条件的会员充值
			 */
			String create_date = request.getParameter("CREATE_DATE");
			String searchMbName = request.getParameter("searchMbName");
			String isquery = request.getParameter("isquery");
			String status = request.getParameter("status");
			
			if (status == null) {
				status = "0";
			}
			/**
			 * add by user 2008-03-25
			 */
			String importType = request.getParameter("TYPE");
			// 汇号查询条件
			String ref_id = request.getParameter("searchRefId");

			if (ref_id == null) {
				ref_id = "";
			} else {
				ref_id = ref_id.trim();
			}
			String s_pageNum = request.getParameter("pageNo");
			int pageNum = 1;
			if (s_pageNum != null && !"".equals(s_pageNum)) {
				pageNum = Integer.parseInt(s_pageNum);
			}
			pageModel.setPageNo(pageNum);
			HashMap hashmap = new HashMap();
			hashmap.put("status", status);
			hashmap.put("createDate", create_date);
			hashmap.put("searchMbName", searchMbName);
			hashmap.put("ref_id", ref_id);
			hashmap.put("import_type", importType);
			hashmap.put("filter1", myForm.getFilter1()+"");

			pageModel.setCondition(hashmap);

			MemberaddMoneyDAO memberaddMoneyDAO = new MemberaddMoneyDAO();

			if (isquery != null && isquery.equals("1")) {

				memberaddMoneyDAO.QueryMemberPrepay(conn, pageModel);
			}
			request.setAttribute("memberMoneyModel", pageModel);
			request.setAttribute("memberaddMoneyForm", myForm);
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
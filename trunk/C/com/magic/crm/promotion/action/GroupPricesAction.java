/**
 * GroupPricesAction.java
 * 2008-5-8
 * 上午09:11:20
 * user
 * GroupPricesAction
 */
package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.promotion.dao.GroupPricesDAO;
import com.magic.crm.promotion.form.GroupPricesForm;
import com.magic.crm.promotion.entity.GroupPrices;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 *
 */
public class GroupPricesAction extends DispatchAction {
	
	/**
	 * 查询
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
		String forward = "list";
		GroupPricesForm pageData = (GroupPricesForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			pageData.setActivityList(GroupPricesDAO.getAllActivityList(conn));
			// 1 查询信息
			Collection ret = GroupPricesDAO.findAll(conn, pageData);
			request.setAttribute("list", ret);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}
	
	/**
	 * 显示新增页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "add";
		GroupPricesForm pageData = (GroupPricesForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// 1 查询信息
			Collection ret = GroupPricesDAO.getAllSectionList(conn);
			//request.setAttribute("sectionList", ret);
			pageData.setSectionList(ret);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 检测输入信息
	 * @param request
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	private boolean checkInput(HttpServletRequest request, GroupPricesForm pageData) throws Exception {
		if (pageData.getSectionId() <= 0) {
			Message.setErrorMsg(request, "请选择销售区!");
			return false;
		}
		if (pageData.getSaleQty() <= 0) {
			Message.setErrorMsg(request, "数量必须大于0!");
			return false;
		}
		if (pageData.getSaleAmt() < 0) {
			Message.setErrorMsg(request, "金额不能小于0!");
			return false;
		}
		if (pageData.getIsGift() < 0 || pageData.getIsGift() > 1) {
			Message.setErrorMsg(request, "请选择是否赠送礼品!");
			return false;
		}
		if (pageData.getBeginDate() == null || pageData.getEndDate() == null) {
			Message.setErrorMsg(request, "起止日期不能为空或日期格式不对!");
			return false;
		}
		if (pageData.getBeginDate().after(pageData.getEndDate())) {
			Message.setErrorMsg(request, "开始日期不能超过结束日期!");
			return false;
		}
		return true;
	}
	/**
	 * 新增
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "message";
		GroupPricesForm pageData = (GroupPricesForm) form;
		Connection conn = null;
		try {
			if (checkInput(request, pageData)) {
				conn = DBManager.getConnection();
				GroupPricesDAO.insert(conn, pageData);
				Message.setErrorMsg(request, "添加成功!");
			}
			

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 显示修改页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "modify";
		GroupPricesForm pageData = (GroupPricesForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			GroupPrices data = GroupPricesDAO.view(conn, pageData);
			PropertyUtils.copyProperties(pageData, data);
			Collection ret = GroupPricesDAO.getAllSectionList(conn);
			pageData.setSectionList(ret);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "message";
		GroupPricesForm pageData = (GroupPricesForm) form;
		Connection conn = null;
		try {
			if (checkInput(request, pageData)) {
				conn = DBManager.getConnection();
				GroupPricesDAO.update(conn, pageData);
				Message.setErrorMsg(request, "修改成功!");
			}

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "message";
		GroupPricesForm pageData = (GroupPricesForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			pageData.setStatus(-1);
			GroupPricesDAO.updateStatus(conn, pageData);
			Message.setErrorMsg(request, "删除成功!");

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 审核
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "message";
		GroupPricesForm pageData = (GroupPricesForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			pageData.setStatus(1);
			GroupPricesDAO.updateStatus(conn, pageData);
			Message.setErrorMsg(request, "审核成功!");

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}
}

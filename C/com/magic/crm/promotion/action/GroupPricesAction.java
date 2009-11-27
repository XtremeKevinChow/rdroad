/**
 * GroupPricesAction.java
 * 2008-5-8
 * ����09:11:20
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
	 * ��ѯ
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
			// 1 ��ѯ��Ϣ
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
	 * ��ʾ����ҳ��
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
			// 1 ��ѯ��Ϣ
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
	 * ���������Ϣ
	 * @param request
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	private boolean checkInput(HttpServletRequest request, GroupPricesForm pageData) throws Exception {
		if (pageData.getSectionId() <= 0) {
			Message.setErrorMsg(request, "��ѡ��������!");
			return false;
		}
		if (pageData.getSaleQty() <= 0) {
			Message.setErrorMsg(request, "�����������0!");
			return false;
		}
		if (pageData.getSaleAmt() < 0) {
			Message.setErrorMsg(request, "����С��0!");
			return false;
		}
		if (pageData.getIsGift() < 0 || pageData.getIsGift() > 1) {
			Message.setErrorMsg(request, "��ѡ���Ƿ�������Ʒ!");
			return false;
		}
		if (pageData.getBeginDate() == null || pageData.getEndDate() == null) {
			Message.setErrorMsg(request, "��ֹ���ڲ���Ϊ�ջ����ڸ�ʽ����!");
			return false;
		}
		if (pageData.getBeginDate().after(pageData.getEndDate())) {
			Message.setErrorMsg(request, "��ʼ���ڲ��ܳ�����������!");
			return false;
		}
		return true;
	}
	/**
	 * ����
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
				Message.setErrorMsg(request, "��ӳɹ�!");
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
	 * ��ʾ�޸�ҳ��
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
	 * �޸�
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
				Message.setErrorMsg(request, "�޸ĳɹ�!");
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
	 * ɾ��
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
			Message.setErrorMsg(request, "ɾ���ɹ�!");

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
	 * ���
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
			Message.setErrorMsg(request, "��˳ɹ�!");

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

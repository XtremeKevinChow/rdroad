package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.order.dao.TicketDAO;
import com.magic.crm.order.entity.OneTicket;
import com.magic.crm.order.entity.Ticket;
import com.magic.crm.product.dao.Product2DAO;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.form.Product2Form;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.promotion.dao.ExpExchangeActivityDAO;
import com.magic.crm.promotion.dao.ExpExchangePackageDAO;
import com.magic.crm.promotion.form.ExpExchangeActivityForm;
import com.magic.crm.promotion.entity.ExpExchangeActivity;
import com.magic.crm.promotion.entity.ExpExchangePackageMst;
import com.magic.crm.promotion.entity.ExpExchangeStepMst;
import com.magic.crm.promotion.entity.ExpExchangeStepDtl;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

import com.magic.crm.io.ExpSettingSync;
//import com.magic.crm.util.DBManager3;

/**
 * ���ֻaction��
 * 
 * @author user
 * 
 */
public class ExpExchangeActivityAction extends DispatchAction {

	/**
	 * ��ѯ���л��ֻ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "list_activity";
		// ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// 1 ��ѯ��Ϣ
			Collection ret = ExpExchangeActivityDAO.findAll(conn);
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
	 * ɾ�����ֻ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "query_activity";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			pageData.setOldStatus(0);
			pageData.setStatus(-1);
			int i = ExpExchangeActivityDAO.updateActivityStatus(conn, pageData);
			if (i != 1) {
				Message.setErrorMsg(request, "״̬�Ѿ��ı䣬����ʧ��!");
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
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
	 * ��˻��ֻ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "query_activity";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			pageData.setOldStatus(0);
			pageData.setStatus(1);
			int i = ExpExchangeActivityDAO.updateActivityStatus(conn, pageData);
			if (i != 1) {
				Message.setErrorMsg(request, "״̬�Ѿ��ı䣬����ʧ��!");
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
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
	 * ������˻��ֻ(�����������)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward recheckActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "query_activity";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			pageData.setOldStatus(-2);
			pageData.setStatus(1);
			int i = ExpExchangeActivityDAO.updateActivityStatus(conn, pageData);
			if (i != 1) {
				Message.setErrorMsg(request, "״̬�Ѿ��ı䣬����ʧ��!");
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
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
	 * ������ֻ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uncheckActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "query_activity";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			pageData.setOldStatus(1);
			pageData.setStatus(-2);
			int i = ExpExchangeActivityDAO.updateActivityStatus(conn, pageData);
			if (i != 1) {
				Message.setErrorMsg(request, "״̬�Ѿ��ı䣬����ʧ��!");
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
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
	 * �رջ��ֻ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward closeActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "query_activity";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			pageData.setOldStatus(1);
			pageData.setStatus(2);
			int i = ExpExchangeActivityDAO.updateActivityStatus(conn, pageData);
			if (i != 1) {
				Message.setErrorMsg(request, "״̬�Ѿ��ı䣬����ʧ��!");
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
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
	 * ��ʾ�����ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showAddActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "add_activity";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {

			ExpExchangeActivity activity = new ExpExchangeActivity();
			activity.init(9);
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * �������ֻ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "query_activity";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		//��½��
		HttpSession session = request.getSession();
		User user = new User();
		user = (User) session.getAttribute("user");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			pageData.copyActivity(activity, 0);
			activity.setCreatePerson(Integer.parseInt(user.getId()));
			if (checkActivity(request, pageData)) {
				ExpExchangeActivityDAO.insertActivity(conn, activity);
				//ExpExchangeActivityDAO.updateActivityHTML(conn, activity);
				request.getSession(true).removeAttribute("activity");
			} else {
				forward = "add_activity";
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			forward = "add_activity";
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
	 * ���������ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backShowAddActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "add_activity";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			// ��Session����ѹ��ҳ��
			pageData.copyActivity(activity, 1);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * ��ʾ�������ֵ���ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showAddStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "add_step";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			if (checkActivity(request, pageData)) {
				// ��ҳ�����ݱ��浽session��
				pageData.copyActivity(activity, 0);
				request.getSession(true).setAttribute("activity", activity);
			} else {
				forward = "add_activity";
			}
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * �����������ֵ���ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backShowAddStep(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "add_step";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			// ��Session����ѹ��ҳ��
			// pageData.copy(activity, 1);
			request.getSession(true).setAttribute("activity", activity);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * �������ֵ��Σ��٣�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = request.getParameter("forward");
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// ȡ��Session�е�����
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// �������ֵ���
			activity.createStep();

			// ��ҳ�����ݱ��浽session��
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * ɾ�����ֵ��Σ��٣�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = request.getParameter("forward");
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// ȡ��Session�е�����
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// �õ�ɾ������
			ExpExchangeStepMst deletedMst = activity.getStepByRow(pageData
					.getRowId());

			// ���������״̬�����ǳ־û����󣬽�ɾ���ĵ��α���
			if (activity.getStatus() == -2 && deletedMst.getId() != 0) {
				/**
				 * ArrayList�÷���
				 * add(object):������׷�ӵ����б�β��
				 * addAll(collecton)����collection������Ԫ��׷�ӵ����б��β��
				 */
				activity.getDeletedDtlList().addAll(deletedMst.getDtlList());
				activity.getDeletedMstList().add(deletedMst);
			}

			// ɾ��ָ���Ļ��ֵ���
			
			activity.removeStep(deletedMst);

			// ��ҳ�����ݱ��浽session��
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * ��ʾ�������ֵ�����Ʒҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showAddStepGift(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "add_step_gift";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// ȡ��Session�е�����
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// ��ҳ�����ݱ��浽session��
			pageData.copyStep(activity, 0);
			request.getSession(true).setAttribute("activity", activity);

			// ��������
			if (checkStep(request, pageData)) { // �ɹ����

			} else {
				forward = "add_step";
			}

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * �������ֵ�����Ʒҳ��(��)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addStepGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = request.getParameter("forward");
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// ȡ��Session�е�����
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// ��ʼ������
			activity.createStepGiftByExp(pageData.getQueryExp());

			// ��ҳ�����ݱ��浽session��
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * �������ֵ�����Ʒҳ��(��)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteStepGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = request.getParameter("forward");
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// ȡ��Session�е�����
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// �õ�������ϸ����
			ExpExchangeStepDtl deletedDtl = activity.getStepDtlByRow(pageData);

			// ɾ��
			//activity.removeStepGift(deletedDtl);
			activity.removeStepGiftByIndex(pageData);

			// ���������״̬���������ǳ־û����󣬱��ݵ�deletedDtlList
			if (activity.getStatus() == -2 && deletedDtl.getId() != 0) {
				activity.getDeletedDtlList().add(deletedDtl);
			}

			// ��ҳ�����ݱ��浽session��
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * �����ύ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFinish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "query_activity";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		// ��½��
		HttpSession session = request.getSession();
		User user = new User();
		user = (User) session.getAttribute("user");

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			// ȡ��Session�е�����
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			activity.setCreatePerson(Integer.parseInt(user.getId()));
			// �����Ʒ��ϸ����
			activity.removeAllStepGift();

			// ��ҳ�����ݱ��浽session��
			pageData.copyStepGift(activity, 0);
			request.getSession(true).setAttribute("activity", activity);

			// ���ҳ������
			if (checkStepGift(request, pageData, conn)) { // ͨ�����

				// �������
				activity = (ExpExchangeActivity) request.getSession(true)
						.getAttribute("activity");
				if (activity != null) {
					// ��ӻ
					ExpExchangeActivityDAO.insertActivity(conn, activity);
					//ExpExchangeActivityDAO.updateActivityHTML(conn, activity);

					// ��ӻ��ֵ��μ���ϸ
					Iterator mstIt = activity.getMstList().iterator();
					while (mstIt.hasNext()) {
						ExpExchangeStepMst stepMst = (ExpExchangeStepMst) mstIt
								.next();
						stepMst.setCreatePerson(Integer.parseInt(user.getId()));

						long stepId = ExpExchangeActivityDAO.insertStepMst(
								conn, stepMst);
						Iterator dtlIt = stepMst.getDtlList().iterator();
						while (dtlIt.hasNext()) {
							ExpExchangeStepDtl stepDtl = (ExpExchangeStepDtl) dtlIt
									.next();
							stepDtl.getStepMst().setId(stepId);
							ExpExchangeActivityDAO.insertStepDtl(conn, stepDtl);
						}
					}
				}

				conn.commit();
				request.getSession(true).removeAttribute("activity");
			} else {
				forward = "add_step_gift";
			}

		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			log.error("exception:", e);
			forward = "add_step_gift";
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * �鿴���ֻ(������������)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "view_activity";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ExpExchangeActivity activity = ExpExchangeActivityDAO.view(conn,
					pageData);
			request.setAttribute("activity", activity);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * ��ʾ���ֻ�޸�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showModifyActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "modify_activity";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ExpExchangeActivity activity = ExpExchangeActivityDAO.viewActivity(
					conn, pageData);
			if (activity.getStatus() != 0 && activity.getStatus() != -2) { // �����½�״̬�����޸�
				Message.setErrorMsg(request, "�����½�������״̬�����޸�!");
				activity = ExpExchangeActivityDAO.view(conn, pageData);
				forward = "view_activity";
				request.setAttribute("activity", activity);
			}
			pageData.copyActivity(activity, 1);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * �޸Ļ��ֻ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "query_activity";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			ExpExchangeActivity activity = new ExpExchangeActivity();
			if (checkActivity(request, pageData)) {
				pageData.copyActivity(activity, 0);
				ExpExchangeActivityDAO.updateActivity(conn, activity);
				//ExpExchangeActivityDAO.updateActivityHTML(conn, activity);
			} else {
				forward = "modify_activity";
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error("exception:", e);
			forward = "modify_activity";
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * ��ʾ���ֻ�����޸�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showModifyStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "modify_step";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ExpExchangeActivity activity = ExpExchangeActivityDAO.view(conn,
					pageData);
			if (activity.getStatus() != 0 && activity.getStatus() != -2) { // �����½�״̬�����޸�
				Message.setErrorMsg(request, "�����½�������״̬�����޸�!");
				activity = ExpExchangeActivityDAO.view(conn, pageData);
				forward = "view_activity";
				request.setAttribute("activity", activity);
			}
			request.getSession(true).setAttribute("activity", activity);
			// pageData.copy(activity, 1);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * ��ʾ���ֻ������ϸ�޸�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showModifyStepGift(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "modify_step_gift";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// ȡ��Session�е�����
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			pageData.copyStep(activity, 0);
			request.getSession(true).setAttribute("activity", activity);
			if (!checkStep(request, pageData)) {

				forward = "modify_step";
			} else {

			}
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * �޸��ύ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyFinish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "query_activity";
		// ҳ�洫�ݹ���������
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		// ��½��
		HttpSession session = request.getSession();
		User user = new User();
		user = (User) session.getAttribute("user");

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			// ȡ��Session�е�����
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			activity.setCreatePerson(Integer.parseInt(user.getId()));
			

			// ��ҳ�����ݱ��浽session��
			pageData.copyStepGift(activity, 0);
			
			request.getSession(true).setAttribute("activity", activity);

			// ���ҳ������
			if (checkStepGift(request, pageData, conn)) { // ͨ�����

				// �������
				activity = (ExpExchangeActivity) request.getSession(true)
						.getAttribute("activity");
				if (activity != null) {
					
					if (activity.getStatus() == 0) { //�½�
						//ɾ���
						ExpExchangeActivityDAO.removeStepDtlByActivityNo(conn, activity.getActivityNo());
						ExpExchangeActivityDAO.removeStepMstByActivityNo(conn, activity.getActivityNo());

						// ��ӻ��ֵ��μ���ϸ
						Iterator mstIt = activity.getMstList().iterator();
						while (mstIt.hasNext()) {
							ExpExchangeStepMst stepMst = (ExpExchangeStepMst) mstIt
									.next();
							stepMst.setCreatePerson(Integer.parseInt(user.getId()));

							long stepId = ExpExchangeActivityDAO.insertStepMst(
									conn, stepMst);
							Iterator dtlIt = stepMst.getDtlList().iterator();
							while (dtlIt.hasNext()) {
								ExpExchangeStepDtl stepDtl = (ExpExchangeStepDtl) dtlIt
										.next();
								stepDtl.getStepMst().setId(stepId);
								ExpExchangeActivityDAO.insertStepDtl(conn, stepDtl);
							}
						}
					} else if (activity.getStatus() == -2) { //����
						// ɾ���ĳ־û�����
						Iterator it = activity.getDeletedDtlList().iterator();
						while (it.hasNext()) {
							ExpExchangeStepDtl deletedDtl = (ExpExchangeStepDtl) it
									.next();
							ExpExchangeActivityDAO.deleteStepDtl(conn, deletedDtl);
						}
						Iterator it2 = activity.getDeletedMstList().iterator();
						while (it2.hasNext()) {
							ExpExchangeStepMst deletedMst = (ExpExchangeStepMst) it2
									.next();
							ExpExchangeActivityDAO.deleteStepMst(conn, deletedMst);
						}
						// ����δ�־û�������
						Iterator mstIt = activity.getMstList().iterator();
						while (mstIt.hasNext()) {
							
							ExpExchangeStepMst stepMst = (ExpExchangeStepMst) mstIt
									.next();
							long stepId =stepMst.getId();
							if (stepId == 0) { // δ�־û������ݣ��Գ־û������ݲ�������
								stepMst.setCreatePerson(Integer.parseInt(user
										.getId()));
								stepId = ExpExchangeActivityDAO.insertStepMst(
										conn, stepMst);
								
							}
							Iterator dtlIt = stepMst.getDtlList().iterator();
							while (dtlIt.hasNext()) {
								ExpExchangeStepDtl stepDtl = (ExpExchangeStepDtl) dtlIt
										.next();
								if (stepDtl.getId() == 0) {
									stepDtl.getStepMst().setId(stepId);
									ExpExchangeActivityDAO.insertStepDtl(conn,
											stepDtl);
								}
							}
							
						}
					}
					
		
				}
				conn.commit();
				request.getSession(true).removeAttribute("activity");
			} else {
				forward = "modify_step_gift";
			}

		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			log.error("exception:", e);
			forward = "modify_step_gift";
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * 
	 * �����޸Ļ����ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backShowModifyStep(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "modify_step";
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			request.getSession(true).setAttribute("activity", activity);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * ͬ�����ֻ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward syncActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "query_activity";

		Connection conn1 = null;
		Connection conn2 = null;
		try {
			conn1 = DBManager.getConnection();
			conn2 = null;//DBManager3.getConnection();

			conn1.setAutoCommit(false);
			conn2.setAutoCommit(false);

			ExpSettingSync sync = new ExpSettingSync();
			sync.execute(conn1, conn2);
			conn1.commit();
			conn2.commit();
			Message.setMessage(request, "ͬ���ɹ�!");

		} catch (Exception e) {
			if (!conn1.getAutoCommit()) {
				conn1.rollback();
			}
			if (!conn2.getAutoCommit()) {
				conn2.rollback();
			}
			
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			if (conn1 != null) {
				conn1.close();
			}
			if (conn2 != null) {
				conn2.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * �����ֻ
	 * 
	 * @param request
	 * @param pageData
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private boolean checkActivity(HttpServletRequest request,
			ExpExchangeActivityForm pageData) throws Exception {

		if (pageData.getActivityType() <= 0) {
			Message.setErrorMsg(request, "����Ͳ���Ϊ��!");
			return false;
		}
		if (pageData.getActivityNo() == null
				|| pageData.getActivityNo().length() == 0) {
			Message.setErrorMsg(request, "���ֻ�Ų���Ϊ��!");
			return false;
		}
		if (pageData.getActivityDesc() == null
				|| pageData.getActivityDesc().length() == 0) {
			Message.setErrorMsg(request, "���������Ϊ��!");
			return false;
		}
		if (pageData.getBeginDate() == null || pageData.getEndDate() == null) {
			Message.setErrorMsg(request, "��ֹ���ڲ���Ϊ��!");
			return false;
		}
		if (pageData.getGiftLastDate() == null) {
			Message.setErrorMsg(request, "��Ʒ��������ڲ���Ϊ��!");
			return false;
		}
		if (pageData.getExchangeType() == null) {
			Message.setErrorMsg(request, "�һ���ʽ����Ϊ��!");
			return false;
		} else {
			if (pageData.getExchangeType().equals("A")) {// һ���Զһ�

				/*if (pageData.getDealType() == null
						|| pageData.getDealType().length() == 0) {
					Message.setErrorMsg(request, "����ʽ����Ϊ��!");
					return false;
				}*/
				pageData.setDealType("A");
				
			} else {
				pageData.setDealType("B");
			}
		}
		return true;
	}

	/**
	 * �����ֵ���
	 * 
	 * @param request
	 * @param pageData
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private boolean checkStep(HttpServletRequest request,
			ExpExchangeActivityForm pageData) throws Exception {
		if (pageData.getBeginExp() == null
				|| pageData.getBeginExp().length == 0) {
			Message.setErrorMsg(request, "��ϸ����Ϊ��!");
			return false;
		}
		// �����Ǵ���0������
		/*
		 * for (int i = 0; i < pageData.getBeginExp().length; i ++) { if
		 * (pageData.getBeginExp()[i] <= 0) { Message.setErrorMsg(request,
		 * "������ϸ��"+ (i+1) +"�У����ֵ��α����Ǵ���0������!"); return false; } }
		 */
		// ���β����ظ�
		int len = pageData.getBeginExp().length;
		for (int i = 0; i < len; i++) {
			if (pageData.getBeginExp()[i] > 0) {
				int outer = pageData.getBeginExp()[i];
				for (int j = i + 1; j < len; j++) {
					int inner = pageData.getBeginExp()[j];
					if (outer == inner) {// �ҵ���ȵļ�¼
						Message.setErrorMsg(request, "������ϸ��" + (i + 1) + "�к͵�"
								+ (j + 1) + "���ظ�����!");
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * ��⵵����ϸ
	 * 
	 * @param request
	 * @param pageData
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private boolean checkStepGift(HttpServletRequest request,
			ExpExchangeActivityForm pageData, Connection conn) throws Exception {
		if (pageData.getStepType() == null
				|| pageData.getStepType().length == 0) {
			Message.setErrorMsg(request, "������ϸ����Ϊ��!");
			return false;
		}
		for (int i = 0; i < pageData.getStepType().length; i++) {
			if (pageData.getStepType()[i] == null
					|| pageData.getStepType()[i].length() == 0) {
				Message.setErrorMsg(request, pageData.getBeginExp()[i]
						+ "�ֵ�������ϸ��" + (i + 1) + "�У����Ͳ���Ϊ��!");
				return false;
			}
			if (pageData.getNo()[i] == null
					|| pageData.getNo()[i].length() == 0) {
				Message.setErrorMsg(request, pageData.getBeginExp()[i]
						+ "�ֵ�������ϸ��" + (i + 1) + "�У����벻��Ϊ��!");
				return false;
			} else {

				if (pageData.getStepType()[i].equals("T")) {// ��ȯ
					Ticket one = TicketDAO.getTicketByNumber(conn, pageData
							.getNo()[i]);
					if (one == null || one.getGiftNumber() == null) { // �Ҳ��������ȯ
						Message.setErrorMsg(request, pageData.getBeginExp()[i]
								+ "�ֵ�������ϸ��" + (i + 1) + "�У���ȯ�Ų�����!");
						return false;
					}
				} else if (pageData.getStepType()[i].equals("G")) {// ��Ʒ
					ProductDAO productDao = new ProductDAO();
					Product2Form obj = new Product2Form();
					obj.setItem_code(pageData.getNo()[i]);
					
					int ret = Product2DAO.findByItemCode(conn, obj);

					if (ret < 0 ) { // ��Ʒ������
						Message.setErrorMsg(request, pageData.getBeginExp()[i]
								+ "�ֵ�������ϸ��" + (i + 1) + "�У����Ų�����!");
						return false;
					}/* else {
						if (obj.getItem_type() == 2 || obj.getItem_type() == 3) { // ��װ��Ʒ
							Message.setErrorMsg(request,
									pageData.getBeginExp()[i] + "�ֵ�������ϸ��"
											+ (i + 1) + "�У���֧��ϵ�к���װ��Ʒ!");
							return false;
						}
					}*/
				} else if (pageData.getStepType()[i].equals("P")) { // ���
					ExpExchangePackageMst packageMst = ExpExchangePackageDAO
							.findByPk(conn, pageData.getNo()[i]);
					if (packageMst.getPackageNo() == null) {// �Ҳ���
						Message.setErrorMsg(request, pageData.getBeginExp()[i]
								+ "�ֵ�������ϸ��" + (i + 1) + "�У������������!");
						return false;
					}
				} else {
					Message.setErrorMsg(request, pageData.getBeginExp()[i]
							+ "�ֵ�������ϸ��" + (i + 1) + "�У����Ͳ���!");
					return false;
				}
			}

			if (pageData.getAddMoney()[i] < 0) {
				Message.setErrorMsg(request, pageData.getBeginExp()[i]
						+ "�ֵ�������ϸ��" + (i + 1) + "�н���С��0!");
				return false;
			}
		}
		return true;
	}
}

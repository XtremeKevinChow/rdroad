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
 * 积分活动action类
 * 
 * @author user
 * 
 */
public class ExpExchangeActivityAction extends DispatchAction {

	/**
	 * 查询所有积分活动
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
			// 1 查询信息
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
	 * 删除积分活动
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
				Message.setErrorMsg(request, "状态已经改变，操作失败!");
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
	 * 审核积分活动
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
				Message.setErrorMsg(request, "状态已经改变，操作失败!");
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
	 * 重新审核积分活动(由弃审再审核)
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
				Message.setErrorMsg(request, "状态已经改变，操作失败!");
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
	 * 弃审积分活动
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
				Message.setErrorMsg(request, "状态已经改变，操作失败!");
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
	 * 关闭积分活动
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
				Message.setErrorMsg(request, "状态已经改变，操作失败!");
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
	 * 显示新增活动页面
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
	 * 新增积分活动
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
		//登陆人
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
	 * 返回新增活动页面
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
			// 将Session数据压入页面
			pageData.copyActivity(activity, 1);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * 显示新增积分档次页面
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			if (checkActivity(request, pageData)) {
				// 将页面数据保存到session中
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
	 * 返回新增积分档次页面
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
			// 将Session数据压入页面
			// pageData.copy(activity, 1);
			request.getSession(true).setAttribute("activity", activity);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * 新增积分档次（假）
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// 取出Session中的数据
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// 新增积分档次
			activity.createStep();

			// 将页面数据保存到session中
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * 删除积分档次（假）
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// 取出Session中的数据
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// 得到删除对象
			ExpExchangeStepMst deletedMst = activity.getStepByRow(pageData
					.getRowId());

			// 如果是弃审状态并且是持久化对象，将删除的档次备份
			if (activity.getStatus() == -2 && deletedMst.getId() != 0) {
				/**
				 * ArrayList用法：
				 * add(object):将对象追加到此列表尾部
				 * addAll(collecton)：将collection中所有元素追加到此列表的尾部
				 */
				activity.getDeletedDtlList().addAll(deletedMst.getDtlList());
				activity.getDeletedMstList().add(deletedMst);
			}

			// 删除指定的积分档次
			
			activity.removeStep(deletedMst);

			// 将页面数据保存到session中
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * 显示新增积分档次礼品页面
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// 取出Session中的数据
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// 将页面数据保存到session中
			pageData.copyStep(activity, 0);
			request.getSession(true).setAttribute("activity", activity);

			// 检测表单数据
			if (checkStep(request, pageData)) { // 成功检测

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
	 * 新增积分档次礼品页面(假)
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// 取出Session中的数据
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// 初始化数据
			activity.createStepGiftByExp(pageData.getQueryExp());

			// 将页面数据保存到session中
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * 新增积分档次礼品页面(假)
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		try {
			// 取出Session中的数据
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");

			// 得到档次明细对象
			ExpExchangeStepDtl deletedDtl = activity.getStepDtlByRow(pageData);

			// 删除
			//activity.removeStepGift(deletedDtl);
			activity.removeStepGiftByIndex(pageData);

			// 如果是弃审状态，并且是是持久化对象，备份到deletedDtlList
			if (activity.getStatus() == -2 && deletedDtl.getId() != 0) {
				activity.getDeletedDtlList().add(deletedDtl);
			}

			// 将页面数据保存到session中
			request.getSession(true).setAttribute("activity", activity);

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setErrorMsg(request, e.getMessage());
		}
		return mapping.findForward(forward);
	}

	/**
	 * 新增提交
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		// 登陆人
		HttpSession session = request.getSession();
		User user = new User();
		user = (User) session.getAttribute("user");

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			// 取出Session中的数据
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			activity.setCreatePerson(Integer.parseInt(user.getId()));
			// 清除赠品明细数据
			activity.removeAllStepGift();

			// 将页面数据保存到session中
			pageData.copyStepGift(activity, 0);
			request.getSession(true).setAttribute("activity", activity);

			// 检测页面数据
			if (checkStepGift(request, pageData, conn)) { // 通过检测

				// 添加数据
				activity = (ExpExchangeActivity) request.getSession(true)
						.getAttribute("activity");
				if (activity != null) {
					// 添加活动
					ExpExchangeActivityDAO.insertActivity(conn, activity);
					//ExpExchangeActivityDAO.updateActivityHTML(conn, activity);

					// 添加积分档次及明细
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
	 * 查看积分活动(包括档次设置)
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
		// 页面传递过来的数据
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
	 * 显示积分活动修改页面
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ExpExchangeActivity activity = ExpExchangeActivityDAO.viewActivity(
					conn, pageData);
			if (activity.getStatus() != 0 && activity.getStatus() != -2) { // 不是新建状态不能修改
				Message.setErrorMsg(request, "不是新建、弃审状态不能修改!");
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
	 * 修改积分活动
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
		// 页面传递过来的数据
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
	 * 显示积分活动档次修改页面
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ExpExchangeActivity activity = ExpExchangeActivityDAO.view(conn,
					pageData);
			if (activity.getStatus() != 0 && activity.getStatus() != -2) { // 不是新建状态不能修改
				Message.setErrorMsg(request, "不是新建、弃审状态不能修改!");
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
	 * 显示积分活动档次明细修改页面
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// 取出Session中的数据
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
	 * 修改提交
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
		// 页面传递过来的数据
		ExpExchangeActivityForm pageData = (ExpExchangeActivityForm) form;
		// 登陆人
		HttpSession session = request.getSession();
		User user = new User();
		user = (User) session.getAttribute("user");

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			// 取出Session中的数据
			ExpExchangeActivity activity = (ExpExchangeActivity) request
					.getSession(true).getAttribute("activity");
			activity.setCreatePerson(Integer.parseInt(user.getId()));
			

			// 将页面数据保存到session中
			pageData.copyStepGift(activity, 0);
			
			request.getSession(true).setAttribute("activity", activity);

			// 检测页面数据
			if (checkStepGift(request, pageData, conn)) { // 通过检测

				// 添加数据
				activity = (ExpExchangeActivity) request.getSession(true)
						.getAttribute("activity");
				if (activity != null) {
					
					if (activity.getStatus() == 0) { //新建
						//删除活动
						ExpExchangeActivityDAO.removeStepDtlByActivityNo(conn, activity.getActivityNo());
						ExpExchangeActivityDAO.removeStepMstByActivityNo(conn, activity.getActivityNo());

						// 添加积分档次及明细
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
					} else if (activity.getStatus() == -2) { //弃审
						// 删除的持久化数据
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
						// 新增未持久化的数据
						Iterator mstIt = activity.getMstList().iterator();
						while (mstIt.hasNext()) {
							
							ExpExchangeStepMst stepMst = (ExpExchangeStepMst) mstIt
									.next();
							long stepId =stepMst.getId();
							if (stepId == 0) { // 未持久化的数据，以持久化的数据不做处理
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
	 * 返回修改活动档次页面
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
	 * 同步积分活动
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
			Message.setMessage(request, "同步成功!");

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
	 * 检测积分活动
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
			Message.setErrorMsg(request, "活动类型不能为空!");
			return false;
		}
		if (pageData.getActivityNo() == null
				|| pageData.getActivityNo().length() == 0) {
			Message.setErrorMsg(request, "积分活动号不能为空!");
			return false;
		}
		if (pageData.getActivityDesc() == null
				|| pageData.getActivityDesc().length() == 0) {
			Message.setErrorMsg(request, "活动描述不能为空!");
			return false;
		}
		if (pageData.getBeginDate() == null || pageData.getEndDate() == null) {
			Message.setErrorMsg(request, "起止日期不能为空!");
			return false;
		}
		if (pageData.getGiftLastDate() == null) {
			Message.setErrorMsg(request, "礼品最后保留日期不能为空!");
			return false;
		}
		if (pageData.getExchangeType() == null) {
			Message.setErrorMsg(request, "兑换方式不能为空!");
			return false;
		} else {
			if (pageData.getExchangeType().equals("A")) {// 一次性兑换

				/*if (pageData.getDealType() == null
						|| pageData.getDealType().length() == 0) {
					Message.setErrorMsg(request, "处理方式不能为空!");
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
	 * 检测积分档次
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
			Message.setErrorMsg(request, "明细不能为空!");
			return false;
		}
		// 必须是大于0的整数
		/*
		 * for (int i = 0; i < pageData.getBeginExp().length; i ++) { if
		 * (pageData.getBeginExp()[i] <= 0) { Message.setErrorMsg(request,
		 * "赠送明细第"+ (i+1) +"行，积分档次必须是大于0的整数!"); return false; } }
		 */
		// 档次不能重复
		int len = pageData.getBeginExp().length;
		for (int i = 0; i < len; i++) {
			if (pageData.getBeginExp()[i] > 0) {
				int outer = pageData.getBeginExp()[i];
				for (int j = i + 1; j < len; j++) {
					int inner = pageData.getBeginExp()[j];
					if (outer == inner) {// 找到相等的记录
						Message.setErrorMsg(request, "赠送明细第" + (i + 1) + "行和第"
								+ (j + 1) + "行重复设置!");
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 检测档次明细
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
			Message.setErrorMsg(request, "赠送明细不能为空!");
			return false;
		}
		for (int i = 0; i < pageData.getStepType().length; i++) {
			if (pageData.getStepType()[i] == null
					|| pageData.getStepType()[i].length() == 0) {
				Message.setErrorMsg(request, pageData.getBeginExp()[i]
						+ "分的赠送明细第" + (i + 1) + "行，类型不能为空!");
				return false;
			}
			if (pageData.getNo()[i] == null
					|| pageData.getNo()[i].length() == 0) {
				Message.setErrorMsg(request, pageData.getBeginExp()[i]
						+ "分的赠送明细第" + (i + 1) + "行，号码不能为空!");
				return false;
			} else {

				if (pageData.getStepType()[i].equals("T")) {// 礼券
					Ticket one = TicketDAO.getTicketByNumber(conn, pageData
							.getNo()[i]);
					if (one == null || one.getGiftNumber() == null) { // 找不到这个礼券
						Message.setErrorMsg(request, pageData.getBeginExp()[i]
								+ "分的赠送明细第" + (i + 1) + "行，礼券号不存在!");
						return false;
					}
				} else if (pageData.getStepType()[i].equals("G")) {// 礼品
					ProductDAO productDao = new ProductDAO();
					Product2Form obj = new Product2Form();
					obj.setItem_code(pageData.getNo()[i]);
					
					int ret = Product2DAO.findByItemCode(conn, obj);

					if (ret < 0 ) { // 产品不存在
						Message.setErrorMsg(request, pageData.getBeginExp()[i]
								+ "分的赠送明细第" + (i + 1) + "行，货号不存在!");
						return false;
					}/* else {
						if (obj.getItem_type() == 2 || obj.getItem_type() == 3) { // 套装产品
							Message.setErrorMsg(request,
									pageData.getBeginExp()[i] + "分的赠送明细第"
											+ (i + 1) + "行，不支持系列和套装产品!");
							return false;
						}
					}*/
				} else if (pageData.getStepType()[i].equals("P")) { // 礼包
					ExpExchangePackageMst packageMst = ExpExchangePackageDAO
							.findByPk(conn, pageData.getNo()[i]);
					if (packageMst.getPackageNo() == null) {// 找不到
						Message.setErrorMsg(request, pageData.getBeginExp()[i]
								+ "分的赠送明细第" + (i + 1) + "行，该礼包不存在!");
						return false;
					}
				} else {
					Message.setErrorMsg(request, pageData.getBeginExp()[i]
							+ "分的赠送明细第" + (i + 1) + "行，类型不对!");
					return false;
				}
			}

			if (pageData.getAddMoney()[i] < 0) {
				Message.setErrorMsg(request, pageData.getBeginExp()[i]
						+ "分的赠送明细第" + (i + 1) + "行金额不能小于0!");
				return false;
			}
		}
		return true;
	}
}

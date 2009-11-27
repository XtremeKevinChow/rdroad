/*
 * Created on 2005-3-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.*;

/**
 * @author Administrator
 * 
 *         TODO 99read
 */
public class OrderPayAction extends DispatchAction {

	private Logger log = Logger.getLogger(OrderPayAction.class);

	/**
	 * 订单充值页面的初始化
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		User user = (User) request.getSession().getAttribute("user");

		try {
			conn = DBManager.getConnection();
			order.setPayments(OrderDAO.listKeyValue(conn, user.getId()));
			order.setCreateDate(ConfigDAO.getCurrentDate(conn));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("input");
	}

	/**
	 * 订单充值页面的初始化
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmoney(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		User user = (User) request.getSession().getAttribute("user");

		try {
			conn = DBManager.getConnection();
			order.setPayments(OrderDAO.listKeyValue(conn, user.getId()));
			order.setCreateDate(ConfigDAO.getCurrentDate(conn));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("emoney");
	}

	/**
	 * 订单充值页面的初始化
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCrush(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		User user = (User) request.getSession().getAttribute("user");

		try {
			conn = DBManager.getConnection();
			//order.setPayments(OrderDAO.listKeyValue(conn, user.getId()));
			//order.setCreateDate(ConfigDAO.getCurrentDate(conn));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("crush");
	}

	/**
	 * 订单充值页面的提交
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitPay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		MemberDAO memberDAO = new MemberDAO();
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			int inValue = 0;
			int ret = OrderDAO.validateOrderNumber(conn, order);
			if (order.getOrderNumber().length() > 0) {
				inValue = 0;
				// 按订单充值
				if (ret == -1) {
					Message.setErrorMsg(request, "该订单不存在");
					return mapping.findForward("error");
				} else if (ret == -2) {
					Message.setErrorMsg(request, "该订单不在缺款状态");
					return mapping.findForward("error");
				}
			} else {
				inValue = 1;
				if (!memberDAO.checkMBCardIDSEQ(conn, order.getCardId())) {
					Message.setErrorMsg(request, "该会员不存在");
					return mapping.findForward("error");
				}
				int mb_id = memberDAO.getMemberInfo(conn, order.getCardId())
						.getID();
				order.setMbId(mb_id);

			}
			System.out.println(order.getMbId());
			// 从session中取用户
			User user = (User) request.getSession().getAttribute("user");
			order.setCreatorId(Integer.parseInt(user.getId()));
			OrderDAO.payOrder(conn, order, inValue);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
			}
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		Message.setMessage(request, "充值成功");
		return mapping.findForward("success");
	}

	/**
	 * 礼金充值页面的提交
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitPayEmoney(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		MemberDAO memberDAO = new MemberDAO();
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			if (!memberDAO.checkMBCardIDSEQ(conn, order.getCardId())) {
				Message.setErrorMsg(request, "该会员不存在");
				return mapping.findForward("error");
			}
			int mb_id = memberDAO.getMemberInfo(conn, order.getCardId())
					.getID();
			order.setMbId(mb_id);

			// 从session中取用户
			User user = (User) request.getSession().getAttribute("user");
			order.setCreatorId(Integer.parseInt(user.getId()));
			int ret = OrderDAO.payEmoney(conn, order, 0);
			if (ret <0 ) {
				conn.rollback();
				if(ret == -19) {
					Message.setErrorMsg(request, "礼金帐户不能充为负值");
				} else {
					Message.setErrorMsg(request, "未知错误,充值失败");
				}
					
			} else {
				conn.commit();
				Message.setMessage(request, "充值成功");
			}
			
			
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
			}
			e.printStackTrace();
			//log.error(e);
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * 礼金充值页面的提交
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitPayCrushCard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		MemberDAO memberDAO = new MemberDAO();
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			if (!memberDAO.checkMBCardIDSEQ(conn, order.getCardId())) {
				Message.setErrorMsg(request, "该会员不存在");
				return mapping.findForward("error");
			}
			int mb_id = memberDAO.getMemberInfo(conn, order.getCardId()).getID();
			order.setMbId(mb_id);

			// 从session中取用户
			User user = (User) request.getSession().getAttribute("user");
			order.setCreatorId(Integer.parseInt(user.getId()));
			int ret = OrderDAO.payCrushCard(conn, order);
			if (ret == -10502) {
				Message.setErrorMsg(request, "该卡尚未开通");
				return mapping.findForward("success");
			} else if (ret == -10501) {
				Message.setErrorMsg(request, "该卡不存在");
				return mapping.findForward("success");
			} else if (ret == -10503) {
				Message.setErrorMsg(request, "该卡已被使用");
				return mapping.findForward("success");
			} else if (ret == -10504) {
				Message.setErrorMsg(request, "该卡已经作废");
				return mapping.findForward("success");
			} else if (ret == -10505) {
				Message.setErrorMsg(request, "该卡已经过期");
				return mapping.findForward("success");
			}
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
			}
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		Message.setMessage(request, "充值成功");
		return mapping.findForward("success");
	}
}

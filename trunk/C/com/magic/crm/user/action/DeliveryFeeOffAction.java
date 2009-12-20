package com.magic.crm.user.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.user.dao.DeliveryFeeOffDAO;
import com.magic.crm.user.form.DeliveryFeeOffForm;
import com.magic.crm.user.entity.DeliveryFeeOffDlv;
import com.magic.crm.user.entity.DeliveryFeeOffItem;
import com.magic.crm.user.entity.DeliveryFeeOff;
import com.magic.crm.user.dao.DeliveryFeeDAO;
import com.magic.crm.util.DBManager;
import java.util.Collection;
import com.magic.crm.util.Message;
import org.apache.struts.util.LabelValueBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class DeliveryFeeOffAction extends DispatchAction {
	/*
	 * 显示特定产品免邮费活动列表
	 */
	public ActionForward showDfoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "deliveryFeeOffList";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			Collection dfoList = dao.findAllDfo(conn);

			request.setAttribute("dfoList", dfoList);
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

	/*
	 * 显示特定产品免邮费活动信息
	 */
	public ActionForward showDfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "edit";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			int id = 0;
			String strId = request.getParameter("id");
			if (strId != null && strId.length() > 0) { // 修改
				id = Integer.parseInt(strId);
				DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
				DeliveryFeeOff dfo = dao.getDfo(conn, id);
				request.setAttribute("dfo", dfo);
			} else { // 新增
				request.setAttribute("dfo", new DeliveryFeeOff());
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

	/*
	 * 保存特定产品免邮费活动信息
	 */
	public ActionForward saveDfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "deliveryFeeOffList";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeOff dfo = new DeliveryFeeOff();

			int id = 0;
			String strId = request.getParameter("dfo_id");
			if (strId != null && strId.length() > 0)
				id = Integer.parseInt(strId);
			dfo.setName(request.getParameter("dfo_name"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			dfo.setBegin_date(format.parse(request.getParameter("startDate")));
			dfo.setEnd_date(format.parse(request.getParameter("endDate")));
			dfo.setStatus(Integer.parseInt(request.getParameter("dfo_status")));

			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			if (id == 0)
				dao.addDfo(conn, dfo);
			else {
				dfo.setId(id);
				dao.updateDfo(conn, dfo);
			}

			Collection dfoList = dao.findAllDfo(conn);
			request.setAttribute("dfoList", dfoList);
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

	/*
	 * 发送方式列表
	 */
	public ActionForward deliveryList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "deliveryList";
		Connection conn = null;
		try {
			int dfoId = Integer.parseInt(request.getParameter("id"));
			conn = DBManager.getConnection();
			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			Collection list = dao.deliveryList(conn, dfoId);
			request.setAttribute("list", list);
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

	/*
	 * 产品列表
	 */
	public ActionForward itemList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "itemList";
		Connection conn = null;
		try {
			int dfoId = Integer.parseInt(request.getParameter("id"));
			conn = DBManager.getConnection();
			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			Collection list = dao.itemList(conn, dfoId);
			request.setAttribute("list", list);
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

	/*
	 * 显示发送方式
	 */
	public ActionForward showDlv(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "editDlv";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			int offid = 0, dlvid = 0;
			offid = Integer.parseInt(request.getParameter("offid"));
			String strId2 = request.getParameter("dlvid");
			DeliveryFeeOffDlv dlv = new DeliveryFeeOffDlv();
			dlv.setOff_id(offid);
			if (strId2 != null && strId2.length() > 0) { // 修改
				dlvid = Integer.parseInt(strId2);
				DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
				dlv = dao.getDlv(conn, offid, dlvid);
				request.setAttribute("dlv", dlv);
				request.setAttribute("deliveryTypes", new ArrayList());
			} else { // 新增
				request.setAttribute("dlv", dlv);
				DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
				request.setAttribute("deliveryTypes", dao.deliveryTypeList(
						conn, offid));
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
	
	/*
	 * 更新产品状态
	 */
	public ActionForward updateItemStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "itemList";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			int id = Integer.parseInt(request.getParameter("id"));
			String itemCode = request.getParameter("itemCode");
			int status = Integer.parseInt(request.getParameter("status"));

			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			dao.updateItemStatus(conn, id, itemCode, status);
			
			Collection list = dao.itemList(conn, id);
			request.setAttribute("list", list);
			request.setAttribute("id", id + "");
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

	/*
	 * 保存发送方式
	 */
	public ActionForward saveDlv(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "deliveryList";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			DeliveryFeeOffDlv dlv = new DeliveryFeeOffDlv();
			dlv.setOff_id(Integer.parseInt(request.getParameter("off_id")));
			dlv
					.setDelivery_id(Integer.parseInt(request
							.getParameter("dlv_id")));
			dlv.setOff_type(Integer.parseInt(request.getParameter("off_type")));
			dlv.setStatus(Integer.parseInt(request.getParameter("dlv_status")));
			if (dlv.getOff_type() == 1)
				dlv.setOff_fee(0);
			else
				dlv.setOff_fee(Double.parseDouble(request
						.getParameter("off_fee")));

			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			DeliveryFeeOffDlv exists = dao.getDlv(conn, dlv.getOff_id(), dlv
					.getDelivery_id());
			if (exists == null)
				dao.addDlv(conn, dlv);
			else
				dao.updateDlv(conn, dlv);

			Collection list = dao.deliveryList(conn, dlv.getOff_id());
			request.setAttribute("list", list);
			request.setAttribute("id", dlv.getOff_id() + "");
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

	/*
	 * 添加产品
	 */
	public ActionForward addItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "itemList";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			int dfoId = Integer.parseInt(request.getParameter("id"));
			String itemCode = request.getParameter("txtItemCode");

			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			DeliveryFeeOffItem item = dao.getItem(conn, dfoId, itemCode);
			if (item != null) {
				dao.updateItemStatus(conn, dfoId, itemCode, 1);
			} else {
				item = new DeliveryFeeOffItem();
				item.setOff_id(dfoId);
				item.setItem_code(itemCode);
				item.setStatus(1);
				int type = dao.getItemType(conn, itemCode);
				if (type == 1)
					item.setItem_type(1);
				else if (type == 3)
					item.setItem_type(2);
				if (type == 1 || type == 3)
					dao.addItem(conn, item);
			}

			Collection list = dao.itemList(conn, dfoId);
			request.setAttribute("list", list);
			request.setAttribute("id", dfoId + "");
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
}

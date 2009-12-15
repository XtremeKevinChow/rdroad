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
import com.magic.crm.util.DBManager;
import java.util.Collection;
import com.magic.crm.util.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeliveryFeeOffAction extends DispatchAction {
	/*
	 * 显示特定产品免邮费活动列表
	 */
	public ActionForward showDfoList(ActionMapping mapping, ActionForm form
			, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	public ActionForward showDfo(ActionMapping mapping, ActionForm form
			, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String forward = "edit";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
		    int id = Integer.parseInt(request.getParameter("id"));
			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			DeliveryFeeOff dfo = dao.getDfo(conn, id);

			request.setAttribute("dfo", dfo);
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
	public ActionForward saveDfo(ActionMapping mapping, ActionForm form
			, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String forward = "deliveryFeeOffList";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryFeeOff dfo = new DeliveryFeeOff();
			
			int id = 0;
			String strId = request.getParameter("dfo_id");
			if(strId!=null && strId.length()>0)
				id = Integer.parseInt(strId);
			dfo.setName(request.getParameter("dfo_name"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			dfo.setBegin_date(format.parse(request.getParameter("startDate")));
			dfo.setEnd_date(format.parse(request.getParameter("endDate")));
			dfo.setStatus(Integer.parseInt(request.getParameter("dfo_status")));
			
			DeliveryFeeOffDAO dao = new DeliveryFeeOffDAO();
			if(id==0) dao.addDfo(conn, dfo);
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
}

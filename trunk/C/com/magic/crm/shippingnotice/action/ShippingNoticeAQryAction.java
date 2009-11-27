/*
 * Created on 2005-5-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.shippingnotice.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.common.PageAttribute;

import com.magic.crm.shippingnotice.dao.ShippingNoticeDAO;
import com.magic.crm.shippingnotice.form.ShippingNoticeForm;
import com.magic.crm.util.ConfigDAO;
import com.magic.crm.util.DBManager;
/**
 * @author Water
 * 
 * 订单高级查询
 */
public class ShippingNoticeAQryAction extends DispatchAction {
	private Logger log = Logger.getLogger(ShippingNoticeAQryAction.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magic.crm.common.WebAction#execute(com.magic.crm.common.DBOperation,
	 *      com.magic.crm.common.WebForm)
	 */
	public ActionForward init(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		ShippingNoticeForm data = (ShippingNoticeForm) form;
		Connection conn = DBManager.getConnection();
		// 取得查询条件下拉框的数据
		try {
			// 取得发货单状态
			data.setStatusList(ConfigDAO.listKeyValue(conn,"S_shippingnotice_STATUS"));
			// 取得发送方式
			data.setDeliveryList(ConfigDAO.listKeyValue(conn,"s_delivery_type"));
			// 取得支付方式
			data.setPayList(ConfigDAO.listKeyValue(conn,"s_payment_method"));
			
		} catch(Exception e) {
			log.error("exception",e);
			throw e;
		} finally {
			conn.close();
		}
		
		return mapping.findForward("init");
	}
	
    /**
     * 查询
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward query(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		ShippingNoticeForm snForm = (ShippingNoticeForm) form;
		Connection conn = DBManager.getConnection();
		try {
			PageAttribute pageUtil = new PageAttribute(20);
			String sql = combineSQL(request);
			log.info(sql);
			pageUtil.setSql(sql);
			pageUtil.setRecordCount(ShippingNoticeDAO.querySnListCount(conn,sql));
			snForm.setPageAttribute(pageUtil);
			request.setAttribute("sn_list",ShippingNoticeDAO.querySnList(conn,sql,pageUtil.getFrom(),pageUtil.getTo()));
			request.getSession().setAttribute("sn_page",pageUtil);
			
		} catch(Exception e) {
			log.error("exception",e);
			throw e;
		} finally {
			try {conn.close();} catch(Exception e){}
		}
		
		return mapping.findForward("list");
	}
	
	 /**
     * 查询
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		ShippingNoticeForm snForm = (ShippingNoticeForm) form;
		Connection conn = DBManager.getConnection();
		try {
			PageAttribute pageUtil = (PageAttribute)request.getSession().getAttribute("sn_page");
			String pageNo = request.getParameter("pageNo");
			pageUtil.setPageNo(Integer.parseInt(pageNo));
			snForm.setPageAttribute(pageUtil);
			request.setAttribute("sn_list",ShippingNoticeDAO.querySnList(conn,pageUtil.getSql(),pageUtil.getFrom(),pageUtil.getTo()));
			request.getSession().setAttribute("sn_page",pageUtil);
			
		} catch(Exception e) {
			log.error("exception",e);
			throw e;
		} finally {
			try {conn.close();} catch(Exception e){}
		}
		
		return mapping.findForward("list");
	}
	
	/**
	 * 拼出查询订单的sql
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String combineSQL(HttpServletRequest request) 
	throws Exception {
		String sql = " SELECT a.*,b.name as mb_name, b.card_id,c.name as status_name,d.name as delivery_type_name "
			+ " FROM ord_shippingnotices a, mbr_members b,s_shippingnotice_status c,s_delivery_type d "
			+ " WHERE a.member_id = b.id and a.status = c.id  and a.delivery_type= d.id  ";
		String temp = request.getParameter("sn_code");
		if( !temp.equals("")) {
			sql += " AND a.barcode = '" + temp + "' ";
		}
		temp = request.getParameter("order_number");
		if( !temp.equals("")) {
			sql += " AND a.order_number = '" + temp + "' ";
		}
		temp = request.getParameter("cardid");
		if( !temp.equals("")) {
			sql += " AND b.card_id = '" + temp + "' ";
		}
		temp = request.getParameter("member_name");
		if( !temp.equals("")) {
			sql += " AND b.name = '" + temp.trim() + "' ";
		}
		
		temp = request.getParameter("check_person");
		if( !temp.equals("")) {
			sql += " AND e.user_name = '" + temp.trim() + "' ";
		}
		
		temp = request.getParameter("status");
		if( !temp.equals("")) {
			sql += " AND a.status = " + temp;
		}
		temp = request.getParameter("release_date_from");
		if( !temp.equals("")) {
			sql += " AND a.create_date >= to_date('" + temp + "','YYYY-MM-DD') ";
		}
		temp = request.getParameter("release_date_to");
		if( !temp.equals("")) {
			sql += " AND a.create_date < to_date('" + temp + "','YYYY-MM-DD') +1 ";
		}
		
		//add by user 2006-04-28
		temp = request.getParameter("print_date_from");
		if( !temp.equals("")) {
			sql += " AND a.print_date >= to_date('" + temp + "','YYYY-MM-DD') ";
		}
		temp = request.getParameter("print_date_to");
		if( !temp.equals("")) {
			sql += " AND a.print_date < to_date('" + temp + "','YYYY-MM-DD') +1 ";
		}
		
		temp = request.getParameter("delivery_type");
		if( !temp.equals("")) {
			sql += " AND a.delivery_type = " + temp + " ";
		}
		temp = request.getParameter("pay_type");
		if( !temp.equals("")) {
			sql += " AND a.payment_method = " + temp + " ";
		}
		temp = request.getParameter("itemCode");
		if( !temp.equals("")) {
			sql += " AND a.id in ( SELECT sn_id FROM ord_shippingnotice_lines where sku_id = " + temp + ") ";
		}
		sql += " order by a.create_date desc ";
		
		return sql;
	}
}
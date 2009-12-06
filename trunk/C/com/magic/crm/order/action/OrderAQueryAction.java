/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.common.PageAttribute;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.util.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.*;
import java.sql.*;
/**
 * @author Water
 * 
 * 订单高级查询
 */
public class OrderAQueryAction extends DispatchAction {
	private Logger log = Logger.getLogger(OrderAQueryAction.class);
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
		OrderForm data = (OrderForm) form;
		Connection conn = DBManager.getConnection();
		// 取得查询条件下拉框的数据
		try {
			// 取得订单状态
			data.setStatusList(ConfigDAO.listKeyValue(conn,"S_ORDER_STATUS"));
			// 取得订单来源
			data.setPrTypes(ConfigDAO.listKeyValue(conn,"S_PR_TYPE"));
			// 取得经办人
			data.setCreatorList(ConfigDAO.listOrgPersonsKeyValue(conn,"ORG_PERSONS"));
			// 取得订单类型
			data.setTypes(ConfigDAO.listKeyValue(conn,"s_order_category"));
			// 取得发送方式
			data.setDeliverys(ConfigDAO.listKeyValue(conn,"s_delivery_type"));
			// 取得支付方式
			data.setPayments(ConfigDAO.listKeyValue(conn,"s_payment_method"));
			// 取的订单行状态
			data.setLineStatusList(ConfigDAO.listKeyValue(conn,"S_ORDER_LINE_STATUS"));
			
			
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
		OrderForm order = (OrderForm) form;
		Connection conn = DBManager.getConnection();
		try {
			PageAttribute pageUtil = new PageAttribute(20);
			String sql = combineSQL(request);
			log.info(sql);
			pageUtil.setSql(sql);
			pageUtil.setRecordCount(OrderDAO.queryOrderListCount(conn,sql));
			order.setPageAttribute(pageUtil);
			request.setAttribute("order_list",OrderDAO.queryOrderList(conn,sql,pageUtil.getFrom(),pageUtil.getTo()));
			request.getSession().setAttribute("order_page",pageUtil);
			
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
		OrderForm order = (OrderForm) form;
		Connection conn = DBManager.getConnection();
		try {
			PageAttribute pageUtil = (PageAttribute)request.getSession().getAttribute("order_page");
			String pageNo = request.getParameter("pageNo");
			pageUtil.setPageNo(Integer.parseInt(pageNo));
			order.setPageAttribute(pageUtil);
			request.setAttribute("order_list",OrderDAO.queryOrderList(conn,pageUtil.getSql(),pageUtil.getFrom(),pageUtil.getTo()));
			request.getSession().setAttribute("order_page",pageUtil);
			
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
		String sql = " SELECT a.*,b.name as mb_name, b.card_id ,c.name as status_name,d.name as pr_type_name, e.name as category_name,f.name as creator_name "
			+ " FROM ORD_HEADERS a, mbr_members b,s_order_status c,s_pr_type d,s_order_category e, org_persons f "
			+ " WHERE a.buyer_id = b.id and a.status = c.id and a.pr_type = d.id and a.order_category = e.id and a.creator_id = f.id and (a.order_type<>15) ";//(updated by user 2008-02-21)扣除团体订单
		String temp = request.getParameter("order_number");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.so_number = '" + temp + "' ";
		}
		temp = request.getParameter("cardid");
		if( temp !=null && !temp.equals("")) {
			sql += " AND b.card_id = '" + temp + "' ";
		}
		temp = request.getParameter("member_name");
		if( temp !=null && !temp.equals("")) {
			sql += " AND b.name = '" + temp.trim() + "' ";
		}
		//temp = request.getParameter("gift_number");
		//if( temp !=null && !temp.equals("")) {
			//sql += " AND a.gift_number  = '" + temp.trim() + "' ";
		//}
		temp = request.getParameter("goods_sum_from");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.goods_fee  >= " + temp.trim();
		}
		temp = request.getParameter("goods_sum_to");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.goods_fee   <= " + temp.trim();
		}
		temp = request.getParameter("order_sum_from");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.order_sum >= " + temp.trim();
		}
		temp = request.getParameter("order_sum_to");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.order_sum <= " + temp.trim();
		}
		temp = request.getParameter("prTypeId");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.pr_type = " + temp.trim();
		}
		temp = request.getParameter("categoryId");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.order_category = " + temp;
		}
		temp = request.getParameter("statusId");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.status = " + temp;
		}
		temp = request.getParameter("release_date_from");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.release_date >= to_date('" + temp + "','YYYY-MM-DD') ";
		}
		temp = request.getParameter("release_date_to");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.release_date < to_date('" + temp + "','YYYY-MM-DD') +1 ";
		}
		temp = request.getParameter("creatorId");
		if( temp !=null && !temp.equals("")&&!temp.equals("0") ) {
			sql += " AND a.creator_id = " + temp + " ";
		}
		temp = request.getParameter("deliveryTypeId");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.delivery_type = " + temp + " ";
		}
		temp = request.getParameter("paymentTypeId");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.payment_method = " + temp + " ";
		}
		temp = request.getParameter("msc");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.msc_code = '" + temp + "' ";
		}
		//根据订单行状态查询
		temp = request.getParameter("lineStatusId");
		if( temp !=null && !temp.equals("")) {
			sql += " AND a.id in ( SELECT order_id FROM ord_lines where status='"+ temp +"' ) ";
		}
		//如果SKU不为空，则按照SKU查询；否则结合itemCode,colorCode,sizeCode,查询
		String sku = request.getParameter("itemSku");		
		String itmCode = request.getParameter("itemCode");
		String colorCode = request.getParameter("colorCode");
		String sizeCode = request.getParameter("sizeCode");
		if (sku != null && sku.length()>0)
		{
			sql += " AND a.id in ( SELECT order_id FROM ord_lines where sku_id in (select sku_id from prd_item_sku where itm_barcode='" + sku + "')) ";
		}
		else if((itmCode !=null && itmCode.length()>0) 
				|| (colorCode != null && colorCode.length()>0) 
				|| (sizeCode != null && sizeCode.length()>0))
		{
			String tempSql = "select sku_id from prd_item_sku where ";
			Boolean hasCond = false;
			if( itmCode !=null && !itmCode.equals("")) {
				tempSql += "itm_code='"+ itmCode.trim()+"'";
				hasCond = true;
			}
			if (colorCode != null && colorCode.length()>0)
			{
				if(hasCond) 
					tempSql += " and ";
				else
					hasCond = true;
				tempSql += "color_code='"+ colorCode.trim()+"'";
			}
			if(sizeCode !=null && sizeCode.length()>0)
			{
				if(hasCond) 
					tempSql += " and ";
				else
					hasCond = true;
				tempSql += "size_code='"+ sizeCode.trim()+"'";
			}
			if(hasCond)
				sql += " AND a.id in ( SELECT order_id FROM ord_lines where sku_id in ("+ tempSql +")) ";
			
		}
		sql += " order by a.release_date desc ";
		
		return sql;
	}
}
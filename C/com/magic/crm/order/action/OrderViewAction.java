/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.sql.Connection;
import com.magic.crm.common.Constants;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebAction;
import com.magic.crm.common.WebForm;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.common.DBOperation;

/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OrderViewAction extends WebAction {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magic.crm.common.WebAction#execute(com.magic.crm.common.DBOperation,
	 *      com.magic.crm.common.WebForm)
	 */
	protected String execute(HttpServletRequest request,HttpServletResponse response,DBOperation db, WebForm form) throws Exception {
	    HttpSession session = request.getSession();
        User user = new User();
        user = (User) session.getAttribute("user");
        Connection conn = null;
		//从session中清掉临时购物篮
		request.getSession(true).
			removeAttribute(Constants.TEMPORARY_ORDER);
		
		OrderForm order = (OrderForm)form;
		
		
		// 得到会员信息
		OrderDAO.getMemberInfo(db,order);
		// 得到订单头信息
		OrderDAO.getOrderHeadersInfo(db, order);
		//order.setOrderOwe(order.getPayable() - order.getOrderUse());
		// 得到订单产品信息
		OrderDAO.getOrderLinesInfo(db, order);
		// 是否有换货单
		//OrderDAO.isChanged(db.conn, order);
		//是否有退货单
		//OrderDAO.isReturned(db.conn, order);
		//是否有补货单
		//OrderDAO.isSupply(db.conn, order);
		//得到联盟信息
		//OrderDAO.viewUnionInfo(db.conn, order);
		if("cancelModify".equalsIgnoreCase(order.getActionType())) {
			//取消修改
			OrderDAO.recoverOrderStatus(db, order.getOrderId(), Integer.parseInt(user.getId()));
			
			//记录取消修改人的信息 
			//OrderDAO.recordCancelInfo(db.conn, order, Integer.parseInt(user.getId()));
			
			order.setStatusId(0);
			order.setStatusName("新创建");
			order.getCart().getOrder().setStatusId(0);
		}
		
		return "success";
	}
}
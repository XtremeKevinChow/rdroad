/*
 * Created on 2005-2-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.magic.crm.order.bo.TicketBO;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.LogicUtility;
import com.magic.crm.common.WebAction;
import com.magic.crm.common.WebForm;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.Message;
/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OrderCancelAction extends WebAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magic.crm.common.WebAction#execute(com.magic.crm.common.DBOperation,
	 *      com.magic.crm.common.WebForm)
	 */
	protected String execute(HttpServletRequest request,HttpServletResponse response,DBOperation db, WebForm form) throws Exception {
		OrderForm order = ((OrderForm) form);
		// 从session中取操作人员信息
        User user = (User)request.getSession().getAttribute("user"); 
        if(user == null) {
			Message.setErrorMsg(request,"您的操作时间超时，当前操作人员信息丢失！");
			return "error";
        } else {
        	order.setCreatorId(LogicUtility.parseInt(user.getId(), 0));
        }
        
		// 得到订单头信息
		OrderDAO.getOrderHeadersInfo(db, order);

		// 订单状态是否可以被取消
		if (!order.getCart().getOrder().isCancelable()) {
			// 不能取消
			Message.setErrorMsg(request,"订单" + order.getCart().getOrder().getOrderNumber() + "状态为"
					+ order.getStatusName() + "，不能执行取消动作！");
			return "error";
		}

		// 取消订单
		int nResult = OrderDAO.cancelOrder(db, order);

		if (nResult != 1) {
			// 取消失败
			throw new Exception("取消订单" + order.getCart().getOrder().getOrderNumber()
					+ "调用数据库存储过程失败！");
		}

		// 修改订单状态
		//nResult = OrderDAO.updateOrderStatus(db, order.getOrderId(), -1);
		//if (nResult != 1) {
			// 取消失败
			//throw new Exception("取消订单" + order.getCart().getOrder().getOrderNumber() + "修改订单状态失败！");
		//}
		
		// 返还礼券(add by user 2006-12-08 20:44)
		//TicketBO bo = new TicketBO(db.conn);
		//bo.cancelTicket(order);
		//返还次数
		//OrderDAO.updateRecruitMember(db.conn, order, -1);
		
		Message.setMessage(request,"订单" + order.getCart().getOrder().getOrderNumber() + "取消成功！");
		return "message";
	}
}
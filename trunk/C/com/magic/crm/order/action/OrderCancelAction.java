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
		// ��session��ȡ������Ա��Ϣ
        User user = (User)request.getSession().getAttribute("user"); 
        if(user == null) {
			Message.setErrorMsg(request,"���Ĳ���ʱ�䳬ʱ����ǰ������Ա��Ϣ��ʧ��");
			return "error";
        } else {
        	order.setCreatorId(LogicUtility.parseInt(user.getId(), 0));
        }
        
		// �õ�����ͷ��Ϣ
		OrderDAO.getOrderHeadersInfo(db, order);

		// ����״̬�Ƿ���Ա�ȡ��
		if (!order.getCart().getOrder().isCancelable()) {
			// ����ȡ��
			Message.setErrorMsg(request,"����" + order.getCart().getOrder().getOrderNumber() + "״̬Ϊ"
					+ order.getStatusName() + "������ִ��ȡ��������");
			return "error";
		}

		// ȡ������
		int nResult = OrderDAO.cancelOrder(db, order);

		if (nResult != 1) {
			// ȡ��ʧ��
			throw new Exception("ȡ������" + order.getCart().getOrder().getOrderNumber()
					+ "�������ݿ�洢����ʧ�ܣ�");
		}

		// �޸Ķ���״̬
		//nResult = OrderDAO.updateOrderStatus(db, order.getOrderId(), -1);
		//if (nResult != 1) {
			// ȡ��ʧ��
			//throw new Exception("ȡ������" + order.getCart().getOrder().getOrderNumber() + "�޸Ķ���״̬ʧ�ܣ�");
		//}
		
		// ������ȯ(add by user 2006-12-08 20:44)
		//TicketBO bo = new TicketBO(db.conn);
		//bo.cancelTicket(order);
		//��������
		//OrderDAO.updateRecruitMember(db.conn, order, -1);
		
		Message.setMessage(request,"����" + order.getCart().getOrder().getOrderNumber() + "ȡ���ɹ���");
		return "message";
	}
}
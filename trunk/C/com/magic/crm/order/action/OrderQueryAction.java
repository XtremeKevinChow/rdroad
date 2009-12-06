/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebAction;
import com.magic.crm.common.WebForm;
import com.magic.crm.member.entity.Member;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import java.util.Collection;
import java.util.ArrayList;

/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OrderQueryAction extends WebAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magic.crm.common.WebAction#execute(com.magic.crm.common.DBOperation,
	 *      com.magic.crm.common.WebForm)
	 */
	protected String execute(HttpServletRequest request,HttpServletResponse response,DBOperation db, WebForm form) throws Exception {
		OrderForm order = (OrderForm) form;
		Collection coll = new ArrayList();
		// �����в�ѯ����
		String cardId = request.getParameter("cardId");
		
		/** �����callcenterϵͳ����sessin��ȡ����Ա��Ϣ�����û����ʾ��¼������� * */
        String iscallcenter = request.getParameter("iscallcenter");
        if (iscallcenter != null && iscallcenter.equals("1")) {
            CallCenterHander hander = new CallCenterHander(request
                    .getSession());
            if (hander.isOnService()) {
                Member mb = hander.getServicedMember();
                cardId = mb.getCARD_ID();
                order.setMbName(mb.getNAME());
            } else {
                /*ControlledError ctlErr = new ControlledError();
                ctlErr.setErrorTitle("��������");
                ctlErr.setErrorBody("û�з��������������");
                request.setAttribute(Constants.ERROR_KEY, ctlErr);
                return "controlledError";*/
            	request.setAttribute("list", coll);
                return "success";

            }
        }
        
		
		if( cardId!=null && !cardId.equals("")) {
			order.setCardId(cardId);
			OrderDAO.listOrder(db, order, coll);
		} else {
			if( (order.getMbName()!= null && !order.getMbName().equals(""))
					||(order.getOrderNumber()!= null && !order.getOrderNumber().equals(""))
					||(order.getTaobaoWangId() != null && !order.getTaobaoWangId().equals(""))) {
				OrderDAO.listOrder(db, order, coll);
			}
		}
		
		request.setAttribute("list", coll);
		return "success";
	}

}
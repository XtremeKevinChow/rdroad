/*
 * Created on 2006-12-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.action;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.common.Constants;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.LogicUtility;


import com.magic.crm.order.bo.TicketBO;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class FinStockChangeAction extends DispatchAction {
 
	
	/**
	 * ҳ���orderAddFirst�����ĳ�ʼ��ҳ��,�ӻ�Ա��Ʒ��õ���ʼ��Ʒ,�õ���Ա������Ʒ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
		
			OrderForm pageData = (OrderForm) form;

			//�������浽session��
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,pageData);
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("input");
	}
	

	/**
	 * ���Ӳ�Ʒ���빺�ﳵ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addItem(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "input";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//��session�еõ�������Ϣ
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			copyData(sessData,pageData);
			pageData.setQueryItemCode(request.getParameter("queryItemCode"));
			pageData.setQueryItemQty(
					Integer.parseInt(request.getParameter("queryItemQty")));
			
			
	
				ItemInfo ii = OrderDAO.findItem(conn, pageData.getQueryItemCode());
				/*if (ii.getItemId()<=0) {
					Message.setErrorMsg(request,"�����Ʒ������");
					//forward = "error";
				} else if (existItems(pageData.getItems(),ii)) {
					Message.setErrorMsg(request,"�����Ʒ�Ѵ��ڣ����������");
					//forward = "error";
					//return mapping.findForward(forward);
				} else {
					    
						pageData.getItems().add(ii);
					
					
			}*/
				
				OrderForm checkForm = new OrderForm();
			copyData(pageData, checkForm);
	

			//�������浽session��
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,pageData);
			
		} catch(Exception e) {
			Message.setErrorMsg(request,e.getMessage());
			forward = "error";
			//log.error(e);
			//throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * ���Ӳ�Ʒ���빺�ﳵ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteItem(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "input";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//��session�еõ�������Ϣ
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			copyData(sessData,pageData);
			
			List lst = pageData.getItems();
			int nIndex = getItemIndex(lst, pageData.getOperateId());
			if (nIndex != -1) {
				
				lst.remove(nIndex);

			}
			
			OrderForm checkForm = new OrderForm();
			copyData(pageData, checkForm);
			
		
			
			//�������浽session��
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,pageData);
			
		} catch(Exception e) {
			Message.setErrorMsg(request,e.getMessage());
			forward = "error";
			//log.error(e);
			//throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward(forward);
	}
	
	
	
	/**
	 * ˢ��ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refresh(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "input";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//��session�еõ�������Ϣ
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			copyData(sessData,pageData);
	
			
			//�������浽session��
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,pageData);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward(forward);
	}
	
	
	
	
	private int getItemIndex(List lst, int itemId) {
		for (int i = 0; i < lst.size(); i++) {
			ItemInfo ii = (ItemInfo) lst.get(i);
			//if (ii.getItemId() == itemId)
				return i;
		}

		return -1;
	}
	private int getItemIndex(List lst, String itemCode) {
		if (itemCode == null)
			return -1;
		itemCode = itemCode.trim();
		for (int i = 0; i < lst.size(); i++) {
			ItemInfo ii = (ItemInfo) lst.get(i);
			if (itemCode.equals(ii.getItemCode())&& ii.isValid())
				return i;
		}

		return -1;
	}
	
	/**
	 * �������Ĳ�Ʒ/��Ʒ�Ƿ��Ѿ������ڹ�������
	 * 
	 * @param lstItems
	 *            �������еĲ�Ʒ/��Ʒ
	 * @param temp
	 *            ��Ʒ/��Ʒ
	 * @return
	 */
	private boolean existItems(List lstItems, ItemInfo temp) {
		for (int i = 0; i < lstItems.size(); i++) {
			//if (((ItemInfo) lstItems.get(i)).getItemId() == temp.getItemId()) {
				return true;
			//}
		}

		return false;
	}
	
	private void copyData(OrderForm source, OrderForm dest) {
		// ������Ʒ
		dest.getItems().addAll(source.getItems());

	}
			
}

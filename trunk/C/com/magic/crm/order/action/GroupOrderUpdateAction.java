/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.utils.Arith;
import com.magic.crm.common.Constants;
import com.magic.crm.common.DBOperation;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.entity.ShoppingCart;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.user.entity.User;

import com.magic.crm.product.dao.ProductBaseDAO;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;
/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class GroupOrderUpdateAction extends DispatchAction {
	private Logger log = Logger.getLogger(GroupOrderAddAction.class);  
	
	/**
	 * ���޸Ķ���ʱ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateFirst(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm)form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DBOperation db = new DBOperation(conn);
			OrderDAO.getGroupOrderHeadersInfo(db,order);
			OrderDAO.getGroupOrderLinesInfo(db,order);
			
			//��������Ʒ���빺�ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			if( carts == null) {
				carts = new ShoppingCart();
				request.getSession().setAttribute("SHOPPINGCART",carts);
			}
			order.getOrgCart().setDiscount(1);// �ۿ�
			carts.setCart(new Long(order.getMbId()),order.getOrgCart().getItems());
			//�޸Ķ���״̬Ϊ�޸���
			HttpSession session = request.getSession();
	        User user = new User();
	        user = (User) session.getAttribute("user");
			int rtn = OrderDAO.modifyOrderStatus(db,order.getOrderId(), Integer.parseInt(user.getId()));
			
			if (rtn == -2) {
				Message.setErrorMsg(request,"����״̬�Ѹ��£������޸�");
				return mapping.findForward("error");
			} else if (rtn == -3) {
				Message.setErrorMsg(request,"��������");
				return mapping.findForward("error");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("step1");
	}
	/**
	 * ��ȡ���޸�ʱ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelUpdate(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm)form;
		Connection conn = null;
		HttpSession session = request.getSession();
        User user = new User();
        user = (User) session.getAttribute("user");
		try {
			conn = DBManager.getConnection();
			DBOperation db = new DBOperation(conn);
			OrderDAO.recoverOrderStatus(db,order.getOrderId(), Integer.parseInt(user.getId()));
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		Message.setMessage(request,"����״̬�ѻָ�","�鿴","order/groupOrderView.do?orderId=" + order.getOrderId());
		return mapping.findForward("success");
	}
	
	/**
	 * ��һ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSecond(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderDAO.getGroupMemberInfo(conn,order);
			OrderDAO.getAddressInfo(conn,order);
			//��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);
			
			//��ԭʼ������ȡ���ͻ���Ϣ
			DBOperation db = new DBOperation(conn);
			OrderDAO.getGroupOrderHeadersInfo(db,order);
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("step2");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refreshFirst(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderDAO.getGroupMemberInfo(conn,order);
			
			//��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);
			
			
			
			//�ӵ�ַ��ȡ�û�Ա��Ϣ
			OrderDAO.getAddressInfo(conn,order);
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("step1");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refreshSecond(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderDAO.getGroupMemberInfo(conn,order);
			
			//��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);
			
			//�ӵ�ַ��ȡ�û�Ա��Ϣ
			OrderDAO.getAddressInfo(conn,order);
			
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("step2");
	}
	
	/**
	 * �޸��Ź�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSubmit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			User user = (User)request.getSession().getAttribute("user");
			order.setCreatorId(Integer.parseInt(user.getId()));
			
			conn = DBManager.getConnection();
			//�޸Ķ������Ĳ� 1ȡ������ 2 ɾ�������� 3 ���¼��϶����� 4 ���¶���ͷ��Ϣ
			conn.setAutoCommit(false);
			// �õ��ͻ���Ϣ�����ʽ
			DBOperation db = new DBOperation(conn);
			//OrderDAO.getMemberInfo(db, order);
			OrderDAO.getAddressInfo(conn,order);
			
			order.getOrgCart().getOtherInfo().setOOSPlan(3);//�Զ���Ϊȡ��ȱ����Ʒ
			order.getOrgCart().getOtherInfo().setNeedInvoice(order.getNeedInvoice());
			order.getOrgCart().getOtherInfo().setRemark(order.getRemark()); 
			order.getOrgCart().getOrgOrder().setCategoryId(20);//�Զ���Ϊ���񶩵�
			order.getOrgCart().getOrgOrder().setPrTypeId(5);//�Զ���Ϊ�Ź�����
			order.getOrgCart().getOrgOrder().setStatusId(0);
			//��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			//order.setItems(vgoods);
			order.getOrgCart().setItems(vgoods);
			
			
			// step1 cancel order
			int ret = OrderDAO.cancelOrder(conn,order);
			if (ret <0 ) {
				conn.rollback();
				Message.setErrorMsg(request,"�޸Ķ���ʱ��������" + ret);
				return mapping.findForward("error");
			} 
			// step2 delete order lines
			OrderDAO.deleteOrderLines(conn,order.getOrderId());
			// step3 add order lines
			double goodsfee = OrderDAO.insertOrderLine(conn,order.getOrderId(),order.getOrgCart().getItems());
			// step4 update order header
			
			order.setTotalMoney(goodsfee);
			OrderDAO.updateGroupOrderHeader(conn,order);
			//���ж���
			OrderDAO.runOrder(conn, order);
			conn.commit();
			// �幺�ﳵ
			carts.deleteCart(new Long(order.getMbId()));
			
		} catch(Exception e) {
			conn.rollback();
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		Message.setMessage(request,"�����޸ĳɹ�");
		return mapping.findForward("success");
	}
	/**
	 * ȡ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelOrder(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		OrderForm order = (OrderForm)form;
		Connection conn = null;
		try {
			User user = (User)request.getSession().getAttribute("user");
			order.setCreatorId(Integer.parseInt(user.getId()));
			conn = DBManager.getConnection();
			DBOperation db = new DBOperation(conn);
			OrderDAO.cancelOrder(db,order);
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		Message.setMessage(request,"������ȡ��");
		return mapping.findForward("success");
	}
	
	public ActionForward runOrder(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		OrderForm order = (OrderForm)form;
		Connection conn = null;
		try {
			//User user = (User)request.getSession().getAttribute("user");
			//order.setCreatorId(Integer.parseInt(user.getId()));
			conn = DBManager.getConnection();
			OrderDAO.runOrder(conn,order);
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		Message.setMessage(request,"����������");
		return mapping.findForward("success");
	}
	
	
	/**
	 * ���ﳵ��������
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
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			int ret = OrderDAO.getGroupMemberInfo(conn,order);
			//��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			
			
			/*String[] datas = order.getQueryItemCode().trim().split("-");
			if(datas == null || datas.length != 3) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "���������ݵĸ�ʽ����ȷ����ȷ�ĸ�ʽ�� ����-����-��ɫ");
				return mapping.findForward("input");
			}
			
			// ͨ����Ʒ����õ���Ʒ��Ϣ
			ItemInfo ii = (ItemInfo) OrderDAO.findItem(conn, datas[0],datas[1],datas[2]); */
			String data = order.getQueryItemCode().trim();
			
			//�������λ��9,��ȥ����λ
			if(data.charAt(0)=='9') {
				data = data.substring(1);
			}
			ItemInfo ii = OrderDAO.findItem(conn,data);
			/*if(ii!=null) {
				ii.setSize_code(datas[1]);
				ii.setColor_code(datas[2]);
			}*/
			// �����Ʒ���Ϸ�
			if (!checkInputParam(request, carts, ii)) {
				return mapping.findForward("input");
			}
			
			if(ii.getSku_id()>0) {
				OrderDAO.filloGroupItemPrice(conn,ii);
			}
			if(!ii.isTruss()) {
				vgoods.add(0, ii);
			} else {
				ArrayList<ItemInfo> parts = OrderDAO.splitSet2Part(conn,ii,3);
				vgoods.addAll(0,parts);
			}
			
			
				for (int i = 0, j = vgoods.size(); i < j; i++) {
					ItemInfo item2 = (ItemInfo) vgoods.get(i);

					item2.setDiscountPrice(Arith.round(Arith.mul(item2
							.getStandardPrice(), order.getDiscount()), 2));

				}
				
				order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
				order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
				order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
				order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
				order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
				order.getOrgCart().setItems(vgoods);
				
				
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("step1");
	}
	
	/**
	 * ���ﳵ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateItem(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderDAO.getGroupMemberInfo(conn,order);
			
			//��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			//long itemid = Long.parseLong(request.getParameter("operateId"));
			int index = order.getOperateId();//getItemIndex(vgoods,order.getOperateId());
			String[] itemQtys = request.getParameterValues("itemQty");
			ItemInfo currItem = (ItemInfo) vgoods.get(index);
			currItem.setItemQty(Integer.parseInt(itemQtys[index]));
			currItem.setColor_code(request.getParameterValues("color_code")[index]);
			currItem.setSize_code(request.getParameterValues("size_code")[index]);
			OrderDAO.fillGroupItem(conn, currItem);
			
			if(!"".equals(currItem.getSet_code())) {
				for(int i=0;i<vgoods.size();i++) {
					ItemInfo i2 = (ItemInfo)vgoods.get(i);
					if(currItem.getSet_code().equals(i2.getSet_code())) {
						i2.setItemQty(currItem.getItemQty());
					}
				}
			}
			
			for (int i = 0, j = vgoods.size(); i < j; i++) {
				ItemInfo item = (ItemInfo) vgoods.get(i);

				item.setDiscountPrice(Arith.round(Arith.mul(item.getSilverPrice(), order.getDiscount()), 2));
				
				item.setGroupItemMomey(item.getDiscountPrice()*item.getItemQty());

			}
			
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("step1");
	}
	
	/**
	 * ɾ�����ﳵ��һ��
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
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			OrderDAO.getGroupMemberInfo(conn,order);
			
			//��session��ȡ���ﳵ
			ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			
			List items = cart.getCart(new Long(order.getMbId()));
			int nIndex = Integer.parseInt(request.getParameter("operateId"));
			if (nIndex != -1) {
				
				ItemInfo item = (ItemInfo)items.get(nIndex);
				if("".equals(item.getSet_code())) {
					//ɾ����ǰ��
					items.remove(nIndex);
				} else {
					
					//ɾ��������װ
					for(int i=items.size()-1;i>=0;i--) {
						ItemInfo i2 = (ItemInfo)items.get(i);
						if(item.getSet_code().equals(i2.getSet_code())) {
							items.remove(i);
						}
					}
				}
			}
			
			
			for (int i = 0, j = items.size(); i < j; i++) {
				ItemInfo item = (ItemInfo) items.get(i);

				item.setDiscountPrice(Arith.round(Arith.mul(item
						.getStandardPrice(), order.getDiscount()), 2));

			}
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(items);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("step1");
	}
	
	/**
	 * ��չ��ﳵ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward clearCart(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		OrderForm order = (OrderForm)form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			int ret = OrderDAO.getGroupMemberInfo(conn,order);
			
			//��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			vgoods.clear();
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("step1");
	}
	
	private int getItemIndex(List lst, int itemId) {
		for (int i = 0; i < lst.size(); i++) {
			ItemInfo ii = (ItemInfo) lst.get(i);
			//if (ii.getItemId() == itemId)
				return i;
		}

		return -1;
	}

	/**
	 * ������������
	 * 
	 * @param pageData
	 * @return
	 */
	private boolean checkInputParam(HttpServletRequest request,
			ShoppingCart cart, ItemInfo ii) {
		
		// ��Ʒ�Ƿ����
		if (ii==null || "".equals(ii.getItemName())) {
			Message.setErrorMsg(request, "�������Ʒ�����ڡ�");
			return false;
		}
		
		// ����Ԥ�۲�Ʒ
		if (cart.isPreSellOrder()) {
			Message.setErrorMsg(request, "�Բ��𣬹��ﳵ�������������Ʒ���������Ʒֻ�ܵ����µ���");
			return false;
		}
		
		// �����Ʒȱ�������ܹ���
		if (ii.getStockStatusName().equals("����ȱ��")||ii.getStockStatusName().equals("��ʱȱ��")) {
			Message.setErrorMsg(request, "�������Ʒȱ���������µ�");
			return false;
		}
		
		return true;
	}

	
	/**
	 * �ı��ͻ���ʽ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeDelivery(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderDAO.changeDelivery(conn,order);
			conn.commit();
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("goto3");
	}
	/**
	 * �ı丶�ʽ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changePayment(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderDAO.changePayment(conn,order);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("goto3");
	}
	
	/**
	 * �ı��ַ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeAddress(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderDAO.changeAddress(conn,order);
			conn.commit();
			//OrderDAO.getGroupOrderHeadersInfo(new DBOperation(conn),order);
			//System.out.println(order.getOrderNumber()+"*****"+order.getOrgCart().getOrgOrder().getOrderNumber());
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("goto3");
	}
	
	/**
	 * �ı��ۿ�(add by user 2005-12-29 10:31)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeDiscount(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderDAO.getGroupMemberInfo(conn,order);
			
			//��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			
			for (int i = 0, j = vgoods.size(); i < j; i++) {
				ItemInfo item = (ItemInfo) vgoods.get(i);
				
				item.setDiscountPrice(Arith.round(Arith.mul(item.getSilverPrice(), order.getDiscount()), 2));
				
				 item.setGroupItemMomey(Arith.mul(item.getDiscountPrice(),
				 item.getItemQty()));
				
			}
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);// ���¹��ﳵ
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("step1");
	}
	
	
}

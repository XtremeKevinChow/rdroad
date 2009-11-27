/*
 * Created on 2005-2-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import org.apache.log4j.*;
import javax.servlet.http.*;

import com.magic.crm.common.Constants;
import com.magic.crm.common.DBOperation;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.*;

import java.sql.*;
import java.util.*;

import com.magic.utils.Arith;

import com.magic.crm.order.form.*;
import com.magic.crm.order.entity.*;
import com.magic.crm.product.dao.ProductBaseDAO;

/**
 * @author Administrator
 * 
 * TODO 99read
 */
public class GroupOrderAddAction extends DispatchAction {
	/**
	 * ���嶩�����ӵĵڶ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSecond(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// ��Ա��Ϣ
			int ret = OrderDAO.getGroupMemberInfo(conn, order);
			if (ret == -1) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "�������Ա�Ų�����");
				return mapping.findForward("error");
			}
			// ��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			if (carts == null) {
				carts = new ShoppingCart();
				request.getSession().setAttribute("SHOPPINGCART", carts);
			}
	
			// ���ݴ����Ʒ���빺�ﳵ
			Vector vgoods = new Vector();
			//(Vector) OrderDAO.initCart(conn, order.getMbId());
			carts.setCart(new Long(order.getOrgCart().getOrgMember().getID()),
					vgoods);
	
			order.getOrgCart().setItems(vgoods);
			
			
			
			//String item_code = order.getQueryItemCode();
			//if (item_code!=null && !"".equals(item_code));
			//order.setSizes(ProductBaseDAO.listSize(conn, item_code));
	
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	
		return mapping.findForward("step2");
	}

	private Logger log = Logger.getLogger(GroupOrderAddAction.class);

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
	 * modified by user 2005-12-28 13:00 ���ﳵ��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			// �õ����ݿ�����
			conn = DBManager.getConnection();
	       
			int ret = OrderDAO.getGroupMemberInfo(conn, order);
			if (ret == -1) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "�û�Ա�Ų�����");
				return mapping.findForward("error");
			}
	
			// ��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
	
			/*String[] datas = order.getQueryItemCode().trim().split("-");
			if(datas == null || datas.length != 3) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "���������ݵĸ�ʽ����ȷ����ȷ�ĸ�ʽ�� ����-����-��ɫ");
				return mapping.findForward("input");
			}*/
			String data = order.getQueryItemCode().trim();
			
			//�������λ��9,��ȥ����λ
			if(data.charAt(0)=='9') {
				data = data.substring(1);
			}
			
			// ͨ����Ʒ����õ���Ʒ��Ϣ
			//ii = (ItemInfo) OrderDAO.findItem(conn, datas[0],datas[1],datas[2]); 
			ItemInfo ii = OrderDAO.findItem(conn, data);
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
				ArrayList<ItemInfo> parts = OrderDAO.splitSet2Part(conn,ii,2);
				vgoods.addAll(0,parts);
			}
			
			
			for (int i = 0, j = vgoods.size(); i < j; i++) {
				ItemInfo item2 = (ItemInfo) vgoods.get(i);
	
				item2.setDiscountPrice(Arith.round(Arith.mul(item2.getSilverPrice(), order.getDiscount()), 2));
	
			}
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);
	
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("step2");
	}

	/**
	 * ˢ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refresh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			// �õ����ݿ�����
			conn = DBManager.getConnection();
	
			String witchStock = request.getParameter("whichStock");
			int ret = OrderDAO.getGroupMemberInfo(conn, order);
			if (ret == -1) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "�û�Ա�Ų�����");
				return mapping.findForward("error");
			}
	
			// ��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
	
			
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);
	
			
			
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("step2");
	}

	/**
	 * ���嶩�����ӵĵ�һ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFirst(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String witchStock = request.getParameter("whichStock");
		if (witchStock != null && witchStock.equals("sales")) {
			request.setAttribute("whichStock", witchStock);
		}
		return mapping.findForward("step1");
	}

	/**
	 * ɾ�����ﳵ��һ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			int ret = OrderDAO.getGroupMemberInfo(conn, order);
			if (ret == -1) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "�û�Ա�Ų�����");
				return mapping.findForward("error");
			}
	
			// ��session��ȡ���ﳵ
			ShoppingCart cart = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
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
			
	
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(items);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("step2");
	}

	/**
	 * ���ﳵ��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			OrderDAO.getGroupMemberInfo(conn, order);

			// ��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List items = carts.getCart(new Long(order.getMbId()));

			
			int index = order.getOperateId();
			//int index = getItemIndex(vgoods, (int) itemid);
			String[] itemQtys = request.getParameterValues("itemQty");
			ItemInfo currItem = (ItemInfo) items.get(index);

			currItem.setItemQty(Integer.parseInt(itemQtys[index]));
			currItem.setColor_code(request.getParameterValues("color_code")[index]);
			currItem.setSize_code(request.getParameterValues("size_code")[index]);
			OrderDAO.fillGroupItem(conn, currItem);
			
			if(!"".equals(currItem.getSet_code())) {
				for(int i=0;i<items.size();i++) {
					ItemInfo i2 = (ItemInfo)items.get(i);
					if(currItem.getSet_code().equals(i2.getSet_code())) {
						i2.setItemQty(currItem.getItemQty());
					}
				}
			}
			
			// order.setItems(vgoods);//���¹��ﳵ
			for (int i = 0, j = items.size(); i < j; i++) {
				ItemInfo item = (ItemInfo) items.get(i);

				item.setDiscountPrice(Arith.round(Arith.mul(item
						.getSilverPrice(), order.getDiscount()), 2));
				
				item.setGroupItemMomey(item.getDiscountPrice()*item.getItemQty());

			}
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(items);
			
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("step2");
	}

	/**
	 * ��չ��ﳵ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward clearCart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			OrderDAO.getGroupMemberInfo(conn, order);

			// ��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			vgoods.clear();
			// order.setItems(vgoods);
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("step2");
	}

	/**
	 * ���嶩�����ӵĵ�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addThird(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			
			conn = DBManager.getConnection();
			OrderDAO.getGroupMemberInfo(conn, order);

			// ��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			//order.setItems(vgoods);
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);
			// �ӵ�ַ����ȡ�ͻ���Ϣ
			OrderDAO.getAddressInfo(conn, order);
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("step3");
	}

	/**
	 * ���嶩�����ӵ��ύ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			
			// ȡ�����ݿ�����
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderDAO.getGroupMemberInfo(conn, order);
			// ��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			System.out.println("action:" + order.getMbId());
			//order.setItems(vgoods);
			order.getOrgCart().setItems(vgoods);
			// ��session��ȡ�û�
			User user = (User) request.getSession().getAttribute("user");
			order.getOrgCart().getOrgOrder().setCreatorId(Integer.parseInt(user.getId()));
			// �ӵ�ַ����ȡ�ͻ���Ϣ
			OrderDAO.getAddressInfo(conn, order);
			
			order.getOrgCart().getOrgOrder().setCategoryId(20);// �Զ���Ϊ���񶩵�
			order.getOrgCart().getOtherInfo().setOOSPlan(3);// �Զ���Ϊȡ��ȱ����Ʒ
			order.getOrgCart().getOtherInfo().setRemark(order.getRemark());
			order.getOrgCart().getOtherInfo().setNeedInvoice(order.getNeedInvoice());
			order.getOrgCart().getOrgOrder().setPrTypeId(5);// �Զ���Ϊ�Ź�����

			OrderDAO.insertOrder(conn, order);

			// �幺�ﳵ
			carts.deleteCart(new Long(order.getMbId()));
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				OrderDAO.updateOrderStatus(new DBOperation(conn), order
						.getOrderId(), 100);
			} else {
				OrderDAO.runOrder(conn, order);
			}

			// load���µ�״̬
			// OrderDAO.getOrderHeadersInfo(new DBOperation(conn), order);

			
			conn.commit();
			Message.setMessage(request, "�����ɹ�,��������<font color=blue>"
					+ order.getOrderNumber() + "</font>");
			return mapping.findForward("success");
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			Message.setMessage(request, "����ʧ�ܣ�" + e.getMessage());

			return mapping.findForward("success");

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

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
	 * �ı��ͻ���ʽ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeDelivery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderDAO.changeDelivery(conn, order);
			conn.commit();
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("goto3");
	}

	/**
	 * �ı丶�ʽ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changePayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderDAO.changePayment(conn, order);
			conn.commit();
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("goto3");
	}

	/**
	 * �ı��ַ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderDAO.changeAddress(conn, order);
			conn.commit();
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("goto3");
	}

	/**
	 * �ı��ۿ�(add by user 2005-12-29 10:31)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeDiscount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrderForm order = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			OrderDAO.getGroupMemberInfo(conn, order);

			// ��session��ȡ���ﳵ
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));

			/*
			 * double discount =
			 * Double.parseDouble(request.getParameter("discount"));//�ۿ�
			 * discount = discount == 0.0 ? 1 : discount; if ( vgoods == null ) {
			 * Message.setErrorMsg(request, "���ﳵΪ��"); return
			 * mapping.findForward("error");
			 * 
			 *  }
			 */
			// ����ÿ����Ʒ
		

			// groupTotalMoney =
			// Arith.mul(order.getGroupTotalMoney(),discount);//�ܶ�
			// double groupTotalMoney = getMoney(vgoods, order.getDiscount());

			for (int i = 0, j = vgoods.size(); i < j; i++) {
				ItemInfo item = (ItemInfo) vgoods.get(i);
				// ���ۿۼ���������
				// if (i != j - 1) {
				// �ۿۼ�
				item.setDiscountPrice(Arith.round(Arith.mul(item.getSilverPrice(), order.getDiscount()), 2));
				// item.setItemPrice(item.getDiscountPrice());
				 item.setGroupItemMomey(Arith.mul(item.getDiscountPrice(),
				 item.getItemQty()));
				 
				// totalMoneyExceptLastItem += item.getGroupItemMomey();

				// }
				// ��һ�����Ʒ�����⴦��
				// else {
				// lastItemMoney = Arith.sub(groupTotalMoney,
				// totalMoneyExceptLastItem);
				// item.setGroupItemMomey(lastItemPrice);
				//					
				// lastItemPrice = Arith.div(lastItemMoney, item.getItemQty(),
				// 1) ;
				// item.setDiscountPrice(lastItemPrice);
				// item.setGroupItemMomey(lastItemMoney);

				// }
			}
			order.getOrgCart().setDiscount(order.getDiscount());// �ۿ�
			order.getOrgCart().setItems(vgoods);// ���¹��ﳵ
			
			
			
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				request.setAttribute("whichStock", witchStock);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward("step2");
	}

}

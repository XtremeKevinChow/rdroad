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
	 * 点修改订单时进入
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
			
			//将订购物品放入购物车
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			if( carts == null) {
				carts = new ShoppingCart();
				request.getSession().setAttribute("SHOPPINGCART",carts);
			}
			order.getOrgCart().setDiscount(1);// 折扣
			carts.setCart(new Long(order.getMbId()),order.getOrgCart().getItems());
			//修改订单状态为修改中
			HttpSession session = request.getSession();
	        User user = new User();
	        user = (User) session.getAttribute("user");
			int rtn = OrderDAO.modifyOrderStatus(db,order.getOrderId(), Integer.parseInt(user.getId()));
			
			if (rtn == -2) {
				Message.setErrorMsg(request,"订单状态已更新，不能修改");
				return mapping.findForward("error");
			} else if (rtn == -3) {
				Message.setErrorMsg(request,"其他错误");
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
	 * 点取消修改时进入
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
		Message.setMessage(request,"订单状态已恢复","查看","order/groupOrderView.do?orderId=" + order.getOrderId());
		return mapping.findForward("success");
	}
	
	/**
	 * 下一步
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
			//从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
			order.getOrgCart().setItems(vgoods);
			
			//从原始订单中取得送货信息
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
			
			//从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
			order.getOrgCart().setItems(vgoods);
			
			
			
			//从地址表取得会员信息
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
			
			//从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
			order.getOrgCart().setItems(vgoods);
			
			//从地址表取得会员信息
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
	 * 修改团购订单
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
			//修改订单分四步 1取消订单 2 删除订单行 3 重新加上订单行 4 更新订单头信息
			conn.setAutoCommit(false);
			// 得到送货信息、付款方式
			DBOperation db = new DBOperation(conn);
			//OrderDAO.getMemberInfo(db, order);
			OrderDAO.getAddressInfo(conn,order);
			
			order.getOrgCart().getOtherInfo().setOOSPlan(3);//自动设为取消缺货产品
			order.getOrgCart().getOtherInfo().setNeedInvoice(order.getNeedInvoice());
			order.getOrgCart().getOtherInfo().setRemark(order.getRemark()); 
			order.getOrgCart().getOrgOrder().setCategoryId(20);//自动设为义务订单
			order.getOrgCart().getOrgOrder().setPrTypeId(5);//自动设为团购订单
			order.getOrgCart().getOrgOrder().setStatusId(0);
			//从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			//order.setItems(vgoods);
			order.getOrgCart().setItems(vgoods);
			
			
			// step1 cancel order
			int ret = OrderDAO.cancelOrder(conn,order);
			if (ret <0 ) {
				conn.rollback();
				Message.setErrorMsg(request,"修改订单时产生错误" + ret);
				return mapping.findForward("error");
			} 
			// step2 delete order lines
			OrderDAO.deleteOrderLines(conn,order.getOrderId());
			// step3 add order lines
			double goodsfee = OrderDAO.insertOrderLine(conn,order.getOrderId(),order.getOrgCart().getItems());
			// step4 update order header
			
			order.setTotalMoney(goodsfee);
			OrderDAO.updateGroupOrderHeader(conn,order);
			//运行订单
			OrderDAO.runOrder(conn, order);
			conn.commit();
			// 清购物车
			carts.deleteCart(new Long(order.getMbId()));
			
		} catch(Exception e) {
			conn.rollback();
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		Message.setMessage(request,"订单修改成功");
		return mapping.findForward("success");
	}
	/**
	 * 取消订单
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
		Message.setMessage(request,"订单已取消");
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
		Message.setMessage(request,"订单已运行");
		return mapping.findForward("success");
	}
	
	
	/**
	 * 购物车增加行数
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
			//从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			
			
			
			/*String[] datas = order.getQueryItemCode().trim().split("-");
			if(datas == null || datas.length != 3) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "您输入数据的格式不正确，正确的格式是 货号-尺码-颜色");
				return mapping.findForward("input");
			}
			
			// 通过产品代码得到产品信息
			ItemInfo ii = (ItemInfo) OrderDAO.findItem(conn, datas[0],datas[1],datas[2]); */
			String data = order.getQueryItemCode().trim();
			
			//如果是首位是9,则去掉首位
			if(data.charAt(0)=='9') {
				data = data.substring(1);
			}
			ItemInfo ii = OrderDAO.findItem(conn,data);
			/*if(ii!=null) {
				ii.setSize_code(datas[1]);
				ii.setColor_code(datas[2]);
			}*/
			// 输入产品不合法
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
				order.getOrgCart().setDiscount(order.getDiscount());// 折扣
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
	 * 购物车更新行数
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
			
			//从session中取购物车
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
			
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
			order.getOrgCart().getOrgOrder().setOrderNumber(order.getOrderNumber());
			order.getOrgCart().getOrgOrder().setOrderId(order.getOrderId());
			order.getOrgCart().getOrgOrder().setPrTypeId(order.getPrTypeId());
			order.getOrgCart().getOrgOrder().setPrTypeName(order.getPrTypeName());
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
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
	 * 删除购物车的一行
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
			
			//从session中取购物车
			ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("SHOPPINGCART");
			
			List items = cart.getCart(new Long(order.getMbId()));
			int nIndex = Integer.parseInt(request.getParameter("operateId"));
			if (nIndex != -1) {
				
				ItemInfo item = (ItemInfo)items.get(nIndex);
				if("".equals(item.getSet_code())) {
					//删除当前行
					items.remove(nIndex);
				} else {
					
					//删除整个套装
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
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
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
	 * 清空购物车
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
			
			//从session中取购物车
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
	 * 检测输入的数据
	 * 
	 * @param pageData
	 * @return
	 */
	private boolean checkInputParam(HttpServletRequest request,
			ShoppingCart cart, ItemInfo ii) {
		
		// 产品是否存在
		if (ii==null || "".equals(ii.getItemName())) {
			Message.setErrorMsg(request, "您输入产品不存在。");
			return false;
		}
		
		// 已有预售产品
		if (cart.isPreSellOrder()) {
			Message.setErrorMsg(request, "对不起，购物车中已有虚拟库存产品，虚拟库存产品只能单独下单。");
			return false;
		}
		
		// 如果产品缺货，不能购买
		if (ii.getStockStatusName().equals("永久缺货")||ii.getStockStatusName().equals("暂时缺货")) {
			Message.setErrorMsg(request, "您输入产品缺货，不能下单");
			return false;
		}
		
		return true;
	}

	
	/**
	 * 改变送货方式
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
	 * 改变付款方式
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
	 * 改变地址
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
	 * 改变折扣(add by user 2005-12-29 10:31)
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
			
			//从session中取购物车
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
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
			order.getOrgCart().setItems(vgoods);// 更新购物车
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward("step1");
	}
	
	
}

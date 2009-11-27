package com.magic.crm.order.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.common.Constants;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.LogicUtility;
import com.magic.crm.member.dao.MemberAddressDAO;
import com.magic.crm.order.bo.TicketBO;
import com.magic.crm.order.dao.OrderChangeDAO;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.dao.OrderGiftsDAO;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.entity.Order;
import com.magic.crm.order.entity.ShoppingCart2;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.promotion.dao.GroupPricesDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

public class OrderChangeAction extends DispatchAction {
	
	private Logger log = Logger.getLogger(OrderChangeAction.class); 
	/**
	 * 初始化退换货页面，购物车的gifts,items,allGifts分别存放原始产品，换货产品和退货产品
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
			conn.setAutoCommit(false);
			OrderForm pageData = (OrderForm) form;
			
			//从数据库中取订单信息
			// 得到订单头信息、会员信息和得到订单产品信息顺序不能变更
			// 得到订单头信息
			OrderDAO.getOrderHeadersInfo(new DBOperation(conn), pageData);
			
			// 得到会员信息
			//OrderDAO.getMemberInfo(conn, pageData);
			
			// 得到订单产品信息
			HttpSession session = request.getSession();
	        User user = new User();
	        user = (User) session.getAttribute("user");
			
			OrderChangeDAO.getOrderLinesInfo2(new DBOperation(conn), pageData);
			
			//订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,pageData);
			
		} catch(Exception e) {
		    conn.rollback();
			log.error("exception",e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * 更改购物车中产品
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
		String forward = "success";
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//从session中得到订单信息
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData,pageData);
			//得到购物车
			ShoppingCart2 cart = pageData.getCart();
			
			List items = cart.getItems();
			
			int nIndex = pageData.getOperateId();
			String color_code = request.getParameterValues("color_code")[nIndex];
			String size_code = request.getParameterValues("size_code")[nIndex];
			int nQty = Integer.parseInt(request.getParameterValues("itemQty")[nIndex]);
			
			if (nIndex != -1) {
				ItemInfo currItem = (ItemInfo) items.get(nIndex);
				currItem.setColor_code(color_code);
				currItem.setSize_code(size_code);
				currItem.setItemQty(nQty);
				
				int ret = OrderChangeDAO.fillItem(conn, currItem);
				if(ret <0) {
					Message.setErrorMsg(request, "对应sku不存在");
					return mapping.findForward("success");
				}
				if(nQty > currItem.getMax_count()) {
					Message.setErrorMsg(request, "换货数量不能超过订购数量");
					currItem.setItemQty(currItem.getMax_count());
					return mapping.findForward("success");
				}
				
				
			}
			
			//订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,pageData);
			
		} catch(Exception e) {
			Message.setErrorMsg(request,e.getMessage());
			forward = "error";
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 删除购物车中产品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteItem1(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "success";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//从session中得到订单信息
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData,pageData);
			//得到购物车
			ShoppingCart2 cart = pageData.getCart();
			
			int nIndex = pageData.getOperateId();
			if (nIndex != -1) {
				ItemInfo item = (ItemInfo) cart.getItems().get(nIndex);
				
				cart.addGift(item);
				cart.removeItem(nIndex);
			}
			
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
	 * 删除购物车中产品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteItem2(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "success";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//从session中得到订单信息
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData,pageData);
			//得到购物车
			ShoppingCart2 cart = pageData.getCart();
			
			int nIndex = pageData.getOperateId();
			if (nIndex != -1) {
				ItemInfo item = (ItemInfo) cart.getAllGifts().get(nIndex);
				
				cart.addGift(item);
				cart.getAllGifts().remove(nIndex);
			}
			
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
	 * 换货购物车中产品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeItem(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "success";
		//String delItemId = request.getParameter("delItemId");
		//String sellType = request.getParameter("sellType");
		//String sectionType = request.getParameter("sectionType");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//从session中得到订单信息
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData,pageData);
			//得到购物车
			ShoppingCart2 cart = pageData.getCart();
			
			int nIndex = pageData.getOperateId();
			
			if (nIndex != -1) {
				ItemInfo item = (ItemInfo) cart.getGifts().get(nIndex);
				//item.setStatus("换货");
				cart.addItem(item);
				cart.removeGift(nIndex);
				//cart.removeItem(nIndex);
			}
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
	 * 退货购物车中产品
	 * @paraum mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward returnItem(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "success";
		//String delItemId = request.getParameter("delItemId");
		//String sellType = request.getParameter("sellType");
		//String sectionType = request.getParameter("sectionType");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//从session中得到订单信息
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData,pageData);
			//得到购物车
			ShoppingCart2 cart = pageData.getCart();
			
			int nIndex = pageData.getOperateId();
			if (nIndex != -1) {
				ItemInfo item = (ItemInfo) cart.getGifts().get(nIndex);
				
				cart.getAllGifts().add(item);
				cart.removeGift(nIndex);
			}
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
	 * 换货提交
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeSubmit(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "message";
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//从session中得到订单信息
			OrderForm sessionData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			
			ShoppingCart2 cart = sessionData.getCart();
			cart.getGifts().clear();
			if (cart.isCartOOS()) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"缺货产品不能换货,请更换其他有货产品");
				return mapping.findForward("success");	
			}
			
			
			Order order = cart.getOrder();
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				Message.setErrorMsg(request,"您的操作时间超时，当前操作人员信息丢失！");
				return mapping.findForward("error");
			} else {
				order.setCreatorId(LogicUtility.parseInt(user.getId(), 0));
			}
			
			conn.setAutoCommit(false);
			OrderChangeDAO.changeOrder(conn, sessionData);
			conn.commit();
			
			Message.setMessage(request,
					"换货成功！换货订单号为："+sessionData.getOrderNumber());
			// 删除session
			request.getSession(true).removeAttribute(Constants.TEMPORARY_ORDER);
			
		} catch(Exception e) {
			Message.setErrorMsg(request,e.getMessage());
			forward = "error";
			e.printStackTrace();
			conn.rollback();
			//throw e;
		} finally {
			try { conn.setAutoCommit(true);conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 换货确认
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeConfirm(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "message";
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			OrderForm data = (OrderForm)form;
			OrderChangeDAO.confirmOrder(conn,data.getOrderId());
			
			Message.setMessage(request,
					"换货已确认,可配货发货");
		} catch(Exception e) {
			Message.setErrorMsg(request,e.getMessage());
			forward = "error";
			log.error(e);
			conn.rollback();
			//throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 换货单取消
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeCancel(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String forward = "message";
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderForm data = (OrderForm)form;
			int ret = OrderChangeDAO.cancelOrder(conn,data.getOrderId());
			if (ret == -1001) {
				Message.setMessage(request, "不是换货定单,不能取消换货");
				
			} else if (ret == -1002) {
				Message.setMessage(request, "已经确认换货,不能取消换货");
				
			} else if (ret == 0) {
				Message.setMessage(request,"换货单已取消");
				conn.commit();
			} else if (ret == -1003) {
				Message.setMessage(request, "调用erp过程错误,不能取消 ");
				conn.rollback();
			} else if (ret == -1 ) {
				Message.setMessage(request, "未知错误 ");
				conn.rollback();
			}
			
		} catch(Exception e) {
			Message.setErrorMsg(request,e.getMessage());
			forward = "error";
			log.error(e);
			conn.rollback();
			//throw e;
		} finally {
			try { conn.setAutoCommit(true);conn.close(); } catch(Exception e) {}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 更改地址
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
		String forward = "success";
		//String delItemId = request.getParameter("delItemId");
		//String sellType = request.getParameter("sellType");
		//String sectionType = request.getParameter("sectionType");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			OrderForm pageData = (OrderForm) form;
			//从session中得到订单信息
			OrderForm sessData = 
				(OrderForm) request.getSession().getAttribute(Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData,pageData);
			
			MemberAddressDAO.updateAddress(conn, pageData.getMbId(),
					pageData.getReceiptorAddressId());
			
			// 得到送货信息、付款方式
			OrderDAO.getMemberInfo(new DBOperation(conn), pageData);
			
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
}

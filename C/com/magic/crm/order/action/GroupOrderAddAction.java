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
	 * 团体订单增加的第二步
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
			// 会员信息
			int ret = OrderDAO.getGroupMemberInfo(conn, order);
			if (ret == -1) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "该团体会员号不存在");
				return mapping.findForward("error");
			}
			// 从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			if (carts == null) {
				carts = new ShoppingCart();
				request.getSession().setAttribute("SHOPPINGCART", carts);
			}
	
			// 将暂存家礼品加入购物车
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
	 * modified by user 2005-12-28 13:00 购物车增加行数
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
			// 得到数据库连接
			conn = DBManager.getConnection();
	       
			int ret = OrderDAO.getGroupMemberInfo(conn, order);
			if (ret == -1) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "该会员号不存在");
				return mapping.findForward("error");
			}
	
			// 从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
	
			/*String[] datas = order.getQueryItemCode().trim().split("-");
			if(datas == null || datas.length != 3) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "您输入数据的格式不正确，正确的格式是 货号-尺码-颜色");
				return mapping.findForward("input");
			}*/
			String data = order.getQueryItemCode().trim();
			
			//如果是首位是9,则去掉首位
			if(data.charAt(0)=='9') {
				data = data.substring(1);
			}
			
			// 通过产品代码得到产品信息
			//ii = (ItemInfo) OrderDAO.findItem(conn, datas[0],datas[1],datas[2]); 
			ItemInfo ii = OrderDAO.findItem(conn, data);
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
				ArrayList<ItemInfo> parts = OrderDAO.splitSet2Part(conn,ii,2);
				vgoods.addAll(0,parts);
			}
			
			
			for (int i = 0, j = vgoods.size(); i < j; i++) {
				ItemInfo item2 = (ItemInfo) vgoods.get(i);
	
				item2.setDiscountPrice(Arith.round(Arith.mul(item2.getSilverPrice(), order.getDiscount()), 2));
	
			}
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
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
	 * 刷新
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
			// 得到数据库连接
			conn = DBManager.getConnection();
	
			String witchStock = request.getParameter("whichStock");
			int ret = OrderDAO.getGroupMemberInfo(conn, order);
			if (ret == -1) {
				request.setAttribute(Constants.LOGIC_MESSAGE, "该会员号不存在");
				return mapping.findForward("error");
			}
	
			// 从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
	
			
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
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
	 * 团体订单增加的第一步
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
	 * 删除购物车的一行
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
				request.setAttribute(Constants.LOGIC_MESSAGE, "该会员号不存在");
				return mapping.findForward("error");
			}
	
			// 从session中取购物车
			ShoppingCart cart = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
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
			
	
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
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
	 * 购物车更新行数
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

			// 从session中取购物车
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
			
			// order.setItems(vgoods);//更新购物车
			for (int i = 0, j = items.size(); i < j; i++) {
				ItemInfo item = (ItemInfo) items.get(i);

				item.setDiscountPrice(Arith.round(Arith.mul(item
						.getSilverPrice(), order.getDiscount()), 2));
				
				item.setGroupItemMomey(item.getDiscountPrice()*item.getItemQty());

			}
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
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
	 * 清空购物车
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

			// 从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			vgoods.clear();
			// order.setItems(vgoods);
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
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
	 * 团体订单增加的第三步
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

			// 从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			//order.setItems(vgoods);
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
			order.getOrgCart().setItems(vgoods);
			// 从地址簿中取送货信息
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
	 * 团体订单增加的提交
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
			
			// 取得数据库连接
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderDAO.getGroupMemberInfo(conn, order);
			// 从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));
			System.out.println("action:" + order.getMbId());
			//order.setItems(vgoods);
			order.getOrgCart().setItems(vgoods);
			// 从session中取用户
			User user = (User) request.getSession().getAttribute("user");
			order.getOrgCart().getOrgOrder().setCreatorId(Integer.parseInt(user.getId()));
			// 从地址簿中取送货信息
			OrderDAO.getAddressInfo(conn, order);
			
			order.getOrgCart().getOrgOrder().setCategoryId(20);// 自动设为义务订单
			order.getOrgCart().getOtherInfo().setOOSPlan(3);// 自动设为取消缺货产品
			order.getOrgCart().getOtherInfo().setRemark(order.getRemark());
			order.getOrgCart().getOtherInfo().setNeedInvoice(order.getNeedInvoice());
			order.getOrgCart().getOrgOrder().setPrTypeId(5);// 自动设为团购订单

			OrderDAO.insertOrder(conn, order);

			// 清购物车
			carts.deleteCart(new Long(order.getMbId()));
			String witchStock = request.getParameter("whichStock");
			if (witchStock != null && witchStock.equals("sales")) {
				OrderDAO.updateOrderStatus(new DBOperation(conn), order
						.getOrderId(), 100);
			} else {
				OrderDAO.runOrder(conn, order);
			}

			// load出新的状态
			// OrderDAO.getOrderHeadersInfo(new DBOperation(conn), order);

			
			conn.commit();
			Message.setMessage(request, "操作成功,订单号是<font color=blue>"
					+ order.getOrderNumber() + "</font>");
			return mapping.findForward("success");
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			Message.setMessage(request, "操作失败：" + e.getMessage());

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
	 * 改变送货方式
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
	 * 改变付款方式
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
	 * 改变地址
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
	 * 改变折扣(add by user 2005-12-29 10:31)
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

			// 从session中取购物车
			ShoppingCart carts = (ShoppingCart) request.getSession()
					.getAttribute("SHOPPINGCART");
			List vgoods = carts.getCart(new Long(order.getMbId()));

			/*
			 * double discount =
			 * Double.parseDouble(request.getParameter("discount"));//折扣
			 * discount = discount == 0.0 ? 1 : discount; if ( vgoods == null ) {
			 * Message.setErrorMsg(request, "购物车为空"); return
			 * mapping.findForward("error");
			 * 
			 *  }
			 */
			// 遍历每个产品
		

			// groupTotalMoney =
			// Arith.mul(order.getGroupTotalMoney(),discount);//总额
			// double groupTotalMoney = getMoney(vgoods, order.getDiscount());

			for (int i = 0, j = vgoods.size(); i < j; i++) {
				ItemInfo item = (ItemInfo) vgoods.get(i);
				// 将折扣价四舍五入
				// if (i != j - 1) {
				// 折扣价
				item.setDiscountPrice(Arith.round(Arith.mul(item.getSilverPrice(), order.getDiscount()), 2));
				// item.setItemPrice(item.getDiscountPrice());
				 item.setGroupItemMomey(Arith.mul(item.getDiscountPrice(),
				 item.getItemQty()));
				 
				// totalMoneyExceptLastItem += item.getGroupItemMomey();

				// }
				// 最一个后产品作例外处理
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
			order.getOrgCart().setDiscount(order.getDiscount());// 折扣
			order.getOrgCart().setItems(vgoods);// 更新购物车
			
			
			
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

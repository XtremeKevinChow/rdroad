/*
 * Created on 2005-4-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;

import com.magic.utils.Arith;
import com.magic.crm.order.entity.TicketMoney;
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
import com.magic.crm.promotion.dao.ExpExchangeActivityDAO;
//import com.magic.crm.promotion.dao.DiamondSetsDAO;
import com.magic.crm.member.dao.MemberExpExchangeDAO;
import com.magic.crm.filter.CallCenterFilter;
import com.magic.crm.member.form.MbrGetAwardForm;
import com.magic.crm.member.form.MemberExpExchangeForm;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberSessionRecruitGifts;
import com.magic.crm.promotion.entity.Recruit_Activity_PriceList;
import com.magic.crm.promotion.dao.Recruit_Activity_PriceListDAO;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.dao.TicketDAO;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.entity.Money4Qty;
import com.magic.crm.order.entity.OneTicket;
import com.magic.crm.order.entity.Proms2;
import com.magic.crm.order.entity.ShoppingCart2;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.order.bo.TicketBO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;
import com.magic.crm.award.dao.AwardDAO;
import com.magic.crm.award.form.AwardForm;
import com.magic.crm.award.bo.*;
import com.magic.crm.product.dao.ProductBaseDAO;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.promotion.dao.ExpExchangePackageDAO;
import com.magic.crm.promotion.dao.Recruit_ActivityDAO;
import com.magic.crm.promotion.dao.Recruit_Activity_PriceListDAO;
import com.magic.crm.promotion.dao.Recruit_ActivityDAO;
import com.magic.crm.promotion.entity.Recruit_Activity;
import com.magic.crm.promotion.entity.Recruit_Activity_Section;
import com.magic.crm.promotion.dao.ExpExchangeActivityDAO;
import com.magic.crm.promotion.dao.GroupPricesDAO;
import com.magic.crm.promotion.entity.ExpExchangeActivity;
import com.magic.crm.promotion.entity.ExpExchangePackageDtl;
import com.magic.crm.promotion.entity.ExpExchangeStepMst;
import com.magic.crm.promotion.entity.DiamondSets;
import com.magic.crm.promotion.entity.DiamondExchange;
import com.magic.crm.promotion.entity.DiamondTimes;
import com.magic.crm.promotion.entity.GroupPrices;
import com.magic.crm.member.entity.Diamond;
import com.magic.crm.common.SequenceManager;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MbrGetAwardDAO2;
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.member.entity.DiamondHistory;
import com.magic.crm.member.entity.Exp;
import com.magic.crm.util.DateUtil;
import com.magic.crm.common.Period;

/**
 * @author Administrator TODO 99read
 */
public class OrderAddSecondAction extends DispatchAction {

	private Logger log = Logger.getLogger(OrderAddSecondAction.class);

	/**
	 * 将sessionRecruit中的打折销售礼品加入购物车（之后清空session中的sessionRecruit）
	 * 
	 * @param pageData
	 * @param sessionGifts
	 * @param db
	 * @throws Exception
	 */
	private void addRecruitGiftsToCart(OrderForm pageData,
			MemberSessionRecruitGifts sessionGifts, DBOperation db)
			throws Exception {
		if (sessionGifts == null || sessionGifts.getMemberId() == 0
				|| sessionGifts.getMemberId() != pageData.getMbId()) {
			return;
		}
		Iterator it = sessionGifts.getSeletedRecruitGifs().iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList sessionGift = (Recruit_Activity_PriceList) it
					.next();
			if (sessionGift.getSellType() == -1) {// 打折销售
				ItemInfo discount_item = new ItemInfo();
				//discount_item.setItemId(sessionGift.getItemId());
				discount_item.setItemCode(sessionGift.getItemCode());
				discount_item.setFloorMoney(0);
				discount_item.setAddy(0);
				discount_item.setItemPrice(sessionGift.getPrice());
				// 库存数量
				discount_item.setTruss(false);

				int nAvailableQty = OrderDAO.getAvailableStockQty(db,
						discount_item, pageData);

				if (nAvailableQty <= pageData.getQueryItemQty()) { // 库存不足
					discount_item.setStockStatusId(1);
					if (discount_item.isLastSell())
						discount_item.setStockStatusName("永久缺货");
					else
						discount_item.setStockStatusName("暂时缺货");
				} else {
					discount_item.setStockStatusId(0);
					if (nAvailableQty - pageData.getQueryItemQty() < 10) {
						discount_item.setStockStatusName("即将缺货");
					} else {
						discount_item.setStockStatusName("库存正常");
					}

				}
				discount_item.setItemName(sessionGift.getItemName());
				discount_item.setCatalog("招募促销");
				discount_item.setItemUnit(sessionGift.getUnit().getName());
				discount_item.setSellTypeId(-1); // 入会打折
				discount_item.setSellTypeName("入会打折");
				pageData.getCart().getItems().add(discount_item);
			}
		}
	}

	
	

	/**
	 * 页面从orderAddFirst过来的初始话页面,从会员礼品表得到初始礼品,得到会员促销礼品组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;

			// 如果是callcenter系统，从sessin中取出会员信息，如果没有提示登录服务对象
			Member member = CallCenterFilter.getCurrentMemberId(request);
			if (member == null) {// 未登录
				return mapping.findForward("controlledError");
			}
			// 设置会员id
			pageData.setMbId(member.getID());

			// 检索会员信息（会员信息、送货信息、发送费）
			OrderDAO.getMemberInfo(new DBOperation(conn), pageData);

			// 取当前客户的赠品（暂存架礼品）
			OrderDAO.addLargess(conn, pageData);

			// 取得当前目录和促销活动（所有有效促销礼品）
			OrderDAO.listGift(new DBOperation(conn), pageData);
			
			//列出有效msc_code供选择
			ArrayList mscs = OrderDAO.getValidMSCCode(conn);
			pageData.setMscs(mscs);

			// 取入会礼品
			/*MemberSessionRecruitGifts sessionGifts = (MemberSessionRecruitGifts) request
					.getSession(true).getAttribute("RECRUIT ACTIVITY");
			addRecruitGiftsToCart(pageData, sessionGifts, new DBOperation(conn));
			request.getSession(true).removeAttribute("RECRUIT ACTIVITY");
			*/
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);
			// System.out.println(request.getParameter("tab"));
			boolean isOld = pageData.getCart().getMember().isOldMember();
			Collection activeMsc = new ArrayList();
			if (!isOld) { //没有有效订单
				activeMsc = GroupPricesDAO.getNewMemberEnjoyRecruits(conn, pageData);
				if (activeMsc.size() == 0) {// not found
					activeMsc = GroupPricesDAO.getOldMemberEnjoyRecruits(conn, pageData);
				}
			} else { //已经有了有效订单
				
			}
			if (activeMsc.size() >= 1) {
				Message.setErrorMsg(request, "此会员有享受促销礼品，请注意!");
			}
			
			
			pageData.getCart().setActiveMsc((List)activeMsc);
			//request.getSession(true).setAttribute("activeMsc", activeMsc);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward("input");
	}

	/**
	 * 购物车
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward shopcart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forward = "input";
		try {
			OrderForm pageData = (OrderForm) form;

			// 从session中得到订单信息
			if (request.getSession().getAttribute(Constants.TEMPORARY_ORDER) == null) {
				Message.setMessage(request, "购物车没有任何商品，请点击【新增订单】重新下单！");
				forward = "error";
			} else {
				OrderForm sessData = (OrderForm) request.getSession()
						.getAttribute(Constants.TEMPORARY_ORDER);
				
				
				OrderForm.copyData(sessData, pageData);
			}
			//判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
		}

		return mapping.findForward(forward);
	}

	/**
	 * 选择订单来源类型
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectPrType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forward = "input";
		try {
			OrderForm pageData = (OrderForm) form;

			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			// 改变sessoin中的订单来源类型
			sessData.setPrTypeId(pageData.getPrTypeId());
			OrderForm.copyData(sessData, pageData);
			//判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
		}

		return mapping.findForward(forward);
	}

	/**
	 * 检测输入的数据
	 * 
	 * @param pageData
	 * @return
	 */
	private boolean checkInputParam(HttpServletRequest request,
			ShoppingCart2 cart, ItemInfo ii) {
		
		// 产品是否存在
		if (ii==null || "".equals(ii.getItemName())) {
			Message.setErrorMsg(request, "您输入产品不存在。");
			return false;
		}
		
		// 已有预售产品
		/*if (cart.isPreSellOrder()) {
			Message.setErrorMsg(request, "对不起，购物车中已有预售产品，预售产品只能单独下单。");
			return false;
		}*/
		
		// 如果产品缺货，不能购买
		if (ii.getStockStatusName().equals("永久缺货")||ii.getStockStatusName().equals("暂时缺货")) {
			Message.setErrorMsg(request, "您输入产品缺货，不能下单");
			return false;
		}
		// 判断购物车中是否已经改买了这个产品
		/*if (cart.existItems(ii)) {
			Message.setErrorMsg(request, "您输入产品已存在，请更改数量。");
			return false;
		}*/
		/*
		 * if (cart.existGifts(ii)) { Message.setErrorMsg(request, "您输入礼品已存在。");
		 * return false; }
		 */
		// 是否预售
		/*if (ii.getIs_pre_sell() == 1) {
			if (cart.getItems().size() > 0 || cart.hasNomalGifts()) {
				Message.setErrorMsg(request, "对不起，预售产品只能单独下单。");
				return false;
			}
		} else {
			if (cart.getItems().size() > 0) {
				if (((ItemInfo) cart.getItems().get(0)).getIs_pre_sell() == 1) {
					Message.setErrorMsg(request, "对不起，非预售产品不能和预售产品同时下单。");
					return false;
				}
			}
		}*/
		return true;
	}

	/**
	 * 增加产品进入购物车
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
		String forward = "input";
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);

			/* delete by zhux 20080809
			String[] datas = pageData.getQueryItemCode().trim().split("-");
			if(datas == null || datas.length != 3) {
				Message.setErrorMsg(request, "您输入数据的格式不正确，正确的格式是 货号-尺码-颜色");
				return mapping.findForward("input");
			}*/
			String data = pageData.getQueryItemCode().trim();
			
			//如果是首位是9,则去掉首位
			if(data.charAt(0)=='9') {
				data = data.substring(1);
			}
			// 得到购物车
			ShoppingCart2 cart = pageData.getCart();
			
			//ItemInfo ii = OrderDAO.findItem(conn, datas[0],datas[1],datas[2]);
			ItemInfo ii = OrderDAO.findItem(conn, data);
			
			// 输入产品不合法
			if (!checkInputParam(request, cart, ii)) {
				return mapping.findForward("input");
			}
			/*
			 * 看他是否存在于可选的促销礼品 如果同时存在多个相同的促销礼品，采取取第一个礼品的策略（新的系统可考虑定位到具体产品）
			 */
			/* 仅做正常销售产品处理,不做礼品
			   if (cart.isValidPromotionGift(data)) {
				
				// 如果一个礼品同时存在多个相同的促销礼品，取最便宜的那个档次的礼品(组促销也包括在内)
				int nIndex = cart.getLowerGiftIndex(pageData.getQueryItemCode());
				ItemInfo ii2 = (ItemInfo) cart.getAllGifts().get(nIndex);
				if (cart.getNotGiftMoney() >= ii2.getFloorMoney()
						&& OrderDAO.ifGroupGift(conn, pageData)) {
					if(!ii2.isTruss()) {
						cart.getGifts().add(0, ii2);
					} else {
						ii.setSet_group_id(cart.getSetGroupId()+1);
						ArrayList<ItemInfo> parts = OrderDAO.splitGiftSet2Part(conn,ii2);
						//cart.getGifts().addAll(0,parts);
						cart.addGiftSet(parts);
					}
					
				} else {  // sell as normal product
					
					if(ii.getSku_id()>0) {
						OrderDAO.fillItemPrice(conn,ii,pageData);
					}
					//如果是套装，进行拆套，如果是普通产品，直接放入购物车
					if(!ii.isTruss()) {
						cart.getItems().add(0, ii);
					} else {
						ii.setSet_group_id(cart.getSetGroupId()+1); 
						ArrayList<ItemInfo> parts = OrderDAO.splitSet2Part(conn,ii,pageData.getCart().getMember().getLEVEL_ID());
						//cart.getItems().addAll(0,parts);
						cart.addItemSet(parts);
					}
					
				}
			} else { */
				if(ii.getSku_id()>0) {
					OrderDAO.fillItemPrice(conn,ii,pageData);
				}
				//如果是套装，进行拆套，如果是普通产品，直接放入购物车
				if(!ii.isTruss()) {
					cart.getItems().add(0, ii);
				} else {
					ii.setSet_group_id(cart.getSetGroupId()+1);
					ArrayList<ItemInfo> parts = OrderDAO.splitSet2Part(conn,ii,pageData.getCart().getMember().getLEVEL_ID());
					//cart.getItems().addAll(0,parts);
					cart.addItemSet(parts);
				}
			//}

			
			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
			//判断msc促销按钮有效性
			//if (cart.isRecruitProductInCart()) {
			//	pageData.setRecruitBtnActive(false);
			//}
			
			// 重置礼券
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			
			
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			Message.setErrorMsg(request, e.getMessage());
			forward = "error";

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * 从购物车中删除产品
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
		String forward = "input";
		String index = request.getParameter("operateId");
		String sellType = request.getParameter("sellType");
		String sectionType = request.getParameter("sectionType");
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// 得到购物车
			ShoppingCart2 cart = pageData.getCart();

			//int nIndex = cart.getItemIndex(pageData.getOperateId());
			int nIndex = Integer.parseInt(index);
			
			if (nIndex != -1) {
				ItemInfo item = (ItemInfo)cart.getItems().get(nIndex);
				if("".equals(item.getSet_code())) {
					//删除当前行
					cart.removeItem(nIndex);
				} else {
					List items = cart.getItems();
					//删除整个套装
					for(int i=items.size()-1;i>=0;i--) {
						ItemInfo i2 = (ItemInfo)items.get(i);
						if(item.getSet_group_id()== i2.getSet_group_id()) {
							items.remove(i);
						}
					}
					
				}
				
				// 去除购物金额线低于现有产品的礼品
				cart.removeReject();
			}

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
			//	判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// 重置礼券
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			pageData.setColors(ProductBaseDAO.listColor(conn));
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			Message.setErrorMsg(request, e.getMessage());
			forward = "error";
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 从购物车中删除产品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGift2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		String index = request.getParameter("operateId");
		String sellType = request.getParameter("sellType");
		String sectionType = request.getParameter("sectionType");
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// 得到购物车
			ShoppingCart2 cart = pageData.getCart();

			//int nIndex = cart.getItemIndex(pageData.getOperateId());
			int nIndex = Integer.parseInt(index);
			
			if (nIndex != -1) {
				ItemInfo item = (ItemInfo)cart.getGifts2().get(nIndex);
				if("".equals(item.getSet_code())) {
					//删除当前行
					cart.getGifts2().remove(nIndex);
				} else {
					List items = cart.getGifts2();
					//删除整个套装
					for(int i=items.size()-1;i>=0;i--) {
						ItemInfo i2 = (ItemInfo)items.get(i);
						if(item.getSet_group_id()== i2.getSet_group_id()) {
							items.remove(i);
						}
					}
					
				}
				
				//根据满足的条件确定这些礼品的金额
				int groupid = item.getGroupId();
				boolean bSel = false;
				int gift2Qty = cart.getGift2ActQty(groupid);
				
				Proms2 pm = cart.getAllGift2Prom(item.getGroupId());
				if (pm !=null) {
					List ms = pm.getMoney4qty();
					Money4Qty last = (Money4Qty)ms.get(ms.size()-1);
					int max_qty = last.getQty();
					double money = last.getMoney();
					if (gift2Qty >=max_qty) {
						int n = gift2Qty%max_qty;
						cart.setGift2Price(groupid,money/max_qty,gift2Qty-n,n);
					}
					
					gift2Qty = gift2Qty%max_qty;
					Iterator it2 = ms.iterator();
					while(it2.hasNext()) {
						Money4Qty m4q = (Money4Qty)it2.next();
						if (m4q.getQty() == gift2Qty) {
							cart.setGift2Price(groupid,m4q.getMoney()/gift2Qty,gift2Qty);
							bSel = true;
							break;
						}
					}
					if (!bSel) {
						cart.setGift2Price(groupid,0,gift2Qty);
					}
				}
				
				
				// 去除购物金额线低于现有产品的礼品
				cart.removeReject();
			}

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
			//	判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// 重置礼券
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			pageData.setColors(ProductBaseDAO.listColor(conn));
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			Message.setErrorMsg(request, e.getMessage());
			forward = "error";
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * 更新购物车中的产品
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
		String forward = "input";
		//String updItemId = request.getParameter("updItemId");
		//String sellType = request.getParameter("sellType");
		//String sectionType = request.getParameter("sectionType");
		
		int index = Integer.parseInt(request.getParameter("operateId"));
		
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			//pageData.setColors(ProductBaseDAO.listColor(conn));
			
			// 得到session购物车
			ShoppingCart2 cart = pageData.getCart();
			// 所有正常产品
			List items = cart.getItems();
			//int nIndex = cart.getItemIndex(pageData.getOperateId());
			//cart.getItemIndex(Integer.parseInt(updItemId), Integer.parseInt(sellType), sectionType);
			if (index != -1) {
				// 得到正常产品
				ItemInfo currItem = (ItemInfo) items.get(index);

				// ///////
				// 判断修改后的产品是否小于允许下单的最大值
				//ProductForm ProductForm = new ProductDAO().findByCode(conn,
				//		currItem.getItemCode());

				currItem.setItemQty(Integer.parseInt(request.getParameter("updateItemQty")));
				
				currItem.setColor_code(request.getParameter("colorCode"));
				currItem.setSize_code(request.getParameter("sizeCode"));
				
				int ret = OrderDAO.fillItem(conn, currItem);
				if(ret <0) {
					Message.setErrorMsg(request, "对应sku不存在");
					return mapping.findForward("input");
				}
				
				if("".equals(currItem.getSet_code())) {
					OrderDAO.fillItemPrice(conn, currItem, pageData);
					
				} else {
					for(int i=0;i<items.size();i++) {
						ItemInfo i2 = (ItemInfo)items.get(i);
						if(currItem.getSet_group_id()==i2.getSet_group_id()) {
							i2.setItemQty(currItem.getItemQty());
						}
					}
					
				}
				
				
				if (currItem.getItemQty() > currItem.getMax_count()) {
					Message.setErrorMsg(request, "该产品不能大于"
							+ currItem.getMax_count() + "个");
					currItem.setItemQty(currItem.getMax_count());
					return mapping.findForward("input");
				}
				
				/*if (currItem.getStockStatusName().equals("虚拟库存")&& cart.getItems().size()>1) {
					Message.setErrorMsg(request, "对不起，预售产品只能单独下单");
					currItem.setSku_id(0);
					return mapping.findForward("input");
				}*/
				
				// 去除购物金额线低于现有产品的礼品
				cart.removeReject();
			}
			
			// 判断msc促销按钮有效性
			/*if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}*/
			// 重置礼券
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();

			
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			Message.setErrorMsg(request, e.getMessage());
			forward = "error";
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * 更新购物车中的产品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateGift2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		//String updItemId = request.getParameter("updItemId");
		//String sellType = request.getParameter("sellType");
		//String sectionType = request.getParameter("sectionType");
		
		int index = Integer.parseInt(request.getParameter("operateId"));
		
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			//pageData.setColors(ProductBaseDAO.listColor(conn));
			
			// 得到session购物车
			ShoppingCart2 cart = pageData.getCart();
			// 所有正常产品
			List items = cart.getGifts2();
			//int nIndex = cart.getItemIndex(pageData.getOperateId());
			//cart.getItemIndex(Integer.parseInt(updItemId), Integer.parseInt(sellType), sectionType);
			if (index != -1) {
				// 得到正常产品
				ItemInfo currItem = (ItemInfo) items.get(index);

				currItem.setColor_code(request.getParameter("colorCode"));
				currItem.setSize_code(request.getParameter("sizeCode"));
				
				int ret = OrderDAO.fillItem(conn, currItem);
				if(ret <0) {
					Message.setErrorMsg(request, "对应sku不存在");
					return mapping.findForward("input");
				}
				
				// 去除购物金额线低于现有产品的礼品
				cart.removeReject();
			}
			
			// 判断msc促销按钮有效性
			/*if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}*/
			// 重置礼券
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();

			
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			Message.setErrorMsg(request, e.getMessage());
			forward = "error";
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}
	/**
	 * 更新购物车中的礼品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		//String updItemId = request.getParameter("updItemId");
		//String sellType = request.getParameter("sellType");
		//String sectionType = request.getParameter("sectionType");
		
		int index = Integer.parseInt(request.getParameter("operateId"));
		
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			//pageData.setColors(ProductBaseDAO.listColor(conn));
			
			// 得到session购物车
			ShoppingCart2 cart = pageData.getCart();
			
			
			ItemInfo currItem = (ItemInfo) cart.getGifts().get(index);
			
			// ///////
				// 判断修改后的产品是否小于允许下单的最大值
				//ProductForm ProductForm = new ProductDAO().findByCode(conn,
				//		currItem.getItemCode());

				//currItem.setItemQty(Integer.parseInt(request.getParameter("updateItemQty")));
				
				currItem.setColor_code(request.getParameter("colorCode"));
				currItem.setSize_code(request.getParameter("sizeCode"));
				
				int ret = OrderDAO.fillItem(conn, currItem);
				if(ret <0) {
					Message.setErrorMsg(request, "对应sku不存在");
					return mapping.findForward("input");
				}
				
				//OrderDAO.fillItemPrice(conn, currItem, pageData);
			
				/*if (currItem.getItemQty() > currItem.getMax_count()) {
					Message.setErrorMsg(request, "该产品不能大于"
							+ currItem.getMax_count() + "个");
					currItem.setItemQty(currItem.getMax_count());
					return mapping.findForward("input");
				}*/
				
				// 去除购物金额线低于现有产品的礼品
				cart.removeReject();
			
			
			// 判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// 重置礼券
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();

			
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			Message.setErrorMsg(request, e.getMessage());
			forward = "error";
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
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
	public ActionForward clearItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;

			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);

			// 得到购物车
			ShoppingCart2 cart = pageData.getCart();

			// 清空购物车中的产品、礼品、礼券
			cart.clearShoppingCart();

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
//			判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * 刷新页面（orderAdd3.jsp转向orderAdd2.jsp）
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
		String forward = "input";
		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			ShoppingCart2 cart = pageData.getCart();
			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
//			判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * 增加礼品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();
			// 页面信息
			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// 得到购物车
			ShoppingCart2 cart = pageData.getCart();
			// 取得页面选取的礼品
			//if (!cart.isPreSellOrder()) { // 非预售订单
				for (int i = 0; i < 40; i++) {
					// 选中的促销礼品（促销礼品id）
					String queryAwardId = request.getParameter("giftCode" + i);
					if (queryAwardId == null) {// 每次可以有多个促销礼品加入
						continue;
					} else {
						pageData.setQueryAwardId(Long.parseLong(queryAwardId));// 促销礼品id
						pageData.setSellTypeId(4);// 销售类型
						pageData.setQueryItemQty(1);// 数量
						// 促销礼品的位置
						int nIndex = cart.getAllGiftIndex(pageData
								.getQueryAwardId(), pageData.getSellTypeId());
						ItemInfo ii2 = (ItemInfo) cart.getAllGifts().get(nIndex);
						
						// get the promtion gift from
						// shoppingcart in the session
						// fulfil the purchase money and promotion group the item your input is promotion gift
						if (cart.getNotGiftMoney() >= ii2.getFloorMoney()
								&& OrderDAO.ifGroupGift(conn, pageData)) {
							//cart.getGifts().add(0, ii2);
							//如果是套装，进行拆套，如果是普通产品，直接放入购物车
							if(!ii2.isTruss()) {
								cart.getGifts().add(0, ii2);
							} else {
								ii2.setSet_group_id(cart.getSetGroupId()+1);
								ArrayList<ItemInfo> parts = OrderDAO.splitGiftSet2Part(conn,ii2);
								//cart.getGifts().addAll(0,parts);
								cart.addGiftSet(parts);
							}
							
						}
						// break;
					}
				}
			//} else { // 预售订单
			//	Message.setErrorMsg(request, "预售产品不能选择促销礼品，只能单独下单");
			//}
			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
			
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}

	/**
	 * 增加多少元任选几件礼品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addGift2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();
			// 页面信息
			OrderForm pageData = (OrderForm) form;
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// 得到购物车
			ShoppingCart2 cart = pageData.getCart();
			// 取得页面选取的礼品
			
			String award_id = request.getParameter("award_id");
			List gfs = cart.getAllGifts2();
			Iterator it = gfs.iterator();
			while(it.hasNext()) {
				Proms2 pm = (Proms2)it.next();
				List iis = pm.getItems();
				Iterator it2 = iis.iterator();
				int gfqty = 0;
				int groupid = 0;
				//如果礼品id和输入的id相同,这检查该组礼品共多少个,满足哪个促销
				while(it2.hasNext()) {
					ItemInfo ii = (ItemInfo)it2.next();
					if (ii.getAwardId()== Integer.parseInt(award_id)) {
						
						//如果礼品符合,加入礼品2list
						groupid = ii.getGroupId();
						if(!ii.isTruss()) {
							cart.addGift2(new ItemInfo(ii));
						} else {
							ii.setSet_group_id(cart.getSetGroupId()+1);
							ArrayList<ItemInfo> parts = OrderDAO.splitGiftSet2Part(conn,ii);
							cart.addGift2Set(parts);
						}
						
						//根据满足的条件确定这些礼品的金额
						int gift2Qty = cart.getGift2ActQty(groupid);
						List ms = pm.getMoney4qty();
						Money4Qty last = (Money4Qty)ms.get(ms.size()-1);
						int max_qty = last.getQty();
						
						gift2Qty = gift2Qty%max_qty;
						if (gift2Qty == 0) {
							gift2Qty = max_qty;
						}
						it2 = ms.iterator();
						while(it2.hasNext()) {
							Money4Qty money = (Money4Qty)it2.next();
							if (money.getQty() == gift2Qty) {
								cart.setGift2Price(groupid,money.getMoney()/gift2Qty,gift2Qty);
								break;
							}
						}
						
						break;
					}
				}
				
			}
			
			cart.resetAllGift();
			
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}
	/**
	 * 删除礼品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		Connection conn = null;
		String delType = request.getParameter("delType");
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderForm pageData = (OrderForm) form;

			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// 得到购物车
			ShoppingCart2 cart = pageData.getCart();
			// 要删除礼品的位置
			int nIndex = pageData.getOperateId();
			
			// 如果是永久删除，则将该礼品从购物车中删除的同时，还要从礼品暂存表中删除
			if (delType != null && delType.equals("combout")) {
				ItemInfo itemInfo = (ItemInfo) cart.getGifts().get(nIndex);
				if (itemInfo.getAwardId() > 0) {
					// 1.将暂存表中礼品置为取消
					AwardDAO ad = new AwardDAO();

					// 2.如果是积分还礼将积分返给会员帐户
					AwardForm af = ad.findByPrimaryKey(conn, itemInfo
							.getAwardId());
					User user = (User) request.getSession()
							.getAttribute("user");

					af.setOperatorName(user.getNAME());
					AwardBO abo = new AwardBO();

					int retValue = abo.cancel(conn, af);

					if (retValue != 0) {
						conn.rollback();

					}
				}
			}

			if (nIndex != -1) {
				// 删除购物车的礼品
				ItemInfo item = (ItemInfo) cart.getGifts().get(nIndex);
				if("".equals(item.getSet_code())) {
					//删除当前行
					cart.removeGift(nIndex);
				} else {
					List items = cart.getGifts();
					//删除整个套装
					for(int i=items.size()-1;i>=0;i--) {
						ItemInfo i2 = (ItemInfo)items.get(i);
						if(item.getSet_group_id()==i2.getSet_group_id()
								&&item.getSet_code().equals(i2.getSet_code())) {
							items.remove(i);
						}
					}
				}
			}

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
//			判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			conn.commit();
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			forward = "error";
			Message.setErrorMsg(request, e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}

	/**
	 * 幸运卡 现在不用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward validTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		Connection conn = null;

		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;

			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);

			OrderForm.copyData(sessData, pageData);

			// 得到购物车
			ShoppingCart2 cart = pageData.getCart();

			// 预售订不能用幸运卡
			if (cart.isPreSellOrder()) {
				Message.setErrorMsg(request, pageData.getTicketNumber()
						+ "预售订不能用幸运卡。");
				return mapping.findForward("input");
			}

			try {
				// 购物车中所有礼券
				List inputTickets = cart.getTickets();

				// 必须是幸运卡
				if (TicketBO.getTicketTypeByNumber(pageData.getTicketNumber()) != 3) {
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "不是幸运卡!");
				}
				// 检测幸运卡是否用过
				if (TicketBO.isInMemory(inputTickets, pageData
						.getTicketNumber())) {
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "已经被使用或者一个订单不能同时使用2张幸运卡!");
				}

				int rtn = OrderDAO.checkTicket(conn, pageData);
				switch (rtn) {
				case -1:
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "卡号不存在!");
				case -2:
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "密码不正确!");
				case -3:
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "已被使用!");
				case -4:
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "已经过期!");
				case -5:
					throw new java.lang.Exception("普通（临时）会员不能使用幸运卡!");
				case -6:
					throw new java.lang.Exception("购物金额不够!");
				}

				// 礼券组判断（需要所有礼券一起检查）
				if (inputTickets.size() > 1) { // 如果是多张礼券判断是不是属于礼券组中
					if (!new TicketBO(conn).checkGiftUseGroup(pageData)) {
						inputTickets.remove(inputTickets.size() - 1);
						throw new java.lang.Exception(pageData
								.getTicketNumber()
								+ "不是同一个礼券组!");
					}
				}
			} catch (Exception e) {
				Message.setErrorMsg(request, e.getMessage());
				forward = "input";
			}

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
//			判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}*/

	/**
	 * 使用礼券(不包括幸运卡)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validTicket2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			OrderForm pageData = (OrderForm) form;

			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			String ticket_num = pageData.getOtherGiftNumber();
			String otherGiftPassword = pageData.getOtherGiftPassword();
			OrderForm.copyData(sessData, pageData);
			pageData.setOtherGiftNumber(ticket_num);
			pageData.setOtherGiftPassword(otherGiftPassword);
			List inputTickets = pageData.getCart().getTickets();
			if(inputTickets.size()>0 ) {
				Message.setErrorMsg(request, "只能使用一张礼券");
				return mapping.findForward("input");
			}
			try {

				TicketBO ticketBo = new TicketBO(conn);
				TicketMoney newItem = new TicketMoney();
				int before = ticketBo.checkTicket(pageData,newItem);
				/*if (before <0) {
					//判断msc促销按钮有效性
					if (pageData.getCart().isRecruitProductInCart()) {
						pageData.setRecruitBtnActive(false);
					}
				}*/
				if (before == -102) {
					Message.setErrorMsg(request, "不能为空。");
					return mapping.findForward("input");
				} else if (before == -101) {
					Message.setErrorMsg(request, pageData.getOtherGiftNumber()
							+ "首字母不能是数字");
					return mapping.findForward("input");
				} else if (before == -100) {
					Message.setErrorMsg(request, "对不起！您提供的礼券"
							+ pageData.getOtherGiftNumber() + "不存在。");
					return mapping.findForward("input");
				} else if (before == -99) {
					Message.setMessage(request, "对不起！您提供的礼券"
							+ pageData.getOtherGiftNumber() + "已经使用过。");
					return mapping.findForward("input");
				} else if (before == -98) {
					Message.setErrorMsg(request, "对不起！您提供的礼券"
							+ pageData.getOtherGiftNumber() + "已经超过了总使用次数。");
					return mapping.findForward("input");
				} else if (before == -97) {
					Message.setErrorMsg(request, "对不起！您提供的礼券"
							+ pageData.getOtherGiftNumber() + "超出了个人使用次数。");
					return mapping.findForward("input");
				} else if (before == -96) {
					Message.setErrorMsg(request, "对不起！您提供的礼券"
							+ pageData.getOtherGiftNumber() + "不属于您个人，所以无法使用。");
					return mapping.findForward("input");
				} else if (before == -103) {
					Message.setErrorMsg(request, "对不起！您提供的礼券"
							+ pageData.getOtherGiftNumber() + "密码不正确，所以无法使用。");
					return mapping.findForward("input");
				} else if (before == -95) {
					Message.setErrorMsg(request, "对不起！您购物金额未达到礼券"
							+ pageData.getOtherGiftNumber() + "要求的购物金额，所以无法使用。");
					return mapping.findForward("input");
				}

				// 将礼券加入购物车
				
				// 根据页面输入的礼券号得到礼券对象
				//OneTicket ticket = TicketDAO.getTicketByNumber2(conn, pageData
				//		.getOtherGiftNumber());
				//newItem.setTicketHeader(ticket.getTicket().getGiftNumber());
				newItem.setTicketHeader(pageData.getOtherGiftNumber());
				newItem.setTicketCode(pageData.getOtherGiftNumber());
				newItem.setTicketType(""
						+ TicketBO.getTicketTypeByNumber(pageData
								.getOtherGiftNumber()));
				newItem.setMoney(newItem.getTicket().getTicket().getGiftMoney());
				newItem.setItemTypeMoney(newItem.getTicket().getTicket().getOrderMoney());
				
				pageData.getCart().getTickets().add(newItem);

				// 礼券业务处理
				/*int rtn = ticketBo.checkTicket(pageData);
				if (rtn != 0) { // 检查出错
					//	判断msc促销按钮有效性
					if (pageData.getCart().isRecruitProductInCart()) {
						pageData.setRecruitBtnActive(false);
					}
					inputTickets.remove(inputTickets.size() - 1);
					if (rtn == -1) {
						Message.setErrorMsg(request, ticket_num + "礼券主表检测出错");
					}
					if (rtn == -2) {
						Message.setErrorMsg(request, ticket_num + "礼券组检测出错");
					}
					// if (rtn == -3) {
					// Message.setErrorMsg(request, "对不起，您提供的礼券" + ticket_num
					// + "必须要购买特定的产品才能使用。");
					// }
					// 礼券产品组
					if (rtn == -301) {
						Message.setErrorMsg(request, "对不起，您提供的礼券" + ticket_num
								+ "必须购买一定数量品种的产品");
					}
					if (rtn == -302) {
						Message.setErrorMsg(request, "对不起，您提供的礼券" + ticket_num
								+ "必须购买产品组中的必买产品");
					}
					if (rtn == -303) {
						Message.setErrorMsg(request, "对不起，您提供的礼券" + ticket_num
								+ "必须购买同类型一定数量的产品");
					}

					if (rtn == -5) {
						Message.setErrorMsg(request, "对不起，您提供的礼券" + ticket_num
								+ "设置有问题。（注：客服人员尽快与IT工程师联系）");
					}
					if (rtn == -6) {
						Message.setErrorMsg(request, "对不起，您的会员级别不能使用礼券"
								+ ticket_num + "。");
					}
					if (rtn == -7) {
						Message.setErrorMsg(request, "对不起，你的购物金额不够，不能使用礼券"
								+ ticket_num + "。");
					}
					//pageData.setColors(ProductBaseDAO.listColor(conn));
					return mapping.findForward("input");
				}*/

			} catch (Exception e) {
				//inputTickets.remove(inputTickets.size() - 1);
				Message.setErrorMsg(request, e.getMessage());
				forward = "input";
				e.printStackTrace();
			}

			// 已经加入购物篮的礼品打上标记
			pageData.getCart().resetAllGift();
//			判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * 删除礼券
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		OrderForm pageData = (OrderForm) form;
		// 从session中得到订单信息
		OrderForm sessData = (OrderForm) request.getSession().getAttribute(
				Constants.TEMPORARY_ORDER);
		OrderForm.copyData(sessData, pageData);
		ShoppingCart2 cart = pageData.getCart();
		String delTicket = request.getParameter("delTicket");
		int nIndex = cart.getTicketIndex(delTicket);
		cart.unuseTicket(nIndex);
//		判断msc促销按钮有效性
		if (pageData.getCart().isRecruitProductInCart()) {
			pageData.setRecruitBtnActive(false);
		}
		Connection conn = DBManager.getConnection();
		
		// 订单保存到session中
		request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
				pageData);
		return mapping.findForward(forward);
	}

	/**
	 * 选择缺货礼品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             modify bymagic
	 */
	public ActionForward selectGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "selectGift";

		return mapping.findForward(forward);
	}

	/**
	 * 显示积分兑换礼品的页面 add by user 2007-12-18
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward showExchangePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "exp";
		OrderForm pageData = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ExpExchangeActivity activity = ExpExchangeActivityDAO
					.loadCurrentActivity(conn);
			request.setAttribute("activity", activity);

			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);

			// 积分帐户信息
			Exp exp = MemberDAO.getExpByCardId(conn, sessData.getCart()
					.getMember().getCARD_ID());
			request.setAttribute("exp", exp);

			// 根据自己的积分设置积分档次有效性
			setRadioStatus(activity, exp);

			// 新增订单还是修改订单标记
			String isAdd = request.getParameter("isAdd");
			request.setAttribute("isAdd", isAdd);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}*/

	/*private void setDiamondRadioStatus(DiamondSets sets, Diamond diamond) {
		Iterator it = sets.getExchangeList().iterator();
		while (it.hasNext()) {
			DiamondExchange exc = (DiamondExchange)it.next();
			
			if (diamond.getNomalCount() >= exc.getExchangeCount()) {
				exc.setEnabled(true);
			}
		}
	}*/
	
	private void setRadioStatus(ExpExchangeActivity activity, Exp exp) {
		Iterator it = activity.getMstList().iterator();
		String exchangeType = activity.getExchangeType();

		while (it.hasNext()) {
			ExpExchangeStepMst mst = (ExpExchangeStepMst) it.next();
			int beginExp = mst.getBeginExp();
			if (exchangeType.equals("A")) {// 一次性
				if (exp.getOldAmountExp() >= beginExp) {
					mst.enabledStepDtl();
				}
			} else { // 实时
				if (exp.getAmountExp() >= beginExp) {
					mst.enabledStepDtl();
				}
			}

		}
	}

	/**
	 * 将积分兑换的礼品加入购物车 add by user 2007-12-18
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward expExchange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		OrderForm pageData = (OrderForm) form;
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			MbrGetAwardForm data = new MbrGetAwardForm();
			data.setMemberID(sessData.getMbId());
			data.setCardID(sessData.getCart().getMember().getCARD_ID());
			data.setOperatorID(Integer.parseInt(user.getId()));
			data.setStepDtlId(Integer.parseInt(request
					.getParameter("stepDtlId")));
			String rtnValue = MbrGetAwardDAO2.expChange(conn, data);
			if (rtnValue.equals("-1")) {
				Message.setMessage(request, "兑换失败：账户积分不足，请检查", "返回", null);
				conn.rollback();
				return mapping.findForward(forward);
			} else if (rtnValue.equals("-2")) {
				Message.setMessage(request, "兑换失败：赠送类型设置错误", "返回", null);
				conn.rollback();
				return mapping.findForward(forward);
			} else if (rtnValue.equals("-3")) {
				Message.setMessage(request, "兑换失败：数据出错啦，请检查", "返回", null);
				conn.rollback();
				return mapping.findForward(forward);
			}

			OrderForm.copyData(sessData, pageData);
			ShoppingCart2 cart = pageData.getCart();
			// 将兑换的礼品加入购物车
			OrderDAO.addExchangeGift(conn, rtnValue, pageData);

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();

			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			Message.setMessage(request, e.getMessage());
			log.error(e);
		} finally {
			if (conn != null) {
				conn.close();
			}

		}

		return mapping.findForward(forward);
	}*/

	/**
	 * 显示钻兑换礼品的页面 add by user 2007-12-18
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/* public ActionForward showDiamondExchangePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "diamond";
		OrderForm pageData = (OrderForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DiamondSets sets = DiamondSetsDAO.loadCurrentSets(conn);
			sets.setExchangeList(DiamondSetsDAO.loadCurrentGiftPackage(conn,
					sets));
			request.setAttribute("sets", sets);

			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);

			// 钻信息
			Diamond diamond = DiamondSetsDAO.loadCurrentMemberDiamond(conn,
					sessData.getCart().getMember().getCARD_ID());
			request.setAttribute("diamond", diamond);

			// 根据自己的积分设置积分档次有效性
			 setDiamondRadioStatus(sets, diamond);

			// 新增订单还是修改订单标记
			String isAdd = request.getParameter("isAdd");
			request.setAttribute("isAdd", isAdd);

		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}*/

	/**
	 * 将钻礼品加入购物车 add by user 2008-04-22
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward diamondExchange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "input";
		OrderForm pageData = (OrderForm) form;
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		int excId = -1;
		try {
			excId = Integer.parseInt(request.getParameter("excId"));
		} catch (NumberFormatException ne) {
			excId = -1;
		}
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);

			StringBuffer awardIds = new StringBuffer();
			DiamondExchange gift = DiamondSetsDAO.findExchangeGiftByPk(conn,
					excId);
			if (gift.getExcId() <= 0) {
				Message.setMessage(request, "找不到该档次的礼品设置!");
				conn.rollback();
				return mapping.findForward(forward);
			}
			// check exchange condition
			Diamond diamond = DiamondSetsDAO.loadCurrentMemberDiamond(conn,
					sessData.getCart().getMember().getCARD_ID());

			if (diamond.getNomalCount() < gift.getExchangeCount()) {
				Message.setMessage(request, "你九久钻不够支付本次兑换!");
				conn.rollback();
				return mapping.findForward(forward);
			}
			int levelId = sessData.getCart().getMember().getLEVEL_ID(); // get setting by member level
			DiamondTimes time = DiamondSetsDAO.getTimeConditionByLevel(conn,
					gift.getActionId(), levelId);
			if (time.getTimeId() <= 0) { // if not exists
				time = DiamondSetsDAO.getTimeConditionByLevel(conn, gift
						.getActionId(), 0); // get default setting
			}
			if (time.getTimeId() <= 0) {
				Message.setMessage(request, "找不到相应的兑换时间限制，请在钻活动中设置!");
				conn.rollback();
				return mapping.findForward(forward);
			} else { // check if fit the time condition
				String timeType = time.getTimeType();
				int md = time.getDays();

				java.util.Date now = new java.util.Date(); // now
				java.util.Date end_date = now;

				if (timeType.equals("1")) { // nature month
					if (md > 0) { // setting is error if less then zero
						end_date = DateUtil.addMonth(now, md);

					}
				} else if (timeType.equals("2")) { // single days
					DiamondHistory his = DiamondSetsDAO.getLastExchangeHis(
							conn, sessData.getMbId());
					java.sql.Date lastExcTime = his.getExcTime(); // the last
																	// exchange
																	// time
					end_date = DateUtil.addDay(lastExcTime, md);
				} else {
					Message.setMessage(request, "没有这种时间类型，请联系市场部检查钻活动设置!");
					conn.rollback();
					return mapping.findForward(forward);
				}
				Period period = new Period(DateUtil.getSqlDate(now), DateUtil
						.getSqlDate(end_date));
				// 统计该时间段内的兑换次数
				int exchanged_cnt = DiamondSetsDAO.countDiamondHisByExcTime(
						conn, sessData.getMbId(), period);
				if (exchanged_cnt >= time.getTimes()) {
					Message.setMessage(request, "已超过了本月（时间段）的兑换最大次数!");
					conn.rollback();
					return mapping.findForward(forward);
				}
			}

			// 兑换

			// DiamondSets sets = DiamondSetsDAO.findSetByPk(conn,
			// gift.getActionId());
			int days = DiamondSetsDAO.getDiamondGiftKeepDays(conn);
			MemberAWARD award = new MemberAWARD();
			if (gift.getGiftType() == 1) {// 1:礼包
				Collection packageList = ExpExchangePackageDAO.findByFk(conn,
						gift.getPackageNo());
				Iterator it = packageList.iterator();
				while (it.hasNext()) {
					ExpExchangePackageDtl packDtl = (ExpExchangePackageDtl) it
							.next();
					if (packDtl.getPackageType().equals("G")) {// 礼品
						int itemId = ProductDAO
								.getItemID(conn, packDtl.getNo());
						int awardId = SequenceManager.getNextSeq(conn,
								"SEQ_MBR_GET_AWARD_ID");
						java.util.Date now = new java.util.Date();
						award.setID(awardId);
						award.setMember_ID(sessData.getMbId());
						//award.setItem_ID(itemId);
						award.setPrice(0);// --------------------
						award.setQuantity(packDtl.getQuantity());
						award.setOperator_id(Integer.parseInt(user.getId()));
						award.setType(19);
						award.setLastDate(DateUtil.date2String(DateUtil.addDay(
								now, days), "yyyy-MM-dd HH:mm:ss"));
						award.setOrder_require(0);// -------------
						award.setDescription(null);
						DiamondSetsDAO.insertAward(conn, award);
						awardIds.append(awardId).append(",");

					} else if (packDtl.getPackageType().equals("T")) { // 礼券
						int awardId = SequenceManager.getNextSeq(conn,
								"SEQ_MBR_GET_AWARD_ID");
						java.util.Date now = new java.util.Date();
						award.setID(awardId);
						award.setMember_ID(sessData.getMbId());
						//award.setItem_ID(-1);
						award.setPrice(0);// --------------------
						award.setQuantity(packDtl.getQuantity());
						award.setOperator_id(Integer.parseInt(user.getId()));
						award.setType(16);
						award.setLastDate(DateUtil.date2String(DateUtil.addDay(
								now, days), "yyyy-MM-dd HH:mm:ss"));
						award.setOrder_require(0);// -------------
						award.setDescription(packDtl.getNo());
						DiamondSetsDAO.insertAward(conn, award);
						awardIds.append(awardId).append(",");
					} else {// error
						Message.setMessage(request, "礼品类型不对!");
						conn.rollback();
						return mapping.findForward(forward);
					}
				}
			} else if (gift.getGiftType() == 2) {// 2：礼品
				int awardId = SequenceManager.getNextSeq(conn,
						"SEQ_MBR_GET_AWARD_ID");
				int itemId = ProductDAO.getItemID(conn, gift.getPackageNo());
				java.util.Date now = new java.util.Date();
				award.setID(awardId);
				award.setMember_ID(sessData.getMbId());
				//award.setItem_ID(itemId);
				award.setPrice(0);// --------------------
				award.setQuantity(1);
				award.setOperator_id(Integer.parseInt(user.getId()));
				award.setType(19);
				award.setLastDate(DateUtil.date2String(DateUtil.addDay(now,
						days), "yyyy-MM-dd HH:mm:ss"));
				award.setOrder_require(0);// -------------
				award.setDescription(null);
				DiamondSetsDAO.insertAward(conn, award);
				awardIds.append(awardId).append(",");
			} else if (gift.getGiftType() == 3) {// 3：礼券
				int awardId = SequenceManager.getNextSeq(conn,
						"SEQ_MBR_GET_AWARD_ID");
				java.util.Date now = new java.util.Date();
				award.setID(awardId);
				award.setMember_ID(sessData.getMbId());
				//award.setItem_ID(-1);
				award.setPrice(0);// --------------------
				award.setQuantity(1);
				award.setOperator_id(Integer.parseInt(user.getId()));
				award.setType(16);
				award.setLastDate(DateUtil.date2String(DateUtil.addDay(now,
						days), "yyyy-MM-dd HH:mm:ss"));
				award.setOrder_require(0);// -------------
				award.setDescription(gift.getPackageNo());
				DiamondSetsDAO.insertAward(conn, award);
				awardIds.append(awardId).append(",");
			} else { // error
				Message.setMessage(request, "礼品类型不对!");
				conn.rollback();
				return mapping.findForward(forward);
			}

			awardIds.append(-1);
			// 更新钻历史状态
			DiamondHistory his = new DiamondHistory();
			his.setMbrId(sessData.getMbId());
			his.setPackageNo(gift.getPackageNo());
			his.setExcOperator(Integer.parseInt(user.getId()));
			his.setExcSeqn(SequenceManager.getNextSeq(conn,
					"SEQ_MBR_DIAMOND_EXC"));
			DiamondSetsDAO.updateDiamondHisByCnt(conn, his, gift
					.getExchangeCount());

			OrderForm.copyData(sessData, pageData);
			ShoppingCart2 cart = pageData.getCart();
			// 将钻的礼品加入购物车
			OrderDAO.addDiamondGift(conn, awardIds.toString(), pageData);

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();

			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			Message.setMessage(request, e.getMessage());
			log.error(e);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}

		}

		return mapping.findForward(forward);
	}*/

	/**
	 * 更换缺货礼品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             modify bymagic
	 */
	public ActionForward changeGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 添加还是修改
		String type1 = request.getParameter("type1");
		String forward = "";
		if (type1.equals("add")) {
			forward = "input";
		} else {
			forward = "modify";
		}
		Connection conn = null;
		try {
			// 得到数据库连接
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;

			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// 由于弹出页没有mbId，因此需要从session中得到
			pageData.setMbId(sessData.getMbId());

			// 购物车
			ShoppingCart2 cart = pageData.getCart();

			// //////////////////////////////////////////////
			String item_id = request.getParameter("item_id"); // 新的礼品id
			int awardId = Integer.parseInt(request.getParameter("awardId"));// 老暂存架id

			pageData.setOperateId(Integer.parseInt(item_id));

			int sellTypeId = Integer.parseInt(request
					.getParameter("sellTypeId"));// 销售类型

			pageData.setOrderId(Integer.parseInt(request
					.getParameter("orderId")));
			int old_item_id = Integer.parseInt(request
					.getParameter("old_item_id"));// 老产品id
			int new_awardId = Integer.parseInt(request
					.getParameter("new_awardId"));// 新暂存架id

			pageData.setRemark("非常抱歉，由于礼品" + old_item_id + "缺货，已为您更换为礼品 "
					+ item_id + " ，希望您能够喜欢，请见谅。");
			// ////////////////////////////////////////////////
			int nIndex = cart.getGiftIndex(awardId, sellTypeId);

			if (nIndex != -1) {
				// 删除购物车的礼品
				cart.removeGift(nIndex);

			}

			// 将更换的礼品加入购物车
			OrderDAO.changeGift(conn, sellTypeId, awardId, new_awardId,
					pageData);

			// 已经加入购物篮的礼品打上标记
			cart.resetAllGift();
//			判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// 订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);

		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return mapping.findForward(forward);
	}


	
	/**
	 * 显示新招募礼品页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showRecruitGroupPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "recruit_group";
		OrderForm pageData = (OrderForm) form;
		String msc = request.getParameter("msc"); // 页面过来的msc
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			sessData.setMsc(msc);
			OrderForm.copyData(sessData, pageData);
			
			// 判断是否已经加载了MSC产品
			//Collection recruitGifts = (Collection)request.getSession(true).getAttribute("RECRUIT_GIFT");
			//if (recruitGifts == null) {
				Collection recruitGifts = GroupPricesDAO.loadActiveGroupsByMsc(conn, msc, pageData);
				request.getSession(true).setAttribute("RECRUIT_GIFT", recruitGifts); // 放入session
			//}
			
			
			// 新增订单还是修改订单标记
			String isAdd = request.getParameter("isAdd");
			request.setAttribute("isAdd", isAdd);

		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 显示更换入会产品页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showChangeRecruitGroupPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "recruit_group_change";
		OrderForm pageData = (OrderForm) form;
		String msc = request.getParameter("msc"); // 页面过来的msc
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			sessData.setMsc(msc);
			OrderForm.copyData(sessData, pageData);
			
//			 新增订单还是修改订单标记
			String isAdd = request.getParameter("isAdd");
			request.setAttribute("isAdd", isAdd);
			
			//替换前产品id
			String oldItemId = request.getParameter("oldItemId");
			request.setAttribute("oldItemId", oldItemId);
			
			//替换前产品所在组
			String gpId = request.getParameter("gpId");
			request.setAttribute("gpId", gpId);
			
			//替换前产品销售区
			String oldSectionType = request.getParameter("oldSectionType");
			request.setAttribute("oldSectionType", oldSectionType);
			
			// 判断是否已经加载了MSC产品
			Collection recruitGifts = (Collection)request.getSession(true).getAttribute("RECRUIT_GIFT");
			if (recruitGifts == null) {
				recruitGifts = GroupPricesDAO.loadActiveGroupsByMsc(conn, msc, pageData);
				request.getSession(true).setAttribute("RECRUIT_GIFT", recruitGifts); // 放入session
			}
			
			GroupPrices group = null;
			Iterator it_groups = recruitGifts.iterator();
			while (it_groups.hasNext()) {
				GroupPrices group1 = (GroupPrices)it_groups.next();
				if (group1.getGpId() == Integer.parseInt(gpId)) {
					group = group1;
					break;
				}
			}
			
			Iterator it = null;
			Iterator it_section = null;
			Iterator it_gift = null;
			//初始化状态
			it = group.getProduct().iterator();
			while (it.hasNext()) {
				Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next(); 
				gift.setDisabled(0);
			}
			it_section = group.getGiftSection().iterator();
			while (it_section.hasNext()) {
				Recruit_Activity_Section section = (Recruit_Activity_Section)it_section.next();
				it_gift = section.getProductsList().iterator();
				while (it_gift.hasNext()) {
					Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it_gift.next(); 
					gift.setDisabled(0);
				}
			}
			
			//禁止已选打套产品
			if (oldSectionType.equals("D")) {
				it = group.getProduct().iterator();
				while (it.hasNext()) {
					Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next(); 
					int index = pageData.getCart().getItemIndex(gift.getItemId(), gift.getSellType(), "D");
					if (index != -1) { // 存在
						gift.setDisabled(1);
					}
				}
				
				//禁止所有赠品
				it_section = group.getGiftSection().iterator();
				while (it_section.hasNext()) {
					Recruit_Activity_Section section = (Recruit_Activity_Section)it_section.next();
					it_gift = section.getProductsList().iterator();
					while (it_gift.hasNext()) {
						Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it_gift.next(); 
						gift.setDisabled(1);
					}
				}
			}
			if (oldSectionType.equals("E")) {
				
				//禁止所有打套产品
				it = group.getProduct().iterator();
				while (it.hasNext()) {
					Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next(); 
					gift.setDisabled(1);
				}
				
				//禁止已选赠品
				it_section = group.getGiftSection().iterator();
				while (it_section.hasNext()) {
					Recruit_Activity_Section section = (Recruit_Activity_Section)it_section.next();
					it_gift = section.getProductsList().iterator();
					while (it_gift.hasNext()) {
						Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it_gift.next(); 
						int index = pageData.getCart().getItemIndex(gift.getItemId(), gift.getSellType(), "E");
						if (index != -1) {
							gift.setDisabled(1);
						}
						
					}
				}
			}
			
			
			//

		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 更换入会产品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeRecruitGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		
		OrderForm pageData = (OrderForm) form;
		String flag = request.getParameter("isAdd");
		if (flag.equals("true")) {
			forward = "input";
		} else {
			forward = "modify";
		}
		String oldItemId = request.getParameter("oldItemId");
		String oldSectionType = request.getParameter("oldSectionType");
		String priceListId = request.getParameter("priceListId");//价格id
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			
			OrderForm.copyData(sessData, pageData);
		
			Recruit_Activity_PriceList gift = Recruit_Activity_PriceListDAO.findByPk(conn, Integer.parseInt(priceListId));
			
			ItemInfo item = new ItemInfo();
			item.setSectionType(gift.getSectionType());
			item.setPriceListLineId(gift.getId()); // 新招募礼品行id
			//item.setItemId(gift.getItemId());
			item.setItemCode(gift.getItemCode());
			item.setItemName(gift.getItemName());
			item.setFloorMoney(gift.getOverx());
			item.setAddy(0);
			item.setItemPrice(gift.getPrice()); //价格需要重新计算
			item.setSellTypeId(-1); // D,E销售类型都是打折销售-1
			item.setPriceListLineId(gift.getId()); // 促销价格id
			item.setSellTypeName("打折销售");
			item.setTruss(false);
			item.setLastSell(gift.getIsLastSell() == 1 ? true: false);
			item.setStandardPrice(gift.getStandardPrice());//-----------------
			
			int nAvailableQty = OrderDAO.getAvailableStockQty(new DBOperation(conn),
					item, pageData);
			if (nAvailableQty <= 1) { // 库存不足
				item.setStockStatusId(1);
				if (item.isLastSell())
					item.setStockStatusName("永久缺货");
				else
					item.setStockStatusName("暂时缺货");
			} else {
				item.setStockStatusId(0);
				if (nAvailableQty - 1 < 10) {
					item.setStockStatusName("即将缺货");
				} else {
					item.setStockStatusName("库存正常");
				}

			}
			item.setItemName(gift.getItemName());
			item.setCatalog("招募促销");
			item.setItemUnit(gift.getUnit().getName());
			
			//定位到购物车
			int index = pageData.getCart().getItemIndex(Integer.parseInt(oldItemId), -1, oldSectionType);
			
			//替换前先把group得到
			ItemInfo idxItem = (ItemInfo)pageData.getCart().getItems().get(index);
			item.setGroupId(idxItem.getGroupId());//档次记录到购物车产品
			
			if (index != -1) {
				//替换
				pageData.getCart().replaceItem(index, item);
				
				if (oldSectionType.equals("D")) { //如果更换礼品是D区的，需要重新计算打套产品价格
//					重算价格（将购物车中的销售类型为-1，销售区为D的重新计算）
					
					
					List list_D = pageData.getCart().getRecruitProductInDSection();
					
					//计算D金额
					//double saleAmt = pageData.getCart().getRecruitProductAmtInDSection(list_D);
					
					int gpId = 0;
					//GroupPrices group = new GroupPrices();
					//group.setSaleAmt(amt);
					
					//总码洋，设置D区价格
					double total_price = 0;
					for (int i = 0; i <list_D.size(); i ++) {
						ItemInfo item_D = (ItemInfo)list_D.get(i);
						if (i == 0) {
							gpId = item_D.getGroupId();
						}
						//Recruit_Activity_PriceList price = new Recruit_Activity_PriceList();
						//price.setStandardPrice(ProductDAO.getStandardPrice(conn, item_D.getItemId()));
						//priceList.add(price);
						//产品标准价格
						total_price += item_D.getStandardPrice();
						
					}
					//得到group
					GroupPrices group = GroupPricesDAO.view(conn, gpId);
					double saleAmt = group.getSaleAmt();
					
					//设置价格
					double part_price = 0;
					for (int i = 0; i < list_D.size(); i ++ ) {
						ItemInfo item_D = (ItemInfo)list_D.get(i);
						
						double calc_price = 0;
						if (i < list_D.size() - 1) { 
							calc_price = Arith.round((item_D.getStandardPrice() / total_price) * saleAmt, 1);
							part_price += calc_price;
							
						} else if (i == list_D.size() - 1) { // the last one
							calc_price = saleAmt - part_price;
							
						}
						item_D.setItemPrice(calc_price);
					}
				}
				
				
			}
			// 已经加入购物篮的礼品打上标记
			pageData.getCart().resetAllGift();
//			判断msc促销按钮有效性
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
//			订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward selectRecruitGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "recruit_group";
		OrderForm pageData = (OrderForm) form;
//		 从session中得到订单信息
		OrderForm sessData = (OrderForm) request.getSession().getAttribute(
				Constants.TEMPORARY_ORDER);
		OrderForm.copyData(sessData, pageData);
		String gpIds = request.getParameter("gpId");
		int gpId = -1;
		try {
			gpId =	Integer.parseInt(gpIds);
		} catch (Exception e) {
			gpId = -1;
		}
		request.setAttribute("gpId", gpIds);
		//Connection conn = null;
		try {
			//conn = DBManager.getConnection();
			
			Collection recruitGifts = (ArrayList)request.getSession().getAttribute("RECRUIT_GIFT"); //规则列表
			GroupPrices one = new GroupPrices();
			Iterator it = recruitGifts.iterator();
			while (it.hasNext()) {
				GroupPrices group = (GroupPrices)it.next();
				if (group.getGpId() == gpId) {
					one = group;
					group.setSelected(true);
					
					//break;
				} else {
					group.setSelected(false);
				}
			}
			request.setAttribute("group", one);
			// 新增订单还是修改订单标记
			String isAdd = request.getParameter("isAdd");
			request.setAttribute("isAdd", isAdd);
			
			

		} catch (Exception e) {
			throw e;
		} finally {
			//if (conn != null) {
				//conn.close();
			//}
		}
		return mapping.findForward(forward);
	}
	/**
	 * 选择礼品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectRecruitGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
//		 新增订单还是修改订单标记
		String isAdd = request.getParameter("isAdd");
		request.setAttribute("isAdd", isAdd);
		if (isAdd.equals("true")) {
			forward = "input";
		} else {
			forward = "modify";
		}
		OrderForm pageData = (OrderForm) form;
		String gpId = request.getParameter("gpId"); // 组
		String ids = request.getParameter("ids"); // 产品
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// 从session中得到订单信息
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			
			// 组信息
			GroupPrices group = GroupPricesDAO.view(conn, Integer.parseInt(gpId));
			
			if (ids != null && ids.length() > 0) {
				
				Collection priceList = Recruit_Activity_PriceListDAO.findByPks(conn, ids);
				//检测数据
				if (checkRecruitRules(request, priceList, group)) { // 检测通过
					// 产品价格根据码洋来平均分配，将产品加入购物车
					List list = (List)priceList;
					List setsProduct = new ArrayList();
					for (int i = 0; i < list.size(); i ++ ) {
						Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList) list.get(i);
						if (gift.getSectionType().equals("D")) {
							setsProduct.add(gift);
						}
						
					}
					calcProductPrice(setsProduct, group);
					addRecruitGiftsToCart2(pageData, priceList, group, new DBOperation(conn));
					
					
				} else {
					//forward = "input";
				}
			}
//			 已经加入购物篮的礼品打上标记
			pageData.getCart().resetAllGift();
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			//订单保存到session中
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 检测所选礼品是否符合规则
	 * @param request
	 * @param priceList
	 * @param group
	 * @return
	 */
	private boolean checkRecruitRules(HttpServletRequest request, Collection priceList, GroupPrices group) {
		
		List seleSetProduct = new ArrayList(); //已选打套产品(D)
		List seleGift = new ArrayList();//已选礼品(E)
		
		// 根据区类型分类出所选礼品
		Iterator it = priceList.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList rapl = (Recruit_Activity_PriceList) it.next();
			if (rapl.getSectionType().equals("D")) {
				seleSetProduct.add(rapl);
			}
			if (rapl.getSectionType().equals("E")) {
				seleGift.add(rapl);
			}
		}
		//判断D区
		if (seleSetProduct.size() == 0 ) {
			Message.setErrorMsg(request, "打套区礼品不满或小于0个!");
			return false;
		}
		if (seleSetProduct.size() != (int)group.getSaleQty()) {
			Message.setErrorMsg(request, "打套区礼品不满或多于" + group.getSaleQty() + "个!");
			return false;
		}
		
		//判断E区
		/**
		if (group.getIsGift() == 1) {
			if (seleGift.size() < group.getSection().getMinGoods()) {
				Message.setErrorMsg(request, "选择的赠品不能小于" + group.getSection().getMinGoods() + "个!");
				return false;
			}
			if (seleGift.size() > group.getSection().getMaxGoods()) {
				Message.setErrorMsg(request, "选择的赠品不能大于!" + group.getSection().getMaxGoods() + "个!");
				return false;
			}
		}
		*/
		return true;
	}
	
	/**
	 * 计算D区产品价格
	 * @param setsProduct
	 * @param group
	 */
	private void calcProductPrice (List setsProduct, GroupPrices group) {
		
		if (setsProduct.size() == 0) {
			return;
		}
		
		double total_price = 0;
		
		
		//计算总码洋
		for (int i = 0; i < setsProduct.size(); i ++ ) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList) setsProduct.get(i);
			total_price += gift.getStandardPrice();
		}
		
		if (total_price <= 0) {
			return;
		}
		
		//设置价格
		double part_price = 0;
		for (int i = 0; i < setsProduct.size(); i ++ ) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)setsProduct.get(i);
			
			double calc_price = 0;
			if (i < setsProduct.size() - 1) { 
				calc_price = Arith.round((gift.getStandardPrice() / total_price) * group.getSaleAmt(), 1);
				part_price += calc_price;
				
			} else if (i == setsProduct.size() - 1) { // the last one
				calc_price = group.getSaleAmt() - part_price;
				
			}
			gift.setPrice(calc_price);
		}
	}
	
	
	
	/**
	 * 加入购物车
	 * @param pageData
	 * @param priceList
	 * @param group
	 * @param db
	 * @throws Exception
	 */
	private void addRecruitGiftsToCart2(OrderForm pageData, Collection priceList, GroupPrices group, DBOperation db) throws Exception {
		
		
		Iterator it = priceList.iterator();
		while (it.hasNext()) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList) it
					.next();
			
			ItemInfo item = new ItemInfo();
			item.setSectionType(gift.getSectionType());
			item.setPriceListLineId(gift.getId()); // 新招募礼品行id
			//item.setItemId(gift.getItemId());
			item.setItemCode(gift.getItemCode());
			item.setItemName(gift.getItemName());
			item.setFloorMoney(gift.getOverx());
			item.setAddy(0);
			item.setItemPrice(gift.getPrice()); //价格需要重新计算
			item.setSellTypeId(-1); // D,E销售类型都是打折销售-1
			item.setPriceListLineId(gift.getId()); // 促销价格id
			item.setSellTypeName("打折销售");
			item.setTruss(false);
			item.setLastSell(gift.getIsLastSell() == 1 ? true: false);
			item.setStandardPrice(gift.getStandardPrice());//----------------
			item.setGroupId(group.getGpId());//档次记录到购物车产品
			int nAvailableQty = OrderDAO.getAvailableStockQty(db,
					item, pageData);
			if (nAvailableQty <= 1) { // 库存不足
				item.setStockStatusId(1);
				if (item.isLastSell())
					item.setStockStatusName("永久缺货");
				else
					item.setStockStatusName("暂时缺货");
			} else {
				item.setStockStatusId(0);
				if (nAvailableQty - 1 < 10) {
					item.setStockStatusName("即将缺货");
				} else {
					item.setStockStatusName("库存正常");
				}

			}
			item.setItemName(gift.getItemName());
			item.setCatalog("招募促销");
			item.setItemUnit(gift.getUnit().getName());
			
			pageData.getCart().getItems().add(0,item);
		}
	}
}

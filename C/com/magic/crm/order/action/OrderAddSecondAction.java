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
	 * ��sessionRecruit�еĴ���������Ʒ���빺�ﳵ��֮�����session�е�sessionRecruit��
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
			if (sessionGift.getSellType() == -1) {// ��������
				ItemInfo discount_item = new ItemInfo();
				//discount_item.setItemId(sessionGift.getItemId());
				discount_item.setItemCode(sessionGift.getItemCode());
				discount_item.setFloorMoney(0);
				discount_item.setAddy(0);
				discount_item.setItemPrice(sessionGift.getPrice());
				// �������
				discount_item.setTruss(false);

				int nAvailableQty = OrderDAO.getAvailableStockQty(db,
						discount_item, pageData);

				if (nAvailableQty <= pageData.getQueryItemQty()) { // ��治��
					discount_item.setStockStatusId(1);
					if (discount_item.isLastSell())
						discount_item.setStockStatusName("����ȱ��");
					else
						discount_item.setStockStatusName("��ʱȱ��");
				} else {
					discount_item.setStockStatusId(0);
					if (nAvailableQty - pageData.getQueryItemQty() < 10) {
						discount_item.setStockStatusName("����ȱ��");
					} else {
						discount_item.setStockStatusName("�������");
					}

				}
				discount_item.setItemName(sessionGift.getItemName());
				discount_item.setCatalog("��ļ����");
				discount_item.setItemUnit(sessionGift.getUnit().getName());
				discount_item.setSellTypeId(-1); // ������
				discount_item.setSellTypeName("������");
				pageData.getCart().getItems().add(discount_item);
			}
		}
	}

	
	

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
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		try {
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;

			// �����callcenterϵͳ����sessin��ȡ����Ա��Ϣ�����û����ʾ��¼�������
			Member member = CallCenterFilter.getCurrentMemberId(request);
			if (member == null) {// δ��¼
				return mapping.findForward("controlledError");
			}
			// ���û�Աid
			pageData.setMbId(member.getID());

			// ������Ա��Ϣ����Ա��Ϣ���ͻ���Ϣ�����ͷѣ�
			OrderDAO.getMemberInfo(new DBOperation(conn), pageData);

			// ȡ��ǰ�ͻ�����Ʒ���ݴ����Ʒ��
			OrderDAO.addLargess(conn, pageData);

			// ȡ�õ�ǰĿ¼�ʹ������������Ч������Ʒ��
			OrderDAO.listGift(new DBOperation(conn), pageData);
			
			//�г���Чmsc_code��ѡ��
			ArrayList mscs = OrderDAO.getValidMSCCode(conn);
			pageData.setMscs(mscs);

			// ȡ�����Ʒ
			/*MemberSessionRecruitGifts sessionGifts = (MemberSessionRecruitGifts) request
					.getSession(true).getAttribute("RECRUIT ACTIVITY");
			addRecruitGiftsToCart(pageData, sessionGifts, new DBOperation(conn));
			request.getSession(true).removeAttribute("RECRUIT ACTIVITY");
			*/
			// �������浽session��
			request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
					pageData);
			// System.out.println(request.getParameter("tab"));
			boolean isOld = pageData.getCart().getMember().isOldMember();
			Collection activeMsc = new ArrayList();
			if (!isOld) { //û����Ч����
				activeMsc = GroupPricesDAO.getNewMemberEnjoyRecruits(conn, pageData);
				if (activeMsc.size() == 0) {// not found
					activeMsc = GroupPricesDAO.getOldMemberEnjoyRecruits(conn, pageData);
				}
			} else { //�Ѿ�������Ч����
				
			}
			if (activeMsc.size() >= 1) {
				Message.setErrorMsg(request, "�˻�Ա�����ܴ�����Ʒ����ע��!");
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
	 * ���ﳵ
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

			// ��session�еõ�������Ϣ
			if (request.getSession().getAttribute(Constants.TEMPORARY_ORDER) == null) {
				Message.setMessage(request, "���ﳵû���κ���Ʒ�����������������������µ���");
				forward = "error";
			} else {
				OrderForm sessData = (OrderForm) request.getSession()
						.getAttribute(Constants.TEMPORARY_ORDER);
				
				
				OrderForm.copyData(sessData, pageData);
			}
			//�ж�msc������ť��Ч��
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
	 * ѡ�񶩵���Դ����
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
			// �ı�sessoin�еĶ�����Դ����
			sessData.setPrTypeId(pageData.getPrTypeId());
			OrderForm.copyData(sessData, pageData);
			//�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// �������浽session��
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
	 * ������������
	 * 
	 * @param pageData
	 * @return
	 */
	private boolean checkInputParam(HttpServletRequest request,
			ShoppingCart2 cart, ItemInfo ii) {
		
		// ��Ʒ�Ƿ����
		if (ii==null || "".equals(ii.getItemName())) {
			Message.setErrorMsg(request, "�������Ʒ�����ڡ�");
			return false;
		}
		
		// ����Ԥ�۲�Ʒ
		/*if (cart.isPreSellOrder()) {
			Message.setErrorMsg(request, "�Բ��𣬹��ﳵ������Ԥ�۲�Ʒ��Ԥ�۲�Ʒֻ�ܵ����µ���");
			return false;
		}*/
		
		// �����Ʒȱ�������ܹ���
		if (ii.getStockStatusName().equals("����ȱ��")||ii.getStockStatusName().equals("��ʱȱ��")) {
			Message.setErrorMsg(request, "�������Ʒȱ���������µ�");
			return false;
		}
		// �жϹ��ﳵ���Ƿ��Ѿ������������Ʒ
		/*if (cart.existItems(ii)) {
			Message.setErrorMsg(request, "�������Ʒ�Ѵ��ڣ������������");
			return false;
		}*/
		/*
		 * if (cart.existGifts(ii)) { Message.setErrorMsg(request, "��������Ʒ�Ѵ��ڡ�");
		 * return false; }
		 */
		// �Ƿ�Ԥ��
		/*if (ii.getIs_pre_sell() == 1) {
			if (cart.getItems().size() > 0 || cart.hasNomalGifts()) {
				Message.setErrorMsg(request, "�Բ���Ԥ�۲�Ʒֻ�ܵ����µ���");
				return false;
			}
		} else {
			if (cart.getItems().size() > 0) {
				if (((ItemInfo) cart.getItems().get(0)).getIs_pre_sell() == 1) {
					Message.setErrorMsg(request, "�Բ��𣬷�Ԥ�۲�Ʒ���ܺ�Ԥ�۲�Ʒͬʱ�µ���");
					return false;
				}
			}
		}*/
		return true;
	}

	/**
	 * ���Ӳ�Ʒ���빺�ﳵ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);

			/* delete by zhux 20080809
			String[] datas = pageData.getQueryItemCode().trim().split("-");
			if(datas == null || datas.length != 3) {
				Message.setErrorMsg(request, "���������ݵĸ�ʽ����ȷ����ȷ�ĸ�ʽ�� ����-����-��ɫ");
				return mapping.findForward("input");
			}*/
			String data = pageData.getQueryItemCode().trim();
			
			//�������λ��9,��ȥ����λ
			if(data.charAt(0)=='9') {
				data = data.substring(1);
			}
			// �õ����ﳵ
			ShoppingCart2 cart = pageData.getCart();
			
			//ItemInfo ii = OrderDAO.findItem(conn, datas[0],datas[1],datas[2]);
			ItemInfo ii = OrderDAO.findItem(conn, data);
			
			// �����Ʒ���Ϸ�
			if (!checkInputParam(request, cart, ii)) {
				return mapping.findForward("input");
			}
			/*
			 * �����Ƿ�����ڿ�ѡ�Ĵ�����Ʒ ���ͬʱ���ڶ����ͬ�Ĵ�����Ʒ����ȡȡ��һ����Ʒ�Ĳ��ԣ��µ�ϵͳ�ɿ��Ƕ�λ�������Ʒ��
			 */
			/* �����������۲�Ʒ����,������Ʒ
			   if (cart.isValidPromotionGift(data)) {
				
				// ���һ����Ʒͬʱ���ڶ����ͬ�Ĵ�����Ʒ��ȡ����˵��Ǹ����ε���Ʒ(�����Ҳ��������)
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
					//�������װ�����в��ף��������ͨ��Ʒ��ֱ�ӷ��빺�ﳵ
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
				//�������װ�����в��ף��������ͨ��Ʒ��ֱ�ӷ��빺�ﳵ
				if(!ii.isTruss()) {
					cart.getItems().add(0, ii);
				} else {
					ii.setSet_group_id(cart.getSetGroupId()+1);
					ArrayList<ItemInfo> parts = OrderDAO.splitSet2Part(conn,ii,pageData.getCart().getMember().getLEVEL_ID());
					//cart.getItems().addAll(0,parts);
					cart.addItemSet(parts);
				}
			//}

			
			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
			//�ж�msc������ť��Ч��
			//if (cart.isRecruitProductInCart()) {
			//	pageData.setRecruitBtnActive(false);
			//}
			
			// ������ȯ
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			
			
			// �������浽session��
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
	 * �ӹ��ﳵ��ɾ����Ʒ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// �õ����ﳵ
			ShoppingCart2 cart = pageData.getCart();

			//int nIndex = cart.getItemIndex(pageData.getOperateId());
			int nIndex = Integer.parseInt(index);
			
			if (nIndex != -1) {
				ItemInfo item = (ItemInfo)cart.getItems().get(nIndex);
				if("".equals(item.getSet_code())) {
					//ɾ����ǰ��
					cart.removeItem(nIndex);
				} else {
					List items = cart.getItems();
					//ɾ��������װ
					for(int i=items.size()-1;i>=0;i--) {
						ItemInfo i2 = (ItemInfo)items.get(i);
						if(item.getSet_group_id()== i2.getSet_group_id()) {
							items.remove(i);
						}
					}
					
				}
				
				// ȥ���������ߵ������в�Ʒ����Ʒ
				cart.removeReject();
			}

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
			//	�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// ������ȯ
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			pageData.setColors(ProductBaseDAO.listColor(conn));
			// �������浽session��
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
	 * �ӹ��ﳵ��ɾ����Ʒ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// �õ����ﳵ
			ShoppingCart2 cart = pageData.getCart();

			//int nIndex = cart.getItemIndex(pageData.getOperateId());
			int nIndex = Integer.parseInt(index);
			
			if (nIndex != -1) {
				ItemInfo item = (ItemInfo)cart.getGifts2().get(nIndex);
				if("".equals(item.getSet_code())) {
					//ɾ����ǰ��
					cart.getGifts2().remove(nIndex);
				} else {
					List items = cart.getGifts2();
					//ɾ��������װ
					for(int i=items.size()-1;i>=0;i--) {
						ItemInfo i2 = (ItemInfo)items.get(i);
						if(item.getSet_group_id()== i2.getSet_group_id()) {
							items.remove(i);
						}
					}
					
				}
				
				//�������������ȷ����Щ��Ʒ�Ľ��
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
				
				
				// ȥ���������ߵ������в�Ʒ����Ʒ
				cart.removeReject();
			}

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
			//	�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// ������ȯ
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			pageData.setColors(ProductBaseDAO.listColor(conn));
			// �������浽session��
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
	 * ���¹��ﳵ�еĲ�Ʒ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			//pageData.setColors(ProductBaseDAO.listColor(conn));
			
			// �õ�session���ﳵ
			ShoppingCart2 cart = pageData.getCart();
			// ����������Ʒ
			List items = cart.getItems();
			//int nIndex = cart.getItemIndex(pageData.getOperateId());
			//cart.getItemIndex(Integer.parseInt(updItemId), Integer.parseInt(sellType), sectionType);
			if (index != -1) {
				// �õ�������Ʒ
				ItemInfo currItem = (ItemInfo) items.get(index);

				// ///////
				// �ж��޸ĺ�Ĳ�Ʒ�Ƿ�С�������µ������ֵ
				//ProductForm ProductForm = new ProductDAO().findByCode(conn,
				//		currItem.getItemCode());

				currItem.setItemQty(Integer.parseInt(request.getParameter("updateItemQty")));
				
				currItem.setColor_code(request.getParameter("colorCode"));
				currItem.setSize_code(request.getParameter("sizeCode"));
				
				int ret = OrderDAO.fillItem(conn, currItem);
				if(ret <0) {
					Message.setErrorMsg(request, "��Ӧsku������");
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
					Message.setErrorMsg(request, "�ò�Ʒ���ܴ���"
							+ currItem.getMax_count() + "��");
					currItem.setItemQty(currItem.getMax_count());
					return mapping.findForward("input");
				}
				
				/*if (currItem.getStockStatusName().equals("������")&& cart.getItems().size()>1) {
					Message.setErrorMsg(request, "�Բ���Ԥ�۲�Ʒֻ�ܵ����µ�");
					currItem.setSku_id(0);
					return mapping.findForward("input");
				}*/
				
				// ȥ���������ߵ������в�Ʒ����Ʒ
				cart.removeReject();
			}
			
			// �ж�msc������ť��Ч��
			/*if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}*/
			// ������ȯ
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();

			
			// �������浽session��
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
	 * ���¹��ﳵ�еĲ�Ʒ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			//pageData.setColors(ProductBaseDAO.listColor(conn));
			
			// �õ�session���ﳵ
			ShoppingCart2 cart = pageData.getCart();
			// ����������Ʒ
			List items = cart.getGifts2();
			//int nIndex = cart.getItemIndex(pageData.getOperateId());
			//cart.getItemIndex(Integer.parseInt(updItemId), Integer.parseInt(sellType), sectionType);
			if (index != -1) {
				// �õ�������Ʒ
				ItemInfo currItem = (ItemInfo) items.get(index);

				currItem.setColor_code(request.getParameter("colorCode"));
				currItem.setSize_code(request.getParameter("sizeCode"));
				
				int ret = OrderDAO.fillItem(conn, currItem);
				if(ret <0) {
					Message.setErrorMsg(request, "��Ӧsku������");
					return mapping.findForward("input");
				}
				
				// ȥ���������ߵ������в�Ʒ����Ʒ
				cart.removeReject();
			}
			
			// �ж�msc������ť��Ч��
			/*if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}*/
			// ������ȯ
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();

			
			// �������浽session��
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
	 * ���¹��ﳵ�е���Ʒ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			//pageData.setColors(ProductBaseDAO.listColor(conn));
			
			// �õ�session���ﳵ
			ShoppingCart2 cart = pageData.getCart();
			
			
			ItemInfo currItem = (ItemInfo) cart.getGifts().get(index);
			
			// ///////
				// �ж��޸ĺ�Ĳ�Ʒ�Ƿ�С�������µ������ֵ
				//ProductForm ProductForm = new ProductDAO().findByCode(conn,
				//		currItem.getItemCode());

				//currItem.setItemQty(Integer.parseInt(request.getParameter("updateItemQty")));
				
				currItem.setColor_code(request.getParameter("colorCode"));
				currItem.setSize_code(request.getParameter("sizeCode"));
				
				int ret = OrderDAO.fillItem(conn, currItem);
				if(ret <0) {
					Message.setErrorMsg(request, "��Ӧsku������");
					return mapping.findForward("input");
				}
				
				//OrderDAO.fillItemPrice(conn, currItem, pageData);
			
				/*if (currItem.getItemQty() > currItem.getMax_count()) {
					Message.setErrorMsg(request, "�ò�Ʒ���ܴ���"
							+ currItem.getMax_count() + "��");
					currItem.setItemQty(currItem.getMax_count());
					return mapping.findForward("input");
				}*/
				
				// ȥ���������ߵ������в�Ʒ����Ʒ
				cart.removeReject();
			
			
			// �ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// ������ȯ
			TicketBO bo = new TicketBO(conn);
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(pageData, checkForm);
			bo.reCheckTicket(checkForm);

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();

			
			// �������浽session��
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
	 * ��չ��ﳵ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;

			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);

			// �õ����ﳵ
			ShoppingCart2 cart = pageData.getCart();

			// ��չ��ﳵ�еĲ�Ʒ����Ʒ����ȯ
			cart.clearShoppingCart();

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
//			�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// �������浽session��
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
	 * ˢ��ҳ�棨orderAdd3.jspת��orderAdd2.jsp��
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
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			ShoppingCart2 cart = pageData.getCart();
			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
//			�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// �������浽session��
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
	 * ������Ʒ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();
			// ҳ����Ϣ
			OrderForm pageData = (OrderForm) form;
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// �õ����ﳵ
			ShoppingCart2 cart = pageData.getCart();
			// ȡ��ҳ��ѡȡ����Ʒ
			//if (!cart.isPreSellOrder()) { // ��Ԥ�۶���
				for (int i = 0; i < 40; i++) {
					// ѡ�еĴ�����Ʒ��������Ʒid��
					String queryAwardId = request.getParameter("giftCode" + i);
					if (queryAwardId == null) {// ÿ�ο����ж��������Ʒ����
						continue;
					} else {
						pageData.setQueryAwardId(Long.parseLong(queryAwardId));// ������Ʒid
						pageData.setSellTypeId(4);// ��������
						pageData.setQueryItemQty(1);// ����
						// ������Ʒ��λ��
						int nIndex = cart.getAllGiftIndex(pageData
								.getQueryAwardId(), pageData.getSellTypeId());
						ItemInfo ii2 = (ItemInfo) cart.getAllGifts().get(nIndex);
						
						// get the promtion gift from
						// shoppingcart in the session
						// fulfil the purchase money and promotion group the item your input is promotion gift
						if (cart.getNotGiftMoney() >= ii2.getFloorMoney()
								&& OrderDAO.ifGroupGift(conn, pageData)) {
							//cart.getGifts().add(0, ii2);
							//�������װ�����в��ף��������ͨ��Ʒ��ֱ�ӷ��빺�ﳵ
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
			//} else { // Ԥ�۶���
			//	Message.setErrorMsg(request, "Ԥ�۲�Ʒ����ѡ�������Ʒ��ֻ�ܵ����µ�");
			//}
			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
			
			// �������浽session��
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
	 * ���Ӷ���Ԫ��ѡ������Ʒ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();
			// ҳ����Ϣ
			OrderForm pageData = (OrderForm) form;
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// �õ����ﳵ
			ShoppingCart2 cart = pageData.getCart();
			// ȡ��ҳ��ѡȡ����Ʒ
			
			String award_id = request.getParameter("award_id");
			List gfs = cart.getAllGifts2();
			Iterator it = gfs.iterator();
			while(it.hasNext()) {
				Proms2 pm = (Proms2)it.next();
				List iis = pm.getItems();
				Iterator it2 = iis.iterator();
				int gfqty = 0;
				int groupid = 0;
				//�����Ʒid�������id��ͬ,���������Ʒ�����ٸ�,�����ĸ�����
				while(it2.hasNext()) {
					ItemInfo ii = (ItemInfo)it2.next();
					if (ii.getAwardId()== Integer.parseInt(award_id)) {
						
						//�����Ʒ����,������Ʒ2list
						groupid = ii.getGroupId();
						if(!ii.isTruss()) {
							cart.addGift2(new ItemInfo(ii));
						} else {
							ii.setSet_group_id(cart.getSetGroupId()+1);
							ArrayList<ItemInfo> parts = OrderDAO.splitGiftSet2Part(conn,ii);
							cart.addGift2Set(parts);
						}
						
						//�������������ȷ����Щ��Ʒ�Ľ��
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
			
			// �������浽session��
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
	 * ɾ����Ʒ
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			OrderForm pageData = (OrderForm) form;

			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// �õ����ﳵ
			ShoppingCart2 cart = pageData.getCart();
			// Ҫɾ����Ʒ��λ��
			int nIndex = pageData.getOperateId();
			
			// ���������ɾ�����򽫸���Ʒ�ӹ��ﳵ��ɾ����ͬʱ����Ҫ����Ʒ�ݴ����ɾ��
			if (delType != null && delType.equals("combout")) {
				ItemInfo itemInfo = (ItemInfo) cart.getGifts().get(nIndex);
				if (itemInfo.getAwardId() > 0) {
					// 1.���ݴ������Ʒ��Ϊȡ��
					AwardDAO ad = new AwardDAO();

					// 2.����ǻ��ֻ��񽫻��ַ�����Ա�ʻ�
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
				// ɾ�����ﳵ����Ʒ
				ItemInfo item = (ItemInfo) cart.getGifts().get(nIndex);
				if("".equals(item.getSet_code())) {
					//ɾ����ǰ��
					cart.removeGift(nIndex);
				} else {
					List items = cart.getGifts();
					//ɾ��������װ
					for(int i=items.size()-1;i>=0;i--) {
						ItemInfo i2 = (ItemInfo)items.get(i);
						if(item.getSet_group_id()==i2.getSet_group_id()
								&&item.getSet_code().equals(i2.getSet_code())) {
							items.remove(i);
						}
					}
				}
			}

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
//			�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			conn.commit();
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// �������浽session��
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
	 * ���˿� ���ڲ���
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
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;

			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);

			OrderForm.copyData(sessData, pageData);

			// �õ����ﳵ
			ShoppingCart2 cart = pageData.getCart();

			// Ԥ�۶����������˿�
			if (cart.isPreSellOrder()) {
				Message.setErrorMsg(request, pageData.getTicketNumber()
						+ "Ԥ�۶����������˿���");
				return mapping.findForward("input");
			}

			try {
				// ���ﳵ��������ȯ
				List inputTickets = cart.getTickets();

				// ���������˿�
				if (TicketBO.getTicketTypeByNumber(pageData.getTicketNumber()) != 3) {
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "�������˿�!");
				}
				// ������˿��Ƿ��ù�
				if (TicketBO.isInMemory(inputTickets, pageData
						.getTicketNumber())) {
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "�Ѿ���ʹ�û���һ����������ͬʱʹ��2�����˿�!");
				}

				int rtn = OrderDAO.checkTicket(conn, pageData);
				switch (rtn) {
				case -1:
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "���Ų�����!");
				case -2:
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "���벻��ȷ!");
				case -3:
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "�ѱ�ʹ��!");
				case -4:
					throw new java.lang.Exception(pageData.getTicketNumber()
							+ "�Ѿ�����!");
				case -5:
					throw new java.lang.Exception("��ͨ����ʱ����Ա����ʹ�����˿�!");
				case -6:
					throw new java.lang.Exception("�������!");
				}

				// ��ȯ���жϣ���Ҫ������ȯһ���飩
				if (inputTickets.size() > 1) { // ����Ƕ�����ȯ�ж��ǲ���������ȯ����
					if (!new TicketBO(conn).checkGiftUseGroup(pageData)) {
						inputTickets.remove(inputTickets.size() - 1);
						throw new java.lang.Exception(pageData
								.getTicketNumber()
								+ "����ͬһ����ȯ��!");
					}
				}
			} catch (Exception e) {
				Message.setErrorMsg(request, e.getMessage());
				forward = "input";
			}

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
//			�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// �������浽session��
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
	 * ʹ����ȯ(���������˿�)
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

			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			String ticket_num = pageData.getOtherGiftNumber();
			String otherGiftPassword = pageData.getOtherGiftPassword();
			OrderForm.copyData(sessData, pageData);
			pageData.setOtherGiftNumber(ticket_num);
			pageData.setOtherGiftPassword(otherGiftPassword);
			List inputTickets = pageData.getCart().getTickets();
			if(inputTickets.size()>0 ) {
				Message.setErrorMsg(request, "ֻ��ʹ��һ����ȯ");
				return mapping.findForward("input");
			}
			try {

				TicketBO ticketBo = new TicketBO(conn);
				TicketMoney newItem = new TicketMoney();
				int before = ticketBo.checkTicket(pageData,newItem);
				/*if (before <0) {
					//�ж�msc������ť��Ч��
					if (pageData.getCart().isRecruitProductInCart()) {
						pageData.setRecruitBtnActive(false);
					}
				}*/
				if (before == -102) {
					Message.setErrorMsg(request, "����Ϊ�ա�");
					return mapping.findForward("input");
				} else if (before == -101) {
					Message.setErrorMsg(request, pageData.getOtherGiftNumber()
							+ "����ĸ����������");
					return mapping.findForward("input");
				} else if (before == -100) {
					Message.setErrorMsg(request, "�Բ������ṩ����ȯ"
							+ pageData.getOtherGiftNumber() + "�����ڡ�");
					return mapping.findForward("input");
				} else if (before == -99) {
					Message.setMessage(request, "�Բ������ṩ����ȯ"
							+ pageData.getOtherGiftNumber() + "�Ѿ�ʹ�ù���");
					return mapping.findForward("input");
				} else if (before == -98) {
					Message.setErrorMsg(request, "�Բ������ṩ����ȯ"
							+ pageData.getOtherGiftNumber() + "�Ѿ���������ʹ�ô�����");
					return mapping.findForward("input");
				} else if (before == -97) {
					Message.setErrorMsg(request, "�Բ������ṩ����ȯ"
							+ pageData.getOtherGiftNumber() + "�����˸���ʹ�ô�����");
					return mapping.findForward("input");
				} else if (before == -96) {
					Message.setErrorMsg(request, "�Բ������ṩ����ȯ"
							+ pageData.getOtherGiftNumber() + "�����������ˣ������޷�ʹ�á�");
					return mapping.findForward("input");
				} else if (before == -103) {
					Message.setErrorMsg(request, "�Բ������ṩ����ȯ"
							+ pageData.getOtherGiftNumber() + "���벻��ȷ�������޷�ʹ�á�");
					return mapping.findForward("input");
				} else if (before == -95) {
					Message.setErrorMsg(request, "�Բ�����������δ�ﵽ��ȯ"
							+ pageData.getOtherGiftNumber() + "Ҫ��Ĺ���������޷�ʹ�á�");
					return mapping.findForward("input");
				}

				// ����ȯ���빺�ﳵ
				
				// ����ҳ���������ȯ�ŵõ���ȯ����
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

				// ��ȯҵ����
				/*int rtn = ticketBo.checkTicket(pageData);
				if (rtn != 0) { // ������
					//	�ж�msc������ť��Ч��
					if (pageData.getCart().isRecruitProductInCart()) {
						pageData.setRecruitBtnActive(false);
					}
					inputTickets.remove(inputTickets.size() - 1);
					if (rtn == -1) {
						Message.setErrorMsg(request, ticket_num + "��ȯ���������");
					}
					if (rtn == -2) {
						Message.setErrorMsg(request, ticket_num + "��ȯ�������");
					}
					// if (rtn == -3) {
					// Message.setErrorMsg(request, "�Բ������ṩ����ȯ" + ticket_num
					// + "����Ҫ�����ض��Ĳ�Ʒ����ʹ�á�");
					// }
					// ��ȯ��Ʒ��
					if (rtn == -301) {
						Message.setErrorMsg(request, "�Բ������ṩ����ȯ" + ticket_num
								+ "���빺��һ������Ʒ�ֵĲ�Ʒ");
					}
					if (rtn == -302) {
						Message.setErrorMsg(request, "�Բ������ṩ����ȯ" + ticket_num
								+ "���빺���Ʒ���еı����Ʒ");
					}
					if (rtn == -303) {
						Message.setErrorMsg(request, "�Բ������ṩ����ȯ" + ticket_num
								+ "���빺��ͬ����һ�������Ĳ�Ʒ");
					}

					if (rtn == -5) {
						Message.setErrorMsg(request, "�Բ������ṩ����ȯ" + ticket_num
								+ "���������⡣��ע���ͷ���Ա������IT����ʦ��ϵ��");
					}
					if (rtn == -6) {
						Message.setErrorMsg(request, "�Բ������Ļ�Ա������ʹ����ȯ"
								+ ticket_num + "��");
					}
					if (rtn == -7) {
						Message.setErrorMsg(request, "�Բ�����Ĺ������������ʹ����ȯ"
								+ ticket_num + "��");
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

			// �Ѿ����빺��������Ʒ���ϱ��
			pageData.getCart().resetAllGift();
//			�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			// �������浽session��
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
	 * ɾ����ȯ
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
		// ��session�еõ�������Ϣ
		OrderForm sessData = (OrderForm) request.getSession().getAttribute(
				Constants.TEMPORARY_ORDER);
		OrderForm.copyData(sessData, pageData);
		ShoppingCart2 cart = pageData.getCart();
		String delTicket = request.getParameter("delTicket");
		int nIndex = cart.getTicketIndex(delTicket);
		cart.unuseTicket(nIndex);
//		�ж�msc������ť��Ч��
		if (pageData.getCart().isRecruitProductInCart()) {
			pageData.setRecruitBtnActive(false);
		}
		Connection conn = DBManager.getConnection();
		
		// �������浽session��
		request.getSession(true).setAttribute(Constants.TEMPORARY_ORDER,
				pageData);
		return mapping.findForward(forward);
	}

	/**
	 * ѡ��ȱ����Ʒ
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
	 * ��ʾ���ֶһ���Ʒ��ҳ�� add by user 2007-12-18
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

			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);

			// �����ʻ���Ϣ
			Exp exp = MemberDAO.getExpByCardId(conn, sessData.getCart()
					.getMember().getCARD_ID());
			request.setAttribute("exp", exp);

			// �����Լ��Ļ������û��ֵ�����Ч��
			setRadioStatus(activity, exp);

			// �������������޸Ķ������
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
			if (exchangeType.equals("A")) {// һ����
				if (exp.getOldAmountExp() >= beginExp) {
					mst.enabledStepDtl();
				}
			} else { // ʵʱ
				if (exp.getAmountExp() >= beginExp) {
					mst.enabledStepDtl();
				}
			}

		}
	}

	/**
	 * �����ֶһ�����Ʒ���빺�ﳵ add by user 2007-12-18
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
			// ��session�еõ�������Ϣ
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
				Message.setMessage(request, "�һ�ʧ�ܣ��˻����ֲ��㣬����", "����", null);
				conn.rollback();
				return mapping.findForward(forward);
			} else if (rtnValue.equals("-2")) {
				Message.setMessage(request, "�һ�ʧ�ܣ������������ô���", "����", null);
				conn.rollback();
				return mapping.findForward(forward);
			} else if (rtnValue.equals("-3")) {
				Message.setMessage(request, "�һ�ʧ�ܣ����ݳ�����������", "����", null);
				conn.rollback();
				return mapping.findForward(forward);
			}

			OrderForm.copyData(sessData, pageData);
			ShoppingCart2 cart = pageData.getCart();
			// ���һ�����Ʒ���빺�ﳵ
			OrderDAO.addExchangeGift(conn, rtnValue, pageData);

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();

			// �������浽session��
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
	 * ��ʾ��һ���Ʒ��ҳ�� add by user 2007-12-18
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

			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);

			// ����Ϣ
			Diamond diamond = DiamondSetsDAO.loadCurrentMemberDiamond(conn,
					sessData.getCart().getMember().getCARD_ID());
			request.setAttribute("diamond", diamond);

			// �����Լ��Ļ������û��ֵ�����Ч��
			 setDiamondRadioStatus(sets, diamond);

			// �������������޸Ķ������
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
	 * ������Ʒ���빺�ﳵ add by user 2008-04-22
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
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);

			StringBuffer awardIds = new StringBuffer();
			DiamondExchange gift = DiamondSetsDAO.findExchangeGiftByPk(conn,
					excId);
			if (gift.getExcId() <= 0) {
				Message.setMessage(request, "�Ҳ����õ��ε���Ʒ����!");
				conn.rollback();
				return mapping.findForward(forward);
			}
			// check exchange condition
			Diamond diamond = DiamondSetsDAO.loadCurrentMemberDiamond(conn,
					sessData.getCart().getMember().getCARD_ID());

			if (diamond.getNomalCount() < gift.getExchangeCount()) {
				Message.setMessage(request, "��ž��겻��֧�����ζһ�!");
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
				Message.setMessage(request, "�Ҳ�����Ӧ�Ķһ�ʱ�����ƣ�������������!");
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
					Message.setMessage(request, "û������ʱ�����ͣ�����ϵ�г������������!");
					conn.rollback();
					return mapping.findForward(forward);
				}
				Period period = new Period(DateUtil.getSqlDate(now), DateUtil
						.getSqlDate(end_date));
				// ͳ�Ƹ�ʱ����ڵĶһ�����
				int exchanged_cnt = DiamondSetsDAO.countDiamondHisByExcTime(
						conn, sessData.getMbId(), period);
				if (exchanged_cnt >= time.getTimes()) {
					Message.setMessage(request, "�ѳ����˱��£�ʱ��Σ��Ķһ�������!");
					conn.rollback();
					return mapping.findForward(forward);
				}
			}

			// �һ�

			// DiamondSets sets = DiamondSetsDAO.findSetByPk(conn,
			// gift.getActionId());
			int days = DiamondSetsDAO.getDiamondGiftKeepDays(conn);
			MemberAWARD award = new MemberAWARD();
			if (gift.getGiftType() == 1) {// 1:���
				Collection packageList = ExpExchangePackageDAO.findByFk(conn,
						gift.getPackageNo());
				Iterator it = packageList.iterator();
				while (it.hasNext()) {
					ExpExchangePackageDtl packDtl = (ExpExchangePackageDtl) it
							.next();
					if (packDtl.getPackageType().equals("G")) {// ��Ʒ
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

					} else if (packDtl.getPackageType().equals("T")) { // ��ȯ
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
						Message.setMessage(request, "��Ʒ���Ͳ���!");
						conn.rollback();
						return mapping.findForward(forward);
					}
				}
			} else if (gift.getGiftType() == 2) {// 2����Ʒ
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
			} else if (gift.getGiftType() == 3) {// 3����ȯ
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
				Message.setMessage(request, "��Ʒ���Ͳ���!");
				conn.rollback();
				return mapping.findForward(forward);
			}

			awardIds.append(-1);
			// ��������ʷ״̬
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
			// �������Ʒ���빺�ﳵ
			OrderDAO.addDiamondGift(conn, awardIds.toString(), pageData);

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();

			// �������浽session��
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
	 * ����ȱ����Ʒ
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
		// ��ӻ����޸�
		String type1 = request.getParameter("type1");
		String forward = "";
		if (type1.equals("add")) {
			forward = "input";
		} else {
			forward = "modify";
		}
		Connection conn = null;
		try {
			// �õ����ݿ�����
			conn = DBManager.getConnection();

			OrderForm pageData = (OrderForm) form;

			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			// ���ڵ���ҳû��mbId�������Ҫ��session�еõ�
			pageData.setMbId(sessData.getMbId());

			// ���ﳵ
			ShoppingCart2 cart = pageData.getCart();

			// //////////////////////////////////////////////
			String item_id = request.getParameter("item_id"); // �µ���Ʒid
			int awardId = Integer.parseInt(request.getParameter("awardId"));// ���ݴ��id

			pageData.setOperateId(Integer.parseInt(item_id));

			int sellTypeId = Integer.parseInt(request
					.getParameter("sellTypeId"));// ��������

			pageData.setOrderId(Integer.parseInt(request
					.getParameter("orderId")));
			int old_item_id = Integer.parseInt(request
					.getParameter("old_item_id"));// �ϲ�Ʒid
			int new_awardId = Integer.parseInt(request
					.getParameter("new_awardId"));// ���ݴ��id

			pageData.setRemark("�ǳ���Ǹ��������Ʒ" + old_item_id + "ȱ������Ϊ������Ϊ��Ʒ "
					+ item_id + " ��ϣ�����ܹ�ϲ��������¡�");
			// ////////////////////////////////////////////////
			int nIndex = cart.getGiftIndex(awardId, sellTypeId);

			if (nIndex != -1) {
				// ɾ�����ﳵ����Ʒ
				cart.removeGift(nIndex);

			}

			// ����������Ʒ���빺�ﳵ
			OrderDAO.changeGift(conn, sellTypeId, awardId, new_awardId,
					pageData);

			// �Ѿ����빺��������Ʒ���ϱ��
			cart.resetAllGift();
//			�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			pageData.setColors(ProductBaseDAO.listColor(conn));
			// �������浽session��
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
	 * ��ʾ����ļ��Ʒҳ��
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
		String msc = request.getParameter("msc"); // ҳ�������msc
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			sessData.setMsc(msc);
			OrderForm.copyData(sessData, pageData);
			
			// �ж��Ƿ��Ѿ�������MSC��Ʒ
			//Collection recruitGifts = (Collection)request.getSession(true).getAttribute("RECRUIT_GIFT");
			//if (recruitGifts == null) {
				Collection recruitGifts = GroupPricesDAO.loadActiveGroupsByMsc(conn, msc, pageData);
				request.getSession(true).setAttribute("RECRUIT_GIFT", recruitGifts); // ����session
			//}
			
			
			// �������������޸Ķ������
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
	 * ��ʾ��������Ʒҳ��
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
		String msc = request.getParameter("msc"); // ҳ�������msc
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			sessData.setMsc(msc);
			OrderForm.copyData(sessData, pageData);
			
//			 �������������޸Ķ������
			String isAdd = request.getParameter("isAdd");
			request.setAttribute("isAdd", isAdd);
			
			//�滻ǰ��Ʒid
			String oldItemId = request.getParameter("oldItemId");
			request.setAttribute("oldItemId", oldItemId);
			
			//�滻ǰ��Ʒ������
			String gpId = request.getParameter("gpId");
			request.setAttribute("gpId", gpId);
			
			//�滻ǰ��Ʒ������
			String oldSectionType = request.getParameter("oldSectionType");
			request.setAttribute("oldSectionType", oldSectionType);
			
			// �ж��Ƿ��Ѿ�������MSC��Ʒ
			Collection recruitGifts = (Collection)request.getSession(true).getAttribute("RECRUIT_GIFT");
			if (recruitGifts == null) {
				recruitGifts = GroupPricesDAO.loadActiveGroupsByMsc(conn, msc, pageData);
				request.getSession(true).setAttribute("RECRUIT_GIFT", recruitGifts); // ����session
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
			//��ʼ��״̬
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
			
			//��ֹ��ѡ���ײ�Ʒ
			if (oldSectionType.equals("D")) {
				it = group.getProduct().iterator();
				while (it.hasNext()) {
					Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next(); 
					int index = pageData.getCart().getItemIndex(gift.getItemId(), gift.getSellType(), "D");
					if (index != -1) { // ����
						gift.setDisabled(1);
					}
				}
				
				//��ֹ������Ʒ
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
				
				//��ֹ���д��ײ�Ʒ
				it = group.getProduct().iterator();
				while (it.hasNext()) {
					Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList)it.next(); 
					gift.setDisabled(1);
				}
				
				//��ֹ��ѡ��Ʒ
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
	 * ��������Ʒ
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
		String priceListId = request.getParameter("priceListId");//�۸�id
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			
			OrderForm.copyData(sessData, pageData);
		
			Recruit_Activity_PriceList gift = Recruit_Activity_PriceListDAO.findByPk(conn, Integer.parseInt(priceListId));
			
			ItemInfo item = new ItemInfo();
			item.setSectionType(gift.getSectionType());
			item.setPriceListLineId(gift.getId()); // ����ļ��Ʒ��id
			//item.setItemId(gift.getItemId());
			item.setItemCode(gift.getItemCode());
			item.setItemName(gift.getItemName());
			item.setFloorMoney(gift.getOverx());
			item.setAddy(0);
			item.setItemPrice(gift.getPrice()); //�۸���Ҫ���¼���
			item.setSellTypeId(-1); // D,E�������Ͷ��Ǵ�������-1
			item.setPriceListLineId(gift.getId()); // �����۸�id
			item.setSellTypeName("��������");
			item.setTruss(false);
			item.setLastSell(gift.getIsLastSell() == 1 ? true: false);
			item.setStandardPrice(gift.getStandardPrice());//-----------------
			
			int nAvailableQty = OrderDAO.getAvailableStockQty(new DBOperation(conn),
					item, pageData);
			if (nAvailableQty <= 1) { // ��治��
				item.setStockStatusId(1);
				if (item.isLastSell())
					item.setStockStatusName("����ȱ��");
				else
					item.setStockStatusName("��ʱȱ��");
			} else {
				item.setStockStatusId(0);
				if (nAvailableQty - 1 < 10) {
					item.setStockStatusName("����ȱ��");
				} else {
					item.setStockStatusName("�������");
				}

			}
			item.setItemName(gift.getItemName());
			item.setCatalog("��ļ����");
			item.setItemUnit(gift.getUnit().getName());
			
			//��λ�����ﳵ
			int index = pageData.getCart().getItemIndex(Integer.parseInt(oldItemId), -1, oldSectionType);
			
			//�滻ǰ�Ȱ�group�õ�
			ItemInfo idxItem = (ItemInfo)pageData.getCart().getItems().get(index);
			item.setGroupId(idxItem.getGroupId());//���μ�¼�����ﳵ��Ʒ
			
			if (index != -1) {
				//�滻
				pageData.getCart().replaceItem(index, item);
				
				if (oldSectionType.equals("D")) { //���������Ʒ��D���ģ���Ҫ���¼�����ײ�Ʒ�۸�
//					����۸񣨽����ﳵ�е���������Ϊ-1��������ΪD�����¼��㣩
					
					
					List list_D = pageData.getCart().getRecruitProductInDSection();
					
					//����D���
					//double saleAmt = pageData.getCart().getRecruitProductAmtInDSection(list_D);
					
					int gpId = 0;
					//GroupPrices group = new GroupPrices();
					//group.setSaleAmt(amt);
					
					//����������D���۸�
					double total_price = 0;
					for (int i = 0; i <list_D.size(); i ++) {
						ItemInfo item_D = (ItemInfo)list_D.get(i);
						if (i == 0) {
							gpId = item_D.getGroupId();
						}
						//Recruit_Activity_PriceList price = new Recruit_Activity_PriceList();
						//price.setStandardPrice(ProductDAO.getStandardPrice(conn, item_D.getItemId()));
						//priceList.add(price);
						//��Ʒ��׼�۸�
						total_price += item_D.getStandardPrice();
						
					}
					//�õ�group
					GroupPrices group = GroupPricesDAO.view(conn, gpId);
					double saleAmt = group.getSaleAmt();
					
					//���ü۸�
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
			// �Ѿ����빺��������Ʒ���ϱ��
			pageData.getCart().resetAllGift();
//			�ж�msc������ť��Ч��
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
//			�������浽session��
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
//		 ��session�еõ�������Ϣ
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
			
			Collection recruitGifts = (ArrayList)request.getSession().getAttribute("RECRUIT_GIFT"); //�����б�
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
			// �������������޸Ķ������
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
	 * ѡ����Ʒ
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
//		 �������������޸Ķ������
		String isAdd = request.getParameter("isAdd");
		request.setAttribute("isAdd", isAdd);
		if (isAdd.equals("true")) {
			forward = "input";
		} else {
			forward = "modify";
		}
		OrderForm pageData = (OrderForm) form;
		String gpId = request.getParameter("gpId"); // ��
		String ids = request.getParameter("ids"); // ��Ʒ
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			// ��session�еõ�������Ϣ
			OrderForm sessData = (OrderForm) request.getSession().getAttribute(
					Constants.TEMPORARY_ORDER);
			OrderForm.copyData(sessData, pageData);
			
			// ����Ϣ
			GroupPrices group = GroupPricesDAO.view(conn, Integer.parseInt(gpId));
			
			if (ids != null && ids.length() > 0) {
				
				Collection priceList = Recruit_Activity_PriceListDAO.findByPks(conn, ids);
				//�������
				if (checkRecruitRules(request, priceList, group)) { // ���ͨ��
					// ��Ʒ�۸����������ƽ�����䣬����Ʒ���빺�ﳵ
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
//			 �Ѿ����빺��������Ʒ���ϱ��
			pageData.getCart().resetAllGift();
			if (pageData.getCart().isRecruitProductInCart()) {
				pageData.setRecruitBtnActive(false);
			}
			//�������浽session��
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
	 * �����ѡ��Ʒ�Ƿ���Ϲ���
	 * @param request
	 * @param priceList
	 * @param group
	 * @return
	 */
	private boolean checkRecruitRules(HttpServletRequest request, Collection priceList, GroupPrices group) {
		
		List seleSetProduct = new ArrayList(); //��ѡ���ײ�Ʒ(D)
		List seleGift = new ArrayList();//��ѡ��Ʒ(E)
		
		// ���������ͷ������ѡ��Ʒ
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
		//�ж�D��
		if (seleSetProduct.size() == 0 ) {
			Message.setErrorMsg(request, "��������Ʒ������С��0��!");
			return false;
		}
		if (seleSetProduct.size() != (int)group.getSaleQty()) {
			Message.setErrorMsg(request, "��������Ʒ���������" + group.getSaleQty() + "��!");
			return false;
		}
		
		//�ж�E��
		/**
		if (group.getIsGift() == 1) {
			if (seleGift.size() < group.getSection().getMinGoods()) {
				Message.setErrorMsg(request, "ѡ�����Ʒ����С��" + group.getSection().getMinGoods() + "��!");
				return false;
			}
			if (seleGift.size() > group.getSection().getMaxGoods()) {
				Message.setErrorMsg(request, "ѡ�����Ʒ���ܴ���!" + group.getSection().getMaxGoods() + "��!");
				return false;
			}
		}
		*/
		return true;
	}
	
	/**
	 * ����D����Ʒ�۸�
	 * @param setsProduct
	 * @param group
	 */
	private void calcProductPrice (List setsProduct, GroupPrices group) {
		
		if (setsProduct.size() == 0) {
			return;
		}
		
		double total_price = 0;
		
		
		//����������
		for (int i = 0; i < setsProduct.size(); i ++ ) {
			Recruit_Activity_PriceList gift = (Recruit_Activity_PriceList) setsProduct.get(i);
			total_price += gift.getStandardPrice();
		}
		
		if (total_price <= 0) {
			return;
		}
		
		//���ü۸�
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
	 * ���빺�ﳵ
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
			item.setPriceListLineId(gift.getId()); // ����ļ��Ʒ��id
			//item.setItemId(gift.getItemId());
			item.setItemCode(gift.getItemCode());
			item.setItemName(gift.getItemName());
			item.setFloorMoney(gift.getOverx());
			item.setAddy(0);
			item.setItemPrice(gift.getPrice()); //�۸���Ҫ���¼���
			item.setSellTypeId(-1); // D,E�������Ͷ��Ǵ�������-1
			item.setPriceListLineId(gift.getId()); // �����۸�id
			item.setSellTypeName("��������");
			item.setTruss(false);
			item.setLastSell(gift.getIsLastSell() == 1 ? true: false);
			item.setStandardPrice(gift.getStandardPrice());//----------------
			item.setGroupId(group.getGpId());//���μ�¼�����ﳵ��Ʒ
			int nAvailableQty = OrderDAO.getAvailableStockQty(db,
					item, pageData);
			if (nAvailableQty <= 1) { // ��治��
				item.setStockStatusId(1);
				if (item.isLastSell())
					item.setStockStatusName("����ȱ��");
				else
					item.setStockStatusName("��ʱȱ��");
			} else {
				item.setStockStatusId(0);
				if (nAvailableQty - 1 < 10) {
					item.setStockStatusName("����ȱ��");
				} else {
					item.setStockStatusName("�������");
				}

			}
			item.setItemName(gift.getItemName());
			item.setCatalog("��ļ����");
			item.setItemUnit(gift.getUnit().getName());
			
			pageData.getCart().getItems().add(0,item);
		}
	}
}

/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.magic.crm.common.Constants;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.LogicUtility;
import com.magic.crm.common.WebAction;
import com.magic.crm.common.WebForm;
import com.magic.crm.member.dao.MemberAddressDAO;

import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.dao.PackageTypeDAO;
import com.magic.crm.order.entity.ItemInfo;

import com.magic.crm.order.entity.TicketMoney;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.order.entity.ShoppingCart2;

import com.magic.crm.util.CodeName;
import com.magic.crm.util.Message;
import com.magic.crm.order.bo.TicketBO;
import com.magic.crm.util.ChangeCoding;


/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OrderAddThirdAction extends WebAction {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magic.crm.common.WebAction#execute(com.magic.crm.common.DBOperation,
	 *      com.magic.crm.common.WebForm)
	 */
	protected String execute(HttpServletRequest request,
			HttpServletResponse response, DBOperation db, WebForm form)
			throws Exception {
		
		OrderForm pageData = (OrderForm) form;
		
		// 从session中取购物篮
		OrderForm sessionData = (OrderForm) request.getSession(true)
				.getAttribute(Constants.TEMPORARY_ORDER);
		ShoppingCart2 cart = sessionData.getCart();

		sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
		
		if(!pageData.getManualFreeFreight())
		{
			sessionData.setFreeFreightReason("");
		}
		
		//判断购物篮中是否有缺货产品
		if (cart.isCartOOS()) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"订单中有缺货产品,不能下单!");
			return "add2";	
		}
		
		//预售订单只能单独下单，不能和其他商品同时下单
		// delete by zhux 20080915 customer request
		/*if (cart.isPreSellOrder()) {
			if( cart.getItems().size()>1 || cart.getGifts().size()>0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"OVS产品只能单独下单!");
				return "add2";
			}
		}*/
		
		//如果是预售订单，不能使用其他礼品礼券
		/*if (cart.isPreSellOrder()) {
			if (cart.getTickets().size() > 0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"预售订单不能使用幸运卡或者其他电子礼券!");
				return "add2";
			}
			if (cart.hasNomalGifts()) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"预售订单不能选择其他礼品!");
				return "add2";
			}
			
		}*/
		
		//所有已选礼品
		List lstGifts = cart.getGifts();
		for (int i = 0; i < lstGifts.size(); i++) {
			ItemInfo ii = (ItemInfo) lstGifts.get(i);
			
			// 入会礼品(modified by user)
			if (ii.getSellTypeId() == 17 && ii.getIs_transfer() == 0) {
				if (ii.getFloorMoney() > cart.getNotGiftMoney()) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "得入会送礼品"+ii.getItemName()+"，正常购物金额必须满" + ii.getFloorMoney() + "元！");
					return "add2";
				}
			}
			
			// 没有购买相关组产品，不能获得相关组礼品
			if (ii.getSellTypeId() == 4 && ii.getFlag() == 3
					&& ii.getIs_transfer() == 0) {
				if (OrderDAO.checkAddThirdPromotion(db, sessionData, ii.getGift_group_id()) != 1) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "你选择的礼品赠品" + ii.getItemCode()
							+ "不满足条件，原因：没有选择对应组产品！");
					return "add2";
				}
			}
		}
		
		//检查xx元选几件礼品是否件数正确
		lstGifts = cart.getGifts2();
		for(int i=0;i<lstGifts.size();i++) {
			ItemInfo ii = (ItemInfo)lstGifts.get(i);
			if (ii.getItemPrice() == 0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "xx元选几件的商品数量不正确！");
				return "add2";
			}
		}
		
		// 检查所有礼品赠品的金额总合是否满足订单购物金额条件
		if (OrderDAO.checkAddThird(db, sessionData) == 1) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request, "你选择的礼品赠品不满足条件，原因：购物金额不够！");
			return "add2";
		}

		// session、订单类型是否为空
		if (sessionData == null || sessionData.getPrTypeId() == 0) {
			OrderForm.copyData(sessionData, pageData);
			// 没有session
			Message.setErrorMsg(request, "您的操作时间超时或未按正确顺序进入页面！");
			return "add2";
		}

		// 检查礼券
		if (!sessionData.getCart().getTickets().isEmpty()) {
			if (!checkTicket(sessionData)) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "订单购物金额不满足礼券使用条件");
				return "add2";
			}
		}

		// 处理按钮Action
		String strAction = pageData.getActionType();
		
		if ("insertOrder".equalsIgnoreCase(strAction))
		{
			
			//如果是预售订单，不支持货到付款
			/*if (cart.isPreSellOrder()) {
				if (sessionData.getCart().getDeliveryInfo().getPaymentTypeId() == 1) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "预售订单不支持货到付款，谢谢!");
					return "input";
				}
			}*/
			
			//得到缺货处理
			sessionData.getCart().getOtherInfo().setOOSPlan(pageData.getOOSPlan());

			// 得到是否需要发票
			sessionData.getCart().getOtherInfo().setNeedInvoice(pageData.getNeedInvoice());
			String invoice_title = pageData.getInvoice_title();
			sessionData.getCart().getOtherInfo().setInvoice_title(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getInvoice_title())));
			
			// 信用卡相关信息
			sessionData.getCart().getOtherInfo().setCredit_card(request.getParameter("credit_card"));
			sessionData.getCart().getOtherInfo().setId_card(request.getParameter("id_card"));
			sessionData.getCart().getOtherInfo().setEf_year(Integer.parseInt(request.getParameter("ef_year")==null?"0":request.getParameter("ef_year")));
			sessionData.getCart().getOtherInfo().setEf_month(Integer.parseInt(request.getParameter("ef_month")==null?"0":request.getParameter("ef_month")));
			sessionData.getCart().getOtherInfo().setVer_code(request.getParameter("ver_code"));
			
			// 得到包装方式
			sessionData.setPackage_type(pageData.getPackage_type());
			
			//备注
			sessionData.getCart().getOtherInfo().setRemark(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getRemark())));
			
			//得到目录
			//sessionData.getCart().getOtherInfo().setCatalog(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getCatalog())));
			sessionData.setMsc(pageData.getMsc());			
			
			//手工免运费
			sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
			sessionData.setFreeFreightReason(pageData.getFreeFreightReason());
			//如果免发送费，则运费=0
			if(sessionData.getManualFreeFreight())
			{
				sessionData.getCart().getDeliveryInfo().setDeliveryFee(0.00);
			}
			
			// 从session中取操作人员信息
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				Message.setErrorMsg(request, "您的操作时间超时，当前操作人员信息丢失！");
				return "error";
			} else {
				sessionData.setCreatorId(LogicUtility.parseInt(user.getId(), 0)); //创建人
			}
			
			// 检查送货方式
			if (sessionData.getCart().getDeliveryInfo().getDeliveryTypeId() == -1) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "请选择送货方式");
				return "input";
			}
			
			//检查付款方式
			if (sessionData.getCart().getDeliveryInfo().getPaymentTypeId() == -1) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "请选择付款方式");
				return "input";
			}
			// 如果是直送,需要查看邮编是否符合
			/*if (sessionData.getCart().getDeliveryInfo().getDeliveryTypeId() == 3) {
				if (!OrderDAO.isPostCodeDelivery(db.conn, sessionData
						.getCart().getDeliveryInfo().getPostCode())) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "该邮编不能直送");
					return "input";
				}
			}*/
			
			//没有正常购物产品
			//delete by zhux 20080915没有正常销售产品也可以下定单
			/*if (sessionData.getCart().getItems().size() == 0 && !sessionData.getCart().existRecruitGift()) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "购物车中没有物品");
				return "input";
			}*/

			db.setAutoCommit(false);

			TicketBO bo = new TicketBO();
			bo.setConnection(db.conn);
			
			// 重新检查礼券
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(sessionData, checkForm);
			bo.reCheckTicket(checkForm);
			
			//根据包装方式取得包装费
			OrderDAO.getPackageFee(db.conn, sessionData);
			
			sessionData.setUse_deposit( pageData.getUse_deposit() );
			
			// 插入订单头信息
			OrderDAO.insertMaster(db, sessionData);

			// 插入订单产品信息
			OrderDAO.insertLine(db, sessionData);

			//插入礼券
			bo.insertTicketUse(sessionData);
			
			db.commit();
			// 提示信息
			Message.setMessage(request,
					"订单新增成功！订单号为："+sessionData.getOrderNumber());

			// 删除session
			request.getSession(true).removeAttribute(Constants.TEMPORARY_ORDER);

			// 运行订单
			OrderDAO.runOrder(db.conn, sessionData);

			// 如果支付方式不是银联,直接提示成功即可,如果是银联,提供提供转到银联的连接
			if(sessionData.getCart().getDeliveryInfo().getPaymentTypeId()!=94) {
				return "success";
			} else {
				
				String ordId = sessionData.getOrderNumber();
				ordId = ordId.substring(1,5) + "01003" + "0" + ordId.substring(7);
				int pay = (int) (sessionData.getCart().getOrderOwe()*100);
				DecimalFormat df = new DecimalFormat("000000000000");
				String payable = df.format(pay);
				
				System.out.println("银联支付金额" + payable);

				SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
				String tranDate = fm.format(new java.util.Date());
				String chkVal = new com.magic.crm.util.ChinaPayUtil()
					.signOrder("808080090301003", ordId, payable, "156", tranDate, "0001");
				
				request.setAttribute("OrdId", ordId);
				request.setAttribute("TransAmt", payable);
				request.setAttribute("TransDate", tranDate);
				request.setAttribute("ChkValue", chkVal);
				return "chinapay";
			}
			
		//如果是更改地址	
		} else if ("changeAddress".equalsIgnoreCase(strAction)) {
			MemberAddressDAO.updateAddress(db.conn, pageData.getMbId(),
					pageData.getReceiptorAddressId());
			db.commit();
			
		//如果是更改发送方式
		} else if ("changeDelivery".equalsIgnoreCase(strAction)) {
			OrderDAO.changeDelivery(db, pageData);
			db.commit();
			
		//如果是更改付款方式
		} else if ("changePayment".equalsIgnoreCase(strAction)) {
			OrderDAO.changePayment(db, pageData);
			db.commit();
			
		} else {
			// modify bymagic at 2005-10-25
			/*
			 * 满足条件的礼品已经在OrderAddSecondAction取出
			 */
			// OrderDAO.addLargess2(db.conn,sessionData);
		}
		// 订单来源
		//pageData.setPrTypeName(sessionData.getPrTypeName());
		
		
		
		// 得到送货信息、付款方式
		OrderDAO.getMemberInfo(db, sessionData);
		
		// 得到包装费列表
		pageData.setPackages(PackageTypeDAO.listPackages(db.conn));

		// 判断是否免发送费
		double ret = OrderDAO.getDeliveryFee(db, sessionData);
		
		if (ret < 0) {
			Message.setMessage(request,"取发送费出错!错误号："+ret);
			return "input";
		}
		
		
		
		// 数据复制
		OrderForm.copyData(sessionData, pageData);

		return "input";
	}

	

	/**
	 * 检查礼券
	 * 
	 * @param data
	 * @return
	 */
	private boolean checkTicket(OrderForm data) {
		boolean ret = false;
		double itemMoney = data.getCart().getNotGiftMoney();
		Iterator it = data.getCart().getTickets().iterator();
		while (it.hasNext()) {
			TicketMoney ticket = (TicketMoney) it.next();
			if (itemMoney >= ticket.getOrder_floor()) {
				ret = true;
				break;
			}
		}

		return ret;
	}
	
}
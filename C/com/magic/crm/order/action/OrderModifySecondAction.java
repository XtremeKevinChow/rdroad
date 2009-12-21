/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;


import java.io.Console;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.order.bo.TicketBO;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.dao.PackageTypeDAO;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.entity.TicketMoney;
import com.magic.crm.order.entity.ShoppingCart2;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.ChangeCoding;
import com.magic.crm.util.Message;
import com.magic.crm.util.CodeName;
/**
 * @author Water
 * 
 * 对Session中的订单对象进行操作
 */
public class OrderModifySecondAction extends WebAction {
	
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magic.crm.common.WebAction#execute(com.magic.crm.common.DBOperation,
	 *      com.magic.crm.common.WebForm)
	 */
	protected String execute(HttpServletRequest request,HttpServletResponse response,DBOperation db, WebForm form) throws Exception {
		
		OrderForm pageData = (OrderForm) form;
		
		// 从session中取购物篮
		OrderForm sessionData = (OrderForm) request.getSession(true)
				.getAttribute(Constants.TEMPORARY_ORDER);
		
		ShoppingCart2 cart = sessionData.getCart();
		
		sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
		
		
//		判断msc促销按钮有效性
		if (cart.isRecruitProductInCart()) {
			pageData.setRecruitBtnActive(false);
		}
		//购物总金额
		//double goods_fee = sessionData.getCart().getTotalMoney();
		
		//判断订单中是否有正常购物的产品（如果入会礼品也可以），如果没有不能提交订单
		/*if (cart.getItems().size() == 0 && !cart.existRecruitGift()) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"您没有购买正常产品,不能下单!");
			return "modify";	
		}*/
		//判断购物篮中是否有缺货产品
		if (cart.isCartOOS()) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"订单中有缺货产品,不能下单!");
			return "modify";	
		}
		
		//预售订单只能单独下单，不能和其他商品同时下单
		/*if (cart.isPreSellOrder()) {
			if( cart.getItems().size()>1 || cart.getGifts().size()>0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"OVS产品只能单独下单!");
				return "add2";
			}
		}*/
		
		//如果是预售订单，不能使用礼券和礼品
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
		//购物篮中所有已选礼品
		List lstGifts = cart.getGifts();	
		
		for (int i = 0; i < lstGifts.size(); i++) {	
			ItemInfo ii = (ItemInfo) lstGifts.get(i);
				
			//注册送礼品
            if(ii.getSellTypeId() == 17 ){
            	MemberGetAwardDAO awardDao = new MemberGetAwardDAO();
            	MemberAWARD award = awardDao.findById(db.conn, ii.getAwardId());
            	
                if(award != null && award.getOrderRequire() > cart.getNotGiftMoney()){
                	OrderForm.copyData(sessionData, pageData);
                	Message.setErrorMsg(request, "得入会送礼品"+ii.getItemName()+"，正常购物金额必须满" + award.getOrderRequire() + "元！");
        			return "modify";	
        	    }    
            } 
                            
			//没有购买相关组产品，不能获得相关组礼品
			if(ii.getSellTypeId() == 4 && ii.getFlag()== 3){
				if(OrderDAO.checkAddThirdPromotion(db,sessionData,ii.getGift_group_id()) !=1 ){
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request,"你选择的礼品赠品"+ii.getItemCode()+"不满足条件，原因：没有选择对应组产品！");
		    		return "modify";	   				
				}				
			}
		}
		
		//检查所有礼品赠品的金额总合是否满足订单购物金额条件
		if(OrderDAO.checkAddThird(db, sessionData) == 1){
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"你选择的礼品赠品不满足条件，原因：购物金额不够！");
			return "modify";	
		}
		
		//检查xx元选几件礼品是否件数正确
		lstGifts = cart.getGifts2();
		for(int i=0;i<lstGifts.size();i++) {
			ItemInfo ii = (ItemInfo)lstGifts.get(i);
			if (ii.getItemPrice() == 0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "xx元选几件的商品数量不正确！");
				return "modify";
			}
		}

		//session丢失或没有选择订单类型
		if (sessionData == null || sessionData.getCart().getOrder().getPrTypeId() == 0) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"您的操作时间超时或未按正确顺序进入页面！");
			return "modify";
		}
		
		//检查礼券
		if(!sessionData.getCart().getTickets().isEmpty()) {
			if(!checkTicket(sessionData)) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"订单购物金额不满足礼券使用条件");
				return "modify";
			}
		}
		
		//得到联盟信息
		//OrderDAO.viewUnionInfo(db.conn, sessionData);
		//选中值
		//java.util.Collection catalogList = OrderDAO.getValidCatalogList(db.conn);
		//OrderForm.checkedDefaultItem(sessionData, catalogList);
		//有效目录列表（add by user 2007-08-21）
		//request.setAttribute("catalogList",catalogList);
		
		// 处理按钮Action
		String strAction = pageData.getActionType();
		
		if ("changeAddress".equalsIgnoreCase(strAction)) {
		    MemberAddressDAO.updateAddress(db.conn, pageData.getMbId(), pageData.getReceiptorAddressId());
			db.commit();
		} else if ("changeDelivery".equalsIgnoreCase(strAction)) {
			OrderDAO.changeDelivery(db, pageData);
			db.commit();
		} else if ("changePayment".equalsIgnoreCase(strAction)) {
			OrderDAO.changePayment(db, pageData);
			db.commit();
		} else if ("updateOrder".equalsIgnoreCase(strAction)) {
		   
			//如果是预售订单，不支持货到付款
			/*if (cart.isPreSellOrder()) {
				if (sessionData.getCart().getDeliveryInfo().getPaymentTypeId() == 1) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "预售订单不支持货到付款，谢谢!");
					return "modify2";
				}
			}*/
		    //OrderDAO.insertOrderHeaderHis(db, sessionData.getOrderId());
		    
		    sessionData.setUse_deposit(pageData.getUse_deposit());
		    
		    //得到缺货处理
			sessionData.getCart().getOtherInfo().setOOSPlan(pageData.getOOSPlan());

			// 得到是否需要发票
			sessionData.getCart().getOtherInfo().setNeedInvoice(pageData.getNeedInvoice());
			sessionData.getCart().getOtherInfo().setInvoice_title(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getInvoice_title())));
			
			// 信用卡相关信息
			sessionData.getCart().getOtherInfo().setCredit_card(request.getParameter("credit_card"));
			sessionData.getCart().getOtherInfo().setId_card(request.getParameter("id_card"));
			sessionData.getCart().getOtherInfo().setEf_year(Integer.parseInt(request.getParameter("ef_year")==null||request.getParameter("ef_year").equals("")?"0":request.getParameter("ef_year")));
			sessionData.getCart().getOtherInfo().setEf_month(Integer.parseInt(request.getParameter("ef_month")==null||request.getParameter("ef_month").equals("")?"0":request.getParameter("ef_month")));
			sessionData.getCart().getOtherInfo().setVer_code(request.getParameter("ver_code"));
			
			
			// 得到包装方式
			sessionData.setPackage_type(pageData.getPackage_type());
			
			// 备注信息
			sessionData.getCart().getOtherInfo().setRemark(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getRemark())));
			
			//手工免运费
			sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
			sessionData.setFreeFreightReason(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getFreeFreightReason())));
			
			//得到目录
			//sessionData.getCart().getOtherInfo().setCatalog(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getCatalog())));
			
			// 从session中取操作人员信息
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				Message.setErrorMsg(request,"您的操作时间超时，当前操作人员信息丢失！");
				return "error";
			} else {
				sessionData.getCart().getOrder().setCreatorId(LogicUtility.parseInt(user.getId(), 0));
			}
			
			//检查送货方式
			if (sessionData.getCart().getDeliveryInfo().getDeliveryTypeId() == -1) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"请选择送货方式");
				return "modify2";
			}
			//检查付款方式
			if (sessionData.getCart().getDeliveryInfo().getPaymentTypeId() == -1) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"请选择付款方式");
				return "modofy2";
			}
			//如果是直送,需要查看邮编是否符合
			/*if (sessionData.getCart().getDeliveryInfo().getDeliveryTypeId() == 3 ) {
				if (!OrderDAO.isPostCodeDelivery(db.conn,sessionData.getCart().getDeliveryInfo().getPostCode())) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request,"该邮编不能直送");
					return "modify2";
				}
			}*/
			
			// 取消原有数据
			pageData.setOrderId(sessionData.getCart().getOrder().getOrderId());
			pageData.setCreatorId(sessionData.getCart().getOrder().getCreatorId());
			int nCancel = OrderDAO.cancelOrder(db, pageData);
			
			if (nCancel != 1) {
				// 取消失败
				throw new Exception("修改订单" + sessionData.getCart().getOrder().getOrderNumber()
						+ "调用存储过程失败！");
			}
			
			// 更新新数据,更新后状态是新建状态
			sessionData.getCart().getOrder().setStatusId(0);
			
			//根据包装方式取得包装费
			OrderDAO.getPackageFee(db.conn, sessionData);
			
			int n1 = OrderDAO.updateOrderHeader(db.conn, sessionData);
			
			if (n1 < 1) {
				throw new Exception("修改订单" + sessionData.getCart().getOrder().getOrderNumber()
						+ "更新头信息失败！");
			}
			
			

			int n2 = OrderDAO.updateLine(db, sessionData);

			if (n2 < 1) {
				throw new Exception("修改订单" + sessionData.getCart().getOrder().getOrderNumber()
						+ "更新产品信息失败！");
			}
			//更新礼券使用情况
			TicketBO bo = new TicketBO(db.conn);
			bo.insertTicketUse(sessionData);
			
			//插入联盟数据
			//OrderDAO.deleteUnionInfo(db.conn, sessionData);
			//OrderDAO.insertUnionInfo(db.conn, sessionData);
			
//			更新Recruit_Members
			//OrderDAO.updateRecruitMember(db.conn, sessionData, 1);
			
			// 更新订单状态
			// 0:订单创建
			
			OrderDAO.updateOrderStatus(db, sessionData.getOrderId(), 0);
			
			db.commit();
			Message.setMessage(request,"订单" + sessionData.getCart().getOrder().getOrderNumber() + "修改成功！");
			// 删除session
			request.getSession(true).removeAttribute(
					Constants.TEMPORARY_ORDER);
			
			
			/////////////////////////////运行订单
			OrderDAO.runOrder(db.conn,sessionData);
			/////////////////////////////
			
			// 如果支付方式不是银联,直接提示成功即可,如果是银联,提供提供转到银联的连接
			if(sessionData.getCart().getDeliveryInfo().getPaymentTypeId()!=94) {
				return "success";
			} else {
				
				String ordId = sessionData.getCart().getOrder().getOrderNumber();
				ordId = ordId.substring(1,5) + "01003" + "0" + ordId.substring(7);
				int pay = (int) (sessionData.getCart().getOrderOwe()*100);
				DecimalFormat df = new DecimalFormat("000000000000");
				String payable = df.format(pay);
				
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
			
		}
		else if(strAction.equals("manualFreeFreight"))
		{
			sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
			if(!pageData.getManualFreeFreight())
			{
				sessionData.setFreeFreightReason("");
			}
		}
		// 得到订单头信息
		OrderDAO.getOrderHeadersInfo(db, pageData);

		// 订单来源
		//pageData.setPrTypeName(sessionData.getPrTypeName());

		// 得到送货信息、付款方式
		OrderDAO.getMemberInfo(db, sessionData);
		
		// 判断是否免发送费
		double ret = OrderDAO.getDeliveryFee(db,sessionData);
		if (ret < 0) {
			Message.setMessage(request,"取发送费出错!错误号："+ret);
			return "modify2";
		}

		// 得到包装费列表
		pageData.setPackages(PackageTypeDAO.listPackages(db.conn));
		
		// 数据复制
		OrderForm.copyData(sessionData, pageData);

		//下单时把送货地址改称默认地址
		MemberAddressDAO.updateAddress(db.conn,sessionData.getMbId(), sessionData.getReceiptorAddressId());

		return "modify2";
	}

}
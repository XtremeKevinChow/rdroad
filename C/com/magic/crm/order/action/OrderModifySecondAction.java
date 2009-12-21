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
 * ��Session�еĶ���������в���
 */
public class OrderModifySecondAction extends WebAction {
	
	/**
	 * �����ȯ
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
		
		// ��session��ȡ������
		OrderForm sessionData = (OrderForm) request.getSession(true)
				.getAttribute(Constants.TEMPORARY_ORDER);
		
		ShoppingCart2 cart = sessionData.getCart();
		
		sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
		
		
//		�ж�msc������ť��Ч��
		if (cart.isRecruitProductInCart()) {
			pageData.setRecruitBtnActive(false);
		}
		//�����ܽ��
		//double goods_fee = sessionData.getCart().getTotalMoney();
		
		//�ж϶������Ƿ�����������Ĳ�Ʒ����������ƷҲ���ԣ������û�в����ύ����
		/*if (cart.getItems().size() == 0 && !cart.existRecruitGift()) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"��û�й���������Ʒ,�����µ�!");
			return "modify";	
		}*/
		//�жϹ��������Ƿ���ȱ����Ʒ
		if (cart.isCartOOS()) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"��������ȱ����Ʒ,�����µ�!");
			return "modify";	
		}
		
		//Ԥ�۶���ֻ�ܵ����µ������ܺ�������Ʒͬʱ�µ�
		/*if (cart.isPreSellOrder()) {
			if( cart.getItems().size()>1 || cart.getGifts().size()>0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"OVS��Ʒֻ�ܵ����µ�!");
				return "add2";
			}
		}*/
		
		//�����Ԥ�۶���������ʹ����ȯ����Ʒ
		/*if (cart.isPreSellOrder()) {
			if (cart.getTickets().size() > 0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"Ԥ�۶�������ʹ�����˿���������������ȯ!");
				return "add2";
			}
			if (cart.hasNomalGifts()) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"Ԥ�۶�������ѡ��������Ʒ!");
				return "add2";
			}
		}*/
		//��������������ѡ��Ʒ
		List lstGifts = cart.getGifts();	
		
		for (int i = 0; i < lstGifts.size(); i++) {	
			ItemInfo ii = (ItemInfo) lstGifts.get(i);
				
			//ע������Ʒ
            if(ii.getSellTypeId() == 17 ){
            	MemberGetAwardDAO awardDao = new MemberGetAwardDAO();
            	MemberAWARD award = awardDao.findById(db.conn, ii.getAwardId());
            	
                if(award != null && award.getOrderRequire() > cart.getNotGiftMoney()){
                	OrderForm.copyData(sessionData, pageData);
                	Message.setErrorMsg(request, "���������Ʒ"+ii.getItemName()+"�����������������" + award.getOrderRequire() + "Ԫ��");
        			return "modify";	
        	    }    
            } 
                            
			//û�й���������Ʒ�����ܻ���������Ʒ
			if(ii.getSellTypeId() == 4 && ii.getFlag()== 3){
				if(OrderDAO.checkAddThirdPromotion(db,sessionData,ii.getGift_group_id()) !=1 ){
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request,"��ѡ�����Ʒ��Ʒ"+ii.getItemCode()+"������������ԭ��û��ѡ���Ӧ���Ʒ��");
		    		return "modify";	   				
				}				
			}
		}
		
		//���������Ʒ��Ʒ�Ľ���ܺ��Ƿ����㶩������������
		if(OrderDAO.checkAddThird(db, sessionData) == 1){
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"��ѡ�����Ʒ��Ʒ������������ԭ�򣺹��������");
			return "modify";	
		}
		
		//���xxԪѡ������Ʒ�Ƿ������ȷ
		lstGifts = cart.getGifts2();
		for(int i=0;i<lstGifts.size();i++) {
			ItemInfo ii = (ItemInfo)lstGifts.get(i);
			if (ii.getItemPrice() == 0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "xxԪѡ��������Ʒ��������ȷ��");
				return "modify";
			}
		}

		//session��ʧ��û��ѡ�񶩵�����
		if (sessionData == null || sessionData.getCart().getOrder().getPrTypeId() == 0) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"���Ĳ���ʱ�䳬ʱ��δ����ȷ˳�����ҳ�棡");
			return "modify";
		}
		
		//�����ȯ
		if(!sessionData.getCart().getTickets().isEmpty()) {
			if(!checkTicket(sessionData)) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"���������������ȯʹ������");
				return "modify";
			}
		}
		
		//�õ�������Ϣ
		//OrderDAO.viewUnionInfo(db.conn, sessionData);
		//ѡ��ֵ
		//java.util.Collection catalogList = OrderDAO.getValidCatalogList(db.conn);
		//OrderForm.checkedDefaultItem(sessionData, catalogList);
		//��ЧĿ¼�б�add by user 2007-08-21��
		//request.setAttribute("catalogList",catalogList);
		
		// ����ťAction
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
		   
			//�����Ԥ�۶�������֧�ֻ�������
			/*if (cart.isPreSellOrder()) {
				if (sessionData.getCart().getDeliveryInfo().getPaymentTypeId() == 1) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "Ԥ�۶�����֧�ֻ������лл!");
					return "modify2";
				}
			}*/
		    //OrderDAO.insertOrderHeaderHis(db, sessionData.getOrderId());
		    
		    sessionData.setUse_deposit(pageData.getUse_deposit());
		    
		    //�õ�ȱ������
			sessionData.getCart().getOtherInfo().setOOSPlan(pageData.getOOSPlan());

			// �õ��Ƿ���Ҫ��Ʊ
			sessionData.getCart().getOtherInfo().setNeedInvoice(pageData.getNeedInvoice());
			sessionData.getCart().getOtherInfo().setInvoice_title(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getInvoice_title())));
			
			// ���ÿ������Ϣ
			sessionData.getCart().getOtherInfo().setCredit_card(request.getParameter("credit_card"));
			sessionData.getCart().getOtherInfo().setId_card(request.getParameter("id_card"));
			sessionData.getCart().getOtherInfo().setEf_year(Integer.parseInt(request.getParameter("ef_year")==null||request.getParameter("ef_year").equals("")?"0":request.getParameter("ef_year")));
			sessionData.getCart().getOtherInfo().setEf_month(Integer.parseInt(request.getParameter("ef_month")==null||request.getParameter("ef_month").equals("")?"0":request.getParameter("ef_month")));
			sessionData.getCart().getOtherInfo().setVer_code(request.getParameter("ver_code"));
			
			
			// �õ���װ��ʽ
			sessionData.setPackage_type(pageData.getPackage_type());
			
			// ��ע��Ϣ
			sessionData.getCart().getOtherInfo().setRemark(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getRemark())));
			
			//�ֹ����˷�
			sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
			sessionData.setFreeFreightReason(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getFreeFreightReason())));
			
			//�õ�Ŀ¼
			//sessionData.getCart().getOtherInfo().setCatalog(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getCatalog())));
			
			// ��session��ȡ������Ա��Ϣ
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				Message.setErrorMsg(request,"���Ĳ���ʱ�䳬ʱ����ǰ������Ա��Ϣ��ʧ��");
				return "error";
			} else {
				sessionData.getCart().getOrder().setCreatorId(LogicUtility.parseInt(user.getId(), 0));
			}
			
			//����ͻ���ʽ
			if (sessionData.getCart().getDeliveryInfo().getDeliveryTypeId() == -1) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"��ѡ���ͻ���ʽ");
				return "modify2";
			}
			//��鸶�ʽ
			if (sessionData.getCart().getDeliveryInfo().getPaymentTypeId() == -1) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"��ѡ�񸶿ʽ");
				return "modofy2";
			}
			//�����ֱ��,��Ҫ�鿴�ʱ��Ƿ����
			/*if (sessionData.getCart().getDeliveryInfo().getDeliveryTypeId() == 3 ) {
				if (!OrderDAO.isPostCodeDelivery(db.conn,sessionData.getCart().getDeliveryInfo().getPostCode())) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request,"���ʱ಻��ֱ��");
					return "modify2";
				}
			}*/
			
			// ȡ��ԭ������
			pageData.setOrderId(sessionData.getCart().getOrder().getOrderId());
			pageData.setCreatorId(sessionData.getCart().getOrder().getCreatorId());
			int nCancel = OrderDAO.cancelOrder(db, pageData);
			
			if (nCancel != 1) {
				// ȡ��ʧ��
				throw new Exception("�޸Ķ���" + sessionData.getCart().getOrder().getOrderNumber()
						+ "���ô洢����ʧ�ܣ�");
			}
			
			// ����������,���º�״̬���½�״̬
			sessionData.getCart().getOrder().setStatusId(0);
			
			//���ݰ�װ��ʽȡ�ð�װ��
			OrderDAO.getPackageFee(db.conn, sessionData);
			
			int n1 = OrderDAO.updateOrderHeader(db.conn, sessionData);
			
			if (n1 < 1) {
				throw new Exception("�޸Ķ���" + sessionData.getCart().getOrder().getOrderNumber()
						+ "����ͷ��Ϣʧ�ܣ�");
			}
			
			

			int n2 = OrderDAO.updateLine(db, sessionData);

			if (n2 < 1) {
				throw new Exception("�޸Ķ���" + sessionData.getCart().getOrder().getOrderNumber()
						+ "���²�Ʒ��Ϣʧ�ܣ�");
			}
			//������ȯʹ�����
			TicketBO bo = new TicketBO(db.conn);
			bo.insertTicketUse(sessionData);
			
			//������������
			//OrderDAO.deleteUnionInfo(db.conn, sessionData);
			//OrderDAO.insertUnionInfo(db.conn, sessionData);
			
//			����Recruit_Members
			//OrderDAO.updateRecruitMember(db.conn, sessionData, 1);
			
			// ���¶���״̬
			// 0:��������
			
			OrderDAO.updateOrderStatus(db, sessionData.getOrderId(), 0);
			
			db.commit();
			Message.setMessage(request,"����" + sessionData.getCart().getOrder().getOrderNumber() + "�޸ĳɹ���");
			// ɾ��session
			request.getSession(true).removeAttribute(
					Constants.TEMPORARY_ORDER);
			
			
			/////////////////////////////���ж���
			OrderDAO.runOrder(db.conn,sessionData);
			/////////////////////////////
			
			// ���֧����ʽ��������,ֱ����ʾ�ɹ�����,���������,�ṩ�ṩת������������
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
		// �õ�����ͷ��Ϣ
		OrderDAO.getOrderHeadersInfo(db, pageData);

		// ������Դ
		//pageData.setPrTypeName(sessionData.getPrTypeName());

		// �õ��ͻ���Ϣ�����ʽ
		OrderDAO.getMemberInfo(db, sessionData);
		
		// �ж��Ƿ��ⷢ�ͷ�
		double ret = OrderDAO.getDeliveryFee(db,sessionData);
		if (ret < 0) {
			Message.setMessage(request,"ȡ���ͷѳ���!����ţ�"+ret);
			return "modify2";
		}

		// �õ���װ���б�
		pageData.setPackages(PackageTypeDAO.listPackages(db.conn));
		
		// ���ݸ���
		OrderForm.copyData(sessionData, pageData);

		//�µ�ʱ���ͻ���ַ�ĳ�Ĭ�ϵ�ַ
		MemberAddressDAO.updateAddress(db.conn,sessionData.getMbId(), sessionData.getReceiptorAddressId());

		return "modify2";
	}

}
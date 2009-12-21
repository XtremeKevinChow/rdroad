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
		
		// ��session��ȡ������
		OrderForm sessionData = (OrderForm) request.getSession(true)
				.getAttribute(Constants.TEMPORARY_ORDER);
		ShoppingCart2 cart = sessionData.getCart();

		sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
		
		if(!pageData.getManualFreeFreight())
		{
			sessionData.setFreeFreightReason("");
		}
		
		//�жϹ��������Ƿ���ȱ����Ʒ
		if (cart.isCartOOS()) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request,"��������ȱ����Ʒ,�����µ�!");
			return "add2";	
		}
		
		//Ԥ�۶���ֻ�ܵ����µ������ܺ�������Ʒͬʱ�µ�
		// delete by zhux 20080915 customer request
		/*if (cart.isPreSellOrder()) {
			if( cart.getItems().size()>1 || cart.getGifts().size()>0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request,"OVS��Ʒֻ�ܵ����µ�!");
				return "add2";
			}
		}*/
		
		//�����Ԥ�۶���������ʹ��������Ʒ��ȯ
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
		
		//������ѡ��Ʒ
		List lstGifts = cart.getGifts();
		for (int i = 0; i < lstGifts.size(); i++) {
			ItemInfo ii = (ItemInfo) lstGifts.get(i);
			
			// �����Ʒ(modified by user)
			if (ii.getSellTypeId() == 17 && ii.getIs_transfer() == 0) {
				if (ii.getFloorMoney() > cart.getNotGiftMoney()) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "���������Ʒ"+ii.getItemName()+"�����������������" + ii.getFloorMoney() + "Ԫ��");
					return "add2";
				}
			}
			
			// û�й���������Ʒ�����ܻ���������Ʒ
			if (ii.getSellTypeId() == 4 && ii.getFlag() == 3
					&& ii.getIs_transfer() == 0) {
				if (OrderDAO.checkAddThirdPromotion(db, sessionData, ii.getGift_group_id()) != 1) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "��ѡ�����Ʒ��Ʒ" + ii.getItemCode()
							+ "������������ԭ��û��ѡ���Ӧ���Ʒ��");
					return "add2";
				}
			}
		}
		
		//���xxԪѡ������Ʒ�Ƿ������ȷ
		lstGifts = cart.getGifts2();
		for(int i=0;i<lstGifts.size();i++) {
			ItemInfo ii = (ItemInfo)lstGifts.get(i);
			if (ii.getItemPrice() == 0) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "xxԪѡ��������Ʒ��������ȷ��");
				return "add2";
			}
		}
		
		// ���������Ʒ��Ʒ�Ľ���ܺ��Ƿ����㶩������������
		if (OrderDAO.checkAddThird(db, sessionData) == 1) {
			OrderForm.copyData(sessionData, pageData);
			Message.setErrorMsg(request, "��ѡ�����Ʒ��Ʒ������������ԭ�򣺹��������");
			return "add2";
		}

		// session�����������Ƿ�Ϊ��
		if (sessionData == null || sessionData.getPrTypeId() == 0) {
			OrderForm.copyData(sessionData, pageData);
			// û��session
			Message.setErrorMsg(request, "���Ĳ���ʱ�䳬ʱ��δ����ȷ˳�����ҳ�棡");
			return "add2";
		}

		// �����ȯ
		if (!sessionData.getCart().getTickets().isEmpty()) {
			if (!checkTicket(sessionData)) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "���������������ȯʹ������");
				return "add2";
			}
		}

		// ����ťAction
		String strAction = pageData.getActionType();
		
		if ("insertOrder".equalsIgnoreCase(strAction))
		{
			
			//�����Ԥ�۶�������֧�ֻ�������
			/*if (cart.isPreSellOrder()) {
				if (sessionData.getCart().getDeliveryInfo().getPaymentTypeId() == 1) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "Ԥ�۶�����֧�ֻ������лл!");
					return "input";
				}
			}*/
			
			//�õ�ȱ������
			sessionData.getCart().getOtherInfo().setOOSPlan(pageData.getOOSPlan());

			// �õ��Ƿ���Ҫ��Ʊ
			sessionData.getCart().getOtherInfo().setNeedInvoice(pageData.getNeedInvoice());
			String invoice_title = pageData.getInvoice_title();
			sessionData.getCart().getOtherInfo().setInvoice_title(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getInvoice_title())));
			
			// ���ÿ������Ϣ
			sessionData.getCart().getOtherInfo().setCredit_card(request.getParameter("credit_card"));
			sessionData.getCart().getOtherInfo().setId_card(request.getParameter("id_card"));
			sessionData.getCart().getOtherInfo().setEf_year(Integer.parseInt(request.getParameter("ef_year")==null?"0":request.getParameter("ef_year")));
			sessionData.getCart().getOtherInfo().setEf_month(Integer.parseInt(request.getParameter("ef_month")==null?"0":request.getParameter("ef_month")));
			sessionData.getCart().getOtherInfo().setVer_code(request.getParameter("ver_code"));
			
			// �õ���װ��ʽ
			sessionData.setPackage_type(pageData.getPackage_type());
			
			//��ע
			sessionData.getCart().getOtherInfo().setRemark(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getRemark())));
			
			//�õ�Ŀ¼
			//sessionData.getCart().getOtherInfo().setCatalog(ChangeCoding.unescape(ChangeCoding.toUtf8String(pageData.getCatalog())));
			sessionData.setMsc(pageData.getMsc());			
			
			//�ֹ����˷�
			sessionData.setManualFreeFreight(pageData.getManualFreeFreight());
			sessionData.setFreeFreightReason(pageData.getFreeFreightReason());
			//����ⷢ�ͷѣ����˷�=0
			if(sessionData.getManualFreeFreight())
			{
				sessionData.getCart().getDeliveryInfo().setDeliveryFee(0.00);
			}
			
			// ��session��ȡ������Ա��Ϣ
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				Message.setErrorMsg(request, "���Ĳ���ʱ�䳬ʱ����ǰ������Ա��Ϣ��ʧ��");
				return "error";
			} else {
				sessionData.setCreatorId(LogicUtility.parseInt(user.getId(), 0)); //������
			}
			
			// ����ͻ���ʽ
			if (sessionData.getCart().getDeliveryInfo().getDeliveryTypeId() == -1) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "��ѡ���ͻ���ʽ");
				return "input";
			}
			
			//��鸶�ʽ
			if (sessionData.getCart().getDeliveryInfo().getPaymentTypeId() == -1) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "��ѡ�񸶿ʽ");
				return "input";
			}
			// �����ֱ��,��Ҫ�鿴�ʱ��Ƿ����
			/*if (sessionData.getCart().getDeliveryInfo().getDeliveryTypeId() == 3) {
				if (!OrderDAO.isPostCodeDelivery(db.conn, sessionData
						.getCart().getDeliveryInfo().getPostCode())) {
					OrderForm.copyData(sessionData, pageData);
					Message.setErrorMsg(request, "���ʱ಻��ֱ��");
					return "input";
				}
			}*/
			
			//û�����������Ʒ
			//delete by zhux 20080915û���������۲�ƷҲ�����¶���
			/*if (sessionData.getCart().getItems().size() == 0 && !sessionData.getCart().existRecruitGift()) {
				OrderForm.copyData(sessionData, pageData);
				Message.setErrorMsg(request, "���ﳵ��û����Ʒ");
				return "input";
			}*/

			db.setAutoCommit(false);

			TicketBO bo = new TicketBO();
			bo.setConnection(db.conn);
			
			// ���¼����ȯ
			OrderForm checkForm = new OrderForm();
			OrderForm.copyData(sessionData, checkForm);
			bo.reCheckTicket(checkForm);
			
			//���ݰ�װ��ʽȡ�ð�װ��
			OrderDAO.getPackageFee(db.conn, sessionData);
			
			sessionData.setUse_deposit( pageData.getUse_deposit() );
			
			// ���붩��ͷ��Ϣ
			OrderDAO.insertMaster(db, sessionData);

			// ���붩����Ʒ��Ϣ
			OrderDAO.insertLine(db, sessionData);

			//������ȯ
			bo.insertTicketUse(sessionData);
			
			db.commit();
			// ��ʾ��Ϣ
			Message.setMessage(request,
					"���������ɹ���������Ϊ��"+sessionData.getOrderNumber());

			// ɾ��session
			request.getSession(true).removeAttribute(Constants.TEMPORARY_ORDER);

			// ���ж���
			OrderDAO.runOrder(db.conn, sessionData);

			// ���֧����ʽ��������,ֱ����ʾ�ɹ�����,���������,�ṩ�ṩת������������
			if(sessionData.getCart().getDeliveryInfo().getPaymentTypeId()!=94) {
				return "success";
			} else {
				
				String ordId = sessionData.getOrderNumber();
				ordId = ordId.substring(1,5) + "01003" + "0" + ordId.substring(7);
				int pay = (int) (sessionData.getCart().getOrderOwe()*100);
				DecimalFormat df = new DecimalFormat("000000000000");
				String payable = df.format(pay);
				
				System.out.println("����֧�����" + payable);

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
			
		//����Ǹ��ĵ�ַ	
		} else if ("changeAddress".equalsIgnoreCase(strAction)) {
			MemberAddressDAO.updateAddress(db.conn, pageData.getMbId(),
					pageData.getReceiptorAddressId());
			db.commit();
			
		//����Ǹ��ķ��ͷ�ʽ
		} else if ("changeDelivery".equalsIgnoreCase(strAction)) {
			OrderDAO.changeDelivery(db, pageData);
			db.commit();
			
		//����Ǹ��ĸ��ʽ
		} else if ("changePayment".equalsIgnoreCase(strAction)) {
			OrderDAO.changePayment(db, pageData);
			db.commit();
			
		} else {
			// modify bymagic at 2005-10-25
			/*
			 * ������������Ʒ�Ѿ���OrderAddSecondActionȡ��
			 */
			// OrderDAO.addLargess2(db.conn,sessionData);
		}
		// ������Դ
		//pageData.setPrTypeName(sessionData.getPrTypeName());
		
		
		
		// �õ��ͻ���Ϣ�����ʽ
		OrderDAO.getMemberInfo(db, sessionData);
		
		// �õ���װ���б�
		pageData.setPackages(PackageTypeDAO.listPackages(db.conn));

		// �ж��Ƿ��ⷢ�ͷ�
		double ret = OrderDAO.getDeliveryFee(db, sessionData);
		
		if (ret < 0) {
			Message.setMessage(request,"ȡ���ͷѳ���!����ţ�"+ret);
			return "input";
		}
		
		
		
		// ���ݸ���
		OrderForm.copyData(sessionData, pageData);

		return "input";
	}

	

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
	
}
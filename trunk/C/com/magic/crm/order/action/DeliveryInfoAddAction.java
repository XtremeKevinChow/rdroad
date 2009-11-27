/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebAction;
import com.magic.crm.common.WebForm;
import com.magic.crm.order.dao.DeliveryInfoDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberAddressDAO;
import com.magic.crm.order.form.DeliveryInfoForm;
import com.magic.crm.user.dao.S_AREADao;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberAddresses;

/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeliveryInfoAddAction extends WebAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.magic.crm.common.WebAction#execute(com.magic.crm.common.DBOperation,
	 *      com.magic.crm.common.WebForm)
	 */
	protected String execute(HttpServletRequest request,HttpServletResponse response,DBOperation db, WebForm form) 
		throws Exception {
		DeliveryInfoForm dif = (DeliveryInfoForm) form;
		if (request.getMethod().equals("GET")) {
			// 取得会员卡号和姓名
			DeliveryInfoDAO.getMemberInfo(db, dif);
			dif.setProvs(S_AREADao.listProvince(db.conn));
			return "input";
		} else {			
			// 操作指令
			String strAction = dif.getActionType();
			if ("changeDelivery".equalsIgnoreCase(strAction)
					|| "changePayment".equalsIgnoreCase(strAction)) {
				// 取得会员卡号和姓名
				DeliveryInfoDAO.getMemberInfo(db, (DeliveryInfoForm) form);
				
				//得到区域列表
				dif.setProvs(S_AREADao.listProvince(db.conn));
				dif.setCitys(S_AREADao.listCity(db.conn, dif.getProvince()));
				dif.setSects(S_AREADao.listSection(db.conn, dif.getCity()));
				
				if(dif.getSection()==null||dif.getSection().equals("")) {
					dif.setSection((String)dif.getSects().get(0));
				}
				
				// 得到送货方式列表
				DeliveryInfoDAO.listDeliveryTypes(db, dif);
				// 得到付款方式列表
				DeliveryInfoDAO.listPaymentTypes(db, dif);
				return "input";
			} else {
				// 提交新增
			    
				int id = DeliveryInfoDAO.insert(db, (DeliveryInfoForm) form);
				
				if (dif.getIsUpdate2MainAddress() == 1) {
				    MemberDAO memberDAO = new MemberDAO();
				    Member member = new Member();
				    member = memberDAO.DetailMembers(db.conn, dif.getMbId()+"");
				    String theday = member.getBIRTHDAY();
		            String birthday = theday.substring(0, 4) + "-"
		                    + theday.substring(4, 6) + "-" + theday.substring(6, 8);
		            member.setBIRTHDAY(birthday);
				    member.setAddressDetail(dif.getAddress());
				    memberDAO.updateDetail(db.conn, member);
				}
				if (dif.getIsUpdate2DefaultAddress() == 1) {
				    MemberAddresses addresses = new MemberAddresses();
				    MemberAddressDAO addressDAO = new MemberAddressDAO();
				    
				    MemberAddressDAO.updateAddress(db.conn, dif.getMbId(), id);
				}
				return "success";
			}
		}
	}

}
/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.action;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebAction;
import com.magic.crm.common.WebForm;
import com.magic.crm.order.dao.DeliveryInfoDAO;
import com.magic.crm.member.dao.MemberBlackListDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberAddressDAO;
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.order.form.DeliveryInfoForm;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.DBManager;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberAddresses;
import com.magic.crm.member.form.MemberForm;

/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeliveryInfoCopyAction extends DispatchAction {

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DeliveryInfoForm info = (DeliveryInfoForm) form;
		//if(info.getMb2Id()==0 ) {
		//	String mb = request.getParameter("");
			
		//}
		
		StringBuffer condition = new StringBuffer();
		condition.append("select * from mbr_members ");
		condition.append(" where is_organization = 0 ");
		
		if (info.getMbName() != null && info.getMbName().length() > 0) {
			
			condition.append(" and name like '" + info.getMbName() + "%'");
		}
		
		if (info.getCardId() != null && info.getCardId().trim().length() > 0) {
			
			condition.append(" and CARD_ID = '" + info.getCardId().trim() + "' ");
			
		}
		if (info.getPhone() != null && info.getPhone().trim().length() > 0) {
			
			condition.append(" and (telephone = '" + info.getPhone()+ "' or family_phone = '" + info.getPhone());
			condition.append("' or company_phone = '" + info.getPhone() + "') and rownum<50 ");
			
		}
		
		if((info.getMbName() == null || info.getMbName().trim().equals(""))
				&&(info.getCardId() == null || info.getCardId().trim().equals(""))
				&&(info.getPhone() == null || info.getPhone().trim().equals(""))) {
			
			request.setAttribute("list", new ArrayList());
			return mapping.findForward("query");
		}
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ArrayList mbs = MemberDAO.ListMembers(conn, condition.toString());
			request.setAttribute("list", mbs);
			
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		
		return mapping.findForward("query");
		
	}
	
	public ActionForward copy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DeliveryInfoForm info = (DeliveryInfoForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			DeliveryInfoDAO.copyDeliveryInfo(conn, info.getMbId(), info.getMb2Id());
			
			// 返回列表选择页
			info.setMbId(info.getMb2Id());
			DBOperation db = new DBOperation(conn);
			DeliveryInfoDAO.getMemberInfo(db, info);
			DeliveryInfoDAO.list(db, info);
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return mapping.findForward("success");
	}
	
	

}
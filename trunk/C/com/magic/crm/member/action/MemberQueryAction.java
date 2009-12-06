/*
 * Created on 2005-1-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.magic.crm.util.CallCenterHander;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.*;
import com.magic.crm.member.dao.*;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebAction;
import com.magic.crm.common.WebForm;
import javax.servlet.http.HttpSession;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * ��Ա��ѯ����Ա�߼���ѯ�������
 */
public final class MemberQueryAction extends WebAction {


	protected String execute(HttpServletRequest request,
			HttpServletResponse response, DBOperation db, WebForm form)
			throws Exception {

		MemberForm memberForm = (MemberForm) form;
		StringBuffer condition = new StringBuffer();
		String isquery = request.getParameter("isquery");
		String isorg = request.getParameter("isorg");
		String service = request.getParameter("service");// �Ƿ�"�������"(0-����;1-��)
		if (service == null || service.equals("")) {
			service = "0";
		}
		
		if (isorg == null) {
            isorg = "0";
        }
		
		/*
		 * ��ѯ����
		 */
		condition.append("select * from mbr_members ");
		condition.append(" where id>0 ");
		if (memberForm.getAddressDetail() != null && memberForm.getAddressDetail().length() > 0) {
			condition.append("and address like '%" + memberForm.getAddressDetail() + "%'");
		}
		if (isorg.equals("1")) {
            condition.append(" and IS_ORGANIZATION = '1'");
        } else {
            condition.append(" and IS_ORGANIZATION = '0'");
        }
		if (memberForm.getNAME() != null && memberForm.getNAME().length() > 0) {
			
			condition.append(" and name like '" + memberForm.getNAME() + "%'");
		}
		if (memberForm.getTaobaoWangId() != null && memberForm.getTaobaoWangId().length()>0)
		{
			condition.append(" and taobaowang_id like '" + memberForm.getTaobaoWangId().trim() +"%'");
		}
		/**
		 * modified by user 2008-02-28 
		 * ���ϻ�Ա�Ź���һ�������
		 */
		if (memberForm.getCARD_ID() != null && memberForm.getCARD_ID().length() > 0) {
			
			condition.append(" and (CARD_ID = '" + memberForm.getCARD_ID().trim() + "' OR OLD_CARD_CODE = '" + memberForm.getCARD_ID().trim() + "') ");
			
		}
		
		if (memberForm.getMSC_CODE() != null && memberForm.getMSC_CODE().length() > 0) {
			condition.append(" and MSC_CODE = '" + memberForm.getMSC_CODE() + "'");
		}
		if (memberForm.getPostcode() != null && memberForm.getPostcode().length() > 0) {
			condition.append(" and Postcode = '" + memberForm.getPostcode() + "'");
		}

		if (memberForm.getCREATE_DATE() != null && memberForm.getCREATE_DATE().length() > 0) {
			condition.append(" and CREATE_DATE >= to_date('" + memberForm.getCREATE_DATE() + "','yyyy-mm-dd')");
		}
		if (memberForm.getCOMPANY_PHONE() != null && memberForm.getCOMPANY_PHONE().length() > 0) {
			condition.append(" and CREATE_DATE < (to_date('"+ memberForm.getCOMPANY_PHONE() + "','yyyy-mm-dd')+1)");
		}
		
		if (memberForm.getEMAIL() != null && memberForm.getEMAIL().length() > 0) {
			condition.append(" and email like '" + memberForm.getEMAIL() + "%'");
		}
		
		if (memberForm.getLEVEL_ID() != -1) {

			condition.append(" and level_id = " + memberForm.getLEVEL_ID());
		}
		StringBuffer condition4 = new StringBuffer(condition);;
		if (memberForm.getTELEPHONE()!=null && !memberForm.getTELEPHONE().trim().equals("")) {
			String queryPhone = memberForm.getTELEPHONE().trim();
			condition4.append("and ( telephone like '%").append(queryPhone).append("%' or family_phone like '%")
			.append(queryPhone).append("%' or company_phone like '%").append(queryPhone).append("%') ");
		} 
		
		if (isquery != null && isquery.equals("1")) { //���ݲ�ѯ

			
				 HttpSession session = request.getSession();
				 if (request.getParameter("TELEPHONE")!=null && request.getParameter("isPageRequest") == null) {
				 
					 session.setAttribute("currentPhone",request.getParameter("TELEPHONE"));
				 }
				 /**
				  * ���û�������ѯ�����Ͳ���ѯ���ݿ�
				  */
				 if (service.equals("1")) { //��ͨ��ѯ
					 if ( (memberForm.getCARD_ID() == null || memberForm.getCARD_ID().trim().length() == 0)
						&& (memberForm.getNAME() == null || memberForm.getNAME().trim().length() == 0)	 
						&& (memberForm.getEMAIL() == null || memberForm.getEMAIL().trim().length() == 0)
						&& (memberForm.getTELEPHONE()==null || memberForm.getTELEPHONE().trim().length() == 0)
						&& (memberForm.getTaobaoWangId()==null || memberForm.getTaobaoWangId().trim().length() == 0)
					 ) {
						 return "success";
						 
					 }
				 }
				System.out.println("��ѯ��Ա��" + condition4.toString());
				
				 MemberDAO.ListMembers(db, memberForm, condition4.toString());
				

				/**
				 * added by user 2006-05-18 18:02 �����ѯ�õ�ֻ��1����¼��ֱ����ת������ҳ��
				 */

				MemberForm myForm = (MemberForm) form;

				if (myForm.getItems().size() == 1 && service.equals("1")) { // ֻ��1����¼

					MemberDAO memberDAO = new MemberDAO();

					Member mb = new Member();
					int memberID = ((MemberForm) myForm.getItems().get(0))
							.getID();
					mb = memberDAO.DetailMembers(db.conn, memberID + "");
					
					// ������
					MemberBlackListDAO memberBlackListDAO = new MemberBlackListDAO();
					boolean isBlackList = memberBlackListDAO.isExistBlacklist(
							db.conn, mb.getID());
					mb.setBlacklistMember(isBlackList);
					if (isBlackList) {
						mb.setBlackRemark(memberBlackListDAO.getBlackRemark(db.conn, memberID));
		            }
					
					// ��ѯ��Աδʹ�õ���ȯ 
		            mb.setGift_num(MemberGetAwardDAO.getAvailableGiftNumber(db.conn, memberID));
					request.setAttribute("member", mb);

					/*
					 * add by user 2006-05-31 14:02
					 * ����ǰ����Ļ�Ա��Ϣ����session�У�ֱ���˳����񣬽����Ự
					 */

					CallCenterHander hander = new CallCenterHander(request
							.getSession());
					Member mb2 = hander.getServicedMember();
					if(mb2!=null&& !mb2.getCARD_ID().equals(mb.getCARD_ID())) {
						hander.setPreServiceMember(mb2);
					}
					hander.setServiceMember(mb);
					
					return "detail_page";

				} 
				
		}

		return "success";
	}

}

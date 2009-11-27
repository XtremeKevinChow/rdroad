/**
 * MemberaddMoneyInputAction.java
 * �������������ֽ�ص�¼�루���񡢿ͷ����ã�
 * 2008-3-26
 * ����10:54:04
 * user
 * MemberaddMoneyInputAction
 */
package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.MemberaddMoneyForm;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

import java.util.Collection;

/**
 * @author user
 *
 */
public class MemberaddMoneyManageAction extends DispatchAction {
	
	/**
	 * ��ʾ����ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("add");
	}
	
	/**
	 * ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		
		
		//��½��
		User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			// ����Ա���Ƿ���ȷ
			/*MemberDAO memberDao = new MemberDAO();
			Member member = memberDao.getMemberInfo(conn, myForm.getMB_CODE());
			if (member == null || member.getID() <= 0) {
				//Message.setErrorMsg(request,"��Ա�Ų�����!");
				//return mapping.findForward("success");
			} else {
				if (!member.getNAME().trim().equals(myForm.getMB_NAME())) {
					Message.setErrorMsg(request,"��Ա�����ͻ�Ա�Ų�ƥ�䣬�п����Ѿ������˻�Ա����!");
					return mapping.findForward("success");
				}
			}
			//ƴ�ӻ�Ա+����
			if (myForm.getMB_CODE() != null 
					&& myForm.getMB_CODE().trim().length() != 0) {
					myForm.setMB_CODE(myForm.getMB_CODE()+"+"+myForm.getMB_NAME());
			}*/
			MemberaddMoney money = new MemberaddMoney();
			money.setTYPE("3");//��������
			money.setOPERATOR_ID(Integer.parseInt(user.getId()));//��½��
			copyForm2Entity(myForm, money);
			moneyDao.insert(conn, money);
			Message.setErrorMsg(request,"�����ɹ�!");
		}catch(Exception e) {
			Message.setErrorMsg(request,"����ʧ�ܣ�������������ϵͳ�Ѿ�������!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * ��ʾ�޸���Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			MemberaddMoney data = moneyDao.findRecordByPK(conn, myForm.getID());
			copyEntity2Form(data, myForm);
		}catch(Exception e) {
			throw new ServletException();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward("modify");
	}
	
	/**
	 * �޸ĵ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		
		//��½��
		User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			//����Ա���Ƿ���ȷ
			/*MemberDAO memberDao = new MemberDAO();
			Member member = memberDao.getMemberInfo(conn, myForm.getMB_CODE());
			if (member == null || member.getID() <= 0) {
				//Message.setErrorMsg(request,"��Ա�Ų�����!");
				//return mapping.findForward("success");
			} else {
				if (!member.getNAME().trim().equals(myForm.getMB_NAME())) {
					Message.setErrorMsg(request,"��Ա�����ͻ�Ա�Ų�ƥ�䣬�п����Ѿ������˻�Ա����!");
					return mapping.findForward("success");
				}
			}
			//ƴ�ӻ�Ա+����
			if (myForm.getMB_CODE() != null 
					&& myForm.getMB_CODE().trim().length() != 0) {
				myForm.setMB_CODE(myForm.getMB_CODE() + "+" + myForm.getMB_NAME()); //20008489+˧ʫ�� ������ʽ
			}*/
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			MemberaddMoney money = new MemberaddMoney();
			
			copyForm2Entity(myForm, money);
			money.setOPERATOR_ID(Integer.parseInt(user.getId()));//��½��
			moneyDao.update2(conn, money);
			Message.setMessage(request,"�����ɹ�!");
		}catch(Exception e) {
			Message.setErrorMsg(request,"����ʧ�ܣ�������������ϵͳ�Ѿ�������!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return mapping.findForward("query2");
	}
	
	/**
	 * �޸ĵ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			
			moneyDao.delete(conn, myForm);
			Message.setErrorMsg(request,"�����ɹ�!");
		}catch(Exception e) {
			
			Message.setErrorMsg(request,"����ʧ��!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return mapping.findForward("query2");
	}
	/**
	 * ��ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MemberaddMoneyForm myForm = (MemberaddMoneyForm)form;
		myForm.setStatus(0);//�´����ĵ���
		myForm.setTYPE("3");//�ֽ𣨻ص���
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberaddMoneyDAO moneyDao = new MemberaddMoneyDAO();
			Collection list = moneyDao.findRecordsByCondition(conn, myForm);
			request.setAttribute("list", list);
		}catch(Exception e) {
			throw new ServletException();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward("query");
	}
	
	
	/**
	 * ��������
	 * @param source
	 * @param dest
	 */
	private void copyForm2Entity (MemberaddMoneyForm source, MemberaddMoney dest) {
		dest.setID(source.getID());
		dest.setREF_ID(source.getREF_ID());
		dest.setMB_CODE(source.getMB_CODE());
		dest.setMB_NAME(source.getMB_NAME());
		dest.setORDER_CODE(source.getORDER_CODE());
		dest.setMONEY(source.getMONEY());
		dest.setBill_date(source.getBILL_DATE());
		dest.setREMARK(source.getREMARK());
		dest.setUSE_TYPE(source.getUSE_TYPE());
		dest.setPayMethod(source.getPayMethod());
	}
	private void copyEntity2Form(MemberaddMoney source, MemberaddMoneyForm dest) {
		dest.setREF_ID(source.getREF_ID());
		dest.setMB_CODE(source.getMB_CODE());
		dest.setORDER_CODE(source.getORDER_CODE());
		dest.setMONEY(source.getMONEY());
		dest.setBILL_DATE(source.getBill_date());
		dest.setREMARK(source.getREMARK());
		dest.setUSE_TYPE(source.getUSE_TYPE());
		dest.setMB_NAME(source.getMB_NAME());
		dest.setPayMethod(source.getPayMethod());
	}
}

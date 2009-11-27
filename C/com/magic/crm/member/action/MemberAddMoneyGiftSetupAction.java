/*
 * Created on 2006-1-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.member.dao.MemberAddMoneyGiftSetupDAO;
import com.magic.crm.member.entity.MemberAddMoneyGiftSetup;
import com.magic.crm.member.form.MemberAddMoneyGiftSetupForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;
import com.magic.crm.product.dao.ProductDAO;
/**
 * @author ��
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAddMoneyGiftSetupAction extends DispatchAction {
	
	private static Logger log = Logger.getLogger("MemberAddMoneyGiftSetupAction.class");
	
	/**
	 * ��ʾ����ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberAddMoneyGiftSetupForm myForm = (MemberAddMoneyGiftSetupForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			int days = MemberAddMoneyGiftSetupDAO.getKeepDays(conn);
			ArrayList gifts = MemberAddMoneyGiftSetupDAO.listAvailabeGiftNumber(conn);
			myForm.setKeepDays(days);
			myForm.setGifts(gifts);
			
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} 

		return mapping.findForward("add");
	}

	/**
	 * ��Ӽ�¼
	 * 
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
		User user = (User)request.getSession().getAttribute("user");
		MemberAddMoneyGiftSetupForm src = (MemberAddMoneyGiftSetupForm) form;
		src.setOperatorID(Integer.parseInt(user.getId()));
		MemberAddMoneyGiftSetupDAO ptDao = new MemberAddMoneyGiftSetupDAO();
		Connection conn = null;
		MemberAddMoneyGiftSetup data = new MemberAddMoneyGiftSetup();
		src.copy(data);//��������
		try {
			conn = DBManager.getConnection();
			/*int itemID = ProductDAO.getItemID(conn, data.getItemCode());
			if ( itemID == 0 ) {
				Message.setMessage(request, "�ò�Ʒ������", "�� ��","member/memberAddMoneyGiftSetup.do?type=addInit");
				return mapping.findForward("message");
			}
			data.setItemID(itemID);
			
			//��������������Ʒ������������
			if (ptDao.hasItemID(conn, data) || ptDao.hasMoney(conn, data)) {
				Message.setMessage(request, "���Ż����Ѿ�����","�� ��","member/memberAddMoneyGiftSetup.do?type=query");
				return mapping.findForward("message");
			}*/
			ptDao.insert(conn, data);

		} catch (Exception e) {
			Message.setMessage(request, "��������");
			log.error("exception:", e);
			return mapping.findForward("message");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query1");
	}

	/**
	 * ��ʾ�޸�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberAddMoneyGiftSetupForm pageData = (MemberAddMoneyGiftSetupForm) form;
		MemberAddMoneyGiftSetupDAO ptDao = new MemberAddMoneyGiftSetupDAO();
		Connection conn = null;
		
		try {
			conn = DBManager.getConnection();
			int days = MemberAddMoneyGiftSetupDAO.getKeepDays(conn);
			
			String id = request.getParameter("id");
			pageData.setId(Integer.parseInt(id));
			MemberAddMoneyGiftSetup data = ptDao.showDetail(conn, pageData);
			pageData.setId(data.getId());
			pageData.setItemID(data.getItemID());
			pageData.setItemCode(data.getItemCode());
			pageData.setMoney(data.getMoney());
			pageData.setPrice(data.getPrice());
			pageData.setKeepDays(data.getKeepDays());
			pageData.setCreateDate(data.getCreateDate());
			pageData.setOperatorID(data.getOperatorID());
			pageData.setStatus(data.getStatus());
			pageData.setKeepDays(days);
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("modify");
	}

	/**
	 * �޸ļ�¼
	 * 
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
		
		MemberAddMoneyGiftSetupForm pageData = (MemberAddMoneyGiftSetupForm) form;
		MemberAddMoneyGiftSetupDAO ptDao = new MemberAddMoneyGiftSetupDAO();
		MemberAddMoneyGiftSetup data = new MemberAddMoneyGiftSetup();
		pageData.copy(data);
		User user = (User)request.getSession().getAttribute("user");
		data.setOperatorID(Integer.parseInt(user.getId()));
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			int itemID = ProductDAO.getItemID(conn, data.getItemCode());
			if ( itemID == 0 ) {
				Message.setMessage(request, "�ò�Ʒ������","�� ��","member/memberAddMoneyGiftSetup.do?type=addInit");
				return mapping.findForward("message");
			}
			data.setItemID(itemID);
			//��������������Ʒ������������
			if (ptDao.hasItemID(conn, data) || ptDao.hasMoney(conn, data)) {
				Message.setMessage(request, "���Ż����Ѿ�����","�� ��","member/memberAddMoneyGiftSetup.do?type=query");
				return mapping.findForward("message");
			}
			ptDao.update(conn, data);

		} catch (Exception e) {
			Message.setMessage(request, "�޸Ĵ���");
			log.error("exception:", e);
			return mapping.findForward("message");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query1");
	}

	/**
	 * ɾ����¼
	 * 
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
		MemberAddMoneyGiftSetupForm pageData = (MemberAddMoneyGiftSetupForm) form;
		MemberAddMoneyGiftSetupDAO ptDao = new MemberAddMoneyGiftSetupDAO();
		String[] delID = request.getParameterValues("delID");
		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			if (delID == null || delID.length == 0) {
				Message.setMessage(request, "��ѡ���¼");
			} 
			for (int i = 0; i < delID.length; i ++) {
				pageData.setId(Integer.parseInt(delID[i]));
				ptDao.delete(conn, pageData);
			}
			
			Message.setMessage(request, "���óɹ�","�� ��","member/memberAddMoneyGiftSetup.do?type=query");
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("message");
	}

	/**
	 * ��ѯ���м�¼
	 * 
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
		//MemberAddMoneyGiftSetupForm data = (MemberAddMoneyGiftSetupForm) form;
		MemberAddMoneyGiftSetupDAO ptDao = new MemberAddMoneyGiftSetupDAO();
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			//1 ��ѯ��Ϣ
			Collection ret = ptDao.getList(conn);
			request.setAttribute("list", ret);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setMessage(request, "��ѯ����");
			throw e;
			
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query");
	}

	
}

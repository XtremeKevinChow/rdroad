/*
 * Created on 2006-2-13
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

import com.magic.crm.member.dao.MbrGetAwardDAO2;
import com.magic.crm.member.dao.MemberExpExchangeDAO;
import com.magic.crm.member.form.MemberExpExchangeForm;
import com.magic.crm.member.form.MemberExpExchangePopForm;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author ��
 * ������Ʒ����
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberExpExchangeSetupAction extends DispatchAction {
	
	private static Logger log = Logger.getLogger("MemberExpExchangeSetupAction.class");
	
	
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
	    MemberExpExchangeForm data = (MemberExpExchangeForm)form;
	    Connection conn = DBManager.getConnection();
		try {
		    int days = MbrGetAwardDAO2.getExchangeGiftKeepDay(conn);
		    data.setValidDay(days);//���ֶһ���Ʒ��������
			
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
		
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		Connection conn = DBManager.getConnection();
		MemberExpExchangeForm data = (MemberExpExchangeForm)form;
		
		try {
			int itemID = ProductDAO.getItemID(conn, data.getItemCode());
			if ( itemID == 0 ) {
				Message.setMessage(request, "�ò�Ʒ������", "�� ��",null);
				return mapping.findForward("message");
			}
			data.setItemID(itemID);
			
			//��������������Ʒ������������
			if (ptDao.hasItemID(conn, data)) {
				Message.setMessage(request, "�ò�Ʒ�Ѿ�����","�� ��",null);
				return mapping.findForward("message");
			}
			
			MemberExpExchangePopForm popForm = ptDao.showMainDetail(conn, data.getParentID());
			if (parseDateFloat(data.getStartDate()) <  parseDateFloat( popForm.getStartDate() )) {
				Message.setMessage(request, "��ʼ���ڲ���С��" + popForm.getStartDate(),"�� ��",null);
				return mapping.findForward("message");
			}
			if (parseDateFloat(data.getEndDate()) >  parseDateFloat( popForm.getEndDate())) {
				Message.setMessage(request, "�������ڲ��ܴ���" + popForm.getEndDate(),"�� ��",null);
				return mapping.findForward("message");
			}
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
	 * ����ת��
	 * @param date
	 * @return
	 */
	private float parseDateFloat (String date) {
		String[] arr = date.split("-");
		StringBuffer buf = new StringBuffer();
		buf.append(arr[0]);
		buf.append(arr[1]);
		buf.append(arr[2]);
		return Float.parseFloat( buf.toString());
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
		MemberExpExchangeForm pageData = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		Connection conn = DBManager.getConnection();
		try {
			String id = request.getParameter("id");
			pageData.setID(Integer.parseInt(id));
			
			MemberExpExchangeForm data = ptDao.showDetail(conn, pageData);
			
			String name = ptDao.getNameByID(conn, data.getParentID());
			data.setParentName(name);
			
			int days = MbrGetAwardDAO2.getExchangeGiftKeepDay(conn);
		    data.setValidDay(days);//���ֶһ���Ʒ��������
			request.setAttribute("memberExpExchangeForm", data);
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
		MemberExpExchangeForm data = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		
		Connection conn = DBManager.getConnection();
		try {
			int itemID = ProductDAO.getItemID(conn, data.getItemCode());
			if ( itemID == 0 ) {
				Message.setMessage(request, "�ò�Ʒ������","�� ��","member/memberExpExchangeSetup.do?type=modInit&id=" + data.getID());
				return mapping.findForward("message");
			}
			data.setItemID(itemID);
			//��������������Ʒ
			if (ptDao.hasItemID(conn, data)) {
				Message.setMessage(request, "�����Ѿ�����","�� ��","member/memberExpExchangeSetup.do?type=modInit&id=" + data.getID());
				return mapping.findForward("message");
			}
			MemberExpExchangePopForm popForm = ptDao.showMainDetail(conn, data.getParentID());
			if (parseDateFloat(data.getStartDate()) <  parseDateFloat( popForm.getStartDate() )) {
				Message.setMessage(request, "��ʼ���ڲ���С��" + popForm.getStartDate(),"�� ��",null);
				return mapping.findForward("message");
			}
			if (parseDateFloat(data.getEndDate()) >  parseDateFloat( popForm.getEndDate())) {
				Message.setMessage(request, "�������ڲ��ܴ���" + popForm.getEndDate(),"�� ��",null);
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
		MemberExpExchangeForm pageData = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		String[] delID = request.getParameterValues("delID");
		Connection conn = DBManager.getConnection();

		try {
			if (delID == null || delID.length == 0) {
				Message.setMessage(request, "��ѡ���¼");
			} 
			for (int i = 0; i < delID.length; i ++) {
				pageData.setID(Long.parseLong(delID[i]));
				ptDao.delete(conn, pageData);
			}
			
			Message.setMessage(request, "���óɹ�","�� ��",null);
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
		MemberExpExchangeForm data = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		Connection conn = DBManager.getConnection();

		try {
			
			//1 ��ѯ��Ϣ
			Collection ret = ptDao.getList(conn, data);
			
			if(request.getMethod().equals("GET")) {
				
				ret = new ArrayList();
			}
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
	
	/**
	 * ѡ������¼
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberExpExchangeForm data = (MemberExpExchangeForm) form;
		MemberExpExchangeDAO ptDao = new MemberExpExchangeDAO();
		Connection conn = DBManager.getConnection();
	
		try {

			//1 ��ѯ��Ϣ
			Collection ret = ptDao.getMstList(conn, data);
			
//			if(request.getMethod().equals("GET")) {
//				
//				ret = new ArrayList();
//			}
			request.setAttribute("expList", ret);
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

		return mapping.findForward("select");
	}
}

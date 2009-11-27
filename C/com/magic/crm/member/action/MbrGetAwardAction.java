/*
 * Created on 2006-2-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.util.Constants;
import com.magic.crm.common.pager.Pager;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberExpExchangeDAO;
import com.magic.crm.member.dao.MbrGetAwardDAO2;
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.form.MemberExpExchangeForm;
import com.magic.crm.member.form.MbrGetAwardForm;
import com.magic.crm.member.form.ExpExchangeHisForm;
import com.magic.crm.member.entity.Exp;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberAWARD;

import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.promotion.dao.ExpExchangeActivityDAO;
import com.magic.crm.promotion.entity.ExpExchangeActivity;
import com.magic.crm.promotion.entity.ExpExchangeStepMst;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.DateUtil;
import com.magic.crm.util.Message;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MbrGetAwardAction extends DispatchAction {

	private static Logger log = Logger.getLogger("ExpExchangeAction.class");

	/**
	 * ��ѯ��Ч��Ʒ��¼
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryValidGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MbrGetAwardForm data = (MbrGetAwardForm) form;
		MemberExpExchangeDAO meeDao = new MemberExpExchangeDAO();
		MbrGetAwardDAO2 eeDao = new MbrGetAwardDAO2();
		Connection conn = DBManager.getConnection();
		MemberExpExchangeForm param = new MemberExpExchangeForm();

		String flag = request.getParameter("flag");// �Ƿ�ӹ��ﳵ���ﴫ�����ı��
		String isAdd = request.getParameter("isAdd");// ���ӻ����޸�ҳ��
		request.setAttribute("isAdd", isAdd);
		request.setAttribute("flag", flag);
		try {
			// 1 ��ѯ��Ϣ
			Collection coll = new ArrayList();
			// if (request.getMethod().equals("GET")) {

			if (data.getCardID() == null || data.getCardID().equals("")) {
				request.setAttribute("list", coll);
				return mapping.findForward("exchange_query");
			}
			param.setValidFlag("Y"); // ��Ч�ļ�¼
			param.setItemCode(data.getItemCode());
			if (!data.getCardID().equals("")) {

				data = eeDao.getMemberInfoByCardID(conn, data);
				if (data == null) { // û�д˻�Ա
					Message.setMessage(request, "�û�Ա������", "����", null);
					return mapping.findForward("message");
				}
			}
			// �������ʱ��
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = format.format(date);
			param.setQueryDate(strDate);

			/*
			 * int size = meeDao.countRecordsByCondition(conn, param); Pager
			 * page = new Pager(param.getOffset(), size);//����page����
			 * page.setOffset(param.getOffset());//���õ�ǰλ�� page.setLength(2);
			 * param.setPager(page);
			 */
			coll = meeDao.getList(conn, param);
			request.setAttribute("flag", flag); // �Ƿ�ӹ��ﳵ�������ı��
			request.setAttribute("isAdd", isAdd); // ���ӻ����޸�ҳ��
			request.setAttribute("memberExpExchangeForm", param);
			request.setAttribute("expExchangeForm", data);
			request.setAttribute("list", coll);

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
		response.getWriter().println("");
		return mapping.findForward("exchange_query");
	}

	/**
	 * ��ѯ��Ч��Ʒ��¼
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryValidGift2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/** �ж�ʱ�� * */
		/*
		 * Date now = DateUtil.getSqlDate(); if
		 * (now.after(DateUtil.getSqlDate(DateUtil.getDate("2007-03-01",
		 * "yyyy-MM-dd"))) ||
		 * now.before(DateUtil.getSqlDate(DateUtil.getDate("2007-01-01",
		 * "yyyy-MM-dd"))) ) { Message.setMessage(request,
		 * "2006���һ���Ի��ֶһ����ڱ�����2007��1��1�ŵ�2007��2��28��֮��","����",null); return
		 * mapping.findForward("message"); }
		 */
		MbrGetAwardForm data = (MbrGetAwardForm) form;
		MemberExpExchangeDAO meeDao = new MemberExpExchangeDAO();
		MbrGetAwardDAO2 eeDao = new MbrGetAwardDAO2();
		Connection conn = DBManager.getConnection();
		MemberExpExchangeForm param = new MemberExpExchangeForm();

		String flag = request.getParameter("flag");// �Ƿ�ӹ��ﳵ���ﴫ�����ı��
		String isAdd = request.getParameter("isAdd");// ���ӻ����޸�ҳ��
		request.setAttribute("isAdd", isAdd);
		request.setAttribute("flag", flag);
		try {
			// 1 ��ѯ��Ϣ
			Collection coll = new ArrayList();
			if (data.getCardID() == null || data.getCardID().equals("")) {
				request.setAttribute("list", coll);
				return mapping.findForward("exchange_query2");
			}
			param.setValidFlag("Y"); // ��Ч�ļ�¼
			param.setParentID(5);
			param.setItemCode(data.getItemCode());
			if (!data.getCardID().equals("")) {
				data = eeDao.getMemberInfoByCardID2(conn, data);
				if (data == null) { // û�д˻�Ա
					Message.setMessage(request, "�û�Ա������", "����", null);
					return mapping.findForward("message");
				}
			}
			// �������ʱ��
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = format.format(date);
			param.setQueryDate(strDate);
			coll = meeDao.getList(conn, param);
			request.setAttribute("flag", flag); // �Ƿ�ӹ��ﳵ�������ı��
			request.setAttribute("isAdd", isAdd); // ���ӻ����޸�ҳ��
			request.setAttribute("memberExpExchangeForm", param);
			request.setAttribute("expExchangeForm", data);
			request.setAttribute("list", coll);

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
		return mapping.findForward("exchange_query2");
	}

	/**
	 * ��ѯ�Ѿ��һ�����Ʒ�ݴ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryGift(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MbrGetAwardForm data = (MbrGetAwardForm) form;
		MemberGetAwardDAO ptDao = new MemberGetAwardDAO();
		MbrGetAwardDAO2 eeDao = new MbrGetAwardDAO2();
		Connection conn = DBManager.getConnection();
		Collection coll = null;

		try {

			if (request.getMethod().equals("GET")) {
				//�����callcenterϵͳ����sessin��ȡ����Ա��Ϣ�����û����ʾ��¼������� 
				CallCenterHander hander = new CallCenterHander(request
						.getSession());
				if(hander!=null&& hander.isOnService()) {
					Member mb = hander.getServicedMember();
					data.setCardID(mb.getCARD_ID());
				}
				
				coll = new ArrayList();
				request.setAttribute("list", coll);
				return mapping.findForward("list_gift");

			}
			// �����post�ύ�������ж��Ƿ���ͨ��ToolBar�ύ��������������session��ȡ�û�Ա��
			String iscallcenter = request.getParameter("iscallcenter");
			String condition = "";
			if (iscallcenter != null && iscallcenter.equals("1")) {
				CallCenterHander hander = new CallCenterHander(request
						.getSession());
				if (hander.isOnService()) {
					Member mb = hander.getServicedMember();
					data.setMemberID(mb.getID());
					data.setCardID(mb.getCARD_ID());
					condition += " AND a.MEMBER_ID = " + data.getMemberID();
				} else {
					ControlledError ctlErr = new ControlledError();
					ctlErr.setErrorTitle("��������");
					ctlErr
							.setErrorBody("û�з��������<a href='/member/query.do?service=1'>�������</a>");
					request.setAttribute(Constants.ERROR_KEY, ctlErr);
					return mapping.findForward("controlledError");

				}
			} else {

				if (data != null && data.getCardID() != null
						&& !data.getCardID().equals("")) {
					MbrGetAwardForm data1 = eeDao.getMemberInfoByCardID(conn,
							data);
					if (data1 == null) { // û�д˻�Ա
						Message.setMessage(request, "�û�Ա������", "����", null);
						return mapping.findForward("message");
					} else {
						data.setMemberID(data1.getMemberID());
					}
				}

				// data.setItemStatus(Integer.parseInt(request.getParameter("itemStatus")));
				if (request.getParameter("sellType") != null) {
					data.setSellType(Integer.parseInt(request
							.getParameter("sellType")));
				}
				
				data.setItemCode(request.getParameter("itemCode"));

				if (data.getMemberID() != 0) {
					condition += " AND a.MEMBER_ID = " + data.getMemberID();
				}
				
				if (data.getMemberName()!=null&&!"".equals(data.getMemberName())) {
					condition += " AND e.name ='" + data.getMemberName() + "' ";
				}
				
				if (data.getTelephone()!=null&&!"".equals(data.getTelephone())) {
					condition = condition + " AND ( e.telephone ='" + data.getTelephone() 
					+ "' or e.family_phone ='" + data.getTelephone() + "' or e.company_phone ='"
					+ data.getTelephone() + "') ";
				}
				
				if (data.getItemCode() != null
						&& !data.getItemCode().equals("")) {
					condition += " AND a.ITEM_CODE = '" + data.getItemCode() + "' ";
				}
				if (data.getSellType() != -1) {
					condition += " AND a.TYPE = " + data.getSellType();
				}
				
				
				if (condition.trim().equals("")) {
					ControlledError ctlErr = new ControlledError();
					ctlErr.setErrorTitle("������ʾ");
					ctlErr.setErrorBody("��û�������ѯ�������������ѯ������ѯ��");
					request.setAttribute(Constants.ERROR_KEY, ctlErr);
					return mapping.findForward("controlledError");
				}
			}
			int size = ptDao.countMemberAward2(conn, condition);
			Pager page = new Pager(data.getOffset(), size);// ����page����

			page.setOffset(data.getOffset());// ���õ�ǰλ��
			data.setPager(page);
			coll = ptDao.queryMemberAWARD(conn, condition, data);

			request.setAttribute("list", coll);
			
			
			// request.setAttribute("expExchangeForm", data);
			return mapping.findForward("list_gift");
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

	}

	
	public ActionForward queryGiftNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MbrGetAwardForm data = (MbrGetAwardForm) form;
		MemberGetAwardDAO ptDao = new MemberGetAwardDAO();
		MbrGetAwardDAO2 eeDao = new MbrGetAwardDAO2();
		Connection conn = DBManager.getConnection();
		Collection coll = null;

		try {

			if (request.getMethod().equals("GET")) {
				CallCenterHander hander = new CallCenterHander(request
						.getSession());
				if(hander!=null&& hander.isOnService()) {
					Member mb = hander.getServicedMember();
					data.setCardID(mb.getCARD_ID());
				}

				coll = new ArrayList();
				request.setAttribute("list", coll);
				return mapping.findForward("list_giftnum");

			}
			
			// �����post�ύ�������ж��Ƿ���ͨ��ToolBar�ύ��������������session��ȡ�û�Ա��
			String iscallcenter = request.getParameter("iscallcenter");
			String condition = "";
			if (iscallcenter != null && iscallcenter.equals("1")) {
				CallCenterHander hander = new CallCenterHander(request
						.getSession());
				if (hander.isOnService()) {
					Member mb = hander.getServicedMember();
					data.setMemberID(mb.getID());
					data.setCardID(mb.getCARD_ID());
					condition += " AND a.MEMBER_ID = " + data.getMemberID();
				} else {
					ControlledError ctlErr = new ControlledError();
					ctlErr.setErrorTitle("��������");
					ctlErr.setErrorBody("û�з��������<a href='/member/query.do?service=1'>�������</a>");
					request.setAttribute(Constants.ERROR_KEY, ctlErr);
					return mapping.findForward("controlledError");

				}
			} else {

				if (data != null && data.getCardID() != null
						&& !data.getCardID().equals("")) {
					MbrGetAwardForm data1 = eeDao.getMemberInfoByCardID(conn,
							data);
					if (data1 == null) { // û�д˻�Ա
						Message.setMessage(request, "�û�Ա������", "����", null);
						return mapping.findForward("message");
					} else {
						data.setMemberID(data1.getMemberID());
					}
				}

				// data.setItemStatus(Integer.parseInt(request.getParameter("itemStatus")));
				if (request.getParameter("sellType") != null) {
					data.setSellType(Integer.parseInt(request
							.getParameter("sellType")));
				}
				
				if (data.getMemberID() != 0) {
					condition += " AND a.mbrid = " + data.getMemberID();
				}
				
				if (data.getMemberName()!=null&&!"".equals(data.getMemberName())) {
					condition += " AND b.name ='" + data.getMemberName() + "' ";
				}
				
				if (data.getTelephone()!=null&&!"".equals(data.getTelephone())) {
					condition = condition + " AND ( b.telephone ='" + data.getTelephone() 
					+ "' or b.family_phone ='" + data.getTelephone() + "' or b.company_phone ='"
					+ data.getTelephone() + "') ";
				}
				
				if (data.getGift_number()!=null && !"".equals(data.getGift_number())) {
					condition += " AND a.ticket_num = '" + data.getGift_number() + "' ";
				}
				
				if (data.getSellType() != -1) {
					condition += " AND a.sell_TYPE = " + data.getSellType();
				}
				
				if (condition.trim().equals("")) {
					ControlledError ctlErr = new ControlledError();
					ctlErr.setErrorTitle("������ʾ");
					ctlErr.setErrorBody("��û�������ѯ�������������ѯ������ѯ��");
					request.setAttribute(Constants.ERROR_KEY, ctlErr);
					return mapping.findForward("controlledError");
				}
			}
			int size = ptDao.countMemberGiftNumber(conn, condition);
			Pager page = new Pager(data.getOffset(), size);// ����page����

			page.setOffset(data.getOffset());// ���õ�ǰλ��
			data.setPager(page);
			coll = ptDao.queryMemberGiftNumber(conn, condition, data);

			request.setAttribute("list", coll);
			// request.setAttribute("expExchangeForm", data);
			return mapping.findForward("list_giftnum");
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

	}
	
	/**
	 * ��ʾ���ֶһ�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showExchangePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MbrGetAwardForm myForm = (MbrGetAwardForm) form;
		String forward = "exp";

		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			ExpExchangeActivity activity = ExpExchangeActivityDAO
					.loadCurrentActivity(conn);
			request.setAttribute("activity", activity);

			// �����ʻ���Ϣ
			if(myForm.getCardID()== null || myForm.getCardID().equals("")) {
				CallCenterHander hander = new CallCenterHander(request.getSession());
	            if (hander.isOnService()) {
	                Member mb = hander.getServicedMember();
	                myForm.setCardID(mb.getCARD_ID());
	            } 
			}
			Exp exp = MemberDAO.getExpByCardId(conn, myForm.getCardID());
			request.setAttribute("exp", exp);

			// �����Լ��Ļ������û��ֵ�����Ч��
			setRadioStatus(activity, exp);

		} catch (Exception e) {

			log.error("exception:", e);

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward(forward);
	}

	private void setRadioStatus(ExpExchangeActivity activity, Exp exp) {
		Iterator it = activity.getMstList().iterator();
		String exchangeType = activity.getExchangeType();

		while (it.hasNext()) {
			ExpExchangeStepMst mst = (ExpExchangeStepMst) it.next();
			int beginExp = mst.getBeginExp();
			if (exchangeType.equals("A")) {// һ����
				if (exp.getOldAmountExp() >= beginExp) {
					mst.enabledStepDtl();
				}
			} else { // ʵʱ
				if (exp.getAmountExp() >= beginExp) {
					mst.enabledStepDtl();
				}
			}

		}
	}

	/**
	 * ��Ʒ�һ� ˵�������Զ����Ʒ�һ���ֻҪ�ʻ������㹻
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward expChangeGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MbrGetAwardForm data = (MbrGetAwardForm) form;

		Connection conn = DBManager.getConnection();

		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		if (data.getStepDtlId() == 0 ) {
			Message.setMessage(request, "�һ�ʧ�ܣ���ѡ���¼", "����", null);
			return mapping.findForward("message");
		}
		try {
			conn.setAutoCommit(false); // ����ѭ��Ϊһ������
			data.setOperatorID(Integer.parseInt(user.getId()));
			data.setStepDtlId(Integer.parseInt(request.getParameter("stepDtlId")));
			//�õ���Աid
			int memberId = MemberDAO.getMemberID(conn, data.getCardID());
			if (memberId == 0) {
				Message.setMessage(request, "�һ�ʧ�ܣ���Ա��:"+data.getCardID()+"������", "����", null);
				conn.rollback();
				return mapping.findForward("message");
			}
			data.setMemberID(memberId);
			
			int ret = MbrGetAwardDAO2.expChange(conn, data);
			if (ret == -1) {
				Message.setMessage(request, "�һ�ʧ�ܣ��˻����ֲ��㣬����", "����", null);
				conn.rollback();
				return mapping.findForward("message");
			} else if (ret == -3) {
				Message.setMessage(request, "�һ�ʧ�ܣ����ݳ�����������", "����", null);
				conn.rollback();
				return mapping.findForward("message");
			} else if (ret == -2) {
				Message.setMessage(request, "�һ�ʧ�ܣ������������ô���", "����", null);
				conn.rollback();
				return mapping.findForward("message");
			}

			conn.commit();
			Message.setMessage(request, "�һ��ɹ�", "����",
					"member/mbrGetAward.do?type=showExchangePage");
		} catch (Exception e) {
			conn.rollback(); // ��������쳣�ͻع�
			log.error("exception:", e);
			Message.setMessage(request, "�һ�ʧ�ܣ���������");

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
	 * �һ�����Ʒȡ�� ˵��������ȡ�������Ʒ modified by user 2007-12-19
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward expCancelGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MbrGetAwardForm data = (MbrGetAwardForm) form; // ҳ���
		MbrGetAwardDAO2 ptDao = new MbrGetAwardDAO2();
		MemberGetAwardDAO mawDao = new MemberGetAwardDAO();
		Connection conn = DBManager.getConnection();
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		if (data.getSelectedID() == null || data.getSelectedID().length == 0) {
			Message.setMessage(request, "��Ʒȡ��ʧ�ܣ���ѡ���¼", "����", null);
			return mapping.findForward("message");
		}
		try {
			conn.setAutoCommit(false); // ����ѭ��Ϊһ������
			for (int i = 0; i < data.getSelectedID().length; i++) {

				java.util.ArrayList list = (java.util.ArrayList) mawDao
						.queryMemberAWARD(conn, " and a.id = "
								+ data.getSelectedID()[i],
								new MbrGetAwardForm()); // �õ���¼
				data.setAwardID(data.getSelectedID()[i]);
				data.setOperatorID(Integer.parseInt(user.getId()));
				data.setOperatorName(user.getNAME());
				//data.setItemID(((MemberAWARD) list.get(0)).getItem_ID());
				data.setItemCode(((MemberAWARD) list.get(0)).getItemCode());
				data.setItemName(((MemberAWARD) list.get(0)).getItemName());
				data.setExpExchange(((MemberAWARD) list.get(0))
						.getUsed_amount_exp());
				data.setExchangePrice(((MemberAWARD) list.get(0)).getPrice());
				data.setSellType(((MemberAWARD) list.get(0)).getType());

				int rtnValue = ptDao.expCancel(conn, data);

				if (rtnValue == 2) {
					Message.setMessage(request, "��Ʒȡ��ʧ�ܣ����ݳ�����������", "����", null);
					return mapping.findForward("message");
				}
			}
			conn.commit();
			// Message.setMessage(request, "��Ʒȡ���ɹ�", "����",
			// "member/expExchange.do?type=queryExchangedGift&cardID=" +
			// data.getCardID());
		} catch (Exception e) {
			conn.rollback();
			log.error("exception:", e);
			Message.setMessage(request, "��Ʒȡ������");
			throw e;

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("item_list");// �����ݴ�� _award.jsp
	}

	
	/**
	 * �һ�����Ʒȡ�� ˵��������ȡ�������Ʒ modified by user 2007-12-19
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelGiftNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MbrGetAwardForm data = (MbrGetAwardForm) form; // ҳ���
		MbrGetAwardDAO2 ptDao = new MbrGetAwardDAO2();
		MemberGetAwardDAO mawDao = new MemberGetAwardDAO();
		Connection conn = DBManager.getConnection();
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		if (data.getSelectedID() == null || data.getSelectedID().length == 0) {
			Message.setMessage(request, "��ȯȡ��ʧ�ܣ���ѡ���¼", "����", null);
			return mapping.findForward("message");
		}
		try {
			conn.setAutoCommit(false); // ����ѭ��Ϊһ������
			for (int i = 0; i < data.getSelectedID().length; i++) {

				java.util.ArrayList list = (java.util.ArrayList) mawDao
						.queryMemberGiftNumber(conn, " and a.id = "
								+ data.getSelectedID()[i],
								new MbrGetAwardForm()); // �õ���¼
				data.setAwardID(data.getSelectedID()[i]);
				data.setOperatorID(Integer.parseInt(user.getId()));
				data.setOperatorName(user.getNAME());
				//data.setItemID(((MemberAWARD) list.get(0)).getItem_ID());
				data.setItemCode(((MemberAWARD) list.get(0)).getItemCode());
				data.setItemName(((MemberAWARD) list.get(0)).getItemName());
				data.setExpExchange(((MemberAWARD) list.get(0))
						.getUsed_amount_exp());
				data.setExchangePrice(((MemberAWARD) list.get(0)).getPrice());
				data.setSellType(((MemberAWARD) list.get(0)).getType());

				int rtnValue = ptDao.cancelGiftNumber(conn, data);

				if (rtnValue == 2) {
					Message.setMessage(request, "��ȯȡ��ʧ�ܣ����ݳ�����������", "����", null);
					return mapping.findForward("message");
				}
			}
			conn.commit();
			// Message.setMessage(request, "��Ʒȡ���ɹ�", "����",
			// "member/expExchange.do?type=queryExchangedGift&cardID=" +
			// data.getCardID());
		} catch (Exception e) {
			conn.rollback();
			log.error("exception:", e);
			Message.setMessage(request, "��ȯȡ������");
			throw e;

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("giftnumber_list");// �����ݴ�� _award.jsp
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
		ExpExchangeHisForm data = (ExpExchangeHisForm) form;
		MbrGetAwardDAO2 ptDao = new MbrGetAwardDAO2();
		Connection conn = DBManager.getConnection();

		try {

			// 1 ��ѯ��Ϣ
			Collection ret = ptDao.getHisList(conn, data);

			if (request.getMethod().equals("GET")) {

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

		try {
			return mapping.findForward("add");
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		}

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
		MbrGetAwardForm data = (MbrGetAwardForm) form;
		MbrGetAwardDAO2 ptDao = new MbrGetAwardDAO2();
		MbrGetAwardDAO2 eeDao = new MbrGetAwardDAO2();
		Connection conn = DBManager.getConnection();
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		data.setOperatorID(Integer.parseInt(user.getId()));
		try {

			if (!data.getCardID().equals("")) {

				MbrGetAwardForm data2 = eeDao.getMemberInfoByCardID(conn, data);
				if (data2 == null) { // û�д˻�Ա
					Message.setMessage(request, "�û�Ա������", "����", null);
					return mapping.findForward("message");
				} else {
					data.setMemberID(data2.getMemberID());
				}
			}
			if (!data.getItemCode().equals("")) {
				int itemID = ProductDAO.getItemID(conn, data.getItemCode());
				if (itemID == 0) {
					Message.setMessage(request, "�ò�Ʒ������", "����", null);
					return mapping.findForward("message");
				} else {
					//data.setItemID(itemID);
				}
			}
			ptDao.insertGift(conn, data);

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
		Message.setMessage(request, "��Ʒ���ӳɹ�", "����",
				"member/MbrGetAward.do?type=queryGift&cardID="
						+ data.getCardID());
		return mapping.findForward("message");
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
	public ActionForward qryActiveGiftNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MbrGetAwardForm data = (MbrGetAwardForm) form;
		//MbrGetAwardDAO2 ptDao = new MbrGetAwardDAO2();
		//MbrGetAwardDAO2 eeDao = new MbrGetAwardDAO2();
		Connection conn = DBManager.getConnection();
		//User user = new User();
		//HttpSession session = request.getSession();
		//user = (User) session.getAttribute("user");
		//data.setOperatorID(Integer.parseInt(user.getId()));
		try {
			
			Collection giftNumbers = MemberGetAwardDAO.qryActiveGiftNumber(conn, data);
			request.setAttribute("list", giftNumbers);	

		} catch (Exception e) {
			log.error("exception:", e);
			Message.setMessage(request, e.getMessage(), "����", null);
			return mapping.findForward("message");
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}
		return mapping.findForward("activeGiftNumberQry");
	}
}
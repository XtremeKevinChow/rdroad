/*
 * Created on 2006-4-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.member.dao.MbrGetAwardDAO2;
import com.magic.crm.member.dao.MemberMoneyDrawbackDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.MbrGetAwardForm;
import com.magic.crm.member.form.MemberMoneyDrawbackForm;
import com.magic.crm.product.dao.Product2DAO;
import com.magic.crm.product.dao.ProductBaseDAO;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.promotion.dao.MbrGiftListDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Config;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;
import com.magic.crm.util.ChangeCoding;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAddGiftAction extends DispatchAction {
    /**
	 * ��ʾ��Ʒ��ʼ��ҳ��
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
		
		MbrGetAwardForm pageData= (MbrGetAwardForm)form;
		Connection conn = DBManager.getConnection();
		try {
			CallCenterHander hander = new CallCenterHander(request.getSession());
			Member mb = hander.getServicedMember();
			if (hander.isOnService()) {
				pageData.setCardID(mb.getCARD_ID());
			}/*else {
				 ControlledError ctlErr = new ControlledError();
	             ctlErr.setErrorTitle("��������");
	             ctlErr.setErrorBody("û�з��������������");
	             request.setAttribute(Constants.ERROR_KEY, ctlErr);
	             return mapping.findForward("controlledError");
			}*/
			
			ArrayList colors = Product2DAO.listItemColor(conn, pageData.getItemCode());
			pageData.setColors(colors);
			
			ArrayList sizes = Product2DAO.listItemSize(conn, pageData.getItemCode());
			pageData.setSizes(sizes);
			
			pageData.setAvailDay(Integer.parseInt(Config.getValue("PROMOTION_GIFT_KEEP_DAY")));
			
		    return mapping.findForward("add");
		} catch (Exception e) {
			
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}

		
	}
	
	/**
	 * ������ȯ�ĳ�ʼ��ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInit2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MbrGetAwardForm pageData= (MbrGetAwardForm)form;
		//Connection conn = DBManager.getConnection();
		try {
			CallCenterHander hander = new CallCenterHander(request.getSession());
			Member mb = hander.getServicedMember();
			if (hander.isOnService()) {
				pageData.setCardID(mb.getCARD_ID());
			}/*else {
				 ControlledError ctlErr = new ControlledError();
	             ctlErr.setErrorTitle("��������");
	             ctlErr.setErrorBody("û�з��������������");
	             request.setAttribute(Constants.ERROR_KEY, ctlErr);
	             return mapping.findForward("controlledError");
			}*/
			
			//ArrayList colors = ProductBaseDAO.listColor(conn);
			//pageData.setColors(colors);
			
			//ArrayList sizes = ProductBaseDAO.listSize(conn, pageData.getItemCode());
			//pageData.setSizes(sizes);
			pageData.setAvailDay(Integer.parseInt(Config.getValue("PROMOTION_GIFT_KEEP_DAY")));
		    return mapping.findForward("add2");
		} catch (Exception e) {
			
			throw e;
		} finally {
			//try { conn.close(); } catch(Exception e) {}
		}

		
	}

	/**
	 * ������Ʒ
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
				if (data2 == null) { //û�д˻�Ա
					Message.setMessage(request, "�û�Ա������", "����", null);
					return mapping.findForward("message");
				} else{
				    data.setMemberID(data2.getMemberID());
				}
			}
		    /*if (!data.getItemCode().equals("")) {
		        int itemID = ProductDAO.getItemID(conn, data.getItemCode());
		        if (itemID == 0) {
		            Message.setMessage(request, "�ò�Ʒ������", "����", null);
					return mapping.findForward("message");
		        } else {
		            //data.setItemID(itemID);
		        }
		    }*/
		    //�����ַ�����
		    data.setDescription(ChangeCoding.unescape(ChangeCoding.toUtf8String(data.getDescription())));
		    //int ret = ptDao.insertGift(conn, data);
		    int ret =ptDao.insertUnauditGift(conn,data);
		    if (ret ==-1) {
		    	Message.setMessage(request, "��Ӧ��sku�����ڣ����ܼ����ݴ��", "����", null);
		    	return mapping.findForward("message");
		    }

		} catch (Exception e) {
			
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}
		Message.setMessage(request, "��Ʒ���ӳɹ�,����Ҫ�������");
		return mapping.findForward("message");
	}

	/**
	 * ������ȯ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add2(ActionMapping mapping, ActionForm form,
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
				if (data2 == null) { //û�д˻�Ա
					Message.setMessage(request, "�û�Ա������", "����", null);
					return mapping.findForward("message");
				} else{
				    data.setMemberID(data2.getMemberID());
				}
			}
		    
		    //�����ַ�����
		    data.setDescription(ChangeCoding.unescape(ChangeCoding.toUtf8String(data.getDescription())));
		    int type = MbrGiftListDAO.checkGiftNumber(conn,data.getGift_number());
		    if (type<0) {
		    	throw new Exception("���������ȯ������");
		    } else if (type==2) {
		    	//data.setGift_number(MbrGiftListDAO.generateGiftNumber(conn, data.getGift_number()));
		    	throw new Exception("��������ϸ����ȯ�Ŷ�������ȯ����");
		    } else if (type ==4 ||type==6) {
		    	throw new Exception("���ܼ����ݴ��,�¶���ʱֱ��ʹ��");
		    }
		    
		    ptDao.insertGiftNumber(conn, data);

		} catch (Exception e) {
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("message");
			
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}
		//Message.setMessage(request, "��Ʒ���ӳɹ�", "����", "member/consoleAward.do?iscallcenter=1");
		return mapping.findForward("giftnumber_list");
	}
	
	
	/**
	 * ��ѯ��Ҫ��˼�¼
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			ArrayList info = new MbrGetAwardDAO2().listAudit(conn);
			request.setAttribute("list", info);
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close();} catch(Exception e){}
		}
		
		
		return mapping.findForward("listUnaudit");
	}
	
	/**
	 * �����Ʒ��¼
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MbrGetAwardForm fm = (MbrGetAwardForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MbrGetAwardDAO2 dao = new MbrGetAwardDAO2();
			conn.setAutoCommit(false);
			
			User user = (User) request.getSession().getAttribute("user");
			int userId = Integer.parseInt(user.getId());
			fm.setOperatorID(userId);
			
			String[] ids = request.getParameterValues("selID");
			if(ids!=null) {
				for(int i=0;i<ids.length;i++) {
					fm.setAwardID(Integer.parseInt(ids[i]));
					dao.audit(conn,fm);
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			//����Ϊ��ʾҳ��
			ArrayList info = new MbrGetAwardDAO2().listAudit(conn);
			request.setAttribute("list", info);
		} catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close();} catch(Exception e){}
		}
		
		return mapping.findForward("listUnaudit");
	}

	/**
	 * ȡ����Ʒ��¼
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MbrGetAwardForm fm = (MbrGetAwardForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			User user = (User) request.getSession().getAttribute("user");
			int userId = Integer.parseInt(user.getId());
			fm.setOperatorID(userId);
			MbrGetAwardDAO2 dao = new MbrGetAwardDAO2();
			
			String[] ids = request.getParameterValues("selID");
			if(ids!=null) {
				for(int i=0;i<ids.length;i++) {
					fm.setAwardID(Integer.parseInt(ids[i]));
					dao.cancelAudit(conn, fm);
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			//����Ϊ��ʾҳ��
			ArrayList info = dao.listAudit(conn);
			request.setAttribute("list", info);
		} catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close();} catch(Exception e){}
		}
		
		return mapping.findForward("listUnaudit");
	}

}

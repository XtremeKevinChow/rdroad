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
import com.magic.crm.member.dao.MemberMoneyDrawbackDAO;
import com.magic.crm.member.form.MemberExpExchangeForm;
import com.magic.crm.member.form.MbrGetAwardForm;
import com.magic.crm.member.form.ExpExchangeHisForm;
import com.magic.crm.member.form.MemberMoneyDrawbackForm;
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
public class MemberMoneyDrawbackAction extends DispatchAction {

	private static Logger log = Logger.getLogger(MemberMoneyDrawbackAction.class);

	/**
	 * 查询有效礼品纪录
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
			ArrayList info = MemberMoneyDrawbackDAO.listAudit(conn);
			request.setAttribute("list", info);
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close();} catch(Exception e){}
		}
		
		
		return mapping.findForward("query");
	}
	
	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberMoneyDrawbackForm fm = (MemberMoneyDrawbackForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			User user = (User) request.getSession().getAttribute("user");
			long userId = Long.parseLong(user.getId());
			fm.setAuditor(userId);
			String[] ids = request.getParameterValues("selID");
			if(ids!=null) {
				for(int i=0;i<ids.length;i++) {
					fm.setID(Long.parseLong(ids[i]));
					MemberMoneyDrawbackDAO.audit(conn,fm);
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			//以下为显示页面
			ArrayList info = MemberMoneyDrawbackDAO.listAudit(conn);
			request.setAttribute("list", info);
		} catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close();} catch(Exception e){}
		}
		
		return mapping.findForward("query");
	}

	public ActionForward cancelAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberMoneyDrawbackForm fm = (MemberMoneyDrawbackForm) form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			User user = (User) request.getSession().getAttribute("user");
			long userId = Long.parseLong(user.getId());
			fm.setAuditor(userId);
			String[] ids = request.getParameterValues("selID");
			if(ids!=null) {
				for(int i=0;i<ids.length;i++) {
					fm.setID(Long.parseLong(ids[i]));
					MemberMoneyDrawbackDAO.cancel(conn, fm);
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			//以下为显示页面
			ArrayList info = MemberMoneyDrawbackDAO.listAudit(conn);
			request.setAttribute("list", info);
		} catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
			Message.setErrorMsg(request, e.getMessage());
			return mapping.findForward("error");
		} finally {
			try { conn.close();} catch(Exception e){}
		}
		
		return mapping.findForward("query");
	}

}
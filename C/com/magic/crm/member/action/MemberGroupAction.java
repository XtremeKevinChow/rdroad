/*
 * Created on 2005-12-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.member.form.MemberGroupForm;
import com.magic.crm.member.dao.MemberGroupDAO;
import com.magic.crm.user.dao.S_AREADao;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;


/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberGroupAction extends DispatchAction {

	private static Logger log = Logger.getLogger("MemberGroupAction.class");
	
	/**
	 * 显示添加页面
	 * @param mapping
	 * @param from
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		try {
			MemberGroupForm data = (MemberGroupForm)form;
			conn = DBManager.getConnection();
			ArrayList provs = S_AREADao.listProvince(conn);
    		data.setProvs(provs);
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {conn.close();} catch(Exception e) {}
		}
		return mapping.findForward("add");
	}
	
	/**
	 * 新增记录
	 * @param mapping
	 * @param from
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		try {
			User user = new User();
			HttpSession session = request.getSession();
			user = (User) session.getAttribute("user");
			MemberGroupForm data = (MemberGroupForm)form; 
			data.setLoginUserID(Integer.parseInt(user.getId()));
			MemberGroupDAO mgDao = new MemberGroupDAO();
			conn = DBManager.getConnection();
			String cardID = mgDao.insert(conn, data);
			if  (cardID == null) {			
				Message.setMessage(request, "添加失败");
			} else {
				Message.setMessage(request, "添加成功,团体会员号为: " + cardID);
			}
		}catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch(Exception e) {
					
				}
			}
		}
		return mapping.findForward("success");
	}
	
	/**
	 * 显示修改页面
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
		Connection conn = null;
		MemberGroupForm newdata = new MemberGroupForm();
		String ID = request.getParameter("id");
		try {
			conn = DBManager.getConnection();
			MemberGroupForm data = (MemberGroupForm)form;
			data.setID(Long.parseLong(ID));
			MemberGroupDAO mgDao = new MemberGroupDAO();
			newdata = mgDao.showDetail(conn, data);
			request.setAttribute("memberGroupForm", newdata);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.close();
				}catch(Exception e) {
					
				}
			}
		}
		return mapping.findForward("modify");
	}
	
	/**
	 * 修改记录
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
		Connection conn = null;
		try {
			User user = new User();
			HttpSession session = request.getSession();
			user = (User) session.getAttribute("user");
			conn = DBManager.getConnection();
			MemberGroupForm data = (MemberGroupForm)form;
			data.setLoginUserID(Integer.parseInt(user.getId()));
			MemberGroupDAO mgDao = new MemberGroupDAO();
			mgDao.update(conn, data);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.close();
				}catch(Exception e) {
					
				}
			}
		}
		return mapping.findForward("list");
	}
}
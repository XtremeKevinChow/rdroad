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
 * @author 蟋蟀
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAddMoneyGiftSetupAction extends DispatchAction {
	
	private static Logger log = Logger.getLogger("MemberAddMoneyGiftSetupAction.class");
	
	/**
	 * 显示新增页面
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
	 * 添加记录
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
		src.copy(data);//拷贝数据
		try {
			conn = DBManager.getConnection();
			/*int itemID = ProductDAO.getItemID(conn, data.getItemCode());
			if ( itemID == 0 ) {
				Message.setMessage(request, "该产品不存在", "返 回","member/memberAddMoneyGiftSetup.do?type=addInit");
				return mapping.findForward("message");
			}
			data.setItemID(itemID);
			
			//假如表中有这个产品或者有这个金额
			if (ptDao.hasItemID(conn, data) || ptDao.hasMoney(conn, data)) {
				Message.setMessage(request, "货号或金额已经存在","返 回","member/memberAddMoneyGiftSetup.do?type=query");
				return mapping.findForward("message");
			}*/
			ptDao.insert(conn, data);

		} catch (Exception e) {
			Message.setMessage(request, "新增错误");
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
	 * 显示修改页面
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
	 * 修改记录
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
				Message.setMessage(request, "该产品不存在","返 回","member/memberAddMoneyGiftSetup.do?type=addInit");
				return mapping.findForward("message");
			}
			data.setItemID(itemID);
			//假如表中有这个产品或者有这个金额
			if (ptDao.hasItemID(conn, data) || ptDao.hasMoney(conn, data)) {
				Message.setMessage(request, "货号或金额已经存在","返 回","member/memberAddMoneyGiftSetup.do?type=query");
				return mapping.findForward("message");
			}
			ptDao.update(conn, data);

		} catch (Exception e) {
			Message.setMessage(request, "修改错误");
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
	 * 删除记录
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
				Message.setMessage(request, "请选择记录");
			} 
			for (int i = 0; i < delID.length; i ++) {
				pageData.setId(Integer.parseInt(delID[i]));
				ptDao.delete(conn, pageData);
			}
			
			Message.setMessage(request, "设置成功","返 回","member/memberAddMoneyGiftSetup.do?type=query");
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
	 * 查询所有记录
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
			//1 查询信息
			Collection ret = ptDao.getList(conn);
			request.setAttribute("list", ret);
		} catch (Exception e) {
			log.error("exception:", e);
			Message.setMessage(request, "查询出错");
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

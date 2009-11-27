/*
 * @author Administrator(ysm)
 * Created on 2005-10-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import com.magic.crm.util.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;

import com.magic.crm.promotion.entity.Promotion;
import com.magic.crm.promotion.dao.PromotionDAO;
import com.magic.crm.promotion.form.PromotionForm;

/**
 * @author Administrator(ysm) Created on 2005-10-10
 */
public class PromotionOperationAction extends DispatchAction {
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromotionForm fForm = (PromotionForm) form;
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		Connection conn = null;
		Promotion info = new Promotion();
		PromotionDAO pdao = new PromotionDAO();
		String name = request.getParameter("name");
		String putbasket = request.getParameter("putbasket");
		String description = request.getParameter("description");
		String flag = request.getParameter("flag");
		String begindate = request.getParameter("begindate");
		String enddate = request.getParameter("enddate");
		info.setName(name);
		info.setPutbasket(Integer.parseInt(putbasket));
		info.setCreatorID(user.getId());
		info.setDescription(description);
		info.setFlag(Integer.parseInt(flag));
		info.setValidFlag(1);
		info.setBeginDate(begindate);
		info.setEndDate(enddate);
		info.setSynch(1);
		Collection promotionCol = new ArrayList();
		try {
			conn = DBManager.getConnection();
			/**
			 * 判断促销名称是否重复
			 */
			if (PromotionDAO.checkPromotion(conn, name) > 0) {
				promotionCol = pdao.queryPromotion(conn, "");
				request.setAttribute("promotionCol", promotionCol);
				return mapping.findForward("error1");
			} else {
				PromotionDAO.insert(conn, info);
			}
			promotionCol = pdao.queryPromotion(conn, "");
			request.setAttribute("promotionCol", promotionCol);
		} catch (SQLException se) {
			se.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException sqe) {

			}

		}
		return mapping.findForward("success");

	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromotionForm fForm = (PromotionForm) form;
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		Connection conn = null;
		Promotion info = new Promotion();
		PromotionDAO pdao = new PromotionDAO();
		Collection promotionCol = new ArrayList();
		String promotionname = request.getParameter("name");
		promotionname=(promotionname==null)?"":promotionname;
		
	    //String validflag = request.getParameter("validflag");
	    //if(validflag == null) validflag = "1";
		String flag = request.getParameter("flag");
			   flag=(flag==null)?"":flag;

		try {

			conn = DBManager.getConnection();
			String condition = " where  id<1000000 ";
		
				
				
			if (promotionname.length() > 0) {
				condition += " and  name like '%" + promotionname + "%' ";
			}
			
			//if(validflag.equals("1")){
			//	condition += " and validflag = 1 and sysdate < enddate + 1";
			//}else if(validflag.equals("0")){
			//	condition += " and validflag = 0 or sysdate >= enddate + 1";
			//}
			if (flag.length() > 0) {
				condition += "  and flag = "+flag;
			}			

			promotionCol = pdao.queryPromotion(conn, condition);

			request.setAttribute("promotionCol", promotionCol);


		} catch (SQLException se) {
			se.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException sqe) {

			}

		}
		return mapping.findForward("success");

	}

	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromotionForm fForm = (PromotionForm) form;
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		Connection conn = null;
		Promotion info = new Promotion();
		PromotionDAO pdao = new PromotionDAO();
		Collection promotionCol = new ArrayList();
		String promotionname = request.getParameter("promotionname");
		String tag = request.getParameter("tag");
		String id = request.getParameter("id");

		try {

			conn = DBManager.getConnection();
			info.setId(Integer.parseInt(id));
			info.setValidFlag(Integer.parseInt(tag));
			info.setModifierID(user.getId());
			PromotionDAO.updateValidFlag(conn, info);

			String condition = "";
			if (promotionname != null && promotionname.length() > 0) {
				condition = " where name like '%" + promotionname + "%'";
			}
			promotionCol = pdao.queryPromotion(conn, condition);
			request.setAttribute("promotionCol", promotionCol);

		} catch (SQLException se) {
			se.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException sqe) {

			}

		}
		return mapping.findForward("success");

	}

	/**
	 * add by user 2005-12-14 促销修改
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
		String page = "success";
		Collection promotionCol = new ArrayList();
		PromotionForm fForm = (PromotionForm) form;
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute(Constants.USER_KEY);
		Connection conn = null;
		Promotion info = new Promotion();
		PromotionDAO pdao = new PromotionDAO();
		info.setId(fForm.getId());
		info.setName(fForm.getName());
		info.setPutbasket(fForm.getPutbasket());
		info.setModifierID(user.getId());
		info.setDescription(fForm.getDescription());
		info.setFlag(fForm.getFlag());
		info.setBeginDate(fForm.getBeginDate());
		info.setEndDate(fForm.getEndDate());
		try {
			conn = DBManager.getConnection();
	
			if ( PromotionDAO.modify(conn, info) ) {
				page = "success";
				
			} else {
				System.out.println("更新错误");
			}
			
			promotionCol = pdao.queryPromotion(conn, "");
			
			request.setAttribute("promotionCol", promotionCol);
			
		} catch (SQLException se) {
			se.printStackTrace();
	
		} finally {
			try {
				conn.close();
	
			} catch (SQLException sqe) {
				sqe.printStackTrace();
			}
	
		}
		return mapping.findForward(page);
	}

	public ActionForward showCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromotionForm fForm = (PromotionForm) form;
		Connection conn = DBManager.getConnection();
	
		try {
	
			conn = DBManager.getConnection();
			PromotionDAO dao = new PromotionDAO();
			dao.showCategory(conn,fForm);
	
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			try {
				conn.close();
	
			} catch (Exception sqe) {
	
			}
	
		}
		return mapping.findForward("showCat");
	
	}

	public ActionForward saveCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromotionForm fForm = (PromotionForm) form;
		Connection conn = DBManager.getConnection();
	
		try {
	
			conn = DBManager.getConnection();
			PromotionDAO dao = new PromotionDAO();
			dao.saveCategory(conn,fForm);
	
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} finally {
			try {
				conn.close();
	
			} catch (Exception sqe) {
	
			}
	
		}
		return mapping.findForward("query");
	
	}
	
	/**
	 * add by user 2005-12-14 显示促销修改页面
	 * 
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
		PromotionForm fForm = (PromotionForm) form;
		Promotion info = new Promotion();
		PromotionDAO pdao = new PromotionDAO();
		String id = request.getParameter("id");
		info.setId(Integer.parseInt(id));
		
		try {
			conn = DBManager.getConnection();

			info = PromotionDAO.showDetail(conn, info);
			
			request.setAttribute("pForm", info);
		} catch (SQLException se) {
			se.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException sqe) {
				sqe.printStackTrace();
			}

		}
		return mapping.findForward("modify");
	}
}
/*
 * @author Administrator(ysm)
 * Created on 2005-10-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.promotion.dao.PromotionDAO;
import com.magic.crm.promotion.entity.*;
import com.magic.crm.promotion.form.Prom_giftForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.Constants;
import com.magic.crm.util.DBManager;

/**
 * @author Administrator(ysm) Created on 2005-10-11
 */
public class Prom_GiftOperationAction extends DispatchAction {
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		Connection conn = null;
		Prom_gift info = new Prom_gift();
		PromotionDAO pdao = new PromotionDAO();
		String promotionid = request.getParameter("promotionid");
		String item_code = request.getParameter("item_code");
		String overx = request.getParameter("overx");
		String addy = request.getParameter("addy");
		String description = request.getParameter("description");
		//String begindate = request.getParameter("begindate");
		//String enddate = request.getParameter("enddate");
		//String prom_url = request.getParameter("prom_url");
		//String scope = request.getParameter("scope");
		info.setCreatorID(user.getId()); 
		info.setPromotionID(Integer.parseInt(promotionid));
		info.setItemcode(item_code);
		info.setOverx(Double.parseDouble(overx));
		info.setAddy(Double.parseDouble(addy));
		info.setDescription(description);
		//info.setBeginDate(begindate);
		//info.setEndDate(enddate);
		//info.setProm_url(prom_url);
		//info.setScope(Integer.parseInt(scope));
		Collection prom_giftCol = new ArrayList();
		try {
			conn = DBManager.getConnection();
			/*
			 * 判断货号是否存在
			 */
			/*if (ProductDAO.getItemID(conn, item_code) == 0) {
				request.setAttribute("checkItem_Gift", "");
				prom_giftCol = pdao.queryPromo_Gift(conn, Integer
						.parseInt(promotionid));
				request.setAttribute("prom_giftCol", prom_giftCol);
				return mapping.findForward("error2");
			}*/
			/*
			 * 根据货号得到产品ID
			 */
			//info.setItemID(String
			//		.valueOf(ProductDAO.getItemID(conn, item_code)));
			/*
			 * 判断促销礼品号是否重复
			 */
			/*
			if (PromotionDAO.checkProm_Gift(conn, info) > 0) {
				request.setAttribute("checkItem_Gift", "");
				prom_giftCol = pdao.QueryPromo_Gift(conn, Integer
						.parseInt(promotionid));
				request.setAttribute("prom_giftCol", prom_giftCol);
				return mapping.findForward("error1");

			} else {
				request.setAttribute("checkItem_Gift", "");
				PromotionDAO.insertProm_Gift(conn, info);
			}
			 */
	    	//modify bymagic 
	    	//修改目的:产品可以重复增加
			request.setAttribute("checkItem_Gift", "");
			PromotionDAO.insertProm_Gift(conn, info);
			prom_giftCol = pdao.queryPromo_Gift(conn, Integer
					.parseInt(promotionid));
			request.setAttribute("prom_giftCol", prom_giftCol);
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

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		Connection conn = null;
		Prom_Item info = new Prom_Item();
		PromotionDAO pdao = new PromotionDAO();
		String promotionid = request.getParameter("promotionid");
		Collection prom_giftCol = new ArrayList();
		String checkItem_Gift = "";
		try {
			conn = DBManager.getConnection();
			if (PromotionDAO
					.checkItem_Gift(conn, Integer.parseInt(promotionid)) == 3) {
				//做组促销时要先添加组产品后再添加组礼品
				checkItem_Gift = "3";
			}
			prom_giftCol = pdao.queryPromo_Gift(conn, Integer
					.parseInt(promotionid));
			request.setAttribute("prom_giftCol", prom_giftCol);
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				conn.close();

			} catch (SQLException sqe) {

			}

		}
		request.setAttribute("checkItem_Gift", checkItem_Gift);
		return mapping.findForward("success");

	}

	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		Collection prom_giftCol = new ArrayList();
		Connection conn = null;
		Prom_Item info = new Prom_Item();
		PromotionDAO pdao = new PromotionDAO();
		String promotionid = request.getParameter("promotionid");
		String id = request.getParameter("id");
		String tag = request.getParameter("tag");
		info.setFlag(Integer.parseInt(tag));
		info.setID(Integer.parseInt(id));
		info.setModifierID(user.getId());
		info.setPromotionID(Integer.parseInt(promotionid));

		try {
			conn = DBManager.getConnection();
			PromotionDAO.updateGiftValidFlag(conn, info);
			prom_giftCol = pdao.queryPromo_Gift(conn, info.getPromotionID());
			request.setAttribute("prom_giftCol", prom_giftCol);
		} catch (SQLException se) {
			se.printStackTrace();

		} finally {
			try {
				conn.close();

			} catch (SQLException sqe) {

			}

		}
		request.setAttribute("checkItem_Gift", "");
		return mapping.findForward("success");

	}

	/**
	 * add by user 2005-12-14 显示促销礼品修改页面
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
		Prom_giftForm fForm = (Prom_giftForm) form;
		Prom_gift info = new Prom_gift();
		PromotionDAO pdao = new PromotionDAO();
		String id = request.getParameter("id");
		info.setID(Integer.parseInt(id));
		try {
			conn = DBManager.getConnection();

			info = PromotionDAO.showPromGiftDetail(conn, info);

			request.setAttribute("pGiftForm", info);
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

	/**
	 * add by user 2005-12-14 促销礼品修改
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
		Collection prom_giftCol = new ArrayList();
		Prom_giftForm fForm = (Prom_giftForm) form;
		User user = new User();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute(Constants.USER_KEY);
		Connection conn = null;
		Prom_gift info = new Prom_gift();
		PromotionDAO pdao = new PromotionDAO();
		info.setID(fForm.getID());

		info.setItemcode(fForm.getItemcode());
		info.setOverx(fForm.getOverx());
		info.setAddy(fForm.getAddy());
		info.setBeginDate(fForm.getBeginDate());
		info.setEndDate(fForm.getEndDate());
		info.setScope(fForm.getScope());
		info.setModifierID(user.getId());
		info.setDescription(fForm.getDescription());
		info.setProm_url(fForm.getProm_url());
		

		try {
			conn = DBManager.getConnection();
			// 判断该产品是否存在
			/*if (ProductDAO.getItemID(conn, fForm.getItemcode()) == 0) {
				request.setAttribute("checkItem_Gift", "");
				prom_giftCol = pdao.queryPromo_Gift(conn, fForm
						.getPromotionID());
				request.setAttribute("prom_giftCol", prom_giftCol);
				return mapping.findForward("error2");

			} else {
				//info.setItemID(String.valueOf(ProductDAO.getItemID(conn, fForm
				//		.getItemcode())));
			}*/

			// 判断促销礼品号是否重复
			/*if (PromotionDAO.checkProm_Gift(conn, info) > 0) {
				request.setAttribute("checkItem_Gift", "");
				prom_giftCol = pdao.queryPromo_Gift(conn, fForm
						.getPromotionID());
				request.setAttribute("prom_giftCol", prom_giftCol);
				return mapping.findForward("error1");

			} else {
				request.setAttribute("checkItem_Gift", "");
				PromotionDAO.insertProm_Gift(conn, info);
			}*/
			request.setAttribute("checkItem_Gift", "");
			if (PromotionDAO.modifyPromGift(conn, info)) {
				page = "success";

			} else {
				System.out.println("更新错误");
			}

			
			prom_giftCol = pdao.queryPromo_Gift(conn, fForm.getPromotionID());
			request.setAttribute("prom_giftCol", prom_giftCol);
			
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

}
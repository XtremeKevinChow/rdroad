package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.DateUtil;
import com.magic.crm.util.Message;
import com.magic.crm.util.DateUtil;

/**
 * 
 * @author user
 * 
 */
public class ChangeNewCardAction extends DispatchAction {

	private static Logger log = Logger.getLogger(ChangeNewCardAction.class);

	/**
	 * 显示换卡页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forward = "input";
		MemberForm pageData = (MemberForm) form;
		java.util.Date now = new java.util.Date();
		if (DateUtil.getDate("2008-01-05", "yyyy-MM-dd").after(now) 
				|| now.after(DateUtil.getDate("2008-03-01", "yyyy-MM-dd"))) {
			Message.setMessage(request, "换卡时间必须是2008年1月5号至2008年2月29号之间", "返回", null);
			forward = "message";
			return mapping.findForward(forward);
		}
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			MemberDAO memberDao = new MemberDAO();
			Member member = memberDao
					.getMemberInfo(conn, pageData.getCARD_ID());
			request.setAttribute("member", member);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}

	/**
	 * 换卡
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeNewCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forward = "desktop";
		MemberForm pageData = (MemberForm) form;

		int itemId = 0;
		try {
			itemId = Integer.parseInt(request.getParameter("itemId"));
		} catch (NumberFormatException ne) {
			Message.setMessage(request, "请选择新卡", "返回", null);
			forward = "message";
			return mapping.findForward(forward);
		}
		int cardId = Integer.parseInt(pageData.getCARD_ID());
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			boolean flag = MemberDAO.isElongCard(conn, String.valueOf(cardId));
			// 检测旧卡是否在换卡区间
			boolean isInArea = false;
			if (itemId == 100004) { // 白金会员卡-格子（条码卡）
				if (cardId >= 20000001 && cardId <= 20050000) {
					isInArea = true;
				}
			} else if (itemId == 100002) { // e龙VIP联名卡-格子（条码卡）

				if ((cardId >= 40000001 && cardId <= 40005000)
						|| (cardId >= 60000001 && cardId <= 60002000)
						|| (cardId >= 40005001 && cardId <= 40010000)
						|| (cardId >= 40010001 && cardId <= 40020000)
						|| (cardId >= 40020001 && cardId <= 40045000) || flag) {
					isInArea = true;
				}
			} else if (itemId == 100000) { // 标准会员卡-格子（条码卡）
				MemberDAO memberDAO = new MemberDAO();
				Member member = memberDAO.getMemberInfo(conn, String
						.valueOf(cardId));
				// 在会员号区间内
				if ((cardId >= 70006001 && cardId <= 70009000)
						|| (cardId >= 30000001 && cardId <= 30020000)
						|| (cardId >= 50000001 && cardId <= 50003000)
						|| (cardId >= 30020001 && cardId <= 30025000)
						|| (cardId >= 30025001 && cardId <= 30045000)
						|| (cardId >= 30045001 && cardId <= 30095000)
						|| (cardId >= 30095001 && cardId <= 30145000)
						|| (cardId >= 30145001 && cardId <= 30195000)
						|| (cardId >= 30195001 && cardId <= 30245000)
						|| (cardId >= 30245001 && cardId <= 30295000)
						|| (cardId >= 30295001 && cardId <= 30345000)
						|| (cardId >= 30345001 && cardId <= 30395000)
						|| (cardId >= 30395001 && cardId <= 30432000)) {
					isInArea = true;
				}
				// 8或9开头的必须是银卡会员以上、并且不再elong卡区间的才能换
				String firstChar = String.valueOf(cardId).substring(0, 1);
				if ( (firstChar.equals("8") || firstChar.equals("9")) && member.getLEVEL_ID() >= 2 && !flag) {
					isInArea = true;
				}
			} else {
				Message.setMessage(request, "没有这种卡类型", "返回", null);
				forward = "message";
				return mapping.findForward(forward);
			}

			// 检测是否已换过新卡
			boolean flag2 = MemberGetAwardDAO.hasChangedNewCard(conn, pageData
					.getID());
			if (!flag2) { // 未换
				if (isInArea) {
					// 登陆人
					HttpSession session = request.getSession();
					User user = new User();
					user = (User) session.getAttribute("user");

					MemberAWARD award = new MemberAWARD();
					award.setMember_ID(pageData.getID());
					//award.setItem_ID(itemId);
					award.setOperator_id(Integer.parseInt(user.getId()));
					award.setLastDate(DateUtil.date2String(DateUtil.addDay(
							new java.util.Date(), 3600), "yyyy-MM-dd"));
					award.setDescription("换新卡活动");
					MemberGetAwardDAO.insertNewCard(conn, award);
				} else {
					Message.setMessage(request, "您的会员号不再换卡区间内", "返回", null);
					forward = "message";
				}

			} else {
				Message.setMessage(request, "您已经换过新卡了", "返回", null);
				forward = "message";
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			Message.setMessage(request, "出错了!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(forward);
	}
}

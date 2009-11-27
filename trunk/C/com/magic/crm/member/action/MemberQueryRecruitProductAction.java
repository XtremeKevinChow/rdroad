package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.Collection;
import com.magic.crm.util.DBManager;
import com.magic.crm.promotion.form.Recruit_ActivityForm;
import com.magic.crm.promotion.entity.Recruit_Activity;
import com.magic.crm.promotion.dao.Recruit_ActivityDAO;
import com.magic.crm.member.entity.MemberSessionRecruitGifts;

/**
 * 查询所有的新会员招募的礼品区（包括礼品）
 * 
 * @author user
 * 
 */
public class MemberQueryRecruitProductAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Recruit_ActivityForm pageForm = (Recruit_ActivityForm) form;
		String mscCode = request.getParameter("msc_code");
		pageForm.setMsc_Code(mscCode);
		MemberSessionRecruitGifts sessionRecruit = null;
		Recruit_Activity recruit = null;
		String nextUrl = "success";
		Connection conn = null;
		try {
			
			//if (request.getSession(true).getAttribute("RECRUIT ACTIVITY") == null) {
				conn = DBManager.getConnection();
				recruit = Recruit_ActivityDAO.findRecruitByMsc(conn, pageForm);
				if (recruit != null) {
					Collection sectionColl = Recruit_ActivityDAO
							.findAllRecruitSections(conn, pageForm);
					recruit.setSectionsList(sectionColl);
				}
				sessionRecruit = new MemberSessionRecruitGifts();
				sessionRecruit.setAllRecruitGifts(recruit);
				request.getSession(true).setAttribute("RECRUIT ACTIVITY", sessionRecruit);
			//} else { 
				//sessionRecruit = (MemberSessionRecruitGifts)request.getSession(true).getAttribute("RECRUIT ACTIVITY");
			//}

			request.setAttribute("recruit", sessionRecruit); // 整个招募活动

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(nextUrl);
	}

}

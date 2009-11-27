package com.magic.crm.member.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.entity.MemberSessionRecruitGifts;
import com.magic.crm.promotion.form.Recruit_ActivityForm;

public class MemberClearRecruitProductAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MemberSessionRecruitGifts sessionRecruit = null;
		String nextUrl = "success";
		
		try {
			
			sessionRecruit = (MemberSessionRecruitGifts)request.getSession(true).getAttribute("RECRUIT ACTIVITY");
			sessionRecruit.clearSeletedGifts();
			sessionRecruit.resetAllGiftsStatus();
			
			request.getSession(true).setAttribute("RECRUIT ACTIVITY", sessionRecruit);
			request.setAttribute("recruit", sessionRecruit); // 整个招募活动
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		return mapping.findForward(nextUrl);
	}
}

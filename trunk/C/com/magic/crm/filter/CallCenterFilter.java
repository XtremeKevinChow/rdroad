package com.magic.crm.filter;

import com.magic.crm.member.entity.Member;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import javax.servlet.http.HttpServletRequest;

public class CallCenterFilter {
	
	/**
	 * 得到当前活动的会员
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Member getCurrentMemberId(HttpServletRequest request) throws Exception {
		String iscallcenter = request.getParameter("iscallcenter");
		
		if (iscallcenter != null && iscallcenter.equals("1")) { //呼叫中心接入
			CallCenterHander hander = new CallCenterHander(request.getSession());
			if (hander.isOnService()) { //有会员被服务
				
				return hander.getServicedMember();
			} else { //没有会员服务
				ControlledError ctlErr = new ControlledError();
				ctlErr.setErrorTitle("操作错误");
				ctlErr.setErrorBody("没有服务对象，请接入服务");
				request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,
						ctlErr);
				return null;
			}
		} else { //没有接入
			return new Member();
		}
		
	}
}

package com.magic.crm.filter;

import com.magic.crm.member.entity.Member;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import javax.servlet.http.HttpServletRequest;

public class CallCenterFilter {
	
	/**
	 * �õ���ǰ��Ļ�Ա
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Member getCurrentMemberId(HttpServletRequest request) throws Exception {
		String iscallcenter = request.getParameter("iscallcenter");
		
		if (iscallcenter != null && iscallcenter.equals("1")) { //�������Ľ���
			CallCenterHander hander = new CallCenterHander(request.getSession());
			if (hander.isOnService()) { //�л�Ա������
				
				return hander.getServicedMember();
			} else { //û�л�Ա����
				ControlledError ctlErr = new ControlledError();
				ctlErr.setErrorTitle("��������");
				ctlErr.setErrorBody("û�з��������������");
				request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,
						ctlErr);
				return null;
			}
		} else { //û�н���
			return new Member();
		}
		
	}
}

package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.pager.Pager;

import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.MbrGetAwardForm;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

public class ConsoleAwardAction extends Action {
	/**
	 * 查询已经兑换的礼品暂存表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MbrGetAwardForm data = (MbrGetAwardForm) form;
		Connection conn = null;
		Collection coll = null;
		try {
			conn = DBManager.getConnection();
			//如果是post提交，继续判断是否是通过ToolBar提交过来，如果是则从session中取得会员号
			String iscallcenter = request.getParameter("iscallcenter");
			
			 if (iscallcenter != null && iscallcenter.equals("1")) {
	            CallCenterHander hander = new CallCenterHander(request.getSession());
	            if (hander.isOnService()) {
	                Member mb = hander.getServicedMember();
	                data.setMemberID(mb.getID());
	                data.setCardID(mb.getCARD_ID());
	                MemberGetAwardDAO ptDao = new MemberGetAwardDAO();
	                int size = ptDao.countMemberAward(conn, mb.getID());
	    			Pager page = new Pager(data.getOffset(), size);//生成page对象
	                page.setLength(10);
	                data.setPager(page);
	    			coll = ptDao.queryMemberConsoleAward(conn, mb.getID(), data);

	    			request.setAttribute("list", coll);
	            } else {
	                ControlledError ctlErr = new ControlledError();
	                ctlErr.setErrorTitle("操作错误");
	                ctlErr.setErrorBody("没有服务对象，请<a href='/member/query.do?service=1'>接入服务</a>");
	                request.setAttribute(Constants.ERROR_KEY, ctlErr);
	                return mapping.findForward("controlledError");

	            }
	        }
			
			return mapping.findForward("success");
		} catch (Exception e) {
			
			Message.setMessage(request, "查询出错");
			throw e;

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		
	}

}

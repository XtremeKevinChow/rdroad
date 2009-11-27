package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.pager.Pager;
import com.magic.crm.member.dao.MbrGetAwardDAO2;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.ExpExchangeHisForm;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message; 
public class ConsoleExpAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExpExchangeHisForm data = (ExpExchangeHisForm) form;
		
		Connection conn = DBManager.getConnection();
		Collection ret = null;
		try {
			
			
			    /** 如果是callcenter系统，从sessin中取出会员信息，如果没有提示登录服务对象 **/
		        String iscallcenter = request.getParameter("iscallcenter");
		        if (iscallcenter != null && iscallcenter.equals("1")) {
		            CallCenterHander hander = new CallCenterHander(request.getSession());
		            if (hander.isOnService()) {
		                Member mb = hander.getServicedMember();
		                data.setMemberID(mb.getID());
		                //1 查询信息
		                MbrGetAwardDAO2 ptDao = new MbrGetAwardDAO2();
		                int size = ptDao.getRsTotalCount(conn, data);//计算记录总数
		                Pager page = new Pager(data.getOffset(), size);//生成page对象
		                page.setOffset(data.getOffset());//设置当前位置
		                page.setLength(10);////////////////////
		                data.setPager(page);
						ret = ptDao.getHisList(conn, data);
						request.setAttribute("list", ret);
		            } else {
		                ControlledError ctlErr = new ControlledError();
		                ctlErr.setErrorTitle("操作错误");
		                ctlErr.setErrorBody("没有服务对象，请接入服务");
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

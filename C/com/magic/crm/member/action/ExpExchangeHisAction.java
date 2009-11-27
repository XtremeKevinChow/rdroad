/*
 * Created on 2006-2-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.Collection;
import com.magic.crm.common.pager.Pager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.member.dao.MbrGetAwardDAO2;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.ExpExchangeHisForm;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExpExchangeHisAction extends DispatchAction {
	
	private static Logger log = Logger.getLogger("ExpExchangeHisAction.class");
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
		ExpExchangeHisForm data = (ExpExchangeHisForm) form;
		MbrGetAwardDAO2 ptDao = new MbrGetAwardDAO2();
		Connection conn = DBManager.getConnection();
		Collection ret = null;
		try {
			
			/*if( request.getMethod().equals("POST") ) {*/
			    /** 如果是callcenter系统，从sessin中取出会员信息，如果没有提示登录服务对象 **/
		        String iscallcenter = request.getParameter("iscallcenter");
		        if (iscallcenter != null && iscallcenter.equals("1")) {
		            CallCenterHander hander = new CallCenterHander(request.getSession());
		            if (hander.isOnService()) {
		                Member mb = hander.getServicedMember();
		                data.setMemberID(mb.getID());
		                data.setCardID(mb.getCARD_ID());
		                //1 查询信息
		                int size = ptDao.getRsTotalCount(conn, data);//计算记录总数
		                Pager page = new Pager(data.getOffset(), size);//生成page对象
		                page.setOffset(data.getOffset());//设置当前位置
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
		       
		            
		            
		        } else {
		            //1 查询信息
		            int size = ptDao.getRsTotalCount(conn, data);
	                Pager page = new Pager(data.getOffset(), size);
	                page.setOffset(data.getOffset());
	                data.setPager(page);
					ret = ptDao.getHisList(conn, data);
					request.setAttribute("list", ret);

		        }
			//} 
			/*else {
			    int size = ptDao.getRsTotalCount(conn, data);
                String url = request.getContextPath() + "/member"+mapping.getPath() + ".do?type=query&memberID="+data.getMemberID();
                Pager page = new Pager(data.getPager().getOffset(), size, url);
                data.setPager(page);
				ret = ptDao.getHisList(conn, data);
				request.setAttribute("list", ret);

			}*/
			
            return mapping.findForward("query");
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

	}
}

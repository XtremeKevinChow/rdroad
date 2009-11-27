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
	 * ��ѯ���м�¼
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
			    /** �����callcenterϵͳ����sessin��ȡ����Ա��Ϣ�����û����ʾ��¼������� **/
		        String iscallcenter = request.getParameter("iscallcenter");
		        if (iscallcenter != null && iscallcenter.equals("1")) {
		            CallCenterHander hander = new CallCenterHander(request.getSession());
		            if (hander.isOnService()) {
		                Member mb = hander.getServicedMember();
		                data.setMemberID(mb.getID());
		                data.setCardID(mb.getCARD_ID());
		                //1 ��ѯ��Ϣ
		                int size = ptDao.getRsTotalCount(conn, data);//�����¼����
		                Pager page = new Pager(data.getOffset(), size);//����page����
		                page.setOffset(data.getOffset());//���õ�ǰλ��
		                data.setPager(page);
						ret = ptDao.getHisList(conn, data);
						request.setAttribute("list", ret);
		            } else {
		                ControlledError ctlErr = new ControlledError();
		                ctlErr.setErrorTitle("��������");
		                ctlErr.setErrorBody("û�з��������������");
		                request.setAttribute(Constants.ERROR_KEY, ctlErr);
		                return mapping.findForward("controlledError");

		            }
		       
		            
		            
		        } else {
		            //1 ��ѯ��Ϣ
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
			Message.setMessage(request, "��ѯ����");
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

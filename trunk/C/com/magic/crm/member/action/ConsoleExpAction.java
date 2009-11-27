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
			
			
			    /** �����callcenterϵͳ����sessin��ȡ����Ա��Ϣ�����û����ʾ��¼������� **/
		        String iscallcenter = request.getParameter("iscallcenter");
		        if (iscallcenter != null && iscallcenter.equals("1")) {
		            CallCenterHander hander = new CallCenterHander(request.getSession());
		            if (hander.isOnService()) {
		                Member mb = hander.getServicedMember();
		                data.setMemberID(mb.getID());
		                //1 ��ѯ��Ϣ
		                MbrGetAwardDAO2 ptDao = new MbrGetAwardDAO2();
		                int size = ptDao.getRsTotalCount(conn, data);//�����¼����
		                Pager page = new Pager(data.getOffset(), size);//����page����
		                page.setOffset(data.getOffset());//���õ�ǰλ��
		                page.setLength(10);////////////////////
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
		       
		            
		            
		        } 
			
            return mapping.findForward("success");
		} catch (Exception e) {
			
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

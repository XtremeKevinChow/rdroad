/**
 * StopCatalogAction.java
 * 2008-3-17
 * ����06:57:14
 * user
 * StopCatalogAction
 */
package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

import com.magic.crm.member.dao.MemberDAO;

/**
 * @author user
 *
 */
public class StopCatalogAction extends Action {
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		String nextUrl = "success";
		
		String[] memberIds = request.getParameterValues("memberId");
		//�Ƿ�ѡ���˼�¼
		if (memberIds == null || memberIds.length == 0) {
			Message.setErrorMsg(request,"��ѡ���¼!");
			nextUrl = "message";
			return mapping.findForward(nextUrl);
		}
		//��½��
		User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        
        Connection conn = null;
        try {
        	conn = DBManager.getConnection();
        	StringBuffer ids = new StringBuffer();
        	for (int i = 0; i < memberIds.length; i ++) {
        		ids.append(memberIds[i]);
        		ids.append(",");
        	}
        	ids.append("-1");
        	MemberDAO.stopSendCatalog(conn, ids.toString());
        	Message.setErrorMsg(request,"�����ɹ�!");
        } 
        catch (Exception e) {
			//conn.rollback();
        	Message.setErrorMsg(request,"���ݸ��³���!");
			nextUrl = "message";
			return mapping.findForward(nextUrl);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(nextUrl);
	}
}

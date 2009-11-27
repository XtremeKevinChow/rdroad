package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.shippingnotice.action.ShippingNoticeDeleteAction;
import com.magic.crm.shippingnotice.dao.ShippingNoticeDAO;
import com.magic.crm.shippingnotice.entity.ShippingNoticeMst;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;
/**
 * @author Administrator
 * �ͷ�ɾ����������action
 * ������״̬������0���½���
 * TODO 99read 
 */
public class ConsoleDeleteSnAction extends Action {
private static Logger log = Logger.getLogger(ShippingNoticeDeleteAction.class);
	
	public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			long sn_id = Long.parseLong(request.getParameter("sn_id"));
			
			//��ⷢ������״̬
			ShippingNoticeMst mst = ShippingNoticeDAO.getShippingNoticeByPK(conn, sn_id);
			if (mst.getStatus() <20 && mst.getStatus()>=0 ) { //
				int ret = ShippingNoticeDAO.deleteShippingNotice(conn,sn_id, Integer.parseInt(user.getId()));
				
				switch(ret) {
					case 0:
						Message.setMessage(request,"������ɾ���ɹ�!");
						break;
					case -12:
						Message.setMessage(request,"��������Ӧ�ķ������Ҳ���!");
						break;
					case -13:
						Message.setMessage(request,"������״̬����ɾ��!");
						break;
					case -14:
						Message.setMessage(request,"�������򲹻�������ɾ��!");
						break;
					default:
						Message.setMessage(request,"��������!");
						break;
				}
			} else { //�����½�״̬
				Message.setMessage(request,"�����������ں˻�֮ǰ����ɾ��!");
			}
			
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return mapping.findForward("success");
	}	
}

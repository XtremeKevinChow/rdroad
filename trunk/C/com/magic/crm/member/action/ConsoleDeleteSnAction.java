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
 * 客服删除发货单的action
 * 发货单状态必须是0（新建）
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
			
			//检测发货单的状态
			ShippingNoticeMst mst = ShippingNoticeDAO.getShippingNoticeByPK(conn, sn_id);
			if (mst.getStatus() <20 && mst.getStatus()>=0 ) { //
				int ret = ShippingNoticeDAO.deleteShippingNotice(conn,sn_id, Integer.parseInt(user.getId()));
				
				switch(ret) {
					case 0:
						Message.setMessage(request,"发货单删除成功!");
						break;
					case -12:
						Message.setMessage(request,"换货单对应的发货单找不到!");
						break;
					case -13:
						Message.setMessage(request,"发货单状态不能删除!");
						break;
					case -14:
						Message.setMessage(request,"换货单或补货单不能删除!");
						break;
					default:
						Message.setMessage(request,"其他错误!");
						break;
				}
			} else { //不是新建状态
				Message.setMessage(request,"发货单必须在核货之前才能删除!");
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

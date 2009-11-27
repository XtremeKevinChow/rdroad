/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.member.entity.MembeMoneyHistory;
import com.magic.crm.member.entity.Member;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberResetaddMoneyAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		HttpSession session=request.getSession();
		User user=new User();
		user = (User)session.getAttribute("user");
		Connection conn = null;
		CallableStatement cstmt = null; 
		MemberaddMoney memberaddMoney=new MemberaddMoney();
			/*
			 * 取到所有值复选框的值
			 */
		
		String inputid[]=request.getParameterValues("inputid");
		
		try{
			conn = DBManager.getConnection();
            for(int i=0;i<inputid.length;i++){
        		memberaddMoney.setID(Integer.parseInt(inputid[i]));
        		memberaddMoney.setMB_ID(0);
        		memberaddMoney.setORDER_ID(0);
        		memberaddMoney.setStatus(2);
        		memberaddMoney.setOPERATOR_ID(Integer.parseInt(user.getId()));
        		MemberaddMoneyDAO.update(conn,memberaddMoney);
            }


		 	return mapping.findForward("success");		
		
		} catch(SQLException se) {

		  	throw new ServletException(se);
	
		 } finally {
	
			 try {
	
				 conn.close();
	
			  } catch(SQLException sqe) {
	
				  throw new ServletException(sqe);
	
			  }
	
		 }
	}

}

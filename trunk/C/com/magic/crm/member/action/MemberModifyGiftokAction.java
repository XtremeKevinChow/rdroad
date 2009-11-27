/*
 * Created on 2005-1-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



import com.magic.crm.util.DBManager;

import com.magic.crm.member.form.MemberGIFTForm;
import com.magic.crm.member.dao.*;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.Constants;




/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public final class MemberModifyGiftokAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
	  			 Connection conn = null;			 
	  			 String condition="";
	  			MemberGIFTForm mf = (MemberGIFTForm) form;
	  			MemberDAO memberDAO = new MemberDAO();
	  			MemberGIFTDAO memberGIFTDAO = new MemberGIFTDAO();
	  			String gift_id=request.getParameter("gift_id");
	  			String recommended_id=request.getParameter("recommended_id");

	  			 try {	 	  			 
	  			 	 conn = DBManager.getConnection();
	  			 	 if(gift_id!=null&&gift_id.length()>0){
	  			 	 memberDAO.updateMemberAward(conn,gift_id,recommended_id);
	  			 	 }

	  			 	return mapping.findForward("success");
	  			 } catch(SQLException se) {
	  			  	throw new ServletException(se);
	  			 } finally {
	  				 try {
	  					 conn.close();

	  				  } catch(SQLException sqe) {


	  				  }

	  			 }

	  }

	}

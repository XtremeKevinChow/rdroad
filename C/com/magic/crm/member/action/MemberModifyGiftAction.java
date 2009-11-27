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
public final class MemberModifyGiftAction extends Action{
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
	  			String recommended_id=request.getParameter("recommended_id");
	  			boolean ischeck=true;
	  			 try {	 	  			 
	  			 	 conn = DBManager.getConnection();
	  			 	ischeck=memberDAO.checkMemberOrder(conn,recommended_id);
	  			 	if(ischeck){
	  		    	    ControlledError ctlErr = new ControlledError();	    		
		    		    ctlErr.setErrorTitle("操作错误提示");	    		
		    		    ctlErr.setErrorBody("这个会员已经下过单,并且订单有效,所以不能修改礼品<a href='#'  onclick='history.back();'>返回</a>");	    		
		    		    request.setAttribute(Constants.ERROR_KEY, ctlErr);
		    		    return mapping.findForward("controlledError");

		    		
	  			 	}  	
	    		    Collection allmemberGIFT=new ArrayList();
	    		    allmemberGIFT = memberGIFTDAO.QueryMemberGIFT(conn,"");
	    		    request.setAttribute("allmemberGIFT", allmemberGIFT);
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

package com.magic.crm.user.action;

import java.util.*;
import java.math.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.magic.crm.user.form.*;
import com.magic.crm.user.entity.*;
import com.magic.crm.user.dao.*;
import com.magic.crm.util.*;
//import com.sun.corba.se.internal.core.Response;


/**
 * <p>Title: FE China Credit System</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FE China</p>
 * @author Liao Yu Hong
 * @version 1.0
 */

public final class LogonAction
extends Action {

public ActionForward execute(ActionMapping mapping,
                           ActionForm form,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {

	
	System.out.println("============Enter LogonAction============");
	HttpSession session = request.getSession();
	
	LogonForm lf = (LogonForm) form;
	
	User user = new User();
	
	
	
	Connection conn = null;
	String tag=request.getParameter("tag");//1登陆CRM，2登陆坐席系统

	String inputPwd="";
	String logID=request.getParameter("logID");
	String telno=request.getParameter("telno");
	
	//System.out.println(telno);
	if(tag.equals("1")){
	user.setUSERID(lf.getUserName());
	inputPwd = lf.getPassword();
	}else{
		user.setUSERID("");	
	
		user.setLogID(Integer.parseInt(logID));
		user.setTelno(telno);	
		inputPwd=request.getParameter("password");
	}
	
	try {

		conn=DBManager.getConnection();
		
		
	  UserDAO userDao = new UserDAO();
	
	  user = userDao.findByName(conn, user);


	  if (user == null) {
	
	    ControlledError ctlErr = new ControlledError();
	    ctlErr.setErrorTitle("登录错误");
	    ctlErr.setErrorBody("输入的用户名不存在!<a href='#'  onclick='history.back();'>返回</a>");
	    request.setAttribute(Constants.ERROR_KEY, ctlErr);
	    return mapping.findForward("controlledError");
	    
		//Message.setErrorMsg(request, "用户名不存在");  
		//return mapping.findForward("error");  
	
	  }
	
	  //user typed password , needs to be checked
	
	  
	
	  //real password
	
	  String realPwd = user.getPWD();
      MD5 m= new MD5();
      inputPwd=m.getMD5ofStr(inputPwd);	 
    
	  //System.out.println("realPwd is "+realPwd);
	
	  if (inputPwd != null && !inputPwd.equals(realPwd)) {
	
	    ControlledError ctlErr = new ControlledError();
	
	    ctlErr.setErrorTitle("密码错误");
	
	    ctlErr.setErrorBody("输入的密码有误!<a href='#'  onclick='history.back();'>返回</a>");
	
	    request.setAttribute(Constants.ERROR_KEY, ctlErr);
	    System.out.println("logon fail") ;
	    return mapping.findForward("controlledError");
	
	  }
		String servletPath = "";
		servletPath = ((HttpServletRequest) request).getServletPath();

	  BitSet rights = new BitSet();
	
	  UserRoleDAO userRoleDao = new UserRoleDAO();
	
	  RoleRightDAO roleRightDao = new RoleRightDAO();
	
	  Collection roleCol = userRoleDao.find(conn, user);
	
	  if (roleCol != null) {
	
	    Iterator i = roleCol.iterator();
	
	    while (i.hasNext()) {
	
	      UserRole ur = (UserRole) i.next();
	
	      Role role = new Role();
	
	      role.setRoleID(ur.getRoleId());
	
	      Collection rightCol = roleRightDao.find(conn, role);
	
	      if (rightCol != null) {
	
	        Iterator ri = rightCol.iterator();
	
	        while (ri.hasNext()) {
	
	          RoleRight rr = (RoleRight) ri.next();
	
	          BigDecimal rightId = rr.getRightId();
	
	          rights.set(rightId.intValue());
	
	        }
	      }
	
	    }
	
	  }
	 
	  user.setAccessRight(rights);
	  
	  //add by user 加入字段权限控制
	  //Map map = FieldViewRightDAO.getFieldRightMap(conn, Integer.parseInt(user.getId()));
	  //user.setFieldRights(map);
	  
	  session.setAttribute(Constants.USER_KEY, user);
	  System.out.println("logon success") ;
	  

      String redirect="";
        if(tag.equals("1")){
        	  //session.setAttribute("login_path", "login.jsp");
        	          	
        	redirect="index.jsp";
      
        }else{
      	  //session.setAttribute("login_path", "callcenter_login.jsp");
      	
        	redirect="index.jsp?isquery=0&LogID="+logID+"&telno="+telno;
        }
        response.sendRedirect(redirect);
		//return mapping.findForward("success");
        return null;
	
	}
	catch (SQLException se) {
		System.out.println(" no connection");
		throw new ServletException(se);
	  
	
	}
	finally {
		
	  try {
	
	    conn.close();
	
	  }
	  catch (SQLException sqe) {
	  	System.out.println("no connection connectting");
	    throw new ServletException(sqe);
	
	  }
	
	}

	}


}
package com.magic.crm.user.action;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.math.*;
import java.sql.*;
import javax.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class LogoutAction extends Action{

  public ActionForward execute(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
               throws Exception{

        HttpSession session = request.getSession();
        session.invalidate();
        String LogID=request.getParameter("LogID");
        LogID=(LogID==null)?"":LogID;

        
        if(LogID.length()>0){
        	response.sendRedirect("callcenter_login.jsp");
        }else{
        	response.sendRedirect("login.jsp");
        }
       // return mapping.findForward("success");
       
        
        return null;

  }
}











/*
 * Created on 2005-3-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.user.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PathAddAction extends Action {


	  public ActionForward execute(ActionMapping mapping,
	  														 ActionForm form,
	  														 HttpServletRequest request,
	  														 HttpServletResponse response)
	  			 throws Exception{




	  			 	 return mapping.findForward("success");


	  }
}

/*
 * Created on 2005-6-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.magic.crm.util.DBManager;
import com.magic.crm.member.dao.*;

import java.util.Collection;
import java.util.ArrayList;
import com.magic.crm.member.form.*;
import com.magic.crm.common.CommonPageUtil;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberOrgListinitAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{

		CommonPageUtil pageModel = new CommonPageUtil();
			Collection memberevent=new ArrayList();
			request.setAttribute("memberPageModel", pageModel);

		 	return mapping.findForward("success");		

	}

}

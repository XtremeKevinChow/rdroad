/*
 * Created on 2005-2-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.user.dao.S_AREADao;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.DateUtil;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAddTokenAction extends Action{
	
    public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws Exception {
    	
    	MemberForm fm = (MemberForm) form;
    	Connection conn = DBManager.getConnection();
    	try {
    		User user= (User)request.getSession().getAttribute("user");
    		
    		ArrayList provs = S_AREADao.listProvince(conn);
    		fm.setProvs(provs);
    		fm.setCreator_name(user.getUSERID());
    		fm.setGENDER("F");
    		fm.setEvent_date(DateUtil.date2String(new Date(), "yyyy-MM-dd"));
    		
    	} finally {
    		try {conn.close();} catch(Exception e) {}
    	}
    	
    	
   	     return mapping.findForward("success");
    }
}

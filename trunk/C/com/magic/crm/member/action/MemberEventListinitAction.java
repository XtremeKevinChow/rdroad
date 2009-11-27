/*
 * Created on 2005-5-18
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
import java.util.HashMap;

import com.magic.crm.member.form.*;
import com.magic.crm.common.CommonPageUtil;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberEventListinitAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		MemberForm mf=(MemberForm)form;
		CommonPageUtil pageModel = new CommonPageUtil();
		Connection conn = null;
		try{
			
			conn=DBManager.getConnection();
			mf.setTypeList(MemberEventsDAO.getEventTypeList(conn));
			
			/** add by user 2006-06-01 10:05 **/
			if(mf.getCARD_ID() != null && !mf.getCARD_ID().equals("")) {
			    System.out.println();
			    MemberEventsDAO memberEventsDAO=new MemberEventsDAO();
			    HashMap map = new HashMap();
			    map.put("card_id", mf.getCARD_ID());
			    map.put("name", "");
			    map.put("event_type", "");
			    map.put("begin_date", "");
			    map.put("end_date", "");
			    pageModel.setCondition(map);
			    memberEventsDAO.QueryMemberEvents(conn,pageModel);
			}
			
			
			request.setAttribute("memberPageModel", pageModel);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				 try {
 					 conn.close();
 				  } catch(Exception ex) {
 				  }
		}
		 	return mapping.findForward("success");		

	}

}

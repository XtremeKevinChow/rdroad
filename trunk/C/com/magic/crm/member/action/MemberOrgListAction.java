/*
 * Created on 2005-6-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.*;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.order.action.GroupOrderAddAction;
import com.magic.crm.util.DBManager;
import java.util.Collection;
import com.magic.crm.common.CommonPageUtil;
import java.util.HashMap;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberOrgListAction extends Action{

	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		MemberForm mf=(MemberForm)form;
		String isquery=request.getParameter("isquery");
		Connection conn = null;
		MemberDAO memberDAO=new MemberDAO();
		CommonPageUtil pageModel = new CommonPageUtil();
		
		String card_id=request.getParameter("CARD_ID");
		String name=request.getParameter("NAME");
		if(card_id==null){
			card_id="";
		}
		if(name==null){
			name="";
		}
		
		String condition="";

		String s_pageNum = request.getParameter("pageNo");
		int pageNum = 1;
		if (s_pageNum != null && !"".equals(s_pageNum)) {
			pageNum = Integer.parseInt(s_pageNum);
		}
		pageModel.setPageNo(pageNum);
		HashMap hashmap = new HashMap();
		hashmap.put("card_id", card_id);
		hashmap.put("name", name);

	
		pageModel.setCondition(hashmap);

		try{
			conn=DBManager.getConnection();
			mf.setTypeList(MemberEventsDAO.getEventTypeList(conn));
				
			memberDAO.QueryOrgMember(conn,pageModel);	
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

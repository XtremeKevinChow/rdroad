/*
 * Created on 2005-11-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.util.*;
import java.sql.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.magic.crm.util.DBManager;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.dao.MemberDAO;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class MemberQueryByAddressAction extends Action {
	public MemberQueryByAddressAction() {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		Collection memberList = null;
		Collection mbinfo = new ArrayList();
		Connection conn = null;
		
		MemberForm mf = (MemberForm)form;
		MemberDAO memberDAO = new MemberDAO();
		Member member = new Member();
		
		String rowId = request.getParameter("rowId");
		request.setAttribute("rowId", rowId);
		String postCode = request.getParameter("postcode");
		String id = request.getParameter("id");
	
		
		
		if (postCode != null && postCode.length() >= 4) {
		    postCode = postCode.substring(0, 4);
		}
		String name = request.getParameter("NAME").trim();
		String subName = name;
		if (name != null && !"".equals(name)) {
			for (int i = 0; i < name.length(); i ++) {
				char c = name.charAt(i);
				if ( c <= 128 || c == '＋' || c == '　' || c == '［' || c == '（' || c == '＊' || c == '｛' ) { // 中文字的ASCII码大于128
					subName = name.substring(0, i);
					break;
				}
			}
			
			if ("".equals(subName)) {
				for (int i = name.length() - 1; i >= 0; i --) {
					char c = name.charAt(i);
					if (c <= 128 || c == '＋' || c == '　' || c == '［' || c == '（' || c == '＊' || c == '｛' ) {
						subName = name.substring(i + 1);
						break;
					}
					
				}
			}
		}
		
		member.setPostcode(postCode);
		member.setNAME("".equals(subName) ? "[=NULL=]" : subName);
		mf.setNAME(subName);
		mf.setPostcode(postCode);



		try {
			conn = DBManager.getConnection();
			memberList = memberDAO.QueryMembers(conn,member);
			request.setAttribute("memberList", memberList);
		    mbinfo = memberDAO.QueryMemberRefid(conn,id);
		    request.setAttribute("mbinfo", mbinfo);
			

				
		} catch (Exception ex) {
			System.out.println("error");
		}finally {
			try {
				conn.close();

			} catch (SQLException sqe) {

			}
		}
		
		
		return mapping.findForward("success");
	}
}
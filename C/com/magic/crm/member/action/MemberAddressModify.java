/*
 * Created on 2005-2-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

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

import com.magic.crm.member.dao.MemberAddressDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.entity.*;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.ChangeCoding;
/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAddressModify extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		Connection conn = null;
		//会员ID
		String id=request.getParameter("id");
		//type=3、初始化增加会员送货地址
		//type=4、修改会员送货地址
		String type=request.getParameter("type");
		//地址ID
		String postcode=request.getParameter("Postcode");

	 	if(postcode.trim().length()!=6){
    	    ControlledError ctlErr = new ControlledError();	    		
		    ctlErr.setErrorTitle("操作错误提示");	    		
		    ctlErr.setErrorBody("你输入的邮编不是6位数，请重新输入!<a href='#'  onclick='history.back();'>返回</a>");	    		
		    request.setAttribute(Constants.ERROR_KEY, ctlErr);
		    return mapping.findForward("controlledError");
		    //return mapping.findForward("error");
	 	}			
		Member member=new Member();
		MemberDAO memberDAO = new MemberDAO();
		MemberAddresses memberAddresses=new MemberAddresses();
		try{
			conn = DBManager.getConnection();

		 //memberAddresses
		//更改会员基本信息
		 	MemberAddressDAO memberAddDAO=new MemberAddressDAO();		 	
		 			
		 	if(type.equals("4")){ //修改送货地址
		 		memberAddresses.setMember_ID(Integer.parseInt(request.getParameter("id")));
		 		memberAddresses.setID(Integer.parseInt(request.getParameter("address_id")));
		 		memberAddresses.setPostcode(postcode);
		 		memberAddresses.setDelivery_address(ChangeCoding.unescape(ChangeCoding.toUtf8String(request.getParameter("Delivery_address"))) );
		 		memberAddresses.setRelation_person(ChangeCoding.unescape(ChangeCoding.toUtf8String(request.getParameter("Relation_person"))));
		 		memberAddresses.setTelephone(ChangeCoding.unescape(ChangeCoding.toUtf8String(request.getParameter("Telephone"))));
		 		memberAddresses.setTelephone2(ChangeCoding.unescape(ChangeCoding.toUtf8String(request.getParameter("telephone2"))));// add by user 2008-03-27
		 		memberAddresses.setSection(request.getParameter("section"));
		 		memberAddDAO.update(conn,memberAddresses);
		 	}
		 	if(type.equals("5")){//新增送货地址
		 		memberAddresses.setMember_ID(Integer.parseInt(request.getParameter("id")));
		 		memberAddresses.setPostcode(postcode);
		 		memberAddresses.setDelivery_address(ChangeCoding.unescape(ChangeCoding.toUtf8String(request.getParameter("Delivery_address"))) );
		 		memberAddresses.setRelation_person(ChangeCoding.unescape(ChangeCoding.toUtf8String(request.getParameter("Relation_person"))));
		 		memberAddresses.setTelephone(ChangeCoding.unescape(ChangeCoding.toUtf8String(request.getParameter("Telephone"))));
		 		memberAddresses.setTelephone2(ChangeCoding.unescape(ChangeCoding.toUtf8String(request.getParameter("telephone2"))));// add by user 2008-03-27
		 		memberAddresses.setSection(request.getParameter("section"));
		 		memberAddDAO.insertAddress(conn,memberAddresses);
		 	}		
			member =memberDAO.DetailMembers(conn,id);
			request.setAttribute("member",member);//会员信息		 	
		 	Collection memberAddCol=memberAddDAO.QueryMemberAddresses(conn," and member_id="+id);		
		 	request.setAttribute("memberAddCol",memberAddCol);//地址簿列表		 	
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

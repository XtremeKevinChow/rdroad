/*
 * Created on 2005-3-17
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
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.DateUtil;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberMSCokAction extends Action{
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
throws Exception{
		Connection conn = null;
        User user= new User();
        HttpSession session = request.getSession();
        user = (User)session.getAttribute("user"); 		
		//会员ID
		String id=request.getParameter("id");
		String type=request.getParameter("type");
		String msc_code=request.getParameter("MSC_CODE");
		String card_id=request.getParameter("card_id");//会员号
		Member member=new Member();
		MemberDAO memberDAO = new MemberDAO();
		MemberAWARD memberAWARD=new MemberAWARD();
	    String condition="";
	    MemberGetAwardDAO memberAWARDDAO=new MemberGetAwardDAO();
		try{
			conn = DBManager.getConnection();
			/**
			 * commented by user 2008-03-17
			 * 修改msc号功能由类SupplyRecruitGiftsAction来完成，此处废弃
			 */
		 	if(type.equals("msc_code")){
		 		condition=" set msc_code='"+msc_code+"' where id="+id;	
		 	}
		 	if(type.equals("card_type")){
			 	condition=" set card_type="+msc_code+" where id="+id;	
			 	/*
			 	 * 当礼品表status==0修改礼品表的卡类型
			 	 * 当礼品表status==1新增礼品表的卡类型
			 	 */
  			 	if(msc_code.equals("0")){
  			 		//memberAWARD.setItem_ID(100000);
  			 		memberAWARD.setPrice(2);
  			 	}
  			 	if(msc_code.equals("1")){
  			 		//memberAWARD.setItem_ID(100002);
  			 		memberAWARD.setPrice(6);
  			 	}
			 	if(MemberGetAwardDAO.getAwardSatus(conn,id)){
			 		//memberAWARDDAO.updateStatus(conn,memberAWARD.getPrice(),memberAWARD.getItem_ID(),Integer.parseInt(id));
			 	}else{			 
			 		memberAWARD.setMember_ID(Integer.parseInt(id));
			 		memberAWARD.setOperator_id(Integer.parseInt(user.getId()));
			 		memberAWARD.setLastDate(DateUtil.date2String(DateUtil.addDay(new java.util.Date(), 3600),"yyyy-MM-dd" ) );
			 		memberAWARDDAO.insert(conn,memberAWARD);
			 	}			 	
			}
		 	memberDAO.updateMSC(conn,condition);
			member =memberDAO.DetailMembers(conn,id);
		 	

			request.setAttribute("member",member);	
			
			/*
			 * 显示购买记录
			 */
			/**
			 * commented by user 2008-03-17
			 * 取出多余的代码
			 */
			/* String condition1="'"+card_id+"'";
		 	 Collection memberBuy=memberDAO.getMemberBuy(conn,condition1);
		 	request.setAttribute("memberBuy",memberBuy);*/		 	
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

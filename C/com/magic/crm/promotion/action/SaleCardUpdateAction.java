/*
 * Created on 2005-9-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.promotion.dao.Crush_Card_MstDAO;
import com.magic.crm.promotion.entity.Crush_Card_MST;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.MD5;
import com.magic.crm.util.Message;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SaleCardUpdateAction extends Action{

	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
        User user= new User();
        HttpSession session = request.getSession();
        user = (User)session.getAttribute("user");		
		String card_num=request.getParameter("card_num");
		String tag=request.getParameter("tag");
		String card_id="";
        Connection conn = null;
        MemberDAO mbrDAO=new MemberDAO();
        Crush_Card_MstDAO ccmDAO=new Crush_Card_MstDAO();
        Crush_Card_MST ccm=new Crush_Card_MST(); 
        double in_money=0;
	    try{
	    	
	     	conn = DBManager.getConnection();
	     	
	     	ccm.setCard_num(card_num);
	     	conn.setAutoCommit(false);
	     	if(tag.equals("1")){

	     		ccm.setSale_person(Integer.parseInt(request.getParameter("sale_person")));
	     		ccm.setCrush_person(Integer.parseInt(user.getId()));
	     		ccmDAO.update(conn,ccm);
	   			Message.setErrorMsg(request,"销售已成功！");
    			return mapping.findForward("error");
	     	}else{
	    		String pass=request.getParameter("pass_num").trim();	     		
	     		card_id=request.getParameter("card_id");
	     		//ccm.setPass(pass);
	     		
	     		ccm = ccmDAO.getCardInfo(conn,card_num);
	     		ccm.setCrush_person(Integer.parseInt(user.getId()));
	     		MD5 m= new MD5();
	     		
	     		if(!mbrDAO.checkMBCardIDSEQ(conn,card_id)){
	    			Message.setErrorMsg(request,"会员号不存在，不能充值！");
	    			return mapping.findForward("error");	     		    
	     		}
	     		
	     		/**
	     		 * remarked by qbzhou 2006-03-15
	     		 * 去掉没有必要的数据库访问
	     		 */
	     		/*
	     		if(ccmDAO.checkCardNum(conn,card_num)==0){
	    			Message.setErrorMsg(request,"销售卡号不存在，不能充值！");
	    			return mapping.findForward("error");	     		    
	     		}	     		
	     		if(ccmDAO.getCardInfo(conn,card_num).getStatus().equals("1")){
	    			Message.setErrorMsg(request,"销售卡还未销售，不能充值！");
	    			return mapping.findForward("error");	     		    
	     		}
	     		if(ccmDAO.getCardInfo(conn,card_num).getStatus().equals("3")){
	    			Message.setErrorMsg(request,"销售卡已被使用，不能充值");
	    			return mapping.findForward("error");	     		    
	     		}	  
	     		if(ccmDAO.getCardInfo(conn,card_num).getStatus().equals("4")){
	    			Message.setErrorMsg(request,"销售卡已作废，不能充值");
	    			return mapping.findForward("error");	     		    
	     		}		     		
	     		if(ccmDAO.checkPass(conn,ccm)==0){
	    			Message.setErrorMsg(request,"销售卡密码错误，不能充值！");
	    			return mapping.findForward("error");	           
	     		}
	     		*/
	     		/**
	     		 * add by qbzhou 2006-03-15
	     		 */
 	     		if(ccm.getCard_num()==null){
 	     			Message.setErrorMsg(request,"销售卡号不存在，不能充值！");
	    			return mapping.findForward("error");	
 	     		}
 	     		if(ccm.getStatus().equals("1")){
 	     			Message.setErrorMsg(request,"销售卡还未销售，不能充值！");
	    			return mapping.findForward("error");	
 	     		}
 	     		if(ccm.getStatus().equals("3")){
 	     			Message.setErrorMsg(request,"销售卡已被使用，不能充值");
	    			return mapping.findForward("error");	     
 	     		}
 	     		if(ccm.getStatus().equals("4")){
 	     			Message.setErrorMsg(request,"销售卡已作废，不能充值");
	    			return mapping.findForward("error");	
 	     		}
 	     		if(!m.getMD5ofStr(pass).toLowerCase().equals(ccm.getPass())){
 	     			Message.setErrorMsg(request,"销售卡密码错误，不能充值！");
	    			return mapping.findForward("error");	 
 	     		}
	     		
 	     		
	     		ccmDAO.updateDeposit(conn,ccm,card_id);
	     		
//	     		if(ccm.getCard_type().equals("1")){
//	     		    in_money=100;
//	     		}	     		
//	     		if(ccm.getCard_type().equals("2")){
//	     		    in_money=200;
//	     		}
//	     		if(ccm.getCard_type().equals("5")){
//	     		    in_money=500;
//	     		}
	     		int memberId = MemberDAO.getMemberID(conn, card_id);
	     		conn.commit();
	   			Message.setMessage(request,"充值已成功:本次充值金额: "+ccm.getMoney()+" 元，你充值后的帐户余额为: "+mbrDAO.getMemberInfo(conn,card_id).getDEPOSIT()+" 元！", "返回", "./member/memberDetail.do?id="+memberId);
    			return mapping.findForward("error");
    			
	     	}
	     	

	     	
	   	  }catch(SQLException se){
	   	  	conn.rollback();
	   	    throw new ServletException(se);
	   	  }finally{
				 try {

					 conn.close();

				  } catch(SQLException sqe) {

				  }		   	  	
	   	  }		
		
	}
}

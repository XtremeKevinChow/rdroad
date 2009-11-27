/*
 * Created on 2005-2-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.member.dao.*;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.user.dao.S_AREADao;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Constants;
import com.magic.crm.util.Message;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberDetailAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Connection conn = null;
        String recommended_id = null;
        String member_id = null;
        MemberForm fm = (MemberForm) form;
        try {
        	
            Member member = new Member();
            PropertyUtils.copyProperties(member, form);
            conn = DBManager.getConnection();
            MemberDAO memberDAO = new MemberDAO();
            String id = request.getParameter("id");
            String s_card_id = request.getParameter("s_card_id");
            recommended_id = request.getParameter("recommended_id");
           
            
            if (recommended_id == null) {
                recommended_id = "2";
            }
            
            member_id = memberDAO.MBR_RECOMMENDED_ID(conn, id);
            
            /* 如果是callcenter系统，从sessin中取出会员信息，如果没有提示登录服务对象 **/
	        String iscallcenter = request.getParameter("iscallcenter");
	        CallCenterHander hander = new CallCenterHander(request.getSession());
	        if (iscallcenter != null && iscallcenter.equals("1")) {
	        	if (hander.isOnService()) {
	                Member mb = hander.getServicedMember();
	                id = String.valueOf(mb.getID());
	                s_card_id = mb.getCARD_ID();
	            } else {
	                ControlledError ctlErr = new ControlledError();
	                ctlErr.setErrorTitle("操作错误");
	                ctlErr.setErrorBody("没有服务对象，请接入服务");
	                request.setAttribute(Constants.ERROR_KEY, ctlErr);
	                return mapping.findForward("controlledError");
	            }
	        } else if ("2".equals(iscallcenter)) {
	        	Member mb = hander.getPreServicedMember();
	        	if(mb == null) {
	        		ControlledError ctlErr = new ControlledError();
	                ctlErr.setErrorTitle("操作错误");
	                ctlErr.setErrorBody("当前没有上一个服务对象");
	                request.setAttribute(Constants.ERROR_KEY, ctlErr);
	                return mapping.findForward("controlledError");
	        	}
	        	id = String.valueOf(mb.getID());
	        	s_card_id = mb.getCARD_ID();
	        	
	        }
            
            if (recommended_id.equals("1")) { //被推荐人信息
               
                member = memberDAO.DetailMembers(conn, member_id);
                if (member == null || member.getID() == 0) {
                	Message.setMessage(request, "没有对应的推荐人", "补推荐", "ajaxpage2('../member/memberGetMemberInitAdd.do?recommendedId=" + id + "&pageType=1','ajaxcontentarea',document.forms[0])");
	                return mapping.findForward("message");
                }
                /**
                 * add by user 2006-05-17 12:01 如果推荐人不存在就显示新增界面
                 */
                if (member.getID() == 0) { //
                    
                    request.setAttribute("recommendedId", id);
                    return mapping.findForward("recormmended_list");
                }
                request.setAttribute("member", member);
                return mapping.findForward("recormmended_member");
                
            } else { //会员详细信息
            	String orderAdd1 = request.getParameter("cardId");
                if (s_card_id != null) {
                    member = memberDAO.getCardInfo(conn, s_card_id);
                } 
                else if(orderAdd1 != null) { //菜单"新增订单"
                	member = memberDAO.getCardInfo(conn, orderAdd1);
            	}
            	else {
                    member = memberDAO.DetailMembers(conn, id);
                    
            		
                }
                if (member == null || member.getID() == 0) {
                	ControlledError ctlErr = new ControlledError();
	                ctlErr.setErrorTitle("提示");
	                ctlErr.setErrorBody("该会员不存在或无效");
	                request.setAttribute(Constants.ERROR_KEY, ctlErr);
	                return mapping.findForward("controlledError");
                }
                /*
                 * add by user 2006-05-31 14:02 将当前服务的会员信息载入session中，直到退出服务，结束会话
                 */
         
                Member mb2 = hander.getServicedMember();
				if(mb2!=null&& !mb2.getCARD_ID().equals(member.getCARD_ID())) {
					hander.setPreServiceMember(mb2);
				}
				hander.setServiceMember(member);
                
            }
            
            // 查看会员黑名单
            MemberBlackListDAO memberBlackListDAO = new MemberBlackListDAO();
            boolean isBlackList = memberBlackListDAO.isExistBlacklist(conn, member.getID());
            member.setBlacklistMember(isBlackList);
            if (isBlackList) {
            	member.setBlackRemark(memberBlackListDAO.getBlackRemark(conn, member.getID()));
            }
            // 查询会员未使用的礼券 
            member.setGift_num(MemberGetAwardDAO.getAvailableGiftNumber(conn, member.getID()));
            
            request.setAttribute("member", member);
            

            /*
             * 会员购买记录
             */
            //String condition = "'" + member.getCARD_ID() + "'";
            //Collection memberBuy = memberDAO.getMemberBuy(conn, condition);
            //request.setAttribute("memberBuy", memberBuy);
            request.setAttribute("DEFAULT_TAB", request.getParameter("tab"));
            
            return mapping.findForward("success");
        } catch (SQLException se) {
            se.printStackTrace();
            throw new ServletException(se);

        } finally {

            try {

                conn.close();

            } catch (SQLException sqe) {

                throw new ServletException(sqe);

            }

        }
    }
}

/*
 * Created on 2005-2-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import com.magic.crm.util.ChangeCoding;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.*;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberModifyAction extends Action {
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	MemberForm fm = (MemberForm) form;
        Connection conn = null;
        User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        Member member = new Member();
        MemberDAO memberDAO = new MemberDAO();
        MemberAddresses memberAdd = new MemberAddresses();
        MemberAddressDAO memberAddDAO = new MemberAddressDAO();
        MemberAWARD memberAWARD = new MemberAWARD();
        MemberGetAwardDAO memberAWARDDAO = new MemberGetAwardDAO();
        
        try {
            PropertyUtils.copyProperties(member, form);
            MemberForm mf = (MemberForm) form;
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            member.setID(mf.getID());
            /*
             * 更改会员基本信息
             */
            if (mf.getPostcode().trim().length() != 6) {
                ControlledError ctlErr = new ControlledError();
                ctlErr.setErrorTitle("操作错误提示");
                ctlErr
                        .setErrorBody("你输入的邮编不是6位数，请重新输入!<a href='#'  onclick='history.back();'>返回</a>");
                request.setAttribute(Constants.ERROR_KEY, ctlErr);
                return mapping.findForward("controlledError");
                //return mapping.findForward("error");
            }
            String email = memberDAO.getEmailById(conn, mf.getID());
            if ( mf.getEMAIL() != null && !mf.getEMAIL().equals(email) ) { //email有变动，判断提交过来的email是否被别的会员引用到
            	if (memberDAO.isEmailUsed(conn, member.getID(), mf.getEMAIL())) {
            		Message.setErrorMsg(request, "你输入的邮件地址可能被其他人使用了!"); //ajax的缘故消息不能显示
                    return mapping.findForward("success");
            	}
            }
            /*
             * 出生日期转换
             */
            String theday = mf.getBIRTHDAY();
            String birthday = theday.substring(0, 4) + "-"
                    + theday.substring(4, 6) + "-" + theday.substring(6, 8);
            //System.out.println(birthday);
            //String CATALOG_TYPE=request.getParameter("CATALOG_TYPE");
            member.setBIRTHDAY(birthday);
            member.setModifier_id(Integer.parseInt(user.getId()));
            String tag = request.getParameter("tag");
            tag = (tag == null) ? "" : tag;
            
            /* 更新会员基本信息 **/
            //页面提交过来的utf8格式信息解码
            member.setNAME(ChangeCoding.unescape(ChangeCoding.toUtf8String(member.getNAME())));
            member.setAddressDetail(ChangeCoding.unescape(ChangeCoding.toUtf8String(member.getAddressDetail())));
            member.setCOMMENTS(ChangeCoding.unescape(ChangeCoding.toUtf8String(member.getCOMMENTS())));
            member.setSection(fm.getSection());
            member.setTaobaoWangId(fm.getTaobaoWangId());
            memberDAO.updateDetail(conn, member);
            
            /* 更新俱乐部信息(先删除后插入) **/
            //memberDAO.deleteClub(conn, member);
            //memberDAO.insertClub(conn, member);
            
            /* 更新会员地址信息 **/
            memberAdd.setDelivery_address(member.getAddressDetail());
            memberAdd.setPostcode(member.getPostcode());
            memberAdd.setMember_ID(member.getID());
            memberAdd.setID(Integer.parseInt(member.getADDRESS_ID()));
            memberAdd.setRelation_person(member.getNAME());
            memberAdd.setTelephone(member.getTELEPHONE());
            memberAdd.setSection(fm.getSection());
            memberAddDAO.update(conn, memberAdd);
            request.setAttribute("member", memberDAO.DetailMembers(conn, String
                    .valueOf(member.getID())));

            /*
             * 会员购买记录
             */
            /*String condition = "'" + member.getCARD_ID() + "'";
            Collection memberBuy = memberDAO.getMemberBuy(conn, condition);
            request.setAttribute("memberBuy", memberBuy);*/
            conn.commit();
            //member = memberDAO.DetailMembers(conn, member.getID()+"");
           // request.setAttribute("member", member);
            //Collection memberAddCol = memberAddDAO.QueryMemberAddresses(conn,
                    //" and member_id=" + member.getID());
            //request.setAttribute("memberAddCol", memberAddCol);
            return mapping.findForward("success");
            //response.sendRedirect("memberInitModify.do?type=0&id="+member.getID()+"&address_id="+memberAdd.getID());
            //return null;
        } catch (Exception se) {
            conn.rollback(); //一发生异常就回滚
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

/*
 * Created on 2006-5-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberGetMemberDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberGetMember;
import com.magic.crm.member.form.MemberGetMemberForm;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 * 新增被推荐信息action类
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMemberAdd2Action extends Action {
    /** 写日志公用类 * */
    private static Logger log = Logger
            .getLogger("MemberGetMemberAdd2Action.class");
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MemberGetMemberForm myForm = (MemberGetMemberForm) form;
        MemberGetMember target = new MemberGetMember();
        Connection conn = DBManager.getConnection();
        MemberGetMemberDAO myDao = new MemberGetMemberDAO();
        MemberDAO memberDao = new MemberDAO();
        User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        Member member = null;
        Member member2 = null;
        long recommendedId = 0;
        long memberId = 0;
        try {
            memberId = MemberDAO.getMemberID(conn, myForm.getCardId());
            member = memberDao.DetailMembers(conn, String.valueOf(memberId));//推荐人member
            member2 = memberDao.DetailMembers(conn, String.valueOf(myForm.getRecommendedId()));//被推荐人member
            recommendedId = member2.getID();
            if (memberId == 0) {
                Message.setMessage(request, "会员号不存在，请先入会", "返回", null);
                return mapping.findForward("success");
            } else {

                if (MemberGetMemberDAO.hasTheRecommendedMan(conn, member
                        .getID(), recommendedId)) {//被推荐人已经存在
                    Message.setMessage(request, "该会员已经被推荐", "返回", null);
                    return mapping.findForward("success");
                }
                if (member.getID() == recommendedId) {
                    Message.setMessage(request, "不能自己推荐自己", "返回", null);
                    return mapping.findForward("success");
                }
                if (member.getCREATE_DATE().compareTo(member2.getCREATE_DATE()) >= 0) {//推荐人的注册日期大于被推荐人的注册日期
                    Message.setMessage(request, "被推荐人的入会日期不能早于推荐人的入会日期", "返回",
                            null);
                    return mapping.findForward("success");
                }

            }

            long itemID = ProductDAO.getItemID(conn, myForm.getItemCode());
            if (itemID == 0) {
                Message.setMessage(request, "该产品不存在", "返回", null);
                return mapping.findForward("success");
            } else {
                if(memberDao.checkMemberGift(conn, (int)itemID)){
                    Message.setMessage(request, "该推荐礼品不存在", "返回", null);
                    return mapping.findForward("success");
  			 	}	
            }
            myForm.setMemberId(new Long(memberId));
            myForm.setStatus(new Integer(0));
            myForm.setGiftId(new Long(itemID));
            myForm.setOperatorId(new Long(Long.parseLong(user.getId())));
            myForm.copy(target);
            Message.setMessage(request, "添加成功", "返 回",
                    "member/memberDetail.do?id="
                            + myForm.getRecommendedId());
            myDao.insert(conn, myForm);

        } catch (Exception e) {
            
            log.error("exception:", e);
            throw e;
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                log.error("an error occur where close connection!");
            }
        }
        return mapping.findForward("success");
    }
}

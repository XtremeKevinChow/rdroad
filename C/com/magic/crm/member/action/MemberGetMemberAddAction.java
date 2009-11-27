/*
 * Created on 2006-5-16
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

import com.magic.crm.member.form.MemberGetMemberForm;
import com.magic.crm.member.dao.MemberGetMemberDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.form.MemberGetMemberForm;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberGetMember;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 * 新增推荐信息action类
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMemberAddAction extends Action {

    /** 写日志公用类 * */
    private static Logger log = Logger
            .getLogger("MemberGetMemberAddAction.class");

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
        long recommendId = 0;
        try {

            member = memberDao.DetailMembers(conn, myForm.getRecommendedId()
                    .toString());//被推荐人member

            recommendId = MemberDAO.getMemberID(conn, myForm.getCardId());
            member2 = memberDao.DetailMembers(conn, String.valueOf(recommendId));//推荐人member

            if (recommendId == 0) {
                Message.setMessage(request, "会员号不存在，请先入会");
                return mapping.findForward("success");
            } else {

                /*if (MemberGetMemberDAO.hasTheRecommendedMan(conn, member
                        .getID(), recommendedId)) {//被推荐人已经存在
                    Message.setMessage(request, "该会员已经被推荐", "返回", null);
                    return mapping.findForward("success");
                }*/
                if (member.getID() == recommendId) {
                    Message.setMessage(request, "不能自己推荐自己");
                    return mapping.findForward("success");
                }
                if (member.getCREATE_DATE().compareTo(member2.getCREATE_DATE()) < 0) {//推荐人的注册日期大于被推荐人的注册日期
                    Message.setMessage(request, "被推荐人的入会日期不能早于推荐人的入会日期" );
                    return mapping.findForward("success");
                }

            }

            /*long itemID = ProductDAO.getItemID(conn, myForm.getItemCode());
            if (itemID == 0) {
                Message.setMessage(request, "该产品不存在", "返回", null);
                return mapping.findForward("success");
            } else {
                if(memberDao.checkMemberGift(conn, (int)itemID)){
                    Message.setMessage(request, "该推荐礼品不存在", "返回", null);
                    return mapping.findForward("success");
  			 	}	
            }*/
            //new Long(recommendedId);
            myForm.setMemberId((long)member2.getID());
            myForm.setRecommendedId((long)member.getID());
            //myForm.setStatus(new Integer(0));
            //myForm.setGiftId(new Long(itemID));
            myForm.setOperatorId(new Long(Long.parseLong(user.getId())));
            //myForm.copy(target);
            myDao.insert(conn, myForm);
            Message.setMessage(request, "添加成功", "返 回",
                    "member/memberDetail.do?id=" + member.getID() + "&recommended_id = 1");
           

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

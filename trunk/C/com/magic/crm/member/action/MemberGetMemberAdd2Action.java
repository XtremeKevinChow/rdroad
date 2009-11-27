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
 * �������Ƽ���Ϣaction��
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMemberAdd2Action extends Action {
    /** д��־������ * */
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
            member = memberDao.DetailMembers(conn, String.valueOf(memberId));//�Ƽ���member
            member2 = memberDao.DetailMembers(conn, String.valueOf(myForm.getRecommendedId()));//���Ƽ���member
            recommendedId = member2.getID();
            if (memberId == 0) {
                Message.setMessage(request, "��Ա�Ų����ڣ��������", "����", null);
                return mapping.findForward("success");
            } else {

                if (MemberGetMemberDAO.hasTheRecommendedMan(conn, member
                        .getID(), recommendedId)) {//���Ƽ����Ѿ�����
                    Message.setMessage(request, "�û�Ա�Ѿ����Ƽ�", "����", null);
                    return mapping.findForward("success");
                }
                if (member.getID() == recommendedId) {
                    Message.setMessage(request, "�����Լ��Ƽ��Լ�", "����", null);
                    return mapping.findForward("success");
                }
                if (member.getCREATE_DATE().compareTo(member2.getCREATE_DATE()) >= 0) {//�Ƽ��˵�ע�����ڴ��ڱ��Ƽ��˵�ע������
                    Message.setMessage(request, "���Ƽ��˵�������ڲ��������Ƽ��˵��������", "����",
                            null);
                    return mapping.findForward("success");
                }

            }

            long itemID = ProductDAO.getItemID(conn, myForm.getItemCode());
            if (itemID == 0) {
                Message.setMessage(request, "�ò�Ʒ������", "����", null);
                return mapping.findForward("success");
            } else {
                if(memberDao.checkMemberGift(conn, (int)itemID)){
                    Message.setMessage(request, "���Ƽ���Ʒ������", "����", null);
                    return mapping.findForward("success");
  			 	}	
            }
            myForm.setMemberId(new Long(memberId));
            myForm.setStatus(new Integer(0));
            myForm.setGiftId(new Long(itemID));
            myForm.setOperatorId(new Long(Long.parseLong(user.getId())));
            myForm.copy(target);
            Message.setMessage(request, "��ӳɹ�", "�� ��",
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

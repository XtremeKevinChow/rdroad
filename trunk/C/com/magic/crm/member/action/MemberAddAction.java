/*
 * Created on 2005-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
import com.magic.crm.product.dao.*;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

import com.magic.crm.member.bo.MemberBO;
import com.magic.crm.member.entity.MemberSessionRecruitGifts;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class MemberAddAction extends Action {
    

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	MemberForm fm = (MemberForm) form;
        User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        Member member = new Member();

        MemberDAO memberDAO = new MemberDAO();
        MemberAddresses memberAddr = new MemberAddresses();
        MemberEvents memberEvents = new MemberEvents();
        MemberAWARD memberAWARD = new MemberAWARD();
        MemberGIFTDAO memberGIFTDAO = new MemberGIFTDAO();

        try {
            PropertyUtils.copyProperties(member, form);
        } catch (InvocationTargetException ite) {
            throw new ServletException(ite);
        }
        Connection conn = null;
        MemberForm mf = (MemberForm) form;
        String condition = "";

        /*
         * ************************��������ǰ�ж����ݵĺϷ���******************************
         */

        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            System.out.println("start insert into member");
            int clubid=0;
            //int clubid = Integer.parseInt(request.getParameter("club_id"));//���ֲ�ID
            //String delivery_address = request.getParameter("delivery_address");//�ͻ���ַ
            //String postcode = request.getParameter("postcode");//�ʱ�
            //String event_date = request.getParameter("EVENT_DATE"); //�¼�����
            //String memgetmemNO = request.getParameter("MemgetmemID"); //�����˻�Ա��

            int memgetmemID = memberDAO.getMemberInfo(conn, fm.getMemgetmemID()).getID();//�����˻�ԱID
            String userid = user.getUSERID();
            //String gift_id = request.getParameter("GIFT_ID"); //��Ʒ��
            //String address = request.getParameter("address"); //��Ա����ʡ��
            //String address1 = request.getParameter("address1"); //��Ա������
            //member.setAddress(address);
            //member.setAddress1(address1);
            //int item_id = 0;
            //if (gift_id != null && !gift_id.equals("")) {
           //     item_id = ProductDAO.getItemID(conn, gift_id);
            //}
            /*
             * int gift_id2=0; if(gift_id!=null&&!gift_id.equals("")){
             * gift_id2=MemberGIFTDAO.getMemberGIFTID(conn,gift_id);//���ݻ��ŵõ���ƷID }
             */
            String comments = request.getParameter("COMMENTS");//˵��
            mf.setCLUB_ID(clubid);
            member.setCLUB_ID(clubid);
            member.setCOMMENTS(fm.getCOMMENTS());
            member.setGift_id(null);
            member.setMbr_get_mbr(fm.getMemgetmemID());
            member.setPostcode(fm.getPostcode());
            member.setAddressDetail(fm.getDelivery_address());
            member.setSection(mf.getSection());
            
            request.setAttribute("member", member);
            /*
             * ��Ա��ļʱ����MSCѡ����Ʒ
             */
            //String[] msc_gift_id = request.getParameterValues("msc_gift_id");
            //String is_msc_gift = request.getParameter("is_msc_gift");

            Collection memberMscGift = new ArrayList();

            //�ͷ������Ŧ�г���Ӧ����Ʒ����ȯ
            String msc_gift = request.getParameter("msc_gift");
            msc_gift = (msc_gift == null) ? "" : msc_gift;
            if (msc_gift != null && msc_gift.equals("1")) {
            	MemberSessionRecruitGifts sessionRecruit = (MemberSessionRecruitGifts)request.getSession(true).getAttribute("RECRUIT ACTIVITY");
                
            	request.setAttribute("mscGift", sessionRecruit.getSeletedRecruitGifs());
                return mapping.findForward("error6");
            }else {
                memberMscGift = memberGIFTDAO.get_MBR_MSC_GIFT_info(conn, mf
                        .getMSC_CODE());
                request.setAttribute("mscGift", memberMscGift);
            }
            /*
            if (mf.getMSC_CODE() != null && !mf.getMSC_CODE().equals("")
                    && msc_gift.equals("1")) {
               // memberMscGift = memberGIFTDAO.get_MBR_MSC_GIFT_info(conn, mf
                        //.getMSC_CODE());
            	MemberSessionRecruitGifts sessionRecruit = (MemberSessionRecruitGifts)request.getSession(true).getAttribute("RECRUIT ACTIVITY");
                request.setAttribute("mscGift", sessionRecruit);
                return mapping.findForward("error6");
            } else {
                memberMscGift = memberGIFTDAO.get_MBR_MSC_GIFT_info(conn, mf
                        .getMSC_CODE());
                request.setAttribute("mscGift", memberMscGift);
            }
            */
            /*
             * �ж���ļ��Աʱ���Ƿ�ѡ������Ʒ����ȯ
             * ���ѡ������Ʒ����ȯ���Ͱ�ֵ������ʱ��������member.setCERTIFICATE_CODE()
             */
            /*if (msc_gift_id == null) {
                member.setCERTIFICATE_CODE("");
            } else {
            	
                member.setCERTIFICATE_CODE(msc_gift_id[0]);
            }
            
            //����Ʒ����ȯ�����ͷ�û��ѡ�񣬷���ҳ��ѡ��

            if (is_msc_gift != null && msc_gift_id == null) {
                return mapping.findForward("error7");
            }*/

            /*
             * �ж������Ƿ��ظ��ύ
             */
            //ControlledError ctlErr = new ControlledError();
            /*String ad = "";
            String ad1 = "";
            if (address == null || address.length() == 0) {
                ad = address;
            } else {
                ad = address + "ʡ";
            }
            if (address1 == null || address1.length() == 0) {
                ad1 = address1;
            } else {
                ad1 = address1 + "��";
            }*/
            /*
             * modified by user 2008-03-10
             * MBR_MEMBERS.TELEPHONE�����˺�������
             */
            String telephone = mf.getTELEPHONE().trim();
            //if (telephone.length() >= 7) {
            //	telephone = telephone.substring(telephone.length() - 7);
            //}
            //System.out.println("user:"+telephone);
            condition =  " and a.name='" + mf.getNAME() + "' and telephone ='"
                    + telephone + "' ";
            if (memberDAO.checkMembers(conn, condition)) {
            	Message.setErrorMsg(request, "��Ա�Ѿ�����,�����ظ����");
                return mapping.findForward("error");
            }
            /*
             * �ж��ʱ�ĳ���
             */
            /*if (postcode.trim().length() != 6) {
                return mapping.findForward("error5");
            }*/
            /*
             * �ж�MSC�Ƿ����
             */

            /*if (memberDAO.checkMemberMSC(conn, mf.getMSC_CODE())) {
                return mapping.findForward("error2");
            }*/
            /*
             * �жϷ������ͺ;��ֲ�ƥ��
             */
            //if (mf.getCARD_TYPE() < 2 && mf.getCLUB_ID() > 1) {
            //    return mapping.findForward("error8");
            //}
            /*
             * �ж�MSC�Ƿ�;��ֲ�ƥ��
             */
            //if (mf.getCLUB_ID() < 2 && member.getMSC_CODE().equals("MB060101")) {
            //    return mapping.findForward("error9");
            //}

            /*
             * �ж��Ƽ���Ա���Ƿ����
             */

            if (fm.getMemgetmemID() != null && !fm.getMemgetmemID().equals("")) {
                if (memberDAO.checkMemberID(conn, fm.getMemgetmemID())) {
                	Message.setErrorMsg(request, "�Ƽ��˻�Ա�Ų�����");
                    return mapping.findForward("error");
                }
            }

            /*
             * �ж���Ʒ���Ƿ����
             */

            /*if (item_id > 0) {
                if (memberDAO.checkMemberGift(conn, item_id)) {
                    return mapping.findForward("error4");
                }
                memberEvents.setGift_ID(item_id);//��Ʒ��
            }*/

            /*
             * �ж��Ƿ��Զ�������Ա���� ����:����Ա����Ϊ�ջ�����NULL��ʱ���Զ���������
             */

            String cardidSeq = "";
            if (mf.getCARD_ID().length() < 1) {
                cardidSeq = memberDAO.getMBCardIDSEQ(conn);
            } else {
                cardidSeq = mf.getCARD_ID();
            }
            member.setCARD_ID(cardidSeq);
            /*
             * �жϻ�Ա�����Ƿ��Ѿ�����
             */
            if (memberDAO.checkMBCardIDSEQ(conn, cardidSeq)) {
                Message.setErrorMsg(request, "��Ա�����Ѿ�����");
                //request.setAttribute(Constants.ERROR_KEY, ctlErr);
                return mapping.findForward("error");
            }

            /*
             * insert into memberAddress
             */
            //System.out.println("1");
            
            memberAddr.setDelivery_address( fm.getDelivery_address());
            memberAddr.setPostcode(fm.getPostcode());
            memberAddr.setSection(mf.getSection());
            /*
             * insert into memberEvents
             */
            memberEvents.setEVENT_DATE(fm.getEvent_date());
            memberEvents.setCOMMENTS(comments);
            memberEvents.setOPERATOR_ID(Integer.parseInt(user.getId()));
            /*
             * insert into MBR_GET_AWARD
             */

            /*if (mf.getCARD_TYPE() == 0) {

                member.setCARD_TYPE(0);
                //memberAWARD.setItem_ID(100000);
            }
            if (mf.getCARD_TYPE() == 1) {
                //memberAWARD.setItem_ID(100002);
                member.setCARD_TYPE(1);

            }
            if (mf.getCARD_TYPE() == 2) {
                //memberAWARD.setItem_ID(118792);
                member.setCARD_TYPE(2);

            }*/

            memberAWARD.setClubID(clubid);
            memberAWARD.setStatus(10);
            memberAWARD.setOperator_id(Integer.parseInt(user.getId()));
            int addEvents = 0;
            if (memgetmemID>0) {
                memberEvents.setMEMBER_ID(memgetmemID);//�Ƽ���ID

            }
            /*
             * if(memberEvents.getMEMBER_ID()>0&&memberEvents.getGift_ID()>0){
             * //���Ƽ��Ļ�Ա�������п�����Ʒ�����л�Ա�Ƽ���Ա�¼�������addEvents=1��
             * //�����MBR_EVENTSʱ��Ҫ�Ѷ�Ӧ����Ϣ�����mbr_get_award
             * //memberEventsDAO.insert(con, memberEvents,1); addEvents=1; }
             */
            memberEvents.setOPERATOR_ID(Integer.parseInt(user.getId()));
            /*
             * �������ڸ�ʽ��
             */
            String theday = mf.getBIRTHDAY();
            String birthday = theday.substring(0, 4) + "-"
                    + theday.substring(4, 6) + "-" + theday.substring(6, 8);
            //��ַ���
            member.setAddressDetail(fm.getDelivery_address());
            member.setBIRTHDAY(birthday);
            member.setCreator_id(Integer.parseInt(user.getId()));
            /*
             * ���ӻ�Ա��ϸ��Ϣ
             */

//            int id = memberDAO.insert(conn, member, memberAdd, memberEvents,
//                    memberAWARD, addEvents);
            
            //��session�õ���Ʒ�б�
            MemberSessionRecruitGifts sessionGifts = (MemberSessionRecruitGifts)request.getSession().getAttribute("RECRUIT ACTIVITY");
            sessionGifts = sessionGifts==null ? new MemberSessionRecruitGifts(): sessionGifts;
            if(sessionGifts.getAllRecruitGifts()!=null && sessionGifts.checkAllSelectedGifts() < 0) {
            	 Message.setErrorMsg(request, "MSC�����Ʒ����������");
            	return mapping.findForward("error");
            }
            //System.out.println("2");
            /*if (!MemberDAO.checkSysPostFee(conn, postcode)) {
            	 return mapping.findForward("error5");
    		}*/
            
            MemberBO memberBO = new MemberBO();//memberҵ��ʵ��
            int id = memberBO.insert(conn, member, memberAddr, memberEvents, memberAWARD, sessionGifts != null ? sessionGifts.getSeletedRecruitGifs(): null);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            //������Ϣ�ͻ�Ա��ļ�޹�
            /*
             * ��ʾ��Ա��ϸ��Ϣ
             */
            member = memberDAO.DetailMembers(conn, String.valueOf(id));
            /**
             * add by user 2006-11-02 15:23 ����ǰ����Ļ�Ա��Ϣ����session�У�ֱ���˳����񣬽����Ự
             */
     
            CallCenterHander hander = new CallCenterHander(request.getSession());
            hander.setServiceMember(member);
            request.setAttribute("member", member);

            conn.commit();
            
            sessionGifts.setMemberId(member.getID());
            
            member.setGift_num(MemberGetAwardDAO.getAvailableGiftNumber(conn, member.getID()));
            request.setAttribute("member", member);
            
            request.getSession(true).setAttribute("RECRUIT ACTIVITY", sessionGifts);
            request.setAttribute("DEFAULT_TAB", "1");//��������ҳ��
            
            return mapping.findForward("success");

        } catch (Exception se) {
            conn.rollback();
            se.printStackTrace();
            Message.setErrorMsg(request, se.getMessage());
            return mapping.findForward("error");

        } finally {

            try {

                conn.close();

            } catch (SQLException sqe) {

                throw new ServletException(sqe);

            }

        }

    }

}

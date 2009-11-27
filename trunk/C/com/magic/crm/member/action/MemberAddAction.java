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
         * ************************新增数据前判断数据的合法性******************************
         */

        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            System.out.println("start insert into member");
            int clubid=0;
            //int clubid = Integer.parseInt(request.getParameter("club_id"));//俱乐部ID
            //String delivery_address = request.getParameter("delivery_address");//送货地址
            //String postcode = request.getParameter("postcode");//邮编
            //String event_date = request.getParameter("EVENT_DATE"); //事件日期
            //String memgetmemNO = request.getParameter("MemgetmemID"); //介绍人会员号

            int memgetmemID = memberDAO.getMemberInfo(conn, fm.getMemgetmemID()).getID();//介绍人会员ID
            String userid = user.getUSERID();
            //String gift_id = request.getParameter("GIFT_ID"); //礼品号
            //String address = request.getParameter("address"); //会员所在省份
            //String address1 = request.getParameter("address1"); //会员所在市
            //member.setAddress(address);
            //member.setAddress1(address1);
            //int item_id = 0;
            //if (gift_id != null && !gift_id.equals("")) {
           //     item_id = ProductDAO.getItemID(conn, gift_id);
            //}
            /*
             * int gift_id2=0; if(gift_id!=null&&!gift_id.equals("")){
             * gift_id2=MemberGIFTDAO.getMemberGIFTID(conn,gift_id);//根据货号得到礼品ID }
             */
            String comments = request.getParameter("COMMENTS");//说明
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
             * 会员招募时根据MSC选择礼品
             */
            //String[] msc_gift_id = request.getParameterValues("msc_gift_id");
            //String is_msc_gift = request.getParameter("is_msc_gift");

            Collection memberMscGift = new ArrayList();

            //客服点击按纽列出对应的礼品或礼券
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
             * 判断招募会员时，是否选择了礼品或礼券
             * 如果选择了礼品或礼券，就把值赋给临时代替属性member.setCERTIFICATE_CODE()
             */
            /*if (msc_gift_id == null) {
                member.setCERTIFICATE_CODE("");
            } else {
            	
                member.setCERTIFICATE_CODE(msc_gift_id[0]);
            }
            
            //有礼品或礼券，但客服没有选择，返回页面选择

            if (is_msc_gift != null && msc_gift_id == null) {
                return mapping.findForward("error7");
            }*/

            /*
             * 判断数据是否重复提交
             */
            //ControlledError ctlErr = new ControlledError();
            /*String ad = "";
            String ad1 = "";
            if (address == null || address.length() == 0) {
                ad = address;
            } else {
                ad = address + "省";
            }
            if (address1 == null || address1.length() == 0) {
                ad1 = address1;
            } else {
                ad1 = address1 + "市";
            }*/
            /*
             * modified by user 2008-03-10
             * MBR_MEMBERS.TELEPHONE增加了函数索引
             */
            String telephone = mf.getTELEPHONE().trim();
            //if (telephone.length() >= 7) {
            //	telephone = telephone.substring(telephone.length() - 7);
            //}
            //System.out.println("user:"+telephone);
            condition =  " and a.name='" + mf.getNAME() + "' and telephone ='"
                    + telephone + "' ";
            if (memberDAO.checkMembers(conn, condition)) {
            	Message.setErrorMsg(request, "会员已经存在,请勿重复入会");
                return mapping.findForward("error");
            }
            /*
             * 判断邮编的长度
             */
            /*if (postcode.trim().length() != 6) {
                return mapping.findForward("error5");
            }*/
            /*
             * 判断MSC是否存在
             */

            /*if (memberDAO.checkMemberMSC(conn, mf.getMSC_CODE())) {
                return mapping.findForward("error2");
            }*/
            /*
             * 判断发卡类型和俱乐部匹配
             */
            //if (mf.getCARD_TYPE() < 2 && mf.getCLUB_ID() > 1) {
            //    return mapping.findForward("error8");
            //}
            /*
             * 判断MSC是否和俱乐部匹配
             */
            //if (mf.getCLUB_ID() < 2 && member.getMSC_CODE().equals("MB060101")) {
            //    return mapping.findForward("error9");
            //}

            /*
             * 判断推荐会员号是否存在
             */

            if (fm.getMemgetmemID() != null && !fm.getMemgetmemID().equals("")) {
                if (memberDAO.checkMemberID(conn, fm.getMemgetmemID())) {
                	Message.setErrorMsg(request, "推荐人会员号不存在");
                    return mapping.findForward("error");
                }
            }

            /*
             * 判断礼品号是否存在
             */

            /*if (item_id > 0) {
                if (memberDAO.checkMemberGift(conn, item_id)) {
                    return mapping.findForward("error4");
                }
                memberEvents.setGift_ID(item_id);//礼品号
            }*/

            /*
             * 判断是否自动产生会员号码 条件:当会员号码为空或者是NULL的时候自动产生号码
             */

            String cardidSeq = "";
            if (mf.getCARD_ID().length() < 1) {
                cardidSeq = memberDAO.getMBCardIDSEQ(conn);
            } else {
                cardidSeq = mf.getCARD_ID();
            }
            member.setCARD_ID(cardidSeq);
            /*
             * 判断会员号码是否已经存在
             */
            if (memberDAO.checkMBCardIDSEQ(conn, cardidSeq)) {
                Message.setErrorMsg(request, "会员号码已经存在");
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
                memberEvents.setMEMBER_ID(memgetmemID);//推荐人ID

            }
            /*
             * if(memberEvents.getMEMBER_ID()>0&&memberEvents.getGift_ID()>0){
             * //有推荐的会员，而且有库存的礼品，就有会员推荐会员事件产生（addEvents=1）
             * //插入表MBR_EVENTS时，要把对应的信息插入表mbr_get_award
             * //memberEventsDAO.insert(con, memberEvents,1); addEvents=1; }
             */
            memberEvents.setOPERATOR_ID(Integer.parseInt(user.getId()));
            /*
             * 出生日期格式化
             */
            String theday = mf.getBIRTHDAY();
            String birthday = theday.substring(0, 4) + "-"
                    + theday.substring(4, 6) + "-" + theday.substring(6, 8);
            //地址组合
            member.setAddressDetail(fm.getDelivery_address());
            member.setBIRTHDAY(birthday);
            member.setCreator_id(Integer.parseInt(user.getId()));
            /*
             * 增加会员详细信息
             */

//            int id = memberDAO.insert(conn, member, memberAdd, memberEvents,
//                    memberAWARD, addEvents);
            
            //从session得到礼品列表
            MemberSessionRecruitGifts sessionGifts = (MemberSessionRecruitGifts)request.getSession().getAttribute("RECRUIT ACTIVITY");
            sessionGifts = sessionGifts==null ? new MemberSessionRecruitGifts(): sessionGifts;
            if(sessionGifts.getAllRecruitGifts()!=null && sessionGifts.checkAllSelectedGifts() < 0) {
            	 Message.setErrorMsg(request, "MSC入会礼品不符合条件");
            	return mapping.findForward("error");
            }
            //System.out.println("2");
            /*if (!MemberDAO.checkSysPostFee(conn, postcode)) {
            	 return mapping.findForward("error5");
    		}*/
            
            MemberBO memberBO = new MemberBO();//member业务实体
            int id = memberBO.insert(conn, member, memberAddr, memberEvents, memberAWARD, sessionGifts != null ? sessionGifts.getSeletedRecruitGifs(): null);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            //以下信息和会员招募无关
            /*
             * 显示会员详细信息
             */
            member = memberDAO.DetailMembers(conn, String.valueOf(id));
            /**
             * add by user 2006-11-02 15:23 将当前服务的会员信息载入session中，直到退出服务，结束会话
             */
     
            CallCenterHander hander = new CallCenterHander(request.getSession());
            hander.setServiceMember(member);
            request.setAttribute("member", member);

            conn.commit();
            
            sessionGifts.setMemberId(member.getID());
            
            member.setGift_num(MemberGetAwardDAO.getAvailableGiftNumber(conn, member.getID()));
            request.setAttribute("member", member);
            
            request.getSession(true).setAttribute("RECRUIT ACTIVITY", sessionGifts);
            request.setAttribute("DEFAULT_TAB", "1");//调到订单页面
            
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

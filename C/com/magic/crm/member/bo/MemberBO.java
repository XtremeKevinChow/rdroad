/*
 * Created on 2006-6-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.bo;

import java.sql.Connection;
import com.magic.crm.util.DateUtil;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberAddresses;
import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.member.entity.MemberEvents;
import com.magic.crm.member.entity.MemberGIFT;
import com.magic.crm.member.entity.MemberGetMember;
import com.magic.crm.member.entity.MemberGetMemberGift;

import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberGIFTDAO;
import com.magic.crm.member.dao.MemberGetMemberDAO;
import com.magic.crm.member.dao.MemberAddressDAO;
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.dao.MemberGetMemberGiftDAO;
import com.magic.crm.config.dao.SConfigKeysDAO;
import com.magic.crm.promotion.dao.MbrGiftListDAO;
import com.magic.crm.promotion.entity.Recruit_Activity_PriceList;
/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberBO {

    public int insert(Connection conn, Member member,
            MemberAddresses memberAddr, MemberEvents memberEvents,
            MemberAWARD memberAWARD, java.util.List giftsList) throws Exception {

        MemberDAO memberDAO = new MemberDAO();

        //** 得到会员seq **/
        int memberId = memberDAO.getMemberSEQ(conn);

        //** 得到会员地址seq **/
        int addressesId = memberDAO.getMemberAddressSEQ(conn);

        /* 得到会员推荐会员礼品设置信息 */
        //MemberGetMemberGiftDAO memberGetMemberGiftDAO = new MemberGetMemberGiftDAO();
        //MemberGetMemberGift memberGetMemberGift = memberGetMemberGiftDAO.findByItemId(conn, memberEvents.getGift_ID());
       
        //** 插入会员表 **
        member.setID(memberId);
        member.setADDRESS_ID(String.valueOf(addressesId));
        member.setIS_ORGANIZATION("0");
        memberDAO.insert(conn, member);
        
        // 插入会员msc礼券*
        memberDAO.insertMscNumber(conn, member);
        
        // 插入会员介绍会员礼券
        if(member.getMbr_get_mbr()!=null&&!member.getMbr_get_mbr().equals("")) {
        	memberDAO.insertMbrGetMbrGift(conn,member);
        }
        
        
        //** 插入俱乐部 **/
        //memberDAO.insertClub(conn, member);
        
        //** 插入会员地址和邮编到会员地址表MBR_ADDRESSES **/
        memberAddr.setMember_ID(memberId);
        memberAddr.setID(addressesId);
        memberAddr.setRelation_person(member.getNAME());
        /*
         * modified by user 2008-05-16
         * 将会员表中的电话插入地址簿
         * 按照优先级：手机，家庭电话，单位电话
         */
        /*String phone = "0";
        if (member.getTELEPHONE() != null && !member.getTELEPHONE().equals("")) {
        	phone = member.getTELEPHONE();
        } else {
        	if (member.getFAMILY_PHONE() != null && !member.getFAMILY_PHONE().equals("")) {
        		phone = member.getFAMILY_PHONE();
        	} else {
        		if (member.getCOMPANY_PHONE() != null && !member.getCOMPANY_PHONE().equals("")) {
        			phone = member.getCOMPANY_PHONE();
        		}
        	}
        }*/
        memberAddr.setTelephone(member.getTELEPHONE());
        memberAddr.setTelephone2(member.getFAMILY_PHONE() + " " +  member.getCOMPANY_PHONE());
        
        MemberAddressDAO memberAddressDAO = new MemberAddressDAO();
        memberAddressDAO.insert(conn, memberAddr);
        
        //** 会员推荐会员 **/
        /*MemberGetMemberDAO memberGetMemberDAO = new MemberGetMemberDAO();
        MemberGetMember memberGetMember = new MemberGetMember();
        memberGetMember.setMemberId(new Long(memberEvents.getMEMBER_ID()));
        memberGetMember.setStatus(new Integer(0));
        memberGetMember.setRecommendedId(new Long(memberId));
        memberGetMember.setGiftId(new Long(memberEvents.getGift_ID()));
        if ( memberGetMemberGift != null ) {
            memberGetMember.setKeepDays(new Integer(memberGetMemberGift.getKeepDays()));
        	memberGetMember.setPrice(new Double(memberGetMemberGift.getPrice()));
        }
        memberGetMember.setOperatorId(new Long(member.getCreator_id()));
        if (memberEvents.getMEMBER_ID() > 0
                && memberEvents.getGift_ID() > 0) {
            memberGetMemberDAO.insert(conn, memberGetMember);
        }*/

        
        
        //** 根据发卡类型插入记录到礼品表MBR_GET_AWARD **/
        /*MemberAWARDDAO memberAWARDDAO = new MemberAWARDDAO();
        memberAWARD.setMember_ID(memberId);
        memberAWARD.setLastDate(DateUtil.date2String(DateUtil.addDay(new java.util.Date(), 3600),"yyyy-MM-dd" ) );
        memberAWARDDAO.insert(conn, memberAWARD);
		*/
        
        /*
         * 招募会员时，如果选择礼品根据MSC插入对应MSC的礼品到mbr_get_award表memberAWARDDAO.insert();
         * 招募会员时，如果选择礼券根据MSC插入对应MSC的礼券到mbr_gift_ticket_use表MemberGIFTDAO.insert();
         */
        // modify zhux 20080714
        
        
        /*String days = SConfigKeysDAO.getValueByKey(conn, "REGISTER_GIFT_KEEP_DAY");
        memberAWARD.setLastDate(DateUtil.date2String(DateUtil.addDay(new java.util.Date(), Integer.parseInt(days)),"yyyy-MM-dd" ) );
        if (giftsList != null) {
	        for (int i = 0; i < giftsList.size(); i ++) {
	    		Recruit_Activity_PriceList product = (Recruit_Activity_PriceList)giftsList.get(i);
	    		if (product.getSellType() == 17) { //入会送礼
	    			memberAWARD.setType(product.getSellType());
	    			memberAWARD.setItem_ID(product.getItemId());
	                memberAWARD.setPrice(product.getPrice());
	                memberAWARD.setOrder_require(product.getOverx());//add by user 2008-02-15
	                memberAWARDDAO.insert(conn, memberAWARD);
	    		}
	    		
	    	}
        }
        */
        return memberId;
    }
}

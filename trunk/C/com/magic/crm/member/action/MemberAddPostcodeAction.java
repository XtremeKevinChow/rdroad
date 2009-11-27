package com.magic.crm.member.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.form.MemberForm;
import com.magic.crm.member.entity.SysPostcode;
import com.magic.crm.member.entity.Member;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * 
 * @author user
 * ��Աע�������ʱ�
 */
public class MemberAddPostcodeAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	MemberForm pageData = (MemberForm)form;
    	
    	String postcode = request.getParameter("Postcode");
    	String realPostcode = request.getParameter("realPostcode");
    	String address = request.getParameter("address");
    	String address1 = request.getParameter("address1");
    	
    	String delivery_address = request.getParameter("Delivery_address");//�ͻ���ַ
        String giftId = request.getParameter("gift_id");
        String userId= request.getParameter("userid");
        String memgetmemNO = request.getParameter("MemgetmemID"); //�����˻�Ա��
        String comments = request.getParameter("COMMENTS");//˵��
        
        //����ҳ������
		Member member = new Member();
		
        member.setNAME(pageData.getNAME());
        member.setCARD_ID(pageData.getCARD_ID());
        member.setGENDER(pageData.getGENDER());
        member.setBIRTHDAY(pageData.getBIRTHDAY());
        member.setCERTIFICATE_TYPE(pageData.getCERTIFICATE_TYPE());
        member.setCERTIFICATE_CODE(pageData.getCERTIFICATE_CODE());
        member.setFAMILY_PHONE(pageData.getFAMILY_PHONE());
        member.setCOMPANY_PHONE(pageData.getCOMPANY_PHONE());
        member.setTELEPHONE(pageData.getTELEPHONE());
        member.setPostcode(postcode);
        member.setRealPostcode(realPostcode);
        member.setAddress(address);
        member.setAddress1(address1);
        member.setAddressDetail(address+address1);
        member.setCLUB_ID(1);
        member.setCOMMENTS(comments);
        member.setMbr_get_mbr(memgetmemNO);
     	member.setGift_id(giftId);
     	member.setCARD_TYPE(pageData.getCARD_TYPE());
     	member.setEMAIL(pageData.getEMAIL());
     	member.setCATEGORY_ID(pageData.getCATEGORY_ID());
     	member.setCreator_id(Integer.parseInt(userId));
    	
    	if (postcode == null || postcode.equals("") || address == null || address.equals("") || address1 == null || address1.equals("")) {
    		Message.setMessage(request, "�������ܿ�!");
    		return mapping.findForward("input");
    	}
    	if (postcode.length() != 6) {
    		Message.setMessage(request, "�ʱ�Ӧ��Ϊ6λ����!");
    		return mapping.findForward("input");
    	}
    	SysPostcode post = new SysPostcode();
    	post.setPostcode(postcode);
    	post.setProvince(address);
    	post.setCity(address1);
    	
    	Connection conn = null;
    	try {
    		conn = DBManager.getConnection();
    		/*if (!MemberDAO.checkSysPostcode(conn, post)) {
    			Message.setMessage(request, "�ʱ�:" + postcode + "�Ѿ�����!");
        		return mapping.findForward("input");
    		}
    		if (!MemberDAO.checkSysPostFee(conn, post)) {
    			Message.setMessage(request, "�ʱ�:" + postcode + "�����ʱ����Ҳ���!");
        		return mapping.findForward("input");
    		}*/
    		MemberDAO.updateSysPostcode(conn, post);
    		member.setRealPostcode(postcode);
            
            
    	} catch(Exception e) {
    		e.printStackTrace();
    		throw new ServletException();
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
    	request.setAttribute("member", member);
    	return mapping.findForward("input");
    }
}

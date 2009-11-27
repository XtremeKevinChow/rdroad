/**
 * SupplyRecruitGiftsAction.java
 * 2008-3-17
 * ����10:46:09
 * user
 * SupplyRecruitGiftsAction
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.config.dao.SConfigKeysDAO;
import com.magic.crm.member.dao.MemberGetAwardDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.member.entity.MemberSessionRecruitGifts;
import com.magic.crm.promotion.entity.Recruit_Activity_PriceList;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.DateUtil;
import com.magic.crm.util.Message;

/**
 * @author user
 *
 */
public class SupplyRecruitGiftsAction extends Action {
	
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		String nextUrl = "success";
		int memberId = Integer.parseInt(request.getParameter("id"));
		String msc_code=request.getParameter("MSC_CODE");
		if (memberId <= 0 || msc_code == null || msc_code.trim().length() == 0) {
			nextUrl = "message";
			Message.setErrorMsg(request,"�������󣬻�Ա��msc���ܿ�!");
			return mapping.findForward(nextUrl);
		}
		//��½��
		User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        
        
		//��Ʒ��
		MemberAWARD memberAWARD = new MemberAWARD();
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			MemberGetAwardDAO memberAWARDDAO = new MemberGetAwardDAO();
	        //�ж��Ƿ��Ѿ����������Ʒ����������Ʒ����ѡ�������һ��©ѡ�ˣ��Ͳ��ܲ���Ʒ��
	        boolean flag = memberAWARDDAO.checkTypeOfGift(conn, 17, memberId);
	        boolean flag2= memberAWARDDAO.checkTypeOfGift(conn, 7, memberId);
	        if (flag || flag2) {//�Ѿ�����������Ʒ
	        	conn.rollback();
	        	nextUrl = "message";
				Message.setErrorMsg(request,"���Ѿ�����������Ʒ������ɾ����Ʒ!");
				request.getSession(true).removeAttribute("RECRUIT ACTIVITY");
				return mapping.findForward(nextUrl);
	        }
			//��session�еõ���ѡ��Ʒ
			MemberSessionRecruitGifts sessionGifts = (MemberSessionRecruitGifts)request.getSession().getAttribute("RECRUIT ACTIVITY");
			sessionGifts = sessionGifts==null ? new MemberSessionRecruitGifts(): sessionGifts;
			List giftsList = sessionGifts.getSeletedRecruitGifs();
			if(sessionGifts.getAllRecruitGifts()!=null && sessionGifts.checkAllSelectedGifts() < 0) {
				nextUrl = "message";
				Message.setErrorMsg(request,"��ѡ���MSC�����Ʒ����������!");
				request.getSession(true).removeAttribute("RECRUIT ACTIVITY");
				return mapping.findForward(nextUrl);
			}
			MemberDAO memberDAO = new MemberDAO();
			//����msc��
			memberDAO.updateMSC(conn, msc_code, memberId);
			/** �õ���Աע����Ʒ�������� **/
	        String days = SConfigKeysDAO.getValueByKey(conn, "REGISTER_GIFT_KEEP_DAY");
	        memberAWARD.setLastDate(DateUtil.date2String(DateUtil.addDay(new java.util.Date(), Integer.parseInt(days)),"yyyy-MM-dd" ) );
	        if (giftsList != null) {
		        for (int i = 0; i < giftsList.size(); i ++) {
		    		Recruit_Activity_PriceList product = (Recruit_Activity_PriceList)giftsList.get(i);
		    		if (product.getSellType() == 17) { //�������
		    			memberAWARD.setMember_ID(memberId);
		    			memberAWARD.setType(product.getSellType());
		    			//memberAWARD.setItem_ID(product.getItemId());
		                memberAWARD.setPrice(product.getPrice());
		                memberAWARD.setOrder_require(product.getOverx());
		                memberAWARD.setOperator_id(Integer.parseInt(user.getId()));
		               
		                //��������Ʒ
		                memberAWARDDAO.insert(conn, memberAWARD);
		    		}
		    		
		    	}
	        }
	        conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new ServletException(e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		if (request.getSession(true).getAttribute("RECRUIT ACTIVITY") != null) {
			request.getSession(true).removeAttribute("RECRUIT ACTIVITY");
		}
		
		return mapping.findForward(nextUrl);
	}
}

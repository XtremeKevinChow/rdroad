/*
 * Created on 2005-2-5
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
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.member.dao.MemberInquiryDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.member.entity.Member;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.common.CommonPageUtil;
import java.util.HashMap;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InquiryListAction2 extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        User user = new User();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        CommonPageUtil pageModel = new CommonPageUtil();
        Collection colUser = null;
        Connection conn = null;
        try {

            conn = DBManager.getConnection();
            MemberInquiryDAO memberInquiryDAO = new MemberInquiryDAO();
            MemberDAO memberDAO = new MemberDAO();
            Member member = new Member();

            String isquery = request.getParameter("isquery");
            String card_id = request.getParameter("s_card_id");
            String type = request.getParameter("type");//1 已解决 2 未解决
            String INQUIRY_TYPE = request.getParameter("INQUIRY_TYPE");
            String solve_date = request.getParameter("SOLVE_DATE");
            String solve_date2 = request.getParameter("SOLVE_DATE2");
            String SOLVE_PERSON = request.getParameter("SOLVE_PERSON");
            String deptid = request.getParameter("deptid");//投诉部门&咨询种类
            String IS_SOLVE = request.getParameter("IS_SOLVE");//投诉 1,咨询 0
            String IS_ANSWER = request.getParameter("IS_ANSWER");//是否需要回复 1是，0否

            if (deptid == null) {
                deptid = "2";
            }
            if (IS_SOLVE == null) {
                IS_SOLVE = "0";
            }
            if (card_id == null) {
                card_id = "";
            } else {
                if (card_id.length() > 0) {
                    member = memberDAO.getMemberInfo(conn, card_id.trim());
                    card_id = String.valueOf(member.getID());
                }
            }
            if (INQUIRY_TYPE == null) {
                INQUIRY_TYPE = "";
            }
            if (solve_date == null) {
                solve_date = "";
            }
            if (solve_date2 == null) {
                solve_date2 = "";
            }
            if (SOLVE_PERSON == null) {
                SOLVE_PERSON = "";
            }

            if (request.getMethod().equalsIgnoreCase("POST")) {
                /** 如果是callcenter系统，从sessin中取出会员信息，如果没有提示登录服务对象 * */
                String iscallcenter = request.getParameter("iscallcenter");

                if (iscallcenter != null && iscallcenter.equals("1")) {
                    CallCenterHander hander = new CallCenterHander(request
                            .getSession());
                    if (hander.isOnService()) {
                        Member mb = hander.getServicedMember();
                        card_id = String.valueOf(mb.getCARD_ID());
                        if (card_id == null) {
                            card_id = "";
                        } else {
                            if (card_id.length() > 0) {
                                member = memberDAO.getMemberInfo(conn, card_id.trim());
                                card_id = String.valueOf(member.getID());
                            }
                        }
                        request.setAttribute("iscallcenter", iscallcenter);
                    } else {
                        ControlledError ctlErr = new ControlledError();
                        ctlErr.setErrorTitle("操作错误");
                        ctlErr.setErrorBody("没有服务对象，请接入服务");
                        request
                                .setAttribute(
                                        com.magic.crm.util.Constants.ERROR_KEY,
                                        ctlErr);
                        return mapping.findForward("controlledError");

                    }
                }
            }
            /*
             * 如果客户端提交方式为"POST"，则按提交条件，从数据库中选择满足条件的会员投诉
             */

            String s_pageNum = request.getParameter("pageNo");
            int pageNum = 1;
            if (s_pageNum != null && !"".equals(s_pageNum)) {
                pageNum = Integer.parseInt(s_pageNum);
            }
            pageModel.setPageNo(pageNum);
            HashMap hashmap = new HashMap();
            hashmap.put("card_id", card_id);
            hashmap.put("type", type);
            hashmap.put("INQUIRY_TYPE", INQUIRY_TYPE);
            hashmap.put("solve_date", solve_date);
            hashmap.put("solve_date2", solve_date2);
            hashmap.put("SOLVE_PERSON", SOLVE_PERSON);
            hashmap.put("deptid", deptid);
            hashmap.put("IS_SOLVE", IS_SOLVE);
            hashmap
                    .put("departmentID", String
                            .valueOf(user.getDEPARTMENT_ID()));
            hashmap.put("IS_ANSWER", IS_ANSWER);
            pageModel.setCondition(hashmap);

            /*
             * 列出所有投诉类型
             */
            Collection InquiryType = memberInquiryDAO.getInquiryType(conn,
                    deptid, IS_SOLVE);
            request.setAttribute("InquiryType", InquiryType);

            Collection listInquiry = new ArrayList();

            /** add by user 2006-06-09 16:24 * */
            if (card_id != null && !card_id.equals("")) {
                isquery = "1";
            }

            if (isquery.equals("1")) {
                listInquiry = memberInquiryDAO.ListInquiry2(conn, pageModel);
            }
            request.setAttribute("memberPageModel", pageModel);
            //request.setAttribute("listInquiry",listInquiry);
            /*
             *  
             */
            UserDAO ud = new UserDAO();
            colUser = ud.findAllUsers(conn, 2);
            request.setAttribute("colUser", colUser);
            return mapping.findForward("success");

        } catch (SQLException se) {
            throw new ServletException(se);
        } finally {
            try {
                conn.close();
            } catch (SQLException sqe) {
                sqe.printStackTrace();
            }

        }
    }
}

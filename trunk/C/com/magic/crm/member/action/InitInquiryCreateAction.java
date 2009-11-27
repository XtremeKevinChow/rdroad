/*
 * Created on 2005-2-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.member.dao.MemberInquiryDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.form.InquiryForm;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InitInquiryCreateAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        saveToken(request);
        InquiryForm myForm = (InquiryForm) form;
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            MemberInquiryDAO memberInquiryDAO = new MemberInquiryDAO();

            /** 如果是callcenter系统，从sessin中取出会员信息，如果没有提示登录服务对象 * */
            String iscallcenter = request.getParameter("iscallcenter");
            if (iscallcenter != null && iscallcenter.equals("1")) {
                CallCenterHander hander = new CallCenterHander(request
                        .getSession());
                if (hander.isOnService()) {
                    Member mb = hander.getServicedMember();

                } else {
                    ControlledError ctlErr = new ControlledError();
                    ctlErr.setErrorTitle("操作错误");
                    ctlErr.setErrorBody("没有服务对象，请接入服务");
                    request.setAttribute(Constants.ERROR_KEY, ctlErr);
                    return mapping.findForward("controlledError");

                }
            }
            /*
             * 列出所有投诉类型
             */
            Collection InquiryType = memberInquiryDAO.getInquiryType(conn, "2","0");
            request.setAttribute("InquiryType", InquiryType);
            return mapping.findForward("success");
        } catch (SQLException se) {
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

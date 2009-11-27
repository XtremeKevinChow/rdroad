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

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.member.form.MemberGetMemberForm;
import com.magic.crm.member.dao.MemberGetMemberDAO;
import com.magic.crm.util.DBManager;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMemberInitAddAction extends Action {

    /** 写日志公用类 * */
    private static Logger log = Logger
            .getLogger("MemberGetMemberInitAddAction.class");

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MemberGetMemberForm myForm = (MemberGetMemberForm) form;
        Connection conn = DBManager.getConnection();
        String pageType = request.getParameter("pageType");
        try {

            MemberGetMemberDAO.getAvailGiftNumber(conn, myForm);
            
            //if (pageType.equals("1")) { //推荐信息
                return mapping.findForward("recommend_page");
           // } else {//被推荐信息
           //     String recommendedId = request.getParameter("recommendedId");
           //     myForm.setRecommendedId(new Long(Long.parseLong(recommendedId)));
           //     return mapping.findForward("recommended_page");
           // }
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
        
        
    }
}

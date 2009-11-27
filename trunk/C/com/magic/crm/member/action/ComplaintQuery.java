/*
 * Created on 2007-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.*;
import com.magic.crm.member.entity.Member;
import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;

/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ComplaintQuery extends Action{
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
            Mbr_complaintDAO cmptDAO = new Mbr_complaintDAO();
            MemberDAO memberDAO = new MemberDAO();
            Member member = new Member();


            String card_id = request.getParameter("s_card_id");
            String status = request.getParameter("status");//0 未解决1已解决 2 重新解决 3客服确认已经解决
            String cmpt_type_id = request.getParameter("cmpt_type_id");
            	cmpt_type_id=(cmpt_type_id==null)?"":cmpt_type_id;
            String solve_date = request.getParameter("solve_date");
            	solve_date=(solve_date==null)?"":solve_date;
            String solve_date2 = request.getParameter("solve_date2");
            	solve_date2=(solve_date2==null)?"":solve_date2;
            String creator = request.getParameter("creator");
            creator=(creator==null)?"":creator;
            String is_fenye = request.getParameter("is_fenye");
            is_fenye=(is_fenye==null)?"":is_fenye;
            String type = request.getParameter("type");//投诉 0,咨询 1
            type=(type==null)?"0":type;
            //String is_answer = request.getParameter("is_answer");//是否需要回复 1是，0否
            //is_answer=(is_answer==null)?"":is_answer;
            String parent_id=request.getParameter("parent_id");
            parent_id=(parent_id==null||parent_id.equals(""))?"10000":parent_id;

            if (card_id == null) {
                card_id = "";
            } else {
                if (card_id.length() > 0) {
                    member = memberDAO.getMemberInfo(conn, card_id.trim());
                    card_id = String.valueOf(member.getID());
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
            hashmap.put("cmpt_type_id", cmpt_type_id);
            hashmap.put("parent_id", parent_id);
            hashmap.put("solve_date", solve_date);
            hashmap.put("solve_date2", solve_date2);
            hashmap.put("creator", creator);
            hashmap.put("status", status);

            //hashmap.put("is_answer", is_answer);
            //hashmap.put("dept_id", String.valueOf(user.getDEPARTMENT_ID()));
            pageModel.setCondition(hashmap);

            /*
             * 列出所有大类类型//默认为咨询
             */
            Collection complaintType = cmptDAO.getSupperClass(conn,Integer.parseInt(type));

            /*
             * 列出所有小类类型//默认为咨询
             */
            Collection complaintSunType = cmptDAO.getSunClass(conn,Integer.parseInt(parent_id));
            request.setAttribute("complaintSunType", complaintSunType);
            request.setAttribute("complaintType", complaintType);

            Collection listInquiry = new ArrayList();

            if(is_fenye.equals("1")){
             listInquiry = cmptDAO.ListComplaintFY(conn, pageModel);
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

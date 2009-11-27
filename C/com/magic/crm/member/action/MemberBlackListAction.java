/*
 * Created on 2006-6-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.member.entity.MemberBlackList;
import com.magic.crm.member.form.MemberBlackListForm;

import com.magic.crm.member.dao.MemberBlackListDAO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberBlackListAction extends DispatchAction {

    public ActionForward showAddedPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	Connection conn = null;
    	try {
    		conn = DBManager.getConnection();
    		MemberBlackListDAO memberBlackListDAO = new MemberBlackListDAO();
    		ArrayList mbs = memberBlackListDAO.list(conn);
    		
    		request.setAttribute("list", mbs);
    		
    	} catch (Exception e) {
    		
    	} finally {
    		try {
                conn.close();
            } catch (SQLException ex) {
            }
    	}
        return mapping.findForward("add");
    }
    
    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	Connection conn = null;
    	try {
    		conn = DBManager.getConnection();
    		MemberBlackListDAO memberBlackListDAO = new MemberBlackListDAO();
    		Long id = Long.parseLong(request.getParameter("ID"));
    		memberBlackListDAO.delete(conn,id);
    		ArrayList mbs = memberBlackListDAO.list(conn);
    		
    		request.setAttribute("list", mbs);
    		
    	} catch (Exception e) {
    		
    	} finally {
    		try {
                conn.close();
            } catch (SQLException ex) {
            }
    	}
        return mapping.findForward("add");
    }

    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MemberBlackListForm myForm = (MemberBlackListForm) form;
        MemberBlackList target = new MemberBlackList();
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            PropertyUtils.copyProperties(target, myForm);
        } catch (InvocationTargetException ex) {
            throw new ServletException(ex);
        }

        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            MemberBlackListDAO memberBlackListDAO = new MemberBlackListDAO();
            int memberID = MemberDAO.getMemberID(conn, myForm.getCardID());
            target.setMemberID(memberID);
            target.setOperatorID(Integer.parseInt(user.getId()));
            if (memberID == 0) {
                Message.setMessage(request, "会员号不存在", "返回", null);
                return mapping.findForward("message");
            }
            if (memberBlackListDAO.isExistBlacklist(conn, memberID)) {
                Message.setMessage(request, "该会员已被加入黑名单", "返回", null);
                return mapping.findForward("message");
            }
            memberBlackListDAO.insert(conn, target);
            Message.setMessage(request, "新增成功", "返回", null);
            return mapping.findForward("message");
        }  
        catch (SQLException ex) {
            throw new ServletException(ex);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
        }
        
    }
}

/*
 * Created on 2006-7-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.action;

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
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.finance.dao.FinPurchaseInvoiceDAO;
import com.magic.crm.finance.form.FinPurchaseInvoiceForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseInvoiceUncheckAction extends DispatchAction {
    
    /**
     * 查询已审核的采购发票
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryCheckedList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseInvoiceForm myForm = (FinPurchaseInvoiceForm) form;
        Collection coll = null;
        Connection conn = null;
        
        if (request.getMethod().equals("GET")) {//提交方式
            
            coll = new ArrayList();
            request.setAttribute("list", coll);
            return mapping.findForward("page_list");
        }
        FinPurchaseInvoiceDAO myDao = new FinPurchaseInvoiceDAO();
        try {
            conn = DBManager.getConnection();
            myForm.setStatus("2");
            coll = myDao.findInvoicesByCondition(conn, myForm);
            request.setAttribute("list", coll);
            return mapping.findForward("page_list");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException();
        } finally {
            try {
                conn.close();
            } catch (SQLException sqe) {

            }

        }
    }
    
    /**
     * 弃审采购发票
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward uncheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseInvoiceForm myForm = (FinPurchaseInvoiceForm) form;
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String[] apID = request.getParameterValues("apID");
        Collection coll = null;
        Connection conn = null;
        
        FinPurchaseInvoiceDAO myDao = new FinPurchaseInvoiceDAO();
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            if (apID != null && apID.length > 0) {
                for (int i = 0; i < apID.length; i ++) {
                    myDao.uncheckInvoice(conn, Integer.parseInt(apID[i]), Integer.parseInt(user.getId()));
                }
            }
            conn.commit();
            Message.setMessage(request, "发票弃审成功!");
            return mapping.findForward("message");
        } catch (Exception ex) {
            conn.rollback();
            ex.printStackTrace();
            throw new ServletException();
        } finally {
            try {
                conn.close();
            } catch (SQLException sqe) {

            }

        }
    }
}

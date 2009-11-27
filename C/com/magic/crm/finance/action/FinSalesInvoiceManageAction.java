/*
 * Created on 2006-7-18
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

import com.magic.crm.finance.dao.FinSalesInvoiceDAO;
import com.magic.crm.finance.form.FinSalesInvoiceForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinSalesInvoiceManageAction extends DispatchAction {
    
    /**
     * 根据销售出库单生成销售发票
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newSalesInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String[] soIDs = request.getParameterValues("soIDs");
        Collection coll = null;
        Connection conn = null;
        
        FinSalesInvoiceDAO myDao = new FinSalesInvoiceDAO();
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            if (soIDs != null && soIDs.length > 0) {
                for (int i = 0; i < soIDs.length; i ++) {
                    myDao.newArInvoice(conn, Integer.parseInt(soIDs[i]), Integer.parseInt(user.getId()));
                }
            }
            conn.commit();
            Message.setMessage(request, "发票生成成功!");
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
    
    /**
     * 用于显示弃审销售发票列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryUncheckInvoiceList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        query(mapping, form, request, response);
        return mapping.findForward("page_uncheck_list");
    }
    
    /**
     * 用于显示审核销售发票列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryCheckInvoiceList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        query(mapping, form, request, response);
        return mapping.findForward("page_check_list");
    }
    
    /**
     * 用于显示销售发票列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryInvoiceList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        query(mapping, form, request, response);
        return mapping.findForward("page_list");
    }
    
    /**
     * 查询所有记录
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void query(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinSalesInvoiceForm myForm = (FinSalesInvoiceForm) form;
        Collection coll = null;
        Connection conn = null;

        if (request.getMethod().equals("GET")) {//提交方式

            coll = new ArrayList();
            request.setAttribute("list", coll);
            return;
            //return mapping.findForward("page_list");
        }

        FinSalesInvoiceDAO myDao = new FinSalesInvoiceDAO();
        try {
            conn = DBManager.getConnection();
            coll = myDao.findInvoicesByCondition(conn, myForm);
            request.setAttribute("list", coll);
            //return mapping.findForward("page_list");
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
     * 查询一条发票
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryInvoiceItems(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinSalesInvoiceForm myForm = (FinSalesInvoiceForm) form;
        Collection coll = null;
        Connection conn = null;
        FinSalesInvoiceDAO myDao = new FinSalesInvoiceDAO();
        try {
            conn = DBManager.getConnection();
            myDao.findInvoicesByPK(conn, myForm);
            coll = myDao.findInvoicesItemsByFK(conn, myForm);
            myForm.setInvoiceDetail(coll);
            return mapping.findForward("page_detail");
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
}

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
public class FinSalesInvoiceBalanceAction extends DispatchAction {

    /**
     * 查询已审核的销售发票
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
       
        FinSalesInvoiceForm myForm = (FinSalesInvoiceForm) form;
        Collection coll = null;
        Connection conn = null;
        
        if (request.getMethod().equals("GET")) {//提交方式
            
            coll = new ArrayList();
            request.setAttribute("list", coll);
            return mapping.findForward("page_list");
        }
        FinSalesInvoiceDAO myDao = new FinSalesInvoiceDAO();
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
     * 销售发票记帐
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward balance(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinSalesInvoiceForm myForm = (FinSalesInvoiceForm) form;
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String[] arID = request.getParameterValues("arID");
        Collection coll = null;
        Connection conn = null;
        
        FinSalesInvoiceDAO myDao = new FinSalesInvoiceDAO();
        
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            if (arID != null && arID.length > 0) {
                for (int i = 0; i < arID.length; i ++) {
                    FinSalesInvoiceForm currForm = new FinSalesInvoiceForm();
                    currForm.setArID(Integer.parseInt(arID[i]));
                    myDao.findInvoicesByPK(conn, currForm);
                    if (currForm != null && !currForm.getStatus().equals("2")) {
                        Message.setMessage(request, "发票"+currForm.getArNO() + "不是审核状态");
                        return mapping.findForward("message");
                    }
                    myDao.balanceInvoice(conn, Integer.parseInt(arID[i]), Integer.parseInt(user.getId()));
                }
            }
            conn.commit();
            Message.setMessage(request, "发票记帐成功!");
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

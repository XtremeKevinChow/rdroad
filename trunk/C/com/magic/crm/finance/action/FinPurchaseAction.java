/*
 * Created on 2006-7-11
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

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.finance.dao.FinPurchaseDAO;
import com.magic.crm.finance.form.FinPurchaseForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseAction extends DispatchAction {
    
    /**
     * 查询已经审核的采购到货单明细
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryCheckedPurchaseItems(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseForm myForm = (FinPurchaseForm) form;
        String ids = request.getParameter("ids");
        String proNO = request.getParameter("proNO");
        System.out.println(ids);
        if (ids != null && !"".equals(ids)) {
            myForm.setIds(ids);
            myForm.setProOrItemCondition(proNO);
            myForm.setProOrItem(3);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        Collection coll = null;
        Connection conn = null; 

        if (myForm.getProOrItemCondition() == null && !"".equals(myForm.getProOrItemCondition())) {//如果查询条件没有
            coll = new ArrayList();
            request.setAttribute("list", coll);
            return mapping.findForward("page_checked_list");
        }
        
        FinPurchaseDAO myDao = new FinPurchaseDAO();
        
        try {
            conn = DBManager.getConnection();
            coll = myDao.findCheckedPurchasesItems(conn, myForm);
            myForm.setPurchaseDetail(coll);
            return mapping.findForward("page_checked_list");
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
     * 查询所有记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseForm myForm = (FinPurchaseForm) form;
        Collection coll = null;
        Connection conn = null;

        if (request.getMethod().equals("GET")) {//提交方式

            coll = new ArrayList();
            request.setAttribute("list", coll);
            return mapping.findForward("page_list");
        }

        FinPurchaseDAO myDao = new FinPurchaseDAO();
        try {
            conn = DBManager.getConnection();
            coll = myDao.findUncheckedPurchases(conn, myForm);
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
     * 显示某条采购单的明细记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showPurchasesItems(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FinPurchaseForm myForm = (FinPurchaseForm) form;
        Collection coll = null;
        Connection conn = null;
        FinPurchaseDAO myDao = new FinPurchaseDAO();
        try {
            conn = DBManager.getConnection();
            myDao.findPurchasesByPK(conn, myForm);
            coll = myDao.findPurchasesItemsByFK(conn, myForm);
            myForm.setPurchaseDetail(coll);
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

    /**
     * 审核采购到货单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward checkPurchases(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FinPurchaseForm myForm = (FinPurchaseForm) form;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String[] psIDs = request.getParameterValues("psIDs");
        Connection conn = null;
        FinPurchaseDAO myDao = new FinPurchaseDAO();

        try {
            conn = DBManager.getConnection();
            if (myForm.getPsID() != 0) { //审核明细
                myForm.setOperatorID(Integer.parseInt(user.getId()));
                myDao.checkPurchase(conn, myForm);
            } else {
                if (psIDs != null && psIDs.length > 0) {
                    for (int i = 0; i < psIDs.length; i++) {
                        FinPurchaseForm param = new FinPurchaseForm();  
                        param.setPsID(Integer.parseInt(psIDs[i]));
                        param.setOperatorID(Integer.parseInt(user.getId()));
                        myDao.checkPurchase(conn, param);
                    }
                }
            }
            Message.setMessage(request, "审核成功!");
            return mapping.findForward("message");
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
     * 查询采购到货单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseForm myForm = (FinPurchaseForm) form;
        Collection coll = null;
        Connection conn = null;

        if (request.getMethod().equals("GET")) {//提交方式

            coll = new ArrayList();
            request.setAttribute("list", coll);
            return mapping.findForward("search_page_list");
        }

        FinPurchaseDAO myDao = new FinPurchaseDAO();
        String psCode=request.getParameter("psCode");
  
       
        try {
            conn = DBManager.getConnection();
            
            
            coll = myDao.searchPurchases(conn, myForm);
            request.setAttribute("list", coll);
            return mapping.findForward("search_page_list");
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

/*
 * Created on 2006-7-19
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

import com.magic.crm.finance.dao.FinSalesDAO;
import com.magic.crm.finance.form.FinSalesForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FinSalesAction extends DispatchAction {

    /**
     * 用于显示生成销售发票列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryNewInvoiceList(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        query(mapping, form, request, response);
        return mapping.findForward("page_new_list");
    }

    /**
     * 用于显示审核销售出库单列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward queryCheckList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        query(mapping, form, request, response);
        return mapping.findForward("page_check_list");
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
    public void query(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinSalesForm myForm = (FinSalesForm) form;
        Collection coll = null;
        Connection conn = null;

        if (request.getMethod().equals("GET")) {//提交方式

            coll = new ArrayList();
            request.setAttribute("list", coll);
            return;
            //return mapping.findForward("page_list");
        }

        FinSalesDAO myDao = new FinSalesDAO();
        try {
            conn = DBManager.getConnection();
            coll = myDao.findSalesByCondition(conn, myForm);
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
     * 显示弃审明细
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showUncheckSalesItems(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FinSalesForm myForm = (FinSalesForm) form;
        Collection coll = null;
        Connection conn = null;
        FinSalesDAO myDao = new FinSalesDAO();
        try {
            conn = DBManager.getConnection();
            int soId = myDao.findSoIdBySoNO(conn, myForm);
            myForm.setSoID(soId);
            myDao.findSalesByPK(conn, myForm);
            if (request.getMethod().equals("GET")) {
                //coll = new ArrayList();
                //myForm.setSalesDetail(coll);
                //return mapping.findForward("page_uncheck_detail");
            } else {
                if (myForm.getStatus() == null) {
                    Message.setMessage(request, "该订单不存在!");
                    return mapping.findForward("message");
                }
                if (myForm.getStatus() != null
                        && !myForm.getStatus().equals("2")) {
                    Message.setMessage(request, "订单状态不对，不能弃审!");
                    return mapping.findForward("message");
                }
            }
            coll = myDao.findSalesItemsByFK(conn, myForm);
            myForm.setSalesDetail(coll);
            return mapping.findForward("page_uncheck_detail");
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
     * 显示审核明细
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showCheckSalesItems(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        showDetail(mapping, form, request, response);
        return mapping.findForward("page_check_detail");
    }

    /**
     * 显示销售单详情
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showSalesItems(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        showDetail(mapping, form, request, response);
        return mapping.findForward("page_detail");
    }

    /**
     * 显示详情
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void showDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FinSalesForm myForm = (FinSalesForm) form;
        Collection coll = null;
        Connection conn = null;
        FinSalesDAO myDao = new FinSalesDAO();
        try {
            conn = DBManager.getConnection();
            myDao.findSalesByPK(conn, myForm);
            coll = myDao.findSalesItemsByFK(conn, myForm);
            myForm.setSalesDetail(coll);
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
     * 审核销售出库单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward checkSales(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FinSalesForm myForm = (FinSalesForm) form;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String[] soIDs = request.getParameterValues("soIDs");
        Connection conn = null;
        FinSalesDAO myDao = new FinSalesDAO();

        try {
            conn = DBManager.getConnection();
            if (myForm.getSoID() != 0) { //审核明细
                myForm.setOperatorID(Integer.parseInt(user.getId()));
                myDao.checkSales(conn, myForm);
            } else {
                if (soIDs != null && soIDs.length > 0) {
                    for (int i = 0; i < soIDs.length; i++) {
                        FinSalesForm param = new FinSalesForm();
                        param.setSoID(Integer.parseInt(soIDs[i]));
                        param.setOperatorID(Integer.parseInt(user.getId()));
                        myDao.checkSales(conn, param);
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
     * 弃审销售出库单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward uncheckSales(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FinSalesForm myForm = (FinSalesForm) form;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        myForm.setOperatorID(Integer.parseInt(user.getId()));
        Connection conn = null;
        FinSalesDAO myDao = new FinSalesDAO();

        try {
            conn = DBManager.getConnection();
            int id = myDao.findSoIdBySoNO(conn, myForm);
            if (id == 0) {
                Message.setMessage(request, "单号不存在!");
                return mapping.findForward("message");
            } else {
                myForm.setSoID(id);
                myDao.findSalesByPK(conn, myForm);
                if (!myForm.getStatus().equals("2")) {
                    Message.setMessage(request, "单据状态不对!");
                    return mapping.findForward("message");
                }
            }
            myDao.uncheckSales(conn, myForm);
            Message.setMessage(request, "弃审成功!");
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
}

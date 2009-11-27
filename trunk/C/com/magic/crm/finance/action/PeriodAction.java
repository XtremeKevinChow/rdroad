/*
 * Created on 2006-7-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.finance.dao.PeriodDAO;
import com.magic.crm.finance.entity.Period;
import com.magic.crm.finance.form.PeriodForm;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PeriodAction extends DispatchAction {
    /**
     * ��ʾ����ҳ��
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("page_add");

    }

    /**
     * ������¼
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PeriodForm myForm = (PeriodForm) form;
        Period period = new Period();
        Connection conn = null;
        PeriodDAO myDao = new PeriodDAO();
        try { 
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            PropertyUtils.copyProperties(period, myForm);
            if (PeriodDAO.checkPeriod(conn, myForm)) {
                Message.setMessage(request, "����ʧ�ܣ�������ظ���");
                return mapping.findForward("message");
            }
            int maxPeriod = PeriodDAO.getMaxPeriod(conn, myForm);
            if ( (myForm.getYear() * 24 + myForm.getMonth()) <= maxPeriod) {
                Message.setMessage(request, "������Ļ���ڲ���С��ϵͳ�е��������");
                return mapping.findForward("message");
            }
            int nextId = PeriodDAO.getNextPeriodId(conn);
            period.setID(nextId);
            myDao.insert(conn, period);
            if (PeriodDAO.getUsedPeroidCnt(conn) > 1) { //����1��
                conn.rollback();
                Message.setMessage(request, "ϵͳ���Ѿ��л��������ʹ��");
                return mapping.findForward("message");
            }
            conn.commit();
            return mapping.findForward("page_back_list");
        } catch (Exception ex) {
            conn.rollback();
            throw new ServletException();
        } finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }

        }

    }

    /**
     * ��ʾ�޸�ҳ��
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initModify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PeriodForm myForm = (PeriodForm) form;
        Period period = new Period();
        Connection conn = null;
        PeriodDAO myDao = new PeriodDAO();
        try {
            conn = DBManager.getConnection();
            period = myDao.findPeriodByPK(conn, myForm);
            PropertyUtils.copyProperties(myForm, period);
            return mapping.findForward("page_modify");
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
     * �޸ļ�¼
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PeriodForm myForm = (PeriodForm) form;
        Period period = new Period();
        Connection conn = null;
        PeriodDAO myDao = new PeriodDAO();
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            PropertyUtils.copyProperties(period, myForm);
            if (PeriodDAO.checkPeriod(conn, myForm)) {
                Message.setMessage(request, "����ʧ�ܣ�������ظ���");
                return mapping.findForward("message");
            }
            /*int maxPeriod = PeriodDAO.getMaxPeriod(conn, myForm);
            if ( (myForm.getYear() * 24 + myForm.getMonth()) <= maxPeriod) {
                Message.setMessage(request, "������Ļ���ڲ���С��ϵͳ�е��������");
                return mapping.findForward("message");
            }*/
            myDao.update(conn, period);
            if (PeriodDAO.getUsedPeroidCnt(conn) > 1) { //����1��
                conn.rollback();
                Message.setMessage(request, "ϵͳ���Ѿ��л��������ʹ��");
                return mapping.findForward("message");
            }
            conn.commit();
            return mapping.findForward("page_back_list");
        } catch (Exception se) {
            conn.rollback();
            throw new ServletException();
        } finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }

        }
    }
    
    /**
     * ��ѯ���м�¼
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
        PeriodForm myForm = (PeriodForm) form;
        Collection coll = null;
        Connection conn = null;
        PeriodDAO myDao = new PeriodDAO();
        try { 
            conn = DBManager.getConnection();
            coll = myDao.findAllPeriods(conn, null);
            request.setAttribute("list", coll);
            return mapping.findForward("page_list");
        } catch (Exception se) {
            throw new ServletException();
        } finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }

        }
    }
}

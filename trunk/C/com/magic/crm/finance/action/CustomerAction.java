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
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.pager.Pager;
import com.magic.crm.finance.dao.CustomerDAO;
import com.magic.crm.finance.dao.CustomerTypeDAO;
import com.magic.crm.finance.form.CustomerForm;
import com.magic.crm.finance.entity.Customer;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomerAction extends DispatchAction {

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
        CustomerForm myForm = (CustomerForm) form;
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            Collection coll = CustomerTypeDAO.findAllCustomerTypes1(conn);
            myForm.setCustomerTypeList(coll);
            return mapping.findForward("page_add");
        }catch(Exception ex) {
            throw new ServletException();
        }finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }
        }

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
       
        CustomerForm myForm = (CustomerForm) form;
        Customer customer = new Customer();
        Connection conn = null;
        CustomerDAO myDao = new CustomerDAO();
        
        try {
            conn = DBManager.getConnection();
           
            PropertyUtils.copyProperties(customer, myForm);
            myDao.insert(conn, customer);
            return mapping.findForward("page_back_list");
        } catch (Exception ex) {
            ex.printStackTrace();
            Message.setMessage(request, "����ʧ�ܣ������ǿͻ������ظ��ˡ�");
            return mapping.findForward("message");
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
        CustomerForm myForm = (CustomerForm) form;
        Customer data = new Customer();
        Connection conn = null;
        CustomerDAO myDao = new CustomerDAO();
        try {
            conn = DBManager.getConnection();
            data = myDao.findCustomerByPK(conn, myForm);
            PropertyUtils.copyProperties(myForm, data);
            Collection coll = CustomerTypeDAO.findAllCustomerTypes1(conn);
            myForm.setCustomerTypeList(coll);
            
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
        CustomerForm myForm = (CustomerForm) form;
        Customer customer = new Customer();
        Connection conn = null;
        CustomerDAO myDao = new CustomerDAO();
        try {
            
            conn = DBManager.getConnection();
            PropertyUtils.copyProperties(customer, myForm);
            myDao.update(conn, customer);
            return mapping.findForward("page_back_list");
        } catch (Exception se) {
            se.printStackTrace();
            Message.setMessage(request, "����ʧ�ܣ������ǿͻ������ظ��ˡ�");
            return mapping.findForward("message");
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
        CustomerForm myForm = (CustomerForm) form;
        Collection coll = null;
        Connection conn = null;
        CustomerDAO myDao = new CustomerDAO();
        try { 
            conn = DBManager.getConnection();
            int size = myDao.countAllCustomers(conn, myForm);
            Pager page = new Pager(myForm.getOffset(), size);//����page����
            page.setOffset(myForm.getOffset());//���õ�ǰλ��
            myForm.setPager(page);
            coll = myDao.findAllCustomers(conn, myForm);
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
    
    /**
     * ��ʾ��������ҳ��
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CustomerForm myForm = (CustomerForm) form;
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            Collection coll = CustomerTypeDAO.findAllCustomerTypes2(conn);
            myForm.setCustomerTypeList(coll);
            return mapping.findForward("page_import");
        }catch(Exception ex) {
            throw new ServletException();
        }finally {
            try {
                conn.close();

            } catch (SQLException sqe) {

            }
        }

    }
    
    /**
     * ���������Ź���Ա
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importOrgMembers(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            CustomerDAO.deleteAllOrgMembers(conn);
            CustomerDAO.insertAllOrgMembers(conn);
            conn.commit();
            Message.setMessage(request, "����ɹ���");
            return mapping.findForward("message");
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
     * �������빩Ӧ��
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importProviders(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            CustomerDAO.deleteAllProviders(conn);
            CustomerDAO.insertAllProviders(conn);
            conn.commit();
            Message.setMessage(request, "����ɹ���");
            return mapping.findForward("message");
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
}

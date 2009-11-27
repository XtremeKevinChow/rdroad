/*
 * Created on 2006-7-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.action;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.DateUtil;
import com.magic.crm.util.Message;
import com.magic.utils.Arith;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.finance.dao.FinPurchaseInvoiceDAO;
import com.magic.crm.finance.dao.FinPurchaseDAO;
import com.magic.crm.finance.form.FinPurchaseInvoiceForm;
import com.magic.crm.finance.form.FinPurchaseItemsForm;
import com.magic.crm.finance.form.FinPurchaseInvoiceItemsForm;
import com.magic.crm.finance.entity.FinPurchaseInvoice;
import com.magic.crm.finance.entity.FinPurchaseInvoiceItems;
import com.magic.crm.util.DBManager;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FinPurchaseInvoiceManageAction extends DispatchAction {

    /**
     * �жϾۼ��еĹ�Ӧ���ǲ���ͬһ��
     * 
     * @param coll
     * @return
     */
    private boolean isSameProviders(Collection coll) {
        FinPurchaseItemsForm item = (FinPurchaseItemsForm) ((java.util.List) coll)
                .get(0);
        Iterator it = coll.iterator();
        boolean isSame = true;
        String temp = item.getProNO();
        while (it.hasNext()) {
            FinPurchaseItemsForm items = (FinPurchaseItemsForm) it.next();
            if (!temp.equals(items.getProNO())) {
                isSame = false;
                break;
            }
        }
        return isSame;
    }

    /**
     * �ж�һ���ɹ��������ϵ����е���ϸ���Ƿ��Ѿ���Ʊ��
     * 
     * @param coll
     * @return
     */
    private boolean isAllItemsBalance(Collection coll) {
        Iterator it = coll.iterator();
        boolean isOver = true;
        while (it.hasNext()) {
            FinPurchaseItemsForm queryItems = (FinPurchaseItemsForm) it.next();
            if (!queryItems.getStatus().equals("3")) {//�ж��ӱ�״̬�Ƿ�ȫ��Ϊ"�������"
                isOver = false;
                break;
            }
        }
        return isOver;
    }

    
    /**
     * У����������
     * @param master
     * @param request
     * @param isExistCode
     * @return
     * @throws Exception
     */
    public boolean checkMasterData(FinPurchaseInvoice master,
            HttpServletRequest request, boolean isExistCode) throws Exception {
        //��Ʊ��
        if (master.getFactAPCode() == null || master.getFactAPCode().equals("")) {
            Message.setMessage(request, "��Ʊ�Ų���Ϊ��!");
            return false;
        }
        //˰��
        /*if (master.getTax() == 0) {
            Message.setMessage(request, "��ͷ˰�ʲ���Ϊ���ұ���Ϊ����!");
            return false;
        }*/
        //��Ʊ���
        if (master.getAmt() == 0) {
            Message.setMessage(request, "��Ʊ����Ϊ���ұ���Ϊ����!");
            return false;
        }
        //��Ʊ����
        if (master.getInvoiceDate() == null || master.getInvoiceDate().equals("")) {
            Message.setMessage(request, "��Ʊ���ڲ���Ϊ���Ҹ�ʽ����Ϊyyyy-mm-dd!");
            return false;
        }
        //�ж�ʵ�ʷ�Ʊ��Ʊ���Ƿ��Ѿ��ظ�
        if (isExistCode) {
            Message.setMessage(request, "��ʵ�ʷ�Ʊ���Ѿ�������!");
            return false;
        }
        return true;
    }
    
    

    /**
     * У�鷢Ʊ��ϸ��
     * 
     * @param master
     * @param request
     * @return
     * @throws Exception
     */
    public boolean checkItemsData(FinPurchaseInvoiceItems items, double useQty,
            HttpServletRequest request) throws Exception {

        //����
        if (items.getQty() == 0) {
            Message.setMessage(request, "��������Ϊ���ұ���Ϊ����!");
            return false;
        }

        //��Ʊ����
        if (items.getApPrice() == 0) {
            Message.setMessage(request, "��Ʊ���۲���Ϊ��!");
            return false;
        }

        //��Ʊ���
        if (items.getAmt() == 0) {
            Message.setMessage(request, "��Ʊ����Ϊ��!");
            return false;
        }
        //˰��
        /*if (items.getTax() == 0) {
            Message.setMessage(request, "˰�ʲ���Ϊ��!");
            return false;
        }*/

        //˰��
        /*if (items.getTax() == 0) {
            Message.setMessage(request, "˰���Ϊ��!");
            return false;
        }*/

        //��˰���
        if (items.getTotalAmt() == 0) {
            Message.setMessage(request, "��˰����Ϊ��!");
            return false;
        }

        //�����������ܴ���ϵͳ���пɽ��������
        if (Math.abs(items.getQty()) > Math.abs(useQty)) { //��������������δ������
            Message.setMessage(request, "��Ʒ" + items.getItemID()
                    + "�������������˿ɿ�Ʊ����!");
            return false;
        }
        
        //��Ʊ���۲��ܴ���Ԥ�㵥��
        
        /*if (Math.abs(items.getApPrice()) > Math.abs(items.getPurPrice())) {
            Message.setMessage(request, "��Ʊ���۲��ܴ���Ԥ�㵥��!");
            return false;
        }*/
        return true;
    }

    /**
     * ��ʾ������Ʊҳ��1
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initAdd1(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward("page_add");

    }

    /**
     * ��ʾ������Ʊҳ��2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initAdd2(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseInvoiceForm myForm = (FinPurchaseInvoiceForm) form;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String PKs = request.getParameter("psIDs");
        Collection coll = null;
        Connection conn = null;
        FinPurchaseDAO myDao = new FinPurchaseDAO();
        try {
            conn = DBManager.getConnection();
            coll = myDao.findCheckedPurchasesItemsByPKs(conn, PKs);

            FinPurchaseItemsForm item = (FinPurchaseItemsForm) ((java.util.List) coll)
                    .get(0);
            if (!isSameProviders(coll)) { //����ͬһ����Ӧ��
                Message.setMessage(request, "��Ҫ��Ʊ�Ĳ�Ʒ������ͬһ����Ӧ��!");
                return mapping.findForward("message");
            }
            myForm.setCreateDate(DateUtil.getSqlDate());
            myForm.setCreator(user.getNAME());
            myForm.setStatus("1");
            myForm.setProNO(item.getProNO());
            myForm.setProName(item.getProName());
            myForm.setInvoiceDetail(coll);
            return mapping.findForward("page_add");
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
     * ������Ʊ
     * 
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

        FinPurchaseInvoiceForm myForm = (FinPurchaseInvoiceForm) form;
        FinPurchaseInvoice master = new FinPurchaseInvoice();
        PropertyUtils.copyProperties(master, myForm);

        //��������
        String amt0 = request.getParameter("amt0");
        master.setAmt(Double.parseDouble(amt0));

        //�ռ���ϸ����
        String[] itemID = request.getParameterValues("itemID");
        String[] purPrice = request.getParameterValues("purPrice");
        String[] qty = request.getParameterValues("qty");
        String[] apPrice = request.getParameterValues("apPrice");
        String[] amt = request.getParameterValues("amt");
        String[] itemTax = request.getParameterValues("itemTax");
        String[] taxAmt = request.getParameterValues("taxAmt");
        String[] totalAmt = request.getParameterValues("totalAmt");
        String[] psDtlID = request.getParameterValues("psDtlID");
        String[] psID = request.getParameterValues("psID");

        Connection conn = null;
        FinPurchaseInvoiceDAO myDao = new FinPurchaseInvoiceDAO();
        FinPurchaseDAO purDAO = new FinPurchaseDAO();
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);

            int newMasterID = myDao.generateMasterID(conn);
            master.setApID(newMasterID);

            //            //��Ʊ��
            //            if (master.getFactAPCode() == null ||
            // master.getFactAPCode().equals("") ) {
            //                Message.setMessage(request, "��Ʊ�Ų���Ϊ��!");
            //                return mapping.findForward("message");
            //            }
            //            //˰��
            //            if (master.getTax() == 0 ) {
            //                Message.setMessage(request, "��ͷ˰�ʲ���Ϊ���ұ���Ϊ����!");
            //                return mapping.findForward("message");
            //            }
            //            //��Ʊ���
            //            if(amt0 == null || amt0.equals("")) {
            //                Message.setMessage(request, "��Ʊ����Ϊ��!");
            //                return mapping.findForward("message");
            //            } else {
            //                if (!StringUtil.isNumEx(amt0)) {
            //                    Message.setMessage(request, "��Ʊ������Ϊ����!");
            //                    return mapping.findForward("message");
            //                }
            //            }
            /** У���������� * */
            boolean isExistCode = myDao.checkFactApCode(conn, master);
            
            if (!checkMasterData(master, request, isExistCode)) {
                conn.rollback();
                return mapping.findForward("message");
            }
            
            /** �������� * */
            myDao.insertMaster(conn, master);

            /** ������ϸ�� * */
            if (psDtlID != null && psDtlID.length > 0) {
                for (int i = 0; i < psDtlID.length; i++) {

                    //                    //����
                    //                    if (qty[i] == null || qty[i].equals("")) {
                    //                        Message.setMessage(request, "��������Ϊ��!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(qty[i])) {
                    //                            Message.setMessage(request, "��������Ϊ����!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //��Ʊ����
                    //                    if (apPrice[i] == null || apPrice[i].equals("")) {
                    //                        Message.setMessage(request, "��Ʊ���۲���Ϊ��!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(apPrice[i])) {
                    //                            Message.setMessage(request, "��Ʊ���۱���Ϊ����!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //��Ʊ���
                    //                    if (amt[i] == null || amt[i].equals("")) {
                    //                        Message.setMessage(request, "��Ʊ����Ϊ��!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(amt[i])) {
                    //                            Message.setMessage(request, "��Ʊ������Ϊ����!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //˰��
                    //                    if (itemTax[i] == null || itemTax[i].equals("")) {
                    //                        Message.setMessage(request, "˰�ʲ���Ϊ��!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(itemTax[i])) {
                    //                            Message.setMessage(request, "˰�ʱ���Ϊ����!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //˰��
                    //                    if (taxAmt[i] == null || taxAmt[i].equals("")) {
                    //                        Message.setMessage(request, "˰���Ϊ��!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(taxAmt[i])) {
                    //                            Message.setMessage(request, "˰�����Ϊ����!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //��˰���
                    //                    if (totalAmt[i] == null || totalAmt[i].equals("")) {
                    //                        Message.setMessage(request, "��˰����Ϊ��!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(totalAmt[i])) {
                    //                            Message.setMessage(request, "��˰������Ϊ����!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }

                    FinPurchaseInvoiceItems invoiceItems = new FinPurchaseInvoiceItems();
                    invoiceItems.setApID(newMasterID);
                    invoiceItems.setItemID(Integer.parseInt(itemID[i]));
                    invoiceItems.setQty(Double.parseDouble(qty[i]));
                    invoiceItems.setPurPrice(Double.parseDouble(purPrice[i]));
                    invoiceItems.setAmt(Double.parseDouble(amt[i]));
                    //invoiceItems.setApPrice(Double.parseDouble(apPrice[i]));
                    invoiceItems.setApPrice(Arith.div(invoiceItems.getAmt(),
                            invoiceItems.getQty(), 2));//���㿪Ʊ����
                    invoiceItems.setTax(Double.parseDouble(itemTax[i]));
                    invoiceItems.setPsDtlID(Integer.parseInt(psDtlID[i]));
                    //invoiceItems.setTotalAmt(Double.parseDouble(totalAmt[i]));
                    invoiceItems.setTotalAmt(Arith.round(invoiceItems.getAmt()
                            + Double.parseDouble(taxAmt[i]), 2));//���㺬˰���
                    FinPurchaseItemsForm items = purDAO.findPurchasesItemsByPK(
                            conn, Integer.parseInt(psDtlID[i]));

                    //                    if (invoiceItems.getQty() > items.getUseQty()) {
                    // //��������������δ������
                    //                        Message.setMessage(request, "��Ʒ"
                    //                                + invoiceItems.getItemID() + "�������������˿ɿ�Ʊ����!");
                    //                        return mapping.findForward("message");
                    //                    }
                    /** У����ϸ������ * */
                    if (!checkItemsData(invoiceItems, items.getUseQty(),
                            request)) {
                        conn.rollback();
                        return mapping.findForward("message");
                    }

                    /** ���뷢Ʊ��ϸ�� * */
                    myDao.insertItems(conn, invoiceItems);

                    /** ���²ɹ���������ϸ������ * */
                    purDAO.updateItemsQtyByPK(conn, invoiceItems);

                    /** ���²ɹ���������ϸ��״̬ * */
                    if (items.getStatus().equals("1")) {

                        if (Math.abs(invoiceItems.getQty()) > 0) {
                            //���³ɲ��ֽ���״̬
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "2");
                        }
                    } 
                    //else if (items.getStatus().equals("2")) {

                        if (Math.abs(invoiceItems.getQty()) >= Math.abs(items.getUseQty())) { //���ν�����������δ������
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "3");
                        }
                   // }

                    /** ���²ɹ�����������״̬ * */
                    Collection coll = purDAO.findPurchasesItemsByFK2(conn,
                            Integer.parseInt(psID[i]));

                    if (isAllItemsBalance(coll)) { //�Ӽ�¼�����������
                        purDAO.updateMasterStatus(conn, Integer
                                .parseInt(psID[i]), "4");
                    } else {
                        purDAO.updateMasterStatus(conn, Integer
                                .parseInt(psID[i]), "3");
                    }
                }
            }
            conn.commit();
            Message.setMessage(request, "������Ʊ�ɹ�!");
            return mapping.findForward("message");
        } catch (Exception ex) {
            ex.printStackTrace();
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
     * �޸ķ�Ʊ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseInvoiceForm myForm = (FinPurchaseInvoiceForm) form;
        FinPurchaseInvoice master = new FinPurchaseInvoice();
        PropertyUtils.copyProperties(master, myForm);

        //��������
        String amt0 = request.getParameter("amt0");
        master.setAmt(Double.parseDouble(amt0));

        //�ռ���ϸ����
        String[] itemID = request.getParameterValues("itemID");
        String[] purPrice = request.getParameterValues("purPrice");
        String[] qty = request.getParameterValues("qty");
        String[] apPrice = request.getParameterValues("apPrice");
        String[] amt = request.getParameterValues("amt");
        String[] itemTax = request.getParameterValues("itemTax");
        String[] taxAmt = request.getParameterValues("taxAmt");
        String[] totalAmt = request.getParameterValues("totalAmt");
        String[] psDtlID = request.getParameterValues("psDtlID");
        String[] apID = request.getParameterValues("apID");
        String[] apDtlID = request.getParameterValues("apDtlID");

        Connection conn = null;
        FinPurchaseInvoiceDAO myDao = new FinPurchaseInvoiceDAO();
        FinPurchaseDAO purDAO = new FinPurchaseDAO();
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            
            /** У���������� * */
            boolean isExistCode = myDao.checkFactApCode(conn, master);
            
            if (!checkMasterData(master, request, isExistCode)) {
                conn.rollback();
                return mapping.findForward("message");
            }
            /** �õ�����Seqence **/
            int newMasterID = myDao.generateMasterID(conn);
            master.setApID(newMasterID);
            
            /** ���������¼ * */
            myDao.insertMaster(conn, master);
            
            /** ������ϸ�� * */
            if (psDtlID != null && psDtlID.length > 0) {
                for (int i = 0; i < psDtlID.length; i++) {

                    FinPurchaseInvoiceItems invoiceItems = new FinPurchaseInvoiceItems();
                    invoiceItems.setApID(newMasterID);
                    invoiceItems.setItemID(Integer.parseInt(itemID[i]));
                    invoiceItems.setQty(Double.parseDouble(qty[i]));
                    invoiceItems.setPurPrice(Double.parseDouble(purPrice[i]));
                    invoiceItems.setAmt(Double.parseDouble(amt[i]));
                    invoiceItems.setApPrice(Arith.div(invoiceItems.getAmt(),
                            invoiceItems.getQty(), 2));//���㿪Ʊ����
                    invoiceItems.setTax(Double.parseDouble(itemTax[i]));
                    invoiceItems.setPsDtlID(Integer.parseInt(psDtlID[i]));
                    invoiceItems.setTotalAmt(Arith.round(invoiceItems.getAmt()
                            + Double.parseDouble(taxAmt[i]), 2));//���㺬˰���

                    /** ʵʱȡ����Ʊ��ϸ�� **/
                    FinPurchaseInvoiceItemsForm items = myDao
                            .findInvoicesItemsByPK(conn, Integer
                                    .parseInt(apDtlID[i]));
                   
                    /** У����ϸ������ * */
                    if (!checkItemsData(invoiceItems, items.getUseQty(),
                            request)) {
                        conn.rollback();
                        return mapping.findForward("message");
                    }
                    
                    /** ���²ɹ���������ϸ�еĿ������� * */
                    purDAO.updateItemsQtyByPK(conn, -items.getQty(), items
                            .getPsDtlID());
                    
                    /** �õ��������ǰ��״̬������ **/
                    FinPurchaseItemsForm items2 = purDAO
                    .findPurchasesItemsByPK(conn, Integer
                            .parseInt(psDtlID[i]));
                    
                    /** ɾ����ϸ�� * */
                    myDao.deleteItemsByPK(conn, items);

                    /** ���뷢Ʊ��ϸ�� * */
                    myDao.insertItems(conn, invoiceItems);

                    /** ���²ɹ���������ϸ������ * */
                    purDAO.updateItemsQtyByPK(conn, invoiceItems);

                    /** ���²ɹ���������ϸ��״̬ * */
                    if (items2.getStatus().equals("3")) {//���ԭ���Ѿ���3���޸�֮�����������С��Ҫ��ԭ����״̬�ĳ�2
                        if (Math.abs(invoiceItems.getQty()) < Math.abs(items2.getUseQty())) {
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "2");
                        }
                        
                    } else if (items2.getStatus().equals("1")) {

                        if (Math.abs(invoiceItems.getQty()) > 0) {
                            //���³ɲ��ֽ���״̬
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "2");
                        }
                    } 
                    //else if (items2.getStatus().equals("2")) {

                        if (Math.abs(invoiceItems.getQty()) >= Math.abs(items.getUseQty())) { //���ν�����������δ������
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "3");
                        }
                    //}
                    
                    /** ���²ɹ�����������״̬ * */
                    Collection coll = purDAO.findPurchasesItemsByFK2(conn,
                            items2.getPsID());

                    if (isAllItemsBalance(coll)) { //�Ӽ�¼�����������
                        purDAO.updateMasterStatus(conn, items2.getPsID(), "4");
                    } else {
                        purDAO.updateMasterStatus(conn, items2.getPsID(), "3");
                    }
                }
            }
            
            /** ɾ�������¼ * */
            myDao.deleteMasterByPK(conn, myForm.getApID());
            
            conn.commit();
            Message.setMessage(request, "�޸ķ�Ʊ�ɹ�!");
            return mapping.findForward("message");
        } catch (Exception ex) {
            ex.printStackTrace();
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
     * ����ҳ�������ѯ���������Ĳɹ���Ʊ
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

        FinPurchaseInvoiceForm myForm = (FinPurchaseInvoiceForm) form;
        Collection coll = null;
        Connection conn = null;

        if (request.getMethod().equals("GET")) {//�ύ��ʽ
            coll = new ArrayList();
            request.setAttribute("list", coll);
            return mapping.findForward("page_list");
        }
        FinPurchaseInvoiceDAO myDao = new FinPurchaseInvoiceDAO();
        try {
            conn = DBManager.getConnection();
            //myForm.setStatus("1");
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
     * ��ʾĳ���ɹ���Ʊ����ϸ��¼
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void showPurchasesItems(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseInvoiceForm myForm = (FinPurchaseInvoiceForm) form;
        Collection coll = null;
        Connection conn = null;
        FinPurchaseInvoiceDAO myDao = new FinPurchaseInvoiceDAO();
        try {
            conn = DBManager.getConnection();
            myDao.findInvoicesByPK(conn, myForm);
            coll = myDao.findInvoicesItemsByFK(conn, myForm);
            myForm.setInvoiceDetail(coll);
            myForm.calc();
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
     * ��ѯ��Ʊ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showPurchasesItemsView(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        showPurchasesItems(mapping, form, request, response);
        return mapping.findForward("page_view");
    }

    /**
     * ��ѯ��Ʊ���飨�޸ģ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showPurchasesItemsModify(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        showPurchasesItems(mapping, form, request, response);
        return mapping.findForward("page_modify");
    }
    
    /**
     * ȡ���ɹ���Ʊ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelPurchase(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FinPurchaseInvoiceForm myForm = (FinPurchaseInvoiceForm) form;
        Connection conn = null;
        FinPurchaseInvoiceDAO myDao = new FinPurchaseInvoiceDAO();
        try {
            conn = DBManager.getConnection();
            myDao.cancelInvoice(conn, myForm.getApID());
            Message.setMessage(request, "��Ʊȡ���ɹ�!");
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

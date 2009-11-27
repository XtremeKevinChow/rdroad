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
     * 判断聚集中的供应商是不是同一个
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
     * 判断一个采购到货单上的所有的明细行是否都已经开票了
     * 
     * @param coll
     * @return
     */
    private boolean isAllItemsBalance(Collection coll) {
        Iterator it = coll.iterator();
        boolean isOver = true;
        while (it.hasNext()) {
            FinPurchaseItemsForm queryItems = (FinPurchaseItemsForm) it.next();
            if (!queryItems.getStatus().equals("3")) {//判断子表状态是否全部为"结算完成"
                isOver = false;
                break;
            }
        }
        return isOver;
    }

    
    /**
     * 校验主表数据
     * @param master
     * @param request
     * @param isExistCode
     * @return
     * @throws Exception
     */
    public boolean checkMasterData(FinPurchaseInvoice master,
            HttpServletRequest request, boolean isExistCode) throws Exception {
        //发票号
        if (master.getFactAPCode() == null || master.getFactAPCode().equals("")) {
            Message.setMessage(request, "发票号不能为空!");
            return false;
        }
        //税率
        /*if (master.getTax() == 0) {
            Message.setMessage(request, "表头税率不能为空且必须为数字!");
            return false;
        }*/
        //开票金额
        if (master.getAmt() == 0) {
            Message.setMessage(request, "开票金额不能为空且必须为数字!");
            return false;
        }
        //发票日期
        if (master.getInvoiceDate() == null || master.getInvoiceDate().equals("")) {
            Message.setMessage(request, "开票日期不能为空且格式必须为yyyy-mm-dd!");
            return false;
        }
        //判断实际发票发票号是否已经重复
        if (isExistCode) {
            Message.setMessage(request, "该实际发票号已经存在了!");
            return false;
        }
        return true;
    }
    
    

    /**
     * 校验发票明细行
     * 
     * @param master
     * @param request
     * @return
     * @throws Exception
     */
    public boolean checkItemsData(FinPurchaseInvoiceItems items, double useQty,
            HttpServletRequest request) throws Exception {

        //数量
        if (items.getQty() == 0) {
            Message.setMessage(request, "数量不能为空且必须为数字!");
            return false;
        }

        //开票单价
        if (items.getApPrice() == 0) {
            Message.setMessage(request, "开票单价不能为空!");
            return false;
        }

        //开票金额
        if (items.getAmt() == 0) {
            Message.setMessage(request, "开票金额不能为空!");
            return false;
        }
        //税率
        /*if (items.getTax() == 0) {
            Message.setMessage(request, "税率不能为空!");
            return false;
        }*/

        //税额
        /*if (items.getTax() == 0) {
            Message.setMessage(request, "税额不能为空!");
            return false;
        }*/

        //含税金额
        if (items.getTotalAmt() == 0) {
            Message.setMessage(request, "含税金额不能为空!");
            return false;
        }

        //结算数量不能大于系统中尚可结算的数量
        if (Math.abs(items.getQty()) > Math.abs(useQty)) { //结算数量超过了未结数量
            Message.setMessage(request, "产品" + items.getItemID()
                    + "结算数量超过了可开票数量!");
            return false;
        }
        
        //开票单价不能大于预算单价
        
        /*if (Math.abs(items.getApPrice()) > Math.abs(items.getPurPrice())) {
            Message.setMessage(request, "开票单价不能大于预算单价!");
            return false;
        }*/
        return true;
    }

    /**
     * 显示新增发票页面1
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
     * 显示新增发票页面2
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
            if (!isSameProviders(coll)) { //不是同一个供应商
                Message.setMessage(request, "你要开票的产品不属于同一个供应商!");
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
     * 新增发票
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

        //主表数据
        String amt0 = request.getParameter("amt0");
        master.setAmt(Double.parseDouble(amt0));

        //收集明细数据
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

            //            //发票号
            //            if (master.getFactAPCode() == null ||
            // master.getFactAPCode().equals("") ) {
            //                Message.setMessage(request, "发票号不能为空!");
            //                return mapping.findForward("message");
            //            }
            //            //税率
            //            if (master.getTax() == 0 ) {
            //                Message.setMessage(request, "表头税率不能为空且必须为数字!");
            //                return mapping.findForward("message");
            //            }
            //            //开票金额
            //            if(amt0 == null || amt0.equals("")) {
            //                Message.setMessage(request, "开票金额不能为空!");
            //                return mapping.findForward("message");
            //            } else {
            //                if (!StringUtil.isNumEx(amt0)) {
            //                    Message.setMessage(request, "开票金额必须为数字!");
            //                    return mapping.findForward("message");
            //                }
            //            }
            /** 校验主表数据 * */
            boolean isExistCode = myDao.checkFactApCode(conn, master);
            
            if (!checkMasterData(master, request, isExistCode)) {
                conn.rollback();
                return mapping.findForward("message");
            }
            
            /** 插入主表 * */
            myDao.insertMaster(conn, master);

            /** 插入明细行 * */
            if (psDtlID != null && psDtlID.length > 0) {
                for (int i = 0; i < psDtlID.length; i++) {

                    //                    //数量
                    //                    if (qty[i] == null || qty[i].equals("")) {
                    //                        Message.setMessage(request, "数量不能为空!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(qty[i])) {
                    //                            Message.setMessage(request, "数量必须为数字!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //开票单价
                    //                    if (apPrice[i] == null || apPrice[i].equals("")) {
                    //                        Message.setMessage(request, "开票单价不能为空!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(apPrice[i])) {
                    //                            Message.setMessage(request, "开票单价必须为数字!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //开票金额
                    //                    if (amt[i] == null || amt[i].equals("")) {
                    //                        Message.setMessage(request, "开票金额不能为空!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(amt[i])) {
                    //                            Message.setMessage(request, "开票金额必须为数字!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //税率
                    //                    if (itemTax[i] == null || itemTax[i].equals("")) {
                    //                        Message.setMessage(request, "税率不能为空!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(itemTax[i])) {
                    //                            Message.setMessage(request, "税率必须为数字!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //税额
                    //                    if (taxAmt[i] == null || taxAmt[i].equals("")) {
                    //                        Message.setMessage(request, "税额不能为空!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(taxAmt[i])) {
                    //                            Message.setMessage(request, "税额必须为数字!");
                    //                            return mapping.findForward("message");
                    //                        }
                    //                    }
                    //                    //含税金额
                    //                    if (totalAmt[i] == null || totalAmt[i].equals("")) {
                    //                        Message.setMessage(request, "含税金额不能为空!");
                    //                        return mapping.findForward("message");
                    //                    } else {
                    //                        if (!StringUtil.isNumEx(totalAmt[i])) {
                    //                            Message.setMessage(request, "含税金额必须为数字!");
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
                            invoiceItems.getQty(), 2));//计算开票单价
                    invoiceItems.setTax(Double.parseDouble(itemTax[i]));
                    invoiceItems.setPsDtlID(Integer.parseInt(psDtlID[i]));
                    //invoiceItems.setTotalAmt(Double.parseDouble(totalAmt[i]));
                    invoiceItems.setTotalAmt(Arith.round(invoiceItems.getAmt()
                            + Double.parseDouble(taxAmt[i]), 2));//计算含税金额
                    FinPurchaseItemsForm items = purDAO.findPurchasesItemsByPK(
                            conn, Integer.parseInt(psDtlID[i]));

                    //                    if (invoiceItems.getQty() > items.getUseQty()) {
                    // //结算数量超过了未结数量
                    //                        Message.setMessage(request, "产品"
                    //                                + invoiceItems.getItemID() + "结算数量超过了可开票数量!");
                    //                        return mapping.findForward("message");
                    //                    }
                    /** 校验明细行数据 * */
                    if (!checkItemsData(invoiceItems, items.getUseQty(),
                            request)) {
                        conn.rollback();
                        return mapping.findForward("message");
                    }

                    /** 插入发票明细行 * */
                    myDao.insertItems(conn, invoiceItems);

                    /** 更新采购到货单明细行数量 * */
                    purDAO.updateItemsQtyByPK(conn, invoiceItems);

                    /** 更新采购单货单明细行状态 * */
                    if (items.getStatus().equals("1")) {

                        if (Math.abs(invoiceItems.getQty()) > 0) {
                            //更新成部分结算状态
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "2");
                        }
                    } 
                    //else if (items.getStatus().equals("2")) {

                        if (Math.abs(invoiceItems.getQty()) >= Math.abs(items.getUseQty())) { //本次结算数量等于未结数量
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "3");
                        }
                   // }

                    /** 更新采购到货单主表状态 * */
                    Collection coll = purDAO.findPurchasesItemsByFK2(conn,
                            Integer.parseInt(psID[i]));

                    if (isAllItemsBalance(coll)) { //子记录都结算完成了
                        purDAO.updateMasterStatus(conn, Integer
                                .parseInt(psID[i]), "4");
                    } else {
                        purDAO.updateMasterStatus(conn, Integer
                                .parseInt(psID[i]), "3");
                    }
                }
            }
            conn.commit();
            Message.setMessage(request, "新增发票成功!");
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
     * 修改发票
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

        //主表数据
        String amt0 = request.getParameter("amt0");
        master.setAmt(Double.parseDouble(amt0));

        //收集明细数据
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
            
            /** 校验主表数据 * */
            boolean isExistCode = myDao.checkFactApCode(conn, master);
            
            if (!checkMasterData(master, request, isExistCode)) {
                conn.rollback();
                return mapping.findForward("message");
            }
            /** 得到主表Seqence **/
            int newMasterID = myDao.generateMasterID(conn);
            master.setApID(newMasterID);
            
            /** 插入主表记录 * */
            myDao.insertMaster(conn, master);
            
            /** 插入明细行 * */
            if (psDtlID != null && psDtlID.length > 0) {
                for (int i = 0; i < psDtlID.length; i++) {

                    FinPurchaseInvoiceItems invoiceItems = new FinPurchaseInvoiceItems();
                    invoiceItems.setApID(newMasterID);
                    invoiceItems.setItemID(Integer.parseInt(itemID[i]));
                    invoiceItems.setQty(Double.parseDouble(qty[i]));
                    invoiceItems.setPurPrice(Double.parseDouble(purPrice[i]));
                    invoiceItems.setAmt(Double.parseDouble(amt[i]));
                    invoiceItems.setApPrice(Arith.div(invoiceItems.getAmt(),
                            invoiceItems.getQty(), 2));//计算开票单价
                    invoiceItems.setTax(Double.parseDouble(itemTax[i]));
                    invoiceItems.setPsDtlID(Integer.parseInt(psDtlID[i]));
                    invoiceItems.setTotalAmt(Arith.round(invoiceItems.getAmt()
                            + Double.parseDouble(taxAmt[i]), 2));//计算含税金额

                    /** 实时取出发票明细行 **/
                    FinPurchaseInvoiceItemsForm items = myDao
                            .findInvoicesItemsByPK(conn, Integer
                                    .parseInt(apDtlID[i]));
                   
                    /** 校验明细行数据 * */
                    if (!checkItemsData(invoiceItems, items.getUseQty(),
                            request)) {
                        conn.rollback();
                        return mapping.findForward("message");
                    }
                    
                    /** 更新采购到货单明细行的可用数量 * */
                    purDAO.updateItemsQtyByPK(conn, -items.getQty(), items
                            .getPsDtlID());
                    
                    /** 得到插入操作前的状态，数量 **/
                    FinPurchaseItemsForm items2 = purDAO
                    .findPurchasesItemsByPK(conn, Integer
                            .parseInt(psDtlID[i]));
                    
                    /** 删除明细行 * */
                    myDao.deleteItemsByPK(conn, items);

                    /** 插入发票明细行 * */
                    myDao.insertItems(conn, invoiceItems);

                    /** 更新采购到货单明细行数量 * */
                    purDAO.updateItemsQtyByPK(conn, invoiceItems);

                    /** 更新采购单货单明细行状态 * */
                    if (items2.getStatus().equals("3")) {//如果原来已经是3，修改之后结算数量变小，要把原来的状态改称2
                        if (Math.abs(invoiceItems.getQty()) < Math.abs(items2.getUseQty())) {
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "2");
                        }
                        
                    } else if (items2.getStatus().equals("1")) {

                        if (Math.abs(invoiceItems.getQty()) > 0) {
                            //更新成部分结算状态
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "2");
                        }
                    } 
                    //else if (items2.getStatus().equals("2")) {

                        if (Math.abs(invoiceItems.getQty()) >= Math.abs(items.getUseQty())) { //本次结算数量等于未结数量
                            purDAO.updateItemsStatusByPK(conn, invoiceItems
                                    .getPsDtlID(), "3");
                        }
                    //}
                    
                    /** 更新采购到货单主表状态 * */
                    Collection coll = purDAO.findPurchasesItemsByFK2(conn,
                            items2.getPsID());

                    if (isAllItemsBalance(coll)) { //子记录都结算完成了
                        purDAO.updateMasterStatus(conn, items2.getPsID(), "4");
                    } else {
                        purDAO.updateMasterStatus(conn, items2.getPsID(), "3");
                    }
                }
            }
            
            /** 删除主表记录 * */
            myDao.deleteMasterByPK(conn, myForm.getApID());
            
            conn.commit();
            Message.setMessage(request, "修改发票成功!");
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
     * 根据页面参数查询符合条件的采购发票
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

        if (request.getMethod().equals("GET")) {//提交方式
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
     * 显示某条采购发票的明细记录
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
     * 查询发票详情
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
     * 查询发票详情（修改）
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
     * 取消采购发票
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
            Message.setMessage(request, "发票取消成功!");
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

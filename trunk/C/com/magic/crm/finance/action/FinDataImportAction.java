/*
 * Created on 2006-7-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.finance.dao.FinDataImportDAOFactory;
import com.magic.crm.finance.dao.FinDataImportDAOIF;
import com.magic.crm.finance.dao.PeriodDAO;
import com.magic.crm.finance.dao.FinPurchaseInvoiceDAO;
import com.magic.crm.finance.dao.FinSalesInvoiceDAO;
import com.magic.crm.finance.entity.Period;
import com.magic.crm.finance.form.PeriodForm;
import com.magic.crm.finance.form.FinDataImportForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinDataImportAction extends DispatchAction {
    /**
     * 采购
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initAPImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward("page_ap_import");

    }
    
    /**
     * 销售
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initARImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward("page_ar_import");

    }
    
    /**
     * 月结
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initMonthImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FinDataImportForm myForm = (FinDataImportForm)form;
        PeriodDAO pDAO = new PeriodDAO();
        Collection coll = new ArrayList();
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
	        coll = pDAO.findAllPeriods(conn, "0");
	        myForm.setPeriodList(coll);
	        return mapping.findForward("page_month_import");
        } catch(Exception ex) {
            throw ex;
        }finally {
            if (conn != null)
                conn.close();
        }

    }
    /**
     * 导入AR数据
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        FinDataImportForm myForm = (FinDataImportForm)form;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        myForm.setOperatorID(Integer.parseInt(user.getId()));
        Connection conn = null;
        FinDataImportDAOIF finDAO = null;
        try {
            conn = DBManager.getConnection();
            
            //如果是月结，检测当前会计期的所有发票是否都已经记帐过了
            if (myForm.getClazzName().equals("com.magic.crm.finance.dao.FinMonthDataImportDAO")) {
                PeriodDAO periodDAO = new PeriodDAO();
                PeriodForm period = new PeriodForm();
                period.setID(myForm.getPeriodID());
                Period p = periodDAO.findPeriodByPK(conn, period);
                if (!FinPurchaseInvoiceDAO.checkInvoiceStatusByPeriod(conn,p)) {
                    Message.setMessage(request, "该会计期内有未记帐的采购发票!");
                    return mapping.findForward("message");
                }
                if (!FinSalesInvoiceDAO.checkInvoiceStatusByPeriod(conn,p)) {
                    Message.setMessage(request, "该会计期内有未记帐的销售发票!");
                    return mapping.findForward("message");
                }
            }
            finDAO = FinDataImportDAOFactory.getImportDAO(myForm.getClazzName());
            finDAO.execute(conn, myForm);
            Message.setMessage(request, "数据导入成功!");
            return mapping.findForward("message");
        } catch (Exception ex) {
            Message.setMessage(request, "数据导入失败!"+ex);
            return mapping.findForward("message");
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {

            }
        }

    }
}

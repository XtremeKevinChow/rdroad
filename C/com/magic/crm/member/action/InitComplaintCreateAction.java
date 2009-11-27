/*
 * Created on 2007-3-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.action;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.member.dao.*;
import com.magic.crm.member.entity.*;
import com.magic.crm.member.form.*;
import com.magic.crm.util.CallCenterHander;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
/**
 * @authormagic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InitComplaintCreateAction  extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        saveToken(request);
        Mbr_ComplaintForm myForm = (Mbr_ComplaintForm) form;
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            Mbr_complaintDAO mcDAO = new Mbr_complaintDAO();

            /** �����callcenterϵͳ����sessin��ȡ����Ա��Ϣ�����û����ʾ��¼������� * */
            String iscallcenter = request.getParameter("iscallcenter");
            if (iscallcenter != null && iscallcenter.equals("1")) {
                CallCenterHander hander = new CallCenterHander(request
                        .getSession());
                if (!hander.isOnService()) {
                    ControlledError ctlErr = new ControlledError();
                    ctlErr.setErrorTitle("��������");
                    ctlErr.setErrorBody("û�з��������������");
                    request.setAttribute(Constants.ERROR_KEY, ctlErr);
                    return mapping.findForward("controlledError");

                }
            }
            String parent_id=request.getParameter("parent_id");
            parent_id=(parent_id==null||parent_id.equals(""))?"10000":parent_id;
            /*
             * �г����д�������//Ĭ��Ϊ��ѯ
             */
            Collection complaintType = mcDAO.getSupperClass(conn,myForm.getType());
            /*
             * �г�����С������//Ĭ��Ϊ��ѯ
             */
            Collection complaintSunType = mcDAO.getSunClass(conn,Integer.parseInt(parent_id));
            request.setAttribute("complaintSunType", complaintSunType);
            request.setAttribute("complaintType", complaintType);
            return mapping.findForward("success");
        } catch (SQLException se) {
            throw new ServletException(se);
        } finally {
            try {
                conn.close();
            } catch (SQLException sqe) {
                throw new ServletException(sqe);
            }

        }

    }
}

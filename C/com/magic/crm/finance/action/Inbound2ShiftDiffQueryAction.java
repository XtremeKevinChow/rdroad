/*
 * Created on 2006-5-10
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

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.pager.Pager;
import com.magic.crm.finance.dao.Inbound2ShiftDiffQueryDAO;
import com.magic.crm.finance.form.Inbound2ShiftDiffQueryForm;
import com.magic.crm.util.DBManager;
/**
 * @author user
 * （退货、扣单）入库上架查询action
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Inbound2ShiftDiffQueryAction extends Action {
    
    public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
        Inbound2ShiftDiffQueryForm myForm = (Inbound2ShiftDiffQueryForm)form;
        Connection conn = null;
        Collection list = null;
        Inbound2ShiftDiffQueryDAO myDao = new Inbound2ShiftDiffQueryDAO();
        if (request.getMethod().equals("GET")) {
            list = new java.util.ArrayList();
            request.setAttribute("list", list);
            return mapping.findForward("success");
        }
        try {
            conn = DBManager.getConnection();
            int size = myDao.countList(conn, myForm);
            Pager page = new Pager(myForm.getOffset(), size);//生成page对象
            page.setOffset(myForm.getOffset());//设置当前位置
            myForm.setPager(page);
            list = myDao.getList(conn, myForm);
            request.setAttribute("list", list);
            return mapping.findForward("success");
        }catch (Exception se) {
            se.printStackTrace();
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

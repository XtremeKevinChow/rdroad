/*
 * Created on 2005-3-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.product.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.common.pager.Pager;
import com.magic.crm.product.dao.ProviderDAO;
import com.magic.crm.product.entity.Provider;
import com.magic.crm.product.form.ProviderForm;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @authormagic
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PrvidersQueryAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProviderForm proForm = (ProviderForm)form;
		Connection conn = null;
		ProviderDAO pDao = new ProviderDAO();
		String condition = "";
		
		String nextUrl = "success";
		try {
			conn = DBManager.getConnection();
			Collection allProviders = new ArrayList();
			if (proForm.getProviderName()!=null && proForm.getProviderName().length() > 0) {
				condition += " and pro_name like '%" + proForm.getProviderName() + "%'";
				
			}
			if (proForm.getPro_no()!=null && proForm.getPro_no().length() > 0) {
				condition += " and pro_no = '" + proForm.getPro_no() + "'";
			}
			int size = pDao.countProviders(conn, condition);
			Pager page = new Pager(proForm.getOffset(), size);//生成page对象
            page.setLength(10);
            proForm.setPager(page);
			allProviders = pDao.queryProviders(conn, proForm, condition);
			request.setAttribute("allProviders", allProviders);
			
		} catch (SQLException se) {
			ControlledError ctlErr = new ControlledError();
			ctlErr.setErrorTitle("供应商查询错误");
			ctlErr.setErrorBody(se.getMessage());
			request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,
					ctlErr);
			nextUrl = "controlledError";
		} finally {
			try {
				conn.close();
			} catch (SQLException sqe) {
				throw new ServletException(sqe);
			}

		}
		return mapping.findForward(nextUrl);
	}
}

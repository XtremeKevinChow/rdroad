package com.magic.crm.user.action;


import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.magic.crm.common.SequenceManager;
import com.magic.crm.user.dao.PostCodeSetDAO;
import com.magic.crm.user.entity.PostcodeGroup;
import com.magic.crm.user.entity.PostcodeSet;
import com.magic.crm.user.form.PostcodeGroupForm;
import com.magic.crm.user.form.PostcodeSetForm;
import com.magic.crm.util.DBManager;
import java.util.Collection;
public class DeletePostcodeSetAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PostcodeSetForm setForm = (PostcodeSetForm)form;
		Connection conn = null;
		
		try {
			conn = DBManager.getConnection();
			
			PostCodeSetDAO.deleteSetByPK(conn, setForm);
			
		} catch(Exception e) {
			
			throw new ServletException("[**an error occured when you delete postcode set**]");
		} finally {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
		return mapping.findForward("success");
	}
}
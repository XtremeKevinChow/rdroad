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
public class InitPostcodeSetEditAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PostcodeSetForm setForm = (PostcodeSetForm)form;
		Connection conn = null;
		PostcodeSet postcodeSet = null;
		try {
			conn = DBManager.getConnection();
			postcodeSet = PostCodeSetDAO.findSetByPK(conn, setForm);
			//request.setAttribute("postcodeSet", postcodeSet);
			setForm.setId(postcodeSet.getId());
			setForm.setPostcode(postcodeSet.getPostcode());
			setForm.setPostFee(postcodeSet.getPostFee());
			setForm.setGroupId(postcodeSet.getPostcodeGroup().getId());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
		return mapping.findForward("success");
	}
}

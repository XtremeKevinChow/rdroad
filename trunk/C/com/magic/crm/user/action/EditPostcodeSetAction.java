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
import com.magic.crm.user.form.PostcodeSetForm;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;

public class EditPostcodeSetAction extends Action  {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PostcodeSetForm setForm = (PostcodeSetForm)form;
		Connection conn = null;
		PostcodeSet postcodeSet = new PostcodeSet();
		try {
			conn = DBManager.getConnection();
			if (PostCodeSetDAO.isExistPostcode(conn, setForm)) {
				ControlledError ctlErr = new ControlledError();
                ctlErr.setErrorTitle("修改邮编设置错误");
                ctlErr.setErrorBody("此邮编已经设置了");
                request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,ctlErr);
                return mapping.findForward("controlledError");
			}
			postcodeSet.setId(setForm.getId());
			postcodeSet.setPostcode(setForm.getPostcode());
			postcodeSet.setPostFee(setForm.getPostFee());
			PostCodeSetDAO.updateSet(conn, postcodeSet);
		} catch(Exception e) {
			throw new ServletException("[**an error occured when you edit postcode set**]");
		} finally {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
		return mapping.findForward("success");
	}
}


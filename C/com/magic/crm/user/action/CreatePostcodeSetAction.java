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
import com.magic.crm.util.Message;

public class CreatePostcodeSetAction extends Action  {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PostcodeSetForm setForm = (PostcodeSetForm)form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			if (PostCodeSetDAO.isExistPostcode(conn, setForm)) {
				ControlledError ctlErr = new ControlledError();
                ctlErr.setErrorTitle("新增邮编设置错误");
                ctlErr.setErrorBody("此邮编已经设置了");
                request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,ctlErr);
                return mapping.findForward("controlledError");
			}
			PostcodeSet setData = new PostcodeSet();
			setData.setPostcode(setForm.getPostcode());
			setData.setPostFee(setForm.getPostFee());
			setData.getPostcodeGroup().setId(setForm.getGroupId());
			
			PostCodeSetDAO.insertSet(conn, setData);
			
		} catch(Exception e) {
			
			throw new ServletException("[**an error occured when you create postcode set**]");
			
		} finally {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
		return mapping.findForward("success");
	}
}

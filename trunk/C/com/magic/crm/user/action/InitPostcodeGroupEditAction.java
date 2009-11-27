package com.magic.crm.user.action;

import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.magic.crm.user.dao.PostCodeSetDAO;
import com.magic.crm.user.entity.PostcodeGroup;
import com.magic.crm.user.form.PostcodeGroupForm;
import com.magic.crm.util.DBManager;

public class InitPostcodeGroupEditAction  extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PostcodeGroupForm groupForm = (PostcodeGroupForm)form;
		Connection conn = null;
		PostcodeGroup postcodeGroup = null;
		try {
			conn = DBManager.getConnection();
			postcodeGroup = PostCodeSetDAO.findGroupByPK(conn, groupForm);
			//postcodeGroup.setPostcodeSet(PostCodeSetDAO.findSetByFK(conn, groupForm));
			//request.setAttribute("postcodeGroup", postcodeGroup);
			// 将值压入页面
			groupForm.setId(postcodeGroup.getId());
			groupForm.setGroupName(postcodeGroup.getGroupName());
			groupForm.setDescription(postcodeGroup.getDescription());
		} catch(Exception e) {
			throw new ServletException("[**an error occured when you find postcode group**]");
		} finally {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
		return mapping.findForward("success");
	}
	
}

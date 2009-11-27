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
public class EditPostcodeGroupAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PostcodeGroupForm groupForm = (PostcodeGroupForm)form;
		Connection conn = null;
		PostcodeGroup groupData = null;
		try {
			conn = DBManager.getConnection();
			groupData = new PostcodeGroup();
			groupData.setId(groupForm.getId());
			groupData.setGroupName(groupForm.getGroupName());
			groupData.setDescription(groupForm.getDescription());
			PostCodeSetDAO.updateGroup(conn, groupData);
		} catch(Exception e) {
			throw new ServletException("[**an error occured when you edit postcode group**]");
		} finally {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
		return mapping.findForward("success");
	}
}

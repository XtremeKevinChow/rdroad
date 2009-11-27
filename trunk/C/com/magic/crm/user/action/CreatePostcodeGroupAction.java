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
import com.magic.crm.util.DBManager;

public class CreatePostcodeGroupAction extends Action  {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PostcodeGroupForm groupForm = (PostcodeGroupForm)form;
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			//conn.setAutoCommit(false);
			PostcodeGroup groupData = new PostcodeGroup();
			groupData.setGroupName(groupForm.getGroupName());
			groupData.setDescription(groupForm.getDescription());
			int groupId = SequenceManager.getNextVal(conn, "jxc.seq_postcode_group");
			groupData.setId(groupId);
			PostCodeSetDAO.insertGroup(conn, groupData);
			
			/*PostcodeSet setData = new PostcodeSet();
			setData.setPostcodeGroup(groupData);
			for (int i = 0; i < groupForm.getPostcode().length; i ++) {
				setData.setPostcode(groupForm.getPostcode()[i]);
				setData.setPostFee(groupForm.getPostFee()[i]);
				PostCodeSetDAO.insertSet(conn, setData);
			}*/
			//conn.commit();
		} catch(Exception e) {
			//conn.rollback();
			throw new ServletException("[**an error occured when you create postcode group**]");
			
		} finally {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
		return mapping.findForward("success");
	}
}

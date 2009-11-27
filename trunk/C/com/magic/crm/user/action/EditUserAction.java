package com.magic.crm.user.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.sql.*;
import javax.sql.*;
import java.math.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.user.form.*;
import com.magic.crm.user.entity.*;
import com.magic.crm.user.dao.*;
import com.magic.crm.util.*;

/**
 * <p>
 * Title: FE China Credit System
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: FE China
 * </p>
 * 
 * @author Liao Yu Hong
 * @version 1.0
 */

public final class EditUserAction extends Action {

	public EditUserAction() {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserForm uf = (UserForm) form;
		UserDAO userDao = new UserDAO();
		User user = new User();
		Connection conn = null;

		try {

			PropertyUtils.copyProperties(user, form);

		} catch (InvocationTargetException ite) {

			throw new ServletException(ite);

		}

		try {

			// DataSource dts = getDataSource(request);

			conn = DBManager.getConnection();
			if (userDao.checkUserID(conn, user)) {
				ControlledError ctlErr = new ControlledError();

				ctlErr.setErrorTitle("修改用户错误");

				ctlErr.setErrorBody("已有相同用户名存在");

				request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,
						ctlErr);

				return mapping.findForward("controlledError");
			}
			//check employee_number is exist
			if (userDao.checkEmployeeNumber(conn, user)) {
				ControlledError ctlErr = new ControlledError();
				ctlErr.setErrorTitle("新增用户错误");
				ctlErr.setErrorBody("已有同名工号存在");
				request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,
						ctlErr);
				return mapping.findForward("controlledError");
			}
			conn.setAutoCommit(false);

			String ifModify = request.getParameter("ifModify");
			userDao.update(conn, user, ifModify);

			UserRoleDAO userRoleDao = new UserRoleDAO();

			userRoleDao.delete(conn, user);

			String userId = user.getId();

			String[] roles = uf.getRoles();

			UserRole userRole = new UserRole();

			userRole.setUserId(userId);

			if (roles != null) {

				for (int i = 0, len = roles.length; i < len; i++) {

					userRole.setRoleId(roles[i].toString());

					userRoleDao.insert(conn, userRole);

				}
			}

			conn.commit();

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
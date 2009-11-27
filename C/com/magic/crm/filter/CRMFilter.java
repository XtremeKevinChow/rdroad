package com.magic.crm.filter;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.magic.crm.user.dao.PathRightDAO;
import com.magic.crm.user.dao.UserDAO;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

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
 * Company: 99Read.com
 * </p>
 * 
 * @author Kevin zhou
 * @version 1.0
 */

public class CRMFilter implements Filter {
	private static Logger log = Logger.getLogger(CRMFilter.class);
	private FilterConfig config = null;

	public void init(FilterConfig config) throws ServletException {

		this.config = config;

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)

	throws IOException, ServletException {

		String servletPath = "";
		servletPath = ((HttpServletRequest) request).getServletPath();
		// step1 ����Ҫ���Ȩ�޵�ҳ��ֱ�������������
		if (servletPath == null || servletPath.equals("/logonnow.do")
				|| servletPath.equals("/userLogonOut.do")
				|| servletPath.equals("/login.jsp")
				|| servletPath.equals("/index.jsp")
				|| servletPath.equals("/callcenter_login.jsp")) {

			chain.doFilter(request, response);
			return;
		}

		Connection conn = null;
		HttpSession session = ((HttpServletRequest) request).getSession();
		ServletContext servletContext = config.getServletContext();

		try {
			conn = DBManager.getConnection();
			
			// step2 �õ�ϵͳ����Ȩ��
			
			Map pathRightMap = (Map)servletContext.getAttribute(Constants.PATH_RIGHT_MAP);
			if (pathRightMap == null) {

				pathRightMap = new PathRightDAO().getPathRightMap(conn);
				servletContext.setAttribute(Constants.PATH_RIGHT_MAP, pathRightMap);
			}
			
			// step3 �û���ʱ�˳���½
			User user = (User) session.getAttribute(Constants.USER_KEY);
			if (user == null ) {
				Message.setMessage((HttpServletRequest)request, "��������ʱ��û��ʹ��ϵͳ���ͻ�������������ӳ�ʱ����<a href='/userLogonOut.do?flag��logout' target='_top'>���µ�½CRM</a>");
				servletContext.getRequestDispatcher("/message.jsp")
					.forward(request, response);
				return;
				
				/*ControlledError ctlErr = new ControlledError();
				ctlErr.setErrorTitle("ϵͳ��ʾ��Ϣ");
				ctlErr.setErrorBody("��������ʱ��û��ʹ��ϵͳ���ͻ�������������ӳ�ʱ����<a href='/userLogonOut.do?flag��logout' target='_top'>���µ�½CRM</a>");
				request.setAttribute(Constants.ERROR_KEY, ctlErr);
				servletContext.getRequestDispatcher("/controlledError.jsp")
										.forward(request, response);
				return;*/
			}
			
			// step4  ���url�����⴦��
			String typParam = request.getParameter("type");
			if(typParam !=null ) {
				servletPath = servletPath + "?type=" + typParam;
			}
			
			// step5 ����û�Ȩ�޺�ϵͳȨ�ޱȽ�
			if (!hasRight(user, pathRightMap, servletPath)) {

				Message.setMessage((HttpServletRequest)request, "�Բ�����û��ִ�д˲�����Ȩ��");
				servletContext.getRequestDispatcher("/message.jsp")
					.forward(request, response);
				return;
				
				/*ControlledError ctlErr = new ControlledError();
				ctlErr.setErrorTitle("Ȩ�޼�����");
				ctlErr.setErrorBody("�Բ�����û��ִ�д˲�����Ȩ��");
				request.setAttribute(Constants.ERROR_KEY, ctlErr);
				servletContext.getRequestDispatcher("/controlledError.jsp")
						.forward(request, response);
				return;*/
			}

			
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Message.setErrorMsg((HttpServletRequest) request, "δ֪����");
			servletContext.getRequestDispatcher("/message.jsp").forward(request,
					response);
			return;
		} finally {

			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

	}

	private boolean hasRight(User user, Map pathRightMap, String requestURI) {

		BitSet userRights = user.getAccessRight();
		BigDecimal neededRight = (BigDecimal) pathRightMap.get(requestURI);

		if (neededRight == null) {

			return true;

		}

		if (userRights == null) {

			return false;

		}

		return userRights.get(neededRight.intValue());

	}

	public void destroy() {

		this.config = null;

	}

}
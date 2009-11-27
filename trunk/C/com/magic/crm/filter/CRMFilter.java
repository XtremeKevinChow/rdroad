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
		// step1 不需要检查权限的页面直接跳过不做检查
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
			
			// step2 得到系统所有权限
			
			Map pathRightMap = (Map)servletContext.getAttribute(Constants.PATH_RIGHT_MAP);
			if (pathRightMap == null) {

				pathRightMap = new PathRightDAO().getPathRightMap(conn);
				servletContext.setAttribute(Constants.PATH_RIGHT_MAP, pathRightMap);
			}
			
			// step3 用户超时退出登陆
			User user = (User) session.getAttribute(Constants.USER_KEY);
			if (user == null ) {
				Message.setMessage((HttpServletRequest)request, "由于您长时间没有使用系统，客户端与服务器连接超时，请<a href='/userLogonOut.do?flag＝logout' target='_top'>重新登陆CRM</a>");
				servletContext.getRequestDispatcher("/message.jsp")
					.forward(request, response);
				return;
				
				/*ControlledError ctlErr = new ControlledError();
				ctlErr.setErrorTitle("系统提示信息");
				ctlErr.setErrorBody("由于您长时间没有使用系统，客户端与服务器连接超时，请<a href='/userLogonOut.do?flag＝logout' target='_top'>重新登陆CRM</a>");
				request.setAttribute(Constants.ERROR_KEY, ctlErr);
				servletContext.getRequestDispatcher("/controlledError.jsp")
										.forward(request, response);
				return;*/
			}
			
			// step4  针对url的特殊处理
			String typParam = request.getParameter("type");
			if(typParam !=null ) {
				servletPath = servletPath + "?type=" + typParam;
			}
			
			// step5 检查用户权限和系统权限比较
			if (!hasRight(user, pathRightMap, servletPath)) {

				Message.setMessage((HttpServletRequest)request, "对不起，你没有执行此操作的权限");
				servletContext.getRequestDispatcher("/message.jsp")
					.forward(request, response);
				return;
				
				/*ControlledError ctlErr = new ControlledError();
				ctlErr.setErrorTitle("权限检测错误");
				ctlErr.setErrorBody("对不起，你没有执行此操作的权限");
				request.setAttribute(Constants.ERROR_KEY, ctlErr);
				servletContext.getRequestDispatcher("/controlledError.jsp")
						.forward(request, response);
				return;*/
			}

			
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Message.setErrorMsg((HttpServletRequest) request, "未知错误");
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
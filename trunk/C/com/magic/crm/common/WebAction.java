package com.magic.crm.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * <p>
 * Title: magic CRM System
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: magic
 * </p>
 * 
 * @author Xjwang
 * @version 1.0
 */

public abstract class WebAction extends Action {
	
	private static Logger log = Logger.getLogger(WebAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DBOperation db = null;
		//this.request = request;
		//this.response = response;
		String forword = "error";

		try {
			// ���ݿ�����
			db = new DBOperation(DBManager.getConnection());
			db.setAutoCommit(false);
			// ִ���߼�����
			forword = execute(request,response,db, (WebForm)form);
			// commit���ݿ����
			db.commit();
		} catch (Exception ex) {
			try {
				if (db != null)
					db.rollback();
			} catch (Exception ex2) {
				throw ex2;
			}

			log.error("exception",ex);
			Message.setErrorMsg(request,ex.getMessage());
			//throw ex;
			forword = "error";
		} finally {
			try {
				if (db != null)
					db.close();
			} catch (Exception ex3) {
			}
		}

		// ����ת���־
		return mapping.findForward(forword);
	}

	/**
	 * ִ���߼�����
	 * 
	 * @param db
	 *            ���ݿ����Ӷ��󣬸�������ִ����ɺ��Զ�����
	 * @param form
	 *            formbean�������ڴ���ҳ����Action֮������ݣ� ���ᳫʹ��request��response�������ݴ���
	 * @return �ַ������͵�ת���־
	 * @throws Exception
	 */
	protected abstract String execute(HttpServletRequest request,HttpServletResponse response,DBOperation db, WebForm form)
			throws Exception;

	/**
	 * @return Http ����
	 */
	/*protected HttpServletRequest getRequest() {
		return request;
	}*/

	/**
	 * @return Http ��Ӧ
	 */
	/*protected HttpServletRequest getResponse() {
		return request;
	}*/

	/*protected void setMessage(String strMessage) {
		setMessage(strMessage,null,null);
	}
	protected void setMessage(String strMessage,String strBtnName,String strUrl) {
		getRequest().setAttribute(Constants.LOGIC_MESSAGE, strMessage);
		getRequest().setAttribute("btnName", strBtnName);
		getRequest().setAttribute("url", strUrl);
	}*/
	
}
/*
 * Created on 2007-2-2 by zhuxiang
 * AutoConfigAction.java
 * TODO 
 */
package com.magic.crm.user.action;

import java.io.PipedInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.util.DBManager;
import com.magic.crm.user.dao.AutoConfigDAO;

/**
 * @author zhuxiang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AutoConfigAction extends DispatchAction {
	
	/**
	 * 列出表所有数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 
	public ActionForward list(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = DBManager.getConnection();
		try {
			DynaActionForm fm = (DynaActionForm) form;
			String selTable = fm.getString("table_select");
			
			// 如果选中了某个表，则列出表中的数据
			if (selTable != null && !"".equals(selTable)) {
				LinkedHashMap hm = AutoConfigDAO.getColumnInfo(conn,selTable);
				fm.set("column_info",hm);
				LinkedHashMap hm_data = AutoConfigDAO.getDataList(conn,selTable,hm);
				fm.set("column_data",hm_data);
			}
			
			ArrayList array = AutoConfigDAO.listTable(conn);
			fm.set("table_list",array);
			
		//} catch (Exception e) {
		//	Message.setErrorMsg(request,e.toString());
		//	return mapping.findForward("message");
		} finally {
			conn.close();
		}
		return mapping.findForward("list");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward show(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = DBManager.getConnection();
		try {
			DynaActionForm fm = (DynaActionForm) form;
			String selTable = fm.getString("table_select");
			
			if (selTable != null && !"".equals(selTable)) {
				LinkedHashMap hm = AutoConfigDAO.getColumnInfo(conn,selTable);
				fm.set("column_info",hm);
				String op_type = fm.getString("op_type");
				if(op_type.equals("update")) {
					fm.set("column_data",AutoConfigDAO.getDataInfo(conn,selTable,hm,request.getParameterMap()));
				}
			}
			
		//} catch (Exception e) {
		//	Message.setErrorMsg(request,e.toString());
		//	return mapping.findForward("message");
		} finally {
			conn.close();
		}
		return mapping.findForward("show");
	}
	
	/**
	 * 插入数据，然后回到列表页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insert(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = DBManager.getConnection();
		try {
			DynaActionForm fm = (DynaActionForm) form;
			String selTable = fm.getString("table_select");
			if (selTable != null && !"".equals(selTable)) {
				LinkedHashMap hm = AutoConfigDAO.getColumnInfo(conn,selTable);
				AutoConfigDAO.insertData(conn,selTable,hm,request.getParameterMap());
			}
		//} catch (Exception e) {
		//	Message.setErrorMsg(request,e.toString());
		//	return mapping.findForward("message");
		} finally {
			conn.close();
		}
		return mapping.findForward("ret_list");
	}
	
	/**
	 * 更新数据，然后回到列表页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = DBManager.getConnection();
		try {
			DynaActionForm fm = (DynaActionForm) form;
			String selTable = fm.getString("table_select");
			if (selTable != null && !"".equals(selTable)) {
				LinkedHashMap hm = AutoConfigDAO.getColumnInfo(conn,selTable);
				AutoConfigDAO.updateData(conn,selTable,hm,request.getParameterMap());
			}
			
		//} catch (Exception e) {
		//	Message.setErrorMsg(request,e.toString());
		//	return mapping.findForward("message");
		} finally {
			conn.close();
		}
		return mapping.findForward("ret_list");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Connection conn = DBManager.getConnection();
		try {
			DynaActionForm fm = (DynaActionForm) form;
			String selTable = fm.getString("table_select");
			if (selTable != null && !"".equals(selTable)) {
				LinkedHashMap hm = AutoConfigDAO.getColumnInfo(conn,selTable);
				AutoConfigDAO.deleteData(conn,selTable,hm,request.getParameterMap());
			}
			
		//} catch (Exception e) {
		//	Message.setErrorMsg(request,e.toString());
		//	return mapping.findForward("message");
		} finally {
			conn.close();
		}
		return mapping.findForward("ret_list");
	}
	
}

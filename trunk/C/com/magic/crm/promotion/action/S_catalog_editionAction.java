/* 
 * @author CodeGen 0.1 
 * create on Thu Sep 04 09:24:48 CST 2008
 * 
 * todo 
 */ 

package com.magic.crm.promotion.action;

import java.sql.Connection;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import java.util.*;

import com.magic.crm.promotion.form.*;
import com.magic.crm.promotion.dao.*;
import com.magic.crm.util.*;
/* 
 * this class is generated by codeGen 0.1 
 * create on Thu Sep 04 09:24:48 CST 2008
 * 
 * todo 
 */ 
public class S_catalog_editionAction extends DispatchAction { 

	/** 
	 * 查询数据信息action  
	 * @param mapping struts mapping 
	 * @param form struts form 
	 * @param request request 
	 * @param response response 
	 * @return ActionForward 需要迁移的页面  
	 * @throws Exception 出现异常则抛出 
	 */ 
	public ActionForward initAdd(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception { 
		Connection conn = null; 
		S_catalog_editionForm fm = (S_catalog_editionForm) form;
		try { 
			conn = DBManager.getConnection(); 
			//int ret = S_catalog_editionDao.selectByPK(conn,fm);
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request,e.getMessage());
			return mapping.findForward("error");
		} finally {
			if(conn!=null) try {conn.close();} catch(Exception e) {};
		}
		return mapping.findForward("addjsp");
	}
	
	/** 
	 * 查询数据信息action  
	 * @param mapping struts mapping 
	 * @param form struts form 
	 * @param request request 
	 * @param response response 
	 * @return ActionForward 需要迁移的页面  
	 * @throws Exception 出现异常则抛出 
	 */ 
	public ActionForward initEdit(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception { 
		Connection conn = null; 
		S_catalog_editionForm fm = (S_catalog_editionForm) form;
		try { 
			conn = DBManager.getConnection(); 
			int ret = S_catalog_editionDao.selectByPK(conn,fm);
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request,e.getMessage());
			return mapping.findForward("error");
		} finally {
			if(conn!=null) try {conn.close();} catch(Exception e) {};
		}
		return mapping.findForward("editjsp");
	}

	/** 
	 * 显示所有数据action  
	 * @param mapping struts mapping 
	 * @param form struts form 
	 * @param request request 
	 * @param response response 
	 * @return ActionForward 需要迁移的页面  
	 * @throws Exception 出现异常则抛出 
	 */ 
	public ActionForward list(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception { 
		Connection conn = null; 
		S_catalog_editionForm fm = (S_catalog_editionForm) form;
		try { 
			conn = DBManager.getConnection(); 
			ArrayList al = S_catalog_editionDao.queryAll(conn);
			request.setAttribute("list",al);
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request,e.getMessage());
			return mapping.findForward("error");
		} finally {
			if(conn!=null) try {conn.close();} catch(Exception e) {};
		}
		return mapping.findForward("queryjsp");
	}

	/** 
	 * 新增数据action  
	 * @param mapping struts mapping 
	 * @param form struts form 
	 * @param request request 
	 * @param response response 
	 * @return ActionForward 需要迁移的页面  
	 * @throws Exception 出现异常则抛出 
	 */ 
	public ActionForward insert(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception { 
		Connection conn = null; 
		S_catalog_editionForm fm = (S_catalog_editionForm) form;
		try { 
			conn = DBManager.getConnection(); 
			int ret = S_catalog_editionDao.insert(conn,fm);
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request,e.getMessage());
			return mapping.findForward("error");
		} finally {
			if(conn!=null) try {conn.close();} catch(Exception e) {};
		}
		return mapping.findForward("query");
	}

	/** 
	 * 更新数据action  
	 * @param mapping struts mapping 
	 * @param form struts form 
	 * @param request request 
	 * @param response response 
	 * @return ActionForward 需要迁移的页面  
	 * @throws Exception 出现异常则抛出 
	 */ 
	public ActionForward update(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception { 
		Connection conn = null; 
		S_catalog_editionForm fm = (S_catalog_editionForm) form;
		try { 
			conn = DBManager.getConnection(); 
			int ret = S_catalog_editionDao.updateByPK(conn,fm);
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request,e.getMessage());
			return mapping.findForward("error");
		} finally {
			if(conn!=null) try {conn.close();} catch(Exception e) {};
		}
		return mapping.findForward("query");
	}

	/** 
	 * 删除数据action  
	 * @param mapping struts mapping 
	 * @param form struts form 
	 * @param request request 
	 * @param response response 
	 * @return ActionForward 需要迁移的页面  
	 * @throws Exception 出现异常则抛出 
	 */ 
	public ActionForward delete(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception { 
		Connection conn = null; 
		S_catalog_editionForm fm = (S_catalog_editionForm) form;
		try { 
			conn = DBManager.getConnection(); 
			int ret = S_catalog_editionDao.deleteByPK(conn,fm);
		} catch(Exception e) {
			e.printStackTrace();
			Message.setErrorMsg(request,e.getMessage());
			return mapping.findForward("error");
		} finally {
			if(conn!=null) try {conn.close();} catch(Exception e) {};
		}
		return mapping.findForward("query");
	}
} 
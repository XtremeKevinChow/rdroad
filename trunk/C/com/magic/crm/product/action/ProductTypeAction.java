/*
 * Created on 2005-11-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.product.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.exception.JException;
import com.magic.crm.product.Constants;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.dao.ProductTypeDAO;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.product.form.ProductTypeForm;
import com.magic.crm.product.entity.Product;
import com.magic.crm.product.entity.ProductType;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProductTypeAction extends DispatchAction {

	private static Logger log = Logger.getLogger("ProductTypeAction.class");

	/**
	 * 显示新增页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductTypeForm data = (ProductTypeForm) form;
		Connection conn = DBManager.getConnection();
		ProductTypeDAO ptDao = new ProductTypeDAO();
		try {
			//String id = request.getParameter("id");
			String id = request.getParameter("ID");
			if ("root".equals(id)) {
				id = "0";
			}
			ProductTypeForm src = ptDao.showDetail(conn, Integer.parseInt(id));
			String level = request.getParameter("level");
			data.copy(src);
			data.setParentType(Integer.parseInt(id));
			data.setParentTypeName(data.getName());
			data.setCategoryLevel(data.getCategoryLevel() + 1);
			data.setCatalogCode(null);
			data.setName(null);
			data.setIsLeaf(0);
			data.setDescription(null);
			//System.out.println(data.getID());
			//request.setAttribute("data", data);
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("add");
	}

	/**
	 * 添加记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductTypeForm data = (ProductTypeForm) form;
		ProductTypeDAO ptDao = new ProductTypeDAO();
		Connection conn = DBManager.getConnection();
		try {
			ptDao.insert(conn, data);

		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query1");
	}

	/**
	 * 显示修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductTypeForm data = (ProductTypeForm) form;
		ProductTypeDAO ptDao = new ProductTypeDAO();
		Connection conn = DBManager.getConnection();
		try {

			String id = request.getParameter("id");
			if ("root".equals(id)) {
				id = "0";
			}
			ProductTypeForm src = ptDao.showDetail(conn, Integer.parseInt(id));

			data.copy(src);
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("modify");
	}

	/**
	 * 修改记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductTypeForm data = (ProductTypeForm) form;
		ProductTypeDAO ptDao = new ProductTypeDAO();
		Connection conn = DBManager.getConnection();
		try {
			ptDao.update(conn, data);

		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query1");
	}

	/**
	 * 删除记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductTypeForm data = (ProductTypeForm) form;
		
		//String id = request.getParameter("id");
		String id = request.getParameter("ID");
		int ret = Integer.parseInt(id);
		if (ret<1000) {
			Message.setMessage(request, "系统分类，不能删除");
			return mapping.findForward("delete");
		}
		
		ProductTypeDAO ptDao = new ProductTypeDAO();
		Connection conn = DBManager.getConnection();
		Connection conn2 = DBManager.getConnection();
		Connection conn3 = DBManager.getConnection();
		
		try {
			if (ptDao.hasChildren(conn2, Integer.parseInt(id))) {
				ControlledError ctlErr = new ControlledError();

				Message.setMessage(request, "删除失败，该类型有子类型");

				return mapping.findForward("delete");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (conn2 != null)
				conn2.close();
		}

		try {
			if (ptDao.hasProduct(conn3, Integer.parseInt(id))) {
				ControlledError ctlErr = new ControlledError();

				Message.setMessage(request, "删除失败，该类型有产品");

				return mapping.findForward("delete");
			}
		} catch (Exception e) {

		} finally {
			try {
				conn3.close();
			} catch (Exception e) {
			}
			;
		}

		try {

			data.setID(Integer.parseInt(id));
			ptDao.delete(conn, data);
			Message.setMessage(request, "删除成功");
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("delete");
	}

	/**
	 * 查询所有记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductTypeForm data = (ProductTypeForm) form;
		ProductTypeDAO ptDao = new ProductTypeDAO();
		Connection conn = DBManager.getConnection();
		try {

			//1 查询信息
			Collection ret = ptDao.getList(conn);
			//data.setList((ArrayList)ret);
			request.setAttribute("list", ret);
		} catch (Exception e) {
			log.error("exception:", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
			;
		}

		return mapping.findForward("query");
	}

	/**
	 * 一个产品类型对应所有的产品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryProductByCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductDAO pd = new ProductDAO();
		
		Connection conn = null;
		
		String categoryID = request.getParameter("ID");
		String qty = request.getParameter("qty");
		request.setAttribute("typeid",categoryID);
		request.setAttribute("qty", qty);
		String s_pageNum = request.getParameter("pageNo");
	
		int pageNum = 1;


		try {
			conn = DBManager.getConnection();
			CommonPageUtil pageModel = new CommonPageUtil();
			if (s_pageNum != null && !"".equals(s_pageNum)) {
				pageNum = Integer.parseInt(s_pageNum);
			}
			pageModel.setPageNo(pageNum);
			HashMap hashmap = new HashMap();
			hashmap.put("categoryID", categoryID);
			hashmap.put("qty", qty);
			pageModel.setCondition(hashmap);
			pageModel = pd.queryGroup(conn, pageModel);
			
			request.setAttribute(Constants.PAGE_MODEL, pageModel);
			return mapping.findForward("queryById");

		} catch (SQLException se) {
			throw new JException("查询产品出错！");
		} finally {
			try {
				conn.close();
			} catch (SQLException sqe) {
				throw new ServletException(sqe);
			}

		}
	}
	
	public ActionForward moveCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductTypeForm pageData = (ProductTypeForm)form;
		ProductTypeDAO productTypeDAO = new ProductTypeDAO();
		ProductDAO productDAO = new ProductDAO();
		Connection conn = null;
		
		//String oldCategoryID = request.getParameter("ID");
		
		String newTypeCode = request.getParameter("newType");
		
		
		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			ProductTypeForm permanenceData = productTypeDAO.showDetail(conn, newTypeCode);
			ControlledError ctlErr = new ControlledError();
			if (permanenceData != null && permanenceData.getID() != 0) {// ok
				productDAO.changeNewType(conn, pageData.getID(), permanenceData.getID());
				

				ctlErr.setErrorTitle("提示");

				ctlErr.setErrorBody("批量更改产品类型成功！");

				
			}  else {
				ctlErr.setErrorTitle("提示");

				ctlErr.setErrorBody("批量更新失败，您输入的类型不存在！");
			}
			conn.commit();
			request.setAttribute(com.magic.crm.util.Constants.ERROR_KEY,
					ctlErr);
			return mapping.findForward("controlledError");

		} catch (SQLException se) {
			conn.rollback();
			throw new JException("批量更新出错！");
		} finally {
			try {
				conn.close();
			} catch (SQLException sqe) {
				throw new ServletException(sqe);
			}

		}
	}
}
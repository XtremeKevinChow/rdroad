//Source file: C:\\j2sdk1.4\\lib\\com\\fechina\\ccms\\message\\action\\SendMsgAction.java

package com.magic.crm.product.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.exception.JException;
import com.magic.crm.product.Constants;
import com.magic.crm.product.dao.ProductCategoryDAO;
import com.magic.crm.product.entity.ProductCategory;
import com.magic.crm.product.form.ProductCategoryForm;
import com.magic.crm.util.DBManager;

/**
 * Implementation of <strong>Action </strong> that populates an instance of
 * <code>SendMsgActionAction</code>
 * 
 * @author Kevin zhou
 * @version 1.0
 */
public final class PrdCatQueryAction extends Action {

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it).
	 * Return an <code>ActionForward</code> instance describing where and how
	 * control should be forwarded, or <code>null</code> if the response has
	 * already been completed.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * 
	 * @return Action to forward to
	 * @throws java.lang.Exception
	 * @exception Exception
	 *                if an input/output error or servlet exception occurs
	 * @roseuid 3ED6F16602B4
	 */
	private static Logger log = Logger.getLogger("InitPrdCatQueryAction.class");

	private static ServletContext context;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		/*if (request.getMethod().equals("GET")) {
			CommonPageUtil pageModel = new CommonPageUtil();
			request.setAttribute(Constants.PAGE_MODEL,pageModel);
			return mapping.findForward("success");
		}*/
		// 查询
		ProductCategoryDAO pd = new ProductCategoryDAO();
		ProductCategory product = new ProductCategory();
		Connection conn = null;

		//HashMap map = (HashMap)
		// request.getSession().getServletContext().getAttribute("rowNumberMap");

		String s_pageNum = request.getParameter("pageNo");

		int pageNum = 1;

		ProductCategoryForm pf = (ProductCategoryForm) form;

		try {
			conn = DBManager.getConnection();
			CommonPageUtil pageModel = new CommonPageUtil();
			if (s_pageNum != null && !"".equals(s_pageNum)) {
				pageNum = Integer.parseInt(s_pageNum);
			}
			pageModel.setPageNo(pageNum);
			HashMap hashmap = new HashMap();
			hashmap.put("prdCatName", pf.getCatalogName());
			hashmap.put("prdCatCode", pf.getCatalogCode());
			pageModel.setCondition(hashmap);

			pageModel = pd.query(conn, pageModel);

			request.setAttribute("actn", "1");

			request.setAttribute(Constants.PAGE_MODEL, pageModel);

			return mapping.findForward("success");

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

}
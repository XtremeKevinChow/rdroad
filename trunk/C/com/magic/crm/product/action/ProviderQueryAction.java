//Source file: C:\\j2sdk1.4\\lib\\com\\fechina\\ccms\\message\\action\\SendMsgAction.java

package com.magic.crm.product.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.exception.JException;
import com.magic.crm.product.Constants;
import com.magic.crm.product.dao.ProviderDAO;
import com.magic.crm.product.entity.Provider;
import com.magic.crm.product.form.ProviderForm;
import com.magic.crm.util.DBManager;

/**
 * Implementation of <strong>Action </strong> that populates an instance of
 * <code>SendMsgActionAction</code>
 * 
 * @author Kevin zhou
 * @version 1.0
 */
public final class ProviderQueryAction extends Action {

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
	private static Logger log = Logger.getLogger("ProviderQueryAction.class");

	private static ServletContext context;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (request.getMethod().equals("GET")) {
			   String show_pro_no=request.getParameter("show_pro_no");
			   show_pro_no=(show_pro_no==null)?"":show_pro_no;			
			   String go_path="";
			   
			   if(show_pro_no.equals("1")){
			   		go_path="pro_no";
			   }else{
			   		go_path="success";
			   }
			return mapping.findForward(go_path);
		}
		// 查询
		ProviderDAO pd = new ProviderDAO();
		Provider product = new Provider();
		Connection conn = null;
		Collection col = null;

		//HashMap map = (HashMap)
		// request.getSession().getServletContext().getAttribute("rowNumberMap");

		String s_pageNum = request.getParameter("pageNo");

		int pageNum = 1;

		ProviderForm pf = (ProviderForm) form;
		   String show_pro_no=request.getParameter("show_pro_no");
		   show_pro_no=(show_pro_no==null)?"":show_pro_no;			
		   String go_path="";
		   if(show_pro_no.equals("1")){
		   		go_path="pro_no";
		   }else{
		   		go_path="success";
		   }

		try {
			conn = DBManager.getConnection();
			if (s_pageNum != null && !"".equals(s_pageNum)) {
				pageNum = Integer.parseInt(s_pageNum);
			}
			
			col = pd.query(conn, pf);

			request.setAttribute("actn", "1");

			request.setAttribute(Constants.PROVIDER_LIST, col);

			return mapping.findForward(go_path);

		} catch (SQLException se) {
			throw new JException("查询供应商出错！");
		} finally {
			try {
				conn.close();
			} catch (SQLException sqe) {
				throw new ServletException(sqe);
			}

		}
	}

}
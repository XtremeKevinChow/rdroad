/*
 * Created on 2005-3-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.product.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.exception.JException;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.entity.Product;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductViewItemAction extends Action {


	private static ServletContext context;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductDAO pd = new ProductDAO();
		ProductForm psf = (ProductForm) form;
		ProductForm pf = new ProductForm();
		Product p=new Product();
		Connection conn = null;
		ProductDAO productDAO=new ProductDAO();

		String actn = request.getParameter("actn");				
		System.out.println("actn is "+actn);
		if(actn!=null && actn.equals("view")) {
						
			try {
				conn = DBManager.getConnection();
				boolean bol = true;

				String item_id=request.getParameter("item_id");

				Collection productCol = new ArrayList();
				Collection productDetailCol = new ArrayList();
				/*
				 * 显示套装产品套件信息
				 */				
				productCol=productDAO.viewProduct(conn,item_id);
				request.setAttribute("item",productCol);
				/*
				 * 显示套装产品主体信息
				 */	
				productDetailCol=productDAO.viewProductDetail(conn,item_id);
				request.setAttribute("itemdetail",productDetailCol);
				return mapping.findForward("view");
			} catch (SQLException se) {
				throw new JException("设置套装产品出错，请与系统管理员联系！");
			} finally {
				try {
					conn.close();
				} catch (SQLException sqe) {
					throw new ServletException(sqe);
				}

			}

			
		}
		return mapping.findForward("view");
	}
}

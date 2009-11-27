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

import com.magic.crm.product.Constants;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.entity.Product;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.exception.JException;
import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.util.DBManager;


/**
 * Implementation of <strong>Action</strong> that populates an instance of
 * <code>SendMsgActionAction</code>
 * @author Kevin zhou
 * @version 1.0
 */
public final class ProductAQueryAction
    extends Action {

  /**
   *     Process the specified HTTP request, and create the corresponding HTTP
   *       response (or forward to another web component that will create it).
       *       Return an <code>ActionForward</code> instance describing where and how
       *       control should be forwarded, or <code>null</code> if the response has
   *       already been completed.
   *
   *       @param mapping The ActionMapping used to select this instance
   *       @param form The optional ActionForm bean for this request (if any)
   *       @param request The HTTP request we are processing
   *       @param response The HTTP response we are creating
   *
   *       @return Action to forward to
   *       @throws java.lang.Exception
   * @exception Exception if an input/output error or servlet exception occurs
   * @roseuid 3ED6F16602B4
   */
  private static Logger log = Logger.getLogger("ProductQueryAction.class");

  private static ServletContext context;

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
      Exception {
  	ProductDAO pd = new ProductDAO();
    Product product = new Product();
    Connection conn = null;

    if (request.getMethod().equals("GET")) {
		CommonPageUtil pageModel = new CommonPageUtil();
		request.setAttribute(Constants.PAGE_MODEL,pageModel);
		return mapping.findForward("success");
	}
    
    String s_pageNum = request.getParameter("pageNo");
    String actn = request.getParameter("actn");
    int pageNum = 1;
    
    ProductForm pf = (ProductForm) form;

    try {
      conn = DBManager.getConnection();
      CommonPageUtil pageModel = new CommonPageUtil();
      if (s_pageNum != null && !"".equals(s_pageNum)) {
        pageNum = Integer.parseInt(s_pageNum);
      }
      pageModel.setPageNo(pageNum);
      
      HashMap hashmap = new HashMap();
      hashmap.put("categoryID", Integer.toString(pf.getCategoryID()));
      hashmap.put("productType",Integer.toString(pf.getItemType()));
      hashmap.put("standardPrice1",request.getParameter("standardPrice1"));
      hashmap.put("standardPrice2",request.getParameter("standardPrice2"));
	  hashmap.put("webPrice1",request.getParameter("webPrice1"));
	  hashmap.put("webPrice2",request.getParameter("webPrice2"));
	  hashmap.put("silverPrice1",request.getParameter("silverPrice1"));
	  hashmap.put("silverPrice2",request.getParameter("silverPrice2"));
	  hashmap.put("godenPrice1",request.getParameter("godenPrice1"));
	  hashmap.put("godenPrice2",request.getParameter("godenPrice2"));
	  hashmap.put("platina_Price1",request.getParameter("platina_Price1"));
	  hashmap.put("platina_Price2",request.getParameter("platina_Price2"));	  
	  hashmap.put("discount1",request.getParameter("discount1"));
	  hashmap.put("discount2",request.getParameter("discount2"));
	  hashmap.put("supplierID",request.getParameter("supplierID"));
	  hashmap.put("publishingHouse",request.getParameter("publishingHouse"));
	  hashmap.put("ifPresell",request.getParameter("ifPresell"));
	  hashmap.put("clubID",request.getParameter("clubID"));
	  hashmap.put("isSet",request.getParameter("isSet"));
	  hashmap.put("ageSegment",request.getParameter("ageSegment"));
	  
      //如果actn = "selectProduct" 则表示是从选择产品的页面提交的，将是否套装属性置为n，查询结果不返回套装产品
      //if (actn.equals("selectProduct")) hashmap.put("isSet","n");
      
      pageModel.setCondition(hashmap);

      //if (map.get("message") != null) {
      //  pageModel.setPageSize(((Integer)(map.get("message"))).intValue());
      //  log.info("page size:"+((Integer)(map.get("message"))).intValue());
      //}

      pageModel = pd.advancequery(conn, pageModel);	      
      request.setAttribute(Constants.PAGE_MODEL, pageModel);
                 
      request.setAttribute("actn",actn);
      
      
      //request.setAttribute(Constants.PRODUCT_LIST,pageModel.getModelList());
            
      return mapping.findForward("success");
      
    }
    catch (SQLException se) {
      throw new JException("查询产品出错！");
    }
    finally {
      try {
        conn.close();
      }
      catch (SQLException sqe) {
        throw new ServletException(sqe);
      }

    }
    
  }

  
}
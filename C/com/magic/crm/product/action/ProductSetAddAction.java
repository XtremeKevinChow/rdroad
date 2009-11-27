//Source file: C:\\j2sdk1.4\\lib\\com\\fechina\\ccms\\message\\action\\SendMsgAction.java

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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import com.magic.crm.product.entity.*;
import com.magic.crm.product.dao.ProductDAO;

//import com.magic.crm.product.form.ProductSetForm;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.exception.JException;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;

/**
 * Implementation of <strong>Action</strong> that populates an instance of
 * <code>SendMsgActionAction</code>
 * @author Kevin zhou
 * @version 1.0
 */
public final class ProductSetAddAction extends Action {

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
	private static Logger log = Logger.getLogger("ProductSetAddAction.class");

	private static ServletContext context;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductDAO pd = new ProductDAO();
		ProductForm psf = (ProductForm) form;
		ProductForm pf = new ProductForm();
		Product p=new Product();
		Connection conn = null;

		User user = (User) request.getSession().getAttribute("user");
		//pf.setOperatorID(user.getId());
		//pf.setOperatorID(user.getId());
		psf.setOperatorID(Integer.parseInt(user.getId()));		
		//psf.setClubID(request.getParameter("club_id"));
		if (request.getMethod().equals("GET")) {
			psf.reset();
			psf.setWebPrice(0);
			psf.setPlatina_Price(0);
			return mapping.findForward("init");
		}
		String actn = request.getParameter("actn");
		if (actn != null && actn.equals("del")) {
			String index = request.getParameter("index");
			if (index != null && psf.getItem().size() > Integer.parseInt(index)) {
				ProductForm tpf = new ProductForm();
				tpf = (ProductForm) psf.getItem().get(Integer.parseInt(index));
				psf.getItem().remove(Integer.parseInt(index));
				psf.setSilverPrice(psf.getSilverPrice()
						- tpf.getSilverPrice());
				psf.setGodenPrice(psf.getGodenPrice() - tpf.getGodenPrice());
				psf.setWebPrice(psf.getWebPrice() - tpf.getWebPrice());
				psf.setStandardPrice(psf.getStandardPrice() - tpf.getStandardPrice());
				psf.setPlatina_Price(psf.getPlatina_Price() - tpf.getPlatina_Price());
				
			}
		} else if(actn!=null && actn.equals("add")) {
			/*
			 * 页面上收集套装产品
			 */
			try {
				conn = DBManager.getConnection();
				boolean bol = true;
				for (int i = 0; i < psf.getItem().size(); i++) {
					pf = (ProductForm) psf.getItem().get(i);
					if (pf.getItemID().equals(psf.getItemID())) {
						bol = false;
					}
				}
				
				if (bol) {
					pf = pd.findByCode(conn, psf.getItemCode());			
					psf.getItem().add(pf);
					psf.setSilverPrice(psf.getSilverPrice()
							+ pf.getSilverPrice());
					psf.setGodenPrice(psf.getGodenPrice()
							+ pf.getGodenPrice());
					psf.setWebPrice(psf.getWebPrice() + pf.getWebPrice());
					psf.setStandardPrice(psf.getStandardPrice() + pf.getStandardPrice());
					
					psf.setPlatina_Price(psf.getPlatina_Price() + pf.getPlatina_Price());

				}
			} catch (SQLException se) {
				throw new JException("设置套装产品出错，请与系统管理员联系！");
			} finally {
				try {
					conn.close();
				} catch (SQLException sqe) {
					throw new ServletException(sqe);
				}

			}
			
		}else if(actn!=null && actn.equals("submit")){
			/*
			 * 套装产品入库
			 */
			String[] itemcode=request.getParameterValues("itemCode");
			try {
				conn = DBManager.getConnection();
				
				/*
				 * 执行两个动作,更新3张表
				 * 1、插入套装表
				 * 2、插入产品单品表
				 * 3、折扣 含税成本和/含税定价和 discount
				 * 4、成本：套件成本和 unpurchasing_cost
				 * 5、不含税成本：套件不含税成本和 purchasing_cost
				 */
				//含税成本和	
				float pcost=0;
				//不含税成本和
				float upcost=0;

				for (int i = 0; i < psf.getItem().size(); i++) {
					pf = (ProductForm) psf.getItem().get(i);
					float sprice = psf.getArraySilverPrice()[i];
					float gprice = psf.getArrayGodenPrice()[i];
					float wprice = psf.getArrayWebPrice()[i];
					float pprice = psf.getArrayPlatina_Price()[i];
					
                    
					pcost +=pf.getPurchasingCost();
					upcost +=pf.getUnpurchasingCost();
					if(psf.getArraySilverPrice()[i]>0){
						pf.setSilverPrice(sprice);//套件普通价格
					}else{
						pf.setSilverPrice(0);//套件普通价格
					}
					if(psf.getArrayGodenPrice()[i]>0){
						pf.setGodenPrice(gprice);//套件金卡价格
					}else{
						pf.setGodenPrice(0);//套件金卡价格
					}
					if(psf.getArrayPlatina_Price()[i]>0){
						pf.setPlatina_Price(pprice);//套件白金卡价格	
					}else{
						pf.setPlatina_Price(0);//套件白金卡价格	
					}	
					if(psf.getArrayWebPrice()[i]>0){
						pf.setWebPrice(wprice);//套件网上价格	
					}else{
						pf.setWebPrice(0);//套件网上价格	
					}
					
				}
				
				psf.setDiscount(pcost/psf.getStandardPrice());
				psf.setPurchasingCost(pcost);
				psf.setUnpurchasingCost(upcost);
				psf.setProductOwnerID(Integer.parseInt(user.getId()));
				String item_code="";			
				item_code=pd.insertSet(conn,psf,pf);
				
				request.setAttribute("item_code",item_code);				
				return mapping.findForward("addok");
				
			} catch (SQLException se) {
				se.printStackTrace();
				
			} finally {
				try {
					conn.close();
				} catch (SQLException sqe) {
					throw new ServletException(sqe);
				}
			}
		}
		return mapping.findForward("init");
	}

}
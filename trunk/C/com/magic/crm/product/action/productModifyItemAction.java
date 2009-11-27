/*
 * Created on 2005-3-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.product.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.exception.JException;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.entity.Product;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.user.entity.RoleRight;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class productModifyItemAction extends Action {

	private static ServletContext context;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm psf = (ProductForm) form;
		ProductForm pf = new ProductForm();
		Product p=new Product();
		ProductDAO productDAO=new ProductDAO();
		Connection conn = null;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Collection productCol = new ArrayList();
		Collection productDetailCol = new ArrayList();
		String item_id=request.getParameter("item_id");
		
		String actn = request.getParameter("actn");
		//System.out.println("item_id is "+item_id);
		//System.out.println("actn is "+actn);
		int isLastSell=0;
		int isPreSell=0;
		try {
			conn = DBManager.getConnection();
			productCol=productDAO.viewProductDetail(conn,item_id);
			request.setAttribute("item",productCol);
			//add by user
			isLastSell = ((Product)(productCol.iterator().next())).getIsLastSel();
			isPreSell = ((Product)(productCol.iterator().next())).getifPresell();
			
		} catch (SQLException se) {
			throw new JException("��װ��Ʒ��������ϵͳ����Ա��ϵ��");
		} finally {
			try {
				conn.close();
			} catch (SQLException sqe) {
				throw new ServletException(sqe);
			}

		}			
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
			 * ҳ�����ռ���װ��Ʒ
			 */

			try {
				conn = DBManager.getConnection();
				//productCol=productDAO.viewProduct(conn,item_id);
				//Iterator plist = productCol.iterator();				
				boolean bol = true;
				for (int i = 0; i < psf.getItem().size(); i++) {
					pf = (ProductForm) psf.getItem().get(i);
					if (pf.getItemID().equals(psf.getItemID())) {
						bol = false;
					}
					
				}
				if (bol) {
					pf = productDAO.findByCode(conn, psf.getItemCode());
					psf.getItem().add(pf);
					psf.setSilverPrice(psf.getSilverPrice()
							+ pf.getSilverPrice());
					psf.setGodenPrice(psf.getGodenPrice()
							+ pf.getGodenPrice());
					psf.setWebPrice(psf.getWebPrice() + pf.getWebPrice());
					psf.setStandardPrice(psf.getStandardPrice() + pf.getStandardPrice());
					psf.setPlatina_Price(psf.getPlatina_Price() + pf.getPlatina_Price());
				}	
				/*
                while (plist.hasNext()) {
					pf = (ProductForm) plist.next();  
					psf.getItem().add(pf);
					psf.setSilverPrice(psf.getSilverPrice()
							+ pf.getSilverPrice());
					psf.setGodenPrice(psf.getGodenPrice()
							+ pf.getGodenPrice());
					psf.setWebPrice(psf.getWebPrice() + pf.getWebPrice());
					psf.setStandardPrice(psf.getStandardPrice() + pf.getStandardPrice());             	
              }		
              		*/
			} catch (SQLException se) {
				se.printStackTrace();
				throw new JException("�ռ���װ��Ʒ��������ϵͳ����Ա��ϵ��");
			} finally {
				try {
					conn.close();
				} catch (SQLException sqe) {
					throw new ServletException(sqe);
				}

			}
		}else if(actn!=null && actn.equals("modify")) {
			/*
			 * ҳ�����ռ���װ��Ʒ
			 */
			try {
				conn = DBManager.getConnection();
				productCol=productDAO.viewProduct(conn,item_id);
				boolean bol = true;
				
				//psf.getItem().clear();
				
				psf.reset();
				psf.setifPresell(isPreSell);
				psf.setIsLastSel(isLastSell);
				Iterator plist = productCol.iterator();
				float a=0;
                while (plist.hasNext()) {
    					pf = (ProductForm) plist.next();  
    					psf.getItem().add(pf);
    					psf.setSilverPrice(psf.getSilverPrice()
    							+ pf.getSilverPrice());
    					psf.setGodenPrice(psf.getGodenPrice()
    							+ pf.getGodenPrice());
    					psf.setWebPrice(psf.getWebPrice() + pf.getWebPrice());
    					psf.setStandardPrice(psf.getStandardPrice() + pf.getStandardPrice());  
    	      	   		a=a+pf.getLength();
    	   				psf.setUnpurchasingCost(psf.getUnpurchasingCost());
    	   				
                  }
                psf.setPlatina_Price(a);
                psf.setItemType(pf.getItemType());
	
			} catch (SQLException se) {
				throw new JException("�޸�������װ��Ʒ��������ϵͳ����Ա��ϵ��");
			} finally {
				try {
					conn.close();
				} catch (SQLException sqe) {
					throw new ServletException(sqe);
				}

			}	
		}else if(actn!=null && actn.equals("submit")) {
			/*
			 * ҳ�����ռ���װ��Ʒ
			 */

			try {
				conn = DBManager.getConnection();
				
				/*
				 * ִ����������,����3�ű�
				 * 1��������װ��
				 * 2�������Ʒ��Ʒ��
				 * 3���ۿ� ��˰�ɱ���/��˰���ۺ� discount
				 * 4���ɱ����׼��ɱ��� unpurchasing_cost
				 * 5������˰�ɱ����׼�����˰�ɱ��� purchasing_cost
				 */
				//��˰�ɱ���	
				float pcost=0;
				//����˰�ɱ���
				float upcost=0;
				
				for (int i = 0; i < psf.getItem().size(); i++) {
					pf = (ProductForm) psf.getItem().get(i);
					float sprice = Float.parseFloat(request.getParameterValues("arraySilverPrice")[i]);
					float gprice = Float.parseFloat(request.getParameterValues("arrayGodenPrice")[i]);
					float wprice = Float.parseFloat(request.getParameterValues("arrayWebPrice")[i]);
					float pprice = Float.parseFloat(request.getParameterValues("arrayPlatina_Price")[i]);
					pcost +=pf.getPurchasingCost();
					upcost +=pf.getUnpurchasingCost();

					if(sprice>0){
						pf.setSilverPrice(sprice);//�׼���ͨ�۸�
					}else{
						pf.setSilverPrice(0);//�׼���ͨ�۸�
					}
					if(gprice>0){
						pf.setGodenPrice(gprice);//�׼��𿨼۸�
					}else{
						pf.setGodenPrice(0);//�׼��𿨼۸�
					}
					if(psf.getArrayPlatina_Price()[i]>0){
						pf.setPlatina_Price(pprice);//�׼��׽𿨼۸�	
					}else{
						pf.setPlatina_Price(0);//�׼��׽𿨼۸�	
					}					
					if(wprice>0){
						pf.setWebPrice(wprice);//�׼����ϼ۸�	
					}else{
						pf.setWebPrice(0);//�׼����ϼ۸�	
					}
						

				}
				
				psf.setDiscount(pcost/psf.getStandardPrice());
				psf.setPurchasingCost(pcost);
				psf.setUnpurchasingCost(upcost);
				psf.setProductOwnerID(Integer.parseInt(user.getId()));
				psf.setOperatorID(Integer.parseInt(user.getId())); 
				psf.setItemID(item_id);
				//user
				//psf.setIsLastSel(isLastSell);
				//psf.setifPresell(isPreSell);
				productDAO.updateSet(conn,psf,pf);
				ProductDAO.insertHistory(conn,psf,0,"UPDATE");				
                request.setAttribute("item_code","");	
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
		
		return mapping.findForward("view");
	}
}

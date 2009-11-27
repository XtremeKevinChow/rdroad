/*
 * Created on 2006-11-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.promotion.dao.*;
import com.magic.crm.promotion.form.*;
import com.magic.crm.promotion.entity.*;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.product.dao.*;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;
import com.magic.crm.exception.JException;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

	public class Pricelist_lineAction extends DispatchAction {
		public ActionForward init(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
				Pricelist_lineDAO pdao=new Pricelist_lineDAO();
				Pricelist_line p=new Pricelist_line();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
				String page="";
	                String id= request.getParameter("id");
	                id=(id==null)?"":id;
	                if(id.length()>0){
	    				try {
	    					conn = DBManager.getConnection();	    		    
	    					p=pdao.findByPrimaryKey(conn,Integer.parseInt(id));	  
	                        
	    				} catch (SQLException se) {
	    					se.printStackTrace();
	    	
	    				} finally {
	    					try {
	    						conn.close();
	    	
	    					} catch (SQLException sqe) {
	    						sqe.printStackTrace();
	    					}
	    	
	    				}    				
	                }
		                request.setAttribute("p", p);
						return mapping.findForward("add");
	                	
	                

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
      public ActionForward init2(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
					Pricelist_lineDAO pdao=new Pricelist_lineDAO();
					Pricelist_line p=new Pricelist_line();
					User user = new User();
					HttpSession session = request.getSession();
					user = (User) session.getAttribute("user");
					Connection conn = null;
					String page="";
		                String id= request.getParameter("id");
		                id=(id==null)?"":id;
		                if(id.length()>0){
		    				try {
		    					conn = DBManager.getConnection();	    		    
		    					p=pdao.findByPrimaryKey(conn,Integer.parseInt(id));	  
		                        
		    				} catch (SQLException se) {
		    					se.printStackTrace();
		    	
		    				} finally {
		    					try {
		    						conn.close();
		    	
		    					} catch (SQLException sqe) {
		    						sqe.printStackTrace();
		    					}
		    	
		    				}    				
		                }
		                
			            request.setAttribute("p", p);
						return mapping.findForward("add2");
		    }		
			
		public ActionForward add(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
				Pricelist_lineDAO pdao=new Pricelist_lineDAO();
				Pricelist_lineForm info=new Pricelist_lineForm();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
   
				String item_code = request.getParameter("item_id_key");
				String sell_type = request.getParameter("sell_type");
				String sale_price = request.getParameter("sale_price");
				String vip_price = request.getParameter("vip_price");
				String pricelist_id = request.getParameter("pricelist_id");
				String catalog_edition = request.getParameter("catalog_edition");
				String page = request.getParameter("page");
                
				info.setItem_code(item_code);
				info.setSell_type(Integer.parseInt(sell_type));
				info.setSale_price(Double.parseDouble(sale_price));
				info.setVip_price(Double.parseDouble(vip_price));
				info.setPricelist_id(Integer.parseInt(pricelist_id));
				info.setPage(Integer.parseInt(page));
				info.setCatalog_editon(Integer.parseInt(catalog_edition));
				info.setOperator_id(Integer.parseInt(user.getId()));
				
				//插入产品价格历史表信息    
				/*ProductForm pf=new ProductForm();
				pf.setOperatorID(info.getOperator_id());
				pf.setGodenPrice((float)info.getCard_price());
				pf.setSilverPrice((float)info.getCommon_price());
				pf.setPlatina_Price((float)info.getPlatina_Price());
				pf.setItemID(item_id);
			    */
				
				
				try {
					conn = DBManager.getConnection();
					conn.setAutoCommit(false);
					if(pdao.checkLineInsert(conn,info)==0){
						pdao.insert(conn,info);
						//ProductDAO.insertHistory(conn,pf,info.getPricelist_id(),"ADD");
					} else {
						Message.setErrorMsg(request, "目录中该产品已经存在");
						return mapping.findForward("error");
					}
					conn.commit();
				} catch (SQLException se) {
					se.printStackTrace();
					Message.setErrorMsg(request, se.getMessage());
					return mapping.findForward("error");
					
				} finally {
					try {
						conn.setAutoCommit(true);
						conn.close();
	
					} catch (SQLException sqe) {
	
					}
	
				}
				return mapping.findForward("addok");

		}
		
		/**
		 * 增加招募活动行
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward add2(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
				Pricelist_lineDAO pdao=new Pricelist_lineDAO();
				Pricelist_lineForm info=new Pricelist_lineForm();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
   
				String item_code = request.getParameter("item_id_key");
				String sell_type = request.getParameter("sell_type");
				String sale_price = request.getParameter("sale_price");
				String vip_price = request.getParameter("vip_price");
				String pricelist_id = request.getParameter("pricelist_id");
				String catalog_edition = "0";//request.getParameter("catalog_edition");
				String page = "1";//request.getParameter("page");
                
				info.setItem_code(item_code);
				info.setSell_type(Integer.parseInt(sell_type));
				info.setSale_price(Double.parseDouble(sale_price));
				info.setVip_price(Double.parseDouble(vip_price));
				info.setPricelist_id(Integer.parseInt(pricelist_id));
				info.setPage(Integer.parseInt(page));
				info.setCatalog_editon(Integer.parseInt(catalog_edition));
				info.setOperator_id(Integer.parseInt(user.getId()));
				
				//插入产品价格历史表信息    
				/*ProductForm pf=new ProductForm();
				pf.setOperatorID(info.getOperator_id());
				pf.setGodenPrice((float)info.getCard_price());
				pf.setSilverPrice((float)info.getCommon_price());
				pf.setPlatina_Price((float)info.getPlatina_Price());
				pf.setItemID(item_id);
			    */
				
				
				try {
					conn = DBManager.getConnection();
					conn.setAutoCommit(false);
					if(pdao.checkLineInsert(conn,info)==0){
						pdao.insert(conn,info);
						//ProductDAO.insertHistory(conn,pf,info.getPricelist_id(),"ADD");
					} else {
						throw new Exception("该产品已经存在该招募活动中");
					}
					conn.commit();
				} catch (SQLException se) {
					se.printStackTrace();
					throw se;
				} finally {
					try {
						conn.close();
	
					} catch (SQLException sqe) {
	
					}
	
				}
				return mapping.findForward("addok2");

		}
		
		/**
		 * 删除目录
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward operation(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			Pricelist_lineDAO pdao=new Pricelist_lineDAO();
			Pricelist_lineForm info=new Pricelist_lineForm();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
		        String typename=request.getParameter("typename");
				String id = request.getParameter("id");
				System.out.println("start del");
				try {
					conn = DBManager.getConnection();
					if(typename.equals("del")){
						pdao.CatalogLineDel(conn,id,user.getId());
					}
				} catch (SQLException se) {
					se.printStackTrace();
	
				} finally {
					try {
						conn.close();
	
					} catch (SQLException sqe) {
	
					}
	
				}
				return mapping.findForward("addok");

		}	
		
		/**
		 *  删除招募活动行
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward delete2(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			Pricelist_lineDAO pdao=new Pricelist_lineDAO();
			Pricelist_lineForm info=new Pricelist_lineForm();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
		        String typename=request.getParameter("typename");
				String id = request.getParameter("id");
				System.out.println("start del");
				try {
					conn = DBManager.getConnection();
					if(typename.equals("del")){
						pdao.CatalogLineDel(conn,id,user.getId());
					}
				} catch (SQLException se) {
					se.printStackTrace();
	
				} finally {
					try {
						conn.close();
	
					} catch (SQLException sqe) {
	
					}
	
				}
				return mapping.findForward("addok2");

		}	
		
		/**
		 * 目录行修改
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
			Pricelist_lineDAO pdao=new Pricelist_lineDAO();
			Pricelist_lineForm info=new Pricelist_lineForm();
			User user = new User();
			HttpSession session = request.getSession();
			user = (User) session.getAttribute("user");
			Connection conn = null;
			
			String item_code = request.getParameter("item_code");
			String sell_type = request.getParameter("sell_type");
			String sale_price = request.getParameter("sale_price");
			String vip_price = request.getParameter("vip_price");
			String pricelist_id = request.getParameter("pricelist_id");
			String catalog_edition = request.getParameter("catalog_edition");
			String page = request.getParameter("page");
            
			info.setItem_code(item_code);
			info.setSell_type(Integer.parseInt(sell_type));
			info.setSale_price(Double.parseDouble(sale_price));
			info.setVip_price(Double.parseDouble(vip_price));
			info.setPricelist_id(Integer.parseInt(pricelist_id));
			info.setPage(Integer.parseInt(page));
			info.setCatalog_editon(Integer.parseInt(catalog_edition));
			info.setOperator_id(Integer.parseInt(user.getId()));
			
			/*ProductForm pf=new ProductForm();
			pf.setOperatorID(info.getOperator_id());
			pf.setGodenPrice((float)info.getCard_price());
			pf.setSilverPrice((float)info.getCommon_price());
			pf.setPlatina_Price((float)info.getPlatina_Price());
			pf.setItemID(item_id);*/
			try {
				conn = DBManager.getConnection();
				conn.setAutoCommit(false);
					int ret=Pricelist_lineDAO.updateLinesByItemCode(conn,info);
					//int ret=Pricelist_lineDAO.CatalogLineUpdate(conn,info);
					//ProductDAO.insertHistory(conn,pf,info.getPricelist_id(),"UPDATE");
					
					if(ret<0){
						throw new JException("修改目录行信息出错！");
					}
			    conn.commit();
			} catch (SQLException se) {
				se.printStackTrace();

			} finally {
				try {
					conn.close();

				} catch (SQLException sqe) {

				}

			}
				return mapping.findForward("addok");

		}		
		
		/**
		 * 招募活动行修改
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward modify2(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			Pricelist_lineDAO pdao=new Pricelist_lineDAO();
			Pricelist_lineForm info=new Pricelist_lineForm();
			User user = new User();
			HttpSession session = request.getSession();
			user = (User) session.getAttribute("user");
			Connection conn = null;
			
			String item_code = request.getParameter("item_code");
			String sell_type = request.getParameter("sell_type");
			String sale_price = request.getParameter("sale_price");
			String vip_price = request.getParameter("vip_price");
			String pricelist_id = request.getParameter("pricelist_id");
			String catalog_edition = "0";//request.getParameter("catalog_edition");
			String page = "1";//request.getParameter("page");
            
			info.setItem_code(item_code);
			info.setSell_type(Integer.parseInt(sell_type));
			info.setSale_price(Double.parseDouble(sale_price));
			info.setVip_price(Double.parseDouble(vip_price));
			info.setPricelist_id(Integer.parseInt(pricelist_id));
			info.setPage(Integer.parseInt(page));
			info.setCatalog_editon(Integer.parseInt(catalog_edition));
			info.setOperator_id(Integer.parseInt(user.getId()));
			
			try {
				conn = DBManager.getConnection();
				conn.setAutoCommit(false);
					int ret=Pricelist_lineDAO.updateLinesByItemCode(conn,info);
					//int ret=Pricelist_lineDAO.CatalogLineUpdate(conn,info);
					//ProductDAO.insertHistory(conn,pf,info.getPricelist_id(),"UPDATE");
					
					if(ret<0){
						throw new JException("修改目录行信息出错！");
					}
			    conn.commit();
			} catch (SQLException se) {
				se.printStackTrace();

			} finally {
				try {
					conn.close();

				} catch (SQLException sqe) {

				}

			}
				return mapping.findForward("addok2");

		}

	}

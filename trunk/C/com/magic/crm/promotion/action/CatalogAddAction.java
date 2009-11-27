/*
 * Created on 2006-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.exception.JException;

import com.magic.crm.promotion.dao.CatalogDAO;
import com.magic.crm.promotion.form.*;
import com.magic.crm.promotion.entity.*;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.Constants;
import com.magic.crm.util.DBManager;
import com.magic.crm.util.Message;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

	public class CatalogAddAction extends DispatchAction {
		public ActionForward init(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
				CatalogDAO cdao=new CatalogDAO();
				Catalog c=new Catalog();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
				String url="";
	                String id= request.getParameter("id");
	                id=(id==null)?"":id;
	                String price_type_id= request.getParameter("price_type_id");
	                
	                if(price_type_id.equals("1")){
	                	url="member_active";
	                }
	                if(price_type_id.equals("3")){
	                	url="catalog";
	                }
	                
	                if(id.length()>0){
	    				try {
	    					conn = DBManager.getConnection();	
	    	                if(price_type_id.equals("1")){
	    	                	c=cdao.findByKey(conn,Integer.parseInt(id));	
	    	                }	    					
	    	                if(price_type_id.equals("3")){
	    	                	c=cdao.findByPrimaryKey(conn,Integer.parseInt(id));	
	    	                }
	    	       	                
	
	    				} catch (SQLException se) {
	    					se.printStackTrace();
	    					throw se;
	    				} finally {
	    					try {
	    						conn.close();
	    	
	    					} catch (SQLException sqe) {
	    						sqe.printStackTrace();
	    					}
	    	
	    				}    				
	                }
		                request.setAttribute("c", c);
						return mapping.findForward(url);
	                	
	                

		}		
		public ActionForward add(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
				CatalogForm info = new CatalogForm();
				CatalogDAO cdao=new CatalogDAO();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
		        String url="";
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String effect_date = request.getParameter("startDate");
				String expirped_date = request.getParameter("endDate");
				String price_type_id = request.getParameter("price_type_id");
				
				String msc_code = request.getParameter("MSC_CODE");
				String gift_number = request.getParameter("gift_number");
				
				String msc = request.getParameter("msc");
				msc=(msc==null)?"0":msc;
				String periodical_id = request.getParameter("periodical_id");
				periodical_id=(periodical_id==null)?"0":periodical_id;
				String member_category_id = request.getParameter("member_category_id");
				member_category_id=(member_category_id==null)?"0":member_category_id;
				String entry_fee = request.getParameter("entry_fee");
				entry_fee=(entry_fee==null)?"0":entry_fee;
				String recruitment_type = request.getParameter("recruitment_type");
				recruitment_type=(recruitment_type==null)?"0":recruitment_type;
				info.setName(name);	
				info.setCatalogs_name(name);
				info.setDescription(description);
				info.setEffect_date(effect_date);
				info.setExpirped_date(expirped_date);
				info.setMsc(msc_code);
				info.setGift_number(gift_number);
				info.setMscID(Integer.parseInt(msc));
				info.setMember_category_id(Integer.parseInt(member_category_id));            
				info.setPeriodical_id(periodical_id);  
				info.setCompany_id(1);
				info.setEntry_fee(Double.parseDouble(entry_fee));
				info.setRecruitment_type(Integer.parseInt(recruitment_type));
				info.setOperator_id(Integer.parseInt(user.getId()));
				      
	
				try {
					conn = DBManager.getConnection();
					if(cdao.checkInsert(conn,info)==0){
						cdao.insert(conn,info,price_type_id);
					}
				} catch (SQLException se) {
					se.printStackTrace();
					Message.setErrorMsg(request, se.getMessage());
					return mapping.findForward("error");
				} finally {
					try {
						conn.close();
	
					} catch (SQLException sqe) {
	
					}
	
				}
				return mapping.findForward("addok");

		}
		public ActionForward operation(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
				CatalogForm info = new CatalogForm();
				CatalogDAO cdao=new CatalogDAO();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
		        String typename=request.getParameter("typename");
				String id = request.getParameter("id");
				try {
					conn = DBManager.getConnection();
					if(typename.equals("release")){
						cdao.CatalogRelease(conn,id);
					}
					if(typename.equals("pause")){
						cdao.CatalogPause(conn,id);
					}
					if(typename.equals("del")){
						cdao.CatalogDel(conn,id,user.getId());
					}
				} catch (SQLException se) {
					se.printStackTrace();
					throw se;
				} finally {
					try {
						conn.close();
	
					} catch (SQLException sqe) {
	
					}
	
				}
				return mapping.findForward("addok");

		}	
		public ActionForward modify(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			System.out.println("start modify");
				CatalogForm info = new CatalogForm();
				CatalogDAO cdao=new CatalogDAO();
				User user = new User();
				HttpSession session = request.getSession();
				user = (User) session.getAttribute("user");
				Connection conn = null;
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String effect_date = request.getParameter("startDate");
				String expirped_date = request.getParameter("endDate");
				String msc_code = request.getParameter("MSC_CODE");
				String gift_number = request.getParameter("gift_number");
				String msc = request.getParameter("msc");
				msc=(msc==null)?"0":msc;
				String periodical_id = request.getParameter("periodical_id");
				periodical_id=(periodical_id==null)?"0":periodical_id;
				String member_category_id = request.getParameter("member_category_id");
				member_category_id=(member_category_id==null)?"0":member_category_id;
				String entry_fee = request.getParameter("entry_fee");
				entry_fee=(entry_fee==null)?"0":entry_fee;
				String price_type_id = request.getParameter("price_type_id");
				String recruitment_type = request.getParameter("recruitment_type");
				recruitment_type=(recruitment_type==null)?"101":recruitment_type;				
				String id = request.getParameter("id");
				info.setID(Integer.parseInt(id));
				info.setMscID(Integer.parseInt(msc));
				info.setMsc(msc_code);
				info.setName(name);	
				info.setGift_number(gift_number);
				info.setCatalogs_name(name);
				info.setDescription(description);
				info.setEffect_date(effect_date);
				info.setExpirped_date(expirped_date);
	            info.setEntry_fee(Double.parseDouble(entry_fee));
				info.setMember_category_id(Integer.parseInt(member_category_id));            
				info.setPeriodical_id(periodical_id);  
				info.setOperator_id(Integer.parseInt(user.getId()));
				info.setRecruitment_type(Integer.parseInt(recruitment_type));
				      
				//System.out.println("price_type_id is "+price_type_id);
				//System.out.println("msc_code is "+msc_code);
				try {
					conn = DBManager.getConnection();
					if(price_type_id.equals("1")){
					  cdao.update(conn,info);
					}
					if(price_type_id.equals("3")){
						  cdao.CatalogUpdate(conn,info);
					}					
				} catch (SQLException se) {
					se.printStackTrace();
					Message.setErrorMsg(request, se.getMessage());
					return mapping.findForward("error");
				} finally {
					try {
						conn.close();
	
					} catch (SQLException sqe) {
	
					}
	
				}
				return mapping.findForward("addok");

		}		

	}

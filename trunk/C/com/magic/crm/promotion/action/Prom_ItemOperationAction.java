/*
 * @author Administrator(ysm)
 * Created on 2005-10-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.magic.crm.promotion.dao.PromotionDAO;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.promotion.entity.*;
import com.magic.crm.promotion.form.PromotionForm;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;

/**
 * @author Administrator(ysm)
 * Created on 2005-10-10
 */
public class Prom_ItemOperationAction extends DispatchAction{
    
    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {  
	    
        User user= new User();
        HttpSession session = request.getSession();
        user = (User)session.getAttribute("user"); 	
        Connection conn = null;
	    Prom_Item info = new Prom_Item();	
	    PromotionDAO pdao=new PromotionDAO();
	    String promotionid=request.getParameter("promotionid");
	    String item_code=request.getParameter("item_code");
        info.setItemcode(item_code);
        info.setCreatorID(user.getId());
        info.setPromotionID(Integer.parseInt(promotionid));
        Collection prom_itemCol=new ArrayList();
	    try {
	    	conn = DBManager.getConnection();

            /*
             * 判断货号是否存在
             */
	    	/*if(ProductDAO.getItemID(conn,item_code)==0){
	    	    prom_itemCol=pdao.queryPromo_Item(conn,Integer.parseInt(promotionid));
	            request.setAttribute("prom_itemCol",prom_itemCol);
	    	    return mapping.findForward("error2");
	    	}*/
	    	/*
	    	 * 根据货号得到产品ID
	    	 */
	    	//info.setItemID(String.valueOf(ProductDAO.getItemID(conn,item_code)));
            /*
             * 判断促销货号是否重复
             */
	    	/*
            if(PromotionDAO.checkProm_Item(conn,info)>0){
                prom_itemCol=pdao.QueryPromo_Item(conn,Integer.parseInt(promotionid));
                request.setAttribute("prom_itemCol",prom_itemCol);
                return mapping.findForward("error1");
	        }else{
	            PromotionDAO.insertProm_Item(conn,info);
	        }
	    	 */
	    	//modify bymagic 
	    	//修改目的:产品可以重复增加
	    	PromotionDAO.insertProm_Item(conn,info);
                prom_itemCol=pdao.queryPromo_Item(conn,Integer.parseInt(promotionid));
            request.setAttribute("prom_itemCol",prom_itemCol);
	    } catch (SQLException se) {
	    	se.printStackTrace();
	    	throw se;

	    } finally {
	    	try {
	    		conn.close();

	    	} catch (SQLException sqe) {

	    	}

	    }	    
	    return mapping.findForward("success");
	
	}
    public ActionForward list(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {  
	    
        User user= new User();
        HttpSession session = request.getSession();
        user = (User)session.getAttribute("user"); 	
        Connection conn = null;
	    Prom_Item info = new Prom_Item();	
	    PromotionDAO pdao=new PromotionDAO();
	    String promotionid=request.getParameter("promotionid");
        
	    try {
	    	conn = DBManager.getConnection();
	    	
            Collection prom_itemCol=pdao.queryPromo_Item(conn,Integer.parseInt(promotionid));
            request.setAttribute("prom_itemCol",prom_itemCol);
	    } catch (SQLException se) {
	    	se.printStackTrace();
	    } finally {
	    	try {
	    		conn.close();

	    	} catch (SQLException sqe) {

	    	}

	    }	    
	    return mapping.findForward("success");
	
	}    
    public ActionForward del(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {  
	    
        User user= new User();
        HttpSession session = request.getSession();
        user = (User)session.getAttribute("user"); 	
        Connection conn = null;
	    Prom_Item info = new Prom_Item();	
	    PromotionDAO pdao=new PromotionDAO();
	    String promotionid=request.getParameter("promotionid");
	    String id=request.getParameter("id");
	    String tag=request.getParameter("tag");
        info.setFlag(Integer.parseInt(tag));
        info.setID(Integer.parseInt(id));
        info.setModifierID(user.getId());
        info.setPromotionID(Integer.parseInt(promotionid));
        
	    try {
	    	conn = DBManager.getConnection();
	    	PromotionDAO.updateItemValidFlag(conn,info);
            Collection prom_itemCol=pdao.queryPromo_Item(conn,info.getPromotionID());
            request.setAttribute("prom_itemCol",prom_itemCol);
	    } catch (SQLException se) {
	    	se.printStackTrace();

	    } finally {
	    	try {
	    		conn.close();

	    	} catch (SQLException sqe) {

	    	}

	    }	    
	    return mapping.findForward("success");
	
	}
}

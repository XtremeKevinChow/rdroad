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
public class Prom_money4qtyOperationAction extends DispatchAction{
    
    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {  
	    
        User user= new User();
        HttpSession session = request.getSession();
        user = (User)session.getAttribute("user"); 	
        Connection conn = null;
	    Prom_money4qty info = new Prom_money4qty();	
	    PromotionDAO pdao=new PromotionDAO();
	    String promotionid=request.getParameter("promotionid");
	    double money = Double.parseDouble(request.getParameter("money"));
	    int qty = Integer.parseInt(request.getParameter("qty"));
	    info.setMoney(money);
	    info.setQty(qty);
        info.setCreatorID(user.getId());
        info.setPromotionID(Integer.parseInt(promotionid));
        Collection prom_itemCol=new ArrayList();
	    try {
	    	conn = DBManager.getConnection();

	    	PromotionDAO.insertProm_Money4qty(conn,info);
	    	
	    	prom_itemCol=pdao.queryPromo_Money4Qty(conn,info.getPromotionID());
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
	    	
            Collection prom_itemCol=pdao.queryPromo_Money4Qty(conn,Integer.parseInt(promotionid));
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
	    Prom_money4qty info = new Prom_money4qty();	
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
	    	PromotionDAO.updateMoney4QtyValidFlag(conn,info);
	    	
            Collection prom_itemCol=pdao.queryPromo_Money4Qty(conn,info.getPromotionID());
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


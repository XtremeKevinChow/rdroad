/*
 * Created on 2005-3-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.product.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.magic.crm.user.entity.User;
import com.magic.crm.util.Constants;
import com.magic.crm.util.ControlledError;
import com.magic.crm.util.DBManager;
import com.magic.crm.product.entity.Provider;
import com.magic.crm.product.form.ProviderForm;
import com.magic.crm.product.dao.ProviderDAO;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrvidersAddokAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws
Exception {  
			
		/*
		 * 令牌机制判断数据重复提交
		 */
        String token_request = request.getParameter("org.apache.struts.taglib.html.TOKEN");	        
        if(!isTokenValid(request)){
    	    ControlledError ctlErr = new ControlledError();	    		
    		    ctlErr.setErrorTitle("操作错误");	    		
    		    ctlErr.setErrorBody("重复提交数据");	    		
    		    request.setAttribute(Constants.ERROR_KEY, ctlErr);
    		    saveToken(request);
    		    return mapping.findForward("controlledError");	           	           	          
        }else{
	        	resetToken(request);
        }  	
        Provider p=new Provider();
		try{  				
				PropertyUtils.copyProperties(p,form);
		   } catch(InvocationTargetException ite) {
				throw new ServletException(ite);
		   }
		   
		   Connection conn = null;
		   ProviderForm pf=(ProviderForm)form;
		   ProviderDAO pDao=new ProviderDAO();
		   System.out.print("postAdd is "+pf.getPostAdd());
		   System.out.print("ProviderManager is "+pf.getProviderManager());
			 try {
 			 	 conn = DBManager.getConnection();
	  				if(pDao.checkTitle(conn,pf.getProviderTitle())){
	  					System.out.println("title is "+pf.getProviderTitle());
  			    	    ControlledError ctlErr = new ControlledError();	  			    		
  			    		    ctlErr.setErrorTitle("操作错误");	  			    		
  			    		    ctlErr.setErrorBody("产品简称已经存在");	  			    		
  			    		    request.setAttribute(Constants.ERROR_KEY, ctlErr);
  			    		    return mapping.findForward("controlledError");	  			           					  				
  				} 			 	 
 			 	 pDao.insert(conn,p);
	return mapping.findForward("success");
  			 } catch(SQLException se) {

  			  	throw new ServletException(se);

  			 } finally {

  				 try {

  					 conn.close();

  				  } catch(SQLException sqe) {

  					  throw new ServletException(sqe);

  				  }

  			 }
	}
}

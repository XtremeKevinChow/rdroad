/*
 * @author Administrator(ysm)
 * Created on 2005-10-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * @author Administrator(ysm)
 * Created on 2005-10-10
 */
public class InitPromotionAction extends  DispatchAction{
	public ActionForward promotion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws
            Exception {  		
	return mapping.findForward("promotion");
	}
	public ActionForward prom_item(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws
            Exception {  		
	return mapping.findForward("prom_item");
	}
	public ActionForward prom_gift(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws
            Exception {  		
	return mapping.findForward("prom_gift");
	}	
	
}

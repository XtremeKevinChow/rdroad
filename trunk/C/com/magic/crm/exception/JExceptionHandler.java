package com.magic.crm.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.ModuleException;


public class JExceptionHandler extends ExceptionHandler {

    /**
    * Handle the exception.
    * Return the <code>ActionForward</code> instance (if any) returned by
    * the called <code>ExceptionHandler</code>.
    *
    * @param ex The exception to handle
    * @param ae The ExceptionConfig corresponding to the exception
    * @param mapping The ActionMapping we are processing
    * @param formInstance The ActionForm we are processing
    * @param request The servlet request we are processing
    * @param response The servlet response we are creating
    *
    * @exception ServletException if a servlet exception occurs
    *
    * @since Struts 1.1
     */
    public ActionForward execute(Exception ex,
                                 ExceptionConfig ae,
                                 ActionMapping mapping,
                                 ActionForm formInstance,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws ServletException {

        ActionForward forward = null;
        ActionMessage error = null;
        String property = null;
        
        // Build the forward from the exception mapping if it exists
        // or from the form input
        if (ae.getPath() != null) {
            forward = new ActionForward(ae.getPath());
                        
        } else {
            forward = mapping.getInputForward();
        }

        // Figure out the error
        if (ex instanceof Exception) {
        	ModuleException me = new ModuleException("test");
        	error = me.getActionMessage();
            property = me.getProperty();
            //request.setAttribute(Constants.FE_EXCEPTION_CODE, ((FeException)ex).getMsgId());
        } else {
        	error = new ActionMessage(ae.getKey(), ex.getMessage());
            property = error.getKey();
        }

        // Store the exception
        request.setAttribute(Globals.EXCEPTION_KEY, ex);
        this.storeException(request, property, error, forward, ae.getScope());
        return forward;
        
    }



}
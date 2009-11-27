package com.magic.crm.util;

import org.apache.struts.action.RequestProcessor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.tiles.TilesRequestProcessor;

public class QbRequestProcessor extends TilesRequestProcessor {


    /**
     * Override the default behavior, set the encoding stuff correctly, since
     * we will working with GBK encoding, so do our clients.
     *
     * <p>
     * <ul>
     * TODO:
     *   <li> Combine locale info to dynamically set up request encoding later.</li>
     *   <li> System encoding checking and log if no GBK supported, especially in
     *        Linux ( Kernel 2.2 or older) </li>
     * </ul>
     * </p>
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     */
    protected boolean processPreprocess(HttpServletRequest request,
                                        HttpServletResponse response) {


     try {
          request.setCharacterEncoding("GBK");
     }
     catch ( Exception ex) {
         // do nothing here --- TODO: logging
     }

     return true;

    }


}
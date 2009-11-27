/*
 * Created on 2006-7-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.utils;

import javax.servlet.ServletException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * @author magic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConverterPlugIn implements PlugIn{

    public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
        ConvertUtils.register(new SqlDateConverter(null),java.sql.Date.class);
    }

    public void destroy() {
        ConvertUtils.deregister();
    }
}


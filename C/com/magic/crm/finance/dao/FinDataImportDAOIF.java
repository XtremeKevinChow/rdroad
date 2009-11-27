package com.magic.crm.finance.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.magic.crm.finance.form.FinDataImportForm;

/*
 * Created on 2006-7-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author user
 * 用于AR, AP导入公用接口
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FinDataImportDAOIF {
    
    public void execute(Connection conn, FinDataImportForm param) throws SQLException;
    
}

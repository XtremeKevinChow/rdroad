/*
 * Created on 2006-7-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.magic.crm.finance.form.FinDataImportForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinShopDataImportDAO implements FinDataImportDAOIF {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(FinShopDataImportDAO.class);
    
    public void execute(Connection conn, FinDataImportForm param) throws SQLException {
        System.out.println("shop producure-----------");
        CallableStatement cstmt = null;
        try {
            cstmt = conn.prepareCall("{call accounts.p_impShopData(?, ?, ?)}");
            cstmt.setDate(1, param.getStartDate());
            cstmt.setDate(2, param.getEndDate());
            cstmt.setInt(3, param.getOperatorID());
            cstmt.execute();
        }catch(SQLException ex) {
            logger.error("insert failed!");
            throw ex;
        }finally {
            if (cstmt != null)
                try {
                    cstmt.close();
                }catch(SQLException ex) {
                    
                }
        }
    }
}

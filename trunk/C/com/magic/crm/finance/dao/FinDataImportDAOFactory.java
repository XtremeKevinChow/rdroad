/*
 * Created on 2006-7-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.dao;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FinDataImportDAOFactory {
    
    /**
     * 得到各个DAO的实例
     * @param clazz
     * @return
     * @throws Exception
     */
    public static FinDataImportDAOIF getImportDAO(String clazz) throws Exception {
        return (FinDataImportDAOIF)Class.forName(clazz).newInstance();
    }
}

/*
 * Created on 2005-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

import com.magic.crm.util.Config;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DBManager2 {
	
	public static Connection getConnection() throws SQLException {
		// 初始化数据源实例       
		try {
			OracleDataSource ods=null;
			ods= new OracleDataSource();
		    ods.setDriverType("thin");
		    ods.setServerName(Config.getValue("database_host"));
		    ods.setNetworkProtocol("tcp");
		    ods.setDatabaseName(Config.getValue("database_name"));
		    ods.setPortNumber(Integer.parseInt(Config.getValue("database_port")));
		    ods.setUser(Config.getValue("database_user"));
		    ods.setPassword(Config.getValue("database_user_password"));
		    
		    return ods.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}
}

package com.magic.crm.util;

import com.magic.utils.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.pool.*;

/**
 * SQLServer数据库连接
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class DBManagerMS {
	
	/**
	 * 提供sql server的jdbc连接
	 * @return
	 * @throws SQLException
	 */
	public static final synchronized java.sql.Connection getNewConnection()
	throws SQLException {
Connection conn = null;
try {
	Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
	conn = DriverManager.getConnection(Config
			.getValue("crm_sql_server_url"), Config
			.getValue("crm_sql_server_user"), Config
			.getValue("crm_sql_server_user_password"));
} catch (Exception e) {
	e.printStackTrace();
	throw new SQLException(e.getMessage());
}
return conn;
}
	
	public static final synchronized java.sql.Connection getConnection()
			throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			conn = DriverManager.getConnection(Config
					.getValue("crm_sql_server_url"), Config
					.getValue("crm_sql_server_user"), Config
					.getValue("crm_sql_server_user_password"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		return conn;
	}
}
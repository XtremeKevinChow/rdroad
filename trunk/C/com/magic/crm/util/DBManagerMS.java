package com.magic.crm.util;

import com.magic.utils.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.pool.*;

/**
 * SQLServer���ݿ�����
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class DBManagerMS {
	
	/**
	 * �ṩsql server��jdbc����
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
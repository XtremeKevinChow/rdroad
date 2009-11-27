package com.magic.crm.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public final class DBManager {

	public static Connection getConnection() throws SQLException {

		try {

			Context envContext = new InitialContext();
			DataSource dts = (DataSource) envContext.lookup("java:/OracleCRM");
			return dts.getConnection();
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}
}
/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.common;


/**
 * 
 * Sequence处理公用类
 * 
 * @author Kevin
 * @version 1.0
 */

import java.sql.*;

public class SequenceManager {

	public static int getNextVal(Connection conn, String sequenceName)
			throws SQLException {
		int ret = 0;
		String sql = "select " + sequenceName + ".NEXTVAL as nextid from dual";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			ret = rs.getInt("nextid");
		}
		rs.close();
		st.close();
		
		return ret;
	}

	public static int getCurrVal(Connection conn, String sequenceName)
			throws SQLException {
		
		
		int ret = 0;
		String sql = "select " + sequenceName + ".currVAL as currid from dual";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			ret = rs.getInt("currid");
		}
		rs.close();
		st.close();
		
		return ret;
	}
	
}
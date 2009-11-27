/*
 * Created on 2005-3-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;
import java.sql.*;
import java.util.*;

import com.magic.crm.order.entity.*;
/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class ConfigDAO {
	public static ArrayList listKeyValue(Connection conn,String tableName) 
		throws Exception {
		ArrayList array = new ArrayList();
		String sql = " SELECT id,name FROM " + tableName + " order by name";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while( rs.next() ) {
			KeyValue keyvalue = new KeyValue();
			keyvalue.setId(rs.getInt("id"));
			keyvalue.setName(rs.getString("name"));
			array.add(keyvalue);
		}
		rs.close();
		st.close();
		return array;
	}
	/**
	 * 得到经办人列表
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static ArrayList listOrgPersonsKeyValue(Connection conn,String tableName) 
	throws Exception {
	ArrayList array = new ArrayList();
	String sql = " SELECT id,name FROM " + tableName + " where status = 0 and department_id = 2 order by name";
	Statement st = conn.createStatement();
	ResultSet rs = st.executeQuery(sql);
	while( rs.next() ) {
		KeyValue keyvalue = new KeyValue();
		keyvalue.setId(rs.getInt("id"));
		keyvalue.setName(rs.getString("name"));
		array.add(keyvalue);
	}
	rs.close();
	st.close();
	return array;
}
	public static String getCurrentDate(Connection conn)
		throws Exception {
		String date = "";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select to_char(sysdate,'yyyy-mm-dd HH24:MI:SS') as cur_date from dual");
		if( rs.next()) {
			date = rs.getString("cur_date");
		}
		rs.close();
		st.close();
		return date;
	}
}

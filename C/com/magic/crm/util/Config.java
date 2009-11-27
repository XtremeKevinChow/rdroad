/*
 * Created on 2005-2-17
 *
 
 */
package com.magic.crm.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
/**
 * @author zhux
 *
 * 
 */
public class Config {
	private static HashMap map = new HashMap();
	
	public static String getValue(String key) {
		return (String)map.get(key);
	}
	
	public static int setValue(String key,String value) {
		if (!map.containsKey(key)) return -1;
		map.put(key, value);
		return 0;
	}
	// 初始化配置
	static {
		Connection conn  = null;
		try {
			//得到配置文件中的配置
			
			FileInputStream in = new FileInputStream("kewise.properties");
			
			Properties prop = new Properties();
			prop.load(in);
			map.putAll(prop);
			
			//得到数据库中的配置
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String host = prop.getProperty("database_host");
			String db = prop.getProperty("database_name");
			String user = prop.getProperty("database_user");
			String pwd = prop.getProperty("database_user_password");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:" + db,
					user,
					pwd);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select key,value from s_config_keys");
			while (rs.next()) {
				String key = rs.getString("key");
				String value = rs.getString("value");
				map.put(key,value);
			}
			rs.close();
			st.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-2);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {}
			
		}
		
	}
	
	
}

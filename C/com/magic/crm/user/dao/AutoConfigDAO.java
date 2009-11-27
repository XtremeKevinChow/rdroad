/*
 * Created on 2007-1-29 by zhuxiang
 * AutoConfigDAO.java
 * TODO 
 */
package com.magic.crm.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.LabelValueBean;
import com.magic.crm.user.entity.TableColInfo;

/**
 * @author zhuxiang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AutoConfigDAO {
	
	/**
	 * 
	 * 取得列信息
	 */
	public static LinkedHashMap  getColumnInfo(Connection conn,String table_name) throws Exception {
		
		LinkedHashMap hm = new LinkedHashMap(32);
		String tbl_owner="";
		
		// step1 自动配置表主表中寻找指定表
		String sql = "SELECT TBL_CODE,TBL_OWNER FROM SYS_AUTO_CONFIGURE WHERE TBL_CODE=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,table_name);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			//tbl_code = StringUtils.upperCase(rs.getString(1));
			tbl_owner = rs.getString(2);
		} 
		rs.close();
		ps.close();
		
		
		// step2 在自动配置表列表中寻找指定列
		sql = "SELECT COL_NAME,COL_CODE,COL_ORDER FROM SYS_AUTO_CONFIGURE_COL WHERE TBL_NAME=? ORDER BY COL_ORDER";
		ps = conn.prepareStatement(sql);
		ps.setString(1,table_name);
		rs = ps.executeQuery();
		while (rs.next()) {
			TableColInfo col = new TableColInfo();
			String col_code = rs.getString("COL_CODE");
			col.setCol_name(rs.getString("COL_NAME"));
			col.setCol_code(col_code);
			col.setCol_order(rs.getInt("COL_ORDER"));
			
			hm.put(col_code,col);
		}
		rs.close();
		ps.close();
		
		// step3 查询列信息。如果自动配置列表中没有，说明是全部列，否则只有自动配置列表中所记录的列
		if (hm.isEmpty()) {
			sql = "SELECT COLUMN_NAME,DATA_TYPE,DATA_LENGTH,nvl(DATA_PRECISION,-1) as DATA_PRECISION," +
				  " nvl(DATA_SCALE,-1) as DATA_SCALE,NULLABLE,COLUMN_ID,DATA_DEFAULT " +
				  " FROM ALL_TAB_COLUMNS where TABLE_NAME=? and OWNER=? order by COLUMN_ID";
			ps = conn.prepareStatement(sql);
			ps.setString(1,table_name);
			ps.setString(2,tbl_owner);
			rs = ps.executeQuery();
			while (rs.next()) {
				TableColInfo col = new TableColInfo();
				String col_code = rs.getString("COLUMN_NAME");
				col.setCol_name(col_code);
				col.setCol_code(col_code);
				col.setCol_order(rs.getInt("COLUMN_ID"));
				col.setData_type(rs.getString("DATA_TYPE"));
				col.setData_length(rs.getInt("DATA_LENGTH"));
				col.setData_precision(rs.getInt("DATA_PRECISION"));
				col.setData_scale(rs.getInt("DATA_SCALE"));
				col.setIs_nullable("Y".equals(rs.getString("NULLABLE")));
				col.setData_default(rs.getObject("DATA_DEFAULT"));
				
				hm.put(col_code,col);
			}
			rs.close();
			ps.close();
			
		} else {
			sql = "SELECT COLUMN_NAME,DATA_TYPE,DATA_LENGTH,DATA_PRECISION,DATA_SCALE,NULLABLE,DATA_DEFAULT " +
				  " FROM ALL_TAB_COLUMNS where TABLE_NAME=? and OWNER=? and COLUMN_NAME=?";
			ps = conn.prepareStatement(sql);
			Iterator it = hm.keySet().iterator();
			while (it.hasNext()) {
				String col_code = (String)it.next();
				ps.setString(1,table_name);
				ps.setString(2,tbl_owner);
				ps.setString(2,col_code);
				rs = ps.executeQuery();
				if (rs.next()) {
					TableColInfo col = (TableColInfo) hm.get(col_code);
					col.setData_type(rs.getString("DATA_TYPE"));
					col.setData_length(rs.getInt("DATA_LENGTH"));
					col.setData_precision(rs.getInt("DATA_PRECISION"));
					col.setData_scale(rs.getInt("DATA_SCALE"));
					col.setIs_nullable("Y".equals(rs.getString("NULLABLE")));
					col.setData_default(rs.getObject("DATA_DEFAULT"));
				}
				rs.close();
			}
			ps.close();
			
		}
		
		// step4 查询表中的主键信息
		sql = " select v2.column_name from all_constraints v1 join all_cons_columns v2 " +
			  " on v1.owner = v2.owner and v1.constraint_name = v2.constraint_name " +
			  " where v1.constraint_type = 'P'" +
			  " and v1.owner=? and v1.table_name=? ";
		ps = conn.prepareStatement(sql);
		ps.setString(1,tbl_owner);
		ps.setString(2,table_name);
		rs = ps.executeQuery();
		while (rs.next()) {
			String pk = rs.getString(1);
			TableColInfo col = (TableColInfo) hm.get(pk);
			if (col != null) {
				col.setIs_pk(true);
			}
		}
		rs.close();
		ps.close();
		
		return hm;
	}
	
	/**
	 * 
	 * @param conn
	 * @param table_name
	 * @param col_info
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap getDataList(Connection conn,String table_name,Map col_info) throws Exception {
		LinkedHashMap hm = new LinkedHashMap(32);
		int count = 0;
		String sql = "SELECT count(*) FROM " + table_name ;
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		rs.close();
		ps.close();
		
		Iterator it = col_info.keySet().iterator();
		while(it.hasNext()){
			hm.put(it.next(),new ArrayList(count));
		}
		sql = "select * from " + table_name;
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			it = col_info.keySet().iterator();
			while(it.hasNext()) {
				String col_name =(String) it.next();
				String data = rs.getString(col_name);
				TableColInfo col = (TableColInfo) col_info.get(col_name);
				if(col.getData_type().equals("DATE")) {
					data = data.substring(0,10);
				}
				ArrayList al = (ArrayList) hm.get(col_name);
				al.add(data);
				System.out.println(data);
				
			}
			
		}
		rs.close();
		ps.close();

		return hm;
	}
	
	/**
	 * 根据组键去取数据
	 * @param conn
	 * @param table_name
	 * @param col_info
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap getDataInfo(Connection conn,String table_name,Map col_info, Map params) throws Exception {
		LinkedHashMap hm = new LinkedHashMap(32);
		StringBuffer buf = new StringBuffer("select * from ");
		buf.append(table_name).append(" where 1=1 ");
		
		Iterator it = col_info.keySet().iterator();
		while(it.hasNext()) {
			String col_name =(String) it.next();
			TableColInfo col = (TableColInfo) col_info.get(col_name);
			if (col.isIs_pk()) {
				buf.append(" and ").append(col_name).append(" = ");
				
				//request的参数map中是以数组形式存放参数的，要取其第一个
				String[] data = (String[]) params.get(col_name);
				if(col.getData_type().equals("CHAR")||col.getData_type().equals("VARCHAR2")) {
					buf.append("'").append(data[0]).append("' ");
				} else if (col.getData_type().equals("DATE")) {
					buf.append("to_date('").append(data[0]).append("','yyyy-mm-dd') ");
				} else {
					buf.append(data[0]);
				}
			}
		}
		
		PreparedStatement ps = conn.prepareStatement(buf.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			 it = col_info.keySet().iterator();
			while(it.hasNext()) {
				String col_name =(String) it.next();
				String data = rs.getString(col_name);
				TableColInfo col = (TableColInfo) col_info.get(col_name);
				if(col.getData_type().equals("DATE")) {
					data = data.substring(0,10);
				}
				hm.put(col_name,data);
			}
			
		}
		rs.close();
		ps.close();

		return hm;
	}
	
	/**
	 * 
	 * @param conn
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	/*public static DynaActionForm getContent(Connection conn,String table_name) throws Exception {
		DynaActionForm fm = new DynaActionForm();
		// 取得表的列信息
		LinkedHashMap hm = getColumnInfo(conn,table_name);
		fm.set("column_info",hm);
		
		// 取得表中的数据信息
		LinkedHashMap hm_data = getData(conn,table_name,hm);
		fm.set("column_data",hm_data);
		
		return fm;
	}*/
	
	
	/**
	 * 列出可以进行自动配置的表
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static ArrayList listTable(Connection conn) throws Exception {
		ArrayList array = new ArrayList();
		
		String sql = " SELECT TBL_NAME,TBL_CODE FROM SYS_AUTO_CONFIGURE order by TBL_NAME ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			LabelValueBean lv = new LabelValueBean();
			lv.setLabel(rs.getString(1));
			lv.setValue(rs.getString(2));
			array.add(lv);
		}
		
		return array;
	}
	
	/**
	 * 对表中插入数据
	 * @param conn
	 * @param col
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static boolean insertData(Connection conn,String table,Map col,Map params) throws Exception {
		StringBuffer buf = new StringBuffer("insert into ");
		buf.append(table).append("(");
		Iterator it = col.keySet().iterator();
		while(it.hasNext()) {
			buf.append(it.next()).append(",");
		}
		buf.deleteCharAt(buf.length()-1);
		buf.append(") values(");
		it = col.keySet().iterator();
		while(it.hasNext()) {
			TableColInfo info = (TableColInfo) col.get(it.next());
			// request中的参数map中的数据是以数组形式保存的，因此取出数组后第一个就是真正的数据
			String[] data = (String[]) params.get(info.getCol_code());
			if (data[0] == null || data[0].equals("")) {
				data[0] = "null";
			}
			if(info.getData_type().equals("CHAR")||info.getData_type().equals("VARCHAR2")) {
				buf.append("'").append(data[0]).append("',");
			} else if (info.getData_type().equals("DATE")) {
				if (!data[0].equals("null")) {
				buf.append("to_date('").append(data[0]).append("','yyyy-mm-dd'),");
				}
			} else {
				buf.append(data[0]).append(",");
			}
	
		}
		buf.deleteCharAt(buf.length()-1);
		buf.append(")");
		
		String sql = buf.toString();
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
		return true;
	}
	
	/**
	 * 对表中删除数据
	 * @param conn
	 * @param col
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteData(Connection conn,String table,Map col,Map params) throws Exception {
		StringBuffer buf = new StringBuffer(" delete from ");
		buf.append(table).append(" where 1=1 ");
		
		Iterator it = col.keySet().iterator();
		while(it.hasNext()) {
			TableColInfo info = (TableColInfo) col.get(it.next());
			if(info.isIs_pk()) {
				String[] data = (String[]) params.get(info.getCol_code());
				buf.append(" and ").append(info.getCol_code()).append("=");
				if(info.getData_type().equals("CHAR")||info.getData_type().equals("VARCHAR2")) {
					buf.append(" '").append(data[0]).append("'");
				} else if (info.getData_type().equals("DATE")) {
					buf.append("to_date('").append(data[0]).append("','yyyy-mm-dd')");
				} else {
					buf.append(data[0]);
				}
			}
		}

		String sql = buf.toString();
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
		return true;
	}
	
	/**
	 * 对表中更新数据
	 * @param conn
	 * @param col
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static boolean updateData(Connection conn,String table,Map col,Map params) throws Exception {
		StringBuffer buf = new StringBuffer("update ");
		buf.append(table).append(" set ");
		StringBuffer buf2 = new StringBuffer(" where 1=1 ");
		
		Iterator it = col.keySet().iterator();
		while(it.hasNext()) {
			TableColInfo info = (TableColInfo) col.get(it.next());
			if(!info.isIs_pk()) {
				String[] data = (String[]) params.get(info.getCol_code());
				buf.append(info.getCol_code()).append("=");
				if(info.getData_type().equals("CHAR")||info.getData_type().equals("VARCHAR2")) {
					buf.append(" '").append(data[0]).append("',");
				} else if (info.getData_type().equals("DATE")) {
					buf.append("to_date('").append(data[0]).append("','yyyy-mm-dd'),");
				} else {
					buf.append(data[0]).append(",");
				}
			} else {
				String[] data = (String[]) params.get(info.getCol_code());
				buf2.append(" and ").append(info.getCol_code()).append("=");
				if(info.getData_type().equals("CHAR")||info.getData_type().equals("VARCHAR2")) {
					buf2.append(" '").append(data[0]).append("'");
				} else if (info.getData_type().equals("DATE")) {
					buf2.append("to_date('").append(data[0]).append("','yyyy-mm-dd')");
				} else {
					buf2.append(data[0]);
				}
			}
		}

		String sql = buf.deleteCharAt(buf.length()-1).append(buf2).toString();
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
		return true;
	}
	
}

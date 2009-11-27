/**
 * DiamondSettingSync.java
 * 2008-4-8
 * 下午08:12:18
 * user
 * DiamondSettingSync
 */
package com.magic.crm.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
/**
 * @author user
 *
 */
public class DiamondSettingSync {
	
	private static Logger logger = Logger.getLogger(DiamondSettingSync.class);
	
	public void execute(Connection conn1, Connection conn2) throws Exception {
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		
		//删除所有记录
		try {
			pstmt2 = conn2.prepareStatement("delete from exp_exchange_package_dtl");
			pstmt2.execute();
			
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		try {
			pstmt2 = conn2.prepareStatement("delete from exp_exchange_package_mst");
			pstmt2.execute();
			
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		try {
			pstmt2 = conn2.prepareStatement("delete from mbr_diamond_exchange");
			pstmt2.execute();
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		try {
			pstmt2 = conn2.prepareStatement("delete from mbr_diamond_times");
			pstmt2.execute();
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		try {
			pstmt2 = conn2.prepareStatement("delete from mbr_diamond_requires");
			pstmt2.execute();
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		try {
			pstmt2 = conn2.prepareStatement("delete from mbr_diamond_sets");
			pstmt2.execute();
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		//钻活动
		try {
			pstmt1 = conn1.prepareStatement("select * from mbr_diamond_sets where status = 2");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into mbr_diamond_sets"
				+ "(action_id, action_desc, status, begin_date, end_date, "
				+ "dal_count, days, operator, op_time) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setString(1, rs1.getString("action_id"));
				pstmt2.setString(2, rs1.getString("action_desc"));
				pstmt2.setInt(3, rs1.getInt("status"));
				pstmt2.setDate(4, rs1.getDate("begin_date"));
				pstmt2.setDate(5, rs1.getDate("end_date"));
				pstmt2.setInt(6, rs1.getInt("dal_count"));
				pstmt2.setInt(7, rs1.getInt("days"));
				pstmt2.setInt(8, rs1.getInt("operator"));
				pstmt2.setDate(9, rs1.getDate("op_time"));
				pstmt2.execute();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs1 != null) {
				rs1.close();
			}
			if (pstmt1 != null) {
				pstmt1.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		
		// 获钻设置
		try {
			pstmt1 = conn1.prepareStatement("select * from mbr_diamond_requires where status = 2");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into mbr_diamond_requires "
				+ "(req_id, action_id, money_type, money_require, mbr_level, "
				+ "dia_count, status, operator, op_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setInt(1, rs1.getInt("req_id"));
				pstmt2.setInt(2, rs1.getInt("action_id"));
				pstmt2.setString(3, rs1.getString("money_type"));
				pstmt2.setDouble(4, rs1.getDouble("money_require"));
				pstmt2.setInt(5, rs1.getInt("mbr_level"));
				pstmt2.setInt(6, rs1.getInt("dia_count"));
				pstmt2.setInt(7, rs1.getInt("status"));
				pstmt2.setInt(8, rs1.getInt("operator"));
				pstmt2.setDate(9, rs1.getDate("op_time"));
				pstmt2.execute();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs1 != null) {
				rs1.close();
			}
			if (pstmt1 != null) {
				pstmt1.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		//兑换条件
		try {
			pstmt1 = conn1.prepareStatement("select * from mbr_diamond_times where status = 2");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into mbr_diamond_times "
				+ "(time_id, action_id, times, mbr_level, days, "
				+ "time_type, operator, op_time, status) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setLong(1, rs1.getLong("time_id"));
				pstmt2.setLong(2, rs1.getLong("action_id"));
				pstmt2.setInt(3, rs1.getInt("times"));
				pstmt2.setInt(4, rs1.getInt("mbr_level"));
				pstmt2.setInt(5, rs1.getInt("days"));
				pstmt2.setString(6, rs1.getString("time_type"));
				pstmt2.setInt(7, rs1.getInt("operator"));
				pstmt2.setDate(8, rs1.getDate("op_time"));
				pstmt2.setInt(9, rs1.getInt("status"));
				pstmt2.execute();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs1 != null) {
				rs1.close();
			}
			if (pstmt1 != null) {
				pstmt1.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		// 兑换礼品
		try {
			pstmt1 = conn1.prepareStatement("select * from mbr_diamond_exchange where status = 2");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into mbr_diamond_exchange "
				+ "(exc_id, action_id, dia_count, gift_type, package_no, "
				+ "operator, op_time, status) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setLong(1, rs1.getLong("exc_id"));
				pstmt2.setLong(2, rs1.getLong("action_id"));
				pstmt2.setInt(3, rs1.getInt("dia_count"));
				pstmt2.setInt(4, rs1.getInt("gift_type"));
				pstmt2.setString(5, rs1.getString("package_no"));
				pstmt2.setInt(6, rs1.getInt("operator"));
				pstmt2.setDate(7, rs1.getDate("op_time"));
				pstmt2.setInt(8, rs1.getInt("status"));
				pstmt2.execute();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs1 != null) {
				rs1.close();
			}
			if (pstmt1 != null) {
				pstmt1.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		//活动包裹
		try {
			pstmt1 = conn1.prepareStatement("select * from exp_exchange_package_mst where status = 'Y'");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into exp_exchange_package_mst "
				+ "(package_no, description, status, url) "
				+ "values (?, ?, ?, ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setString(1, rs1.getString("package_no"));
				pstmt2.setString(2, rs1.getString("description"));
				pstmt2.setString(3, rs1.getString("status"));
				pstmt2.setString(4, rs1.getString("url"));
				pstmt2.execute();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs1 != null) {
				rs1.close();
			}
			if (pstmt1 != null) {
				pstmt1.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		//包裹明细
		try {
			pstmt1 = conn1.prepareStatement("select * from exp_exchange_package_dtl where status = 'Y'");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into exp_exchange_package_dtl "
				+ "(id, package_no, type, no, quantity, status) "
				+ "values (?, ?, ?, ?, ?, ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setLong(1, rs1.getLong("id"));
				pstmt2.setString(2, rs1.getString("package_no"));
				pstmt2.setString(3, rs1.getString("type"));
				pstmt2.setString(4, rs1.getString("no"));
				pstmt2.setDouble(5, rs1.getDouble("quantity"));
				pstmt2.setString(6, rs1.getString("status"));
				pstmt2.execute();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (rs1 != null) {
				rs1.close();
			}
			if (pstmt1 != null) {
				pstmt1.close();
			}
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
	}
}

package com.magic.crm.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.magic.crm.promotion.entity.ExpExchangeActivity;
import com.magic.crm.promotion.dao.ExpExchangeActivityDAO;

public class ExpSettingSync {
	
	private static Logger log = Logger.getLogger(MemberGiftExchanger.class);
	
	/**
	 * 积分设置数据同步
	 * @param conn1
	 * @param conn2
	 * @throws Exception
	 */
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
			pstmt2 = conn2.prepareStatement("delete from exp_exchange_step_dtl");
			pstmt2.execute();
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		try {
			pstmt2 = conn2.prepareStatement("delete from exp_exchange_step_mst");
			pstmt2.execute();
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		try {
			pstmt2 = conn2.prepareStatement("delete from exp_exchange_activity");
			pstmt2.execute();
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		
		//积分活动
		try {
			pstmt1 = conn1.prepareStatement("select * from exp_exchange_activity where status = 1");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into exp_exchange_activity"
				+ "(activity_no, activity_desc, status, begin_date, end_date, "
				+ "create_person, create_date, check_person, check_date, exchange_type, "
				+ "deal_type, gift_last_date, HeadHtml, type) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, empty_clob(), ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setString(1, rs1.getString("activity_no"));
				pstmt2.setString(2, rs1.getString("activity_desc"));
				pstmt2.setInt(3, rs1.getInt("status"));
				pstmt2.setDate(4, rs1.getDate("begin_date"));
				pstmt2.setDate(5, rs1.getDate("end_date"));
				pstmt2.setInt(6, rs1.getInt("create_person"));
				pstmt2.setDate(7, rs1.getDate("create_date"));
				pstmt2.setInt(8, rs1.getInt("check_person"));
				pstmt2.setDate(9, rs1.getDate("check_date"));
				pstmt2.setString(10, rs1.getString("exchange_type"));
				pstmt2.setString(11, rs1.getString("deal_type"));
				pstmt2.setDate(12, rs1.getDate("gift_last_date"));
				pstmt2.setInt(13, rs1.getInt("type"));// add by user 2008-04-03
				pstmt2.execute();
				//更新大型字段HEADHTML
				ExpExchangeActivity activity = new ExpExchangeActivity();
				activity.setActivityNo(rs1.getString("activity_no"));
				activity.setHeadHtml(rs1.getString("headhtml"));
				//ExpExchangeActivityDAO.updateActivityHTML(conn2, activity);
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
		
		
		//积分活动档次
		try {
			pstmt1 = conn1.prepareStatement("select * from exp_exchange_step_mst where status = 'Y'");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into exp_exchange_step_mst "
				+ "(id, activity_no, begin_exp, create_person, create_date, "
				+ "status) values (?, ?, ?, ?, ?, ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setLong(1, rs1.getLong("id"));
				pstmt2.setString(2, rs1.getString("activity_no"));
				pstmt2.setInt(3, rs1.getInt("begin_exp"));
				pstmt2.setInt(4, rs1.getInt("create_person"));
				pstmt2.setDate(5, rs1.getDate("create_date"));
				pstmt2.setString(6, rs1.getString("status"));
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
		
		//积分活动档次明细
		try {
			pstmt1 = conn1.prepareStatement("select * from exp_exchange_step_dtl where status = 'Y'");
			rs1 = pstmt1.executeQuery();
			String sql = "insert into exp_exchange_step_dtl "
				+ "(id, step_id, type, no, begin_date, "
				+ "end_date, order_require, add_money, status) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt2 = conn2.prepareStatement(sql);
			while (rs1.next()) {
				pstmt2.setLong(1, rs1.getLong("id"));
				pstmt2.setLong(2, rs1.getLong("step_id"));
				pstmt2.setString(3, rs1.getString("type"));
				pstmt2.setString(4, rs1.getString("no"));
				pstmt2.setDate(5, rs1.getDate("begin_date"));
				pstmt2.setDate(6, rs1.getDate("end_date"));
				pstmt2.setDouble(7, rs1.getDouble("order_require"));
				pstmt2.setDouble(8, rs1.getDouble("add_money"));
				pstmt2.setString(9, rs1.getString("status"));
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
		
		//积分活动包裹
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
		
		//积分活动包裹明细
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

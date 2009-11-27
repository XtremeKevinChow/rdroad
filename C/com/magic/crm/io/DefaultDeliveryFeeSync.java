/**
 * DefaultDeliveryFeeSync.java
 * 2008-5-20
 * 上午09:41:23
 * user
 * DefaultDeliveryFeeSync
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
public class DefaultDeliveryFeeSync implements Sync {

	private static Logger logger = Logger.getLogger(DefaultDeliveryFeeSync.class);
	/* (non-Javadoc)
	 * @see com.magic.crm.io.Sync#execute(java.sql.Connection, java.sql.Connection)
	 */
	public void execute(Connection conn1, Connection conn2) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		//删除所有记录
		try {
			pstmt2 = conn2.prepareStatement("delete from s_default_delivery_fee");
			pstmt2.execute();
			
		}catch(SQLException e) {
			throw e;
		} finally {
			if (pstmt2 != null) {
				pstmt2.close();
			}
		}
		
		// 新增数据
		String sql = "insert into s_default_delivery_fee (id, delivery_id, level_id, fees, package_fees) values (?, ?, ?, ?, ?)";
		try {
			
			pstmt2 = conn2.prepareStatement(sql);
			pstmt1 = conn1.prepareStatement("select * from s_default_delivery_fee ");
			rs1 = pstmt1.executeQuery();
			
			while (rs1.next()) {
				pstmt2.setInt(1, rs1.getInt("id"));
				pstmt2.setInt(2, rs1.getInt("delivery_id"));
				pstmt2.setInt(3, rs1.getInt("level_id"));
				pstmt2.setDouble(4, rs1.getDouble("fees"));
				pstmt2.setDouble(5, rs1.getDouble("package_fees"));
				pstmt2.execute();
			}
		}catch(SQLException e) {
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

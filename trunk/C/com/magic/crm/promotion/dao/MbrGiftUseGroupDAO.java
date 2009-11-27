package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import com.magic.crm.promotion.entity.MbrGiftUseGroup;

/**
 * 
 * @author user
 * 
 */
public class MbrGiftUseGroupDAO {

	/**
	 * 得到礼券组有效纪录数
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static int getGiftUseGroupRowCount(Connection conn)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " select count(*) from mbr_gift_use_group "
				+ "where status = 1 and is_used = 0 order by group_no asc ";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}

	}

	/**
	 * 查询所有有效记录
	 * 
	 * @param conn
	 * @param list
	 * @throws SQLException
	 */
	public Collection getAllValidRecords(Connection conn)
			throws SQLException {

		String sql = " select * from mbr_gift_use_group "
				+ "where status = 1 and is_used = 0 order by group_no asc ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new java.util.ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MbrGiftUseGroup info = new MbrGiftUseGroup();
				info.setId(rs.getLong("id"));
				info.setGroupNO(rs.getString("group_no"));
				info.setGiftType(rs.getInt("gift_type"));
				info.setGiftNumber(rs.getString("gift_number"));
				info.setIsUsed(rs.getInt("is_used"));
				info.setStatus(rs.getInt("status"));
				coll.add(info);
			}
			return coll;
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}
	}

}

/*
 * Created on 2007-7-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.promotion.entity.*;

/**
 * @authormagic TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Recruit_Activity_PriceListDAO {
	public static void insert(Connection con, Recruit_Activity_PriceList info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sQuery = "INSERT INTO Recruit_Activity_PriceList(ID,SectionId,ItemId,ItemCode,"
					+ " SellType,Price,StartDate,EndDate,CreateDate,LastModiDate,CreatorId,LastModifierId,Status,overx)"
					+ " VALUES(SEQ_Recruit_PriceList_ID.nextval,?,?, ?, ?, ?, to_date('"
					+ info.getStartDate()
					+ "','YYYY-MM-DD hh24:mi:ss'),to_date('"
					+ info.getEndDate()
					+ "','YYYY-MM-DD hh24:mi:ss'),sysdate,sysdate,?, ?, ?,?)";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, info.getSectionId());
			pstmt.setInt(2, info.getItemId());
			pstmt.setString(3, info.getItemCode());
			pstmt.setInt(4, info.getSellType());
			pstmt.setDouble(5, info.getPrice());
			pstmt.setInt(6, info.getCreatorId());
			pstmt.setInt(7, info.getLastModifierId());
			pstmt.setInt(8, 1);
			pstmt.setDouble(9, info.getOverx());

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	/*
	 * 检查是否有重复的产品
	 */
	public static int checkItemId(Connection con,
			Recruit_Activity_PriceList info) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int theResult = 0;

		try {
			String ardidSql = "select * from Recruit_Activity_PriceList where status=1 and sectionid=? and itemid=? ";
			pstmt = con.prepareStatement(ardidSql);
			pstmt.setInt(1, info.getSectionId());
			pstmt.setInt(2, info.getItemId());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				theResult = 1;
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return theResult;
	}

	public static void update(Connection con, Recruit_Activity_PriceList info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sQuery = "update Recruit_Activity_PriceList set SectionId=?,SellType=?,Price=?,"
					+ " StartDate=to_date('"
					+ info.getStartDate()
					+ "','YYYY-MM-DD hh24:mi:ss'),EndDate=to_date('"
					+ info.getEndDate()
					+ "','YYYY-MM-DD hh24:mi:ss'),LastModiDate=sysdate,LastModifierId=? ,overx=? where id=?";

			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, info.getSectionId());
			pstmt.setInt(2, info.getSellType());
			pstmt.setDouble(3, info.getPrice());
			pstmt.setInt(4, info.getLastModifierId());
			pstmt.setDouble(5, info.getOverx());
			pstmt.setInt(6, info.getId());

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}

	/*
	 * 销售区产品分页
	 */
	public static ArrayList DataToPages(Connection con, String sql, int from,
			int to) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList finCol = new ArrayList();

		ArrayList ret = new ArrayList();
		String sql1 = " SELECT * FROM ( SELECT t.*, rownum rownum_ FROM ( "
				+ sql + " )t WHERE rownum <= ?) B WHERE rownum_ >? ";

		PreparedStatement ps = con.prepareStatement(sql1);
		ps.setInt(1, to);
		ps.setInt(2, from);
		rs = ps.executeQuery();
		while (rs.next()) {

			Recruit_Activity_PriceList info = new Recruit_Activity_PriceList();
			info.setId(rs.getInt("id"));

			info.setSectionId(rs.getInt("SectionId"));
			info.setItemId(rs.getInt("ItemId"));
			info.setItemCode(rs.getString("ItemCode"));
			info.setSellType(rs.getInt("SellType"));
			info.setPrice(rs.getDouble("Price"));
			info.setStartDate(rs.getString("StartDate"));
			info.setEndDate(rs.getString("EndDate"));
			info.setCreatorId(rs.getInt("CreatorId"));
			info.setLastModifierId(rs.getInt("LastModifierId"));
			info.setStatus(rs.getInt("status"));
			ret.add(info);
		}
		rs.close();
		ps.close();
		return ret;

	}

	public static int queryListCount(Connection conn, String sql)
			throws Exception {
		int ret = 0;
		String sql1 = " SELECT COUNT(*) "
				+ sql.substring(sql.toUpperCase().indexOf(" FROM "));
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql1);
		if (rs.next()) {
			ret = rs.getInt(1);
		}
		rs.close();
		st.close();
		return ret;
	}

	/**
	 * 检查是否招募礼品（包括打折产品）
	 * 
	 * @param con
	 * @param itemId
	 * @return 0-不是招募礼品；1-是招募礼品
	 * @throws SQLException
	 */
	public static int isRecruitGifts(Connection con, int itemId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int theResult = 0;
		String sql = "select * from Recruit_Activity_PriceList "
				+ "where status = 1 and sysdate >= startdate and sysdate < enddate + 1 "
				+ "and itemid = ? ";
		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				theResult = 1; // 存在
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return theResult;
	}

	/**
	 * 根据主键查找记录
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static Recruit_Activity_PriceList findByPk(Connection conn, int id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Recruit_Activity_PriceList info = new Recruit_Activity_PriceList();
		String sql = "select a.*, b.type, c.standard_price, c.name as item_name, "
			+ "d.name as unit_name, d.id as unit, c.is_last_sell "
			+ "from recruit_activity_pricelist a "
			+ "inner join recruit_activity_section b on a.sectionid = b.id "
			+ "inner join prd_items c on a.itemid = c.item_id "
			+ "inner join s_uom d on c.unit = d.id "
			+ "where a.id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				info.setId(rs.getInt("id"));
				info.setSectionId(rs.getInt("SectionId"));
				info.setItemId(rs.getInt("ItemId"));
				info.setItemCode(rs.getString("ItemCode"));
				info.setItemName(rs.getString("item_name"));
				info.setSellType(rs.getInt("SellType"));
				info.setPrice(rs.getDouble("Price"));
				info.setStartDate(rs.getString("StartDate"));
				info.setEndDate(rs.getString("EndDate"));
				info.setCreatorId(rs.getInt("CreatorId"));
				info.setLastModifierId(rs.getInt("LastModifierId"));
				info.setStatus(rs.getInt("status"));
				info.setSectionType(rs.getString("type"));
				info.setOverx(rs.getDouble("overx"));
				info.getUnit().setId(rs.getInt("unit"));
				info.getUnit().setName(rs.getString("unit_name"));
				info.setStandardPrice(rs.getDouble("standard_price"));
				info.setIsLastSell(rs.getInt("is_last_sell"));
				
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return info;

	}

	/**
	 * 根据主键查找记录
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static Collection findByPks(Connection conn, String ids)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select a.*, b.type, c.standard_price, c.name as item_name, "
			+ "d.name as unit_name, d.id as unit, c.is_last_sell "
			+ "from recruit_activity_pricelist a "
			+ "inner join recruit_activity_section b on a.sectionid = b.id "
			+ "inner join prd_items c on a.itemid = c.item_id "
			+ "inner join s_uom d on c.unit = d.id "
			+ "where a.id in (" + ids + ")";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Recruit_Activity_PriceList info = new Recruit_Activity_PriceList();
				info.setId(rs.getInt("id"));
				info.setSectionId(rs.getInt("SectionId"));
				info.setItemId(rs.getInt("ItemId"));
				info.setItemCode(rs.getString("ItemCode"));
				info.setItemName(rs.getString("item_name"));
				info.setSellType(rs.getInt("SellType"));
				info.setPrice(rs.getDouble("Price"));
				info.setStartDate(rs.getString("StartDate"));
				info.setEndDate(rs.getString("EndDate"));
				info.setCreatorId(rs.getInt("CreatorId"));
				info.setLastModifierId(rs.getInt("LastModifierId"));
				info.setStatus(rs.getInt("status"));
				info.setSectionType(rs.getString("type"));
				info.setOverx(rs.getDouble("overx"));
				info.getUnit().setId(rs.getInt("unit"));
				info.getUnit().setName(rs.getString("unit_name"));
				info.setStandardPrice(rs.getDouble("standard_price"));
				info.setIsLastSell(rs.getInt("is_last_sell"));
				coll.add(info);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;

	}

	public static Collection findBySectionId(Connection conn, int sectionId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select * from recruit_activity_pricelist where sectionid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sectionId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Recruit_Activity_PriceList info = new Recruit_Activity_PriceList();
				info.setId(rs.getInt("id"));
				info.setSectionId(rs.getInt("SectionId"));
				info.setItemId(rs.getInt("ItemId"));
				info.setItemCode(rs.getString("ItemCode"));
				info.setSellType(rs.getInt("SellType"));
				info.setPrice(rs.getDouble("Price"));
				info.setStartDate(rs.getString("StartDate"));
				info.setEndDate(rs.getString("EndDate"));
				info.setCreatorId(rs.getInt("CreatorId"));
				info.setLastModifierId(rs.getInt("LastModifierId"));
				info.setStatus(rs.getInt("status"));
				info.setSectionType(rs.getString("type"));
				info.setOverx(rs.getDouble("over"));
				info.getUnit().setId(rs.getInt("unit"));
				info.getUnit().setName(rs.getString("unit_name"));
				info.setStandardPrice(rs.getDouble("standard_price"));
				coll.add(info);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return coll;

	}
}

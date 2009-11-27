/*
 * Created on 2007-7-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import com.magic.crm.promotion.entity.Recruit_Activity;
import com.magic.crm.promotion.entity.Recruit_Activity_Section;
import com.magic.crm.order.form.OrderForm;

/**
 * @authormagic TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Recruit_Activity_SectionDAO {
	public static void insert(Connection con, Recruit_Activity_Section info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sQuery = "INSERT INTO Recruit_Activity_Section( ID,MSC,Type,Name,"
					+ " MaxGoods,MinGoods,CreateDate,CreatorId,sectionImg)"
					+ " VALUES(SEQ_RECRUIT_SECTION_ID.nextval,?,?, ?, ?, ?, sysdate, ?,?)";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, info.getMsc_Code());
			pstmt.setString(2, info.getType());
			pstmt.setString(3, info.getName());
			pstmt.setInt(4, info.getMaxGoods());
			pstmt.setInt(5, info.getMinGoods());
			pstmt.setInt(6, info.getCreatorId());
			pstmt.setString(7, info.getSectionImg());
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

	public static void update(Connection con, Recruit_Activity_Section info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sQuery = "update Recruit_Activity_Section set msc=?,"
					+ " type=?,maxgoods=?,mingoods=?,CreateDate=sysdate,CreatorId=?,sectionImg=?  where id=?";

			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, info.getMsc_Code());
			pstmt.setString(2, info.getType());
			pstmt.setInt(3, info.getMaxGoods());
			pstmt.setInt(4, info.getMinGoods());
			pstmt.setInt(5, info.getCreatorId());
			pstmt.setString(6, info.getSectionImg());
			pstmt.setInt(7, info.getId());

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

	public static int checkName(Connection con, Recruit_Activity_Section info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int theResult = 0;

		try {
			String ardidSql = "select * from Recruit_Activity_Section where name=? and msc=?";
			pstmt = con.prepareStatement(ardidSql);
			pstmt.setString(1, info.getName());
			pstmt.setString(2, info.getMsc_Code());
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

	/**
	 * 根据查找区
	 * @param conn
	 * @param sectionId
	 * @return
	 * @throws SQLException
	 */
	public static Recruit_Activity_Section findByPk(Connection conn, int sectionId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Recruit_Activity_Section info = new Recruit_Activity_Section();
		String sql = "select * from recruit_activity_section where id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sectionId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setId(rs.getInt("id"));
				info.setMsc_Code(rs.getString("msc"));
				info.setName(rs.getString("name"));
				info.setType(rs.getString("type"));
				info.setMaxGoods(rs.getInt("MaxGoods"));
				info.setMinGoods(rs.getInt("MinGoods"));
				info.setCreateDate(rs.getString("CreateDate"));
				info.setCreatorId(rs.getInt("CreatorId"));
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
	
	
	
	public static Collection findProductByMsc(Connection conn, String msc, OrderForm pageData) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select * from recruit_activity_section where msc = ? and type = 'E' ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msc);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Recruit_Activity_Section info = new Recruit_Activity_Section();
				info.setId(rs.getInt("id"));
				info.setMsc_Code(rs.getString("msc"));
				info.setName(rs.getString("name"));
				info.setType(rs.getString("type"));
				info.setMaxGoods(rs.getInt("MaxGoods"));
				info.setMinGoods(rs.getInt("MinGoods"));
				info.setCreateDate(rs.getString("CreateDate"));
				info.setCreatorId(rs.getInt("CreatorId"));
				info.setProductsList(GroupPricesDAO.loadGfitsByMsc(conn, msc, "E", pageData));
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

			Recruit_Activity_Section info = new Recruit_Activity_Section();
			info.setId(rs.getInt("id"));

			info.setMsc_Code(rs.getString("msc"));
			info.setName(rs.getString("name"));

			info.setType(rs.getString("type"));

			info.setMaxGoods(rs.getInt("MaxGoods"));
			info.setMinGoods(rs.getInt("MinGoods"));
			info.setCreateDate(rs.getString("CreateDate"));
			info.setCreatorId(rs.getInt("CreatorId"));
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
}

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
import oracle.sql.*;
import oracle.jdbc.driver.*;

import java.io.*;

import com.magic.crm.promotion.entity.*;
import com.magic.crm.promotion.form.Recruit_ActivityForm;

/**
 * @authormagic TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Recruit_ActivityDAO {
	public static void insert(Connection con, Recruit_Activity info)
			throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		ResultSet clob_rs = null;

		try {
			String sQuery = "INSERT INTO Recruit_Activity(id,MSC,Name,StartDate,EndDate,"
					+ " Status,Scope,CreateDate,LastModiDate,CreatorId,LastModifierId,PromulgatorId,Remarks,headhtml)"
					+ " VALUES(SEQ_RECRUIT_ACTIVITY_ID.nextval,?,?,to_date('"
					+ info.getStartDate()
					+ "','YYYY-MM-DD hh24:mi:ss'),to_date('"
					+ info.getEndDate()
					+ "','YYYY-MM-DD hh24:mi:ss'),?, ?,sysdate, sysdate,?,?, ?, ?,empty_clob())";
			// System.out.println(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1, info.getMsc_Code());
			pstmt.setString(2, info.getName());
			pstmt.setInt(3, info.getStatus());
			pstmt.setInt(4, info.getScope());
			pstmt.setInt(5, info.getCreatorId());
			pstmt.setInt(6, info.getLastModifierId());
			pstmt.setInt(7, info.getPromulgatorId());
			pstmt.setString(8, info.getRemarks());
			pstmt.execute();
			con.commit();
			con.setAutoCommit(false);
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
		oracle.sql.CLOB clob = null;
		String sql = "select headhtml from Recruit_Activity where msc='"
				+ info.getMsc_Code() + "' for update";
		// 查询并锁定记录

		try {
			con.setAutoCommit(false);
			pstmt1 = con.prepareStatement(sql);
			clob_rs = pstmt1.executeQuery();
			System.out.println();
			if (clob_rs.next()) {
				clob = (oracle.sql.CLOB) clob_rs.getClob(1);// 得到记录
			}
			clob.putChars(1, info.getHeadhtml().toCharArray());
			pstmt1 = con
					.prepareStatement("UPDATE Recruit_Activity SET headhtml=?  where msc=?");
			pstmt1.setClob(1, clob);
			pstmt1.setString(2, info.getMsc_Code());
			pstmt1.execute();

			con.commit();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (clob_rs != null)
				try {
					clob_rs.close();
				} catch (Exception e) {
				}

			if (pstmt1 != null)
				try {
					pstmt1.close();
				} catch (Exception e) {
				}
		}

	}

	public static void update(Connection con, Recruit_Activity info)
			throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;

		ResultSet rs = null;
		ResultSet clob_rs = null;
		// java.sql.Clob clob = new
		// javax.sql.rowset.serial.SerialClob(info.getHeadhtml().toCharArray());

		try {

			String sQuery = "update Recruit_Activity set StartDate=to_date('"
					+ info.getStartDate()
					+ "','YYYY-MM-DD hh24:mi:ss'),EndDate=to_date('"
					+ info.getEndDate()
					+ "','YYYY-MM-DD hh24:mi:ss'),"
					+ " scope=?,remarks=?,LastModiDate=sysdate,LastModifierId=?,headhtml=empty_clob() where msc=?";
			// System.out.println(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, info.getScope());
			pstmt.setString(2, info.getRemarks());
			pstmt.setInt(3, info.getLastModifierId());
			pstmt.setString(4, info.getMsc_Code());
			pstmt.execute();
			// con.commit();
			// con.setAutoCommit(false);

		} catch (SQLException e) {

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
		oracle.sql.CLOB clob = null;
		String sql = "select headhtml from Recruit_Activity where msc='"
				+ info.getMsc_Code() + "' for update";
		// 查询并锁定记录

		try {
			con.setAutoCommit(false);
			pstmt1 = con.prepareStatement(sql);
			clob_rs = pstmt1.executeQuery();
			System.out.println();
			if (clob_rs.next()) {
				clob = (oracle.sql.CLOB) clob_rs.getClob(1);// 得到记录
			}
			clob.putChars(1, info.getHeadhtml().toCharArray());
			pstmt1 = con
					.prepareStatement("UPDATE Recruit_Activity SET headhtml=?  where msc=?");
			pstmt1.setClob(1, clob);
			pstmt1.setString(2, info.getMsc_Code());
			pstmt1.execute();

			con.commit();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (clob_rs != null)
				try {
					clob_rs.close();
				} catch (Exception e) {
				}

			if (pstmt1 != null)
				try {
					pstmt1.close();
				} catch (Exception e) {
				}
		}
	}

	public static int checkMsc(Connection con, Recruit_Activity info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int theResult = 0;

		try {
			String ardidSql = "select * from Recruit_Activity where msc=? or name=?";
			pstmt = con.prepareStatement(ardidSql);
			pstmt.setString(1, info.getMsc_Code());
			pstmt.setString(2, info.getName());
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

	public static Recruit_Activity findRecruitByMsc(Connection con, String msc)
			throws SQLException {
		Recruit_ActivityForm param = new Recruit_ActivityForm();
		param.setMsc_Code(msc);
		return findRecruitByMsc(con, param);
	}

	/**
	 * 通过产品id取得msc对象
	 * 
	 * @param con
	 * @param itemId
	 * @return
	 * @throws SQLException
	 */
	public static Recruit_Activity findRecruitByItemId(Connection con,
			int itemId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Recruit_Activity data = null;
		String sql = "select a.* from RECRUIT_ACTIVITY a inner join recruit_activity_section b "
				+ "on a.msc = b.msc inner join recruit_activity_pricelist c on b.id = c.sectionid "
				+ "where a.status = 1 and a.scope in(2, 3) and c.itemid = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				data = new Recruit_Activity();
				data.setMsc_Code(rs.getString("msc"));
				data.setName(rs.getString("name"));
				data.setStartDate(rs.getString("startdate"));
				data.setEndDate(rs.getString("enddate"));
				data.setStatus(rs.getInt("status"));
				data.setScope(rs.getInt("scope"));
				data.setCreateDate(rs.getString("createdate"));
				data.setLastModiDate(rs.getString("lastmodidate"));
				data.setCreatorId(rs.getInt("creatorid"));
				data.setLastModifierId(rs.getInt("lastmodifierid"));
				data.setPromulgatorId(rs.getInt("PROMULGATORID"));
				data.setRemarks(rs.getString("remarks"));
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
		return data;
	}

	public static Recruit_Activity findRecruitByMsc(Connection con,
			Recruit_ActivityForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Recruit_Activity data = null;
		String sql = "select * from RECRUIT_ACTIVITY where status = 1 and scope in(2, 3) and msc = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getMsc_Code());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				data = new Recruit_Activity();
				data.setMsc_Code(rs.getString("msc"));
				data.setName(rs.getString("name"));
				data.setStartDate(rs.getString("startdate"));
				data.setEndDate(rs.getString("enddate"));
				data.setStatus(rs.getInt("status"));
				data.setScope(rs.getInt("scope"));
				data.setCreateDate(rs.getString("createdate"));
				data.setLastModiDate(rs.getString("lastmodidate"));
				data.setCreatorId(rs.getInt("creatorid"));
				data.setLastModifierId(rs.getInt("lastmodifierid"));
				data.setPromulgatorId(rs.getInt("PROMULGATORID"));
				data.setRemarks(rs.getString("remarks"));
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
		return data;
	}

	public static Collection findAllRecruitSections(Connection conn, String msc)
			throws SQLException {
		Recruit_ActivityForm param = new Recruit_ActivityForm();
		param.setMsc_Code(msc);
		return findAllRecruitSections(conn, param);
	}

	/**
	 * 查询所有的礼品区（包括礼品区设置的产品）
	 * 
	 * @param con
	 * @param msc
	 * @return
	 * @throws SQLException
	 */
	public static Collection findAllRecruitSections(Connection con,
			Recruit_ActivityForm param) throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Collection sectionColl = new ArrayList();

		String sectionSql = "select * from RECRUIT_ACTIVITY_SECTION where msc = ? and type not in('D', 'E') order by type ";
		String productSql = "select a.*, b.name as itemname, c.id as unit, c.name as unit_name "
				+ "from RECRUIT_ACTIVITY_PRICELIST a inner join prd_items b on a.itemid = b.item_id "
				+ "inner join s_uom c on b.unit = c.id "
				+ "where a.status = 1 "
				+ "and a.startdate <= sysdate and a.enddate + 1 > sysdate and a.sectionid = ? ";
		try {
			pstmt = con.prepareStatement(sectionSql);
			pstmt.setString(1, param.getMsc_Code());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Recruit_Activity_Section section = new Recruit_Activity_Section();
				section.setId(rs.getInt("id"));
				section.setMsc_Code(rs.getString("msc"));
				section.setType(rs.getString("type"));
				section.setName(rs.getString("name"));
				section.setMaxGoods(rs.getInt("maxgoods"));
				section.setMinGoods(rs.getInt("mingoods"));
				section.setCreateDate(rs.getString("createdate"));
				section.setCreatorId(rs.getInt("creatorid"));
				Collection productColl = new ArrayList();
				pstmt2 = con.prepareStatement(productSql);
				pstmt2.setInt(1, rs.getInt("id"));
				rs2 = pstmt2.executeQuery();

				while (rs2.next()) {
					Recruit_Activity_PriceList price = new Recruit_Activity_PriceList();
					price.setId(rs2.getInt("id"));
					price.setSectionId(rs2.getInt("sectionid"));
					price.setItemId(rs2.getInt("itemid"));
					price.setItemCode(rs2.getString("itemcode"));
					price.setSellType(rs2.getInt("selltype"));
					price.setPrice(rs2.getDouble("price"));
					price.setStartDate(rs2.getString("startdate"));
					price.setEndDate(rs2.getString("enddate"));
					price.setCreateDate(rs2.getString("createdate"));
					price.setLastModiDate(rs2.getString("lastmodidate"));
					price.setCreatorId(rs2.getInt("creatorid"));
					price.setLastModifierId(rs2.getInt("lastmodifierid"));
					price.setStatus(rs2.getInt("status"));
					price.setItemName(rs2.getString("itemname"));
					price.getUnit().setId(rs2.getInt("unit"));
					price.getUnit().setName(rs2.getString("unit_name"));
					price.setOverx(rs2.getDouble("overx"));// //add by user
															// 2008-02-15
					productColl.add(price);
				}
				rs2.close();
				pstmt2.close();
				section.setProductsList(productColl);
				sectionColl.add(section);

			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (rs2 != null)
				try {
					rs2.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (Exception e) {
				}
		}
		return sectionColl;
	}

	/*
	 * 招募活动分页
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

			Recruit_Activity info = new Recruit_Activity();

			info.setMsc_Code(rs.getString("msc"));
			info.setName(rs.getString("name"));
			info.setStartDate(rs.getString("startdate"));
			info.setEndDate(rs.getString("enddate"));
			info.setStatus(rs.getInt("status"));
			info.setScope(rs.getInt("scope"));
			info.setCreateDate(rs.getString("createdate"));
			info.setLastModiDate(rs.getString("lastmodidate"));
			info.setCreatorId(rs.getInt("creatorid"));
			info.setLastModifierId(rs.getInt("lastmodifierid"));
			info.setPromulgatorId(rs.getInt("PROMULGATORID"));
			info.setRemarks(rs.getString("remarks"));
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

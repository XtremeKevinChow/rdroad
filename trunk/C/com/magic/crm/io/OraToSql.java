/*
 * Created on 2005-1-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.io;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import oracle.jdbc.driver.*;
import oracle.sql.CLOB;
import org.apache.log4j.Logger;

import com.magic.crm.util.*;

/**
 * @author zhux this class is for member gift exchange
 */
public class OraToSql {
	private static Logger log = Logger.getLogger(OraToSql.class);

	Connection connOra = null;

	Connection connSql = null;

	/**
	 * 初始化连接,包括一个oracle connection and a sqlserver connection
	 * 
	 * @param conn1
	 * @param conn2
	 * @throws Exception
	 */
	public void initConnection(Connection conn1, Connection conn2)
			throws Exception {
		if (conn1 == null || conn1.isClosed()) {
			throw new Exception(" Oracle Connection is null or closed ");
		}
		if (conn2 == null || conn2.isClosed()) {
			throw new Exception(" sqlserver Connection is null or closed ");
		}
		connOra = conn1;
		connSql = conn2;
	}

	public void execute() throws Exception {

		try {
			connOra = DBManager.getConnection();
			connSql = null;//DBManager3.getConnection();
			// connSql.setAutoCommit(false);

			// 选择oracle相关表中记录
			String activity_ora_sql = "select * from Recruit_Activity where Status=1 ";
			String section_ora_sql = "select b.* from Recruit_Activity a inner join Recruit_Activity_Section b on a.msc=b.msc where a.Status=1 ";
			String priceList_ora_sql = " select c.* from Recruit_Activity a inner join Recruit_Activity_Section b on a.msc=b.msc ";
			priceList_ora_sql += " inner join Recruit_Activity_PriceList c on b.id=c.sectionid where a.Status=1";
			String groupPrices_ora_sql = " select * from recruit_activity_group_prices where status = 1 ";
			
			PreparedStatement Activity_ora = connOra
					.prepareStatement(activity_ora_sql);
			PreparedStatement Section_ora = connOra
					.prepareStatement(section_ora_sql);
			PreparedStatement PriceList_ora = connOra
					.prepareStatement(priceList_ora_sql);
			PreparedStatement GroupPrices_ora = connOra.prepareStatement(groupPrices_ora_sql);

			// 删除sql招募活动记录
			PreparedStatement pstmt_sql1 = null;
			pstmt_sql1 = connSql
					.prepareStatement("delete from Recruit_Activity ");
			pstmt_sql1.execute();
			pstmt_sql1.close();
			// System.out.println("test");
			// 删除sql招募活动商品销售区表
			pstmt_sql1 = connSql
					.prepareStatement("delete from Recruit_Activity_Section ");
			pstmt_sql1.execute();
			pstmt_sql1.close();
			// System.out.println("test**********");
			// 删除sql招募活动商品价目表
			pstmt_sql1 = connSql
					.prepareStatement("delete from Recruit_Activity_PriceList ");
			pstmt_sql1.execute();
			pstmt_sql1.close();
			// System.out.println("test*******************");
			/**
			 * add by user 2008-05-08
			 * 删除网站打套价格列表
			 */
			pstmt_sql1 = connSql.prepareStatement("delete from Recruit_Activity_Group_Prices ");
			pstmt_sql1.execute();
			pstmt_sql1.close();
			// 新增sql招募活动记录
			String Activity_sQuery = "INSERT INTO Recruit_Activity(id,MSC,Name,StartDate,EndDate,"
					+ " Status,Scope,CreateDate,LastModiDate,CreatorId,LastModifierId,PromulgatorId,Remarks,headhtml)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement Activity_pstmt = connSql
					.prepareStatement(Activity_sQuery);

			// 新增sql招募活动商品销售区表
			String Section_sQuery = "INSERT INTO Recruit_Activity_Section( ID,MSC,Type,Name,"
					+ " MaxGoods,MinGoods,CreateDate,CreatorId,sectionimg)"
					+ " VALUES(?,?,?, ?, ?, ?, ?, ?,?)";
			PreparedStatement Section_pstmt = connSql
					.prepareStatement(Section_sQuery);
			// 新增sql招募活动商品价目表
			String PriceList_sQuery = "INSERT INTO Recruit_Activity_PriceList(ID,SectionId,ItemId,ItemCode,"
					+ " SellType,Price,StartDate,EndDate,CreateDate,LastModiDate,CreatorId,LastModifierId,Status,overx)"
					+ " VALUES(?,?,?, ?, ?, ?,?,?,?,?,?, ?, ?,?)";

			PreparedStatement PriceList_pstmt = connSql
					.prepareStatement(PriceList_sQuery);
			
			/**
			 * add by user 2008-05-08
			 * 同步打套价格列表
			 */
			String GroupPrices_sQuery = "insert into Recruit_Activity_Group_Prices "
				+ "(gp_id, sectionid, sale_qty, sale_amt, is_gift, startdate, enddate, "
				+ "createdate, lastmodidate, creatorid, lastmodifierid, status) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			PreparedStatement GroupPrices_pstmt = connSql
			.prepareStatement(GroupPrices_sQuery);
			
			// 循环ORACLE招募活动记录
			String headhtml = "";
			ResultSet rs = Activity_ora.executeQuery();
			while (rs.next()) {
				// System.out.println("同步is "+rs.getInt("id"));
				Activity_pstmt.setInt(1, rs.getInt("id"));
				Activity_pstmt.setString(2, rs.getString("MSC"));
				Activity_pstmt.setString(3, rs.getString("Name"));
				Activity_pstmt.setDate(4, rs.getDate("StartDate"));
				Activity_pstmt.setDate(5, rs.getDate("EndDate"));
				Activity_pstmt.setInt(6, rs.getInt("Status"));
				Activity_pstmt.setInt(7, rs.getInt("Scope"));
				Activity_pstmt.setDate(8, rs.getDate("CreateDate"));
				Activity_pstmt.setDate(9, rs.getDate("LastModiDate"));
				Activity_pstmt.setInt(10, rs.getInt("CreatorId"));
				Activity_pstmt.setInt(11, rs.getInt("LastModifierId"));
				Activity_pstmt.setInt(12, rs.getInt("PromulgatorId"));
				Activity_pstmt.setString(13, rs.getString("Remarks"));

				java.sql.Clob clob = rs.getClob("headhtml");

				if (clob != null) {
					Reader is = clob.getCharacterStream();
					java.io.BufferedReader br = new java.io.BufferedReader(is);
					String s = br.readLine();
					while (s != null) {
						headhtml += s;
						s = br.readLine();
					}

				}

				Activity_pstmt.setString(14, headhtml);
				Activity_pstmt.execute();
				/**
				 * modified by user 2008-03-14
				 * 此处是BUG，headhtml必须清空，否则循环第二条会将字符串累加
				 */
				headhtml = "";

			}
			rs.close();
			Activity_ora.close();
			Activity_pstmt.close();
			ResultSet rs1 = Section_ora.executeQuery();
			while (rs1.next()) {
				// System.out.println("同步1is "+rs1.getInt("id"));
				Section_pstmt.setInt(1, rs1.getInt("id"));
				Section_pstmt.setString(2, rs1.getString("MSC"));
				Section_pstmt.setString(3, rs1.getString("Type"));
				Section_pstmt.setString(4, rs1.getString("Name"));
				Section_pstmt.setInt(5, rs1.getInt("MaxGoods"));
				Section_pstmt.setInt(6, rs1.getInt("MinGoods"));
				Section_pstmt.setDate(7, rs1.getDate("CreateDate"));
				Section_pstmt.setInt(8, rs1.getInt("CreatorId"));
				Section_pstmt.setString(9, rs1.getString("sectionimg"));
				Section_pstmt.execute();

			}
			rs1.close();
			Section_ora.close();
			Section_pstmt.close();

			ResultSet rs2 = PriceList_ora.executeQuery();
			while (rs2.next()) {
				// System.out.println("同步2is "+rs2.getInt("id"));
				PriceList_pstmt.setInt(1, rs2.getInt("id"));
				PriceList_pstmt.setInt(2, rs2.getInt("SectionId"));
				PriceList_pstmt.setInt(3, rs2.getInt("ItemId"));
				PriceList_pstmt.setString(4, rs2.getString("ItemCode"));
				PriceList_pstmt.setInt(5, rs2.getInt("SellType"));
				PriceList_pstmt.setDouble(6, rs2.getDouble("Price"));
				PriceList_pstmt.setDate(7, rs2.getDate("StartDate"));
				PriceList_pstmt.setDate(8, rs2.getDate("EndDate"));
				PriceList_pstmt.setDate(9, rs2.getDate("CreateDate"));
				PriceList_pstmt.setDate(10, rs2.getDate("LastModiDate"));
				PriceList_pstmt.setInt(11, rs2.getInt("CreatorId"));
				PriceList_pstmt.setInt(12, rs2.getInt("LastModifierId"));
				PriceList_pstmt.setInt(13, rs2.getInt("Status"));
				PriceList_pstmt.setDouble(14, rs2.getDouble("overx"));

				PriceList_pstmt.execute();
			}
			rs2.close();
			PriceList_ora.close();
			PriceList_pstmt.close();
			
			/**
			 * 同步打套价格列表
			 */
			ResultSet rs3 = GroupPrices_ora.executeQuery();
			while (rs3.next()) {
				GroupPrices_pstmt.setInt(1, rs3.getInt("gp_id"));
				GroupPrices_pstmt.setInt(2, rs3.getInt("sectionid"));
				GroupPrices_pstmt.setDouble(3, rs3.getInt("sale_qty"));
				GroupPrices_pstmt.setDouble(4, rs3.getInt("sale_amt"));
				GroupPrices_pstmt.setInt(5, rs3.getInt("is_gift"));
				GroupPrices_pstmt.setDate(6, rs3.getDate("startdate"));
				GroupPrices_pstmt.setDate(7, rs3.getDate("enddate"));
				GroupPrices_pstmt.setDate(8, rs3.getDate("createdate"));
				GroupPrices_pstmt.setDate(9, rs3.getDate("lastmodidate"));
				GroupPrices_pstmt.setInt(10, rs3.getInt("creatorid"));
				GroupPrices_pstmt.setInt(11, rs3.getInt("lastmodifierid"));
				GroupPrices_pstmt.setInt(12, rs3.getInt("status"));
				GroupPrices_pstmt.execute();
			}
			rs3.close();
			GroupPrices_ora.close();
			GroupPrices_pstmt.close();
			// connSql.commit();
		} catch (Exception se) {

			se.printStackTrace();

		} finally {
			try {
				connSql.close();

			} catch (Exception sqe) {
			}
			try {
				connOra.close();

			} catch (Exception sqe) {
			}

		}

	}
}

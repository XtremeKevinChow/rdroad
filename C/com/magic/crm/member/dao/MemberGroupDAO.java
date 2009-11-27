/*
 * Created on 2005-12-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.magic.crm.member.form.MemberGroupForm;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGroupDAO implements Serializable {
	
	private static Logger logger = Logger.getLogger(MemberGroupDAO.class);
	
	/** 空构造函数 **/
	public MemberGroupDAO() {}
	
	/**
	 * 新增团体会员
	 * @param con
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public String insert(Connection con, MemberGroupForm data) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long newMemberID = 0;
		long newAddressID = 0;
		
		con.setAutoCommit(false);
		
		//新的会员号
		String cardID = this.getNextMemberCardID(con);
		System.out.println("user: " + cardID);
		if (cardID == null) {
			throw new SQLException();
		}
		//得到新的会员ID
		try {
			String sql = "SELECT seq_MBR_MEMBERS_id.NEXTVAL FROM DUAL";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				newMemberID = rs.getLong(1);
			}
		} catch (SQLException e) {
			try {
				con.rollback();
				return null;
			}catch(Exception ex) {
				ex.printStackTrace();
			}
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
		
		//得到新的地址ID
		try {
			String sql = "SELECT seq_mbr_addresses_id.NEXTVAL FROM DUAL";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				newAddressID = rs.getLong(1);
			}
		} catch (SQLException e) {
			try {
				con.rollback();
				return null;
			}catch(Exception ex) {
				ex.printStackTrace();
			}
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
		
		
		
		//插入会员表
		try {
			StringBuffer insertSql = new StringBuffer();
			insertSql.append("INSERT INTO MBR_MEMBERS ");
			insertSql.append("(ID, CARD_ID, CLUB_ID, NAME, TELEPHONE, COMPANY_PHONE, ");
			insertSql.append("ADDRESS_ID, CREATOR_ID, ADDRESS, POSTCODE, IS_ORGANIZATION,section) ");
			insertSql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
			pstmt = con.prepareStatement(insertSql.toString());
			pstmt.setLong(1, newMemberID);
			pstmt.setString(2, cardID);
			pstmt.setInt(3, 1);
			pstmt.setString(4, data.getCompayName());
			pstmt.setString(5, data.getTelephone());
			pstmt.setString(6, data.getTelephone2());
			pstmt.setLong(7, newAddressID);
			pstmt.setInt(8, data.getLoginUserID());
			pstmt.setString(9, data.getAddress());
			pstmt.setString(10, data.getPostcode());
			pstmt.setString(11, "1");
			pstmt.setString(12, data.getSection());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				return null;
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		} finally {
			
			if (pstmt != null)
				pstmt.close();

		}
		
		//插入地址表
		try {
			StringBuffer insertSql = new StringBuffer();
			insertSql.append("INSERT INTO MBR_ADDRESSES ");
			insertSql.append("(ID, DELIVERY_ADDRESS, RELATION_PERSON, MEMBER_ID, TELEPHONE, POSTCODE,is_default,section) ");
			insertSql.append("VALUES(?, ?, ?, ?, ?, ?,1,?)");
			pstmt = con.prepareStatement(insertSql.toString());
			pstmt.setLong(1, newAddressID);
			pstmt.setString(2, data.getAddress());
			pstmt.setString(3, data.getRelationPerson());
			pstmt.setLong(4, newMemberID);
			pstmt.setString(5, data.getTelephone());
			pstmt.setString(6, data.getPostcode());
			pstmt.setString(7, data.getSection());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				return null;
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			
		} finally {
			
			if (pstmt != null)
				pstmt.close();

		}
		
		con.commit();
		
		return cardID;
	}
	
	/**
	 * 得到团体会员的流水号
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	private synchronized String getNextMemberCardID (Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long currMemberID = 0;
		try {
			String sql = "SELECT MAX(TO_NUMBER(SUBSTR(CARD_ID, 2))) FROM MBR_MEMBERS WHERE IS_ORGANIZATION = '1' AND CARD_ID LIKE 'T%'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				currMemberID = rs.getLong(1);
				currMemberID ++;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		System.out.println("user2: " + "T" + String.valueOf(currMemberID));
		return "T" + String.valueOf(currMemberID);
	}
	
	/**
	 * 显示页面详情
	 * @param con
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public MemberGroupForm showDetail (Connection con, MemberGroupForm data) throws SQLException {
		MemberGroupForm form = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
			"SELECT " +
			"ID, " +
			"CARD_ID, " +
			"NAME, " +
			"TELEPHONE, " +
			"COMPANY_PHONE, " +
			"ADDRESS, " +
			"POSTCODE " +
			" FROM MBR_MEMBERS WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, data.getID());
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				form = new MemberGroupForm();
				form.setID(rs.getLong("ID"));
				form.setGroupCode(rs.getString("CARD_ID"));
				form.setCompayName(rs.getString("NAME"));
				form.setTelephone(rs.getString("TELEPHONE"));
				form.setTelephone2(rs.getString("COMPANY_PHONE"));
				form.setAddress(rs.getString("ADDRESS"));
				form.setPostcode(rs.getString("POSTCODE"));
			} 
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
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
		return form;
	}
	
	/**
	 * 修改记录
	 * @param con
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public void update (Connection con, MemberGroupForm data) throws SQLException {
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = 
			"UPDATE MBR_MEMBERS " +
			"SET NAME = ?, " +
			"TELEPHONE = ?, " +
			"COMPANY_PHONE = ?, " +
			"MODIFIER_ID = ?, " +
			"MODIFY_DATE = sysdate, " +
			"ADDRESS = ?, " +
			"POSTCODE = ? " +
			"WHERE ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data.getCompayName());
			pstmt.setString(2, data.getTelephone());
			pstmt.setString(3, data.getTelephone2());
			pstmt.setInt(4, data.getLoginUserID());
			pstmt.setString(5, data.getAddress());
			pstmt.setString(6, data.getPostcode());
			pstmt.setLong(7, data.getID());
			pstmt.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {

				}
		}
		
	}
}

package com.magic.crm.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import org.apache.log4j.Logger;

import com.magic.crm.order.entity.Ticket;
import com.magic.crm.order.entity.OneTicket;

public class TicketDAO {
	
	private static Logger log = Logger.getLogger(TicketDAO.class);
	
	/**
	 * 得到Oracle时间
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static java.sql.Date getOracleDate (Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " select sysdate from dual ";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getDate(1);
			} 
			return null;
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
	 * 通过礼券号查找1条礼券
	 * @see 4.0版本之前
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static Ticket getTicketByNumber(Connection conn, String ticketNumber)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Ticket ticket = null;
		/**
		 * old_sql
		 */
		String sql = " select * from mbr_gift_certificates where gift_number = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ticketNumber);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ticket = new Ticket();
				ticket.setId(rs.getLong("id"));
				ticket.setGiftNumber(rs.getString("gift_number"));
				ticket.setGiftType(rs.getInt("gift_type"));
				ticket.setPersonNum(rs.getInt("person_num"));
				ticket.setAmount(rs.getInt("amount"));
				ticket.setGiftMoney(rs.getDouble("gift_money"));
				ticket.setOrderMoney(rs.getDouble("order_money"));
				ticket.setStartDate(rs.getString("start_date"));
				ticket.setEndDate(rs.getString("end_date"));
				ticket.setMemberStartDate(rs.getString("member_start_date"));
				ticket.setMemberEndDate(rs.getString("member_end_date"));
				ticket.setDescription(rs.getString("description"));
				ticket.setIsWeb(rs.getInt("is_web"));
				ticket.setIsNewMember(rs.getInt("is_new_member"));
				ticket.setIsOldMember(rs.getInt("is_old_member"));
				ticket.setIsMemberLevel(rs.getInt("is_member_level"));
				ticket.setProductGroupId(rs.getInt("product_group_id"));
				ticket.setIsMoneyForOrder(rs.getInt("is_money_for_order"));
				return ticket;
			} 
			return ticket;
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
	 * 通过礼券号查找1条礼券
	 * @see new
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static OneTicket getTicketByNumber2(Connection conn, String ticketNumber)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Ticket ticket = new Ticket();
		OneTicket one = new OneTicket();
		String sql = " select b.*, a.*, a.id as dtl_id from mbr_gift_certificates a inner join mbr_gift_lists b "
					+ "on a.gift_number = b.gift_number where b.gift_no = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ticketNumber);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				ticket.setId(rs.getLong("id"));
				ticket.setGiftNumber(rs.getString("gift_number"));
				ticket.setGiftType(rs.getInt("gift_type"));
				ticket.setPersonNum(rs.getInt("person_num"));
				ticket.setAmount(rs.getInt("amount"));
				ticket.setGiftMoney(rs.getDouble("gift_money"));
				ticket.setOrderMoney(rs.getDouble("order_money"));
				ticket.setStartDate(rs.getString("start_date"));
				ticket.setEndDate(rs.getString("end_date"));
				ticket.setMemberStartDate(rs.getString("member_start_date"));
				ticket.setMemberEndDate(rs.getString("member_end_date"));
				ticket.setDescription(rs.getString("description"));
				ticket.setIsWeb(rs.getInt("is_web"));
				ticket.setIsNewMember(rs.getInt("is_new_member"));
				ticket.setIsOldMember(rs.getInt("is_old_member"));
				ticket.setIsMemberLevel(rs.getInt("is_member_level"));
				ticket.setProductGroupId(rs.getInt("product_group_id"));
				ticket.setIsMoneyForOrder(rs.getInt("is_money_for_order"));
				
				one.setTicket(ticket);
				one.setId(rs.getInt("dtl_id"));
				one.setGiftNo(rs.getString("gift_no"));
				one.setPass(rs.getString("pass"));
				one.setIsNeedPass(rs.getInt("isneedpass"));
				one.setCreateDate(rs.getDate("create_date"));
				one.setCreatePerson(rs.getInt("create_person"));
				one.setAgentId(rs.getInt("agent_id"));
				one.setAgentName(rs.getString("agent_name"));
				
			} 
			return one;
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
	 * 
	 * @param con
	 * @param info
	 * @return
	 * @throws SQLException
	 * 新增礼券
	 */
	public static void insert(Connection con,Ticket info) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			sp = "{?=call member.f_member_gift_certificate_add(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			cstmt = con.prepareCall(sp);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setString(2, info.getGiftNumber());
			cstmt.setInt(3, info.getPersonNum());
			cstmt.setInt(4, info.getAmount());
			cstmt.setInt(5, info.getGiftType());
			cstmt.setDouble(6, info.getGiftMoney());
			cstmt.setDouble(7, info.getOrderMoney());
			//cstmt.setDate(8, info.getStartDate());
			//cstmt.setDate(9, info.getEndDate());
			cstmt.setString(8, info.getStartDate());
			cstmt.setString(9, info.getEndDate());
			cstmt.setString(10, info.getMemberStartDate());
			cstmt.setString(11, info.getMemberEndDate());
			cstmt.setInt(12, info.getIsWeb());
			cstmt.setInt(13, info.getIsNewMember());
			cstmt.setInt(14, info.getIsOldMember());
			cstmt.setInt(15, info.getIsMemberLevel());
			cstmt.setInt(16, info.getProductGroupId());
			cstmt.setInt(17, info.getIsMoneyForOrder());
			cstmt.setString(18, info.getDescription());
			
			
			cstmt.execute();
			re = cstmt.getInt(1);
			cstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			e.printStackTrace();
			throw e;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (Exception e) {
				}
		}
		//return re;
	}
	/**
	 * 
	 * @param con
	 * @param info
	 * @return
	 * @throws SQLException
	 * 修改礼券
	 */
	public static void update(Connection con,Ticket info) throws SQLException {
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sp = null;
		int itemID = 1;

		int re = 0;

		try {
			sp = "{?=call member.f_mbr_gift_certificate_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			cstmt = con.prepareCall(sp);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setLong(2, info.getId());
			cstmt.setString(3, info.getGiftNumber());
			cstmt.setInt(4, info.getPersonNum());
			cstmt.setInt(5, info.getAmount());
			cstmt.setInt(6, info.getGiftType());
			cstmt.setDouble(7, info.getGiftMoney());
			cstmt.setDouble(8, info.getOrderMoney());
			cstmt.setString(9, info.getStartDate());
			cstmt.setString(10, info.getEndDate());
			cstmt.setString(11, info.getMemberStartDate());
			cstmt.setString(12, info.getMemberEndDate());
			cstmt.setInt(13, info.getIsWeb());
			cstmt.setInt(14, info.getIsNewMember());
			cstmt.setInt(15, info.getIsOldMember());
			cstmt.setInt(16, info.getIsMemberLevel());
			cstmt.setInt(17, info.getProductGroupId());
			cstmt.setInt(18, info.getIsMoneyForOrder());
			cstmt.setString(19, info.getDescription());
			
			
			cstmt.execute();
			re = cstmt.getInt(1);
			cstmt.close();
			if (re < 0) {
				System.out.println("re is " + re);
			}

		} catch (SQLException e) {
			if (con != null)
				try {
					con.rollback();
				} catch (Exception ex) {
				}
			e.printStackTrace();
			throw e;
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (Exception e) {
				}
		}
		//return re;
	}	
	/**
	 * 
	 * @param con
	 * @param info
	 * @return
	 * @throws SQLException
	 * 判断礼券号是否重复
	 */
	public static int checkGift(Connection conn, Ticket info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int ticket = 0;
	String sql = " select * from mbr_gift_certificates where gift_number = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, info.getGiftNumber());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ticket=1;
			} 
			return ticket;
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
	 * 
	 * @param con
	 * @param info
	 * @return
	 * @throws SQLException
	 * 判断礼券号是否已经被使用
	 */
	public static int checkIfused(Connection conn, String gift_number)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int ticket = 0;
	String sql = " select * from mbr_gift_ticket_use where ticket_num = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, gift_number);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ticket=1;
			} 
			return ticket;
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

package com.magic.crm.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.magic.crm.order.entity.GiftTicketUse;

/**
 * ��ȯʹ�����
 * 
 * @author user
 * 
 */
public class GiftTicketUseDAO {

	/**
	 * ���ݻ�Ա�ź���ȯ�Ų��Ҽ�¼
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public GiftTicketUse loadByUnionPK(Connection conn, GiftTicketUse data)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GiftTicketUse newData = null;
		String sql = "select * from MBR_GIFT_TICKET_USE where mbrid = ? and ticket_num = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, data.getMemberId());
			pstmt.setString(2, data.getTicketNum());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				newData = new GiftTicketUse();
				newData.setMemberId(rs.getInt("mbrid"));
				newData.setTicketNum(rs.getString("ticket_num"));
				newData.setTotalNum(rs.getInt("total_num"));
				newData.setNum(rs.getInt("num"));
				newData.setModDate(rs.getDate("mod_date"));
				newData.setIsUpdateable(rs.getString("is_updateable"));
				return newData;
			} else {
				return null;
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
	 * �ĵ���ȯ�����ʹ�ô���
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public int getTotalUseOfTicket(Connection conn, String ticketNumber)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select nvl(sum(num), 0) as total_num from MBR_GIFT_TICKET_USE where ticket_num = ? and status>=0 ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ticketNumber);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
			return 0;
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
	 * �õ���ȯ����ʹ���ܴ���
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public int getTotalUseOfTicket(Connection conn, GiftTicketUse data)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select nvl(sum(num), 0) as total_num from MBR_GIFT_TICKET_USE where ticket_num = ? and status>=0 ";
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, data.getTicketNum());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
			return 0;
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
	 * �õ���ȯ����ʹ���ܴ���
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public int getPersonUseOfTicket(Connection conn, GiftTicketUse data)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select nvl(sum(num), 0) as total_num from MBR_GIFT_TICKET_USE " +
				" where ticket_num = ? and mbrid = ?  and status>=0 ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getTicketNum());
		    pstmt.setLong(2, data.getMemberId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
			return 0;
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
	 * ����һ����¼
	 * 
	 * @param conn
	 * @param order_gifts
	 * @throws SQLException
	 */
	public void insert(Connection conn, GiftTicketUse data) throws SQLException {
		PreparedStatement pstmt = null;

		String sql = "insert into MBR_GIFT_TICKET_USE "
				+ "(MBRID, TICKET_NUM, TOTAL_NUM, NUM, MOD_DATE, IS_UPDATEABLE) "
				+ " values (?, ?, ?, ?, sysdate, '0') ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, data.getMemberId());
			pstmt.setString(2, data.getTicketNum());
			pstmt.setInt(3, data.getTotalNum());
			pstmt.setInt(4, data.getNum());
			pstmt.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null)
				pstmt.close();
		}

	}

	
	
	/**
	 * ����һ����¼
	 * 
	 * @param conn
	 * @param order_gifts
	 * @throws SQLException
	 */
	public void update(Connection conn, GiftTicketUse data) throws SQLException {
		PreparedStatement pstmt = null;

		String sql = "update MBR_GIFT_TICKET_USE set num = ?, mod_date = sysdate "

				+ "where mbrid = ? and ticket_num = ? ";
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, data.getNum());
			pstmt.setInt(2, data.getMemberId());
			pstmt.setString(3, data.getTicketNum());
			pstmt.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null)
				pstmt.close();
		}

	}

	/**
	 * ����һ����¼
	 * 
	 * @param conn
	 * @param order_gifts
	 * @throws SQLException
	 */
	public void update(Connection conn, int memberId, String ticketNumber)
			throws SQLException {
		GiftTicketUse param = new GiftTicketUse();
		param.setMemberId(memberId);
		param.setTicketNum(ticketNumber);
		update(conn, param);
	}

	/**
	 * ʹ�ô�����1
	 * @param conn
	 * @param memberId
	 * @param ticketNumber
	 * @throws SQLException
	 */
	public int addUseNumber(Connection conn, int memberId, String ticketNumber)
			throws SQLException {
		PreparedStatement pstmt = null;

		String sql = "update MBR_GIFT_TICKET_USE set num = num - 1, mod_date = sysdate "

				+ "where mbrid = ? and ticket_num = ? ";
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, memberId);
			pstmt.setString(2, ticketNumber);
			return pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null)
				pstmt.close();
		}
	}
}

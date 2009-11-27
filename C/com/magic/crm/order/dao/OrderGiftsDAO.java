package com.magic.crm.order.dao;

import java.util.Collection;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.magic.crm.order.entity.OrderGifts;

/**
 * 
 * @author user
 * 
 */
public class OrderGiftsDAO {

	/**
	 * 查找详情
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public OrderGifts getRecordByPK(Connection conn, long id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderGifts order_gifts = null;
		String sql = " select * from ord_gifts where go_id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				order_gifts = new OrderGifts();
				order_gifts.setId(rs.getLong("go_id"));
				order_gifts.setOrderId(rs.getInt("order_id"));
				order_gifts.setGiftNumber(rs.getString("gift_number"));
				order_gifts.setDisAmt(rs.getDouble("dis_amt"));
				return order_gifts;
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
	 * 查找详情
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public OrderGifts getRecordByTicket(Connection conn, String ticketNumber, int orderId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderGifts order_gifts = null;
		String sql = " select * from ord_gifts where gift_number = ? and order_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ticketNumber);
			pstmt.setInt(2, orderId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				order_gifts = new OrderGifts();
				order_gifts.setId(rs.getLong("go_id"));
				order_gifts.setOrderId(rs.getInt("order_id"));
				order_gifts.setGiftNumber(rs.getString("gift_number"));
				order_gifts.setDisAmt(rs.getDouble("dis_amt"));
				order_gifts.setAward_id(rs.getInt("award_id"));
				
				return order_gifts;
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
	 * 插入一条记录
	 * 
	 * @param conn
	 * @param order_gifts
	 * @throws SQLException
	 */
	public void insert(Connection conn, OrderGifts order_gifts)
			throws SQLException {
		PreparedStatement pstmt = null;

		String sql = "insert into ORD_GIFTS (GO_ID, ORDER_ID, GIFT_NUMBER, DIS_AMT,award_id) "
				+ " values (seq_ord_gifts_id.nextval, ?, ?, ?,?) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, order_gifts.getOrderId());
			pstmt.setString(2, order_gifts.getGiftNumber());
			pstmt.setDouble(3, order_gifts.getDisAmt());
			pstmt.setLong(4,order_gifts.getAward_id());
			pstmt.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null)
				pstmt.close();
		}

	}

	/**
	 * 根据订单删除
	 * @param conn
	 * @param orderId
	 * @throws SQLException
	 */
	public void deleteByOrderId(Connection conn, int orderId)
			throws SQLException {
		PreparedStatement pstmt = null;
		String sql1 = " update mbr_gift_ticket_use set num = num-1 " +
				" where num>0 and id in " +
				" (select award_id from ord_gifts where order_id = ? )";
		
		String sql2 = "delete from ORD_GIFTS where order_id = ? ";
		try {
			//pstmt = conn.prepareStatement(sql1);
			//pstmt.setLong(1,orderId);
			//pstmt.executeUpdate();
			//pstmt.close();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setLong(1,orderId);
			pstmt.executeUpdate();
			
			
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null)
				pstmt.close();
		}

	}

	/**
	 * 通过订单查找列表
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Collection getRecordsByOrderId(Connection conn, int orderId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select a.*, c.gift_number as ticket_header "
			+ "from ord_gifts a left join mbr_gift_lists b on a.gift_number = b.gift_no "
			+ "left join mbr_gift_certificates c on b.gift_number = c.gift_number "
			+ "where a.order_id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, orderId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderGifts order_gifts = new OrderGifts();
				order_gifts.setId(rs.getLong("go_id"));
				order_gifts.setOrderId(rs.getInt("order_id"));
				order_gifts.setGiftNumber(rs.getString("gift_number"));
				order_gifts.setDisAmt(rs.getDouble("dis_amt"));
				order_gifts.getTicket().setGiftNumber(rs.getString("ticket_header"));
				order_gifts.setAward_id(rs.getLong("award_id"));
				//order_gifts.getMoneyByOrder().setDis_type(rs.getInt("dis_type"));
				//order_gifts.getMoneyByOrder().setIs_discount(rs.getString("is_discount"));
				coll.add(order_gifts);

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

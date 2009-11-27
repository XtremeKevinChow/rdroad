/*
 * Created on 2005-2-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import com.magic.crm.util.DateUtil;
import com.magic.crm.common.pager.CompSQL;
import com.magic.crm.member.entity.MemberAWARD;
import com.magic.crm.member.form.MbrGetAwardForm;
import com.magic.crm.order.dao.TicketDAO;
import com.magic.crm.order.entity.OneTicket;

/**
 * @author user1 TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGetAwardDAO {

	public int insert(Connection conn, MemberAWARD info) throws SQLException {
		PreparedStatement pstmt = null;
		int awardid = 0;
		double price = 0;
		ResultSet rs = null;
		try {
			String AwardSql = "select seq_mbr_get_award_id.nextval from dual";
			pstmt = conn.prepareStatement(AwardSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				awardid = rs.getInt(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
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
		try {
			String getItem_ID = "select standard_price from prd_items where item_id="
					+ "";// info.getItem_ID();
			pstmt = conn.prepareStatement(getItem_ID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				price = rs.getDouble(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
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
		try {
			String sQuery = "INSERT INTO MBR_GET_AWARD( "
					+ "ID, member_id, item_id, price, status,  OPERATOR_ID,  type,"
					+ "ORDER_REQUIRE,clubid, last_date, description)"
					+ "values(?,?,?,?,?,?,?,?,?, ?, ?)";
			pstmt = conn.prepareStatement(sQuery);

			pstmt.setInt(1, awardid);
			pstmt.setInt(2, info.getMember_ID());
			// pstmt.setInt(3, info.getItem_ID());

			pstmt.setInt(5, 0);
			pstmt.setInt(6, info.getOperator_id());

			if (info.getType() == 7 || info.getType() == 17) {// 入会礼品
				pstmt.setDouble(4, info.getPrice());
				pstmt.setInt(7, info.getType());
				pstmt.setDouble(8, info.getOrder_require());
				pstmt.setInt(9, info.getClubID());
			} else if (info.getType() == 16) {// 电子礼券
				pstmt.setDouble(4, 0);
				pstmt.setInt(7, 16);
				pstmt.setInt(8, 0);
				pstmt.setInt(9, 1);
			} else if (info.getType() == 18) {// 3周年礼品
				pstmt.setDouble(4, 0);
				pstmt.setInt(7, 18);
				pstmt.setInt(8, 0);
				pstmt.setInt(9, 1);
			} else { // 会员卡
				pstmt.setDouble(4, price);
				pstmt.setInt(7, 8);
				pstmt.setInt(8, 0);
				pstmt.setInt(9, 1);
			}

			pstmt.setDate(10, DateUtil.getSqlDate(DateUtil.getDate(info
					.getLastDate(), "yyyy-MM-dd")));
			pstmt.setString(11, info.getDescription());

			pstmt.execute();

		} catch (SQLException e) {
			System.out.println("memberAward insert sql is error");
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

		return awardid;
	}

	/**
	 * 判断是否已经换过卡
	 * 
	 * @param conn
	 * @param memberId
	 * @throws SQLException
	 */
	public static boolean hasChangedNewCard(Connection conn, int memberId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			String sql = "select * from mbr_get_award where type = 8 "
					+ "and status in(0, 1) and price = 0 and member_id = ? and create_date >= date'2008-01-05' and create_date < date'2008-03-01' ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return flag;
	}

	/**
	 * 插入新的会员卡
	 * 
	 * @param conn
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public static void insertNewCard(Connection conn, MemberAWARD info)
			throws SQLException {
		PreparedStatement pstmt = null;
		int awardid = 0;
		ResultSet rs = null;
		try {
			String AwardSql = "select seq_mbr_get_award_id.nextval from dual";
			pstmt = conn.prepareStatement(AwardSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				awardid = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
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

		try {
			String sQuery = "INSERT INTO MBR_GET_AWARD( "
					+ "ID, member_id, item_id, price, status, OPERATOR_ID, type,"
					+ "ORDER_REQUIRE,clubid, last_date, description)"
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, awardid);
			pstmt.setInt(2, info.getMember_ID());
			// pstmt.setInt(3, info.getItem_ID());
			pstmt.setInt(5, 0);
			pstmt.setInt(6, info.getOperator_id());
			pstmt.setDouble(4, 0);// 新卡价格为0
			pstmt.setInt(7, 8);
			pstmt.setInt(8, 0);
			pstmt.setInt(9, 1);
			pstmt.setDate(10, DateUtil.getSqlDate(DateUtil.getDate(info
					.getLastDate(), "yyyy-MM-dd")));
			pstmt.setString(11, info.getDescription());
			pstmt.execute();
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

	}

	public int countMemberAward2(Connection conn, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		String sQuery = "SELECT count(1) "
				+ "from MBR_GET_AWARD a left join prd_item b on a.item_code= b.itm_code "
				+ "left join org_persons c on c.id = a.operator_id "
				+ "left join mbr_members e on a.member_id = e.id where a.id>0 "
				+ condition + " order by a.create_date desc ";

		try {
			pstmt = conn.prepareStatement(sQuery.toString());
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				cnt = rs.getInt(1);
			}
			return cnt;

		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}
	}

	public int countMemberGiftNumber(Connection conn, String condition)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		String sQuery = "SELECT count(1) "
				+ "from mbr_gift_ticket_use a join mbr_members b on a.mbrid = b.id "
				+ " where 1=1  " + condition;

		try {
			pstmt = conn.prepareStatement(sQuery.toString());
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				cnt = rs.getInt(1);
			}
			return cnt;

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
	 * 查询会员暂存架(控制台)
	 * 
	 * @param con
	 * @param memberId
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public Collection queryMemberConsoleAward(Connection con, long memberId,
			MbrGetAwardForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();
		try {

			String sQuery = "SELECT a.*, b.itm_name, c.name as operator_name,d.name as color_name, (last_date - sysdate) as isvalid_date "
					+ " from MBR_GET_AWARD a left join prd_item b on a.item_code= b.itm_code "
					+ " left join prd_item_color d on a.color_code = d.code "
					+ " left join org_persons c on c.id = a.operator_id  where a.id>0 "
					+ " and a.member_id=? order by a.create_date desc ";

			sQuery = CompSQL.getNewSql(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, param.getPager().getOffset()
					+ param.getPager().getLength());
			pstmt.setInt(3, param.getPager().getOffset());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberAWARD info = new MemberAWARD();

				info.setID(rs.getInt("id"));
				info.setMember_ID(rs.getInt("member_ID"));
				// info.setItem_code(rs.getString("item_code"));
				info.setPrice(rs.getDouble("price"));
				info.setCreate_date(rs.getString("create_date"));
				info.setUsed_amount_exp(rs.getInt("used_amount_exp"));
				info.setGift_number(rs.getString("gift_number"));
				info.setSku_id(rs.getInt("sku_id"));
				info.setColor_code(rs.getString("color_code"));
				info.setColor_name(rs.getString("color_name"));
				double isValidDate = rs.getDouble("isvalid_date");

				if (rs.getInt("status") == 0 && isValidDate < 0) { // 未发礼品已经过期
					info.setStatus(-5);
				} else {
					info.setStatus(rs.getInt("status"));
				}

				info.setOperator_id(rs.getInt("operator_id"));
				info.setQuantity(rs.getInt("quantity"));
				info.setPricelist_line_ID(rs.getInt("pricelist_line_ID"));
				info.setREF_ORDER_LINE_ID(rs.getInt("REF_ORDER_LINE_ID"));
				info.setDescription(rs.getString("description"));

				info.setOrderRequire(rs.getDouble("ORDER_REQUIRE"));
				info.setLastDate(rs.getString("LAST_DATE"));
				info.setType(rs.getInt("TYPE"));
				info.setIsTransfer(rs.getInt("IS_TRANSFER"));
				info.setItemCode(rs.getString("item_code"));
				info.setItemName(rs.getString("itm_name"));
				info.setOperatorName(rs.getString("operator_name"));

				memberCol.add(info);
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
		return memberCol;
	}

	public int countMemberAward(Connection conn, long memberId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		String sql = "SELECT count(*) from MBR_GET_AWARD where member_id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				cnt = rs.getInt(1);
			}
			return cnt;

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
	 * 得到个人当前的礼券
	 * 
	 * @param conn
	 * @param ticketNumbe
	 * @param memberId
	 * @return
	 * @throws SQLException
	 */
	public MemberAWARD getAvailableTicket(Connection conn, String ticketNumber,
			int memberId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberAWARD info = null;
		String sql = "select t1.*,t3.end_date from mbr_gift_ticket_use t1 join mbr_gift_lists t2 on t1.ticket_num=t2.GIFT_NO "
				+ " join mbr_gift_certificates t3 on t2.gift_number=t3.gift_number "
				+ " where t1.mbrid = ? and t1.ticket_num = ? and t1.status = 0 and t1.num < t1.total_num ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			pstmt.setString(2, ticketNumber);
			// pstmt.setInt(3, type);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				info = new MemberAWARD();
				info.setMember_ID(rs.getInt("mbrid"));
				info.setGift_number(rs.getString("ticket_num"));
				// info.setItemCode(rs.getString("item_code"));
				// info.setPrice(rs.getDouble("price"));
				info.setCreate_date(rs.getString("mod_date"));
				info.setStatus(rs.getInt("status"));
				info.setID(rs.getInt("id"));
				info.setOperator_id(rs.getInt("operator_id"));
				// info.setQuantity(rs.getInt("quantity"));
				// info.setPricelist_line_ID(rs.getInt("pricelist_line_id"));
				// info.setDescription(rs.getString("description"));
				// info.setREF_ORDER_LINE_ID(rs.getInt("ref_order_line_id"));
				info.setType(rs.getInt("sell_type"));
				// info.setOrderRequire(rs.getDouble("order_require"));
				// info.setClubID(rs.getInt("clubid"));
				// info.setIsTransfer(rs.getInt("is_transfer"));
				info.setLastDate(rs.getString("end_date"));
				info.setTotal_num(rs.getInt("total_num"));
				info.setNum(rs.getInt("num"));
			}
			return info;
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
	}

	/**
	 * 插入会员礼券
	 * 
	 * @param conn
	 * @param ticketNumbe
	 * @param memberId
	 * @return
	 * @throws SQLException
	 */
	public MemberAWARD insertMemberTicket(Connection conn, String ticketNumber,
			int memberId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberAWARD info = null;
		String sql = "insert into mbr_gift_ticket_use(id,mbrid,ticket_num,sell_type,num) values(seq_mbr_get_award_id.nextval,?,?,16,0)";
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			pstmt.setString(2, ticketNumber);
			pstmt.executeUpdate();
			pstmt.close();

			return getAvailableTicket(conn,ticketNumber,memberId);
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
	}

	/**
	 * 得到个人当前的礼券
	 * 
	 * @param conn
	 * @param ticketNumbe
	 * @param memberId
	 * @return
	 * @throws SQLException
	 */
	public MemberAWARD getTicketByAwardId(Connection conn, long award_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberAWARD info = null;
		String sql = "select t1.*,t3.end_date from mbr_gift_ticket_use t1 join mbr_gift_lists t2 on t1.ticket_num=t2.GIFT_NO "
				+ " join mbr_gift_certificates t3 on t2.gift_number=t3.gift_number "
				+ " where t1.id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, award_id);

			rs = pstmt.executeQuery();
			if (rs.next()) {

				info = new MemberAWARD();
				info.setMember_ID(rs.getInt("mbrid"));
				info.setGift_number(rs.getString("ticket_num"));
				// info.setItemCode(rs.getString("item_code"));
				// info.setPrice(rs.getDouble("price"));
				info.setCreate_date(rs.getString("mod_date"));
				info.setStatus(rs.getInt("status"));
				info.setID(rs.getInt("id"));
				info.setOperator_id(rs.getInt("operator_id"));
				// info.setQuantity(rs.getInt("quantity"));
				// info.setPricelist_line_ID(rs.getInt("pricelist_line_id"));
				// info.setDescription(rs.getString("description"));
				// info.setREF_ORDER_LINE_ID(rs.getInt("ref_order_line_id"));
				info.setType(rs.getInt("sell_type"));
				// info.setOrderRequire(rs.getDouble("order_require"));
				// info.setClubID(rs.getInt("clubid"));
				// info.setIsTransfer(rs.getInt("is_transfer"));
				info.setLastDate(rs.getString("end_date"));
				info.setTotal_num(rs.getInt("total_num"));
				info.setNum(rs.getInt("num"));
			}
			return info;
		} catch (SQLException e) {
			e.printStackTrace();
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
	}

	public MemberAWARD findById(Connection conn, long awardId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberAWARD info = null;
		String sql = "select * from mbr_get_award where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, awardId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info = new MemberAWARD();
				info.setMember_ID(rs.getInt("member_id"));
				info.setItemCode(rs.getString("item_code"));
				info.setPrice(rs.getDouble("price"));
				info.setCreate_date(rs.getString("create_date"));
				info.setStatus(rs.getInt("status"));
				info.setID(rs.getInt("id"));
				info.setOperator_id(rs.getInt("operator_id"));
				info.setQuantity(rs.getInt("quantity"));
				info.setPricelist_line_ID(rs.getInt("pricelist_line_id"));
				info.setDescription(rs.getString("description"));
				info.setREF_ORDER_LINE_ID(rs.getInt("ref_order_line_id"));
				info.setType(rs.getInt("type"));
				info.setOrderRequire(rs.getDouble("order_require"));
				info.setClubID(rs.getInt("clubid"));
				info.setIsTransfer(rs.getInt("is_transfer"));
				info.setLastDate(rs.getString("last_date"));

			}
			return info;
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
	}

	public Collection queryMemberAWARD(Connection con, String condition,
			MbrGetAwardForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "SELECT a.*, b.itm_name, c.name as operator_name,d.name as color_name, (last_date - sysdate) as isvalid_date "
					+ " from MBR_GET_AWARD a left join prd_item b on a.item_code= b.itm_code "
					+ " left join prd_item_color d on a.color_code = d.code "
					+ " left join org_persons c on c.id = a.operator_id  "
					+ " left join mbr_members e on a.member_id = e.id where a.id>0 "
					+ condition + " order by a.create_date desc ";
			System.out.println(sQuery);
			sQuery = CompSQL.getNewSql(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, param.getPager().getOffset()
					+ param.getPager().getLength());
			pstmt.setInt(2, param.getPager().getOffset());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberAWARD info = new MemberAWARD();

				info.setID(rs.getInt("id"));
				info.setMember_ID(rs.getInt("member_ID"));
				// info.setItem_code(rs.getString("item_code"));
				info.setPrice(rs.getDouble("price"));
				info.setCreate_date(rs.getString("create_date"));
				info.setUsed_amount_exp(rs.getInt("used_amount_exp"));
				info.setGift_number(rs.getString("gift_number"));
				info.setSku_id(rs.getInt("sku_id"));
				info.setColor_code(rs.getString("color_code"));
				info.setColor_name(rs.getString("color_name"));
				double isValidDate = rs.getDouble("isvalid_date");

				if (rs.getInt("status") == 0 && isValidDate < 0) { // 未发礼品已经过期
					info.setStatus(-5);
				} else {
					info.setStatus(rs.getInt("status"));
				}

				info.setOperator_id(rs.getInt("operator_id"));
				info.setQuantity(rs.getInt("quantity"));
				info.setPricelist_line_ID(rs.getInt("pricelist_line_ID"));
				info.setREF_ORDER_LINE_ID(rs.getInt("REF_ORDER_LINE_ID"));
				info.setDescription(rs.getString("description"));

				info.setOrderRequire(rs.getDouble("ORDER_REQUIRE"));
				info.setLastDate(rs.getString("LAST_DATE"));
				info.setType(rs.getInt("TYPE"));
				info.setIsTransfer(rs.getInt("IS_TRANSFER"));
				info.setItemCode(rs.getString("item_code"));
				info.setItemName(rs.getString("itm_name"));
				info.setOperatorName(rs.getString("operator_name"));

				memberCol.add(info);
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
		return memberCol;
	}

	/**
	 * 查询会员所有的礼券
	 * 
	 * @param con
	 * @param condition
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public Collection queryMemberGiftNumber(Connection con, String condition,
			MbrGetAwardForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "SELECT a.*, c.name as operator_name, (t3.end_date - sysdate) as isvalid_date,t3.end_date "
					+ " from mbr_gift_ticket_use a join mbr_gift_lists t2 on a.ticket_num = t2.gift_no "
					+ " join mbr_gift_certificates t3 on t2.gift_number = t3.gift_number "
					+ " join mbr_members b on a.mbrid = b.id "
					+ " left join org_persons c on c.id = a.operator_id  where 1=1 "
					+ condition + " order by a.mod_date desc ";
			System.out.println(sQuery);
			sQuery = CompSQL.getNewSql(sQuery);
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, param.getPager().getOffset()
					+ param.getPager().getLength());
			pstmt.setInt(2, param.getPager().getOffset());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberAWARD info = new MemberAWARD();

				info.setID(rs.getInt("id"));
				info.setMember_ID(rs.getInt("mbrid"));
				// info.setItem_code(rs.getString("item_code"));
				// info.setPrice(rs.getDouble("price"));
				info.setCreate_date(rs.getString("mod_date"));
				info.setUsed_amount_exp(rs.getInt("used_amount_exp"));
				info.setGift_number(rs.getString("ticket_num"));
				// info.setSku_id(rs.getInt("sku_id"));
				// info.setColor_code(rs.getString("color_code"));
				// info.setColor_name(rs.getString("color_name"));
				info.setTotal_num(rs.getInt("total_num"));
				info.setNum(rs.getInt("num"));

				double isValidDate = rs.getDouble("isvalid_date");

				if (rs.getInt("status") == 0 && isValidDate < 0) { // 未发礼品已经过期
					info.setStatus(-5);
				} else {
					info.setStatus(rs.getInt("status"));
				}

				info.setOperator_id(rs.getInt("operator_id"));

				info.setDescription(rs.getString("description"));
				info.setLastDate(rs.getString("end_date"));
				info.setType(rs.getInt("sell_TYPE"));

				info.setOperatorName(rs.getString("operator_name"));

				memberCol.add(info);
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
		return memberCol;
	}

	public static Collection qryActiveGiftNumber(Connection con,
			MbrGetAwardForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();

		try {
			String sQuery = "SELECT t1.*,t3.* "
					+ " from mbr_gift_ticket_use t1 join mbr_gift_lists t2 on t1.ticket_num =t2.gift_no  "
					+ " left join  mbr_gift_certificates t3 on t2.gift_number = t3.gift_number "
					+ " where t1.status=0 and t1.num<t1.total_num "
					+ " and t3.end_date>sysdate+1 and t1.mbrid= (select id from mbr_members where card_id= ?)"
					+ " order by t3.end_date asc ";

			pstmt = con.prepareStatement(sQuery);
			// pstmt.setInt(1, param.getPager().getOffset()
			// + param.getPager().getLength());
			// pstmt.setInt(2, param.getPager().getOffset());
			pstmt.setString(1, param.getCardID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberAWARD info = new MemberAWARD();

				// info.setID(rs.getInt("id"));
				// info.setMember_ID(rs.getInt("mbrid"));
				// info.setItem_code(rs.getString("item_code"));
				// info.setPrice(rs.getDouble("price"));
				info.setCreate_date(rs.getString("mod_date"));
				// info.setUsed_amount_exp(rs.getInt("used_amount_exp"));
				info.setGift_number(rs.getString("ticket_num"));
				// info.setSku_id(rs.getInt("sku_id"));
				// info.setColor_code(rs.getString("color_code"));
				// info.setColor_name(rs.getString("color_name"));
				info.setTotal_num(rs.getInt("total_num"));
				info.setNum(rs.getInt("num"));

				// double isValidDate = rs.getDouble("last_date");

				// if (rs.getInt("status") == 0 && isValidDate < 0) { //
				// 未发礼品已经过期
				// info.setStatus(-5);
				// } else {
				// info.setStatus(rs.getInt("status"));
				// }

				// info.setOperator_id(rs.getInt("operator_id"));
				info.setOrder_require(rs.getDouble("order_money"));
				info.setPrice(rs.getDouble("gift_money"));
				info.setDescription(rs.getString("description"));
				info.setLastDate(rs.getString("end_DATE"));
				// info.setType(rs.getInt("sell_TYPE"));

				// info.setOperatorName(rs.getString("operator_name"));

				memberCol.add(info);
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
		return memberCol;
	}

	public double getOrder_require(Connection con, String item_id,
			String msc_code) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();
		double x = 0;
		try {
			String sQuery = "SELECT order_require from MBR_msc_gift where msc_code='"
					+ msc_code + "' and item_id='" + item_id + "'";

			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getDouble("order_require");
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
		return x;
	}

	public double getOrder_Addmoney(Connection con, String item_id,
			String msc_code) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberCol = new ArrayList();
		double x = 0;
		try {
			String sQuery = "SELECT addmoney from MBR_msc_gift where msc_code='"
					+ msc_code + "' and item_id='" + item_id + "'";

			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getDouble("addmoney");
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
		return x;
	}

	public static boolean getAwardSatus(Connection con, String member_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean status = false;

		try {
			String sQuery = "SELECT status from MBR_GET_AWARD where status =0 and item_id in (100000,100002) and member_id="
					+ member_id;
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				status = true;
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
		return status;
	}

	/**
	 * 判断是否得到过了某种礼品
	 * 
	 * @param con
	 * @param member_id
	 * @return
	 * @throws SQLException
	 */
	public boolean checkTypeOfGift(Connection conn, int awardType, int memberId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean status = false;
		String sql = "select 1 from mbr_get_award where member_id= ? and type = ? and status in(0, 1) and sysdate<=last_date + 1 ";
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			pstmt.setInt(2, awardType);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				status = true;
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
		return status;
	}

	/*
	 * 修改礼品表的卡类型
	 */
	public void updateStatus(Connection conn, double price, int card_code,
			int id) throws SQLException {
		PreparedStatement pstmt = null;
		int awardid = 0;

		try {
			String sQuery = "update MBR_GET_AWARD set item_id=?, price=? where status=0 and item_id in (100000,100002) and member_id=? ";
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, card_code);
			pstmt.setDouble(2, price);
			pstmt.setInt(3, id);

			pstmt.execute();

		} catch (SQLException e) {
			System.out.println("memberAward insert sql is error");
			throw e;
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}

	/**
	 * 更新暂存架礼券状态
	 * 
	 * @param conn
	 * @param memberId
	 * @param ticketNumber
	 * @throws SQLException
	 */
	public static int updateTicketStatus(Connection conn, int memberId,
			String ticketNumber) throws SQLException {

		PreparedStatement pstmt = null;

		try {
			String sQuery = "update MBR_GET_AWARD set status = 1 where status = 0 and member_id = ? and gift_Number = ? ";
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, memberId);
			pstmt.setString(2, ticketNumber);

			int i = pstmt.executeUpdate();
			return i;
		} catch (SQLException e) {
			throw e;
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}

	/**
	 * 重置暂存架礼券状态
	 * 
	 * @param conn
	 * @param memberId
	 * @param ticketNumber
	 * @throws SQLException
	 */
	public static int resetTicketStatus(Connection conn, int memberId,
			String ticketNumber) throws SQLException {

		PreparedStatement pstmt = null;

		try {
			String sQuery = "update MBR_GET_AWARD set status = 0 where status = 1 and member_id = ? and description = ? ";
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1, memberId);
			pstmt.setString(2, ticketNumber);

			int i = pstmt.executeUpdate();
			return i;
		} catch (SQLException e) {
			throw e;
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}

	/**
	 * 取得会员可用的礼券数量
	 * 
	 * @param con
	 * @param member_id
	 * @return
	 * @throws SQLException
	 */
	public static int getAvailableGiftNumber(Connection con, int member_id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int ret = 0;

		try {
			String sQuery = "SELECT count(1) from mbr_gift_ticket_use t1 join mbr_gift_lists t2 on t1.ticket_num = t2.gift_no "
					+ " join mbr_gift_certificates t3 on t2.gift_number= t3.gift_number "
					+ " where mbrid=? "
					+ " and  t1.status =0 and sysdate < t3.end_date+1 and num < total_num";

			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, member_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ret = rs.getInt(1);
			}
			rs.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return ret;
	}
}

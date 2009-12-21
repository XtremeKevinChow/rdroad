/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.magic.utils.Arith;
import org.apache.log4j.Logger;

import com.magic.crm.user.dao.S_AREADao;
import com.magic.crm.util.DateUtil;
import com.magic.crm.util.KeyValue;
import com.magic.crm.common.Constants;
import com.magic.crm.common.DBOperation;
import com.magic.crm.common.LogicUtility;
import com.magic.crm.common.SequenceManager;
import com.magic.crm.common.WebData;
import com.magic.crm.member.dao.MemberBlackListDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.entity.Money4Qty;
import com.magic.crm.order.entity.Proms2;
import com.magic.crm.order.entity.TicketMoney;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.util.MD5;
import com.magic.crm.product.dao.Product2DAO;
import com.magic.crm.product.dao.ProductBaseDAO;
import com.magic.crm.product.dao.ProductDAO;
import com.magic.crm.product.dao.ProductSKUDAO;
import com.magic.crm.promotion.dao.PromotionDAO;
import com.magic.crm.product.form.ProductForm;
import com.magic.crm.product.form.ProductSKUForm;
import com.magic.crm.promotion.entity.Prom_gift;
import com.magic.crm.order.entity.Order;
import com.magic.crm.util.CodeName;

/**
 * @author Water
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OrderDAO {

	private static Logger log = Logger.getLogger(OrderDAO.class);

	/**
	 * 更新幸运卡使用信息
	 * 
	 * @param conn
	 * @param ticketNumber
	 * @param status
	 * @throws SQLException
	 */
	public static void updateGGTicket(Connection conn, String ticketNumber,
			int status) throws SQLException {

		PreparedStatement pstmt = null;

		String sql = "update ggcard_mst set is_used = ?, used_date = sysdate where card_num =? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setString(2, ticketNumber);

			pstmt.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null)
				pstmt.close();
		}
	}

	/**
	 * 检查幸运卡礼券
	 * 
	 * @param conn
	 * @param data
	 * @return 0-正常
	 * @throws Exception
	 */
	public static int checkTicket(Connection conn, OrderForm data)
			throws Exception {
		String strNum = data.getTicketNumber().trim();
		if (strNum.length() == 0) {

			return -1;
		}
		Statement st = null;
		ResultSet rs = null;
		String strSql = null;

		try {
			st = conn.createStatement();
			// 2 检查是否乐透卡礼券
			String pass = new MD5().getMD5ofStr(data.getTicketPassword())
					.toLowerCase();

			strSql = "select * from ggcard_mst where card_num = '" + strNum
					+ "'";

			rs = st.executeQuery(strSql);
			if (!rs.next()) {
				rs.close();
				return -1;

			} else {

				if (data.getOrderId() == 0) {// 新建订单
					if (rs.getInt("is_used") == 1) {
						return -3;
					}
				} else {// 修改订单，getRecordByTicket返回null，证明该礼券是被别的订单使用的，
					// 不是null表示是本订单使用的
					if (rs.getInt("is_used") == 1
							&& (new OrderGiftsDAO().getRecordByTicket(conn,
									strNum, data.getOrderId()) == null)) {
						return -3;
					}
				}
				if (!rs.getString("pass").equals(pass)) {
					return -2;
				}

				if (DateUtil.getSqlDate().after(
						DateUtil.addDay(rs.getDate("end_date"), 1))) {
					return -4;
				}
				/*
				 * if ( DateUtil.getSqlDate().after(rs.getDate("end_date"))) {
				 * return -4; }
				 */
				if (data.getCart().getMember().getLEVEL_ID() <= 1) {// 临时会员（0）
					// 和普通会员
					// （1）不能使用幸运卡

					return -5;
				}
				// 3 列出乐透卡礼券对应礼品
				String card_type = rs.getString("card_type");
				rs = st
						.executeQuery("select a.* from ggcard_detail a where a.card_type= '"
								+ card_type
								+ "' and overx <="
								+ data.getCart().getNotGiftMoney()
								+ " order by a.overx desc ");

				if (card_type.equals("A")) {
					if (rs.next()) {
						String itemCode = rs.getString("giftid");
						ItemInfo item = OrderDAO.findItem(conn, itemCode);
						OrderDAO.getStockStatus(conn, item);
						OrderDAO.getLandDate(conn, item);

						item.setValid(true);
						item.setSellTypeId(11);
						item.setSellTypeName("刮刮卡礼品");
						data.getCart().getTickets().add(item);
					}

				} else {// 进入这里
					if (rs.next()) {
						TicketMoney money = new TicketMoney();
						if (rs.getString("is_discount").equals("N")) {

							money.setTicketCode(strNum);
							money.setTicketType("3");
							money.setIsDiscount("N");
							money.setDisType(1);
							money.setMoney(rs.getDouble("price"));
							money.setOrder_floor(rs.getDouble("overx"));
						} else { // 折扣方式
							money.setTicketCode(strNum);
							money.setTicketType("3");
							money.setIsDiscount("Y");
							money.setDisType(1);
							money.setMoney(Arith.roundX((1 - rs
									.getDouble("price"))
									* data.getCart().getNotGiftMoney(), 1));
							money.setOrder_floor(rs.getDouble("overx"));
						}

						// 将幸运卡加入礼券金额List
						data.getCart().getTickets().add(money);
					} else {
						return -6;
					}
				}
				rs.close();

			}

		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		} finally {
			try {
				st.close();
			} catch (Exception e) {
			}
		}
		return 0;

	}

	/**
	 * 判断暂存架中是否有3周年礼品了
	 * 
	 * @author
	 */

	public static boolean isExist3YearGift(Connection conn, int memberId)
			throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = " select * from mbr_get_award where member_id = ? and type=18 and status <> -1 ";

		st = conn.prepareStatement(sql);
		st.setInt(1, memberId);
		rs = st.executeQuery();
		if (rs.next()) {
			return true;
		}
		rs.close();
		st.close();
		return false;
	}

	/**
	 * 下单时添加赠品
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void addLargess(Connection conn, OrderForm data)
			throws Exception {

		// 取得礼品表中的赠品
		String sql = " select t1.id,t1.Is_transfer,t1.quantity,t1.order_require, t1.item_code, t1.price,t2.sale_price,"
				+ " t1.clubid,t2.itm_name,t3.name as unit_name,t2.is_last_sell,t2.itm_type,t1.color_code,t1.sku_id, "
				+ " t1.type,t4.name as type_name,t1.prom_level,t1.used_amount_exp,t5.size_code "
				+ " from mbr_get_award t1, prd_item t2,s_uom t3,s_sell_type t4 ,prd_item_sku t5"
				+ " where t1.item_code = t2.itm_code and t2.itm_unit = t3.id(+) and t1.type = t4.id and t1.sku_id=t5.sku_id(+)"
				+ " and t1.status = 0  and t1.last_date>=sysdate and t1.member_id = ? ";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, data.getMbId());
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			String itemCode = rs.getString("item_code");
			data.setQueryItemCode(itemCode);
			ItemInfo item = new ItemInfo();
			item.setSku_id(rs.getInt("sku_id"));
			item.setItemCode(rs.getString("item_code"));
			item.setItemName(rs.getString("itm_name"));
			item.setLastSell(rs.getBoolean("is_last_sell"));
			item.setTruss(rs.getInt("itm_type") == 2
					|| rs.getInt("itm_type") == 3);
			item.setItemUnit(rs.getString("unit_name"));
			item.setAwardId(rs.getInt("id"));
			item.setItemPrice(rs.getDouble("price"));
			item.setItemSilverPrice(rs.getDouble("sale_price"));
			item.setItemQty(rs.getInt("quantity"));
			item.setRequired(rs.getInt("type") == 8);

			item.setFloorMoney(rs.getDouble("order_require"));

			// item.setReplacerId(rs.getInt("replace_sku_id"));
			item.setClubID(rs.getInt("clubid"));
			item.setSellTypeId(rs.getInt("type"));
			if (rs.getInt("type") == 12) {
				// item.setSellTypeId(4);
				item.setSellTypeName("礼品赠品");
				item.setProm_level(rs.getDouble("order_require"));// 替代品匹配条件
			} else {
				item.setSellTypeName(rs.getString("type_name"));
				if (rs.getInt("type") == 4) {
					item.setProm_level(rs.getDouble("order_require"));
				}
				if (rs.getInt("type") == 6) {
					item.setProm_level(rs.getDouble("used_amount_exp"));
				}
				if (rs.getInt("type") == 5) {
					item.setProm_level(0);
				}
				if (rs.getInt("type") == 15) {
					item.setProm_level(rs.getDouble("prom_level"));
				}
				// add by user
				if (rs.getInt("type") == 17) {
					item.setProm_level(rs.getDouble("price"));
				}
			}
			OrderDAO.getStockStatus(conn, item);
			item.setIs_transfer(rs.getInt("Is_transfer"));
			item.setColor_code(rs.getString("color_code"));
			item.setSize_code(rs.getString("size_code"));

			if (!item.isTruss()) {
				// item.setSize_code(rs.getString("size_code"));
				// item.setSizes(ProductBaseDAO.listSize(conn,
				// item.getItemCode()));
				item.setColors(Product2DAO.listItemColor(conn, itemCode));
				item.setSizes(Product2DAO.listItemSize(conn, itemCode));

				data.getCart().getGifts().add(item);
			} else {
				item.setSet_group_id(data.getCart().getSetGroupId()+1);
				ArrayList<ItemInfo> parts = OrderDAO.splitGiftSet2Part(conn,
						item);
				data.getCart().addGiftSet(parts);
			}
			// data.getCart().getGifts().add(item);
		} // end while
		rs.close();
		st.close();
	}

	/**
	 * 修改订单状态
	 * 
	 * @param db
	 * @param data
	 *            包含订单order_id
	 * @param newStatus
	 *            要置的新状态
	 * @return
	 * @throws Exception
	 */
	public static int updateOrderStatus(DBOperation db, int orderId,
			int newStatus) throws Exception {
		WebData wd = new WebData();
		wd.setTable("ord_headers");
		wd.setSubWhere("id = " + orderId);
		wd.addHeaderData("status", new Integer(newStatus));
		int nResult = db.modify(wd);
		wd = null;
		return nResult;
	}

	/**
	 * 插入订单历史表(added by user 2006-05-22 11:38)
	 * 
	 * @deprecated
	 * @param con
	 * @param orderId
	 * @throws SQLException
	 * @return
	 */
	/*
	 * public static int insertOrderHeaderHis(Connection con, int orderId)
	 * throws SQLException { PreparedStatement pstmt = null; String sql = null;
	 * try { sql =
	 * "insert into ord_headers_history select * from ord_headers where id = ? "
	 * ; pstmt = con.prepareStatement(sql); pstmt.setInt(1, orderId); return
	 * pstmt.executeUpdate(); } catch (SQLException ex) { throw ex; } finally {
	 * if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) {
	 * 
	 * } } }
	 */

	/**
	 * 插入订单历史表(added by user 2006-05-22 11:38)
	 * 
	 * @param con
	 * @param orderId
	 * @throws SQLException
	 * @return
	 */
	/*
	 * public static int insertOrderHeaderHis(DBOperation db, int orderId)
	 * throws SQLException {
	 * 
	 * String sql =
	 * "insert into ord_headers_history(id,so_number,goods_fee,append_fee,package_fee,delivery_fee,mod_date,modifier_id )"
	 * +
	 * " select id,so_number,goods_fee,append_fee,package_fee,delivery_fee,mod_date,modifier_id from ord_headers where id = "
	 * + orderId; return db.executeUpdate(sql);
	 * 
	 * }
	 */

	/**
	 * 修改订单最后操作人(added by user 2006-05-22 11:12)
	 * 
	 * @param con
	 * @param orderId
	 * @return
	 */
	public static int modifyOrderOperator(Connection con, int orderId,
			int modifierId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "update ord_headers set modifier_id = ? where id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, modifierId);
			pstmt.setInt(2, orderId);
			return pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {

				}
		}

	}

	/**
	 * 判断登陆人和修改人是否一致(added by user 2006-05-22 11:20)
	 * 
	 * @param con
	 * @param OrderId
	 * @param loginId
	 * @return
	 */
	public static boolean isTheSameOperator(Connection con, int orderId,
			int loginId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select id from ord_headers where id = ? and modifier_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderId);
			pstmt.setInt(2, loginId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {

				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {

				}
		}
	}

	/**
	 * 修改订单状态为修改中
	 * 
	 * @param db
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	/**
	 * public static int modifyOrderStatus(DBOperation db, int orderId, int
	 * loginId) throws Exception { int ret = 0; String status = ""; String sql ="select a.status,b.name from ord_headers a,s_order_status b where a.status=b.id and a.id ="
	 * + orderId; Statement st = db.conn.createStatement(); ResultSet rs =
	 * st.executeQuery(sql); if (rs.next()) { ret = rs.getInt("status"); status
	 * = rs.getString("name"); } rs.close(); st.close(); if (ret > 25 || ret <
	 * 0) { throw new Exception("订单状态为" + status + ",不能修改"); }
	 * 
	 * sql =
	 * "update ord_headers set old_status = status, status =-6, modifier_id = "
	 * + loginId + ", mod_date=sysdate where id = " + orderId; return
	 * db.executeUpdate(sql);
	 * 
	 * }
	 */
	public static int modifyOrderStatus(DBOperation db, int orderId, int loginId)
			throws Exception {
		// 调用存储过程
		CallableStatement cstmt = db.conn
				.prepareCall("{? = call F_ORDER_MODIFY(?,?)}");
		cstmt.setInt(2, orderId);
		// 操作人员
		cstmt.setInt(3, loginId);

		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

		cstmt.execute();

		// CallableStatement在本架构中会被自动关闭
		int ret = cstmt.getInt(1);
		cstmt.close();
		return ret;

	}

	public static int recoverOrderStatus(DBOperation db, int orderId,
			int loginId) throws Exception {
		String sql = "update ord_headers set status = nvl(old_status,0), old_status = null,mod_date = sysdate,modifier_id = "
				+ loginId + " where id =" + orderId;
		return db.executeUpdate(sql);
	}

	/**
	 * add by user 2008-03-14 记录取消人的信息
	 * 
	 * @param conn
	 * @param order
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	public static void recordCancelInfo(Connection conn, OrderForm order,
			int loginId) throws SQLException {

		PreparedStatement ps = null;
		String sql = "insert into ord_header_modify_his "
				+ "(so_number, op_type, op_user, goods_fee, op_time) "
				+ "values (?, ?, ?, ?, sysdate)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, order.getCart().getOrder().getOrderNumber());
			ps.setString(2, "取消修改");
			ps.setInt(3, loginId);
			ps.setDouble(4, order.getCart().getOrder().getGoodsFee());
			ps.execute();
		} catch (SQLException e) {
			throw e;

		} finally {
			if (ps != null) {
				ps.close();
			}
		}
		order.setStatusId(0);
	}

	/**
	 * 调用f_order_cancel存储过程取消订单 f_order_cancel2(int order_id, int operator_id)
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 * @return 1: 成功 其它：失败
	 */
	public static int cancelOrder(DBOperation db, OrderForm data)
			throws Exception {
		// 调用存储过程
		CallableStatement cstmt = db.conn
				.prepareCall("{?=call orders.f_order_cancel2(?,?)}");
		cstmt.setInt(2, data.getOrderId());
		// 操作人员
		cstmt.setInt(3, data.getCreatorId());

		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

		cstmt.execute();

		// CallableStatement在本架构中会被自动关闭
		int ret = cstmt.getInt(1);
		cstmt.close();
		return ret;

	}

	/**
	 * 调用f_order_cancel存储过程取消订单 f_order_cancel(int order_id, int operator_id)
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 * @return 1: 成功 其它：失败
	 */
	public static int cancelOrder(Connection db, OrderForm data)
			throws Exception {
		// 调用存储过程
		CallableStatement cstmt = db
				.prepareCall("{?=call orders.f_order_cancel2(?,?)}");
		cstmt.setInt(2, data.getOrderId());
		// 操作人员
		cstmt.setInt(3, data.getCreatorId());

		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.execute();

		int ret = cstmt.getInt(1);
		cstmt.close();
		return ret;
	}

	/**
	 * 运行订单检查过程，库存、金额等
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static int runOrder(Connection conn, OrderForm data)
			throws Exception {

		// 调用存储过程
		CallableStatement cstmt = conn
				.prepareCall("{?=call service.f_order_run(?)}");
		cstmt.setInt(2, data.getOrderId());

		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.execute();

		int ret = cstmt.getInt(1);
		cstmt.close();
		return ret;
	}

	/**
	 * 得到订单头信息，查询所得的信息存储于data中
	 * 
	 * @param db
	 * @param data
	 *            用其订单ID进行查询
	 * @throws Exception
	 */
	public static void getOrderHeadersInfo(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select t1.id, t1.so_number, t2.id as mbId,t2.is_organization, "
				+ " t2.card_id, t2.name as mbname, t1.goods_fee, t1.pay_discount, "
				+ "t1.order_sum, t1.delivery_fee, t1.gift_number, t1.append_fee,"
				+ "t1.status as statusid, t3.name as statusname, t1.pr_type "
				+ "as prtypeid, t4.name as prtypename, t1.order_category as "
				+ "categoryid, t5.name as categoryname, t1.create_date, "
				+ "t1.creator_id, t6.name as creatorName, t1.contact,t1.section, "
				+ "t1.phone, t1.address, t1.postcode, t1.delivery_type as "
				+ "deliverytypeid, t7.name as deliverytypename,t1.discount_fee, "
				+ "t1.payment_method as paymenttypeid, t8.name as paymenttypename,"
				+ " t1.oos_dispose, t1.is_invoice,t1.ref_order_id,t1.bank_id,t1.id_card,t1.ef_year,t1.ef_month,t1.ver_code,"
				+ "t1.payed_money ,t1.payed_emoney, t1.is_use_dpt,t1.normal_fee,"
				+ "t1.remark, t1.order_type, t1.phone1, t1.package_fee,t1.package_type,t1.invoice_title "
				+ ", t1.Manual_Free_Freight,t1.Free_Freight_Reason "
				+ "from ord_headers t1, mbr_members t2, "
				+ "s_order_status t3, s_pr_type t4, s_order_category t5, "
				+ "org_persons t6, s_delivery_type t7, s_payment_method t8 "
				+ "where t1.buyer_id = t2.id(+) and t1.status = t3.id(+) "
				+ "and t1.pr_type = t4.id(+) and t1.order_category = t5.id(+) "
				+ "and t1.creator_id = t6.id(+) "
				+ "and t1.delivery_type = t7.id(+) "
				+ "and t1.payment_method = t8.id(+) and t1.id = "
				+ data.getOrderId();

		db.queryHeaderData(sql, wd);
		if (wd.getHeaderInt("id") > 0) {
			/* order header infomation */

			data.getCart().getOrder().setOrderId(wd.getHeaderInt("id"));
			data.getCart().getOrder().setRef_order_id(
					wd.getHeaderInt("ref_order_id"));
			data.getCart().getOrder().setOrderNumber(
					wd.getHeaderString("so_number"));
			// data.setMbId(wd.getHeaderInt("mbId"));
			// data.setIs_Organization(wd.getHeaderString("is_organization"));
			// data.setCardId(wd.getHeaderString("card_id"));
			// data.setMbName(wd.getHeaderString("mbname"));
			data.getCart().getMember().setID(wd.getHeaderInt("mbId"));
			data.getCart().getOrder().setTotalMoney(
					wd.getHeaderDouble("goods_fee"));
			data.getCart().getOrder().setGoodsFee(
					wd.getHeaderDouble("goods_fee"));
			data.getCart().getOrder().setNormalFee(wd.getHeaderDouble("normal_fee"));
			data.getCart().getOrder().setDiscount_fee(
					wd.getHeaderDouble("discount_fee"));
			data.getCart().getOrder().setPayable(
					wd.getHeaderDouble("order_sum"));
			// data.setDeliveryFee(wd.getHeaderDouble("delivery_fee"));
			data.getCart().getOrder().setStatusId(wd.getHeaderInt("statusid"));
			data.getCart().getOrder().setStatusName(
					wd.getHeaderString("statusname"));
			data.getCart().getOrder().setPrTypeId(wd.getHeaderInt("prtypeid"));
			data.getCart().getOrder().setPrTypeName(
					wd.getHeaderString("prtypename"));
			data.getCart().getOrder().setOrderType(
					wd.getHeaderInt("order_type"));
			data.getCart().getOrder().setCategoryId(
					wd.getHeaderInt("categoryid"));
			data.getCart().getOrder().setCategoryName(
					wd.getHeaderString("categoryname"));
			data.getCart().getOrder().setCreateDate(
					wd.getHeaderDate("create_date"));
			data.getCart().getOrder().setCreatorId(
					wd.getHeaderInt("creator_id"));
			data.getCart().getOrder().setCreatorName(
					wd.getHeaderString("creatorName"));
			data.getCart().getOrder().setOrderUse(
					wd.getHeaderDouble("payed_money"));
			data.getCart().getOrder().setOrderEmoney(
					wd.getHeaderDouble("payed_emoney"));

			double append_fee = wd.getHeaderDouble("append_fee");
			data.getCart().getOrder().setAppendFee(Math.abs(append_fee));
			
			//手工免运费信息
			data.setManualFreeFreight(wd.getHeaderInt("manual_free_freight")==1);
			data.setFreeFreightReason(wd.getHeaderString("free_freight_reason"));
			
			/* delivery infomation */
			// 送货信息
			data.getCart().getDeliveryInfo().setReceiptor(
					wd.getHeaderString("contact"));
			data.getCart().getDeliveryInfo().setPhone(
					wd.getHeaderString("phone"));
			data.getCart().getDeliveryInfo().setPhone2(
					wd.getHeaderString("phone1"));
			data.getCart().getDeliveryInfo().setSectionName(
					S_AREADao.getFullBySection(db.conn,wd.getHeaderString("section")));
			data.getCart().getDeliveryInfo().setAddress(
					wd.getHeaderString("address"));
			data.getCart().getDeliveryInfo().setPostCode(
					wd.getHeaderString("postcode"));
			// 送货方式
			data.getCart().getDeliveryInfo().setDeliveryTypeId(
					wd.getHeaderInt("deliverytypeid"));
			data.getCart().getDeliveryInfo().setDeliveryType(
					wd.getHeaderString("deliverytypename"));
			// 付款方式
			data.getCart().getDeliveryInfo().setPaymentTypeId(
					wd.getHeaderInt("paymenttypeid"));
			data.getCart().getDeliveryInfo().setPaymentType(
					wd.getHeaderString("paymenttypename"));
			// 发送费
			data.getCart().getDeliveryInfo().setDeliveryFee(
					wd.getHeaderDouble("delivery_fee"));
			data.getCart().getDeliveryInfo().setSection(wd.getHeaderString("section"));

			/* other infomation */
			// 缺货处理
			data.getCart().getOtherInfo().setOOSPlan(
					wd.getHeaderInt("oos_dispose"));
			// 是否要发票
			data.getCart().getOtherInfo().setNeedInvoice(
					wd.getHeaderString("is_invoice"));
			data.getCart().getOtherInfo().setInvoice_title(
					wd.getHeaderString("invoice_title"));
			data.getCart().getOtherInfo().setCredit_card(
					wd.getHeaderString("bank_id"));
			data.getCart().getOtherInfo().setId_card(
					wd.getHeaderString("id_card"));
			data.getCart().getOtherInfo().setVer_code(
					wd.getHeaderString("ver_code"));
			data.getCart().getOtherInfo().setEf_year(
					wd.getHeaderInt("ef_year"));
			data.getCart().getOtherInfo().setEf_month(
					wd.getHeaderInt("ef_month"));
			// 包装信息
			data.getCart().setPackageFee(wd.getHeaderDouble("package_fee"));
			data.getCart().setPackageType(wd.getHeaderInt("package_type"));

			// 备注信息
			data.getCart().getOtherInfo().setRemark(
					wd.getHeaderString("remark"));
			data.setUse_deposit(wd.getHeaderInt("is_use_dpt"));
			data.getCart().getOtherInfo().setPaydiscount(
					wd.getHeaderDouble("pay_discount"));
			
		

		}
		wd = null;
	}

	/**
	 * 得到团购订单头信息，查询所得的信息存储于data中 add by user 2007-02-28
	 * 
	 * @param db
	 * @param data
	 *            用其订单ID进行查询
	 * @throws Exception
	 */
	public static void getGroupOrderHeadersInfo(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select t1.id, t1.so_number, t2.id as mbId,t2.is_organization, "
				+ " t2.card_id, t2.name as mbname, t1.goods_fee, "
				+ "t1.order_sum, t1.delivery_fee, t1.gift_number, t1.append_fee,"
				+ "t1.status as statusid, t3.name as statusname, t1.pr_type "
				+ "as prtypeid, t4.name as prtypename, t1.order_category as "
				+ "categoryid, t5.name as categoryname, t1.create_date, "
				+ "t1.creator_id, t6.name as creatorName, t1.contact, "
				+ "t1.phone, t1.address, t1.postcode, t1.delivery_type as "
				+ "deliverytypeid, t7.name as deliverytypename, "
				+ "t1.payment_method as paymenttypeid, t8.name as "
				+ "paymenttypename, t1.oos_dispose, t1.is_invoice, "
				+ "t1.payed_money+ t1.payed_emoney as used_money, "
				+ "t1.remark "

				+ "from ord_headers t1, mbr_members t2, "
				+ "s_order_status t3, s_pr_type t4, s_order_category t5, "
				+ "org_persons t6, s_delivery_type t7, s_payment_method t8 "
				+ "where t1.buyer_id = t2.id(+) and t1.status = t3.id(+) "
				+ "and t1.pr_type = t4.id(+) and t1.order_category = t5.id(+) "
				+ "and t1.creator_id = t6.id(+) "
				+ "and t1.delivery_type = t7.id(+) "
				+ "and t1.payment_method = t8.id(+) and t1.id = "
				+ data.getOrderId();

		db.queryHeaderData(sql, wd);
		if (wd.getHeaderInt("id") > 0) {
			/** member infomation **/
			data.getOrgCart().getOrgMember().setID(wd.getHeaderInt("mbId"));
			data.getOrgCart().getOrgMember().setCARD_ID(
					wd.getHeaderString("card_id"));
			data.getOrgCart().getOrgMember().setNAME(
					wd.getHeaderString("mbname"));
			/** order header infomation **/
			data.getOrgCart().getOrgOrder().setOrderId(wd.getHeaderInt("id"));
			data.getOrgCart().getOrgOrder().setPrTypeId(
					wd.getHeaderInt("prtypeid"));
			data.getOrgCart().getOrgOrder().setPrTypeName(
					wd.getHeaderString("prtypename"));
			data.getOrgCart().getOrgOrder().setOrderNumber(
					wd.getHeaderString("so_number"));

			data.getOrgCart().getOrgOrder().setTotalMoney(
					wd.getHeaderDouble("goods_fee"));
			data.getOrgCart().getOrgOrder().setGoodsFee(
					wd.getHeaderDouble("goods_fee"));
			data.getOrgCart().getOrgOrder().setPayable(
					wd.getHeaderDouble("order_sum"));

			data.getOrgCart().getOrgOrder().setStatusId(
					wd.getHeaderInt("statusid"));
			data.getOrgCart().getOrgOrder().setStatusName(
					wd.getHeaderString("statusname"));
			data.getOrgCart().getOrgOrder().setPrTypeId(
					wd.getHeaderInt("prtypeid"));
			data.getOrgCart().getOrgOrder().setPrTypeName(
					wd.getHeaderString("prtypename"));
			data.getOrgCart().getOrgOrder().setCategoryId(
					wd.getHeaderInt("categoryid"));
			data.getOrgCart().getOrgOrder().setCategoryName(
					wd.getHeaderString("categoryname"));
			data.getOrgCart().getOrgOrder().setCreateDate(
					wd.getHeaderDate("create_date"));
			data.getOrgCart().getOrgOrder().setCreatorId(
					wd.getHeaderInt("creator_id"));
			data.getOrgCart().getOrgOrder().setCreatorName(
					wd.getHeaderString("creatorName"));
			data.getOrgCart().getOrgOrder().setOrderUse(
					wd.getHeaderDouble("used_money"));

			// double append_fee = wd.getHeaderDouble("append_fee");
			//data.getOrgCart().getOrgCart().setAppendFee(Math.abs(append_fee));

			/** delivery infomation **/
			// 送货信息
			data.getOrgCart().getDeliveryInfo().setReceiptor(
					wd.getHeaderString("contact"));
			data.getOrgCart().getDeliveryInfo().setPhone(
					wd.getHeaderString("phone"));
			data.getOrgCart().getDeliveryInfo().setAddress(
					wd.getHeaderString("address"));
			data.getOrgCart().getDeliveryInfo().setPostCode(
					wd.getHeaderString("postcode"));
			// 送货方式
			data.getOrgCart().getDeliveryInfo().setDeliveryTypeId(
					wd.getHeaderInt("deliverytypeid"));
			data.getOrgCart().getDeliveryInfo().setDeliveryType(
					wd.getHeaderString("deliverytypename"));
			// 付款方式
			data.getOrgCart().getDeliveryInfo().setPaymentTypeId(
					wd.getHeaderInt("paymenttypeid"));
			data.getOrgCart().getDeliveryInfo().setPaymentType(
					wd.getHeaderString("paymenttypename"));
			// 发送费
			// data.getOrgCart().getDeliveryInfo().setDeliveryFee(wd.
			// getHeaderDouble("delivery_fee"));

			/** other infomation **/
			// 缺货处理
			data.getOrgCart().getOtherInfo().setOOSPlan(
					wd.getHeaderInt("oos_dispose"));
			// 是否要发票
			data.getOrgCart().getOtherInfo().setNeedInvoice(
					wd.getHeaderString("is_invoice"));
			// 备注信息
			data.getOrgCart().getOtherInfo().setRemark(
					wd.getHeaderString("remark"));
		}
		wd = null;
	}

	/**
	 * 得到订单产品信息，查询所得的非礼品存储于data.getItems()中， 礼品存储于data.getGifts()中
	 * 
	 * @param db
	 * @param data
	 *            用其订单ID或者订单编号进行查询
	 * @throws Exception
	 */
	public static void getOrderLinesInfo(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select t1.id, t1.sku_id, t2.itm_code, nvl(t1.mbr_award_id,0) as mbr_award_id, "
				+ "t21.itm_name as itemname, 0 as clubid,t1.sell_type as selltypeid, "
				+ "t3.name as selltypename, t1.quantity, t5.name as status, t1.award_id2, "
				+ "t4.name as unit, t1.price, t21.itm_type, t1.pricelist_line_id, "
				+ " t2.color_code,t6.name as color_name,t2.size_code,t1.set_code "
				+ "from ord_lines t1, prd_item_sku t2,prd_item t21, "
				+ "s_sell_type t3, s_uom t4 ,s_order_line_status t5 ,prd_item_color t6 "
				+ "where t1.sku_id = t2.sku_id(+) and t2.itm_code = t21.itm_code "
				+ "and t1.status = t5.id(+) and t2.color_code = t6.code(+) "
				+ "and t1.sell_type = t3.id(+) and t2.itm_unit = t4.id(+) and "
				+ "t1.order_id = " + data.getOrderId();

		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			ItemInfo ii = new ItemInfo();

			ii.setLineId(wd.getDetailInt("id"));
			ii.setSku_id(wd.getDetailInt("sku_id"));
			ii.setItemCode(wd.getDetailString("itm_code"));
			ii.setItemName(wd.getDetailString("itemname"));
			ii.setSellTypeId(wd.getDetailInt("selltypeid"));
			ii.setSellTypeName(wd.getDetailString("selltypename"));
			ii.setItemQty(wd.getDetailInt("quantity"));
			ii.setOldItemQty(ii.getItemQty());
			ii.setItemUnit(wd.getDetailString("unit"));
			ii.setItemPrice(wd.getDetailDouble("price"));
			ii.setAwardId(wd.getDetailInt("mbr_award_id"));
			ii.setStatus(wd.getDetailString("status"));
			ii.setSet_code(wd.getDetailString("set_code"));
			ii.setGroupId(wd.getDetailInt("award_id2"));
			if (ii.getGroupId()>0) {
				ii.setSellTypeName("商品促销");
			}
			
			// ii.setIs_pre_sell(wd.getDetailInt("is_pre_sell"));
			// ii.setClubID(wd.getDetailInt("clubid"));
			ii.setTruss(wd.getDetailInt("itm_type") == 2
					|| wd.getDetailInt("itm_type") == 3 ? true : false);

			ii.setPriceListLineId(wd.getDetailInt("pricelist_line_id"));// add
			ii.setColor_name(wd.getDetailString("color_name"));
			ii.setColor_code(wd.getDetailString("color_code"));
			ii.setSize_code(wd.getDetailString("size_code"));

			// ii.setStandardPrice(wd.getDetailDouble("standard_price"));
			// ii.setPurchaseingCost(wd.getDetailDouble("purchasing_cost"));
			// ii.setUnPurchaseingCost(wd.getDetailDouble("unpurchasing_cost"));
			// ii.setGroupItemMomey(Arith.mul(ii.getDiscountPrice(), ii
			// .getItemQty()));
			// 得到到货日期
			// getLandDate(db, ii);

			ii.setColors(Product2DAO.listItemColor(db.conn, ii.getItemCode()));
			ii.setSizes(Product2DAO.listItemSize(db.conn, ii.getItemCode()));

			if (ii.getSellTypeId() == 4) {// 促销礼品
				Prom_gift prom_gift = PromotionDAO.findPromGiftByPK(db.conn, ii
						.getAwardId());
				if (prom_gift != null) {
					ii.setFloorMoney(prom_gift.getOverx());
				}
			}

			// 库存状态
			int nAvailableQty = OrderDAO.getAvailableStockQty(db, ii, data);
			if (nAvailableQty < ii.getItemQty()) { // 库存不足
				ii.setStockStatusId(1);
				if (ii.getIs_pre_sell() == 1)
					ii.setStockStatusName("虚拟库存");
				else if (ii.isLastSell())

					ii.setStockStatusName("永久缺货");
				else
					ii.setStockStatusName("暂时缺货");
			} else {
				ii.setStockStatusId(0);

				if (nAvailableQty - ii.getItemQty() < 10) {
					ii.setStockStatusName("即将缺货");
				} else {
					ii.setStockStatusName("库存正常");
				}
			}

			// 添加商品到购物车中
			if (ii.getSellTypeId() < 4) { // 如果销售方式是<4为商品销售，其他为礼品销售
				data.getCart().getItems().add(ii);

			} else {
				data.getCart().getGifts().add(ii);
			}
		}
		wd = null;
	}

	/**
	 * 得到订单产品信息，查询所得的非礼品存储于data.getItems()中， 礼品存储于data.getGifts()中
	 * 
	 * @param db
	 * @param data
	 *            用其订单ID或者订单编号进行查询
	 * @throws Exception
	 */
	public static void getOrderLinesInfo2(DBOperation db, OrderForm data)
			throws Exception {
		Connection conn = db.conn;
		String sql1 = "select t1.sale_price set_price,t2.sale_price " +
				" from prd_item t1 join prd_item_set t2 on t1.itm_code = t2.SET_ITEM_CODE "
				+ " where t2.part_item_code = ?";
		PreparedStatement ps = conn.prepareStatement(sql1);
		
		WebData wd = new WebData();
		String sql = "select t1.id, t1.sku_id, t2.itm_code,t1.frozen_item, nvl(t1.mbr_award_id,0) as mbr_award_id, "
				+ "t21.itm_name as itemname, 0 as clubid,t1.sell_type as selltypeid, "
				+ "t3.name as selltypename, t1.quantity, t5.name as status, nvl(gift_marker,0) gift_marker,"
				+ "t4.name as unit, t1.price, t21.itm_type, t1.pricelist_line_id, "
				+ " t2.color_code,t6.name as color_name,t2.size_code,t1.set_code, "
				+ " nvl(t7.use_qty - t7.frozen_qty,0) availqty, nvl(t1.award_id2,0) award_id2,"
				+ " nvl(t7.use_qty - t7.frozen_qty + t2.os_qty*t2.enable_os,0) availqty2 "
				+ "from ord_lines t1, prd_item_sku t2,prd_item t21, "
				+ "s_sell_type t3, s_uom t4 ,s_order_line_status t5 ,prd_item_color t6,sto_stock t7 "
				+ "where t1.sku_id = t2.sku_id(+) and t2.itm_code = t21.itm_code "
				+ "and t1.status = t5.id(+) and t2.color_code = t6.code(+) "
				+ "and t1.sell_type = t3.id(+) and t2.itm_unit = t4.id(+) "
				+ "and t1.sku_id = t7.sku_id "
				+ "and t1.order_id = "
				+ data.getOrderId();

		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			ItemInfo item = new ItemInfo();

			item.setLineId(wd.getDetailInt("id"));
			item.setSku_id(wd.getDetailInt("sku_id"));
			item.setItemCode(wd.getDetailString("itm_code"));
			item.setItemName(wd.getDetailString("itemname"));
			item.setSellTypeId(wd.getDetailInt("selltypeid"));
			item.setSellTypeName(wd.getDetailString("selltypename"));
			item.setItemQty(wd.getDetailInt("quantity"));
			item.setOldItemQty(item.getItemQty());
			item.setItemUnit(wd.getDetailString("unit"));
			item.setItemPrice(wd.getDetailDouble("price"));
			item.setAwardId(wd.getDetailInt("mbr_award_id"));
			item.setStatus(wd.getDetailString("status"));
			item.setSet_code(wd.getDetailString("set_code"));
			item.setFrozenItem(wd.getDetailString("frozen_item"));
			
			if (!"".equals(item.getSet_code())) {
				ps.setString(1, item.getItemCode());
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					item.setItemSilverPrice(rs.getDouble("sale_price"));
					item.setSetSilverPrice(rs.getDouble("set_price"));
					item.setSet_price(item.getItemPrice()/item.getItemSilverPrice()*item.getSetSilverPrice());
				}
				rs.close();
				
			}
			
			item.setGroupId(wd.getDetailInt("award_id2"));
			if (item.getGroupId()>0) {
				item.setSellTypeName("商品促销");
			}
			item.setSet_group_id(wd.getDetailInt("gift_marker"));
			int setGroupId = data.getCart().getSetGroupId();
			if (item.getSet_group_id()> setGroupId) {
				data.getCart().setSetGroupId(item.getSet_group_id());
			}

			item.setPriceListLineId(wd.getDetailInt("pricelist_line_id"));// add
			item.setColor_name(wd.getDetailString("color_name"));
			item.setColor_code(wd.getDetailString("color_code"));
			item.setSize_code(wd.getDetailString("size_code"));

			// item.setStandardPrice(wd.getDetailDouble("standard_price"));
			// item.setPurchaseingCost(wd.getDetailDouble("purchasing_cost"));
			//item.setUnPurchaseingCost(wd.getDetailDouble("unpurchasing_cost"))
			// ;
			// item.setGroupItemMomey(Arith.mul(item.getDiscountPrice(), item
			// .getItemQty()));
			// 得到到货日期
			// getLandDate(db, item);
			item.setAvailQty(wd.getDetailInt("availqty"));
			item.setAvailQty2(wd.getDetailInt("availqty2"));

			item.setColors(Product2DAO.listItemColor(db.conn, item
					.getItemCode()));
			item.setSizes(Product2DAO.listItemSize(db.conn, item
							.getItemCode()));

			if (item.getSellTypeId() == 4) {// 促销礼品
				Prom_gift prom_gift = PromotionDAO.findPromGiftByPK(db.conn,
						item.getAwardId());
				if (prom_gift != null) {
					item.setFloorMoney(prom_gift.getOverx());
				}
			}

			// 设置库存状态
			if (item.getAvailQty() >= item.getItemQty()) {
				item.setStockStatusId(0);
				item.setStockStatusName("库存正常");
			} else if (item.getAvailQty2() >= item.getItemQty()) {
				item.setIs_pre_sell(1);
				item.setStockStatusName("虚拟库存");
			} else {
				if (item.isLastSell()) {
					item.setStockStatusName("永久缺货");
				} else {
					item.setStockStatusName("暂时缺货");
				}
			}

			// 添加商品到购物车中
			if (item.getSellTypeId() < 4) { // 如果销售方式是<4为商品销售，其他为礼品销售
				data.getCart().getItems().add(item);

			} else {
				if(item.getGroupId()>0) {
					data.getCart().getGifts2().add(item);
					
				} else {
					data.getCart().getGifts().add(item);
				}
			}
		}
		ps.close();
		wd = null;
	}

	/**
	 * 得到订单产品信息，查询所得的非礼品存储于data.getItems()中， 礼品存储于data.getGifts()中 add by user
	 * 2006-01-20 18:00
	 * 
	 * @param db
	 * @param data
	 *            用其订单ID或者订单编号进行查询
	 * @throws Exception
	 */
	public static void showGroupOrderLinesInfo(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select t1.id, t1.sku_id, t6.itm_code, nvl(t1.mbr_award_id,0) as mbr_award_id, "
				+ "t6.itm_name as itemname, t1.sell_type as selltypeid, t2.color_code,t2.size_code, "
				+ "t3.name as selltypename, t1.quantity, t5.name as status, "
				+ "t4.name as unit, t1.price, t2.standard_price, t2.itm_cost, t1.price "
				// +
				// "product.F_GET_ITEM_PRICE(t1.sku_id, 2, 1, null) as silver_price, "
				// // 银卡价
				// +
				// "product.F_GET_ITEM_PRICE(t1.sku_id, 3, 1, null) as golden_price, "
				// // 金卡价
				// + "erp.f_get_item_avail_quantity(t1.sku_id, 2) as avail_qty "
				// // 可用库存
				+ "from ord_lines t1, prd_item_sku t2, "
				+ "s_sell_type t3, s_uom t4 ,s_order_line_status t5,prd_item t6"
				+ " where t1.sku_id = t2.sku_id and t2.itm_code = t6.itm_code "
				+ " and t1.status = t5.id(+) "
				+ "and t1.sell_type = t3.id(+) and t2.itm_unit = t4.id(+) and "
				+ "t1.order_id = " + data.getOrderId();

		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			ItemInfo ii = new ItemInfo();
			ii.setLineId(wd.getDetailInt("id"));
			ii.setSku_id(wd.getDetailInt("sku_id"));
			ii.setItemCode(wd.getDetailString("itm_code"));
			ii.setItemName(wd.getDetailString("itemname"));
			ii.setColor_code(wd.getDetailString("color_code"));
			ii.setSize_code(wd.getDetailString("size_code"));
			ii.setSellTypeId(wd.getDetailInt("selltypeid"));
			ii.setSellTypeName(wd.getDetailString("selltypename"));
			ii.setItemQty(wd.getDetailInt("quantity"));
			ii.setItemUnit(wd.getDetailString("unit"));
			ii.setDiscountPrice(wd.getDetailDouble("price"));
			ii.setAwardId(wd.getDetailInt("mbr_award_id"));
			// ii.setStatus(wd.getDetailString("status"));
			// ii.setIs_pre_sell(wd.getDetailInt("is_pre_sell"));
			// ii.setClubID(wd.getDetailInt("clubid"));

			// ii.setSilverPrice(wd.getDetailDouble("silver_price"));
			// ii.setGoldenPrice(wd.getDetailDouble("golden_price"));
			// ii.setAvailQty(wd.getDetailInt("avail_qty"));

			ii.setStandardPrice(wd.getDetailDouble("standard_price"));
			// ii.setPurchaseingCost(wd.getDetailDouble("purchasing_cost"));
			ii.setPurchaseingCost(wd.getDetailDouble("itm_cost"));
			ii.setGroupItemMomey(Arith.mul(ii.getDiscountPrice(), ii
					.getItemQty()));
			// 得到到货日期
			// getLandDate(db, ii);

			// 库存状态
			/*
			 * int nAvailableQty = OrderDAO.getAvailableStockQty(db, ii, data);
			 * if (nAvailableQty < ii.getItemQty()) { // 库存不足
			 * ii.setStockStatusId(1); if (ii.getIs_pre_sell() == 1)
			 * ii.setStockStatusName("预售缺货"); else if (ii.isLastSell())
			 * ii.setStockStatusName("永久缺货"); else
			 * ii.setStockStatusName("暂时缺货"); } else { ii.setStockStatusId(0);
			 * 
			 * ii.setStockStatusName("库存正常");
			 * 
			 * }
			 */

			// 添加商品到formbean中
			if (ii.getSellTypeId() == 0 || ii.getSellTypeId() == 1
					|| ii.getSellTypeId() == 2 || ii.getSellTypeId() == 3) { // 如果销售方式是0
				// -
				// 3
				// 为商品销售
				// ，
				// 其他为礼品销售
				data.getOrgCart().getItems().add(ii);

			} else {
				data.getOrgCart().getGifts().add(ii);
			}
		}
		wd = null;
	}

	/**
	 * get group order line info
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void getGroupOrderLinesInfo(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select t1.id, t1.sku_id, t6.itm_code, nvl(t1.mbr_award_id,0) as mbr_award_id, "
				+ "t6.itm_name as itemname, t1.sell_type as selltypeid, t1.set_code,"
				+ "t3.name as selltypename, t1.quantity, t5.name as status, "
				+ "t4.name as unit, t1.price, t2.standard_price, t2.itm_cost, t1.price, "
				+ "nvl(t7.use_qty - t7.frozen_qty + t2.enable_os*t2.os_qty,0) availqty,t2.color_code,t2.size_code "
				// +
				// "product.F_GET_ITEM_PRICE(t1.sku_id, 2, 1, null) as silver_price, "
				// // 银卡价
				// +
				// "product.F_GET_ITEM_PRICE(t1.sku_id, 3, 1, null) as golden_price, "
				// // 金卡价
				// + "erp.f_get_item_avail_quantity(t1.sku_id, 2) as avail_qty "
				// // 可用库存
				+ " from ord_lines t1, prd_item_sku t2, sto_stock t7,"
				+ " s_sell_type t3, s_uom t4 ,s_order_line_status t5,prd_item t6"
				+ " where t1.sku_id = t2.sku_id and t2.itm_code = t6.itm_code "
				+ " and t1.status = t5.id(+) and t1.sku_id = t7.sku_id "
				+ " and t1.sell_type = t3.id(+) and t2.itm_unit = t4.id(+) and "
				+ " t1.order_id = " + data.getOrderId();

		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			ItemInfo ii = new ItemInfo();
			ii.setLineId(wd.getDetailInt("id"));
			ii.setSku_id(wd.getDetailInt("sku_id"));
			ii.setItemCode(wd.getDetailString("itm_code"));
			ii.setItemName(wd.getDetailString("itemname"));
			ii.setSellTypeId(wd.getDetailInt("selltypeid"));
			ii.setSellTypeName(wd.getDetailString("selltypename"));
			ii.setItemQty(wd.getDetailInt("quantity"));
			ii.setItemUnit(wd.getDetailString("unit"));
			ii.setColor_code(wd.getDetailString("color_code"));
			ii.setSize_code(wd.getDetailString("size_code"));
			ii.setDiscountPrice(wd.getDetailDouble("price"));
			ii.setAwardId(wd.getDetailInt("mbr_award_id"));
			// ii.setStatus(wd.getDetailString("status"));
			// ii.setIs_pre_sell(wd.getDetailInt("is_pre_sell"));
			// ii.setClubID(wd.getDetailInt("clubid"));

			// ii.setSilverPrice(wd.getDetailDouble("silver_price"));
			// ii.setGoldenPrice(wd.getDetailDouble("golden_price"));
			ii.setAvailQty(wd.getDetailInt("availqty"));

			ii.setStandardPrice(wd.getDetailDouble("standard_price"));
			ii.setSet_code(wd.getDetailString("set_code"));
			// ii.setPurchaseingCost(wd.getDetailDouble("purchasing_cost"));
			ii.setPurchaseingCost(wd.getDetailDouble("itm_cost"));
			ii.setGroupItemMomey(Arith.mul(ii.getDiscountPrice(), ii
					.getItemQty()));

			// ii.setSizes(ProductBaseDAO.listSize(db.conn, ii.getItemCode()));
			// 设置该产品可选颜色和尺寸
			ii.setColors(Product2DAO.listItemColor(db.conn, ii.getItemCode()));
			ii.setSizes(Product2DAO.listItemSize(db.conn, ii.getItemCode()));

			filloItemPrice(db.conn, ii);

			// 库存状态

			if (ii.getAvailQty() < ii.getItemQty()) { // 库存不足
				ii.setStockStatusId(1);
				if (ii.isLastSell())
					ii.setStockStatusName("永久缺货");
				else
					ii.setStockStatusName("暂时缺货");
			} else {
				ii.setStockStatusId(0);
				ii.setStockStatusName("库存正常");
			}

			// 添加商品到formbean中
			if (ii.getSellTypeId() < 4) { // 如果销售方式是0
				// -
				// 3
				// 为商品销售
				// ，
				// 其他为礼品销售
				data.getOrgCart().getItems().add(ii);

			} else {
				data.getOrgCart().getGifts().add(ii);
			}
		}
		wd = null;
	}

	/**
	 * 更新订单中的产品信息，具体步骤为： 先删除订单中的所有产品信息，然后插入新的产品信息
	 * 
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	public static int updateLine(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		wd.setTable("ord_lines");
		// 订单ID
		wd.setSubWhere("order_id = " + data.getOrderId());
		int nResult = db.delete(wd);

		wd = null;
		// 删除订单产品失败
		if (nResult < 1)
			return nResult;
		else
			return insertLine(db, data);
	}

	/**
	 * 插入订单主信息
	 * 
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	public static void insertMaster(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		wd.setTable("ord_headers");

		// 订单ID
		data.setOrderId(SequenceManager.getNextVal(db.conn,
				"seq_ord_headers_id"));

		String so_number_code = "";
		/**
		 * 1邮购订单P，2电话订单T，3网站订单W，5团体会员订单C
		 * 6传真订单F，7Email订单E，8信件订单X，9短信订单，10外呼订单，11回单
		 */
		if (data.getPrTypeId() == 1)
			so_number_code = "P";
		if (data.getPrTypeId() == 2)
			so_number_code = "T";
		if (data.getPrTypeId() == 3)
			so_number_code = "W";
		if (data.getPrTypeId() == 6)
			so_number_code = "F";
		if (data.getPrTypeId() == 7)
			so_number_code = "E";
		if (data.getPrTypeId() == 8)
			so_number_code = "X";
		if (data.getPrTypeId() == 10)
			so_number_code = "O";
		if (data.getPrTypeId() == 11)
			so_number_code = "B";
		// 订单编号
		data.setOrderNumber(so_number_code
				+ LogicUtility.getDateAsString().replaceAll("-", "").substring(
						2)
				+ SequenceManager.getNextVal(db.conn, "seq_ord_number_id"));

		/** order infomation **/
		wd.addHeaderData("id", new Integer(data.getOrderId()));
		wd.addHeaderData("supplier_id", new Integer(1));
		// order_type:10 正常订单
		wd.addHeaderData("order_type",
				data.getCart().isPreSellOrder() ? new Integer(5) : new Integer(
						10));
		wd.addHeaderData("buyer_id", new Integer(data.getMbId()));
		wd.addHeaderData("so_number", data.getOrderNumber());
		wd.addHeaderData("order_sum", new Double(data.getCart().getPayable()));
		wd.addHeaderData("order_all_sum", new Double(data.getCart()
				.getPayable()
				+ data.getCart().getTicketKill()));
		// 购物总金额
		wd.addHeaderData("goods_fee",
				new Double(data.getCart().getTotalMoney()));
		wd.addHeaderData("normal_fee",new Double(data.getCart().getNormalSaleMoney()));

		// 礼券
		wd.addHeaderData("gift_number", "-1");
		wd.addHeaderData("append_fee", new Double(-data.getCart()
				.getTicketKill()));

		// 0:订单创建
		wd.addHeaderData("status", new Integer(0));
		wd.addHeaderData("pr_type", new Integer(data.getPrTypeId()));
		// add by user 2008-05-19
		wd.addHeaderData("package_fee", new Double(data.getCart()
				.getPackageFee()));

		// 订单类型
		if (data.getCart().getMember().isOldMember()) {
			// if (data.getCart().isCommitment()) {
			// wd.addHeaderData("ORDER_CATEGORY", new Integer(20));//
			// } else {
			wd.addHeaderData("ORDER_CATEGORY", new Integer(10));// 老会员定单
			// }
		} else {
			// 入会订单
			wd.addHeaderData("ORDER_CATEGORY", new Integer(0));
		}
		// 创建人
		wd.addHeaderData("creator_id", new Integer(data.getCreatorId()));
		// 修改人
		wd.addHeaderData("modifier_id", new Integer(data.getCreatorId()));

		/* delivery infomation */
		wd.addHeaderData("contact", data.getCart().getDeliveryInfo()
				.getReceiptor());
		wd.addHeaderData("phone", data.getCart().getDeliveryInfo().getPhone());
		wd
				.addHeaderData("phone1", data.getCart().getDeliveryInfo()
						.getPhone2());// add by user 2008-03-27
		wd.addHeaderData("address", data.getCart().getDeliveryInfo()
				.getAddress());
		wd.addHeaderData("postcode", data.getCart().getDeliveryInfo()
				.getPostCode());
		wd.addHeaderData("delivery_type", new Integer(data.getCart()
				.getDeliveryInfo().getDeliveryTypeId()));
		wd.addHeaderData("delivery_fee", new Double(data.getCart()
				.getDeliveryInfo().getDeliveryFee()));
		wd.addHeaderData("payment_method", new Integer(data.getCart()
				.getDeliveryInfo().getPaymentTypeId()));

		/* other infomation */
		wd.addHeaderData("remark", data.getCart().getOtherInfo().getRemark());
		wd.addHeaderData("oos_dispose", new Integer(data.getCart()
				.getOtherInfo().getOOSPlan()));
		wd.addHeaderData("is_invoice", data.getCart().getOtherInfo()
				.getNeedInvoice());
		wd.addHeaderData("invoice_title", data.getCart().getOtherInfo()
				.getInvoice_title());
		wd.addHeaderData("package_type", data.getCart().getPackageType());
		wd.addHeaderData("package_fee", data.getCart().getPackageFee());
		wd.addHeaderData("is_use_dpt", data.getUse_deposit());
		wd.addHeaderData("msc_code", data.getMsc());
		wd.addHeaderData("section", data.getCart().getDeliveryInfo()
				.getSection());

		wd.addHeaderData("pay_discount", data.getCart().getOtherInfo()
				.getPaydiscount());
		wd.addHeaderData("discount_fee", -data.getCart().getDiscount_fee());
		wd.addHeaderData("bank_id", data.getCart().getOtherInfo().getCredit_card());
		wd.addHeaderData("id_card", data.getCart().getOtherInfo().getId_card());
		wd.addHeaderData("ef_year", data.getCart().getOtherInfo().getEf_year());
		wd.addHeaderData("ef_month", data.getCart().getOtherInfo().getEf_month());
		wd.addHeaderData("ver_code", data.getCart().getOtherInfo().getVer_code());
		
		wd.addHeaderData("manual_free_freight", data.getManualFreeFreight()?1:0);
		wd.addHeaderData("Free_Freight_Reason", data.getFreeFreightReason());
		db.insert(wd);
		wd = null;
	}

	/**
	 * 插入订单中的产品信息
	 * 
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	public static int insertLine(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		wd.setTable("ord_lines");
		List lstItems = data.getCart().getItems();

		// 非礼品
		int nResult = 0;
		for (int i = 0; i < lstItems.size(); i++) {
			ItemInfo ii = (ItemInfo) lstItems.get(i);
			wd.addHeaderData("id", new Integer(SequenceManager.getNextVal(
					db.conn, "seq_ord_lines_id")));
			wd.addHeaderData("sku_id", new Integer(ii.getSku_id()));
			wd.addHeaderData("quantity", new Integer(ii.getItemQty()));
			wd.addHeaderData("price", new Double(ii.getItemPrice()));
			// 0：新建订单
			wd.addHeaderData("status", new Integer(0));
			wd.addHeaderData("order_id", new Integer(data.getOrderId()));
			wd.addHeaderData("sell_type", new Integer(ii.getSellTypeId()));

			wd.addHeaderData("IS_COMMITMENT",
					ii.isCommitment() ? new Integer(1) : new Integer(0));

			wd.addHeaderData("set_code", ii.getSet_code());
			if (ii.getSet_group_id()>0 ) {
				wd.addHeaderData("gift_marker", new Integer(ii.getSet_group_id()));
			}

			// add by user 2008-05-10
			// wd.addHeaderData("pricelist_line_id", new
			// Integer(ii.getPriceListLineId()));
			nResult += db.insert(wd);
			wd.clearHeaderData();
		}

		// 礼品
		lstItems = data.getCart().getGifts();
		for (int i = 0; i < lstItems.size(); i++) {
			ItemInfo ii = (ItemInfo) lstItems.get(i);
			Integer ord_lines_id = new Integer(SequenceManager.getNextVal(
					db.conn, "seq_ord_lines_id"));
			wd.addHeaderData("id", ord_lines_id);
			wd.addHeaderData("sku_id", new Integer(ii.getSku_id()));
			wd.addHeaderData("quantity", new Integer(ii.getItemQty()));
			wd.addHeaderData("price", new Double(ii.getItemPrice()));
			wd.addHeaderData("status", new Integer(0));
			wd.addHeaderData("order_id", new Integer(data.getOrderId()));
			wd.addHeaderData("sell_type", new Integer(ii.getSellTypeId()));

			wd.addHeaderData("IS_COMMITMENT",
					ii.isCommitment() ? new Integer(1) : new Integer(0));

			wd.addHeaderData("set_code", ii.getSet_code());
			if (ii.getSet_group_id()>0 ) {
				wd.addHeaderData("gift_marker", new Integer(ii.getSet_group_id()));
			}

			// mbr_get_award中的礼品（促销礼品的促销id也放在awardId中）
			if (ii.getAwardId() > 0) {
				wd.addHeaderData("mbr_award_id", new Integer(ii.getAwardId()));
				if (ii.getSellTypeId() != 4) {
					// 把该礼品的status置为1
					WebData webdata = new WebData();
					webdata.setTable("mbr_get_award");
					webdata.setSubWhere("id = " + ii.getAwardId());
					// 该礼品已经使用
					webdata.addHeaderData("status", new Integer(1));
					webdata.addHeaderData("ref_order_line_id", ord_lines_id);

					db.modify(webdata);
					webdata = null;
				}
			}

			nResult += db.insert(wd);
			wd.clearHeaderData();
		}
		
		lstItems = data.getCart().getGifts2();
		for (int i = 0; i < lstItems.size(); i++) {
			ItemInfo ii = (ItemInfo) lstItems.get(i);
			Integer ord_lines_id = new Integer(SequenceManager.getNextVal(
					db.conn, "seq_ord_lines_id"));
			wd.addHeaderData("id", ord_lines_id);
			wd.addHeaderData("sku_id", new Integer(ii.getSku_id()));
			wd.addHeaderData("quantity", new Integer(ii.getItemQty()));
			wd.addHeaderData("price", new Double(ii.getItemPrice()));
			wd.addHeaderData("status", new Integer(0));
			wd.addHeaderData("order_id", new Integer(data.getOrderId()));
			wd.addHeaderData("sell_type", new Integer(ii.getSellTypeId()));

			wd.addHeaderData("IS_COMMITMENT",
					ii.isCommitment() ? new Integer(1) : new Integer(0));

			wd.addHeaderData("set_code", ii.getSet_code());
			if (ii.getSet_group_id()>0 ) {
				wd.addHeaderData("gift_marker", new Integer(ii.getSet_group_id()));
			}
			if (ii.getGroupId()>0 && ii.getSellTypeId()==4) {
				wd.addHeaderData("award_id2", new Integer(ii.getGroupId()));
			}

			// mbr_get_award中的礼品（促销礼品的促销id也放在awardId中）
			if (ii.getAwardId() > 0) {
				wd.addHeaderData("mbr_award_id", new Integer(ii.getAwardId()));
			}

			nResult += db.insert(wd);
			wd.clearHeaderData();
		}
		wd = null;

		return nResult;
	}

	/**
	 * 没有购买相关组产品，却获得相关组礼品 首先确定是否有组礼品赠品 然后比较Promotion_ID
	 * 
	 * @param db
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static int checkAddThirdPromotion(DBOperation db, OrderForm data,
			int promotion_ID) throws Exception {
		WebData wd = new WebData();

		String sql = "";
		int nResult = 0;

		// 所有正常产品
		List lstItems = data.getCart().getItems();
		// 没有购买相关组产品，却获得相关组礼品

		for (int i = 0; i < lstItems.size(); i++) {
			ItemInfo ii = (ItemInfo) lstItems.get(i);
			sql = "  select itemcode from prom_item  where flag = 1 and "
					+ " itemcode = '" + ii.getItemCode()
					+ "' and promotionid = " + promotion_ID;
			db.queryDetailData(sql, wd, false);
			if (wd.next()) {
				nResult = 1;
			}

		}
		wd = null;

		return nResult;

	}

	/**
	 * 没有购买相关组产品，却获得相关组礼品 首先确定是否有组礼品赠品 然后比较GROUP_ID
	 * 
	 * @param db
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static int checkAddThirdGroup(DBOperation db, OrderForm data,
			int group_id) throws Exception {
		WebData wd = new WebData();
		List lstItems = data.getItems();
		String sql = "";
		int rs_group_id = 0;
		int nResult = 0;

		// 购物金额
		double goods_fee = 0;
		goods_fee = data.getTotalMoney();
		sql = "  select require from prd_gift_setting where require<="
				+ goods_fee + " and group_id=" + group_id;
		db.queryDetailData(sql, wd, false);
		if (wd.next()) {
			for (int i = 0; i < lstItems.size(); i++) {
				ItemInfo ii = (ItemInfo) lstItems.get(i);

				sql = "select group_id from prd_pricelist_gift_group_lines where group_id="
						+ group_id + " and sku_id=" + ii.getSku_id();
				db.queryDetailData(sql, wd, false);
				if (wd.next()) {
					rs_group_id = wd.getDetailInt("group_id");
					if (rs_group_id == group_id) {
						nResult = 1;
					} else {
						nResult = -1;
					}
				}

			}
		} else {
			nResult = -1;
		}
		wd = null;

		return nResult;

	}

	/**
	 * 检查所有礼品赠品的金额总合是否满足订单购物金额条件
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static int checkAddThird(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();

		String sql = "";
		int nResult = 0;
		// 所有已购礼品
		List lstItems = data.getCart().getGifts();

		// 购物金额
		// double goods_fee = data.getCart().getTotalMoney();
		double goods_fee = data.getCart().getNotGiftMoney();
		for (int i = 0; i < lstItems.size(); i++) {
			ItemInfo ii = (ItemInfo) lstItems.get(i);
			// 没有转移的促销礼品
			if (ii.getSellTypeId() == 4 && ii.getIs_transfer() == 0) {

				sql = "select overx from prom_gift where scope in (2,4,6,7) and validflag = 1 and id = "
						+ ii.getAwardId();

				db.queryDetailData(sql, wd, false);
				if (wd != null && wd.next()) {

					if (goods_fee < wd.getDetailDouble("overx")) {

						nResult = 1;
					}
				}

				wd = null;
			}

		}

		return nResult;
	}

	/**
	 * 简单订单查询
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void listOrder(DBOperation db, OrderForm data, Collection coll)
			throws Exception {
		WebData wd = new WebData();

		String sql = "select t1.id, t1.buyer_id, t1.so_number, t2.card_id, t2.name as "
				+ "mbname, t1.goods_fee , t1.order_sum , t1.status as statusid, "
				+ "t3.name as statusname, t1.pr_type as prtypeid,t1.order_type, "
				+ "t4.name as prtypename, t1.order_category as categoryid, "
				+ "t5.name as categoryname, t1.release_date, t1.creator_id, t1.payed_money,t1.payed_emoney, "
				+ "t6.name as creatorName from ord_headers t1, "
				+ "mbr_members t2, s_order_status t3, s_pr_type t4, "
				+ "s_order_category t5, org_persons t6 where t1.pr_type<>5 "
				+ "and t1.buyer_id = t2.id(+) and t1.status = t3.id(+) "
				+ "and t1.pr_type = t4.id(+) and t1.order_category = t5.id(+) "
				+ "and t1.creator_id = t6.id(+)";

		boolean blHasCondition = false;
		// 淘宝旺旺号
		if(data.getTaobaoWangId() != null && data.getTaobaoWangId().trim().length()>0)
		{
			sql += " and t2.taobaowang_id = '" 
				+ data.getTaobaoWangId().trim() + "'";
			blHasCondition = true;
		}
		// 会员号
		if (data.getCardId() != null && data.getCardId().trim().length() > 0) {
			sql += " and t2.card_id = '"
					+ LogicUtility.getDataString(data.getCardId().trim()) + "'";
			blHasCondition = true;
		}

		// 会员名称
		if (data.getMbName() != null && data.getMbName().trim().length() > 0) {
			sql += " and t2.name = '"
					+ LogicUtility.getDataString(data.getMbName().trim()) + "'";
			blHasCondition = true;
		}

		// 订单号
		if (data.getOrderNumber() != null
				&& data.getOrderNumber().trim().length() > 0) {
			sql += " and t1.so_number = '"
					+ LogicUtility.getDataString(data.getOrderNumber().trim())
					+ "'";
			blHasCondition = true;
		}

		if (!blHasCondition) {
			// 没有查询条件
			throw new Exception("请输入查询条件！");
		}

		sql += " order by t1.id desc";

		// 分页处理
		db.setPageAttribute(data.getPageAttribute());
		db.queryDetailData(sql, wd, true);
		while (wd.next()) {
			// OrderForm order = new OrderForm();
			Order order = new Order();
			order.setOrderId(wd.getDetailInt("id"));
			order.setOrderNumber(wd.getDetailString("so_number"));
			/* refacted by user 2007-03-19 */
			// order.setMbId(wd.getDetailInt("buyer_id"));
			// order.setCardId(wd.getDetailString("card_id"));
			// order.setMbName(wd.getDetailString("mbname"));
			order.getMember().setID(wd.getDetailInt("buyer_id"));
			order.getMember().setCARD_ID(wd.getDetailString("card_id"));
			order.getMember().setNAME(wd.getDetailString("mbname"));
			// order.setTotalMoney(wd.getDetailDouble("goods_fee"));
			order.setGoodsFee(wd.getDetailDouble("goods_fee"));
			order.setPayable(wd.getDetailDouble("order_sum"));
			order.setMbPayable(wd.getDetailDouble("order_sum")
					- wd.getDetailDouble("payed_money") - wd.getDetailDouble("payed_emoney"));
			order.setStatusId(wd.getDetailInt("statusid"));
			order.setStatusName(wd.getDetailString("statusname"));
			order.setPrTypeId(wd.getDetailInt("prtypeid"));
			order.setPrTypeName(wd.getDetailString("prtypename"));
			order.setCategoryId(wd.getDetailInt("categoryid"));
			order.setCategoryName(wd.getDetailString("categoryname"));
			order.setCreateDate(wd.getDetailTime("release_date"));
			order.setCreatorId(wd.getDetailInt("creator_id"));
			order.setCreatorName(wd.getDetailString("creatorName"));

			coll.add(order);
		}
		wd = null;

	}

	/**
	 * 高级订单查询
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void listAdvanceOrder(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select t1.id, t1.so_number, t2.card_id, t2.name as "
				+ "mbname, t1.goods_fee, t1.order_sum, t1.status as statusid, "
				+ "t3.name as statusname, t1.pr_type as prtypeid, "
				+ "t4.name as prtypename, t1.order_category as categoryid, "
				+ "t5.name as categoryname, t1.release_date, t1.creator_id, "
				+ "t6.name as creatorName from ord_headers t1, "
				+ "mbr_members t2, s_order_status t3, s_pr_type t4, "
				+ "s_order_category t5, org_persons t6 where "
				+ "t1.buyer_id = t2.id(+) and t1.status = t3.id(+) "
				+ "and t1.pr_type = t4.id(+) and t1.order_category = t5.id(+) "
				+ "and t1.creator_id = t6.id(+)";

		boolean blHasCondition = false;
		// 会员号
		if (data.getCardId() != null && data.getCardId().trim().length() > 0) {
			sql += " and t2.card_id = '"
					+ LogicUtility.getDataString(data.getCardId().trim()) + "'";
			blHasCondition = true;
		}

		// 会员名称
		if (data.getMbName() != null && data.getMbName().trim().length() > 0) {
			sql += " and t2.name = '"
					+ LogicUtility.getDataString(data.getMbName().trim()) + "'";
			blHasCondition = true;
		}

		// 订单号
		if (data.getOrderNumber() != null
				&& data.getOrderNumber().trim().length() > 0) {
			sql += " and t1.so_number = '"
					+ LogicUtility.getDataString(data.getOrderNumber().trim())
					+ "'";
			blHasCondition = true;
		}

		// 订单状态(Constants.BLANK_OPTION_VALUE: 默认未选择任何订单状态)
		if (data.getStatusId() != Constants.BLANK_OPTION_VALUE) {
			sql += " and t1.status = " + data.getStatusId();
			blHasCondition = true;
		}

		// 订单来源(Constants.BLANK_OPTION_VALUE: 默认未选择任何订单来源)
		if (data.getPrTypeId() != Constants.BLANK_OPTION_VALUE) {
			sql += " and t1.pr_type = " + data.getPrTypeId();
			blHasCondition = true;
		}

		// 经办人(Constants.BLANK_OPTION_VALUE: 默认未选择任何经办人)
		if (data.getCreatorId() != Constants.BLANK_OPTION_VALUE) {
			sql += " and t1.creator_id = " + data.getCreatorId();
			blHasCondition = true;
		}

		// 订单总金额
		double dbl = LogicUtility.parseDouble(data.getTotalMoneyBottom(), 0.0);
		if (dbl > 0.0) {
			sql += " and t1.goods_fee >= " + dbl;
			blHasCondition = true;
		}
		dbl = LogicUtility.parseDouble(data.getTotalMoneyTop(), 0.0);
		if (dbl > 0.0) {
			sql += " and t1.goods_fee <= " + dbl;
			blHasCondition = true;
		}

		// 订单创建日期
		String date = data.getCreateDateBottom().trim();
		if (LogicUtility.parseDateFromString(date) != null) {
			sql += " and t1.release_date >= to_date('" + date
					+ "', 'yyyy-MM-dd')";
			blHasCondition = true;
		} else {
			data.setCreateDateBottom(null);
		}

		date = data.getCreateDateTop().trim();
		if (LogicUtility.parseDateFromString(date) != null) {
			sql += " and t1.release_date <= to_date('" + date
					+ "', 'yyyy-MM-dd')";
			blHasCondition = true;
		} else {
			data.setCreateDateTop(null);
		}

		if (!blHasCondition) {
			// 没有查询条件
			throw new Exception("请输入查询条件！");
		}

		sql += " order by t1.id desc";

		// 分页处理
		db.setPageAttribute(data.getPageAttribute());
		db.queryDetailData(sql, wd, true);
		while (wd.next()) {
			OrderForm order = new OrderForm();
			order.setOrderId(wd.getDetailInt("id"));
			order.setOrderNumber(wd.getDetailString("so_number"));
			order.setCardId(wd.getDetailString("card_id"));
			order.setMbName(wd.getDetailString("mbname"));
			order.setTotalMoney(wd.getDetailDouble("goods_fee"));
			order.setPayable(wd.getDetailDouble("order_sum"));
			order.setStatusId(wd.getDetailInt("statusid"));
			order.setStatusName(wd.getDetailString("statusname"));
			order.setPrTypeId(wd.getDetailInt("prtypeid"));
			order.setPrTypeName(wd.getDetailString("prtypename"));
			order.setCategoryId(wd.getDetailInt("categoryid"));
			order.setCategoryName(wd.getDetailString("categoryname"));
			order.setCreateDate(wd.getDetailDate("release_date"));
			order.setCreatorId(wd.getDetailInt("creator_id"));
			order.setCreatorName(wd.getDetailString("creatorName"));

			data.getItems().add(order);
		}
		wd = null;
	}

	/**
	 * 得到促销礼品列表 add bymagic
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void listGift(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = null;
		// 保存现场
		String queryItemCode = data.getQueryItemCode();

		// step1 取得新会员促销产品
		Member mb = data.getCart().getMember();
		if (!mb.isOldMember()) {
			sql = "select t2.id,t2.item_code,t3.itm_name,t2.price,t3.itm_type," +
					" t3.sale_price,t3.vip_price, t4.name as item_unit "
					+ " from prd_pricelists t1 join prd_pricelist_gift t2 on t1.id = t2.pricelist_id "
					+ " join prd_item t3 on t2.item_code = t3.itm_code  "
					+ " left join s_uom t4 on t3.itm_unit = t4.id "
					+ " where t1.msc='"
					+ mb.getMSC_CODE()
					+ "' and t1.PRICE_TYPE_ID=1 and t2.status=0 "
					+ " order by t2.price";
			wd.clearDetailData();
			db.queryDetailData(sql, wd, false);

			while (wd.next()) {
				ItemInfo gift = new ItemInfo();
				// gift.setSku_id(wd.getDetailInt("itemid"));
				gift.setItemCode(wd.getDetailString("item_code"));
				gift.setItemName(wd.getDetailString("itm_name"));
				gift.setFloorMoney(0);
				gift.setAddy(wd.getDetailDouble("price"));

				gift.setItemSilverPrice(wd.getDetailDouble("sale_price"));
				gift.setItemGoldenPrice(wd.getDetailDouble("vip_price"));
				gift.setItemUnit(wd.getDetailString("item_unit"));

				// gift.setItemPrice((data.getCart().getMember().getLEVEL_ID()
				// == 3
				// && data.getCart().getMember().isOldMember()) ? gift
				// .getGoldenPrice()
				// : gift.getSilverPrice());
				// 是否套装
				gift.setTruss(wd.getDetailInt("itm_type") == 2
						|| wd.getDetailInt("itm_type") == 3);

				gift.setStockStatusName("未确定");

				// gift.setItemName(wd.getDetailString("name"));
				gift.setCatalog("新会员促销");
				// gift.setItemUnit(wd.getDetailString("unit"));

				// gift.setSelected(gift.getSku_id() ==
				// data.getSelectedGiftId());
				gift.setValid(data.getTotalMoneyNoCard() >= gift
						.getFloorMoney());
				gift.setItemPrice(wd.getDetailDouble("price"));
				gift.setSellTypeId(4);
				gift.setSellTypeName("礼品赠品");
				// 1：全场促销，2:分类促销 3：组促销
				gift.setFlag(1);
				gift.setGift_group_id(0);// 促销id
				gift.setGift_group_name("全场 任意");
				gift.setAwardId(wd.getDetailInt("id"));// 促销礼品id

				gift.setGroupId(0);
				if (gift.getFlag() == 3) {
					gift.setProm_items(PromotionDAO.queryPromo_Item(db.conn,
							gift.getGift_group_id()));
				}

				gift.setColors(Product2DAO.listItemColor(db.conn, gift
						.getItemCode()));
				gift.setSizes(Product2DAO.listItemSize(db.conn, gift
						.getItemCode()));

				data.getCart().getAllGifts().add(gift);
			}
		}

		// step2 取得老会员促销礼品
		sql = "select t1.id,t1.itemcode,t2.itm_name,t2.itm_type,t1.overx,t1.addy,nvl(t3.group_id,0) group_id, "
				+ "t3.id as promotionid,t3.name as promotion_name,t3.flag, t4.name as unit,t2.sale_price,t2.vip_price "
				+ " from prom_gift t1,prd_item t2,promotion t3, s_uom t4 "
				+ " where t1.itemcode=t2.itm_code  and t1.promotionid = t3.id and t2.itm_unit = t4.id(+) "
				+ " and t1.validflag = 1 "
				// + " and t1.PROMOTIONID<1000000 "
				+ " and t3.validflag = 1 "
				+ " and sysdate>=t3.BEGINDATE and sysdate<t3.ENDDATE+1 "
				+ " order by t3.flag,t1.promotionid,t1.overx ";

		wd.clearDetailData();
		db.queryDetailData(sql, wd, false);

		while (wd.next()) {
			ItemInfo gift = new ItemInfo();
			// gift.setSku_id(wd.getDetailInt("itemid"));
			gift.setItemCode(wd.getDetailString("itemcode"));
			gift.setItemName(wd.getDetailString("itm_name"));
			gift.setFloorMoney(wd.getDetailDouble("overx"));
			gift.setAddy(wd.getDetailDouble("addy"));
			gift.setItemSilverPrice(wd.getDetailDouble("sale_price"));
			gift.setItemGoldenPrice(wd.getDetailDouble("vip_price"));

			// gift.setItemPrice((data.getCart().getMember().getLEVEL_ID() == 3
			// && data.getCart().getMember().isOldMember()) ? gift
			// .getGoldenPrice()
			// : gift.getSilverPrice());
			// 是否套装
			gift.setTruss(wd.getDetailInt("itm_type") == 2
					|| wd.getDetailInt("itm_type") == 3);

			gift.setStockStatusName("未确定");

			// gift.setItemName(wd.getDetailString("name"));
			gift.setCatalog("老会员促销");
			gift.setItemUnit(wd.getDetailString("unit"));

			// gift.setSelected(gift.getSku_id() == data.getSelectedGiftId());
			gift.setValid(data.getTotalMoneyNoCard() >= gift.getFloorMoney());
			gift.setItemPrice(wd.getDetailDouble("addy"));
			gift.setSellTypeId(4);
			gift.setSellTypeName("礼品赠品");
			// 1：全场促销，2:分类促销 3：组促销
			gift.setFlag(wd.getDetailInt("flag"));
			gift.setGift_group_id(wd.getDetailInt("promotionid"));// 促销id
			gift.setGift_group_name(wd.getDetailString("promotion_name"));
			gift.setAwardId(wd.getDetailInt("id"));// 促销礼品id

			gift.setGroupId(wd.getDetailInt("group_id"));
			if (gift.getFlag() == 3) {
				gift.setProm_items(PromotionDAO.queryPromo_Item(db.conn, gift
						.getGift_group_id()));
			}

			gift.setColors(Product2DAO.listItemColor(db.conn, gift.getItemCode()));
			gift.setSizes(Product2DAO.listItemSize(db.conn, gift.getItemCode()));

			data.getCart().getAllGifts().add(gift);
		}
		
		//获得多少元任选几件促销
		ArrayList allGifts2 = OrderDAO.getValidPromotion2(db.conn);
		data.getCart().setAllGifts2(allGifts2);
		
		// 恢复现场
		data.setQueryItemCode(queryItemCode);
		wd = null;
	}

	/**
	 * 得到有效的多少元任选几件促销
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static ArrayList getValidPromotion2(Connection conn) throws Exception {
		ArrayList ret = new ArrayList();
		String sql = " select t1.name,t1.id "
				   + " from promotion t1 "
				   + " where t1.validflag=1 and t1.begindate < sysdate "
				   + " and t1.enddate>trunc(sysdate)-1 and t1.putbasket <>1 and t1.flag=4";
		
		String sql1 = "select * from prom_money4qty where promotionid=? and flag=1 order by money";
		
		String sql2 = "select t1.id,t1.itemcode,t2.itm_name,t2.itm_type,t2.sale_price,t3.name as item_unit "
			+ " from prom_item t1 join prd_item t2 on t1.itemcode=t2.itm_code "
		    + " left join s_uom t3 on t2.itm_unit = t3.id "
			+ " where promotionid =? and flag=1 ";
		PreparedStatement ps = null,ps1 = null,ps2 = null;
		try {
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sql1);
			ps2 = conn.prepareStatement(sql2);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				// 得到促销主信息
				Proms2 prom= new Proms2();
				prom.setId(rs.getLong("id"));
				prom.setName(rs.getString("name"));
				
				// 得到促销规则信息
				ps1.setLong(1, prom.getId());
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next()) {
					Money4Qty mq = new Money4Qty();
					mq.setId(rs1.getLong("id"));
					mq.setMoney(rs1.getDouble("money"));
					mq.setQty(rs1.getInt("qty"));
					
					prom.getMoney4qty().add(mq);
				}
				rs1.close();
				
				//得到促销产品信息
				ps2.setLong(1, prom.getId());
				ResultSet rs2 = ps2.executeQuery();
				while(rs2.next()) {
					ItemInfo ii = new ItemInfo();
					ii.setAwardId(rs2.getInt("id"));
					ii.setItemCode(rs2.getString("itemcode"));
					ii.setItemName(rs2.getString("itm_name"));
					ii.setItemSilverPrice(rs2.getDouble("sale_price"));
					ii.setItemUnit(rs2.getString("item_unit"));
					ii.setGroupId((int)prom.getId());
					int itemType = rs2.getInt("itm_type");
					ii.setTruss(itemType==2||itemType==3);
					
					ii.setSellTypeId(4);
					ii.setSellTypeName("商品促销");
					ii.setStockStatusName("未确定");
					
					ii.setColors(Product2DAO.listItemColor(conn, ii.getItemCode()));
					ii.setSizes(Product2DAO.listItemSize(conn, ii.getItemCode()));
					
					prom.getItems().add(ii);
				}
				rs2.close();
				
				
				ret.add(prom);
			}
			rs.close();
			
		} finally {
			try {ps.close();} catch(Exception e){}
			try {ps1.close();} catch(Exception e){}
			try {ps2.close();} catch(Exception e){}
		}
		return ret;
	}
	/**
	 * 根据会员ID或者会员卡号得到会员信息
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void getMemberInfo(DBOperation db, OrderForm data)
			throws Exception {

		// 得到默认的送货地址 如果提供会员id,根据id查,如果提供会员号,根据会员号查,如果提供订单id,根据订单id查
		String sql = "select a.id, a.card_id, a.name as mbname, a.telephone, "
				+ "b.id as address_id, a.level_id, a.msc_code, a.deposit,a.emoney,a.frozen_emoney, "
				+ "a.emoney, a.forzen_credit, a.ANIMUS_COUNT, a.is_organization, a.create_date "
				+ "from mbr_members a left outer join mbr_addresses b on b.member_id = a.id and b.is_default = 1 "
				+ "where ";
		if (data.getMbId() > 0) {
			sql += "a.id = " + data.getMbId();
		} else {
			if (data.getCardId() == null || data.getCardId().equals("")) {
				if (data.getOrderId() > 0) {
					sql += "a.id = (select buyer_id from ord_headers where id = "
							+ data.getOrderId() + " )";
				}

			} else {
				sql += "a.card_id = '"
						+ LogicUtility.getDataString(data.getCardId()) + "'";
			}
		}

		WebData wd = new WebData();
		db.queryHeaderData(sql, wd);
		if (wd.getHeaderInt("id") > 0) {
			data.setMbId(wd.getHeaderInt("id"));
			data.getCart().getMember().setID(wd.getHeaderInt("id"));
			data.getCart().getMember()
					.setCARD_ID(wd.getHeaderString("card_id"));
			data.getCart().getMember().setNAME(wd.getHeaderString("mbname"));
			data.getCart().getMember().setTELEPHONE(
					wd.getHeaderString("telephone"));
			data.getCart().getMember().setADDRESS_ID(
					wd.getHeaderString("address_id"));
			data.getCart().getMember().setLEVEL_ID(wd.getHeaderInt("level_id"));
			data.getCart().getMember().setMSC_CODE(
					wd.getHeaderString("msc_code"));
			data.getCart().getMember()
					.setDEPOSIT(wd.getHeaderDouble("deposit"));
			data.getCart().getMember().setFORZEN_CREDIT(
					wd.getHeaderDouble("forzen_credit"));
			data.getCart().getMember().setEMONEY(wd.getHeaderInt("emoney"));
			data.getCart().getMember().setFROZEN_EMONEY(
					wd.getHeaderInt("frozen_emoney"));
			data.getCart().getMember().setANIMUS_COUNT(
					wd.getHeaderInt("ANIMUS_COUNT"));
			data.getCart().getMember().setIS_ORGANIZATION(
					wd.getHeaderString("is_organization"));
			data.getCart().getMember().setCREATE_DATE(
					wd.getHeaderString("create_date"));// add by user 2007-08-31

		}

		if (wd.getHeaderInt("address_id") == 0) {
			wd = null;
			return;
		}

		// 查询送货信息、付款信息
		sql = "select t1.id, t1.member_id, t1.relation_person, "
				+ "t1.telephone, t1.delivery_address, t1.postcode, "
				+ "t1.delivery_type as deliveryTypeId,t1.section,"
				+ "t2.name as deliveryTypeName, t2.delivery_fee, "
				+ "t2.gold_delivery_fee, t2.platina_delivery_fee, "
				+ "t1.pay_method as paymentTypeId,t1.section,"
				+ "t3.name as paymentTypeName, t1.telephone1,nvl(t3.discount,1) discount "
				+ "from mbr_addresses t1, " + "s_delivery_type t2,"
				+ "s_payment_method t3 " + "where t1.delivery_type = t2.id(+) "
				+ "and t1.pay_method = t3.id(+) and t1.id = "
				+ data.getCart().getMember().getADDRESS_ID();
		wd.clearHeaderData();
		db.queryHeaderData(sql, wd);

		if (wd.getHeaderInt("id") > 0) {
			data.getCart().getDeliveryInfo()
					.setAddressId(wd.getHeaderInt("id"));
			data.getCart().getDeliveryInfo().setReceiptor(
					wd.getHeaderString("relation_person"));
			data.getCart().getDeliveryInfo().setPhone(
					wd.getHeaderString("telephone"));
			data.getCart().getDeliveryInfo().setPhone2(
					wd.getHeaderString("telephone1"));
			data.getCart().getDeliveryInfo().setSectionName(
					S_AREADao.getFullBySection(db.conn, wd.getHeaderString("section")));
			data.getCart().getDeliveryInfo().setAddress(
					wd.getHeaderString("delivery_address"));
			data.getCart().getDeliveryInfo().setPostCode(
					wd.getHeaderString("postcode"));
			data.getCart().getDeliveryInfo().setDeliveryTypeId(
					wd.getHeaderInt("deliveryTypeId"));
			data.getCart().getDeliveryInfo().setDeliveryType(
					wd.getHeaderString("deliveryTypeName"));
			data.getCart().getDeliveryInfo().setPaymentTypeId(
					wd.getHeaderInt("paymentTypeId"));
			data.getCart().getDeliveryInfo().setPaymentType(
					wd.getHeaderString("paymentTypeName"));
			data.getCart().getDeliveryInfo().setSection(
					wd.getHeaderString("section"));

			data.getCart().getOtherInfo().setPaydiscount(
					wd.getHeaderDouble("discount"));

		}
		wd = null;

		/**
		 * @author user 2006-06-23 TODO 判断会员是否老会员
		 */
		data.getCart().getMember().setOldMember(isOldMember(db, data));
		/**
		 * @author user 2006-06-23 TODO 判断改会员是否是黑名单成员，如果是页面上的信息用红色标记
		 */
		data.getCart().getMember().setBlacklistMember(
				new MemberBlackListDAO().isExistBlacklist(db.conn, data
						.getMbId()));
	}

	public static void changeAddress(DBOperation db, OrderForm data)
			throws Exception {
		String strSql = "update mbr_members set address_id = "
				+ data.getReceiptorAddressId() + " where id = "
				+ data.getMbId();
		db.executeUpdate(strSql);
	}

	public static void changeAddress(Connection conn, OrderForm data)
			throws Exception {
		String strSql = "update mbr_members set address_id = "
				+ data.getReceiptorAddressId() + " where id = "
				+ data.getMbId();
		Statement st = conn.createStatement();
		st.executeUpdate(strSql);
		st.close();
	}

	public static void changeDelivery(DBOperation db, OrderForm data)
			throws Exception {
		String strSql = "update mbr_addresses set delivery_type = "
				+ data.getDeliveryTypeId() + " where id = "
				+ data.getReceiptorAddressId();
		int nRows = db.executeUpdate(strSql);
		if (nRows > 0) {
			// 检查PaymentID是否在允许范围内
			strSql = "select payment_id from s_delivery_payment "
					+ " where delivery_id = " + data.getDeliveryTypeId()
					+ " and payment_id in ("
					+ " select pay_method from mbr_addresses where id = "
					+ data.getReceiptorAddressId() + ")";
			WebData wd = new WebData();
			db.queryHeaderData(strSql, wd);
			if (wd.getHeaderInt("payment_id") == 0) {
				// 付款方式与更改后的送货方式不匹配
				// 清除付款方式
				strSql = "update mbr_addresses set pay_method = decode(delivery_type,1,6,3,3,4,6,5,1,3)  where id = "
						+ data.getReceiptorAddressId();
				db.executeUpdate(strSql);
			}
		}
	}

	public static void changeDelivery(Connection conn, OrderForm data)
			throws Exception {

		String strSql = "update mbr_addresses set delivery_type = "
				+ data.getDeliveryTypeId() + " where id = "
				+ data.getReceiptorAddressId();
		Statement st = conn.createStatement();
		int nRows = st.executeUpdate(strSql);
		if (nRows > 0) {
			// 检查PaymentID是否在允许范围内
			strSql = "select payment_id from s_delivery_payment "
					+ " where delivery_id = " + data.getDeliveryTypeId()
					+ " and payment_id in ("
					+ " select pay_method from mbr_addresses where id = "
					+ data.getReceiptorAddressId() + ")";
			ResultSet rs = st.executeQuery(strSql);
			if (rs.next()) {
				// ///////////
			} else {
				// 付款方式与更改后的送货方式不匹配
				// 清除付款方式
				strSql = "update mbr_addresses set pay_method = null where id = "
						+ data.getReceiptorAddressId();
				st.executeUpdate(strSql);
			}
			rs.close();
		}
		st.close();
	}

	public static void changePayment(DBOperation db, OrderForm data)
			throws Exception {
		String strSql = "update mbr_addresses set pay_method = "
				+ data.getPaymentTypeId() + " where id = "
				+ data.getReceiptorAddressId();
		db.executeUpdate(strSql);
	}

	public static void changePayment(Connection conn, OrderForm data)
			throws Exception {
		String strSql = "update mbr_addresses set pay_method = "
				+ data.getPaymentTypeId() + " where id = "
				+ data.getReceiptorAddressId();
		Statement st = conn.createStatement();
		st.executeUpdate(strSql);
		st.close();
	}

	/**
	 * 取产品信息
	 * 
	 * @param db
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static ItemInfo findItem(Connection conn, String itemCode)
			throws Exception {
		ItemInfo item = null;
		PreparedStatement ps;
		try {

			String sql = "SELECT a.*,b.name as unit_name "
					+ "FROM PRD_ITEM A,S_UOM B  WHERE A.ITM_CODE = ? AND A.itm_unit = b.id(+)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				item = new ItemInfo();
				// item.setSku_id(rs.getInt("sku_id"));
				item.setItemCode(rs.getString("itm_code"));
				item.setItemName(rs.getString("itm_name"));
				item.setItemUnit(rs.getString("unit_name"));
				item.setLastSell(rs.getBoolean("is_last_sell"));
				int item_type = rs.getInt("itm_type");
				if (item_type == 2 || item_type == 3) {
					item.setTruss(true);
				} else {
					item.setTruss(false);
				}
				// item.setStandardPrice(rs.getDouble("standard_price"));
				// item.setReplacerId(rs.getInt("replace_sku_id"));
				// get other information here later
				// item.setStandardPrice(rs.getDouble("standard_price"));
				// item.setPurchaseingCost(rs.getDouble("purchasing_cost"));
				// item.setUnPurchaseingCost(rs.getDouble("unpurchasing_cost"));
				item.setItemSilverPrice(rs.getDouble("sale_price"));
				item.setItemGoldenPrice(rs.getDouble("vip_price"));
				// item.setAvailQty(rs.getInt("avail_qty"));
				item.setItem_category(rs.getInt("category_id"));
				item.setStockStatusName("未确定");
				item.setSellTypeId(0);
				item.setSellTypeName("正常销售");
			}
			rs.close();
			ps.close();

			if (item == null) {
				return null;
			}

			// 如果对应的sku存在且唯一，则该产品直接确定为该sku
			sql = "select count(*) from prd_item_sku where itm_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			int sku_count = 0;
			if (rs.next()) {
				sku_count = rs.getInt(1);
			}
			rs.close();
			ps.close();
			if (sku_count == 1) {

				sql = "select t1.sku_id, t1.color_code,t1.size_code,t1.standard_price,t1.itm_cost,"
						+ " nvl(t2.use_qty - t2.frozen_qty ,0) availqty,nvl(t2.use_qty - t2.frozen_qty + t1.enable_os*t1.os_qty ,0) availqty2 "
						+ " from prd_item_sku t1 left join sto_stock t2 on t1.sku_id = t2.sku_id"
						+ " where itm_code =?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, itemCode);
				rs = ps.executeQuery();
				if (rs.next()) {
					item.setSku_id(rs.getInt("sku_id"));
					item.setColor_code(rs.getString("color_code"));
					item.setSize_code(rs.getString("size_code"));
					item.setStandardPrice(rs.getDouble("standard_price"));
					item.setPurchaseingCost(rs.getDouble("itm_cost"));
					item.setAvailQty(rs.getInt("availqty"));
					item.setAvailQty2(rs.getInt("availqty2"));
				}
				rs.close();
				ps.close();

				// 如果是套装，不用再判断库存等，直接返回
				if (item.isTruss()) {
					return item;
				}

				// 设置库存状态
				if (item.getAvailQty() >= item.getItemQty()) {
					item.setStockStatusId(0);
					item.setStockStatusName("库存正常");
				} else if (item.getAvailQty2() >= item.getItemQty()) {
					item.setIs_pre_sell(1);
					item.setStockStatusName("虚拟库存");
				} else {
					if (item.isLastSell()) {
						item.setStockStatusName("永久缺货");
					} else {
						item.setStockStatusName("暂时缺货");
					}
				}
			}

			// 设置该产品可选颜色和尺寸
			item.setColors(Product2DAO.listItemColor(conn, itemCode));
			item.setSizes(Product2DAO.listItemSize(conn, itemCode));

		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}
		return item;
	}

	/**
	 * 取产品信息
	 * 
	 * @param db
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static ItemInfo findItem(Connection conn, String itemCode,
			String sizeCode, String colorCode) throws Exception {
		ItemInfo item = null;
		PreparedStatement ps;
		try {

			String sql = "SELECT a.*,b.name as unit_name "
					+ "FROM PRD_ITEM A,S_UOM B  WHERE A.ITM_CODE = ? AND A.itm_unit = b.id(+)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				item = new ItemInfo();
				// item.setSku_id(rs.getInt("sku_id"));
				item.setItemCode(rs.getString("itm_code"));
				item.setItemName(rs.getString("itm_name"));
				item.setItemUnit(rs.getString("unit_name"));
				item.setLastSell(rs.getBoolean("is_last_sell"));
				int item_type = rs.getInt("itm_type");
				if (item_type == 2 || item_type == 3) {
					item.setTruss(true);
				} else {
					item.setTruss(false);
				}
				// item.setStandardPrice(rs.getDouble("standard_price"));
				// item.setReplacerId(rs.getInt("replace_sku_id"));
				// get other information here later
				// item.setStandardPrice(rs.getDouble("standard_price"));
				// item.setPurchaseingCost(rs.getDouble("purchasing_cost"));
				// item.setUnPurchaseingCost(rs.getDouble("unpurchasing_cost"));
				item.setItemSilverPrice(rs.getDouble("sale_price"));
				item.setItemGoldenPrice(rs.getDouble("vip_price"));
				// item.setAvailQty(rs.getInt("avail_qty"));
				item.setItem_category(rs.getInt("category_id"));
				item.setStockStatusName("未确定");
				item.setSellTypeId(0);
				item.setSellTypeName("正常销售");
			}
			rs.close();
			ps.close();

			// 如果是套装，不用再判断库存等，直接返回
			if (item == null || item.isTruss()) {
				return item;
			}

			// 如果对应的sku存在且唯一，则该产品直接确定为该sku
			sql = "select count(*) from prd_item_sku where itm_code = ? and size_code =? and color_code =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ps.setString(2, sizeCode.toUpperCase());
			ps.setString(3, colorCode.toUpperCase());
			rs = ps.executeQuery();
			int sku_count = 0;
			if (rs.next()) {
				sku_count = rs.getInt(1);
			}
			rs.close();
			ps.close();
			if (sku_count == 1) {
				sql = "select t1.sku_id, t1.color_code,t1.size_code,t1.standard_price,t1.itm_cost, t3.name, "
						+ " nvl(t2.use_qty - t2.frozen_qty ,0) availqty,nvl(t2.use_qty - t2.frozen_qty + t1.enable_os*t1.os_qty ,0) availqty2 "
						+ " from prd_item_sku t1 left join sto_stock t2 on t1.sku_id = t2.sku_id "
						+ " left join prd_item_color t3 on t1.color_code = t3.code "
						+ " where itm_code =? and size_code=? and color_code=? ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, itemCode);
				ps.setString(2, sizeCode.toUpperCase());
				ps.setString(3, colorCode.toUpperCase());
				rs = ps.executeQuery();
				if (rs.next()) {
					item.setSku_id(rs.getInt("sku_id"));
					item.setColor_code(rs.getString("color_code"));
					item.setColor_name(rs.getString("name"));
					item.setSize_code(rs.getString("size_code"));
					item.setStandardPrice(rs.getDouble("standard_price"));
					item.setPurchaseingCost(rs.getDouble("itm_cost"));
					item.setAvailQty(rs.getInt("availqty"));
					item.setAvailQty2(rs.getInt("availqty2"));
				}
				rs.close();
				ps.close();

				// 设置库存状态
				if (item.getAvailQty() >= item.getItemQty()) {
					item.setStockStatusId(0);
					item.setStockStatusName("库存正常");
				} else if (item.getAvailQty2() >= item.getItemQty()) {
					item.setIs_pre_sell(1);
					item.setStockStatusName("虚拟库存");
				} else {
					if (item.isLastSell()) {
						item.setStockStatusName("永久缺货");
					} else {
						item.setStockStatusName("暂时缺货");
					}
				}
			}

			// 设置该产品可选颜色和尺寸
			item.setColors(Product2DAO.listItemColor(conn, itemCode));
			item.setSizes(Product2DAO.listItemSize(conn, itemCode));

		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}
		return item;
	}

	/**
	 * 判断该会员是否为老会员
	 * 
	 * @param db
	 * @param order
	 *            使用其中的会员ID查询、并且订单号不为order中的订单号（用于订单修改之用）
	 * @return
	 */
	public static boolean isOldMember(DBOperation db, OrderForm order)
			throws Exception {
		WebData wd = new WebData();
		/**
		 * 订单状态： -1：订单取消 -2：订单超期缺货取消 -3：订单缺款取消 -4：订单未处理取消 -5：订单待确认取消 -8：订单退货
		 */
		String strSql = "select id from ord_headers where buyer_id = "
				+ order.getMbId() + " and id <> " + order.getOrderId()
				+ " and status >=0 ";
		db.queryHeaderData(strSql, wd);
		boolean blResult = (wd.getHeaderInt("id") > 0);
		wd = null;
		return blResult;
	}

	/**
	 * 取到货日期
	 * 
	 * @param db
	 * @param ii
	 *            取其商品ID作为查询条件 把到货日期存储其中
	 * @throws Exception
	 */
	private static void getLandDate(DBOperation db, ItemInfo ii)
			throws Exception {
		String strSql = "select t2.pre_date from jxc_sto_pur_dtl t1, "
				+ "jxc_sto_pur_mst t2 where t1.pur_no = t2.pur_no(+) "
				+ "and t1.sku_id = " + ii.getSku_id()
				+ " and t2.pre_date >= sysdate and status in (2, 3) "
				+ " order by t2.pre_date";
		WebData wd = new WebData();
		db.queryHeaderData(strSql, wd);
		ii.setLandDate(wd.getHeaderDate("pre_date"));
		wd = null;
	}

	/**
	 * 取到货日期
	 * 
	 * @param db
	 * @param ii
	 *            取其商品ID作为查询条件 把到货日期存储其中
	 * @throws Exception
	 */
	public static void getLandDate(Connection conn, ItemInfo ii)
			throws Exception {
		String sql = "select t2.pre_date from jxc_sto_pur_dtl t1, "
				+ "jxc_sto_pur_mst t2 where t1.pur_no = t2.pur_no(+) "
				+ "and t1.sku_id = " + ii.getSku_id()
				+ " and t2.pre_date >= sysdate and status in (2, 3) "
				+ " order by t2.pre_date";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			ii.setLandDate(rs.getString("pre_date"));
		}
		rs.close();
		st.close();
	}

	/**
	 * 取价格的流程控制
	 * 
	 * @param db
	 * @param ii
	 *            取其商品ID作为查询条件 把查询所得的价格和销售方式存储其中 SellTypeId = -1时，当作礼品查询
	 *            SellTypeId = -2时，当作产品查询
	 * @param order
	 *            取其中的会员等级、是否为老会员、会员msc参与查询条件
	 * @throws Exception
	 */
	public static void fillItemPrice(Connection conn, ItemInfo item,
			OrderForm order) throws Exception {
		if (order.getCart().getMember().isOldMember()) {
			filloItemPrice(conn, item);

		} else {
			fillnItemPrice(conn, item, order.getCart().getMember()
					.getMSC_CODE());

		}
		if (order.getCart().getMember().getLEVEL_ID() == 3) {
			item.setItemPrice(item.getGoldenPrice());
		} else {
			item.setItemPrice(item.getSilverPrice());
		}
		item.setSellTypeId(0);
		item.setSellTypeName("正常销售");

	}

	/**
	 * 套装产品分拆为单品
	 * 
	 * @param db
	 * @param ii
	 * 
	 * @throws Exception
	 */
	public static ArrayList<ItemInfo> splitSet2Part(Connection conn,
			ItemInfo ii, int mbr_level) throws Exception {
		ArrayList<ItemInfo> parts = new ArrayList<ItemInfo>();
		String sql = "select t1.part_item_code,t1.color_code,t1.sale_price,t1.vip_price, "
				+ " t2.itm_name ,t3.name as item_unit "
				+ " from prd_item_set t1 join prd_item t2 on t1.part_item_code=t2.itm_code "
				+ " left join s_uom t3 on t2.itm_unit = t3.id"
				+ " where t1.set_item_code = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, ii.getItemCode());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ItemInfo part = new ItemInfo();
			part.setItemCode(rs.getString("part_item_code"));
			part.setItemName(rs.getString("itm_name"));
			part.setSet_code(ii.getItemCode());
			part.setColor_code(rs.getString("color_code"));
			part.setSilverPrice(rs.getFloat("sale_price")
					* ii.getSilverPrice() / ii.getItemSilverPrice());
			part.setGoldenPrice(rs.getFloat("vip_price") * ii.getGoldenPrice()
					/ ii.getItemGoldenPrice());
			part.setSet_group_id(ii.getSet_group_id());
			part.setSet_price(ii.getItemPrice());
			part.setSellTypeId(-77);
			part.setSellTypeName("套装打折");
			part.setStockStatusName("未确定");
			part.setItemUnit(rs.getString("item_unit"));
			part.setIsDiscount(ii.getIsDiscount());
			if (mbr_level == 3) {
				part.setItemPrice(part.getGoldenPrice());
			} else {
				part.setItemPrice(part.getSilverPrice());
			}

			
			// 设置该产品可选颜色和尺寸
			part.setColors(Product2DAO.listItemColor(conn, part.getItemCode()));
			part.setSizes(Product2DAO.listItemSize(conn, part.getItemCode()));

			parts.add(part);
		}
		rs.close();
		ps.close();

		return parts;
	}

	/**
	 * 礼品套装产品分拆为单品
	 * 
	 * @param db
	 * @param ii
	 * 
	 * @throws Exception
	 */
	public static ArrayList<ItemInfo> splitGiftSet2Part(Connection conn,
			ItemInfo ii) throws Exception {
		ArrayList<ItemInfo> parts = new ArrayList<ItemInfo>();
		String sql = "select t1.part_item_code,t1.color_code,t1.sale_price,t1.vip_price, "
				+ " t2.itm_name,t3.name as item_unit "
				+ " from prd_item_set t1 join prd_item t2 on t1.part_item_code=t2.itm_code "
				+ " left join s_uom t3 on t2.itm_unit = t3.id"
				+ " where t1.set_item_code = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, ii.getItemCode());
		ResultSet rs = ps.executeQuery();
		BigDecimal setPrice = BigDecimal.valueOf(ii.getItemPrice());
		
		while (rs.next()) {
			ItemInfo part = new ItemInfo();
			part.setItemCode(rs.getString("part_item_code"));
			part.setItemName(rs.getString("itm_name"));
			part.setSet_code(ii.getItemCode());
			part.setColor_code(rs.getString("color_code"));
			//part.setSilverPrice(rs.getDouble("sale_price")*ii.getSilverPrice()
			// /ii.getItemSilverPrice());
			//part.setGoldenPrice(rs.getDouble("vip_price")*ii.getGoldenPrice()/
			// ii.getItemGoldenPrice());
			
			part.setSet_price(ii.getItemPrice());
			part.setItemSilverPrice(rs.getDouble("sale_price"));
			part.setSetSilverPrice(ii.getItemSilverPrice());
			BigDecimal itemPrice =BigDecimal.valueOf(part.getItemSilverPrice()/part.getSetSilverPrice() * part.getSet_price()).setScale(2); 
			
			part.setItemPrice(itemPrice.doubleValue());
			part.setAwardId(ii.getAwardId());
			
			part.setItemQty(ii.getItemQty());
			part.setSellTypeId(ii.getSellTypeId());
			part.setSellTypeName(ii.getSellTypeName());
			part.setStockStatusName(ii.getStockStatusName());
			part.setIsDiscount(ii.getIsDiscount());
			
			part.setGroupId(ii.getGroupId());
			part.setSet_group_id(ii.getSet_group_id());
			part.setItemUnit(rs.getString("item_unit"));
			/*
			 * if (mbr_level ==3) { part.setItemPrice(part.getGoldenPrice()); }
			 * else { part.setItemPrice(part.getSilverPrice()); }
			 */

			// 设置该产品可选颜色和尺寸
			part.setColors(Product2DAO.listItemColor(conn, part.getItemCode()));
			part.setSizes(Product2DAO.listItemSize(conn, part.getItemCode()));
			
			parts.add(part);
		}
		rs.close();
		ps.close();

		return parts;
	}

	/**
	 * 订单来源列表
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void listPrType(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select id, name from s_pr_type order by id";
		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			KeyValue kv = new KeyValue();
			kv.setId(wd.getDetailInt("id"));
			kv.setName(wd.getDetailString("name"));
			data.getPrTypes().add(kv);
		}
	}

	/**
	 * 订单状态列表
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void listStatus(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select id, name from s_order_status order by id";
		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			KeyValue kv = new KeyValue();
			kv.setId(wd.getDetailInt("id"));
			kv.setName(wd.getDetailString("name"));
			data.getStatusList().add(kv);
		}
	}

	/**
	 * 订单操作人员列表
	 * 
	 * @param db
	 * @param data
	 * @throws Exception
	 */
	public static void listCreator(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select id, name from org_persons where id <> 0 "
				+ "order by name";
		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			KeyValue kv = new KeyValue();
			kv.setId(wd.getDetailInt("id"));
			kv.setName(wd.getDetailString("name"));
			data.getCreatorList().add(kv);
		}
	}

	/**
	 * 得到指定产品的可用库存数量，指定产品可以为普通产品、套装产品和替代产品 检查产品库存应使用本方法，而不要使用
	 * getTrussStockQty(DBOperation db, int itemId, boolean isOldMember) 和
	 * getAvailableStockQty(DBOperation db, int itemId, boolean isOldMember)
	 * 
	 * @param db
	 * @param ii
	 *            指定的产品，除了其应有的产品ID property项，还需要其套装产品和替代产品的 property项
	 * @param isOldMember
	 * @return
	 * @throws Exception
	 */
	public static int getAvailableStockQty(DBOperation db, ItemInfo ii,
			OrderForm order) throws Exception {
		boolean isOldMember = order.getCart().getMember().isOldMember();
		// 取现有库存可用数量
		int nStockQty = 0;
		if (ii.isTruss()) {
			// 套装产品
			nStockQty = getTrussStockQty(db, ii.getSku_id(), isOldMember);
		} else if (ii.getReplacerId() > 0) {
			// 有替代品
			// 自身可用数量
			int nQty1 = getAvailableStockQty(db.conn, ii.getSku_id(),
					isOldMember);
			// 替代品可用数量
			// int nQty2 = getAvailableStockQty(db,
			// ii.getReplacerId(),isOldMember);
			// modify bymagic
			// 下订单时,只判断自身可用数量,如果自身可用数量不足,则要用替代品替代
			// nStockQty = Math.max(nQty1, nQty2);
			nStockQty = nQty1;
		} else {
			// 普通产品
			nStockQty = getAvailableStockQty(db.conn, ii.getSku_id(),
					isOldMember);
		}
		// System.out.println(nStockQty);
		// 取当前订单中的产品数量
		int nOrderQty = 0;
		if (order.getOrderId() > 0) {
			String str = "select order_id, frozen_qty from ord_lines "
					+ "where order_id = " + order.getOrderId()
					+ " and sku_id = " + ii.getSku_id();
			WebData wd = new WebData();
			db.queryHeaderData(str, wd);
			if (wd.getHeaderInt("order_id") > 0) {
				nOrderQty = wd.getHeaderInt("frozen_qty");
			}
			wd = null;
		}

		return nStockQty + nOrderQty;
	}

	/**
	 * 得到套装产品的可用数量 和getAvailableStockQty(DBOperation db, ItemInfo ii, boolean
	 * isOldMember) 调用
	 * 
	 * @param db
	 * @param itemId
	 * @param isOldMember
	 * @return
	 * @throws Exception
	 */
	private static int getTrussStockQty(DBOperation db, int itemId,
			boolean isOldMember) throws Exception {
		WebData wd = new WebData();
		// 得到套装组成产品
		String sql = "select set_sku_id, part_sku_id "
				+ "from prd_item_sets where set_sku_id = " + itemId;

		// 参数false表示不进行分页处理，取得所有符合条件的记录
		db.queryDetailData(sql, wd, false);
		int nMinQty = 99999;
		int nAvailable = 0;
		// 读取所有套装产品中最小可用库存量
		while (wd.next()) {
			nAvailable = getAvailableStockQty(db.conn, wd
					.getDetailInt("part_sku_id"), isOldMember);

			if (nMinQty > nAvailable)
				nMinQty = nAvailable;
		}

		return nMinQty;
	}

	/**
	 * 得到指定产品的可用数量 此方法供getTrussStockQty(DBOperation db, int itemId, boolean
	 * isOldMember) 和getAvailableStockQty(DBOperation db, ItemInfo ii, boolean
	 * isOldMember) 调用
	 * 
	 * @param db
	 * @param itemId
	 * @param isOldMember
	 * @return
	 * @throws Exception
	 */
	private static int getAvailableStockQty(Connection conn, int itemId,
			boolean isOldMember) throws Exception {
		// WebData wd = new WebData();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mQty = 0;
		int frozenQty = 0;
		String sql = "select sku_id, use_qty,frozen_qty from sto_stock where sku_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemId);

			rs = pstmt.executeQuery();
			// db.queryHeaderData(sql, wd);

			if (rs.next()) {
				mQty = rs.getInt("use_qty");
				// int MQty = wd.getHeaderInt("o_member_qty");
				frozenQty = rs.getInt("frozen_qty");
				// int oldFrozenQty = wd.getHeaderInt("o_frozen_qty");

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return mQty - frozenQty;
	}

	/**
	 * 取得是否免发送费，免发送费有两种情况 1.定单金额达到免发送费的金额 去发送费用函数 f_get_delivery_fee
	 * 
	 * @param db
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static double getDeliveryFee(DBOperation db, OrderForm order)
			throws Exception {
		System.out.println(order.getCart().getDeliveryInfo()
				.getDeliveryTypeId());
		if(order.getManualFreeFreight())
		{
			order.getCart().getDeliveryInfo().setDeliveryFee(0.00);
			return 0;
		}
		/*
		 * if (order.getCart().getDeliveryInfo().getDeliveryTypeId() <= 0 ||
		 * order.getCart().getDeliveryInfo().getDeliveryTypeId() > 3) { //
		 * 没有发送方式 return 0; }
		 */
		CallableStatement cstmt = db.conn
				.prepareCall("{?=call orders.f_get_delivery_fee(?, ?, ?, ?, ?)}");
		cstmt.setString(2, order.getCart().getDeliveryInfo().getSection());
		cstmt.setInt(3, order.getCart().getDeliveryInfo().getDeliveryTypeId());
		cstmt.setInt(4, order.getCart().getMember().getLEVEL_ID());
		cstmt.setInt(5, 1);
		cstmt.setDouble(6, order.getCart().getTotalMoney()
				- order.getCart().getTicketKill()
				+ order.getCart().getPackageFee());
		cstmt.registerOutParameter(1, java.sql.Types.DOUBLE);
		cstmt.execute();
		double ret = cstmt.getDouble(1);
		if (ret < 0) { // 返回值小于0出错

		} else {
			order.getCart().getDeliveryInfo().setDeliveryFee(ret);
		}

		cstmt.close();
		return ret;
	}

	/*
	 * 获取*/
	public static void getPackageFee(Connection conn, OrderForm order)
			throws Exception {
		double ret = 0;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select price from package_type where id =? ";
		try {
			st = conn.prepareStatement(sql);
			// st.setInt(1,
			// order.getCart().getDeliveryInfo().getDeliveryTypeId());
			// st.setInt(2, order.getCart().getMember().getLEVEL_ID());
			st.setInt(1, order.getPackage_type());
			rs = st.executeQuery();
			if (rs.next()) {
				ret = rs.getDouble("price");

			} else {
				ret = 0;// 没有找到对应的会员

			}
			order.getCart().setPackageType(order.getPackage_type());
			order.getCart().setPackageFee(ret);

		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		}

	}

	/**
	 * this function get Member information
	 * 
	 * @param conn
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static int getMemberInfo(Connection conn, OrderForm order)
			throws Exception {
		int ret = 0;
		Statement st = null;
		try {
			st = conn.createStatement();
			String sql = "select id,card_id,name,nvl(deposit,0) + nvl(emoney,0) money,forzen_credit,address_id from mbr_members where is_organization='0' ";
			if (order.getMbId() > 0) {
				sql += " and id = " + order.getMbId();
			}
			if (order.getCardId() != null && !order.getCardId().equals("")) {
				sql += " and card_id = '" + order.getCardId() + "'";
			}
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				order.setMbId(rs.getInt("id"));
				order.setCardId(rs.getString("card_id"));
				order.setMbName(rs.getString("name"));
				order.setMbMoney(rs.getString("money"));
				order.setMbFrozenMoney(rs.getString("forzen_credit"));
				order.setReceiptorAddressId(rs.getInt("address_id"));
			} else {
				ret = -1;// 没有找到对应的会员
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}

		return ret;
	}

	/**
	 * this function get OrderID
	 * 
	 * @param conn
	 * @param OrderCode
	 * @return OrderID
	 * @throws Exception
	 */
	public int getOrderID(Connection conn, String orderCode) throws Exception {
		int ret = -1;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			String sql = "select id from ord_headers where so_number = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderCode);
			rs = pstm.executeQuery();
			if (rs.next()) {
				ret = rs.getInt(1);
			}
		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {

				}
			if (pstm != null)
				try {
					pstm.close();
				} catch (Exception e) {

				}
		}

		return ret;
	}

	public int getMemberID(Connection conn, String orderID) throws Exception {
		int ret = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select buyer_id from ord_headers where so_number = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = rs.getInt("buyer_id");
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {

				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {

				}
			}

		}

		return ret;
	}

	/**
	 * this function get 团体 Member information
	 * 
	 * @param conn
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static int getGroupMemberInfo(Connection conn, OrderForm order)
			throws Exception {
		int ret = 0;
		Statement st = null;
		try {
			st = conn.createStatement();
			String sql = "select id,card_id,name,nvl(deposit,0) + nvl(emoney,0) money,forzen_credit,address_id from mbr_members where is_organization = '1'";
			if (order.getMbId() > 0) {
				sql += " and id = " + order.getMbId();
			}
			if (order.getCardId() != null && !order.getCardId().equals("")) {
				sql += " and card_id = '" + order.getCardId() + "'";
			}
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				order.getOrgCart().getOrgMember().setID(rs.getInt("id"));
				order.getOrgCart().getOrgMember().setCARD_ID(
						rs.getString("card_id"));
				order.getOrgCart().getOrgMember().setNAME(rs.getString("name"));
				order.getOrgCart().getOrgMember().setDEPOSIT(
						rs.getDouble("money"));
				order.getOrgCart().getOrgMember().setFORZEN_CREDIT(
						rs.getDouble("forzen_credit"));
				order.getOrgCart().getOrgMember().setADDRESS_ID(
						rs.getString("address_id"));
				order.setReceiptorAddressId(rs.getInt("address_id"));
				/**
				 * order.setMbId(rs.getInt("id"));
				 * order.setCardId(rs.getString("card_id"));
				 * order.setMbName(rs.getString("name"));
				 * order.setMbMoney(rs.getString("money"));
				 * order.setMbFrozenMoney(rs.getString("forzen_credit"));
				 * order.setReceiptorAddressId(rs.getInt("address_id"));
				 */

			} else {
				ret = -1;// 没有找到对应的会员
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}

		return ret;
	}

	/**
	 * 从会员礼品表初始化会员购物车
	 * 
	 * @param conn
	 * @param mbid
	 * @return
	 * @throws Exception
	 */
	public static Collection initCart(Connection conn, long mbid)
			throws Exception {
		Vector vgoods = new Vector();
		try {
			Statement st = conn.createStatement();
			String sql = "SELECT a.id,a.quantity,a.price,a.item_code,a.color_code"
					+ " b.itm_name as item_name,c.name as unit_name,d.name as color_name "
					+ " FROM MBR_GET_AWARD a, PRD_ITEM B ,S_UOM C, prd_item_color d"
					+ " WHERE A.STATUS =0 AND A.MEMBER_ID= ? AND a.item_code=b.itm_code "
					+ " AND B.itm_UNIT = C.ID(+) and a.color_code =d.code";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// ItemInfo item = new ItemInfo();
				ItemInfo item = new ItemInfo();
				// item.setSku_id(rs.getInt("sku_id"));
				item.setItemQty(rs.getInt("quantity"));
				item.setItemPrice(rs.getDouble("price"));
				item.setAwardId(rs.getInt("id"));
				item.setItemCode(rs.getString("item_code"));
				item.setItemName(rs.getString("item_name"));
				item.setItemUnit(rs.getString("unit_name"));

				item.setSellTypeId(4);
				item.setSellTypeName("礼品赠品");

				vgoods.add(item);

				getLandDate(conn, item);
				getStockStatus(conn, item);
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}

		return vgoods;
	}

	/**
	 * 根据货号得到产品信息(用于团购) modified by user 2005-12-28 15:16 增加了采购价，市场价，金卡价，银卡价
	 * 
	 * @param conn
	 * @param itemCode
	 * @return
	 * @throws Exception
	 */
	public static ItemInfo getGroupItemByCode(Connection conn, String itemCode)
			throws Exception {
		ItemInfo item = null;
		PreparedStatement ps;
		try {

			String sql = "SELECT a.*,b.name as unit_name "
					+ "FROM PRD_ITEM A, S_UOM B  WHERE A.ITM_CODE = ? AND A.itm_unit = b.id(+)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				item = new ItemInfo();
				// item.setSku_id(rs.getInt("sku_id"));
				item.setItemCode(rs.getString("itm_code"));
				item.setItemName(rs.getString("itm_name"));
				item.setItemUnit(rs.getString("unit_name"));
				item.setLastSell(rs.getBoolean("is_last_sell"));
				int item_type = rs.getInt("itm_type");
				if (item_type == 2 || item_type == 3) {
					item.setTruss(true);
				} else {
					item.setTruss(false);
				}
				// item.setStandardPrice(rs.getDouble("standard_price"));
				// item.setReplacerId(rs.getInt("replace_sku_id"));
				// get other information here later
				// item.setStandardPrice(rs.getDouble("standard_price"));
				// item.setPurchaseingCost(rs.getDouble("purchasing_cost"));
				// item.setUnPurchaseingCost(rs.getDouble("unpurchasing_cost"));
				item.setSilverPrice(rs.getDouble("sale_price"));
				item.setGoldenPrice(rs.getDouble("vip_price"));
				// item.setAvailQty(rs.getInt("avail_qty"));

			}
			rs.close();
			ps.close();

			// 如果对应的sku存在，也取出来
			sql = "select count(*) from prd_item_sku where itm_code = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			int sku_count = 0;
			if (rs.next()) {
				sku_count = rs.getInt(1);
			}
			rs.close();
			ps.close();
			if (sku_count == 0) {
				throw new Exception("产品对应的sku不存在");
			} else if (sku_count == 1) {
				sql = "select t1.sku_id, t1.color_code,t1.size_code,t1.standard_price,t1.itm_cost, nvl(t2.use_qty - t2.frozen_qty + t1.enable_os*t1.os_qty,0) availqty "
						+ " from prd_item_sku t1 left join sto_stock t2 on t1.sku_id = t2.sku_id"
						+ " where t1.itm_code =?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, itemCode);
				rs = ps.executeQuery();
				if (rs.next()) {
					item.setSku_id(rs.getInt("sku_id"));
					item.setColor_code(rs.getString("color_code"));
					item.setSize_code(rs.getString("size_code"));
					item.setStandardPrice(rs.getDouble("standard_price"));
					item.setPurchaseingCost(rs.getDouble("itm_cost"));
					item.setAvailQty(rs.getInt("availqty"));
				}
				rs.close();
				ps.close();

				filloItemPrice(conn, item);

			}

			if (item.isTruss()) {
				return item;
			}
			item.setColors(Product2DAO.listItemColor(conn, itemCode));
			item.setSizes(Product2DAO.listItemSize(conn, itemCode));

		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}
		return item;
	}

	public static int filloItemPrice(Connection conn, ItemInfo item)
			throws Exception {
		CallableStatement cstmt = null;
		String sp = null;

		sp = "{?=call F_GET_SKU_PRICE(?,?,?) }";
		cstmt = conn.prepareCall(sp);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.setInt(2, item.getSku_id());
		cstmt.registerOutParameter(3, java.sql.Types.DOUBLE);
		cstmt.registerOutParameter(4, java.sql.Types.DOUBLE);
		cstmt.execute();
		int ret = cstmt.getInt(1);
		item.setSilverPrice(cstmt.getDouble(3));
		item.setGoldenPrice(cstmt.getDouble(4));
		item.setIsDiscount(ret);

		cstmt.close();
		return 0;
	}

	public static int filloGroupItemPrice(Connection conn, ItemInfo item)
			throws Exception {
		CallableStatement cstmt = null;
		String sp = null;

		sp = "{?=call F_GET_SKU_PRICE_GROUP(?,?,?) }";
		cstmt = conn.prepareCall(sp);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.setInt(2, item.getSku_id());
		cstmt.registerOutParameter(3, java.sql.Types.DOUBLE);
		cstmt.registerOutParameter(4, java.sql.Types.DOUBLE);
		cstmt.execute();
		int ret = cstmt.getInt(1);
		item.setSilverPrice(cstmt.getDouble(3));
		item.setGoldenPrice(cstmt.getDouble(4));

		cstmt.close();
		return 0;
	}

	public static int fillnItemPrice(Connection conn, ItemInfo item, String msc)
			throws Exception {

		String sp = "{?=call F_GET_SKU_PRICE_MSC(?,?,?,?) }";
		CallableStatement cs = conn.prepareCall(sp);
		cs.setString(2, msc);
		cs.setInt(3, item.getSku_id());

		cs.registerOutParameter(1, Types.DOUBLE);
		cs.registerOutParameter(4, Types.DOUBLE);
		cs.registerOutParameter(5, Types.DOUBLE);
		cs.execute();
		int ret = cs.getInt(1);
		item.setSilverPrice(cs.getDouble(4));
		item.setGoldenPrice(cs.getDouble(5));
		item.setIsDiscount(ret);
		
		cs.close();

		return 0;
	}

	public static int fillGroupItem(Connection conn, ItemInfo item)
			throws Exception {

		PreparedStatement ps;
		try {

			String sql = "select t1.sku_id,standard_price,itm_cost, "
					+ " nvl(t2.use_qty - t2.frozen_qty,0 ) availqty, "
					+ " nvl(t2.use_qty - t2.frozen_qty + t1.enable_os*t1.os_qty,0) availqty2 "
					+ " from prd_item_sku t1 left join sto_stock t2 on t1.sku_id = t2.sku_id"
					+ " where itm_code=? and color_code = ? and size_code =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, item.getItemCode());
			ps.setString(2, item.getColor_code());
			ps.setString(3, item.getSize_code());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				item.setSku_id(rs.getInt("sku_id"));
				item.setStandardPrice(rs.getDouble("standard_price"));
				item.setDiscountPrice(item.getStandardPrice());
				item.setPurchaseingCost(rs.getDouble("itm_cost"));
				item.setItem_cost(rs.getDouble("itm_cost"));
				item.setAvailQty(rs.getInt("availqty"));
				item.setAvailQty2(rs.getInt("availqty2"));
			}
			rs.close();
			ps.close();

			// 设置库存状态
			if (item.getAvailQty() >= item.getItemQty()) {
				item.setStockStatusId(0);
				item.setStockStatusName("库存正常");
			} else if (item.getAvailQty2() >= item.getItemQty()) {
				item.setIs_pre_sell(1);
				item.setStockStatusName("虚拟库存");
			} else {
				if (item.isLastSell()) {
					item.setStockStatusName("永久缺货");
				} else {
					item.setStockStatusName("暂时缺货");
				}
			}

			// item.setSizes(ProductBaseDAO.listSize(conn, item.getItemCode()));
			if ("".equals(item.getSet_code())) {
				filloGroupItemPrice(conn, item);
			}
			// item.setGroupItemMomey(item.getItemQty()*item.)

		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}
		return 0;
	}

	//检查商品库存
	public static int fillItem(Connection conn, ItemInfo item) throws Exception {

		PreparedStatement ps;
		try {

			String sql = "select t1.sku_id,standard_price,itm_cost,max_count, t3.name, "
					+ " nvl(t2.use_qty - t2.frozen_qty,0) availqty, "
					+ " nvl(t2.use_qty - t2.frozen_qty + t1.enable_os*t1.os_qty,0) availqty2 "
					+ " from prd_item_sku t1 left join sto_stock t2 on t1.sku_id = t2.sku_id "
					+ " left join prd_item_color t3 on t1.color_code = t3.code "
					+ " where itm_code=? and color_code = ? and size_code =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, item.getItemCode());
			ps.setString(2, item.getColor_code());
			ps.setString(3, item.getSize_code());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				item.setSku_id(rs.getInt("sku_id"));
				item.setColor_name(rs.getString("name"));
				item.setStandardPrice(rs.getDouble("standard_price"));
				item.setPurchaseingCost(rs.getDouble("itm_cost"));
				item.setItem_cost(rs.getDouble("itm_cost"));
				item.setAvailQty(rs.getInt("availqty"));
				item.setMax_count(rs.getInt("max_count"));
				item.setAvailQty2(rs.getInt("availqty2"));

				// 设置库存状态
				if (item.getAvailQty() >= item.getItemQty()) {
					item.setStockStatusId(0);
					item.setStockStatusName("库存正常");
				} else if (item.getAvailQty2() >= item.getItemQty()) {
					item.setIs_pre_sell(1);
					item.setStockStatusName("虚拟库存");
				} else {
					if (item.isLastSell()) {
						item.setStockStatusName("永久缺货");
					} else {
						item.setStockStatusName("暂时缺货");
					}
				}
			} else {
				item.setSku_id(0);
				item.setStockStatusName("未确定");
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}

		if (item.getSku_id() <= 0) {
			return -1;
		}
		return 0;
	}
	/**
	 * 修改锁定库存的值
	 * addedQty：+新增，-减少
	 * 
	 */
	public static int modifyFrozenItem(Connection conn, ItemInfo resultItem, int frozenQty, int addedQty) throws Exception
	{
		
		log.info("modifyFrozenItem:");
		String sql = "select t1.sku_id,standard_price,itm_cost,max_count,  "
			+ " nvl(t2.use_qty - t2.frozen_qty,0) availqty, "
			+ " nvl(t2.use_qty - t2.frozen_qty + t1.enable_os*t1.os_qty,0) availqty2 "
			+ " from prd_item_sku t1 left join sto_stock t2 on t1.sku_id = t2.sku_id "
			+ " where t2.sku_id=?";
		PreparedStatement ps;
		try
		{			
			ps = conn.prepareStatement(sql);
			ps.setString(1, resultItem.getFrozenItem());			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int availqty = rs.getInt("availqty");
				int availQty2 = (rs.getInt("availqty2"));
				int newQty = frozenQty + addedQty;
				
				resultItem.setStandardPrice(rs.getDouble("standard_price"));
				resultItem.setPurchaseingCost(rs.getDouble("itm_cost"));
				resultItem.setAvailQty(availqty - addedQty);
				resultItem.setAvailQty2(availQty2 - addedQty);
				resultItem.setItemQty(newQty);
				resultItem.setMax_count(rs.getInt("max_count"));
				log.info("skuid:"+ resultItem.getFrozenItem());
				log.info("availqty:" + availqty);
				log.info("addedQty:" + addedQty);
				// 变化后的库存状态依然>0
				if (availqty-addedQty>=0) {
					resultItem.setStockStatusId(0);
					resultItem.setStockStatusName("库存正常");
				} else if (availQty2-addedQty >= 0) {
					resultItem.setIs_pre_sell(1);
					resultItem.setStockStatusName("虚拟库存");
				} else {
					if (resultItem.isLastSell()) {
						resultItem.setStockStatusName("永久缺货");
					} else {
						resultItem.setStockStatusName("暂时缺货");
					}
				}
				
			} else {
				resultItem.setSku_id(0);
				resultItem.setStockStatusName("未确定");
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("exception", e);
			throw e;
		}
		return 0;
	}
	/**
	 * 取库存状态
	 * 
	 * @param db
	 * @param ii
	 *            取其商品ID作为查询条件 把库存状态存储其中
	 * @throws Exception
	 */
	public static void getStockStatus(Connection conn, ItemInfo item)
			throws Exception {
		int qty = -1;
		String sql = "";
		// 如果产品是套装,直接返回，如果
		if (item.isTruss() || item.getSku_id() == 0) {
			// sql = " select min(nvl(use_qty,0) - nvl(frozen_qty,0)) qty"
			// + " from sto_stock a, prd_item_sets b "
			// + " where b.set_sku_id = " + item.getSku_id()
			// + " and a.sto_no = '000' and a.sku_id = b.part_sku_id ";
			item.setStockStatusName("未确定");
			return;
		}

		sql = " select nvl(use_qty,0) - nvl(frozen_qty,0) qty"
				+ " from sto_stock " + " where sku_id = ?";

		// System.out.println(sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, item.getSku_id());

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			qty = rs.getInt("qty");
		}
		rs.close();
		ps.close();

		sql = "select nvl(os_qty,0) from prd_item_sku where sku_id=? and enable_os=1";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, item.getSku_id());
		int qty1 = 0;
		rs = ps.executeQuery();
		if (rs.next()) {
			qty1 = rs.getInt(1);
		}
		rs.close();
		ps.close();

		if (qty + qty1 >= item.getItemQty()) {
			// item.setStockStatusId(0);
			item.setStockStatusName("库存正常");
		} else {
			if (item.isLastSell()) {
				item.setStockStatusName("永久缺货");
			} else {
				item.setStockStatusName("暂时缺货");
			}
		}
	}

	public static void getAddressInfo(Connection conn, OrderForm order)
			throws Exception {
		String sql = " select a.*,b.name as payment_name,c.name as delivery_name"
				+ " from mbr_addresses a, s_payment_method b, s_delivery_type c"
				+ " where a.id = " + order.getReceiptorAddressId()

				+ " and a.pay_method = b.id(+) and a.delivery_type = c.id(+)";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			order.getOrgCart().getDeliveryInfo().setAddressId(rs.getInt("id"));
			order.getOrgCart().getDeliveryInfo().setReceiptor(
					rs.getString("relation_person"));
			order.getOrgCart().getDeliveryInfo().setPhone(
					rs.getString("telephone"));
			order.getOrgCart().getDeliveryInfo().setAddress(
					rs.getString("delivery_address"));
			order.getOrgCart().getDeliveryInfo().setPostCode(
					rs.getString("postcode"));
			order.getOrgCart().getDeliveryInfo().setDeliveryTypeId(
					rs.getInt("delivery_type"));
			order.getOrgCart().getDeliveryInfo().setDeliveryType(
					rs.getString("delivery_name"));
			order.getOrgCart().getDeliveryInfo().setPaymentTypeId(
					rs.getInt("pay_method"));
			order.getOrgCart().getDeliveryInfo().setPaymentType(
					rs.getString("payment_name"));
			order.getOrgCart().getDeliveryInfo().setSection(
					rs.getString("section"));
		}
		rs.close();
		st.close();

	}

	public static void insertOrder(Connection conn, OrderForm order)
			throws Exception {
		long orderId = insertOrderHeader(conn, order);
		order.setOrderId((int) orderId);
		insertOrderLine(conn, orderId, order.getOrgCart().getItems());
	}

	/**
	 * 插入团购订单头信息
	 * 
	 * @param conn
	 * @param order
	 * @return
	 * @throws Exception
	 */
	private static long insertOrderHeader(Connection conn, OrderForm order)
			throws Exception {

		long id = -1;
		String code = "";
		Statement st = conn.createStatement();
		ResultSet rs = st
				.executeQuery(" SELECT SEQ_ORD_HEADERS_ID.nextval,to_char(sysdate,'yymmdd')||seq_ord_number_id.nextval from dual");

		if (rs.next()) {
			id = rs.getLong(1);
			code = "G" + rs.getString(2);
		}
		rs.close();

		StringBuffer sql_buf = new StringBuffer(
				" INSERT INTO ORD_HEADERS(id,buyer_id,so_number,")
				.append(
						"pr_type,release_date,creator_id,modifier_id,oos_dispose,gift_number,postcode,address, ")
				.append(
						" phone,contact,delivery_type,delivery_fee,payment_method,is_invoice,")
				.append(
						" append_fee,goods_fee,order_sum,order_all_sum,remark,order_category, order_type,section,manual_free_freight,free_freight_reason ) values(")
				.append(id).append(",").append(
						order.getOrgCart().getOrgMember().getID()).append(",'")
				.append(code).append("',").append(
						order.getOrgCart().getOrgOrder().getPrTypeId()).append(
						",sysdate,").append(
						order.getOrgCart().getOrgOrder().getCreatorId())
				.append(",").append(
						order.getOrgCart().getOrgOrder().getCreatorId())
				.append(",").append(
						order.getOrgCart().getOtherInfo().getOOSPlan()).append(
						",");
		if (order.getTicketNumber() == null
				|| order.getTicketNumber().equals("")) {
			sql_buf.append("null");
		} else {
			sql_buf.append("'").append(order.getTicketNumber()).append("'");
		}
		sql_buf
				.append(",'")
				.append(order.getOrgCart().getDeliveryInfo().getPostCode())
				.append("','")
				.append(order.getOrgCart().getDeliveryInfo().getAddress())
				.append("','")
				.append(order.getOrgCart().getDeliveryInfo().getPhone())
				.append("','")
				.append(order.getOrgCart().getDeliveryInfo().getReceiptor())
				.append("',")
				.append(
						order.getOrgCart().getDeliveryInfo()
								.getDeliveryTypeId())
				.append(",")
				.append("0")
				.append(",")
				.append(// delivery_fee
						order.getOrgCart().getDeliveryInfo().getPaymentTypeId())
				.append(",").append(
						order.getOrgCart().getOtherInfo().getNeedInvoice())
				.append(",").append("0").append(",").append(// append_fee
						order.getOrgCart().getTotalMoney()).append(",").append(// goods_fee
						order.getOrgCart().getTotalMoney()).append(",")
				.append(// order_sum
						order.getOrgCart().getTotalMoney())
				// order_all_sum
				.append(",'").append(order.getRemark()).append("',").append(
						order.getOrgCart().getOrgOrder().getCategoryId())
				.append(",").append(15).append(",").append(
						order.getOrgCart().getDeliveryInfo().getSection())
				.append(",").append(order.getManualFreeFreight()?1:0)
				.append(",").append(order.getFreeFreightReason())
				.append(")");

		log.info(sql_buf.toString());
		int i = st.executeUpdate(sql_buf.toString());
		st.close();
		order.setOrderNumber(code);
		return id;
	}

	public static double insertOrderLine(Connection conn, long orderId,
			Collection items) throws Exception {
		double goodsFee = 0;
		String sql = " INSERT INTO ORD_LINES(id,sku_id,quantity,price,order_id,sell_type,"
				+ " mbr_award_id,set_code) values(seq_ord_lines_id.nextval,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemInfo item = (ItemInfo) it.next();
			ps.setInt(1, item.getSku_id());
			ps.setInt(2, item.getItemQty());
			ps.setDouble(3, item.getDiscountPrice());
			ps.setLong(4, orderId);

			ps.setInt(5, item.getSellTypeId());
			if (item.getAwardId() > 0) {
				ps.setInt(6, item.getAwardId());
			} else {
				ps.setNull(6, Types.INTEGER);
			}
			ps.setString(7, item.getSet_code());
			ps.executeUpdate();

			goodsFee += item.getDiscountPrice() * item.getItemQty();
		}
		ps.close();

		return goodsFee;
	}

	public static int deleteOrderLines(Connection conn, long orderId)
			throws Exception {
		int ret = 0;
		String sql = " DELETE FROM ORD_LINES WHERE ORDER_ID = " + orderId;
		Statement st = conn.createStatement();
		ret = st.executeUpdate(sql);
		st.close();
		return ret;
	}

	/*
	 * public static double insertOrderLines(Connection conn, long orderId,
	 * Collection items) throws Exception { double goodsFee = 0; String sql =
	 * " insert into ord_lines(id,order_id,sku_id,price,quantity,sell_type,mbr_award_id) "
	 * + " values(seq_ord_lines_id.nextval," + orderId + ",?,?,?,?,?)";
	 * PreparedStatement ps = conn.prepareStatement(sql); Iterator it =
	 * items.iterator(); while (it.hasNext()) { ItemInfo item = (ItemInfo)
	 * it.next(); ps.setInt(1, item.getSku_id()); // ps.setDouble(2,
	 * item.getItemPrice()); ps.setDouble(2, item.getDiscountPrice());//
	 * modified by user ps.setInt(3, item.getItemQty()); ps.setInt(4,
	 * item.getSellTypeId()); if (item.getAwardId() > 0) { ps.setInt(5,
	 * item.getAwardId()); } else { ps.setNull(5, Types.INTEGER); } // goodsFee
	 * += item.getItemPrice() item.getItemQty(); goodsFee +=
	 * item.getDiscountPrice() item.getItemQty(); ps.executeUpdate(); }
	 * ps.close(); return goodsFee; }
	 */

	/**
	 * 更新订单
	 * 
	 * @param conn
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static int updateOrderHeader(Connection conn, OrderForm order)
			throws Exception {
		int ret = 0;
		String sql = "update ord_headers set gift_number=?,append_fee=?,postcode=?,address=?,"
				+ "phone=?,payment_method=?, contact=?,delivery_type=?,delivery_fee=?,"
				+ "goods_fee=?,order_sum=?,remark=?,oos_dispose=?,is_invoice=?,status=?,"
				+ "modifier_id=?, order_type=?, phone1=?, package_fee = ?,package_type=?,"
				+ "invoice_title=?,is_use_dpt=?,pay_discount=?,discount_fee = ?,section=?, "
				+ "normal_fee=?,bank_id=?,id_card=?,ef_year=?,ef_month=?,ver_code=?,manual_free_freight=?,free_freight_reason=? where id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, "-1");// MODIFY BY user 2006-12-14 18:40
		ps.setDouble(2, -order.getCart().getTicketKill());
		ps.setString(3, order.getCart().getDeliveryInfo().getPostCode());
		ps.setString(4, order.getCart().getDeliveryInfo().getAddress());
		ps.setString(5, order.getCart().getDeliveryInfo().getPhone());
		ps.setInt(6, order.getCart().getDeliveryInfo().getPaymentTypeId());
		ps.setString(7, order.getCart().getDeliveryInfo().getReceiptor());
		ps.setInt(8, order.getCart().getDeliveryInfo().getDeliveryTypeId());
		ps.setDouble(9, order.getCart().getDeliveryInfo().getDeliveryFee());
		ps.setDouble(10, order.getCart().getTotalMoney());
		ps.setDouble(11, order.getCart().getTotalMoney()
				- order.getCart().getTicketKill()
				+ order.getCart().getDeliveryInfo().getDeliveryFee());
		ps.setString(12, order.getCart().getOtherInfo().getRemark());
		ps.setInt(13, order.getCart().getOtherInfo().getOOSPlan());
		ps.setString(14, order.getCart().getOtherInfo().getNeedInvoice());
		ps.setInt(15, order.getCart().getOrder().getStatusId());
		ps.setLong(16, order.getCart().getOrder().getCreatorId());
		ps.setInt(17, order.getCart().isPreSellOrder() ? 5 : 10);
		ps.setString(18, order.getCart().getDeliveryInfo().getPhone2());
		ps.setDouble(19, order.getCart().getPackageFee());
		ps.setInt(20, order.getCart().getPackageType());
		ps.setString(21, order.getCart().getOtherInfo().getInvoice_title());
		ps.setInt(22, order.getUse_deposit());
		ps.setDouble(23, order.getCart().getOtherInfo().getPaydiscount());
		ps.setDouble(24, -order.getCart().getDiscount_fee());
		ps.setString(25,order.getCart().getDeliveryInfo().getSection());
		ps.setDouble(26, order.getCart().getNormalSaleMoney());
		ps.setString(27, order.getCart().getOtherInfo().getCredit_card());
		ps.setString(28, order.getCart().getOtherInfo().getId_card());
		ps.setInt(29, order.getCart().getOtherInfo().getEf_year());
		ps.setInt(30, order.getCart().getOtherInfo().getEf_month());
		ps.setString(31, order.getCart().getOtherInfo().getVer_code());
		ps.setInt(32,order.getManualFreeFreight()?1:0);
		ps.setString(33,order.getFreeFreightReason());
		ps.setLong(34, order.getOrderId());

		ret = ps.executeUpdate();
		ps.close();
		return ret;
	}

	/**
	 * 更新团购订单
	 * 
	 * @param conn
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static int updateGroupOrderHeader(Connection conn, OrderForm order)
			throws Exception {
		int ret = 0;
		String sql = "update ord_headers set gift_number=?,append_fee=?,postcode=?,address=?,"
				+ "phone=?,payment_method=?, contact=?,delivery_type=?,delivery_fee=?,"
				+ "goods_fee=?,order_sum=?,remark=?,oos_dispose=?,is_invoice=?,status=?,modifier_id=? where id=?";
		PreparedStatement ps = conn.prepareStatement(sql);

		// order infomation
		ps.setString(1, "-1");// MODIFY BY user 2006-12-14 18:40
		ps.setDouble(2, 0);
		ps.setDouble(9, 0);
		ps.setDouble(10, order.getOrgCart().getTotalMoney());
		ps.setDouble(11, order.getOrgCart().getTotalMoney());
		ps.setInt(15, order.getOrgCart().getOrgOrder().getStatusId());
		ps.setLong(16, order.getOrgCart().getOrgOrder().getCreatorId());

		// delivery infomation
		ps.setString(3, order.getOrgCart().getDeliveryInfo().getPostCode());
		ps.setString(4, order.getOrgCart().getDeliveryInfo().getAddress());
		ps.setString(5, order.getOrgCart().getDeliveryInfo().getPhone());
		ps.setInt(6, order.getOrgCart().getDeliveryInfo().getPaymentTypeId());
		ps.setString(7, order.getOrgCart().getDeliveryInfo().getReceiptor());
		ps.setInt(8, order.getOrgCart().getDeliveryInfo().getDeliveryTypeId());

		// other infomation
		ps.setString(12, order.getOrgCart().getOtherInfo().getRemark());
		ps.setInt(13, order.getOrgCart().getOtherInfo().getOOSPlan());
		ps.setString(14, order.getOrgCart().getOtherInfo().getNeedInvoice());

		ps.setLong(17, order.getOrderId());
		ret = ps.executeUpdate();
		ps.close();
		return ret;
	}

	/*
	 * 判断订单号是否存在 add by jackey
	 */
	public Member getOrderNum(Connection conn, String orderid)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member info = new Member();
		try {
			// String ardidSql = "select id,BUYER_ID from ord_headers where
			// so_number='"+orderid+"' and (status=0 or status=15)";
			String ardidSql = "select a.id,b.card_id from ord_headers a ,mbr_members b where a.so_number='"
					+ orderid
					+ "' and (a.status=0 or a.status=15) and a.buyer_id=b.id";
			pstmt = conn.prepareStatement(ardidSql);
			// System.out.println(ardidSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setID(rs.getInt(1));
				info.setCARD_ID(rs.getString(2));
			}

		} catch (SQLException e) {
			System.out.println("getOrderNum error");
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
		return info;
	}

	/**
	 * 得到订单邮包信息
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static void getPostPackage(Connection conn, OrderForm order)
			throws Exception {
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int packageCategory = 0;
		int shipId = 0;
		int boundVar = 0;
		try {
			sql = "select package_category, id from ord_shippingnotices where ref_order_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order.getOrderId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				packageCategory = rs.getInt("package_category");
				shipId = rs.getInt("id");
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
		try {
			if (packageCategory == 0) { // 普通发货单
				sql = " SELECT * FROM JXC.STO_POST WHERE BARCODE = (SELECT BARCODE FROM ORD_SHIPPINGNOTICES "
						+ "WHERE shippingnotices_category = 0 AND REF_ORDER_ID = ? )";
				boundVar = order.getOrderId();
			} else if (packageCategory == -1) { // 子发货单
				sql = " SELECT * FROM JXC.STO_POST WHERE BARCODE = (select barcode from ord_shippingnotices "
						+ "where id = (select parent_ship_id from ord_ship_sets where child_ship_id = ?)) ";
				boundVar = shipId;
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boundVar);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				order.setPostPackageCode(rs.getString("post_num"));
				order.setPostPackageDate(rs.getString("post_day"));
				order.setPostPackageFee(rs.getString("post_fee"));
				order.setPostPackageWeight(rs.getString("post_weight"));
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
	}

	/**
	 * 得到换货单邮包信息
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static void getChangedPostPackage(Connection conn, OrderForm order)
			throws Exception {
		String sql = " SELECT * FROM JXC.STO_POST WHERE BARCODE = (SELECT BARCODE FROM ORD_SHIPPINGNOTICES WHERE shippingnotices_category = 1 AND REF_ORDER_ID = "
				+ order.getOrderId() + ")";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			order.setPostPackageCode(rs.getString("post_num"));
			order.setPostPackageDate(rs.getString("post_day"));
			order.setPostPackageFee(rs.getString("post_fee"));
			order.setPostPackageWeight(rs.getString("post_weight"));
		}
		rs.close();
		st.close();

	}

	/**
	 * 得到补货单邮包信息
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static void getSupplyPostPackage(Connection conn, OrderForm order)
			throws Exception {
		String sql = " SELECT * FROM JXC.STO_POST WHERE BARCODE = (SELECT BARCODE FROM ORD_SHIPPINGNOTICES WHERE shippingnotices_category = 2 AND REF_ORDER_ID = "
				+ order.getOrderId() + ")";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			order.setPostPackageCode(rs.getString("post_num"));
			order.setPostPackageDate(rs.getString("post_day"));
			order.setPostPackageFee(rs.getString("post_fee"));
			order.setPostPackageWeight(rs.getString("post_weight"));
		}
		rs.close();
		st.close();

	}

	/**
	 * 得到订单list
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static ArrayList queryOrderList(Connection conn, String sql,
			int from, int to) throws Exception {
		ArrayList ret = new ArrayList();
		String sql1 = " SELECT * FROM ( SELECT t.*, rownum rownum_ FROM ( "
				+ sql + " )t WHERE rownum <= ?) B WHERE rownum_ >? ";
		// System.out.println(sql1);
		PreparedStatement ps = conn.prepareStatement(sql1);
		ps.setInt(1, to);
		ps.setInt(2, from);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			// OrderForm order = new OrderForm();
			Order order = new Order();
			order.setOrderId(rs.getInt("id"));
			order.setOrderNumber(rs.getString("so_number"));
			order.setPayable(rs.getDouble("order_sum"));
			// order.setTotalMoney(rs.getDouble("goods_fee")) ;
			order.setGoodsFee(rs.getDouble("goods_fee"));
			order.setCategoryId(rs.getInt("order_category"));
			// order.setMbId(rs.getInt("buyer_id"));
			// order.setCardId(rs.getString("card_id"));
			// order.setMbName(rs.getString("mb_name"));
			order.getMember().setID(rs.getInt("buyer_id"));
			order.getMember().setCARD_ID(rs.getString("card_id"));
			order.getMember().setNAME(rs.getString("mb_name"));
			order.setCategoryName(rs.getString("category_name"));
			order.setPrTypeName(rs.getString("pr_type_name"));
			order.setStatusName(rs.getString("status_name"));
			order.setCreatorName(rs.getString("creator_name"));
			order.setCreateDate(rs.getString("release_date"));
			order.setMbPayable(rs.getDouble("order_sum")
					- rs.getDouble("payed_money") - rs.getDouble("payed_emoney"));

			ret.add(order);
		}
		rs.close();
		ps.close();
		return ret;
	}

	/**
	 * 得到订单list的总条数
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static int queryOrderListCount(Connection conn, String sql)
			throws Exception {
		int ret = 0;
		String sql1 = " SELECT COUNT(1) "
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
	 * 得到订单退包原因
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static void getReturnReason(Connection conn, OrderForm order)
			throws Exception {
		String sql = " select RR_DESC FROM JXC.RETURN_REASONS WHERE rr_no = "
				+ " ( SELECT RR_NO FROM jxc.sto_rk_mst where rk_calss='R' and pur_no = "
				+ "	( select id from ord_shippingnotices where shippingnotices_category = 0 and ref_order_id = "
				+ order.getOrderId() + "))";
		// System.out.println(sql);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			order.setRetReason(rs.getString("RR_DESC"));
		}
		rs.close();
		st.close();

	}

	/**
	 * 得到订单换货包原因
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static void getChangeReason(Connection conn, OrderForm order)
			throws Exception {
		String sql = " select RR_DESC FROM JXC.RETURN_REASONS WHERE rr_no = "
				+ " ( SELECT RR_NO FROM jxc.sto_rk_mst where rk_calss='T' and pur_no = "
				+ "	( select id from ord_shippingnotices where shippingnotices_category = 0 and ref_order_id = "
				+ order.getOrderId() + "))";
		// System.out.println(sql);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			order.setRetReason(rs.getString("RR_DESC"));
		}
		rs.close();
		st.close();

	}

	/**
	 * 是否有补货单
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static void isSupply(Connection conn, OrderForm order)
			throws Exception {
		String sql = "select id from ord_shippingnotices where shippingnotices_category = 2 and ref_order_id = "
				+ order.getOrderId();
		// System.out.println(sql);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			order.getCart().getOrder().setSupply(true);
		}

		rs.close();
		st.close();

	}

	/**
	 * 是否有换货单
	 * 
	 * @param conn
	 * @param order
	 * @throws Exception
	 */
	public static void isChanged(Connection conn, OrderForm order)
			throws Exception {
		String sql = " select RR_DESC FROM JXC.RETURN_REASONS WHERE rr_no in "
				+ " ( SELECT RR_NO FROM jxc.sto_rk_mst where rk_calss='T' and pur_no in "
				+ "	( select id from ord_shippingnotices where shippingnotices_category = 0 and ref_order_id = "
				+ order.getOrderId() + "))";
		// System.out.println(sql);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			order.getCart().getOrder().setChanged(true);
		}

		rs.close();
		st.close();

	}

	public static boolean isPostCodeDelivery(Connection conn, String postCode)
			throws Exception {

		int i = 0;
		String sql = "select is_express from s_postcode where postcode = "
				+ postCode.substring(0, 4);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			i = rs.getInt("is_express");
		}
		rs.close();
		st.close();
		return i > 0;
	}

	/**
	 * 检查定单有效状态
	 * 
	 * @param conn
	 * @param order
	 * @return 0-正常;-1-订单不存在;-2订单的状态不能充值
	 * @throws Exception
	 */
	public static int validateOrderNumber(Connection conn, OrderForm order)
			throws Exception {
		int ret = 0;
		String sql = " SELECT id,buyer_id,status from ord_headers where so_number = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, order.getOrderNumber());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int status = rs.getInt("status");
			if (status == 0 || status == 15) {
				order.setOrderId(rs.getInt("id"));
				order.setMbId(rs.getInt("buyer_id"));
			} else {
				ret = -2;
			}
		} else {
			ret = -1;
		}
		rs.close();
		ps.close();
		return ret;
	}

	/**
	 * 订单充值
	 * 
	 * @param conn
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static int payOrder(Connection conn, OrderForm order, int inValue)
			throws Exception {
		int ret = 0;
		CallableStatement cstmt = null;
		String sp = null;

		sp = "{?=call member.f_member_add_money(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		cstmt = conn.prepareCall(sp);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.setString(2, order.getOrderNumber());
		cstmt.setString(3, order.getCardId());
		cstmt.setDouble(4, Double.parseDouble(order.getMbMoney()));
		cstmt.setInt(5, order.getPaymentTypeId());
		cstmt.setString(6, "");
		cstmt.setInt(7, order.getCreatorId());
		cstmt.setString(8, order.getRemark());
		cstmt.setInt(9, order.getAuto_Level());
		cstmt.setInt(10, order.getAuto_Increase());
		cstmt.setInt(11, order.getAuto_Gift());
		cstmt.setString(12, "");
		cstmt.setInt(13, order.getReason());
		cstmt.setInt(14, 1);

		cstmt.execute();
		cstmt.close();

		/** **************以上是手工充值新代码************************** */
		if (inValue == 0) {
			// step3 运行相应的订单
			CallableStatement cstmt1 = conn
					.prepareCall("{?=call service.f_order_run(?,0)}");
			cstmt1.setInt(2, order.getOrderId());
			cstmt1.registerOutParameter(1, java.sql.Types.INTEGER);

			cstmt1.execute();
			ret = cstmt1.getInt(1);
			cstmt1.close();
		}
		return ret;

	}

	public static int payEmoney(Connection conn, OrderForm order, int inValue)
			throws Exception {
		int ret = 0;
		CallableStatement cstmt = null;
		String sp = null;

		sp = "{?=call member.f_member_add_emoney(?,?,?,?,?,?,?,?,?)}";
		cstmt = conn.prepareCall(sp);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.setString(2, order.getCardId());
		cstmt.setDouble(3, Double.parseDouble(order.getMbMoney()));
		cstmt.setInt(4, order.getPaymentTypeId());
		cstmt.setString(5, "");
		cstmt.setInt(6, order.getCreatorId());
		cstmt.setString(7, order.getRemark());
		cstmt.setString(8, "");
		cstmt.setInt(9, order.getReason());
		cstmt.setInt(10, 1);
		cstmt.execute();
		cstmt.close();
		ret = cstmt.getInt(1);
		return ret;

	}

	/**
	 * 使用充值卡充值
	 * @param conn
	 * @param order
	 * @param inValue
	 * @return
	 * @throws Exception
	 */
	public static int payCrushCard(Connection conn, OrderForm order)
			throws Exception {
		int ret = 0;
		CallableStatement cstmt = null;
		String sp = null;

		sp = "{?=call member.f_crush_member_money (?,?,?)}";
		cstmt = conn.prepareCall(sp);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.setString(2, order.getCardId());
		cstmt.setString(3, order.getRemark());
		cstmt.setInt(4, order.getCreatorId());
		cstmt.execute();
		cstmt.close();
		ret = cstmt.getInt(1);
		return ret;

	}

	public static ArrayList listKeyValue(Connection conn, String id)
			throws Exception {
		ArrayList array = new ArrayList();
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		int roleid = 0;
		try {
			String rolesql = "select roleid from crm_userrole  where id=" + id;
			st = conn.createStatement();
			rs = st.executeQuery(rolesql);
			while (rs.next()) {
				roleid = rs.getInt(1);
				// roleid=32客服手工充值
				if (roleid == 32) {
					sql = " SELECT id,name FROM S_PAYMENT_METHOD where ref_dept=2 order by name";
				}
				// roleid=33财务手工充值
				if (roleid == 33 || roleid == 64) {

					sql = " SELECT id,name FROM S_PAYMENT_METHOD  order by name";
				}
				if (roleid == 3 || roleid == 5) {
					sql = "select id,name from s_payment_method where id in (10,110)";
				}
			}
			rs.close();
			st.close();

			// 其他人只能使用内部充值
			if (sql == null || "".equals(sql)) {
				sql = " SELECT id, name FROM S_PAYMENT_METHOD where id=10 ";
			}
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				KeyValue keyvalue = new KeyValue();
				keyvalue.setId(rs.getInt("id"));
				keyvalue.setName(rs.getString("name"));
				array.add(keyvalue);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (st != null)
				try {
					st.close();
				} catch (Exception e) {
				}
		}
		return array;
	}

	/**
	 * 取消礼品
	 * 
	 * @param conn
	 * @param awardId
	 * @return
	 * @throws Exception
	 */
	public static int cancelMemberAward(Connection conn, long awardId)
			throws Exception {
		int ret = 0;
		String sql = " update MBR_GET_AWARD set status=-1 WHERE id = "
				+ awardId;
		Statement st = conn.createStatement();
		ret = st.executeUpdate(sql);
		st.close();
		return ret;
	}

	/**
	 * 得到会员可使用的礼券
	 * 
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	public static void getAvailabelTicket(Connection conn, OrderForm data)
			throws Exception {
		String sql = " select * from mbr_gift_ticket_use where mbrid= "
				+ data.getMbId() + " and total_num=1 and num=0 ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			data.setTicketNumber(rs.getString("ticket_num"));
		}
		rs.close();
		st.close();

	}

	/**
	 * 返回选择组的产品对应的礼品
	 * 
	 * @author Administrator(ysm) Created on 2005-10-26
	 */

	public static boolean getGroupGift(Connection conn, int sku_id,
			String item_code) throws Exception {
		Statement st = null;
		ResultSet rs = null;

		String sql = "";
		boolean code = false;

		sql = "  select  b.itemcode from promotion a,prom_gift b,prom_item c"
				+ " where a.id=b.promotionid and b.promotionid=c.promotionid "
				+ "and a.flag =3 and b.scope in (2,4,6,7) and c.itemid="
				+ sku_id + " and b.itemcode='" + item_code + "'";

		st = conn.createStatement();
		rs = st.executeQuery(sql);
		if (rs.next()) {
			code = true;
		}
		rs.close();
		st.close();
		return code;
	}

	/**
	 * 返回选择全部的产品对应的礼品
	 * 
	 * @author Administrator(ysm) Created on 2005-10-26
	 */

	public static boolean getItemGift(Connection conn, String item_code)
			throws Exception {
		Statement st = null;
		ResultSet rs = null;

		String sql = "";
		boolean rt = false;

		sql = "  select  b.itemcode from promotion a,prom_gift b"
				+ " where a.id=b.promotionid and a.flag =0 and b.scope in (2,4,6,7) ";

		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()) {
			if (item_code.equals(rs.getString(1))) {
				rt = true;
			}
		}
		rs.close();
		st.close();
		return rt;
	}

	/**
	 * 如果订单里有《99妈咪宝贝》的书，就返回1
	 * 
	 * @author Administrator(ysm) Created on 2005-12-8
	 */
	public static int getClubID(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();

		String sql = "";
		boolean rt = false;
		int clubid = 0;

		List lstItems = data.getItems();
		for (int i = 0; i < lstItems.size(); i++) {
			ItemInfo ii = (ItemInfo) lstItems.get(i);
			sql = "  select  clubid from prd_items where sku_id= "
					+ ii.getSku_id();
			db.queryDetailData(sql, wd, false);
			while (wd.next()) {
				if (wd.getDetailInt("clubid") == 1) {
					clubid = 1;
				}
			}
		}
		wd = null;
		return clubid;
	}

	/**
	 * 提交订单时，把这次暂时不要的礼品status=-1改成status=1
	 * 
	 * @author Administrator(ysm) Created on 2005-12-13
	 */

	public static void changeGiftStatus(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();

		String sql = "  update mbr_get_award set status=0 where status=-1 and member_id= "
				+ data.getMbId();
		db.executeUpdate(sql);
		wd = null;
	}

	/**
	 * public static Collection isGroupItem(Connection conn, OrderForm data)
	 * throws SQLException { PreparedStatement ps = null; ResultSet rs = null;
	 * Collection coll = new ArrayList(); String sql =
	 * "select a.* from prom_item a " +
	 * "inner join promotion b on a.promotionid = b.id " +
	 * "where sysdate >= b.begindate and sysdate < b.enddate + 1 " +
	 * "and b.validflag = 1 and b.flag = 3 " +
	 * "and a.itemcode = ? and a.flag = 1"; try { ps =
	 * conn.prepareStatement(sql); ps.setString(1, data.getItemCode()); rs =
	 * ps.executeQuery(); while (rs.next()) {
	 * 
	 * }
	 * 
	 * } catch (SQLException ex) { throw ex; } finally { if (rs != null) {
	 * rs.close(); } if (ps != null) { ps.close(); } } }
	 */

	/**
	 * 判断该促销礼品是否符合促销条件
	 * 
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static boolean ifGroupGift(Connection conn, OrderForm data)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rt = false;
		// 购物车中所有正常销售产品
		List lstItems = data.getCart().getItems();

		int prom_id = 0, type_id = 0, group_id = 0;
		double overx = 0.0;

		String sql = " select a.overx,b.id,b.flag,nvl(b.group_id,0) group_id from prom_gift a,promotion b "
				+ " where a.promotionid = b.id  "
				+ " and sysdate >= b.begindate and sysdate < b.enddate + 1 and a.validflag = 1  "
				+ " and a.id = ?";
		ps = conn.prepareStatement(sql);
		ps.setLong(1, data.getQueryAwardId());
		rs = ps.executeQuery();
		if (rs.next()) {
			prom_id = rs.getInt("id");
			type_id = rs.getInt("flag");
			group_id = rs.getInt("group_id");
			overx = rs.getDouble("overx");
		}
		rs.close();
		ps.close();

		// 如果是全场促销
		if (type_id == 1) {
			rt = true;
			// 如果是分类促销
		} else if (type_id == 2) {
			sql = "select count(1) from prd_item where  itm_code = ? and category_id "
					+ " in (select catalog_id from prd_item_category start with catalog_id=? "
					+ " connect by parent_id= prior catalog_id ) ";
			double price_sum = 0;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(2, group_id);
			for (int i = 0; i < lstItems.size(); i++) {
				ItemInfo jj = (ItemInfo) lstItems.get(i);
				pstmt.setString(1, jj.getItemCode());
				ResultSet rs1 = pstmt.executeQuery();
				if (rs1.next()) { // 购买了这个组中的产品
					rt = rs1.getInt(1) > 0 ? true : false;
					if (rt) {
						price_sum += jj.getItemMoney();
					}
				}
				rs1.close();

				pstmt.setString(1, jj.getSet_code());
				rs1 = pstmt.executeQuery();
				if (rs1.next()) { // 购买了这个组中的产品
					rt = rs1.getInt(1) > 0 ? true : false;
					if (rt) {
						price_sum += jj.getItemMoney();
					}
				}
				rs1.close();

			}
			pstmt.close();
			if (price_sum >= overx) {
				return true;
			} else {
				return false;
			}

			// 如果是组促销
		} else {
			// 是组礼品,然后判断是否已购买了这个组的产品

			sql = "select count(*) from prom_item where flag=1 and itemcode = ? and promotionid=?";
			double price_sum = 0;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(2, prom_id);
			for (int i = 0; i < lstItems.size(); i++) {
				ItemInfo jj = (ItemInfo) lstItems.get(i);
				pstmt.setString(1, jj.getItemCode());
				ResultSet rs1 = pstmt.executeQuery();
				if (rs1.next()) { // 购买了这个组中的产品
					rt = rs1.getInt(1) > 0 ? true : false;
					if (rt) {
						price_sum += jj.getItemMoney();
					}
				}
				rs1.close();

				pstmt.setString(1, jj.getSet_code());
				rs1 = pstmt.executeQuery();
				if (rs1.next()) { // 购买了这个组中的产品
					rt = rs1.getInt(1) > 0 ? true : false;
					if (rt) {
						price_sum += jj.getItemMoney();
					}
				}
				rs1.close();

			}
			pstmt.close();
			if (price_sum >= overx) {
				return true;
			} else {
				return false;
			}
		}

		return rt;
	}

	/**
	 * 礼品缺货更换同类礼品 @author Administrator(ysm) Created on 2006-2-20
	 * 
	 * @param conn
	 * @param sellTypeId
	 * @param awardId
	 * @param data
	 * @throws Exception
	 */
	public static void changeGift(Connection conn, int sellTypeId, int awardId,
			int new_awardId, OrderForm data) throws Exception {
		// 1:网站适用；2：CRM适用；3：门店适用；4：网站、CRM适用；5：网站、门店适用；6：CRM、门店适用
		// 注册送礼=7
		// 积分换礼=6
		// 预付款赠礼=15
		// 介绍人赠品=5
		// 礼品赠品=4
		String sql = "";
		String sell_name = "";
		if (sellTypeId == 4 || sellTypeId == 12) {
			sell_name = "礼品赠品";
			sql = " select b.sku_id,b.item_code,b.name,c.name,a.addy,b.clubid from prom_gift a,prd_items b,s_uom c where ";
			sql += " b.unit = c.id and a.itemid=b.sku_id  ";
			sql += " and a.scope in (2,4,6,7) and a.ValidFlag=1 and  a.itemid = "
					+ data.getOperateId() + " and a.id = " + new_awardId;
			// System.out.println(sql);
		}
		if (sellTypeId == 5) {
			sell_name = "介绍人赠品";
			sql = "select b.sku_id,b.item_code,b.name,c.name,a.price,b.clubid from mbr_get_mbr_gift a,prd_items b,s_uom c";
			sql += " where a.sku_id=b.item_code and b.unit = c.id ";
			sql += " and b.sku_id=" + data.getOperateId();
			// System.out.println(sql);
		}
		if (sellTypeId == 6) {
			sell_name = "积分换礼";
			sql = " select b.sku_id,b.item_code,b.name,c.name,a.exchange_price,b.clubid from mbr_exp_exchange_dtl a,prd_items b,s_uom c ";
			sql += " where a.sku_id=b.sku_id and b.unit = c.id ";
			sql += " and b.sku_id=" + data.getOperateId();
		}
		if (sellTypeId == 7) {
			sell_name = "注册送礼";
			sql = "select b.sku_id,b.item_code,b.name,c.name,a.addmoney,b.clubid from mbr_msc_gift a,prd_items b,s_uom c";
			sql += " where a.sku_id=b.item_code and b.unit = c.id ";
			sql += " and b.sku_id=" + data.getOperateId();

		}
		if (sellTypeId == 15) {
			sell_name = "预付款赠礼";
			sql = "select b.sku_id,b.item_code,b.name,c.name,b.clubid from mbr_money_gift a,prd_items b,s_uom c ";
			sql += " where a.sku_id=b.sku_id and b.unit = c.id ";
			sql += " and b.sku_id=" + data.getOperateId();

			// out.println(sql);
		}
		// add by user 2007-07-06
		if (sellTypeId == 17) { // 入会礼品
			sell_name = "入会送礼";
			sql = "select a.itemid, a.itemcode, b.name, c.name , a.price, b.clubid from recruit_activity_pricelist a "
					+ "inner join prd_items b on a.itemid=b.sku_id "
					+ "inner join s_uom c on b.unit=c.id "
					+ "and a.status = 1 and sysdate >=startdate and sysdate < enddate + 1 "
					+ "and a.itemid = "
					+ data.getOperateId()
					+ " and a.id = "
					+ new_awardId;
		}
		Statement st = conn.createStatement();
		Statement st1 = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			ItemInfo item = new ItemInfo();// getItemByCode(conn,itemCode);
			item.setSku_id(rs.getInt(1));
			item.setItemCode(rs.getString(2));
			item.setItemName(rs.getString(3));
			item.setItemUnit(rs.getString(4));
			item.setClubID(rs.getInt("clubid"));
			if (sellTypeId == 15) {
				item.setItemPrice(0);
			} else {

				item.setItemPrice(rs.getDouble(5));
			}
			item.setItemQty(1);
			item.setSellTypeId(sellTypeId);
			item.setSellTypeName(sell_name);
			item.setStockStatusId(0);// add by user 2008-05-13
			item.setStockStatusName("库存正常");

			item.setAwardId(awardId);

			st1.executeUpdate("update mbr_get_award set sku_id="
					+ item.getSku_id() + ",clubid=" + item.getClubID()
					+ " , price=" + item.getItemPrice() + " where id="
					+ awardId);
			data.getCart().getGifts().add(0, item);

		}

		st1.close();
		rs.close();
		st.close();

	}

	/**
	 * 积分兑换礼品
	 * 
	 * @param conn
	 * @param awardId
	 * @param data
	 * @throws Exception
	 */
	public static void addExchangeGift(Connection conn, String awardIds,
			OrderForm data) throws Exception {
		// String[] arr = awardIds.split(",");
		String sql = " select b.sku_id, b.item_code, b.name as item_name, c.name as unit_name, "
				+ " a.price, b.clubid,  "
				+ "a.used_amount_exp, a.id from mbr_get_award a, prd_items b, s_uom c "
				+ "where a.sku_id = b.sku_id "
				+ "and b.unit = c.id "
				+ " and a.id in (" + awardIds + ") ";

		PreparedStatement st = conn.prepareStatement(sql);

		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			ItemInfo item = new ItemInfo();
			item.setSku_id(rs.getInt("sku_id"));
			item.setItemCode(rs.getString("item_code"));
			item.setItemName(rs.getString("item_name"));
			item.setItemUnit(rs.getString("unit_name"));
			item.setClubID(rs.getInt("clubid"));
			item.setItemPrice(rs.getDouble("price"));
			item.setItemQty(1);
			item.setProm_level(rs.getDouble("used_amount_exp"));
			item.setSellTypeId(6);
			item.setSellTypeName("积分换礼");
			item.setAwardId(rs.getInt("id"));

			int nAvailableQty = getAvailableStockQty(new DBOperation(conn),
					item, data);
			if (nAvailableQty <= item.getItemQty()) { // 库存不足
				item.setStockStatusId(1);
				if (item.isLastSell())
					item.setStockStatusName("永久缺货");
				else
					item.setStockStatusName("库存不足");
			} else {
				item.setStockStatusId(0);
				if (nAvailableQty - item.getItemQty() < 10) {
					item.setStockStatusName("即将缺货");
				} else {
					item.setStockStatusName("库存正常");
				}
			}

			data.getCart().getGifts().add(0, item);
		}
		rs.close();
		st.close();

	}

	/**
	 * 钻兑换礼品 add by user 2008-04-23
	 * 
	 * @param conn
	 * @param awardId
	 * @param data
	 * @throws Exception
	 */
	public static void addDiamondGift(Connection conn, String awardIds,
			OrderForm data) throws Exception {

		String sql = " select b.sku_id, b.item_code, b.name as item_name, c.name as unit_name, "
				+ " a.price, b.clubid,  "
				+ "a.used_amount_exp, a.id from mbr_get_award a, prd_items b, s_uom c "
				+ "where a.sku_id = b.sku_id "
				+ "and b.unit = c.id "
				+ " and a.id in (" + awardIds + ") ";

		PreparedStatement st = conn.prepareStatement(sql);

		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			ItemInfo item = new ItemInfo();
			item.setSku_id(rs.getInt("sku_id"));
			item.setItemCode(rs.getString("item_code"));
			item.setItemName(rs.getString("item_name"));
			item.setItemUnit(rs.getString("unit_name"));
			item.setClubID(rs.getInt("clubid"));
			item.setItemPrice(rs.getDouble("price"));
			item.setItemQty(1);
			item.setProm_level(rs.getDouble("used_amount_exp"));
			item.setSellTypeId(19);
			item.setSellTypeName("九久钻");
			item.setAwardId(rs.getInt("id"));

			int nAvailableQty = getAvailableStockQty(new DBOperation(conn),
					item, data);
			if (nAvailableQty <= item.getItemQty()) { // 库存不足
				item.setStockStatusId(1);
				if (item.isLastSell())
					item.setStockStatusName("永久缺货");
				else
					item.setStockStatusName("库存不足");
			} else {
				item.setStockStatusId(0);
				if (nAvailableQty - item.getItemQty() < 10) {
					item.setStockStatusName("即将缺货");
				} else {
					item.setStockStatusName("库存正常");
				}
			}

			data.getCart().getGifts().add(0, item);
		}
		rs.close();
		st.close();

	}

	/**
	 * 根据定单行找到相应信息 add by user 206-08-15 :13:00
	 * 
	 * @param conn
	 * @param lineId
	 * @return
	 * @throws SQLException
	 */
	public static ItemInfo findOrderLineByPK(Connection conn, long lineId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemInfo info = null;
		String sql = "select * from ord_lines where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, lineId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info = new ItemInfo();
				info.setLineId(rs.getInt("ID"));
				info.setSku_id(rs.getInt("sku_id"));
				info.setItemQty(rs.getInt("QUANTITY"));
				info.setItemPrice(rs.getDouble("PRICE"));
				info.setStatus(rs.getString("STATUS"));
				info.setSellTypeId(rs.getInt("SELL_TYPE"));
				info.setPriceListLineId(rs.getInt("PRICELIST_LINE_ID"));
				info.setCommitment(rs.getInt("IS_COMMITMENT") == 1 ? true
						: false);
				info.setAwardId(rs.getInt("MBR_AWARD_ID"));
				info.setReplacerId(rs.getInt("REPLACE_sku_id"));
				info.setFrozenQty(rs.getInt("FROZEN_QTY"));
				info.setFrozenItem(rs.getString("FROZEN_ITEM"));
			}
			return info;
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * 更新相应的订单行 add by user 206-08-15 :13:00
	 * 
	 * @param conn
	 * @param lineId
	 * @return
	 * @throws SQLException
	 */
	public static void updateOrderLineByPK(Connection conn, ItemInfo item)
			throws SQLException {

		PreparedStatement pstmt = null;
		String sql = "update ord_lines set status = ?, comments = ? where id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getStatus());
			pstmt.setString(2, item.getComments());
			pstmt.setLong(3, item.getLineId());
			pstmt.execute();

		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * 更新相应的订单行 add by user 206-08-15 :13:00
	 * 
	 * @param conn
	 * @param lineId
	 * @return
	 * @throws SQLException
	 */
	public static void updateOrderLineStatusByFK(Connection conn, int status,
			int orderId) throws SQLException {

		PreparedStatement pstmt = null;
		String sql = "update ord_lines set status = ? where order_id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setInt(2, orderId);
			pstmt.execute();

		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * 得到某段时间内订单金额总和
	 * 
	 * @param conn
	 * @param lineId
	 * @return
	 * @throws SQLException
	 */
	public static double calcFinishedOrderMoneyByPeriod(Connection conn,
			java.sql.Date beginDate, java.sql.Date endDate, int memberId)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select sum(goods_fee) from ord_headers where release_date >= ? and release_date < ? + 1 and buyer_id = ?";
		double money = 0d;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, beginDate);
			pstmt.setDate(2, endDate);
			pstmt.setInt(3, memberId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				money = rs.getDouble(1);
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}
		return money;
	}

	/**
	 * 得到有效目录的期号
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static Collection getValidCatalogList(Connection conn)
			throws SQLException {
		Collection coll = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.periodical_id from prd_catalogs_basic a inner join prd_pricelists b on a.pricelist_id=b.id "
				+ "where b.price_type_id=3 and b.status=100 "
				+ "and sysdate <= expired_date and sysdate >= effect_date ";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			coll.add(new CodeName(null, "会员未提供"));
			while (rs.next()) {
				CodeName code = new CodeName(rs.getString("periodical_id"), rs
						.getString("periodical_id"));
				coll.add(code);
			}
		} catch (SQLException ex) {
			throw ex;
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

	/**
	 * 得到有效msc code
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList getValidMSCCode(Connection conn)
			throws SQLException {
		ArrayList coll = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select distinct msc from prd_pricelists  "
				+ "where status=100 "
				+ "and sysdate < expired_date+1 and sysdate >= effect_date ";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			coll.add(new CodeName("", "请选择"));
			while (rs.next()) {
				CodeName code = new CodeName(rs.getString("msc"), rs
						.getString("msc"));
				coll.add(code);
			}
		} catch (SQLException ex) {
			throw ex;
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

	/**
	 * 将订单和目录关联
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public static void insertUnionInfo(Connection conn, OrderForm data)
			throws SQLException {

		PreparedStatement pstmt = null;

		String sql = "insert into ord_union_order "
				+ "(id, order_number,is_pay, union_id, c_id, ad_id, liquan_money, order_money, is_first_union_order, commission, create_date) "
				+ "values (SEQ_ORD_ORDER_UNION_ID.nextval, ?, 3, '99read', ?, ?, 0, 0, null, 0, sysdate)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getOrderNumber() == null ? data.getCart()
					.getOrder().getOrderNumber() : data.getOrderNumber());
			pstmt
					.setString(
							2,
							(data.getCart().getOtherInfo().getCatalog() != null && !data
									.getCart().getOtherInfo().getCatalog()
									.equals("")) ? "DM" : null);
			pstmt.setString(3, data.getCart().getOtherInfo().getCatalog());
			pstmt.execute();

		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}

	}

	public static void updateRecruitMember(Connection conn, OrderForm data,
			int cnt) throws SQLException {

		if (!data.getCart().isRecruitProductInCart_M()) {
			return;
		}
		PreparedStatement pstmt = null;

		String sql = "update recruit_members set order_num = order_num + ? where msc = ? and mbr_id = ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, data.getMsc());
			pstmt.setInt(3, data.getCart().getMember().getID());

			pstmt.execute();

		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}

	}

	/**
	 * 修改订单的时候先删除后添加
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public static void deleteUnionInfo(Connection conn, OrderForm data)
			throws SQLException {

		PreparedStatement pstmt = null;

		String sql = "delete from ord_union_order where order_number= ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getCart().getOrder().getOrderNumber());
			pstmt.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}

	}

	/**
	 * 得到联盟信息
	 * 
	 * @param conn
	 * @param data
	 * @throws SQLException
	 */
	public static void viewUnionInfo(Connection conn, OrderForm data)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select ad_id from ord_union_order where order_number= ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getCart().getOrder().getOrderNumber());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				data.getCart().getOtherInfo().setCatalog(
						rs.getString("ad_id") == null ? "（会员未提供）" : rs
								.getString("ad_id"));
			} else {
				data.getCart().getOtherInfo().setCatalog("（会员未提供）");
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}

	}

	/**
	 * 查询个人未处理的订单
	 * 
	 * @param conn
	 * @param personId
	 * @return
	 * @throws SQLException
	 */
	public static Collection findUnfinishedOrderByPerson(Connection conn,
			int personId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.id, a.so_number, a.goods_fee, a.order_sum, "
				+ "a.status, b.name as status_name, "
				+ "a.delivery_type, c.name as delivery_type_name, a.order_category, "
				+ "a.pr_type, f.name as pr_type_name, d.name as order_category_name, "
				+ "a.order_type, e.name as order_type_name, a.release_date "
				+ "from ord_headers a left join s_order_status b on a.status = b.id "
				+ "left join s_delivery_type c on a.delivery_type = c.id "
				+ "left join s_order_category d on a.order_category = d.id "
				+ "left join s_order_type e on a.order_type = e.id "
				+ "left join s_pr_type f on a.pr_type = f.id "
				+ "where a.creator_id = ? and a.status in(-6, 15, 20, 21) and a.release_date >= sysdate - 90 order by a.release_date desc ";
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, personId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("id"));
				order.setOrderNumber(rs.getString("so_number"));
				order.setGoodsFee(rs.getDouble("goods_fee"));
				order.setPayable(rs.getDouble("order_sum"));
				order.setTotalMoney(rs.getDouble("order_sum"));
				order.setStatusName(rs.getString("status_name"));
				order.getDeliveryInfo().setDeliveryType(
						rs.getString("delivery_type_name"));
				order.setCategoryName(rs.getString("order_category_name"));
				order.setOrderTypeName(rs.getString("order_type_name"));
				order.setPrTypeName(rs.getString("pr_type_name"));
				order.setCreateDate(rs.getString("release_date"));
				coll.add(order);
			}
		} catch (SQLException ex) {
			throw ex;
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

	/**
	 * 产品缺货登记
	 * 
	 * @param conn
	 * @param sku_id
	 * @param operatorId
	 * @throws SQLException
	 */
	public static void registerOOS(Connection conn, int itemId, int operatorId)
			throws SQLException {

		PreparedStatement pstmt = null;
		String sql = "insert into ord_oos " + "(sku_id, op_time, operator) "
				+ "values(?, sysdate, ?) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			pstmt.setInt(2, operatorId);
			pstmt.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}

	}

	/**
	 * 判断订单号是否存在
	 * 
	 * @param conn
	 * @param orderCode
	 * @return
	 * @throws SQLException
	 */
	public boolean checkOrderCode(Connection conn, String orderCode)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		String sql = " select id from ord_headers where so_number = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderCode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return flag;
	}

}

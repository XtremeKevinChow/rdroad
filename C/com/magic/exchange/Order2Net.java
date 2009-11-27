package com.magic.exchange;

import org.apache.log4j.*;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.*;

import com.magic.crm.util.*;

/**
 * 更新网站订单内容
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */

public class Order2Net {
	private static Logger log = Logger.getLogger(Order2Net.class);
	private int success_count = 0;
	private int failed_count = 0;

	public void execute() {
		Connection conMs = null;
		Connection conOra = null;
		try {
			//log.debug("--start--" + new java.util.Date());
			conOra = DBManager2.getConnection();
			conMs = DBManagerMS.getConnection();
			//log.debug("--get connection --" + new java.util.Date());
			//String querysql = "select odrmst_orderstatus,odrmst_odrid,OdrMst_affirmdate,OdrMst_confirmdate from odrmst  where odrmst_orderstatus>=0 and odrmst_orderstatus<>5 and odrmst_orderstatus<>10 and OdrMst_confirmdate is not null and getDate()-OdrMst_confirmdate<30 and odrmst_change=0";
			String sql = "select * from ord_mst_temp ";
			Statement st = conOra.createStatement();
			ResultSet rs = st.executeQuery(sql);
			//log.debug("--query order header--" + new java.util.Date());
			while (rs.next()) {
				
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setSo_number(rs.getString("so_number"));
				orderInfo.setOrder_status(rs.getInt("status"));
				orderInfo.setOrdermoney(rs.getFloat("order_sum"));
				orderInfo.setOrder_id(rs.getInt("id"));
				try {
							
						//得到订单上所有的订单行
						queryOrderLine(conOra, orderInfo);
						//log.debug("--query order_line --" + new java.util.Date());

						//调用方法更新网站上的订单状态信息
						conMs.setAutoCommit(false);
						updateStatus(conMs,orderInfo);
						//log.debug("--update web  order status--" + new java.util.Date());
						updateOrderLine(conMs,orderInfo);
						//log.debug("--update web order line --" + new java.util.Date());
						conMs.commit();
						
						deleteOrder(conOra,orderInfo);
						success_count++;
						log.info("更新网上订单\t定单号" + orderInfo.getSo_number()
								+ "\t更新成功\t成功:" + success_count + "\t失败:"
								+ failed_count);
					} catch (Exception e) {
						
						failed_count++;
						log.info("更新网上订单\t定单号" + orderInfo.getSo_number()
								+ "\t更新失败\t成功:" + success_count + "\t失败:"
								+ failed_count);
						log.error("更新网上订单有误：" + "\t网上订单号更新失败",e);
					}
			
			}
			
			rs.close();
			st.close();

		} catch (Exception e) {
			log.error("exception",e);
		} finally {
			try {conOra.close();} catch (Exception e) {}
			try {conMs.close();} catch (Exception e) {}
		}
		
	}

	/**
	 * 判断一个订单号在ＣＲＭ系统中是否存在 如果存在，返回这个订单号对应的订单的状态信息 否则，返回-50值
	 * 
	 * @param args
	 */
	public int opinionByOrderId(Connection conn, String s) {
		Statement stmt = null;
		ResultSet rs = null;
		s = s.trim();
		int result = -50;
		try {
			String querysql = "select status from ord_headers  where so_number='"
					+ s + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querysql);
			if (rs.next())
				result = rs.getInt("status");
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (e != null) {
				String sqlState = "LocallizeMessage:" + e.getLocalizedMessage();
				String message = "Message:" + e.getMessage();
				String vendor = "Class:" + e.getClass();

				log.error(sqlState + message + vendor);

			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 判断一个订单号在ＣＲＭ系统中是否存在 如果存在，返回这个订单号对应的订单ID
	 * 
	 * @param args
	 */
	public int queryByOrderId(Connection conn, String s) {
		Statement stmt = null;
		ResultSet rs = null;
		s = s.trim();
		int result = -50;
		try {

			String querysql = "select * from ord_headers  where so_number='"
					+ s + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(querysql);

			while (rs.next()) {
				result = rs.getInt("id");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 更新网站上订单的状态信息
	 * 
	 * @param args
	 */
	public void updateStatus(Connection conn,OrderInfo o) throws Exception {
		Statement st = conn.createStatement();
		String updatesql = "";
		if (o.getOrder_status() >= 0) {
			updatesql = "update odrmst set odrmst_orderstatus="
					+ o.getOrder_status() + ",odrmst_ordermoney="
					+ o.getOrdermoney()
					+ ",OdrMst_cardprice=-1  where odrmst_odrid='"
					+ o.getSo_number().trim() + "'";
		} else {
			updatesql = "update odrmst set odrmst_orderstatus="
					+ o.getOrder_status()
					+ ",OdrMst_cardprice=-1 where odrmst_odrid='"
					+ o.getSo_number().trim() + "'";
		}
		
		st.executeUpdate(updatesql);
		st.close();
	}
	
	/**
	 * 删除更新过的订单
	 * 
	 * @param args
	 */
	public void deleteOrder(Connection conn,OrderInfo o) throws Exception {
		Statement st = conn.createStatement();
		st.executeUpdate("delete from ord_dtl_temp where order_id= " + o.getOrder_id());
		st.executeUpdate("delete from ord_mst_temp where id = " + o.getOrder_id());
		st.close();
	}

	/**
	 * 如果订单存在，则根据此订单号去查询到订单对应的行状态的信息
	 * 
	 * @param args
	 */
	public void queryOrderLine(Connection conn, OrderInfo order) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String querysql = "select a.item_id,a.id,a.order_id,nvl(a.web_id,0) as web_id,a.sell_type,a.status,a.price,a.quantity "
				+ ",b.item_code,b.name,b.standard_price "
				+ " from ord_dtl_temp a, prd_items b"
				+ " where a.item_id = b.item_id and a.order_id= "
					+ order.getOrder_id();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querysql);
			while (rs.next()) {
				OrderLine line = new OrderLine();
				line.setWebId(rs.getLong("web_id"));
				line.setItemId(rs.getLong("item_id"));
				line.setItemCode(rs.getString("item_code"));
				line.setItemName(rs.getString("name"));
				line.setStatus(rs.getInt("status"));
				line.setSellType(rs.getInt("sell_type"));
				line.setPrice(rs.getDouble("price"));
				line.setStand_price(rs.getDouble("standard_price"));
				line.setQty(rs.getInt("quantity"));
				order.lines.add(line);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			log.error("得到订单行信息出错",e);
			throw e;
		} 
	}

	/**
	 * 更新网上订单产品行
	 * 
	 * @param args
	 */
	public void updateOrderLine(Connection conn,OrderInfo od ) throws Exception {
		PreparedStatement ps = conn.prepareStatement("update odrdtl set odrdtl_shipstatus=? where odrdtl_subodrid = ? ");
		Statement st = conn.createStatement();
		Iterator it = od.lines.iterator();
		while (it.hasNext()) {
			OrderLine line = (OrderLine) it.next();
			try {
				ps.setInt(1,line.getStatus());
				ps.setLong(2,line.getWebId());
				//ps.setString(3,line.getItemCode());
				//ps.setInt(4,line.getSellType());
				int count = ps.executeUpdate();
				//如果没有则插入新的订单行
				if(count == 0) {
					StringBuffer updatesql = new StringBuffer();
					updatesql.append("insert into odrdtl (");
					updatesql
							.append("OdrDtl_odrid,OdrDtl_gdsid,OdrDtl_shopcode,OdrDtl_shopname,OdrDtl_gdsname,");
					updatesql
							.append("OdrDtl_saleprice,OdrDtl_memberprice,OdrDtl_gdscount,OdrDtl_totalmoney,");
					updatesql.append("odrdtl_saletype,");
					updatesql.append("OdrDtl_finishcount,odrdtl_shipstatus)");
					updatesql.append(" values ('");
					updatesql.append(od.getSo_number().trim()).append("',");
					updatesql.append("'").append(line.getItemCode().trim()).append(
							"',");
					updatesql.append("'19999999',");
					updatesql.append("'99书城',");
					updatesql.append("'").append(line.getItemName()).append("',");
					updatesql.append(line.getStand_price()).append(",");
					updatesql.append(line.getPrice()).append(",");
					updatesql.append(line.getQty()).append(",");
					updatesql.append(line.getPrice()*line.getQty()).append(",");
					updatesql.append(line.getSellType()).append(",");
					updatesql.append(line.getQty()).append(",");
					updatesql.append(line.getStatus());
					updatesql.append(")");
					st.executeUpdate(updatesql.toString());
				}
				
			} catch(Exception e) {
				log.error("更新订单行到网站出错",e);
				throw e;
			}
		}
		st.close();
		ps.close();
		
	}
	
	public static void main(String[] args) {
		PropertyConfigurator
		.configure("E:\\crm_service\\service\\schedule\\log4j.properties");
		
		new Order2Net().execute();
	}
}
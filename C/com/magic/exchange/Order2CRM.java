package com.magic.exchange;

import java.sql.*;
import java.sql.SQLException;
import java.util.*;
import com.magic.utils.*;
import com.magic.crm.util.*;

import org.apache.log4j.*;

/**
 * 网站订单同步到CRM系统
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class Order2CRM {
	private static Logger log = Logger.getLogger(Order2CRM.class);
	private int success_count = 0;
	private int failed_count = 0;
	
	//mapping
	private HashMap deliveryType_mapping = new HashMap();
	private HashMap paymentType_mapping = new HashMap();
	
	public void initMapping() {
		Connection conOra = null;
		try {
			conOra = DBManager2.getConnection();
			Statement st = conOra.createStatement();
			deliveryType_mapping.clear();
			ResultSet rs = st.executeQuery("select * from nsp_delivery_type ");
			while (rs.next()) {
				Integer crm_id = new Integer(rs.getInt("crm_id"));
				Integer net_id = new Integer(rs.getInt("nsp_id"));
				deliveryType_mapping.put(net_id,crm_id);
			}
			rs.close();
			paymentType_mapping.clear();
			rs = st.executeQuery("select * from nsp_payment_method ");
			while (rs.next()) {
				Integer crm_id = new Integer(rs.getInt("crm_id"));
				Integer net_id = new Integer(rs.getInt("nsp_id"));
				paymentType_mapping.put(net_id,crm_id);
			}
			rs.close();
			st.close();
			
		} catch(Exception e) {
			log.error("exception",e);
		} finally {
			try { conOra.close();}catch(Exception e){}
			
		}
		
	}
	
	public void execute() {
		initMapping();
		Connection conOra = null;
		Connection  conMs = null;
		try {
			conOra = DBManager2.getConnection();
			conMs = DBManagerMS.getConnection();
			Statement st = conMs.createStatement();
			PreparedStatement ps = conMs.prepareStatement("update odrmst set odrmst_change= 0 where odrmst_change=1 and rtrim(Ltrim(odrmst_odrid)) =?");
			String querysql_1 = "SELECT  odrmst.OdrMst_odrid, b2cmbr.b2cmbr_cardid, "
					+ "odrmst.OdrMst_mbrid, odrmst.OdrMst_confirmdate, "
					+ "odrmst.OdrMst_raddress, odrmst.OdrMst_rzipcode, "
					+ "odrmst.OdrMst_rname, odrmst.OdrMst_rphone, odrmst.OdrMst_shipid, "
					+ "odrmst.OdrMst_shipfee, odrmst.OdrMst_PayID,"
					+ "odrmst.OdrMst_InvoiceFlag, odrmst.OdrMst_gdsmoney, "
					+ "odrmst.OdrMst_liquanmoney, odrmst.OdrMst_ordermoney, "
					+ "odrmst.OdrMst_orderstatus, odrmst.OdrMst_membmemo, "
					+ "odrmst.OdrMst_IfWait, odrmst.OdrMst_createdate, "
					+ "odrmst.OdrMst_ticketid ticketid,odrmst_prepaymoney, "
					+ "odrmst.OdrMst_cardtype,odrmst.odrmst_bankpayment "
					+ "FROM odrmst INNER JOIN "
					+ "b2cmbr ON "
					+ "odrmst.OdrMst_mbrid = b2cmbr.b2cmbr_id "
					+ "WHERE (odrmst.OdrMst_orderstatus = 0) AND (odrmst.odrmst_change = 1)"
					+ "ORDER BY odrmst.OdrMst_createdate ";

			ResultSet rs = st.executeQuery(querysql_1);
			while (rs.next()) {
				try {

					OrderInfo orderInfo = new OrderInfo();
					String order_id = rs.getString("odrmst_odrid");//订单号
					String net_id = rs.getString("odrmst_mbrid");
					orderInfo.setSo_number(order_id);
					orderInfo.setBuyer_id(rs.getInt("odrmst_mbrid"));
					
					getCrmMemberId(conOra,orderInfo);
					
					log.info("定单会员号码:" + rs.getString("b2cmbr_cardid"));
					//orderInfo.setRelease_date(rs.getDate("odrmst_confirmdate"));//确认日期
					orderInfo.setRelease_date(rs.getDate("OdrMst_createdate")
							+ " " + rs.getTime("OdrMst_createdate"));//下订单日期
					orderInfo.setAddress(rs.getString("OdrMst_raddress"));//订单地址
					orderInfo.setPostcode(rs.getString("odrmst_rzipcode")
							.trim());//邮编
					orderInfo.setContact(rs.getString("odrmst_rname"));//订单人姓名
					orderInfo.setPhone(rs.getString("odrmst_rphone"));//联系电话
					
					orderInfo.setDelivery_type(
							((Integer)deliveryType_mapping.get(new Integer(rs.getInt("odrmst_shipid")))).intValue()
							);//订单的送货方式
					orderInfo.setDelivery_fee(rs.getFloat("odrmst_shipfee"));//发送费

					//调用方法进行处理
					orderInfo.setPayment_method(
							((Integer)paymentType_mapping.get(new Integer(rs.getInt("odrmst_payid")))).intValue()
							);//订单的付款方式

					//加入判断，当网站中odrmst_ifwait为０时，Oos_dispose为３
					//　　　　　当网站中Odrmst_ifwait为１时，Oos_dispose为２
					int ifwait = rs.getInt("odrmst_ifwait");
					if (ifwait == 0) {
						orderInfo.setOos_dispose(3);
					} else {
						orderInfo.setOos_dispose(2);
					}
					orderInfo.setOos_dispose(orderInfo.getOos_dispose());

					orderInfo.setIs_invoice(rs.getInt("OdrMst_InvoiceFlag"));//是否要发票
					orderInfo.setProduct_value(rs.getFloat("odrmst_gdsmoney"));//订单行总经额
					//传入订单的礼卷金额，为负数，保存在订单的附加费用里
					orderInfo.setAppendfee(-1
							* rs.getFloat("odrmst_liquanmoney"));//礼卷金额
					orderInfo.setOrder_value(rs.getFloat("odrmst_ordermoney"));//订单实际总经额
					orderInfo.setOrder_status(rs.getInt("odrmst_orderstatus"));//订单状态

					orderInfo.setOrder_memo(rs.getString("OdrMst_membmemo"));//订单备注
					orderInfo.setPrepayMoney(rs.getFloat("odrmst_prepaymoney"));
					orderInfo.setBank_pay(rs.getDouble("odrmst_bankpayment"));
					//通过getGiftTicketId()方法取得订单对应的礼卷号
					orderInfo.setGift_number(StringUtil.cEmpty(rs
							.getString("ticketid")));

					//调用一个方法,取得订单对应的产品的信息
					getOrderProductInfo(conMs,conOra,orderInfo);

					if (!(orderInfo.getItem_id().equals("")
							|| orderInfo.getQuantity().equals("")
							|| orderInfo.getPrice().equals("")
							|| orderInfo.getIs_commitment().equals("") || orderInfo
							.getSell_type().equals(""))) {
						//调用方法，处理订单信息前，先进行判断，如果订单行里有任何一个值为空的话，则跳过去
						//调用方法，处理会员的Card_type（更新）,传入两个参数，网站中的订单ID和网站网站会员ID
						conOra.setAutoCommit(false);
						updateMemberInfo(conOra,orderInfo);
						transactOrderInfo(conOra,orderInfo);
						
						ps.setString(1,orderInfo.getSo_number());
						ps.executeUpdate();
						
						success_count++;
						log.info("从网站订单同步到CRM系统,\t" + orderInfo.getSo_number()
								+ "处理成功,\t成功:" + success_count + "\t失败:"
								+ failed_count);
						conOra.commit();
						try {
							new SendMail().send(conOra,orderInfo.getOrder_id(),1);
						} catch(Throwable e) {
							log.error("发送订单确认信出错" + orderInfo.getOrder_id() ,e);
						}
					
					} else {

						log.error("订单导入有误：" + "\t订单产品中存在空值:" + "\t订单号："
								+ orderInfo.getSo_number());
						failed_count++;
						log.info("从网站订单同步到CRM系统,\t" + orderInfo.getSo_number()
								+ "处理失败,\t成功:" + success_count + "\t失败:"
								+ failed_count);
					}
					
				} catch (Exception e) {
					conOra.rollback();
					log.error("订单导入有误：",e);
					failed_count++;
					log.info("从网站订单同步到CRM系统,\t" + rs.getString("odrmst_odrid")
							+ "处理失败,\t成功:" + success_count + "\t失败:"
							+ failed_count);
				}
			}
			rs.close();
			st.close();
			ps.close();
			
		} catch (SQLException se) {
			log.error("exception",se);
		} finally {
			try {conOra.close();} catch(Exception e) {}
			try {conMs.close();} catch(Exception e) {}
		}
		
	}
	
	/**
	 * 处理订单中的产品信息
	 * 
	 * @param OrderInfo
	 */
	public void getOrderProductInfo(Connection conn,Connection conOra,OrderInfo orderInfo) throws Exception {
		Statement st = conn.createStatement();
		String s = orderInfo.getSo_number().trim();
		String line_id = "",item_id = "", quantity = "", price = "", is_commitment = "", sell_type = "",prom_line_id = "";
		String querysql = "select odrdtl_subodrid,odrdtl_saletype, odrdtl_gdsid,odrdtl_memberprice, odrdtl_gdscount,odrdtl_dutyflag,isnull(promotion_gift_id,0)as prom_line_id from odrdtl  where odrdtl_odrid='"
				+ s + "'";

		ResultSet rs = st.executeQuery(querysql);
		while (rs.next()) {
			//取出信息，重组成新的String
			//调用queryProductId()在CRM系统的prd_item表中找出产品对应的ID,构成字符串
			int i_str = this.queryProductId(conOra,(rs.getString("odrdtl_gdsid").trim()));
			if (i_str != -1) {
				item_id = item_id + i_str + ",";
				line_id = line_id + rs.getFloat("odrdtl_subodrid") + ",";
				price = price + rs.getFloat("odrdtl_memberprice") + ",";
				quantity = quantity + rs.getInt("odrdtl_gdscount") + ",";

				sell_type = sell_type + rs.getInt("odrdtl_saletype") + ",";
				is_commitment = is_commitment + rs.getInt("odrdtl_dutyflag")
						+ ",";
				prom_line_id = prom_line_id + rs.getInt("prom_line_id") + ",";
			} else {

				log.error("订单导入有误：" + "\t订单产品在CRM系统中不存在" + "\t订单号：" + s
						+ "\t货号：" + item_id);
				throw new Exception("订单产品在CRM系统中不存在,订单号：" + s + "\t货号："
						+ item_id);
			}
		}
		rs.close();
		st.close();
		orderInfo.setWeb_line_id(line_id);
		orderInfo.setItem_id(item_id);
		orderInfo.setQuantity(quantity);
		orderInfo.setPrice(price);
		orderInfo.setIs_commitment(is_commitment);
		orderInfo.setSell_type(sell_type);
		orderInfo.setPro_line_id(prom_line_id);
	}
	
	/**
	 * 根据网站系统中订单表中的订单号去订单对应的产品表中取得货号（货号唯一） 再根据货号在CRM系统中找出对应的产品ID
	 * 
	 * @param ss
	 *            产品编码
	 */
	public int queryProductId(Connection conn,String ss) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		int result = -1;
		ss = ss.trim();
		String querysql = "select item_id from prd_items where item_code='"
				+ ss + "'";
		rs = stmt.executeQuery(querysql);
		if (rs.next())
			result = rs.getInt("item_id");
		rs.close();
		stmt.close();
		return result;
	}
	
	/**
	 * 在导入订单信息之前 更新会员的信息,包括充值和会员卡类型
	 */
	public int updateMemberInfo(Connection conn,OrderInfo order)
			throws Exception {
		int ret=0; 
		//只有1 时才更新CRM系统中会员的Card_type，（CRM系统中默认值为0，所以为0时不必更新）其它值都不做更新操作
		/*if (order.getBank_pay() > 0 ) {
			//step1 给会员充值
			String sql1 = "update mbr_members set deposit = deposit + ? where id = ?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setDouble(1, order.getBank_pay());
			ps1.setLong(2, order.getMbr_id());
			ps1.executeUpdate();
			ps1.close();

			// step2 记录充值历史
			String sql2 = "insert into mbr_money_history(ID,MEMBER_ID,OPERATOR_ID,DEPOSIT,MONEY_UPDATE,COMMENTS,MODIFY_DATE,EVENT_TYPE,PAY_METHOD) "
					+ " values(seq_mbr_money_history.nextval,?,?,(select deposit from mbr_members where id = ? ),?,?,sysdate,2020,?)";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setLong(1, order.getMbr_id());
			ps2.setInt(2, 0);
			ps2.setLong(3, order.getMbr_id());
			ps2.setDouble(4, order.getBank_pay());
			ps2.setString(5, order.getSo_number());
			ps2.setInt(6,order.getPayment_method());
			ps2.executeUpdate();
			ps2.close();
		}*/ 
		
		if (order.getCard_type() == 1) {
			String updatesql = "update mbr_members set card_type=" + order.getCard_type()
					+ "  where netshop_id=" + order.getBuyer_id();
			Statement st = conn.createStatement();
			int i = st.executeUpdate(updatesql);
			if (i == 0) {
				log.error("订单导入出错：" + "\t该会员在网下不存在" + "\t会员网站ID："
						+ order.getBuyer_id());
				throw new Exception("订单导入出错：该会员在网下不存在,会员网站ID："
						+ order.getBuyer_id());
			}
			st.close();
		}
		
		return ret;
	}
	
	/**
	 * 调用存储过程，插入新订单(网站订单导CRM系统)
	 * 
	 * @param orderInfo
	 *            订单信息
	 * @param card_type
	 *            卡类型
	 * @throws java.lang.Exception
	 */
	public void transactOrderInfo(Connection conn, OrderInfo orderInfo)
			throws Exception {
		
		
		String release_date = orderInfo.getRelease_date();
		CallableStatement cst = conn
				.prepareCall("{?=call WEB_INTERFACE.F_GET_WEB_ORDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cst.registerOutParameter(1, Types.INTEGER);
		cst.setString(2, orderInfo.getSo_number().trim());
		//	    System.out.println("订单号："+orderInfo.getSo_number());
		cst.setLong(3, orderInfo.getMbr_id());
		//System.out.println("CRM中会员ID："+queryCardId(orderInfo.getBuyer_id()));
		cst.setString(4, release_date);
		//	    System.out.println("创建日期："+orderInfo.getRelease_date());
		cst.setString(5, orderInfo.getAddress().trim());
		//	    System.out.println("地址："+orderInfo.getAddress());
		cst.setString(6, orderInfo.getPostcode() + "");
		//	    System.out.println("邮编："+orderInfo.getPostcode());
		cst.setString(7, orderInfo.getContact());
		//	    System.out.println("联系人："+orderInfo.getContact());
		cst.setString(8, orderInfo.getPhone());
		//		System.out.println("电话："+orderInfo.getPhone());
		cst.setInt(9, orderInfo.getDelivery_type());
		//	    System.out.println("发货方式："+orderInfo.getDelivery_type());
		cst.setFloat(10, orderInfo.getDelivery_fee());
		//	    System.out.println("发贷费用："+orderInfo.getDelivery_fee());
		cst.setInt(11, orderInfo.getPayment_method());
		//	    System.out.println("负款方式："+orderInfo.getPayment_method());
		cst.setInt(12, orderInfo.getIs_invoice());
		//	    System.out.println("是否要发票："+orderInfo.getIs_invoice());
		cst.setFloat(13, orderInfo.getProduct_value());
		//	    System.out.println("产品金额："+orderInfo.getProduct_value());
		cst.setFloat(14, orderInfo.getAppendfee());
		//				    System.out.println("追加费用："+orderInfo.getAppendfee());
		cst.setFloat(15, orderInfo.getOrder_value());
		//    System.out.println("订单总金额："+orderInfo.getOrder_value());
		cst.setInt(16, orderInfo.getOrder_status());
		//	    System.out.println("订单状态："+orderInfo.getOrder_status());
		cst.setInt(17, orderInfo.getOos_dispose());
		//	    System.out.println("oos_dispose:"+orderInfo.getOos_dispose());
		cst.setString(18, orderInfo.getWeb_line_id());
		cst.setString(19, orderInfo.getItem_id());
		//	    System.out.println("产品ID："+orderInfo.getItem_id());
		cst.setString(20, orderInfo.getQuantity());
		//	    System.out.println("订购数量："+orderInfo.getQuantity());
		cst.setString(21, orderInfo.getPrice());
		//	    System.out.println("产品价格："+orderInfo.getPrice());
		cst.setString(22, orderInfo.getIs_commitment());
		//	   System.out.println("是否义务书："+orderInfo.getIs_commitment());
		cst.setString(23, orderInfo.getSell_type());
		//	    System.out.println("sell_type："+orderInfo.getSell_type());
		cst.setString(24, orderInfo.getGift_number());
		//订单备注信息
		// System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~订单备注信息:"+orderInfo.getOrder_memo());
		cst.setString(25, orderInfo.getOrder_memo());
		cst.setInt(26, orderInfo.getCard_type());
		cst.setString(27,orderInfo.getPro_line_id());
		cst.execute();
		int order_id = cst.getInt(1);
		String description = "";
		if (order_id < 0) {
			if (order_id == -9) {
				log.error("订单存在" + "订单：" + orderInfo.getSo_number()
						+ "已存在，不需要重新导入。");
			} else {
				throw new Exception(String.valueOf(order_id));
			}
		} else {
			orderInfo.setOrder_id(order_id);
		}
		cst.close();
	}
	
	private void getCrmMemberId(Connection conn,OrderInfo order) throws Exception
	{
		long crm_member_id = -1;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select id from mbr_members where netshop_id=" + order.getBuyer_id());
		if (rs.next()) {
			crm_member_id = rs.getLong("id");
			order.setMbr_id(crm_member_id);
		}
		if(crm_member_id <0 ) {
			throw new Exception("网站id为" + rs.getInt("id")+ "会员不存在");
		}
		rs.close();
		st.close();
	}
}
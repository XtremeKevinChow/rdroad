package com.magic.crm.order.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.magic.crm.common.DBOperation;
import com.magic.crm.common.LogicUtility;
import com.magic.crm.common.SequenceManager;
import com.magic.crm.common.WebData;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.order.entity.ShoppingCart2;
import com.magic.crm.order.form.OrderForm;
import com.magic.crm.product.dao.Product2DAO;
import com.magic.crm.promotion.dao.PromotionDAO;
import com.magic.crm.promotion.entity.Prom_gift;

public class OrderChangeDAO {

	/**
	 * �õ�������Ʒ��Ϣ����ѯ���õķ���Ʒ�洢��data.getItems()�У� ��Ʒ�洢��data.getGifts()��
	 * 
	 * @param db
	 * @param data
	 *            ���䶩��ID���߶�����Ž��в�ѯ
	 * @throws Exception
	 */
	public static void getOrderLinesInfo2(DBOperation db, OrderForm data)
			throws Exception {
		WebData wd = new WebData();
		String sql = "select t1.id, t1.sku_id, t2.itm_code, nvl(t1.mbr_award_id,0) as mbr_award_id, "
				+ "t21.itm_name as itemname, 0 as clubid,t1.sell_type as selltypeid, "
				+ "t3.name as selltypename, t1.quantity-nvl(t1.return_qty,0) as quantity, t5.name as status, "
				+ "t4.name as unit, t1.price, t21.itm_type, t1.pricelist_line_id, "
				+ " t2.color_code,t6.name as color_name,t2.size_code,t1.set_code, "
				+ " nvl(t7.use_qty - t7.frozen_qty,0) availqty, "
				+ " nvl(t7.use_qty - t7.frozen_qty + t2.os_qty*t2.enable_os,0) availqty2 "
				+ "from ord_lines t1, prd_item_sku t2,prd_item t21, "
				+ "s_sell_type t3, s_uom t4 ,s_order_line_status t5 ,prd_item_color t6,sto_stock t7 "
				+ "where t1.sku_id = t2.sku_id(+) and t2.itm_code = t21.itm_code "
				+ "and t1.status = t5.id(+) and t2.color_code = t6.code(+) "
				+ "and t1.sell_type = t3.id(+) and t2.itm_unit = t4.id(+) "
				+ "and t1.sku_id = t7.sku_id and (t1.status>0 or t1.status=-8) "
				+ "and t1.order_id = " + data.getOrderId();

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
			item.setMax_count(item.getItemQty());
			item.setItemUnit(wd.getDetailString("unit"));
			item.setItemPrice(wd.getDetailDouble("price"));
			item.setAwardId(wd.getDetailInt("mbr_award_id"));
			item.setStatus(wd.getDetailString("status"));
			item.setSet_code(wd.getDetailString("set_code"));
			// item.setIs_pre_sell(wd.getDetailInt("is_pre_sell"));
			// item.setClubID(wd.getDetailInt("clubid"));
			item.setTruss(wd.getDetailInt("itm_type") == 2
					|| wd.getDetailInt("itm_type") == 3 ? true : false);

			item.setPriceListLineId(wd.getDetailInt("pricelist_line_id"));// add
			item.setColor_name(wd.getDetailString("color_name"));
			item.setColor_code(wd.getDetailString("color_code"));
			item.setSize_code(wd.getDetailString("size_code"));

			item.setAvailQty(wd.getDetailInt("availqty"));
			item.setAvailQty2(wd.getDetailInt("availqty2"));

			item.setColors(Product2DAO.listItemColor(db.conn, item
					.getItemCode()));
			item
					.setSizes(Product2DAO.listItemSize(db.conn, item
							.getItemCode()));

			if (item.getSellTypeId() == 4) {// ������Ʒ
				Prom_gift prom_gift = PromotionDAO.findPromGiftByPK(db.conn,
						item.getAwardId());
				if (prom_gift != null) {
					item.setFloorMoney(prom_gift.getOverx());
				}
			}

			// ���ÿ��״̬
			if (item.getAvailQty() >= item.getItemQty()) {
				item.setStockStatusId(0);
				item.setStockStatusName("�������");
			} else if (item.getAvailQty2() >= item.getItemQty()) {
				item.setIs_pre_sell(1);
				item.setStockStatusName("������");
			} else {
				if (item.isLastSell()) {
					item.setStockStatusName("����ȱ��");
				} else {
					item.setStockStatusName("��ʱȱ��");
				}
			}

			data.getCart().getGifts().add(item);

		}
		wd = null;
	}

	/**
	 * ��ѯ������Ʒ��Ϣ
	 * @param conn
	 * @param item
	 * @return
	 * @throws Exception
	 */
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
				//item.setMax_count(rs.getInt("max_count"));
				item.setAvailQty2(rs.getInt("availqty2"));

				// ���ÿ��״̬
				if (item.getAvailQty() >= item.getItemQty()) {
					item.setStockStatusId(0);
					item.setStockStatusName("�������");
				} else if (item.getAvailQty2() >= item.getItemQty()) {
					item.setIs_pre_sell(1);
					item.setStockStatusName("������");
				} else {
					if (item.isLastSell()) {
						item.setStockStatusName("����ȱ��");
					} else {
						item.setStockStatusName("��ʱȱ��");
					}
				}
			} else {
				item.setSku_id(0);
				item.setStockStatusName("δȷ��");
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			//log.error("exception", e);
			throw e;
		}

		if (item.getSku_id() <= 0) {
			return -1;
		}
		return 0;
	}

	/**
	 * �ύ��������
	 * 
	 * @param conn
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public static int changeOrder(Connection conn, OrderForm pageData)
			throws Exception {
		
		ShoppingCart2 cart = pageData.getCart();
		int ret = 0;
		long old_order_id = cart.getOrder().getOrderId();
		long new_order_id = SequenceManager.getNextVal(conn,
				"seq_ord_headers_id");

		String so_number = "T"
				+ LogicUtility.getDateAsString().replaceAll("-", "").substring(
						2)
				+ SequenceManager.getNextVal(conn, "seq_ord_number_id");
		pageData.setOrderNumber(so_number);

		// step1 ���뻻��������Ϣ modify zhux 20090110 �������ķ�����ʽ��Ϊ��������
		String sql = "insert into ord_headers(id,so_number,buyer_id,order_type,pr_type,order_category,creator_id,"
				+ " delivery_type,delivery_fee,payment_method,goods_fee,order_sum,ref_order_id,postcode,address,contact,phone,phone1,section,status)"
				+ " (select ?,?,buyer_id,20,2,10,?,3,0,payment_method,0,0,?,?,?,?,?,?,?,22 "
				+ " from ord_headers where id =?) ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, new_order_id);
		ps.setString(2, so_number);
		ps.setInt(3, cart.getOrder().getCreatorId());
		ps.setLong(4, old_order_id);
		ps.setString(5, cart.getDeliveryInfo().getPostCode());
		ps.setString(6, cart.getDeliveryInfo().getAddress());
		ps.setString(7, cart.getDeliveryInfo().getReceiptor());
		ps.setString(8, cart.getDeliveryInfo().getPhone());
		ps.setString(9, cart.getDeliveryInfo().getPhone2());
		ps.setString(10, cart.getDeliveryInfo().getSection());
		ps.setLong(11, old_order_id);
		

		ret = ps.executeUpdate();
		ps.close();

		// step2 ���뻻��������Ϣ,����
		PreparedStatement ps0 = conn.prepareStatement("select use_qty-frozen_qty from sto_stock where sku_id =?");
		
		sql = "insert into ord_lines(id,ref_line_id,order_id,sku_id,quantity,price,sell_type,frozen_qty,frozen_item,set_code,status)"
				+ "(select SEQ_ORD_LINES_ID.nextval,id,?,?,?,0,?,?,?,set_code,? from ord_lines where id = ?)";
		ps = conn.prepareStatement(sql);
		PreparedStatement ps1 = conn
				.prepareStatement("update ord_lines set status =? where id =?");
		PreparedStatement ps2 = conn
				.prepareStatement("update sto_stock set frozen_qty = frozen_qty + ? where sku_id =? ");
		
		//�������Ķ�����
		List lstItems = cart.getItems();
		for (int i = 0; i < lstItems.size(); i++) {
			ItemInfo ii = (ItemInfo) lstItems.get(i);
			
			int actQty = 0;
			ps0.setInt(1, ii.getSku_id());
			ResultSet rs = ps0.executeQuery();
			if(rs.next()) {
				actQty = rs.getInt(1);
			}
			rs.close();
			
			ps.setInt(2, ii.getSku_id());
			ps.setLong(1, new_order_id);
			ps.setInt(3, ii.getItemQty());
			ps.setInt(4, ii.getSellTypeId());
			ps.setInt(5, ii.getItemQty());
			ps.setString(6, String.valueOf(ii.getSku_id()));
			if (actQty >= ii.getItemQty()) {
				ps.setInt(7, 30);
			} else {
				ps.setInt(7, 21);
			}
			ps.setLong(8, ii.getLineId());
			ps.executeUpdate();

			ps1.setInt(1, 22);
			ps1.setLong(2, ii.getLineId());
			ps1.executeUpdate();

			ps2.setInt(1, ii.getItemQty());
			ps2.setInt(2, ii.getSku_id());
			ps2.executeUpdate();
		}
		
		//�����˻��Ķ�����
		List lstGift = cart.getAllGifts();
		for (int i = 0; i < lstGift.size(); i++) {
			ItemInfo ii = (ItemInfo) lstGift.get(i);
			ps.setInt(2, ii.getSku_id());
			ps.setLong(1, new_order_id);
			ps.setInt(3, ii.getItemQty());
			ps.setInt(4, ii.getSellTypeId());
			ps.setInt(5, ii.getItemQty());
			ps.setString(6, String.valueOf(ii.getSku_id()));
			ps.setInt(7, -8);
			ps.setLong(8, ii.getLineId());
			ps.executeUpdate();

			ps1.setInt(1, -8);
			ps1.setLong(2, ii.getLineId());
			ps1.executeUpdate();

		}

		ps.close();
		ps1.close();
		ps2.close();

		//����erp�������
		CallableStatement cstmt = conn.prepareCall("{call f_exchange_order(?)}");
		cstmt.setLong(1, new_order_id);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.execute();
		ret = cstmt.getInt(1);
		cstmt.close();
		
		return ret;
	}

	/**
	 * ȷ�ϻ�������
	 * 
	 * @param conn
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public static int confirmOrder(Connection conn, long order_id)
			throws Exception {

		int ret = 0;

		// step1 ���뻻��������Ϣ
		String sql = "update ord_headers set status = 25 where id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, order_id);

		ret = ps.executeUpdate();
		ps.close();

		return ret;
	}

	/**
	 * ȡ����������
	 * 
	 * @param conn
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public static int cancelOrder(Connection conn, long order_id)
			throws Exception {
		CallableStatement cstmt = conn.prepareCall("{?=call orders.f_delete_changed_order(?)}");
		cstmt.setLong(2, order_id);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.execute();

		int ret = cstmt.getInt(1);
		cstmt.close();

		return ret;
	}
}

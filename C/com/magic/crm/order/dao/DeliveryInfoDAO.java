/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.magic.crm.common.DBOperation;
import com.magic.crm.common.SequenceManager;
import com.magic.crm.common.WebData;
import com.magic.crm.order.entity.DeliveryInfo;
import com.magic.crm.order.form.DeliveryInfoForm;
import com.magic.crm.util.KeyValue;

/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeliveryInfoDAO {
	/**
	 * 插入送货信息
	 * 
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	public static int insert(DBOperation db, DeliveryInfoForm data)
			throws Exception {
		WebData wd = new WebData();
		// 表名
		wd.setTable("mbr_addresses");

		// 关键字
		wd.addHeaderData("id", new Integer(SequenceManager.getNextVal(db.conn,
				"seq_mbr_addresses_id")));
		// 页面输入的数据
		wd.addHeaderData("delivery_address", data.getAddress());
		wd.addHeaderData("relation_person", data.getReceiptor());
		wd.addHeaderData("member_id", new Integer(data.getMbId()));
		wd.addHeaderData("telephone", data.getPhone());
		wd.addHeaderData("telephone1", data.getPhone2());
		wd.addHeaderData("postcode", data.getPostCode());
		wd.addHeaderData("section",data.getSection());
		wd.addHeaderData("delivery_type", new Integer(data
						.getDeliveryTypeId()));
		wd.addHeaderData("pay_method", new Integer(data.getPaymentTypeId()));

		// 保存
		db.insert(wd);
		int _id = wd.getHeaderInt("ID");
		wd = null;
		return _id;
	}

	public static void modify(DBOperation db, DeliveryInfoForm data)
			throws Exception {
		WebData wd = new WebData();
		// 表名
		wd.setTable("mbr_addresses");

		// 关键字条件
		wd.setSubWhere("id = " + data.getAddressId() + " and member_id = "
				+ data.getMbId());
		// 页面输入的数据
		wd.addHeaderData("delivery_address", data.getAddress());
		wd.addHeaderData("relation_person", data.getReceiptor());
		wd.addHeaderData("telephone", data.getPhone());
		wd.addHeaderData("telephone1", data.getPhone2());
		wd.addHeaderData("postcode", data.getPostCode());
		wd.addHeaderData("section", data.getSection());
		wd.addHeaderData("delivery_type", new Integer(data
						.getDeliveryTypeId()));
		wd.addHeaderData("pay_method", new Integer(data.getPaymentTypeId()));

		// 保存
		db.modify(wd);
		wd = null;
	}

	public static void getMemberInfo(DBOperation db, DeliveryInfoForm data)
			throws Exception {
		WebData wd = new WebData();
		// 得到会员姓名和选择的AddressID
		String sql = "select id, card_id, name as mbname, address_id "
				+ "from mbr_members where id = " + data.getMbId();
		db.queryHeaderData(sql, wd);

		data.setMbId(wd.getHeaderInt("id"));
		data.setCardId(wd.getHeaderString("card_id"));
		data.setMbName(wd.getHeaderString("mbname"));
		data.setAddressId(wd.getHeaderInt("address_id"));
		wd = null;
	}
	
	public static void copyDeliveryInfo(Connection conn, long src_id,long dst_id)
			throws Exception {
		String sql = "insert into mbr_addresses(id,delivery_address,relation_person," +
				" member_id,telephone,postcode,section) select SEQ_MBR_ADDRESSES_ID.nextval," +
				" address,name,?,telephone,postcode,section from mbr_members where id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, dst_id);
		ps.setLong(2, src_id);
		
		int ret = ps.executeUpdate();
		ps.close();
		
	}


	/**
	 * 根据会员ID和送货地址ID查询到该条送货地址信息
	 * 
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	public static void find(DBOperation db, DeliveryInfoForm data)
			throws Exception {
		// 会员不存在
		if (data.getMbId() == 0) {
			return;
		}

		// 查询送货信息、付款信息
		String sql = "select id, member_id, relation_person, "
				+ "telephone, delivery_address, postcode, "
				+ "delivery_type, pay_method, telephone1,section from mbr_addresses "
				+ "where member_id = " + data.getMbId() + " and id = "
				+ data.getAddressId();

		WebData wd = new WebData();
		db.queryHeaderData(sql, wd);

		data.setAddressId(wd.getHeaderInt("id"));
		data.setReceiptor(wd.getHeaderString("relation_person"));
		data.setPhone(wd.getHeaderString("telephone"));
		data.setPhone2(wd.getHeaderString("telephone1"));
		data.setAddress(wd.getHeaderString("delivery_address"));
		data.setPostCode(wd.getHeaderString("postcode"));
		data.setDeliveryTypeId(wd.getHeaderInt("delivery_type"));
		data.setPaymentTypeId(wd.getHeaderInt("pay_method"));
		data.setSection(wd.getHeaderString("section"));
	
		wd = null;
	}

	public static void list(DBOperation db, DeliveryInfoForm data)
			throws Exception {
		// 会员不存在
		if (data.getMbId() == 0) {
			return;
		}

		// 查询送货信息、付款信息
		String sql = "select t1.id, t1.member_id, t1.relation_person, "
				+ "t1.telephone, t1.delivery_address, t1.postcode, "
				+ "t2.name as delivery_type, t2.delivery_fee, "
				+ "t3.name as payment_method, t1.is_default from mbr_addresses t1, "
				+ "s_delivery_type t2," + "s_payment_method t3 "
				+ "where t1.member_id = " + data.getMbId()
				+ " and t1.delivery_type = t2.id(+) "
				+ " and t1.pay_method = t3.id(+)";
		WebData wd = new WebData();
		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			DeliveryInfo di = new DeliveryInfo();
			di.setAddressId(wd.getDetailInt("id"));
			di.setReceiptor(wd.getDetailString("relation_person"));
			di.setPhone(wd.getDetailString("telephone"));
			di.setAddress(wd.getDetailString("delivery_address"));
			di.setPostCode(wd.getDetailString("postcode"));
			di.setDeliveryType(wd.getDetailString("delivery_type"));
			di.setPaymentType(wd.getDetailString("payment_method"));
			//if (di.getAddressId() == data.getAddressId())
			if (wd.getDetailInt("is_default") == 1)
				di.setDefault(true);
			data.getDeliveryInfoList().add(di);
		}
		wd = null;
	}

	/**
	 * 根据会员编号和会员送货邮政编码得到备选的送货方式列表
	 * 
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	public static void listDeliveryTypes(DBOperation db, DeliveryInfoForm data)
			throws Exception {
		WebData wd = new WebData();
		// 查询该送货邮编是否可以直送
		String sql = "select count(*) as id from s_area where is_express=1 and areacode = '"
				+ data.getSection() + "'";
		wd.clearHeaderData();
		db.queryHeaderData(sql, wd);
		boolean directable = (wd.getHeaderInt("id") > 0);

		// 查询得到送货方式
		sql = "select id, name from s_delivery_type";
		if (!directable)
			sql += " where id not in (3,5)";
		sql += " order by id";
		wd.clearHeaderData();
		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			KeyValue kv = new KeyValue();
			kv.setId(wd.getDetailInt("id"));
			kv.setName(wd.getDetailString("name"));
			data.getDeliveryTypeList().add(kv);
		}

		wd = null;
	}

	/**
	 * 根据会员送货方式得到备选的付款方式列表
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	public static void listPaymentTypes(DBOperation db, DeliveryInfoForm data)
			throws Exception {
		WebData wd = new WebData();
		// 查询得到付款方式
		String sql = "select id, name from s_payment_method where id in "
				+ "(select payment_id from s_delivery_payment where "
				+ "delivery_id = " + data.getDeliveryTypeId() + ")"
				+ " order by id";
		wd.clearHeaderData();
		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			KeyValue kv = new KeyValue();
			kv.setId(wd.getDetailInt("id"));
			kv.setName(wd.getDetailString("name"));
			data.getPaymentTypeList().add(kv);
		}
		wd = null;
	}
}
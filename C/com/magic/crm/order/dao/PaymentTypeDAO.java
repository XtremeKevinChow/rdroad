/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.dao;

import java.sql.Connection;
import java.util.Vector;

import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebData;
import com.magic.crm.order.form.PaymentTypeForm;
import com.magic.crm.util.KeyValue;

/**
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PaymentTypeDAO {

	public static void find(DBOperation db, PaymentTypeForm data, boolean is_org )
			throws Exception {
		WebData wd = new WebData();
		// 得到会员姓名和选择的AddressID
		String sql = "select id, card_id, name as mbname "
				+ " from mbr_members where id = " + data.getMbId();
		db.queryHeaderData(sql, wd);

		data.setMbId(wd.getHeaderInt("id"));
		data.setCardId(wd.getHeaderString("card_id"));
		data.setMbName(wd.getHeaderString("mbname"));
		//data.setAddressId(wd.getHeaderInt("address_id"));

		// 会员不存在
		if (data.getMbId() == 0) {
			wd = null;
			return;
		}

		// 查询送货地址和邮政编码
		sql = "select t1.id, t1.delivery_address, t1.postcode, "
				+ "t1.delivery_type, t2.name as deliveryTypeName, "
				+ "t1.pay_method from mbr_addresses t1, s_delivery_type t2 "
				+ " where t1.is_default=1 and t1.member_id = " + data.getMbId()
				+ " and t1.delivery_type = t2.id(+)";
		wd.clearHeaderData();
		db.queryHeaderData(sql, wd);
		if (wd.getHeaderInt("id") > 0) {
			data.setAddress(wd.getHeaderString("delivery_address"));
			data.setPostCode(wd.getHeaderString("postcode"));
			data.setDeliveryTypeId(wd.getHeaderInt("delivery_type"));
			data.setDeliveryTypeName(wd.getHeaderString("deliveryTypeName"));
			data.setCurrPaymentType(wd.getHeaderInt("pay_method"));
		}

		// 查询得到付款方式
		sql = "select id, name from s_payment_method where id in "
				+ "(select payment_id from s_delivery_payment where "
				+ "delivery_id = " + data.getDeliveryTypeId() + ")";
		if(is_org) {
			sql += " and org_use = 'Y' ";
		} else {
			sql += " and person_use = 'Y' ";
		}
		
		sql += " order by seq ";
		
		wd.clearHeaderData();
		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			KeyValue kv = new KeyValue();
			kv.setId(wd.getDetailInt("id"));
			kv.setName(wd.getDetailString("name"));
			kv.setDefault(kv.getId() == data.getCurrPaymentType());
			data.getTypeList().add(kv);
		}
		wd = null;
	}
	
}
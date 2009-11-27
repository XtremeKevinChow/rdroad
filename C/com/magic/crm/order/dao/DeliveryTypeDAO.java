/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.order.dao;

import com.magic.crm.common.DBOperation;
import com.magic.crm.common.WebData;
import com.magic.crm.order.form.DeliveryTypeForm;
import com.magic.crm.util.KeyValue;

/**a
 * @author Water
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeliveryTypeDAO {

	public static void find(DBOperation db, DeliveryTypeForm data,boolean is_org)
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
		sql = "select id, delivery_address, postcode, delivery_type,section "
				+ "from mbr_addresses where is_default=1 and member_id = " + data.getMbId();
		wd.clearHeaderData();
		db.queryHeaderData(sql, wd);
		data.setAddress(wd.getHeaderString("delivery_address"));
		data.setPostCode(wd.getHeaderString("postcode"));
		data.setCurrDeliveryType(wd.getHeaderInt("delivery_type"));
		data.setSection(wd.getHeaderString("section"));
		if (data.getPostCode() == null)
			data.setPostCode("");

		// 查询该区域是否可以直送
		sql = "select count(*) as id from s_area where is_express=1 and areacode = '"
				+ data.getSection() + "'";
		wd.clearHeaderData();
		db.queryHeaderData(sql, wd);
		boolean directable = (wd.getHeaderInt("id") > 0);

		// 查询得到送货方式
		sql = "select id, name from s_delivery_type where ";
		if( is_org ) {
			sql += " org_use = 'Y' ";
		} else {
			sql += " person_use = 'Y' ";
		}
		if (!directable)
			sql += " and id not in (3,5)";
		wd.clearHeaderData();
		db.queryDetailData(sql, wd, false);
		while (wd.next()) {
			KeyValue kv = new KeyValue();
			kv.setId(wd.getDetailInt("id"));
			kv.setName(wd.getDetailString("name"));
			kv.setDefault(kv.getId() == data.getCurrDeliveryType());
			data.getTypeList().add(kv);
		}

		wd = null;
	}
}
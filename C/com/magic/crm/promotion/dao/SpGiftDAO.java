/*
 * Created on 2005-7-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.magic.crm.promotion.entity.MscEntity;
import com.magic.crm.promotion.entity.PrdGroupEntity;
import com.magic.crm.promotion.form.SpGiftForm;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpGiftDAO {
	private static Logger log = Logger.getLogger("SpGiftDAO.class");
	
	/**
	 * 取得对应msc的所有促销活动
	 * @param conn
	 * @param msc
	 * @return
	 * @throws Exception
	 */
	public static ArrayList query(Connection conn,String msc) throws Exception {
		ArrayList ret = new ArrayList();
		Statement st = conn.createStatement();
		String sql = "";
		if (msc == null || "".equals(msc.trim())) {
			sql = "select t1.*,t2.item_code,t2.name as item_name, t3.name as group_name "
				+ " from prd_gift_setting t1 inner join prd_items t2 on t1.item_id = t2.item_id "
				+ " inner join prd_pricelist_gift_groups t3 on t1.group_id = t3.id "
				+ " where t1.msc_code is null order by t1.group_id,t1.require";
		} else {
			sql = "select t1.*,t2.item_code,t2.name as item_name ,t3.name as group_name "
				+ " from prd_gift_setting t1 inner join prd_items t2 on t1.item_id = t2.item_id "
				+ " inner join prd_pricelist_gift_groups t3 on t1.group_id =t3.id "
				+ " where t1.msc_code = '" + msc.trim() + "' order by t1.group_id,t1.require";
		}
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			SpGiftForm sp = new SpGiftForm();
			sp.setId(rs.getLong("id"));
			sp.setItem_id(rs.getLong("item_id"));
			sp.setItem_code(rs.getString("item_code"));
			sp.setItem_name(rs.getString("item_name"));
			sp.setGroup_id(rs.getInt("group_id"));
			sp.setGroup_name(rs.getString("group_name"));
			sp.setOrder_require(rs.getString("require"));
			sp.setSilver_price(rs.getString("silver_price"));
			sp.setGold_price(rs.getString("gold_price"));
			sp.setWeb_price(rs.getString("web_price"));
			sp.setStart_date(rs.getDate("start_date"));
			sp.setEnd_date(rs.getDate("end_date"));
			sp.setValid_flag(rs.getString("valid_flag"));
			sp.setScope(rs.getInt("scope"));
			ret.add(sp);
		}
		rs.close();
		st.close();
		return ret;
	}
	
	/**
	 * 列出所有有效的msc
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static ArrayList listMsc(Connection conn) throws Exception {
		ArrayList ret = new ArrayList();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from prd_pricelists where status=100 and sysdate >= effect_date and sysdate < expired_date +1 and msc is not null order by msc");
		while(rs.next()) {
			MscEntity msc = new MscEntity();
			msc.setMsc_code(rs.getString("msc"));
			msc.setMsc_name(rs.getString("msc") + " " + rs.getString("name"));
			ret.add(msc);
		}
		rs.close();
		st.close();
		return ret;
	}
	
	/**
	 * 列出所有的产品群
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static ArrayList listPrdGroup(Connection conn) throws Exception {
		ArrayList ret = new ArrayList();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from prd_pricelist_gift_groups order by name ");
		while(rs.next()) {
			PrdGroupEntity group = new PrdGroupEntity();
			group.setGroup_id(rs.getString("id"));
			group.setGroup_name(rs.getString("name"));
			ret.add(group);
		}
		rs.close();
		st.close();
		return ret;
	}
	
	/**
	 * 新增促销产品
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static int insert(Connection conn,SpGiftForm data) throws Exception {
		String sql = "insert into prd_gift_setting(id,item_id,gold_price,silver_price,web_price,group_id,require,start_date,end_date,scope,msc_code) "
			+ " values(SEQ_GIFT_SETTING_ID.nextval,(select item_id from prd_items where item_code =?),?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,data.getItem_code());
		ps.setDouble(2,Double.parseDouble(data.getGold_price()));
		ps.setDouble(3,Double.parseDouble(data.getSilver_price()));
		if (!data.getWeb_price().equals("") ) {
			ps.setDouble(4,Double.parseDouble(data.getWeb_price()));
		} else {
			ps.setNull(4,Types.DOUBLE);
		}
		ps.setInt(5,data.getGroup_id());
		ps.setDouble(6,Double.parseDouble(data.getOrder_require()));
		ps.setDate(7,data.getStart_date());
		ps.setDate(8,data.getEnd_date());
		ps.setInt(9,data.getScope());
		if (data.getSel_msc() == null || "".equals(data.getSel_msc().trim())) {
			ps.setNull(10,Types.VARCHAR);
		} else {
			ps.setString(10,data.getSel_msc());
		}
		
		int ret = ps.executeUpdate();
		ps.close();
		return ret;
	}
	
	/**
	 * 新增促销产品
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static int update(Connection conn,SpGiftForm data) throws Exception {
		String sql = "update prd_gift_setting set item_id=(select item_id from prd_items where item_code=?),"
			+ " gold_price=?,silver_price=?,web_price=?,group_id=?,require=?,"
			+ "start_date=?,end_date=?,scope=?,msc_code=? where id =?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,data.getItem_code());
		ps.setDouble(2,Double.parseDouble(data.getGold_price()));
		ps.setDouble(3,Double.parseDouble(data.getSilver_price()));
		if (!data.getWeb_price().equals("") ) {
			ps.setDouble(4,Double.parseDouble(data.getWeb_price()));
		} else {
			ps.setNull(4,Types.DOUBLE);
		}
		ps.setInt(5,data.getGroup_id());
		ps.setDouble(6,Double.parseDouble(data.getOrder_require()));
		ps.setDate(7,data.getStart_date());
		ps.setDate(8,data.getEnd_date());
		ps.setInt(9,data.getScope());
		if (data.getSel_msc() == null || "".equals(data.getSel_msc().trim())) {
			ps.setNull(10,Types.VARCHAR);
		} else {
			ps.setString(10,data.getSel_msc());
		}
		ps.setLong(11,data.getId());
		
		int ret = ps.executeUpdate();
		ps.close();
		return ret;
	}
	
	/**
	 * 根据主键查询促销产品信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static SpGiftForm findByPK(Connection conn,long id) throws Exception {
		SpGiftForm data = new SpGiftForm();
		String sql = " select t1.item_id,t1.gold_price,t1.silver_price,t1.web_price,"
			+ " t1.group_id,t1.require,t1.start_date,t1.end_date,t1.scope,t1.valid_flag, t1.msc_code,t2.item_code"
			+ " from prd_gift_setting t1 inner join prd_items t2 on t1.item_id = t2.item_id where id=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setLong(1,id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			data.setId(id);
			data.setItem_id(rs.getLong("item_id"));
			data.setItem_code(rs.getString("item_code"));
			data.setGold_price(rs.getString("gold_price"));
			data.setSilver_price(rs.getString("silver_price"));
			data.setWeb_price(rs.getString("web_price"));
			data.setGroup_id(rs.getInt("group_id"));
			data.setOrder_require(rs.getString("require"));
			data.setStart_date(rs.getDate("start_date"));
			data.setEnd_date(rs.getDate("end_date"));
			data.setScope(rs.getInt("scope"));
			data.setValid_flag(rs.getString("valid_flag"));
			data.setSel_msc(rs.getString("msc_code"));
			
		}
		rs.close();
		ps.close();
		return data;
	}
	
	/**
	 * 取消促销产品
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static int cancel(Connection conn,SpGiftForm data) throws Exception {
		String sql = " update prd_gift_setting set valid_flag='C' where id =?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1,data.getId());
		int ret = ps.executeUpdate();
		ps.close();
		return ret;
	}
}

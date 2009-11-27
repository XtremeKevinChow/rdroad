/*
 * Created on 2005-7-27
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
public class SpFreeDeliveryDAO {
	// log
	private static Logger log = Logger.getLogger(SpFreeDeliveryDAO.class);
	
	/**
	 * 得到免发送费的设置
	 * @param conn
	 * @param msc
	 * @return
	 * @throws Exception
	 */
	public static String getFreeDelivery(Connection conn, String msc) throws Exception {
		String ret = "";
		String sql = "";
		if (msc == null || "".equals(msc.trim())) {
			sql = " select require from prd_free_delivery_setting where msc_code is null";
		} else {
			sql = " select require from prd_free_delivery_setting where msc_code = '" + msc.trim() + "'";
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			ret = rs.getString("require");
		}
		rs.close();
		ps.close();
		
		return ret;
		
	}
	
	/**
	 * 进行免发送费的设置
	 * @param conn
	 * @param msc
	 * @return
	 * @throws Exception
	 */
	public static int setFreeDelivery(Connection conn, String msc,double require) throws Exception {
		String sql = "update prd_free_delivery_setting set require = ? ";
		if (msc == null || "".equals(msc.trim())) {
			sql = sql + " where msc_code is null ";
		} else {
			sql = sql + " where msc_code = '" + msc.trim() + "' ";
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1,require);
		int ret = ps.executeUpdate();
		ps.close();
		if(ret ==0) {
			PreparedStatement ps2 = conn.prepareStatement("insert into prd_free_delivery_setting(require,msc_code) values(?,?)");
			ps2.setDouble(1,require);
			if (msc == null || "".equals(msc.trim())) {
				ps2.setNull(2,Types.VARCHAR);
			} else {
				ps2.setString(2,msc.trim());
			}
			ret = ps2.executeUpdate();
			ps2.close();
		}
		
		return ret;
		
	}
}

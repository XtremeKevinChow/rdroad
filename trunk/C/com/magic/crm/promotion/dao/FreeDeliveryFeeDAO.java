/*
 * Created on 2005-7-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.magic.crm.promotion.entity.MscEntity;
import com.magic.crm.promotion.entity.PrdGroupEntity;
import com.magic.crm.promotion.form.FreeDeliveryFeeForm;
import com.magic.crm.promotion.form.SpGiftForm;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FreeDeliveryFeeDAO  {
	// log
	private static Logger log = Logger.getLogger(FreeDeliveryFeeDAO.class);
	
	/**
	 * 得到免发送费的设置
	 * @param conn
	 * @param msc
	 * @return
	 * @throws Exception
	 */
	public static ArrayList list (Connection conn) throws Exception {
		
		ArrayList ret = new ArrayList();
		String sql = "select * from prd_free_delivery_by_time order by id desc ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			FreeDeliveryFeeForm obj = new FreeDeliveryFeeForm();
			obj.setID(rs.getInt("id"));
			obj.setOrder_require(rs.getString("order_require"));
			obj.setBegin_date(rs.getString("begin_date").substring(0,10));
			obj.setEnd_date(rs.getString("end_date").substring(0,10));
			obj.setStatus(rs.getInt("status"));
			ret.add(obj);
		}
		rs.close();
		ps.close();
		
		return ret;
	}
	
	/**
	 * 得到免发送费的设置
	 * @param conn
	 * @param msc
	 * @return
	 * @throws Exception
	 */
	public static FreeDeliveryFeeForm findByPk (Connection conn, FreeDeliveryFeeForm info) throws Exception {
		//FreeDeliveryFeeForm obj = new FreeDeliveryFeeForm();
		ArrayList ret = new ArrayList();
		String sql = "select * from prd_free_delivery_by_time where id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1,info.getID());
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			info.setID(rs.getInt("id"));
			info.setOrder_require(rs.getString("order_require"));
			info.setBegin_date(rs.getString("begin_date").substring(0,10));
			info.setEnd_date(rs.getString("end_date").substring(0,10));
			info.setStatus(rs.getInt("status"));
			
		}
		rs.close();
		ps.close();
		
		return info;
	}
	
	/**
	 * 插入免发送费条件
	 * @param conn
	 * @param fm
	 * @return
	 * @throws Exception
	 */
	public static int insert (Connection conn,FreeDeliveryFeeForm fm ) throws Exception {
		
		int ret = 0;
		String sql = "insert into prd_free_delivery_by_time(id,order_require,begin_date,end_date,order_type,status) " +
				" values((select nvl(max(id),0)+1 from prd_free_delivery_by_time)," +
				" ?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, Double.parseDouble(fm.getOrder_require()));
		ps.setString(2, fm.getBegin_date());
		ps.setString(3, fm.getEnd_date());
		ps.setInt(4,fm.getOrder_type());
		ps.setInt(5, fm.getStatus());
		ret = ps.executeUpdate();
		ps.close();
		return ret;
	}
	
	/**
	 * 修改免发送费条件
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static int update (Connection conn,FreeDeliveryFeeForm fm ) throws Exception {
		
		int ret = 0;
		String sql = "update prd_free_delivery_by_time set order_require = ?, " +
				" begin_date=to_date(?,'yyyy-mm-dd') ,end_date= to_date(?,'yyyy-mm-dd') " +
				" where id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, Double.parseDouble(fm.getOrder_require()));
		ps.setString(2, fm.getBegin_date());
		ps.setString(3, fm.getEnd_date());
		ps.setInt(4,fm.getID());
		ret = ps.executeUpdate();
		ps.close();
		return ret;
	}
	
	/**
	 * 更新条件状态
	 * @param conn
	 * @param newStatus
	 * @return
	 * @throws Exception
	 */
public static int updateStatus (Connection conn,FreeDeliveryFeeForm fm ) throws Exception {
		
		int ret = 0;
		String sql = "update prd_free_delivery_by_time set status = ? " +
				" where id = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, fm.getStatus());
		ps.setInt(2,fm.getID());
		ret = ps.executeUpdate();
		ps.close();
		return ret;
	}
	
	
}

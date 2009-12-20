package com.magic.crm.user.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;
import java.util.ArrayList;

import com.magic.crm.user.entity.DeliveryFeeOff;
import com.magic.crm.user.entity.DeliveryFeeOffDlv;
import com.magic.crm.user.entity.DeliveryFeeOffItem;
import com.magic.crm.user.form.DeliveryFeeOffForm;

import com.magic.crm.util.CodeName;
import org.apache.struts.util.LabelValueBean;

/*
 * 特定产品免发送费
 */
public class DeliveryFeeOffDAO {
	/**
	 * 查询全部特定产品免发送费的活动
	 */
	public Collection findAllDfo(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select * From bas_dlv_fee_off Order By off_id Desc";
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryFeeOff dfo = new DeliveryFeeOff();
				dfo.setId(rs.getInt("off_id"));
				dfo.setName(rs.getString("off_name"));
				dfo.setBegin_date(rs.getDate("start_date"));
				dfo.setEnd_date(rs.getDate("end_date"));
				dfo.setStatus(rs.getInt("status"));
				coll.add(dfo);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}
	
	/*
	 * 加载免发送费活动
	 */
	public DeliveryFeeOff getDfo(Connection conn, int id) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select * From bas_dlv_fee_off where off_id=?";
		DeliveryFeeOff dfo = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dfo = new DeliveryFeeOff();
				dfo.setId(rs.getInt("off_id"));
				dfo.setName(rs.getString("off_name"));
				dfo.setBegin_date(rs.getDate("start_date"));
				dfo.setEnd_date(rs.getDate("end_date"));
				dfo.setStatus(rs.getInt("status"));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
		return dfo;
	}
	
	/*
	 * 更新免发送费活动
	 */
	public void updateDfo(Connection conn, DeliveryFeeOff dfo) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Update bas_dlv_fee_off Set off_name=?,start_date=?,end_date=?,status=? Where off_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dfo.getName());
			pstmt.setDate(2, new java.sql.Date(dfo.getBegin_date().getTime()));
			pstmt.setDate(3, new java.sql.Date(dfo.getEnd_date().getTime()));
			pstmt.setInt(4, dfo.getStatus());
			pstmt.setInt(5, dfo.getId());
			pstmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/*
	 * 添加免发送费活动
	 */
	public void addDfo(Connection conn, DeliveryFeeOff dfo) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Insert Into bas_dlv_fee_off( Off_Id, Off_Name, start_date, end_date, status)"+
              " Values(seq_bas_dlv_fee_off.nextval,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dfo.getName());
			pstmt.setDate(2, new java.sql.Date(dfo.getBegin_date().getTime()));
			pstmt.setDate(3, new java.sql.Date(dfo.getEnd_date().getTime()));
			pstmt.setInt(4, dfo.getStatus());
			pstmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 发送方式列表
	 */
	public Collection deliveryList(Connection conn, int dfoId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select a.*, b.name As dlv_name "+
                     "From bas_dlv_fee_off_dlv a "+
                     "Inner Join s_delivery_type b On a.delivery_id=b.id "+
                     "Where a.off_id=? ";
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dfoId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryFeeOffDlv dlv = new DeliveryFeeOffDlv();
				dlv.setOff_id(rs.getInt("off_id"));
				dlv.setOff_type(rs.getInt("off_type"));
				dlv.setOff_fee(rs.getDouble("off_fee"));
				dlv.setStatus(rs.getInt("status"));	
				dlv.setDelivery_id(rs.getInt("delivery_id"));
				dlv.setDelivery_name(rs.getString("dlv_name"));				
				coll.add(dlv);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}
	
	/**
	 * 产品列表
	 */
	public Collection itemList(Connection conn, int dfoId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select a.*,i.itm_name "+
                     "From bas_dlv_fee_off_itm a "+
                     "Inner Join prd_item i On a.item_code=i.itm_code "+
                     "Where a.off_id=? Order By a.item_code";
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dfoId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryFeeOffItem itm = new DeliveryFeeOffItem();				
				itm.setOff_id(rs.getInt("off_id"));
				itm.setItem_type(rs.getInt("item_type"));
				itm.setItem_code(rs.getString("item_code"));
				itm.setItem_name(rs.getString("itm_name"));
				itm.setStatus(rs.getInt("status"));	
				coll.add(itm);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}
	
	/*
	 * 加载发送方式
	 */
	public DeliveryFeeOffDlv getDlv(Connection conn, int offid, int dlvid) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select a.*, b.name As dlv_name "+
            "From bas_dlv_fee_off_dlv a "+
            "Inner Join s_delivery_type b On a.delivery_id=b.id "+
            "Where a.off_id=? and a.delivery_id=? ";
		DeliveryFeeOffDlv dlv = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offid);
			pstmt.setInt(2, dlvid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dlv = new DeliveryFeeOffDlv();
				dlv.setOff_id(rs.getInt("off_id"));
				dlv.setOff_type(rs.getInt("off_type"));
				dlv.setOff_fee(rs.getDouble("off_fee"));
				dlv.setStatus(rs.getInt("status"));	
				dlv.setDelivery_id(rs.getInt("delivery_id"));
				dlv.setDelivery_name(rs.getString("dlv_name"));		
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
		return dlv;
	}
	
	/*
	 * 更新免发送费活动
	 */
	public void updateDlv(Connection conn, DeliveryFeeOffDlv entity) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Update bas_dlv_fee_off_dlv Set off_type=?,off_fee=?,status=? Where off_id=? and delivery_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, entity.getOff_type());
			pstmt.setDouble(2, entity.getOff_fee());
			pstmt.setInt(3, entity.getStatus());
			pstmt.setInt(4, entity.getOff_id());
			pstmt.setInt(5, entity.getDelivery_id());
			pstmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/*
	 * 添加发送方式
	 */
	public void addDlv(Connection conn, DeliveryFeeOffDlv entity) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Insert Into bas_dlv_fee_off_dlv(Off_Id, delivery_id, Off_Type, Off_Fee, status) Values(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, entity.getOff_id());
			pstmt.setInt(2, entity.getDelivery_id());
			pstmt.setInt(3, entity.getOff_type());
			pstmt.setDouble(4, entity.getOff_fee());
			pstmt.setInt(5, entity.getStatus());
			pstmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/*
	 * 添加产品
	 */
	public void addItem(Connection conn, DeliveryFeeOffItem entity) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Insert Into bas_dlv_fee_off_itm(off_id,item_type,item_code,status) Values(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, entity.getOff_id());
			pstmt.setInt(2, entity.getItem_type());
			pstmt.setString(3, entity.getItem_code());
			pstmt.setInt(4, entity.getStatus());
			pstmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/*
	 * 获取产品类型, 1: 单品；3: 套装
	 */
	public int getItemType(Connection conn, String itemCode) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int type = 0;
		String sql = "Select itm_type From prd_item Where itm_code=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemCode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				type = rs.getInt("itm_type");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
		return type;
	}
	
	/*
	 * 加载产品
	 */
	public DeliveryFeeOffItem getItem(Connection conn, int offid, String itemCode) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select * From bas_dlv_fee_off_itm Where off_id=? And item_code=?";
		DeliveryFeeOffItem entity = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offid);
			pstmt.setString(2, itemCode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				entity = new DeliveryFeeOffItem();
				entity.setItem_code(itemCode);
				entity.setItem_type(rs.getInt("item_type"));
				entity.setOff_id(offid);
				entity.setStatus(rs.getInt("status"));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
		return entity;
	}
	
	/*
	 * 更新产品状态
	 */
	public void updateItemStatus(Connection conn, int dfoId, String itemCode, int status) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Update bas_dlv_fee_off_itm a Set a.status=? Where a.off_id=? And a.item_code=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setInt(2, dfoId);
			pstmt.setString(3, itemCode);
			pstmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 添加时，获取发送方式列表
	 */
	public Collection deliveryTypeList(Connection conn, int dfoId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select Id,Name From s_delivery_type Where Id Not In(Select d.delivery_id From bas_dlv_fee_off_dlv d Where d.off_id=?)";
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dfoId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				coll.add(new LabelValueBean(rs.getString("name"),rs.getString("id")));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if ( pstmt != null) {
				pstmt.close();
			}
		}
		return coll;
	}
}

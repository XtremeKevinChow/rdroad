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
}

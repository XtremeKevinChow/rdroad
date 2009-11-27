/**
 * DeliveryFeeDAO.java
 * 2008-4-9
 * 下午05:24:07
 * user
 * DeliveryFeeDAO
 */
package com.magic.crm.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;
import java.util.ArrayList;

import com.magic.crm.common.pager.CompSQL;
import com.magic.crm.user.entity.DeliveryFee;
import com.magic.crm.user.form.DeliveryFeeForm;

import com.magic.crm.util.CodeName;
import com.magic.crm.user.entity.DefaultDeliveryFee;
import org.apache.struts.util.LabelValueBean;
/**
 * @author user
 *
 */
public class DeliveryFeeDAO {
	
	/**
	 * 计算记录数
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public int countRecords (Connection conn, DeliveryFeeForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(1) from s_delivery_fees where area_code= ? ";
		
		int cnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param.getSearchProvince());
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt(1);
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
		return cnt;
	}
	
	/**
	 * 根据条件查询记录
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public Collection findByCondition (Connection conn, DeliveryFeeForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select t1.area_code,t1.id,t1.delivery_type,t1.fees,t1.level_id,t2.name as deliveryName,t3.name as levelName" +
				" from s_delivery_fees t1 "
			+ " left join s_delivery_type t2 on t1.DELIVERY_TYPE = t2.ID "
			+ " left join s_member_level t3 on t1.LEVEL_ID = t3.id "
			+ " where t1.area_code = ? ";
		sql = CompSQL.getNewSql(sql);
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param.getSearchProvince());
			if (param.getSearchCity() != null && !param.getSearchCity().equals("")) {
				pstmt.setString(2, param.getSearchCity());
				pstmt.setInt(3, param.getPager().getOffset()
						+ param.getPager().getLength());
				pstmt.setInt(4, param.getPager().getOffset());
			} else {
				pstmt.setInt(2, param.getPager().getOffset()
						+ param.getPager().getLength());
				pstmt.setInt(3, param.getPager().getOffset());
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryFee fee = new DeliveryFee();
				fee.setId(rs.getInt("id"));
				fee.setDeliveryType(rs.getInt("delivery_type"));
				fee.setDeliveryTypeName(rs.getString("deliveryName"));
				fee.setLevelId(rs.getInt("level_id"));
				fee.setLevelName(rs.getString("levelName"));
				fee.setFees(rs.getDouble("fees"));
				fee.setRegionCode(rs.getString("area_code"));
				coll.add(fee);
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
	 * 根据省市查询记录
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public Collection findByProvinceAndCity (Connection conn, DeliveryFeeForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id, postcode, is_express, city from s_postcode where province = ? ";
		if (param.getSearchCity() != null && !param.getSearchCity().equals("")) {
			sql += "and city = ?";
		}
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param.getSearchProvince());
			if (param.getSearchCity() != null && !param.getSearchCity().equals("")) {
				pstmt.setString(2, param.getSearchCity());
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryFee fee = new DeliveryFee();
				fee.setDeliveryType(rs.getInt("is_express")==1 ? 3 : 1);
				fee.setPostcode(rs.getString("postcode"));
				fee.setCityCode(rs.getString("city"));
				coll.add(fee);
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
	 * 省列表
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Collection getProvinceList (Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from s_area where parentareacode='0001'";
		
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			coll.add(new CodeName("-1","选择..."));
			while (rs.next()) {
				CodeName code_name = new CodeName(rs.getString("areacode"), rs.getString("areaname"));
				coll.add(code_name);
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
	 * 省列表
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Collection getDeliveryTypeList (Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from s_delivery_type";
		
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			//coll.add(new LabelValueBean("-1","选择..."));
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
	
	/**
	 * 省列表
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Collection getMbrLevels (Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from s_member_level";
		
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			//coll.add(new LabelValueBean("-1","选择..."));
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
	/**
	 * 市列表
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public Collection getCityList (Connection conn, DeliveryFeeForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select distinct city from s_postcode where province = ?";
		
		Collection coll = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param.getSearchProvince());
			rs = pstmt.executeQuery();
			coll.add(new CodeName("","所有"));
			while (rs.next()) {
				CodeName code_name = new CodeName(rs.getString("city"), rs.getString("city"));
				coll.add(code_name);
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
	 * 根据主键查询记录
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public DeliveryFee findByPk (Connection conn, DeliveryFeeForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from s_delivery_fees where id = ?";
		DeliveryFee fee = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, param.getSearchId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				fee = new DeliveryFee();
				fee.setId(rs.getInt("id"));
				fee.setDeliveryType(rs.getInt("delivery_type"));
				fee.setLevelId(rs.getInt("level_id"));
				fee.setPostcode(rs.getString("postcode"));
				fee.setFees(rs.getDouble("fees"));
				fee.setBeginDate(rs.getDate("begin_date"));
				fee.setEndDate(rs.getDate("end_date"));
				fee.setRequireAmt(rs.getDouble("require_amt"));
				fee.setRemark(rs.getString("remark"));
				fee.setCityCode(rs.getString("city_code"));
				fee.setRegionCode(rs.getString("region_code"));
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
		return fee;
	}
	
	/**
	 * 检测记录的唯一性
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public boolean existsSetting (Connection conn, DeliveryFee param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select fees from s_delivery_fees where delivery_type = ? and level_id = ? and postcode = ? ";
		boolean flag = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, param.getDeliveryType());
			pstmt.setInt(2, param.getLevelId());
			pstmt.setString(3, param.getPostcode());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
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
		return flag;
	}
	
	/**
	 * 新增记录
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public void insert (Connection conn, DeliveryFee param) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "insert into s_delivery_fees "
			+ "(id, delivery_type, level_id,  fees,  "
			+ "area_code) "
			+ "values (SEQ_DELIVERY_FEES_ID.nextval, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, param.getDeliveryType());
			pstmt.setInt(2, param.getLevelId());
			//pstmt.setString(3, param.getPostcode());
			pstmt.setDouble(3, param.getFees());
			//pstmt.setDate(5, param.getBeginDate());
			//pstmt.setDate(6, param.getEndDate());
			//pstmt.setDouble(7, param.getRequireAmt());
			//pstmt.setString(8, param.getRemark());
			//pstmt.setString(9, param.getCityCode());
			pstmt.setString(4,param.getRegionCode());
			pstmt.execute();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 修改记录
	 * @param conn
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public void update (Connection conn, DeliveryFee param) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "update s_delivery_fees set "
			+ "fees = ?, begin_date = ?, "
			+ "end_date = ?, require_amt = ?, remark = ?, city_code = ?, region_code = ? "
			+ "where id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, param.getFees());
			pstmt.setDate(2, param.getBeginDate());
			pstmt.setDate(3, param.getEndDate());
			pstmt.setDouble(4, param.getRequireAmt());
			pstmt.setString(5, param.getRemark());
			pstmt.setString(6, param.getCityCode());
			pstmt.setString(7, param.getRegionCode());
			pstmt.setInt(8, param.getId());
			pstmt.execute();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	
	public void delete (Connection conn, int id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "delete s_delivery_fees where id =? ";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.execute();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	/**
	 * 更新默认发送费配置
	 * @param conn
	 * @param fee
	 * @throws SQLException
	 */
	public void updateDeliveryFeeByPk(Connection conn, DefaultDeliveryFee fee) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "update s_default_delivery_fee set fees = ?, package_fees = ? where id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, fee.getDeliveryFee());
			pstmt.setDouble(2, fee.getPackageFee());
			pstmt.setInt(3, fee.getId());
			pstmt.execute();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 新增默认发送费配置
	 * @param conn
	 * @param fee
	 * @throws SQLException
	 */
	public void insertDeliveryFeeByPk(Connection conn, DefaultDeliveryFee fee) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "insert s_default_delivery_fee (id, delivery_id, level_id, fees, package_fees) "
			+ "values(?, ?, ?, ?, ?) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fee.getId());
			pstmt.setInt(2, fee.getDeliveryType().getId());
			pstmt.setInt(3, fee.getMemberLevel().getId());
			pstmt.setDouble(4, fee.getDeliveryFee());
			pstmt.setDouble(5, fee.getPackageFee());
			pstmt.execute();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			
			if ( pstmt != null) {
				pstmt.close();
			}
		}
	}
	
	/**
	 * 默认发送费列表
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Collection listDefaultDeliveryFee(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection coll = new ArrayList();
		String sql = "select a.*, b.name as delivery_type_name, c.name as level_name "
			+ "from s_default_delivery_fee a "
			+ "inner join s_delivery_type b on a.delivery_id = b.id "
			+ "inner join s_member_level c on a.level_id = c.id  ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DefaultDeliveryFee fee = new DefaultDeliveryFee();
				fee.setId(rs.getInt("id"));
				fee.getDeliveryType().setId(rs.getInt("delivery_id"));
				fee.getDeliveryType().setName(rs.getString("delivery_type_name"));
				fee.getMemberLevel().setId(rs.getInt("level_id"));
				fee.getMemberLevel().setName(rs.getString("level_name"));
				fee.setDeliveryFee(rs.getDouble("fees"));
				fee.setPackageFee(rs.getDouble("package_fees"));
				coll.add(fee);
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

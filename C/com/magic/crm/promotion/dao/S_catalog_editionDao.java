/* 
 * @author CodeGen 0.1 
 * create on Thu Sep 04 09:24:48 CST 2008
 * 
 * todo 
 */ 

package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.magic.crm.promotion.form.S_catalog_editionForm;
/* 
 * this class is generated by codeGen 0.1 
 * create on Thu Sep 04 09:24:48 CST 2008
 * 
 * todo 
 */ 
public class S_catalog_editionDao { 

	/** 
	 * 根据主键查询内容  
	 * @param conn 使用连接 
	 * @param info  
	 * @return 0 正常 <0  错误信息  >0 未使用 
	 * @throws SQLException 出现sql异常则抛出 
	 */ 
	public static int selectByPK(Connection conn,S_catalog_editionForm info) throws SQLException { 
		int ret = 0; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from s_catalog_edition where 1=1 and id=? ";
		try { 
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,info.getId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setId(rs.getInt("id"));
				info.setName(rs.getString("name"));
				info.setDescription(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(rs!=null) try {rs.close();} catch(Exception e) {};
			if(pstmt!=null) try {pstmt.close();} catch(Exception e) {};
		}
		return ret;
	}

	/** 
	 * 取出所有数据  
	 * @param conn 使用连接 
	 * @return ArrayList 包含所有数据的表  
	 * @throws SQLException 出现sql异常则抛出 
	 */ 
	public static ArrayList queryAll(Connection conn) throws SQLException { 
		ArrayList ret = new ArrayList(); 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from s_catalog_edition";
		try { 
			pstmt = conn.prepareStatement(sql); 
			rs = pstmt.executeQuery();
			while (rs.next()) {
				S_catalog_editionForm info = new S_catalog_editionForm();
				info.setId(rs.getInt("id"));
				info.setName(rs.getString("name"));
				info.setDescription(rs.getString("description"));
				ret.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(rs!=null) try {rs.close();} catch(Exception e) {};
			if(pstmt!=null) try {pstmt.close();} catch(Exception e) {};
		}
		return ret;
	}

	/** 
	 * 插入一条记录  
	 * @param conn 使用连接 
	 * @param info  
	 * @return >0 插入数据条数 <0  错误信息   
	 * @throws SQLException 出现sql异常则抛出 
	 */ 
	public static int insert(Connection conn,S_catalog_editionForm info) throws SQLException { 
		int ret = 0; 
		PreparedStatement pstmt = null;
		String sql = "insert into s_catalog_edition(id,name,description) values(?,?,?) ";
		try { 
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,info.getId());
			pstmt.setString(2,info.getName());
			pstmt.setString(3,info.getDescription());
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(Exception e) {};
		}
		return ret;
	}

	/** 
	 * 更新一条记录  
	 * @param conn 使用连接 
	 * @param info  
	 * @return >0 更新数据条数 <0  错误信息   
	 * @throws SQLException 出现sql异常则抛出 
	 */ 
	public static int updateByPK(Connection conn,S_catalog_editionForm info) throws SQLException { 
		int ret = 0; 
		PreparedStatement pstmt = null;
		String sql = "update s_catalog_edition set name=?, description=?where id = ?  ";
		try { 
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1,info.getName());
			pstmt.setString(2,info.getDescription());
			pstmt.setInt(3,info.getId());
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(Exception e) {};
		}
		return ret;
	}

	/** 
	 * 删除一条记录  
	 * @param conn 使用连接 
	 * @param info  
	 * @return >0 删除数据条数 <0  错误信息   
	 * @throws SQLException 出现sql异常则抛出 
	 */ 
	public static int deleteByPK(Connection conn,S_catalog_editionForm info) throws SQLException { 
		int ret = 0; 
		PreparedStatement pstmt = null;
		String sql = "delete from s_catalog_edition where id = ? ";
		try { 
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1,info.getId());
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(Exception e) {};
		}
		return ret;
	}
} 
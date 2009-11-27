/***********************************************************************
 * Module:  PostcodeGroupDAO.java
 * Author:  user
 * Purpose: Defines the Class PostcodeGroup
 ***********************************************************************/
package com.magic.crm.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.magic.crm.user.entity.PostcodeGroup;
import com.magic.crm.user.entity.PostcodeSet;
import com.magic.crm.user.form.PostcodeSetForm;
import com.magic.crm.user.form.PostcodeGroupForm;

import java.util.Collection;
import java.util.ArrayList;

public class PostCodeSetDAO {
	
	public static void insertGroup(Connection conn, PostcodeGroup param) throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "INSERT INTO jxc.postcode_group("
			+ "id, group_name, description, isvalid) "
			+ "values (?, ?, ?, ?)";
		 try {
	         pstmt = conn.prepareStatement(sQuery);
	         pstmt.setInt(1, param.getId());
	         pstmt.setString(2, param.getGroupName());
	         pstmt.setString(3, param.getDescription());
	         pstmt.setInt(4, 1);

	         pstmt.execute();

	       } catch (SQLException e) {
	       	  e.printStackTrace();
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      
	}
	public static void updateGroup(Connection conn, PostcodeGroup param) throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "update jxc.postcode_group set group_name = ?, description = ? "
			+ "where id = ?";
		 try {

	         
	         pstmt = conn.prepareStatement(sQuery);
	         
	         pstmt.setString(1, param.getGroupName());
	         pstmt.setString(2, param.getDescription());
	         pstmt.setInt(3, param.getId());
	         pstmt.execute();

	       } catch (SQLException e) {
	       	
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      
	}
	public static void deleteGroupByPK(Connection conn, PostcodeGroupForm param) throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "update jxc.postcode_group set isvalid = 0 "
			+ "where id = ?";
		 try {

	         
	         pstmt = conn.prepareStatement(sQuery);
	         pstmt.setInt(1, param.getId());
	         
	         pstmt.execute();

	       } catch (SQLException e) {
	       	
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      
	}
	public static PostcodeGroup findGroupByPK(Connection conn, PostcodeGroupForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		PostcodeGroup data = null;
		String sQuery = "select * from jxc.postcode_group where isvalid = 1 "
			+ "and id = ?";
		 try {

	         
	         pstmt = conn.prepareStatement(sQuery);
	        
	         pstmt.setInt(1, param.getId());
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	        	 data = new PostcodeGroup();
	        	 data.setId(rs.getInt("id"));
	        	 data.setGroupName(rs.getString("group_name"));
	        	 data.setDescription(rs.getString("description"));
	        	 data.setIsValid(rs.getInt("isvalid"));
	         }

	       } catch (SQLException e) {
	       	
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      return data;
	}
	
	public static Collection findAllGroup(Connection conn, PostcodeGroupForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		Collection coll = new ArrayList();
		String sQuery = "select * from jxc.postcode_group where isvalid = 1 ";
		 try {
			 
	         
	         pstmt = conn.prepareStatement(sQuery);
	        
	         rs = pstmt.executeQuery();
	         while (rs.next()) {
	        	 PostcodeGroup data  = new PostcodeGroup();
	        	 data.setId(rs.getInt("id"));
	        	 data.setGroupName(rs.getString("group_name"));
	        	 data.setDescription(rs.getString("description"));
	        	 data.setIsValid(rs.getInt("isvalid"));
	        	 coll.add(data);
	         }

	       } catch (SQLException e) {
	       		e.printStackTrace();
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      return coll;
	}
	
	public static void insertSet(Connection conn, PostcodeSet param) throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "INSERT INTO jxc.postcode_set(id, group_id, postcode, post_fee, isvalid) "
			+ "values (jxc.seq_postcode_set.nextval, ?, ?, ?, ?)";
		 try {

	         
	         pstmt = conn.prepareStatement(sQuery);
	         pstmt.setInt(1, param.getPostcodeGroup().getId());
	         pstmt.setString(2, param.getPostcode());
	         pstmt.setDouble(3, param.getPostFee());
	         pstmt.setInt(4, 1);
	         pstmt.execute();

	       } catch (SQLException e) {
	       	  e.printStackTrace();
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      
	}
	public static void updateSet(Connection conn, PostcodeSet param) throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "update jxc.postcode_set set postcode = ?, post_fee = ? "
			+ "where id= ?";
		 try {

	         
	         pstmt = conn.prepareStatement(sQuery);
	         
	         pstmt.setString(1, param.getPostcode());
	         pstmt.setDouble(2, param.getPostFee());
	         pstmt.setInt(3, param.getId());
	         pstmt.execute();

	       } catch (SQLException e) {
	       	e.printStackTrace();
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      
	}
	public static void deleteSetByPK(Connection conn, PostcodeSetForm param) throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "update jxc.postcode_set set isvalid = 0 "
			+ "where id = ?";
		 try {

	         
	         pstmt = conn.prepareStatement(sQuery);
	         pstmt.setInt(1, param.getId());
	        
	         pstmt.execute();

	       } catch (SQLException e) {
	       	
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      
	}
	public static void deleteSetByFK(Connection conn, PostcodeGroupForm param) throws SQLException {
		PreparedStatement pstmt = null;
		String sQuery = "update jxc.postcode_set set isvalid = 0 "
			+ "where group_id = ?";
		 try {

	         
	         pstmt = conn.prepareStatement(sQuery);
	         pstmt.setInt(1, param.getId());
	        
	         pstmt.execute();

	       } catch (SQLException e) {
	       	
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      
	}
	public static PostcodeSet findSetByPK(Connection conn, PostcodeSetForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		PostcodeSet data = null;
		String sQuery = "select * from jxc.postcode_set where isvalid = 1 "
			+ "and id = ?";
		 try {
	         pstmt = conn.prepareStatement(sQuery);
	        
	         pstmt.setInt(1, param.getId());
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	        	 data = new PostcodeSet();
	        	 data.setId(rs.getInt("id"));
	        	 data.getPostcodeGroup().setId(rs.getInt("group_id"));
	        	 data.setPostcode(rs.getString("postcode"));
	        	 data.setPostFee(rs.getDouble("post_fee"));
	        	 data.setIsValid(rs.getInt("isvalid"));
	        	
	         }

	       } catch (SQLException e) {
	       	
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	     return data;
	}
	public static Collection findSetByFK(Connection conn, PostcodeSetForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		Collection coll = new ArrayList();
		String sQuery = "select * from jxc.postcode_set where isvalid = 1 "
			+ "and group_id = ?";
		 try {

	         
	         pstmt = conn.prepareStatement(sQuery);
	        
	         pstmt.setInt(1, param.getGroupId());
	         rs = pstmt.executeQuery();
	         while (rs.next()) {
	        	 PostcodeSet data = new PostcodeSet();
	        	 data.setId(rs.getInt("id"));
	        	 data.getPostcodeGroup().setId(rs.getInt("group_id"));
	        	 data.setPostcode(rs.getString("postcode"));
	        	 data.setPostFee(rs.getDouble("post_fee"));
	        	 data.setIsValid(rs.getInt("isvalid"));
	        	 coll.add(data);
	         }

	       } catch (SQLException e) {
	       	
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	      return coll;
	}
	public static boolean isExistPostcode(Connection conn, PostcodeSetForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		boolean rtn = false;
		String sQuery = "select * from jxc.postcode_set where isvalid = 1 "
			+ "and id <> "+ param.getId()+ " and postcode = " + param.getPostcode();
		 try {
	         pstmt = conn.prepareStatement(sQuery);
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	        	 rtn = true;
	         }

	       } catch (SQLException e) {
	       	
	          throw e;
	       } finally {
	       	
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	     return rtn;
	}
}

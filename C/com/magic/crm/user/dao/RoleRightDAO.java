package com.magic.crm.user.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.magic.crm.user.entity.*;

/**
 * Jdbc Bean Class<br>
 * <br>
 * Autogenerated on 03/17/2003 10:36:03<br>
 * &nbsp;&nbsp;&nbsp; table = "crm_roleright"
*
* @author Generator
*/
public class RoleRightDAO implements java.io.Serializable {

	public Collection find(Connection con, Role info) throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;
			 Collection roleRightCol = new ArrayList();

       try {
          String sQuery = "SELECT roleId, rightId FROM crm_roleright WHERE roleId=? ";
          pstmt = con.prepareStatement(sQuery);
					pstmt.setString(1, info.getRoleID());

          rs = pstmt.executeQuery();

          while (rs.next()) {
          		RoleRight rr = new RoleRight();

              rr.setRoleID(rs.getString("roleId"));
              rr.setRightId(rs.getBigDecimal("rightId"));

              roleRightCol.add(rr);
          }

       } catch (SQLException e) {
         throw e;
       } finally {
          if (rs != null)
             try { rs.close(); } catch (Exception e) {}
          if (pstmt != null)
             try { pstmt.close(); } catch (Exception e) {}
       }
       return roleRightCol;
   }
	public void insert(Connection con, int rightid, com.magic.crm.user.entity.RoleRight info) throws SQLException {
	      PreparedStatement pstmt = null;

	      try {	
	         String sQuery = "INSERT INTO crm_roleright(roleId, rightId) VALUES(?, ?)";
	         //System.out.println(rightid+"<<<<<<"+info.getRoleID());
	         pstmt = con.prepareStatement(sQuery);

	         pstmt.setString(1, info.getRoleID());
	         pstmt.setInt(2, rightid);

	         pstmt.execute();
	       
	       } catch (SQLException e) {	
	          throw e;
	       } finally {
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	   }
   public static int  getRightID(Connection con, String rightid,String roleid) throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;
			 Collection roleRightCol = new ArrayList();
			 int rid=0;
			 
       try {
          String sQuery = "SELECT roleId, rightId FROM crm_roleright WHERE roleId=? and rightId=?";
          pstmt = con.prepareStatement(sQuery);
					pstmt.setString(1, roleid);
					pstmt.setString(2, rightid);

          rs = pstmt.executeQuery();
         
          if (rs.next()) {
              rid=1;
          }

       } catch (SQLException e) {
         throw e;
       } finally {
          if (rs != null)
             try { rs.close(); } catch (Exception e) {}
          if (pstmt != null)
             try { pstmt.close(); } catch (Exception e) {}
       }
       return rid;
   }


   
   public void delete(Connection con, Role role) throws SQLException {
      PreparedStatement pstmt = null;

      try {
         String sQuery = "DELETE from crm_roleright WHERE roleId=?";
         pstmt = con.prepareStatement(sQuery);
	 			 pstmt.setString(1,role.getRoleID());

         pstmt.execute();

       } catch (SQLException e) {
          throw e;
       } finally {
          if (pstmt != null)
             try { pstmt.close(); } catch (Exception e) {}
       }
   }


}
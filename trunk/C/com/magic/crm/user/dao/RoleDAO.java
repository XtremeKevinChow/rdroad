package com.magic.crm.user.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.math.*;

import com.magic.crm.user.entity.*;


/**
 * Jdbc Bean Class<br>
 * <br>
 * Autogenerated on 03/17/2003 10:36:03<br>
 * &nbsp;&nbsp;&nbsp; table = "roles"
*
* @author Generator
*/
public class RoleDAO implements java.io.Serializable {


   public com.magic.crm.user.entity.Role findRole(Connection con,com.magic.crm.user.entity.Role info) throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;


       try {
          String sQuery = "SELECT roleId, roleName, description FROM crm_roles WHERE roleId=?";
          pstmt = con.prepareStatement(sQuery);
	        pstmt.setString(1,info.getRoleID());

          rs = pstmt.executeQuery();

          if (rs.next()) {
              info.setRoleID(rs.getString("roleId"));
              info.setRoleName(rs.getString("roleName"));
              info.setDescription(rs.getString("description"));
          } else {
             return null;
          }
       } catch (SQLException e) {
         throw e;
       } finally {
          if (rs != null)
             try { rs.close(); } catch (Exception e) {}
          if (pstmt != null)
             try { pstmt.close(); } catch (Exception e) {}
       }
       return info;
   }

   public com.magic.crm.user.entity.Role findRoleByName(Connection con,com.magic.crm.user.entity.Role info) throws SQLException {
      PreparedStatement pstmt = null;
      ResultSet rs = null;


      try {
         String sQuery = "SELECT roleId, roleName, description FROM crm_roles WHERE rolename=?";
         pstmt = con.prepareStatement(sQuery);
               pstmt.setString(1,info.getRoleName());

         rs = pstmt.executeQuery();

         if (rs.next()) {
             info.setRoleID(rs.getString("roleId"));
             
             info.setRoleName(rs.getString("roleName"));
             info.setDescription(rs.getString("description"));
         } else {
            return null;
         }
      } catch (SQLException e) {
        throw e;
      } finally {
         if (rs != null)
            try { rs.close(); } catch (Exception e) {}
         if (pstmt != null)
            try { pstmt.close(); } catch (Exception e) {}
      }
      return info;
   }

   public Collection findAllRoles(Connection con) throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;
			 Collection roleCol = new ArrayList();

       try {
          String sQuery = "SELECT roleId, roleName, description FROM crm_roles order by roleid";
          pstmt = con.prepareStatement(sQuery);

          rs = pstmt.executeQuery();

          while (rs.next()) {

          		Role info = new Role();
              info.setRoleID(rs.getString("roleId"));
              info.setRoleName(rs.getString("roleName"));
              info.setDescription(rs.getString("description"));
              
              roleCol.add(info);
          }

       } catch (SQLException e) {
         throw e;
       } finally {
          if (rs != null)
             try { rs.close(); } catch (Exception e) {}
          if (pstmt != null)
             try { pstmt.close(); } catch (Exception e) {}
       }
       return roleCol;
   }

   public BigDecimal insert(Connection con, com.magic.crm.user.entity.Role info) throws SQLException {
      PreparedStatement pstmt = null;
      BigDecimal roleId = null;
      ResultSet rs=null;
		try {
	         String roleIdSql = "select role_seq.nextval from dual";
	         pstmt = con.prepareStatement(roleIdSql);
	          rs= pstmt.executeQuery();

	         if(rs.next()) {
	             roleId = rs.getBigDecimal(1);
	         }

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}      
      try {



         String sQuery = "INSERT INTO crm_roles(roleId, roleName, description) VALUES(?, ?, ?)";
         pstmt = con.prepareStatement(sQuery);

         pstmt.setBigDecimal(1, roleId);
         pstmt.setString(2, info.getRoleName().trim());
         pstmt.setString(3, info.getDescription());

         pstmt.execute();

       } catch (SQLException e) {
          throw e;
       } finally {
        if (rs != null)
            try { rs.close(); } catch (Exception e) {}
          if (pstmt != null)
             try { pstmt.close(); } catch (Exception e) {}
       }

       return roleId;
   }

   public void update(Connection con, com.magic.crm.user.entity.Role info) throws SQLException {
      PreparedStatement pstmt = null;

      try {
         String sQuery = "UPDATE crm_roles SET roleId=?, roleName=?, description=? WHERE roleId=?";
         pstmt = con.prepareStatement(sQuery);

         pstmt.setString(1, info.getRoleID());
         pstmt.setString(2, info.getRoleName().trim());
         pstmt.setString(3, info.getDescription());
         pstmt.setString(4, info.getRoleID());

         pstmt.execute();

       } catch (SQLException e) {
          throw e;
       } finally {
          if (pstmt != null)
             try { pstmt.close(); } catch (Exception e) {}
       }
   }

   public void delete(Connection con, com.magic.crm.user.entity.Role info) throws SQLException {
      PreparedStatement pstmt = null;

      try {
         String sQuery = "DELETE from crm_roles WHERE roleId=?";
         pstmt = con.prepareStatement(sQuery);
	       pstmt.setString(1,info.getRoleID());

         pstmt.execute();

       } catch (SQLException e) {
          throw e;
       } finally {
          if (pstmt != null)
             try { pstmt.close(); } catch (Exception e) {}
       }
   }


}

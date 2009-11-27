/*
 * Created on 2005-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
import com.magic.crm.util.*;


import com.magic.crm.member.entity.*;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberAddressDAO {
	public MemberAddressDAO(){
		
	}
	
	public MemberAddresses findRecordById(Connection conn, int id) throws Exception {
	    PreparedStatement pstmt = null;
        ResultSet rs=null;

        try {
            String sql = "select * from mbr_addresses where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                MemberAddresses data = new MemberAddresses();
                data.setID(rs.getInt("ID"));
                data.setDelivery_address(rs.getString("delivrey_address"));
                data.setRelation_person(rs.getString("relation_person"));
                data.setMember_ID(rs.getInt("member_id"));
                data.setTelephone(rs.getString("telephone"));
                data.setPostcode(rs.getString("postcode"));
                return data;
            } else {
                return null;
            }
        } finally {
            if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
            if (pstmt != null)
               try { pstmt.close(); } catch (Exception e) {}
        }
	}
	   public int insert(Connection con, MemberAddresses member ) throws SQLException {
        PreparedStatement pstmt = null;
        int addressesId = 0;
        ResultSet rs=null;

        try {        	
           String sQuery = "INSERT INTO mbr_addresses(ID,member_id,POSTCODE,telephone,relation_person,delivery_address,is_default,section,telephone1)"
        	   + " VALUES(?, ?, ?, ?, ?, ?,?,?,?)";
           pstmt = con.prepareStatement(sQuery);          
           	pstmt.setInt(1, member.getID());
  			pstmt.setInt(2,member.getMember_ID());
  			pstmt.setString(3,member.getPostcode());
  			pstmt.setString(4,member.getTelephone());
  			pstmt.setString(5,member.getRelation_person());
  			pstmt.setString(6,CheckStr.checkStr(member.getDelivery_address()));
  			pstmt.setInt(7,1);
  			pstmt.setString(8, member.getSection());
  			pstmt.setString(9, member.getTelephone2());
           pstmt.execute();

         } catch (SQLException e) {
         	System.out.println("sql is error");
            throw e;
         } finally {
         	if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
            if (pstmt != null)
               try { pstmt.close(); } catch (Exception e) {}
         }
         return addressesId;
     }  
	   public int insertAddress(Connection con, MemberAddresses member ) throws SQLException {
        PreparedStatement pstmt = null;
        int addressesId = 0;
        ResultSet rs=null;
		try {
	           String addresuserl = "select seq_mbr_addresses_id.nextval from dual";
	           pstmt = con.prepareStatement(addresuserl);
	           rs = pstmt.executeQuery();
	           if(rs.next()) {
	           	addressesId = rs.getInt(1);
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
        	String sQuery = "INSERT INTO mbr_addresses(ID,member_id,POSTCODE,telephone,relation_person,delivery_address,telephone1,section) VALUES(?, ?, ?, ?, ?, ?, ?,?)";
           pstmt = con.prepareStatement(sQuery);          
           	pstmt.setInt(1, addressesId);
  			pstmt.setInt(2,member.getMember_ID());
  			pstmt.setString(3,member.getPostcode());
  			pstmt.setString(4,member.getTelephone());
  			pstmt.setString(5,member.getRelation_person());
  			pstmt.setString(6,CheckStr.checkStr(member.getDelivery_address()));
  			pstmt.setString(7, member.getTelephone2());
  			pstmt.setString(8, member.getSection());
           pstmt.execute();

         } catch (SQLException e) {
         	System.out.println("sql is error");
            throw e;
         } finally {
         	if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
            if (pstmt != null)
               try { pstmt.close(); } catch (Exception e) {}
         }
         return addressesId;
     }  	   
	   
	   
	   public void update(Connection con, MemberAddresses member ) throws SQLException {
        PreparedStatement pstmt = null;
        
        ResultSet rs=null;
        try {
            String sQuery = "update  mbr_addresses set ID=?,member_id=?,POSTCODE=?,telephone=?,relation_person=?,delivery_address=?, telephone1 = ?,section=? where id=?";
            pstmt = con.prepareStatement(sQuery);          
           	pstmt.setInt(1, member.getID());
  			pstmt.setInt(2,member.getMember_ID());
  			pstmt.setString(3,member.getPostcode());
  			pstmt.setString(4,member.getTelephone());
  			pstmt.setString(5,member.getRelation_person());
  			pstmt.setString(6,CheckStr.checkStr(member.getDelivery_address()));
  			pstmt.setString(7, member.getTelephone2());
  			pstmt.setString(8, member.getSection());
  			pstmt.setInt(9,member.getID());
            pstmt.execute();
         } catch (SQLException e) {
         	System.out.println("sql is error");
            throw e;
         } finally {
         	if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
            if (pstmt != null)
               try { pstmt.close(); } catch (Exception e) {}
         }

     }  
		  public void delete(Connection con,String condition) throws SQLException {
		       Statement stmt = null;
		       ResultSet rs = null;	
		       try {
		          String sQuery = "delete from MBR_ADDRESSES  where id>0 "+condition;
		          stmt = con.createStatement();
		          stmt.executeUpdate(sQuery);		         
		       } catch (SQLException e) {
		         throw e;
		       } finally {
		          if (rs != null)
		             try { rs.close(); } catch (Exception e) {}
		          if (stmt != null)
		             try { stmt.close(); } catch (Exception e) {}
		       }
		   }		   
		  public Collection QueryMemberAddresses(Connection con,String condition) throws SQLException {
		       PreparedStatement pstmt = null;
		       ResultSet rs = null;	
		       Collection memberAddCol = new ArrayList();
		       try {
		          String sQuery = "SELECT * from MBR_ADDRESSES  where id>0 "+condition;
		          pstmt = con.prepareStatement(sQuery);
		          rs = pstmt.executeQuery();
		          while (rs.next()) {
		          	MemberAddresses info = new MemberAddresses();

		           	info.setID(rs.getInt("id"));
		  			info.setMember_ID(rs.getInt("member_id"));
		  			info.setDelivery_address(rs.getString("delivery_address"));
		  			info.setPostcode(rs.getString("POSTCODE"));
		  			info.setTelephone(rs.getString("telephone"));
		  			info.setTelephone2(rs.getString("telephone1"));
		  			info.setRelation_person(rs.getString("relation_person"));
		  			info.setSection(rs.getString("section"));
					memberAddCol.add(info);

		          }
		       } catch (SQLException e) {
		         throw e;
		       } finally {
		          if (rs != null)
		             try { rs.close(); } catch (Exception e) {}
		          if (pstmt != null)
		             try { pstmt.close(); } catch (Exception e) {}
		       }
		       return memberAddCol;
		   }	
		  /*
		   * 下订单时，修改订单，把地址改成默认地址is_default=1
		   * @author Administrator(ysm)
		   * Created on 2005-11-9
		   */
		  public static void updateAddress(Connection con,int mb_id,int id ) throws SQLException {
		        PreparedStatement pstmt = null;
		        
		        ResultSet rs=null;
		        try {
			           String sQuery1 = "select * from mbr_addresses where id="+id+" and is_default=0";
			           pstmt = con.prepareStatement(sQuery1); 
			           rs=pstmt.executeQuery();
                       if(rs.next()){	
                           //如果修改订单时，选择的地址不是默认地址
                           //那么，把原来的地址都设置成非默认地址
				           String sQuery = "update  mbr_addresses set is_default=0 where member_id=?";
				           pstmt = con.prepareStatement(sQuery);          
				           pstmt.setInt(1,mb_id);
				           pstmt.execute(); 
				           pstmt.close();
				           //再把当前的地址设置成默认地址
				           String sQuery3 = "update  mbr_addresses set is_default=1 where id=?";
				           pstmt = con.prepareStatement(sQuery3);          
				           pstmt.setInt(1,id);
				           pstmt.execute();
				           pstmt.close();
                       }
		         } catch (SQLException e) {
		         	System.out.println("sql is error");
		            throw e;
		         } finally {
		         	if (rs != null)
						try {
							rs.close();
						}catch (Exception e){}
		            if (pstmt != null)
		               try { pstmt.close(); } catch (Exception e) {}
		         }

		     }  

}

/*
 * Created on 2005-2-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.member.entity.MemberGIFT;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGIFTDAO {
	public Collection QueryMemberGIFT(Connection con,String condition) throws SQLException {
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;
				 Collection memberCol = new ArrayList();

	       try {
	           String sQuery = "select  a.*,b.name,b.item_code from mbr_get_mbr_gift a ,prd_items b where " +
	           		"a.IS_VALID=0 and sysdate>=a.begin_date and sysdate<a.end_date+1 and b.item_id=a.item_id "+condition;
	          pstmt = con.prepareStatement(sQuery);
	          rs = pstmt.executeQuery();
	          while (rs.next()) {
	          	MemberGIFT info = new MemberGIFT();
	          	
	          		info.setNAME(rs.getString("name"));
	          		info.setID(rs.getInt("id"));
					info.setItem_ID(rs.getInt("item_ID"));
					info.setitem_Code(rs.getString("item_code"));
					info.setOperator_id(rs.getInt("operator_id"));
					info.setKeep_days(rs.getInt("keep_days"));
					info.setEnd_date(rs.getString("end_date"));
					info.setCreate_date(rs.getString("create_date"));
					info.setPrice(rs.getDouble("price"));
					info.setIS_VALID(rs.getInt("IS_VALID"));
	
	              memberCol.add(info);
	          }
	       } catch (SQLException e) {
	         throw e;
	         
	       } finally {
	          if (rs != null)
	             try { rs.close(); } catch (Exception e) {}
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	       return memberCol;
	   }	
	/*
	 * 根据货号得到有效的礼品ID
	 * IS_VALID=1
	 */
	public static int  getMemberGIFTID(Connection con,String gift_id) throws SQLException {
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;
			int id=0;

	       try {
	          String sQuery = "select id from PRD_GET_MBR_GIFTS  where IS_VALID=1 and item_id="+gift_id;

	          pstmt = con.prepareStatement(sQuery);
	          rs = pstmt.executeQuery();

	          while (rs.next()) {
	          		id=rs.getInt("id");

	          }
	       } catch (SQLException e) {
	         throw e;
	       } finally {
	          if (rs != null)
	             try { rs.close(); } catch (Exception e) {}
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	       return id;
	   }	
	/*
	 * 根据产品ID或礼券号判断是礼券还是礼品
	 * 1、礼品
	 * ２、礼券
	 */
	public static int  getGiftType(Connection con,String gift_id) throws SQLException {
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;
			int type=0;

	       try {
	          String sQuery = "select type from mbr_msc_gift  where  item_id='"+gift_id+"'";

	          pstmt = con.prepareStatement(sQuery);
	          rs = pstmt.executeQuery();

	          while (rs.next()) {
	          	type=rs.getInt("type");

	          }
	       } catch (SQLException e) {
	         throw e;
	       } finally {
	          if (rs != null)
	             try { rs.close(); } catch (Exception e) {}
	          if (pstmt != null)
	             try { pstmt.close(); } catch (Exception e) {}
	       }
	       return type;
	   }
	/*
	 * 招募会员时，如果选择礼券根据MSC插入对应MSC的礼券到mbr_gift_ticket_use表
	 */
	public static void insert(Connection conn,MemberGIFT info)throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs=null;
			
		try {      
			String sQuery = "INSERT INTO mbr_gift_ticket_use( MBRID,NUM,TICKET_NUM,TOTAL_NUM)values(?,?,?,?)"; 
			pstmt = conn.prepareStatement(sQuery);
			pstmt.setInt(1,info.getOperator_id());					        
			pstmt.setInt(2,0);
			pstmt.setString(3,info.getitem_Code());
			pstmt.setInt(4,1);
			pstmt.execute();

		} catch (SQLException e) {
	       	System.out.println("mbr_gift_ticket_use insert sql is error");
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
	/*
	 * 2005-07-29
	 * 招募会员选择礼券或礼品
	 */
		
		
		public Collection get_MBR_MSC_GIFT_info(Connection con, String msc_code)
		throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection memberGIFT = new ArrayList();
	
		try {
			String sQuery = "select mbr_msc_gift.item_id,mbr_msc_gift.type,prd_items.name from mbr_msc_gift"+
			" left outer join prd_items on mbr_msc_gift.item_id=to_char(prd_items.item_id)" +
			" where mbr_msc_gift.status=0 and mbr_msc_gift.msc_code='"+msc_code+"'";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				MemberGIFT info = new MemberGIFT();
				info.setitem_Code(rs.getString(1));
				//type １是礼品，２是礼券
				if(rs.getInt(2)==1)
				info.setNAME(rs.getString(3));
				else
				info.setNAME(rs.getString(1));	
				memberGIFT.add(info);
			}
		} catch (SQLException e) {
			throw e;
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
		return memberGIFT;
	}		
}

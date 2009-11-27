/*
 * Created on 2006-12-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.magic.crm.promotion.entity.*;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mbr_use_gift_groupDAO {
	/**
     * 新增礼券使用组明细表
     * @param con
     * @param info
     * @throws SQLException
     */
    public static void insert(Connection con,Mbr_use_gift_group info)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			String sQuery = "INSERT INTO MBR_GIFT_USE_GROUP( ID,GROUP_NO,GIFT_TYPE,GIFT_NUMBER,IS_USED,STATUS )"
					+ " VALUES(SEQ_MBR_GIFT_USE_GROUP_ID.nextval,?,?, ?,?,?)";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1,info.getGroup_no());
			pstmt.setInt(2, info.getGift_type());
			pstmt.setString(3,info.getGift_number());
			pstmt.setInt(4, info.getIs_used());
			pstmt.setInt(5, 0);
			pstmt.execute();
						
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
    /**
     * 删除或启用礼券礼券使用组明细表
     * @param con
     * @param info
     * @throws SQLException
     */    
    public static void delete(Connection con,Mbr_use_gift_group info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

		try {
			String sQuery = "update MBR_GIFT_USE_GROUP set status=? where id=? and status<>-1";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1,info.getStatust() );
			pstmt.setInt(2,info.getID() );
			pstmt.execute();
						
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}    
    /**
     * 修改礼券礼券使用组明细表
     * @param con
     * @param info
     * @throws SQLException
     */    
    public static void update(Connection con,Mbr_use_gift_group info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

		try {
			String sQuery = "update MBR_GIFT_USE_GROUP set GROUP_NO=?,GIFT_TYPE=?,GIFT_NUMBER=? where id=? ";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setString(1,info.getGroup_no() );
			pstmt.setInt(2,info.getGift_type() );
			pstmt.setString(3,info.getGift_number() );
			pstmt.setInt(4,info.getID() );
			pstmt.execute();
						
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				}catch (Exception e){}
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}      
    /**
     * 同组有效的礼券号不能重复
     * @param con
     * @param info
     * @throws SQLException
     */
    public static int checkGiftNo(Connection con, Mbr_use_gift_group info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	
	try {
		String sQuery = "SELECT count(*) from MBR_GIFT_USE_GROUP where  status<>-1 and GROUP_NO='"+info.getGroup_no()+"' and GIFT_NUMBER='"+info.getGift_number()+"'";
		pstmt = con.prepareStatement(sQuery);
		rs = pstmt.executeQuery();	
		System.out.println(sQuery);
		if (rs.next()) {
			checkp=rs.getInt(1);
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
	return checkp;
	}       
}

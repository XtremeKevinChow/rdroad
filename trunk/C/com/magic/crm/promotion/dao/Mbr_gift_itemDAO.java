/*
 * Created on 2006-12-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import com.magic.crm.promotion.entity.*;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mbr_gift_itemDAO {
    /**
     * 新增礼券产品明细表
     * @param con
     * @param info
     * @throws SQLException
     */
    public static void insert(Connection con,Mbr_gift_item info)
			throws SQLException {
		PreparedStatement pstmt = null;
		
	
		try {
			String sQuery = "INSERT INTO MBR_GIFT_BY_ITEMS( ID,ITEM_GROUP_ID,ITEM_ID,IS_MUST )"
					+ " VALUES(SEQ_MBR_GIFT_BY_ITEMS_ID.nextval,?,?, ?)";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1,info.getItem_group_id() );
			pstmt.setInt(2, info.getItem_id());
			pstmt.setInt(3, info.getIs_must());
			pstmt.execute();
						
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
    /**
     * 删除礼券产品明细表
     * @param con
     * @param info
     * @throws SQLException
     */    
    public static void delete(Connection con,Mbr_gift_item info)
	throws SQLException {
	PreparedStatement pstmt = null;

		try {
			String sQuery = "delete from MBR_GIFT_BY_ITEMS where id=?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1,info.getID() );
			pstmt.execute();
						
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}    
    
    /**
     * 查找1条主表有效记录
     * add by user 2006-12-01
     * status = 0
     * @param con
     * @param info
     * @throws SQLException
     */    
    public static Mbr_gift_item_mst getHeadByPK(Connection con, long id)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Mbr_gift_item_mst mst = null;
		try {
			String sQuery = "select * from MBR_GIFT_ITEM_MST where item_group_id = ? and status = 1 ";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setLong( 1, id );
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mst = new Mbr_gift_item_mst();
				mst.setItem_group_id(rs.getInt("item_group_id"));
				mst.setMin_item_count(rs.getInt("min_item_count"));
				mst.setGroup_desc(rs.getString("group_desc"));
				mst.setStatus(rs.getInt("status"));
				mst.setItemgroup_type(rs.getInt("itemgroup_type"));
			}
			return mst;
		} catch (SQLException e) {
				e.printStackTrace();
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
	}    
    
    /**
     * 根据主表id查找明细列表
     * add by user 2006-12-01
     * @param con
     * @param info
     * @throws SQLException
     */    
    public static Collection getLinesByFK(Connection con, long id)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Collection coll = new ArrayList();
	
		try {
			String sQuery = "select * from MBR_GIFT_BY_ITEMS where item_group_id = ? ";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setLong( 1, id );
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Mbr_gift_item dtl = new Mbr_gift_item();
				dtl.setID(rs.getInt("id"));
				dtl.setItem_group_id(rs.getInt("item_group_id"));
				dtl.setItem_id(rs.getInt("item_id"));
				dtl.setIs_must(rs.getInt("is_must"));
				coll.add(dtl);
			}
			return coll;
		} catch (SQLException e) {
				e.printStackTrace();
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
	}    
    /**
     * 礼券失效后产品品组才能失效
     * @param con
     * @param info
     * @throws SQLException
     */
    public static int checkItem_group(Connection con, Mbr_gift_item_mst info)
	throws SQLException {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int checkp=0;
	
	try {
		String sQuery = "SELECT count(*) from mbr_gift_certificates a, mbr_gift_item_mst b ";
		sQuery+=" where  a.product_group_id=b.item_group_id ";
		sQuery+=" and a.start_date<=sysdate and a.end_date>=sysdate+1 and b.item_group_id="+info.getItem_group_id();
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

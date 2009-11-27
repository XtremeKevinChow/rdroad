/*
 * Created on 2006-6-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.magic.crm.member.entity.MemberGetMemberGift;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMemberGiftDAO implements Serializable {

    /*public MemberGetMemberGift findByItemId(Connection conn, int itemId)
            throws SQLException {
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MemberGetMemberGift data = null;
        
        try {
            String sQuery = "select * from MBR_GET_MBR_GIFT where item_id = ? ";
            pstmt = conn.prepareStatement(sQuery);
            pstmt.setInt(1, itemId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                data = new MemberGetMemberGift();
                data.setId(rs.getInt("id"));
                data.setItemId(rs.getInt("item_id"));
                data.setBeginDate(rs.getDate("begin_date"));
                data.setEndDate(rs.getDate("end_date"));
                data.setIsvalid(rs.getInt("is_valid"));
                data.setPrice(rs.getDouble("price"));
                data.setKeepDays(rs.getInt("keep_days"));
                data.setCreateDate(rs.getDate("create_date"));
                data.setOperatorId(rs.getInt("operator_id"));
                return data;
            } else {
                return null;
            }

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

    }*/

}

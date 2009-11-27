package com.magic.crm.member.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.magic.crm.member.entity.PostCodeFile;

import java.util.Collection;
import java.util.ArrayList;

public class PostCodeFileDAO {
	
	/**
     * 删除邮编文件记录
     * @param conn
     * @throws SQLException
     */
    public static void deletePostCodeFile (Connection conn) throws SQLException {
    	PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "delete from mbr_postcode_file";
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
    }
    
    /**
     * 更新订单邮编
     * @param conn
     * @param param
     * @throws SQLException
     */
    public static void updateOrderPostcode (Connection conn, PostCodeFile param) throws SQLException {
    	PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "update ord_headers set postcode = ? where so_number = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, param.getNewPostcode());
            pstmt.setString(2, param.getOrdNumber());
            
            pstmt.execute();
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
    }
    
    /**
     * 更新会员邮编
     * @param conn
     * @param param
     * @throws SQLException
     */
    public static void updateMemberPostcode (Connection conn, PostCodeFile param) throws SQLException {
    	PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "update mbr_members set postcode = ? where card_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, param.getNewPostcode());
            pstmt.setString(2, param.getCardId());
            
            pstmt.execute();
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
    }
    
    /**
     * 插入数据
     * @param conn
     * @param param
     * @throws SQLException
     */
    public static void insert (Connection conn, PostCodeFile param) throws SQLException {
    	PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "insert into mbr_postcode_file "
            	+ "(ord_number, ord_postcode, ord_address, card_id, mbr_postcode, new_postcode) "
            	+ "values (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, param.getOrdNumber());
            pstmt.setString(2, param.getOrdPostcode());
            pstmt.setString(3, param.getOrdAddress());
            pstmt.setString(4, param.getCardId());
            pstmt.setString(5, param.getMbrPostcode());
            pstmt.setString(6, param.getNewPostcode());
            
            pstmt.execute();
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
    }
    
    
    /**
     * 查找所有记录
     * @param conn
     * @return
     * @throws SQLException
     */
    public static Collection findAll(Connection conn) throws SQLException {
    	PreparedStatement pstmt = null;
        Collection coll = new ArrayList();
        ResultSet rs = null;
        String sql = null;
        try {
            sql = "select * from mbr_postcode_file";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	PostCodeFile file = new PostCodeFile();
            	file.setOrdNumber(rs.getString("ord_number"));
            	file.setOrdPostcode(rs.getString("ord_postcode"));
            	file.setOrdAddress(rs.getString("ord_address"));
            	file.setCardId(rs.getString("card_id"));
            	file.setMbrPostcode(rs.getString("mbr_postcode"));
            	file.setNewPostcode(rs.getString("new_postcode"));
            	coll.add(file);
            }
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
        }
        return coll;
    }
}

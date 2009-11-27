/*
 * Created on 2006-6-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import org.apache.log4j.Logger;

import com.magic.crm.member.entity.MemberBlackList;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemberBlackListDAO implements Serializable {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(MemberBlackListDAO.class);

    
    /**
     * 列出会员黑名单
     * 
     * @param conn
     * @param data
     * @throws SQLException
     */
    public ArrayList list(Connection conn)
            throws SQLException {
    	ArrayList ret = new ArrayList();
        PreparedStatement pstmt = null;
        
        String sql = "select t1.*,t2.card_id,t2.name " +
        		" from mbr_blacklist t1 join mbr_members t2 on t1.member_id=t2.id";
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
            	MemberBlackList data = new MemberBlackList();
            	data.setCardID(rs.getString("card_id"));
            	data.setCreateDate(rs.getDate("create_date"));
            	data.setID(rs.getLong("id"));
            	data.setDescription(rs.getString("description"));
            	
            	ret.add(data);
            }
            
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    logger.error("close statement error!!");
                }
        }
        return ret;
    }
    
    /**
     * 插入会员到黑名单
     * 
     * @param conn
     * @param data
     * @throws SQLException
     */
    public void insert(Connection conn, MemberBlackList data)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "insert into mbr_blacklist "
            + "(id, member_id, description, create_date, operator_id) "
            + "values(SEQ_MBR_BLACKLIST_ID.nextval, ?, ?, sysdate, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, data.getMemberID());
            pstmt.setString(2, data.getDescription());
            pstmt.setLong(3, data.getOperatorID());
            pstmt.execute();
            logger.info("insert successfully!!");
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    logger.error("close statement error!!");
                }
        }
    }

    /**
     * 从黑名单去除会员
     * 
     * @param conn
     * @param data
     * @throws SQLException
     */
    public void delete(Connection conn, Long id)
            throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "delete from mbr_blacklist "
            + "where id = ? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            logger.info("insert successfully!!");
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    logger.error("close statement error!!");
                }
        }
    }

    /**
     * 检测会员是否已经存在于黑名单
     * @param conn
     * @param memberID
     * @return
     * @throws SQLException
     */
    public boolean isExistBlacklist(Connection conn, long memberID)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select 1 from mbr_blacklist where member_id = ? ";
        try {
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } 
            return false;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException ex) {
                    logger.error("close resultset error!!");
                }
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    logger.error("close statement error!!");
                }
        }
    }
    public String getBlackRemark(Connection conn, long memberID)
    		throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select description from mbr_blacklist where member_id = ? ";
		String reason = null;
		try {
		    
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setLong(1, memberID);
		    rs = pstmt.executeQuery();
		    if (rs.next()) {
		    	reason = rs.getString("description");
		    } 
		    
		} finally {
		    if (rs != null)
		        try {
		            rs.close();
		        } catch (SQLException ex) {
		            logger.error("close resultset error!!");
		        }
		    if (pstmt != null)
		        try {
		            pstmt.close();
		        } catch (SQLException ex) {
		            logger.error("close statement error!!");
		        }
		}
		return reason;
    }
}

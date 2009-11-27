/*
 * Created on 2006-5-16
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

import org.apache.log4j.Logger;

import com.magic.crm.member.entity.MemberGetMember;
import com.magic.crm.member.form.MemberGetMemberForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberGetMemberDAO implements Serializable {
    
    /** 写日志公用类 **/
    private static Logger log = Logger.getLogger("MemberGetMemberDAO.class");
    
    /**
     * 新增记录
     * @param con
     * @param data
     * @throws SQLException
     */
	public void insert(Connection con, MemberGetMemberForm data) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "insert into MBR_GET_MBR (EVENT_ID, MEMBER_ID, gift_number,STATUS, "
			    + "RECOMMENDED_ID, KEEP_DAYS, OPERATOR_ID, CREATE_DATE) "
			    + "values ( SEQ_MBR_GET_MBR_ID.nextval, ?, ?, ?, ?, ?, ?, sysdate )";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, data.getMemberId().longValue());
			pstmt.setString(2, data.getGiftNumber());
			pstmt.setInt(3, 0);
			//pstmt.setLong(3, data.getGiftId().longValue());
			pstmt.setLong(4, data.getRecommendedId().longValue());
			pstmt.setInt(5, data.getKeepDays().intValue());
			//pstmt.setDouble(6, data.getPrice().doubleValue());
			pstmt.setLong(6, data.getOperatorId().longValue());
			pstmt.execute();

		} catch (SQLException e) {
			log.error("an error occur when insert a record into the dabatase!");
			throw e;
		} finally {	
			if (pstmt != null)
			    try {
			        pstmt.close();
			    }catch(SQLException ex){
			        log.error("an error occur when close statement! ");
			        throw ex;
			    }
		}
	}
	
	/**
	 * 得到会员推荐会员礼品保留天数
	 * @param con
	 * @return days
	 * @throws SQLException
	 */
	public static Integer getMGMGiftKeepDay(Connection con) throws SQLException {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int days = 0;
	    try {
	        String sql = "SELECT VALUE FROM S_CONFIG_KEYS WHERE KEY = 'MGM_GIFT_KEEP_DAY'";
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            days = Integer.parseInt(rs.getString(1));
	        }
	    } catch(SQLException e) {
	        e.printStackTrace();
	    }finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            }catch(Exception e) {
	                
	            }
	        }
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            }catch(Exception e) {
	                
	            }
	        }
	    }
	    return new Integer(days);
	}
	
	/**
	 * 得到有效的礼券，如果有效的礼券不止一个，仅取第一个
	 * @param conn
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public static int getAvailGiftNumber(Connection conn, MemberGetMemberForm info )
		throws SQLException {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int days = 0;
	    try {
	        String sql = "SELECT t1.gift_number,t1.keep_days,t2.order_money,t2.gift_money,t2.end_date " +
	        		"from mbr_get_mbr_gift t1 join mbr_gift_certificates t2 on t1.gift_number = t2.GIFT_NUMBER " +
	        		" where t1.is_valid = 0 and sysdate>=t1.begin_date and sysdate< t1.end_date + 1 ";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            info.setGiftNumber(rs.getString("gift_number"));
	            info.setGiftMoney(rs.getDouble("gift_money"));
	            info.setKeepDays(rs.getInt("keep_days"));
	            info.setOrderMoney(rs.getDouble("order_money"));
	            info.setEndDate(rs.getString("end_date").substring(1,10));
	        }
	        
	    } catch(SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            }catch(Exception e) {
	                
	            }
	        }
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            }catch(Exception e) {
	                
	            }
	        }
	    }
	    return 0;
	}
	
	/**
	 *  此被推荐会员是否已经存在
	 * @param con
	 * @param memberId
	 * @param recommendedId
	 * @return
	 * @throws SQLException
	 */
	public static boolean hasTheRecommendedMan(Connection con, long memberId, long recommendedId) throws SQLException {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT RECOMMENDED_ID FROM MBR_GET_MBR WHERE RECOMMENDED_ID = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setLong(1, recommendedId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return true;
	        }
	        return false;
	    } catch(SQLException e) {
	        throw e;
	    }finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            }catch(Exception e) {
	                
	            }
	        }
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            }catch(Exception e) {
	                
	            }
	        }
	    }
	    
	}
}

/*
 * Created on 2006-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.magic.crm.member.entity.MembeMoneyHistory;
/**
 * @author user
 * 充值历史DAO
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberMoneyHistoryDAO {
    
    
    
    /**
     * 增加一条历史纪录
     * @param conn
     * @param data
     * @throws SQLException
     */
    public void insert(Connection conn, MembeMoneyHistory data) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            sql = "insert into mbr_money_history ("
                + "id, member_id, operator_id, deposit, money_update, "
                + "comments, modify_date, event_type, pay_method, sdate"
                + ") values (SEQ_MBR_MONEY_HISTORY.nextval, ?, ?, ?, ?, "
                + "?, ?, ?, ?, sysdate)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, data.getMEMBER_ID());
            pstmt.setInt(2, data.getOPERATOR_ID());
            pstmt.setDouble(3, data.getDEPOSIT());
            pstmt.setDouble(4, data.getMONEY_UPDATE());
            pstmt.setString(5,data.getCOMMENTS());
            pstmt.setString(6,data.getMODIFY_DATE());
            pstmt.setInt(7, data.getEVENT_TYPE());
            pstmt.setInt(8, data.getPayMethod());
            pstmt.execute();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException ex) {}
        }
    }
}

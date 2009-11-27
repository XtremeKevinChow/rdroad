/*
 * Created on 2006-7-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.finance.entity.CustomerType;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomerTypeDAO {

    /**
     * 查询所有记录
     * 
     * @param con
     * @return
     * @throws SQLException
     */
    public static Collection findAllCustomerTypes(Connection con)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Collection coll = new ArrayList();
        try {

            String sql = "select * from CUST_TYPE_MST ";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CustomerType data = new CustomerType();
                data.setTypeID(rs.getInt("type_id"));
                data.setTypeDesc(rs.getString("type_desc"));
                coll.add(data);

            }
        } catch (SQLException e) {

            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {

                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {

                }
            }
        }
        return coll;
    }

    /**
     * 查询所有记录（除了4-团购，5-物流）
     * @param con
     * @return
     * @throws SQLException
     */
    public static Collection findAllCustomerTypes1(Connection con)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Collection coll = new ArrayList();
        try {

            String sql = "select * from CUST_TYPE_MST where type_id not in(4,5) ";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CustomerType data = new CustomerType();
                data.setTypeID(rs.getInt("type_id"));
                data.setTypeDesc(rs.getString("type_desc"));
                coll.add(data);

            }
        } catch (SQLException e) {

            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {

                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {

                }
            }
        }
        return coll;
    }
    /**
     * 查询所有记录（4-团购，5-物流）
     * @param con
     * @return
     * @throws SQLException
     */
    public static Collection findAllCustomerTypes2(Connection con)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Collection coll = new ArrayList();
        try {

            String sql = "select * from CUST_TYPE_MST where type_id in(4,5) ";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CustomerType data = new CustomerType();
                data.setTypeID(rs.getInt("type_id"));
                data.setTypeDesc(rs.getString("type_desc"));
                coll.add(data);

            }
        } catch (SQLException e) {

            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {

                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {

                }
            }
        }
        return coll;
    }
    /** 显示详详情 * */
    public static CustomerType findNameByPK(Connection con, int id)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CustomerType data = null;
        try {

            String sql = "select * from CUST_TYPE_MST where type_id = ? ";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs != null) {
                data = new CustomerType();
                while (rs.next()) {
                    data.setTypeID(rs.getInt("type_id"));
                    data.setTypeDesc(rs.getString("type_desc"));

                }
            }
        } catch (SQLException e) {

            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {

                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {

                }
            }
        }
        return data;
    }

}

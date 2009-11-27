/*
 * Created on 2006-7-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.common.pager.CompSQL;
import com.magic.crm.finance.entity.Customer;
import com.magic.crm.finance.form.CustomerForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomerDAO {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CustomerDAO.class);
    
    
    
    /** 新增记录 * */
	public void insert(Connection con, Customer data) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		try {
		    
			sql = "insert into CUST_MST (CUST_NO, TYPE_ID, CUST_NAME, REMARK) values ( ?, ?, ?, ? )";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data.getCustomerNO());
			pstmt.setInt(2, data.getTypeID());
			pstmt.setString(3, data.getCustomerName());
			pstmt.setString(4, data.getRemark());
			
			pstmt.execute();

		} catch (SQLException e) {
		    logger.error("[" + data.getCustomerNO() + "], [" + data.getTypeID() + "], [" + data.getCustomerName() + "], [" + data.getRemark() + "]");
			throw e;
		} finally {
			
			if (pstmt != null)
				pstmt.close();

		}
	}

	/** 显示详主表详情 **/
	public Customer findCustomerByPK(Connection con, CustomerForm param)
			throws SQLException {
	    
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Customer data = null;
		try {
		    sql = "select * from cust_mst where cust_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getCustomerNO());
			rs = pstmt.executeQuery();
			if (rs != null) {
				data = new Customer();
				while (rs.next()) {
					data.setCustomerNO(rs.getString("cust_no"));
					data.setTypeID(rs.getInt("type_id"));
					data.setCustomerName(rs.getString("cust_name"));
					data.setRemark(rs.getString("remark"));
				}
			} 
		} catch (SQLException e) {
		    logger.error("[" + param.getCustomerNO() + "]");
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
	
	
	/** 修改记录 * */
	public void update(Connection con, Customer data) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "update cust_mst set type_id = ?, cust_name = ?, remark = ? where cust_no = ? ";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, data.getTypeID());
			pstmt.setString(2, data.getCustomerName());
			pstmt.setString(3, data.getRemark());
			pstmt.setString(4, data.getCustomerNO());
			pstmt.execute();

		} catch (SQLException e) {
		    logger.error("[" + data.getCustomerNO() + "], [" + data.getTypeID() + "], [" + data.getCustomerName() + "], [" + data.getRemark() + "], [" + data.getCustomerNO() + "]");
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					
				}
		}
	}

	/** 删除指定记录（保留） **/
	public void delete(Connection con, CustomerForm param) throws SQLException {
	    
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "DELETE FROM cust_mst WHERE cust_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getCustomerNO());
			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
	
	/** 删除客户表中团体会员客户 **/
	public static void deleteAllOrgMembers(Connection con) throws SQLException {
	    
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "DELETE FROM cust_mst WHERE type_id = 4";
			pstmt = con.prepareStatement(sql);
			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
	
	/** 批量插团体会员客户 **/
	public static void insertAllOrgMembers(Connection con) throws SQLException {
	    
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "insert into cust_mst (CUST_NO, TYPE_ID, CUST_NAME, REMARK) " 
			    + "select card_id, 4, name, '批量插入' "
			    + "from mbr_members where is_organization = 1 ";
			pstmt = con.prepareStatement(sql);
			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
	
	/** 删除客户表中供应商客户 **/
	public static void deleteAllProviders(Connection con) throws SQLException {
	    
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "DELETE FROM cust_mst WHERE type_id = 5 OR cust_no = '00'";
			pstmt = con.prepareStatement(sql);
			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
	
	/** 批量插入供应商客户 **/
	public static void insertAllProviders(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		sql = "insert into cust_mst (CUST_NO, TYPE_ID, CUST_NAME, REMARK) " 
		    + "select deliver_no, 5, deliver_name, '批量插入' "
		    + "from jxc.delivers ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		
		
		sql = "update cust_mst set type_id = 1 where cust_no = '00' ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.execute();

		} catch (SQLException e) {
		    e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	}
	
	/**
     * 组合sql
     * @param param
     * @param sql
     * @return
     */
    private String compoundSql(String sql, CustomerForm param) {
        return sql;
    }
    
    /**
     * 查找记录总数
     * @param conn
     * @param stoNO
     * @param itemID
     * @throws SQLException
     */
    public int countAllCustomers(Connection conn, CustomerForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0;
        String sql = "select count(*) from cust_mst ";
        sql = compoundSql(sql, param);
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs != null && rs.next()) {
                cnt = rs.getInt(1);
            }
            return cnt;
            
        } catch(SQLException ex) {
            logger.error("load error ");
            throw ex;
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
        }
    }
	/** 查询所有记录 **/
	public Collection findAllCustomers(Connection con, CustomerForm param) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Collection coll = new ArrayList();
		sql = "select * from cust_mst";
		sql = compoundSql(sql, param);
        sql = CompSQL.getNewSql(sql);
		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getPager().getOffset() + param.getPager().getLength());
            pstmt.setInt(2, param.getPager().getOffset());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				Customer data = new Customer();
				data.setCustomerNO(rs.getString("cust_no"));
				data.setTypeID(rs.getInt("type_id"));
				//data.setTypeName(rs.getString("type_desc"));
				data.setCustomerName(rs.getString("cust_name"));
				data.setRemark(rs.getString("remark"));
				coll.add(data);
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
		return coll;
	}

}

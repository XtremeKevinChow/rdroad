/*
 * Created on 2006-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.finance.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.common.pager.CompSQL;
import com.magic.crm.finance.form.Inbound2ShiftDiffQueryForm;


/**
 * @author user
 * （退货、扣单）入库上架查询dao
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Inbound2ShiftDiffQueryDAO implements Serializable {
    
    /**
     * 组合sql
     * @param param
     * @param sql
     * @return
     */
    private String compoundSql(String sql, Inbound2ShiftDiffQueryForm param) {
        if ( param.getStartDate() != null && !param.getStartDate().equals("") ) {  
		    sql += "and a.createdate >= date'" + param.getStartDate() + "' ";
		}
		if ( param.getEndDate() != null && !param.getEndDate().equals("") ) {
		    sql += "and a.createdate < date'" + param.getEndDate() + "' + 1 ";
		}
		if ( param.getRsType() != null && !param.getRsType().equals("") ) {
		    sql += "and rs_type = '" + param.getRsType() + "' ";
		}
        return sql;
    }
    
    /**
     * 计算记录数
     * @param conn
     * @param param
     * @return
     * @throws Exception
     */
    public int countList(Connection conn, Inbound2ShiftDiffQueryForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0;
        String sql = "select count(1) "
		    + "from jxc.sto_rktoshift_dtl b inner join jxc.sto_rktoshift_mst a on a.rs_no = b.rs_no "
		    + "inner join prd_items c on b.item_id = c.item_id "
		    + "where 1 = 1 and (b.sum_qty - b.fact_qty) <> 0 and a.status = 'F' ";
        sql = compoundSql(sql, param);
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs != null && rs.next()) {
                cnt = rs.getInt(1);
            }
            return cnt;
            
        } catch(SQLException ex) {
            throw ex;
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
        }
    }
	/**
	 * 查询所有记录
	 * @param con
	 * @param form
	 * @return
	 * @throws SQLException
	 */
	public Collection getList(Connection con, Inbound2ShiftDiffQueryForm form) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection result = new ArrayList();
		String sql = null;
		sql = "select a.rs_no, a.rs_type, a.createdate, c.item_code, c.name, (b.sum_qty - b.fact_qty) as diff_qty, b.shelf_no "
		    + "from jxc.sto_rktoshift_dtl b inner join jxc.sto_rktoshift_mst a on a.rs_no = b.rs_no "
		    + "inner join prd_items c on b.item_id = c.item_id "
		    + "where 1 = 1 and (b.sum_qty - b.fact_qty) <> 0 and a.status = 'F' ";
		try {
			
			sql = compoundSql(sql, form);
	        sql = CompSQL.getNewSql(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, form.getPager().getOffset() + form.getPager().getLength());
            pstmt.setInt(2, form.getPager().getOffset());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Inbound2ShiftDiffQueryForm info = new Inbound2ShiftDiffQueryForm();
				info.setRsNO(rs.getString("rs_no"));
				info.setRsType(rs.getString("rs_type"));
				info.setCreateDate(rs.getDate("createdate"));
				info.setItemCode(rs.getString("item_code"));
				info.setItemName(rs.getString("name"));
				info.setDiffQty(rs.getDouble("diff_qty"));
				info.setShelfNO(rs.getString("shelf_no"));
				result.add(info);
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
		return result;
	}
}

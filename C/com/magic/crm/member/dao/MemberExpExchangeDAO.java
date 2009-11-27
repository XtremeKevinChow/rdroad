/*
 * Created on 2006-2-13
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
import java.util.ArrayList;
import java.util.Collection;

import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.order.entity.ItemInfo;
import com.magic.crm.common.pager.CompSQL;
import com.magic.crm.member.form.MemberExpExchangeForm;
import com.magic.crm.member.form.MemberExpExchangePopForm;
/**
 * @author 蟋蟀
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemberExpExchangeDAO implements Serializable {
	public MemberExpExchangeDAO() {}
	
	/** 新增记录 **/
	public void insert(Connection con, MemberExpExchangeForm data) throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		int newID = 0;
		try {
			String sql = "SELECT SEQ_MBR_EXP_EXCHANGE_DTL.nextval FROM DUAL ";
			pstmt2 = con.prepareStatement(sql);
			rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				newID = rs2.getInt(1);
			}
		} catch (SQLException e) {

		} finally {
			if (rs2 != null)
				try {
					rs2.close();
				} catch (Exception e) {

				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (Exception e) {

				}
		}
		try {
			String insertSql = null;
			insertSql = "INSERT INTO MBR_EXP_EXCHANGE_DTL (ID, PID, ITEM_ID, EXP_START, EXCHANGE_PRICE, " +
			"CONTENT, EXP_END, START_DATE, END_DATE, VALID_DAY) VALUES (?, ?, ?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ? ) ";
			pstmt = con.prepareStatement(insertSql);
			pstmt.setLong(1, newID);
			pstmt.setLong(2, data.getParentID());
			pstmt.setInt(3, data.getItemID());
			pstmt.setInt(4, data.getExpStart());
			pstmt.setInt(5, data.getExchangePrice());
			pstmt.setString(6, data.getContent());
			pstmt.setInt(7, 0);
			pstmt.setString(8, data.getStartDate());
			pstmt.setString(9, data.getEndDate());
			pstmt.setInt(10, data.getValidDay());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if (pstmt != null)
				pstmt.close();

		}
	}
	
	/** 显示详情 **/
	public MemberExpExchangeForm showDetail(Connection con, MemberExpExchangeForm form)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberExpExchangeForm data = null;
		try {
			String sql = "SELECT a.ID, a.PID, a.ITEM_ID, b.ITEM_CODE, b.NAME, " + 
			"a.EXP_START, a.EXCHANGE_PRICE, a.CONTENT, a.START_DATE, a.END_DATE, a.VALID_DAY " +
			"FROM MBR_EXP_EXCHANGE_DTL a INNER JOIN PRD_ITEMS b ON a.ITEM_ID = b.ITEM_ID " +
			"WHERE a.ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, form.getID());
			rs = pstmt.executeQuery();
			if (rs != null) {
				data = new MemberExpExchangeForm();
				while (rs.next()) {
					data.setID(rs.getLong("ID"));
					data.setParentID(rs.getLong("PID"));
					data.setItemID(rs.getInt("ITEM_ID"));
					data.setItemCode(rs.getString("ITEM_CODE"));
					data.setItemName(rs.getString("NAME"));
					data.setExpStart(rs.getInt("EXP_START"));
					data.setExchangePrice(rs.getInt("EXCHANGE_PRICE"));
					data.setContent(rs.getString("CONTENT"));
					data.setStartDate(rs.getString("START_DATE"));
					data.setEndDate(rs.getString("END_DATE"));
					data.setValidDay(rs.getInt("VALID_DAY"));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
	/** 通过ID得到Name **/
	public String getNameByID(Connection con, long id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String rname = null;
		try {
			String sql = "SELECT NAME FROM MBR_EXP_EXCHANGE_MST WHERE VALID_FLAG = 'Y' AND ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs != null) {
				
				while (rs.next()) {
					rname = rs.getString("NAME");
					
				}
			} else{
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
		
		return rname;
	}
	/** 修改记录 **/
	public void update(Connection con, MemberExpExchangeForm data) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			
			String updateSql = 
			"UPDATE MBR_EXP_EXCHANGE_DTL SET ITEM_ID = ?, EXP_START = ?, EXCHANGE_PRICE = ?, " + 
			"CONTENT = ?, PID = ?, START_DATE = TO_DATE(?, 'YYYY-MM-DD'), END_DATE = TO_DATE(?, 'YYYY-MM-DD'),VALID_DAY = ? WHERE ID= ? ";
			pstmt = con.prepareStatement(updateSql);
			pstmt.setInt(1, data.getItemID());
			pstmt.setInt(2, data.getExpStart());
			pstmt.setInt(3, data.getExchangePrice());
			pstmt.setString(4, data.getContent());
			pstmt.setLong(5, data.getParentID());
			pstmt.setString(6, data.getStartDate());
			pstmt.setString(7, data.getEndDate());
			pstmt.setInt(8, data.getValidDay());
			pstmt.setLong(9, data.getID());
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

	/** 删除记录 **/
	public void delete(Connection con, MemberExpExchangeForm form) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String delSql = "UPDATE MBR_EXP_EXCHANGE_DTL SET VALID_FLAG = 'N' WHERE ID = ?";
			pstmt = con.prepareStatement(delSql);
			pstmt.setLong(1, form.getID());
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
     * 组合sql
     * @param param
     * @param sql
     * @return
     */
    private StringBuffer compoundSql(StringBuffer sQuery, MemberExpExchangeForm form) {
        
        if ( form.getParentID() != 0 ) {
			sQuery.append("AND a.PID = " + form.getParentID());
		}
		if( form.getValidFlag() != null && !form.getValidFlag().equals("")) {
			sQuery.append("AND a.VALID_FLAG = '" + form.getValidFlag() + "' AND a.PID >=3 ");
		}
		if( form.getQueryDate() != null && ! form.getQueryDate().equals("") ) {
			sQuery.append("AND a.START_DATE < DATE'" + form.getQueryDate() + "' + 1 ");
			sQuery.append("AND a.END_DATE >= DATE'" + form.getQueryDate() + "' ");
		}
		if (form.getItemCode() != null && !form.getItemCode().equals("")) {
		    sQuery.append("AND b.item_code = '" + form.getItemCode() + "' ");
		}
        return sQuery;
    }
    
    /**
     * 查找记录总数
     * @param conn
     * @param stoNO
     * @param itemID
     * @throws SQLException
     */
    public int countRecordsByCondition(Connection conn, MemberExpExchangeForm param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0;
        StringBuffer sQuery = new StringBuffer();
        sQuery.append("SELECT count(1) "); 
		sQuery.append("FROM MBR_EXP_EXCHANGE_DTL a INNER JOIN PRD_ITEMS b ON a.ITEM_ID = b.ITEM_ID ");
		sQuery.append("INNER JOIN MBR_EXP_EXCHANGE_MST c ON a.PID = c.ID ");
		sQuery.append("WHERE 1 = 1 ");
		sQuery = compoundSql(sQuery, param);
        try {
            pstmt = conn.prepareStatement(sQuery.toString());
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
	public Collection getList(Connection con, MemberExpExchangeForm form) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection gift = new ArrayList();
		ItemInfo item = null;
		StringBuffer sQuery = new StringBuffer();
		int keepDays = MbrGetAwardDAO2.getExchangeGiftKeepDay(con);
		try {
			sQuery.append("SELECT a.ID, a.PID, a.ITEM_ID, b.ITEM_CODE, b.NAME AS ITEM_NAME, a.EXP_START, "); 
			sQuery.append("a.EXCHANGE_PRICE, a.CONTENT, c.NAME, a.START_DATE, a.END_DATE, a.VALID_DAY, a.VALID_FLAG ");
			sQuery.append("FROM MBR_EXP_EXCHANGE_DTL a INNER JOIN PRD_ITEMS b ON a.ITEM_ID = b.ITEM_ID ");
			sQuery.append("INNER JOIN MBR_EXP_EXCHANGE_MST c ON a.PID = c.ID ");
			sQuery.append("WHERE 1 = 1 ");
			/*if ( form.getParentID() != 0 ) {
				sQuery.append("AND a.PID = " + form.getParentID());
			}
			if( form.getValidFlag() != null && !form.getValidFlag().equals("")) {
				sQuery.append("AND a.VALID_FLAG = '" + form.getValidFlag() + "' AND a.PID >=3 ");
			}
			if( form.getQueryDate() != null && ! form.getQueryDate().equals("") ) {
				sQuery.append("AND a.START_DATE < DATE'" + form.getQueryDate() + "' + 1 ");
				sQuery.append("AND a.END_DATE >= DATE'" + form.getQueryDate() + "' ");
			}
			if (form.getItemCode() != null && !form.getItemCode().equals("")) {
			    sQuery.append("AND b.item_code = '" + form.getItemCode() + "' ");
			}*/
			sQuery = compoundSql(sQuery, form);
			sQuery.append(" ORDER BY a.VALID_FLAG DESC, a.EXP_START ASC ");
			
			//String sql = CompSQL.getNewSql(sQuery.toString());
			pstmt = con.prepareStatement(sQuery.toString());
			//pstmt.setInt(1, form.getPager().getOffset() + form.getPager().getLength());
            //pstmt.setInt(2, form.getPager().getOffset());
			rs = pstmt.executeQuery();
			//System.out.println(sQuery.toString());
			while (rs.next()) {

				MemberExpExchangeForm info = new MemberExpExchangeForm();
				info.setID(rs.getLong("ID"));	
				info.setParentID(rs.getLong("PID"));
				info.setParentName(rs.getString("NAME"));
				info.setItemID(rs.getInt("ITEM_ID"));
				item = OrderDAO.findItem(con,rs.getString("ITEM_CODE"));
				OrderDAO.getStockStatus(con, item);
				//String status = getStockStatus(con, rs.getInt("ITEM_ID"));
				info.setStockStatus(item.getStockStatusName());
				info.setItemCode(rs.getString("ITEM_CODE"));
				info.setItemName(rs.getString("ITEM_NAME"));
				info.setExpStart(rs.getInt("EXP_START"));
				info.setExchangePrice(rs.getInt("EXCHANGE_PRICE"));
				info.setContent(rs.getString("CONTENT"));
				info.setStartDate(rs.getString("START_DATE"));
				info.setEndDate(rs.getString("END_DATE"));
				//info.setValidDay(rs.getInt("VALID_DAY"));
				info.setValidDay(keepDays);
				//下面这种写法有点不太好，DAO中最好不要包含什么逻辑
				if (rs.getString("VALID_FLAG").equals("N")) {
					info.setValidFlag("无效");
				} else {
					info.setValidFlag("有效");
				}
				
				gift.add(info);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		return gift;
	}
	
	
	/** 产品库存状态(废弃) **/
	public String getStockStatus(Connection con, int itemID)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberExpExchangePopForm data = null;
		String rtv = null;
		try {
			String sql = "SELECT (USE_QTY - FROZEN_QTY) AS AVAIL_QTY FROM JXC.STO_STOCK WHERE STO_NO = '000' AND ITEM_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, itemID);
			rs = pstmt.executeQuery();
			if (rs != null) {
				
				if (rs.next()) {
					if (rs.getInt("AVAIL_QTY") > 0 ) {
					    rtv = "库存正常";
					} else {
					    rtv = "仓库缺货";
					}
				
				} else {
				    rtv = "仓库缺货";
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
		return rtv;
		
	}
	/** 显示主表详情 **/
	public MemberExpExchangePopForm showMainDetail(Connection con, long id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberExpExchangePopForm data = null;
		try {
			String sql = "SELECT ID, NAME, DESCRIPTION, START_DATE, END_DATE, VALID_FLAG, EXP_TYPE " +
			"FROM MBR_EXP_EXCHANGE_MST WHERE VALID_FLAG = 'Y' AND ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs != null) {
				data = new MemberExpExchangePopForm();
				while (rs.next()) {
					data.setID(rs.getLong("ID"));
					data.setName(rs.getString("NAME"));
					data.setDescription(rs.getString("DESCRIPTION"));
					data.setStartDate(rs.getString("START_DATE"));
					data.setEndDate(rs.getString("END_DATE"));
					data.setValidFlag(rs.getString("VALID_FLAG"));
					data.setExpType(rs.getInt("EXP_TYPE"));
				
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
	/** 查询主表所有记录(不分页) **/
	public Collection getMstList(Connection con, MemberExpExchangeForm form) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection gift = new ArrayList();

		try {
			String sQuery = "SELECT ID, NAME, DESCRIPTION, START_DATE, END_DATE, EXP_TYPE " +
			"FROM MBR_EXP_EXCHANGE_MST WHERE VALID_FLAG = 'Y' AND 1 = 1 ";
			if (form != null && form.getID() != 0 ) {
				sQuery += " AND ID = " + form.getID();
			}
			sQuery += " ORDER BY ID DESC ";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				MemberExpExchangePopForm info = new MemberExpExchangePopForm();
				info.setID(rs.getLong("ID"));	
				info.setName(rs.getString("NAME"));
				info.setDescription(rs.getString("DESCRIPTION"));
				info.setStartDate(rs.getString("START_DATE"));
				info.setEndDate(rs.getString("END_DATE"));
				info.setExpType(rs.getInt("EXP_TYPE"));

				gift.add(info);
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return gift;
	}
	
	/** 判断产品在某个活动中已经存在 **/
	public boolean hasItemID(Connection con, MemberExpExchangeForm form) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT ID FROM MBR_EXP_EXCHANGE_DTL WHERE ITEM_ID = ? AND ID <> ? AND PID = ? AND VALID_FLAG = 'Y'";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, form.getItemID());
			pstmt.setLong(2, form.getID());
			pstmt.setLong(3, form.getParentID());
			rs = pstmt.executeQuery();
			if(rs.next()) {//有记录
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				}catch(Exception e){
					
				}
			if (pstmt != null)
				
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return false;
	}
}

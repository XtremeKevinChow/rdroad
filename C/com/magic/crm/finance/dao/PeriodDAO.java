/*
 * Created on 2006-7-7
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

import org.apache.log4j.Logger;

import com.magic.crm.finance.entity.Period;
import com.magic.crm.finance.form.PeriodForm;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PeriodDAO {
    
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(PeriodDAO.class);
    
    /** 新增记录 * */
	public void insert(Connection con, Period data) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		try {
		    
			sql = "insert into fin_period (PRD_YEAR, PRD_MONTH, BEGIN_DATE, END_DATE, ISDELETED, ISUSED, ISCLOSED, PID) "
			    + "values (  ?, ?, ?, ?, '0', ?, '0', ? )";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, data.getYear());
			pstmt.setInt(2, data.getMonth());
			pstmt.setDate(3, data.getBeginDate());
			pstmt.setDate(4, data.getEndDate());
			pstmt.setString(5, data.getIsUsed());
			pstmt.setInt(6, data.getID());
			pstmt.execute();

		} catch (SQLException e) {
		    logger.error("[" + data.getID() + "], [" + data.getYear() + "], [" + data.getMonth() + "], [" + data.getBeginDate() + "], [" + data.getEndDate() + "], [" + data.getIsUsed() + "]");
			throw e;
		} finally {
			
			if (pstmt != null)
				pstmt.close();

		}
	}
	
	/** 显示详主表详情 **/
	public Period findPeriodByPK(Connection con, PeriodForm param)
			throws SQLException {
	    
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Period data = null;
		try {
		    
			String sql = "select * from fin_period where isdeleted = '0' and pid = ? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getID());
			rs = pstmt.executeQuery();
			if (rs != null) {
				data = new Period();
				while (rs.next()) {
				    data.setID(rs.getInt("pid"));
					data.setYear(rs.getInt("prd_year"));
					data.setMonth(rs.getInt("prd_month"));
					data.setBeginDate(rs.getDate("begin_date"));
					data.setEndDate(rs.getDate("end_date"));
					data.setIsDeleted(rs.getString("isdeleted"));
					data.setIsUsed(rs.getString("isused"));
					data.setIsClosed(rs.getString("isclosed"));
				}
			} 
			
		} catch (SQLException e) {
		    logger.error("[" + param.getID() + "]");
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
	
	/** 判断会计期是否已经存在 **/
	public static boolean checkPeriod (Connection con, PeriodForm param) throws SQLException {
	    PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		    
			String sql = "select * from fin_period where isdeleted = '0' and prd_year = ? and prd_month = ? and pid <> ? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getYear());
			pstmt.setInt(2, param.getMonth());
			pstmt.setInt(3, param.getID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
			    return true;
			} else {
			    return false;
			}
		} catch (SQLException e) {
		    logger.error("[" + param.getYear() + "], [" + param.getMonth() + "], [" + param.getID() + "]");
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
	}
	
	/** 得到正在使用的会计期数（不能大于1个） **/
	public static int getUsedPeroidCnt(Connection con) throws SQLException {
	    
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) as cnt from fin_period where isdeleted = '0' and isused = '1' ";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
			    return rs.getInt("cnt");
			} else {
			    return 0;
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
	}
	
	/** 得到最大的会计期 **/
	public static int getMaxPeriod (Connection con, PeriodForm param) throws SQLException {
	    PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		    
			String sql = "select max(prd_year * 24 + prd_month) as period from fin_period where isdeleted = '0' and pid <> ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
			    return rs.getInt(1);
			}  else {
			    return 0;
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
	}
	
	/** 得到最大的会计期ID **/
	public static int getMaxPeriodId (Connection con) throws SQLException {
	    PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		    
			String sql = "select nvl(max(pid), 0) from fin_period ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
			    return rs.getInt(1);
			}  else {
			    return 0;
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
	}
	
	/** 得到下一个会计期ID **/
	public static int getNextPeriodId (Connection con) throws SQLException {
	    PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		    
			String sql = "select nvl(max(pid), 0) from fin_period ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
			    return rs.getInt(1) + 1;
			} else {
				return 1;
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
	}
	
	/** 修改记录 * */
	public void update(Connection con, Period data) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "update fin_period set prd_year = ?, prd_month = ?, begin_date = ?, end_date = ?, isused = ? where pid = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, data.getYear());
			pstmt.setInt(2, data.getMonth());
			pstmt.setDate(3, data.getBeginDate());
			pstmt.setDate(4, data.getEndDate());
			pstmt.setString(5, data.getIsUsed());
			pstmt.setInt(6, data.getID());
			pstmt.execute();
			System.out.println(data.getID());
		} catch (SQLException e) {
		    logger.error("[" + data.getYear() + "], [" + data.getMonth() + "], [" + data.getBeginDate() + "], [" + data.getEndDate() + "], [" + data.getIsUsed() + "], [" + data.getID() + "]");
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					
				}
		}
	}

	/** 删除记录 * */
	public void delete(Connection con, PeriodForm param) throws SQLException {
	    
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "update fin_period set isdeleted = '1' where pid = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getID());
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
	
	/** 查询所有记录 **/
	public Collection findAllPeriods(Connection con, String status) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Collection coll = new ArrayList();

		try {
			sql = "select * from fin_period where isdeleted = '0' ";
			if (status != null && !status.equals("")) {
			    sql += "and isused = 1 and isclosed = '" + status + "' ";
			}
			sql += "order by prd_year desc, Prd_month desc ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				Period data = new Period();
				data.setID(rs.getInt("pid"));
				data.setYear(rs.getInt("prd_year"));
				data.setMonth(rs.getInt("prd_month"));
				data.setBeginDate(rs.getDate("begin_date"));
				data.setEndDate(rs.getDate("end_date"));
				data.setIsDeleted(rs.getString("isdeleted"));
				data.setIsUsed(rs.getString("isused"));
				data.setIsClosed(rs.getString("isclosed"));
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

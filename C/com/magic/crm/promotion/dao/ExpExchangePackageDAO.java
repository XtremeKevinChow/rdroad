package com.magic.crm.promotion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.ArrayList;

import com.magic.crm.common.pager.CompSQL;
import com.magic.crm.promotion.form.ExpExchangePackageForm;
import com.magic.crm.promotion.entity.ExpExchangePackageMst;
import com.magic.crm.promotion.entity.ExpExchangePackageDtl;

/**
 * 礼包DAO
 * 
 * @author user
 */
public class ExpExchangePackageDAO {

	private static Logger log = Logger.getLogger(ExpExchangePackageDAO.class);

	/**
	 * 插入礼包信息
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void insertMst(Connection con, ExpExchangePackageForm param)
			throws Exception {

		PreparedStatement pstmt = null;

		try {

			String sql = "insert into EXP_EXCHANGE_PACKAGE_MST (package_no, description, status, url) "
					+ "values (?, ?, 'Y', ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getPackageNo());
			pstmt.setString(2, param.getDesc());
			pstmt.setString(3, param.getUrl());
			pstmt.execute();

		} catch (SQLException e) {
			log.error(e.getMessage());
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
	 * 更新礼包信息
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void updateMst(Connection con, ExpExchangePackageForm param)
			throws Exception {

		PreparedStatement pstmt = null;

		try {

			String sql = "update EXP_EXCHANGE_PACKAGE_MST set description = ?, url = ? "
					+ "where package_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getDesc());
			pstmt.setString(2, param.getUrl());
			pstmt.setString(3, param.getPackageNo());
			pstmt.execute();

		} catch (SQLException e) {
			log.error(e.getMessage());
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
	 * 插入礼包明细
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void insertDtl(Connection con, ExpExchangePackageDtl param)
			throws Exception {

		PreparedStatement pstmt = null;

		try {

			String sql = "insert into EXP_EXCHANGE_PACKAGE_DTL (id, package_no, type, no, quantity, status) "
					+ "values (SEQ_EXP_EXCHANGE_PACK_DTL_ID.nextval, ?, ?, ?, ?, 'Y')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getMst().getPackageNo());
			pstmt.setString(2, param.getPackageType());
			pstmt.setString(3, param.getNo());
			pstmt.setInt(4, param.getQuantity());
			pstmt.execute();

		} catch (SQLException e) {
			log.error(e.getMessage());
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
	 * 记录数
	 * @param con
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static int countRecords(Connection con, ExpExchangePackageForm param)
			throws Exception {

		PreparedStatement pstmt = null;

		ResultSet rs = null;
		int cnt = 0;
		try {

			String sql = "select count(1) from EXP_EXCHANGE_PACKAGE_MST where status = 'Y' ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		return cnt;
	}

	/**
	 * 查找礼包列表
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static Collection findAll(Connection con,
			ExpExchangePackageForm param) throws Exception {

		PreparedStatement pstmt = null;

		ResultSet rs = null;
		Collection list = new ArrayList();
		try {

			String sql = "select * from EXP_EXCHANGE_PACKAGE_MST where status = 'Y' ";
			sql = CompSQL.getNewSql(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, param.getPager().getOffset()
					+ param.getPager().getLength());
			pstmt.setInt(2, param.getPager().getOffset());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ExpExchangePackageMst data = new ExpExchangePackageMst();
				data.setPackageNo(rs.getString("package_no"));
				data.setDesc(rs.getString("description"));
				data.setStatus(rs.getString("status"));
				data.setUrl(rs.getString("url"));
				list.add(data);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		return list;
	}

	/**
	 * 查找一个礼包
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static ExpExchangePackageMst findByPk(Connection con,
			String packageNo) throws Exception {
		ExpExchangePackageForm param = new ExpExchangePackageForm();
		param.setPackageNo(packageNo);
		return findByPk(con, param);
	}

	/**
	 * 查找一个礼包
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static ExpExchangePackageMst findByPk(Connection con,
			ExpExchangePackageForm param) throws Exception {

		PreparedStatement pstmt = null;

		ResultSet rs = null;
		ExpExchangePackageMst data = new ExpExchangePackageMst();
		try {

			String sql = "select * from EXP_EXCHANGE_PACKAGE_MST where status = 'Y' and package_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getPackageNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				data.setPackageNo(rs.getString("package_no"));
				data.setDesc(rs.getString("description"));
				data.setStatus(rs.getString("status"));
				data.setUrl(rs.getString("url"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		return data;
	}

	/**
	 * 查找一个礼包所有明细
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static Collection findByFk(Connection con,
			ExpExchangePackageForm param) throws Exception {

		PreparedStatement pstmt = null;

		ResultSet rs = null;
		Collection list = new ArrayList();
		try {

			String sql = "select * from EXP_EXCHANGE_PACKAGE_DTL where  package_no = ? and status = 'Y' ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getPackageNo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ExpExchangePackageDtl data = new ExpExchangePackageDtl();
				// data.setPackageNo(rs.getString("package_no"));
				data.setPackageType(rs.getString("type"));
				data.setNo(rs.getString("no"));
				data.setQuantity(rs.getInt("quantity"));
				data.setStatus(rs.getString("status"));
				list.add(data);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		return list;
	}

	/**
	 * 查找一个礼包所有明细
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static Collection findByFk(Connection con, String packageNo)
			throws Exception {
		ExpExchangePackageForm param = new ExpExchangePackageForm();
		param.setPackageNo(packageNo);
		return findByFk(con, param);

	}

	/**
	 * 更新礼包状态 0-新建;1-审核;2-关闭
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void deleteMstByPk(Connection con,
			ExpExchangePackageForm param) throws Exception {

		PreparedStatement pstmt = null;

		try {

			String sql = "update EXP_EXCHANGE_PACKAGE_MST set status = 'N' "
					+ "where package_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getPackageNo());
			pstmt.execute();

		} catch (SQLException e) {
			log.error(e.getMessage());
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
	 * 根据礼包号删除明细 Y-有效;N-无效
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void deleteDtlByFk(Connection con,
			ExpExchangePackageForm param) throws Exception {

		PreparedStatement pstmt = null;

		try {

			String sql = "update EXP_EXCHANGE_PACKAGE_DTL set status = 'N' "
					+ "where package_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getPackageNo());
			pstmt.execute();

		} catch (SQLException e) {
			log.error(e.getMessage());
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
	 * 根据礼包号删除明细 Y-有效;N-无效
	 * 
	 * @param con
	 * @param param
	 * @throws Exception
	 */
	public static void removeDtlByFk(Connection con,
			ExpExchangePackageForm param) throws Exception {

		PreparedStatement pstmt = null;

		try {

			String sql = "delete from EXP_EXCHANGE_PACKAGE_DTL where package_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, param.getPackageNo());
			pstmt.execute();

		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}

	}
}

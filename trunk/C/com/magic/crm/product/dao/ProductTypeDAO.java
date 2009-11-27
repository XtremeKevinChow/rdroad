/*
 * Created on 2005-11-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.product.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
// import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.common.CommonPageUtil;
import com.magic.crm.product.form.ProductTypeForm;
import com.magic.crm.product.entity.ProductType;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProductTypeDAO implements Serializable {

	/** 表名 * */
	private static final String TBL_PRD_ITEM_CATEGORY = "PRD_ITEM_CATEGORY";

	/** 构造器 * */
	public ProductTypeDAO() {
	}

	/** 新增记录 * */
	public void insert(Connection con, ProductTypeForm pt) throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		int newID = 0;
		try {
			String sql = "SELECT SEQ_PRD_CATALOG_ID.NEXTVAL FROM DUAL";
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
			StringBuffer insertSql = new StringBuffer();
			insertSql.append("INSERT INTO PRD_ITEM_CATEGORY ");
			insertSql.append("(CATALOG_ID, PARENT_ID, CATALOG_LEVEL, ");
			insertSql.append("COMPANY_ID, IS_LEAF, DESCRIPTION, ");
			insertSql.append("CATALOG_NAME, CATALOG_CODE)");
			insertSql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt = con.prepareStatement(insertSql.toString());
			pstmt.setInt(1, newID);
			pstmt.setInt(2, pt.getParentType());
			pstmt.setInt(3, pt.getCategoryLevel());
			pstmt.setInt(4, pt.getCompanyID());
			pstmt.setInt(5, pt.getIsLeaf());
			pstmt.setString(6, pt.getDescription());
			pstmt.setString(7, pt.getName());
			pstmt.setString(8, pt.getCatalogCode());
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				pstmt.close();

		}
	}

	/** 显示详情 * */
	public ProductTypeForm showDetail(Connection con, int id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductTypeForm data = null;
		try {
			String sql = "SELECT ID, PARENT_TYPE, CATEGORY_LEVEL, COMPANY_ID, ";
			sql += "IS_LEAF_NAME, IS_LEAF, CATALOG_CODE,  DESCRIPTION, NAME, ";
			sql += "PARENT_TYPE_NAME FROM VW_PRD_ITEM_CATEGORY WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs != null) {
				data = new ProductTypeForm();
				while (rs.next()) {
					data.setID(rs.getInt("ID"));
					data.setParentType(rs.getInt("PARENT_TYPE"));
					data.setCategoryLevel(rs.getInt("CATEGORY_LEVEL"));
					data.setCompanyID(rs.getInt("COMPANY_ID"));
					data.setIsLeafName(rs.getString("IS_LEAF_NAME"));
					data.setIsLeaf(rs.getInt("IS_LEAF"));
					data.setCatalogCode(rs.getString("CATALOG_CODE"));
					data.setDescription(rs.getString("DESCRIPTION"));
					data.setName(rs.getString("NAME"));
					data.setParentTypeName(rs.getString("PARENT_TYPE_NAME"));

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
	/** 显示详情 * */
	public ProductTypeForm showDetail(Connection con, String code)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductTypeForm data = null;
		try {
			String sql = "SELECT ID, PARENT_TYPE, CATEGORY_LEVEL, COMPANY_ID, ";
			sql += "IS_LEAF_NAME, IS_LEAF, CATALOG_CODE,  DESCRIPTION, NAME, ";
			sql += "PARENT_TYPE_NAME FROM VW_PRD_ITEM_CATEGORY WHERE CATALOG_CODE = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();
			if (rs != null) {
				data = new ProductTypeForm();
				while (rs.next()) {
					data.setID(rs.getInt("ID"));
					data.setParentType(rs.getInt("PARENT_TYPE"));
					data.setCategoryLevel(rs.getInt("CATEGORY_LEVEL"));
					data.setCompanyID(rs.getInt("COMPANY_ID"));
					data.setIsLeafName(rs.getString("IS_LEAF_NAME"));
					data.setIsLeaf(rs.getInt("IS_LEAF"));
					data.setCatalogCode(rs.getString("CATALOG_CODE"));
					data.setDescription(rs.getString("DESCRIPTION"));
					data.setName(rs.getString("NAME"));
					data.setParentTypeName(rs.getString("PARENT_TYPE_NAME"));

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

	/** 修改记录 * */
	public void update(Connection con, ProductTypeForm pt) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String sQuery = "UPDATE PRD_ITEM_CATEGORY SET PARENT_ID=?, CATALOG_LEVEL=?, ";
			sQuery += "COMPANY_ID=?, IS_LEAF=?, DESCRIPTION=?, CATALOG_NAME=?, CATALOG_CODE=? WHERE CATALOG_ID=?";
			pstmt = con.prepareStatement(sQuery);

			pstmt.setInt(1, pt.getParentType());
			pstmt.setInt(2, pt.getCategoryLevel());
			pstmt.setInt(3, pt.getCompanyID());
			pstmt.setInt(4, pt.getIsLeaf());
			pstmt.setString(5, pt.getDescription());
			pstmt.setString(6, pt.getName());
			pstmt.setString(7, pt.getCatalogCode());
			pstmt.setInt(8, pt.getID());

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

	/** 删除记录 * */
	public void delete(Connection con, ProductTypeForm pt) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			String sQuery = "DELETE FROM PRD_ITEM_CATEGORY WHERE CATALOG_ID=?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, pt.getID());
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
	 * 判断是否有下级目录
	 * 
	 * @param con
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	public boolean hasChildren(Connection con, int ID) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flg = false;
		try {
			String sQuery = "SELECT PARENT_ID FROM PRD_ITEM_CATEGORY WHERE PARENT_ID = ?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, ID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flg = true;
			} else {
				flg = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return flg;
	}

	/**
	 * 判断该类型下面是否有产品
	 * 
	 * @param con
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	public boolean hasProduct(Connection con, int ID) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flg = false;
		try {
			String sQuery = "SELECT ITEM_ID FROM PRD_ITEMS WHERE CATEGORY_ID = ?";
			pstmt = con.prepareStatement(sQuery);
			pstmt.setInt(1, ID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flg = true;
			} else {
				flg = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return flg;
	}

	/** 查询所有记录 * */
	public Collection getList(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Collection ptCol = new ArrayList();

		try {
			String sQuery = "SELECT ID, PARENT_TYPE, CATEGORY_LEVEL, COMPANY_ID, ";
			sQuery += "IS_LEAF_NAME, IS_LEAF, CATALOG_CODE,  DESCRIPTION, NAME, ";
			sQuery += "PARENT_TYPE_NAME FROM VW_PRD_ITEM_CATEGORY ORDER BY PARENT_TYPE ";
			pstmt = con.prepareStatement(sQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				ProductType info = new ProductType();
				info.setID(rs.getInt("ID"));
				info.setParentType(rs.getInt("PARENT_TYPE"));
				info.setCategoryLevel(rs.getInt("CATEGORY_LEVEL"));
				info.setCompanyID(rs.getInt("COMPANY_ID"));
				info.setIsLeafName(rs.getString("IS_LEAF_NAME"));
				info.setIsLeaf(rs.getInt("IS_LEAF"));
				info.setCatalogCode(rs.getString("CATALOG_CODE"));
				info.setDescription(rs.getString("DESCRIPTION"));
				info.setName(rs.getString("NAME"));
				info.setParentTypeName(rs.getString("PARENT_TYPE_NAME"));
				ptCol.add(info);
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
		return ptCol;
	}
}
package com.magic.crm.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Syjun Wang
 * @version 1.0
 */

public final class DBOperation {
	/* 数据库连接 */
	public Connection conn = null;

	/* 分页数据 */
	private PageAttribute pageAttribute = null;
	
	/**
	 * 此方法供DBOperation在进行分页查询前由DAO调用设置分页数据
	 * 
	 * @param pageAttribute
	 *            分页数据
	 */
	public void setPageAttribute(PageAttribute pageAttribute) {
		this.pageAttribute = pageAttribute;
	}

	public DBOperation(Connection conn) {
		this.conn = conn;
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		conn.setAutoCommit(autoCommit);
	}

	public void commit() throws SQLException {
		conn.commit();
	}

	public void rollback() throws SQLException {
		conn.rollback();
	}

	public void close() throws SQLException {
		
		// 关闭数据库连接
		if(conn != null) conn.close();
	}

	/**
	 * execute query and retrive the record count
	 * 
	 * @param strSql
	 *            SQL string( select )
	 * @return result data
	 */
	public long getRecordCount(String strSql) throws SQLException {
		ResultSet rs = null;
		Statement st = null;
		long lCount = 0L;
		try {
			// database operation statement
			st = conn.createStatement();
			rs = st.executeQuery(strSql);
			while (rs != null && rs.next()) {
				lCount++;
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException ex) {
				}
			}
		}

		return lCount;
	}

	/**
	 * execute query and retrive the data
	 * 
	 * @param strSql
	 *            SQL string( select )
	 * @return result data
	 */
	public void queryHeaderData(String strSql, WebData targetData)
			throws SQLException {
		if (targetData == null)
			return;

		ResultSet rs = null;
		Statement st = null;
		WebData result = null;
		try {
			// database operation statement
			st = conn.createStatement();
			rs = st.executeQuery(strSql);
			readHeaderData(rs, targetData);
		} catch (SQLException ex) {
			throw new SQLException(ex.getMessage() + "\n" + strSql);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException ex) {
				}
			}
		}
	}

	/**
	 * execute query and retrive the data
	 * 
	 * @param strSql
	 *            SQL string( select )
	 * @return result data
	 */
	public void queryDetailData(String strSql, WebData targetData,
			boolean blFilter) throws SQLException {

		if (targetData == null)
			return;

		ResultSet rs = null;
		Statement st = null;
		WebData result = null;
		try {
			// database operation statement
			st = conn.createStatement();
			rs = st.executeQuery(strSql);
			readDetailData(rs, targetData, blFilter);
		} catch (SQLException ex) {
			throw new SQLException(ex.getMessage() + "\n" + strSql);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException ex) {
				}
			}
		}
	}

	/**
	 * delete records from database table
	 * 
	 * @param strSql
	 *            SQL string ( delete )
	 * @return execute result -1: error other: normal(the count of the data rows
	 *         modified )
	 */
	public int delete(WebData ds) throws SQLException {
		String strTable = ds.getTable();
		String strCondition = ds.getSubWhere();
		// return value
		int nRetValue = -1;
		Statement st = null;
		try {
			// database operation statement
			st = conn.createStatement();
			// update
			nRetValue = st.executeUpdate("DELETE FROM " + strTable + " WHERE "
					+ strCondition);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException ex) {
				}
			}
		}

		return nRetValue;
	}

	/**
	 * insert records from database table.
	 * 
	 * set values into argument WebData first by method
	 * WebData.addHeaderData(Table_Field_Name, Field_Value)
	 * 
	 * All the fields that not exist in WebData will be set to null or database
	 * default value
	 * 
	 * @param strSql
	 *            SQL string ( INSERT )
	 * 
	 * @return execute result -1: error other: normal(the count of the data rows
	 *         modified )
	 */
	public int insert(WebData ds) throws SQLException {
		String strTable = ds.getTable();
		// return value
		int nRetValue = -1;
		Statement st = null;
		try {
			// sub sql statement: fields chain and values chain
			String strFields = null;
			String strValues = null;
			String strField = null;
			Enumeration er = ds.getHeaderKey();
			// not fields info
			if (er == null || !er.hasMoreElements()) {
				throw new SQLException(
						"there is no any data to be inserted into database.");
			}

			while (er.hasMoreElements()) {
				if (strFields == null) {
					strFields = "";
					strValues = "";
				} else {
					strFields += ", ";
					strValues += ", ";
				}

				strField = (String) er.nextElement();
				strFields += strField;

				// 使用Hashtable存储，ds.getHeaderData(strField)不可能为null
				strValues += getSQLValue(ds.getHeaderData(strField));
			} // end loop

			// INSERT
			nRetValue = executeUpdate("INSERT INTO " + strTable + "("
					+ strFields + ") VALUES(" + strValues + ")");
		} catch (SQLException ex) {
			throw ex;
		} finally {
		}

		return nRetValue;
	}

	/**
	 * Modify record
	 * 
	 * @param strSql
	 *            SQL string ( MODIFY )
	 * 
	 * @return execute result -1: error other: normal(the count of the data rows
	 *         modified )
	 */
	public int modify(WebData ds) throws Exception {
		String strTable = ds.getTable();
		String strCondition = ds.getSubWhere();

		// has sub-where?
		if (strCondition == null || strCondition.trim().length() == 0)
			throw new Exception(
					"Sub-where statement expected in DBOpertion.modify(WebData ds)!");

		// return value
		int nRetValue = -1;
		try {
			// update body
			String strBody = null;
			String strField = null;
			String strValue = null;
			Enumeration er = ds.getHeaderKey();
			// not fields info
			if (er == null || !er.hasMoreElements()) {
				throw new Exception(
						"there is no any data to be inserted into database.");
			}

			while (er.hasMoreElements()) {
				if (strBody == null) {
					strBody = "";
				} else {
					strBody += ", ";
				}

				strField = (String) er.nextElement();

				// 使用Hashtable存储，ds.getHeaderData(strField)不可能为null
				strBody += strField + " = "
						+ getSQLValue(ds.getHeaderData(strField));

			} // end loop

			// update
			nRetValue = executeUpdate("UPDATE " + strTable + " SET " + strBody
					+ " WHERE " + strCondition);
		} catch (SQLException ex) {
			throw ex;
		} finally {
		}

		return nRetValue;
	}

	/**
	 * modify the data in database
	 * 
	 * @param strSql
	 *            SQL string ( delete, insert and update )
	 * @return execute result -1: error other: normal(the count of the data rows
	 *         modified )
	 */
	public int executeUpdate(String strSql) throws SQLException {
		// return value
		int nRetValue = -1;
		Statement st = null;

		try {
			// database operation statement
			st = conn.createStatement();
			// update
			// System.out.println(strSql);
			nRetValue = st.executeUpdate(strSql);
		} catch (SQLException ex) {
			throw new SQLException(ex.getMessage() + "\n" + strSql);
		} finally {
			// close Statement
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
		}

		return nRetValue;
	}

	/**
	 * read header&footer-displaying data and restore
	 * 
	 * @param sourceData
	 *            data reader
	 * @param targetData
	 *            data container
	 */
	private void readHeaderData(ResultSet sourceData, WebData targetData)
			throws SQLException {

		// no data
		if (sourceData == null || targetData == null) {
			return;
		}

		// read column name
		ResultSetMetaData col = sourceData.getMetaData();
		String arrColName[] = new String[col.getColumnCount()];

		for (int i = 0; i < arrColName.length; i++) {
			arrColName[i] = (col.getColumnName(i + 1)).toUpperCase();
		}

		String strValue = null;
		DateFormat df = DateFormat.getDateTimeInstance();
		java.util.Date time = null;

		// read data and save
		if (sourceData.next()) {
			// save, if the arrColName[i] is null, an exception will occur
			for (int j = 0; j < arrColName.length; j++) {
				switch (col.getColumnType(j + 1)) {
				case Types.DATE: // date: yyyy-mm-dd
					time = sourceData.getDate(arrColName[j]);
					if (time == null) {
						strValue = "";
					} else {
						strValue = time.toString();
					}
					break;
				case Types.TIME: // time: yyyy-mm-dd hh:mm:ss
				case Types.TIMESTAMP: // time: yyyy-mm-dd hh:mm:ss
					time = sourceData.getTimestamp(arrColName[j]);
					if (time == null) {
						strValue = "";
					} else {
						strValue = df.format(time);
					}

					break;
				default:
					strValue = sourceData.getString(arrColName[j]);
					if (strValue == null)
						strValue = "";
					break;
				}
				// store
				targetData.addHeaderData(arrColName[j], strValue);
			}
		}
	}

	/**
	 * filter the data from result set with the special page no. store the data
	 * filtered into SysData instance
	 * 
	 * Notice: if the boolFilter is true, that is, the next page to be display
	 * must be a data-list page, this method can split all the datas read, and
	 * transfer only those datas for certain page to the client
	 * 
	 * otherwise, this method will not filter the records, just restore the
	 * datas and page no. into SysData instance
	 * 
	 * @param sourceData
	 *            result set
	 * @param targetData
	 *            data container(include a parameter: page no)
	 * @param boolFilter
	 *            need to filter data?
	 */
	private void readDetailData(ResultSet sourceData, WebData targetData,
			boolean blFilter) throws SQLException {
		if (sourceData == null || targetData == null) {
			return;
		}

		int nStart = 0;
		int nEnd = 0;
		// 要求分页，但是没有给予分页参数
		if (blFilter) {
			if (pageAttribute == null) {
				throw new SQLException("要求分页，但是没有给予分页参数，"
						+ "请把WebForm中的分页参数传入DBOperation实例！");
			} else {
				nStart = pageAttribute.getStartPosition();
				nEnd = nStart + pageAttribute.getMaxResults() - 1;
			}
		}

		int i = 0;

		// read column name
		ResultSetMetaData col = sourceData.getMetaData();
		String arrColName[] = new String[col.getColumnCount()];

		// collumn name
		for (; i < arrColName.length; i++) {
			arrColName[i] = col.getColumnName(i + 1).toUpperCase();
		}

		Object objValue = null;
		DateFormat df = DateFormat.getDateTimeInstance();
		java.util.Date time = null;

		for (i = 0; sourceData.next(); i++) {
			// if filter
			if (blFilter) {
				if (i < nStart || i > nEnd)
					continue;
			}

			// read data and store
			Hashtable row = new Hashtable();
			// save, if the arrColName[i] is null, an exception will occur
			for (int j = 0; j < arrColName.length; j++) {
				switch (col.getColumnType(j + 1)) {
				case Types.DATE: // date: yyyy-mm-dd
				/*
				 * time = sourceData.getDate(arrColName[j]); if (time == null) {
				 * strValue = ""; } else { strValue = time.toString(); } break;
				 */
				case Types.TIME: // time: yyyy-mm-dd hh:mm:ss
				case Types.TIMESTAMP: // time: yyyy-mm-dd hh:mm:ss
					time = sourceData.getTimestamp(arrColName[j]);
					if (time == null) {
						objValue = "";
					} else {
						objValue = df.format(time);
					}

					break;
				case Types.CLOB:
					objValue = sourceData.getClob(arrColName[j]);
					break;
				case Types.BLOB:
					objValue = sourceData.getBlob(arrColName[j]);
					break;
				default:
					objValue = sourceData.getString(arrColName[j]);
					// System.out.println("Type: " + col.getColumnType(j + 1) +
					// " Field: " + arrColName[j] + " Value: " +objValue);
					break;
				}

				if (objValue == null)
					objValue = "";
				row.put(arrColName[j], objValue);
			}

			targetData.addRowData(row);
		}

		// 要分页，保存总记录数
		if(blFilter) pageAttribute.setRecordCount(i);
	}

	/**
	 * 根据传入的数据库字段值类型来得到DML的SQL值
	 * 
	 * @param obj
	 * @return
	 */
	private String getSQLValue(Object obj) throws SQLException {
		if (obj instanceof String) { // 字符串
			return "'" + LogicUtility.getDataString((String) obj) + "'";
		} else if (obj instanceof Integer || obj instanceof Float
				|| obj instanceof Double || obj instanceof Short
				|| obj instanceof Byte) { // 数值
			return obj.toString();
		} else if (obj instanceof Date) { // 日期
			return "to_date('" + LogicUtility.getDateAsString((Date) obj)
					+ "', 'yyyy-MM-dd')";
		} else {
			throw new SQLException(
					"An unkown type of field value is encountered.");
		}
	}
}
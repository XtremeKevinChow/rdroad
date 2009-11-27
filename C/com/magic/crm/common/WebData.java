package com.magic.crm.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Syjun Wang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WebData {
	/* datas displayed in header and footer */
	private Hashtable headerFooter = null;

	/* datas to be displayed in detialed list */
	private ArrayList detailData = null;

	/* every row data/record in data container */
	private Hashtable tempData = null;

	/* message */
	private String strMessage = null;
	
	/* 表名 */
	private String strTable = null;
	
	/* 查询条件 */
	private String strSubWhere = null;

	private int nIndex = -1;
	
	/**
	 * add a data to be displayed in header and footer and store it into
	 * hashtable
	 * 
	 * @param strKey
	 *            name
	 * @param strValue
	 *            value
	 */
	public void addHeaderData(String strKey, Object objValue) {
		// store
		if (strKey != null && objValue != null) {
			// null? intialize hashtable
			if (headerFooter == null) {
				headerFooter = new Hashtable();
			}

			headerFooter.put(strKey.toUpperCase(), objValue);
		}
	}

	/**
	 * Get the table for modify and delete operation
	 */
	public String getTable() {
		return strTable;
	}

	/**
	 * Set the table for modify and delete operation
	 * 
	 * @param strTable
	 *            table name
	 */
	public void setTable(String strTable) {
		// set table
		this.strTable = strTable;
	}

	/**
	 * Get the table for modify and delete operation
	 */
	public String getSubWhere() {
		return strSubWhere;
	}

	/**
	 * Set the table for modify and delete operation
	 * 
	 * @param strTable
	 *            table name
	 */
	public void setSubWhere(String strCondition) {
		strSubWhere = strCondition;
	}

	/**
	 * get a header&footer-displaying data from the data container
	 * 
	 * @param strKey
	 *            name
	 * @return Object value
	 */
	public Object getHeaderData(String strKey) {
		// no data container or unvalid name, no data
		if (headerFooter == null || strKey == null) {
			return null;
		} else { // has data container
			return headerFooter.get(strKey.toUpperCase());
		}
	}

	/**
	 * get a header&footer-displaying data from the data container
	 * 
	 * @param strKey
	 *            name
	 * @return Object value
	 */
	public String getHeaderString(String strKey) {
		// no data container or unvalid name, no data
		Object obj = getHeaderData(strKey);
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * get a header&footer-displaying data. Date type
	 * yyyy-mm-dd
	 * 
	 * @param strKey
	 *            name
	 * @return Object value
	 */
	public String getHeaderDate(String strKey) {
		// no data container or unvalid name, no data
		String str = getHeaderString(strKey);
		int nIndex = str.indexOf(" ");
		if (nIndex > 0)
			str = str.substring(0, nIndex);

		return str;
	}

	/**
	 * get an integer header&footer-displaying data from the data container
	 * 
	 * @param strKey
	 *            name
	 * @return Object value
	 */
	public int getHeaderInt(String strKey) {
		return LogicUtility.parseInt(getHeaderString(strKey), 0);
	}
	
	/**
	 * get a double header&footer-displaying data from the data container
	 * 
	 * @param strKey
	 *            name
	 * @return Object value
	 */
	public double getHeaderDouble(String strKey) {
		return LogicUtility.parseDouble(getHeaderString(strKey), 0.0);
	}	
	
	/**
	 * 得到所有在HeaderFooter部分的字段名
	 * @return
	 */
	public Enumeration getHeaderKey() {
		return (headerFooter == null)?null:headerFooter.keys();
	}

	/**
	 * store a detail-displaying record into detail-container
	 * 
	 * @param data
	 *            a detail-displaying record
	 */
	public void addRowData(Hashtable data) {
		if (detailData == null) {
			detailData = new ArrayList();
		}

		detailData.add(data);
	}
	
	/**
	 * clear headerfooter-displaying records
	 * 
	 * @param data
	 *            a headerfooter-displaying record
	 */
	public void clearHeaderData() {
		if (headerFooter != null) {
			headerFooter.clear();
		}
	}

	/**
	 * clear detail-displaying records
	 * 
	 * @param data
	 *            a detail-displaying record
	 */
	public void clearDetailData() {
		if (detailData != null) {
			detailData.clear();
		}
		// rewind cursor
		nIndex = -1;
	}

	/**
	 * test whether next record exists, if it does, move to next value
	 * 
	 * @return true has next record false no next record
	 */
	public boolean next() {
		if (detailData == null
			|| detailData.size() == 0
			|| nIndex + 1 >= detailData.size()) {
			return false;
		} else {
			nIndex++;
			tempData = (Hashtable) detailData.get(nIndex);
			return true;
		}
	}
	
	/**
	 * get detail data from current record
	 * 
	 * @param strKey
	 *            name
	 * @return Object value
	 */
	public Object getDetailData(String strKey) {
		if (tempData == null || strKey == null) {
			return null;
		}
		
		return tempData.get(strKey.toUpperCase());
	}
	
	/**
	 * get detail value from current record
	 * 
	 * @param strKey
	 *            value name
	 * @return value
	 */
	public String getDetailString(String strKey) {
		Object obj = getDetailData(strKey);

		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * get detail data. Date type
	 * @param strKey name
	 * @return Date value
	 */
	public String getDetailDate(String strKey) {
		// no data container or unvalid name, no data
		String str = getDetailString(strKey);
		int nIndex = str.indexOf(" ");
		if (nIndex > 0)
			str = str.substring(0, nIndex);

		return str;
	}
	
	public String getDetailTime(String strKey) {
		// no data container or unvalid name, no data
		String str = getDetailString(strKey);
		return str;
	}
	

	/**
	 * get an integer value from current record
	 * 
	 * @param strKey
	 *            value name
	 * @return value
	 */
	public int getDetailInt(String strKey) {
		return LogicUtility.parseInt(getDetailString(strKey), 0);
	}
	
	/**
	 * get a double value from current record
	 * 
	 * @param strKey
	 *            value name
	 * @return value
	 */
	public double getDetailDouble(String strKey) {
		return LogicUtility.parseDouble(getDetailString(strKey), 0.0);
	}

	/**
	 * store message from server
	 * 
	 * @param strMessage
	 *            message
	 */
	public void setMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	/**
	 * retrieve message from server.
	 */
	public String getMessage() {
		return strMessage;
	}

	/**
	 * test whether has message
	 * 
	 * @return
	 */
	public boolean hasMessage() {
		return (strMessage != null);
	}

	/**
	 * record size
	 * 
	 * @return record size
	 */
	public int size() {
		return (detailData != null) ? detailData.size() : 0;
	}
}
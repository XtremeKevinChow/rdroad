package com.magic.crm.common;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Water
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class LogicUtility {
	/* number format */
	private static NumberFormat nf = NumberFormat
			.getNumberInstance(Locale.CHINA);

	/**
	 * parse an integer value from a string, if the string can't be parsed, it
	 * will return the default value
	 * 
	 * @param strValue
	 *            string value
	 * @param nDefaultValue
	 *            default integer value
	 * @return
	 */
	public static int parseInt(String strValue, int nDefaultValue) {
		// parse
		try {
			nDefaultValue = Integer.parseInt(strValue);
		} catch (Exception ex) {
		}

		return nDefaultValue;
	}

	/**
	 * parse a double value from a string, if the string can't be parsed, it
	 * will return the default value
	 * 
	 * @param strValue
	 *            string value
	 * @param dblDefaultValue
	 *            default double value
	 * @return
	 */
	public static double parseDouble(String strValue, double dblDefaultValue) {
		// parse
		try {
			dblDefaultValue = Double.parseDouble(strValue);
		} catch (Exception ex) {
		}

		return dblDefaultValue;
	}

	/**
	 * yyyy-mm-dd HH24:mi:ss
	 * 
	 * @return
	 */
	public static String getTimeAsString() {
		Calendar objDate = Calendar.getInstance();
		String strDate = objDate.get(Calendar.YEAR) + "-"
				+ (objDate.get(Calendar.MONTH) + 1) + "-"
				+ objDate.get(Calendar.DATE) + " "
				+ objDate.get(Calendar.HOUR_OF_DAY) + ":"
				+ objDate.get(Calendar.MINUTE) + ":"
				+ objDate.get(Calendar.SECOND);

		objDate = null;

		return strDate;
	}

	/**
	 * Returns the time in milliseconds
	 * 
	 * @param strTime
	 *            time (yyyy-mm-dd or yyyy/dd/mm)
	 * 
	 * @return milliseconds time
	 */
	public static long getTimeInMillis(String strTime) {
		Date date = parseDateFromString(strTime);
		return (date == null) ? 0L : date.getTime();
	}

	/**
	 * 得到yyyy-MM-dd格式的日期字符串
	 * 
	 * @param date
	 *            传入的日期对象
	 * @return
	 */
	public static String getDateAsString(Date date) {
		Calendar objDate = Calendar.getInstance();
		objDate.setTime(date);

		String strDate = objDate.get(Calendar.YEAR) + "-";

		if (objDate.get(Calendar.MONTH) + 1 < 10)
			strDate += "0";

		strDate += (objDate.get(Calendar.MONTH) + 1) + "-";

		if (objDate.get(Calendar.DATE) < 10)
			strDate += "0";

		strDate += objDate.get(Calendar.DATE);

		objDate = null;

		return strDate;
	}

	/**
	 * @param strDate
	 *            yyyy-MM-dd格式的日期字符串
	 * @return 日期类型的实例
	 */
	public static Date parseDateFromString(String strDate) {
		try {
			strDate = strDate.trim();
			// 确定分割符是"-"还是"/"
			String str = "-";
			int nIndex = strDate.indexOf(str);
			if (nIndex < 0) {
				str = "/";
				nIndex = strDate.indexOf(str);
			}
			// 0位置到第一个分割符位置：年
			int nYear = Integer.parseInt(strDate.substring(0, nIndex));
			// 截去 年
			strDate = strDate.substring(nIndex + 1);
			// 取第二个分割符位置
			nIndex = strDate.indexOf(str);
			// 第一个分割符到第二个分割符之间：月
			int nMonth = Integer.parseInt(strDate.substring(0, nIndex));
			if (--nMonth < 0) {
				nYear--;
				nMonth = 11;
			}
			// 第二个分割符之后：日
			int nDay = Integer.parseInt(strDate.substring(nIndex + 1));
			// 产生日历对象
			Calendar objDate = Calendar.getInstance();
			objDate.clear();
			// 重新设置日历年月日
			objDate.set(nYear, nMonth, nDay);
			// 返回日历Date类型对象
			return objDate.getTime();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 得到yyyy-MM-dd格式的日期字符串
	 * 
	 * @return
	 */
	public static String getDateAsString() {
		Calendar objDate = Calendar.getInstance();
		String strDate = objDate.get(Calendar.YEAR) + "-";

		if (objDate.get(Calendar.MONTH) + 1 < 10)
			strDate += "0";

		strDate += (objDate.get(Calendar.MONTH) + 1) + "-";

		if (objDate.get(Calendar.DATE) < 10)
			strDate += "0";

		strDate += objDate.get(Calendar.DATE);

		objDate = null;

		return strDate;
	}

	/**
	 * convert String for database operation
	 * 
	 * @param strValue
	 *            string
	 * @return String converted <
	 */
	public static String getDataString(String strValue) {

		StringBuffer stbResult = new StringBuffer("");

		// not null
		if (strValue != null && strValue.length() > 0) {
			for (int i = 0; i < strValue.length(); i++) {
				if (strValue.charAt(i) == '\'') {
					stbResult.append("\'\'");
				} else {
					stbResult.append(strValue.charAt(i));
				}
			} // end for

			return stbResult.toString();
		} else { // is null
			return "";
		}
	}

	/**
	 * Duplicate the String specified with multiple times
	 * 
	 * @param strValue
	 *            String specified
	 * @param nUnit
	 *            unit times
	 * @return String duplicated
	 */
	public static String duplicateString(String strValue, int nUnit) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < nUnit; i++) {
			sb.append(strValue);
		}

		return sb.toString();
	}

	/**
	 * 使用MD5加密字符串
	 * 
	 * @param strValue
	 *            要加密的字符串
	 * @return encrypt or disencrypt password
	 */
	public static String getPassword(String strValue) {
		if (strValue == null)
			strValue = "";

		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte byEncrypt[] = md.digest(strValue.getBytes());

			for (int i = 0; i < byEncrypt.length; i++) {
				sb.append(Integer.toHexString(byEncrypt[i] & 0xFFFF));
			}
		} catch (Exception ex) {
		}

		return sb.toString();
	}

	/**
	 * Constructs a string tokenizer for the specified string. The characters in
	 * the <code>delim</code> argument are the delimiters for separating
	 * tokens. Delimiter characters themselves will not be treated as tokens.
	 * 
	 * @param strValue
	 *            a string to be parsed.
	 * @param strDelim
	 *            the delimiters.
	 */
	public static String[] splitString(String strValue, String strDelim) {
		if (strValue == null || strDelim == null)
			return null;
		java.util.StringTokenizer st = new java.util.StringTokenizer(strValue,
				strDelim);
		String arrResult[] = new String[st.countTokens()];

		for (int i = 0; i < arrResult.length; i++) {
			arrResult[i] = st.nextToken();
		}

		return arrResult;
	}

	/**
	 * 用字符串表示格式化后的双精度数值，使用四舍五入
	 * 
	 * @param dbValue
	 *            双精度数值
	 * @param nFraction
	 *            小数位数
	 * @return
	 */
	public static String formatNumber(double dbValue, int nFraction) {
		nf.setMaximumFractionDigits(nFraction);
		return nf.format(dbValue);
	}

	/**
	 * 用字符串表示格式化后的双精度数值，使用四舍五入
	 * 
	 * @param strValue
	 *            字符串性表示的双精度数值
	 * @param nFraction
	 *            小数位数
	 * @return
	 */
	public static String formatNumber(String strValue, int nFraction) {
		return formatNumber(parseDouble(strValue, 0.0), nFraction);
	}

	/**
	 * 读取Oracle的Clob字段，以字符串形式返回其中的值
	 * 
	 * @param objClob
	 * @return
	 * @throws Exception
	 */
	public static String getClobString(Object objClob) throws Exception {
		if (objClob == null)
			return null;
		// Method
		StringBuffer sb = new StringBuffer();
		Method mtd = objClob.getClass().getMethod("getCharacterStream", null);
		Reader reader = (Reader) mtd.invoke(objClob, null);

		BufferedReader read = new BufferedReader(reader);
		char arr[] = new char[512];
		int nLen = read.read(arr);

		while (nLen > 0) {
			sb.append(arr, 0, nLen);
			nLen = read.read(arr);
		}
		read.close();

		return sb.toString();
	}
}
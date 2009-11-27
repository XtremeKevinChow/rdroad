/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.common;

import java.util.HashMap;

/**
 * @author user1 TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class CharacterFormat {

	/** 订单类型映射表 * */
	private static HashMap orderTypeMap = null;

	static {
		orderTypeMap = new HashMap();
		orderTypeMap.put("0", "P");// 邮购订单
		orderTypeMap.put("1", "T");// 电话订单
		orderTypeMap.put("2", "W");// 网站订单
		orderTypeMap.put("3", "G");// 团体会员订单
		orderTypeMap.put("4", "F");// 传真订单
		orderTypeMap.put("5", "E");// Email订单
		orderTypeMap.put("6", "X");// 信件订单
		orderTypeMap.put("7", "M");// 短信订单
		orderTypeMap.put("8", "O");// 外呼订单
		orderTypeMap.put("9", "B");// 回单

	}

	/*
	 * 判断字符串是否全是数字
	 */
	public static boolean isNum(String isnum) {
		boolean a = false;
		int x = 0;
		int y = 0;
		char c[] = isnum.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] > 47 && c[i] < 58) { // ASCII 数字是48---57
				x = 1;
			} else {
				y = 1;
			}
		}
		if (y == 0) {
			a = true;
		}
		return a;
	}

	/*public static String isNum(String isnum, int j) {
		System.out.println(isnum+"***"+isnum.length());
		if (isnum == null || isnum.trim().length() < j) {
			return null;
		}
		
		String str = null;
		
		String outStr = null;
		// 防止参数是空值截取8位后溢出

		for (int i = 0; i < isnum.length() - j+1; i++) {
			
			 * 获得一个j位数的字符串
			 
			str = isnum.substring(i, i + j);
			System.out.println(str);
			
			 * 判断这个字符串是否是数字
			 
			if (CharacterFormat.isNum(str)) {// j 位数字
				if (j  == 8) { //截取会员
					outStr = str;
					break;
				} else { //截取订单
					
					 * 判断是否是网上订单,订单号前有字母w就认为是网上订单
					 
					
					outStr = analyseOrderType(isnum.toUpperCase(), str);
					
					break; 
					
				}
			}
		}
		return outStr;
	}
	*/
	/**
	 * 判断输入的字符串中是否包含j位数字串
	 * @param inputStr:输入字符串
	 * @param len:截取位数
	 * @return 如果有立刻返回第一个符合条件的数字串；如果没有就返回null
	 */
	public static String analyseNumber(String inputStr, int len) {
		
		if (inputStr == null || inputStr.length() < len) {
			return null;
		}
		String str = null;
		String outStr = null;
		// 防止参数是空值截取8位后溢出
		for (int i = 0; i < inputStr.length() - len + 1; i ++) {
			
			//获得一个len位数的字符串
			str = inputStr.substring(i, i + len);
			//System.out.println(str);
			//判断这个字符串是否是数字
			 
			if (CharacterFormat.isNum(str)) {// len 位数字
				outStr = str;
				break;
			
			}
		}
		return outStr;
	}

	
	
	/**
	 * 定义一个小方法 判断字符串是否包含预定义的 字符+otherStr，如果是返回 字符+otherStr，不是返回 otherStr
	 * 
	 * @param inputStr:
	 *            要分析的字符串
	 * @param otherStr:
	 *            要拼装返回的字符串
	 * @return
	 */
	public static String analyseOrderType(String inputStr, String otherStr) {
		String rtn = null;
		for (int i = 0; i < orderTypeMap.size(); i++) {
			String sign = (String) orderTypeMap.get(String.valueOf(i));

			if (inputStr.indexOf(sign + otherStr) != -1) { // 找到
				rtn = sign + otherStr;
				break;
			}
		}
		if (rtn == null) {
			if (inputStr.indexOf(otherStr) != -1) { //能够匹配
				for (int i = 0; i < orderTypeMap.size(); i++) {
					String sign = (String) orderTypeMap.get(String.valueOf(i));
					if (inputStr.indexOf(sign) != -1) { //能匹配字母
						rtn = sign + otherStr;
						break;
					}
				}
				if (rtn == null) {
					//字母不能匹配默认是W
					if (inputStr.indexOf("七" + otherStr) != -1) {
						rtn = "T" + otherStr;
					} else {
						rtn = "W" + otherStr;
					}
				}
			}
		}
		return (rtn);
	}

	/**
	 * 根据特殊字符分离出中文字
	 * 
	 * @param inputStr
	 * @return
	 */
	public static String separateString(String inputStr) {
		String subName = inputStr;
		if (inputStr != null && !"".equals(inputStr)) {
			int len = inputStr.length();
			for (int i = 0; i < len; i++) {
				char c = inputStr.charAt(i);
				if (c <= 128 || c == '＋' || c == '　' || c == '［' || c == '（'
						|| c == '＊' || c == '｛') { // 中文字的ASCII码大于128
					subName = inputStr.substring(0, i);
					break;
				}
			}

			if ("".equals(subName)) {
				for (int i = len - 1; i >= 0; i--) {
					char c = inputStr.charAt(i);
					if (c <= 128 || c == '＋' || c == '　' || c == '［'
							|| c == '（' || c == '＊' || c == '｛') {
						subName = inputStr.substring(i + 1);
						break;
					}

				}
			}

		}
		return subName;
	}

	private static char[] specChar = new char[] { '＋', '　', '［', '］', '（', '）',
			'＊', '｛', '｝' };
}
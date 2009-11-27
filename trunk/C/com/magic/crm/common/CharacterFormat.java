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

	/** ��������ӳ��� * */
	private static HashMap orderTypeMap = null;

	static {
		orderTypeMap = new HashMap();
		orderTypeMap.put("0", "P");// �ʹ�����
		orderTypeMap.put("1", "T");// �绰����
		orderTypeMap.put("2", "W");// ��վ����
		orderTypeMap.put("3", "G");// �����Ա����
		orderTypeMap.put("4", "F");// ���涩��
		orderTypeMap.put("5", "E");// Email����
		orderTypeMap.put("6", "X");// �ż�����
		orderTypeMap.put("7", "M");// ���Ŷ���
		orderTypeMap.put("8", "O");// �������
		orderTypeMap.put("9", "B");// �ص�

	}

	/*
	 * �ж��ַ����Ƿ�ȫ������
	 */
	public static boolean isNum(String isnum) {
		boolean a = false;
		int x = 0;
		int y = 0;
		char c[] = isnum.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] > 47 && c[i] < 58) { // ASCII ������48---57
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
		// ��ֹ�����ǿ�ֵ��ȡ8λ�����

		for (int i = 0; i < isnum.length() - j+1; i++) {
			
			 * ���һ��jλ�����ַ���
			 
			str = isnum.substring(i, i + j);
			System.out.println(str);
			
			 * �ж�����ַ����Ƿ�������
			 
			if (CharacterFormat.isNum(str)) {// j λ����
				if (j  == 8) { //��ȡ��Ա
					outStr = str;
					break;
				} else { //��ȡ����
					
					 * �ж��Ƿ������϶���,������ǰ����ĸw����Ϊ�����϶���
					 
					
					outStr = analyseOrderType(isnum.toUpperCase(), str);
					
					break; 
					
				}
			}
		}
		return outStr;
	}
	*/
	/**
	 * �ж�������ַ������Ƿ����jλ���ִ�
	 * @param inputStr:�����ַ���
	 * @param len:��ȡλ��
	 * @return ��������̷��ص�һ���������������ִ������û�оͷ���null
	 */
	public static String analyseNumber(String inputStr, int len) {
		
		if (inputStr == null || inputStr.length() < len) {
			return null;
		}
		String str = null;
		String outStr = null;
		// ��ֹ�����ǿ�ֵ��ȡ8λ�����
		for (int i = 0; i < inputStr.length() - len + 1; i ++) {
			
			//���һ��lenλ�����ַ���
			str = inputStr.substring(i, i + len);
			//System.out.println(str);
			//�ж�����ַ����Ƿ�������
			 
			if (CharacterFormat.isNum(str)) {// len λ����
				outStr = str;
				break;
			
			}
		}
		return outStr;
	}

	
	
	/**
	 * ����һ��С���� �ж��ַ����Ƿ����Ԥ����� �ַ�+otherStr������Ƿ��� �ַ�+otherStr�����Ƿ��� otherStr
	 * 
	 * @param inputStr:
	 *            Ҫ�������ַ���
	 * @param otherStr:
	 *            Ҫƴװ���ص��ַ���
	 * @return
	 */
	public static String analyseOrderType(String inputStr, String otherStr) {
		String rtn = null;
		for (int i = 0; i < orderTypeMap.size(); i++) {
			String sign = (String) orderTypeMap.get(String.valueOf(i));

			if (inputStr.indexOf(sign + otherStr) != -1) { // �ҵ�
				rtn = sign + otherStr;
				break;
			}
		}
		if (rtn == null) {
			if (inputStr.indexOf(otherStr) != -1) { //�ܹ�ƥ��
				for (int i = 0; i < orderTypeMap.size(); i++) {
					String sign = (String) orderTypeMap.get(String.valueOf(i));
					if (inputStr.indexOf(sign) != -1) { //��ƥ����ĸ
						rtn = sign + otherStr;
						break;
					}
				}
				if (rtn == null) {
					//��ĸ����ƥ��Ĭ����W
					if (inputStr.indexOf("��" + otherStr) != -1) {
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
	 * ���������ַ������������
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
				if (c <= 128 || c == '��' || c == '��' || c == '��' || c == '��'
						|| c == '��' || c == '��') { // �����ֵ�ASCII�����128
					subName = inputStr.substring(0, i);
					break;
				}
			}

			if ("".equals(subName)) {
				for (int i = len - 1; i >= 0; i--) {
					char c = inputStr.charAt(i);
					if (c <= 128 || c == '��' || c == '��' || c == '��'
							|| c == '��' || c == '��' || c == '��') {
						subName = inputStr.substring(i + 1);
						break;
					}

				}
			}

		}
		return subName;
	}

	private static char[] specChar = new char[] { '��', '��', '��', '��', '��', '��',
			'��', '��', '��' };
}
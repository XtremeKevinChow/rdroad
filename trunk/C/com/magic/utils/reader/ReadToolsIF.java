/*
 * Created on 2005-11-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.utils.reader;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ReadToolsIF {
	
	/** ��ȡmap��Ӧ��keyֵ **/
	public static final String R_DATA = "r_data";

	/** д���¼map��Ӧ��keyֵ **/
	public static final String W_DATA = "w_data";
	
	/**
	 * ��ȡ�ļ�����ʵ�ֵĹ��ýӿڣ��ļ����Ϳ����� XLS, TXT, CSV, XML...
	 */
	public void read();
	
	/**
	 * д��¼��map 
	 * @param map
	 * @param cols: ������
	 */
	public void write(java.util.HashMap map, int cols);
	
	/**
	 * ���е��ϴ�������
	 * @return
	 */
	public java.util.HashMap getData();
	
	/**
	 * �����ļ���
	 * @param file
	 */
	public void setFile(String file);
	
	/**
	 * ���ò������ݿ��쳣ʱ���ļ���
	 * @param file
	 */
	public void setErrorFile(String errorFile);
	
	/**
	 * �õ��ϴ��ļ���������
	 * @return
	 */
	public int getTotalRows();
	
	/**
	 * �õ��ϴ��ļ���������
	 * @return
	 */
	public int getTotalCols();
	
}

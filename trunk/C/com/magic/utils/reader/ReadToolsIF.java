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
	
	/** 读取map对应的key值 **/
	public static final String R_DATA = "r_data";

	/** 写入记录map对应的key值 **/
	public static final String W_DATA = "w_data";
	
	/**
	 * 读取文件必须实现的公用接口，文件类型可以是 XLS, TXT, CSV, XML...
	 */
	public void read();
	
	/**
	 * 写记录到map 
	 * @param map
	 * @param cols: 总行数
	 */
	public void write(java.util.HashMap map, int cols);
	
	/**
	 * 所有的上传得数据
	 * @return
	 */
	public java.util.HashMap getData();
	
	/**
	 * 设置文件名
	 * @param file
	 */
	public void setFile(String file);
	
	/**
	 * 设置插入数据库异常时的文件名
	 * @param file
	 */
	public void setErrorFile(String errorFile);
	
	/**
	 * 得到上传文件的总行数
	 * @return
	 */
	public int getTotalRows();
	
	/**
	 * 得到上传文件的总列数
	 * @return
	 */
	public int getTotalCols();
	
}

/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.utils.reader;

import java.util.HashMap;
import java.io.*;
import java.util.ArrayList;


/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TxtReadTools implements ReadToolsIF {
	
	/**
	 * 分割符，用来分割字符串。
	 */
	private String separator = null;
	/**
	 * txt文件名称包括全路径
	 */
	private String txtFile = null;
	
	/**
	 * 写入错误记录的文件名
	 */
	private String errorFile = null;
	
	/**
	 * 读取上传得txt文件的map
	 */
	private HashMap map = null;
	
	/**
	 * 总行数
	 */
	private int rows = 0;
	
	
	/**
	 * 总列数
	 */
	private int cols = 0;
	
	/**
	 * 将上传的txt文件读取到map中,格式为二维矩阵
	 */
	public TxtReadTools(String separator) {
		this.separator = separator;
	}
	
	public void read() {
		BufferedReader in = null;
		FileReader fr = null;
		try {
			map = new HashMap();
			fr = new FileReader(this.txtFile);
			in = new BufferedReader(fr);
			
			String s;
			int rows = 0;
			final int cols = 7;
			
			
			while ((s = in.readLine()) != null) {
				
				rows ++;
				
			}
			this.rows = rows;
			this.cols = cols;
			
			String[][] data = new String[rows][cols];
			in = new BufferedReader(new FileReader(this.txtFile));
			int j = 0;
			while ((s = in.readLine()) != null) {
				String[] arr = s.split( separator );
				
				for (int i = 0; i < arr.length; i ++) {
					data[j][i] = ( arr[i] == null ? "" : arr[i] );
				}
				j ++;
				
			}
			map.put(ReadToolsIF.R_DATA, data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if ( fr != null )
					fr.close();
				if ( in != null )
					in.close();
				
			} catch (IOException ioe) {
				System.out.println("提示: 可能是csv格式不正确");
			}
		}
	}

	/**
	 * 将错误记录写入另一个txt文件中
	 * 传过来的map里面放置String[][]
	 * @param map
	 * @param cols
	 */
	public synchronized void write(HashMap map, int cols) {
		ArrayList list = (ArrayList) map.get(ReadToolsIF.W_DATA);
		BufferedWriter out = null;
		
		try {
			out = new BufferedWriter(new FileWriter(errorFile));
			String total = "";
			for(int i = 0; i < list.size(); i ++) {
				String[] arr = (String[]) list.get(i);
				
				String str = "";
				for(int j = 0; j < cols; j ++) {
					
					str += ( arr[j] == null ? "" : arr[j] ) + separator;
				}
				str = str.substring(0, str.lastIndexOf( separator )) + '\r' + "\n";
				total += str;
				
			}
			out.write(total);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException ioe) {

			}
		}
	}

	/**
	 * 得到整个数据
	 * 
	 * @return
	 */
	public HashMap getData() {
		return (this.map);
	}

	/**
	 * 设置文件名
	 * 
	 * @param file
	 */
	public void setFile(String file) {
		this.txtFile = file;
	}
	
	/**
	 * 设置记录导入错误记录的文件名
	 * 
	 * @param errorFile
	 */
	public void setErrorFile(String errorFile) {
		this.errorFile = errorFile;
	}
	/**
	 * 得到导入数据的总行数
	 * 
	 * @return
	 */
	public int getTotalRows() {
		return ( this.rows );
	}

	/**
	 * 得到导入数据的总列数
	 * 
	 * @return
	 */
	public int getTotalCols() {
		return ( this.cols );
	}
}
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
	 * �ָ���������ָ��ַ�����
	 */
	private String separator = null;
	/**
	 * txt�ļ����ư���ȫ·��
	 */
	private String txtFile = null;
	
	/**
	 * д������¼���ļ���
	 */
	private String errorFile = null;
	
	/**
	 * ��ȡ�ϴ���txt�ļ���map
	 */
	private HashMap map = null;
	
	/**
	 * ������
	 */
	private int rows = 0;
	
	
	/**
	 * ������
	 */
	private int cols = 0;
	
	/**
	 * ���ϴ���txt�ļ���ȡ��map��,��ʽΪ��ά����
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
				System.out.println("��ʾ: ������csv��ʽ����ȷ");
			}
		}
	}

	/**
	 * �������¼д����һ��txt�ļ���
	 * ��������map�������String[][]
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
	 * �õ���������
	 * 
	 * @return
	 */
	public HashMap getData() {
		return (this.map);
	}

	/**
	 * �����ļ���
	 * 
	 * @param file
	 */
	public void setFile(String file) {
		this.txtFile = file;
	}
	
	/**
	 * ���ü�¼��������¼���ļ���
	 * 
	 * @param errorFile
	 */
	public void setErrorFile(String errorFile) {
		this.errorFile = errorFile;
	}
	/**
	 * �õ��������ݵ�������
	 * 
	 * @return
	 */
	public int getTotalRows() {
		return ( this.rows );
	}

	/**
	 * �õ��������ݵ�������
	 * 
	 * @return
	 */
	public int getTotalCols() {
		return ( this.cols );
	}
}
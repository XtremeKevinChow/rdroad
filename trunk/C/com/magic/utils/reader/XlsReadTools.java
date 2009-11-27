/*
 * Created on 2005-11-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.utils.reader;

import jxl.Workbook;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XlsReadTools implements ReadToolsIF {

	private static Logger logger = Logger.getLogger(XlsReadTools.class);
	
	/**
	 * the sheet name of error data
	 */
	public static final String ERROR_SHEET = "err_data";
	
	/**
	 * excel file which to read & write
	 */
	private String xlsFile = null;
	
	/**
	 * wirte file name
	 */
	private String errorFile = null;
	
	/**
	 * the excel work sheet object
	 */
	private Sheet sheet = null;
	
	/**
	 * the map which to add read data
	 */
	private HashMap map = null;
	
	/**
	 * private contructor
	 *
	 */
	public XlsReadTools() {
	}
	
	
	/**
	 * read all data from excel file
	 */
	public void read() {
		
		InputStream is = null;
		
		Workbook wb = null;
		
		
		
		try {
			
			map = new HashMap();
			
			is = new FileInputStream(xlsFile);
			
		} catch(FileNotFoundException fnfe) {
			
			System.out.println("file not be found!" + xlsFile);
		}
		try {
			
			wb = Workbook.getWorkbook(is);//工作簿
			this.sheet = wb.getSheet(0);//工作表
			int rows = this.getTotalRows();//总行
			int cols = this.getTotalCols();//总列
			
			String[][] data = new String[rows][cols];//存放全部数据的二维矩阵
			
			for (int i = 0; i < rows; i ++) {
				
				for(int j = 0; j < cols; j ++) {
					
					data[i][j] = sheet.getCell(j, i).getContents();//目前都是字符
					
				}
				
			}
			
			this.map.put(ReadToolsIF.R_DATA, data);
			
			
		} catch(IOException ioe) {
			logger.error("io error");
			
		} catch(BiffException be) {
		
		}finally { //释放资源
			try {
				if ( wb != null )
					wb.close();
				if ( is != null )
					is.close();
			}catch(IOException ioe) {
				logger.error("close error");
			}
		}
	}
	
	/**
	 * write error data into the map
	 */
	public synchronized void write(HashMap map, int cols) {
		
		ArrayList list = ( ArrayList ) map.get( ReadToolsIF.W_DATA );
		
		OutputStream os = null;
		
		WritableWorkbook wwb = null;
		
		if (list == null) {
			
			logger.error("all of the data is ok!");
			
			return;
		}
		
		try {
			
			os = new FileOutputStream(errorFile);//输出流
			
			wwb = Workbook.createWorkbook(os);//创建可写工作薄
			
			WritableSheet ws = wwb.createSheet(ERROR_SHEET, 0);//创建可写工作表
			
			Label[][] lab = new Label[cols][list.size()];
			
			for(int i = 0; i < list.size(); i ++) {
				
				String[] errData = (String[])list.get(i);
				
				for (int j = 0; j < cols; j ++){
					
					ws.addCell(new Label(j,i, errData[j]));
					
				}

			}
			wwb.write();
			
		}catch(FileNotFoundException fnfe) {
			
			logger.error("the excel file to write no been found " + xlsFile);
			
		}catch(IOException ioe) {
			
		}catch(WriteException we) {
			
			logger.error("write into excel file error");
			
		}finally {//资源释放
			try {
				if ( wwb != null )
					wwb.close();
				if ( os != null )
					os.close();
			}catch(IOException ioe) {
				
				logger.error("close error");
			}
		}
	}
	
	/**
	 * the total cols of the upload excel
	 */
	public int getTotalRows() {
		return sheet.getRows();
	}
	
	/**
	 * the total rols of the upload excel
	 */
	public int getTotalCols() {
		return sheet.getColumns();
	}
	
	/**
	 * the file name which to deal with
	 */
	public void setFile(String file) {
		this.xlsFile = file;
	}
	
	/**
	 * the error file name which to deal with
	 */
	public void setErrorFile(String errorFile) {
		this.errorFile = errorFile;
	}
	
	/**
	 * get the upload excel data
	 */
	public HashMap getData() {
		
		return ( this.map );
	}
	
	
}

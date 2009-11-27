/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.utils.reader;

import java.util.HashMap;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadFactory {
	
	/** CSV 文件的分割符 **/
	public final static String CSV_SEPA = ",";
	
	/** TXT 文件的分割符 **/
	public final static String TXT_SEPA = "\t";
	
	/** EXCEL 文件 **/
	public final static String XLS_FILE = "xls";
	
	/** TXT 文件(tab分开的文件) **/
	public final static String TXT_FILE = "txt";
	
	/** CSV 文件(逗号分开的文件) **/
	public final static String CSV_FILE = "csv";
	
	/** XML 文件 **/
	public final static String XML_FILE = "xml";
	
	/** PDF 文件 **/
	public final static String PDF_FILE = "pdf";
	
	/** 文件key和value的映射map **/
	public final static HashMap FILE_MAP = new HashMap();
	
	
	static {
		
		FILE_MAP.put("1", XLS_FILE);
		FILE_MAP.put("2", TXT_FILE);
		FILE_MAP.put("3", CSV_FILE);
		FILE_MAP.put("4", XML_FILE);
		FILE_MAP.put("5", PDF_FILE);
	}
	
	/** 私有构造器保证外部不能对它实例化 **/
	private ReadFactory() {
		
	}
	
	/** 静态工厂方法，用来产生各种文件存储器 **/
	public static ReadToolsIF factory(int fileType) throws FileTypeException {
		
		ReadToolsIF tool = null;
		
		switch (fileType) {
			case 1:
				tool = new XlsReadTools();
				break;
			case 2:
				tool = new TxtReadTools(TXT_SEPA);
				break;
			case 3:
				tool = new TxtReadTools(CSV_SEPA);
				break;
			default:
				throw new FileTypeException("目前不支持这种格式:" + FILE_MAP.get(String.valueOf(fileType)));
		}
		
		return tool;
	}
	
}

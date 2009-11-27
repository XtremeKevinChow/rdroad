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
	
	/** CSV �ļ��ķָ�� **/
	public final static String CSV_SEPA = ",";
	
	/** TXT �ļ��ķָ�� **/
	public final static String TXT_SEPA = "\t";
	
	/** EXCEL �ļ� **/
	public final static String XLS_FILE = "xls";
	
	/** TXT �ļ�(tab�ֿ����ļ�) **/
	public final static String TXT_FILE = "txt";
	
	/** CSV �ļ�(���ŷֿ����ļ�) **/
	public final static String CSV_FILE = "csv";
	
	/** XML �ļ� **/
	public final static String XML_FILE = "xml";
	
	/** PDF �ļ� **/
	public final static String PDF_FILE = "pdf";
	
	/** �ļ�key��value��ӳ��map **/
	public final static HashMap FILE_MAP = new HashMap();
	
	
	static {
		
		FILE_MAP.put("1", XLS_FILE);
		FILE_MAP.put("2", TXT_FILE);
		FILE_MAP.put("3", CSV_FILE);
		FILE_MAP.put("4", XML_FILE);
		FILE_MAP.put("5", PDF_FILE);
	}
	
	/** ˽�й�������֤�ⲿ���ܶ���ʵ���� **/
	private ReadFactory() {
		
	}
	
	/** ��̬�����������������������ļ��洢�� **/
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
				throw new FileTypeException("Ŀǰ��֧�����ָ�ʽ:" + FILE_MAP.get(String.valueOf(fileType)));
		}
		
		return tool;
	}
	
}

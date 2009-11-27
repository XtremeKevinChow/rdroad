/*
 * Created on 2005-11-14
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
public class FileTypeException extends Exception {
	
	/** 该异常类发生在创建文件读取器的时候，当没有定义这种类型就会抛出该异常 **/
	
	public FileTypeException() {
		super();
	}
	
	public FileTypeException(String msg) {
		super(msg);
	}
}

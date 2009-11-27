/*
 * Created on 2006-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.letter.bo;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LetterTypeException extends Exception {
    
    /** 没有这种信件模版的时候，就会抛出该异常 **/
	
	public LetterTypeException() {
		super();
	}
	
	public LetterTypeException(String msg) {
		super(msg);
	}
}

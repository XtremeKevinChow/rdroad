/*
 * Created on 2006-6-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.letter.bo;

/**
 * 邮件发送产生的异常，可能是数据不正常
 * 客户端程序捕捉到该异常时需要取消该邮件
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OrderLetterException extends java.sql.SQLException {
    
    /** 错误号 **/
    private int code = -100000;
    
    /** 信息 **/
    private String message = null;
    
    /**
     * @return Returns the code.
     */
    public int getCode() {
        return code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(int code) {
        this.code = code;
    }
    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    public OrderLetterException() {
		super();
	}
	
	public OrderLetterException(int code, String msg) {
	    super(msg);
	    this.code = code;
	    this.message = msg;

	}
}

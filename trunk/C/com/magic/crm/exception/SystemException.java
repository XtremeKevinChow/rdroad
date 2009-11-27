package com.magic.crm.exception;



public class SystemException extends JException
{

    public SystemException()
    {
        super("系统错误!\n");
    }
    public SystemException(Exception e) {
        super("系统错误!\n");
    }
}
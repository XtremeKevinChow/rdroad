package com.magic.crm.user.exception;



public class SystemException extends FeException
{

    public SystemException()
    {
        super("系统错误!\n");
    }
    public SystemException(Exception e) {
        super("系统错误!\n");
    }
}
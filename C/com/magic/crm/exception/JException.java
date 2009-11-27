package com.magic.crm.exception;


public class JException extends Exception
{
    String msgid = null;

    public JException()
    {
        super("Unknow error!\n");
    }
    public JException(String s)
    {
        super(s);
        msgid = s;
    }
    public String toString()
    {
        return "CreditEval Generated!\n" + super.toString();
    }

    public String getMsgId()
    {
        return msgid;
    }
}
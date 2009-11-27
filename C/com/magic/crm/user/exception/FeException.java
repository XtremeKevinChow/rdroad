package com.magic.crm.user.exception;




public class FeException extends Exception
{
    String msgid = null;

    public FeException()
    {
        super("Unknow error!\n");
    }
    public FeException(String s)
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
package com.magic.utils;

import java.util.Properties;
import java.util.Date;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMailTest{

    public static void main(String args[]){
        String []toAddress = {"james@magic.com"};
        Mail sendmail = new HtmlMail("smtp.163.com","xfhibby","qazqaz");
        try{
            sendmail.setSubject("HelloWorld");
            sendmail.setSendDate(new Date());
            //String plainText = "Welcome to use this Mail-Send program!";
            String htmlText = "<H1>HelloWorld</H1>" +
                   "<img src=\"http://cn.yimg.com/i/cn/home/m6v4.gif\">" +
                     "<img src=\"c:\\test\\test.gif\">";;
            //sendmail.setMailContent(plainText);
            sendmail.setMailContent(htmlText);
            //sendmail.setAttachments("D:\\wwpdev\\attach.jsp");
            sendmail.setMailFrom("xfhibby@163.com");
            sendmail.setMailTo(toAddress,"to");
            sendmail.setMailTo(toAddress,"cc");
            sendmail.sendMail();
        }
        catch(Exception ex){ ex.printStackTrace();}
    }
}
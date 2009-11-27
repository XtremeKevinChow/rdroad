package com.magic.utils;

import java.util.Properties;
import java.util.Date;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * 1.发送纯文本文件的的子类MailSendText.class
 * 
 * */
class TextMail extends Mail{

    public TextMail(String smtpHost,String username,String password){
        super(smtpHost,username,password);
        multipart = new MimeMultipart();
    }

    public void setMailContent(String mailContent)throws MessagingException{
        messageBodyPart.setText(mailContent);
        multipart.addBodyPart(messageBodyPart);
    }
}
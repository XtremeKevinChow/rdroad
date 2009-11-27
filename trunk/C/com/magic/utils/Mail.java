package com.magic.utils;

import java.util.Properties;
import java.util.Date;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 * 邮件处理
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
abstract class Mail{
    
    protected BodyPart messageBodyPart = null;
    protected Multipart multipart = null;
    protected MimeMessage mailMessage = null;
    protected Session mailSession = null;
    protected Properties mailProperties = System.getProperties();

    protected InternetAddress mailFromAddress = null;
    protected InternetAddress mailToAddress = null;
    protected MailAuthenticator authenticator = null;

    protected String mailSubject ="";
    protected Date mailSendDate = null;
    
    public Mail(String smtpHost,String username,String password){

        mailProperties.put("mail.smtp.host",smtpHost);
        mailProperties.put("mail.smtp.auth","false"); //使用方式
        mailProperties.put("mail.smtp.auth","true"); //设置smtp认证，很关键的一句
        authenticator = new MailAuthenticator(username,password);
        mailSession = Session.getDefaultInstance(mailProperties,authenticator);
        mailMessage = new MimeMessage(mailSession);
        messageBodyPart = new MimeBodyPart();
    }

    //设置邮件主题
    public void setSubject(String mailSubject)throws MessagingException{
        this.mailSubject = mailSubject;
        mailMessage.setSubject(mailSubject);
    }
    //所有子类都需要实现的抽象方法，为了支持不同的邮件类型
    protected abstract void setMailContent(String mailContent)throws MessagingException;
    
    //设置邮件发送日期
    public void setSendDate(Date sendDate)throws MessagingException{
        this.mailSendDate = sendDate;
        mailMessage.setSentDate(sendDate);
    }

    //设置邮件发送附件
    public void setAttachments(String attachmentName)throws MessagingException{
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentName);
        messageBodyPart.setDataHandler(new DataHandler(source));
        int index = attachmentName.lastIndexOf('\\');
        String attachmentRealName = attachmentName.substring(index+1);
        messageBodyPart.setFileName(attachmentRealName);
        multipart.addBodyPart(messageBodyPart);
    }
    
    //设置发件人地址
    public void setMailFrom(String mailFrom)throws MessagingException{
        mailFromAddress = new InternetAddress(mailFrom);
        mailMessage.setFrom(mailFromAddress);
    }
    
    //设置收件人地址，收件人类型为to,cc,bcc(大小写不限)
    public void setMailTo(String[] mailTo,String mailType)throws Exception{
        for(int i=0;i<mailTo.length;i++){

            mailToAddress = new InternetAddress(mailTo[i]);

            if(mailType.equalsIgnoreCase("to")){
                mailMessage.addRecipient(Message.RecipientType.TO,mailToAddress);
            }
            else if(mailType.equalsIgnoreCase("cc")){
                mailMessage.addRecipient(Message.RecipientType.CC,mailToAddress);
            }
            else if(mailType.equalsIgnoreCase("bcc")){
                mailMessage.addRecipient(Message.RecipientType.BCC,mailToAddress);
            }
            else{
                throw new Exception("Unknown mailType: "+mailType+"!");
            }
        }
    }
    
    //开始投递邮件
    public void sendMail()throws MessagingException,SendFailedException{
        if(mailToAddress == null){
            System.out.println("请你必须你填写收件人地址！");
            System.exit(1);
        }
        else{
            mailMessage.setContent(multipart);
           // System.out.println("正在发送邮件，请稍候.......");
            Transport.send(mailMessage);
           // System.out.println("恭喜你，邮件已经成功发送!");
        }
    }

}



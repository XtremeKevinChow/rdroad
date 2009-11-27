package com.magic.utils;

import java.util.Properties;
import java.util.Date;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 * �ʼ�����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
        mailProperties.put("mail.smtp.auth","false"); //ʹ�÷�ʽ
        mailProperties.put("mail.smtp.auth","true"); //����smtp��֤���ܹؼ���һ��
        authenticator = new MailAuthenticator(username,password);
        mailSession = Session.getDefaultInstance(mailProperties,authenticator);
        mailMessage = new MimeMessage(mailSession);
        messageBodyPart = new MimeBodyPart();
    }

    //�����ʼ�����
    public void setSubject(String mailSubject)throws MessagingException{
        this.mailSubject = mailSubject;
        mailMessage.setSubject(mailSubject);
    }
    //�������඼��Ҫʵ�ֵĳ��󷽷���Ϊ��֧�ֲ�ͬ���ʼ�����
    protected abstract void setMailContent(String mailContent)throws MessagingException;
    
    //�����ʼ���������
    public void setSendDate(Date sendDate)throws MessagingException{
        this.mailSendDate = sendDate;
        mailMessage.setSentDate(sendDate);
    }

    //�����ʼ����͸���
    public void setAttachments(String attachmentName)throws MessagingException{
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentName);
        messageBodyPart.setDataHandler(new DataHandler(source));
        int index = attachmentName.lastIndexOf('\\');
        String attachmentRealName = attachmentName.substring(index+1);
        messageBodyPart.setFileName(attachmentRealName);
        multipart.addBodyPart(messageBodyPart);
    }
    
    //���÷����˵�ַ
    public void setMailFrom(String mailFrom)throws MessagingException{
        mailFromAddress = new InternetAddress(mailFrom);
        mailMessage.setFrom(mailFromAddress);
    }
    
    //�����ռ��˵�ַ���ռ�������Ϊto,cc,bcc(��Сд����)
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
    
    //��ʼͶ���ʼ�
    public void sendMail()throws MessagingException,SendFailedException{
        if(mailToAddress == null){
            System.out.println("�����������д�ռ��˵�ַ��");
            System.exit(1);
        }
        else{
            mailMessage.setContent(multipart);
           // System.out.println("���ڷ����ʼ������Ժ�.......");
            Transport.send(mailMessage);
           // System.out.println("��ϲ�㣬�ʼ��Ѿ��ɹ�����!");
        }
    }

}



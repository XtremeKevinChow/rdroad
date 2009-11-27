/*
 * Created on 2005-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;
import java.util.*; 
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.servlet.ServletException;
import javax.activation.*;
import java.sql.Connection;
import java.sql.SQLException;
;


/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SendMail 
{ 
	Connection conn=null;
	public SendMail() 
	{ 
	} 
	public  void send(Connection conn,int orderID,int type){ 
	try 
	{  
		MailHtmlSource sds=new MailHtmlSource();
		Properties props = new Properties(); 
		Session sendMailSession; 
		Store store; 
		Transport transport; 
		props.put("mail.smtp.auth","true"); 
		props.put("mail.smtp.host", "192.168.10.185"); //smtp主机名。 
		props.put("mail.smtp.user","service@99read.com"); //发送方邮件地址。 
		props.put("mail.smtp.password","99readmail2005"); //邮件密码。 
		PopupAuthenticator popA=new PopupAuthenticator();//邮件安全认证。 
		PasswordAuthentication pop = popA.performCheck("service99","99readmail2005"); //验证用户名及密码 
		sendMailSession = Session.getInstance(props, popA); 
		MimeMessage newMessage = new MimeMessage(sendMailSession);
		newMessage.setFrom(new InternetAddress("service@99read.com")); 
		newMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(sds.getEmail(conn,orderID))); //接收方邮件地址
		//newMessage.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("smyang@99read.com")); //接收方邮件地址
		MimeBodyPart htmlodyPart = new MimeBodyPart();
		MimeMultipart multipart = new MimeMultipart();
		String subject="";
		switch (type){
		       case 1:
		       	subject="订单确认信";
		       	break;
		       case 2:
		       	subject="催款通知";
		       	break;	
		       case 3:
		       	subject="订单缺货通知";
		       	break;	
		       case 4:
		       	subject="订单缺款通知";
		       	break;	
		       case 5:
		       	subject="订单发货通知";
		       	break;		       	
		       default:
		       	subject="通知";
		
		}
		newMessage.setSubject(subject); 
		String mailContent="";
		mailContent=sds.getContent(conn,orderID,type);

		htmlodyPart.setContent(mailContent, "text/html;charset=GBK");//定义邮件内容允许HTML格式 
		htmlodyPart.setHeader("Content-Transfer-Encoding", "base64");//中文处理
		multipart.addBodyPart(htmlodyPart);
		newMessage.setContent(multipart);
		transport = sendMailSession.getTransport("smtp"); 
		transport.send(newMessage); 
 
	}catch (MessagingException ex){ 
		ex.printStackTrace(); 
	}catch (SQLException e){ 
		e.printStackTrace(); 
	} 

} 
	
public static void main(String[] args) 
{ 
	
	Connection conn = null;
	try {
		conn = DBManager2.getConnection();
		SendMail sml = new SendMail();
		sml.send(conn,228672,1); 
	} catch(Exception e) {
		e.printStackTrace();
	} finally {
		try { conn.close(); } catch(Exception e){}
	}
} 


public class PopupAuthenticator extends Authenticator{ 
String username=null; 
String password=null; 
public PopupAuthenticator(){} 
public PasswordAuthentication performCheck(String user,String pass){ 
username = user; 
password = pass; 
return getPasswordAuthentication(); 
} 
protected PasswordAuthentication getPasswordAuthentication() { 
return new PasswordAuthentication(username, password); 
} 

} 
} 


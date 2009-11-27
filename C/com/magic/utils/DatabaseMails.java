package com.magic.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import com.magic.app.DocType;
import com.magic.crm.util.Config;

/**
 * 发送邮件
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */

public class  DatabaseMails{	

	private static String smtp_host="";
	private static String user_name="";
	private static String user_password="";
  private static String mail_from="";
  private Log log;
  
    //实例化连接数据库的对象
	private  DBLink  dblink=null;
  /**
	 *自定义构造器
	 *调用方法getConfin(),设置smtp_host,user_name,user_password参数
	 */
	 public DatabaseMails(){
				if(smtp_host.equals(""))
                    getConfig();
        log=new Log();
	 }
		/**
		 * 方法sendMails()用于从数据库中取出所有未发送邮件的记录
		 * 并调用Mail类的方法发送邮件
		 */	
		public void sendMails(){		    
      dblink=new DBLink();
          Statement stmt=null;
    ResultSet rs=null;
       DBLink dblink1=new DBLink();
      String fileName="";
      try{  
		  //从数据库中取出所有未发送邮件的记录,即STATUS为0的记录
		    String strSql="select *  from BAS_EMAILS  where STATUS>=-3 and status<=0";
		    stmt=dblink1.createStatement() ;
            rs= stmt.executeQuery(strSql);         
		    while(rs.next())
			{	
        try
        {
          	//在这里,调用send()方法,发送邮件	
				Mail mail = new HtmlMail(smtp_host,user_name,user_password);
				//mail.setNeedAuth(true);
        mail.setSubject(rs.getString("subject"));
        mail.setSendDate(new Date());
        mail.setMailFrom(mail_from);
				//判断正文是否为文件,如果是文件,则读出来再发送!
        String mail_body="";
        if(rs.getString("content_type").equals("F"))
        {
				   BufferedReader in= new BufferedReader(new FileReader(rs.getString("content")));
						 String  str="";			 
						 while(in.ready()){
							str+=in.readLine();
						 }       
             mail_body=str;
            in.close();
            
           fileName=rs.getString("content");  
          
             //System.out.println(mail_body);
				}
        else
        {
            mail_body=rs.getString("content");
            fileName="";
        }
        mail_body = new String(mail_body.getBytes("GB2312"),"iso-8859-1");
        mail.setMailContent(mail_body);

				//判断是否有附件,如果为空,就不发送附件
				if(rs.getString("attachment")!=null)
					  mail.setAttachments(rs.getString("attachment"));
  
        String []mail_to = {rs.getString("mail_to")};
        mail.setMailTo(mail_to,"to");
        mail.sendMail();
        setDataBaseSuccessStatus(rs.getInt("id"));
         if(!fileName.equals(""))
         {
            java.io.File file=new java.io.File(fileName);
            file.delete();
         }
            log.logEvent(DocType.EMAIL,"邮件发送成功，收件人:"+rs.getString("mail_to")+",邮件ID:"+rs.getInt("id"),"");
        }catch(Exception e){
          setDataBaseFailedStatus(rs.getInt("id"));
          log.logError(DocType.EMAIL,"邮件发送失败，收件人:"+rs.getString("mail_to")+",邮件ID:"+rs.getInt("id"),e.toString());
		  } 		 
		
      }
		  rs.close();
      dblink1.close();
      dblink.close();
		  }catch(Exception e){
        dblink1.close();
        dblink.close();
        e.printStackTrace();
		    System.out.println("DatabaseMails.sendMails()中出现异常:"+e.getMessage());	
		  } 		  
		}
		
		/**
		 * 下面的方法用于对已发送了邮件的记录的STATUS属性付值
		 * 方法名:setDataBaseStatus();
		 * 参数:   int  p_id(发送邮件的记录的ID)
		 * return void 
		 */	
	public void setDataBaseSuccessStatus(int p_id){
	    String strSql="update BAS_EMAILS  set STATUS=100  where  ID="+p_id;
		System.out.println(strSql);
		try{
		  	dblink.executeUpdate(strSql);
		}catch(Exception e){
		  System.out.println("MailSend.setDataBaseStatus()方法出现异常:"+e.getMessage());	
		}
	}	
  public void setDataBaseFailedStatus(int p_id){
	    String strSql="update BAS_EMAILS  set STATUS=STATUS-1  where  ID="+p_id;
		System.out.println(strSql);
		try{
		  	dblink.executeUpdate(strSql);
		}catch(Exception e){
      e.printStackTrace();
		  System.out.println("MailSend.setDataBaseStatus()方法出现异常:"+e.getMessage());	
		}
	}	
		
	 /**
		*   本方法用于从数据库中的S_CONFIG_KEYS的表中取出记录
		*   用来设置smtp_host,user_name,user_password的值
		*/
    public void getConfig(){
		user_name=Config.getValue("EMAIL_ACCOUNT_USERNAME");
		user_password=Config.getValue("EMAIL_ACCOUNT_PASSWORD");
		smtp_host=Config.getValue("EMAIL_SMTP_HOST");
		mail_from=Config.getValue("EMAIL_FROM_NAME");	
	}


		public static void main(String args[]){
       	 DatabaseMails mail=new DatabaseMails();
			   mail.sendMails();
				}
		
}

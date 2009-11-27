package com.magic.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import com.magic.app.DocType;
import com.magic.crm.util.Config;

/**
 * �����ʼ�
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */

public class  DatabaseMails{	

	private static String smtp_host="";
	private static String user_name="";
	private static String user_password="";
  private static String mail_from="";
  private Log log;
  
    //ʵ�����������ݿ�Ķ���
	private  DBLink  dblink=null;
  /**
	 *�Զ��幹����
	 *���÷���getConfin(),����smtp_host,user_name,user_password����
	 */
	 public DatabaseMails(){
				if(smtp_host.equals(""))
                    getConfig();
        log=new Log();
	 }
		/**
		 * ����sendMails()���ڴ����ݿ���ȡ������δ�����ʼ��ļ�¼
		 * ������Mail��ķ��������ʼ�
		 */	
		public void sendMails(){		    
      dblink=new DBLink();
          Statement stmt=null;
    ResultSet rs=null;
       DBLink dblink1=new DBLink();
      String fileName="";
      try{  
		  //�����ݿ���ȡ������δ�����ʼ��ļ�¼,��STATUSΪ0�ļ�¼
		    String strSql="select *  from BAS_EMAILS  where STATUS>=-3 and status<=0";
		    stmt=dblink1.createStatement() ;
            rs= stmt.executeQuery(strSql);         
		    while(rs.next())
			{	
        try
        {
          	//������,����send()����,�����ʼ�	
				Mail mail = new HtmlMail(smtp_host,user_name,user_password);
				//mail.setNeedAuth(true);
        mail.setSubject(rs.getString("subject"));
        mail.setSendDate(new Date());
        mail.setMailFrom(mail_from);
				//�ж������Ƿ�Ϊ�ļ�,������ļ�,��������ٷ���!
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

				//�ж��Ƿ��и���,���Ϊ��,�Ͳ����͸���
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
            log.logEvent(DocType.EMAIL,"�ʼ����ͳɹ����ռ���:"+rs.getString("mail_to")+",�ʼ�ID:"+rs.getInt("id"),"");
        }catch(Exception e){
          setDataBaseFailedStatus(rs.getInt("id"));
          log.logError(DocType.EMAIL,"�ʼ�����ʧ�ܣ��ռ���:"+rs.getString("mail_to")+",�ʼ�ID:"+rs.getInt("id"),e.toString());
		  } 		 
		
      }
		  rs.close();
      dblink1.close();
      dblink.close();
		  }catch(Exception e){
        dblink1.close();
        dblink.close();
        e.printStackTrace();
		    System.out.println("DatabaseMails.sendMails()�г����쳣:"+e.getMessage());	
		  } 		  
		}
		
		/**
		 * ����ķ������ڶ��ѷ������ʼ��ļ�¼��STATUS���Ը�ֵ
		 * ������:setDataBaseStatus();
		 * ����:   int  p_id(�����ʼ��ļ�¼��ID)
		 * return void 
		 */	
	public void setDataBaseSuccessStatus(int p_id){
	    String strSql="update BAS_EMAILS  set STATUS=100  where  ID="+p_id;
		System.out.println(strSql);
		try{
		  	dblink.executeUpdate(strSql);
		}catch(Exception e){
		  System.out.println("MailSend.setDataBaseStatus()���������쳣:"+e.getMessage());	
		}
	}	
  public void setDataBaseFailedStatus(int p_id){
	    String strSql="update BAS_EMAILS  set STATUS=STATUS-1  where  ID="+p_id;
		System.out.println(strSql);
		try{
		  	dblink.executeUpdate(strSql);
		}catch(Exception e){
      e.printStackTrace();
		  System.out.println("MailSend.setDataBaseStatus()���������쳣:"+e.getMessage());	
		}
	}	
		
	 /**
		*   ���������ڴ����ݿ��е�S_CONFIG_KEYS�ı���ȡ����¼
		*   ��������smtp_host,user_name,user_password��ֵ
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

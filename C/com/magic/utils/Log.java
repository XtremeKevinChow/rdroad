package com.magic.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import com.magic.app.DocType;
import com.magic.crm.util.Config;

/**
 * 生成日志文件
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class Log {
	private static final String  FILEURL=Config.getValue("LOG_FILE_DIR")+"\\log"; 
  private static final String  AUDITDIR=Config.getValue("LOG_FILE_DIR"); 
  public static final int EVENT_DELETE=-1;
  public static final int EVENT_ADD=1;
  public static final int EVENT_UPDATE=0;
	private String  logFile;
  
	public Log()
	{	
      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date error_date=new java.util.Date();
      logFile=FILEURL+sdf.format(error_date)+".log";
	}
  public Log(String log_file)
	{	

      logFile=log_file;

  }
	
	public  void logEPrint(int doc_type, String sqlState, String message,String vendor)
	{
		DocType doctype=new DocType(doc_type);
		String doc_name=doctype.getDocName();
		String doc_dataSource=doctype.getDataSource();
		String doc_fieldKey=doctype.getKeyField();
		try{		  
		  
			String printName="doc_name:"+doc_name;
		    String printDataSource="doc_dataSource:"+doc_dataSource;
		  	String printFieldKey="doc_fieldKey:"+doc_fieldKey;
		  	BufferedWriter bw=new BufferedWriter(new FileWriter(logFile,true));
		  	bw.newLine();
		  	bw.write("(操作时间 ："+getCurrentDate()+")");		  	
		  	bw.newLine();
		  	bw.write("操作失败，错误信息提示如下：");
		  	bw.newLine();
		  	bw.write(printName);
		  	bw.newLine();
		  	bw.write(printDataSource);
		  	bw.newLine();
		  	bw.write(printFieldKey);
		  	bw.newLine();	
		  	bw.write(sqlState);
		  	bw.newLine();
		  	bw.write(message);
		  	bw.newLine();
		  	bw.write(vendor);
		  	bw.newLine();	  	
		  	
		  	bw.close();
		}catch(Exception e){
		   e.printStackTrace();	
		}
	}
	public  void logEvent(int doc_type, String description, String message )
	{
		try{
        BufferedWriter bw=new BufferedWriter(new FileWriter(logFile,true));
		 	  bw.newLine();
		  	bw.write("[EVENT]\t"+getCurrentDate()+"\t"+doc_type+"\t"+description+"\t"+message);		  	
		  	bw.close();
		}catch(Exception e){
		   e.printStackTrace();	
		}
	}
	
	public  void logEvent(String sqlState, String message,String vendor )
	{
		logEvent(0,message,vendor);
	}
	
	
	public void logError(int doc_type, String description,String message)
	{
    	try{
        BufferedWriter bw=new BufferedWriter(new FileWriter(logFile,true));
        bw.newLine();
		  	bw.write("[ERROR]\t"+getCurrentDate()+"\t"+doc_type+"\t"+description+"\t"+message);		  	
        bw.close(); 
      }catch(Exception e){
		    e.printStackTrace();	
      }
	}
  
  public String getCurrentDate()
  {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd a HH:mm:ss z");
			java.util.Date error_date=new java.util.Date();
			return sdf.format(error_date);
  }
  
  public void audit(int org_id, int person_id, int doc_type, int event,int doc_id)
  {
     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date error_date=new java.util.Date();
      String file_name=sdf.format(error_date);
     try{
        BufferedWriter bw=new BufferedWriter(new FileWriter(AUDITDIR+"\\"+file_name+".log",true));
        bw.newLine();
        String log_line="[EVENT]\t"+getCurrentDate()+"\t"+org_id+"\t"+person_id+"\t";
       
        log_line=log_line+getPersonName(person_id)+"\t"+getDocName(doc_type)+"\t";
        if(event==EVENT_ADD)
          log_line=log_line+"增加";
        if(event==EVENT_UPDATE)
          log_line=log_line+"更改";
        if(event==EVENT_DELETE)
          log_line=log_line+"删除";
        log_line=log_line+"\t"+doc_id;
		  	bw.write(log_line);		  	
        bw.close(); 
      }catch(Exception e){
		    e.printStackTrace();	
      }
  }
  
  public void logSQL(String sql, long execute_time)
  {
      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date error_date=new java.util.Date();
      String file_name=sdf.format(error_date);
     try{
        BufferedWriter bw=new BufferedWriter(new FileWriter(AUDITDIR+"\\"+file_name+"_SQL.log",true));
        bw.newLine();
        String log_line="[EXECUTE_TIME]\t"+execute_time+"\t[SQL]\t"+sql;
		  	bw.write(log_line);		  	
        bw.close(); 
      }catch(Exception e){
		    e.printStackTrace();	
      }
  }
 	protected void finalize()
  {
        
  }
  public static void main(String args[]){
   Log log=new Log();
   log.logError(0,"test","test");
	}
  
  private String getDocName(int doc_type)
  {
    String name="";
     DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
    try
    {
      
       stmt=dblink.createStatement();rs= stmt.executeQuery("select name from s_doc_type where id="+doc_type);
       rs.next();
       name=rs.getString("name");
    }catch(Exception e)
    {
      e.printStackTrace();
    }finally
    {
        try
            {
                 if(rs!=null) rs.close();
                 if(stmt!=null) stmt.close();
                 dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
    }
    return name;
  }
  
  private String getPersonName(int person_id)
  {
    String name="";
    try
    {
       DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
       stmt=dblink.createStatement();rs= stmt.executeQuery("select name from org_persons where id="+person_id);
       rs.next();
        name=rs.getString("name");
       dblink.close();
    
    }catch(Exception e)
    {
      e.printStackTrace();
    }
       return name;
  }
}

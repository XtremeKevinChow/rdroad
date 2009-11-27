package com.magic.utils;

import java.sql.ResultSet;
import java.sql.Statement;

import com.magic.crm.util.Config;
/**
 * 附件类，描述一个上传的附件
 */
public class Attachment { 
  //文件物理存放目录
  private static String ATTACHMENT_FILE_SAVE_DIR=null;
  //文件URL前缀
  private static String ATTACHMENT_FILE_URL_PREFIX=null; 
  //文件上传位置
  private static String ATTACHMENT_FILE_POSITION=null;
  
  private  int attachment_id;
  private String name;
  private String file_url;
  private String file_type;
  private String file_name;
  private String file_HTML_string;
  private int file_size;
  private String description="";
  private String picture,file_extention;

  public Attachment(){} 
  
  /**
   * 从上传的文件得到的附件存盘,并记录数据库中
   */  
  public Attachment(com.magic.utils.File file) {   
    try{
        this.loadProperties();
        name=file.getFilePathName();         //上传文件名         
        name=this.getFName(name);       
        file_extention=file.getFileExt();     //上传文件扩展名 
        file_size=file.getSize();                //上传文件大小              
        //上传文件在本地实际的保存名
        attachment_id=this.getNewAttachmentID();       
        file_name=attachment_id+"."+file_extention;        
       
        file.saveAs(ATTACHMENT_FILE_SAVE_DIR+"/"+file_name);//保存文件
        file.saveAs(ATTACHMENT_FILE_POSITION+"/"+file_name);//上传文件
        
        //根据类型，去找到对应的图片    
        file_type = this.getFileType(file_extention);
        picture=this.getPicture(file_type);;
        file_HTML_string=this.getHTMLString();
        
        attachment_id = this.saveToDB();//插入记录到数据库     
           
      }catch(Exception e){
      }
  }  
  /**
   * 从数据库中得到文件
   */
  public Attachment( int attachment_id){    
  }
  /**
   * 返回字符串
   * file_url连接Url
   * XXXX根据文件类型取得对应的图片
   * file_name文件上传时的名称
   */ 
  public String getHTMLString()
  {
    String htmlstr="<a href=\""+ATTACHMENT_FILE_URL_PREFIX+name+"\"><img src=\""+picture+"\" border=0/>"+name+"</a>";
    return htmlstr;
  }
  /**
   * 从配置文件中读取上传文件保存目录等信息
   */
  private void loadProperties(){
    if(ATTACHMENT_FILE_SAVE_DIR==null)
       ATTACHMENT_FILE_SAVE_DIR=Config.getValue("ATTACHMENT_FILE_SAVE_DIR");
    
    if(ATTACHMENT_FILE_URL_PREFIX==null)
      ATTACHMENT_FILE_URL_PREFIX=Config.getValue("ATTACHMENT_FILE_URL_PREFIX");
    
    if(ATTACHMENT_FILE_POSITION==null)
      ATTACHMENT_FILE_POSITION=Config.getValue("ATTACHMENT_FILE_POSITION");
  }
  /**
   * 返回从表中取得最大ID号+1的值，用作产生文件保存名
   */
  private int getNewAttachmentID(){
      String sql="select SEQ_BAS_ATTACHMENT_ID.nextval from dual";
      DBLink dblink=new DBLink();
      Statement stmt=null;
      ResultSet rs=null;
      int result=0;
      try{ 
         stmt=dblink.createStatement();
         rs= stmt.executeQuery(sql);
         if(rs.next()){
           result=rs.getInt(1);
         }
      }catch(Exception e){
         e.printStackTrace();
      }finally{
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
      return  result;
  }

 /**
   * 插入数据库记录
   * @param UpLoadFileSaveToDBInfo
   */
   public int saveToDB(){
       DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
       int result=0;
       String sql="insert into BAS_ATTachments(id,name,description,file_url,file_type,file_size,file_name,file_HTML_String)values("+attachment_id+",'"+name+"','"+description+"','"+ATTACHMENT_FILE_URL_PREFIX+"','"+file_type+"',"+file_size+",'"+file_name+"','"+file_HTML_string+"')";
       try{
         boolean b=dblink.executeInsert(sql);
         if(b){result=attachment_id;}
           else{System.out.println("插入数据失败!");}
       }catch(Exception e){
             e.printStackTrace();
       }finally{
          if(dblink!=null)
            dblink.close();
       }    
       return result;
   }
   /**
    * 根据扩展名去数据库中找到对应的文件类型
    */
    private String getFileType(String file_ext){
       String result="";   
       DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
       String sql="select FILE_TYPE_NAME  from BAS_FILE_TYPE_EXETENSIONS  where  FILE_EXTENSION='"+file_ext+"'";
       try{
            stmt=dblink.createStatement();rs= stmt.executeQuery(sql);
           if(rs.next()){
              result=rs.getString("FILE_TYPE_NAME");
           }else{
             //设置默认值，当上传文件类型在数据库中不存在的时候，默认为此值
             result="unknown";
           }
           rs.close();
       }catch(Exception e){
          e.printStackTrace();
       }finally{
          if(dblink!=null)
            dblink.close();
       }  
       return result;
    } 
   
   /**
   * 从数据库中找出类型对应的图片
   * @prama  String 文件类型
   */
   public String getPicture(String fileType){
     DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
     String picture="";//如果没有，设定一个默认值 
     String sql="select img_file_name from bas_file_type where name='"+fileType.trim()+"'";
     try{
          stmt=dblink.createStatement();rs= stmt.executeQuery(sql);
        if(rs.next()){
           picture=rs.getString("img_file_name");
        }
        rs.close();
        stmt.close();
     }catch(Exception e){
       e.printStackTrace();
     }finally{
       if(dblink!=null)
          dblink.close();
     }
     return picture;
   }
   
   public static void main(String args[]){
   }
  
  /**
	 * 从文件路径中截取文件名
	*/
	private static String getFName(String str)
	{ 
    String result="";
		if ((str == null) || (str.length() == 0))
			return result;
    int index=str.lastIndexOf("/")>str.lastIndexOf("\\")?str.lastIndexOf("/"):str.lastIndexOf("\\");
    if(index>0){
      result = str.substring(index+1);
    }	
    return result;
	}
  
  /**
   * 得到附件保存到数据库的ID
   * @return 
   */
  public int getID()
  {
    return attachment_id;
  }
   
}
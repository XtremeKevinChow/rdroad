package com.magic.utils;

import java.sql.ResultSet;
import java.sql.Statement;

import com.magic.crm.util.Config;
/**
 * �����࣬����һ���ϴ��ĸ���
 */
public class Attachment { 
  //�ļ�������Ŀ¼
  private static String ATTACHMENT_FILE_SAVE_DIR=null;
  //�ļ�URLǰ׺
  private static String ATTACHMENT_FILE_URL_PREFIX=null; 
  //�ļ��ϴ�λ��
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
   * ���ϴ����ļ��õ��ĸ�������,����¼���ݿ���
   */  
  public Attachment(com.magic.utils.File file) {   
    try{
        this.loadProperties();
        name=file.getFilePathName();         //�ϴ��ļ���         
        name=this.getFName(name);       
        file_extention=file.getFileExt();     //�ϴ��ļ���չ�� 
        file_size=file.getSize();                //�ϴ��ļ���С              
        //�ϴ��ļ��ڱ���ʵ�ʵı�����
        attachment_id=this.getNewAttachmentID();       
        file_name=attachment_id+"."+file_extention;        
       
        file.saveAs(ATTACHMENT_FILE_SAVE_DIR+"/"+file_name);//�����ļ�
        file.saveAs(ATTACHMENT_FILE_POSITION+"/"+file_name);//�ϴ��ļ�
        
        //�������ͣ�ȥ�ҵ���Ӧ��ͼƬ    
        file_type = this.getFileType(file_extention);
        picture=this.getPicture(file_type);;
        file_HTML_string=this.getHTMLString();
        
        attachment_id = this.saveToDB();//�����¼�����ݿ�     
           
      }catch(Exception e){
      }
  }  
  /**
   * �����ݿ��еõ��ļ�
   */
  public Attachment( int attachment_id){    
  }
  /**
   * �����ַ���
   * file_url����Url
   * XXXX�����ļ�����ȡ�ö�Ӧ��ͼƬ
   * file_name�ļ��ϴ�ʱ������
   */ 
  public String getHTMLString()
  {
    String htmlstr="<a href=\""+ATTACHMENT_FILE_URL_PREFIX+name+"\"><img src=\""+picture+"\" border=0/>"+name+"</a>";
    return htmlstr;
  }
  /**
   * �������ļ��ж�ȡ�ϴ��ļ�����Ŀ¼����Ϣ
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
   * ���شӱ���ȡ�����ID��+1��ֵ�����������ļ�������
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
   * �������ݿ��¼
   * @param UpLoadFileSaveToDBInfo
   */
   public int saveToDB(){
       DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
       int result=0;
       String sql="insert into BAS_ATTachments(id,name,description,file_url,file_type,file_size,file_name,file_HTML_String)values("+attachment_id+",'"+name+"','"+description+"','"+ATTACHMENT_FILE_URL_PREFIX+"','"+file_type+"',"+file_size+",'"+file_name+"','"+file_HTML_string+"')";
       try{
         boolean b=dblink.executeInsert(sql);
         if(b){result=attachment_id;}
           else{System.out.println("��������ʧ��!");}
       }catch(Exception e){
             e.printStackTrace();
       }finally{
          if(dblink!=null)
            dblink.close();
       }    
       return result;
   }
   /**
    * ������չ��ȥ���ݿ����ҵ���Ӧ���ļ�����
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
             //����Ĭ��ֵ�����ϴ��ļ����������ݿ��в����ڵ�ʱ��Ĭ��Ϊ��ֵ
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
   * �����ݿ����ҳ����Ͷ�Ӧ��ͼƬ
   * @prama  String �ļ�����
   */
   public String getPicture(String fileType){
     DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
     String picture="";//���û�У��趨һ��Ĭ��ֵ 
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
	 * ���ļ�·���н�ȡ�ļ���
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
   * �õ��������浽���ݿ��ID
   * @return 
   */
  public int getID()
  {
    return attachment_id;
  }
   
}
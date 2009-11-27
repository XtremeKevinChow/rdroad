package com.magic.utils;
/**
 * 上传文件内容保存到数据库
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class UpLoadFileSaveToDBInfo 
{
  private String name;
  private String file_url;
  private String file_type;
  private String file_name;
  private String file_HTML_string;
  private int file_size;
  private String description;
  
  public UpLoadFileSaveToDBInfo(){}  
  
  public UpLoadFileSaveToDBInfo(com.magic.utils.File myFile,String upLoadrealityFileName){
      this.name=myFile.getFileName();//附件上传的文件名
      this.file_url=myFile.getFilePathName();//附件URL
      this.file_type=myFile.getContentType();//附件文件类型
      
      this.file_name=upLoadrealityFileName;//实际存放的文件名称
      
      this.file_HTML_string=myFile.getContentString();//显示内容
      this.description="";//附件描述
      this.file_size=myFile.getSize();//文件大小
  }

  public void setName(String name){   this.name = name;  }
  public String getName()  {    return name;  }
  
  public void setFile_url(String file_url)  {    this.file_url = file_url;  }
  public String getFile_url()  {    return file_url;  }
  
  public void setFile_type(String file_type)  {    this.file_type = file_type;  }
  public String getFile_type()  {    return file_type;  }
  
  public void setFile_name(String file_name)  {    this.file_name = file_name;  }
  public String getFile_name()  {    return file_name;  }
  
  public void setFile_HTML_string(String file_HTML_string){ this.file_HTML_string = file_HTML_string;  }
  public String getFile_HTML_string() {    return file_HTML_string;  }
  
  public void setFile_size(int file_size)  {    this.file_size = file_size;  }
  public int getFile_size()  {    return file_size;  }
  
  public void setDescription(String description)  {    this.description = description;  }
  public String getDescription()  {    return description;  }
  
  
  }
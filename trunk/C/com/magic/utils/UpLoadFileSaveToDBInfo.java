package com.magic.utils;
/**
 * �ϴ��ļ����ݱ��浽���ݿ�
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
      this.name=myFile.getFileName();//�����ϴ����ļ���
      this.file_url=myFile.getFilePathName();//����URL
      this.file_type=myFile.getContentType();//�����ļ�����
      
      this.file_name=upLoadrealityFileName;//ʵ�ʴ�ŵ��ļ�����
      
      this.file_HTML_string=myFile.getContentString();//��ʾ����
      this.description="";//��������
      this.file_size=myFile.getSize();//�ļ���С
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
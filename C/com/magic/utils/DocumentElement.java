package com.magic.utils;

import com.magic.utils.*;
import com.magic.crm.util.DBManager;

import java.sql.*;
/**
 * 读取系统文档信息
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class DocumentElement 
{
  private String doc_name;
  private Field[] fields;
  private boolean is_readonly;
  private String data_source;
  private boolean is_global;
  private String key_field;
  private boolean is_log_operator;
  private boolean has_parent_doc;
  Connection conn =null;
  public DocumentElement(int doc_type)
  {
       Statement stmt=null;ResultSet rs=null;
      try
      {
      	conn=DBManager.getConnection();
          stmt=conn.createStatement();rs= stmt.executeQuery("select * from s_doc_type where id="+doc_type);
          rs.next();
          this.doc_name=rs.getString("name");
          this.data_source=rs.getString("data_source");
          this.is_readonly=(rs.getInt("is_readonly")==1);
          this.is_global=(rs.getInt("is_global")==1);
          this.key_field=rs.getString("key");
          this.is_log_operator=(rs.getInt("is_log_operator")==1);
          this.has_parent_doc=(rs.getInt("has_parent_doc")==1);
          stmt.close();
          stmt=conn.createStatement();rs=stmt.executeQuery("select count(*) count from s_doc_field where doc_type="+doc_type);
          rs.next();
          this.fields=new Field[rs.getInt("count")];
          stmt.close();
          stmt=conn.createStatement();rs=stmt.executeQuery("select * from s_doc_field where doc_type="+doc_type+" order by display_sequence");
          int i=0;
          while(rs.next())
          {
              Field fd=FieldMap.getField(rs.getString("field_name"));
              
              int isDetail=0;
              if(fd.isDetail()) isDetail=1;
              int isVisible=0;
              if(fd.getVisible()) isVisible=1;
              
              fields[i]= new Field(fd.getName(),fd.getCaption(),fd.getDataSize(),fd.getDataType(),isVisible,isDetail,fd.getLookup(),fd.getDocType(),fd.getRefIdName(),fd.getInputType());
              fields[i].setRequired(rs.getInt("is_required")==1);
              fields[i].setNew(rs.getInt("is_new")==1);
              fields[i].setUpdate(rs.getInt("is_update")==1);
              fields[i].setDetail(rs.getInt("is_detail")==1);
              fields[i].setQuery(rs.getInt("is_query")==1);
              fields[i].setList(rs.getInt("is_list")==1);
              fields[i].setQuickQuery(rs.getInt("is_quick_query")==1);
              fields[i].setDescription(StringUtil.cNull(rs.getString("description")));
              fields[i].setDefaultValue(StringUtil.cNull(rs.getString("default_value")));
              i++;
          }
      }catch(Exception e)
      {
          System.err.println("ERROR>>> error in DocType."+e);
      }finally
      {
          try{
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
          }catch(Exception e)
          {
              e.printStackTrace();
          }
      }
   
  }

    public String getDocName()
    {
        return this.doc_name;
    }
    
    public Field[] getFields()
    {
        return this.fields;
    }

    public boolean isReadonly()
    {
        return is_readonly;
    }

    public String getDataSource()
    {
        return data_source;
    }

    public boolean is_global()
    {
        return is_global;
    }

  public String getKeyField()
  {
    return key_field;
  } 

  public boolean isLogOperator()
  {
    return is_log_operator;
  }


  public boolean isParentDoc()
  {
    return has_parent_doc;
  }

}
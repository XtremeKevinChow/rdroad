package com.magic.utils;
import com.magic.app.DocType;
import java.sql.*;

/**
 * 文档处理的基类
 */
public abstract class BaseDocument 
{
 
  private int doc_type;
  private String sp_new; 
  private String sp_update;
  private String sp_delete;
  
  private DocType doc=null;
  private String success_info=null;
  
  private String error_message=null;
  private SessionInfo sessionInfo=null;
  
  public BaseDocument(int docType,SessionInfo sessionInfo)
  {
      doc=new DocType(docType);
      this.sessionInfo=sessionInfo;
  }
  
  abstract String getSPNew();
  abstract String getSPUpdate();
  abstract String getSPDelete();
  /**
   * 新增保存时调用的方法
   * @return 数据库ID 
   */
  public int addNew()
  {

     Field[] fds=doc.getFields();
     DBLink dblink=new DBLink();
     Statement stmt=null;
     ResultSet rs=null;
      CallableStatement cstmt=null;
     String sp=getSPNew();
     int re=0;
     try
        {
             int para_index=1;
             cstmt = dblink.prepareCall(sp); 
             System.out.println(fds.length);
             for(int i=0;i<fds.length;i++)
             {           
                  if(fds[i].isNew())
                  {
                      para_index++;
                      String name=fds[i].getName();
                      System.out.println("参数名称:"+name);
                      System.out.println("参数值："+sessionInfo.getParameter(name));
                      System.out.println("参数类型:"+fds[i].getDataType()+"\n");
                  
                      String para_value=StringUtil.cEmpty(sessionInfo.getParameter(name)).trim();
                      if(fds[i].getInputType().equals("KEYSET"))
                      {
                          String key_value=StringUtil.cEmpty(sessionInfo.getParameter(name+"_key"));
                          String value="";
                          para_value="";
                          if(!key_value.equals(""))
                          {
                                  Lookup lu=LookupMap.getLookup(fds[i].getLookup());
                                  stmt=dblink.createStatement();
                                  rs= stmt.executeQuery("select "+lu.getValueField()+" from "+lu.getDataSource()+" where "+lu.getKeyField()+"='"+key_value+"'");
                                try
                                {
                                    if(rs.next())
                                      para_value=rs.getString(1);
                                    else
                                    {
                                       error_message="字段\""+fds[i].getCaption()+"\"没有输入有效的代码.";
                                       return (-1);
                                    }
                                }catch(Exception e)
                                {
                                  System.out.println("error-"+e);
                                }
                          }
                      } 
                      if(fds[i].isRequired())
                      {
                          if(para_value.trim().equals(""))
                          {
                             error_message="必要的字段\""+fds[i].getCaption()+"\"没有输入";
                             return (-1);
                          }
                      }
                      if(fds[i].getDataType().equals("CHAR"))
                      {
                          String value=para_value.trim();
                          if(fds[i].getName().toUpperCase().equals("PWD"))
                          {
                              value=(new MD5()).getMD5ofStr(value);
                          }
                          cstmt.setString(para_index,value);                      
                      }
                      if(fds[i].getDataType().equals("DATE"))
                      {
                          String value=para_value.trim();
                          if(!StringUtil.isDate(value)&&!value.equals(""))
                          {
                              error_message="字段\""+fds[i].getCaption()+"\"不是有效的日期类型.";
                              return(-1);
                          }
                          cstmt.setString(para_index,value);      
                      }
                      if(fds[i].getDataType().equals("INTEGER"))
                      {
                          if(!StringUtil.isNum(para_value) &&!para_value.equals(""))
                          {
                              error_message="字段\""+fds[i].getCaption()+"\"不是整型.";
                              return(-1);
                          }
                          int value=0;
                          if(StringUtil.isNum(para_value))
                              value=Integer.parseInt(para_value);
                          cstmt.setInt(para_index,value);  
                      }
                      if(fds[i].getDataType().equals("FLOAT"))
                      {
                          if(!StringUtil.isNumEx(para_value)&&!para_value.equals(""))
                          {
                              error_message="字段\""+fds[i].getCaption()+"\"不是数字型.";
                              return -1;
                          }
                          float value=0;
                          if(StringUtil.isNumEx(para_value))
                              value=Float.parseFloat(para_value);
                          else
                              if(fds[i].getInputType().equals("TEXT")) value=-1;
                          cstmt.setFloat(para_index,value);  
                      } 
                      if(fds[i].getDataType().equals("CLOB"))
                      {
                          // Clob clob = cstmt.getClob(4);
                         //Clob cb.getAsciiStream().read
                         // clob.setString(0,request.getParameter(name));
                          //clob.open (CLOB.MODE_READWRITE);
                          String value=para_value;
                        //  clob.putString(0,value);
                        //  clob.close();
                         // cstmt.setObject(para_index,clob);   
                         cstmt.setString(para_index,value); 
                      }         
                  } 
            }
         if(doc.isParentDoc())
            {
                para_index++;
                cstmt.setInt(para_index, Integer.parseInt(sessionInfo.getParameter("parent_doc_id"))); 
                System.out.println("参数名称:父文档ID");
                System.out.println("参数值："+sessionInfo.getIntParameter("parent_doc_id"));
                System.out.println("参数类型:INTEGER");
            }          
            if(!doc.is_global())  
            {
                para_index++;
                cstmt.setInt(para_index,sessionInfo.getCompanyID()); 
                System.out.println("参数名称:俱乐部ID");
                System.out.println("参数值："+sessionInfo.getCompanyID());
                System.out.println("参数类型:INTEGER");
            }
           
            if(doc.isLogOperator())
            {
                 para_index++;
                cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
                System.out.println("参数名称:操作员ID");
                System.out.println("参数值："+this.sessionInfo.getOperatorID());
                System.out.println("参数类型:INTEGER");
            }
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            re=cstmt.getInt(1);
            if(re<0)
            {
                   KException ke=new KException(re);
                   error_message="错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage();
                   return(-2);
            }
        }catch(Exception e)
        {
            error_message="错误:"+e.toString();
            System.out.println(e);
            return(-3);
        }finally{
            try
            {
                if(cstmt!=null) cstmt.close();
                dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
      
        }
        Log log=new Log();
        log.audit(sessionInfo.getCompanyID(),sessionInfo.getOperatorID(),doc_type,log.EVENT_ADD,0);
        return 0;
  }
  /**
   * 修改保存时调用的方法
   * @param doc_id 修改数据的ID
   * @return 数据库ID
   */
  public int update(int doc_id)
  {
     Field[] fds=doc.getFields();
     DBLink dblink=new DBLink();
     Statement stmt=null;
     ResultSet rs=null;
     String sp=getSPUpdate();
     int re=0;
     try
      {
             int para_index=2;
             CallableStatement cstmt = dblink.prepareCall(sp); 
             cstmt.setInt(para_index,doc_id); 
             System.out.println("参数名称:doc_id");
             System.out.println("参数值："+doc_id);
             System.out.println("参数类型:INTEGER"); 
             System.out.println(fds.length);
             for(int i=0;i<fds.length;i++)
             {     
                  if(fds[i].isUpdate())
                  {	
                      para_index++;
                      String name=fds[i].getName();
                      System.out.println("参数名称:"+name);
                      System.out.println("参数值："+sessionInfo.getParameter(name));
                      System.out.println("参数类型:"+fds[i].getDataType()+"\n");
                      if(fds[i].isRequired()&&fds[i].isUpdate())
                      {
                          if(sessionInfo.getParameter(name)==null || sessionInfo.getParameter(name).trim().equals(""))
                          {
                            error_message="必要的字段"+fds[i].getCaption()+"没有输入";
                            return -1;
                          }
                      }
                      String para_value=StringUtil.cEmpty(sessionInfo.getParameter(name)).trim();
                      if(fds[i].getInputType().equals("KEYSET"))
                      {
                          String key_value=StringUtil.cEmpty(sessionInfo.getParameter(name+"_key"));
                          String value="";
                           if(!key_value.equals(""))
                          {
                          Lookup lu=LookupMap.getLookup(fds[i].getLookup());
                          stmt=dblink.createStatement(); 
                          rs= stmt.executeQuery("select "+lu.getValueField()+" from "+lu.getDataSource()+" where "+lu.getKeyField()+"='"+key_value+"'");
                        try
                        {
                            if(rs.next())
                              para_value=rs.getString(1);
                            else
                            {
                               error_message="字段\""+fds[i].getCaption()+"\"没有输入有效的代码.";
                               return -1;
                            }
                        }catch(Exception e)
                        {
                          System.out.println("error-"+e);
                        }   
                          }
                      } 
                      if(fds[i].getDataType().equals("CHAR")||fds[i].getDataType().equals("DATE"))
                      {
                          String value=sessionInfo.getParameter(name).trim();
                          if(fds[i].getName().toUpperCase().equals("PWD"))
                          {
                              value=(new MD5()).getMD5ofStr(value);
                          }
                          cstmt.setString(para_index,value);                     
                      }
                      if(fds[i].getDataType().equals("INTEGER"))
                      {
                          if(!StringUtil.isNum(sessionInfo.getParameter(name)) &&!sessionInfo.getParameter(name).equals(""))
                          {
                              error_message="字段\""+fds[i].getCaption()+"\"不是整型.";
                              return -1;
                          }
                          int value=0;
                          if(StringUtil.isNum(sessionInfo.getParameter(name)))
                              value=Integer.parseInt(sessionInfo.getParameter(name));
                          else
                            if(fds[i].getInputType().equals("TEXT")) value=-1;
                          cstmt.setInt(para_index,value);                     
                      }
                      if(fds[i].getDataType().equals("FLOAT"))
                      {
                          if(!StringUtil.isNumEx(sessionInfo.getParameter(name))&&!sessionInfo.getParameter(name).equals(""))
                          {
                              error_message="字段\""+fds[i].getCaption()+"\"不是数字型.";
                              return -1;
                          }
                          float value=0;
                          if(StringUtil.isNumEx(sessionInfo.getParameter(name)))
                              value=Float.parseFloat(sessionInfo.getParameter(name));
                          else
                            if(fds[i].getInputType().equals("TEXT")) value=-1;
                          cstmt.setFloat(para_index,value);                     
                      }
                       if(fds[i].getDataType().equals("CLOB"))
                      {
                         // Clob clob = cstmt.getClob(4);
                         //Clob cb.getAsciiStream().read
                         // clob.setString(0,request.getParameter(name));
                          //clob.open (CLOB.MODE_READWRITE);
                          String value=sessionInfo.getParameter(name);
                        //  clob.putString(0,value);
                        //  clob.close();
                         // cstmt.setObject(para_index,clob);   
                         cstmt.setString(para_index,value); 
                      }      
                  } 
            }
             if(doc_type==doc.SET_PRODUCTS)
            {
                para_index++;
                cstmt.setInt(para_index,Integer.parseInt(sessionInfo.getParameter("parent_doc_id"))); 
                System.out.println("参数名称:父文档ID");
                System.out.println("参数值："+Integer.parseInt(sessionInfo.getParameter("parent_doc_id")));
                System.out.println("参数类型:INTEGER");
            }  
            if(!doc.is_global())
            {
                para_index++;
                cstmt.setInt(para_index,sessionInfo.getCompanyID()); 
                System.out.println("参数名称:俱乐部ID");
                System.out.println("参数值："+sessionInfo.getCompanyID());
                System.out.println("参数类型:INTEGER");
            }
            if(doc.isLogOperator())
            {
                 para_index++;
                cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
                System.out.println("参数名称:操作员ID");
                System.out.println("参数值："+this.sessionInfo.getOperatorID());
                System.out.println("参数类型:INTEGER");
            }
           
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            re=cstmt.getShort(1);
            cstmt.close();
            if(re<0)
            {
                   KException ke=new KException(re);
                   error_message="错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage();
                   return -2;
            }
      }catch(Exception e)
      {
            error_message="错误:"+e.toString();
            System.out.println(e);
            return(-3);
      }
      dblink.close();
      Log log=new Log();
      log.audit(sessionInfo.getCompanyID(),sessionInfo.getOperatorID(),doc_type,log.EVENT_UPDATE,doc_id);
      return 0;
  }
  /**
   * 删除数据时调用的方法
   * @param doc_id  删除数据的ID
   * @return 成功或错误编号
   */
  public int delete(int doc_id)
  {
    DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
    String sp=getSPDelete();
    try
    {
        int para_index=2;
        CallableStatement cstmt = dblink.prepareCall(sp); 
        cstmt.setInt(para_index,doc_id); 
        System.out.println("参数名称:doc_id");
        System.out.println("参数值："+doc_id);
        System.out.println("参数类型:INTEGER"); 
        if(doc.isLogOperator())
        {
            para_index++;
            cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
            System.out.println("参数名称:操作员ID");
            System.out.println("参数值："+this.sessionInfo.getOperatorID());
            System.out.println("参数类型:INTEGER");
        }

        cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
        cstmt.execute();
        int re=cstmt.getShort(1);
        cstmt.close();
        if(re<0)
        {
            KException ke=new KException(re);
            error_message="错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage();
            return(-2);
       }
    }catch(Exception e)
    {
        error_message="错误:"+e.toString();
        System.out.println(e);
        return(-3);
    }
    dblink.close();
    Log log=new Log();
    log.audit(sessionInfo.getCompanyID(),sessionInfo.getOperatorID(),doc_type,log.EVENT_UPDATE,doc_id);
    return 0;
  }
  /**
   * 保存成功系统跳转的方法
   * @return 
   */
  public  abstract String getNewSuccessInfo();
  /**
   * 修改成功系统跳转的方法
   * @return 
   */
  public  abstract String getUpdateSuccessInfo();
  /**
   * 删除成功系统跳转的方法
   * @return 
   */
  public  abstract String getDeleteSuccessInfo();

}
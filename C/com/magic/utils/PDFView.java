package com.magic.utils;
import java.util.*;
import java.sql.*;


public class PDFView 
{
  private String subject=null;
  private String subtitle=null;
  private String[][] data=new String[20][500];
  int rows;
  int cols;
  
  public PDFView()
  {
    
  }

  public String getSubject()
  {
    return subject;
  }

  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  public String getSubtitle()
  {
    return subtitle;
  }

  public void setSubtitle(String subtitle)
  {
    this.subtitle = subtitle;
  }

  void setData(ResultSet rs)
  {
    
     int width_count=0;
     this.cols=0;
     this.rows=0;
      Vector fds=new Vector();
     data[0][0]="ÐòºÅ";
     try{
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        for(int i=1;i<=numberOfColumns;i++)
        {  
            Field fd=FieldMap.getField(rsmd.getColumnName(i));
            if(fd!=null)
            {  
                if(fd.getVisible()) 
                {
                    cols++;
                    data[cols][0]=fd.getCaption();
                    width_count=width_count+fd.getDataSize(); 
                     fds.add(fd);
                }
            }
        }

        while(rs.next())
        {   
            for(int i=0;i<fds.size();i++)
            {
                Field fd=(Field)fds.elementAt(i);
                rows++;
                String name=fd.getName();
                String ref_field_name=fd.getRefIdName();
                String cell_data=null;
              
                    if(rs.getString(name)==null) 
                      cell_data="";
                    else
                    {   
                        if(fd.getDataType().equals("CHAR"))
                            cell_data=StringUtil.cEmpty(rs.getString(name));
                        if(fd.getDataType().equals("DATE"))
                            cell_data=rs.getDate(name)+"";
                        if(fd.getDataType().equals("FLOAT"))
                            cell_data=rs.getFloat(name)+"";
                        if(cell_data==null)
                             cell_data=StringUtil.cEmpty(rs.getString(name));
                    }
 
                    data[i][rows]=cell_data;
              rs.close();
                  }
        }
              }catch(Exception e)
          {
                System.err.println("ERROR>>"+e);
            } 
       
  }

  public String getData(int col,int row)
  {
    return data[col][row];
  }

  public int getRows()
  {
    return rows;
  }

  public int getCols()
  {
    return cols;
  }
}
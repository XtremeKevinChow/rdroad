package com.magic.utils;
import java.util.*;
import java.sql.*;
/**
 * 字段映射类
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class FieldMap 
{
    private static HashMap map=null;

    private static void initMap()
    {
        DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
        map=new HashMap();
       
        try{
             stmt=dblink.createStatement();rs= stmt.executeQuery("select * from s_field");
            while(rs.next())
            {
                Field fd=new Field(rs.getString("name"),rs.getString("caption"),rs.getInt("data_size"),rs.getString("data_type"),rs.getInt("is_visible"),rs.getInt("is_detail"),rs.getString("ref_lookup"),rs.getInt("ref_doc_type"),rs.getString("ref_id_name"),rs.getString("input_type"));
                map.put(rs.getString("name").toUpperCase(),fd);  
            }
          
        }catch(Exception e)
        {
           System.err.println(e); 
        } finally
        {
            try{
              if(rs!=null) rs.close();
              if(stmt!=null) stmt.close();
              dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static Field getField(String name)
    {
        if(map==null || map.isEmpty()) initMap();
        if(map.get(name.toUpperCase())==null) 
            return null;
        else
            return (Field)map.get(name.toUpperCase());
    }

}
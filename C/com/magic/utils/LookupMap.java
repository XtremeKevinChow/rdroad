package com.magic.utils;
import java.util.*;
import java.sql.*;

public class LookupMap 
{
    private static HashMap map=null;
    
    private static HashMap valueMap=new HashMap();

    private static void initMap()
    {
        DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
        map=new HashMap();
       
        try{
             stmt=dblink.createStatement();
             rs= stmt.executeQuery("select * from s_lookup");
            while(rs.next())
            {
                Lookup lu=new Lookup(rs.getString("name"),rs.getString("data_source"),rs.getString("value_field"),rs.getString("display_field"),rs.getString("key_field"),rs.getInt("is_company_constraint"));
                map.put(rs.getString("name").toUpperCase(),lu);  
            }
            rs.close();
            stmt.close();
        }catch(Exception e)
        {
           System.err.println(e); 
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
    }
    
    public static Lookup getLookup(String name)
    {
        if(map==null || map.isEmpty())  initMap();
        if(map.get(name.toUpperCase())==null) 
            return null;
        else
            return (Lookup)map.get(name.toUpperCase());
    }
    
    public static Map getListValues(String list_name,int company_id,String where)
    {
        Lookup lu=LookupMap.getLookup(list_name);
        if(lu==null)
        {
            System.err.println("ERRPR>>没有找到对应的Lookup名称:"+list_name+".");
            return null;
        }
        String valueField=lu.getValueField();
        String keyField=lu.getKeyField();
        boolean companyConstraint=lu.isCompanyConstraint();
        String dataSource=lu.getDataSource();
        
        HashMap map=new HashMap();
        
        String sql="select "+valueField+","+ keyField+" from "+dataSource+" where 1=1 ";
        if(companyConstraint)
            sql=sql+"  company_id="+company_id+" and ";
        //sql=sql+" status=0";
        if(where!=null)
            sql=sql+" and "+ where ;
        sql=sql+" order by "+valueField;
            DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;  
        try
        {
          
             stmt=dblink.createStatement();rs= stmt.executeQuery(sql);
            while(rs.next())
                map.put(rs.getString(valueField), rs.getString(keyField));  
        }catch(Exception e)
        {
            System.err.println("ERROR>>error in Lookup.getListValues();"+e);
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
        return map;
    }
    
}
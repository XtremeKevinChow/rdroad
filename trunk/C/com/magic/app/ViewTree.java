package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;

/**
 * 树形结构浏览界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class ViewTree 
{
  private String res;
  
 public  ViewTree(int login_company_id,int login_operator_id)
  {
     try
     {
         DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
         String catalog_name;
         String catalog_ID;
         String catalog_isLeaf;
         String catalog_level;
         String catalog_parentID;
         String item_count;
         String oldParentID="";
         int i=0;
          res="<script language=\"JavaScript\" src=\"tree.js\"></script><script language=\"JavaScript\">\n";
          res+="function defineMenuItems(treeVariable) {\n";
          res+="treeVariable = treeVariable.addItem(new obj_node('root','<b><font color=#000000>采购目录</font></b>','','root','','',0,''));\n";
          String sql="select catalog_id,parent_id,isleaf,catalog_level,catalog_name from vw_master_catalog where company_id="+login_company_id+" order by catalog_level,parent_id,catalog_name";
           stmt=dblink.createStatement();rs= stmt.executeQuery(sql);
          while (rs.next())
          {
            catalog_ID=rs.getString("catalog_id");
                catalog_parentID=rs.getString("parent_id");
                if (catalog_parentID.equals("0"))
                {
                  catalog_parentID="root";
                }
                catalog_isLeaf=rs.getString("isLeaf");
                catalog_level=rs.getString("catalog_Level");
                catalog_name=rs.getString("catalog_name");
                if (oldParentID.equals(catalog_parentID))
                  i=i+1;
                else
                {
                 i=1;
                 oldParentID=catalog_parentID;
                 }
                if (catalog_level.equals("0") || catalog_level.equals("1"))
                  
                  res+="treeVariable = treeVariable.addItem(new obj_node('"+catalog_ID+"','"+catalog_name+"','"+catalog_parentID+"','folder','','',"+i+",'main'));\n";
        
                else
                  res+="treeVariable = treeVariable.addItem(new obj_node('"+catalog_ID+"','"+catalog_name+"','"+catalog_parentID+"','folder','catalog_item_list.jsp?catalog_id="+catalog_ID+"','',"+i+",'main'));\n";
              
                }
        
           res+="}";
        
           res+="startMenu();\n";
            
           res+="</script>\n";
           dblink.close();
     }catch(Exception e)
     {
       System.err.println("ERROR in ViewTree."+e.toString());
       res="ERROR";
     }
  }
  
  public String getString()
  {
      return res;
  }
}
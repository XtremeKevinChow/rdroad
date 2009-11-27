package com.magic.utils;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;

import com.magic.crm.util.DBManager;
/**
 * 事务跳转处理
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class GoTo extends BaseServlet 
{
 
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setCharacterEncoding("GB2312");
    super.service(request, response);
    int transaction_code=0;
    try{
            transaction_code=Integer.parseInt(request.getParameter("t_code"));
        }catch(Exception e)
        {
             System.out.println(e);
             message("未完成",e.getMessage(),request, response);
             return;
        }
    	Connection conn = null;


    		
       Statement stmt=null;ResultSet rs=null;
     try
     {
     	conn=DBManager.getConnection();
         stmt=conn.createStatement();
         rs= stmt.executeQuery("select link_addr from s_menu where menu_type=0 and id="+transaction_code);
        if(!rs.next())
            {
                message("未完成","没有此事务代码:"+transaction_code,request, response);
                return;
            }
      else
        {
          System.out.println(rs.getString("link_addr")+"&t_code="+transaction_code);
          
          response.sendRedirect("."+rs.getString("link_addr")+"&t_code="+transaction_code);
        }
     }
      catch(Exception e)
      {
        System.err.println(e);
      }finally
      {
          try
            {
                 if(rs!=null) rs.close();
                 if(stmt!=null) stmt.close();
                 conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
      }
     
  } 
}
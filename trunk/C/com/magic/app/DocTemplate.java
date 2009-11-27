package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import com.magic.utils.*;
import oracle.sql.*;

/**
 * 文档模板Servlet
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class DocTemplate extends BaseServlet 
{
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
     request.setCharacterEncoding("GBK");
     super.service(request, response);
      if(!assertSession(request))
        {
           response.sendRedirect("/relogin.html");
           return;
        }
        HttpSession session=request.getSession();
        /*
         * 从SessionInfo中取得用户的公司Id，权限等
         */
        SessionInfo sessionInfo = new SessionInfo(
            ((Integer)session.getAttribute("login_company_id")).intValue(), 
            ((Integer)session.getAttribute("login_operator_id")).intValue(), 
            (java.util.HashMap)session.getAttribute("powermap"),
            request.getParameterMap());
            
            
     String name=request.getParameter("name");
     String content=request.getParameter("content");
     String description=request.getParameter("description");
     DBLink dblink=new DBLink();Statement stmt=null;ResultSet rs=null;
     try
     {
         stmt=dblink.createStatement();
         rs= stmt.executeQuery("select nval(max(id)+1,1) id from prd_document_templates");
        rs.next();
        int id=rs.getInt("id");
        Statement stmt1=dblink.createStatement();rs=stmt1.executeQuery ("select * from prd_document_templates");
        rs.moveToInsertRow();
        CLOB clob = (CLOB) rs.getObject ("content");
        rs.updateInt("id",id);
        rs.updateInt("company_id",sessionInfo.getCompanyID());
        rs.updateString("name",name);
        rs.updateString("description",description);
        rs.updateInt("status",0);
        /*
         * modify by jackey
         */
        //clob.open (CLOB.MODE_READWRITE);
        clob.putString(0,content);
        /*
         * modify by jackey
         */        
        //clob.close();
        rs.updateObject("content",clob);
        rs.insertRow();
      
        
     }catch(Exception e)
     {
       System.out.println(e);
     }finally
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
}
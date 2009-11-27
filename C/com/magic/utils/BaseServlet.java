package com.magic.utils;
import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
/**
 * 系统Servlet基类
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public abstract class BaseServlet extends HttpServlet 
{
  protected static final String CONTENT_TYPE = "text/html; charset=GBK";

  /**
   * 初始化
   * @param config
   * @throws javax.servlet.ServletException
   */
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }
  /**
   * 执行方法
   * @param request
   * @param response
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   */
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  }
   
  /**
   * 操作完成显示信息方法
   * @param title       标题名称
   * @param content     显示内容
   * @param request     
   * @param response
   */
   protected void message(String title,String content,HttpServletRequest request,HttpServletResponse response)
   {
       HttpSession session=request.getSession();
       session.setAttribute("showMsgTitle",title);
       session.setAttribute("showMsgContent",content);
       try
       {
         response.sendRedirect("../app/message");
       }catch(Exception e)
       {
         System.out.println(e);
       }
       
   }
   
  /**
   * 成功操作完成显示信息方法
   * @param title         标题名称
   * @param content       显示内容
   * @param request
   * @param response
   */
      protected void success(String title,String content,HttpServletRequest request,HttpServletResponse response)
   {
       HttpSession session=request.getSession();
       session.setAttribute("showMsgTitle",title);
       session.setAttribute("showMsgContent",content);
       try
       {
         response.sendRedirect("../app/success");
       }catch(Exception e)
       {
         System.out.println(e);
       }
   }
  /**
   * 在Session中插入登录人信息
   * @param request
   * @return 
   */
    protected boolean assertSession(HttpServletRequest request)
    {
       HttpSession session=request.getSession();

       if(session.getAttribute("login_company_id")==null || session.getAttribute("login_operator_id")==null)
       return false;
       
       try
       {
          int companyId=((Integer)session.getAttribute("login_company_id")).intValue();
          int operatorId=((Integer)session.getAttribute("login_operator_id")).intValue();
          if(companyId>0 && operatorId>0)
            return true;
          else
           return false;
       }catch(Exception e)
       {
         return false;
       }
    }
   
}
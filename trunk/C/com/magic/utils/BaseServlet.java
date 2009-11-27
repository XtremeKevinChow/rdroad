package com.magic.utils;
import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
/**
 * ϵͳServlet����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public abstract class BaseServlet extends HttpServlet 
{
  protected static final String CONTENT_TYPE = "text/html; charset=GBK";

  /**
   * ��ʼ��
   * @param config
   * @throws javax.servlet.ServletException
   */
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }
  /**
   * ִ�з���
   * @param request
   * @param response
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   */
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
  }
   
  /**
   * ���������ʾ��Ϣ����
   * @param title       ��������
   * @param content     ��ʾ����
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
   * �ɹ����������ʾ��Ϣ����
   * @param title         ��������
   * @param content       ��ʾ����
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
   * ��Session�в����¼����Ϣ
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
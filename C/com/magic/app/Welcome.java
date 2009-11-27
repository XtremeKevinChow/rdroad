package com.magic.app;
import com.magic.utils.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
/**
 * 产生欢迎界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class Welcome extends BaseServlet
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
           SessionInfo sessionInfo = new SessionInfo(
            ((Integer)session.getAttribute("login_company_id")).intValue(), 
            ((Integer)session.getAttribute("login_operator_id")).intValue(), 
            (java.util.HashMap)session.getAttribute("powermap"),
            request.getParameterMap());
    HTMLView hv=new HTMLView();
    hv.setSubject("我的桌面");
    
   // stmt=dblink.createStatement();rs= stmt.executeQuery("select * from vw_test");
   // hv.addListView(rs);
    
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter(); 
   
    out.println(hv.getHTML());
    out.close();
  }
}